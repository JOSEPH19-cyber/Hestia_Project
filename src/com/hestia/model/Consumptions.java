/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hestia.model;

/**
 *
 * @author hp
 */
public class Consumptions {
    
    // Déclarations des attributs
    private int consumption_id;
    private int reservation_id;
    private int service_id;
    private int  frequence;
    
    // Constructeur vise
    public Consumptions(){}
    
    // Constructeur pour l'insertion
    public Consumptions(int reservation_id, int service_id, int frequence) {
        this.reservation_id = reservation_id;
        this.service_id = service_id;
        this.frequence = frequence;
    }
    
    // Constructeur pour la récupération
    public Consumptions (int consumption_id, int reservation_id, int service_id, int frequence)
    {
        this.consumption_id = consumption_id;
        this.reservation_id = reservation_id;
        this.service_id = service_id;
        this.frequence = frequence;
    }
    
    // Getters
    public int getConsumptionid(){return consumption_id;}
    public int getReservationid(){return reservation_id;}
    public int getServiceid(){return service_id;}
    public int getFrequence(){return frequence;}
    
    // Setters
    public void setConsumptionid(int consumption_id){this.consumption_id = consumption_id;}
    public void setReservationid(int reservation_id){this.reservation_id = reservation_id;}
    public void setServiceid(int service_id){this.service_id = service_id;}
    public void setFrequence(int frequence){this.frequence = frequence;}
}
