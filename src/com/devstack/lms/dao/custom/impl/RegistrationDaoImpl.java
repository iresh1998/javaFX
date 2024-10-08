package com.devstack.lms.dao.custom.impl;

import com.devstack.lms.dao.CrudUtil;
import com.devstack.lms.dao.custom.RegistrationDao;
import com.devstack.lms.db.DbConnection;
import com.devstack.lms.entity.Registration;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class RegistrationDaoImpl implements RegistrationDao {

    @Override
    public boolean create(Registration registration) throws SQLException, ClassNotFoundException {

        return CrudUtil.execute("INSERT INTO registration VALUES(?,?,?,?,?,?)",
                registration.getRegisterId(),registration.getDate(),registration.getNic(),registration.getPaymentType().name(),registration.getStudent(),registration.getCourse());
    }

    @Override
    public Registration find(String s) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean update(Registration registration) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public List<Registration> findAll() throws SQLException, ClassNotFoundException {
        return null;
    }
}
