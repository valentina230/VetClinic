package com.petshop.model;

import java.util.ArrayList;
import java.util.List;

public class Clients extends User{

    private int idClient;
    
    public Clients(String name, String lastname, String email, String phone, String identification, String address) {
        super(name, lastname, email, phone, identification, address);
    }

    public static List<String> getAllClientNames() {
        List<String> clientNames = new ArrayList<>();
        for (Clients client : getAllClients()) {
            clientNames.add(client.getName());
        }
        return clientNames;
    }

    public static List<Clients> getAllClients() {
        List<Clients> clients = new ArrayList<>();
        return clients;
    }
    
    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    @Override
    public String toString() {
        return getName() + " " + getLastname() + " " + getIdClient();
    }

    public String getNamesClients(){
        return null;
    }
    
}
