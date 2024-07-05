package models.Tokens;

abstract public class Token {
    private String token;
    private String matricula;
    private String nombre;
    private String edad;


    public Token() {
    }

    public Token(String token, String matricula, String nombre, String edad) {
        this.token = token;
        this.matricula = matricula;
        this.nombre = nombre;
        this.edad = edad;

    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public abstract void initInformation(String matricula);
}
