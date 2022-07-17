package com.ead.authuser.services.impl;

import com.ead.authuser.repositories.UserCourseRepository;
import com.ead.authuser.services.UserCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserCourseServiceImpl implements UserCourseService {

    @Autowired
    UserCourseRepository userCourseRepository;

//    Another way to be in the place of @Autowired:
//    final
//    UserCourseRepository userCourseRepository;
//
//    public UserCourseServiceImpl(UserCourseRepository userCourseRepository) {
//        this.userCourseRepository = userCourseRepository;
//    }
}
