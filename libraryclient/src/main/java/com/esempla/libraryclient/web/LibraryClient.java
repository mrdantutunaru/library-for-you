package com.esempla.libraryclient.web;

import com.esempla.libraryclient.config.MyConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name="books", url="localhost:8443/api/jws/books", configuration = MyConfig.class)
public interface LibraryClient {

    @GetMapping("")
    String getAll();

    @PostMapping("")
    String saveBook(String encodedJson);


}
