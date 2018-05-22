package com.apress.prospring4.ch6.jdbcInJava.dao;


import com.apress.prospring4.ch6.jdbcInJava.components.Contact;

import java.util.List;

public interface ContactDao {
    List<Contact> findAll();
    List<Contact> findByFirstName(String firstName);
    String findLastNameById(Long id);
    String findFirstNameById(Long id);
    void insert(Contact contact);
    void update(Contact contact);
    void delete(Long contactID);
}
