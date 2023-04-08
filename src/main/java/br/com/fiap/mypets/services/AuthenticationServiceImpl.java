package br.com.fiap.mypets.services;

import br.com.fiap.mypets.domain.exception.BadRequestException;
import br.com.fiap.mypets.domain.interfaces.AuthenticationService;
import br.com.fiap.mypets.domain.interfaces.JwtService;
import br.com.fiap.mypets.domain.model.AuthenticationRequest;
import br.com.fiap.mypets.domain.model.AuthenticationResponse;
import br.com.fiap.mypets.domain.model.RegisterRequest;
import br.com.fiap.mypets.domain.model.entity.Token;
import br.com.fiap.mypets.domain.model.entity.User;
import br.com.fiap.mypets.domain.interfaces.repository.TokenRepository;
import br.com.fiap.mypets.domain.interfaces.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    Logger log = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

    @Autowired
    private UserRepository repository;
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) throws Exception {

        if(repository.existsByEmail(request.getEmail())){
         throw new BadRequestException("O Email informado já existe!");
        }

        var user = new User(request.getFirstName(), request.getLastName(), request.getEmail(), passwordEncoder.encode(request.getPassword()));
        var savedUser = repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        saveUserToken(savedUser, jwtToken);
        log.info("Credencial cadastrada com sucesso!");
        return new AuthenticationResponse(jwtToken);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        log.info("Usuário autenticado com sucesso!");
        return new AuthenticationResponse(jwtToken);
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = new Token(user, jwtToken, false, false);
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }
}
