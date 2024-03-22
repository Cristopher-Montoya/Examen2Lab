package Examen2Lab;
/**
 *
 * @author crist
 */public class HashTable {
    private Entry ent;
    public void add(String username, long pos) {
        Entry newEntry = new Entry(username, pos);
        if (ent == null) {
            ent= newEntry;
        } else {
            Entry current = ent;
            while (current.getNext() != null) {
                current = current.getNext();
            }
            current.setNext(newEntry);
        }
    }
    public void remove(String username) {
        if (ent == null) {
            return;
        }
        if (ent.getUser().equals(username)) {
            ent = ent.getNext();
            return;
        }
        Entry current = ent;
        Entry previous = null;
        while (current != null && !current.getUser().equals(username)) {
            previous = current;
            current = current.getNext();
        }
        if (current != null) {
            previous.setNext(current.getNext());
        }
    }
    public long search(String username) {
        Entry current =ent;
        while (current != null) {
            if (current.getUser().equals(username)) {
                return current.getPos();
            }
            current = current.getNext();
        }
        return -1;
    }
}