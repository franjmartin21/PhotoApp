package com.photoapp.controllers;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by fran on 17/08/14.
 */
@Controller
public class HelloController {
	private final Logger logger = LogManager.getLogger(HelloController.class.getName());

    @RequestMapping("/hello")
    public ModelAndView sayHelloToOpenShift(){
    	logger.info("sayHelloToOpenShift --> Start");
        Map<String, Object> templateModel = new HashMap<String, Object>();
        templateModel.put("firstname", "Francisco");
        templateModel.put("lastname", "Martin");
        logger.info("sayHelloToOpenShift --> End");
        return new ModelAndView("page/hello2", templateModel);
    }

}
