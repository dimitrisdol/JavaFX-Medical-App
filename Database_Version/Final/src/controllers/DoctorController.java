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
import models.Doctor;
import utilities.ConnectionUtil;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

import static controllers.ButtonController.infoBox;

/**
 *
 * @author Mike Koukias
 */
public class DoctorController {

    // VALIDATIONS //

    public static boolean isEmailValid(String email) //email validation
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
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

    @FXML
    private TextField deleteid;

    // CONNECTION //

    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement = null;
    ObservableList<Doctor> oblist = FXCollections.observableArrayList();

    public DoctorController() {
        connection = ConnectionUtil.connectdb();
    }

    //DATA SELECT //

    @FXML
    private void ShowDoc(ActionEvent event) {
        oblist.removeAll(oblist);
        String sql = "SELECT * FROM doctor";

        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                oblist.add(new Doctor(resultSet.getString("doctor_id"),resultSet.getString("first_name"),resultSet.getString("last_name"),resultSet.getString("job_id"),resultSet.getString("email"),resultSet.getString("phone_number"),resultSet.getString("salary")));
            }
        }
        catch(Exception e){
            e.printStackTrace();
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

    // INSERT POPUP //

    public void showInsert(ActionEvent event)throws IOException{
        Stage newStage = new Stage();
        Parent comp = FXMLLoader.load(getClass().getResource("../views/docinsert.fxml"));
        newStage.setTitle("Insert Doctor");
        Scene stageScene = new Scene(comp, 500, 500);
        newStage.setScene(stageScene);
        newStage.setResizable(false);
        newStage.show();
    }

    // INSERT DOCTOR //

    public void addDoctor(ActionEvent event){
        String first_name = Textfname.getText().toString();
        String last_name = Textlname.getText().toString();
        String job_id = Textjob.getText().toString();
        String email = Textemail.getText().toString();
        String phone_number = Textphone.getText().toString();
        String salary = Textsalary.getText().toString();

        String sql = "INSERT INTO doctor (first_name, last_name, job_id, email, phone_number, salary) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, first_name);
            preparedStatement.setString(2, last_name);
            preparedStatement.setString(3, job_id);
            preparedStatement.setString(4, email);
            preparedStatement.setString(5, phone_number);
            preparedStatement.setString(6, salary);


             if(!isEmailValid(email)){
                infoBox("Please enter a correct Email", null, "Failed");
            }
            else if(!isNumberValid(salary)){
                infoBox("Please enter a correct Salary", null, "Failed");
            }
            else {
                preparedStatement.executeUpdate();
                infoBox("Doctor Addition Successful", null, "Success");
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
        Parent comp = FXMLLoader.load(getClass().getResource("../views/docupdate.fxml"));
        newStage.setTitle("Update Doctor");
        Scene stageScene = new Scene(comp, 500, 500);
        newStage.setScene(stageScene);
        newStage.setResizable(false);
        newStage.show();

    }

    // UPDATE DOCTOR //

    public void updateDoctor(ActionEvent event){
        String first_name = Textfname.getText().toString();
        String last_name = Textlname.getText().toString();
        String job_id = Textjob.getText().toString();
        String email = Textemail.getText().toString();
        String phone_number = Textphone.getText().toString();
        String salary = Textsalary.getText().toString();
        String doctor_id = Textdocid.getText().toString();

        String sql = "UPDATE doctor SET first_name=?, last_name=?, job_id=?, email=?, phone_number=?, salary=? WHERE doctor_id=?";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, first_name);
            preparedStatement.setString(2, last_name);
            preparedStatement.setString(3, job_id);
            preparedStatement.setString(4, email);
            preparedStatement.setString(5, phone_number);
            preparedStatement.setString(6, salary);
            preparedStatement.setString(7, doctor_id);


            if(!isEmailValid(email)){
                infoBox("Please enter a correct Email", null, "Failed");
            }
            else if(!isNumberValid(salary)){
                infoBox("Please enter a correct Salary", null, "Failed");
            }
            else {
                preparedStatement.executeUpdate();
                infoBox("Doctor Edit Successful", null, "Success");
            }
        }
        catch(Exception e){
            infoBox("Please insert correct Information",null, "Failed");
            e.printStackTrace();
        }
    }

    // DELETE SECTION //

    public void deleteDoctor(ActionEvent event) {

        String doctor_id = deleteid.getText().toString();

        String sql = "DELETE FROM doctor WHERE doctor_id=?";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, doctor_id);

            preparedStatement.executeUpdate();
            infoBox("Doctor Deletion Successful", null, "Success");

        }
        catch(Exception e){
            infoBox("Please choose a valid Doctor_ID",null, "Failed");
            e.printStackTrace();
        }
    }

    // CSV DOWNLOAD //

    public void CSVData (ActionEvent event) throws IOException, SQLException {

        String csvFilePath = "Doctor-export.csv";
        BufferedWriter fileWriter = new BufferedWriter(new FileWriter(csvFilePath));
        String sql = "SELECT * FROM doctor";

        try{
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            fileWriter.write("First_Name,Last_Name,Job_ID,Email,Phone_Number,Salary");

            while (resultSet.next()) {
                String first_name = resultSet.getString("first_name");
                String last_name = resultSet.getString("last_name");
                String job_id = resultSet.getString("job_id");
                String email = resultSet.getString("email");
                String phone_number = resultSet.getString("phone_number");
                String salary = resultSet.getString("salary");

                String line = String.format("%s,%s,%s,%s,%s,%s",
                         first_name, last_name, job_id, email, phone_number, salary);

                fileWriter.newLine();
                fileWriter.write(line);
            }
                preparedStatement.close();
                fileWriter.close();
                infoBox("Data has been exported at Doctor-export.csv", null, "Success");

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
