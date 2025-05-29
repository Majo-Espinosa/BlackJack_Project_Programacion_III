package co.edu.uptc.server;

import co.edu.uptc.enums.*;
import co.edu.uptc.model.*;
import java.util.*;
import java.util.concurrent.*;

//Gestor de la logica en si del juego
public class GameManager {
    // Estado del juego
    private final Map<String, Player> players;
    private final Map<String, ClientHandler> clientHandlers;
    private final Deck deck;
    private final Dealer dealer;
    private final ScheduledExecutorService scheduler;
    private final List<String> playerOrder;
    // Momento actually: Una variable es volatile es una que puede ser modificada
    // por múltiples hilos al mismo tiempo☝️🤓
    private volatile GameState gameState;
    private volatile String currentPlayerId;
    private volatile int currentPlayerIndex;
    // Representa una tarea que se está ejecutando o que puede completarse en el
    // futuro, pero no se especifica el tipo
    // del resultado (por eso el <?>)
    private volatile Future<?> currentTimeoutTask;

    // Constructor
    public GameManager() {
        this.players = new ConcurrentHashMap<>();
        this.clientHandlers = new ConcurrentHashMap<>();
        this.dealer = new Dealer();
        this.gameState = GameState.WAITING_PLAYERS;
        this.scheduler = Executors.newScheduledThreadPool(3);
        this.deck = new Deck();
        this.playerOrder = Collections.synchronizedList(new ArrayList<>());
        this.currentPlayerIndex = 0;
        this.currentPlayerId = null;
        this.currentTimeoutTask = null;

        logGameInfo("GameManager inicializado");
    }

    // ----------Gestion de los jugadores----------

    public synchronized void addPlayer(String playerId, ClientHandler clientHandler) {
        if (playerId == null || clientHandler == null) {
            logError("Intento de agregar jugador con datos nulos");
            return;
        }

        if (players.containsKey(playerId)) {
            logWarning("Jugador " + playerId + " ya existe en el juego");
            return;
        }

        // Crear y configurar el jugador
        Player player = new Player(playerId);
        player.setBalance(GameManagerConstants.INITIAL_BALANCE);

        players.put(playerId, player);
        clientHandlers.put(playerId, clientHandler);

        logGameInfo("Jugador " + playerId + " agregado. Total jugadores: " + players.size());

        // Notificar al jugador su balance inicial
        sendMessageToPlayer(playerId, Map.of(
                "type", "balance_update",
                "balance", GameManagerConstants.INITIAL_BALANCE,
                "message", "Bienvenido al Blackjack! Balance inicial: $" + GameManagerConstants.INITIAL_BALANCE));

        // Iniciar juego
        if (shouldStartNewGame()) {
            scheduleGameStart();
        }
    }

    public synchronized void removePlayer(String playerId) {
        if (playerId == null || !players.containsKey(playerId)) {
            return;
        }

        players.remove(playerId);
        clientHandlers.remove(playerId);
        playerOrder.remove(playerId);

        logGameInfo("Jugador " + playerId + " removido. Jugadores restantes: " + players.size());

        // Ajustar índice del jugador actual si es necesario
        if (playerId.equals(currentPlayerId)) {
            advanceToNextPlayer();
        }

        // Si no hay jugadores, volver a estado de espera
        if (players.isEmpty()) {
            resetGameToWaitingState();
        }
    }

    // ----------Control del ciclo del juego----------

    private void startNewRound() {
        if (!canStartNewRound()) {
            resetGameToWaitingState();
            return;
        }

        logGameInfo("=== INICIANDO NUEVA RONDA ===");

        // Cambiar estado y preparar componentes
        gameState = GameState.BETTING_PHASE;
        deck.shuffle();
        dealer.clearHand();

        // Limpiar estado de jugadores
        preparePlayersForNewRound();

        // Establecer orden de jugadores
        setupPlayerOrder();

        // Solicitar apuestas
        requestBetsFromPlayers();

        // Configurar timeout para apuestas
        scheduleAutomaticBets();
    }

    // Apuestas para jugadores que no lo hicieron en los 20 segundos
    private void processAutomaticBets() {
        logGameInfo("Procesando apuestas automáticas...");

        for (Player player : players.values()) {
            if (player.getCurrentBet() == 0) {
                processAutomaticBetForPlayer(player);
            }
        }

        startGamePhase();
    }

