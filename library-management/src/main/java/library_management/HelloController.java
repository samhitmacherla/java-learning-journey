package library_management;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello Samhit! Your Spring Boot app is working!";
    }

    @GetMapping("/info")
    public String info() {
        return "Library Management System - Version 1.0";
    }
}