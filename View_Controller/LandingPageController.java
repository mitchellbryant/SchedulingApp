/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.Appointment;
import Model.Customer;
import mitchellbryantsoftware2.*;
import static View_Controller.CalendarController.appointment30Data;
import static View_Controller.CalendarController.appointmentData;
import static View_Controller.LoginController.currentUser;
import static View_Controller.ScheduleReportController.myAppointmentData;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 *
 * @author Mitchell Bryant
 */
public class LandingPageController implements Initializable {
    

    
    private Button calendar;
    private Button exit;
    public Stage landingStage = new Stage();
    public Date startDate;
    public Date endDate;
    private Connection conn = mitchellbryantsoftware2.MySqlConnect.get();
    public static ObservableList<Customer> customerData = FXCollections.observableArrayList();
    String sqlCount = "SELECT COUNT(*) FROM customer";
    String sqlCustomerName = "SELECT customerName FROM customer WHERE customerId = ?";
    String sqlCustomerId = "SELECT customerId FROM customer WHERE customerName = ?";
    String sqlAddressId = "SELECT addressId FROM customer WHERE customerId = ?";
    String sqlAddress = "SELECT address, phone FROM address WHERE addressId =?";
    String sqlPhone = "SELECT phone FROM address WHERE addressId =?";
    String sqlAddCustomer = "INSERT INTO customer (customerId, customerName, addressId, active, createDate, createdBy, lastUpdate, lastUpdateBy) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    String sqlDeleteCustomer = "DELETE FROM customer WHERE customerId = ?";
    String sqlHighestId = "SELECT customerId FROM customer ORDER BY customerId DESC LIMIT 1";
    String sqlHighestApptId = "SELECT appointmentId FROM appointment ORDER BY appointmentId DESC LIMIT 1";
    String sqlAppointment = "SELECT title, customerId, start, end, description, createdBy, lastUpdateBy FROM appointment WHERE appointmentId = ?";
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private PreparedStatement ps2 = null;
    private ResultSet rs2 = null;
    private PreparedStatement ps3 = null;
    private ResultSet rs3 = null;
    private PreparedStatement ps4 = null;
    private ResultSet rs4 = null;
    private PreparedStatement ps5 = null;
    private ResultSet rs5 = null;
    private PreparedStatement ps6 = null;
    private ResultSet rs6 = null;
    String a;
    String n;
    String p;
    String title;
    String description;
    String name;
    String start;
    String end;
    public static int appointmentCount;
    
    @FXML
    private Button appt;
    @FXML
    private void handleExitButton(ActionEvent event) {
        System.exit(1);
    }

