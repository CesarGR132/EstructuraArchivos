
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import java.awt.event.ActionEvent;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author Bryan Mayoral
 */
public class CalificacionesAlumnos extends javax.swing.JFrame {

    static String matricula;
    String datos[] = new String[4];
    static ArrayList<String> Semestre1;
    static ArrayList<String> Semestre2;
    static ArrayList<String> Semestre3;
    static ArrayList<String> Semestre4;
    static ArrayList<String> Clases;
    static String NombreMaestro = "";
    DefaultTableModel dtm;


    /**
     * Creates new form CalificacionesAlumnos
     */
    public CalificacionesAlumnos(String Matricula, String NombreMaestro, ArrayList<String> Semestre1, ArrayList<String> Semestre2, ArrayList<String> Semestre3, ArrayList<String> Semestre4, ArrayList<String> Clases) {
        initComponents();
        setLocationRelativeTo(null);
        this.matricula = Matricula;
        this.NombreMaestro = NombreMaestro;
        this.Semestre1 = Semestre1;
        this.Semestre2 = Semestre2;
        this.Semestre3 = Semestre3;
        this.Semestre4 = Semestre4;
        this.Clases = Clases;
        DefinirCombobox();
        jtCalificaciones.setVisible(false);
        setLocationRelativeTo(null);
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }         
        });
        dtm=(DefaultTableModel) jtCalificaciones.getModel();
        btnActualizar.setVisible(false);
    }

    
    private void btnActualizarActionPerformed(ActionEvent evt) {
        try{
           JSONParser parser=new JSONParser();
           JSONObject materias=(JSONObject) parser.parse(new FileReader("src/main/java/resources/materias.json"));
           JSONArray jsonArray=(JSONArray) materias.get("Materias");

            for(Object obj:jsonArray){
               JSONObject jsonObj=(JSONObject) obj;
              
                if(jsonObj.get("Nombre").equals(cbMaterias.getSelectedItem())){      
                    JSONArray array=(JSONArray) jsonObj.get("Alumnos");
                    JSONObject obj2=(JSONObject) array.get(0);
                    int Semestre=Integer.parseInt(""+jsonObj.get("Semestre"));
                    switch(Semestre){
                        case 1:LlamarVariables(obj2,1,Semestre1,array,jsonObj,jsonArray,materias);break;
                        case 2:LlamarVariables(obj2,2,Semestre2,array,jsonObj,jsonArray,materias);break;
                        case 3:LlamarVariables(obj2,3,Semestre3,array,jsonObj,jsonArray,materias);break;
                        case 4:LlamarVariables(obj2,4,Semestre4,array,jsonObj,jsonArray,materias);break;
                    }
                    
                }   
            }
           
        }catch(Exception e){
            
        }
        
    }
    
    public void LlamarVariables(JSONObject obj2, int Semestre, ArrayList<String>Array,JSONArray array,JSONObject jsonObj,JSONArray jsonArray,JSONObject materias){
        try{
            String parciales[]={"Primer Parcial","Segundo Parcial","Tercer Parcial"};
            if(ValidarValores()){
                for(int i=0;i<Array.size();i++){
                    JSONObject alumno=(JSONObject) obj2.get(Array.get(i));
                    for(int j=1;j<jtCalificaciones.getColumnCount();j++){
                        alumno.put(parciales[j-1],Integer.parseInt(""+jtCalificaciones.getValueAt(i,j)));    
                    }          
                    PrintWriter pw=new PrintWriter("src/main/java/resources/materias.json");
                    pw.print(materias.toJSONString());
                    pw.close();
                }
            }
        }catch(Exception e){
            if(ValidarEspacioVacio()){
                JOptionPane.showMessageDialog(null,"Error: Uno de los campos de la tabla esta vacio o tiene un caracter");
            }
        }
    }
    
    public boolean ValidarEspacioVacio(){
        boolean x=false;
        
        try{
            for(int i=0;i<jtCalificaciones.getColumnCount();i++){
                for(int j=1;j<jtCalificaciones.getRowCount();j++){
                    int a=Integer.parseInt(""+jtCalificaciones.getValueAt(i,j));
                }
            }
        }catch(Exception e){
            x=true;
        }
        
        return x;
    }
    
    public boolean ValidarValores(){
        boolean x=true;
        
        for(int i=0;i<jtCalificaciones.getRowCount();i++){
            for(int j=1;j<jtCalificaciones.getColumnCount();j++){
                System.out.println(jtCalificaciones.getValueAt(i,j));
                if(Integer.parseInt(""+jtCalificaciones.getValueAt(i,j))<0 || Integer.parseInt(""+jtCalificaciones.getValueAt(i,j))>100){
                    x=false;
                    JOptionPane.showMessageDialog(null,"Error: Uno de los campos de la tabla es menor a 0 o mayor a 100");
                }
            }
        }
        
        return x;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtCalificaciones = new javax.swing.JTable();
        btnActualizar = new javax.swing.JButton();
        btnVolver = new javax.swing.JButton();
        cbMaterias = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Calificaciones De Los Alumnos.");

        jtCalificaciones.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null}
                },
                new String[]{
                        "Alumno", "Primer Parcial", "Segundo Parcial", "Tercer Parcial"
                }
        ));
        jScrollPane1.setViewportView(jtCalificaciones);

        btnActualizar.setText("Actualizar");

        btnVolver.setText("Volver");
        btnVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverActionPerformed(evt);
            }
        });

        cbMaterias.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Item 1", "Item 2", "Item 3", "Item 4"}));
        cbMaterias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbMateriasActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(btnVolver)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel1)
                                                .addGap(18, 18, 18)
                                                .addComponent(cbMaterias, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(38, 38, 38))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addGap(0, 301, Short.MAX_VALUE)
                                                .addComponent(btnActualizar)
                                                .addGap(282, 282, 282))))
                        .addGroup(layout.createSequentialGroup()
                                .addGap(126, 126, 126)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 408, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGap(16, 16, 16)
                                                                .addComponent(jLabel1))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(btnVolver)))
                                                .addGap(36, 36, 36))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(cbMaterias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)))
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(42, 42, 42)
                                .addComponent(btnActualizar)
                                .addContainerGap(24, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        new VentanaMaestro(matricula).setVisible(true);
        dispose();
    }//GEN-LAST:event_btnVolverActionPerformed

    private void cbMateriasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbMateriasActionPerformed
        try {
            if (!cbMaterias.getSelectedItem().equals("Seleccione una materia")) {
                btnActualizar.setVisible(true);
                JSONParser parser = new JSONParser();
                JSONObject json = (JSONObject) parser.parse(new FileReader("src/main/java/resources/materias.json"));
                JSONArray Materias = (JSONArray) json.get("Materias");
                for (Object obj : Materias) {
                    JSONObject clases = (JSONObject) obj;
                    if (clases.get("Nombre").equals(cbMaterias.getSelectedItem()) && clases.get("Instructor").equals(NombreMaestro)) {
                        int semestre = Integer.parseInt("" + clases.get("Semestre"));

                        switch (semestre) {
                            case 1:
                                LlenarTabla(clases, Semestre1);
                                break;
                            case 2:
                                LlenarTabla(clases, Semestre2);
                                break;
                            case 3:
                                LlenarTabla(clases, Semestre3);
                                break;
                            case 4:
                                LlenarTabla(clases, Semestre4);
                                break;
                        }
                    }
                }

            } else {
                dtm.getDataVector().removeAllElements();
                jtCalificaciones.updateUI();
                btnActualizar.setVisible(false);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }//GEN-LAST:event_cbMateriasActionPerformed

    public void LlenarTabla(JSONObject objeto, ArrayList<String> array) {
        File gradeSettings = new File("src\\main\\java\\resources\\materias.json");
        DefaultTableModel model = (DefaultTableModel) jtCalificaciones.getModel();
        model.setRowCount(0); // Clear existing data
        try {
            JSONParser gradesParser = new JSONParser();
            JSONObject gradesInformation = (JSONObject) gradesParser.parse(new FileReader(gradeSettings));
            JSONArray grades = (JSONArray) gradesInformation.get("Materias");

            for (Object grade : grades) {
                JSONObject gradeObject = (JSONObject) grade;
                if (String.valueOf(gradeObject.get("Nombre")).equalsIgnoreCase(String.valueOf(objeto.get("Nombre")))) {
                    JSONArray information = (JSONArray) gradeObject.get("Alumnos");
                    for (Object data : information) {
                        JSONObject dataObject = (JSONObject) data;
                        for (String nombre : array) {
                            JSONObject student = (JSONObject) dataObject.get(nombre);
                            if (student != null) {
                                String[] dataRow = {nombre, String.valueOf(student.get("Primer Parcial")), String.valueOf(student.get("Segundo Parcial")), String.valueOf(student.get("Tercer Parcial"))};
                                model.addRow(dataRow);
                            } else {
                                System.out.println("Student data for " + nombre + " not found.");
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error set grades " + e.getMessage());
        }
        jtCalificaciones.setModel(model);
        jtCalificaciones.setVisible(true);
        jScrollPane1.setViewportView(jtCalificaciones);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws UnsupportedLookAndFeelException {


        UIManager.setLookAndFeel(new FlatMacDarkLaf());
        SwingUtilities.updateComponentTreeUI(new Login());
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CalificacionesAlumnos(matricula, NombreMaestro, Semestre1, Semestre2, Semestre3, Semestre4, Clases).setVisible(true);
            }
        });
    }


    public void DefinirCombobox() {
        for (int i = 0; i < Clases.size(); i++) {
            String clase = Clases.get(i);
            if (clase.equals("null")) {
                Clases.remove(i);
                i--;
            }
        }

        String materias[] = new String[Clases.size() + 1];
        materias[0] = "Seleccione una materia";
        for (int i = 1; i < materias.length; i++) {
            materias[i] = Clases.get(i - 1);
        }

        cbMaterias.setModel(new javax.swing.DefaultComboBoxModel<>(materias));

    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnVolver;
    private javax.swing.JComboBox<String> cbMaterias;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jtCalificaciones;
    // End of variables declaration                  

}