    private void startGamePhase() {
        gameState = GameState.DEALING_CARDS;
        logGameInfo("=== REPARTIENDO CARTAS ===");

        // Repartir cartas iniciales
        dealInitialCards();

        // Cambiar a fase de acciones de jugadores
        gameState = GameState.PLAYER_ACTIONS;
        currentPlayerIndex = 0;
        currentPlayerId = null;

        broadcastGameState();
        processNextPlayerAction();
    }

    // Turno del siguiente jugador
    private void processNextPlayerAction() {
        cancelCurrentTimeout();

        // Buscar el siguiente jugador activo
        String nextPlayerId = findNextActivePlayer();
        // Todos jugaron
        if (nextPlayerId == null) {
            playDealerTurn();
            return;
        }
        // Configurar turno del jugador
        setupPlayerTurn(nextPlayerId);
    }

    // Turno del dealer
    private void playDealerTurn() {
        gameState = GameState.DEALER_TURN;
        logGameInfo("=== TURNO DEL DEALER ===");
        // Dealer juega según reglas estándar
        playDealerHand();
        logGameInfo("Dealer terminó con valor: " + dealer.getHandValue());
        broadcastGameState();
        // Programa la ejecución del metodo calculateResults() después de un retraso
        // fijo para dar tiempo a los
        // jugadores a ver las cartas del crupier antes de mostrar los resultados.
        scheduler.schedule(this::calculateResults, GameManagerConstants.DELAY_BEFORE_RESULTS, TimeUnit.SECONDS);
    }

    // Resultados de la ronda
    private void calculateResults() {
        gameState = GameState.ROUND_FINISHED;
        logGameInfo("=== CALCULANDO RESULTADOS ===");

        int dealerValue = dealer.getHandValue();
        boolean dealerBusted = dealerValue > 21;

        // Procesar resultado para cada jugador
        for (Player player : players.values()) {
            if (playerParticipatedInRound(player)) {
                processPlayerResult(player, dealerValue, dealerBusted);
            }
        }

        // Enviar estado final y programar nueva ronda
        broadcastGameState();
        scheduleNextRound();
    }

    // ----------Procesar las acciones del cliente----------

    public synchronized void processBet(String playerId, int amount) {
        if (!isValidBettingContext(playerId, amount)) {
            return;
        }

        Player player = players.get(playerId);
        // Validar y procesar apuesta
        int validatedAmount = validateBetAmount(player, amount);
        if (validatedAmount <= 0) {
            logWarning("Apuesta inválida de " + playerId + ": $" + amount);
            return;
        }
        // Aplicar apuesta
        player.setBet(validatedAmount);
        player.setBalance(player.getBalance() - validatedAmount);

        logGameInfo("Apuesta procesada - " + playerId + ": $" + validatedAmount +
                " (Balance restante: $" + player.getBalance() + ")");
        // Notificar al jugador
        sendMessageToPlayer(playerId, Map.of(
                "type", "bet_accepted",
                "amount", validatedAmount,
                "newBalance", player.getBalance()));
    }

    // Procesa una acción de jugador durante su turno
    public synchronized void processPlayerAction(String playerId, PlayerAction action) {
        if (!isValidActionContext(playerId)) {
            return;
        }

        Player player = players.get(playerId);
        logGameInfo("Procesando acción de " + playerId + ": " + action);

        boolean shouldAdvance = executePlayerAction(player, action);

        if (shouldAdvance) {
            advanceToNextPlayer();
        } else {
            // Continuar con el mismo jugador
            setupPlayerTurn(playerId);
        }

        broadcastGameState();
    }

    // ----------Utilidades para el juego----------
    private boolean shouldStartNewGame() {
        return gameState == GameState.WAITING_PLAYERS &&
                players.size() >= GameManagerConstants.MIN_PLAYERS_TO_START;
    }

    private boolean canStartNewRound() {
        return !players.isEmpty();
    }

    private void scheduleGameStart() {
        scheduler.schedule(this::startNewRound, 2, TimeUnit.SECONDS);
    }

    private void resetGameToWaitingState() {
        gameState = GameState.WAITING_PLAYERS;
        currentPlayerId = null;
        currentPlayerIndex = 0;
        cancelCurrentTimeout();
        logGameInfo("Esperando jugadores...");
    }

    private void preparePlayersForNewRound() {
        for (Player player : players.values()) {
            player.clearHands();
        }
    }

