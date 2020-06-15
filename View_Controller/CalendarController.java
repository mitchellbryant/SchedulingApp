/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.Appointment;
import Model.Customer;
import static View_Controller.LandingPageController.customerData;
import static View_Controller.ScheduleReportController.myAppointmentData;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import mitchellbryantsoftware2.MitchellBryantSoftware2;
import static mitchellbryantsoftware2.MitchellBryantSoftware2.tz;

/**
 *
 * @author Mitchell Bryant
 */
public class CalendarController implements Initializable {
    public Stage calendarStage = new Stage();   
    public RadioButton thisWeek = new RadioButton();
    public RadioButton thisMonth = new RadioButton();
    public Button exitButton = new Button();
    public static Appointment selectedAppt;
    public static ObservableList<Appointment> appointmentData = FXCollections.observableArrayList();
    public static ObservableList<Appointment> appointment30Data = FXCollections.observableArrayList();
    public static ObservableList<Customer> selectedCustomerData = FXCollections.observableArrayList();
    private Connection conn = mitchellbryantsoftware2.MySqlConnect.get();
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private PreparedStatement ps2 = null;
    private ResultSet rs2 = null;
    String sqlCustomerName = "SELECT customerName, addressId FROM customer WHERE customerId = ?";
    String sqlPhone = "SELECT phone FROM address WHERE addressId =?";
    public static String cusName;
    public static String cusPhone;
   
    
    
    @FXML
    private  TableView<Appointment> appointmentTable = new TableView();
    @FXML
    private  TableView<Appointment> appointment30Table = new TableView();
    @FXML
    private  TableView<Customer> selectedCustomerTable = new TableView();
    @FXML
    private  TableColumn<Appointment, String> apptName = new TableColumn();
    @FXML
    private  TableColumn<Appointment, String> customerName = new TableColumn();
    @FXML
    private  TableColumn<Appointment, String> apptStart = new TableColumn();
    @FXML
    private  TableColumn<Appointment, String> apptEnd = new TableColumn();
    @FXML    
    DatePicker datePicker = new DatePicker();
    @FXML
    private Button modifyApptBtn;
    @FXML
    private Label timeZone;
//        LocalDate selectedDate = datePicker.getLocalDate();
//    // Add some action (in Java 8 lambda syntax style).
//        datePicker.setOnAction(event -> {
//         LocalDate date = datePicker.getValue();
//         System.out.println("Selected date: " + date);
//        });
//
//        // Add the DatePicker to the Stage.
//        StackPane root = new StackPane();
//        root.getChildren().add(datePicker);
//        stage.setScene(new Scene(root, 500, 650));
//        stage.show();
    
    @FXML
    private void handleCancelButton(ActionEvent event) throws Exception {
        calendarStage = (Stage) thisWeek.getScene().getWindow();
        calendarStage.close();
        appointmentData.clear();
        appointment30Data.clear();
        myAppointmentData.clear();
        customerData.clear();
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("Loading");
        alert.setHeaderText("Please wait a few moments");
        alert.setContentText("Almost there");
        alert.show();
        Parent root = FXMLLoader.load(getClass().getResource("LandingPage.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Landing Page");
            stage.show();
            alert.close();
    }
    @FXML
    private void handleNewAppt(ActionEvent event)throws SQLException, IOException {
    Parent root = FXMLLoader.load(getClass().getResource("NewAppointment.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("New Appointment");
            stage.show();
    }
    @FXML
    private void calendar30Button(ActionEvent event) throws Exception{
        Stage calendarStage = (Stage) thisWeek.getScene().getWindow();
        calendarStage.close();
        Parent root = FXMLLoader.load(getClass().getResource("Calendar30.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Monthly Calendar");
            stage.show(); 
    }
        @FXML
    private void calendarWeekButton(ActionEvent event) throws Exception{
        Stage calendarStage = (Stage) thisMonth.getScene().getWindow();
        calendarStage.close();
        Parent root = FXMLLoader.load(getClass().getResource("Calendar.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Weekly Appointment");
            stage.show(); 
    }
    @FXML
        private void handleModifyAppt(ActionEvent event)throws SQLException, IOException {
        Stage calendarStage = (Stage) exitButton.getScene().getWindow();
        selectedAppt = appointmentTable.getSelectionModel().getSelectedItem();
        
        ps = conn.prepareStatement(sqlCustomerName);
        ps.setInt(1, selectedAppt.getCustomerId());
        rs = ps.executeQuery();
        if (rs.next()){
        ps2 = conn.prepareStatement(sqlPhone);
        ps2.setInt(1, rs.getInt(2));
        rs2 = ps2.executeQuery();
        if (rs2.next()){
        cusName = rs.getString(1);
        cusPhone = rs2.getString(1);
        Parent root = FXMLLoader.load(getClass().getResource("EditAppointment.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Edit Appointment");
            stage.show(); 
            
            
          
    }}}
            @FXML
        private void handleModifyAppt30(ActionEvent event)throws SQLException, IOException {
        Stage calendarStage = (Stage) exitButton.getScene().getWindow();
        selectedAppt = appointment30Table.getSelectionModel().getSelectedItem();
        
        ps = conn.prepareStatement(sqlCustomerName);
        ps.setInt(1, selectedAppt.getCustomerId());
        rs = ps.executeQuery();
        if (rs.next()){
        ps2 = conn.prepareStatement(sqlPhone);
        ps2.setInt(1, rs.getInt(2));
        rs2 = ps2.executeQuery();
        if (rs2.next()){
        cusName = rs.getString(1);
        cusPhone = rs2.getString(1);
        Parent root = FXMLLoader.load(getClass().getResource("EditAppointment.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Edit Appointment");
            stage.show(); 
            
          
    }}}
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       datePicker.setValue(LocalDate.now());
       apptName.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
       customerName.setCellValueFactory(cellData -> cellData.getValue().customerNameProperty());
       apptStart.setCellValueFactory(cellData -> cellData.getValue().startProperty());
       apptEnd.setCellValueFactory(cellData -> cellData.getValue().endProperty());
       appointmentTable.setItems(appointmentData);
       appointment30Table.setItems(appointment30Data);
       timeZone.setText("EST");
       if (MitchellBryantSoftware2.tz.contains("Denver")){
           timeZone.setText("MT (Mountain Time)");
       }
       if (MitchellBryantSoftware2.tz.contains("Europe")){
           timeZone.setText("GMT+1");
       }
    }    
    
}
