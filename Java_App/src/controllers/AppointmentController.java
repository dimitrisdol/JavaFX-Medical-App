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
import models.Appointment;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

import static controllers.ButtonController.infoBox;

/**
 *
 * @author Mike Koukias
 */
public class AppointmentController implements Initializable {


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

        ObservableList<Appointment> oblist = FXCollections.observableArrayList();

    // POPULATE CSV TO TABLEVIEW //

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //  oblist.removeAll(oblist);
        String csvFilePath = "Appointment.csv";
        String line;
        String cvsSplitBy = ",";

        try (BufferedReader fileReader = new BufferedReader(new FileReader(csvFilePath));) {
            while ((line = fileReader.readLine()) != null) { // Reading the file luine by line and fill up the oblist list
                String[] fields = line.split(cvsSplitBy);
                Appointment appointment =  new Appointment(fields[0], fields[1], fields[2], fields[3]);
                oblist.add(appointment);

            }
        } catch (FileNotFoundException e) {
            infoBox("No Appointment File Detected", null, "Failed");
            e.printStackTrace();
        } catch (IOException k) {
            infoBox("There is an error with reading the Appointment File", null, "Failed");
            k.printStackTrace();
        }

        col_appid.setCellValueFactory(new PropertyValueFactory<>("appointment_id"));
        col_appdate.setCellValueFactory(new PropertyValueFactory<>("appointment_date"));
        col_docid.setCellValueFactory(new PropertyValueFactory<>("doctor_id"));
        col_patid.setCellValueFactory(new PropertyValueFactory<>("patient_id"));

        AppointmentTable.setItems(oblist); // Populate the Tableview with the list

    }

    // SAVE TO CSV //

    public void CSVData (ActionEvent event) {

        String csvFilePath = "Appointment.csv";
        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(csvFilePath))) {

            for (Appointment appointment : oblist) {

                String text = appointment.getAppointment_id() + "," + appointment.getAppointment_date() + "," + appointment.getDoctor_id() + "," + appointment.getPatient_id()  + "\n";
                fileWriter.write(text);
            }
        } catch (FileNotFoundException e) {
            infoBox("No Appointment File Detected", null, "Failed");
            e.printStackTrace();
        } catch (IOException k) {
            infoBox("There is an error with reading the Appointment File", null, "Failed");
            k.printStackTrace();
        }
    }

    // INSERT APPOINTMENT //

    public void addAppointment(ActionEvent event) {
        String appointment_id = Textappid.getText().toString();
        String appointment_date = Textdate.getText().toString();
        String doctor_id = Textdocid.getText().toString();
        String patient_id = Textpatid.getText().toString();

        if(appointment_id.isEmpty() || appointment_date.isEmpty() || doctor_id.isEmpty() || patient_id.isEmpty()){ // Empty field validation
            infoBox("Please complete all fields", null, "Failed");
        }
        else {
            Appointment appointment = new Appointment(appointment_id, appointment_date, doctor_id, patient_id);
            oblist.add(appointment);

            Textappid.clear(); // Clear the textfields after a successful input
            Textdate.clear();
            Textdocid.clear();
            Textpatid.clear();
        }

        col_appid.setCellValueFactory(new PropertyValueFactory<>("appointment_id"));
        col_appdate.setCellValueFactory(new PropertyValueFactory<>("appointment_date"));
        col_docid.setCellValueFactory(new PropertyValueFactory<>("doctor_id"));
        col_patid.setCellValueFactory(new PropertyValueFactory<>("patient_id"));

        AppointmentTable.setItems(oblist); // Add the single list

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

