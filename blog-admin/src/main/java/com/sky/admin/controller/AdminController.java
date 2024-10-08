package com.sky.admin.controller;

import com.sky.common.core.domain.CommonResult;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author admin
 */
@RestController
@RequestMapping("/v1")
public class AdminController {

    @PreAuthorize("hasAnyAuthority('a:a:a')")
    @GetMapping("/test")
    public CommonResult<String> test(){
        return CommonResult.success("你奶奶");
    }
}
