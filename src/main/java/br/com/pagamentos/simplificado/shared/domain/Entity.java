package br.com.pagamentos.simplificado.shared.domain;

import br.com.pagamentos.simplificado.shared.domain.exceptions.NotificationException;
import br.com.pagamentos.simplificado.shared.domain.validation.Notification;

import java.util.Objects;

public class Entity<ID extends Uuid>{

    protected final ID id;
    protected Notification notification = new Notification();


    protected Entity(final ID id) {
        Objects.requireNonNull(id, "'id' should not be null");
        this.id = id;
    }

    public ID getId() {
        return id;
    }

    protected void validate() {
        if (notification.hasErrors()) {
            throw new NotificationException(notification.getErrors());
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Entity<?> entity = (Entity<?>) o;
        return getId().equals(entity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
