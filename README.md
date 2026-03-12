
# Bank Application - Microservicios con Spring Boot

Este proyecto es una aplicación bancaria desarrollada con **Spring Boot** utilizando una arquitectura de **microservicios**.

La aplicación está compuesta por dos servicios principales:

- **Client Service**: encargado de la gestión de clientes.  
  Puerto: **8001**

- **Account Service**: encargado de la gestión de cuentas y transacciones.  
  Puerto: **8000**

El servicio de **Account** se comunica con el servicio de **Client** para validar que el cliente exista antes de crear una cuenta.

Para facilitar las pruebas de los endpoints, se incluye una colección de Postman:

**collection_bank_postman.json**

Ubicación del archivo: /Microservices/BankApplication

