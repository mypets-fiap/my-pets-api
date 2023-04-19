package br.com.fiap.mypets.domain.interfaces;

import br.com.fiap.mypets.domain.model.AuthenticationRequest;
import br.com.fiap.mypets.domain.model.AuthenticationResponse;
import br.com.fiap.mypets.domain.model.RegisterRequest;
import br.com.fiap.mypets.domain.model.entity.User;

public interface AuthenticationService {
    AuthenticationResponse register(RegisterRequest request) throws Exception;
    AuthenticationResponse authenticate(AuthenticationRequest request);
    User extractUser(String token);
}
