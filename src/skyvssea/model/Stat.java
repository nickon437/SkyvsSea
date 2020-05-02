package skyvssea.model;

public class Stat<T> implements Cloneable {
    private T value;

    public Stat(T value) { this.value = value; }
    public T getValue() { return value; }
    public void setValue(T value) { this.value = value; }

    public Stat<T> clone() throws CloneNotSupportedException { return (Stat<T>) super.clone(); }
}
