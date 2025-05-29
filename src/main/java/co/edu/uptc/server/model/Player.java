package co.edu.uptc.server.model;

public class Player {
    private String name;
    private int id;
    private boolean inTurn;
    private int bet;
    private int balance;
    private Hand hand;
    private String action;


    //private List<Hand> hands; --> para metodo dividir


    public Player(String name) {
        this.name = name;
        this.id = -1; //  valor -1 para un jugador que no esta enlazado a un cliente
        this.balance = 1000; // Balance inicial
        this.bet = 0;
        this.action = "STAND"; //se planta por defecto si no se le cambia la accion

        //this.hands = new ArrayList<>();
        //this.hands.add(new Hand()); // Mano principal
    }

   /*

   public void addCard(Card card) {

        if (!hands.isEmpty()) {
           hands.get(0).addCard(card);
        }

    }
    */

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean isInTurn() {
        return inTurn;
    }

    public void setInTurn(boolean inTurn) {
        this.inTurn = inTurn;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    public Hand getHand() {
        return hand;
    }


//    public List<Hand> getHands() { return hands; }
}