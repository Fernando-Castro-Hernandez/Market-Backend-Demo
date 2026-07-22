# Market Backend Demo

API REST de backend para un **mercado / tienda**, que gestiona productos, categorías, clientes y compras. Es un proyecto académico de la materia **Programación Aplicada** (3er cuatrimestre, TecDeSoftware), cuyo objetivo formativo es aprender el desarrollo backend con **Java** y el **ecosistema Spring**.

Cada reto del curso agrega un flujo de negocio completo replicando el mismo patrón por capas de punta a punta.

---

## Stack tecnológico

| Componente | Tecnología |
|---|---|
| Lenguaje | Java 21 |
| Framework | Spring Boot 4.0.6 (Spring Web MVC + Spring Data JPA) |
| Base de datos | PostgreSQL |
| Mapeo objeto-objeto | MapStruct 1.6.3 (procesador de anotaciones) |
| Documentación de API | SpringDoc OpenAPI / Swagger UI 3.0.2 |
| Build | Gradle Wrapper (`gradlew.bat` / `./gradlew`) |

---

## Conceptos que abarca el proyecto

Este proyecto es un recorrido práctico por los pilares del backend con Spring:

- **Spring Boot** — arranque autoconfigurado de la aplicación y servidor embebido.
- **Inyección de dependencias** — `@Autowired` / inyección por constructor para conectar controladores, servicios y repositorios.
- **Arquitectura por capas (estilo puertos y adaptadores)** — separación clara entre **dominio** y **persistencia**.
- **Spring Data JPA** — `CrudRepository` y *query methods* derivados del nombre del método (sin escribir SQL).
- **Mapeo con MapStruct** — traducción automática entre entidades de persistencia y modelos de dominio.
- **Documentación de API con OpenAPI/Swagger** — endpoints anotados y explorables desde el navegador.
- **Modelado JPA avanzado** — llaves primarias compuestas (`@EmbeddedId`), relaciones `@ManyToOne` / `@OneToMany`, persistencia en cascada y `@MapsId`.

---

## Arquitectura

El código sigue una **arquitectura por capas con separación dominio/persistencia**. La regla central que la define:

- El **dominio** está en **inglés** (`Product`, `Category`, `Purchase`, `ProductService`).
- La **persistencia** (entidades JPA + esquema de BD) está en **español** (`Producto`, `Categorias`, `Compra`, columnas como `precio_venta`, `cantidad_stock`, `estado`).
- **MapStruct traduce entre ambos mundos.** Una entidad de persistencia nunca sale hacia los controladores: siempre se convierte primero a su modelo de dominio.

### Flujo de una petición (ejemplo `/products`)

```
web/controller/ProductController      → expone endpoints REST, trabaja con Product (dominio)
        │
domain/service/ProductService         → lógica de negocio (p. ej. verifica existencia antes de borrar)
        │
domain/repository/ProductRepository   → INTERFAZ de dominio (el "puerto")
        │
persistence/ProductoRepository        → IMPLEMENTA la interfaz de dominio (el "adaptador");
        │                                orquesta CRUD + mapper, anotado @Repository
        ├── persistence/crud/ProductoCrudRepository   → Spring Data CrudRepository<Producto, Integer>
        └── persistence/mapper/ProductMapper          → MapStruct: Producto ⇄ Product
```

---

## Endpoints

Flujos end-to-end implementados y probados vía Swagger. Las entidades `Cliente` y `Categorias` no tienen controlador propio todavía: se exponen indirectamente a través de Product y Purchase.

### Product — `/products`

| Método | Ruta | Descripción | Respuestas |
|---|---|---|---|
| `GET` | `/products` | Lista todos los productos | `200` |
| `GET` | `/products/{id}` | Producto por ID | `200`, `404` |
| `GET` | `/products/category/{id}` | Productos de una categoría | `200`, `404` |
| `POST` | `/products` | Crea un producto (cuerpo = `Product`) | `201`, `400` |
| `DELETE` | `/products/{id}` | Elimina un producto si existe | `200`, `404` |

### Purchase — `/purchases`

| Método | Ruta | Descripción | Respuestas |
|---|---|---|---|
| `GET` | `/purchases/all` | Lista todas las compras | `200` |
| `GET` | `/purchases/client/{idClient}` | Compras de un cliente | `200`, `404` |
| `POST` | `/purchases/save` | Registra una compra con sus renglones (persistencia en cascada) | `201`, `400` |

> **Al crear una compra (`POST /purchases/save`) no envíes `purchaseId`:** el id lo genera la base de datos. El `clientId` debe ser un cliente existente y cada `productId` un producto existente, o la BD rechaza el `INSERT` por violación de llave foránea.

---

## Puesta en marcha

### Requisitos previos

- **JDK 21**
- **PostgreSQL** corriendo en `localhost:5432` con una base llamada `Market-app-Fer`.

> No hay migraciones (Flyway/Liquibase): el esquema de la BD se asume preexistente y las entidades deben coincidir exactamente con las tablas en español ya creadas.

### Perfiles

| Perfil | Archivo | Puerto | Uso |
|---|---|---|---|
| `dev` (por defecto) | `application-dev.properties` | **8090** | Desarrollo local (PostgreSQL local) |
| `pdn` | `application-pdn.properties` | **80** | Producción |

### Comandos (Gradle Wrapper)

```powershell
# Compilar
.\gradlew.bat build

# Ejecutar la aplicación (perfil dev, puerto 8090)
.\gradlew.bat bootRun

# Ejecutar todos los tests
.\gradlew.bat test

# Compilar sin ejecutar tests
.\gradlew.bat build -x test

# Limpiar artefactos de compilación
.\gradlew.bat clean
```

En Linux/macOS usa `./gradlew` en lugar de `.\gradlew.bat`.

### Documentación interactiva

Con la aplicación corriendo en perfil `dev`, la **Swagger UI** está disponible en:

```
http://localhost:8090/swagger-ui.html
```

Desde ahí puedes explorar y probar todos los endpoints, con ejemplos de cuerpo de petición ya incluidos.

---

## Convenciones para extender el proyecto

Al añadir una nueva entidad de negocio, replica el patrón de punta a punta (ya aplicado en **Product** y **Purchase**):

```
entidad en español (entity)
   → CrudRepository (Spring Data)
   → interfaz de dominio (repository, el "puerto")
   → implementación en persistence (el "adaptador")
   → mapper MapStruct
   → modelo de dominio en inglés
   → servicio
   → controlador REST
```

- El controlador trabaja **solo con el `Service`**; nunca inyecta ni toca directamente el repositorio o las entidades de persistencia.
- Al agregar un campo, **actualiza el mapper** correspondiente o el mapeo se pierde silenciosamente.
- Códigos HTTP: `200 OK` en consultas, `201 CREATED` al crear, `404 NOT FOUND` cuando no existe el recurso.

---

*Proyecto académico — Programación Aplicada, TecDeSoftware.*
