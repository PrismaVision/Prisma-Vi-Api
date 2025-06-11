package com.api.prisma_vi.user.auth;


import com.api.prisma_vi.user.auth.login.LoginForm;
import com.api.prisma_vi.user.auth.login.LoginView;
import com.api.prisma_vi.user.auth.register.RegisterForm;
import com.api.prisma_vi.user.auth.register.RegisterService;
import com.api.prisma_vi.infra.security.TokenService;
import com.api.prisma_vi.user.Users;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final TokenService tokenService;

    private final AuthenticationManager authenticationManager;

    private final RegisterService registerService;

    public AuthenticationController(TokenService tokenService, AuthenticationManager authenticationManager, RegisterService registerService) {
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
        this.registerService = registerService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginForm data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((Users) auth.getPrincipal());

        return ResponseEntity.status(HttpStatus.OK).body(new LoginView(token, "Bearer"));
    }


    @PostMapping("/register")
    public  ResponseEntity<?> register(@RequestBody @Valid RegisterForm data){
        return registerService.registerUser(data);
    }

}
