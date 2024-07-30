package Repository;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.swing.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class MainOperations implements AdminRepository{

    File subjecSettings = new File("src/main/java/resources/materias.json");
    static File teacherSettings = new File("src/main/java/resources/profesores.json");
    File studentSettings = new File("src/main/java/resources/alumnos.json");

    //All methods need to remove a student
    @Override
    public void deleteStudent() {
        JPanel panel = new JPanel();
        JLabel nameLabel = new JLabel("Seleccione un alumno a eliminar: ");
        JComboBox<String> students = new JComboBox<>();
        ArrayList<String> studentsList = gettingStudents();
        students.addItem("Seleccione un alumno");
        for(String student : studentsList){
            students.addItem(student);
        }

        panel.add(nameLabel); panel.add(students);

        int result = JOptionPane.showConfirmDialog(null, panel, "Eliminar alumno", JOptionPane.OK_CANCEL_OPTION);
        if(result == JOptionPane.OK_OPTION){
            String studentName = (String) students.getSelectedItem();
            if(studentName.equals("Seleccione un alumno")){
                JOptionPane.showMessageDialog(null, "Error, seleccione un alumno", "Error", JOptionPane.ERROR_MESSAGE);
            }else{
                try{
                JSONParser parser = new JSONParser();
                JSONObject studentInformation = (JSONObject) parser.parse(new FileReader(studentSettings));
                JSONArray studentsArray = (JSONArray) studentInformation.get("Estudiantes");

                for(Object student : studentsArray){
                    JSONObject studentObject = (JSONObject) student;
                    if(studentObject.get("Nombre").equals(studentName)){
                        studentsArray.remove(studentObject);
                        deleteStudentFromCredentials((String) studentObject.get("Matricula"));
                        deleteStudentFromSubjects(studentName, studentObject.get("Semestre").toString());
                        break;
                    }
                }

                studentInformation.put("Estudiantes", studentsArray);

                try(FileWriter file = new FileWriter(studentSettings)){
                    file.write(studentInformation.toJSONString());
                    file.flush();
                }


                JOptionPane.showMessageDialog(null, "Alumno eliminado con exito", "Eliminacion exitosa", JOptionPane.INFORMATION_MESSAGE);
                }catch (Exception e){
                    System.err.println("Error en --> " + e.getMessage());
                }
            }
        }
    }

    public void deleteStudentFromCredentials(String matricula){
        try{
            JSONParser parser = new JSONParser();
            JSONObject credentialsInformation = (JSONObject) parser.parse(new FileReader("src/main/java/resources/credenciales.json"));
            JSONArray credentials = (JSONArray) credentialsInformation.get("credentials");

            for(Object credential : credentials){
                JSONObject credentialObject = (JSONObject) credential;
                if(credentialObject.get("Matricula").equals(matricula)){
                    credentials.remove(credentialObject);
                    break;
                }
            }
            credentialsInformation.put("Credenciales", credentials);
            try(FileWriter file = new FileWriter("src/main/java/resources/credenciales.json")){
                file.write(credentialsInformation.toJSONString());
                file.flush();
            }
        }catch (Exception e){
            System.err.println("Error en deleteStudentFromCredentials --> " + e.getMessage());
        }
    }

    public void deleteStudentFromSubjects(String nombre, String semestre){
        try {
            JSONParser parser = new JSONParser();
            JSONObject subjectInformation = (JSONObject) parser.parse(new FileReader(subjecSettings));
            JSONArray subjects = (JSONArray) subjectInformation.get("Materias");

            for (Object subject : subjects) {
                JSONObject subjectObject = (JSONObject) subject;
                String subjectSemester = String.valueOf(subjectObject.get("Semestre"));
                if (subjectSemester.equals(semestre)) {
                    JSONArray alumnos = (JSONArray) subjectObject.get("Alumnos");
                    if (!alumnos.isEmpty()) {
                        JSONObject lastAlumnosObject = (JSONObject) alumnos.get(alumnos.size() - 1);
                        if (lastAlumnosObject.containsKey(nombre)) {
                            lastAlumnosObject.remove(nombre);
                        }
                    }
                    break;
                }
            }

            try (FileWriter file = new FileWriter(subjecSettings)) {
                file.write(subjectInformation.toJSONString());
                file.flush();
            }
        } catch (Exception e) {
            System.err.println("Error in deleteStudentFromSubjects --> " + e.getMessage());
        }
    }
    //-------------------------------

    //All the methods need to dd a new student
    @Override
    public void addNewStudent() {
        JPanel panel = new JPanel();
        JLabel nameLabel = new JLabel("Nombre: ");
        JTextField name = new JTextField(20);
        JLabel firstLastName = new JLabel("Apellido: ");
        JTextField lastName = new JTextField(20);
        JLabel ageLabel = new JLabel("Edad: ");
        JComboBox<String> age = new JComboBox<>();
        for(int i = 15; i <= 60; i++){
            age.addItem(String.valueOf(i));
        }
        JLabel semesterLabel = new JLabel("Semestre: ");
        JComboBox<String> semester = new JComboBox<>();
        semester.addItem("1"); semester.addItem("2"); semester.addItem("3"); semester.addItem("4");

        panel.add(nameLabel); panel.add(name); panel.add(firstLastName); panel.add(lastName); panel.add(ageLabel);
        panel.add(age); panel.add(semesterLabel); panel.add(semester);

        int result = JOptionPane.showConfirmDialog(null, panel, "Registro de nuevo alumno", JOptionPane.OK_CANCEL_OPTION);
        if(result == JOptionPane.OK_OPTION){
            String studentName = name.getText();
            String studentLastName = lastName.getText();
            String studentAge = String.valueOf(age.getSelectedItem());
            String studentSemester = (String) semester.getSelectedItem();

            if(studentName.isEmpty() || studentLastName.isEmpty()){
                JOptionPane.showMessageDialog(null, "Error, campos no llenados correctamente", "Error", JOptionPane.ERROR_MESSAGE);
            }else{
                try{
                    String studentMatricula = generateMatricula(studentName, studentLastName, String.valueOf(Year.now().getValue()), "A");
                    JSONParser parser = new JSONParser();
                    JSONObject studentInformation = (JSONObject) parser.parse(new FileReader(studentSettings));
                    JSONArray students = (JSONArray) studentInformation.get("Estudiantes");

                    JSONObject newStudent = new JSONObject();
                    newStudent.put("Matricula", studentMatricula);
                    newStudent.put("Nombre", studentName + " " + studentLastName);
                    newStudent.put("Edad", Integer.parseInt(studentAge));
                    newStudent.put("Semestre", Integer.parseInt(studentSemester));

                    students.add(newStudent);
                    studentInformation.put("Estudiantes", students);

                    try(FileWriter file = new FileWriter(studentSettings)){
                        file.write(studentInformation.toJSONString());
                        file.flush();
                    }
                    addNewStudentToCredentials(studentMatricula, studentName+"123", studentLastName+"123");
                    addNewStudentToSubjects(studentName + " " + studentLastName, studentSemester);
                    JOptionPane.showMessageDialog(null, "Alumno registrado con exito", "Registro exitoso", JOptionPane.INFORMATION_MESSAGE);
                }catch (Exception e){
                    System.err.println("Error en --> " + e.getMessage());
                }
            }

        }
    }

    public void addNewStudentToSubjects(String name, String semester){
        try {
            JSONParser parser = new JSONParser();
            JSONObject subjectInformation = (JSONObject) parser.parse(new FileReader(subjecSettings));
            JSONArray subjects = (JSONArray) subjectInformation.get("Materias");

            for (Object subject : subjects) {
                JSONObject subjectObject = (JSONObject) subject;
                String subjectSemester = String.valueOf(subjectObject.get("Semestre"));
                if (subjectSemester.equals(semester)) {
                    JSONArray alumnos = (JSONArray) subjectObject.get("Alumnos");
                    JSONObject studentGrades = new JSONObject();
                    studentGrades.put("Primer Parcial", 0);
                    studentGrades.put("Segundo Parcial", 0);
                    studentGrades.put("Tercer Parcial", 0);

                    // Add the new student to the last JSONObject in the Alumnos array
                    JSONObject lastAlumnosObject = (JSONObject) alumnos.get(alumnos.size() - 1);
                    lastAlumnosObject.put(name, studentGrades);
                }
            }

            try (FileWriter file = new FileWriter(subjecSettings)) {
                file.write(subjectInformation.toJSONString());
                file.flush();
            }
        } catch (Exception e) {
            System.err.println("Error inside studentToSubjects --> " + e.getMessage());
        }
    }

    public void addNewStudentToCredentials(String matricula, String user, String password){
        try {
            JSONParser parser = new JSONParser();
            JSONObject credentialsInformation = (JSONObject) parser.parse(new FileReader("src/main/java/resources/credenciales.json"));
            JSONArray credentials = (JSONArray) credentialsInformation.get("credentials");

            JSONObject newCredential = new JSONObject();
            newCredential.put("Matricula", matricula);
            newCredential.put("user", user);
            newCredential.put("password", password);
            newCredential.put("token", "alumno");

            credentials.add(newCredential);
            credentialsInformation.put("Credenciales", credentials);

            try (FileWriter file = new FileWriter("src/main/java/resources/credenciales.json")) {
                file.write(credentialsInformation.toJSONString());
                file.flush();
            }
        } catch (Exception e) {
            System.err.println("Error inside in StudentToCredentials --> " + e.getMessage());
        }
    }

    //-------------------------------

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

    //All methods need to remove a teacher
    @Override
    public void deleteTeacher() {
        JPanel panel = new JPanel();
        JLabel nameLabel = new JLabel("Seleccione un profesor a eliminar: ");
        JComboBox<String> teachers = new JComboBox<>();
        ArrayList<String> teachersList = getTeacherList();
        teachers.addItem("Seleccione un profesor");
        for (String teacher : teachersList) {
            teachers.addItem(teacher);
        }

        panel.add(nameLabel);
        panel.add(teachers);
        int result = JOptionPane.showConfirmDialog(null, panel, "Eliminar profesor", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String teacherName = String.valueOf(teachers.getSelectedItem());
            if (teacherName.equals("Seleccione un profesor")) {
                JOptionPane.showMessageDialog(null, "Error, seleccione un profesor", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                if (hasSubject(teacherName)) {
                    JOptionPane.showMessageDialog(null, "Error, el profesor tiene materias asignadas", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    try {
                        JSONParser parser = new JSONParser();
                        JSONObject teacherInformation = (JSONObject) parser.parse(new FileReader(teacherSettings));
                        JSONArray teachersArray = (JSONArray) teacherInformation.get("Docentes");

                        for (int i = 0; i < teachersArray.size(); i++) {
                            JSONObject teacherObject = (JSONObject) teachersArray.get(i);
                            if (teacherObject.get("Nombre").equals(teacherName)) {
                                teachersArray.remove(i);
                                break;
                            }
                        }

                        try (FileWriter file = new FileWriter(teacherSettings)) {
                            file.write(teacherInformation.toJSONString());
                            file.flush();
                        }

                        deleteTeacherFromCredentials(getTeacherMatricula(teacherName));
                        updateInstructorToUnassigned(teacherName);

                        JOptionPane.showMessageDialog(null, "Profesor eliminado con éxito", "Eliminación exitosa", JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception e) {
                        System.err.println("Error en deleteTeacher --> " + e.getMessage());
                    }
                }
            }
        }
    }

    private String getTeacherMatricula(String teacherName) {
        try {
            JSONParser parser = new JSONParser();
            JSONObject teacherInformation = (JSONObject) parser.parse(new FileReader(teacherSettings));
            JSONArray teachersArray = (JSONArray) teacherInformation.get("Docentes");

            for (Object teacher : teachersArray) {
                JSONObject teacherObject = (JSONObject) teacher;
                if (teacherObject.get("Nombre").equals(teacherName)) {
                    return (String) teacherObject.get("Matricula");
                }
            }
        } catch (Exception e) {
            System.err.println("Error en getTeacherMatricula --> " + e.getMessage());
        }
        return null;
    }
    public void updateInstructorToUnassigned(String instructorName) {
        try {
            JSONParser parser = new JSONParser();
            JSONObject subjectInformation = (JSONObject) parser.parse(new FileReader("src/main/java/resources/materias.json"));
            JSONArray subjectsArray = (JSONArray) subjectInformation.get("Materias");

            for (Object subject : subjectsArray) {
                JSONObject subjectObject = (JSONObject) subject;
                if (subjectObject.get("Instructor").equals(instructorName)) {
                    subjectObject.put("Instructor", "no asignado");
                }
            }

            try (FileWriter file = new FileWriter("src/main/java/resources/materias.json")) {
                file.write(subjectInformation.toJSONString());
                file.flush();
            }
            System.out.println("Instructor updated successfully.");
        } catch (Exception e) {
            System.err.println("Error in updateInstructorToUnassigned --> " + e.getMessage());
        }
    }

    public void deleteTeacherFromCredentials(String matricula){
        try {
            JSONParser parser = new JSONParser();
            JSONObject credentialsInformation = (JSONObject) parser.parse(new FileReader("src/main/java/resources/credenciales.json"));
            JSONArray credentials = (JSONArray) credentialsInformation.get("Credenciales");

            for (int i = 0; i < credentials.size(); i++) {
                JSONObject credentialObject = (JSONObject) credentials.get(i);
                if (credentialObject.get("Matricula").equals(matricula)) {
                    credentials.remove(i);
                    break;
                }
            }

            try (FileWriter file = new FileWriter("src/main/java/resources/credenciales.json")) {
                file.write(credentialsInformation.toJSONString());
                file.flush();
            }
        } catch (Exception e) {
            System.err.println("Error in deleteTeacherFromCredentials --> " + e.getMessage());
        }
    }
    //-------------------------------

    //All methods need to add a new teacher
    @Override
    public void addNewTeacher() {
        JPanel panel = new JPanel();
        JLabel nameLabel = new JLabel("Nombre: ");
        JTextField name = new JTextField(20);
        JLabel firtLastName = new JLabel("Apellido Paterno: ");
        JTextField firtLastNameLabel = new JTextField(20);
        JLabel ageLabel = new JLabel("Edad: ");
        JComboBox<String> age = new JComboBox<>();
        for(int i = 25; i <= 70; i++){
            age.addItem(String.valueOf(i));
        }
        JLabel subjectLabel = new JLabel("Materias: ");
        JComboBox<String> subjects = new JComboBox<>();
        ArrayList<String> subjectsList = getNoTeacherSubjects();
        for(String subject : subjectsList){
            subjects.addItem(subject);
        }
        subjects.addItem("No asignar");

        panel.add(nameLabel); panel.add(name); panel.add(firtLastName); panel.add(firtLastNameLabel);
        panel.add(ageLabel); panel.add(age); panel.add(subjectLabel);

        int result = JOptionPane.showConfirmDialog(null, panel, "Registro de nuevo profesor", JOptionPane.OK_CANCEL_OPTION);
        if(result == JOptionPane.OK_OPTION) {
            String teacherName = name.getText();
            String teacherAge = String.valueOf(age.getSelectedItem());
            String teacherSubject = (String) subjects.getSelectedItem();
            if(teacherName.isEmpty() || teacherSubject.isEmpty()){
                JOptionPane.showMessageDialog(null, "Error, campos no llenados correctamente", "Error", JOptionPane.ERROR_MESSAGE);
            }else{
                if (teacherName.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Error, campos no llenados correctamente", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    try {
                        String teacherMatricula = generateMatricula(teacherName, firtLastNameLabel.getText(), String.valueOf(Year.now().getValue()), "P");
                        JSONParser parser = new JSONParser();
                        JSONObject teacherInformation = (JSONObject) parser.parse(new FileReader(teacherSettings));
                        JSONArray teachers = (JSONArray) teacherInformation.get("Docentes");

                        JSONObject newTeacher = new JSONObject();
                        newTeacher.put("Matricula", teacherMatricula);
                        newTeacher.put("Nombre", teacherName + " " + firtLastNameLabel.getText());
                        newTeacher.put("Edad", Integer.parseInt(teacherAge));

                        JSONObject subjectsObject = new JSONObject();
                        if(!teacherSubject.equals("No asignar")){
                            addNewTeacherToSubject(teacherName + " " + firtLastNameLabel.getText(), teacherSubject);
                        }
                        newTeacher.put("Materias", subjectsObject);

                        teachers.add(newTeacher);
                        teacherInformation.put("Docentes", teachers);

                        try (FileWriter file = new FileWriter(teacherSettings)) {
                            file.write(teacherInformation.toJSONString());
                            file.flush();
                        }
                        addNewTeacherToCredentials(teacherMatricula, teacherName + "123", firtLastNameLabel.getText() + "123");
                        JOptionPane.showMessageDialog(null, "Profesor registrado con exito", "Registro exitoso", JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception e) {
                        System.err.println("Error en addNewTeacher --> " + e.getMessage());
                    }
                }
            }

        }
    }

    public void addNewTeacherToCredentials(String matricula, String user, String password){
        try {
            JSONParser parser = new JSONParser();
            JSONObject credentialsInformation = (JSONObject) parser.parse(new FileReader("src/main/java/resources/credenciales.json"));
            JSONArray credentials = (JSONArray) credentialsInformation.get("credentials");

            JSONObject newCredential = new JSONObject();
            newCredential.put("Matricula", matricula);
            newCredential.put("user", user);
            newCredential.put("password", password);
            newCredential.put("token", "maestro");

            credentials.add(newCredential);
            credentialsInformation.put("Credenciales", credentials);

            try (FileWriter file = new FileWriter("src/main/java/resources/credenciales.json")) {
                file.write(credentialsInformation.toJSONString());
                file.flush();
            }
        } catch (Exception e) {
            System.err.println("Error inside in addNewTeacherToCredentials --> " + e.getMessage());
        }
    }

    public void addNewTeacherToSubject(String teacherName, String subject){
        try{
            JSONParser parser = new JSONParser();
            JSONObject subjectInformation = (JSONObject) parser.parse(new FileReader(subjecSettings));
            JSONArray subjects = (JSONArray) subjectInformation.get("Materias");

            for(Object subjectObject : subjects){
                JSONObject subjectObject1 = (JSONObject) subjectObject;
                if(subjectObject1.get("Nombre").equals(subject)){
                    JSONObject teacher = new JSONObject();
                    teacher.put("Instructor", teacherName);
                    subjectObject1.put("Instructor", teacher);
                }
            }

            try(FileWriter file = new FileWriter(subjecSettings)){
                file.write(subjectInformation.toJSONString());
                file.flush();
            }
        }catch (Exception e){
            System.err.println("Error in addNewTeacherToSubject --> " + e.getMessage());
        }
    }

    //--------------------------

    @Override
    public void updateTeacherInformation() {
        JPanel panel = new JPanel();
        JLabel nameLabel = new JLabel("Seleccione un profesor a actualizar: ");
        JComboBox<String> teachers = new JComboBox<>();
        ArrayList<String> teachersList = getTeacherList();
        teachers.addItem("Seleccione un profesor");
        for (String teacher : teachersList) {
            teachers.addItem(teacher);
        }

        panel.add(nameLabel);
        panel.add(teachers);
        int result = JOptionPane.showConfirmDialog(null, panel, "Actualizar profesor", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String teacherName = String.valueOf(teachers.getSelectedItem());
            if (teacherName.equals("Seleccione un profesor")) {
                JOptionPane.showMessageDialog(null, "Error, seleccione un profesor", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JPanel updatePanel = new JPanel();
                JLabel newNameLabel = new JLabel("Nombre: ");
                JTextField newNameField = new JTextField(20);
                JLabel ageLabel = new JLabel("Edad: ");
                JComboBox<String> ageComboBox = new JComboBox<>();
                for (int i = 25; i <= 70; i++) {
                    ageComboBox.addItem(String.valueOf(i));
                }
                JLabel subjectsLabel = new JLabel("Materias: ");
                JList<String> subjectsList = new JList<>(getAllSubjects().toArray(new String[0]));
                subjectsList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

                updatePanel.add(newNameLabel);
                updatePanel.add(newNameField);
                updatePanel.add(ageLabel);
                updatePanel.add(ageComboBox);
                updatePanel.add(subjectsLabel);
                updatePanel.add(new JScrollPane(subjectsList));

                int updateResult = JOptionPane.showConfirmDialog(null, updatePanel, "Actualizar detalles del profesor", JOptionPane.OK_CANCEL_OPTION);
                if (updateResult == JOptionPane.OK_OPTION) {
                    String newName = newNameField.getText();
                    String newAge = String.valueOf(ageComboBox.getSelectedItem());
                    List<String> selectedSubjects = subjectsList.getSelectedValuesList();

                    if (newName.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Error, campos no llenados correctamente", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        updateTeacherInJson(teacherName, newName, Integer.parseInt(newAge), selectedSubjects);
                    }
                }
            }
        }
    }

    private void updateTeacherInJson(String oldName, String newName, int newAge, List<String> newSubjects) {
        try {
            JSONParser parser = new JSONParser();
            JSONObject teacherInformation = (JSONObject) parser.parse(new FileReader(teacherSettings));
            JSONArray teachersArray = (JSONArray) teacherInformation.get("Docentes");

            for (Object teacher : teachersArray) {
                JSONObject teacherObject = (JSONObject) teacher;
                if (teacherObject.get("Nombre").equals(oldName)) {
                    teacherObject.put("Nombre", newName);
                    teacherObject.put("Edad", newAge);

                    JSONObject subjectsObject = new JSONObject();
                    for (int i = 0; i < newSubjects.size(); i++) {
                        subjectsObject.put(String.valueOf(i), newSubjects.get(i));
                    }
                    teacherObject.put("Materias", subjectsObject);
                    break;
                }
            }

            try (FileWriter file = new FileWriter(teacherSettings)) {
                file.write(teacherInformation.toJSONString());
                file.flush();
            }

            updateSubjectsInJson(oldName, newName, newSubjects);

            JOptionPane.showMessageDialog(null, "Profesor actualizado con éxito", "Actualización exitosa", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            System.err.println("Error en updateTeacherInJson --> " + e.getMessage());
        }
    }

    private void updateSubjectsInJson(String oldName, String newName, List<String> newSubjects) {
        try {
            JSONParser parser = new JSONParser();
            JSONObject subjectInformation = (JSONObject) parser.parse(new FileReader(subjecSettings));
            JSONArray subjectsArray = (JSONArray) subjectInformation.get("Materias");

            for (Object subject : subjectsArray) {
                JSONObject subjectObject = (JSONObject) subject;
                String instructor = (String) subjectObject.get("Instructor");
                if (instructor.equals(oldName)) {
                    if (newSubjects.contains(subjectObject.get("Nombre"))) {
                        subjectObject.put("Instructor", newName);
                    } else {
                        subjectObject.put("Instructor", "no asignado");
                    }
                } else if (newSubjects.contains(subjectObject.get("Nombre")) && instructor.equals("no asignado")) {
                    subjectObject.put("Instructor", newName);
                }
            }

            try (FileWriter file = new FileWriter(subjecSettings)) {
                file.write(subjectInformation.toJSONString());
                file.flush();
            }
        } catch (Exception e) {
            System.err.println("Error en updateSubjectsInJson --> " + e.getMessage());
        }
    }

    private ArrayList<String> getAllSubjects() {
        ArrayList<String> subjects = new ArrayList<>();
        try {
            JSONParser parser = new JSONParser();
            JSONObject subjectInformation = (JSONObject) parser.parse(new FileReader(subjecSettings));
            JSONArray subjectsArray = (JSONArray) subjectInformation.get("Materias");

            for (Object subject : subjectsArray) {
                JSONObject subjectObject = (JSONObject) subject;
                subjects.add((String) subjectObject.get("Nombre"));
            }
        } catch (Exception e) {
            System.err.println("Error en getAllSubjects --> " + e.getMessage());
        }
        return subjects;
    }

    //_--------------

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
    public void addNewSubject() {
        JPanel panel = new JPanel();
        JLabel nameLabel = new JLabel("Nombre: ");
        JTextField name = new JTextField(20);
        JLabel semesterLabel = new JLabel("Semestre: ");
        JComboBox<String> semester = new JComboBox<>();
        semester.addItem("1"); semester.addItem("2"); semester.addItem("3"); semester.addItem("4");
        JLabel teacherLabel = new JLabel("Profesor: ");
        JComboBox<String> teachers = new JComboBox<>();
        ArrayList<String> teachersList = getTeacherList();
        teachers.addItem("Seleccione un profesor");
        for(String teacher : teachersList){
            teachers.addItem(teacher);
        }
        teachers.addItem("No asignar");

        panel.add(nameLabel); panel.add(name); panel.add(semesterLabel); panel.add(semester); panel.add(teacherLabel); panel.add(teachers);

        int result = JOptionPane.showConfirmDialog(null, panel, "Registro de nueva materia", JOptionPane.OK_CANCEL_OPTION);
        if(result == JOptionPane.OK_OPTION){
            String subjectName = name.getText();
            String subjectSemester = (String) semester.getSelectedItem();
            String subjectTeacher = (String) teachers.getSelectedItem();
            if(subjectName.isEmpty() || subjectTeacher.equals("Seleccione un profesor") || subjectTeacher.isEmpty()){
                JOptionPane.showMessageDialog(null, "Error, campos no llenados correctamente", "Error", JOptionPane.ERROR_MESSAGE);
            }else{
                newSubject(getStudentsPerSemester(subjectSemester), subjectName, subjectTeacher, Integer.parseInt(subjectSemester));
                addSubjectToTeacher(subjectName, subjectTeacher);
            }
        }
    }


    //--------------------
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

    public static void addSubjectToTeacher(String nombreMateria, String name) {
        try {
            JSONParser parser = new JSONParser();
            JSONObject teacherInformation = (JSONObject) parser.parse(new FileReader(teacherSettings));
            JSONArray teachersArray = (JSONArray) teacherInformation.get("Docentes");

            for (Object teacher : teachersArray) {
                JSONObject teacherObject = (JSONObject) teacher;
                if (teacherObject.get("Nombre").equals(name)) {
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
        } catch (Exception e) {
            System.err.println("Error in addSubjectToTeacher --> " + e.getMessage());
        }
    }
    //--------------

    public void addTeacherSubject(String name,String subjectName){
        try{
            JSONParser parser = new JSONParser();
            JSONObject subjectInformation = (JSONObject) parser.parse(new FileReader(teacherSettings));
            JSONArray teachers = (JSONArray) subjectInformation.get("Docentes");

            for(Object teacher: teachers){
                JSONObject teacherObject = (JSONObject) teacher;
                if(teacherObject.get("Nombre").equals(name)){
                    JSONObject size = (JSONObject) teacherObject.get("Materias");
                    JSONArray subjects = (JSONArray) teacherObject.get("Materias");
                    JSONObject newSubject = new JSONObject();
                    newSubject.put(String.valueOf(size.size()), subjectName);
                    subjects.add(newSubject);
                    teacherObject.put("Materias", subjects);
                }
            }

            try(FileWriter file = new FileWriter(subjecSettings)){
                file.write(subjectInformation.toJSONString());
                file.flush();
            }
        }catch (Exception e){
            System.err.println("Error en --> " + e.getMessage());
        }
    }


    public static int generateRandomThreeDigitNumber() {
        Random random = new Random();
        return 100 + random.nextInt(900);
    }

    public ArrayList<String> gettingSemesterSubjects(String semester){
        ArrayList<String> subjects = new ArrayList<>();
        try{
            JSONParser parserSubject = new JSONParser();
            JSONObject subjectInformation = (JSONObject) parserSubject.parse(new FileReader(subjecSettings));
            JSONArray subjectsArray = (JSONArray) subjectInformation.get("Materias");

            for(Object subject: subjectsArray){
                JSONObject subjectObject = (JSONObject) subject;
                String compSemester = subjectObject.get("Semestre").toString();
                if(compSemester.equals(semester)){
                    subjects.add((String) subjectObject.get("Nombre"));
                }
            }
        }catch (Exception e){
            System.err.println("Error en --> " + e.getMessage());
        }
        return subjects;
    }

    public ArrayList<String> gettingStudents(){
        ArrayList<String> studentsList = new ArrayList<>();
        try{
            JSONParser parser = new JSONParser();
            JSONObject studentInformation = (JSONObject) parser.parse(new FileReader(studentSettings));
            JSONArray students = (JSONArray) studentInformation.get("Estudiantes");

            for(Object student : students){
                JSONObject studentObject = (JSONObject) student;
                studentsList.add((String) studentObject.get("Nombre"));
            }
        }catch (Exception e){
            System.err.println("Error en getting students--> " + e.getMessage());
        }
        return studentsList;
    }

    public boolean hasTeacher(String subject){
        boolean HasTeacher = true;
        try{
            JSONParser parser = new JSONParser();
            JSONObject subjectInformation = (JSONObject) parser.parse(new FileReader(subjecSettings));
            JSONArray subjects = (JSONArray) subjectInformation.get("Materias");

            for(Object obj : subjects){
                JSONObject subjectObject = (JSONObject) obj;
                if(subjectObject.get("Instructor") == null){
                    HasTeacher = false;
                }
            }
        }catch (Exception e){
            System.err.println("Error en --> " + e.getMessage());
        }
        return HasTeacher;
    }

    public boolean hasSubject(String name){
        boolean hasSubject = false;
        try{
            JSONParser parser = new JSONParser();
            JSONObject subjectInformation = (JSONObject) parser.parse(new FileReader(subjecSettings));
            JSONArray subjects = (JSONArray) subjectInformation.get("Materias");

            for(Object subject : subjects){
                JSONObject subjectObject = (JSONObject) subject;
                if(subjectObject.get("Instructor").equals(name)){
                    hasSubject = true;
                }
            }
        }catch (Exception e){
            System.err.println("Error en --> " + e.getMessage());
        }
        return hasSubject;
    }

    public ArrayList<String> getTeacherList(){
        ArrayList<String> teachers = new ArrayList<>();
        try{
            JSONParser parser = new JSONParser();
            JSONObject teacherInformation = (JSONObject) parser.parse(new FileReader(teacherSettings));
            JSONArray teachersArray = (JSONArray) teacherInformation.get("Docentes");

            for(Object teacher : teachersArray){
                JSONObject teacherObject = (JSONObject) teacher;
                teachers.add((String) teacherObject.get("Nombre"));
            }
        }catch (Exception e){
            System.err.println("Error inside getTeacherList --> " + e.getMessage());
        }
        return teachers;
    }

    public ArrayList<String> getNoTeacherSubjects(){
        ArrayList<String> subjects = new ArrayList<>();
        try{
            JSONParser parser = new JSONParser();
            JSONObject subjectInformation = (JSONObject) parser.parse(new FileReader(subjecSettings));
            JSONArray subjectsArray = (JSONArray) subjectInformation.get("Materias");

            for(Object subject : subjectsArray){
                JSONObject subjectObject = (JSONObject) subject;
                if(subjectObject.get("Instructor") == null){
                    subjects.add((String) subjectObject.get("Nombre"));
                }
            }
        }catch (Exception e){
            System.out.println("Error inside getNoTeacherSubjects --> " + e.getMessage());
        }
        return subjects;
    }

    public ArrayList<String> getStudentsPerSemester(String semester){
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
