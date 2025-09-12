# 💼 BankApp - README

#Integrantes de las pruebas unitarias
- Milton Peluffo
- Camilo Sehuanes
- Moises Salas

## 📝 Descripción

**BankApp** es una aplicación en desarrollo realizada en **Java 23** que simula un sistema bancario básico. En esta versión, se han implementado pruebas unitarias utilizando **JUnit** para validar el funcionamiento de las funciones de **login** y **registro** de usuarios.

### 🧾 Características del registro:
El formulario de registro incluye los siguientes campos:
- 🧑 Nombre
- 🧑‍🦰 Apellido
- 📧 Correo electrónico
- 🔐 Contraseña (PIN)
- 📱 Número de teléfono
- 🆔 Cédula

### 🧾 Características del login:
El formulario del login incluye los siguientes campos:
- 📱 Número de teléfono
- 🔐 Contraseña (PIN)

## 🧪 Pruebas Unitarias

Se han implementado pruebas unitarias con **JUnit** para garantizar la correcta validación del **login** y el **registro** de usuarios. Las pruebas están diseñadas para verificar los flujos de autenticación y almacenamiento de datos.

## 📊 Generación de Reportes

Para facilitar la visualización de los resultados de las pruebas unitarias, se ha configurado **Maven** para generar un reporte en formato **HTML**. Este reporte se encuentra en la siguiente ruta:  
`BankApp/test-reports/reports/surefire.html`
