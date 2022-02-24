package com.pumpink.demo.RunAllTestPac;

import groovy.util.logging.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@CrossOrigin
public class RunAllController {

    @GetMapping("/test")
    public void testGet(){

    }


}
