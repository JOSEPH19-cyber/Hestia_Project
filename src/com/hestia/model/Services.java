/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hestia.model;

/**
 *
 * @author hp
 */
public class Services {
    
    // Déclarations des attributs
    private int service_id;
    private double unit_price;
    private String service_type;
    
    // Constructeur vise
    public Services(){}
    
    // Constructeur pour l'insertion
    public Services(double unit_price, String service_type) {
        this.unit_price = unit_price;
        this.service_type = service_type;
    }
    
    // Constructeur pour la récupération
    public Services (int service_id, double unit_price, String service_type)
    {
        this.service_id = service_id;
        this.unit_price = unit_price;
        this.service_type = service_type;
    }
    // Getters
    
    public int getServiceid(){return service_id;}
    public double getUnitprice(){return unit_price;}
    public String getServicetype(){return service_type;}
    
    // Setters
    public void setServiceid(int service_id){this.service_id = service_id;}
    public void setUnitprice(double unit_price){this.unit_price = unit_price;}
    public void setPassword(String service_type){this.service_type = service_type;}
}
