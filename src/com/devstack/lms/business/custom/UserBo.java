package com.devstack.lms.business.custom;

import com.devstack.lms.dto.UserDto;

import java.sql.SQLException;
import java.util.List;

public interface UserBo {
    boolean create(UserDto dto) throws SQLException, ClassNotFoundException;
    public boolean login(String username, String password) throws ClassNotFoundException, SQLException;

}
