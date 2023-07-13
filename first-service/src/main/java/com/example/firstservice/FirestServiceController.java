package com.example.firstservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/first-service")
@Slf4j
public class FirestServiceController {

    Environment env;

    @Autowired
    public FirestServiceController(Environment env){
        this.env = env;
    }

    @GetMapping("/welcome")
    public String firstService(){
        return "hello first service";
    }

    @GetMapping("/message")
    public String message(@RequestHeader("first-request") String header){
        log.info(header);
        return "Hello world First Service";
    }

    @GetMapping("/check")
    public String check(HttpServletRequest request){

        log.info("Server port = {}", env.getProperty("local.server.port"));
        log.info("Server port = {}", request.getServerPort());
        return "Hi, there. This is a message from First-service";
    }
}
