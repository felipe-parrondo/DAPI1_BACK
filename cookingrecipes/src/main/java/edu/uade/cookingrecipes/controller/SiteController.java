package edu.uade.cookingrecipes.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Site Operations")
@Validated
@RestController
@RequestMapping("/site")
@RequiredArgsConstructor
public class SiteController {
}
