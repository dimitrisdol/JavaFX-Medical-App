package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.Patient;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

import static controllers.ButtonController.infoBox;
import static controllers.DoctorController.isEmailValid;


/**
 *
 * @author Mike Koukias
 */
public class PatientController implements Initializable {

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

    ObservableList<Patient> oblist = FXCollections.observableArrayList();

    // POPULATE CSV TO TABLEVIEW //

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //  oblist.removeAll(oblist);
        String csvFilePath = "Patient.csv";
        String line;
        String cvsSplitBy = ",";

        try (BufferedReader fileReader = new BufferedReader(new FileReader(csvFilePath));) {
            while ((line = fileReader.readLine()) != null) {
                String[] fields = line.split(cvsSplitBy);
                Patient patient =  new Patient(fields[0], fields[1], fields[2], fields[3], fields[4], fields[5]);
                oblist.add(patient);

            }
        } catch (FileNotFoundException e) {
            infoBox("No Patient File Detected", null, "Failed");
            e.printStackTrace();
        } catch (IOException k) {
            infoBox("There is an error with reading the Patient File", null, "Failed");
            k.printStackTrace();
        }

        col_patid.setCellValueFactory(new PropertyValueFactory<>("patient_id"));
        col_patfname.setCellValueFactory(new PropertyValueFactory<>("patient_name"));
        col_patlname.setCellValueFactory(new PropertyValueFactory<>("patient_lname"));
        col_patsick.setCellValueFactory(new PropertyValueFactory<>("sickness"));
        col_patemail.setCellValueFactory(new PropertyValueFactory<>("email"));
        col_patphone.setCellValueFactory(new PropertyValueFactory<>("phone_number"));

        PatientTable.setItems(oblist);

    }

    // SAVE TO CSV //

    public void CSVData (ActionEvent event) {

        String csvFilePath = "Patient.csv";
        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(csvFilePath))) {

            for (Patient patient : oblist) {

                String text = patient.getPatient_id() + "," + patient.getPatient_name() + "," + patient.getPatient_lname() + "," + patient.getSickness() + "," + patient.getEmail() + "," + patient.getPhone_number() + "\n";
                fileWriter.write(text);
            }
        } catch (FileNotFoundException e) {
            infoBox("No Patient File Detected", null, "Failed");
            e.printStackTrace();
        } catch (IOException k) {
            infoBox("There is an error with reading the Patient File", null, "Failed");
            k.printStackTrace();
        }
    }

    // INSERT APPOINTMENT //

    public void addPatient(ActionEvent event) {
        String patient_id = Textpatid.getText().toString();
        String patient_name = Textpatname.getText().toString();
        String patient_lname = Textpatlname.getText().toString();
        String sickness = Textsick.getText().toString();
        String email = Textemail.getText().toString();
        String phone_number = Textphone.getText().toString();

        if(!isEmailValid(email)){
            infoBox("Please enter a correct Email", null, "Failed");
        }
        else if(patient_id.isEmpty() || patient_name.isEmpty() || patient_lname.isEmpty() || sickness.isEmpty() || email.isEmpty() || phone_number.isEmpty()){
            infoBox("Please complete all fields", null, "Failed");
        }
        else {
            Patient patient = new Patient(patient_id, patient_name, patient_lname, sickness, email, phone_number);
            oblist.add(patient);

            Textpatid.clear();
            Textpatname.clear();
            Textpatlname.clear();
            Textsick.clear();
            Textemail.clear();
            Textphone.clear();
        }

        col_patid.setCellValueFactory(new PropertyValueFactory<>("patient_id"));
        col_patfname.setCellValueFactory(new PropertyValueFactory<>("patient_name"));
        col_patlname.setCellValueFactory(new PropertyValueFactory<>("patient_lname"));
        col_patsick.setCellValueFactory(new PropertyValueFactory<>("sickness"));
        col_patemail.setCellValueFactory(new PropertyValueFactory<>("email"));
        col_patphone.setCellValueFactory(new PropertyValueFactory<>("phone_number"));

        PatientTable.setItems(oblist);

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