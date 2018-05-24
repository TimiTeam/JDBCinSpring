package com.apress.prospring4.ch6.JdbcAnnotationInSpring.dao;


import com.apress.prospring4.ch6.jdbcInJava.components.Contact;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class SelectContactByFirstName extends MappingSqlQuery<Contact>{
    private static final String SQL_BY_FIRST_NAME = "SELECT * FROM contact WHERE first_name = :first_name";
//  The ':first_name' is named parameter
    public SelectContactByFirstName(DataSource ds) {
        super(ds, SQL_BY_FIRST_NAME);
        super.declareParameter(new SqlParameter("first_name", Types.VARCHAR));
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
