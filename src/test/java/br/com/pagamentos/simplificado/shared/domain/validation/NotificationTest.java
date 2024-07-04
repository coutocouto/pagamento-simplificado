package br.com.pagamentos.simplificado.shared.domain.validation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotificationTest {

    @Test
    void testAddError() {
        Notification notification = new Notification();
        notification.addError("error", "field");
        assertTrue(notification.hasErrors());
    }

    @Test
    void testHasErrors() {
        Notification notification = new Notification();
        assertFalse(notification.hasErrors());
    }

    @Test
    void testGetErrors() {
        Notification notification = new Notification();
        notification.addError("error", "field");
        assertFalse(notification.getErrors().isEmpty());
    }

    @Test
    void testCopyErrors() {
        Notification notification = new Notification();
        Notification notification2 = new Notification();
        notification.addError("error", "field");
        notification2.copyErrors(notification);
        assertEquals(notification.getErrors(), notification2.getErrors());
    }

    @Test
    void testToJSON() {
        Notification notification = new Notification();
        notification.addError("error", "field");
        assertEquals("{field=[error]}", notification.toJSON());
    }

}