package br.com.fiap.mypets.services;


import br.com.fiap.mypets.domain.exception.UnauthorizedException;
import br.com.fiap.mypets.domain.model.PetResponse;
import br.com.fiap.mypets.domain.model.entity.PetEntity;
import br.com.fiap.mypets.domain.model.entity.User;
import br.com.fiap.mypets.domain.interfaces.repository.PetRepository;
import br.com.fiap.mypets.domain.interfaces.repository.UserRepository;
import org.apache.logging.log4j.util.Strings;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PetServiceTests {

    @InjectMocks
    PetServiceImpl petService;

    @Mock
    UserRepository userRepository;
    @Mock
    PetRepository petRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void deveRetornarSucessoNoCadastroQuandoUsuarioEPetValidos() {
        User user = new User("nomeTeste", "sobrenomeTeste", "teste@teste.com", "senhaTeste");
        when(petRepository.save(any(PetEntity.class))).thenReturn(null);

        PetEntity pet = new PetEntity();
        pet.setNome("rodolfo");
        pet.setRaca("chouchou");

        PetResponse petResponse = petService.save(user, pet);

        assertNotNull(petResponse.getId());
        assertEquals(petResponse.getNome(), "rodolfo");
        assertEquals(petResponse.getRaca(), "chouchou");
        assertEquals(petResponse.getUser().getFirstName(), "nomeTeste");
        assertEquals(petResponse.getUser().getLastName(), "sobrenomeTeste");
        assertEquals(petResponse.getUser().getEmail(), "teste@teste.com");
    }

    @Test
    public void deveRetornarIdDiferenteNoCadastroQuandoUmIdEhEnviado() {
        User user = new User("nomeTeste", "sobrenomeTeste", "teste@teste.com", "senhaTeste");

        when(petRepository.save(any(PetEntity.class))).thenReturn(null);

        PetEntity pet = new PetEntity();
        pet.setNome("rodolfo");
        pet.setRaca("chouchou");
        pet.setId("7c2e7f05-8a7c-461e-bf97-f676eb57d5a3");
        pet.setUser(user);

        PetResponse petResponse = petService.save(user, pet);

        assertNotEquals(petResponse.getId(), "7c2e7f05-8a7c-461e-bf97-f676eb57d5a3");
        assertEquals(petResponse.getNome(), "rodolfo");
        assertEquals(petResponse.getRaca(), "chouchou");
        assertEquals(petResponse.getUser().getFirstName(), "nomeTeste");
        assertEquals(petResponse.getUser().getLastName(), "sobrenomeTeste");
        assertEquals(petResponse.getUser().getEmail(), "teste@teste.com");
    }

    @Test
    public void deveRetornarSucessoNaAlteracaoQuandoOUsuarioEhDonoDoPet() {
        User user = new User("nomeTeste", "sobrenomeTeste", "teste@teste.com", "senhaTeste");
        user.setId(10);

        when(petRepository.save(any(PetEntity.class))).thenReturn(null);

        PetEntity pet = new PetEntity();
        pet.setNome("rodolfo");
        pet.setRaca("chouchou");
        pet.setId("7c2e7f05-8a7c-461e-bf97-f676eb57d5a3");
        pet.setUser(user);

        PetResponse petResponse = petService.update(user, pet);

        assertEquals(petResponse.getId(), "7c2e7f05-8a7c-461e-bf97-f676eb57d5a3");
        assertEquals(petResponse.getNome(), "rodolfo");
        assertEquals(petResponse.getRaca(), "chouchou");
        assertEquals(petResponse.getUser().getFirstName(), "nomeTeste");
        assertEquals(petResponse.getUser().getLastName(), "sobrenomeTeste");
        assertEquals(petResponse.getUser().getEmail(), "teste@teste.com");
    }

    @Test
    public void deveRetornarUnauthorizedExceptionNaAlteracaoQuandoOUserNaoEhDonoDoPet() {
        User user = new User("nomeTeste", "sobrenomeTeste", "teste@teste.com", "senhaTeste");
        user.setId(10);
        User dono = new User("dono", "sobrenomeDono", "dono@teste.com", "senhaDono");
        dono.setId(11);

        PetEntity pet = new PetEntity();
        pet.setNome("rodolfo");
        pet.setRaca("chouchou");
        pet.setId("7c2e7f05-8a7c-461e-bf97-f676eb57d5a3");
        pet.setUser(dono);

        when(petRepository.findById(pet.getId())).thenReturn(Optional.of(pet));

        UnauthorizedException exception = assertThrows(UnauthorizedException.class, () -> {
            PetResponse petResponse = petService.update(user, pet);
        });

        assertTrue(Strings.isNotEmpty(exception.getMessage()));
    }

    @Test
    public void deveRetornarUnauthorizedExceptionNaExclusaoQuandoOUsuarioNaoEhDonoDoPet() {
        User user = new User("nomeTeste", "sobrenomeTeste", "teste@teste.com", "senhaTeste");
        user.setId(10);
        User dono = new User("dono", "sobrenomeDono", "dono@teste.com", "senhaDono");
        dono.setId(11);

        PetEntity pet = new PetEntity();
        pet.setNome("rodolfo");
        pet.setRaca("chouchou");
        pet.setId("7c2e7f05-8a7c-461e-bf97-f676eb57d5a3");
        pet.setUser(dono);

        when(petRepository.findById(pet.getId())).thenReturn(Optional.of(pet));

        UnauthorizedException exception = assertThrows(UnauthorizedException.class, () -> {
            petService.delete(pet.getId(), user);
        });

        assertTrue(Strings.isNotEmpty(exception.getMessage()));
    }

    @Test
    public void deveRetornarSucessoNaExclusaoQuandoOUsuarioEhDonoDoPet() {
        User user = new User("nomeTeste", "sobrenomeTeste", "teste@teste.com", "senhaTeste");
        user.setId(10);

        PetEntity pet = new PetEntity();
        pet.setNome("rodolfo");
        pet.setRaca("chouchou");
        pet.setId("7c2e7f05-8a7c-461e-bf97-f676eb57d5a3");
        pet.setUser(user);

        when(petRepository.findById(pet.getId())).thenReturn(Optional.of(pet));

        petService.delete(pet.getId(), user);

        verify(petRepository, times(1)).findById(pet.getId());
        verify(petRepository, times(1)).deleteById(pet.getId());
    }

    @Test
    public void deveRetornarSucessoNaBuscaDoPetQuandoOUserEhDonoDoPet() {
        User user = new User("nomeTeste", "sobrenomeTeste", "teste@teste.com", "senhaTeste");
        user.setId(10);

        PetEntity pet = new PetEntity();
        pet.setNome("rodolfo");
        pet.setRaca("chouchou");
        pet.setId("7c2e7f05-8a7c-461e-bf97-f676eb57d5a3");
        pet.setUser(user);

        when(petRepository.findById(pet.getId())).thenReturn(Optional.of(pet));

        PetResponse petResponse = petService.find(pet.getId(), user);

        assertEquals(petResponse.getId(), pet.getId());
        assertEquals(petResponse.getNome(), pet.getNome());
        assertEquals(petResponse.getRaca(), pet.getRaca());
        assertEquals(petResponse.getUser().getFirstName(), user.getFirstName());
        assertEquals(petResponse.getUser().getLastName(), user.getLastName());
        assertEquals(petResponse.getUser().getEmail(), user.getEmail());

        verify(petRepository, times(1)).findById(pet.getId());
    }

    @Test
    public void deveRetornarErroNaBuscaDoPetQuandoOUserNaoEhDonoDoPet() {
        User user = new User("nomeTeste", "sobrenomeTeste", "teste@teste.com", "senhaTeste");
        user.setId(10);
        User dono = new User("dono", "sobrenomeDono", "dono@teste.com", "senhaDono");
        dono.setId(11);

        PetEntity pet = new PetEntity();
        pet.setNome("rodolfo");
        pet.setRaca("chouchou");
        pet.setId("7c2e7f05-8a7c-461e-bf97-f676eb57d5a3");
        pet.setUser(dono);

        when(petRepository.findById(pet.getId())).thenReturn(Optional.of(pet));

        UnauthorizedException exception = assertThrows(UnauthorizedException.class, () -> {
            petService.delete(pet.getId(), user);
        });

        verify(petRepository, times(1)).findById(pet.getId());
    }

    @Test
    public void deveRetornarSucessoNaBuscaDeTodosOsPetsDoUser() {
        User user = new User("nomeTeste", "sobrenomeTeste", "teste@teste.com", "senhaTeste");
        user.setId(10);

        PetEntity pet = new PetEntity();
        pet.setNome("rodolfo");
        pet.setRaca("chouchou");
        pet.setId(UUID.randomUUID().toString());
        pet.setUser(user);

        PetEntity pet2 = new PetEntity();
        pet2.setNome("Saori");
        pet2.setRaca("Akita Inu");
        pet2.setId(UUID.randomUUID().toString());
        pet2.setUser(user);

        List<PetEntity> pets = List.of(pet, pet2);

        when(petRepository.findByUser(user)).thenReturn(pets);

        List<PetResponse> response = petService.findByUser(user);

        assertFalse(response.isEmpty());
        assertEquals(2, response.size());

        verify(petRepository, times(1)).findByUser(user);
    }
}
