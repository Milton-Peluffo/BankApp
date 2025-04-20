package com.proaula.BankApp.core.models.repositories;

import com.proaula.BankApp.core.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    boolean existsByCorreo(String correo);
    boolean existsByCedula(String cedula);
    boolean existsByTelefono(String telefono);
    Usuario findByTelefono(String telefono);
}
