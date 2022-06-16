package com.blocpal.mbnk;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

@Controller("/")
public class MainController {
    @Get("/show")
    public String show(){
        return "$$$$$$$$$$$NEW$$$$$$$$$$$$$$";
    }
}
