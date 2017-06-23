package com.tlw.jdbctemplate.snippets;

import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DeadlockLoserDataAccessException;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;

import java.sql.SQLException;

/**
 * Created by hdp on 2017/6/23.
 */
public class A04SQLExceptionTranslator {

    Context context = new Context();

    @Before
    public void create() {
        context.getJDBC_Template().execute("create table user (id int AUTO_INCREMENT primary key, userName varchar(32), password varchar(32))");
    }

    @Test
    public void test() {
        //19.2.3 SQLExceptionTranslator
        // create a custom translator and set the DataSource for the default translation lookup
        CustomSQLErrorCodesTranslator tr = new CustomSQLErrorCodesTranslator();
        tr.setDataSource(context.getDataSource());
        context.getJDBC_Template().setExceptionTranslator(tr);

        context.getJDBC_Template().update("update orders" +
                " set shipping_charge = shipping_charge * ? / 100" +
                " where id = ?");
    }

    public class CustomSQLErrorCodesTranslator extends SQLErrorCodeSQLExceptionTranslator {

        protected DataAccessException customTranslate(String task, String sql, SQLException sqlex) {
            System.out.println(sqlex.getErrorCode());
            if (sqlex.getErrorCode() == -12345) {
                return new DeadlockLoserDataAccessException(task, sqlex);
            } else if (sqlex.getErrorCode() == 42102) {
                return new DataAccessException("My test custom exception...") {

                };
            }
            return null;
        }
    }
}