    private void setupPlayerOrder() {
        playerOrder.clear();
        playerOrder.addAll(players.keySet());
        currentPlayerIndex = 0;
    }

    // Mensaje a los clientes para pedir la apuesta
    private void requestBetsFromPlayers() {
        broadcastMessage(Map.of(
                "type", "prompt_bet",
                "minBet", GameManagerConstants.MIN_BET,
                "maxBet", GameManagerConstants.MAX_BET,
                "timeLimit", GameManagerConstants.BET_TIME_SECONDS));
    }

    // Programar la apuesta automatica si no hace ninguna
    private void scheduleAutomaticBets() {
        currentTimeoutTask = scheduler.schedule(() -> {
            processAutomaticBets();
        }, GameManagerConstants.BET_TIME_SECONDS, TimeUnit.SECONDS);
    }

    private void processAutomaticBetForPlayer(Player player) {
        if (player.getBalance() >= GameManagerConstants.MIN_BET) {
            player.setBet(GameManagerConstants.MIN_BET);
            player.setBalance(player.getBalance() - GameManagerConstants.MIN_BET);
            logGameInfo("Apuesta automática para " + player.getId() + ": $" + GameManagerConstants.MIN_BET);
        } else {
            notifyInsufficientBalance(player.getId());
        }
    }

    private void dealInitialCards() {
        // Repartir 2 cartas a cada jugador y dealer
        for (int round = 0; round < 2; round++) {
            // Cartas para jugadores con apuesta
            for (String playerId : playerOrder) {
                Player player = players.get(playerId);
                if (player != null && player.getCurrentBet() > 0) {
                    player.addCard(deck.dealCard());
                }
            }
            // Carta para dealer
            dealer.addCard(deck.dealCard());
        }
    }

    private String findNextActivePlayer() {
        while (currentPlayerIndex < playerOrder.size()) {
            String playerId = playerOrder.get(currentPlayerIndex);
            Player player = players.get(playerId);

            if (isPlayerActiveForTurn(player)) {
                return playerId;
            }
            currentPlayerIndex++;
        }
        return null;
    }

    private boolean isPlayerActiveForTurn(Player player) {
        return player != null &&
                player.getCurrentBet() > 0 &&
                !player.isFinished() &&
                player.getHandValue() < 21;
    }

    private void setupPlayerTurn(String playerId) {
        currentPlayerId = playerId;
        logGameInfo("Turno del jugador: " + playerId);

        sendMessageToPlayer(playerId, Map.of(
                "type", "prompt_hit",
                "timeLimit", GameManagerConstants.ACTION_TIME_SECONDS));
        // Configurar timeout
        currentTimeoutTask = scheduler.schedule(() -> {
            if (gameState == GameState.PLAYER_ACTIONS && playerId.equals(currentPlayerId)) {
                logGameInfo("Timeout para " + playerId + ", se planta automáticamente");
                processPlayerAction(playerId, PlayerAction.STAND);
            }
        }, GameManagerConstants.ACTION_TIME_SECONDS, TimeUnit.SECONDS);

        broadcastGameState();
    }

    private void advanceToNextPlayer() {
        cancelCurrentTimeout();
        currentPlayerIndex++;
        processNextPlayerAction();
    }

    // Cancela una tarea programada previamente (por ejemplo, un temporizador) si
    // todavía no ha terminado
    private void cancelCurrentTimeout() {
        if (currentTimeoutTask != null && !currentTimeoutTask.isDone()) {
            currentTimeoutTask.cancel(false);
            currentTimeoutTask = null;
        }
    }

    private void playDealerHand() {
        while (dealer.getHandValue() < 17) {
            Card newCard = deck.dealCard();
            dealer.addCard(newCard);
            logGameInfo("Dealer pidió: " + newCard + " (Valor total: " + dealer.getHandValue() + ")");
        }
    }

    private boolean isValidBettingContext(String playerId, int amount) {
        if (gameState != GameState.BETTING_PHASE) {
            logWarning("Apuesta rechazada de " + playerId + " - no es fase de apuestas");
            return false;
        }
        if (!players.containsKey(playerId)) {
            logWarning("Apuesta de jugador no encontrado: " + playerId);
            return false;
        }
        return true;
    }

