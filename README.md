## Documentación general - Postulación BCI


## Como utilizar la aplicación

El primer paso es compilarla. Ubicado en la raíz del proyecto, se debe ejecutar el siguiente comando:

```
mvn clean package
```
Una vez finalizada la ejecución del build y los tests, se debe generar una instancia local de la misma de la siguiente manera:

```
java -jar target/bci-0.0.1-SNAPSHOT.jar
```

Una vez ejecutado, tendrá disponibles los endpoints para realizar las llamadas y pruebas correspondientes. El controlador principal que los contiene es UserController.java. 

## Endpoints

# GET

```bash
curl --location --request GET 'http://localhost:8080/users' --header 'bci-token: Bearer xxxxxxx'
```

Ejemplo:

```bash
curl --location --request GET 'http://localhost:8080/users' --header 'bci-token: Bearer eyJhbGciOiJIUzUxMiJ9.eyJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwic3ViIjoiR0lHQSIsImlhdCI6MTcwMjEwOTY2OCwiZXhwIjoxNzAyMTI3NjY4fQ.B3VbL_5HSqgMq8gKw7Pdok1Ac0VRGwE_LER94XhV_bXhicoDxj5D5rbxTWK4lGW4VRHcvWaKIT8INrXp0w6POg'
```

Explicación de algunos parámetros y/o headers

```
HEADERS:
bci-token        -> Token recibido al realizar el registro (POST) del usuario.
```

# Código de respuesta satisfactoria:

200 - OK

# Response
```json
{
    "name": "NAHUEL",
    "email": "naahuuu@hotmail.com",
    "password": "$2a$10$s4ozMXdIOyC///6HqrMg1O5WucCkpWGII2HW19T6yhkE0P9fhrw1G",
    "phones": [
        {
            "number": 123456,
            "cityCode": 12,
            "countryCode": 55
        }
    ]
}
```

# POST

```bash
curl --location --request POST 'http://localhost:8080/users' \
--header 'Content-Type: application/json' \
--data-raw 'El json con la data a Cargar'
```

Ejemplo:

```
curl --location --request POST 'http://localhost:8080/users' \
--header 'Content-Type: application/json' \
--data-raw '{"name":"Nahu",
"password":"Nahueasd1-l22!",
"email":"naahuuu@hotmail.com",
"phones":[{
    "number":"123456",
    "cityCode":"12",
    "countryCode":"55"
}]
}'
```

Explicación de algunos parámetros y/o headers

```
BODY:
name       -> Nombre / username del usuario.
email      -> Correo del usuario.
password   -> Password del usuario.
phones    -> Listado de teléfonos del usuario. 
Cada elemento del listado de phones contiene los siguientes campos:
- number      -> Número de teléfono.
- cityCode    -> Código de la ciudad.
- countryCode -> Código del país.
```

# Código de respuesta satisfactoria:

201 - CREATED

# Response
```json
{
    "name": "Nahu",
    "email": "naahuuu@hotmail.com",
    "password": "$2a$10$9.40nvIt/eRNbgzPSad0j.WAFkVNJyiG3pZOHyuOLfOvNN0zc2GTu",
    "phones": [
        {
            "number": 123456,
            "cityCode": 12,
            "countryCode": 55
        }
    ],
    "id": "671a70f3-b6c4-4bda-8de9-6029ea2f825b",
    "created": "2023-12-11T00:34:50.603234-03:00",
    "modified": null,
    "lastLogin": "2023-12-11T00:34:50.603276-03:00",
    "token": "Bearer eyJhbGciOiJIUzUxMiJ9.eyJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwic3ViIjoiTmFodSIsImlhdCI6MTcwMjI2NTY5MCwiZXhwIjoxNzAyMjgzNjkwfQ.lDy6JIQZ8fqnlRt6V30ed52x54GhplRk_CDS3GTYMY5IdT4bCoaIbVodt-md8TPaPvfKLIBx5P9RTPGdi-dKkw",
    "isActive": true
}
```

# PUT

```bash
curl --location --request PUT 'http://localhost:8080/users/<USER_ID>' \
--header 'bci-token: Bearer xxxxxx' \
--header 'Content-Type: application/json' \
--data-raw 'El json con la data que se va a modificar en el usuario con el ID correspondiente'
```

Ejemplo:

