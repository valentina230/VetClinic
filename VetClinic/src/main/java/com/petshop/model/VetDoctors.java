package com.petshop.model;

// Heredando de la clase User 😊
public class VetDoctors extends User {

    private int idVetDoctor;
    private VetSpecialization specialization;
    private Working status;

    

    @Override
    public String toString() {
        return " " + getName()  + " " + getLastname()+ " " +  getIdVetDoctor();
    }

    
    

    public VetDoctors() {
        
    }




    public VetDoctors(String name, String lastName, String email, String phone, String identification, String address, VetSpecialization specialization, Working status) {
        super(name, lastName, email, phone, identification, address);
        this.specialization = specialization;
        this.status = status;
    }




    public Working getStatus() {
        return status;
    }

    public void setStatus(Working status) {
        this.status = status;
    }

    public int getIdVetDoctor() {
        return idVetDoctor;
    }

    public void setIdVetDoctor(int idVetDoctor) {
        this.idVetDoctor = idVetDoctor;
    }




    public VetSpecialization getSpecialization() {
        return specialization;
    }




    public void setSpecialization(VetSpecialization specialization) {
        this.specialization = specialization;
    }
    
}
