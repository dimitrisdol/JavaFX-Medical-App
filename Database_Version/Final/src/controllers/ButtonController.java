package controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import utilities.ConnectionUtil;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;



/**
 *
 * @author Mike Koukias
 */
public class ButtonController {

    // CONNECTION & DECLARATIONS //

    @FXML
    private TextField textUsername;

    @FXML
    private PasswordField textPassword;

    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    public ButtonController() {
        connection = ConnectionUtil.connectdb();
    }

    public static void infoBox(String infoMessage, String headerText, String title){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(infoMessage);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }

    // LOGIN SECTION //

    @FXML
    private void LoginScene(ActionEvent event){

        String username = textUsername.getText().toString();
        String password = textPassword.getText().toString();

        String sql = "SELECT * FROM admin WHERE username = ? and password = ?";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                infoBox("Please enter correct Username and Password", null, "Failed");
            } else {
                infoBox("Login Successful", null, "Success");
                Parent view2 = FXMLLoader.load(getClass().getResource("../views/menu.fxml"));
                Scene scene2 = new Scene(view2);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(scene2);
                window.show();
            }
        }
        catch(Exception e){
            e.printStackTrace();
         }
     }


    // DOCTOR SECTION //

    @FXML
    private void DoctorScene(ActionEvent event) throws IOException{
        Parent view3 = FXMLLoader.load(getClass().getResource("../views/doctor.fxml"));
        Scene scene3 = new Scene(view3);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene3);
        window.show();
    }

    // PATIENT SECTION //

    @FXML
    private void PatientScene(ActionEvent event) throws IOException{
        Parent view4 = FXMLLoader.load(getClass().getResource("../views/patient.fxml"));
        Scene scene4 = new Scene(view4);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene4);
        window.show();
    }

    // APPOINTMENTS SECTION //

    @FXML
    private void AppointmentScene(ActionEvent event) throws IOException{
        Parent view5 = FXMLLoader.load(getClass().getResource("../views/appointment.fxml"));
        Scene scene5 = new Scene(view5);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene5);
        window.show();
    }

    // DIAGNOSES-CURES SECTION //

    @FXML
    private void CureScene(ActionEvent event) throws IOException{
        Parent view6 = FXMLLoader.load(getClass().getResource("../views/cure.fxml"));
        Scene scene6 = new Scene(view6);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene6);
        window.show();
    }

    // LOGOUT SECTION //

    @FXML
    private void LogoutScene(ActionEvent event)throws IOException{
        Parent view7 = FXMLLoader.load(getClass().getResource("../views/login.fxml"));
        Scene scene7 = new Scene(view7);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene7);
        window.show();
    }

}

