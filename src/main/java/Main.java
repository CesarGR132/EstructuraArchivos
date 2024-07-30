import Repository.*;
import models.Key;
import models.Subject;
import models.Tokens.Token;
import models.Tokens.adminToken;
import models.Tokens.studentToken;
import models.Tokens.teacherToken;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.print.DocFlavor;
import javax.swing.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class Main {
    static File studentSettings = new File("src/main/java/resources/alumnos.json");
    static File teacherSettings = new File("src/main/java/resources/profesores.json");
    public static void main(String[] args) {
        ArrayList<String> students = getStudentsPerSemester("3");
        newSubject(getStudentsPerSemester("3"),"Sistemas digitales", "No asignado", 3);


    }

    public static void newSubject(ArrayList<String> alumnos, String nombreMateria, String Instructor, int semestre){
        try {
            JSONParser parser = new JSONParser();
            JSONObject subjectInformation = (JSONObject) parser.parse(new FileReader("src/main/java/resources/materias.json"));
            JSONArray subjectsArray = (JSONArray) subjectInformation.get("Materias");

            JSONObject newSubject = new JSONObject();
            newSubject.put("Nombre", nombreMateria);
            newSubject.put("Instructor", Instructor);
            newSubject.put("Semestre", semestre);


            JSONArray alumnosArray = new JSONArray();
            JSONObject alumnosObject = new JSONObject();
            for (String alumno : alumnos) {
                JSONObject grades = new JSONObject();
                grades.put("Primer Parcial", 0);
                grades.put("Segundo Parcial", 0);
                grades.put("Tercer Parcial", 0);
                alumnosObject.put(alumno, grades);
            }
            alumnosArray.add(alumnosObject);
            newSubject.put("Alumnos", alumnosArray);

            subjectsArray.add(newSubject);

            try (FileWriter file = new FileWriter("src/main/java/resources/materias.json")) {
                file.write(subjectInformation.toJSONString());
                file.flush();
            }
            System.out.println("Materia añadida exitosamente.");
        } catch (Exception e) {
            System.err.println("Error in newSubject --> " + e.getMessage());
        }
    }

    public static void addSubjectToTeacher(String nombreMateria, String matricula) {
        try {
            JSONParser parser = new JSONParser();
            JSONObject teacherInformation = (JSONObject) parser.parse(new FileReader(teacherSettings));
            JSONArray teachersArray = (JSONArray) teacherInformation.get("Docentes");

            for (Object teacher : teachersArray) {
                JSONObject teacherObject = (JSONObject) teacher;
                if (teacherObject.get("Matricula").equals(matricula)) {
                    JSONObject materias = (JSONObject) teacherObject.get("Materias");
                    int newKey = materias.size();
                    materias.put(String.valueOf(newKey), nombreMateria);
                    break;
                }
            }

            try (FileWriter file = new FileWriter(teacherSettings)) {
                file.write(teacherInformation.toJSONString());
                file.flush();
            }
            System.out.println("Subject added successfully.");
        } catch (Exception e) {
            System.err.println("Error in addSubjectToTeacher --> " + e.getMessage());
        }
    }


    public static ArrayList<String> getStudentsPerSemester(String semester){
        ArrayList<String> students = new ArrayList<>();
        try {
            JSONParser parser = new JSONParser();
            JSONObject studentInformation = (JSONObject) parser.parse(new FileReader(studentSettings));
            JSONArray studentsArray = (JSONArray) studentInformation.get("Estudiantes");

            for (Object student : studentsArray) {
                JSONObject studentObject = (JSONObject) student;
                String semestre = String.valueOf(studentObject.get("Semestre"));
                if (semestre.equals(semester)) {
                    students.add((String) studentObject.get("Nombre"));
                }
            }
        }catch (Exception e){
            System.out.println("Error inside getStudentsPerSemester --> " + e.getMessage());
        }
        return students;
    }

}

