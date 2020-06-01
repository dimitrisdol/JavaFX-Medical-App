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
import models.Doctor;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import static controllers.ButtonController.infoBox;


/**
 *
 * @author Dimitris Dologlou
 */
public class DoctorController implements Initializable {

    // VALIDATIONS //

    public static boolean isEmailValid(String email) //email validation
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+   // Google algorithm for e-mail validation
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    public static boolean isNumberValid(String salary) //salary validation
    {
        try {
            int value = Integer.parseInt(salary);
            return value >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // TABLEVIEW DECLARATIONS //

    @FXML
    private TableView<Doctor> DoctorTable;
    @FXML
    private TableColumn<Doctor, String> col_docid;
    @FXML
    private TableColumn<Doctor, String> col_docfname;
    @FXML
    private TableColumn<Doctor, String> col_doclname;
    @FXML
    private TableColumn<Doctor, String> col_docjob;
    @FXML
    private TableColumn<Doctor, String> col_docemail;
    @FXML
    private TableColumn<Doctor, String> col_docphone;
    @FXML
    private TableColumn<Doctor, String> col_docsal;

    //  DECLARATIONS //

    @FXML
    private TextField Textfname;

    @FXML
    private TextField Textlname;

    @FXML
    private TextField Textjob;

    @FXML
    private TextField Textemail;

    @FXML
    private TextField Textphone;

    @FXML
    private TextField Textsalary;

    @FXML
    private TextField Textdocid;

    ObservableList<Doctor> oblist = FXCollections.observableArrayList();

    // POPULATE CSV TO TABLEVIEW //

    @Override
    public void initialize(URL location, ResourceBundle resources) {
      //  oblist.removeAll(oblist);
        String csvFilePath = "Doctor.csv";
        String line;
        String cvsSplitBy = ",";

        try (BufferedReader fileReader = new BufferedReader(new FileReader(csvFilePath));) {
            while ((line = fileReader.readLine()) != null) {
                String[] fields = line.split(cvsSplitBy);
                Doctor doctor =  new Doctor(fields[0], fields[1], fields[2], fields[3], fields[4], fields[5],fields[6]);
                oblist.add(doctor);

            }
        } catch (FileNotFoundException e) {
            infoBox("No Doctor File Detected", null, "Failed");
            e.printStackTrace();
        } catch (IOException k) {
            infoBox("There is an error with reading the Doctor File", null, "Failed");
            k.printStackTrace();
        }

        col_docid.setCellValueFactory(new PropertyValueFactory<>("doctor_id"));
        col_docfname.setCellValueFactory(new PropertyValueFactory<>("first_name"));
        col_doclname.setCellValueFactory(new PropertyValueFactory<>("last_name"));
        col_docjob.setCellValueFactory(new PropertyValueFactory<>("job_id"));
        col_docemail.setCellValueFactory(new PropertyValueFactory<>("email"));
        col_docphone.setCellValueFactory(new PropertyValueFactory<>("phone_number"));
        col_docsal.setCellValueFactory(new PropertyValueFactory<>("salary"));

        DoctorTable.setItems(oblist);

    }

    // SAVE TO CSV //

    public void CSVData (ActionEvent event) {

        String csvFilePath = "Doctor.csv";
        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(csvFilePath))) {

            for (Doctor doctor : oblist) {

                String text = doctor.getDoctor_id() + "," + doctor.getFirst_name() + "," + doctor.getLast_name() + "," + doctor.getJob_id() + "," + doctor.getEmail() + "," + doctor.getPhone_number() + "," + doctor.getSalary() + "\n";
                fileWriter.write(text);
            }
        } catch (FileNotFoundException e) {
            infoBox("No Doctor File Detected", null, "Failed");
            e.printStackTrace();
        } catch (IOException k) {
            infoBox("There is an error with reading the Doctor File", null, "Failed");
            k.printStackTrace();
        }
    }

    // INSERT DOCTOR //

    public void addDoctor(ActionEvent event) {
        String doctor_id = Textdocid.getText().toString();
        String first_name = Textfname.getText().toString();
        String last_name = Textlname.getText().toString();
        String job_id = Textjob.getText().toString();
        String email = Textemail.getText().toString();
        String phone_number = Textphone.getText().toString();
        String salary = Textsalary.getText().toString();

        if(!isEmailValid(email)){
            infoBox("Please enter a correct Email", null, "Failed");
        }
        else if(!isNumberValid(salary)){
            infoBox("Please enter a correct Salary", null, "Failed");
        }
        else if(doctor_id.isEmpty() || first_name.isEmpty() || last_name.isEmpty() || job_id.isEmpty() || email.isEmpty() || phone_number.isEmpty() || salary.isEmpty()){
            infoBox("Please complete all fields", null, "Failed");
        }
        else {
            Doctor doctor = new Doctor(doctor_id, first_name, last_name, job_id, email, phone_number, salary);
            oblist.add(doctor);

            Textdocid.clear();
            Textfname.clear();
            Textlname.clear();
            Textjob.clear();
            Textemail.clear();
            Textphone.clear();
            Textsalary.clear();
        }

        col_docid.setCellValueFactory(new PropertyValueFactory<>("doctor_id"));
        col_docfname.setCellValueFactory(new PropertyValueFactory<>("first_name"));
        col_doclname.setCellValueFactory(new PropertyValueFactory<>("last_name"));
        col_docjob.setCellValueFactory(new PropertyValueFactory<>("job_id"));
        col_docemail.setCellValueFactory(new PropertyValueFactory<>("email"));
        col_docphone.setCellValueFactory(new PropertyValueFactory<>("phone_number"));
        col_docsal.setCellValueFactory(new PropertyValueFactory<>("salary"));

        DoctorTable.setItems(oblist);


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
