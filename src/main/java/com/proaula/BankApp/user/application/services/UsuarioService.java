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

        if (dto.getCorreo() == null || !dto.getCorreo().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new Exception("Correo inválido");
        }
        if (dto.getTelefono() == null || !dto.getTelefono().matches("^[0-9]{10}$")) {
            throw new Exception("Teléfono inválido: debe tener 10 dígitos");
        }
        if (dto.getPin() == null || !dto.getPin().matches("\\d{4}")) {
            throw new Exception("Pin inválido: debe ser numérico de 4 dígitos");
        }
      
        
      
        if (dto.getNombres() == null || !dto.getNombres().matches("^[a-zA-Z]+$")) {
            throw new Exception("Nombre inválido");
        }
        if (dto.getApellidos() == null || !dto.getApellidos().matches("^[a-zA-Z]+$")) {
            throw new Exception("Apellido inválido");
        }
        if (dto.getCedula() == null || !dto.getCedula().matches("^[0-9]{10,}$")) {
            throw new Exception("Cédula inválida");
        }

         
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
