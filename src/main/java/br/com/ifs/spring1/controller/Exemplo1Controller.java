package br.com.ifs.spring1.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("exemplo1")
public class Exemplo1Controller {

    //Get Post Put Delete
    @GetMapping
    public String olaMundoGet1() {
        return "Ola mundo Get1!";
    }
    @GetMapping("/get2")
    public String olaMundoGet2() {
        return "Ola mundo Get2!";
    }
    @PostMapping
    public String olaMundoPost() {
        return "Ola mundo Post!";
    }
    @PutMapping
    public String olaMundoPut() {
        return "Ola mundo Put!";
    }

    @DeleteMapping
    public String olaMundoDelete() {
        return "Ola mundo Delete!";
    }
}
