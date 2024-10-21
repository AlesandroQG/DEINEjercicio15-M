# Ejercicios 1_5 M - Aeropuertos
## DM2 DEIN 2024-2025
### Alesandro Quirós Gobbato

La estructura del proyecto es la siguiente:
- `src > main`:
    - `java > com.alesandro.ejercicio15m`:
        - `AeropuertosApplication.java`: Clase que lanza la aplicación
        - `controller`:
            - `ActivarDesactivarAvionController.java`: Clase que controla los eventos de la ventana para activar/desactivar aviones
            - `AeropuertosController.java`: Clase que controla los eventos de la ventana principal de la aplicación
            - `AniadirAvionController.java`: Clase que controla los eventos de la ventana que añade aviones
            - `BorrarAvionController.java`: Clase que controla los eventos de la ventana que borra aviones
            - `DatosAeropuertoController.java`: Clase que controla los eventos de la ventana para añadir un aeropuerto o editar uno existente
            - `LoginController.java`: Clase que controla los eventos de la ventana para iniciar sesión
        - `dao`:
            - `DaoAeropuerto.java`: Clase que hace las operaciones con la base de datos del modelo Aeropuerto
            - `DaoAeropuertoPrivado.java`: Clase que hace las operaciones con la base de datos del modelo Aeropuerto Privado
            - `DaoAeropuertoPublico.java`: Clase que hace las operaciones con la base de datos del modelo Aeropuerto Público
            - `DaoAvion.java`: Clase que hace las operaciones con la base de datos del modelo Avión
            - `DaoDireccion.java`: Clase que hace las operaciones con la base de datos del modelo Dirección
            - `DaoUsuario.java`: Clase que hace las operaciones con la base de datos del modelo Usuario
        - `db`:
            - `DBConnect.java`: Clase que se conecta a la base de datos
        - `model`:
            - `Aeropuerto.java`: Clase que define el objeto Aeropuerto
            - `AeropuertoPrivado.java`: Clase que define el objeto Aeropuerto Privado
            - `AeropuertoPublico.java`: Clase que define el objeto Aeropuerto Público
            - `Avion.java`: Clase que define el objeto Avión
            - `Direccion.java`: Clase que define el objeto Dirección
            - `Usuario.java`: Clase que define el objeto Usuario
      - `resources`:
        - `fxml`:
          - `ActivarDesactivarAvion.fxml`: Ventana para activar/desactivar aviones
          - `Aeropuertos.fxml`: Ventana principal de la aplicación
          - `AniadirAvion.fxml`: Ventana para añadir aviones
          - `BorrarAvion.fxml`: Ventana para borrar aviones
          - `DatosAeropuerto.fxml`: Ventana para añadir un nuevo aeropuerto o editar uno existente
          - `Login.fxml`: Ventana para iniciar sesión en la aplicación
        - `images`: Imágenes de la aplicación
        - `style`: Estilos de la aplicación