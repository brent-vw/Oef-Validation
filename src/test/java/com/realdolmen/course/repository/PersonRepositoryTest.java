package com.realdolmen.course.repository;

import com.realdolmen.course.AbstractPersistenceTest;
import com.realdolmen.course.domain.Address;
import com.realdolmen.course.domain.Person;
import com.realdolmen.course.utils.DateUtils;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PersonRepositoryTest extends AbstractPersistenceTest {
    private static final long TEST_PERSON_ID = 1;
    private PersonRepository personRepository;

    @Before
    public void initializeRepository() {
        personRepository = new PersonRepository();
        personRepository.em = em;
    }

    @Test
    public void shouldSaveAPerson() {
        Person p = new Person("Theo", "Tester", DateUtils.createDate("1970-01-01"));
        Address a = new Address("TheStreet", "123", "9876", "TheCity");
        p.setAddress(a);
        p.setEmail("Brent.vanwynsberge@realdolmen.com");
        personRepository.save(p);
        assertNotNull("Person ID is not supposed to be null after saving", p.getId());
    }

    @Test
    public void shouldReturnAllPersons() {
        List<Person> persons = personRepository.findAll();
        assertNotNull(persons);
        assertEquals(5, persons.size());
    }

    @Test
    public void shouldReturnAPerson() {
        Person p = personRepository.findById(TEST_PERSON_ID);
        assertNotNull(p);
        assertNotNull(p.getId());
        assertEquals("John", p.getFirstName());
    }

    @Test
    public void personCanBeRemoved() throws Exception {
        personRepository.remove(TEST_PERSON_ID);
        assertEquals(4, count(Person.class));
    }

}
