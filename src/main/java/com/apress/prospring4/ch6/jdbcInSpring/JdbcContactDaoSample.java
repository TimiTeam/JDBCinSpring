package com.apress.prospring4.ch6.jdbcInSpring;


import com.apress.prospring4.ch6.jdbcInSpring.components.ContactDao;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class JdbcContactDaoSample {
    private static final Logger LOGGER = Logger.getLogger(JdbcContactDaoSample.class);

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("datasource-drivermanager.xml");

        ContactDao contactDao = context.getBean("contactDao",ContactDao.class);
        LOGGER.info("The name of contact 1 is: "+contactDao.findFirstNameById(1L));
    }
}