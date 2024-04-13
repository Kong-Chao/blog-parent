package com.sky.system.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author kc
 */
@RestController
@RequestMapping("/v2")
public class V2Controller {

    @GetMapping("/test")
    public String test(){
        return "654654";
    }
}
