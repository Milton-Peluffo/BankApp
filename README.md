# BankApp - README

## Descripción

**BankApp** es una aplicación en desarrollo realizada en **Java 23** que simula un sistema bancario básico. En esta versión, se han implementado pruebas unitarias utilizando **JUnit** para validar el funcionamiento de las funciones de **login** y **registro** de usuarios.

### Características del registro:
El formulario de registro incluye los siguientes campos:
- Nombre
- Apellido
- Correo electrónico
- Contraseña (PIN)
- Número de teléfono
- Cédula

## Pruebas Unitarias

Se han implementado pruebas unitarias con **JUnit** para garantizar la correcta validación del **login** y el **registro** de usuarios. Las pruebas están diseñadas para validar los flujos de autenticación y el almacenamiento de la información ingresada por los usuarios.

## Generación de Reportes

Para facilitar la visualización de los resultados de las pruebas unitarias, se ha configurado **Maven** para generar un reporte en formato **HTML**. Este reporte detalla los resultados de las pruebas y se encuentra en la siguiente ruta: `BankApp/target/reports/surefire.html`