    public LandingPageController ()throws SQLException, IOException, ParseException{
    String todaysDay = new SimpleDateFormat("dd").format(new Date());
    String todaysMonth = new SimpleDateFormat("MM").format(new Date());
    String todaysTime = new SimpleDateFormat("HH:mm").format(new Date());
    int todaysHour = Integer.parseInt(todaysTime.substring(0,2));
    int todaysMin = Integer.parseInt(todaysTime.substring(3,5));
    ps5 = conn.prepareStatement(sqlHighestId);
    rs5 = ps5.executeQuery();
    if (rs5.next()){
    int customerCount = rs5.getInt(1);
    ps6 = conn.prepareStatement(sqlHighestApptId);
    rs6 = ps6.executeQuery();
    if (rs6.next()){
    int appointmentCount = rs6.getInt(1);   
    
    for (int i = 0; i < customerCount + 1; i++){

        ps = conn.prepareStatement(sqlCustomerName) ;
        ps.setInt(1, i);
        rs = ps.executeQuery();
        ps2 = conn.prepareStatement(sqlAddressId);
        ps2.setInt(1, i);
        rs2 = ps2.executeQuery();
        if (rs.next() && rs2.next()){
        ps3 = conn.prepareStatement(sqlAddress);
        ps3.setInt(1, rs2.getInt(1));
        rs3 = ps3.executeQuery();
        if (rs3.next()){
        n = rs.getString(1);
        a = rs3.getString(1);
        p = rs3.getString(2);
        ps4 = conn.prepareStatement(sqlCustomerId);
        ps4.setString(1, n);
        rs4 = ps4.executeQuery();
        if(rs4.next()){
            
        customerData.add(new Customer (rs4.getInt(1), n, a, p));}
        }
        }}
    for (int i = 0; i < appointmentCount + 1; i++){
        ps = conn.prepareStatement(sqlAppointment);
        ps.setInt(1, i);
        rs = ps.executeQuery();
        if (rs.next()){
        ps2 = conn.prepareStatement(sqlCustomerName);
        ps2.setInt(1, rs.getInt(2));
        rs2 = ps2.executeQuery();
        if (rs2.next()){
        title = rs.getString(1);
        description = rs.getString(5);
        name = rs2.getString(1);
        start = rs.getString(3).substring(0, rs.getString(3).length() - 2);
        end = rs.getString(4).substring(0, rs.getString(4).length() - 2);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd hh:mm:ss");
        startDate = sdf.parse(start);
        String test = startDate.toString();
        endDate = sdf.parse(end);
        String test2 = endDate.toString();
        String test3 = test2.substring(11,13);
        String test4 = test.substring(11,13);
        if (test3.contains("00")){test3="12";} if (test4.contains("00")){test4="12";} //for some reason the Tableview was showing 00 when the database showed 12    
        int test5 = Integer.parseInt(test3);
        int test6 = Integer.parseInt(test4);
            if (MitchellBryantSoftware2.tz.contains("Denver")){   // Changing Time to users' Mountain timezone for Phoenix
                test5 = test5 - 2;
                test6 = test6 - 2;
                test3 = String.valueOf(test5); if (test3.length() == 1){test3="0"+test3;}
                test4 = String.valueOf(test6); if (test4.length() == 1){test4="0"+test4;}
            }
            if (MitchellBryantSoftware2.tz.contains("Europe")){   // Changing Time to users' UTC+1 timezone for London
                test5 = test5 + 5;
                test6 = test6 + 5;
                test3 = String.valueOf(test5); if (test3.length() == 1){test3="0"+test3;}
                test4 = String.valueOf(test6); if (test4.length() == 1){test4="0"+test4;}
            }
        int day = Integer.parseInt(todaysDay);
        int todayMonth = Integer.parseInt(todaysMonth);
        int day7 = Integer.parseInt(start.substring(8,10));
        int month = Integer.parseInt(start.substring(5,7));
        if (day7 - 7 <= day && month ==todayMonth){
        appointmentData.add(new Appointment(i,rs.getInt(2),title, description, name, test.substring(0,10) + "  @ " + test4 + ":" + test.substring(14, 16), test2.substring(0,10) + "  @ " + test3 + ":" +test2.substring(14, 16), rs.getString(6), rs.getString(7)));}

        if ((Integer.parseInt(test.substring(8,10)) == day && (Integer.parseInt(test4) == todaysHour) && (Integer.parseInt(test.substring(14,16)) <= todaysMin +15 && Integer.parseInt(test.substring(14,16)) >= todaysMin) || (Integer.parseInt(test4)== todaysHour+1  && Integer.parseInt(test.substring(14,16)) ==0 && todaysMin >=45) && currentUser.contains(rs.getString(6))  ) )
            {
            alert.setTitle("Appointment Alert");
            alert.setHeaderText("Upcoming Appointment in 15 minutes or less!");
            alert.setContentText("You have a meeting with " + name + " at " +test4 + ":" + test.substring(14,16));
            alert.showAndWait();
        }
        if (month == todayMonth){
        appointment30Data.add(new Appointment(i,rs.getInt(2),title, description, name, test.substring(0,10) + "  @ " + test4 + ":" + test.substring(14, 16), test2.substring(0,10) + "  @ " + test3 + ":" +test2.substring(14, 16), rs.getString(6), rs.getString(7)));}
        
        if (currentUser.contains(rs.getString(6))){
            myAppointmentData.add(new Appointment(i,rs.getInt(2),title, description, name, test.substring(0,10) + "  @ " + test4 + ":" + test.substring(14, 16), test2.substring(0,10) + "  @ " + test3 + ":" +test2.substring(14, 16), rs.getString(6), rs.getString(7)));
        }
        }
    }  
}  }}
}    
    @FXML
    public void customerButton(ActionEvent event) throws Exception{

        try {
            Parent root = FXMLLoader.load(getClass().getResource("Customers.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Customers");
            stage.show();
              
        } catch(Exception f) {
            f.printStackTrace();
        }
    }
        @FXML
    public void calendarButton(ActionEvent event) throws Exception{
        landingStage = (Stage) appt.getScene().getWindow();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Calendar.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Appointments/Calendar");
            stage.show();
            landingStage.close();
              
        } catch(Exception f) {
            f.printStackTrace();
        }
    }
    @FXML
    public void apptTypeReportButton(ActionEvent event) throws Exception{

        try {
            Parent root = FXMLLoader.load(getClass().getResource("DistinctApptsReport.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Report");
            stage.show();
              
        } catch(Exception f) {
            f.printStackTrace();
        }
    }
        @FXML
    public void customerReportButton(ActionEvent event) throws Exception{

        try {
            Parent root = FXMLLoader.load(getClass().getResource("DistinctCustomers.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Customer Count");
            stage.show();
              
        } catch(Exception f) {
            f.printStackTrace();
        }
    }
        @FXML
    public void scheduleReportButton(ActionEvent event) throws Exception{

        try {
            Parent root = FXMLLoader.load(getClass().getResource("ScheduleReport.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Schedule");
            stage.show();
              
        } catch(Exception f) {
            f.printStackTrace();
        }
    }
  
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }    
    
}
