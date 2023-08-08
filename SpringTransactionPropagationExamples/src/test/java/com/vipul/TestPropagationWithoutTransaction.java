package com.vipul;

import com.vipul.entity.Person;
import com.vipul.service.PersonService;
import com.vipul.util.Runner;
import com.vipul.util.repository.PersonRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TransactionRequiredException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.IllegalTransactionStateException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TestPropagationWithoutTransaction {

    @Autowired
    private PersonService personService;

    @Autowired
    private PersonRepository repository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private Runner runner;

    @AfterEach
    public void reset() {
        repository.deleteAll();
    }

    @Test
    public void testPersistWithJpaRepository() {
        Person person = new Person();
        person.setFirstName("Person");
        person.setLastName("Person");
        person.setEmail("email");

        assertDoesNotThrow(() -> repository.save(person),
                "Should not throw exception since it has " +
                        "its own transaction (REQUIRED)");
        assertEquals(1, repository.count());
    }

    @Test
    public void testPersistWithEntityManager() {
        Person person = new Person();
        person.setFirstName("Person");
        person.setLastName("Person");
        person.setEmail("email");

        assertThrows(TransactionRequiredException.class,
                () -> entityManager.persist(person),
                "Should throw exception when persisting data without transaction");
    }

    @Test
    public void testRequiresNew() {
        assertDoesNotThrow(() -> runner.runWithoutTransaction(personService::requiresNew),
                "Should not throw exception (creates new transaction)");
        assertEquals(1, repository.count());
    }

    @Test
    public void testRequired() {
        assertDoesNotThrow(() -> runner.runWithoutTransaction(personService::required),
                "Should not throw exception (creates new transaction)");
        assertEquals(1, repository.count());
    }

    @Test
    public void testMandatory() {
        assertThrows(IllegalTransactionStateException.class,
                () -> runner.runWithoutTransaction(personService::mandatory),
                "MANDATORY propagation mode should throw exception when calling " +
                        "without any transaction");
        assertEquals(0, repository.count());
    }

    @Test
    public void testNested() {
        // Works like REQUIRED when there is no transaction
        assertDoesNotThrow(() -> runner.runWithoutTransaction(personService::required),
                "Should not throw exception (creates new transaction)");
        assertEquals(1, repository.count());
    }

    @Test
    public void testNever() {
        assertDoesNotThrow(() -> runner.runWithoutTransaction(personService::never),
                "Should not throw exception when calling in non-" +
                        "transactional mode");
    }

    @Test
    public void testNotSupport() {
        assertDoesNotThrow(() -> runner.runWithoutTransaction(personService::notSupported),
                "Should not throw exception when calling in non-" +
                        "transactional mode");
    }

    @Test
    public void testSupports() {
        assertThrows(TransactionRequiredException.class,
                () -> runner.runWithoutTransaction(personService::supports),
                "SUPPORTS propagation mode should throw exception " +
                        "when persisting data in itself in non transactional mode");
    }
}
