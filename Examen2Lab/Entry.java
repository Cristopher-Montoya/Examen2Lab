package Examen2Lab;
/**
 *
 * @author crist
 */
public class Entry {
private String username;
private long pos;
private Entry siguiente;

public Entry(String username, long position) {
    this.username=username;
    this.pos=position;
    this.siguiente=null;
}
 public String getUser() {
        return username;
    }
    public void setUser(String username) {
        this.username=username;
    }
    public long getPos() {
        return pos;
    }
    public void setPos(long posicion) {
        pos = posicion;
    }
    public Entry getNext() {
        return siguiente;
    }
    public void setNext(Entry next) {
        siguiente = next;
}
}
