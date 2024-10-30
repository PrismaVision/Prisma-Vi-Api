package com.api.prisma_vi.auth;


import com.api.prisma_vi.auth.login.LoginView;
import com.api.prisma_vi.auth.login.LoginForm;
import com.api.prisma_vi.auth.register.RegisterForm;
import com.api.prisma_vi.auth.register.RegisterService;
import com.api.prisma_vi.infra.security.TokenService;
import com.api.prisma_vi.user.Users;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RegisterService registerService;

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
