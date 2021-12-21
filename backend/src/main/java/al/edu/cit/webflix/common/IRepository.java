package al.edu.cit.webflix.common;

import java.util.List;

public interface IRepository<T> {
    int count();
    List<T> getAll();
    T get(int id);
    void insert(T newItem);
    void batchInsert(List<T> items);
    void update(T updatedItem);
    void delete(int id);
}
