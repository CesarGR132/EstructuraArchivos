package models.Tokens;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class teacherToken extends Token {
    private ArrayList<String> materiasImpartidas = new ArrayList<>();

    File professorSettings = new File("src/main/java/resources/profesores.json");

    public teacherToken() {
    }

    public teacherToken(String token, String matricula, String nombre, String edad) {
        super(token, matricula, nombre, edad);
    }

    public ArrayList<String> getMateriasImpartidas() {
        return materiasImpartidas;
    }

    public void setMateriasImpartidas(ArrayList<String> materiasImpartidas) {
        this.materiasImpartidas = materiasImpartidas;
    }

    @Override
    public void initInformation(String matricula) {
        try{
            JSONParser parserProfessor = new JSONParser();
            JSONObject professorInformation = (JSONObject) parserProfessor.parse(new FileReader(professorSettings));
            JSONArray professors = (JSONArray) professorInformation.get("Docentes");

            for (Object professor : professors){
                JSONObject professorObject = (JSONObject) professor;
                if(professorObject.get("Matricula").equals(matricula)){
                    this.setNombre((String) professorObject.get("Nombre"));
                    this.setEdad(String.valueOf(professorObject.get("Edad")));
                    this.setMatricula((String) professorObject.get("Matricula"));

                    JSONObject classes = (JSONObject) professorObject.get("Materias");
                    for (int i = 0; i < classes.size(); i++){
                        this.materiasImpartidas.add((String) classes.get(String.valueOf(i)));
                    }

                }
            }

        }catch (Exception e){
            System.err.println("Error: " + e.getMessage());
        }
    }
}
