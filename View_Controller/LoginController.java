/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import java.sql.*;
import java.text.SimpleDateFormat;
import javax.swing.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author Mitchell Bryant
 */
public class LoginController implements Initializable {

private Connection conn = null;
private PreparedStatement ps = null;
private ResultSet rs = null;
String LoginLog = "src\\mitchellbryantsoftware2\\LogFile.txt";
BufferedWriter bw = null;
FileWriter fw = null;
PrintWriter out = null;
Writer output = null;
    
public LoginController (){
 // initialize connection
conn= mitchellbryantsoftware2.MySqlConnect.createConnection();
}    
public Stage loginStage = new Stage();   
public static String currentUser;
@FXML
private Label label;
@FXML
private Button okBtn;
@FXML
private Button exitBtn;
@FXML
private Label infoPassword;
@FXML
private TextField nameFld;
@FXML
private PasswordField passwordFld;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
         conn=  mitchellbryantsoftware2.MySqlConnect.get();
    }   
    
    @FXML
    private void handleButtonAction(ActionEvent event)throws SQLException {
    String userName = nameFld.getText().trim();
    String password = passwordFld.getText().trim();

    String sql = "SELECT * FROM user WHERE userName = ? AND password = ?";
    try {
        ps = conn.prepareStatement(sql);
        ps.setString(1, userName);
        ps.setString(2, password);
        rs = ps.executeQuery();

        if (rs.next()) {
            currentUser = userName;
            Timestamp time = new Timestamp(System.currentTimeMillis());
            String timeStamp = time.toString();
            loginStage = (Stage) nameFld.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("LandingPage.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Landing Page");
            stage.show();
            loginStage.close();
        try{  
            BufferedWriter out = new BufferedWriter(new FileWriter(LoginLog, true));
            out.write(timeStamp.substring(0,19) + "  " + currentUser );
            out.newLine();
            out.close();
        }   
        catch (IOException f){
            System.err.println("IOException:" + f.getMessage());
        }

            Stage login = (Stage) exitBtn.getScene().getWindow();
            login.close();
        System.out.println("Connected!");
        label.setText("Connected...");
        } else {
               JOptionPane.showMessageDialog(null, "The username and password did not match", "Access Denied", JOptionPane.ERROR_MESSAGE);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
  
}
        @FXML
    private void handleButtonActionFrench(ActionEvent event)throws SQLException, IOException {
    String userName = nameFld.getText().trim();
    String password = passwordFld.getText().trim();

    String sql = "SELECT * FROM user WHERE userName = ? AND password = ?";
    try {
        ps = conn.prepareStatement(sql);
        ps.setString(1, userName);
        ps.setString(2, password);
        rs = ps.executeQuery();

        if (rs.next()) {
            loginStage = (Stage) nameFld.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("LandingPage.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Landing Page");
            stage.show();
            currentUser = userName;
            loginStage.close();
//
//            Stage login = (Stage) exitBtn.getScene().getWindow();
//            login.close();
        System.out.println("Connected!");
        label.setText("Connected...");
        } else {
               JOptionPane.showMessageDialog(null, "Le nom d'utilisateur et le mot de passe ne correspondent pas", "Accès refusé", JOptionPane.ERROR_MESSAGE);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    }
    @FXML
    private void exitButton(ActionEvent event) {
        System.exit(1);
    }
        
    }
    
 
    

