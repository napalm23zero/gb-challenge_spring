package com.gb.challenge.service;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

public interface GenericService<T, ID> {
    void saveOrUpdate(T entity);

    List<T> getAll();

    Page<T> getAllPaginated(int page, int count, Sort.Direction direction, String sortProperty);

    T get(ID id);

    T add(T entity);

    T update(T entity);

    T addOrUpdate(T entity);

    void updateEntities(T entitys);

    void remove(T entity);

    void removeById(ID id);

    void removeEntities(T entitys);

    void removeEntitiesById(List<ID> entityIds);

    void removeEntitiesList(Set<T> entities);

}