package br.com.pagamentos.simplificado.shared.infrastructure.repository;

public interface BaseRepository<T> {
    T save(T entity);
    T update(T entity);
}
