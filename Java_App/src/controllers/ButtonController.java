package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Dimitris Dologlou
 */
public class ButtonController {

    // DECLARATIONS //

    @FXML
    private TextField textUsername;

    @FXML
    private PasswordField textPassword;

    // Popup box function

    public static void infoBox(String infoMessage, String headerText, String title) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(infoMessage);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }

    // LOGIN SECTION //

    @FXML
    private void LoginScene(ActionEvent event) {
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader("LoginCSV"));
            String st;
            while ((st = br.readLine()) != null) {
                String[] splitted = st.split(",");   // Now check if the credentials submitted in Textfields equal the two in the file
                if (textUsername.getText().equals(splitted[0]) && textPassword.getText().equals(splitted[1])) {
                    infoBox("Login Successful", null, "Success");
                    Parent view2 = FXMLLoader.load(getClass().getResource("../views/menu.fxml"));
                    Scene scene2 = new Scene(view2);
                    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    window.setScene(scene2);
                    window.show();
                } else {
                    infoBox("Please enter correct Username and Password", null, "Failed");
                }
            }
        } catch (FileNotFoundException e) {
            infoBox("No Login File Detected", null, "Failed");
            e.printStackTrace();
        } catch (IOException k) {
            infoBox("There is an error with reading the Login File", null, "Failed");
            k.printStackTrace();
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
        infoBox("Logout Successful", null, "Success");
        Parent view7 = FXMLLoader.load(getClass().getResource("../views/login.fxml"));
        Scene scene7 = new Scene(view7);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene7);
        window.show();
    }


}
