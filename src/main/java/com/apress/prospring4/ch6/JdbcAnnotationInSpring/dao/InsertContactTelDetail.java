package com.apress.prospring4.ch6.JdbcAnnotationInSpring.dao;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.BatchSqlUpdate;

import javax.sql.DataSource;
import java.sql.Types;


public class InsertContactTelDetail extends BatchSqlUpdate {

    private static final String SQL_INSERCT_CONTACT_TEL = "INSERT INTO contact_tel_detail(contact_id, tel_type, tel_number)" +
            "VALUES (:contact_id, :tel_type, :tel_number) ";
    private static final int BATCH_SIZE = 10;

    public InsertContactTelDetail(DataSource ds) {
        super(ds, SQL_INSERCT_CONTACT_TEL);

        declareParameter(new SqlParameter("contact_id", Types.INTEGER));
        declareParameter(new SqlParameter("tel_type",Types.VARCHAR));
        declareParameter(new SqlParameter("tel_number",Types.VARCHAR));

        setBatchSize(BATCH_SIZE);
//  The packet size for the JDBC insert operation.
    }
}
