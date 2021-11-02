import java.util.*;

public class Rules {
    final int MAX_ACCESSIBLE = 8;
    public Deck deck;
    public DoublyLinkedHashSet<Card> stockPile;
    public DoublyLinkedHashSet<Card> talonPile;
    public HashMap<String, Stack<Card>> tableau;
    public DoublyLinkedHashSet<Card> accessibleCardsInStockPile = new DoublyLinkedHashSet<>();
    private Stack<Card> chainOfCard = new Stack<>();

    public Rules(Deck deck, HashMap<String, Stack<Card>> tableau){
        this.deck = deck;
        this.stockPile = deck.stockPile;
        this.talonPile = deck.talonPile;
        this.tableau = tableau;
    }

    public boolean addCardToTableauPileFromTableauPile(String fromTableauPile, Card card, String toTableauPile){
        Stack<Card> fromPile = tableau.get(fromTableauPile);
        Stack<Card> toPile = tableau.get(toTableauPile);

        if (!card.location.equals(fromTableauPile) || !card.faceUp) {
            System.out.println("Bugged move");
            return false;
        }

        try {
            Card target = toPile.peek();

            if (oppositeColor(card, target) && targetIsOneLarger(target, card)) {
                moveChainOfCards(fromPile, card, toPile, toTableauPile);
                discoverCard(fromPile);
                return true;
            }
            else {
                System.out.println("Not allowed");
                return false;
            }
        } catch (Exception e){
            if (card.value.equals("K")){
                moveChainOfCards(fromPile, card, toPile, toTableauPile);
                discoverCard(fromPile);
                return true;
            }
            else {
                System.out.println("Not allowed");
                return false;
            }
        }
    }

    public boolean addCardToTableauPileFromAcePile(String acePile, Card card, String tableauPile) {
        Stack<Card> fromAcePile = tableau.get(acePile);
        Stack<Card> toTableauPile = tableau.get(tableauPile);

        if (!card.location.equals(acePile) || !card.faceUp) {
            System.out.println("Bugged move");
            return false;
        }

        try {
            Card target = toTableauPile.peek();

            if (oppositeColor(card, target) && targetIsOneLarger(target, card)) {
                fromAcePile.remove(card);
                toTableauPile.add(card);
                card.location = tableauPile;
                return true;
            }
            else {
                System.out.println("Not allowed");
                return false;
            }
        }catch (Exception e) {
            if (card.value.equals("K")){
                fromAcePile.remove(card);
                toTableauPile.add(card);
                card.location = tableauPile;
                return true;
            }
            else {
                System.out.println("Not allowed");
                return false;
            }
        }
    }


    public boolean addCardToTableauPileFromStockPile(Card card, String tableauPile) throws Exception {
        Stack<Card> toTableauPile = tableau.get(tableauPile);

        if (!card.location.equals("stock") || !card.faceUp) {
            System.out.println("Bugged move");
            return false;
        }

        try {
            Card target = toTableauPile.peek();

            if (oppositeColor(card, target) && targetIsOneLarger(target, card)) {
                discoverCard(card);
                stockPile.remove(card);
                toTableauPile.add(card);
                card.location = tableauPile;
                return true;
            }
            else {
                System.out.println("Not allowed");
                return false;
            }
        }catch (Exception e) {
            if (card.value.equals("K")){
                discoverCard(card);
                stockPile.remove(card);
                toTableauPile.add(card);
                card.location = tableauPile;
                return true;
            }
            else {
                System.out.println("Not allowed");
                return false;
            }
        }

    }

    public boolean addCardToAcePileFromStockPile(Card card, String acePile) throws Exception {
        Stack<Card> toAcePile = tableau.get(acePile);

        if (!card.location.equals("stock") || !card.faceUp) {
            System.out.println("Bugged move");
            return false;
        }

        try {
            Card target = toAcePile.peek();

            if (card.suit.equals(acePile) && targetIsOneLarger(card, target)) {
                discoverCard(card);
                stockPile.remove(card);
                toAcePile.add(card);
                card.location = acePile;
                return true;
            }
            else{
                System.out.println("Not allowed");
                return false;
            }
        }catch (Exception e) {
            if (card.value.equals("A") && card.suit.equals(acePile)){
                discoverCard(card);
                stockPile.remove(card);
                toAcePile.add(card);
                card.location = acePile;
                return true;
            }
            else {
                System.out.println("Not allowed");
                return false;
            }
        }
    }

    public boolean addCardToAcePileFromTableauPile(String tableauPile, Card card, String acePile){
        Stack<Card> fromTableauPile = tableau.get(tableauPile);
        Stack<Card> toAcePile = tableau.get(acePile);

        if (!card.location.equals(tableauPile) || !card.faceUp) {
            System.out.println("Bugged move");
            return false;
        }

        try {
            Card target = toAcePile.peek();

            if (card.suit.equals(acePile) && targetIsOneLarger(card, target)) {
                fromTableauPile.remove(card);
                toAcePile.add(card);
                card.location = acePile;
                discoverCard(fromTableauPile);
                return true;
            }
            else {
                System.out.println("Not allowed");
                return false;
            }

        }catch (Exception e) {
            if (card.value.equals("A") && card.suit.equals(acePile)){
                fromTableauPile.remove(card);
                toAcePile.add(card);
                card.location = acePile;
                discoverCard(fromTableauPile);
                return true;
            }
            else {
                System.out.println("Not allowed");
                return false;
            }

        }
    }

    private void discoverCard(Stack<Card> pile){
        try {
            pile.peek().setFaceUp();
        }catch (Exception e) {
        }
    }

    private void discoverCard(Card card) throws Exception {
        Card discovered = stockPile.getPrevious(card);
        discovered.setFaceUp();
        accessibleCardsInStockPile.swap(card, discovered); // Du er her
    }

    public void passThroughDeck() throws Exception {
        accessibleCardsInStockPile.clear();

        while (stockPile.size() > 2){
            accessibleCardsInStockPile.addLast(turnThreeCards());
        }
        while (!stockPile.isEmpty()) {
            talonPile.addLast(stockPile.remove());
            if (stockPile.size() == 1) {
                Card card = stockPile.remove();
                card.setFaceUp();
                talonPile.addLast(card);
            }
        }
        deck.pickUpTalonPile();
    }

    public Card turnThreeCards() throws Exception {
        Card card = stockPile.getHead();
        for (int i = 0; i < 3; i++){
            card = stockPile.remove();
            talonPile.addLast(card);


        }
        card.setFaceUp();
        return card;
    }

    private void moveChainOfCards(Stack<Card> fromPile, Card card, Stack<Card> toPile, String location){

        while (!fromPile.peek().equals(card)){
            chainOfCard.add(fromPile.pop());
        }
        chainOfCard.add(fromPile.pop());

        while (!chainOfCard.isEmpty()) {
            card = chainOfCard.pop();
            toPile.add(card);
            card.location = location;
        }
    }

    public boolean targetIsOneLarger(Card target, Card card){
        if (target.numericValue == (card.numericValue + 1)){
            return true;
        }
        return false;
    }

    public boolean oppositeColor(Card card1, Card card2){
        if (card1.redColor == card2.redColor){
            return false;
        }
        return true;
    }

    public void viewAccessibleCardsInStockPile(){
        System.out.println("Accessible Cards In Stock Pile:   " + accessibleCardsInStockPile);
    }
}
