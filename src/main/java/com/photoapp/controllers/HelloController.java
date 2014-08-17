package com.photoapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by fran on 17/08/14.
 */
@Controller
public class HelloController {

    @RequestMapping("/hello")
    public String sayHelloToOpenShift(){
        return "hello";
    }


}
