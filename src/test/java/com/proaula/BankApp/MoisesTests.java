package com.proaula.BankApp;

import com.proaula.BankApp.user.application.dtos.UsuarioDTO;
import com.proaula.BankApp.user.application.services.UsuarioService;
import com.proaula.BankApp.user.domain.models.Usuario;
import com.proaula.BankApp.user.domain.repositories.UsuarioRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class MoisesTests {

    private UsuarioRepository usuarioRepository;
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {

        usuarioRepository = mock(UsuarioRepository.class);
        usuarioService = new UsuarioService();


        try {
            var field = UsuarioService.class.getDeclaredField("usuarioRepository");
            field.setAccessible(true);
            field.set(usuarioService, usuarioRepository);
        } catch (Exception e) {
            throw new RuntimeException("Error al inyectar el mock del repositorio", e);
        }

        when(usuarioRepository.existsByCorreo(any())).thenReturn(false);
        when(usuarioRepository.existsByCedula(any())).thenReturn(false);
        when(usuarioRepository.existsByTelefono(any())).thenReturn(false);

        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> {
            Usuario u = invocation.getArgument(0);
            if (u.getUsuario_id() == null) {
                u.setUsuario_id(1);
            }
            return u;
        });
    }

    // Metodo para crear usuario, evitando las validaciones del correo, telefono y pin
    private UsuarioDTO crearUsuarioDTO(String nombres, String apellidos, String cedula) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setNombres(nombres);
        dto.setApellidos(apellidos);
        dto.setCedula(cedula);
        dto.setCorreo("correo@dominio.com");   // Correo válido para evitar validación
        dto.setTelefono("1234567890");         // Teléfono válido para evitar validación
        dto.setPin("1234");                    // Pin válido para evitar validación
        return dto;
    }

    @Test
    void testNombreValido() throws Exception {
        UsuarioDTO dto = crearUsuarioDTO("Moises", "Salas", "1234567890");

        Usuario usuario = usuarioService.crearUsuario(dto);
        assertNotNull(usuario);
        assertEquals("Moises", usuario.getNombres());
        assertEquals("Salas", usuario.getApellidos());
        assertEquals("1234567890", usuario.getCedula());
    }

    @Test
    void testApellidoConNumerosInvalido() {
        UsuarioDTO dto = crearUsuarioDTO("Moises", "Salas123", "1234567890");

        Exception ex = assertThrows(Exception.class, () -> usuarioService.crearUsuario(dto));
        assertEquals("Apellido inválido", ex.getMessage());
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    void testNombreVacioInvalido() {
        UsuarioDTO dto = crearUsuarioDTO("", "Salas", "1234567890");

        Exception ex = assertThrows(Exception.class, () -> usuarioService.crearUsuario(dto));
        assertEquals("Nombre inválido", ex.getMessage());
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    void testCedulaValida() throws Exception {
        UsuarioDTO dto = crearUsuarioDTO("Moises", "Salas", "1234567890");

        Usuario usuario = usuarioService.crearUsuario(dto);
        assertNotNull(usuario);
        assertEquals("1234567890", usuario.getCedula());
        verify(usuarioRepository).save(any(Usuario.class));
    }

    @Test
    void testCedulaConLetrasInvalida() {
        UsuarioDTO dto = crearUsuarioDTO("Moises", "Salas", "12345AB890");

        Exception ex = assertThrows(Exception.class, () -> usuarioService.crearUsuario(dto));
        assertEquals("Cédula inválida", ex.getMessage());
        verify(usuarioRepository, never()).save(any());
    }
}