package models.Tokens;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;

public class adminToken extends Token {
    File adminSettings = new File("src/main/java/resources/administrativos.json");


    @Override
    public void initInformation(String matricula) {
        try{
            JSONParser parserAdmin = new JSONParser();
            JSONObject adminInformation = (JSONObject) parserAdmin.parse(new FileReader(adminSettings));
            JSONArray admins = (JSONArray) adminInformation.get("Administrativos");

            for (Object admin : admins){
                JSONObject adminObject = (JSONObject) admin;
                if(adminObject.get("Matricula").equals(matricula)){
                    this.setNombre((String) adminObject.get("Nombre"));
                    this.setEdad(String.valueOf(adminObject.get("Edad")));
                    this.setMatricula((String) adminObject.get("Matricula"));
                    this.setToken("admin");
                }
            }
        }catch (Exception e){
            System.err.println("Error: " + e.getMessage());
        }
    }
}
