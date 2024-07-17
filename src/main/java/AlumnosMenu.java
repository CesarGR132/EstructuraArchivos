/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import models.Subject;
import models.Tokens.studentToken;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author cesar
 */
public class AlumnosMenu extends javax.swing.JFrame {
    static studentToken mainToken = new studentToken();
    private javax.swing.JTable gradesTable;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblMatricula;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblEdad;
    private javax.swing.JLabel lblSemestre;
    private javax.swing.JTextField txtEdad;
    private javax.swing.JTextField txtMatricula;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtSemestre;

    public boolean showingGrades = false;
    public boolean showingInformation = false;
    public boolean gradesSecure = false;
    public boolean informationSecure = false;
    /**
     * Creates new form AlumnosMenu
     */
    public AlumnosMenu(studentToken token) {
        if(token.getNombre() == null){
            JOptionPane.showMessageDialog(null, "Error al iniciar sesión","Error", JOptionPane.ERROR_MESSAGE);
            this.dispose();
            System.exit(0);
        }else{
            mainToken = token;
            initComponents();
            lblNombre.setText(token.getNombre());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnExit = new javax.swing.JButton();
        lblNombre = new javax.swing.JLabel();
        btnExit1 = new javax.swing.JButton();
        btnShowGrades = new javax.swing.JButton();
        btnShowInformation = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBounds(new java.awt.Rectangle(1132, 740, 740, 740));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setMaximumSize(new java.awt.Dimension(1132, 740));
        setMinimumSize(new java.awt.Dimension(1132, 740));
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(1132, 740));
        setSize(new java.awt.Dimension(1132, 740));
        getContentPane().setLayout(null);

        btnExit.setBackground(new java.awt.Color(255, 0, 0));
        btnExit.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnExit.setForeground(new java.awt.Color(255, 255, 255));
        btnExit.setText("X");
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });
        getContentPane().add(btnExit);
        btnExit.setBounds(1070, 20, 50, 40);

        lblNombre.setFont(new java.awt.Font("Lemon", 0, 14)); // NOI18N
        lblNombre.setText("...");
        getContentPane().add(lblNombre);
        lblNombre.setBounds(35, 26, 340, 19);

        btnExit1.setBackground(new java.awt.Color(255, 0, 0));
        btnExit1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnExit1.setForeground(new java.awt.Color(255, 255, 255));
        btnExit1.setText("Log out");
        btnExit1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExit1ActionPerformed(evt);
            }
        });
        getContentPane().add(btnExit1);
        btnExit1.setBounds(1010, 680, 110, 40);

        btnShowGrades.setBackground(new java.awt.Color(30, 30, 50));
        btnShowGrades.setFont(new java.awt.Font("Lemon", 1, 14)); // NOI18N
        btnShowGrades.setForeground(new java.awt.Color(255, 255, 255));
        btnShowGrades.setIcon(new javax.swing.ImageIcon("C:\\Users\\cesar\\Documents\\Universidad_UNEDL\\4tosemestre\\Estructura_archivos\\EstructuraArchivos\\src\\main\\java\\resources\\icons\\diploma-certificate.png")); // NOI18N
        btnShowGrades.setText("Calificaciones");
        btnShowGrades.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShowGradesActionPerformed(evt);
            }
        });
        getContentPane().add(btnShowGrades);
        btnShowGrades.setBounds(550, 160, 240, 43);

        btnShowInformation.setBackground(new java.awt.Color(30, 30, 50));
        btnShowInformation.setFont(new java.awt.Font("Lemon", 1, 14)); // NOI18N
        btnShowInformation.setForeground(new java.awt.Color(255, 255, 255));
        btnShowInformation.setIcon(new javax.swing.ImageIcon("C:\\Users\\cesar\\Documents\\Universidad_UNEDL\\4tosemestre\\Estructura_archivos\\EstructuraArchivos\\src\\main\\java\\resources\\icons\\information.png")); // NOI18N
        btnShowInformation.setText("Ver información");
        btnShowInformation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShowInformationActionPerformed(evt);
            }
        });
        getContentPane().add(btnShowInformation);
        btnShowInformation.setBounds(290, 160, 240, 43);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        int option = JOptionPane.showConfirmDialog(null, "¿Estás seguro que deseas salir?", "Salir", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }//GEN-LAST:event_btnExitActionPerformed

    private void btnExit1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExit1ActionPerformed
        int option = JOptionPane.showConfirmDialog(null, "¿Estás seguro que deseas cerrar sesión?", "Salir", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            Login newLogin = new Login();
            newLogin.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_btnExit1ActionPerformed

    private void btnShowGradesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShowGradesActionPerformed
        if(!showingGrades){
            if (informationSecure){
                deletingInformation();
            }
            gradesSecure = true;
            gradesTable = new javax.swing.JTable();
            jScrollPane1 = new javax.swing.JScrollPane();

            gradesTable.setModel(new javax.swing.table.DefaultTableModel(
                    new Object [][] {
                            {null, null, null, null},
                            {null, null, null, null},
                            {null, null, null, null},
                            {null, null, null, null},
                            {null, null, null, null},
                            {null, null, null, null},
                            {null, null, null, null},
                            {null, null, null, null},
                            {null, null, null, null},
                            {null, null, null, null},
                            {null, null, null, null},
                            {null, null, null, null},
                            {null, null, null, null},
                            {null, null, null, null},
                            {null, null, null, null},
                            {null, null, null, null},
                            {null, null, null, null},
                            {null, null, null, null},
                            {null, null, null, null},
                            {null, null, null, null}
                    },
                    new String [] {
                            "Materia", "Primer Parcial", "Segundo Parcial", "Tercer Parcial"
                    }
            ));
            jScrollPane1.setViewportView(gradesTable);

            getContentPane().add(jScrollPane1);
            jScrollPane1.setBounds(220, 240, 720, 406);

            ((DefaultTableModel) gradesTable.getModel()).setRowCount(0);
            for(Subject materia : mainToken.getMaterias()){
                String nombre = materia.getNombreMateria();
                String [] dataRow = {nombre, String.valueOf(materia.getGrades().get("Primer Parcial")), String.valueOf(materia.getGrades().get("Segundo Parcial")), String.valueOf(materia.getGrades().get("Tercer Parcial"))};
                ((DefaultTableModel) gradesTable.getModel()).addRow(dataRow);
            }
            showingGrades = true;
        }else if(showingGrades){
            deletingTable();
        }

    }//GEN-LAST:event_btnShowGradesActionPerformed

    private void btnShowInformationActionPerformed(java.awt.event.ActionEvent evt){
        if(!showingInformation){
            if(gradesSecure){
                deletingTable();
            }
            informationSecure = true;
            lblName = new javax.swing.JLabel();
            lblEdad = new javax.swing.JLabel();
            lblMatricula = new javax.swing.JLabel();
            lblSemestre = new javax.swing.JLabel();
            txtSemestre = new javax.swing.JTextField();
            txtMatricula = new javax.swing.JTextField();
            txtNombre = new javax.swing.JTextField();
            txtEdad = new javax.swing.JTextField();


            lblName.setFont(new java.awt.Font("Lemon", 1, 14)); // NOI18N
            lblName.setText("Nombre:");
            getContentPane().add(lblName);
            lblName.setBounds(280, 360, 90, 19);

            lblEdad.setFont(new java.awt.Font("Lemon", 1, 14)); // NOI18N
            lblEdad.setText("Edad:");
            getContentPane().add(lblEdad);
            lblEdad.setBounds(280, 400, 90, 19);

            lblMatricula.setFont(new java.awt.Font("Lemon", 1, 14)); // NOI18N
            lblMatricula.setText("Matricula:");
            getContentPane().add(lblMatricula);
            lblMatricula.setBounds(280, 320, 110, 19);

            lblSemestre.setFont(new java.awt.Font("Lemon", 1, 14)); // NOI18N
            lblSemestre.setText("Semestre:");
            getContentPane().add(lblSemestre);
            lblSemestre.setBounds(280, 440, 90, 19);
            getContentPane().add(txtSemestre);
            txtSemestre.setBounds(400, 440, 380, 26);
            txtSemestre.setFont(new java.awt.Font("Lemon", 1, 14));
            txtSemestre.setEditable(false);
            getContentPane().add(txtMatricula);
            txtMatricula.setBounds(400, 320, 380, 26);
            txtMatricula.setFont(new java.awt.Font("Lemon", 1, 14));
            txtMatricula.setEditable(false);
            getContentPane().add(txtNombre);
            txtNombre.setBounds(400, 360, 380, 26);
            txtNombre.setFont(new java.awt.Font("Lemon", 1, 14));
            txtNombre.setEditable(false);
            getContentPane().add(txtEdad);
            txtEdad.setBounds(400, 400, 380, 26);
            txtEdad.setFont(new java.awt.Font("Lemon", 1, 14));;
            txtEdad.setEditable(false);

            txtNombre.setText(mainToken.getNombre());
            txtEdad.setText(mainToken.getEdad());
            txtMatricula.setText(mainToken.getMatricula());
            txtSemestre.setText(String.valueOf(mainToken.getSemestre()));

            showingInformation = true;
        } else if (showingInformation) {
            deletingInformation();
        }

    }



    public void deletingTable(){
        getContentPane().remove(jScrollPane1);
        getContentPane().remove(gradesTable);
        getContentPane().revalidate();
        getContentPane().repaint();
        showingGrades = false;
    }

    public void deletingInformation(){
        getContentPane().remove(lblName);
        getContentPane().remove(lblEdad);
        getContentPane().remove(lblSemestre);
        getContentPane().remove(lblMatricula);
        getContentPane().remove(txtNombre);
        getContentPane().remove(txtEdad);
        getContentPane().remove(txtMatricula);
        getContentPane().remove(txtSemestre);

        getContentPane().repaint();
        showingInformation = false;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel(new FlatMacDarkLaf());
        SwingUtilities.updateComponentTreeUI(new JFrame());

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AlumnosMenu(mainToken).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnExit1;
    private javax.swing.JButton btnShowGrades;
    private javax.swing.JButton btnShowInformation;
    private javax.swing.JLabel lblNombre;
    // End of variables declaration//GEN-END:variables
}
