/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mitchellbryantsoftware2;

import Model.Customer;
import java.io.IOException;
import java.util.Date;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

/**
 *
 * @author Mitchell Bryant
 */
public class MitchellBryantSoftware2 extends Application {
    private Stage stage;
    private java.util.Date date=new java.util.Date(); 
    public Locale currentLocale;
    public static String tz;

   
    
    
    public MitchellBryantSoftware2(){
  
//        customerData.add(new Customer( 1, "testy", 1, true, date, "Mitch", date, "Mitch"));
    }
    
    @Override
    public void start(Stage stage) throws Exception {
//        Locale.setDefault(new Locale("fr", "FR"));
        
    tz = TimeZone.getDefault().getID().toString();

    System.out.println(tz);


        currentLocale = Locale.getDefault();
        this.stage = stage;
        login();
          
    }
//    public java.sql.Date getDate(int index, java.util.Calendar cal){
//        return date;
//    }
    public void login(){
        
        if (currentLocale.getCountry() == "FR"){
        try{    
            Parent root = FXMLLoader.load(View_Controller.LoginController.class.getResource("LoginFRENCH.fxml"));
            Scene scene = new Scene(root);
        
            stage.setScene(scene);
            stage.show();
        } 
        catch (IOException e){
        e.printStackTrace();}}
        else{
        try{
            Parent root2 = FXMLLoader.load(View_Controller.LoginController.class.getResource("Login.fxml"));
            Scene scene2 = new Scene(root2);
        
            stage.setScene(scene2);
            stage.show();
    }
    catch (IOException e){
        e.printStackTrace();}
    }}
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        
    }
    
}
