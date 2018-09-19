package com.realdolmen.course.service;

import com.realdolmen.course.AbstractRemoteIntegrationTest;
import com.realdolmen.course.domain.Person;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class PersonServiceRemoteTest extends AbstractRemoteIntegrationTest {
    @Test
    public void testPersonServiceCanBeAccessedRemotely() throws Exception {
        PersonServiceRemote personService = lookup("jee7-bean-validation-starter/PersonServiceBean!com.realdolmen.course.service.PersonServiceRemote");
        List<Person> people = personService.findAll();
        assertEquals(5, people.size());
        for (Person person : people) {
            System.out.println("Retrieved person " + person);
        }
    }
}
