/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hestia.model;

/**
 *
 * @author hp
 */
public class Rooms {
    
    // Déclaration des attributs
    private int room_id;
    private String room_number;
    private int category_id;
    private String status;
    
    //Attribut pour l'affcihage du type de catégorie
    private String category_type;
    
    // Constructeur vide
    public Rooms(){}
    
    // Constructeur pour la récupération 
    public Rooms(String room_number, int category_id, String status)
    {
        this.room_number = room_number;
        this.category_id = category_id;
        this.status = status;
    };
    
    
    // Constructeur complet
    public Rooms(int room_id, String room_number, int category_id, String status)
    {
        this.room_id = room_id;
        this.room_number = room_number;
        this.category_id = category_id;
        this.status = status;
    };
    
    // Getters
    public int getRoomid(){return room_id;}
    public String getRoomnumber(){return room_number;}
    public int getCategoryid(){return category_id;}
    public String getStatus(){return status;}
    public String getCategorytype() { return category_type; }
    
    // Setters
    public void setRoomid(int room_id){this.room_id = room_id;}
    public void setRoomnumber(String room_number){this.room_number = room_number;}
    public void setCategoryid(int category_id){this.category_id = category_id;}
    public void setStatus(String status){this.status = status;}
    public void setCategorytype(String category_type) { this.category_type = category_type; }
}
