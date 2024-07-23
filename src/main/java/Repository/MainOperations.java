package Repository;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class MainOperations implements AdminRepository{

    File subjecSettings = new File("src/main/java/resources/materias.json");
    File teacherSettings = new File("src/main/java/resources/profesores.json");

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
    public void addNewAdmin() {

    }

    @Override
    public void updateAdminInformation(String name) {

    }
}
