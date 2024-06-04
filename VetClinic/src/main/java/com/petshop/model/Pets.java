package com.petshop.model;

public class Pets {

    private int idPet;
    private String name;
    private PetType species;
    private String breed;
    private Sex sex;
    private int age;
    private StatusPet status;
    private Clients owner;


    public Pets(String name, PetType species, String breed, Sex sex, int age, StatusPet status, Clients owner) {
   
        this.name = name;
        this.species = species;
        this.breed = breed;
        this.sex = sex;
        this.age = age;
        this.status = status;
        this.owner = owner;
    }

    public Pets() {
    }

    public int getIdPet() {
        return idPet;
    }


    public void setIdPet(int idPet) {
        this.idPet = idPet;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public PetType getSpecies() {
        return species;
    }


    public void setSpecies(PetType species) {
        this.species = species;
    }


    public String getBreed() {
        return breed;
    }


    public void setBreed(String breed) {
        this.breed = breed;
    }


    public Sex getSex() {
        return sex;
    }


    public void setSex(Sex sex) {
        this.sex = sex;
    }


    public int getAge() {
        return age;
    }


    public void setAge(int age) {
        this.age = age;
    }
    
    public Clients getOwner() {
        return owner;
    }

    public void setOwner(Clients owner) {
        this.owner = owner;
    }

    public StatusPet getStatus() {
        return status;
    }

    public void setStatus(StatusPet status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "" + name + ", " + species + ", OWNER: " + owner.getName();
    }
}
