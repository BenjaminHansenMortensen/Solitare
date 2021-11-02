import java.util.*;
import java.util.function.Consumer;

public class DoublyLinkedHashSet<T> implements Iterable<T>{
    private int size = 0;
    private final int NEXT_POINTER = 1;
    private final int PREVIOUS_POINTER = 0;
    private HashMap<T, ArrayList<T>> nodePointers = new HashMap<>();
    private T tail = null;
    private T head = null;

    public DoublyLinkedHashSet() {
    }

    public boolean insertAfter(T node, T after) throws Exception {
        if (!(nodePointers.containsKey(node) || nodePointers.containsKey(after))) {
            throw new Exception("Illegal Node/Nodes");
        }

        T next = getNext(after);
        nodePointers.get(next).set(PREVIOUS_POINTER, node);
        nodePointers.get(after).set(NEXT_POINTER, node);
        nodePointers.put(node, new ArrayList<T>(Arrays.asList(after, next)));
        size++;
        return true;
    }

    public boolean insertBefore(T node, T before) throws Exception {
        if (!(nodePointers.containsKey(node) || nodePointers.containsKey(before))) {
            throw new Exception("Illegal Node/Nodes");
        }

        T previous = getPrevious(before);
        nodePointers.get(previous).set(NEXT_POINTER, node);
        nodePointers.get(before).set(PREVIOUS_POINTER, node);
        nodePointers.put(node, new ArrayList<T>(Arrays.asList(previous, before)));
        size++;
        return true;
    }

    public boolean swap(T node, T with) throws Exception {
        if (!nodePointers.containsKey(node)) {
            throw new Exception("Illegal Node/Nodes");
        }

        ArrayList<T> nodePointer = nodePointers.get(node);
        nodePointers.put(with, nodePointer);
        updatePointers(node, with);
        nodePointers.remove(node, nodePointer);
        return true;
    }

    public boolean swapNodes(T node1, T node2) throws Exception {
        if (!(nodePointers.containsKey(node1) || nodePointers.containsKey(node2))) {
            throw new Exception("Illegal Node/Nodes");
        }

        ArrayList<T> node1Pointers = nodePointers.get(node1);
        ArrayList<T> node2Pointers = nodePointers.get(node2);
        ArrayList<T> newNode1Pointers = new ArrayList<>(node1Pointers);
        ArrayList<T> newNode2Pointers = new ArrayList<>(node2Pointers);

        updatePointers(node1, node2);
        updatePointers(node2, node1);

        nodePointers.remove(node1, node1Pointers);
        nodePointers.remove(node2, node2Pointers);
        nodePointers.put(node1, newNode2Pointers);
        nodePointers.put(node2, newNode1Pointers);

        return true;
    }

    private void updatePointers(T node, T with) {
        ArrayList<T> previous = nodePointers.get(getPrevious(node));
        ArrayList<T> next = nodePointers.get(getNext(node));
        if (previous != (null)) {
            previous.set(NEXT_POINTER, with);
        }
        else {
            head = with;
        }
        if (next != (null)) {
            next.set(PREVIOUS_POINTER, with);
        }
        else {
            tail = with;
        }
    }

    public boolean removeTail(){
        return remove(tail);
    }

    public boolean removeHead(){
        return remove(head);
    }

    public T remove() {
        T oldHead = getHead();
        removeHead();
        return oldHead;
    }

    public boolean remove(T node) {
        if (!nodePointers.containsKey(node)) {
            return true;
        }

        T nodePrevious = getPrevious(node);
        T nodeNext = getNext(node);

        if (nodePrevious != null) {
            nodePointers.get(nodePrevious).set(NEXT_POINTER, nodeNext);
        } else {
            head = nodeNext;
        }

        if (nodeNext != null) {
            nodePointers.get(nodeNext).set(PREVIOUS_POINTER, nodePrevious);
        } else {
            tail = nodePrevious;
        }

        size--;
        return nodePointers.remove(node, nodePointers.get(node));
    }

    public void clear() {
        nodePointers.clear();
    }

    public boolean addLast(T node) throws Exception {
        if (size < 1) {
            nodePointers.put(node, new ArrayList<>(Arrays.asList(null, null)));
            head = node;
        }
        else {
            nodePointers.get(tail).set(NEXT_POINTER, node);
            nodePointers.put(node, new ArrayList<>(Arrays.asList(tail, null)));
        }
        tail = node;
        size++;
        return true;
    }

    public boolean addFirst(T node) throws Exception {
        if (size < 1) {
            nodePointers.put(node, new ArrayList<>(Arrays.asList(null, null)));
            tail = node;
        }
        else {
            nodePointers.get(head).set(PREVIOUS_POINTER, node);
            nodePointers.put(node, new ArrayList<>(Arrays.asList(null, head)));
        }
        head = node;
        size++;
        return true;
    }

    public T getNext(T node){
        if (!nodePointers.containsKey(node)) {
            return null;
        }

        return nodePointers.get(node).get(NEXT_POINTER);
    }

    public T getPrevious(T node){
        if (!nodePointers.containsKey(node)) {
            return null;
        }

        return nodePointers.get(node).get(PREVIOUS_POINTER);
    }

    public T getHead(){
        return head;
    }

    public T getTail(){
        return tail;
    }

    public List<T> getNodes() {
        List<T> ordered = new ArrayList<T>(size);

        for (Iterator<T> iter = iterator(); iter.hasNext();) {
            ordered.add(iter.next());
        }
        return ordered;
    }

    public boolean isEmpty(){
        if (size == 0) {
            return true;
        }

        return false;
    }

    public int size() {
        return size;
    }

    @Override
    public String toString() {
        return nodePointers.toString();
    }

    private class ListIterator implements Iterator{
        T pointer = head;

        @Override
        public void remove() {
            removeTail();
        }

        @Override
        public boolean hasNext() {
            return pointer != null;
        }

        @Override
        public T next() {
            T current = pointer;
            pointer = getNext(pointer);
            return current;
        }
    }

    private class ReverseListIterator implements Iterator{
        T pointer = tail;

        @Override
        public void remove() {
            removeTail();
        }

        @Override
        public boolean hasNext() {
            return pointer != null;
        }

        @Override
        public T next() {
            T current = pointer;
            pointer = getPrevious(pointer);
            return current;
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new ListIterator();
    }

    public Iterator<T> reverseIterator() { return new ReverseListIterator(); }

    @Override
    public void forEach(Consumer action) {
    }

    @Override
    public Spliterator spliterator() {
        return null;
    }
}
