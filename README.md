# Proyecto Servidores (DAW)
# Hugo Jiménez Martín

# Indice

1. [Introducción](#introducción)
   - [Descripción](#descripción)
   - [Objetivos](#objetivos)
2. [Métodos Utilizados](#métodos-utilizados)
   - [Project](#project)
   - [Developers](#developers)
   - [Technologies](#technologies)
3. [Tecnologías Utilizadas](#tecnologías-utilizadas)
   - [Framework](#framework)
4. [Guía de uso](#guía-de-uso)
5. [Conclusión](#conclusión)


# Introdución

## Descripción

- Esta API esta formado por un Proyecto en el cual esta enlazado a un Status, además de va a tener listas de Developers
  y Technologies.
  Lo he puesto de tal manera que va cuando se muestre el proyecto, en el apartado de *Developers* se muestren: **Id**, **Nombre** y **Apellido** y en el apartado de *Tecnologies* se muestren: **Id**, **Nombre**. 


## Objetivos

- Construir una API con la posibilidad de crear proyectos, además de poder añadirle el estado en el
  que se encuentra. También poder crear desarrolladores y tecnologías, y estas tener la opción de poder agregarlas a los proyectos que sean necesarios. Y como resultado final, mostrar los proyectos con sus atributos, el estatus, los desarrolladores con: **Id**, **Nombre** y **Apellidos** y para las tecnologías: **Id**, **Nombre**.

## Métodos Utilizados

### Project

- getAllProjects: **mostrar todos los proyectos**
- getProjectByPrefix: **mostrar los proyectos que empiecen por la palabra que busque**
- createProject: **crear un proyecto**
- updateProject: **modificar un proyecto**
- deleteProject: **borrar un proyecto**


### Developers

- createDeveloper: **crear un developer**
- deleteDeveloper: **borrar un developer**
- addDeveloperToProject: **agregar un developer a un proyecto**


### Technologies

- createTechnologies: **crear una technology**
- deleteTechnologies: **borrar una technology**
- addTechnologieToProject: **agregar una technology a un proyecto**


## Tecnologías Utilizadas

- **Java**

### Framework

- **SpringBoot**


## Guía de uso

- Los primeros pasos son crear un proyecto, desarrollador y tecnología. Al crear el proyecto pones
  los atributos que tiene la clase **Project** y al final, añadir el *statusId*, para aclarar en que estado va a estar. En **Developers** y **Technologies**, simplemente poner los atributos que tiene. Para poder agregar los *desarrolladores* o *tecnologías*, hay un método llamado 
  **add...ToProject**, el cual tienes que pasarle el *idProject* y el id (tech o dev).


## Conclusión

- Al desarrollar este proyecto, he ido entendiendo mejor que hace cada cosa y su funcionamiento
  interno, además de comprender el funcionamiento de las tablas intermedias y la solución a algunos errores.
