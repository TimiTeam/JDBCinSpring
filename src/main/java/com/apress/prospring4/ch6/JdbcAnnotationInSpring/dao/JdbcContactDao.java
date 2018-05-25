package com.apress.prospring4.ch6.JdbcAnnotationInSpring.dao;

import com.apress.prospring4.ch6.jdbcInJava.components.Contact;
import com.apress.prospring4.ch6.jdbcInJava.components.ContactTelDetail;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
    private UpdateContact updateContact;
    private InsertContact insertContact;

    @Resource(name = "dataSource")
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.selectAllContacts = new SelectAllContacts(dataSource);
        this.byFirstName = new SelectContactByFirstName(dataSource);
        this.updateContact = new UpdateContact(dataSource);
        this.insertContact = new InsertContact(dataSource);
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
        JdbcTemplate template = new JdbcTemplate(dataSource);
        String sql = "SELECT c.id, c.first_name, c.last_name, c.birth_date" +
                ", t.id AS contact_tel_id, t.tel_type, t.tel_number FROM contact c " +
                "LEFT JOIN contact_tel_detail t on c.id = t.contact_id ";
       return template.query(sql,new ContactWithDetailExtractor());
    }

    public void insert(Contact contact) {
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("first_name",contact.getFirstName());
        map.put("last_name",contact.getLastName());
        map.put("birth_date",contact.getBirthDate());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        insertContact.updateByNamedParam(map,keyHolder);
        contact.setId(keyHolder.getKey().longValue());

    }

    public void insertWithDetail(Contact contact) {

        InsertContactTelDetail insertContactTelDetail = new InsertContactTelDetail(dataSource);
//      Each time the insertWithDetail() method is called,
//      a new instance of InsertContactTelDetail is created,
//      because The BatchSqlUpdate class is not secure with respect to threads.

        Map<String,Object> map = new HashMap<String, Object>();
        map.put("first_name",contact.getFirstName());
        map.put("last_name",contact.getLastName());
        map.put("birth_date",contact.getBirthDate());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        insertContact.updateByNamedParam(map,keyHolder);
        contact.setId(keyHolder.getKey().longValue());
        LOGGER.info("Insert contact with id: "+contact.getId());

        List<ContactTelDetail> telDetails = contact.getContactTelDetails();
        if(telDetails != null){
            for(ContactTelDetail c: telDetails){
                map = new HashMap<String, Object>();
                map.put("contact_id",contact.getId());
                map.put("tel_type",c.getTelType());
                map.put("tel_number",c.getTelNumber());
                insertContactTelDetail.updateByNamedParam(map);
            }
        }
        insertContactTelDetail.flush();
    }

    public void update(Contact contact) {
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("first_name",contact.getFirstName());
        map.put("last_name",contact.getLastName());
        map.put("id",contact.getId());
        map.put("birth_date",contact.getBirthDate());

        updateContact.updateByNamedParam(map);
        LOGGER.info("Update a contact with id: "+contact.getId());
    }

    private static final class ContactWithDetailExtractor implements ResultSetExtractor<List<Contact>>{


        public List<Contact> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
            Map<Long,Contact> map = new HashMap<Long, Contact>();
            Contact contact = null;

            for(;resultSet.next();){
                Long id = resultSet.getLong("id");
                contact = map.get(id);

                if(contact == null){
                    contact = new Contact();
                    contact.setId(id);
                    contact.setFirstName(resultSet.getString("first_name"));
                    contact.setLastName(resultSet.getString("last_name"));
                    contact.setBirthDate(resultSet.getDate("birth_date"));
                    contact.setContactTelDetails(new ArrayList<ContactTelDetail>());
                    map.put(id, contact);
                }
                Long contactTelDetailId = resultSet.getLong("contact_tel_id");

                if(contactTelDetailId >0){
                    ContactTelDetail contactTelDetail = new ContactTelDetail();
                    contactTelDetail.setContactId(contactTelDetailId);
                    contactTelDetail.setContactId(id);
                    contactTelDetail.setTelType(resultSet.getString("tel_type"));
                    contactTelDetail.setTelNumber(resultSet.getString("tel_number"));
                    contact.getContactTelDetails().add(contactTelDetail);
                }
            }
            return new ArrayList<Contact>(map.values());
        }
    }

}











