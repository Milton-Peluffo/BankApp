package com.proaula.BankApp.core.models.services;

import com.proaula.BankApp.dtos.LoginDTO;
import com.proaula.BankApp.core.models.Usuario;
import com.proaula.BankApp.core.models.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginService {

    @Autowired
    private UsuarioRepository usuarioRepository;

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
                return "PIN incorrecto";
            }
        }

        // login exitoso
        intentosFallidos.remove(usuario.getTelefono());
        return "login exitoso";
    }
}
