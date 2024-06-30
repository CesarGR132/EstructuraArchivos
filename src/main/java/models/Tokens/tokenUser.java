package models.Tokens;

import models.Subject;

import java.util.ArrayList;

public class tokenUser extends token{
    private String token;
    private int semestre;
    private ArrayList<Subject> materias;


    public tokenUser() {
    }

    public tokenUser(String token, int semestre, ArrayList<Subject> materias) {
        this.token = token;
        this.semestre = semestre;
        this.materias = materias;
    }

    public tokenUser(String matricula, String nombre, String edad, String token, int semestre, ArrayList<Subject> materias) {
        super(matricula, nombre, edad);
        this.token = token;
        this.semestre = semestre;
        this.materias = materias;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getSemestre() {
        return semestre;
    }

    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }

    public ArrayList<Subject> getMaterias() {
        return materias;
    }

    public void setMaterias(ArrayList<Subject> materias) {
        this.materias = materias;
    }
}
