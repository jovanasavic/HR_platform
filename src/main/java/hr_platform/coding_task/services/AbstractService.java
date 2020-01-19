package hr_platform.coding_task.services;


import java.util.List;

public interface AbstractService<T, K> {

    List<T> findAll();

    T create(T t);

}
