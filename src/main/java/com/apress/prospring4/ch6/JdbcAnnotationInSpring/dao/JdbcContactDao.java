package com.apress.prospring4.ch6.JdbcAnnotationInSpring.dao;

import com.apress.prospring4.ch6.jdbcInJava.components.Contact;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("contactDao")
//This annotation instructs Spring to translate database-specific SQL exceptions
// into a more user-friendly hierarchy of DataAccessException
public class JdbcContactDao implements ContactDao{
    private static final Logger LOGGER = Logger.getLogger(ContactDao.class);

    private DataSource dataSource;
    private SelectAllContacts selectAllContacts;
    private SelectContactByFirstName byFirstName;

    @Resource(name = "dataSource")
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.selectAllContacts = new SelectAllContacts(dataSource);
        this.byFirstName = new SelectContactByFirstName(dataSource);
    }

    public List<Contact> findAll() {
        return selectAllContacts.execute();
    }

    public List<Contact> findByFirstName(String firstName) {
        Map<String,Object> map =  new HashMap<String, Object>();
        map.put("first_name",firstName);
        return byFirstName.executeByNamedParam(map);
    }

    public String findFirstNameById(Long id) {
        return null;
    }

    public List<Contact> findAllWithDetail() {
        return null;
    }

    public void insert(Contact contact) {

    }

    public void insertWithDetail(Contact contact) {

    }

    public void update(Contact contact) {

    }
}
