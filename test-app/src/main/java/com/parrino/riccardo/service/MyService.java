package com.parrino.riccardo.service;

import org.simple.framework.beans.annotations.Bean;
import org.simple.framework.beans.annotations.Inject;

import com.parrino.riccardo.repository.MyRepository;

@Bean
public class MyService {
 
    @Inject
    private MyRepository myRepository;

}
