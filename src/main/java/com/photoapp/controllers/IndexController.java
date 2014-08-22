package com.photoapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by fran on 22/08/14.
 */
@Controller
public class IndexController extends GenericController {

    @RequestMapping("/index")
    public ModelAndView getIndex() {

        return new ModelAndView(formViewWeb);
    }


}
