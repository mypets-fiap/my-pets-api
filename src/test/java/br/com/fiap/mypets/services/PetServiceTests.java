package br.com.fiap.mypets.services;


import br.com.fiap.mypets.domain.model.PetResponse;
import br.com.fiap.mypets.domain.model.entity.PetEntity;
import br.com.fiap.mypets.domain.model.entity.User;
import br.com.fiap.mypets.repository.PetRepository;
import br.com.fiap.mypets.repository.UserRepository;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PetServiceTests {

    @InjectMocks
    PetService petService = new PetService();

    @Mock
    UserRepository userRepository;
    @Mock
    PetRepository petrepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void criarPetSucessoTest() {
        Optional<User> userOptional = Optional.of(new User("nomeTeste", "sobrenomeTeste","teste@teste.com", "senhaTeste"));
        when(userRepository.findByEmail(anyString())).thenReturn(userOptional);
        when(petrepository.save(any(PetEntity.class))).thenReturn(null);

        PetEntity pet = new PetEntity();
        pet.setNome("rodolfo");
        pet.setRaca("chouchou");

        PetResponse petResponse = petService.save("teste@teste.com", pet);

        assertNotNull(petResponse.getId());
        assertEquals(petResponse.getNome(), "rodolfo");
        assertEquals(petResponse.getRaca(), "chouchou");
        assertEquals(petResponse.getUser().getFirstName(), "nomeTeste");
        assertEquals(petResponse.getUser().getLastName(), "sobrenomeTeste");
        assertEquals(petResponse.getUser().getEmail(), "teste@teste.com");

    }

    @Test
    public void criarPetUsuarioNaoExisteTest() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(petrepository.save(any(PetEntity.class))).thenReturn(null);

        PetEntity pet = new PetEntity();
        pet.setNome("rodolfo");
        pet.setRaca("chouchou");

        NoSuchElementException thrown = assertThrows(NoSuchElementException.class, () -> {
            petService.save("teste@teste.com", pet);
        });
        assertEquals(thrown.getMessage(), "No value present");
    }

    @Test
    public void alterandoPetSucessoTest() {
        Optional<User> userOptional = Optional.of(new User("nomeTeste", "sobrenomeTeste","teste@teste.com", "senhaTeste"));
        when(userRepository.findByEmail(anyString())).thenReturn(userOptional);
        when(petrepository.save(any(PetEntity.class))).thenReturn(null);

        PetEntity pet = new PetEntity();
        pet.setNome("rodolfo");
        pet.setRaca("chouchou");
        pet.setId("7c2e7f05-8a7c-461e-bf97-f676eb57d5a3");

        PetResponse petResponse = petService.save("teste@teste.com", pet);

        assertEquals(petResponse.getId(), "7c2e7f05-8a7c-461e-bf97-f676eb57d5a3");
        assertEquals(petResponse.getNome(), "rodolfo");
        assertEquals(petResponse.getRaca(), "chouchou");
        assertEquals(petResponse.getUser().getFirstName(), "nomeTeste");
        assertEquals(petResponse.getUser().getLastName(), "sobrenomeTeste");
        assertEquals(petResponse.getUser().getEmail(), "teste@teste.com");

    }

    @Test
    public void deletandoPetSucessoTest() {
        petService.delete("7c2e7f05-8a7c-461e-bf97-f676eb57d5a3");
    }

    @Test
    public void buscandoPetSucessoTest() {
        User user = new User("nomeTeste", "sobrenomeTeste","teste@teste.com", "senhaTeste");
        user.setId(1);
        PetEntity pet = new PetEntity();
        pet.setId("7c2e7f05-8a7c-461e-bf97-f676eb57d5a3");
        pet.setNome("rodolfo");
        pet.setRaca("chouchou");
        pet.setUser(user);
        Optional<PetEntity> petOptional = Optional.of(pet);
        when(petrepository.findById(anyString())).thenReturn(petOptional);

        PetResponse petResponse = petService.find("7c2e7f05-8a7c-461e-bf97-f676eb57d5a3");

        assertEquals(petResponse.getId(), "7c2e7f05-8a7c-461e-bf97-f676eb57d5a3");
        assertEquals(petResponse.getNome(), "rodolfo");
        assertEquals(petResponse.getRaca(), "chouchou");
        assertEquals(petResponse.getUser().getId(), 1);
        assertEquals(petResponse.getUser().getFirstName(), "nomeTeste");
        assertEquals(petResponse.getUser().getLastName(), "sobrenomeTeste");
        assertEquals(petResponse.getUser().getEmail(), "teste@teste.com");
    }
}
