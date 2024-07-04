package br.com.pagamentos.simplificado.shared.domain;

import java.util.List;

public interface BaseRepository<T> {
    T save(T entity);
    List<T> list();
}
