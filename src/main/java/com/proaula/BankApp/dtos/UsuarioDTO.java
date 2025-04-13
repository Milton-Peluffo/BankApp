package com.proaula.BankApp.dtos;

import lombok.Data;

@Data
public class UsuarioDTO {
    private String nombres;
    private String apellidos;
    private String cedula;
    private String correo;
    private String telefono;
    private String pin;
}
