package com.proaula.BankApp;

import com.proaula.BankApp.user.application.dtos.UsuarioDTO;
import com.proaula.BankApp.user.application.services.UsuarioService;
import com.proaula.BankApp.user.domain.models.Usuario;
import com.proaula.BankApp.user.infrastructure.controllers.UsuarioController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class UsuarioControllerTest {

    private UsuarioService usuarioService;
    private UsuarioController usuarioController;

    @BeforeEach
    void setUp() {
        usuarioService = Mockito.mock(UsuarioService.class);
        usuarioController = new UsuarioController();
        // Inyectamos manualmente el mock en el campo privado
        try {
            var field = UsuarioController.class.getDeclaredField("usuarioService");
            field.setAccessible(true);
            field.set(usuarioController, usuarioService);
        } catch (Exception e) {
            throw new RuntimeException("Error al inyectar mock", e);
        }
    }

    private UsuarioDTO crearUsuarioValido() {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setNombres("Milton");
        dto.setApellidos("Peluffo");
        dto.setCedula("1234567890");
        dto.setCorreo("milton@test.com");
        dto.setTelefono("3001234567");
        dto.setPin("1234");
        return dto;
    }

    @Test
    void testCrearUsuarioExitoso() throws Exception {
        UsuarioDTO dto = crearUsuarioValido();
        Usuario usuario = new Usuario();
        usuario.setTelefono(dto.getTelefono());

        when(usuarioService.crearUsuario(any(UsuarioDTO.class))).thenReturn(usuario);

        ResponseEntity<?> response = usuarioController.crearUsuario(dto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(usuario, response.getBody());
    }

    @Test
    void testCrearUsuarioConError() throws Exception {
        UsuarioDTO dto = crearUsuarioValido();

        when(usuarioService.crearUsuario(any(UsuarioDTO.class)))
                .thenThrow(new Exception("Correo inválido"));

        ResponseEntity<?> response = usuarioController.crearUsuario(dto);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Correo inválido", response.getBody());
    }
}
