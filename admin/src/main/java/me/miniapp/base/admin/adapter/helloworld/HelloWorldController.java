package me.miniapp.base.admin.adapter.helloworld;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("")
@AllArgsConstructor
public class HelloWorldController {

    @RequestMapping("/hello")
    public String hello() {
        return "Hello World";
    }
}
