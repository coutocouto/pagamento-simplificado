package br.com.pagamentos.simplificado.shared.application;

public abstract class UseCase<IN, OUT> {

    public abstract OUT execute(IN in);
}
