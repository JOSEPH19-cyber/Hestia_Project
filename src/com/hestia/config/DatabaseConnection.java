/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hestia.config;

import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author hp
 */
public class DatabaseConnection {
    
    //Les informations de connexion
    private static final String URL = "jdbc:mysql://localhost:3306/hestia_db?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    
    //La méthode pour obtenir la connexion
    public static Connection getConnect()
    {
        Connection con = null;
        try
        {
            //Chargement du driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            //Tentative de connexion
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connexion à Mysql réussie");
        }
        catch(ClassNotFoundException e)
        {
            System.out.println("Erreur : Driver Mysql non trouvé!");
        }
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null, "Erreur système : " + e.getMessage());
        }
        return con;
    }
    
    //test pour vérifier
    public static void main(String[] args)
    {
        getConnect();
    }
}
