package com.petshop.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.Locale;

import com.petshop.App;
import com.petshop.model.Chips;
import com.petshop.model.Pets;
import com.petshop.model.StatusChip;
import com.petshop.model.Technology;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
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
import javafx.scene.text.Text;

public class ChipsController extends BaseController{

    @FXML
    private ComboBox<StatusChip> activeCbx;

    @FXML
    private Button cancelBtn;

    @FXML
    private TableView<Chips> chipTable;

    @FXML
    private CheckBox dateExpirationChk;

    @FXML
    private TextField dateExpirationField;

    @FXML
    private TableColumn<Chips, LocalDate> dateExplirationCol;

    @FXML
    private CheckBox dateInstalationChk;

    @FXML
    private TableColumn<Chips, LocalDate> dateInstalationCol;

    @FXML
    private TextField dateInstalationField;

    @FXML
    private TableColumn<Chips, Integer> idChipCol;

    @FXML
    private TextField idChipField;

    @FXML
    private TableColumn<Chips, Integer> idFactoryChipCol;

    @FXML
    private CheckBox idFactoryChk;

    @FXML
    private CheckBox identificationClientChk;

    @FXML
    private TableColumn<Chips, String> identificationCol;

    @FXML
    private ComboBox<Pets> petCbx;

    @FXML
    private CheckBox petNameChk;

    @FXML
    private TableColumn<Chips, String> petNameCol;

    @FXML
    private Button registerChipBtn;

    @FXML
    private Button returnBtn;

    @FXML
    private TextField searchChipField;

    @FXML
    private CheckBox statusChk;
    
    @FXML
    private CheckBox identificationChk;
    
    @FXML
    private TableColumn<Chips, StatusChip> statusCol;

    @FXML
    private ComboBox<Technology> technologyCbx;

    @FXML
    private TableColumn<Chips, Technology> technologyChipCol;

    @FXML
    private CheckBox technologyChk;
    
    @FXML
    private Text dateExpirationTxt;

    @FXML
    private Button updateBtn;

    @FXML
    private CheckBox usuarioActivoChk;
 
    private Chips selectedChips;
    private ObservableList<Chips> chipsData = FXCollections.observableArrayList();
    private ObservableList<Pets> petsData = FXCollections.observableArrayList();
    private ObservableList<Chips> filteredChips;

    public ChipsController(){
        super();
    }

