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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.Cure;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

import static controllers.ButtonController.infoBox;


/**
 *
 * @author Dimitris Dologlou
 */
public class CureController implements Initializable {

    // VALIDATIONS //

    public static boolean isCuredValid(String cured) // Cured type validation
    {

        if(cured.equals("No")) {
            return true;
        }
        else return cured.equals("Yes");
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

    ObservableList<Cure> oblist = FXCollections.observableArrayList();

    // POPULATE CSV TO TABLEVIEW //

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //  oblist.removeAll(oblist);
        String csvFilePath = "Cure.csv";
        String line;
        String cvsSplitBy = ",";

        try (BufferedReader fileReader = new BufferedReader(new FileReader(csvFilePath));) {
            while ((line = fileReader.readLine()) != null) { // Reading the file line by line and fill up the oblist list
                String[] fields = line.split(cvsSplitBy);
                Cure cure =  new Cure(fields[0], fields[1], fields[2], fields[3], fields[4]);
                oblist.add(cure);

            }
        } catch (FileNotFoundException e) {
            infoBox("No Cure File Detected", null, "Failed");
            e.printStackTrace();
        } catch (IOException k) {
            infoBox("There is an error with reading the Cure File", null, "Failed");
            k.printStackTrace();
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

        CureTable.setItems(oblist);  // Populate the Tableview with the list

    }

    // SAVE TO CSV //

    public void CSVData (ActionEvent event) {

        String csvFilePath = "Cure.csv";
        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(csvFilePath))) {

            for (Cure cure : oblist) {

                String text = cure.getDiagnoses_id() + "," + cure.getPatient_id() + "," + cure.getDoctor_id() + "," + cure.getCure() + "," + cure.getCured() + "\n";
                fileWriter.write(text);
            }
        } catch (FileNotFoundException e) {
            infoBox("No Cure File Detected", null, "Failed");
            e.printStackTrace();
        } catch (IOException k) {
            infoBox("There is an error with reading the Cure File", null, "Failed");
            k.printStackTrace();
        }
    }

    // INSERT CURE //

    public void addCure(ActionEvent event) {
        String patient_id = Textpatid.getText().toString();
        String doctor_id = Textdocid.getText().toString();
        String cures = Textcure.getText().toString();
        String cured = Textcured.getText().toString();
        String diagnoses_id = Textcureid.getText().toString();

        if(!isCuredValid(cured)){
            infoBox("Please enter a correct Cured Value: No or Yes", null, "Failed");
        }
        else if(patient_id.isEmpty() || doctor_id.isEmpty() || cures.isEmpty() || cured.isEmpty() || diagnoses_id.isEmpty()){
            infoBox("Please complete all fields", null, "Failed");
        }
        else {
            Cure cure = new Cure(diagnoses_id, patient_id, doctor_id, cures, cured);
            oblist.add(cure);

            Textpatid.clear();
            Textdocid.clear();
            Textcure.clear();
            Textcured.clear();
            Textcureid.clear();
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

        CureTable.setItems(oblist);

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