    private int validateBetAmount(Player player, int amount) {
        if (amount < GameManagerConstants.MIN_BET || amount > GameManagerConstants.MAX_BET
                || amount > player.getBalance()) {
            return -1;
        }
        return amount;
    }

    private boolean isValidActionContext(String playerId) {
        return gameState == GameState.PLAYER_ACTIONS &&
                playerId.equals(currentPlayerId) &&
                players.containsKey(playerId);
    }

    private boolean executePlayerAction(Player player, PlayerAction action) {
        switch (action) {
            case HIT -> {
                return executeHitAction(player);
            }
            case STAND -> {
                return executeStandAction(player);
            }
            case DOUBLE -> {
                return executeDoubleAction(player);
            }
            case SURRENDER -> {
                return executeSurrenderAction(player);
            }
            default -> {
                logWarning("Acción no implementada: " + action);
                return executeStandAction(player);
            }
        }
    }

    private boolean executeHitAction(Player player) {
        Card newCard = deck.dealCard();
        player.addCard(newCard);

        logGameInfo(player.getId() + " pidió carta: " + newCard +
                " (Valor total: " + player.getHandValue() + ")");
        if (player.getHandValue() >= 21) {
            player.setFinished(true);
            return true; // Avanzar al siguiente jugador
        }
        return false; // Continuar con el mismo jugador
    }

    private boolean executeStandAction(Player player) {
        logGameInfo(player.getId() + " se plantó");
        player.setFinished(true);
        return true; // Avanzar al siguiente jugador
    }

    private boolean executeDoubleAction(Player player) {
        if (!canPlayerDouble(player)) {
            logGameInfo("No se puede doblar, tratando como STAND");
            return executeStandAction(player);
        }
        // Doblar apuesta
        int additionalBet = player.getCurrentBet();
        player.setBalance(player.getBalance() - additionalBet);
        player.setBet(player.getCurrentBet() + additionalBet);
        // Dar una carta y terminar turno
        Card newCard = deck.dealCard();
        player.addCard(newCard);
        player.setFinished(true);
        logGameInfo(player.getId() + " dobló. Nueva apuesta: $" + player.getCurrentBet() +
                ", Carta: " + newCard + " (Valor: " + player.getHandValue() + ")");
        // Notificar actualización
        sendMessageToPlayer(player.getId(), Map.of(
                "type", "action_processed",
                "action", "DOUBLE",
                "newBalance", player.getBalance(),
                "newBet", player.getCurrentBet()));
        return true; // Avanzar al siguiente jugador
    }

    private boolean executeSurrenderAction(Player player) {
        // Recuperar la mitad de la apuesta
        int halfBet = player.getCurrentBet() / 2;
        player.setBalance(player.getBalance() + halfBet);
        player.setFinished(true);
        logGameInfo(player.getId() + " se rindió. Recupera: $" + halfBet);
        sendMessageToPlayer(player.getId(), Map.of(
                "type", "game_result",
                "result", "SURRENDER",
                "amount", -player.getCurrentBet() + halfBet,
                "newBalance", player.getBalance()));
        return true; // Avanzar al siguiente jugador
    }

    private boolean canPlayerDouble(Player player) {
        return player.getHands().get(0).getCards().size() == 2 &&
                player.getBalance() >= player.getCurrentBet();
    }

    private boolean playerParticipatedInRound(Player player) {
        return player.getCurrentBet() > 0;
    }

    private void processPlayerResult(Player player, int dealerValue, boolean dealerBusted) {
        GameResult result = determineResult(player, dealerValue, dealerBusted);
        int winAmount = calculateWinAmount(player, result);
        int netGain = winAmount - player.getCurrentBet();

        // Actualizar balance
        player.setBalance(player.getBalance() + winAmount);
        logGameInfo("Resultado para " + player.getId() + ": " + result +
                " | Apuesta: $" + player.getCurrentBet() +
                " | Ganancia total: $" + winAmount +
                " | Ganancia neta: $" + netGain +
                " | Nuevo balance: $" + player.getBalance());

        // Enviar resultado al cliente
        sendMessageToPlayer(player.getId(), Map.of(
                "type", "game_result",
                "result", result.toString(),
                "amount", netGain,
                "totalWin", winAmount,
                "bet", player.getCurrentBet(),
                "newBalance", player.getBalance(),
                "playerValue", player.getHandValue(),
                "dealerValue", dealerValue));
    }

