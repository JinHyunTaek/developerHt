package com.programming1.developerHt.controller;

import com.programming1.developerHt.dto.CreateDeveloper;
import com.programming1.developerHt.dto.DeveloperDto;
import com.programming1.developerHt.service.DeveloperService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class HtController {
    private final DeveloperService developerService;

    @GetMapping("/developers")
    public List<DeveloperDto> getAllDevelopers() {
        log.info("Get/developers Http/1.1");
        return Arrays.asList("snow", "Elsa", "Olaf");
    }


    @PostMapping("/create-developers")
    public CreateDeveloper.Response createDevelopers(
         @Valid @RequestBody CreateDeveloper.Request request
            ) {
        log.info("request: {}", request);

        return developerService.createDeveloper(request);
    }

}
