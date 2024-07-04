package br.com.pagamentos.simplificado.shared.application;

import java.util.List;

public abstract class ListUseCase<T> {
    public abstract List<T> execute();
}
