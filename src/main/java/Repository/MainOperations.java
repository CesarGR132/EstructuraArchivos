package Repository;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.swing.*;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

public class MainOperations implements AdminRepository{

    File subjecSettings = new File("src/main/java/resources/materias.json");
    File teacherSettings = new File("src/main/java/resources/profesores.json");
    File studentSettings = new File("src/main/java/resources/alumnos.json");

    @Override
    public void deleteStudent(String name) {

    }

    @Override
    public void addNewStudent() {

    }

    @Override
    public void updateStudentInformation(String name) {

    }

    @Override
    public void updateStudentGrade(String name) {

    }

    @Override
    public void updateStudentSemester(String name) {

    }

    public void seeStudentDetails(String matricula) {
        String [] dataRow = new String[4];
        try {
            JSONParser parserStudent = new JSONParser();
            JSONObject studentInformation = (JSONObject) parserStudent.parse(new FileReader(studentSettings));
            JSONArray students = (JSONArray) studentInformation.get("Estudiantes");

            detailLoop: for (Object student : students) {
                JSONObject studentObject = (JSONObject) student;
                if (studentObject.get("Matricula").equals(matricula)) {
                    dataRow[0] = (String) studentObject.get("Matricula");
                    dataRow[1] = (String) studentObject.get("Nombre");
                    dataRow[2] = String.valueOf(studentObject.get("Edad"));
                    dataRow[3] = String.valueOf(studentObject.get("Semestre"));

                    JOptionPane.showMessageDialog(null, "Matricula: " + dataRow[0] +
                            "\nNombre: " + dataRow[1] +
                            "\nEdad: " + dataRow[2] +
                            "\nSemestre: " + dataRow[3], "Detalles", JOptionPane.INFORMATION_MESSAGE);

                    break detailLoop;
                }
            }
        }catch (Exception e){
            System.err.println("Error en --> " + e.getMessage());
        }
    }

    @Override
    public void deleteTeacher(String name) {

    }

    @Override
    public void addNewTeacher() {

    }

    @Override
    public void updateTeacherInformation(String name) {

    }

    @Override
    public String[] seeTeacherInformation(String matricula) {
        String [] dataRow = new String[4];
        try{
            JSONParser parserTeacher = new JSONParser();
            JSONObject teacherInformation = (JSONObject) parserTeacher.parse(new FileReader(teacherSettings));
            JSONArray teachers = (JSONArray) teacherInformation.get("Docentes");

            for(Object teacher : teachers){
                JSONObject teacherObject = (JSONObject) teacher;
                if(teacherObject.get("Matricula").equals(matricula)){
                    dataRow[0] = (String) teacherObject.get("Matricula");
                    dataRow[1] = (String) teacherObject.get("Nombre");
                    dataRow[2] = String.valueOf(teacherObject.get("Edad"));
                    JSONObject classes = (JSONObject) teacherObject.get("Materias");
                    for (int i = 0; i < classes.size(); i++){
                        dataRow[3] = (String) classes.get(String.valueOf(i));
                    }
                }
            }

        }catch (Exception e){
            System.err.println("Error en " + e.getMessage());
        }
        return dataRow;
    }

    @Override
    public String generateMatricula(String name, String lastName, String registerYear, String key){
        String matricula = "";
        matricula += name.toUpperCase().charAt(0);
        matricula += name.toUpperCase().charAt(1);
        matricula += registerYear;
        matricula += lastName.toUpperCase().charAt(0);
        matricula += lastName.toUpperCase().charAt(1);
        int random = generateRandomThreeDigitNumber();
        matricula += random;
        matricula += key;
        return matricula;
    }

    @Override
    public void showTeacherDetails(String matricula) {
        try{
            JSONParser parserTeacher = new JSONParser();
            JSONObject teacherInformation = (JSONObject) parserTeacher.parse(new FileReader(teacherSettings));
            JSONArray teachers = (JSONArray) teacherInformation.get("Docentes");

            detailLoop: for(Object obj : teachers){
                JSONObject teacher = (JSONObject) obj;
                if(teacher.get("Matricula").equals(matricula)){
                    ArrayList<String> materias = new ArrayList<>();
                    JSONObject classes = (JSONObject) teacher.get("Materias");
                    for(int i = 0; i < classes.size(); i++){
                        materias.add((String) classes.get(String.valueOf(i)));
                    }
                    String materiasString = String.join(", ", materias);
                    JOptionPane.showMessageDialog(null, "Matricula: " + teacher.get("Matricula") +
                            "\nNombre: " + teacher.get("Nombre") +
                            "\nEdad: " + teacher.get("Edad") +
                            "\nMaterias: " + materiasString, "Detalles", JOptionPane.INFORMATION_MESSAGE);
                    break detailLoop;
                }
            }

        }catch (Exception e){
            System.err.println("Error en " + e.getMessage());
        }
    }

    @Override
    public void showSubjectDetails(String subject) {
        try{
            JSONParser parserSubject = new JSONParser();
            JSONObject subjectInformation = (JSONObject) parserSubject.parse(new FileReader(subjecSettings));
            JSONArray subjects = (JSONArray) subjectInformation.get("Materias");

            detailLoop: for(Object obj : subjects){
                JSONObject subjectObject = (JSONObject) obj;
                if(subjectObject.get("Nombre").equals(subject)){
                    String semestre = subjectObject.get("Semestre").toString();
                    ArrayList<String> studentsRegister = new ArrayList<>();

                    JSONParser studentRegistered = new JSONParser();
                    JSONObject students = (JSONObject) studentRegistered.parse(new FileReader(studentSettings));
                    JSONArray studentsArray = (JSONArray) students.get("Estudiantes");

                    for(Object student : studentsArray){
                        JSONObject studentObject = (JSONObject) student;
                        String auxSemestre = studentObject.get("Semestre").toString();
                        if(auxSemestre.equals(semestre)){
                            studentsRegister.add((String) studentObject.get("Nombre"));
                        }
                    }
                    if(studentsRegister.isEmpty()){
                        studentsRegister.add("No hay alumnos registrados");
                    }
                    String alumnos = String.join(", ", studentsRegister);
                    JOptionPane.showMessageDialog(null, "Nombre: " + subjectObject.get("Nombre") +
                            "\nAlumnos registrados: " + alumnos
                             ,"Detalles", JOptionPane.INFORMATION_MESSAGE);
                    break detailLoop;
                }
            }
        }catch (Exception e){
            System.err.println("Error en  -->" + e.getMessage());
        }
    }

    @Override
    public void addNewAdmin() {

    }

    @Override
    public void updateAdminInformation(String name) {

    }


    public static int generateRandomThreeDigitNumber() {
        Random random = new Random();
        return 100 + random.nextInt(900);
    }
}
