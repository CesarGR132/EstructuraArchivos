package models;

import java.util.HashMap;


public class Subject {
    HashMap<String, Double> gradesTemplate = new HashMap<String, Double>(){{
            put("Primer Parcial",  null);
            put("Segundo Parcial", null);
            put("Tercer Parcial",  null);
        }};

    private String nombreMateria;
    private HashMap<String, Double> grades = gradesTemplate;


    public Subject() {
    }

    public Subject(String nombreMateria, HashMap<String, Double> grades) {
        this.nombreMateria = nombreMateria;
        this.grades = grades;
    }

    public String getNombreMateria() {
        return nombreMateria;
    }

    public void setNombreMateria(String nombreMateria) {
        this.nombreMateria = nombreMateria;
    }

    public HashMap<String, Double> getGrades() {
        return grades;
    }

    public void setGrades(HashMap<String, Double> grades) {
        this.grades = grades;
    }
}
