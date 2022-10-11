package com.jzq.server.controller;

import com.jzq.server.pojo.Salary;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class TestController {

    @GetMapping("/test")
    public Map test(){
        Map<String, String> map = new HashMap<>();
        map.put("aaa", "sdas");
        return map;
    }

    @GetMapping("/salary/sob/h")
    public String t2() {
        return "/salary/sob/";
    }

    @GetMapping("/personnel/salary/h")
    public String t3() {
        return "/personnel/salary/";
    }

    @GetMapping("/statistics/all")
    public String t4 () {
        return "/statistics/all";
    }

    @GetMapping("/emc/ployee/basi/454545/dsdas")
    public String t5 () {
        return "/emc/ployee/basi";
    }

}
