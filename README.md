# Master Martini Aprende

Aplicación móvil académica para **DSY1105 – Desarrollo de Aplicaciones Móviles**. Su objetivo es ofrecer una forma sencilla de explorar cursos gastronómicos, con contenidos de pastelería, chocolatería y panadería pensados para emprendedores y negocios del rubro.

## Funciones incluidas

- Registro e inicio de sesión con validaciones y mensajes visibles.
- Perfil básico que guarda nombre, correo y tipo de negocio.
- Catálogo de cursos y detalle de cada curso.
- Inscripción local y seguimiento de clases completadas.
- Lecciones con fotografías, materiales y pasos de preparación.
- Video MP4 incluido en la aplicación, reproducido dentro del detalle de una lección.
- Registro del resultado final mediante cámara o selección desde la galería.
- Persistencia local de usuarios, inscripciones, progreso y fotografías con Room.
- Navegación entre registro, inicio, catálogo, cursos inscritos y lecciones.
- Interfaz en tonos crema y cacao, con controles amplios y textos legibles.

La aplicación es un prototipo local: no incluye pagos, servidor ni sincronización entre dispositivos.

## Tecnologías y organización

- **Kotlin** y **Jetpack Compose** para la interfaz.
- **MVVM** para separar pantallas, estado y lógica.
- **Room** para la base de datos local.
- **Navigation Compose** para recorrer las pantallas.
- **Activity Result Contracts** para solicitar la cámara y la galería.

El código se organiza principalmente en:

- `data/local`: entidades, DAO y base de datos Room.
- `model`: modelos de dominio.
- `repository`: acceso a datos y contenido de cursos/lecciones.
- `security`: utilidades relacionadas con las credenciales.
- `ui/screens`: pantallas de la aplicación.
- `ui/components`: componentes visuales compartidos.
- `ui/theme`: colores y tema.
- `viewmodel`: estado y lógica de presentación.

## Cómo ejecutar

1. Abre esta carpeta en Android Studio y espera a que Gradle termine de sincronizar.
2. Selecciona un emulador o conecta un dispositivo Android.
3. Ejecuta la configuración **app**.

El proyecto tiene `minSdk 24`. Para generar el APK de depuración desde Windows:

```powershell
.\gradlew.bat :app:assembleDebug
```

El APK se genera en `app/build/outputs/apk/debug/app-debug.apk`.

## Recorrido de demostración

1. Crea una cuenta con correo, contraseña y tipo de negocio.
2. Inicia sesión y entra a **Explorar cursos**.
3. Abre un curso, inscríbete y entra a sus lecciones.
4. Completa una lección para observar el avance.
5. En la clase de chocolatería con video, reproduce el MP4 incluido.
6. Guarda una fotografía final usando la cámara o la galería.
7. Cierra y vuelve a iniciar sesión para revisar los datos guardados localmente.

## Recursos visuales

Las fotografías y el video de muestra se descargaron de Pexels y se usan bajo su licencia. Los autores, enlaces a cada recurso y licencia están registrados en [media-credits.txt](app/src/main/assets/media-credits.txt). El logo vectorial de la aplicación es una propuesta gráfica propia para este prototipo académico; no representa el logo oficial de Master Martini.

## Pendientes para completar el caso y la evaluación

- Ampliar el perfil para registrar el tamaño del negocio y los intereses de aprendizaje que solicita el caso.
- Probar y demostrar en un dispositivo la captura con cámara, la selección desde galería, el guardado local y la reproducción del video.
- Mantener evidencia de tareas distribuidas, commits del equipo y tablero Trello para la evaluación de trabajo colaborativo.
- Preparar la defensa individual explicando MVVM, Compose, validaciones, Room, recursos nativos y navegación.
