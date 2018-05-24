package com.apress.prospring4.ch6.jdbcInJava.components;


import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class Contact {
    private Long id;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private List<ContactTelDetail> contactTelDetails;

    public Contact() {
    }

    public Contact(String firstName, String lastName, Date birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }

    public Contact(String firstName, String lastName,String birthDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        this.firstName = firstName;
        this.lastName = lastName;
        java.util.Date date = null;
        try {
            date = format.parse(birthDate);
        } catch (ParseException e) {
            e.getMessage();
        }
        if(date != null) {
            this.birthDate = new Date(date.getTime());
        }else {
            this.birthDate = new Date(Calendar.getInstance().getTimeInMillis());
        }
//        Alternative to input the date from contact. You enter a string that is formatted into util.Date
//        that is the source of the information for sql.date
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public List<ContactTelDetail> getContactTelDetails() {
        return contactTelDetails;
    }

    public void setContactTelDetails(List<ContactTelDetail> contactTelDetails) {
        this.contactTelDetails = contactTelDetails;
    }

    @Override
    public String toString() {
        return "Contact - " +
                "id: " + id +
                ", firstName: " + firstName +
                ", lastName: " + lastName +
                ", birthDate: " + birthDate +
                "\n";
    }
}
