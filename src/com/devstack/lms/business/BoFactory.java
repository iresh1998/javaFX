package com.devstack.lms.business;

import com.devstack.lms.business.custom.impl.CourseBoImpl;
import com.devstack.lms.business.custom.impl.RegistrationBoImpl;
import com.devstack.lms.business.custom.impl.StudentBoImpl;
import com.devstack.lms.business.custom.impl.UserBoImpl;

public class BoFactory {
    private BoFactory daoFactory;

    private BoFactory() {
    }

    public enum BoType {
        COURSE, REGISTRATION, STUDENT, USER
    }


    public static <T> T getBo(BoType type) {
        switch (type) {
            case USER:
                return (T) new UserBoImpl();
            case COURSE:
                return (T) new CourseBoImpl();
            case STUDENT:
                return (T) new StudentBoImpl();
            case REGISTRATION:
                return (T) new RegistrationBoImpl();
            default:
                return null;
        }
    }

}
