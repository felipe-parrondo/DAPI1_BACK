package edu.uade.cookingrecipes.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "User Operations")
@Validated
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    //TODO recordar que el POST para crear tambien funciona como PUT
}