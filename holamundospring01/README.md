# CRUD de pizzería

API Spring Boot con Java 21, Gradle y MariaDB. Basada en la referencia de clase:
https://github.com/vmartinez1984/javaSpring_pizza

Cada recurso tiene DTO de entrada, controlador, servicio con transacciones y repositorio JPA.

Las clases se organizan dentro de `src/main/java/xyz/diego/holamundospring01/`:

- `controllers/`: endpoints HTTP de los cuatro recursos.
- `dtos/`: datos de entrada y sus validaciones.
- `entities/`: entidades y columnas de las tablas JPA.
- `businessLayer/`: reglas de negocio y transacciones; clases como `PizzasBusinessLayer`, siguiendo la estructura de clase.
- `repositories/`: acceso a la base de datos.

La clase principal y `ServletInitializer` permanecen en el paquete raíz, desde donde Spring descubre los subpaquetes.

## Preparación

1. Inicia MariaDB y ejecuta `database/schema.sql`, por ejemplo:
   ```bash
   mariadb -h 127.0.0.1 -P 3306 -u root -p < database/schema.sql
   ```
   El script crea la base y las tablas que falten. Si ya tienes tablas, compara sus columnas con el script: `IF NOT EXISTS` no modifica tablas existentes.
2. Revisa URL y usuario en `src/main/resources/application.properties`. Configura la contraseña mediante la variable de entorno `SPRING_DATASOURCE_PASSWORD`. Para ejecutar desde VS Code puedes definirla en el archivo `.env` de la raíz del espacio de trabajo (excluido de Git).
3. Desde esta carpeta ejecuta:
   ```bash
   SPRING_DATASOURCE_PASSWORD=tu_contrasena ./gradlew bootRun
   ```
4. Abre Swagger: http://localhost:8080/swagger-ui/index.html

## Operaciones

Las rutas base son `/api/pizzas`, `/api/tamanos`, `/api/bebidas` y `/api/clientes`.
También están disponibles con el prefijo `/api/v1/` usado en la referencia.

| Método | Ruta | Resultado |
| --- | --- | --- |
| GET | ruta base | Lista de registros activos |
| GET | ruta base/{id} | Consulta un registro |
| POST | ruta base | Crea un registro; responde 201 con el registro y Location |
| PUT | ruta base/{id} | Actualiza todos sus campos |
| DELETE | ruta base/{id} | Desactiva el registro; responde 204 |

Los registros inexistentes o desactivados responden 404. Los datos inválidos responden 400.
El borrado es lógico (`activo=false`), siguiendo la propuesta del profesor. El servidor genera el ID y asigna `activo=true` al crear.

### Pizzas, tamaños y bebidas

Envía JSON con los siguientes campos. Nombre y descripción son obligatorios y el precio no puede ser negativo.
Los tamaños se manejan como un catálogo independiente con su propio precio; no se han definido relaciones con pizzas.

```json
{"nombre":"Hawaiana","descripcion":"Jamón, piña y queso","precio":120}
```

Ejemplo para crear una pizza:

```bash
curl -i -X POST http://localhost:8080/api/pizzas \
  -H 'Content-Type: application/json' \
  -d '{"nombre":"Hawaiana","descripcion":"Jamón, piña y queso","precio":120}'
```

Para tamaños puedes usar `Chica`, `Mediana` o `Grande`; para bebidas, `Agua` o `Refresco`.

### Clientes

Nombre, teléfono, correo válido y dirección son obligatorios:

```json
{"nombre":"Ana Pérez","telefono":"5512345678","correo":"ana@example.com","direccion":"Calle 1"}
```

## Verificación

```bash
./gradlew test bootWar
```

Las pruebas usan H2 en memoria y no requieren MariaDB. Comprueban creación, consulta, actualización, borrado lógico, validación y respuestas 404 en los cuatro recursos mediante peticiones HTTP reales.
El WAR se genera en `build/libs/`. La conexión real con MariaDB se configura por separado.
