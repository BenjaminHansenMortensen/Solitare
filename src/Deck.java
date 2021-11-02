import java.util.*;

public class Deck {
    public DoublyLinkedHashSet<Card> stockPile = new DoublyLinkedHashSet<>();
    public DoublyLinkedHashSet<Card> talonPile = new DoublyLinkedHashSet<>();

    public Deck() throws Exception {
        createDeck();
    }

    public void shuffle() throws Exception {
        List<Card> deck = new ArrayList<>();
        while (!stockPile.isEmpty()){
            deck.add(stockPile.remove());
        }

        Collections.shuffle(deck);
        for (Card card : deck) {
            stockPile.addLast(card);
        }
    }

    public void pickUpTalonPile() throws Exception {
        while (!talonPile.isEmpty()) {
            stockPile.addLast(talonPile.remove());
        }
    }

    private void createDeck() throws Exception {
        String[] suits = {"H", "C", "D", "S"};
        String[] values = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};

        for (String suit : suits){
            for (String value : values) {
                stockPile.addLast(new Card(value,suit));
            }
        }
    }
}
