package com.realdolmen.course.beans;

import com.realdolmen.course.domain.Address;
import com.realdolmen.course.domain.Person;
import com.realdolmen.course.service.PersonServiceBean;

import javax.faces.flow.FlowScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;

/**
 * Created by cda5732 on 17/05/2016.
 */
@Named
@FlowScoped("registration") // This is a flow's backing bean
public class RegistrationFlowBean implements Serializable {
    private Person person;

    @Inject
    PersonServiceBean personServiceBean;

    public void prepare() {
        person = new Person();
        person.setAddress(new Address());
    }

    public Person getPerson() {
        return person;
    }

    public void save() {
       personServiceBean.save(person);
    }
}