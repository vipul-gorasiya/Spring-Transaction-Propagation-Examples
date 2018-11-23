package com.vipul.service;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.vipul.dao.PersonDAO;
import com.vipul.entity.Person;

@Service
public class PersonService implements ApplicationContextAware {

	@Autowired
	private PersonDAO personDAO;

	private static final Logger LOGGER = LoggerFactory.getLogger(PersonService.class);

	@Transactional(propagation = Propagation.NEVER)
	public void callTransactionalMethods() {
		Person person1 = new Person();
		person1.setFirstName("Person1");
		person1.setLastName("Person1");
		person1.setEmail("email1");
		personDAO.save(person1);
		required();
		requiresNew();
		nested();
		never();
		supports();
		notSupported();
		nested();
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void requiresNew() {
		Person person2 = new Person();
		person2.setFirstName("Person2");
		person2.setLastName("Person2");
		person2.setEmail("email2");
		personDAO.save(person2);
		LOGGER.info("This is from requiresNew()");
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void required() {
		Person person3 = new Person();
		person3.setFirstName("Person3");
		person3.setLastName("Person3");
		person3.setEmail("email3");
		personDAO.save(person3);
		LOGGER.info("This is from required()");
	}

	@Transactional(propagation = Propagation.MANDATORY)
	public void mandatory() {
		Person person4 = new Person();
		person4.setFirstName("Person4");
		person4.setLastName("Person4");
		person4.setEmail("email4");
		personDAO.save(person4);
		LOGGER.info("This is from mandatory()");
	}

	@Transactional(propagation = Propagation.NESTED)
	public void nested() {
		Person person5 = new Person();
		person5.setFirstName("Person5");
		person5.setLastName("Person5");
		person5.setEmail("email5");
		personDAO.save(person5);
		LOGGER.info("This is from nested()");
	}

	@Transactional(propagation = Propagation.NEVER)
	public void never() {
		try {
			Person person6 = new Person();
			person6.setFirstName("Person6");
			person6.setLastName("Person6");
			person6.setEmail("email6");
			personDAO.save(person6);
		} catch (Exception e) {
			LOGGER.error("Error in never() :", e);
		}
		LOGGER.info("This is from never()");
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public void notSupported() {
		try {
			EntityManager em = this.applicationContext.getBean(EntityManager.class);
			Person person7 = new Person();
			person7.setFirstName("Person7");
			person7.setLastName("Person7");
			person7.setEmail("email7");
			personDAO.save(person7);
		} catch (Exception e) {
			LOGGER.error("Error in notSupported() :", e);
		}
		LOGGER.info("This is from notSupported()");
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public void supports() {
		Person person7 = new Person();
		person7.setFirstName("Person8");
		person7.setLastName("Person8");
		person7.setEmail("email8");
		personDAO.save(person7);
		LOGGER.info("This is from supports()");
	}

	private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

}
