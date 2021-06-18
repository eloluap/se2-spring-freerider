package de.freerider.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;

import de.freerider.model.Customer;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class CustomerRepositoryTest {

    @Autowired
    CrudRepository<Customer, String> customerRepository;

    private Customer mats;
    private Customer thomas;

    @BeforeEach
    public void setUpEach() {
        mats = new Customer();
        thomas = new Customer();
        customerRepository.deleteAll();
    }

    // Konstruktor-Tests
    @Test
    @Order(11)
    void testConstructor() {
        assertEquals(0, customerRepository.count());
    }

    // NormalSave
    void normalSave() {
        mats.setId("1");
        thomas.setId("2");
        customerRepository.save(mats);
        customerRepository.save(thomas);
    }

    // save()-Tests
    @Test
    @Order(21)
    void testSaveNormal() {
        mats.setId("1");
        thomas.setId("2");
        customerRepository.save(mats);
        customerRepository.save(thomas);
        assertEquals(2, customerRepository.count());
        assertEquals(mats, customerRepository.findById("1").get());
        assertEquals(thomas, customerRepository.findById("2").get());
    }

    @Test
    @Order(22)
    void testSaveNullId() {
        Customer savedMats = customerRepository.save(mats);
        assertNotNull(customerRepository.findById(savedMats.getId()));
        assertNotEquals("", customerRepository.findById(savedMats.getId()));
    }

    @Test
    @Order(23)
    void testSaveNotNullId() {
        mats.setId("1");
        customerRepository.save(mats);
        assertEquals(1, customerRepository.count());
        assertEquals(mats.getId(), customerRepository.findById("1").get().getId());
    }

    @Test
    @Order(24)
    void testSaveNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            customerRepository.save(null);
        });
        assertEquals(0, customerRepository.count());
    }

    @Test
    @Order(25)
    void testSaveTwoTimes() {
        customerRepository.save(mats);
        customerRepository.save(mats);
        assertEquals(1, customerRepository.count());
    }

    @Test
    @Order(26)
    void testSaveSameId() {
        mats.setId("1");
        thomas.setId("1");
        customerRepository.save(mats);
        customerRepository.save(thomas);
        assertEquals(1, customerRepository.count());
        assertEquals(thomas, customerRepository.findById("1").get());
    }

    // saveAll()-Tests
    @Test
    @Order(31)
    void testSaveAllNormal() {
        ArrayList<Customer> entities = new ArrayList<Customer>();
        mats.setId("1");
        thomas.setId("2");
        entities.add(mats);
        entities.add(thomas);
        customerRepository.saveAll(entities);
        assertEquals(2, customerRepository.count());
        assertEquals(mats, customerRepository.findById("1").get());
        assertEquals(thomas, customerRepository.findById("2").get());
    }

    @Test
    @Order(32)
    void testSaveAllNullId() {
        ArrayList<Customer> entities = new ArrayList<Customer>();
        entities.add(mats);
        entities.add(thomas);
        customerRepository.saveAll(entities);
        assertEquals(2, customerRepository.count());
        for (Customer c : customerRepository.findAll()) {
            assertNotNull(c.getId());
        }
    }

    @Test
    @Order(33)
    void testSaveAllNotNullId() {
        ArrayList<Customer> entities = new ArrayList<Customer>();
        mats.setId("1");
        thomas.setId("2");
        entities.add(mats);
        entities.add(thomas);
        customerRepository.saveAll(entities);
        assertEquals(2, customerRepository.count());
        assertEquals(mats.getId(), customerRepository.findById("1").get().getId());
        assertEquals(thomas.getId(), customerRepository.findById("2").get().getId());
    }

    @Test
    @Order(34)
    void testSaveAllNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            customerRepository.saveAll(null);
        });
        assertEquals(0, customerRepository.count());
    }

    @Test
    @Order(35)
    void testSaveAllNullInList() {
        ArrayList<Customer> entities = new ArrayList<Customer>();
        mats.setId("1");
        thomas.setId("2");
        entities.add(mats);
        entities.add(null);
        entities.add(thomas);
        customerRepository.saveAll(entities);
        assertEquals(2, customerRepository.count());
        assertEquals(mats.getId(), customerRepository.findById("1").get().getId());
        assertEquals(thomas.getId(), customerRepository.findById("2").get().getId());
    }

    @Test
    @Order(36)
    void testSaveAllTwoTimes() {
        ArrayList<Customer> entities = new ArrayList<Customer>();
        mats.setId("1");
        entities.add(mats);
        entities.add(mats);
        customerRepository.saveAll(entities);
        assertEquals(1, customerRepository.count());
    }

    @Test
    @Order(37)
    void testSaveAllSameId() {
        ArrayList<Customer> entities = new ArrayList<Customer>();
        mats.setId("1");
        thomas.setId("1");
        entities.add(mats);
        entities.add(thomas);
        customerRepository.saveAll(entities);
        assertEquals(1, customerRepository.count());
        assertEquals(mats, customerRepository.findById("1").get());
    }

    // findById()-Tests
    @Test
    @Order(41)
    void testFindByIdNormal() {
        normalSave();
        assertEquals(mats, customerRepository.findById(mats.getId()).get());
        assertEquals(thomas, customerRepository.findById(thomas.getId()).get());
    }

    @Test
    @Order(42)
    void testFindByIdMissingId() {
        normalSave();
        assertTrue(customerRepository.findById("3").isEmpty());
    }

    @Test
    @Order(43)
    void testFindByIdNull() {
        normalSave();
        assertThrows(IllegalArgumentException.class, () -> {
            customerRepository.findById(null);
        });
    }

    // existsById()-Tests
    @Test
    @Order(44)
    void testExistsByIdNormal() {
        normalSave();
        assertTrue(customerRepository.existsById(mats.getId()));
        assertTrue(customerRepository.existsById(thomas.getId()));
    }

    @Test
    @Order(45)
    void testExistsByIdMissingId() {
        normalSave();
        assertFalse(customerRepository.existsById("3"));
    }

    @Test
    @Order(46)
    void testExistsByIdNull() {
        normalSave();
        assertThrows(IllegalArgumentException.class, () -> {
            customerRepository.existsById(null);
        });
    }

    // findAll()-Tests
    @Test
    @Order(51)
    void testFindAllNormal() {
        normalSave();
        int counter = 0;
        for (Customer c : customerRepository.findAll()) {
            counter++;
        }
        assertEquals(counter, customerRepository.count());
    }

    @Test
    @Order(52)
    void testFindAllEmpty() {
        int counter = 0;
        for (Customer c : customerRepository.findAll()) {
            counter++;
        }
        assertEquals(counter, customerRepository.count());
    }

    // findAllById()-Tests
    @Test
    @Order(53)
    void testFindAllByIdNormal() {
        normalSave();
        ArrayList<String> ids = new ArrayList<String>();
        ids.add(mats.getId());
        ids.add(thomas.getId());
        int counter = 0;
        for (Customer c : customerRepository.findAllById(ids)) {
            counter++;
        }
        assertEquals(counter, customerRepository.count());
    }

    @Test
    @Order(54)
    void testFindAllByIdMissing() {
        normalSave();
        ArrayList<String> ids = new ArrayList<String>();
        ids.add(mats.getId());
        ids.add("3");
        ids.add("5");
        int counter = 0;
        for (Customer c : customerRepository.findAllById(ids)) {
            counter++;
            assertEquals(c, mats);
        }
        assertEquals(1, counter);
    }

    @Test
    @Order(55)
    void testFindAllByIdNull() {
        normalSave();
        assertThrows(IllegalArgumentException.class, () -> {
            customerRepository.findAllById(null);
        });
    }

    @Test
    @Order(56)
    void testFindAllByIdNullInList() {
        normalSave();
        ArrayList<String> ids = new ArrayList<String>();
        ids.add(mats.getId());
        ids.add(null);
        ids.add(thomas.getId());
        int counter = 0;
        for (Customer c : customerRepository.findAllById(ids)) {
            counter++;
        }
        assertEquals(2, counter);
    }

    // count()-Tests
    @Test
    @Order(61)
    void testCountNormal() {
        normalSave();
        assertEquals(2, customerRepository.count());
    }

    @Test
    @Order(62)
    void testCountEmpty() {
        assertEquals(0, customerRepository.count());
    }

    // deleteById()-Tests
    @Test
    @Order(71)
    void testDeleteByIdNormal() {
        normalSave();
        customerRepository.deleteById(mats.getId());
        assertEquals(1, customerRepository.count());
        assertFalse(customerRepository.existsById(mats.getId()));
    }

    @Test
    @Order(72)
    void testDeleteByIdMissing() {
        normalSave();
        customerRepository.deleteById("3");
        assertEquals(2, customerRepository.count());
    }

    @Test
    @Order(73)
    void testDeleteByIdNull() {
        normalSave();
        assertThrows(IllegalArgumentException.class, () -> {
            customerRepository.deleteById(null);
        });
        assertEquals(2, customerRepository.count());
    }

    // delete()-Tests
    @Test
    @Order(81)
    void testDeleteNormal() {
        normalSave();
        customerRepository.delete(mats);
        assertEquals(1, customerRepository.count());
        assertFalse(customerRepository.existsById(mats.getId()));
    }

    @Test
    @Order(82)
    void testDeleteMissing() {
        normalSave();
        Customer cust = new Customer();
        cust.setId("3");
        customerRepository.delete(cust);
        assertEquals(2, customerRepository.count());
    }

    @Test
    @Order(83)
    void testDeleteNull() {
        normalSave();
        assertThrows(IllegalArgumentException.class, () -> {
            customerRepository.delete(null);
        });
        assertEquals(2, customerRepository.count());
    }

    // deleteAllById()-Tests
    @Test
    @Order(91)
    void testDeleteAllByIdNormal() {
        normalSave();
        ArrayList<String> ids = new ArrayList<String>();
        ids.add(mats.getId());
        ids.add(thomas.getId());
        customerRepository.deleteAllById(ids);
        assertEquals(0, customerRepository.count());
    }

    @Test
    @Order(92)
    void testDeleteAllByIdMissing() {
        normalSave();
        Customer cust = new Customer();
        cust.setId("3");
        ArrayList<String> ids = new ArrayList<String>();
        ids.add(mats.getId());
        ids.add(cust.getId());
        ids.add(thomas.getId());
        customerRepository.deleteAllById(ids);
        assertEquals(0, customerRepository.count());
    }

    @Test
    @Order(93)
    void testDeleteAllByIdNull() {
        normalSave();
        assertThrows(IllegalArgumentException.class, () -> {
            customerRepository.deleteAllById(null);
        });
        assertEquals(2, customerRepository.count());
    }

    @Test
    @Order(94)
    void testDeleteAllByIdNullInList() {
        normalSave();
        ArrayList<String> ids = new ArrayList<String>();
        ids.add(mats.getId());
        ids.add(null);
        ids.add(thomas.getId());
        customerRepository.deleteAllById(ids);
        assertEquals(0, customerRepository.count());
    }

    // deleteAll(entities)-Tests
    @Test
    @Order(101)
    void testDeleteAllEntitiesNormal() {
        normalSave();
        ArrayList<Customer> entities = new ArrayList<Customer>();
        entities.add(mats);
        entities.add(thomas);
        customerRepository.deleteAll(entities);
        assertEquals(0, customerRepository.count());
    }

    @Test
    @Order(102)
    void testDeleteAllEntitiesMissing() {
        normalSave();
        Customer cust = new Customer();
        cust.setId("3");
        ArrayList<Customer> entities = new ArrayList<Customer>();
        entities.add(mats);
        entities.add(cust);
        entities.add(thomas);
        customerRepository.deleteAll(entities);
        assertEquals(0, customerRepository.count());
    }

    @Test
    @Order(103)
    void testDeleteAllEntitiesNull() {
        normalSave();
        assertThrows(IllegalArgumentException.class, () -> {
            customerRepository.deleteAll(null);
        });
        assertEquals(2, customerRepository.count());
    }

    @Test
    @Order(104)
    void testDeleteAllEntitiesNullInList() {
        normalSave();
        ArrayList<Customer> entities = new ArrayList<Customer>();
        entities.add(mats);
        entities.add(null);
        entities.add(thomas);
        customerRepository.deleteAll(entities);
        assertEquals(0, customerRepository.count());
    }

    // deleteAll()-Tests
    @Test
    @Order(111)
    void testDeleteAllNormal() {
        normalSave();
        customerRepository.deleteAll();
        assertEquals(0, customerRepository.count());
    }

    @Test
    @Order(112)
    void testDeleteAllEmpty() {
        customerRepository.deleteAll();
        assertEquals(0, customerRepository.count());
    }

}
