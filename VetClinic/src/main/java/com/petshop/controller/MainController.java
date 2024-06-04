package com.petshop.controller;

import java.io.IOException;

import com.petshop.App;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainController {

    @FXML
    private Button DocBtn;

    @FXML
    private Button chipBtn;

    @FXML
    private Button clientBtn;

    @FXML
    private Button historyBtn;

    @FXML
    private Button petBtn;

    public void initialize(){
           
    }

    @FXML
    private void goChip() throws IOException {
        App.setRoot("chip");
    }

    @FXML
    private void goClient() throws IOException {
        App.setRoot("client");
    }

    @FXML
    private void goDoc() throws IOException {
        App.setRoot("vetDoc");
    }

    @FXML
    private void goHistory() throws IOException {
        App.setRoot("historyPet");
    }

    @FXML
    private void goPet() throws IOException {
        App.setRoot("pet");
    }

}
