package models.Tokens;

abstract public class token {
    private String matricula;
    private String nombre;
    private String edad;


    public token() {
    }

    public token(String matricula, String nombre, String edad) {
        this.matricula = matricula;
        this.nombre = nombre;
        this.edad = edad;
    }



    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }
}
