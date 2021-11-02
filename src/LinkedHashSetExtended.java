import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;

public class LinkedHashSetExtended<T> extends LinkedHashSet {
    private T last = null;
    private HashMap<T, ArrayList<T>> previousNextMap = new HashMap<>();

    public T removeExtended(){
        T obj = (T) this.stream().findFirst().get();
        T next = getNext((T) obj);
        if (next != null) {
            previousNextMap.get(next).set(0, null);
        }
        previousNextMap.remove(obj);
        this.remove(obj);
        return obj;
    }

    public boolean removeExtended(Object o){
        T previous = getPrevious((T) o);
        T next = getNext((T) o);
        if (previous != null) {
            previousNextMap.get(previous).set(1, next);
        }
        if (next != null) {
            previousNextMap.get(next).set(0, previous);
        }
        previousNextMap.remove(o);
        return this.remove(o);
    }

    public T getFirst(){
        T obj = (T) this.stream().findFirst().get();
        return obj;
    }

    public T getLast(){
        return last;
    }

    public T getNext(T obj) {
        return previousNextMap.get(obj).get(1);
    }

    public T getPrevious(T obj) {
        return previousNextMap.get(obj).get(0);
    }

    public void addExtended(T obj){
        if (previousNextMap.isEmpty()) {
            this.add(obj);
            previousNextMap.put(obj, new ArrayList<>(Arrays.asList(null, null)));
            last = obj;
        }
        else {
            this.add(obj);
            previousNextMap.get(last).add(1, obj);
            previousNextMap.put(obj, new ArrayList<>(Arrays.asList(last, null)));
            last = obj;
        }
    }
}
