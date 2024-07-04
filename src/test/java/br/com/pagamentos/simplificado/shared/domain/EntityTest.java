package br.com.pagamentos.simplificado.shared.domain;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class EntityTest {

    @Test
    void equals() {
        Entity<Uuid> entity1 = new Entity<Uuid>(new Uuid() {
            @Override
            public String getValue() {
                return UUID.randomUUID().toString();
            }
        });

        Entity<Uuid> entity2 = new Entity<Uuid>(new Uuid() {
            @Override
            public String getValue() {
                return UUID.randomUUID().toString();
            }
        });

        assertFalse(entity1.equals(entity2));
        assertFalse(entity2.equals(entity1));
    }

    @Test
    void equalsSameEntity() {
        Entity<Uuid> entity1 = new Entity<Uuid>(new Uuid() {
            @Override
            public String getValue() {
                return UUID.randomUUID().toString();
            }
        });

        assertTrue(entity1.equals(entity1));
    }

}