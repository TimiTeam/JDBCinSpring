package com.apress.prospring4.ch6.jdbcInSpring;


import com.apress.prospring4.ch6.jdbcInJava.components.Contact;
import com.apress.prospring4.ch6.jdbcInJava.components.ContactTelDetail;
import com.apress.prospring4.ch6.jdbcInSpring.components.ContactDao;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class JdbcContactDaoSample {
    private static final Logger LOGGER = Logger.getLogger(JdbcContactDaoSample.class);

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("datasource-drivermanager.xml");

        ContactDao contactDao = context.getBean("contactDao",ContactDao.class);
        LOGGER.info("The name of contact 1 is: "+contactDao.findFirstNameById(1L));

        List<Contact> contacts = contactDao.getAll();
        LOGGER.info("All contacts: ");
        for(Contact c: contacts){
            LOGGER.info(c);
            if(c.getContactTelDetails() != null ){
                for(ContactTelDetail detail: c.getContactTelDetails()){
                    LOGGER.info(detail);
                }
            }
        }
    }
}
