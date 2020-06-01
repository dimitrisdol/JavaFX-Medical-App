package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.Cure;
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
 * @author 1.01 update
 */
public class CureController {

    // VALIDATIONS //

    public static boolean isCuredValid(String cured) //cured type validation
    {

            if(cured.equals("No")) {
                return true;
            }
         else return cured.equals("Yes");
    }

    public String StringToBoolean(String cured) { //Turning String from Yes & No to 0 & 1
        if(cured.equals("Yes")){
            return "1";
        }
        else if(cured.equals("No")){
            return "0";
        }
        else
            return cured;
    }

    public String CSVTransformation (String bool) { //Turning 0 & 1 to Yes or No (for CSV)
        if(bool.equals("1")){
            return "Yes";
        }
        else
            return "No";
    }


    // TABLEVIEW DECLARATIONS //

    @FXML
    private TableView<Cure> CureTable;
    @FXML
    private TableColumn<Cure, String> col_cureid;
    @FXML
    private TableColumn<Cure, String> col_patid;
    @FXML
    private TableColumn<Cure, String> col_docid;
    @FXML
    private TableColumn<Cure, String> col_cure;
    @FXML
    private TableColumn<Cure, String> col_cured;

    // DECLARATIONS //

    @FXML
    private TextField Textpatid;

    @FXML
    private TextField Textdocid;

    @FXML
    private TextField Textcure;

    @FXML
    private TextField Textcured;

    @FXML
    private TextField Textcureid;

    @FXML
    private TextField deleteid;

    // CONNECTION //

    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement = null;
    ObservableList<Cure> oblist = FXCollections.observableArrayList();

    public CureController() {
        connection = ConnectionUtil.connectdb();
    }

    // DATA SELECT //

    @FXML
    private void ShowCure(ActionEvent event) {
        oblist.removeAll(oblist); // So that we don't have data duplication in our TableView
        String sql = "SELECT * FROM diagnoses";

        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                oblist.add(new Cure(resultSet.getString("diagnoses_id"),resultSet.getString("patient_id"),resultSet.getString("doctor_id"),resultSet.getString("cure"),resultSet.getString("cured")));
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }

        col_cureid.setCellValueFactory(new PropertyValueFactory<>("diagnoses_id"));
        col_patid.setCellValueFactory(new PropertyValueFactory<>("patient_id"));
        col_docid.setCellValueFactory(new PropertyValueFactory<>("doctor_id"));
        col_cure.setCellValueFactory(new PropertyValueFactory<>("cure"));
        col_cured.setCellValueFactory(new PropertyValueFactory<>("cured"));

        col_cure.setCellFactory(tc -> {         //setCellFactory algorithm to enable TextWrapping for Cure Text
            TableCell<Cure, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(col_cure.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell ;
        });

        col_cured.setCellFactory(tc -> new TableCell<Cure, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);   //setCellFactory algorithm to change 0 & 1 from our SQL-Database to Yes & No in our JavaFX interface
                if (empty || item == null || getTableRow() == null) {
                    setText(null);
                }
                else if(item.equals("0")){
                    setText("No");
                }
                else{
                    setText("Yes");
                }
            }
        });

        CureTable.setItems(oblist);

    }

    // INSERT POPUP //

    public void showInsert(ActionEvent event)throws IOException{
        Stage newStage = new Stage();
        Parent comp = FXMLLoader.load(getClass().getResource("../views/cureinsert.fxml"));
        newStage.setTitle("Insert Diagnosis");
        Scene stageScene = new Scene(comp, 500, 500);
        newStage.setScene(stageScene);
        newStage.setResizable(false);
        newStage.show();
    }

    // INSERT DIAGNOSIS //

    public void addCure(ActionEvent event){
        String patient_id = Textpatid.getText().toString();
        String doctor_id = Textdocid.getText().toString();
        String cure = Textcure.getText().toString();
        String cured = Textcured.getText().toString();


        String sql = "INSERT INTO diagnoses (patient_id, doctor_id, cure, cured) VALUES (?, ?, ?, ?)";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, patient_id);
            preparedStatement.setString(2, doctor_id);
            preparedStatement.setString(3, cure);
            preparedStatement.setString(4, StringToBoolean(cured));    //Turning Yes & No to 0 & 1 for our SQL Database

            if(!isCuredValid(cured)){
                infoBox("Please enter a correct Cured Value: Yes or No", null, "Failed");
            }
            else {
                preparedStatement.executeUpdate();
                infoBox("Diagnosis Addition Successful", null, "Success");
            }
        }
        catch(Exception e){
            infoBox("Please insert correct Information",null, "Failed");
            e.printStackTrace();
        }
    }

    // SHOW UPDATE //

    public void showUpdate(ActionEvent event) throws IOException,NullPointerException {
        Stage newStage = new Stage();
        Parent comp = FXMLLoader.load(getClass().getResource("../views/cureupdate.fxml"));
        newStage.setTitle("Update Diagnosis");
        Scene stageScene = new Scene(comp, 500, 500);
        newStage.setScene(stageScene);
        newStage.setResizable(false);
        newStage.show();

    }

    // UPDATE CURE //

    public void updateCure(ActionEvent event){
        String patient_id = Textpatid.getText().toString();
        String doctor_id = Textdocid.getText().toString();
        String cure = Textcure.getText().toString();
        String cured = Textcured.getText().toString();
        String diagnoses_id = Textcureid.getText().toString();

        String sql = "UPDATE diagnoses SET patient_id=?, doctor_id=?, cure=?, cured=? WHERE diagnoses_id=?";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, patient_id);
            preparedStatement.setString(2, doctor_id);
            preparedStatement.setString(3, cure);
            preparedStatement.setString(4, StringToBoolean(cured)); // Turning Yes & No to 0 & 1
            preparedStatement.setString(5, diagnoses_id);


            if(!isCuredValid(cured)){
                infoBox("Please enter a correct Cured Value: No or Yes", null, "Failed");
            }
            else {
                preparedStatement.executeUpdate();
                infoBox("Diagnosis Edit Successful", null, "Success");
            }
        }
        catch(Exception e){
            infoBox("Please insert correct Information",null, "Failed");
            e.printStackTrace();
        }
    }

    // DELETE SECTION //

    public void deleteCure(ActionEvent event) {

        String diagnoses_id = deleteid.getText().toString();

        String sql = "DELETE FROM diagnoses WHERE diagnoses_id=?";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, diagnoses_id);

            preparedStatement.executeUpdate();
            infoBox("Diagnosis Deletion Successful", null, "Success");

        }
        catch(Exception e){
            infoBox("Please choose a valid Patient_ID",null, "Failed");
            e.printStackTrace();
        }
    }

    // CSV DOWNLOAD //

    public void CSVData (ActionEvent event) throws IOException, SQLException {

        String csvFilePath = "Cure-export.csv";
        BufferedWriter fileWriter = new BufferedWriter(new FileWriter(csvFilePath));
        String sql = "SELECT * FROM diagnoses";

        try{
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            fileWriter.write("Patient_ID,Doctor_ID,Cure,Cured?");

            while (resultSet.next()) {
                String patient_id = resultSet.getString("patient_id");
                String doctor_id = resultSet.getString("doctor_id");
                String cure = resultSet.getString("cure");
                String cured = CSVTransformation(resultSet.getString("cured"));

                String line = String.format("%s,%s,%s,%s",
                        patient_id, doctor_id, cure, cured);

                fileWriter.newLine();
                fileWriter.write(line);
            }
            preparedStatement.close();
            fileWriter.close();
            infoBox("Data has been exported at Cure-export.csv", null, "Success");

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
