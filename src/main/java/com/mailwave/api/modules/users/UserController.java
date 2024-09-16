package com.mailwave.api.modules.users;

import com.mailwave.api.config.TokenService;
import com.mailwave.api.modules.users.dtos.*;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService service;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public UserController(UserService userService, AuthenticationManager authenticationManager, TokenService tokenService) {
        this.service = userService;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserLoginResponse> login(@RequestBody @Valid UserLoginRequest loginInput) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(
                loginInput.email(),
                loginInput.password()
        );

        var auth = authenticationManager.authenticate(usernamePassword);
        var user = (User)auth.getPrincipal();
        var token = tokenService.generateToken(user);

        return ResponseEntity.ok(new UserLoginResponse(token));
    }

    @GetMapping("/email/{email}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserResponse> getByUsername(@PathVariable String email) {
        var user = service.getByEmail(email);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserResponse> getById(@PathVariable Long id) {
        var user = service.getById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserResponse> register(@RequestBody @Valid UserCreateRequest request) {
        var response = service.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("{/id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserResponse> update(@RequestBody @Valid UserUpdateRequest request) {
        return ResponseEntity.ok(service.update(request));
    }

    @PutMapping("/activate/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserResponse> activate(@PathVariable Long id) {
        return ResponseEntity.ok(service.activate(id));
    }

    @PutMapping("/deactivate/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserResponse> deactivate(@PathVariable Long id) {
        return ResponseEntity.ok(service.deactivate(id));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Page<UserResponse>> getAll(Pageable page) {
        var response = service.getAll(page);
        return ResponseEntity.ok(response);
    }

}
