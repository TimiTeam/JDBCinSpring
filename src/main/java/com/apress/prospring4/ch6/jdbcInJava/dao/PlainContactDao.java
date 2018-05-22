package com.apress.prospring4.ch6.jdbcInJava.dao;


import com.apress.prospring4.ch6.jdbcInJava.components.Contact;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlainContactDao implements ContactDao {
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/timur_base","root","root");
    }
    private void closeConnection(Connection connection){
        if(connection != null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Contact> findAll() {
        List<Contact> result = new ArrayList<Contact>();
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT *FROM contact");
            ResultSet set = statement.executeQuery();
            for(;set.next();){
                Contact contact = new Contact();
                contact.setId(set.getLong("id"));
                contact.setFirstName(set.getString("first_name"));
                contact.setLastName(set.getString("last_name"));
                contact.setBirthDate(set.getDate("birth_date"));

                result.add(contact);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            closeConnection(connection);
        }
        return result;
    }

    public List<Contact> findByFirstName(String firstName) {
        List<Contact> result = new ArrayList<Contact>();
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement statement =
                    connection.prepareStatement("SELECT * FROM contact WHERE first_name =?");
            statement.setString(1,firstName);
            ResultSet set = statement.executeQuery();
            for (;set.next();){
                Contact contact = new Contact();
                contact.setId(set.getLong("id"));
                contact.setFirstName(set.getString("first_name"));
                contact.setLastName(set.getString("last_name"));
                contact.setBirthDate(set.getDate("birth_date"));

                result.add(contact);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            closeConnection(connection);
        }
        return result;
    }

    public String findLastNameById(Long id) {
        String result = null;
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT last_name FROM contact WHERE id=?");
            statement.setLong(1, id);
            ResultSet set = statement.executeQuery();
            result = set.getString("last_name");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            closeConnection(connection);
        }
        return result;
    }

    public String findFirstNameById(Long id) {
        String result = null;
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT first_name FROM contact WHERE id=?");
            statement.setLong(1, id);
            ResultSet set = statement.executeQuery();
            result = set.getString("first_name");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            closeConnection(connection);
        }
        return result;
    }

    public void insert(Contact contact) {
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO contact(first_name, last_name, birth_date)" +
                    " VALUES (?,?,?)",Statement.RETURN_GENERATED_KEYS);
            statement.setString(1,contact.getFirstName());
            statement.setString(2,contact.getLastName());
            statement.setDate(3,contact.getBirthDate());
            statement.execute();
            ResultSet generateKeys = statement.getGeneratedKeys();
            if(generateKeys.next()){
                contact.setId(generateKeys.getLong(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            closeConnection(connection);
        }
    }

    public void update(Contact contact) {

    }

    public void delete(Long contactID) {
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM contact WHERE id=?");
            statement.setLong(1,contactID);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            closeConnection(connection);
        }
    }
}
