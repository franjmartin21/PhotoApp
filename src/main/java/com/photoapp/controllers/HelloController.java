package com.photoapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fran on 17/08/14.
 */
@Controller
public class HelloController {

    @RequestMapping("/hello")
    public ModelAndView sayHelloToOpenShift(){
        Map<String, Object> templateModel = new HashMap<String, Object>();
        templateModel.put("firstname", "Francisco");
        templateModel.put("lastname", "Martin");
        return new ModelAndView("page/hello2", templateModel);
    }

}
