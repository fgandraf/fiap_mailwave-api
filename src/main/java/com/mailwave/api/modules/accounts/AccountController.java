package com.mailwave.api.modules.accounts;

import com.mailwave.api.modules.accounts.dtos.AccountResponse;
import com.mailwave.api.modules.accounts.dtos.AccountCreateRequest;
import com.mailwave.api.modules.accounts.dtos.AccountUpdateRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<AccountResponse> createAccount(@Valid @RequestBody AccountCreateRequest request) {
        var createdAccount = accountService.createAccount(request);
        return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
    }




    // Buscar todas as contas
    @GetMapping
    public ResponseEntity<List<AccountResponse>> getAllAccounts() {
        var accounts = accountService.getAllAccounts();
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }




    // Buscar uma conta pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> getAccountById(@PathVariable Long id) {
        var account = accountService.getAccountById(id);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }




    // Atualizar uma conta existente
    @PutMapping
    public ResponseEntity<AccountResponse> updateAccount(@Valid @RequestBody AccountUpdateRequest request) {
        var updatedAccount = accountService.updateAccount(request);
        return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
    }




    // Deletar uma conta
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}

