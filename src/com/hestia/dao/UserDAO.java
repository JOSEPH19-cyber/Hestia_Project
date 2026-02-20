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
import com.hestia.model.Users;

/**
 *
 * @author hp
 */
public class UserDAO {
    
    // Méthode pour récupérer les rôles 
    public List<String> getRoles() {
        List<String> listRoles = new ArrayList<>(); 
        String sql = "SELECT DISTINCT role FROM Users";
        
        try (Connection con = DatabaseConnection.getConnect();
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery())
        {
            
            while(rs.next()) 
            {
                listRoles.add(rs.getString("role"));
            }
            
            rs.close();
            pst.close();
            
        } catch(SQLException e) {
            System.out.println("Erreur dans UserDAO (getRoles) : " + e.getMessage());
        }
        return listRoles;
    }
    
    //Méthode pour enregistrer les utilisateurs
    public boolean addUser(Users user)
    {
        String sql = "INSERT INTO Users (username, password, role) VALUES (?, ?, ?)";
        
        try(Connection con = DatabaseConnection.getConnect();
            PreparedStatement pst = con.prepareStatement(sql))
        {
            // On remplit les ? avec les données de l'objet 'user'
            pst.setString(1, user.getUsername());
            pst.setString(2, user.getPassword());
            pst.setString(3, user.getRole());
            
            // On renvoie le nombre de lignes insérées
            return pst.executeUpdate() > 0;
        }
        catch(SQLException e)
        {
            System.err.println("Erreur lors de l'ajout : " + e.getMessage());
            
        }
        return false;
    }
    
    // Méthode pour vérifier si l'utilisateur existe déjà
    public boolean isUsernameTaken(String username) {
        String sql = "SELECT COUNT(*) FROM Users WHERE LOWER(username) = LOWER(?)";

        try (Connection con = DatabaseConnection.getConnect();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, username.trim());

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
    
    // Méthode pour afficher les informations de l'utilisateur
    public List<Users> getAllUsers()
    {
        List<Users> liste = new ArrayList<>();
        
        // Sélectionner les informations dans la BDD
        String sql = "SELECT user_id, username, role FROM Users ORDER BY user_id DESC";
        
        // Essayer de se connecter à la BDD
        try(Connection con = DatabaseConnection.getConnect();
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery())
        {
            while(rs.next())
            {
                //Créer l'objet et remplir 
                Users u = new Users();
                u.setUserid(rs.getInt("user_id"));
                u.setUsername(rs.getString("username"));
                u.setRole(rs.getString("role"));
                
                // AJouter l'objet dans la liste
                liste.add(u);
            }
        }
        catch(SQLException e)
        {
             System.err.println("Erreur de liste : " + e.getMessage());
        }
        return liste;
    }
    
    // Méthode de modification avec mot de passe 
    public boolean updateUser(Users user)
    {
        String sql = "UPDATE Users SET username = ?, password = ?, role = ? WHERE user_id = ?";
        
        try(Connection con = DatabaseConnection.getConnect();
            PreparedStatement pst = con.prepareStatement(sql))
        {
            // On remplit les ? avec les données de l'objet 'user'
            pst.setString(1, user.getUsername());
            pst.setString(2, user.getPassword());
            pst.setString(3, user.getRole());
            pst.setInt(4, user.getUserid());
            
            // On renvoie les nombres des lignes insérées
            return pst.executeUpdate() > 0;
        }
        catch(SQLException e)
        {
            System.err.println("Erreur UPDATE (Full) : " + e.getMessage());
            
        }
        return false;
    }
    
    // Méthode de modification sans le mot de passe
    public boolean updateUserWithoutPassword(Users user)
    {
        String sql = "UPDATE Users SET username = ?, role = ? WHERE user_id = ?";
        
        try(Connection con = DatabaseConnection.getConnect();
            PreparedStatement pst = con.prepareStatement(sql))
        {
            // On remplit les ? avec les données de l'objet 'user'
            pst.setString(1, user.getUsername());
            pst.setString(2, user.getRole());
            pst.setInt(3, user.getUserid());
            
            // On renvoie le nombre de lignes insérées
            return pst.executeUpdate() > 0;
        }
        catch(SQLException e)
        {
            System.err.println("Erreur UPDATE (Basic) : " + e.getMessage());
            
        }
        return false;
    }
    
    // Méthode pour supprimer un utilisateur
    public boolean deleteUser(int id)
    {
        String sql = "DELETE FROM Users WHERE user_id = ?";
        
        try(Connection con = DatabaseConnection.getConnect();
            PreparedStatement pst = con.prepareStatement(sql))
        {
            pst.setInt(1, id);
            return pst.executeUpdate()> 0;

        }
        catch(SQLException e)
        {
            System.err.println("Erreur lors de la suppression : " + e.getMessage());
            
        }
        return false;
        
    }
    
    // Méthode pour rechercher un utilisateur
    public List<Users> searchUsers(String search) {
        List<Users> list = new ArrayList<>();
        String sql = "SELECT * FROM Users WHERE username LIKE ?"; 
        
        try(Connection con = DatabaseConnection.getConnect();
            PreparedStatement pst = con.prepareStatement(sql))
        {
            // Appliquer le filtrage sur le nom
            pst.setString(1, "%" + search + "%");
            
            ResultSet rs = pst.executeQuery();
            while(rs.next())
            {
                // Récupérer les données de la ligne actuelle
                int id = rs.getInt("user_id");
                String name = rs.getString("username");
                String pass = rs.getString("password");
                String role = rs.getString("role");
                
                // Créer l'objet Users les données récupérées
                Users user = new Users(id, name, pass, role);
                
                // Ajouter l'objet à la liste
                list.add(user);
            }
        }
        catch(SQLException e)
        {
            System.err.println("Erreur lors de la recherche : " + e.getMessage());
            
        }
        return list;
    }
}
