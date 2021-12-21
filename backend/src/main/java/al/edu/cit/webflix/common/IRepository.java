package al.edu.cit.webflix.common;

import java.util.List;

public interface IRepository<T> {
    List<T> getAll();
    T get(int id);
    void add(T item);
    void update(T person);
    void delete(int id);
}
