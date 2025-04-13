package com.proaula.BankApp.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer usuario_id;

    private String nombres;
    private String apellidos;
    private String cedula;
    private String correo;
    private String telefono;
    private String pin;
    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean bloqueado = false;

    private LocalDateTime creado_en = LocalDateTime.now();
    private LocalDateTime actualizado_en = LocalDateTime.now();

    @PreUpdate
    protected void onUpdate() {
        actualizado_en = LocalDateTime.now();
    }
}
