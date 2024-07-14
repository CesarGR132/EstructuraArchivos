# Proyecto Sistema de gestión de escuela

## Objetivo del proyecto

### Desarrollar una aplicación de escritorio utilizando Java Swing, que gestione información relevante de una de las temáticas propuestas, utilizando archivos JSON para el almacenamiento de datos. El proyecto debe incluir un sistema de login para gestionar el acceso a los distintos módulos.


## Alumnos en el equipo:
- *Mayoral Gonzalez Bryan Omar*
- *Mariscal Ruiz César Gabriel*
- *Gonzalez Kevin Alberto*
- *Ponce Aguilera Jessica Lizbeth*



## Avance 1
  El poryecto se ejecuta desde la clase del codigo login, aun no esta implementada la función de no dejar abrir las demas ventanas sin validaciones.
  Las credenciales de inicio de sesion se encuentran dentro del archivo credenciales.json


  La función del login es de que si coincide algun campo con los parametros otorgados, crea un objeto dependiendo el tipo de crendencial que haya sido 
  ingresada, luego dentro del objeto se le asignan toda la informacion de usuario que ha ingresado sesión


  ## Estructura Json crendenciales
    La estructura de este Json es simple dado a que solo es la matricula, usuario, contraseña y tipo de usuario(token)

  ## Administrativos JSON
    Este json cuenta con la misma estructura del anterio pero solo cuenta con tres campos nombre,matricula y edad

  ## Maestros JSON
    Este a diferencia de los otros cuenta con un sub-Objeto dentro el apartado materias que serian todas las materias que estan asignadas
    y cuentas con los campos matricula,edad,nombre,materias.

  ## Estudiantes json
    Es un json simple con los campos matricula,nombre, semestre y edad.

  ## Materias Json
    Un json mas complicado dado a que tiene dos sub objetos, uno que esta dentro de alumnos registrados y dentro de ese objetos con la credencial del alumno
    que tiene sus calificaciones registradas y cuenta con los campos nombre, instructor, semestre, alumnos.

  ## Formularios
    Hasta el momento de estos avances, el unico formulario es el login
      La función del login es de que si coincide algun campo con los parametros otorgados, crea un objeto dependiendo el tipo de crendencial que haya sido 
      ingresada, luego dentro del objeto se le asignan toda la informacion de usuario que ha ingresado sesión

    


