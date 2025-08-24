package com.proaula.BankApp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MoisesTests {

    // Validaciones auxiliares

    // Nombre y Apellido: letras solamente, no puede ser vacío,
    // no puede ser solo números, no caracteres especiales ni espacios
    private boolean ValidacionNombreYApellido(String texto) {
        if (texto == null) return false;
        if (texto.isEmpty()) return false;
        if (texto.matches("\\d+")) return false;
        return texto.matches("^[A-Za-z][a-zA-Z]*$");
    }

    // Cédula: solo números, máximo 10 dígitos
    private boolean ValidacionCedula(String cedula) {
        if (cedula == null) return false;
        if (cedula.isEmpty()) return false;
        return cedula.matches("^\\d{1,10}$");
    }

    // Tests
    //=================================

    @Test
    void TestNombres() {
        System.out.println("=== TEST NOMBRE ===");
        assertTrue(ValidacionNombreYApellido("Moises")); System.out.println(" Válido: Moises");
        assertTrue(ValidacionNombreYApellido("moises")); System.out.println(" Válido: moises (empieza minúscula)");
        assertFalse(ValidacionNombreYApellido("12345")); System.out.println(" Inválido: solo números");
        assertFalse(ValidacionNombreYApellido("Moisés#")); System.out.println(" Inválido: acento y simbolos");
        assertFalse(ValidacionNombreYApellido("Moises1")); System.out.println(" Inválido: contiene número");
        assertFalse(ValidacionNombreYApellido("")); System.out.println(" Inválido: esta vacío");
    }

    @Test
    void TestApellidos() {
        System.out.println("=== TEST APELLIDO ===");
        assertTrue(ValidacionNombreYApellido("Salas")); System.out.println(" Válido: Salas");
        assertTrue(ValidacionNombreYApellido("salas")); System.out.println(" Válido: salas (empieza minúscula)");
        assertFalse(ValidacionNombreYApellido("12345")); System.out.println(" Inválido: solo números");
        assertFalse(ValidacionNombreYApellido("Salas-")); System.out.println(" Inválido: símbolo no permitido");
        assertFalse(ValidacionNombreYApellido("Salas1")); System.out.println(" Inválido: contiene número");
        assertFalse(ValidacionNombreYApellido("")); System.out.println(" Inválido: esta vacío");
    }

    @Test
    void TestCedula() {
        System.out.println("=== TEST CÉDULA ===");
        assertTrue(ValidacionCedula("1234567890")); System.out.println(" Válido: 10 dígitos");
        assertTrue(ValidacionCedula("12345")); System.out.println(" Válido: menos de 10 dígitos");
        assertFalse(ValidacionCedula("12345678901")); System.out.println(" Inválido: más de 10 dígitos");
        assertFalse(ValidacionCedula("12345a")); System.out.println(" Inválido: contiene letra");
        assertFalse(ValidacionCedula("")); System.out.println(" Inválido: vacío");
        assertFalse(ValidacionCedula(null)); System.out.println(" Inválido: null");
    }
}
