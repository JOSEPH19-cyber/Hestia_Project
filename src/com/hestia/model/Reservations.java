/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hestia.model;

/**
 *
 * @author hp
 */
public class Reservations {
    
    // Déclarations des attributs
    private int user_id;
    private String username;
    private String password;
    private String role;
    
    // Constructeur vise
    public Reservations(){}
    
    // Constructeur pour l'insertion
    public Reservations(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
    
    // Constructeur pour la récupération
    public Reservations (int user_id, String username, String password, String role)
    {
        this.user_id = user_id;
        this.username = username;
        this.password = password;
        this.role = role;
    }
    
    // Getters
    public int getUserid(){return user_id;}
    public String getUsername(){return username;}
    public String getPassword(){return password;}
    public String getRole(){return role;}
    
    // Setters
    public void setUserid(int user_id){this.user_id = user_id;}
    public void setUsername(String username){this.username = username;}
    public void setPassword(String password){this.password = password;}
    public void setRole(String role){this.role = role;}
}
