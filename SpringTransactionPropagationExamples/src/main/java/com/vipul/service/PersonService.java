package com.vipul.service;

import com.vipul.entity.Person;
import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PersonService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonService.class);

    private final EntityManager entityManager;

    public PersonService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    private void doTransaction() {
        Person person7 = new Person();
        person7.setFirstName("Person8");
        person7.setLastName("Person8");
        person7.setEmail("email8");
        entityManager.persist(person7);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void requiresNew() {
        try {
            doTransaction();
            LOGGER.info("This is from requiresNew()");
        } catch (Exception e) {
            LOGGER.error("Error in requiresNew() : {}", e.getClass());
            throw e;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void required() {
        try {
            doTransaction();
            LOGGER.info("This is from required()");
        } catch (Exception e) {
            LOGGER.error("Error in required() : {}", e.getClass());
            throw e;
        }
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void mandatory() {
        try {
            doTransaction();
            LOGGER.info("This is from mandatory()");
        } catch (Exception e) {
            LOGGER.error("Error in mandatory() : {}", e.getClass());
            throw e;
        }
    }

    @Transactional(propagation = Propagation.NESTED)
    public void nested() {
        try {
            doTransaction();
            LOGGER.info("This is from nested()");
        } catch (Exception e) {
            LOGGER.error("Error in nested() : {}", e.getClass());
            throw e;
        }
    }

    @Transactional(propagation = Propagation.NEVER)
    public void never() {
        LOGGER.info("This is from never()");
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void notSupported() {
        LOGGER.info("This is from notSupported()");
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void supports() {
        try {
            doTransaction();
            LOGGER.info("This is from supports()");
        } catch (Exception e) {
            LOGGER.error("Error in supports() : {}", e.getClass());
            throw e;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void transactionalNeverCall() {
        never();
    }
}
