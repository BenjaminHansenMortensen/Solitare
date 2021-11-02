public class Main2 {
    public static void main(String[] args) throws Exception {
        Game g = new Game();
        Rules move = g.rules;
        move.passThroughDeck();
        viewGameInitial(g);

        // Testing
        move.addCardToAcePileFromTableauPile("1", g.tableau.get("1").get(0), "H");
        viewGame(g);
        move.addCardToTableauPileFromTableauPile("3", g.tableau.get("3").peek(), "7");
        viewGame(g);
        move.addCardToTableauPileFromStockPile(move.accessibleCardsInStockPile.getHead(), "4");
        viewGame(g);

    }
    private static void viewGameInitial(Game g){
        g.viewAcePiles();
        g.viewTableau();
        g.viewStockPile();
        g.viewAccessibleCardsInStockPile();
    }

    private static void viewGame(Game g){
        System.out.println("\n\n\n");
        g.viewAcePiles();
        g.viewTableau();
        g.viewStockPile();
        //System.out.println(g.rules.accessibleCardsInStockPile);
        g.viewAccessibleCardsInStockPile();
    }
}
