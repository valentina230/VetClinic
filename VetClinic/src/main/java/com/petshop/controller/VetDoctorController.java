package com.petshop.controller;

import java.io.IOException;

import com.petshop.App;
import com.petshop.model.VetDoctors;
import com.petshop.model.VetSpecialization;
import com.petshop.model.Working;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class VetDoctorController extends BaseController{

    @FXML
    private TableColumn<VetDoctors, Integer> IdVetDoctorCol;

    @FXML
    private TextField identificationField;

    @FXML
    private TextField addressField;

    @FXML
    private TableColumn<VetDoctors, String> addressVetCol;

    @FXML
    private Button cancelBtn;

    @FXML
    private TableView<VetDoctors> doctorTable;

    @FXML
    private CheckBox emailChk;

    @FXML
    private TextField emailField;

    @FXML
    private TableColumn<VetDoctors, String> emailVetCol;

    @FXML
    private TableColumn<VetDoctors, String> identificationVetCol;

    @FXML
    private TableColumn<VetDoctors, String> lastNameVetCol;

    @FXML
    private TextField lastnameField;

    @FXML
    private CheckBox nameChk;

    @FXML
    private TextField nameField;

    @FXML
    private TableColumn<VetDoctors, String> nameVetCol;

    @FXML
    private CheckBox phoneChk;

    @FXML
    private TextField phoneField;

    @FXML
    private TableColumn<VetDoctors, String> phoneVetCol;

    @FXML
    private Button registerDoctorBtn;

    @FXML
    private Button returnBtn;

    @FXML
    private TextField searchDocField;

    @FXML
    private ComboBox<VetSpecialization> specialitationCbx;

    @FXML
    private CheckBox specialitationChk;

    @FXML
    private TableColumn<VetDoctors, VetSpecialization> specialitationVetCol;

    @FXML
    private TableColumn<VetDoctors, Working> statusVetCol;

    @FXML
    private Button updateBtn;

    @FXML
    private CheckBox usuarioActivoChk;

    @FXML
    private ComboBox<Working> workStatusCbx;

    @FXML
    private CheckBox workingChk;

    // Create Objects and List
    private VetDoctors vetDoctors;
    private ObservableList<VetDoctors> vetData = FXCollections.observableArrayList();
    private FilteredList<VetDoctors> filteredVet;


    public void initialize() {
        // Cargar datos de mascotas
        super.uploadDataVet(vetData);
        doctorTable.setItems(vetData);

        //Get info for cbox
        specialitationCbx.getItems().setAll(VetSpecialization.values());
        workStatusCbx.getItems().setAll(Working.values());


        // Configurar columnas de la tabla
        IdVetDoctorCol.setCellValueFactory(new PropertyValueFactory<>("idVetDoctor"));
        nameVetCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        lastNameVetCol.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        identificationVetCol.setCellValueFactory(new PropertyValueFactory<>("identification"));
        emailVetCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneVetCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        addressVetCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        specialitationVetCol.setCellValueFactory(new PropertyValueFactory<>("specialization"));
        statusVetCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Inicializar listas filtradas
        filteredVet = new FilteredList<>(vetData, p -> true);

    }

    @FXML
    void cancel(ActionEvent event) {

    }

    @FXML
    void chargeEditDoctor(MouseEvent event) {

    }

    @FXML
    void filterDoctors(KeyEvent event) {

    }

    @FXML
    private void goMenu() throws IOException {
        App.setRoot("main");
    }

    @FXML
    void registerDoctor(ActionEvent event) {
        String name = nameField.getText();
        String lastName = lastnameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        String identification = identificationField.getText();
        String address = addressField.getText();
        VetSpecialization specialization = specialitationCbx.getValue();
        Working workStatus = workStatusCbx.getValue();

        if (name.isEmpty() || lastName.isEmpty() || email.isEmpty() || phone.isEmpty() || specialization == null || workStatus == null) {
            popUp("The operation was not successful. Please fill all fields.");
        } else {
            VetDoctors newDoctor = new VetDoctors(name, lastName, email, phone, identification, address ,specialization, workStatus);
            vetDoctorsDao.insertar(newDoctor);
            vetData.add(newDoctor);
            popUp("Successful operation");
            updateTable();
        }
    }

    @FXML
    void updateDoctor(ActionEvent event) {

    }

    private void popUp(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void cleanFields() {
        nameField.clear();
        lastnameField.clear();
        emailField.clear();
        phoneField.clear();
        specialitationCbx.setValue(null);
        workStatusCbx.setValue(null);
    }

    private void updateTable() {
        doctorTable.setItems(vetData);
        cleanFields();
    }
}
