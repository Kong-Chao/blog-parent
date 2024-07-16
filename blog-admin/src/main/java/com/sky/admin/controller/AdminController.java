package com.sky.admin.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author admin
 */
@RestController
@RequestMapping("/v1")
public class AdminController {

    @GetMapping("/test")
    public String test(){
        return "你奶奶";
    }
}
