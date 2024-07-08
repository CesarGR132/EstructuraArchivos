package models;

public class Key {
    private String matricula;
    private String token;

    public Key(){

    }

    public Key(String matricula, String token) {
        this.matricula = matricula;
        this.token = token;
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
}
