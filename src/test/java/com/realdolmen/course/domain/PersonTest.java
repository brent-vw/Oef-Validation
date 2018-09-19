package com.realdolmen.course.domain;

import com.realdolmen.course.utils.DateUtils;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

// TODO Update this test for the extra email field

public class PersonTest {
    private Validator validator;

    @Before
    public void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void nameReturnsConcatenationOfFirstNameAndLastName() throws Exception {
        assertEquals("George Orwell", new Person("George", "Orwell", DateUtils.createDate("1970-01-01")).name());
    }

    @Test
    public void personIsInstantiatedWithNullId() throws Exception {
        assertNull("Person ID is supposed to be null before saving", new Person("Paul", "McCartney", DateUtils.createDate("1970-01-01")).getId());
    }

    @Test
    public void personWithInvalidEmailShouldNotBeValid() throws Exception {
        Person p = getGenericPerson();
        p.setEmail("Brent@brentvw.me");

        Set<ConstraintViolation<Person>> violations = validator.validate(p);
        assertEquals(1, violations.size());
    }

    @Test
    public void personWithValidEmailShouldBeValid() throws Exception {
        Person p = getGenericPerson();
        p.setEmail("Brent.VanWynsberge@realdolmen.com");

        Set<ConstraintViolation<Person>> violations = validator.validate(p);
        assertEquals(0, violations.size());
    }

    @Test
    public void personWithEmailInGroupShouldNotBeValid() throws Exception {
        Person p = getGenericPerson();
        p.setEmail("Brent@brentvw.me");

        Set<ConstraintViolation<Person>> violations = validator.validate(p, EmailGroup.class);
        assertEquals(1, violations.size());

        Set<ConstraintViolation<Person>> noViolations = validator.validate(p, NotRelatedGroup.class);
        assertEquals(0, noViolations.size());
    }

    private Person getGenericPerson() {
        Person p = new Person("Brent", "Van Wynsberge", DateUtils.createDate("1994-09-20"));
        Address address = new Address("Bremstraat", "5", "9940", "Evergem");
        p.setAddress(address);
        return p;
    }
}
