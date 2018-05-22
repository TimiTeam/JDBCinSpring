package com.apress.prospring4.ch6.jdbcInJava;


import com.apress.prospring4.ch6.jdbcInJava.components.Contact;
import com.apress.prospring4.ch6.jdbcInJava.dao.ContactDao;
import com.apress.prospring4.ch6.jdbcInJava.dao.PlainContactDao;
import org.apache.log4j.Logger;

import java.sql.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class PlaintJdbcSample {
    private static final Logger LOGGER = Logger.getLogger(PlaintJdbcSample.class);
    private static ContactDao contactDao = new PlainContactDao();

    public static void main(String[] args) {

        listAllContacts();

        LOGGER.info("Insert new contact");

        contactDao.insert(new Contact("Timur","Bujalo",
                new Date(new GregorianCalendar(1996,12,22).getTime().getTime())));
        Contact contact = new Contact();
        contact.setFirstName("John");
        contact.setLastName("Smith");
        contact.setBirthDate(new Date(new GregorianCalendar(1998,11,7).getTime().getTime()));
        contactDao.insert(contact);

        contactDao.delete(contact.getId());
        listAllContacts();
    }
    private static void listAllContacts(){
        List<Contact> allContacts = contactDao.findAll();
        StringBuilder builder = new StringBuilder();
        for (Contact contact: allContacts){
            builder.append(contact);
        }
        LOGGER.info("\n"+builder.toString()+"\n");
    }
}
