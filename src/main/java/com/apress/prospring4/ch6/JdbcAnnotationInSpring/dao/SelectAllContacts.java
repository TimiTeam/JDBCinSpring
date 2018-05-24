package com.apress.prospring4.ch6.JdbcAnnotationInSpring.dao;

import com.apress.prospring4.ch6.jdbcInJava.components.Contact;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.object.MappingSqlQuery;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * Created by admin on 24.05.2018.
 */
public class SelectAllContacts extends MappingSqlQuery<Contact> {

    private static final String SQL_SELECT_ALL_CONTACTS = "SELECT * FROM contact";

    public SelectAllContacts(DataSource ds) {
        super(ds, SQL_SELECT_ALL_CONTACTS);
    }

    @Override
    protected Contact mapRow(ResultSet resultSet, int i) throws SQLException {
        Contact contact = new Contact();
        contact.setId(resultSet.getLong("id"));
        contact.setFirstName(resultSet.getString("first_name"));
        contact.setLastName(resultSet.getString("last_name"));
        contact.setBirthDate(resultSet.getDate("birth_date"));

        return contact;
    }
}
