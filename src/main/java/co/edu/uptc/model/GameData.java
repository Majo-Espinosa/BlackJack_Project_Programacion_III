package co.edu.uptc.model;

import co.edu.uptc.enums.GameState;

import java.util.ArrayList;
import java.util.List;

public class GameData {
    private List<Player> players;
    private GameState gameState;
    private String currentPlayer;
    private String message;
    private int phaseTimeSeconds;

    public GameData() {
        this.players = new ArrayList<>();
    }

    public GameData(List<Player> players, GameState gameState) {
        this();
        this.players = players;
        this.gameState = gameState;
    }

    // Getters y setters
    public List<Player> getPlayers() { return players; }
    public void setPlayers(List<Player> players) { this.players = players; }

    public GameState getGameState() { return gameState; }
    public void setGameState(GameState gameState) { this.gameState = gameState; }

    public String getCurrentPlayer() { return currentPlayer; }
    public void setCurrentPlayer(String currentPlayer) { this.currentPlayer = currentPlayer; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public int getPhaseTimeSeconds() { return phaseTimeSeconds; }
    public void setPhaseTimeSeconds(int phaseTimeSeconds) { this.phaseTimeSeconds = phaseTimeSeconds; }
}