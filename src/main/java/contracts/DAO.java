package contracts;

import java.util.List;

public interface DAO<T> {
    List<T> getAll();
    T get(int index);
    void add(T element);
    void remove(int index);
}
