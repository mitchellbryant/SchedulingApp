/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.Appointment;
import Model.Customer;
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
public class ScheduleReportController implements Initializable {
    public Stage calendarStage = new Stage();   
    private Connection conn = mitchellbryantsoftware2.MySqlConnect.get();
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private PreparedStatement ps2 = null;
    private ResultSet rs2 = null;
    String sqlCustomerName = "SELECT customerName, addressId FROM customer WHERE customerId = ?";
    String sqlPhone = "SELECT phone FROM address WHERE addressId =?";
    public static ObservableList<Appointment> myAppointmentData = FXCollections.observableArrayList();
   
    
    
    @FXML
    private  TableView<Appointment> myAppointmentTable = new TableView();
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
    private Button exitButton;

    
    @FXML
    private void handleCancelButton(ActionEvent event) {
        calendarStage = (Stage) exitButton.getScene().getWindow();
        calendarStage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

       apptName.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
       customerName.setCellValueFactory(cellData -> cellData.getValue().customerNameProperty());
       apptStart.setCellValueFactory(cellData -> cellData.getValue().startProperty());
       apptEnd.setCellValueFactory(cellData -> cellData.getValue().endProperty());
       myAppointmentTable.setItems(myAppointmentData);


       }
    }    
    

