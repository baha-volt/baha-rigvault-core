# 🎮 RigVault

## Rig Setup & Hardware Component Management Core

### Se crea hito 3 a partir de hito 1

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
│   │   └── cl
│   │       └── bahatech
│   │           ├── application
│   │           ├── domain
│   │           │   ├── entity
│   │           │   ├── exception
│   │           │   │   ├── componenttype
│   │           │   │   ├── hardwarecomponent
│   │           │   │   ├── riguser
│   │           │   │   └── setup
│   │           │   ├── repository
│   │           │   └── valueobject
│   │           └── infrastructure
│   │               └── persistence
│   └── resources
└── test
    └── java
        └── cl
            └── bahatech
                ├── application
                │   ├── service
                │   └── usecase
                ├── domain
                │   └── entity
                └── infrastructure
                    └── persistence
```

---

# 📖 Descripción de los paquetes

| Paquete | Responsabilidad |
|----------|-----------------|
| **domain** | Entidades del dominio. Se autovalidan en su constructor y en sus setters: nunca pueden existir en un estado invalido. |
| **exception** | Excepciones personalizadas organizadas por contexto de negocio. |
| **repository** | *Ports* de salida: interfaces que declaran lo que la aplicacion necesita persistir, sin decir como. |
| **infrastructure.persistence** | *Adapters* que implementan esos ports en memoria (`InMemory*Repository`). Es el unico paquete que sabe como se persisten los datos hoy. |
| **service** | Casos de uso: orquestan el dominio y los ports (chequear duplicados, invocar el repository), sin reimplementar validaciones que ya son responsabilidad de la entidad. |
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

# 🧭 Hito 3 — Hacia un dominio DDD real

El Hito 1 dejó una arquitectura en capas (`domain` / `service` / `repository`) con nombres inspirados en DDD, pero sin varias de las garantías que ese vocabulario promete. Este hito corrige los puntos mas importantes sin rehacer el proyecto desde cero.

## Que cambió

**1. Las entidades ahora se autovalidan.**
Antes, `RigUser`, `Setup` y `HardwareComponent` podían construirse (o mutarse via setters) con datos inválidos; la validación solo se disparaba si el `Service` la invocaba explícitamente. Ahora cada constructor y cada setter llama a las invariantes de la propia entidad, así que **un objeto de dominio en memoria siempre es válido** — sin depender de que la capa de aplicación se acuerde de validar. Como consecuencia, los `Service` (`RigUserServiceImpl`, `SetupServiceImpl`, `HardwareComponentServiceImpl`) ya no revalidan campo por campo: solo orquestan (chequear duplicados, delegar al repository).

**2. Nueva entidad `ComponentType`.**
`HardwareComponent.type` dejó de ser un `String` libre ("GPU", "RAM"...) y ahora es una referencia a `ComponentType` (`id`, `name`, `description`), con su propio repository, service y excepciones (`exception/componenttype`). Es un catálogo con identidad y ciclo de vida propios, no un valor repetido en cada componente.

Como demostración de una regla de negocio real (a diferencia de las otras tres, que detectan "duplicado" por colisión de ID — una regla técnica de persistencia), `ComponentTypeServiceImpl` valida la invariante real: no puede haber dos tipos con el mismo `name`.

**3. Los repositorios ahora son Ports.**
`RigUserRepository`, `SetupRepository`, `HardwareComponentRepository` y el nuevo `ComponentTypeRepository` pasaron de ser clases concretas a **interfaces** (ports de salida, en el sentido de Ports & Adapters). Sus implementaciones en memoria (`InMemoryRigUserRepository`, `InMemorySetupRepository`, `InMemoryHardwareComponentRepository`, `InMemoryComponentTypeRepository`) viven ahora en `infrastructure.persistence`, como adapters. Esto deja el terreno preparado para el Hito 4 (Spring Data JPA / PostgreSQL): se podrá agregar un adapter nuevo (`JpaRigUserRepositoryAdapter`, etc.) sin tocar el dominio ni los `Service`.

**4. Custom port, servicios y casos de uso**
Se agrega un custom port a application y caso de uso, haciendo refactorización de servicios para adaptarlo a DDD

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