package com.petshop.dao;

import java.util.List;

public interface BaseDAO<T> {
    void insertar(T obj);
    T buscarPorId(int id);
    List<T> buscarTodos();
    void actualizar(T obj);
    void eliminar(int id);
}
