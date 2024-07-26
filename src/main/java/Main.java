import Repository.*;
import models.Key;
import models.Subject;
import models.Tokens.Token;
import models.Tokens.adminToken;
import models.Tokens.studentToken;
import models.Tokens.teacherToken;

import javax.swing.*;
import java.util.Calendar;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        /*RepositorUnivesity repository = new operations();
        Key key = new Key();
        //cambia las credenciales de los parametros del login usando las credenciales del archivo credenciales.json
        key = repository.login("mary123","jane123");

        printTokes(key);
         */
    }

    public static void printTokes(Key key){
        if(key.getMatricula() != null && key.getToken() != null){
            if(key.getToken().equalsIgnoreCase("alumno")){
                Token student = new studentToken();
                student.initInformation(key.getMatricula());
                System.out.println("Nombre: " + student.getNombre());
                System.out.println("Edad: " + student.getEdad());
                System.out.println("Semestre: " + ((studentToken) student).getSemestre());
                System.out.println("Matricula: " + student.getMatricula());

                for (Subject materia : ((studentToken) student).getMaterias()){
                    System.out.println("Materia: " + materia.getNombreMateria());
                    for (HashMap.Entry<String, Double> entry : materia.getGrades().entrySet()) {
                        System.out.println(entry.getKey() + " : " + entry.getValue());
                    }
                }
            }else if (key.getToken().equalsIgnoreCase("maestro")) {
                Token teacher = new teacherToken();
                teacher.initInformation(key.getMatricula());

                System.out.println("Nombre: " + teacher.getNombre());
                System.out.println("Edad: " + teacher.getEdad());
                System.out.println("Matricula: " + teacher.getMatricula());

                for (String materia : ((teacherToken) teacher).getMateriasImpartidas()){
                    System.out.println("Materia: " + materia);
                }
            }else if(key.getToken().equalsIgnoreCase("admin")){
                Token admin = new adminToken();
                admin.initInformation(key.getMatricula());
                System.out.println("Nombre: " + admin.getNombre());
                System.out.println("Edad: " + admin.getEdad());
                System.out.println("Matricula: " + admin.getMatricula());
                System.out.println("Token: " + admin.getToken());
            }
        }else{
            JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos");
        }
    }
}