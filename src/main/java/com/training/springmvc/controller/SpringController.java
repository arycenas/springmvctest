package com.training.springmvc.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.training.springmvc.model.Person;

@Controller
public class SpringController {

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name = "name", required = false, defaultValue = "World") String name,
            Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    @GetMapping("/integer")
    @ResponseBody
    public int integer() {
        int sum = 0;
        for (int i = 0; i <= 10; i++) {
            if (i % 2 == 0) {
                sum += i;
            }
        }

        return sum;
    }

    @GetMapping("/string")
    @ResponseBody
    public String string() {
        String[] words = { "A", "Spring", "Boot", "Testing" };
        StringBuilder builder = new StringBuilder();

        for (String word : words) {
            if (word.length() > 3) {
                builder.append(word).append(" ");
            }
        }

        return builder.toString().trim();
    }

    @GetMapping("/boolean")
    @ResponseBody
    public boolean isEven() {
        int[] numbers = { 3, 7, 10, 15, 20 };
        boolean hasEven = false;

        for (int number : numbers) {
            if (number % 2 == 0) {
                hasEven = true;
                break;
            }
        }

        return hasEven;
    }

    @GetMapping("/object")
    @ResponseBody
    public List<Person> personList() {
        List<Person> people = Arrays.asList(
                new Person("Heru", "Cahyadi", 25),
                new Person("Martin", "Kesongo", 32),
                new Person("Dani", "Ramadan", 18),
                new Person("David", "Santoso", 54));

        for (Person person : people) {
            if (person.getAge() > 30) {
                person.setFirstName(person.getFirstName() + " (Senior)");
            }
        }

        return people;
    }
}
