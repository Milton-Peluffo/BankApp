package com.proaula.BankApp;

import com.proaula.BankApp.user.application.dtos.UsuarioDTO;
import com.proaula.BankApp.user.application.services.UsuarioService;
import com.proaula.BankApp.user.domain.models.Usuario;
import com.proaula.BankApp.user.domain.repositories.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
// Camilo Andres Sehuanes Contreras
class TestRegistroCamilo {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // ✅ Caso 1: Registro exitoso
    @Test
    void testRegistroExitoso() throws Exception {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setCorreo("camilo@test.com");
        dto.setTelefono("3001234567");
        dto.setPin("1234");

        when(usuarioRepository.existsByCorreo(dto.getCorreo())).thenReturn(false);
        when(usuarioRepository.existsByTelefono(dto.getTelefono())).thenReturn(false);

        Usuario usuarioMock = new Usuario();
        usuarioMock.setCorreo(dto.getCorreo());
        usuarioMock.setTelefono(dto.getTelefono());
        usuarioMock.setPin(dto.getPin());

        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioMock);

        Usuario resultado = usuarioService.crearUsuario(dto);

        assertNotNull(resultado);
        assertEquals("camilo@test.com", resultado.getCorreo());
        assertEquals("3001234567", resultado.getTelefono());
        assertEquals("1234", resultado.getPin());
    }

    // ❌ Caso 2: Correo ya registrado
    @Test
    void testRegistroCorreoDuplicado() {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setCorreo("camilo@test.com");
        dto.setTelefono("3001234567");
        dto.setPin("1234");

        when(usuarioRepository.existsByCorreo(dto.getCorreo())).thenReturn(true);

        Exception ex = assertThrows(Exception.class, () -> usuarioService.crearUsuario(dto));
        assertEquals("Correo ya registrado", ex.getMessage());
    }

    // ❌ Caso 3: Teléfono ya registrado
    @Test
    void testRegistroTelefonoDuplicado() {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setCorreo("camilo@test.com");
        dto.setTelefono("3001234567");
        dto.setPin("1234");

        when(usuarioRepository.existsByTelefono(dto.getTelefono())).thenReturn(true);

        Exception ex = assertThrows(Exception.class, () -> usuarioService.crearUsuario(dto));
        assertEquals("Telefono ya registrado", ex.getMessage());
    }

    // ❌ Caso 4: PIN inválido (menos de 4 dígitos)
    @Test
    void testRegistroPinInvalidoCorto() {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setCorreo("nuevo@test.com");
        dto.setTelefono("3009998888");
        dto.setPin("12"); // inválido

        when(usuarioRepository.existsByCorreo(dto.getCorreo())).thenReturn(false);
        when(usuarioRepository.existsByTelefono(dto.getTelefono())).thenReturn(false);

        Exception ex = assertThrows(Exception.class, () -> usuarioService.crearUsuario(dto));
        assertTrue(ex.getMessage().contains("Pin inválido"));
    }

    // ❌ Caso 5: PIN inválido (no es numérico)
    @Test
    void testRegistroPinInvalidoNoNumerico() {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setCorreo("nuevo2@test.com");
        dto.setTelefono("3017776666");
        dto.setPin("ABCD"); // inválido

        when(usuarioRepository.existsByCorreo(dto.getCorreo())).thenReturn(false);
        when(usuarioRepository.existsByTelefono(dto.getTelefono())).thenReturn(false);

        Exception ex = assertThrows(Exception.class, () -> usuarioService.crearUsuario(dto));
        assertTrue(ex.getMessage().contains("Pin inválido"));
    }
}