    public void initialize(){
        uploadDataPets(petsData);
        petCbx.getItems().setAll(petsData);
        technologyCbx.getItems().setAll(Technology.values());
        activeCbx.getItems().setAll(StatusChip.values());

        idChipCol.setCellValueFactory(new PropertyValueFactory<>("idChip"));
        idFactoryChipCol.setCellValueFactory(new PropertyValueFactory<>("idFactory"));
        technologyChipCol.setCellValueFactory(new PropertyValueFactory<>("technology"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        dateInstalationCol.setCellValueFactory(new PropertyValueFactory<>("dateOfInstalation"));
        dateExplirationCol.setCellValueFactory(new PropertyValueFactory<>("expirationDate"));


        petNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPet().getName())); 
        identificationCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPet().getOwner().getIdentification()));
        
        uploadDataChips(chipsData);
        chipTable.setItems(chipsData);
    
    }

    @FXML
    void cancel() {
        cleanFields();
        updateBtn.setVisible(false);
        registerChipBtn.setVisible(true);
        cancelBtn.setVisible(false);
        dateExpirationField.setVisible(false);
        dateExpirationTxt.setVisible(false);
    }

    private void cleanFields() {
        idChipField.clear();
        technologyCbx.getSelectionModel().clearSelection();
        activeCbx.getSelectionModel().clearSelection();
        petCbx.getSelectionModel().clearSelection();
        dateInstalationField.clear();
        dateExpirationField.clear();
    }

    @FXML
    void chargeEditChip() {
        selectedChips = chipTable.getSelectionModel().getSelectedItem();
        if (selectedChips != null) {
            dateExpirationField.setVisible(true);
            dateExpirationTxt.setVisible(true);
            
            registerChipBtn.setVisible(false);
            updateBtn.setVisible(true);
            cancelBtn.setVisible(true);

            idChipField.setText(String.valueOf(selectedChips.getIdFactory()));
            technologyCbx.setValue(selectedChips.getTechnology());
            activeCbx.setValue(selectedChips.getStatus());
            petCbx.setValue(selectedChips.getPet());
            dateInstalationField.setText(selectedChips.getDateOfInstalation().toString());
            if (selectedChips.getExpirationDate() != null) {
                dateExpirationField.setText(selectedChips.getExpirationDate().toString());
            } else{
                dateExpirationField.clear();
            }
        }
    }

    @FXML
    void filterChips() {
        if (filteredChips == null) {
            filteredChips = new FilteredList<>(chipsData, p -> true);
        }
        String filterText = searchChipField.getText().toLowerCase();
        
        ((FilteredList<Chips>) filteredChips).setPredicate(chip -> {
            if (filterText == null || filterText.isEmpty()) {
                return true;
            }

            Boolean idFactoryMatch = idFactoryChk.isSelected() && (chip.getIdFactory() == Integer.parseInt(filterText)); 
            Boolean technologyMatch = technologyChk.isSelected() && chip.getTechnology().toString().toLowerCase().contains(filterText);
            Boolean statusMatch =  statusChk.isSelected() && chip.getStatus().toString().toLowerCase().contains(filterText);
            Boolean dateInstalationMatch = dateInstalationChk.isSelected() && (chip.getDateOfInstalation().toString().contains(filterText));
            Boolean dateExpirationMatch;
            if (chip.getExpirationDate() != null) {
                dateExpirationMatch = dateExpirationChk.isSelected() && (chip.getExpirationDate().toString().contains(filterText));
            } else {
                dateExpirationMatch = false;
            }
            Boolean petNameMatch = petNameChk.isSelected() && chip.getPet().getName().contains(filterText);
            Boolean identificationMatch = identificationChk.isSelected() && (chip.getPet().getOwner().getIdentification().contains(filterText));

            return idFactoryMatch || technologyMatch || statusMatch || dateInstalationMatch || dateExpirationMatch || petNameMatch || identificationMatch;
        });
        chipTable.setItems(filteredChips);
    }

    @FXML
    private void goMenu() throws IOException {
        App.setRoot("main");
    }

    
    @FXML
    void registerChip() {
        String idFactoryString = idChipField.getText();
        Technology technology = technologyCbx.getValue();
        StatusChip statusChip = activeCbx.getValue();
        LocalDate dateInstalation = null;
        Pets pet = petCbx.getValue();

        if (idFactoryString.isEmpty() || technology == null || statusChip == null || dateInstalationField.getText().isEmpty() || pet == null) {
            popUp("The operation was not successful. Please fill all fields.");
        } else {
            try {
                int idFactory = Integer.parseInt(idFactoryString);
                dateInstalation = LocalDate.parse(dateInstalationField.getText(), formatter());
                Chips chip = new Chips(idFactory, technology, statusChip, dateInstalation, null, pet);
                chipsData.add(chip);
                chipDao.insertar(chip);
                super.uploadDataChips(chipsData);
                updateTable();
                popUp("Successful operation");
            } catch (NumberFormatException e) {
                e.printStackTrace();
                popUp("Error: ID Factory must be a number.");
            } catch (DateTimeParseException e) {
                e.printStackTrace();
                popUp("Error: Incorrect date format.");
            }
        }
    }


    private void updateTable() {
        chipTable.setItems(chipsData);
        cleanFields();
    }
    
    public DateTimeFormatter formatter(){
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                .appendOptional(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
                .toFormatter(Locale.ENGLISH);
        return formatter;
    }

    @FXML
    void updateChip() {

        // Covertir y obtener valores de entrada
        int idFactory = Integer.parseInt(idChipField.getText());
        Technology technology = technologyCbx.getValue();
        StatusChip status = activeCbx.getValue();
        LocalDate dateInstalation = LocalDate.parse(dateInstalationField.getText(), formatter());
        LocalDate dateExpiration = null;
        if (!(dateExpirationField.getText().isBlank()) || dateExpirationField.getText() != null) {
            dateExpiration = LocalDate.parse(dateExpirationField.getText(), formatter());
        }

        // Obtener mascota selecionada
        Pets pet = petCbx.getValue();


        // Actualizar objeto
        selectedChips.setIdFactory(idFactory);
        selectedChips.setTechnology(technology);
        selectedChips.setStatus(status);
        selectedChips.setDateOfInstalation(dateInstalation);
        selectedChips.setExpirationDate(dateExpiration);
        selectedChips.setPet(pet);

        //Actualizar base de datos e interfaz

        chipDao.actualizar(selectedChips);
        super.uploadDataChips(chipsData);
        updateTable();


        cleanFields();
        registerChipBtn.setVisible(true);
        cancelBtn.setVisible(false);
        updateBtn.setVisible(false);
        dateExpirationTxt.setVisible(false);
        dateExpirationField.setVisible(false);
    }

    private void popUp(String msg) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(msg);

        
        alert.showAndWait();
    }

}