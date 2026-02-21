/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hestia.dao;

// Les imports
import com.hestia.config.DatabaseConnection;
import com.hestia.model.Rooms;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hp
 */
public class RoomDAO {
    
    // Méthode pour vérifier si une chambre exite déjà
    public boolean isRoomNumberExists(String roomNumber) {
        
        String sql = "SELECT COUNT(*) FROM Rooms WHERE room_number = ?";
        
        try (Connection con = DatabaseConnection.getConnect();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, roomNumber);
            ResultSet rs = pst.executeQuery();

            // Retourne true si le compte est supérieur à 0
            if (rs.next()) {
                return rs.getInt(1) > 0; 
            }
        } catch (SQLException ex) {
            System.err.println("Erreur vérification doublon : " + ex.getMessage());
        }
        return false;
    }
    
    // Méthode pour ajouter une chambre
    public boolean addRoom(Rooms room) {
        String sql = "INSERT INTO Rooms (room_number, category_id, status) VALUES (?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnect();
             PreparedStatement pst = con.prepareStatement(sql)) 
        {  

            pst.setString(1, room.getRoomnumber());
            pst.setInt(2, room.getCategoryid());
            pst.setString(3, room.getStatus());

            // Rétourner le nombre des lignes inésérées
            return pst.executeUpdate() > 0; 

        } catch (SQLException ex) {
            System.err.println("Erreur RoomDAO (Enregistrement) : " + ex.getMessage());
            return false;
        }
    }
    
    // Méthode pour afficher les chambres
    public List<Rooms> getAllRooms() {
        
        List<Rooms> list = new ArrayList<>();
        
        String sql = "SELECT r.*, c.category_type FROM Rooms r " +
                     "INNER JOIN Categories c ON r.category_id = c.category_id";

        try (Connection con = DatabaseConnection.getConnect();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                Rooms r = new Rooms();
                r.setRoomid(rs.getInt("room_id"));
                r.setRoomnumber(rs.getString("room_number"));
                r.setCategoryid(rs.getInt("category_id"));
                r.setStatus(rs.getString("status"));
                r.setCategorytype(rs.getString("category_type"));

                list.add(r);
            }
        } catch (SQLException ex) {
            System.err.println("Erreur getAllRooms : " + ex.getMessage());
        }
        return list;
    }
    
    // Méthode pour ajouter une chambre
    public boolean updateRoom(Rooms room) {
        String sql = "UPDATE Rooms SET room_number = ?, category_id = ?, status = ? WHERE room_id = ?";

        try (Connection con = DatabaseConnection.getConnect();
             PreparedStatement pst = con.prepareStatement(sql)) 
        {  

            pst.setString(1, room.getRoomnumber());
            pst.setInt(2, room.getCategoryid());
            pst.setString(3, room.getStatus());
            pst.setInt(4, room.getRoomid());

            // Rétourner le nombre des lignes inésérées
            return pst.executeUpdate() > 0; 

        } catch (SQLException ex) {
            System.err.println("Erreur RoomDAO (Enregistrement) : " + ex.getMessage());
            return false;
        }
    }
}
