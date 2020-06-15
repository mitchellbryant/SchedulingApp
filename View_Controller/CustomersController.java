/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.Address;
import Model.City;
import Model.Customer;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 *
 * @author u9
 */
public class CustomersController implements Initializable {
    
    String a;
    String n;
    String p;
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
    String sqlCount = "SELECT COUNT(*) FROM customer";
    String sqlCustomerName = "SELECT customerName FROM customer WHERE customerId = ?";
    String sqlCustomerId = "SELECT customerId FROM customer WHERE customerName = ?";
    String sqlAddressId = "SELECT addressId FROM customer WHERE customerId = ?";
    String sqlCityId = "SELECT cityId FROM address WHERE addressId = ?";
    String sqlAddress = "SELECT address, phone FROM address WHERE addressId =?";
    String sqlPhone = "SELECT phone FROM address WHERE addressId =?";
    String sqlAddCustomer = "INSERT INTO customer (customerId, customerName, addressId, active, createDate, createdBy, lastUpdate, lastUpdateBy) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    String sqlAddAddress = "INSERT INTO address (addressId, address, address2, cityId, postalCode, phone, createDate, createdBy, lastUpdate, lastUpdateBy) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    String sqlDeleteCustomer = "DELETE FROM customer WHERE customerId = ?";
    String sqlDeleteAddress = "DELETE FROM address WHERE addressId = ?";
    String sqlHighestId = "SELECT customerId FROM customer ORDER BY customerId DESC LIMIT 1";
    String sqlHighestAddress = "SELECT addressId FROM customer ORDER BY addressId DESC LIMIT 1";
    private int countCustomers;
    
    public Stage customerStage = new Stage();
    private Customer customer = new Customer(0,"",0,false,null,"",null,"");
    private Address address = new Address(0,"","",0,"","",null,"",null,"");
    private City city = new City(0,"",0,null,"",null,"");
    public static Customer selectedCustomer;
    final ToggleGroup group = new ToggleGroup();
    @FXML
    private Button cancelButton;
    @FXML
    private Button saveButton;
    @FXML
    private Button addCustomer;
    @FXML
    private Button editCustomer;
    @FXML
    private Button deleteCustomer;
    @FXML
    private TextField Name;
    @FXML
    private TextField Address;
    @FXML
    private TextField City;
    @FXML
    private TextField Zip;
    @FXML
    private TextField Address2;
    @FXML
    private TextField Phone;
    @FXML
    private  TableView<Customer> customerTable = new TableView();
    @FXML
    private  TableColumn<Customer, String> customerName = new TableColumn();
    @FXML
    private  TableColumn<Customer, String> customerAddress = new TableColumn();
    @FXML
    private  TableColumn<Customer, String> customerPhone = new TableColumn();
    @FXML
    private void handleCancelButton(ActionEvent event) {
        customerStage = (Stage) Name.getScene().getWindow();
        customerStage.close();
    }

