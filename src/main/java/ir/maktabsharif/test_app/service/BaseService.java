package ir.maktabsharif.test_app.service;


import java.util.List;

public interface BaseService<T, ID> {
    T save(T entity);
    T update(T entity);
    void deleteById(ID id);
    T findById(ID id);
    List<T> findAll();
}
