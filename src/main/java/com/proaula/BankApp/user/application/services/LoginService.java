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

    public LoginService() {
        this.usuarioRepository = null;
    }

    private Map<String, Integer> intentosFallidos = new HashMap<>();

    public String login(LoginDTO loginDTO) {
        Usuario usuario = usuarioRepository.findByTelefono(loginDTO.getTelefono());


        // ----------------------------- VALIDACIONES DE LOGIN --------------------------

        if (usuario == null) {
            return "telefono no registrado";
        }

        //############# VALIDACIONES DEL PIN ##############

        if (loginDTO.getPin() == null || loginDTO.getPin().trim().isEmpty()) {
            return "el pin no puede estar vacio";
        }

        if (!loginDTO.getPin().matches("^[0-9]+$")) {
            return "el pin solo debe ser numerico";
        }

        if (loginDTO.getPin().length() != 4) {
            return "el pin debe tener exactamente 4 digitos";
        }

        //############# VALIDACIONES DEL TELEFONO ##############

        if (loginDTO.getTelefono() == null || loginDTO.getTelefono().trim().isEmpty()) {
            return "el telefono no puede estar vacio";
        }

        if (!loginDTO.getTelefono().matches("^[0-9]+$")) {
            return "el telefono solo debe ser numerico";
        }

        if (loginDTO.getTelefono().length() != 10) {
            return "el telefono debe tener exactamente 10 digitos";
        }


        //############# VALIDACION/LOGICA DE BLOQUEO DE CUENTA Y ACCESO ##############

        if (usuario.isBloqueado()) {
            return "cuenta bloqueada";
        }

        int intentos = intentosFallidos.getOrDefault(usuario.getTelefono(), 0);
        if (!usuario.getPin().equals(loginDTO.getPin())) {
            intentos++;

            if (intentos >= 3) {
                usuario.setBloqueado(true);
                usuarioRepository.save(usuario);
                intentosFallidos.remove(usuario.getTelefono());
                return "cuenta bloqueada";
            } else {
                intentosFallidos.put(usuario.getTelefono(), intentos);
                return "telefono o pin incorrecto";
            }
        }

        intentosFallidos.remove(usuario.getTelefono());
        return "login exitoso";
    }
}