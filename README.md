# Servicio REST para análisis de sentimiento de tweets

*Lee este documento en: [English](README.en.md)*

Aplicación dividida en dos servicios REST que, a partir de ciertos temas, busca mensajes en Twitter donde se hable de estos temas, para posteriormente analizar el sentimiento de cada mensaje.

Así, tenemos un servicio REST encargado de buscar los temas y los mensajes sobre estos, así como de guardarlos en la base de datos, y otro servicio encargado del análisis sentimental de texto.

Los temas son buscados utilizando la herramienta [ArchMS](http://sele.inf.um.es/archms/), de la Universidad de Murcia, donde obtenemos información de HCE (Historia Clínica Electrónica).

Los comentarios sobre temas son buscandos utilizando [Twitter4j](http://twitter4j.org/en/), una librería no oficial de Java, que permite un fácil acceso a la API de Twitter.

El análisis sentimental de texto se ha realizado mediante múltiples algoritmos:

* Basado en un diccionario de palabras propio.
* Utilizando la librería de [Apache OpenNLP](https://opennlp.apache.org/).
* Utilizando la librería de [Stanford CoreNLP](https://stanfordnlp.github.io/CoreNLP/).

Ambos servicios funcionan de forma independiente. El servicio de análisis sentimental de texto puede ser fácilmente conectado y utilizado por otros servicios, a través de peticiones a su servicio REST.

El servicio de obtención de comentarios permite el registro de usuarios. Estos usuarios pueden añadir y eliminar favoritos. Estos son personas de las redes sociales, cuyos comentarios tendrán prioridad sobre el resto.

## Comenzando 🚀

Estas instrucciones te permitirán obtener una copia del proyecto en funcionamiento en tu máquina local.

### Pre-requisitos 📋

El proyecto se ha realizado utilizando Java.

Ambos servicios están desplegados sobre Tomcat.

En definitiva:

```bash
Java
Tomcat
```

## Construido con 🛠️

Herramientas utilizadas para la construcción del proyecto

Sistema

* [Maven](https://maven.apache.org/) - Manejador de dependencias
* [ArchMS](http://sele.inf.um.es/archms/) - Herramienta de la Universidad de Murcia, para la obtención de temas

Redes sociales

* [Twitter4j](http://twitter4j.org/en/) - Librería para acceder a Twitter

Algoritmos analizados de sentimiento

* [Apache OpenNLP](https://opennlp.apache.org/) - Librería para el análisis de sentimiento de textos
* [Stanford CoreNLP](https://stanfordnlp.github.io/CoreNLP/) - Librería para el análisis de sentimiento de textos

Bases de datos

* [MySQL](https://www.oracle.com/es/mysql/) - Base de datos para la conexión utilizando ArchMS
* [Neo4j](https://neo4j.com/) - Base de datos para la persistencia de información

Test

* [JUnit](https://junit.org) - Test sobre el código
* [REST-assured](http://rest-assured.io/) - Test sobre servicios REST
* [Swagger](https://swagger.io/) - Interfaz interactiva de los servicios REST

Interfaz

* [HTML5](https://es.wikipedia.org/wiki/HTML) - Interfaz en HTML
* [CSS3](https://www.w3schools.com/css/) - Interfaz decorada con CSS
* [JavaScript](https://www.javascript.com/) - Interfaz usando JS
* [AngularJS](https://angularjs.org/) - Framework de JS

## Autores ✒️

* **José Fernándo Fernández Espín** - *Desarrollo inicial*
* **Diego Valera** - *Desarrollo y completado posterior* - [Di3GO95](https://github.com/Di3GO95/)

## Licencia 📄

Este proyecto está bajo la licencia MIT - mira el archivo [LICENSE.md](LICENSE.md) para detalles.
