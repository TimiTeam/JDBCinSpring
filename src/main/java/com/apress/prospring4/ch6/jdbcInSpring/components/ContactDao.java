package com.apress.prospring4.ch6.jdbcInSpring.components;

import com.apress.prospring4.ch6.jdbcInJava.components.Contact;

import java.util.List;

public interface ContactDao {
    String findFirstNameById(Long id);
    List<Contact> getAll();

}