    private void scheduleNextRound() {
        scheduler.schedule(() -> {
            removePlayersWithoutBalance();
            if (!players.isEmpty()) {
                startNewRound();
            } else {
                resetGameToWaitingState();
            }
        }, GameManagerConstants.DELAY_BETWEEN_ROUNDS, TimeUnit.SECONDS);
    }

    private void removePlayersWithoutBalance() {
        List<String> playersToRemove = new ArrayList<>();

        for (Player player : players.values()) {
            if (player.getBalance() < GameManagerConstants.MIN_BET) {
                playersToRemove.add(player.getId());
                notifyInsufficientBalance(player.getId());
            }
        }

        for (String playerId : playersToRemove) {
            logGameInfo("Removiendo jugador sin balance suficiente: " + playerId);
            removePlayer(playerId);
        }
    }

    private void notifyInsufficientBalance(String playerId) {
        sendMessageToPlayer(playerId, Map.of(
                "type", "insufficient_balance",
                "message", "Balance insuficiente para continuar. ¡Gracias por jugar!"));
    }

    private GameResult determineResult(Player player, int dealerValue, boolean dealerBusted) {
        int playerValue = player.getHandValue();
        boolean playerBusted = playerValue > 21;
        boolean playerBlackjack = player.isBlackjack();
        boolean dealerBlackjack = dealer.getHand().isBlackjack();
        // Perdida
        if (playerBusted) {
            return GameResult.LOSE;
        }
        // Ganancia
        if (dealerBusted) {
            return playerBlackjack ? GameResult.BLACKJACK : GameResult.WIN;
        }
        // Blackjack
        if (playerBlackjack && dealerBlackjack) {
            return GameResult.PUSH;
        }
        if (playerBlackjack) {
            return GameResult.BLACKJACK;
        }
        if (dealerBlackjack) {
            return GameResult.LOSE;
        }
        // Comparar valores con el dealer
        if (playerValue > dealerValue) {
            return GameResult.WIN;
        } else if (playerValue < dealerValue) {
            return GameResult.LOSE;
        } else {
            return GameResult.PUSH;
        }
    }

    // Calcular ganancia
    private int calculateWinAmount(Player player, GameResult result) {
        int bet = player.getCurrentBet();
        return switch (result) {
            case BLACKJACK -> (int) (bet * 2.5); // Blackjack paga 3:2
            case WIN -> bet * 2; // Ganancia normal 1:1
            case PUSH -> bet; // Empate: devolver apuesta
            case LOSE, SURRENDER -> 0; // Pérdida: no devolver nada
        };
    }

    // ----------Comunicacion----------
    private void sendMessageToPlayer(String playerId, Map<String, Object> message) {
        ClientHandler handler = clientHandlers.get(playerId);
        if (handler != null && handler.isActive()) {
            handler.sendMessage(message);
        }
    }

    // Enviar a todos
    private void broadcastMessage(Map<String, Object> message) {
        for (ClientHandler handler : clientHandlers.values()) {
            if (handler.isActive()) {
                handler.sendMessage(message);
            }
        }
    }

    // Enviar el game state
    private void broadcastGameState() {
        Map<String, Object> gameStateData = createGameStateData();
        broadcastMessage(Map.of(
                "type", "game_state",
                "data", gameStateData));
    }

    // Preparar el game state
    private Map<String, Object> createGameStateData() {
        Map<String, Object> data = new HashMap<>();
        // Estado del dealer (mostrar todas las cartas solo al final)
        boolean showAllDealerCards = gameState == GameState.ROUND_FINISHED ||
                gameState == GameState.DEALER_TURN;
        data.put("dealer", dealer.toMap(showAllDealerCards));
        // Estado de los jugadores
        data.put("players", players.values().stream()
                .map(Player::toMap)
                .toList());
        data.put("gameState", gameState.toString());
        data.put("currentPlayer", currentPlayerId);
        data.put("minBet", GameManagerConstants.MIN_BET);
        data.put("maxBet", GameManagerConstants.MAX_BET);
        data.put("totalPlayers", players.size());
        return data;
    }

    // ----------Log dicese imprimir en consola del servidor----------

    private void logGameInfo(String message) {
        System.out.println("[GAME] " + message);
    }

    private void logWarning(String message) {
        System.out.println("[WARNING] " + message);
    }

    private void logError(String message) {
        System.err.println("[ERROR] " + message);
    }

}