    public CustomersController ()throws SQLException, IOException{
        
    } 
    @FXML
    public void addCustomer()throws SQLException, IOException{
        java.sql.Date createDate = new java.sql.Date(new java.util.Date().getTime());
        java.sql.Timestamp createDateTime = new java.sql.Timestamp(new java.util.Date().getTime());
        if (City.getText().toLowerCase().contains("phoenix")){city.setCityId(1);} else if (City.getText().toLowerCase().contains("new york")){city.setCityId(2);} else if (City.getText().toLowerCase().contains("london")){city.setCityId(3);} else {city.setCityId(1);}
        ps5 = conn.prepareStatement(sqlHighestId);
        rs5 = ps5.executeQuery();
        if (rs5.next()){
             int highestCustomerId = rs5.getInt(1);
        ps4 = conn.prepareStatement(sqlHighestAddress);
        rs4 = ps4.executeQuery();
        if (rs4.next()){
             int highestAddressId = rs5.getInt(1);
        
        customer.setCustomerId(highestCustomerId + 1);
        customer.setCustomerName(Name.getText());
        customer.setAddressId(highestAddressId + 1);
        address.setAddressId(highestAddressId + 1);
        address.setAddress(Address.getText());
        address.setCityId(city.getCityId());
        address.setPostalCode(Zip.getText());
        address.setPhone(Phone.getText());
        address.setCreateDate(createDate);
        address.setCreatedBy(LoginController.currentUser);
        address.setLastUpdate(createDate);
        address.setLastUpdateBy(LoginController.currentUser);
        customer.setActive(true);
        customer.setCreateDate(createDate);
        customer.setCreatedBy(LoginController.currentUser);
        customer.setLastUpdate(createDate);
        customer.setLastUpdateBy(LoginController.currentUser);
        
        LandingPageController.customerData.add(new Customer(customer.getCustomerId(),Name.getText(), Address.getText(), Phone.getText()));
        ps = conn.prepareStatement(sqlAddCustomer) ;
        ps.setInt(1, customer.getCustomerId());
        ps.setString(2, Name.getText());
        ps.setInt(3, customer.getAddressId());
        ps.setBoolean(4, customer.getActive());
        ps.setDate(5, createDate);
        ps.setTimestamp(5, createDateTime);
        ps.setString(6, customer.getCreatedBy());
        ps.setDate(7, createDate);
        ps.setTimestamp(7, createDateTime);
        ps.setString(8, customer.getLastUpdateBy());
        ps.executeUpdate(); 
        ps2 = conn.prepareStatement(sqlAddAddress);
        ps2.setInt(1, highestAddressId + 1);
        ps2.setString(2, Address.getText());
        ps2.setString(3, Address2.getText());
        ps2.setInt(4, city.getCityId());
        ps2.setString(5, Zip.getText());
        ps2.setString(6, Phone.getText());
        ps2.setDate(7, createDate);
        ps2.setTimestamp(7, createDateTime);
        ps2.setString(8, address.getCreatedBy());
        ps2.setDate(9, createDate);
        ps2.setTimestamp(9, createDateTime);
        ps2.setString(10, address.getLastUpdateBy());
        ps2.executeUpdate();
        Name.clear();
        Address.clear();
        Phone.clear();
        City.clear();
        Zip.clear();
        Address2.clear();
        }}
    }
    @FXML
    public void deleteCustomerButton(ActionEvent event) throws Exception{
        Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete");
        alert.setContentText("Are you sure you want to delete this Customer?");
        Optional <ButtonType> action = alert.showAndWait();
        if (action.get() == ButtonType.OK) {
            LandingPageController.customerData.remove(selectedCustomer);
            ps = conn.prepareStatement(sqlAddressId);
            ps.setInt(1, selectedCustomer.getCustomerId());
            rs = ps.executeQuery();
            if (rs.next()){
             int addressID = rs.getInt(1);
             ps5 = conn.prepareStatement(sqlDeleteCustomer);
             ps5.setInt(1, selectedCustomer.getCustomerId());
             ps5.executeUpdate();
             ps4 = conn.prepareStatement(sqlDeleteAddress);
             ps4.setInt(1, addressID);
             ps4.executeUpdate();
        }}
    }
    @FXML
    public void editCustomerButton(ActionEvent event) throws Exception{
        selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
        ps = conn.prepareStatement(sqlAddressId);
        ps.setInt(1, selectedCustomer.getCustomerId());
        rs = ps.executeQuery();
        if (rs.next()){
        int addressID = rs.getInt(1);
        selectedCustomer.setAddressId(addressID);
        customerStage = (Stage) editCustomer.getScene().getWindow();
        customerStage.close();
            Parent root = FXMLLoader.load(getClass().getResource("EditCustomers.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Edit Customer");
            stage.show();
       
       
    }}

    public int countCustomers()throws SQLException, IOException{
        ps = conn.prepareStatement(sqlCount);
        rs = ps.executeQuery();
        if (rs.next()){
        countCustomers = rs.getInt(1);
        return countCustomers;
        }
        else return countCustomers;  
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       customerName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
       customerAddress.setCellValueFactory(cellData -> cellData.getValue().addressProperty());
       customerPhone.setCellValueFactory(cellData -> cellData.getValue().phoneProperty());
       
       customerTable.setItems(LandingPageController.customerData);
       
    }    
    
}
