package com.appointments.api.service;

import java.util.List;

public interface IService<T, U> {

    public T save(T model);

    public Iterable<T> saveAll(Iterable<T> models);

    List<T> findAll();

    void deleteAll();
}
