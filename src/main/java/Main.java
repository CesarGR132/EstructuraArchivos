import Repository.*;
import models.Key;
import models.Subject;
import models.Tokens.Token;
import models.Tokens.studentToken;
import models.Tokens.teacherToken;

import javax.swing.*;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        RepositorUnivesity repository = new operations();
        Key key = new Key();

        key = repository.login("cesar123","abc123");

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


            }
        }else{
            JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos");
        }
    }
}