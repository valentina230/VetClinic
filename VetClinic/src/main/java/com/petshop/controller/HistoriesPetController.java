package com.petshop.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;

import com.petshop.App;
import com.petshop.model.Clients;
import com.petshop.model.HistoriesPet;
import com.petshop.model.Pets;
import com.petshop.model.VetDoctors;

import javafx.beans.property.SimpleStringProperty;
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

public class HistoriesPetController extends BaseController {

    @FXML
    private TableColumn<HistoriesPet, Integer> IdHistoryPetCol;

    @FXML
    private TableColumn<HistoriesPet, String> idPetCol;

    @FXML
    private Button cancelBtn;

    @FXML
    private ComboBox<Clients> clientCbx;

    @FXML
    private CheckBox dateChk;

    @FXML
    private TableColumn<HistoriesPet, LocalDate> dateCol;

    @FXML
    private TextField dateConsultField;

    @FXML
    private TextField descriptionFiled;

    @FXML
    private TableColumn<HistoriesPet, String> descriptionHistoryCol;

    @FXML
    private TextField diagnosticField;

    @FXML
    private ComboBox<VetDoctors> docCbx;

    @FXML
    private TableView<HistoriesPet> historiesTable;

    @FXML
    private TableColumn<HistoriesPet, String> nameClientCol;

    @FXML
    private TableColumn<HistoriesPet, String> diagnosticVetCol;

    @FXML
    private TableColumn<HistoriesPet, String> petNameCol;

    @FXML
    private CheckBox nameClientChk;

    @FXML
    private CheckBox nameDocChk;

    @FXML
    private TableColumn<HistoriesPet, String> nameDocCol;

    @FXML
    private CheckBox namePetChk;

    @FXML
    private ComboBox<Pets> petCbx;

    @FXML
    private Button registerHistoryBtn;

    @FXML
    private Button returnBtn;

    @FXML
    private TextField searchHistoryField;

    @FXML
    private Button updateBtn;

    @FXML
    private CheckBox usuarioActivoChk;

    private HistoriesPet selectedHistoriesPet;
    private ObservableList<HistoriesPet> filterHistoryPet;
    private ObservableList<HistoriesPet> historiesData = FXCollections.observableArrayList();

    public HistoriesPetController() {
        super();
    }

    public void initialize() {
        super.uploadDataHistories(historiesData);
        historiesTable.setItems(historiesData);

        ObservableList<VetDoctors> vetDoctorsCbox = FXCollections.observableArrayList(vetDoctorsDao.buscarTodos());
        ObservableList<Pets> petsCbox = FXCollections.observableArrayList(petsDao.buscarTodos());
        ObservableList<Clients> clientsCbox = FXCollections.observableArrayList(clientDao.buscarTodos());

        docCbx.getItems().setAll(vetDoctorsCbox);
        petCbx.getItems().setAll(petsCbox);
        clientCbx.getItems().setAll(clientsCbox);

        IdHistoryPetCol.setCellValueFactory(new PropertyValueFactory<>("idHistory"));
        nameClientCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClient().getName()));
        idPetCol.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getPet().getIdPet())));
        nameDocCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getVetDoctor().getName()));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("dateOfConsult"));
        descriptionHistoryCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        diagnosticVetCol.setCellValueFactory(new PropertyValueFactory<>("diagnostic"));
        petNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getPet().getName())));


        filterHistoryPet = new FilteredList<>(historiesData, p -> true);
    }

    @FXML
    void cancel(ActionEvent event) {
        cleanFields();
    }

    @FXML
    void registerHistory(ActionEvent event) {
        String description = descriptionFiled.getText();
        String dateConsult = dateConsultField.getText();
        Pets selectedPet = petCbx.getValue();
        Clients selectedClient = clientCbx.getValue();
        VetDoctors selectedDoctor = docCbx.getValue();
        String diagnostic = diagnosticField.getText();

        if (description.isEmpty() || dateConsult.isEmpty() || selectedPet == null || selectedClient == null || selectedDoctor == null || diagnostic.isEmpty()) {
            popUp("Please fill all fields.");
        } else {
            


            updateTable();
        }
    }

    private void popUp(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void updateTable() {
        historiesTable.setItems(historiesData);
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
    void updateHistory(ActionEvent event) {
        Clients client = clientCbx.getValue();
        Pets pet = petCbx.getValue();
        VetDoctors vetDoctors = docCbx.getValue();

        LocalDate dateOfAppoiment = LocalDate.parse(dateConsultField.getText(), formatter());
        String description = descriptionFiled.getText();
        String diagnistic = diagnosticField.getText();

        // Update Object

        selectedHistoriesPet.setClient(client);
        selectedHistoriesPet.setPet(pet);
        selectedHistoriesPet.setDateOfConsult(dateOfAppoiment);
        selectedHistoriesPet.setVetDoctor(vetDoctors);
        selectedHistoriesPet.setDescription(description);
        selectedHistoriesPet.setDiagnostic(diagnistic);


        historiesDao.actualizar(selectedHistoriesPet);
        super.uploadDataHistories(historiesData);
        popUp("Successful operation");
        updateTable();
        cleanFields();
    }

    private void cleanFields() {
        descriptionFiled.clear();
        dateConsultField.clear();
        petCbx.getSelectionModel().clearSelection();
        clientCbx.getSelectionModel().clearSelection();
        docCbx.getSelectionModel().clearSelection();
        diagnosticField.clear();
    }

    @FXML
    private void goMenu() throws IOException {
        App.setRoot("main");
    }

    @FXML
    void filterHistoryPet() {
        if (filterHistoryPet == null) {
            filterHistoryPet = new FilteredList<>(historiesData , p -> true);
        }

        String filterText = searchHistoryField.getText().toLowerCase();

        ((FilteredList<HistoriesPet>) filterHistoryPet).setPredicate(history -> {
            if (filterText.isEmpty()) {
                return true; // Si no hay texto de filtro, mostrar todos los elementos
            }
            // Verificar si alguno de los campos coincide con el texto de filtro
            return history.getClient().getName().toLowerCase().contains(filterText)
                    || history.getPet().getName().toLowerCase().contains(filterText)
                    || history.getVetDoctor().getName().toLowerCase().contains(filterText)
                    || history.getDateOfConsult().toString().toLowerCase().contains(filterText)
                    || history.getDescription().toLowerCase().contains(filterText)
                    || history.getDiagnostic().toLowerCase().contains(filterText);
        });

        historiesTable.setItems(filterHistoryPet);
    }

    @FXML
    void chargeEditHistory() {
        selectedHistoriesPet = historiesTable.getSelectionModel().getSelectedItem();
        if (selectedHistoriesPet != null) {
            dateConsultField.setText(selectedHistoriesPet.getDateOfConsult().toString());
            clientCbx.setValue(selectedHistoriesPet.getClient());
            petCbx.setValue(selectedHistoriesPet.getPet());
            docCbx.setValue(selectedHistoriesPet.getVetDoctor());
            diagnosticField.setText(selectedHistoriesPet.getDiagnostic());
            descriptionFiled.setText(selectedHistoriesPet.getDescription());
            diagnosticField.setText(selectedHistoriesPet.getDiagnostic());

        }

        updateBtn.setVisible(true);
        registerHistoryBtn.setVisible(false);
        cancelBtn.setVisible(true);
    }
}
