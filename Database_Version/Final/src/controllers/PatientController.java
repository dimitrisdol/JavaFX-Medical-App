package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.Patient;
import utilities.ConnectionUtil;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static controllers.ButtonController.infoBox;
import static controllers.DoctorController.isEmailValid;

/**
 *
 * @author Dimitris Dologlou
 */
public class PatientController {

    // TABLEVIEW DECLARATIONS //

    @FXML
    private TableView<Patient> PatientTable;
    @FXML
    private TableColumn<Patient, String> col_patid;
    @FXML
    private TableColumn<Patient, String> col_patfname;
    @FXML
    private TableColumn<Patient, String> col_patlname;
    @FXML
    private TableColumn<Patient, String> col_patsick;
    @FXML
    private TableColumn<Patient, String> col_patemail;
    @FXML
    private TableColumn<Patient, String> col_patphone;

    // DECLARATIONS //

    @FXML
    private TextField Textpatname;

    @FXML
    private TextField Textpatlname;

    @FXML
    private TextField Textsick;

    @FXML
    private TextField Textemail;

    @FXML
    private TextField Textphone;

    @FXML
    private TextField Textpatid;

    @FXML
    private TextField deleteid;

    // CONNECTION //

    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement = null;
    ObservableList<Patient> oblist = FXCollections.observableArrayList();

    public PatientController() {
        connection = ConnectionUtil.connectdb();
    }

    //DATA SELECT //

    @FXML
    private void ShowPat(ActionEvent event) {
        oblist.removeAll(oblist);
        String sql = "SELECT * FROM patient";

        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                oblist.add(new Patient(resultSet.getString("patient_id"),resultSet.getString("patient_name"),resultSet.getString("patient_lname"),resultSet.getString("sickness"),resultSet.getString("email"),resultSet.getString("phone_number")));
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }

        col_patid.setCellValueFactory(new PropertyValueFactory<>("patient_id"));
        col_patfname.setCellValueFactory(new PropertyValueFactory<>("patient_name"));
        col_patlname.setCellValueFactory(new PropertyValueFactory<>("patient_lname"));
        col_patsick.setCellValueFactory(new PropertyValueFactory<>("sickness"));
        col_patemail.setCellValueFactory(new PropertyValueFactory<>("email"));
        col_patphone.setCellValueFactory(new PropertyValueFactory<>("phone_number"));

        PatientTable.setItems(oblist);

    }

    // INSERT POPUP //

    public void showInsert(ActionEvent event)throws IOException{
        Stage newStage = new Stage();
        Parent comp = FXMLLoader.load(getClass().getResource("../views/patinsert.fxml"));
        newStage.setTitle("Insert Patient");
        Scene stageScene = new Scene(comp, 500, 500);
        newStage.setScene(stageScene);
        newStage.setResizable(false);
        newStage.show();
    }

    // INSERT PATIENT //

    public void addPatient(ActionEvent event){
        String patient_name = Textpatname.getText().toString();
        String patient_lname = Textpatlname.getText().toString();
        String sickness = Textsick.getText().toString();
        String email = Textemail.getText().toString();
        String phone_number = Textphone.getText().toString();

        String sql = "INSERT INTO patient (patient_name, patient_lname, sickness, email, phone_number) VALUES (?, ?, ?, ?, ?)";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, patient_name);
            preparedStatement.setString(2, patient_lname);
            preparedStatement.setString(3, sickness);
            preparedStatement.setString(4, email);
            preparedStatement.setString(5, phone_number);


            if(!isEmailValid(email)){
                infoBox("Please enter a correct Email", null, "Failed");
            }
            else {
                preparedStatement.executeUpdate();
                infoBox("Patient Addition Successful", null, "Success");
            }
        }
        catch(Exception e){
            infoBox("Please insert correct Information",null, "Failed");
            e.printStackTrace();
        }
    }

    // UPDATE POPUP //

    public void showUpdate(ActionEvent event) throws IOException,NullPointerException {
        Stage newStage = new Stage();
        Parent comp = FXMLLoader.load(getClass().getResource("../views/patupdate.fxml"));
        newStage.setTitle("Update Patient");
        Scene stageScene = new Scene(comp, 500, 500);
        newStage.setScene(stageScene);
        newStage.setResizable(false);
        newStage.show();

    }

    // UPDATE PATIENT //

    public void updatePatient(ActionEvent event){
        String patient_name = Textpatname.getText().toString();
        String patient_lname = Textpatlname.getText().toString();
        String sickness = Textsick.getText().toString();
        String email = Textemail.getText().toString();
        String phone_number = Textphone.getText().toString();
        String patient_id = Textpatid.getText().toString();

        String sql = "UPDATE patient SET patient_name=?, patient_lname=?, sickness=?, email=?, phone_number=? WHERE patient_id=?";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, patient_name);
            preparedStatement.setString(2, patient_lname);
            preparedStatement.setString(3, sickness);
            preparedStatement.setString(4, email);
            preparedStatement.setString(5, phone_number);
            preparedStatement.setString(6, patient_id);


            if(!isEmailValid(email)){
                infoBox("Please enter a correct Email", null, "Failed");
            }
            else {
                preparedStatement.executeUpdate();
                infoBox("Patient Edit Successful", null, "Success");
            }
        }
        catch(Exception e){
            infoBox("Please insert correct Information",null, "Failed");
            e.printStackTrace();
        }
    }

    // DELETE SECTION //

    public void deletePatient(ActionEvent event) {

        String patient_id = deleteid.getText().toString();

        String sql = "DELETE FROM patient WHERE patient_id=?";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, patient_id);
            int AffectedRows = preparedStatement.executeUpdate();

            if(AffectedRows==0){
                infoBox("Please insert a Patient ID in the field on the right", null, "Failed");
            }
            else {
                infoBox("Patient Deletion Successful", null, "Success");
            }

        }
        catch(Exception e){
            infoBox("Please choose a valid Patient_ID",null, "Failed");
            e.printStackTrace();
        }
    }

    // CSV DOWNLOAD //

    public void CSVData (ActionEvent event) throws IOException, SQLException {

        String csvFilePath = "Patient-export.csv";
        BufferedWriter fileWriter = new BufferedWriter(new FileWriter(csvFilePath));
        String sql = "SELECT * FROM patient";

        try{
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            fileWriter.write("Patient_Name,Patient_LastName,Sickness,Email,Phone_Number");

            while (resultSet.next()) {
                String patient_name = resultSet.getString("patient_name");
                String patient_lastname = resultSet.getString("patient_lname");
                String sickness = resultSet.getString("sickness");
                String email = resultSet.getString("email");
                String phone_number = resultSet.getString("phone_number");

                String line = String.format("%s,%s,%s,%s,%s",
                        patient_name, patient_lastname, sickness, email, phone_number);

                fileWriter.newLine();
                fileWriter.write(line);
            }
            preparedStatement.close();
            fileWriter.close();
            infoBox("Data has been exported at Patient-export.csv", null, "Success");

        }
        catch (SQLException e) {
            infoBox("Database Error!",null, "Failed");
            e.printStackTrace();
        }
        catch  (IOException ex) {
            infoBox("There is an error with your file!",null, "Failed");
            ex.printStackTrace();
        }

    }

    // BACK BUTTON //

    @FXML
    private void BackScene(ActionEvent event)throws IOException {
        Parent view8 = FXMLLoader.load(getClass().getResource("../views/menu.fxml"));
        Scene scene8 = new Scene(view8);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene8);
        window.show();
    }
}
