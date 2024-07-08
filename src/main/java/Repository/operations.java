package Repository;

import models.Key;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.swing.*;
import java.io.File;
import java.io.FileReader;

public class operations implements RepositorUnivesity{

    @Override
    public Key login(String username, String password) {
        File credentials = new File("src/main/java/resources/credenciales.json");
        Key tokenKey = new Key();

        try{
            JSONParser parser = new JSONParser();
            JSONObject session = (JSONObject) parser.parse(new FileReader(credentials));
            JSONArray keys = (JSONArray) session.get("credentials");

            for (Object key : keys) {
                JSONObject credential = (JSONObject) key;
                if (credential.get("user").equals(username) && credential.get("password").equals(password)){
                    tokenKey.setToken((String) credential.get("token"));
                    tokenKey.setMatricula((String) credential.get("Matricula"));
                }
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, "Hubo un error", "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println(e.getMessage());
        }

        return tokenKey;
    }
}
