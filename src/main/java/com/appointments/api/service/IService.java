package com.appointments.api.service;

import java.util.List;
import java.util.Optional;

public interface IService<T, U> {

    public T save(T model);

    public Iterable<T> saveAll(Iterable<T> models);

    List<T> findAll();

    Optional<T> findById(U id);

    void deleteAll();

    void delete(U id);
}
