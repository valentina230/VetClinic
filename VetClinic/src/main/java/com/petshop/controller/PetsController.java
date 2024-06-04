package com.petshop.controller;

import java.io.IOException;

import com.petshop.App;
import com.petshop.model.Clients;
import com.petshop.model.StatusPet; 
import com.petshop.model.PetType;
import com.petshop.model.Pets;
import com.petshop.model.Sex;


import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class PetsController extends BaseController {
 
    @FXML
    private void goMenu() throws IOException {
        App.setRoot("main");
    }

    @FXML
    private TableColumn<Pets, Integer> Id_PetCol;

    @FXML
    private ComboBox<StatusPet> statusCbx;

    @FXML
    private TableColumn<Pets, StatusPet> statusPetCol;

    @FXML
    private TextField buscarPetField;

    @FXML
    private Button cancelBtn;

    @FXML
    private ComboBox<Clients> clientCbx;

    @FXML
    private CheckBox clientIdentificationChk;

    @FXML
    private TableColumn<Pets, String> nameClientPetCol;

    @FXML
    private TableColumn<Pets, String> namePetCol;

    @FXML
    private TextField nameField;

    @FXML
    private CheckBox namePetChk;

    @FXML
    private TableView<Pets> petTable;

    @FXML
    private TextField racePet;

    @FXML
    private TableColumn<Pets, String> racePetCol;

    @FXML
    private CheckBox racePetChk;

    @FXML
    private Button registrarPetBtn;

    @FXML
    private Button returnBtn;

    @FXML
    private ComboBox<Sex> sexCbx;

    @FXML
    private CheckBox sexPetChk;

    @FXML
    private TableColumn<Pets, String> sexPetCol;

    @FXML
    private ComboBox<PetType> specieCbx;

    @FXML
    private CheckBox speciePetChk;

    @FXML
    private TableColumn<Pets, String> speciePetCol;

    @FXML
    private Button updateBtn;

    @FXML
    private CheckBox usuarioActivoChk;

    @FXML
    private TextField yearBornField;

    @FXML
    private CheckBox yearBornPetChk;

    @FXML
    private TableColumn<Pets, Integer> yearBornPetCol;

    @FXML
    private TableColumn<Pets, String> identificationClientCol;

    // Create objects and list
    private Pets selectPet;
    private ObservableList<Pets> petsData = FXCollections.observableArrayList();
    private FilteredList<Pets> filteredPets;

    public PetsController() {
        super();
    }

    public void initialize() {
        // Cargar datos de mascotas
        super.uploadDataPets(petsData);
        petTable.setItems(petsData);

        // upload the information to the combox
        specieCbx.getItems().setAll(PetType.values());
        statusCbx.getItems().setAll(StatusPet.values());
        ObservableList<Clients> clientsOL= FXCollections.observableArrayList(clientDao.buscarTodos());
        clientCbx.getItems().setAll(clientsOL);
        sexCbx.setItems(FXCollections.observableArrayList(Sex.values()));
        
        // Configurar columnas de la tabla
        Id_PetCol.setCellValueFactory(new PropertyValueFactory<>("idPet"));
        namePetCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        racePetCol.setCellValueFactory(new PropertyValueFactory<>("breed"));
        sexPetCol.setCellValueFactory(new PropertyValueFactory<>("sex"));
        speciePetCol.setCellValueFactory(new PropertyValueFactory<>("species"));
        yearBornPetCol.setCellValueFactory(new PropertyValueFactory<>("age"));
        statusPetCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        nameClientPetCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOwner().getName()));
        identificationClientCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOwner().getIdentification()));

        // Inicializar listas filtradas
        filteredPets = new FilteredList<>(petsData, p -> true);

    }

    @FXML
    void cancel(ActionEvent event) {
        cleanFields();
        updateBtn.setVisible(false);
        registrarPetBtn.setVisible(true);
        cancelBtn.setVisible(false);
    }

    @FXML
    void chargeEditPet(MouseEvent event) {
        selectPet = petTable.getSelectionModel().getSelectedItem();
        if (selectPet != null) {
            nameField.setText(selectPet.getName());
            racePet.setText(selectPet.getBreed());
            sexCbx.setValue(selectPet.getSex());
            specieCbx.setValue(selectPet.getSpecies());
            yearBornField.setText(String.valueOf(selectPet.getAge()));
            clientCbx.setValue(selectPet.getOwner());
            statusCbx.setValue(selectPet.getStatus());
        }
        updateBtn.setVisible(true);
        registrarPetBtn.setVisible(false);
        cancelBtn.setVisible(true);
    }

    @FXML
    void filterPet(KeyEvent event) {
        String filterText = buscarPetField.getText().toLowerCase();

        filteredPets.setPredicate(pet -> {
            if (filterText == null || filterText.isEmpty()) {
                return true;
            }
            return pet.getName().toLowerCase().contains(filterText) ||
                   pet.getBreed().toLowerCase().contains(filterText) ||
                   pet.getSpecies().toString().toLowerCase().contains(filterText) ||
                   pet.getSex().toString().toLowerCase().contains(filterText) ||
                   pet.getStatus().toString().toLowerCase().contains(filterText) ||
                   String.valueOf(pet.getAge()).contains(filterText) ||
                   pet.getOwner().getName().toLowerCase().contains(filterText);
        });

        petTable.setItems(filteredPets);
    }

    @FXML
    void registerPet(ActionEvent event) {

    
        String name = nameField.getText();
        PetType species = specieCbx.getValue();
        String race = racePet.getText();
        Sex sex = sexCbx.getValue();
        String yearbornString = yearBornField.getText();
        Clients client = clientCbx.getValue();
        StatusPet status = statusCbx.getValue();

        if (name.isEmpty() || species == null || race.isEmpty() || sex == null || yearbornString.isEmpty()|| client == null || status == null){
            popUp("The operation was not successful. Please fill all fields.");
            

        }else{
            int yearborn = Integer.parseInt(yearbornString);
            System.out.println(yearborn);
            Pets newPet = new Pets(name, species, race, sex, yearborn, status, client);
            petsDao.insertar(newPet);
            petsData.add(newPet);
            super.uploadDataPets(petsData);
            popUp("Successful operation");
            updateTable();
        }
            

    }

    
    @FXML
    void updatePet() {

        Sex sex = sexCbx.getValue();
        StatusPet status = statusCbx.getValue();
        Clients client = clientCbx.getValue();
        
        selectPet.setName(nameField.getText());
        selectPet.setSpecies(specieCbx.getValue());
        selectPet.setBreed(racePet.getText());
        selectPet.setSex(sex);
        selectPet.setAge(Integer.parseInt(yearBornField.getText()));
        selectPet.setOwner(client);
        selectPet.setStatus(status);
    
        petsDao.actualizar(selectPet);
        super.uploadDataPets(petsData);
        popUp("Successful operation");
        updateTable();
        cleanFields();
    }
    

    private void popUp(String msg) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(msg);

        alert.showAndWait();
    }

    public void updateTable() {
        petTable.setItems(petsData);
        cleanFields();
    }

    private void cleanFields() {
        nameField.clear();
        racePet.clear();
        sexCbx.setValue(null);
        specieCbx.setValue(null);
        yearBornField.clear();
        clientCbx.setValue(null);
        statusCbx.setValue(null);
    }
}
