package com.petshop.model;

import java.sql.Date;
import java.time.LocalDate;

public class HistoriesPet {

    private int idHistory;
    private Clients client;
    private Pets pet;
    private VetDoctors vetDoctor;
    private String description;
    private String diagnostic;
    private LocalDate dateOfConsult;


    
    public HistoriesPet() {
    }

    
    
    public HistoriesPet(Clients client, Pets pet, VetDoctors vetDoctor, String description, String diagnostic,
            LocalDate dateOfConsult) {
        this.client = client;
        this.pet = pet;
        this.vetDoctor = vetDoctor;
        this.description = description;
        this.diagnostic = diagnostic;
        this.dateOfConsult = dateOfConsult;
    }



    public int getIdHistory() {
        return idHistory;
    }
    public void setIdHistory(int idHistory) {
        this.idHistory = idHistory;
    }
    public Clients getClient() {
        return client;
    }
    public void setClient(Clients client) {
        this.client = client;
    }
    public Pets getPet() {
        return pet;
    }
    public void setPet(Pets pet) {
        this.pet = pet;
    }
    public VetDoctors getVetDoctor() {
        return vetDoctor;
    }
    public void setVetDoctor(VetDoctors vetDoctor) {
        this.vetDoctor = vetDoctor;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getDiagnostic() {
        return diagnostic;
    }
    public void setDiagnostic(String diagnostic) {
        this.diagnostic = diagnostic;
    }
    public LocalDate getDateOfConsult() {
        return dateOfConsult;
    }
    public void setDateOfConsult(LocalDate dateOfConsult) {
        this.dateOfConsult = dateOfConsult;
    }

    public Date changeType(LocalDate date){
        Date dateDB = Date.valueOf(date);
        return dateDB;
    }
    

    
}
