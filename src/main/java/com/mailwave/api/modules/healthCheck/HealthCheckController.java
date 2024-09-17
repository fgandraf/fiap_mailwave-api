package com.mailwave.api.modules.healthCheck;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class HealthCheckController {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> getIndexByFolder(){
        return ResponseEntity.ok("Health Check: OK");
    }
}
