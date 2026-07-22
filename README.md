# 🎮 RigVault

## Rig Setup & Hardware Component Management Core

> **Build it. Organize it. Validate it.**

Un núcleo de software desarrollado en **Java puro** para gestionar, organizar y validar componentes de hardware dentro de configuraciones (*setups*) personalizadas de usuarios.

El proyecto fue diseñado siguiendo un enfoque inspirado en **Domain-Driven Design (DDD)** y **Clean Architecture**, aplicando separación de responsabilidades, inversión de dependencias y desarrollo guiado por pruebas (**TDD**), manteniendo el dominio completamente desacoplado de cualquier framework empresarial.

---

# 🛠 Tecnologías

| Tecnología | Versión | Propósito |
|------------|---------|-----------|
| Java | 25 | Lenguaje de programación |
| Apache Maven | 3.x | Gestión de dependencias y automatización de la construcción |
| JUnit 5 | 5.13.4 | Framework para pruebas unitarias |
| Mockito | 5.20.0 | Mocking y aislamiento de dependencias |
| JaCoCo | 0.8.13 | Cobertura de código |

![Java](https://img.shields.io/badge/Java-25-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Apache Maven](https://img.shields.io/badge/Apache_Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)
![JUnit](https://img.shields.io/badge/JUnit_5-5.13.4-25A162?style=for-the-badge&logo=junit5&logoColor=white)
![Mockito](https://img.shields.io/badge/Mockito-5.20.0-3F51B5?style=for-the-badge)
![JaCoCo](https://img.shields.io/badge/JaCoCo-0.8.13-00BCD4?style=for-the-badge)

> **Nota:** Este proyecto fue desarrollado íntegramente utilizando **Java SE**, sin Spring Boot ni frameworks de inversión de control. El objetivo es implementar manualmente la arquitectura, las reglas de negocio y la estrategia de pruebas antes de evolucionar hacia un ecosistema empresarial.

---

# 📐 Arquitectura

El proyecto implementa una arquitectura inspirada en **Clean Architecture** y **Ports & Adapters (Arquitectura Hexagonal)**, manteniendo el dominio completamente independiente de tecnologías externas.

## Principios aplicados

- Dominio independiente de frameworks.
- Separación de responsabilidades.
- Inversión de dependencias mediante interfaces.
- Reglas de negocio encapsuladas dentro de las entidades.
- Alta testabilidad mediante Mockito.
- Desarrollo guiado por pruebas (**TDD**).

---

# 📂 Estructura del proyecto

```text
src
├── main
│   ├── java
│   │   ├── domain
│   │   │   ├── HardwareComponent.java
│   │   │   ├── RigUser.java
│   │   │   └── Setup.java
│   │   │
│   │   ├── exception
│   │   │   ├── hardwarecomponent
│   │   │   │   ├── DuplicateHardwareComponentException.java
│   │   │   │   ├── HardwareComponentNotFoundException.java
│   │   │   │   ├── InvalidHardwareComponentBrandException.java
│   │   │   │   ├── InvalidHardwareComponentModelException.java
│   │   │   │   ├── InvalidHardwareComponentQuantityException.java
│   │   │   │   └── InvalidHardwareComponentTypeException.java
│   │   │   │
│   │   │   ├── riguser
│   │   │   │   ├── DuplicateRigUserException.java
│   │   │   │   ├── InvalidRigUserEmailException.java
│   │   │   │   ├── InvalidRigUserNameException.java
│   │   │   │   ├── InvalidRigUserPasswordException.java
│   │   │   │   └── RigUserNotFoundException.java
│   │   │   │
│   │   │   └── setup
│   │   │       ├── DuplicateSetupException.java
│   │   │       ├── InvalidSetupDescriptionException.java
│   │   │       ├── InvalidSetupNameException.java
│   │   │       └── SetupNotFoundException.java
│   │   │
│   │   ├── repository
│   │   │   ├── HardwareComponentRepository.java
│   │   │   ├── RigUserRepository.java
│   │   │   └── SetupRepository.java
│   │   │
│   │   └── service
│   │       ├── hardwarecomponent
│   │       │   ├── HardwareComponentService.java
│   │       │   └── HardwareComponentServiceImpl.java
│   │       │
│   │       ├── riguser
│   │       │   ├── RigUserService.java
│   │       │   └── RigUserServiceImpl.java
│   │       │
│   │       └── setup
│   │           ├── SetupService.java
│   │           └── SetupServiceImpl.java
│   │
│   └── resources
│
└── test
    └── java
        ├── hardwarecomponent
        │   ├── TestHardwareComponent.java
        │   ├── TestHardwareComponentRepository.java
        │   └── TestHardwareComponentServiceImpl.java
        │
        ├── riguser
        │   ├── TestRigUser.java
        │   ├── TestRigUserRepository.java
        │   └── TestRigUserServiceImpl.java
        │
        └── setup
            ├── TestSetup.java
            ├── TestSetupRepository.java
            └── TestSetupServiceImpl.java
```

---

# 📖 Descripción de los paquetes

| Paquete | Responsabilidad |
|----------|-----------------|
| **domain** | Entidades del dominio y reglas de negocio. |
| **exception** | Excepciones personalizadas organizadas por contexto de negocio. |
| **repository** | Persistencia en memoria y acceso a datos mediante abstracciones. |
| **service** | Casos de uso y lógica de negocio que coordinan el dominio. |
| **test** | Suite de pruebas unitarias para entidades, repositorios y servicios utilizando JUnit 5 y Mockito. |

---

# ✅ Funcionalidades

- Gestión de usuarios.
- Administración de setups personalizados.
- Gestión de componentes de hardware.
- Validaciones de reglas de negocio.
- Prevención de registros duplicados.
- Manejo de excepciones personalizadas.
- Arquitectura desacoplada mediante interfaces.
- Cobertura de pruebas con JUnit 5, Mockito y JaCoCo.

---

# 🧪 Testing

El proyecto fue desarrollado siguiendo una estrategia **Test Driven Development (TDD)**.

La suite de pruebas cubre:

- Entidades del dominio.
- Validaciones de negocio.
- Servicios.
- Repositorios.
- Casos exitosos.
- Casos excepcionales.
- Pruebas parametrizadas.
- Mocking con Mockito.
- Uso de Spies.
- Verificación de interacciones (`verify()`).
- Cobertura de código mediante JaCoCo.

---

# 🚀 Comandos de Ejecución

Ejecute los siguientes comandos desde la raíz del proyecto.

## Ejecutar la suite completa de pruebas

Compila el proyecto, ejecuta todas las pruebas unitarias y genera automáticamente el informe de cobertura de código de **JaCoCo** en formato HTML.

```bash
mvn clean test
```

---

## Generar nuevamente el reporte de cobertura

Genera el reporte HTML de **JaCoCo** utilizando los resultados de la última ejecución de pruebas, sin volver a compilar ni ejecutar la suite de tests.

```bash
mvn jacoco:report
```

---

## Ubicación del reporte

Una vez generado, el informe estará disponible en:

```text
target/site/jacoco/index.html
```

Abra el archivo **index.html** con cualquier navegador para consultar el porcentaje de cobertura, clases cubiertas, ramas ejecutadas y el detalle por paquete.

---

# 🎯 Objetivos de aprendizaje

Este proyecto fue desarrollado para practicar y consolidar conocimientos en:

- Programación Orientada a Objetos (POO).
- Domain-Driven Design (DDD).
- Clean Architecture.
- Inversión de Dependencias (DIP).
- Test Driven Development (TDD).
- JUnit 5.
- Mockito.
- Diseño de APIs desacopladas.
- Validaciones de negocio.
- Manejo de excepciones personalizadas.
- Cobertura de código con JaCoCo.

---

# 🚀 Próximas etapas

RigVault representa el **Core del dominio** de una aplicación más amplia.

La siguiente evolución del proyecto contempla la migración hacia un ecosistema empresarial basado en:

- Spring Boot
- Spring Data JPA
- Hibernate
- PostgreSQL
- Spring Security
- Swagger / OpenAPI
- Docker
- API REST
- Autenticación mediante JWT
- Frontend con React + TypeScript