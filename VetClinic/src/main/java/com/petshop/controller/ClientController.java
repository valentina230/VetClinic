package com.petshop.controller;

import java.io.IOException;

import com.petshop.App;
import com.petshop.model.Clients;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ClientController extends BaseController{

    @FXML
    private CheckBox addressChk;

    @FXML
    private CheckBox lastNameChk;

    @FXML
    private TableColumn<Clients, String> addressClientCol;

    @FXML
    private TextField addressField;

    @FXML
    private Button cancelBtn;

    @FXML
    private TableView<Clients> clientTable;

    @FXML
    private CheckBox emailChk;

    @FXML
    private TableColumn<Clients, String> emailClientCol;

    @FXML
    private TextField emailField;

    @FXML
    private TableColumn<Clients, Integer> idClientCOL;

    @FXML
    private CheckBox identificationChk;

    @FXML
    private TableColumn<Clients, String> identificationClientCol;

    @FXML
    private TableColumn<Clients, String> lastNameClientCol;

    @FXML
    private TextField lastNameField;

    @FXML
    private CheckBox nameChk;

    @FXML
    private TableColumn<Clients, String> nameClientCol;

    @FXML
    private TextField nameField;

    @FXML
    private TextField numIdentificationField;

    @FXML
    private CheckBox phoneChk;

    @FXML
    private TableColumn<Clients, String> phoneClientCol;

    @FXML
    private TextField phoneField;

    @FXML
    private Button registerClientBtn;

    @FXML
    private Button returnBtn;

    @FXML
    private TextField searchClientField;

    @FXML
    private Button updateBtn;

    @FXML
    private CheckBox usuarioActivoChk;

    // Create objects and list
    private Clients selectClient;
    private ObservableList<Clients> clientsData = FXCollections.observableArrayList(); 
    private FilteredList<Clients> filteredClients; 

    public ClientController(){
        super();
    }

    public void initialize(){
        idClientCOL.setCellValueFactory(new PropertyValueFactory<>("idClient"));
        nameClientCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        lastNameClientCol.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        emailClientCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneClientCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        identificationClientCol.setCellValueFactory(new PropertyValueFactory<>("identification"));
        addressClientCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        
        super.uploadDataClients(clientsData);
        System.out.println("Contenido de clientsData: " + clientsData);
        clientTable.setItems(clientsData);
    }


    @FXML
    void cancel() {
        cleanFields();
        updateBtn.setVisible(false);
        registerClientBtn.setVisible(true);
        cancelBtn.setVisible(false);
    }

    @FXML
    void chargeEditClient() {
        selectClient = clientTable.getSelectionModel().getSelectedItem();
        if (selectClient != null) {
            nameField.setText(selectClient.getName());
            lastNameField.setText(selectClient.getLastname());
            numIdentificationField.setText(selectClient.getIdentification());
            emailField.setText(selectClient.getEmail());
            phoneField.setText(selectClient.getPhone());
            addressField.setText(selectClient.getAddress());
        }
        updateBtn.setVisible(true);
        registerClientBtn.setVisible(false);
        cancelBtn.setVisible(true);
    }

    @FXML
    public void filterClient() {
        if (filteredClients == null) {
            filteredClients = new FilteredList<>(clientsData, p -> true);
        }
        String filterText = searchClientField.getText().toLowerCase();

        filteredClients.setPredicate(client -> {
            // Si el filtro está vacío, mostrar todos los elementos
            if (filterText == null || filterText.isEmpty()) {
                return true;
            }

            boolean nameMatch = nameChk.isSelected() && client.getName().toLowerCase().contains(filterText);
            boolean lastNameMatch = lastNameChk.isSelected() && client.getLastname().toLowerCase().contains(filterText);
            boolean identificationMatch = identificationChk.isSelected()
                    && client.getIdentification().toLowerCase().contains(filterText);
            boolean emailMatch = emailChk.isSelected() && client.getEmail().toLowerCase().contains(filterText) ;
            boolean phoneMatch = phoneChk.isSelected() && client.getPhone().contains(filterText);
            boolean addressMatch = addressChk.isSelected() && client.getAddress().toLowerCase().contains(filterText);

            // Verificar si el autor coincide con alguno de los campos seleccionados
            return nameMatch || lastNameMatch || identificationMatch || emailMatch || phoneMatch || addressMatch;
        });

        clientTable.setItems(filteredClients);
    }

    @FXML
    private void goMenu() throws IOException {
        App.setRoot("main");
    }

    @FXML
    void updateClient() {
        String name = nameField.getText();
        String lastName = lastNameField.getText();
        String identification = numIdentificationField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        String address = addressField.getText();

        selectClient.setName(name);
        selectClient.setLastname(lastName);
        selectClient.setIdentification(identification);
        selectClient.setEmail(email);
        selectClient.setPhone(phone);
        selectClient.setAddress(address);

        clientDao.actualizar(selectClient);
        super.uploadDataClients(clientsData);
        updateTable();
    }

    @FXML
    public void registerClient () {
        String name = nameField.getText();
        String lastName = lastNameField.getText();
        String identification = numIdentificationField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        String address = addressField.getText();

        Clients newClient = new Clients(name, lastName, email, phone, identification, address);
        // Llamamos al metodo insertar que inserta a la base de datos
        clientDao.insertar(newClient);
        // Agregar a el observableList
        clientsData.add(newClient);
        popUpSuccess("Successful operation");
        updateTable();
    }

    private void popUpSuccess(String msg) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Éxito");
        alert.setHeaderText(null);
        alert.setContentText(msg);

        // Mostrar la ventana de alerta y esperar hasta que el usuario la cierre
        alert.showAndWait();
    }

    public void updateTable() {
        System.out.println("Contenido de clientsData: " + clientsData);
        clientTable.setItems(clientsData);
        cleanFields();
    }

    private void cleanFields() {
        nameField.clear();
        lastNameField.clear();
        emailField.clear();
        phoneField.clear();
        addressField.clear();
        numIdentificationField.clear();
    }

    public void cleanTable() {
        clientsData.clear();
    }

    public void deleteClient(Clients client) {
        clientsData.remove(client);
    }
}
