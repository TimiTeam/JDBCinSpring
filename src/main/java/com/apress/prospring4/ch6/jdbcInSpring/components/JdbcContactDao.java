package com.apress.prospring4.ch6.jdbcInSpring.components;


import com.apress.prospring4.ch6.jdbcInJava.components.Contact;
import com.apress.prospring4.ch6.jdbcInJava.components.ContactTelDetail;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JdbcContactDao implements ContactDao,InitializingBean {
    private DataSource dataSource;
    private NamedParameterJdbcTemplate template;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        NamedParameterJdbcTemplate template1 = new NamedParameterJdbcTemplate(dataSource);
        this.template = template1;
    }

    public String findFirstNameById(Long id) {
        String sql = "SELECT first_name FROM contact WHERE id = :contactId";
        Map<String, Object> namedParameter = new HashMap<String, Object>();
        namedParameter.put("contactId", id);
        return template.queryForObject(sql, namedParameter, String.class);
    }

    public List<Contact> findAllWithDetail() {
       String sql = "select c.id, c.first_name, c.last_name, c.birth_date" +
                ", t.id as contact_tel_id, t.tel_type, t.tel_number from contact c " +
                "left join contact_tel_detail t on c.id = t.contact_id";
        return template.query(sql,new ContactWithDetailExtractor());
    }

    public void afterPropertiesSet() throws Exception {
        if (dataSource == null) {
            throw new BeanCreationException("Properties dataSource must be set!");
        }
        if (template == null) {
            throw new BeanCreationException("Null JdbcTemplate on ContactDao");
        }
    }

    private static final class ContactWithDetailExtractor implements ResultSetExtractor<List<Contact>> {

        public List<Contact> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
            Map<Long, Contact> map = new HashMap<Long, Contact>();
            Contact contact = null;
            for (; resultSet.next(); ) {
                Long id = resultSet.getLong("id");
                contact = map.get(id);
                if(contact == null){
                    contact = new Contact();
                    contact.setId(resultSet.getLong("id"));
                    contact.setFirstName(resultSet.getString("first_name"));
                    contact.setLastName(resultSet.getString("last_name"));
                    contact.setBirthDate(resultSet.getDate("birth_date"));
                    contact.setContactTelDetails(new ArrayList<ContactTelDetail>());
                    map.put(id,contact);
                }
                Long contactTelDetailId = resultSet.getLong("contact_tel_id");
                if(contactTelDetailId > 0){
                    ContactTelDetail contactTelDetail = new ContactTelDetail();
                    contactTelDetail.setId(contactTelDetailId);
                    contactTelDetail.setContactId(id);
                    contactTelDetail.setTelNumber(resultSet.getString("tel_number"));
                    contactTelDetail.setTelType(resultSet.getString("tel_type"));
                    contact.getContactTelDetails().add(contactTelDetail);
                }
            }
            return new ArrayList<Contact>(map.values());
        }
    }
}
