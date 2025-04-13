package com.proaula.BankApp.repositories;

import com.proaula.BankApp.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    boolean existsByCorreo(String correo);
    boolean existsByCedula(String cedula);
    boolean existsByTelefono(String telefono);
}
