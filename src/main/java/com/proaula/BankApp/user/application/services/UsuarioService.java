package com.proaula.BankApp.user.application.services;

import com.proaula.BankApp.user.application.dtos.UsuarioDTO;
import com.proaula.BankApp.user.domain.models.Usuario;
import com.proaula.BankApp.user.domain.repositories.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario crearUsuario(UsuarioDTO dto) throws Exception {
        if (usuarioRepository.existsByCorreo(dto.getCorreo()))
            throw new Exception("Correo ya registrado");
        if (usuarioRepository.existsByCedula(dto.getCedula()))
            throw new Exception("Cedula ya registrada");
        if (usuarioRepository.existsByTelefono(dto.getTelefono()))
            throw new Exception("Telefono ya registrado");

        Usuario usuario = new Usuario();
        usuario.setNombres(dto.getNombres());
        usuario.setApellidos(dto.getApellidos());
        usuario.setCedula(dto.getCedula());
        usuario.setCorreo(dto.getCorreo());
        usuario.setTelefono(dto.getTelefono());
        usuario.setPin(dto.getPin());

        return usuarioRepository.save(usuario);
    }
}
