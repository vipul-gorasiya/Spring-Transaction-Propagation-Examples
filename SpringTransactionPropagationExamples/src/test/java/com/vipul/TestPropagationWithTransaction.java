package com.vipul;

import com.vipul.entity.Person;
import com.vipul.service.PersonService;
import com.vipul.util.Runner;
import com.vipul.util.repository.PersonRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.IllegalTransactionStateException;
import org.springframework.transaction.NestedTransactionNotSupportedException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TestPropagationWithTransaction {

    @Autowired
    private PersonService personService;

    @Autowired
    private PersonRepository repository;

    @Autowired
    private Runner runner;

    @AfterEach
    public void reset() {
        repository.deleteAll();
    }

    @Test
    public void testRequiresNew() {
        assertDoesNotThrow(() -> runner.runWithTransaction(personService::requiresNew),
                "Should not throw exception (creates new transaction)");
        assertEquals(1, repository.count());
    }

    @Test
    public void testRequired() {
        assertDoesNotThrow(() -> runner.runWithTransaction(personService::required),
                "Should not throw exception (has existing transaction)");
        assertEquals(1, repository.count());
    }

    @Test
    public void testMandatory() {
        assertDoesNotThrow(() -> runner.runWithTransaction(personService::mandatory),
                "Should not throw exception (has existing transaction)");
        assertEquals(1, repository.count());
    }

    @Test
    public void testNested() {
        // document: Marks a save point here and execute nested transaction using the current transaction
        assertThrows(NestedTransactionNotSupportedException.class,
                () -> runner.runWithTransaction(personService::nested),
                "JpaDialect does not support savepoints");
        assertEquals(0, repository.count());
    }

    @Test
    public void testNever() {
        assertThrows(IllegalTransactionStateException.class,
                () -> runner.runWithTransaction(personService::never),
                "NEVER propagation mode should throw exception " +
                        "when there is an existing transaction");
    }

    @Test
    public void testNotSupport() {
        // document: Suspend current transaction and execute the inner business logic
        assertDoesNotThrow(() -> runner.runWithTransaction(personService::notSupported),
                "Business logic should execute without transaction");
    }

    @Test
    public void testSupports() {
        assertDoesNotThrow(() -> runner.runWithTransaction(personService::supports),
                "Should not throw exception (support existing transaction)");
        assertEquals(1, repository.count());
    }

    @Test
    public void testTransactionalNeverCall() {
        assertDoesNotThrow(() -> runner.runWithTransaction(personService::transactionalNeverCall),
                "Should not throw exception since it use the propagation " +
                        "attribute of the first @Transactional method call in one bean (so in fact it is REQUIRED" +
                        "no matter whatever personService methods are called)");
    }
}
