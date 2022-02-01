package com.zakharov.mvc_hw.controller;

import com.zakharov.mvc_hw.model.Person;
import com.zakharov.mvc_hw.model.PersonForm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.ArrayList;
import java.util.List;

@Controller
@EnableWebMvc
public class PersonController {
    private static List<Person> personList = new ArrayList<Person>();

    static {
        personList.add(new Person(1, "Alex", "Zakharov", 20, "Ukraine"));
        personList.add(new Person(2, "Bill", "Gates", 66, "USA"));
        personList.add(new Person(3, "Steve", "Jobs", 56, "USA"));
    }

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String index(Model model) {

        model.addAttribute("message1", "Cursor H/W 15");
        model.addAttribute("message2", "made by:Zakharov Alex");
        return "index";
    }

    @RequestMapping(value = {"/personList"}, method = RequestMethod.GET)
    public String getPeople(Model model) {
        model.addAttribute("persons", personList);
        return "personList";
    }


    @RequestMapping(value = {"/addPerson"}, method = RequestMethod.GET)
    public String showAddPersonPage(Model model) {


        PersonForm personForm = new PersonForm();
        model.addAttribute("personForm", personForm);

        return "addPerson";
    }

    @RequestMapping(value = {"/addPerson"}, method = RequestMethod.POST)
    public String savePerson(Model model, //
                             @ModelAttribute("personForm") PersonForm personForm) {

        long id = personForm.getId();
        String firstName = personForm.getFirstName();
        String lastName = personForm.getLastName();
        int age = personForm.getAge();
        String address = personForm.getAddress();

        if (firstName != null && firstName.length() > 0 //
                && lastName != null && lastName.length() > 0
                && age > 0
                && address != null && address.length() > 0) {
            Person newPerson = new Person(id, firstName, lastName, age, address);
            personList.add(newPerson);

            return "redirect:/personList";
        }
        model.addAttribute("errorMessage", "Error");
        return "addPerson";
    }

    @RequestMapping(value = "deletePerson/{id}", method = RequestMethod.GET)
    public String deletePerson(@PathVariable("id") Long id) {
        personList.remove(personList.stream()
                .filter(p -> p.getId() == id).findAny().orElse(null));
        return "redirect:/personList";
    }

    @RequestMapping(value = {"/updatePerson/{id}"}, method = RequestMethod.GET)
    public String showEditPersonPage(Model model, @PathVariable Long id) {
        Person person = personList.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
        PersonForm personForm = new PersonForm();

        personList.remove(person);

        personForm.setId(person.getId());
        personForm.setFirstName(person.getFirstName());
        personForm.setLastName(person.getLastName());
        personForm.setAge(person.getAge());
        personForm.setAddress(person.getAddress());

        model.addAttribute("personForm", personForm);

        return "updatePerson";
    }

    @RequestMapping(value = "updatePerson")
    public String updatePerson(Model model, @ModelAttribute("personForm") PersonForm personForm) {

        long id = personForm.getId();
        String firstName = personForm.getFirstName();
        String lastName = personForm.getLastName();
        int age = personForm.getAge();
        String address = personForm.getAddress();

        if (firstName != null && firstName.length() > 0 //
                && lastName != null && lastName.length() > 0
                && age > 0
                && address != null && address.length() > 0) {
            Person newPerson = new Person(id, firstName, lastName, age, address);
            personList.add(newPerson);

            return "redirect:/personList";
        }
        model.addAttribute("errorMessage", "Error");
        return "updatePerson";
    }
}
