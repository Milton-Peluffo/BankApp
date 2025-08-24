package com.proaula.BankApp.user.application.services;

import com.proaula.BankApp.user.application.dtos.LoginDTO;
import com.proaula.BankApp.user.domain.models.Usuario;
import com.proaula.BankApp.user.domain.repositories.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public LoginService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }
    
    // Constructor sin argumentos para compatibilidad con Spring
    public LoginService() {
        this.usuarioRepository = null; // Spring inyectará la dependencia
    }

    private Map<String, Integer> intentosFallidos = new HashMap<>();

    public String login(LoginDTO loginDTO) {
        Usuario usuario = usuarioRepository.findByTelefono(loginDTO.getTelefono());

        if (usuario == null) {
            return "telefono no registrado";
        }

        if (usuario.isBloqueado()) {
            return "cuenta bloqueada";
        }

        int intentos = intentosFallidos.getOrDefault(usuario.getTelefono(), 0);
        if (!usuario.getPin().equals(loginDTO.getPin())) {
            intentos++;

            if (intentos >= 3) {
                usuario.setBloqueado(true);
                usuarioRepository.save(usuario); // guardamos el estado en la BD
                intentosFallidos.remove(usuario.getTelefono());
                return "cuenta bloqueada";
            } else {
                intentosFallidos.put(usuario.getTelefono(), intentos);
                return "telefono o pin incorrecto";
            }
        }

        // login exitoso
        intentosFallidos.remove(usuario.getTelefono());
        return "login exitoso";
    }
}
