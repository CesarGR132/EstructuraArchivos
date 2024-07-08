package models.Tokens;

import com.formdev.flatlaf.json.Json;
import models.Subject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;


public class studentToken extends Token {
    private int semestre;
    private ArrayList<Subject> materias = new ArrayList<>();

    File studentSettings = new File("src/main/java/resources/alumnos.json");
    File gradeSettings = new File("src/main/java/resources/materias.json");


    public studentToken() {
    }

    public studentToken(int semestre, ArrayList<Subject> materias) {
        this.semestre = semestre;
        this.materias = materias;
    }

    public studentToken(String token, String matricula, String nombre, String edad, int semestre, ArrayList<Subject> materias) {
        super(token, matricula, nombre, edad);
        this.semestre = semestre;
        this.materias = materias;
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

    @Override
    public void initInformation(String matricula){
        try {
            JSONParser parserStudent = new JSONParser();
            JSONObject studentInformation = (JSONObject) parserStudent.parse(new FileReader(studentSettings));
            JSONArray students = (JSONArray) studentInformation.get("Estudiantes");

            for (Object student : students){
                JSONObject studentObject = (JSONObject) student;
                if (studentObject.get("Matricula").equals(matricula)){
                    this.setMatricula((String) studentObject.get("Matricula"));
                    this.setNombre((String) studentObject.get("Nombre"));
                    this.setEdad(String.valueOf(studentObject.get("Edad")));
                    this.setSemestre(Integer.parseInt(String.valueOf(studentObject.get("Semestre"))));
                    this.setGrades(this.getNombre(), this.getSemestre());
                }
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void setGrades(String nombre, int semester){
        // int counter = this.getSemesterSubjects(semester);
        try{
            JSONParser gradesParser = new JSONParser();
            JSONObject gradesInformation = (JSONObject) gradesParser.parse(new FileReader(gradeSettings));
            JSONArray grades = (JSONArray) gradesInformation.get("Materias");

            for (Object grade : grades){
                JSONObject gradeObject = (JSONObject) grade;
                if(String.valueOf(gradeObject.get("Semestre")).equalsIgnoreCase(String.valueOf(semester))){
                    //Setting the name subject
                    Subject studentGrades = new Subject();
                    studentGrades.setNombreMateria(gradeObject.get("Nombre").toString());
                    //Setting the grades
                    JSONArray information = (JSONArray) gradeObject.get("Alumnos");
                    for (Object data : information){
                        JSONObject dataObject = (JSONObject) data;
                        JSONObject student = (JSONObject) dataObject.get(nombre);
                        studentGrades.setGrades("Primer Parcial", ((Number) student.get("Primer Parcial")).doubleValue());
                        studentGrades.setGrades("Segundo Parcial", ((Number) student.get("Segundo Parcial")).doubleValue());
                        studentGrades.setGrades("Tercer Parcial", ((Number) student.get("Tercer Parcial")).doubleValue());
                    }
                    this.materias.add(studentGrades);
                }
            }

        }catch (Exception e){
            System.err.println("Error set grades " + e.getMessage());
        }
    }

    public int getSemesterSubjects(int semester){
        int counter = 0;
        try{
            JSONParser counterParser = new JSONParser();
            JSONObject counterInformation = (JSONObject) counterParser.parse(new FileReader(gradeSettings));
            JSONArray studentData = (JSONArray) counterInformation.get("Materias");

            for(Object data: studentData){
                JSONObject dataObject = (JSONObject) data;
                if((Integer) dataObject.get("Semestre") == semester){
                    counter++;
                }
            }

        }catch (Exception e){
            System.out.println("Error counter subject " + e.getMessage());
        }

        return counter;
    }
}

