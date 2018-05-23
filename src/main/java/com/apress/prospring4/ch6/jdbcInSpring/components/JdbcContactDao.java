package com.apress.prospring4.ch6.jdbcInSpring.components;


import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class JdbcContactDao implements ContactDao,InitializingBean{
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public String findFirstNameById(Long id) {
        return jdbcTemplate.queryForObject("SELECT first_name FROM contact WHERE id=?",
                new Object[]{id},String.class);
    }


    public void afterPropertiesSet() throws Exception {
        if(dataSource == null){
            throw new BeanCreationException("Properties dataSource must be set!");
        }
        if(jdbcTemplate == null){
            throw new BeanCreationException("Null JdbcTemplate on ContactDao");
        }
    }
}
