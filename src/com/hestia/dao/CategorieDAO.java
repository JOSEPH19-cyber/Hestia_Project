/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hestia.dao;

// Les imports
import com.hestia.config.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.hestia.model.Categories;

/**
 *
 * @author hp
 */
public class CategorieDAO {
    
    // Méthode pour enregistrer les catégories
        public boolean addCategory(Categories cat) {
        String sql = "INSERT INTO Categories (category_type, nightly_price, max_capacity) VALUES (?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnect();
             PreparedStatement pst = con.prepareStatement(sql)) {

            // On remplit les ? avec les données de l'objet 'cat'
            pst.setString(1, cat.getCategorytype());
            pst.setDouble(2, cat.getNightlyprice());
            pst.setInt(3, cat.getMaxcapacity());

            // On renvoie les nombres des lignes insérées
            return pst.executeUpdate() > 0; 

        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout : " + e.getMessage());
        }
         return false;
    }
        
    // Méthode pour vérifier si la catégorie existe déjà
    public boolean isTypeDuplicate(String category_type) {
        String sql = "SELECT COUNT(*) FROM Categories WHERE LOWER(category_type) = LOWER(?)";

        try (Connection con = DatabaseConnection.getConnect();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, category_type.trim());

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    // Si le compte est supérieur à 0, ça veut dire que le nom existe déjà
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur de vérification : " + e.getMessage());
        }
        return false; 
    }
}
