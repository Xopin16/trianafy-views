# Trianafy
Una simple App que gestiona canciones.

## Autor
José Ignacio Rivas Fuentes

## Descripcion
Es una app creada para la gestión de canciones en las que el usuario podrá realizar
diferentes gestiones sobre los artistas, las canciones y las playlist.

En cuanto a los artistas, el usuario podra obtenerlos todos, mediante una petición GET que devuelve todos los artistas, obtener uno, mediante una petición GET que recibe un ID y devuelve un artista, agregar un artista, mediante una petición POST escribiendo los datos del artista, editar un artista, mediante una petición PUT que recibe un artista y su ID y edita sus atributos y borrar un artista, que recibe su ID y lo elimina.

En cuanto las canciones el usuario también podrá obtener todas las canciones, mediante una petición GET que devuelve todos las canciones, obtener una canción, mediante una petición GET que recibe un ID y devuelve una canción, agregar una playlist, mediante una petición POST escribiendo los datos de la canción, editar una canción, mediante una petición PUT que recibe una canción y su ID y edita sus atributos y borrar una canción, que recibe su ID y lo elimina.

Y por último en cuanto a las playlist, el usuario podrá obtener todas las playlist, mediante una petición GET que devuelve todas las playlist, obtener una playlist, mediante una petición GET que recibe un ID y devuelve una playlist, agregar una playlist, mediante una petición POST escribiendo los datos de la playlist, editar una canción, mediante una petición PUT que recibe una playlist y su ID y edita sus atributos y borrar una playlist, que recibe su ID y la elimina. 

Además de realizar estas cinco gestiones básicas sobre las playlist, el usuario podrá obtener todas las canciones de la playlist, que recibe el ID de la playlist y devuelve sus canciones, obtener una canción de esta lista, que recibe los IDs de la lista y la canción y la devuelve,
agregar una canción que ya existe a la playlist, mediante una petición POST que recibe los IDs de la playlist y la canción y la devuelve, y borrar una canción de la lista, que recibe los IDs de la playlist y la canción.

Las peticiones GET, mandarán un 404 NOT FOUND si no encuentra nada y un 200 si se ha realizado con éxito.
Las peticiones POST, mándaran un 400 BAD REQUEST si hay algún error en los datos y un 201 si
se ha realizado con éxito 
Las peticiones PUT, mándaran un 400 BAD REQUEST si hay algún error en los datos,  un 201 si
se ha realizado con éxito y un 404 si no encuentra nada.
Las peticiones DELETE mandarán un 204 NO CONTENT si se realiza correctamente.


## Tecnología
Esta API REST está desarrollada con Spring Boot y escrita el lenguaje Java. 


### ENLACES

Para acceder a la documentación una vez arrancada la app:
http://localhost:8080/swagger-ui.html

