package com.petshop.model;

import java.sql.Date;
import java.time.LocalDate;

public class Chips {

    private int idChip;
    private int idFactory;
    private Technology technology;
    private StatusChip status;
    private LocalDate dateOfInstalation;
    private LocalDate expirationDate;
    private Pets pet;
    
    public Chips(int idFactory, Technology technology, StatusChip status, LocalDate dateOfInstalation,
        LocalDate expirationDate, Pets pet) {
        this.idFactory = idFactory;
        this.technology = technology;
        this.status = status;
        this.dateOfInstalation = dateOfInstalation;
        this.expirationDate = expirationDate;
        this.pet = pet;
    }

    public Chips() {
    }

    public int getIdChip() {
        return idChip;
    }
    
    public void setIdChip(int idChip) {
        this.idChip = idChip;
    }

    public Technology getTechnology() {
        return technology;
    }
    public void setTechnology(Technology technology) {
        this.technology = technology;
    }
    public StatusChip getStatus() {
        return status;
    }
    public void setStatus(StatusChip status) {
        this.status = status;
    }
 
    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public LocalDate getDateOfInstalation() {
        return dateOfInstalation;
    }

    public void setDateOfInstalation(LocalDate dateOfInstalation) {
        this.dateOfInstalation = dateOfInstalation;
    }

    public int getIdFactory() {
        return idFactory;
    }

    public void setIdFactory(int idFactory) {
        this.idFactory = idFactory;
    }

    @Override
    public String toString() {
        return "--" + idChip + "-- " + idFactory + " petName= " + pet.getName() + "OWNER:" + pet.getOwner().getName() + ", Expiración()=" + getExpirationDate() + "]";
    }

    public Date changeType(LocalDate date){
        Date dateDB = Date.valueOf(date);
        return dateDB;
    }

    public Pets getPet() {
        return pet;
    }

    public void setPet(Pets pet) {
        this.pet = pet;
    }
}
