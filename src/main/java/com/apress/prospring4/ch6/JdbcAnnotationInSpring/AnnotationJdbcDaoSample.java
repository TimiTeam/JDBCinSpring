package com.apress.prospring4.ch6.JdbcAnnotationInSpring;


import com.apress.prospring4.ch6.JdbcAnnotationInSpring.dao.ContactDao;
import com.apress.prospring4.ch6.jdbcInJava.components.Contact;
import com.apress.prospring4.ch6.jdbcInJava.components.ContactTelDetail;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class AnnotationJdbcDaoSample {
    private static final Logger LOGGER = Logger.getLogger(AnnotationJdbcDaoSample.class);

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("app-context-annotation.xml");
        ContactDao dao = context.getBean("contactDao",ContactDao.class);

        Contact contact = new Contact("Timur","Bujalo",
                "1996-12-22");
        contact.setId(6L);

        dao.update(contact);

        listContacts(dao.findAll());

    }
    private static void listContacts(List<Contact> contacts){
        for(Contact c: contacts) {
            LOGGER.info(c);
            if(c.getContactTelDetails() != null){
                for (ContactTelDetail contactTelDetail: c.getContactTelDetails()){
                    LOGGER.info("----"+contactTelDetail);
                }
            }
        }
    }
}
