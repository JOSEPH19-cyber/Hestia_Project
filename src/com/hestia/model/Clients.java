/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hestia.model;

/**
 *
 * @author hp
 */
public class Clients {
    
    // Déclarations des variables
    private int client_id;
    private String last_name;
    private String first_name;
    private String phone;
    private String email;
    private String identify_number;
    
    // Constructeur vide
    public Clients(){}
    
    // Constructeur pour l'insertion
    public Clients(String last_name, String first_name, String phone, String email, String identify_number)
    {
        this.last_name = last_name;
        this.first_name = first_name;
        this.phone = phone;
        this.email = email;
        this.identify_number = identify_number;
    }
    
    // Constructeur pour la récupération
    public Clients(int client_id, String last_name, String first_name, String phone, String email, String identify_number)
    {
        this.client_id = client_id;
        this.last_name = last_name;
        this.first_name = first_name;
        this.phone = phone;
        this.email = email;
        this.identify_number = identify_number;
    }
    
    // Getters
    public int getClientid(){return client_id;}
    public String getLastname(){return last_name;}
    public String getFirstname(){return first_name;}
    public String getPhone(){return phone;}
    public String getEmail(){return email;}
    public String getIdentifynumber(){return identify_number;}
    
    // Setters 
    public void setClientid(int client_id){this.client_id = client_id;}
    public void setLastname(String last_name){this.last_name = last_name;}
    public void setFirstname(String first_name){this.first_name = first_name;}
    public void setPhone(String phone){this.phone = phone;}
    public void setEmail(String email){this.email =email;}
    public void setIdentifynumber(String identify_number){this.identify_number = identify_number;}
}
