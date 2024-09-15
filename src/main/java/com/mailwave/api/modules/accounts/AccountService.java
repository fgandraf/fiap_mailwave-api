package com.mailwave.api.modules.accounts;

import com.mailwave.api.exceptions.*;
import com.mailwave.api.modules.accounts.dtos.AccountResponse;
import com.mailwave.api.modules.accounts.dtos.AccountCreateRequest;
import com.mailwave.api.modules.accounts.dtos.AccountUpdateRequest;
import com.mailwave.api.modules.users.UserRepository;
import com.mailwave.api.modules.users.UserService;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService {


    //-----Repositórios que serão usados por este serviço
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;




    //-----Construtor padrão injeta as dependências
    //-----Evitar a anotação @Autowired
    public AccountService(AccountRepository accountRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }




    //-----Criar uma nova conta
    @Transactional
    public AccountResponse createAccount(AccountCreateRequest model) {

        // Obtém o User que compõe o objeto Account
        var existentUser = userRepository.findById(model.userId()).orElseThrow(() -> new UserNotFoundException(model.userId()));


        try {
            // Criptografar a senha que vem da requisição
            var encryptedPassword = new BCryptPasswordEncoder().encode(model.password());

            // Cria a entidade Account
            var account = new Account(
                    null,
                    model.emailAddress(),
                    model.provider(),
                    encryptedPassword,
                    model.incomingServer(),
                    model.incomingPort(),
                    model.incomingProtocol(),
                    model.outgoingServer(),
                    model.outgoingPort(),
                    model.useSsl(),
                    model.useTls(),
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    existentUser
            );

            // Salva a entidade na base de dados
            var savedAccount = accountRepository.save(account);

            // Retorna um novo registro(record) construído a partir da entidade salva no banco
            return new AccountResponse(savedAccount);

        }catch (IllegalArgumentException ex) {
            throw new PasswordEncodingException("Erro ao codificar a senha", ex);
        }catch (DataIntegrityViolationException ex) {
            throw new DatabaseOperationException("Erro ao salvar: dados inválidos ou em conflito.", ex);
        } catch (Exception ex) {
            throw new DatabaseOperationException("Erro inesperado ao salvar.", ex);
        }


    }




    //-----Buscar todas as contas
    public List<AccountResponse> getAllAccounts() {

        // Cria uma lista de registros(record) do tipo AccountResponse
        List<AccountResponse> accounts = new ArrayList<>();

        try{
            // Busca todos as contas na base e, para cada uma, cria um registro e adicona na lista
            var results = accountRepository.findAll();

            // Se não houver registro, lança uma exceção
            if (results.isEmpty()) { throw new NoRecordsFoundException(); }

            // Para cada item retornado, cria um registro e adicona na lista
            results.forEach(result -> accounts.add(new AccountResponse(result)));

            // Retorna a lista de registros
            return accounts;


        }catch (NoRecordsFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new DatabaseOperationException("Erro inesperado.", ex);
        }

    }




    //-----Buscar uma conta pelo ID
    public AccountResponse getAccountById(Long id) {
        var account = accountRepository.findById(id).orElseThrow(() -> new AccountNotFoundException(id));
        return new AccountResponse(account);
    }




    //-----Atualizar uma conta existente
    public AccountResponse updateAccount(AccountUpdateRequest model) {

        // Obtém o User que compõe o objeto Account
        var existentUser = userRepository.findById(model.userId()).orElseThrow(() -> new UserNotFoundException(model.userId()));

        // Instancia o objeto caso encontre a entidade no banco de dados com o id fornecido
        var account = accountRepository.findById(model.id()).orElseThrow(() -> new AccountNotFoundException(model.id()));


        try{
            // Criptografar a senha que vem da requisição
            var encryptedPassword = new BCryptPasswordEncoder().encode(model.password());

            // Atualiza a entidade Account
            account.setEmailAddress(model.emailAddress());
            account.setProvider(model.provider());
            account.setPasswordHash(encryptedPassword);
            account.setIncomingServer(model.incomingServer());
            account.setIncomingPort(model.incomingPort());
            account.setIncomingProtocol(model.incomingProtocol());
            account.setOutgoingServer(model.outgoingServer());
            account.setOutgoingPort(model.outgoingPort());
            account.setUseSsl(model.useSsl());
            account.setUseTls(model.useTls());
            account.setUpdatedAt(LocalDateTime.now());
            account.setUser(existentUser);

            // Salva a entidade na base de dados
            var savedAccount = accountRepository.save(account);

            // Retorna um novo registro(record) construído a partir da entidade salva no banco
            return new AccountResponse(savedAccount);

        } catch (IllegalArgumentException ex) {
            throw new PasswordEncodingException("Erro ao codificar a senha", ex);
        }catch (DataIntegrityViolationException ex) {
            throw new DatabaseOperationException("Erro ao salvar: dados inválidos ou em conflito.", ex);
        } catch (Exception ex) {
            throw new DatabaseOperationException("Erro inesperado ao salvar.", ex);
        }

    }




    // Deletar uma conta
    public void deleteAccount(Long id) {

        // Instancia o objeto caso encontre a entidade no banco de dados com o id fornecido
        var account = accountRepository.findById(id).orElseThrow(() -> new AccountNotFoundException(id));

        try{

            // Exclui a entidade do banco de dados
            accountRepository.delete(account);

        } catch (DataIntegrityViolationException ex) {
            throw new DatabaseOperationException("Erro ao deletar: pode haver dados relacionados.", ex);
        } catch (Exception ex) {
            throw new DatabaseOperationException("Erro inesperado ao deletar.", ex);
        }

    }



}
