/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hestia.model;

/**
 *
 * @author hp
 */
public class Categories {
    
    // Déclarations des attributs
    private int category_id;
    private String category_type;
    private double nightly_price;
    private int  max_capacity;
    
    // Constructeur vide
    public Categories(){}
    
    // Constructeur pour l'insertion
    public Categories(String category_type, double nightly_price, int max_capacity) {
        this.category_type = category_type;
        this.nightly_price = nightly_price;
        this.max_capacity = max_capacity;
    }
    
    // Constructeur pour la récupération
    public Categories (int category_id, String category_type, double nightly_price, int max_capacity)
    {
        this.category_id = category_id;
        this.category_type = category_type;
        this.nightly_price = nightly_price;
        this.max_capacity = max_capacity;
    }
    
    // Getters
    public int getCategoryid(){return category_id;}
    public String getCategorytype(){return category_type;}
    public double getNightlyprice(){return nightly_price;}
    public int getMaxcapacity(){return max_capacity;}
    
    // Setters
    public void setCategoryid(int category_id){this.category_id = category_id;}
    public void setCategorytype(String category_type){this.category_type = category_type;}
    public void setNightlyprice(double nightly_price){this.nightly_price = nightly_price;}
    public void setMaxcapacity(int max_capacity){this.max_capacity = max_capacity;}
}
