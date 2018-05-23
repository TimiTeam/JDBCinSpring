package com.apress.prospring4.ch6.jdbcInSpring.components;


import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.util.HashMap;
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


    public void afterPropertiesSet() throws Exception {
        if(dataSource == null){
            throw new BeanCreationException("Properties dataSource must be set!");
        }
        if(template == null){
            throw new BeanCreationException("Null JdbcTemplate on ContactDao");
        }
    }
}
