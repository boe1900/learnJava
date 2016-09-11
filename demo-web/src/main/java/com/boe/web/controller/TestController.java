package com.boe.web.controller;

import com.boe.service.inter.domain.User;
import com.boe.service.inter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Boe on 2016-09-10.
 */
@Controller
public class TestController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    @ResponseBody
    public User getUserMsgById(@PathVariable Long id){
        return userService.getUserById(id);
    }

}
