package com.parrino.riccardo.controller;

import org.simple.framework.annotations.Get;
import org.simple.framework.annotations.Post;
import org.simple.framework.beans.annotations.Bean;
import org.simple.framework.beans.annotations.Inject;

import com.parrino.riccardo.model.Order;
import com.parrino.riccardo.service.MyService;

@Bean
public class MyController {

    @Inject
    private MyService myService;
    
    @Get(path = "/api/myController/get")
    public Order myGetController() {
        return new Order().setId(1l).setName("myGetOrder").setPrice(1.50);
    }

    @Post(path = "/api/myController/post")
    public Order myPostController() {
        return new Order().setId(2l).setName("myPostOrder").setPrice(2.50);
    }

}
