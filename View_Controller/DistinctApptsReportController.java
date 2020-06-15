/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.Appointment;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

/**
 *
 * @author Mitchell Bryant
 */
public class DistinctApptsReportController implements Initializable {
    
    String sqlAppt = "SELECT count(distinct description) FROM appointment WHERE start LIKE ?";
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private Connection conn = mitchellbryantsoftware2.MySqlConnect.get();
    public String countA;
    private Date today = new Date();
    private String todaysMonth = new SimpleDateFormat("MM").format(new Date());
    
    @FXML
    private Label count;
    @FXML
    private Label count2;
    @FXML
    private Button cancelButton;
    
    public DistinctApptsReportController()throws SQLException, IOException{
        ps = conn.prepareStatement(sqlAppt);
        ps.setString(1,"2017"+"-"+todaysMonth+"%");
        ps.executeQuery();
        rs = ps.executeQuery();
        if (rs.next()){
            countA = (rs.getString(1));
        }
    }
    @FXML
    private void handleCancelButton(ActionEvent event) {
        Stage editCustomerStage = (Stage) cancelButton.getScene().getWindow();
    editCustomerStage.close();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        count.setText(countA);
   
    }    
    
}
