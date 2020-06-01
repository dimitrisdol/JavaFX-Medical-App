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
import models.Appointment;
import utilities.ConnectionUtil;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static controllers.ButtonController.infoBox;


/**
 *
 * @author Dimitris Dologlou
 */
public class AppointmentController {

    // TABLEVIEW DECLARATIONS //

    @FXML
    private TableView<Appointment> AppointmentTable;
    @FXML
    private TableColumn<Appointment, String> col_appid;
    @FXML
    private TableColumn<Appointment, String> col_appdate;
    @FXML
    private TableColumn<Appointment, String> col_docid;
    @FXML
    private TableColumn<Appointment, String> col_patid;

    // DECLARATIONS //

    @FXML
    private TextField Textdate;

    @FXML
    private TextField Textdocid;

    @FXML
    private TextField Textpatid;

    @FXML
    private TextField Textappid;

    @FXML
    private TextField deleteid;

   // CONNECTION //

    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement = null;
    ObservableList<Appointment> oblist = FXCollections.observableArrayList();

    public AppointmentController() {
        connection = ConnectionUtil.connectdb();
    }

    // DATA SELECT //

    @FXML
    private void ShowApp(ActionEvent event) {
        oblist.removeAll(oblist);
        String sql = "SELECT * FROM appointment";

        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                oblist.add(new Appointment(resultSet.getString("appointment_id"),resultSet.getString("appointment_date"),resultSet.getString("doctor_id"),resultSet.getString("patient_id")));
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }

        col_appid.setCellValueFactory(new PropertyValueFactory<>("appointment_id"));
        col_appdate.setCellValueFactory(new PropertyValueFactory<>("appointment_date"));
        col_docid.setCellValueFactory(new PropertyValueFactory<>("doctor_id"));
        col_patid.setCellValueFactory(new PropertyValueFactory<>("patient_id"));

        AppointmentTable.setItems(oblist);

    }

    // INSERT POPUP //

    public void showInsert(ActionEvent event)throws IOException{
        Stage newStage = new Stage();
        Parent comp = FXMLLoader.load(getClass().getResource("../views/appinsert.fxml"));
        newStage.setTitle("Insert Appointment");
        Scene stageScene = new Scene(comp, 500, 500);
        newStage.setScene(stageScene);
        newStage.setResizable(false);
        newStage.show();
    }

    // INSERT APPOINTMENT //

    public void addAppointment(ActionEvent event){
        String appointment_date = Textdate.getText().toString();
        String doctor_id = Textdocid.getText().toString();
        String patient_id = Textpatid.getText().toString();

        String sql = "INSERT INTO appointment (appointment_date, doctor_id, patient_id) VALUES (?, ?, ?)";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, appointment_date);
            preparedStatement.setString(2, doctor_id);
            preparedStatement.setString(3, patient_id);

            preparedStatement.executeUpdate();
            infoBox("Appointment Addition Successful", null, "Success");

        }
        catch(Exception e){
            infoBox("Please insert correct Information",null, "Failed");
            e.printStackTrace();
        }
    }

    // UPDATE POPUP //

    public void showUpdate(ActionEvent event) throws IOException,NullPointerException {
        Stage newStage = new Stage();
        Parent comp = FXMLLoader.load(getClass().getResource("../views/appupdate.fxml"));
        newStage.setTitle("Update Appointment");
        Scene stageScene = new Scene(comp, 500, 500);
        newStage.setScene(stageScene);
        newStage.setResizable(false);
        newStage.show();

    }

    // UPDATE PATIENT //

    public void updateAppointment(ActionEvent event){
        String appointment_date = Textdate.getText().toString();
        String doctor_id = Textdocid.getText().toString();
        String patient_id = Textpatid.getText().toString();
        String appointment_id = Textappid.getText().toString();

        String sql = "UPDATE appointment SET appointment_date=?, doctor_id=?, patient_id=? WHERE appointment_id=?";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, appointment_date);
            preparedStatement.setString(2, doctor_id);
            preparedStatement.setString(3, patient_id);
            preparedStatement.setString(4, appointment_id);

            preparedStatement.executeUpdate();
            infoBox("Appointment Edit Successful", null, "Success");

        }
        catch(Exception e){
            infoBox("Please insert correct Information",null, "Failed");
            e.printStackTrace();
        }
    }

    // DELETE SECTION //

    public void deleteAppointment(ActionEvent event) {

        String appointment_id = deleteid.getText().toString();

        String sql = "DELETE FROM appointment WHERE appointment_id=?";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, appointment_id);

            preparedStatement.executeUpdate();
            infoBox("Appointment Deletion Successful", null, "Success");

        }
        catch(Exception e){
            infoBox("Please choose a valid Appointment_ID",null, "Failed");
            e.printStackTrace();
        }
    }

    // CSV DOWNLOAD //

    public void CSVData (ActionEvent event) throws IOException, SQLException {

        String csvFilePath = "Appointment-export.csv";
        BufferedWriter fileWriter = new BufferedWriter(new FileWriter(csvFilePath));
        String sql = "SELECT * FROM appointment";

        try{
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            fileWriter.write("Appointment_Date,Doctor_ID,Patient_ID");

            while (resultSet.next()) {
                String appointment_date = resultSet.getString("appointment_date");
                String doctor_id = resultSet.getString("doctor_id");
                String patient_id = resultSet.getString("patient_id");

                String line = String.format("%s,%s,%s",
                        appointment_date, doctor_id, patient_id);

                fileWriter.newLine();
                fileWriter.write(line);
            }
            preparedStatement.close();
            fileWriter.close();
            infoBox("Data has been exported at Appointment-export.csv", null, "Success");

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
