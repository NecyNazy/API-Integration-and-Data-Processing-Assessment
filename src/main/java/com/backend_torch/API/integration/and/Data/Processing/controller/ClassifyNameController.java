package com.backend_torch.API.integration.and.Data.Processing.controller;

import com.backend_torch.API.integration.and.Data.Processing.dtos.ClassifyNameRequest;
import com.backend_torch.API.integration.and.Data.Processing.dtos.ClassifyNameResponse;
import com.backend_torch.API.integration.and.Data.Processing.service.ClassifyNameService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ClassifyNameController {
    private final ClassifyNameService classifyNameService;

    @GetMapping("/classify")
    public ResponseEntity<ClassifyNameResponse> classifyName(@RequestParam String name) {
         return classifyNameService.classifyName(name) ;
    }
}
