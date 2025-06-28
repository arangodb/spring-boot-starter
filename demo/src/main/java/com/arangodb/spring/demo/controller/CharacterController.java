package com.arangodb.spring.demo.controller;

import com.arangodb.spring.demo.entity.Character;
import com.arangodb.spring.demo.repository.CharacterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/characters")
public class CharacterController {

    @Autowired
    private CharacterRepository repository;

    @GetMapping
    public List<Character> getAllCharacters() {
        return (List<Character>) repository.findAll();
    }
}

