package co.edu.uptc.server.model;
//
//import co.edu.uptc.enums.GameState;
//
//public class Player {
//    private String name;
//    private int id;
//    private GameState gameState;
//    private int bet;
//    private int balance;
//    private Hand hand;
//    private String action;
//    private boolean inGame;
//    private boolean inTurn;
//
//    public Player() {
//        this.name = null;
//        this.inGame = false;
//        this.id = -1;
//        this.balance = 1000;
//        this.bet = 0;
//        this.action = "STAND";
//    }
//
//    public Player(String name) {
//        this();
//        this.name = name;
//    }
//
//    // Getters y Setters
//    public int getId() { return id; }
//    public void setId(int id) { this.id = id; }
//
//    public void setName(String name) { this.name = name; }
//    public String getName() { return name; }
//
//    public GameState getGameState() { return gameState; }
//    public void setGameState(GameState gameState) { this.gameState = gameState; }
//
//    public String getAction() { return action; }
//    public void setAction(String action) { this.action = action; }
//
//    public int getBalance() { return balance; }
//    public void setBalance(int balance) { this.balance = balance; }
//
//    public void setHand(Hand hand) { this.hand = hand; }
//    public Hand getHand() { return hand; }
//
//    public boolean isInGame() { return inGame; }
//    public void setInGame(boolean inGame) { this.inGame = inGame; }
//
//    public int getBet() { return bet; }
//    public void setBet(int bet) { this.bet = bet; }
//
//
//}