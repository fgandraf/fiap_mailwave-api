package com.mailwave.api.modules.users;

import com.mailwave.api.exceptions.UserNotFoundException;
import com.mailwave.api.modules.users.dtos.UserResponse;
import com.mailwave.api.modules.users.dtos.UserCreateRequest;
import com.mailwave.api.modules.users.dtos.UserUpdateRequest;
import com.mailwave.api.modules.users.enums.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository repository;

    public UserService(UserRepository userRepository) {
        this.repository = userRepository;
    }

    public UserResponse getByEmail(String email) {
        var user = repository.getByEmail(email);
        if (user == null)
            throw new UserNotFoundException(email);

        return new UserResponse(user);
    }

    public UserResponse create(UserCreateRequest request) {
        var user = new User(
                null,
                request.email(),
                new BCryptPasswordEncoder().encode(request.password()),
                false,
                UserRole.USER,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        return new UserResponse(repository.save(user));
    }

    public UserResponse update(UserUpdateRequest request) {
        var user = repository.findById(request.id()).orElseThrow(() -> new UserNotFoundException(request.id()));
        user.setEmail(request.email());
        user.setPasswordHash(new BCryptPasswordEncoder().encode(request.password()));
        user.setUpdatedAt(LocalDateTime.now());

        return new UserResponse(repository.save(user));
    }

    public UserResponse activate(Long id) {
        var user = repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        user.setActive(true);
        return new UserResponse(repository.save(user));
    }

    public UserResponse deactivate(Long id) {
        var user = repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        user.setActive(false);
        return new UserResponse(repository.save(user));
    }

    public UserResponse getById(Long id) {
        var user = repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return new UserResponse(user);
    }

    public UserResponse upgradePermission(Long id) {
        var user = repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        user.setRole(UserRole.ADMIN);
        return new UserResponse(repository.save(user));
    }

    public UserResponse downgradePermission(Long id) {
        var user = repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        user.setRole(UserRole.USER);
        return new UserResponse(repository.save(user));
    }

    public Page<UserResponse> getAll(Pageable page) {
        return repository.findAll(page).map(UserResponse::new);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByEmail(username);
    }

}