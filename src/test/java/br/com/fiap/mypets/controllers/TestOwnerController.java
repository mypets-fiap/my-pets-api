package br.com.fiap.mypets.controllers;

import br.com.fiap.mypets.entity.OwnerEntity;
import br.com.fiap.mypets.services.OwnerService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultHandler;

import java.net.http.HttpClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OwnerController.class)
public class TestOwnerController {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private OwnerService service;

    @InjectMocks
    private OwnerController controller;

    @Test
    public void getOwnerById() throws Exception{
        OwnerEntity owner = new OwnerEntity();
        owner.setNome("Edu");
        owner.setIdade(29);

        when(service.save(owner)).thenThrow(new Exception(""));

        ResponseEntity response = controller.saveOwner(owner);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(400));

    }

}
