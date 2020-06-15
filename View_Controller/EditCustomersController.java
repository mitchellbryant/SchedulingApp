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
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Mitchell Bryant
 */
public class EditCustomersController implements Initializable {
    
    
    private Stage editCustomerStage;
    private Customer customer = new Customer(0,"",0,false,null,"",null,"");
    private Address address = new Address(0,"","",0,"","",null,"",null,"");
    private City city = new City(0,"",0,null,"",null,"");
    private Connection conn = mitchellbryantsoftware2.MySqlConnect.get();
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private PreparedStatement ps2 = null;
    private ResultSet rs2 = null;
    private PreparedStatement ps3 = null;
    private ResultSet rs3 = null;
    private  SimpleStringProperty address2Test = new SimpleStringProperty("");
    public  int cityIdTest;
    public String cityX;
    private  SimpleStringProperty postalCodeTest = new SimpleStringProperty("");
    String sqlAddressId = "SELECT addressId FROM customer WHERE customerId = ?";
    String sqlUpdateCustomer = "UPDATE customer SET customerName = ?, lastUpdate = ?, lastUpdateBy = ? WHERE customerID = ?";
    String sqlUpdateAddress = "UPDATE address SET address = ?, address2 = ?,cityId = ?, postalCode = ?, phone = ?, lastUpdate = ?, lastUpdateBy = ? WHERE addressID = ?";
    String sqlGetAddressOther ="SELECT address2, cityId, postalCode FROM address WHERE addressId = ? ";
    @FXML
    private TextField Name2;
    @FXML
    private TextField Address2;
    @FXML
    private TextField City2;
    @FXML
    private TextField Zip2;
    @FXML
    private TextField Address22;
    @FXML
    private TextField Phone2;
    @FXML
    private Button cancelButton;
    @FXML
    private Button saveButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       try{
         ps = conn.prepareStatement(sqlGetAddressOther);
         ps.setInt(1, CustomersController.selectedCustomer.getAddressId());
         rs = ps.executeQuery();
         if (rs.next()){
             address2Test = new SimpleStringProperty(rs.getString(1));
             cityIdTest = rs.getInt(2);
             if (cityIdTest == 1){cityX = "Phoenix";}else if (cityIdTest == 2){cityX = "New York";}else if (cityIdTest == 3){cityX = "London";}
             postalCodeTest = new SimpleStringProperty(rs.getString(3));
          
         }
       }
       catch (SQLException e){
                e.printStackTrace();
                }
        Name2.setText(CustomersController.selectedCustomer.getCustomerName());
        Address2.setText(CustomersController.selectedCustomer.getAddress());
        Phone2.setText(CustomersController.selectedCustomer.getPhone());
        City2.setText(cityX);
        Address22.setText(address2Test.get());
        Zip2.setText(postalCodeTest.get());

 
        
        
    }  

    @FXML
    private void handleCancelButton(ActionEvent event) {
    editCustomerStage = (Stage) cancelButton.getScene().getWindow();
    editCustomerStage.close();
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
    public void handleSaveButton()throws SQLException, IOException{
        editCustomerStage = (Stage) saveButton.getScene().getWindow();
        java.sql.Date updateDate = new java.sql.Date(new java.util.Date().getTime());
        java.sql.Timestamp updateDateTime = new java.sql.Timestamp(new java.util.Date().getTime());
         if (City2.getText().toLowerCase().contains("phoenix")){city.setCityId(1);} else if (City2.getText().toLowerCase().contains("new york")){city.setCityId(2);} else if (City2.getText().toLowerCase().contains("london")){city.setCityId(3);} else {city.setCityId(1);}
        Customer selectedCustomer = CustomersController.selectedCustomer;
        customer.setCustomerId(selectedCustomer.getCustomerId());
        customer.setCustomerName(Name2.getText());
        customer.setAddressId(selectedCustomer.getAddressId());
        customer.setActive(true);
//        customer.setCreateDate(updateDate);
//        customer.setCreatedBy(selectedCustomer.getCreatedBy());
        customer.setLastUpdate(updateDate);
        customer.setLastUpdateBy(LoginController.currentUser);
        ps3 = conn.prepareStatement(sqlAddressId);
        ps3.setInt(1, customer.getCustomerId());
        rs3 = ps3.executeQuery();
        if (rs3.next()){
             int addressId = rs3.getInt(1);
        address.setAddressId(addressId);
        address.setAddress(Address2.getText());
        address.setAddress2(Address22.getText());
        address.setCityId(city.getCityId());
        address.setPostalCode(Zip2.getText());
        address.setPhone(Phone2.getText());
        address.setLastUpdate(updateDate);
        address.setLastUpdateBy(LoginController.currentUser);
        LandingPageController.customerData.remove(selectedCustomer);
        LandingPageController.customerData.add(new Customer(selectedCustomer.getCustomerId(),Name2.getText(), Address2.getText(), Phone2.getText()));
        ps = conn.prepareStatement(sqlUpdateCustomer) ;
        ps.setString(1, Name2.getText());
        ps.setDate(2, updateDate);
        ps.setTimestamp(2, updateDateTime);
        ps.setString(3, customer.getLastUpdateBy());
        ps.setInt(4, customer.getCustomerId());
        ps.executeUpdate(); 
        ps2 = conn.prepareStatement(sqlUpdateAddress);
        ps2.setString(1, address.getAddress());
        ps2.setString(2, address.getAddress2());
        ps2.setInt(3, address.getCityId());
        ps2.setString(4, address.getPostalCode());
        ps2.setString(5, address.getPhone());
        ps2.setDate(6, updateDate);
        ps2.setTimestamp(6, updateDateTime);
        ps2.setString(7, address.getLastUpdateBy());
        ps2.setInt(8, address.getAddressId());
        ps.executeUpdate();
        ps2.executeUpdate();
        
        editCustomerStage.close();
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
    }
    
    }

