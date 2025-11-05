package com.parrino.riccardo.controller;

import org.simple.framework.annotations.Get;
import org.simple.framework.annotations.Post;
import org.simple.framework.beans.annotations.Bean;
import org.simple.framework.beans.annotations.Inject;

import com.parrino.riccardo.service.MyService;

@Bean
public class MyController {

    @Inject
    private MyService myService;
    
    @Get(path = "/api/myController/get")
    public String myGetController() {
        return "I'm /api/myController/get endpoint";
    }

    @Post(path = "/api/myController/post")
    public String myPostController() {
        return "I'm /api/myController/post endpoint";
    }

}
