# Climalert

Servicio autónomo (sin interfaz gráfica) desarrollado con **Spring Boot 4.1.0 / Java 21** que
monitorea condiciones climáticas a través de [WeatherAPI](https://www.weatherapi.com/) y envía
alertas por correo electrónico cuando detecta condiciones críticas.

## Contexto

Climalert se conecta periódicamente a un proveedor meteorológico externo, procesa los datos
recibidos y notifica por correo electrónico a las entidades correspondientes cuando se detectan
condiciones climáticas peligrosas o inusuales. En esta primera iteración se considera "alerta" a:

> **temperatura > 35°C y humedad > 60%**

## Funcionalidad

1. **Integración con WeatherAPI** — consulta el endpoint `/current.json` para una ubicación fija
   (Buenos Aires por defecto, configurable).
2. **Obtención y almacenamiento periódico** — cada **5 minutos** obtiene el clima actual y lo
   persiste en una base local (H2) para historial y análisis posterior.
3. **Análisis de alertas** — cada **1 minuto** evalúa el último registro disponible.
4. **Notificación por correo** — si se detecta una condición crítica, envía un mail con el detalle
   completo del clima a:
   - admin@clima.com
   - emergencias@clima.com
   - meteorologia@clima.com

## Arquitectura

```
entrantes/              -> Cron Tasks (equivalentes a Controllers, disparadas por tiempo y no por HTTP)
servicio/               -> Orquestación de Casos de Uso (ClimaService, AlertaService)
dominio/                -> Entidades y VOs, modela las condiciones que define la consigna (cuándo hay alerta, qué se notifica)
salientes/clima/        -> Interfaz saliente ProveedorClimaExterno + adaptador WeatherApiClimaProvider (REST)
salientes/notificacion/ -> Interfaz saliente NotificadorDeAlertas + adaptador EmailNotificadorDeAlertas
datos/                  -> Repositorio JPA (persistencia local del histórico)
dto/                    -> DTOs para deserializar la respuesta de WeatherAPI
config/                 -> Configuración tipada (ClimalertProperties) y beans (RestTemplate)
docs/                   -> Diagramas (clases y secuencia) en formato PlantUML
```

## Requisitos

- Java 21
- Maven (o `./mvnw`, incluido)
- Una API key gratuita de [WeatherAPI](https://www.weatherapi.com/)
- Una cuenta de correo SMTP (ej. Gmail con contraseña de aplicación) para el envío de alertas

## Configuración

Antes de correr el sistema, completar por **variables de entorno** (nunca hardcodeadas en el
`.properties`):

| Variable | Descripción |
|---|---|
| `WEATHER_API_KEY` | API key de WeatherAPI |
| `MAIL_USERNAME` | Usuario / remitente del correo |
| `MAIL_PASSWORD` | Contraseña de aplicación del correo |

Los umbrales de alerta (35°/60%), la ubicación (Buenos Aires) y los destinatarios ya están
precargados según la consigna, en `src/main/resources/application.properties`.

## Ejecución

```bash
./mvnw spring-boot:run
```

El sistema, una vez levantado:

1. Cada 5 minutos consulta WeatherAPI (`/current.json`) para la ubicación configurada y guarda el
   resultado en la base local (H2, archivo `./data/climalert`).
2. Cada 1 minuto analiza el último registro guardado; si supera los umbrales (temp > 35° **y**
   humedad > 60%), genera una alerta.
3. Ante una alerta, envía un correo con el detalle completo del clima a los 3 destinatarios.

### Opción A — Correr desde terminal (bash / Git Bash)

Creá un archivo `env.sh` (nunca lo subas al repo, agregalo a `.gitignore`) con:

```bash
export WEATHER_API_KEY=tu_api_key
export MAIL_USERNAME=tu_correo@gmail.com
export MAIL_PASSWORD=tu_app_password
```

> ⚠️ El `export` en cada línea es obligatorio. Sin él, la variable solo existe en la
> terminal actual y **no se propaga** al proceso de Maven/Java, lo que produce el error
> `Could not resolve placeholder 'WEATHER_API_KEY'`.

Y en la misma sesión de terminal (el `source` y el `run` deben ejecutarse sin cerrarla entre medio):

```bash
source env.sh
./mvnw spring-boot:run
```

### Opción B — Correr desde el botón ▶ de IntelliJ

IntelliJ lanza un proceso propio que **no hereda** las variables exportadas en tu terminal, así que
hay que cargarlas directamente en la Run Configuration:

`Run → Edit Configurations... → ClimalertApplication → Environment variables` y agregar:

```
WEATHERAPI_KEY=tu_api_key;MAIL_USERNAME=tu_correo@gmail.com;MAIL_PASSWORD=tu_app_password
```

La base de datos H2 se persiste en `./data/climalert.mv.db`. La consola H2 queda disponible en
`http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:file:./data/climalert`).

## Tests

Se agregaron tests unitarios que corren sin necesidad de red, base de datos real ni servidor SMTP
(se mockean los puertos salientes):

| Test | Valida                                                                                                                         |
|---|--------------------------------------------------------------------------------------------------------------------------------|
| `ClimaRegistradoTest` | La condición `esCondicionDeAlerta` (temp > 35° **y** humedad > 60%, distintas combinaciones y el caso límite).                 |
| `ClimaServiceTest` | El CU "obtener y registrar clima" llame al `ProveedorClimaExterno`, traduzca bien los datos y los persista vía el repositorio. |
| `AlertaServiceTest` | El CU "analizar y notificar" dispare la notificación solo cuando corresponde, y no falle si todavía no hay datos registrados.  |
| `WeatherApiClimaProviderTest` | El adaptador REST interprete correctamente una respuesta simulada de WeatherAPI (`MockRestServiceServer`, sin llamada real).   |

```bash
./mvnw test
```

## Diagramas

En `docs/` hay tres diagramas PlantUML:
- `diagramaDeClasesClimalert.puml` — capas y cómo se conectan mediante interfaces (Inversión de
  Dependencias).
- `diagramaObtencionClima.puml` — flujo de "obtener y registrar clima" (cada 5 min).
- `diagramaAnalisisAlertas.puml` — flujo de "analizar y notificar alerta" (cada 1 min).

## Decisiones de diseño

Documentación con Decisiones de Diseño: [Climalert - Decisiones de Diseño](https://docs.google.com/document/d/1SQYV1YeR2NslzvBcHb2hEcU2xrR7U1dYQtngTP0u0Mg/edit?usp=sharing)

---
## Autor
Lujan Rodriguez, Angie Nicole