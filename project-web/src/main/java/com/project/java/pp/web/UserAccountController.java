package com.project.java.pp.web;

import com.project.java.pp.dto.UserAccountDto;
import com.project.java.pp.service.UserAccountService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/project/accounts")
@RequiredArgsConstructor
public class UserAccountController {

    private final UserAccountService service;

    @Operation(summary = "Get all accounts")
    @GetMapping(value = "",
        produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserAccountDto> getAll(){
        return service.getAll();
    }

    @Operation(summary = "Get account by id")
    @GetMapping(value = "/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE)
    public UserAccountDto getById(@PathVariable Long id){
        return service.findById(id);
    }
    @Operation(summary = "Add new account")
    @PostMapping(value = "",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public UserAccountDto addNewAccount(@RequestBody UserAccountDto account){
        return service.create(account);
    }
}
