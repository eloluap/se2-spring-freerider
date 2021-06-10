package de.freerider.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.springframework.boot.test.context.SpringBootTest;

import de.freerider.model.Customer.Status;

@SpringBootTest
public class CustomerTest {
    private Customer mats;
    private Customer thomas;

    public CustomerTest() {
        mats = new Customer();
        thomas = new Customer();
    }

    // Id-Tests:
    @Test
    void testIdNull() {
        assertNull(thomas.getId());
    }

    @Test
    void testSetId() {
        thomas.setId("TestID");
        assertNotNull(thomas.getId());
    }

    @Test
    void testSetIdOnlyOnce() {
        String ID1 = "TestID1";
        String ID2 = "TestID2";
        thomas.setId(ID1);
        thomas.setId(ID2);
        assertNotEquals(ID2, thomas.getId());
    }

    @Test
    void testResetId() {
        thomas.setId("TestID");
        thomas.setId(null);
        assertNull(thomas.getId());
    }

    // Name-Tests:
    @Test
    void testNamesInitial() {
        assertEquals("", thomas.getFirstName());
        assertEquals("", thomas.getLastName());
    }

    @Test
    void testNamesSetNull() {
        thomas.setFirstName(null);
        thomas.setLastName(null);
        assertEquals("", thomas.getFirstName());
        assertEquals("", thomas.getLastName());
    }

    @Test
    void testSetNames() {
        thomas.setFirstName("Thomas");
        thomas.setLastName("Müller");
        assertEquals("Thomas", thomas.getFirstName());
        assertEquals("Müller", thomas.getLastName());
    }

    // Contact-Tests:
    @Test
    void testContactsInitial() {
        assertEquals("", thomas.getContact());
    }

    @Test
    void testContactsSetNull() {
        thomas.setContact(null);
        assertEquals("", thomas.getContact());
    }

    @Test
    void testSetContact() {
        thomas.setContact("Albrechtstraße 5");
        assertEquals("Albrechtstraße 5", thomas.getContact());
    }

    // Status-Tests:
    @Test
    void testStatusInitial() {
        assertEquals(Status.New, thomas.getStatus());
    }

    @Test
    void testSetStatus() {
        thomas.setStatus(Status.InRegistration);
        assertEquals(Status.InRegistration, thomas.getStatus());
        thomas.setStatus(Status.Active);
        assertEquals(Status.Active, thomas.getStatus());
        thomas.setStatus(Status.Suspended);
        assertEquals(Status.Suspended, thomas.getStatus());
        thomas.setStatus(Status.Deleted);
        assertEquals(Status.Deleted, thomas.getStatus());
        thomas.setStatus(Status.New);
        assertEquals(Status.New, thomas.getStatus());
    }
}
