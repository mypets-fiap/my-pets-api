package br.com.fiap.mypets.domain.interfaces;

import br.com.fiap.mypets.domain.model.AuthenticationRequest;
import br.com.fiap.mypets.domain.model.AuthenticationResponse;
import br.com.fiap.mypets.domain.model.RegisterRequest;

public interface AuthenticationService {
    AuthenticationResponse register(RegisterRequest request) throws Exception;
    AuthenticationResponse authenticate(AuthenticationRequest request);
}
