import java.util.*;

public class Game {
    public Deck deck = new Deck();
    public Rules rules;
    public DoublyLinkedHashSet<Card> stockPile = deck.stockPile;
    public DoublyLinkedHashSet<Card> talonPile = deck.talonPile;


    public HashMap<String, Stack<Card>> tableau = new HashMap<>();
    public Stack<Card> tableauPile1st = new Stack<>();
    public Stack<Card> tableauPile2nd = new Stack<>();
    public Stack<Card> tableauPile3rd = new Stack<>();
    public Stack<Card> tableauPile4th = new Stack<>();
    public Stack<Card> tableauPile5th = new Stack<>();
    public Stack<Card> tableauPile6th = new Stack<>();
    public Stack<Card> tableauPile7th = new Stack<>();

    public Stack<Card> heartPile = new Stack<>();
    public Stack<Card> clubsPile = new Stack<>();
    public Stack<Card> diamondsPile = new Stack<>();
    public Stack<Card> spadesPile = new Stack<>();

    public Game() throws Exception {
        //deck.shuffle();
        createTableauAndAcePilesMap();
        dealTableauPiles();
        this.rules = new Rules(deck, tableau);
    }

    private void createTableauAndAcePilesMap(){
        tableau.put("1", tableauPile1st);
        tableau.put("2", tableauPile2nd);
        tableau.put("3", tableauPile3rd);
        tableau.put("4", tableauPile4th);
        tableau.put("5", tableauPile5th);
        tableau.put("6", tableauPile6th);
        tableau.put("7", tableauPile7th);

        tableau.put("H", heartPile);
        tableau.put("C", clubsPile);
        tableau.put("D", diamondsPile);
        tableau.put("S", spadesPile);
    }

    private void dealTableauPiles(){
        for (int pile = 1; pile <= 7; pile++){
            addFaceUpCard("" + pile);
            for (int otherPile = 1 + pile; otherPile <= 7; otherPile++){
                addFaceDownCard("" + otherPile);
            }
        }
    }

    private void addFaceUpCard(String pile){
        Card card = stockPile.remove();
        card.setFaceUp();
        card.setLocation(pile);
        tableau.get(pile).add(card);
    }

    private void addFaceDownCard(String pile){
        Card card = stockPile.remove();
        card.setLocation(pile);
        tableau.get(pile).add(card);
    }

    public void viewTableau(){
        System.out.printf("Tableau:    ");
        for (String pile : new String[]{"1", "2", "3", "4", "5", "6", "7"}){
            System.out.printf(tableau.get(pile).toString() + "   ");
        }
        System.out.println();
    }

    public void viewAcePiles(){
        System.out.printf("Ace Piles:    ");
        for (String pile : new String[]{"H", "C", "D", "S"}){
            System.out.printf(tableau.get(pile).toString() + "   ");
        }
        System.out.println();
    }

    public void viewStockPile(){
        System.out.println("Stock Pile:   " + stockPile.getNodes());
    }

    public void viewAccessibleCardsInStockPile() {
        System.out.println("Accessiable cards:   " + rules.accessibleCardsInStockPile.getNodes());
    }

    public void viewTalonPile(){
        System.out.println("Talon Pile:   " + talonPile.getNodes());
    }
}
