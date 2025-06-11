package com.api.prisma_vi.user.auth.register;

import com.api.prisma_vi.user.UserRole;
import com.api.prisma_vi.user.Users;
import com.api.prisma_vi.user.UsersRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {

    private final UsersRepository repository;

    public RegisterService(UsersRepository repository) {
        this.repository = repository;
    }

    public ResponseEntity<Object> registerUser(RegisterForm data) {

        if (data.nickname().contains(" ") || data.password().contains(" ")) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new RegisterView("Input field can not have spaces"));
        }

        if (repository.existsByEmail(data.email())) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                    .body(new RegisterView("There is already an account linked to this email"));
        }

        return ResponseEntity.ok(saveUser(data));
    }

    private RegisterView saveUser(RegisterForm data) {
        UserRole role = data.email().equals("prisma@prismatic") ? UserRole.ADMIN : UserRole.USER;

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());

        Users user = new Users(
                data.nickname(),
                data.email(),
                encryptedPassword,
                role
        );

        repository.save(user);

        return new RegisterView(data.nickname() + " was registered successfully");
    }
}
