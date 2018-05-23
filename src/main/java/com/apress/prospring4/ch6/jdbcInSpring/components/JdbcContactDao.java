package com.apress.prospring4.ch6.jdbcInSpring.components;


import com.apress.prospring4.ch6.jdbcInJava.components.Contact;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JdbcContactDao implements ContactDao,InitializingBean{
    private DataSource dataSource;
    private NamedParameterJdbcTemplate template;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        NamedParameterJdbcTemplate template1 = new NamedParameterJdbcTemplate(dataSource);
        this.template = template1;
    }

    public String findFirstNameById(Long id) {
        String sql = "SELECT first_name FROM contact WHERE id = :contactId";
        Map<String,Object> namedParameter = new HashMap<String, Object>();
        namedParameter.put("contactId",id);
        return template.queryForObject(sql,namedParameter,String.class);
    }

    public List<Contact> getAll() {
        String sql= "SELECT * FROM contact";
        return template.query(sql,new ContactMapper());
    }

    public void afterPropertiesSet() throws Exception {
        if(dataSource == null){
            throw new BeanCreationException("Properties dataSource must be set!");
        }
        if(template == null){
            throw new BeanCreationException("Null JdbcTemplate on ContactDao");
        }
    }

    private static final class ContactMapper implements RowMapper<Contact>{
//        The RowMapper<> converts the values of a particular record to the result set to the desired domain object.
// Declaring it as a static inner class allows you to share RowMapper <T> - a variety of search methods.

        public Contact mapRow(ResultSet resultSet, int i) throws SQLException {
            Contact contact = new Contact();
            contact.setId(resultSet.getLong("id"));
            contact.setFirstName(resultSet.getString("first_name"));
            contact.setLastName(resultSet.getString("last_name"));
            contact.setBirthDate(resultSet.getDate("birth_date"));

            return contact;
        }
    }
}
