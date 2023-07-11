package com.example.firstservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class FirestServiceController {

    @GetMapping("/welcome")
    public String firstService(){
        return "hello first service";
    }

}
