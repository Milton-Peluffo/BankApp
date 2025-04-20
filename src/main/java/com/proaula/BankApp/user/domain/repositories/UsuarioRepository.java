package com.proaula.BankApp.user.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proaula.BankApp.user.domain.models.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    boolean existsByCorreo(String correo);
    boolean existsByCedula(String cedula);
    boolean existsByTelefono(String telefono);
    Usuario findByTelefono(String telefono);
}
