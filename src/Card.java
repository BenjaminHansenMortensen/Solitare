import java.util.HashMap;

public class Card {
    final int NUMERIC_VALUES = 13;
    public String value;
    public int numericValue;
    public String suit;
    public boolean redColor;
    public boolean faceUp = false;
    private HashMap<String, Integer> numericValueMap = new HashMap<>(NUMERIC_VALUES);
    public String location = "stock";

    public Card(String value, String suit){
        this.value = value;
        this.suit = suit;
        createNumericValueMap();
        setNumericValue(value);

        if (suit == "D" || suit == "H") {
            this.redColor = true;
        }
        else {
            this.redColor = false;
        }
    }

    private void setNumericValue(String value){
        this.numericValue = numericValueMap.get(value);
    }

    public void setFaceUp(){
        faceUp = true;
    }

    public void setLocation(String placement){location = placement;}

    private void createNumericValueMap(){
        numericValueMap.put("A", 1);
        numericValueMap.put("2", 2);
        numericValueMap.put("3", 3);
        numericValueMap.put("4", 4);
        numericValueMap.put("5", 5);
        numericValueMap.put("6", 6);
        numericValueMap.put("7", 7);
        numericValueMap.put("8", 8);
        numericValueMap.put("9", 9);
        numericValueMap.put("10", 10);
        numericValueMap.put("J", 11);
        numericValueMap.put("Q", 12);
        numericValueMap.put("K", 13);

    }

    public String toString(){
        if (faceUp) {
            return (value + suit);
        }
        return ("?");
        //return value + suit;
    }
}