```
curl --location --request PUT 'http://localhost:8080/users/671a70f3-b6c4-4bda-8de9-6029ea2f825b' \
--header 'bci-token: Bearer eyJhbGciOiJIUzUxMiJ9.eyJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwic3ViIjoiTmFodSIsImlhdCI6MTcwMjI2NTY5MCwiZXhwIjoxNzAyMjgzNjkwfQ.lDy6JIQZ8fqnlRt6V30ed52x54GhplRk_CDS3GTYMY5IdT4bCoaIbVodt-md8TPaPvfKLIBx5P9RTPGdi-dKkw' \
--header 'Content-Type: application/json' \
--data-raw '{"name":"Nahu",
"password":"Nahuel22!",
"email":"nahuconotromail@hotmail.com",
"phones":[{
    "number":"123456",
    "cityCode":"12",
    "countryCode":"55"
}]
}'
```

Explicación de algunos parámetros y/o headers

```
REQUEST PARAM:
<USER_ID>  -> ID del usuario a modificar.

HEADERS:
bci-token  -> Token recibido al realizar el registro (POST) del usuario.

BODY:
name       -> Nombre / username del usuario.
email      -> Correo del usuario.
password   -> Password del usuario.
phones    -> Listado de teléfonos del usuario. 
Cada elemento del listado de phones contiene los siguientes campos:
- number      -> Número de teléfono.
- cityCode    -> Código de la ciudad.
- countryCode -> Código del país.
```
# Código de respuesta satisfactoria:

200 - OK

# Response
```json
{
    "name": "Nahu",
    "email": "nahuconotromail@hotmail.com",
    "password": "Nahuel22!",
    "phones": [
        {
            "number": 123456,
            "cityCode": 12,
            "countryCode": 55
        }
    ],
    "id": "671a70f3-b6c4-4bda-8de9-6029ea2f825b",
    "created": "2023-12-11T00:34:50.603234-03:00",
    "modified": "2023-12-11T00:40:01.333222-03:00",
    "lastLogin": "2023-12-11T00:34:50.603276-03:00",
    "token": "Bearer eyJhbGciOiJIUzUxMiJ9.eyJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwic3ViIjoiTmFodSIsImlhdCI6MTcwMjI2NTY5MCwiZXhwIjoxNzAyMjgzNjkwfQ.lDy6JIQZ8fqnlRt6V30ed52x54GhplRk_CDS3GTYMY5IdT4bCoaIbVodt-md8TPaPvfKLIBx5P9RTPGdi-dKkw",
    "isActive": true
}
```


# DELETE

```bash
curl --location --request DELETE 'http://localhost:8080/users/<USER_ID>' \
--header 'bci-token: Bearer xxxxx'
```

Ejemplo:

```
curl --location --request DELETE 'http://localhost:8080/users/671a70f3-b6c4-4bda-8de9-6029ea2f825b' \
--header 'bci-token: Bearer eyJhbGciOiJIUzUxMiJ9.eyJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwic3ViIjoiTmFodSIsImlhdCI6MTcwMjI2NTY5MCwiZXhwIjoxNzAyMjgzNjkwfQ.lDy6JIQZ8fqnlRt6V30ed52x54GhplRk_CDS3GTYMY5IdT4bCoaIbVodt-md8TPaPvfKLIBx5P9RTPGdi-dKkw' \
```

Explicación de algunos parámetros y/o headers

```
REQUEST PARAM:
<USER_ID>  -> ID del usuario a modificar.

HEADERS:
bci-token  -> Token recibido al realizar el registro (POST) del usuario.
```

# Código de respuesta satisfactoria:

204 - No content

# Response
```json
Sin response body
```







## Jacoco
* En el momento de realizar el build de la aplicación (el primer comando al comienzo de este documento) se va a generar a la par un reporte de Jacoco, herramienta que se utiliza para visualizar porcentajes de coverage. Estos gráficos y el reporte final se podrán ver en la siguiente ruta una vez finalizado el build de la app: 

![Jacoco](src/main/resources/images/jacoco.png)

```
<Raiz del proyecto>/target/site/jacoco/index.html
```

## Swagger

Se puede acceder a documentación Swagger realizada para la API a través del siguiente link:

```
http://localhost:8080/swagger-ui/index.html
```
