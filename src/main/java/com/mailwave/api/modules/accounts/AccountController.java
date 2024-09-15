package com.mailwave.api.modules.accounts;

import com.mailwave.api.modules.accounts.dtos.AccountResponse;
import com.mailwave.api.modules.accounts.dtos.AccountCreateRequest;
import com.mailwave.api.modules.accounts.dtos.AccountUpdateRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {


    //-----Serviços que serão usados por esta controladora
    private final AccountService accountService;




    //-----Construtor padrão injeta as dependências
    //-----Evitar a anotação @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }




    // Criar uma nova conta
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AccountResponse> createAccount(@Valid @RequestBody AccountCreateRequest request) {
        var response = accountService.createAccount(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("{/id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(location).body(response);
    }




    // Buscar todas as contas
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<AccountResponse>> getAllAccounts() {
        var response = accountService.getAllAccounts();
        return ResponseEntity.ok(response);
    }




    // Buscar uma conta pelo ID
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<AccountResponse> getAccountById(@PathVariable Long id) {
        var response = accountService.getAccountById(id);
        return ResponseEntity.ok(response);
    }




    // Atualizar uma conta existente
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<AccountResponse> updateAccount(@Valid @RequestBody AccountUpdateRequest request) {
        var response = accountService.updateAccount(request);
        return ResponseEntity.ok(response);
    }




    // Deletar uma conta
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }


}

