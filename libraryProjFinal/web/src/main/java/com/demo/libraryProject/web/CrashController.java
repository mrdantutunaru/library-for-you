package com.demo.libraryProject.web;

import com.demo.libraryProject.utils.NoSuchBookException;
import org.springframework.web.bind.annotation.GetMapping;

public class CrashController {
    @GetMapping("/oups")
    public String triggerException() {
        throw new NoSuchBookException();
    }

}
