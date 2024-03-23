package com.project.java.pp.web;

import com.project.java.pp.dto.MemberDto;
import com.project.java.pp.repository.filters.MemberFilter;
import com.project.java.pp.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/project/members")
@RequiredArgsConstructor
@Tag(name = "member",description = "Member Controller")
public class MemberController {
    private final MemberService service;

    @Operation(summary = "Get member by id",
            responses = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "400")
            }
    )
    @GetMapping(value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public MemberDto getById(@PathVariable Long id){
        return service.getById(id);
    }

    @Operation(summary = "Get all members")
    @GetMapping(value = "",
        produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MemberDto> getAll(){
        return service.getAll();
    }
    @Operation(summary = "Get members matching the requested filter")
    @PostMapping(value = "/search",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MemberDto> search(@RequestBody MemberFilter filter){
        return service.search(filter);
    }

    @Operation(summary = "Creation a new member")
    @PostMapping(value = "",
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
    public MemberDto create(@RequestBody MemberDto dto){
        return service.create(dto);
    }

    @Operation(summary = "Update member")
    @PutMapping(value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public MemberDto update(@PathVariable Long id,
                            @RequestBody @Valid MemberDto dto){
        return service.update(id,dto);
    }
    @Operation(summary = "Delete member")
    @DeleteMapping(value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void delete(@PathVariable Long id){
        service.delete(id);
    }

}
