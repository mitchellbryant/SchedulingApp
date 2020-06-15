/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.Appointment;
import Model.Customer;
import static View_Controller.CalendarController.appointment30Data;
import static View_Controller.CalendarController.appointmentData;
import static View_Controller.CalendarController.selectedAppt;
import static View_Controller.LoginController.currentUser;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author Mitchell Bryant
 */
public class EditAppointmentController implements Initializable {
    public Stage apptStage = new Stage();   
    private Appointment appt = new Appointment(0,0,"","","","","",0,0,null,"",null,"");
    private Connection conn = mitchellbryantsoftware2.MySqlConnect.get();
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
    private ObservableList<String> hours = FXCollections.observableArrayList("8","9","10","11","12","1","2","3","4","5");
    private ObservableList<String> minutes = FXCollections.observableArrayList("00","15","30","45");
    String sqlAddAppointment = "INSERT INTO appointment (appointmentId, customerId, title, description, location, contact, url, start, end, createDate, createdBy, lastUpdate, lastUpdateBy) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,? ,?)";
    String sqlAddressId = "SELECT addressId FROM customer WHERE customerId = ?";
    String sqlCity = "SELECT cityId, phone FROM address WHERE addressId = ?";
    String sqlLocation = "SELECT city FROM city WHERE cityId =?";
    String sqlStartEnd = "SELECT start, end FROM appointment WHERE start LIKE ?";
    String sqlCountAppts = "SELECT COUNT(*) FROM appointment WHERE start LIKE ?";
    String sqlHighestApptId = "SELECT appointmentId FROM appointment ORDER BY appointmentId DESC LIMIT 1";
    String sqlDeleteAppt = "DELETE FROM appointment WHERE appointmentId = ?";
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    @FXML
    private Label Name;
    @FXML
    private Label Phone;
    @FXML
    private Label Date2;
    @FXML
    private Label startTime;
    @FXML
    private Label endTime;
    @FXML
    private  TableView<Customer> customerTableAppt = new TableView();
    @FXML
    private  TableColumn<Customer, String> customerName = new TableColumn();
    @FXML
    private  TableColumn<Customer, String> customerAddress = new TableColumn();
    @FXML
    private  TableColumn<Customer, String> customerPhone = new TableColumn(); 
    @FXML
    private TextField apptName;
    @FXML
    private TextField apptDescription;
    @FXML
    private ChoiceBox startHour = new ChoiceBox(FXCollections.observableArrayList());
    @FXML
    private ChoiceBox startMin = new ChoiceBox(FXCollections.observableArrayList());
    @FXML
    private ChoiceBox endHour = new ChoiceBox(FXCollections.observableArrayList());
    @FXML
    private ChoiceBox endMin = new ChoiceBox(FXCollections.observableArrayList());
// Create the DatePicker.
@FXML    
 DatePicker datePickerAppt = new DatePicker();

    
    @FXML
    private void handleCancelButton(ActionEvent event)throws SQLException, IOException {
        apptStage = (Stage) datePickerAppt.getScene().getWindow();
        apptStage.close();
    }
    @FXML
    private void handleDeleteButton(ActionEvent event)throws SQLException, IOException {
        apptStage = (Stage) datePickerAppt.getScene().getWindow();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete");
        alert.setContentText("Are you sure you want to delete this Appointment?");
        Optional <ButtonType> action = alert.showAndWait();
        if (action.get() == ButtonType.OK) {
            appointmentData.remove(CalendarController.selectedAppt);
            appointment30Data.remove(CalendarController.selectedAppt);
            ps = conn.prepareStatement(sqlDeleteAppt);
            ps.setInt(1, CalendarController.selectedAppt.getAppointmentId());
            ps.executeUpdate();
        }
        apptStage.close();
    }
    @FXML
    public void addAppt()throws SQLException, IOException{
        apptStage = (Stage) datePickerAppt.getScene().getWindow();
        java.sql.Date createDate = new java.sql.Date(new java.util.Date().getTime());
        java.sql.Timestamp createDateTime = new java.sql.Timestamp(new java.util.Date().getTime());
        String todaysDay = new SimpleDateFormat("dd").format(new Date());
        String todaysMonth = new SimpleDateFormat("MM").format(new Date());
        int day = Integer.parseInt(todaysDay);
        int todayMonth = Integer.parseInt(todaysMonth);
//            String createdBy = 
            //The below statement removes the old appointment to allow the save of the new appointment with the same Appointment and Customer IDs
            appointmentData.remove(CalendarController.selectedAppt);
            appointment30Data.remove(CalendarController.selectedAppt);
            ps = conn.prepareStatement(sqlDeleteAppt);
            ps.setInt(1, selectedAppt.getAppointmentId());
            ps.executeUpdate();
        Date startDate = Date.from(datePickerAppt.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        String todaysPickedDate = datePickerAppt.getValue().toString();
        int day7 = Integer.parseInt(todaysPickedDate.toString().substring(8,10));
        int month = Integer.parseInt(todaysPickedDate.toString().substring(5,7));

      
        ps6 = conn.prepareStatement(sqlCountAppts); // Counting number of appointments today
        ps6.setString(1, todaysPickedDate + "%");
        rs6 = ps6.executeQuery();
        if (rs6.next()){
            int[][] daysAppts = new int[rs6.getInt(1)][2];
            int i = 0;
            ps5 = conn.prepareStatement(sqlStartEnd);
            ps5.setString(1, todaysPickedDate + "%");       
            rs5 = ps5.executeQuery();
            while (rs5.next()){
                
            daysAppts[i][0] = (Integer.parseInt(rs5.getString(1).substring(11,13))* 60 + (Integer.parseInt(rs5.getString(1).substring(14, 16))));
            daysAppts[i][1] = (Integer.parseInt(rs5.getString(2).substring(11,13))* 60 + (Integer.parseInt(rs5.getString(2).substring(14, 16))));
                 i++;
        // The above code sets the selected days' stored appointments' start and end times in minutes
        }
        int startH = Integer.parseInt(startHour.getValue().toString());
        int endH = Integer.parseInt(endHour.getValue().toString());
 
        if (startH < 8){startH = startH +12;} if(endH < 8){endH = endH + 12;}
        String startTime = (startH + ":" + startMin.getValue() + ":00");
        String endTime = (endH + ":" + endMin.getValue() + ":00");
        ps4 = conn.prepareStatement(sqlAddressId);
        ps4.setInt(1, selectedAppt.getCustomerId());
        rs4 = ps4.executeQuery();
        if (rs4.next()){
        ps3 = conn.prepareStatement(sqlCity);
        ps3.setInt(1, rs4.getInt(1));
        rs3 = ps3.executeQuery();
        if (rs3.next()){
        ps4 = conn.prepareStatement(sqlLocation);
        ps4.setInt(1, rs3.getInt(1));
        rs4 = ps4.executeQuery();
        if (rs4.next()){
          
        
        appt.setAppointmentId(selectedAppt.getAppointmentId());
        appt.setCustomerId(selectedAppt.getCustomerId());
        appt.setTitle(apptName.getText());
        appt.setDescription(apptDescription.getText());
        appt.setStart(startH * 60 + Integer.parseInt(startMin.getValue().toString()));
        appt.setEnd(endH * 60 + Integer.parseInt(endMin.getValue().toString()));
        for (int j = 0; j < rs6.getInt(1); i++){
        if((appt.getStart() >= daysAppts[j][0] && appt.getEnd() <= daysAppts[j][1]) || ((appt.getStart() >= daysAppts[j][0]) && appt.getStart() < daysAppts[j][1])   ){
            alert.setTitle("Error: Overlapping Times");
            alert.setHeaderText("The selected appointment time is unavailable");
            alert.setContentText("Appointment times cannot overlap");
            alert.showAndWait();
            j = rs6.getInt(1);
        }
        else{
            if (day7 - 7 <= day && month ==todayMonth){
        appointmentData.add(new Appointment(selectedAppt.getAppointmentId(), appt.getCustomerId(), apptName.getText(),appt.getDescription(), Name.getText(), startDate.toString().substring(0,10) + "  @ " + startTime.substring(0,5), startDate.toString().substring(0,10) + "  @ " + endTime.substring(0,5),CalendarController.selectedAppt.getCreatedBy(),currentUser));}
            if(month == todayMonth){
        appointment30Data.add(new Appointment(selectedAppt.getAppointmentId(), appt.getCustomerId(), apptName.getText(),appt.getDescription(), Name.getText(), startDate.toString().substring(0,10) + "  @ " + startTime.substring(0,5), startDate.toString().substring(0,10) + "  @ " + endTime.substring(0,5),CalendarController.selectedAppt.getCreatedBy(),currentUser));}    
            
        ps2 = conn.prepareStatement(sqlAddAppointment);
        ps2.setInt(1, appt.getAppointmentId());
        ps2.setInt(2, appt.getCustomerId());
        ps2.setString(3, appt.getTitle());
        ps2.setString(4, appt.getDescription());
        ps2.setString(5, rs4.getString(1));
        ps2.setString(6, rs3.getString(2));
        ps2.setString(7, ""); 
        ps2.setString(8, datePickerAppt.getValue() + " " + startTime);
        ps2.setString(9, datePickerAppt.getValue() + " " + endTime);
        ps2.setDate(10, createDate);
        ps2.setTimestamp(10, createDateTime);
        ps2.setString(11, LoginController.currentUser);
        ps2.setDate(12, createDate);
        ps2.setTimestamp(12, createDateTime);
        ps2.setString(13, LoginController.currentUser);
        ps2.executeUpdate();
        j = rs6.getInt(1);
        apptStage.close();}}
        if (rs6.getInt(1) == 0){
        if (day7 - 7 <= day && month ==todayMonth){
            appointmentData.add(new Appointment(selectedAppt.getAppointmentId(), appt.getCustomerId(), apptName.getText(),appt.getDescription(), Name.getText(), startDate.toString().substring(0,10) + "  @ " + startTime.substring(0,5), startDate.toString().substring(0,10) + "  @ " + endTime.substring(0,5),CalendarController.selectedAppt.getCreatedBy(),currentUser));}
        if(month == todayMonth){
            appointment30Data.add(new Appointment(selectedAppt.getAppointmentId(), appt.getCustomerId(), apptName.getText(),appt.getDescription(), Name.getText(), startDate.toString().substring(0,10) + "  @ " + startTime.substring(0,5), startDate.toString().substring(0,10) + "  @ " + endTime.substring(0,5),CalendarController.selectedAppt.getCreatedBy(),currentUser));} 
        ps2 = conn.prepareStatement(sqlAddAppointment);
        ps2.setInt(1, appt.getAppointmentId());
        ps2.setInt(2, appt.getCustomerId());
        ps2.setString(3, appt.getTitle());
        ps2.setString(4, appt.getDescription());
        ps2.setString(5, rs4.getString(1));
        ps2.setString(6, rs3.getString(2));
        ps2.setString(7, ""); 
        ps2.setString(8, datePickerAppt.getValue() + " " + startTime);
        ps2.setString(9, datePickerAppt.getValue() + " " + endTime);
        ps2.setDate(10, createDate);
        ps2.setTimestamp(10, createDateTime);
        ps2.setString(11, LoginController.currentUser);
        ps2.setDate(12, createDate);
        ps2.setTimestamp(12, createDateTime);
        ps2.setString(13, LoginController.currentUser);
        ps2.executeUpdate();
        apptStage.close();

        }
    }}}}}
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       datePickerAppt.setValue(LocalDate.now());
       customerName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
       customerAddress.setCellValueFactory(cellData -> cellData.getValue().addressProperty());
       customerPhone.setCellValueFactory(cellData -> cellData.getValue().phoneProperty());
       customerTableAppt.setItems(LandingPageController.customerData);
       startHour.setItems(hours);
       startMin.setItems(minutes);
       endHour.setItems(hours);
       endMin.setItems(minutes);
       Name.setText(CalendarController.cusName);
       Phone.setText(CalendarController.cusPhone);
       Date2.setText(selectedAppt.startProperty().toString().substring(22,34));
       startTime.setText(selectedAppt.startProperty().toString().substring(37,42));
       endTime.setText(selectedAppt.endProperty().toString().substring(37,42));
       apptName.setText(selectedAppt.titleProperty().toString().substring(23,selectedAppt.titleProperty().toString().length()-1));
       apptDescription.setText(selectedAppt.descriptionProperty().toString().substring(23,selectedAppt.descriptionProperty().toString().length()-1));
 
   
      
       
    }    
    
}
