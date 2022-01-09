package com.programming1.developerHt.controller;

import com.programming1.developerHt.dto.CreateDeveloper;
import com.programming1.developerHt.dto.DeveloperDetailDto;
import com.programming1.developerHt.dto.DeveloperDto;
import com.programming1.developerHt.dto.EditDeveloper;
import com.programming1.developerHt.service.DeveloperService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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
        return developerService.getAllDevelopers();
    }

    @GetMapping("/developer/{memberId}")
    public DeveloperDetailDto getDeveloperDetail(
            @PathVariable String memberId
    ) {
        log.info("Get/developers Http/1.1");
        return developerService.getDeveloperDetail(memberId);
    }

    @PostMapping("/create-developers")
    public CreateDeveloper.Response createDevelopers(
         @Valid @RequestBody CreateDeveloper.Request request
            ) {
        log.info("request: {}", request);

        return developerService.createDeveloper(request);
    }

    @PutMapping("/developer/{memberId}")
    public DeveloperDetailDto editDeveloper(
            @PathVariable String memberId,
            @Valid @RequestBody EditDeveloper.Request request
    ) {
        log.info("Get/developers Http/1.1");

        return developerService.editDeveloper(memberId, request);
    }

    @DeleteMapping("/developer/{memberId}")
    public DeveloperDetailDto deleteDeveloper(@PathVariable String memberId){
        return developerService.deleteDeveloper(memberId);
    }
}
