package br.com.pagamentos.simplificado.shared.domain;

import java.util.List;

public interface BaseRepository<T> {
    T create(T entity);
    List<T> list();
}
