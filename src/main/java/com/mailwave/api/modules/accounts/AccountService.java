package com.mailwave.api.modules.accounts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    // Criar uma nova conta
    public Account createAccount(Account account) {
        account.setCreatedAt(LocalDateTime.now());
        account.setUpdatedAt(LocalDateTime.now());
        return accountRepository.save(account);
    }

    // Buscar todas as contas
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    // Buscar uma conta pelo ID
    public Optional<Account> getAccountById(Long id) {
        return accountRepository.findById(id);
    }

    // Atualizar uma conta existente
    public Optional<Account> updateAccount(Long id, Account accountDetails) {
        return accountRepository.findById(id).map(account -> {
            account.setEmailAddress(accountDetails.getEmailAddress());
            account.setProvider(accountDetails.getProvider());
            account.setPasswordHash(accountDetails.getPasswordHash());
            account.setIncomingServer(accountDetails.getIncomingServer());
            account.setIncomingPort(accountDetails.getIncomingPort());
            account.setIncomingProtocol(accountDetails.getIncomingProtocol());
            account.setOutgoingServer(accountDetails.getOutgoingServer());
            account.setOutgoingPort(accountDetails.getOutgoingPort());
            account.setUseSsl(accountDetails.getUseSsl());
            account.setUseTls(accountDetails.getUseTls());
            account.setUpdatedAt(LocalDateTime.now());
            return accountRepository.save(account);
        });
    }

    // Deletar uma conta
    public boolean deleteAccount(Long id) {
        return accountRepository.findById(id).map(account -> {
            accountRepository.delete(account);
            return true;
        }).orElse(false);
    }
}
