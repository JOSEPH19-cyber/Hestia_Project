/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.hestia.view;

// Les imports
import com.hestia.dao.UserDAO;
import com.hestia.model.Users;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author hp
 */
public class panelSettings extends javax.swing.JPanel {

    /**
     * Creates new form panelUsers
     */
    public panelSettings() {
        initComponents();
        loadRoles();
        loadUserData();
        initTableEvent();
        tableUsers.setDefaultEditor(Object.class, null);
    }
    
    // Méthode pour charger les rôles
    private void loadRoles()
    {
        try {
            UserDAO dao = new UserDAO();
            List<String> liste = dao.getRoles();

            // On récupère le modèle interne de la JComboBox. 
            javax.swing.DefaultComboBoxModel model = (javax.swing.DefaultComboBoxModel) cbRole.getModel();

            // On vide proprement
            model.removeAllElements();

            if (liste != null) {
                for (String c : liste) {
                    // On ajoute l'objet au modèle
                    model.addElement(c); 
                }
            }
        } catch (Exception e) {
            System.err.println("Erreur de chargement : " + e.getMessage());
        }
    }
    
    
    // Méthode de hashage
    private String hashPassword(String password) 
    {
        try 
        {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) 
            {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception ex) 
        {
            throw new RuntimeException(ex);
        }
    }
    
    // Méthode pour afficher les informations du Users dans tabUsers
    public final void loadUserData()
    {
        // Récupérer le modèle du tableau (DefaultTableModel)
        DefaultTableModel model = (DefaultTableModel) tableUsers.getModel();
        
        // vider le tableau
        model.setRowCount(0);
        
        // Appelle de la DAO pour récupérer la liste
        UserDAO dao = new UserDAO();
        List<Users> maListe = dao.getAllUsers();
        
        // Boucler pour rajouter chaque utilisateur au tableau
        for(Users u : maListe)
        {
            // Créer un objet pour représenté une ligne
            Object[] row = {
                u.getUserid(),
                u.getUsername(),
                u.getRole()
            };
            
            // Ajouter la ligne au modèle
            model.addRow(row);
        }
    }
    
    // Méthode pour remplir les champs des utilisateurs à la selection d'une ligne
    private void initTableEvent() {
        tableUsers.getSelectionModel().addListSelectionListener((ListSelectionEvent event) -> {
            // Agir si l'action de selection est terminée
            if (!event.getValueIsAdjusting()) {
                // Vérifier si une ligne a été selectionée
                int row = tableUsers.getSelectedRow();
                if (row != -1) {
                    // Extraire les donneés des cellules du tableau
                    String nom = tableUsers.getValueAt(row, 1).toString();
                    String role = tableUsers.getValueAt(row, 2).toString();
                    
                    // Renvoyer les données vers les champs
                    txtUsername.setText(nom);
                    cbRole.setSelectedItem(role);
                    
                    // Vider le champ mot de passe pour la sécurité
                    txtPassword.setText("");
                }
            }
        });
    }
    
    // Méthode pour filtrer un utilisateur
    private void loadSearchData(String searchText) {
        UserDAO dao = new UserDAO();
        
        // Récupérer la liste filtrée depuis le DAO
        List<Users> users = dao.searchUsers(searchText);

        DefaultTableModel model = (DefaultTableModel) tableUsers.getModel();
        // on vide le tableau pour rafraîchir
        model.setRowCount(0); 

        for (Users u : users) {
            Object[] row = {
                u.getUserid(),
                u.getUsername(),
                u.getRole()
            };
            model.addRow(row);
        }
    }
    
    // Méthode pour rafraîchir le tableau après modification du user
    private void clearFields() {
        txtUsername.setText("");
        txtPassword.setText("");
        cbRole.setSelectedIndex(0); 
        tableUsers.clearSelection(); 
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        tabRooms = new javax.swing.JPanel();
        pnlFomrCham = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtRoomNumber = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        cbCategory = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        btnAddRoom = new javax.swing.JButton();
        btnUpdateRoom = new javax.swing.JButton();
        btnDeleteRoom = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        txtSearchRoom = new javax.swing.JTextField();
        spnMaxCapacity = new javax.swing.JSpinner();
        spnPriceNight = new javax.swing.JSpinner();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableChambres = new javax.swing.JTable();
        tabUsers = new javax.swing.JPanel();
        pnlFormUser = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        jLabel4 = new javax.swing.JLabel();
        cbRole = new javax.swing.JComboBox<>();
        btnAddUser = new javax.swing.JButton();
        btnUpdateUser = new javax.swing.JButton();
        btnDeleteUser = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        txtSearchUser = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableUsers = new javax.swing.JTable();

        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(900, 700));
        setMinimumSize(new java.awt.Dimension(900, 700));
        setPreferredSize(new java.awt.Dimension(900, 700));
        setLayout(new java.awt.BorderLayout());

        jTabbedPane1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        tabRooms.setLayout(new java.awt.BorderLayout());

        pnlFomrCham.setMaximumSize(new java.awt.Dimension(900, 300));
        pnlFomrCham.setMinimumSize(new java.awt.Dimension(900, 300));
        pnlFomrCham.setPreferredSize(new java.awt.Dimension(900, 300));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("AJOUTER UNE CHAMBRE");

        txtRoomNumber.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtRoomNumber.setMaximumSize(new java.awt.Dimension(150, 30));
        txtRoomNumber.setMinimumSize(new java.awt.Dimension(150, 30));
        txtRoomNumber.setPreferredSize(new java.awt.Dimension(150, 30));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Prix/Nuit ($)");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Capacité max");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Catégorie");

        cbCategory.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbCategory.setMaximumSize(new java.awt.Dimension(150, 30));
        cbCategory.setMinimumSize(new java.awt.Dimension(150, 30));
        cbCategory.setPreferredSize(new java.awt.Dimension(150, 30));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Numéro de chambre");

        btnAddRoom.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnAddRoom.setText("AJOUTER");
        btnAddRoom.setMaximumSize(new java.awt.Dimension(120, 30));
        btnAddRoom.setMinimumSize(new java.awt.Dimension(120, 30));
        btnAddRoom.setPreferredSize(new java.awt.Dimension(120, 30));
        btnAddRoom.addActionListener(this::btnAddRoomActionPerformed);

        btnUpdateRoom.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnUpdateRoom.setText("MODIFIER");
        btnUpdateRoom.setMaximumSize(new java.awt.Dimension(120, 30));
        btnUpdateRoom.setMinimumSize(new java.awt.Dimension(120, 30));
        btnUpdateRoom.setPreferredSize(new java.awt.Dimension(120, 30));

        btnDeleteRoom.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnDeleteRoom.setText("SUPPRIMER");
        btnDeleteRoom.setMaximumSize(new java.awt.Dimension(120, 30));
        btnDeleteRoom.setMinimumSize(new java.awt.Dimension(120, 30));
        btnDeleteRoom.setPreferredSize(new java.awt.Dimension(120, 30));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("RECHERCHE");

        txtSearchRoom.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtSearchRoom.setMaximumSize(new java.awt.Dimension(150, 30));
        txtSearchRoom.setMinimumSize(new java.awt.Dimension(150, 30));
        txtSearchRoom.setPreferredSize(new java.awt.Dimension(150, 30));

        spnMaxCapacity.setMaximumSize(new java.awt.Dimension(80, 30));
        spnMaxCapacity.setMinimumSize(new java.awt.Dimension(80, 30));
        spnMaxCapacity.setPreferredSize(new java.awt.Dimension(80, 30));
        spnMaxCapacity.setRequestFocusEnabled(false);

        spnPriceNight.setMaximumSize(new java.awt.Dimension(80, 30));
        spnPriceNight.setMinimumSize(new java.awt.Dimension(80, 30));
        spnPriceNight.setPreferredSize(new java.awt.Dimension(80, 30));

        javax.swing.GroupLayout pnlFomrChamLayout = new javax.swing.GroupLayout(pnlFomrCham);
        pnlFomrCham.setLayout(pnlFomrChamLayout);
        pnlFomrChamLayout.setHorizontalGroup(
            pnlFomrChamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFomrChamLayout.createSequentialGroup()
                .addGroup(pnlFomrChamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlFomrChamLayout.createSequentialGroup()
                        .addGroup(pnlFomrChamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlFomrChamLayout.createSequentialGroup()
                                .addGap(250, 250, 250)
                                .addComponent(spnMaxCapacity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFomrChamLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(52, 52, 52)))
                        .addGap(18, 18, 18))
                    .addGroup(pnlFomrChamLayout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addGroup(pnlFomrChamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(44, 44, 44)
                        .addComponent(txtRoomNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 98, Short.MAX_VALUE)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(56, 56, 56)))
                .addGroup(pnlFomrChamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(spnPriceNight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(101, 101, 101))
            .addGroup(pnlFomrChamLayout.createSequentialGroup()
                .addGroup(pnlFomrChamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlFomrChamLayout.createSequentialGroup()
                        .addGap(159, 159, 159)
                        .addComponent(btnAddRoom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(137, 137, 137)
                        .addComponent(btnUpdateRoom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(132, 132, 132)
                        .addComponent(btnDeleteRoom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlFomrChamLayout.createSequentialGroup()
                        .addGap(316, 316, 316)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(txtSearchRoom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlFomrChamLayout.setVerticalGroup(
            pnlFomrChamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFomrChamLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addGap(27, 27, 27)
                .addGroup(pnlFomrChamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtRoomNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel9)
                    .addComponent(spnPriceNight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(pnlFomrChamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(cbCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spnMaxCapacity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addGroup(pnlFomrChamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddRoom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpdateRoom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDeleteRoom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39)
                .addGroup(pnlFomrChamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtSearchRoom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28))
        );

        tabRooms.add(pnlFomrCham, java.awt.BorderLayout.PAGE_START);

        tableChambres.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tableChambres);

        tabRooms.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("CHAMBRES", tabRooms);

        tabUsers.setLayout(new java.awt.BorderLayout());

        pnlFormUser.setMaximumSize(new java.awt.Dimension(900, 250));
        pnlFormUser.setMinimumSize(new java.awt.Dimension(900, 250));
        pnlFormUser.setPreferredSize(new java.awt.Dimension(900, 250));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("AJOUTER UN UTILISATEUR");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Nom");

        txtUsername.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtUsername.setDragEnabled(true);
        txtUsername.setMaximumSize(new java.awt.Dimension(150, 30));
        txtUsername.setMinimumSize(new java.awt.Dimension(150, 30));
        txtUsername.setPreferredSize(new java.awt.Dimension(150, 30));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Mot de passe");

        txtPassword.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPassword.setMaximumSize(new java.awt.Dimension(150, 30));
        txtPassword.setMinimumSize(new java.awt.Dimension(150, 30));
        txtPassword.setPreferredSize(new java.awt.Dimension(150, 30));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Rôle");

        cbRole.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbRole.setMaximumSize(new java.awt.Dimension(150, 30));
        cbRole.setMinimumSize(new java.awt.Dimension(150, 130));
        cbRole.setPreferredSize(new java.awt.Dimension(150, 30));

        btnAddUser.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnAddUser.setText("AJOUTER");
        btnAddUser.setMaximumSize(new java.awt.Dimension(120, 30));
        btnAddUser.setMinimumSize(new java.awt.Dimension(120, 30));
        btnAddUser.setPreferredSize(new java.awt.Dimension(120, 30));
        btnAddUser.addActionListener(this::btnAddUserActionPerformed);

        btnUpdateUser.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnUpdateUser.setText("MODIFIER");
        btnUpdateUser.setMaximumSize(new java.awt.Dimension(120, 30));
        btnUpdateUser.setMinimumSize(new java.awt.Dimension(120, 30));
        btnUpdateUser.setPreferredSize(new java.awt.Dimension(120, 30));
        btnUpdateUser.addActionListener(this::btnUpdateUserActionPerformed);

        btnDeleteUser.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnDeleteUser.setText("SUPPRIMER");
        btnDeleteUser.setMaximumSize(new java.awt.Dimension(120, 30));
        btnDeleteUser.setMinimumSize(new java.awt.Dimension(120, 30));
        btnDeleteUser.setPreferredSize(new java.awt.Dimension(120, 30));
        btnDeleteUser.addActionListener(this::btnDeleteUserActionPerformed);

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("RECHERCHE");

        txtSearchUser.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtSearchUser.setMaximumSize(new java.awt.Dimension(200, 30));
        txtSearchUser.setMinimumSize(new java.awt.Dimension(200, 30));
        txtSearchUser.setPreferredSize(new java.awt.Dimension(200, 30));
        txtSearchUser.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchUserKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout pnlFormUserLayout = new javax.swing.GroupLayout(pnlFormUser);
        pnlFormUser.setLayout(pnlFormUserLayout);
        pnlFormUserLayout.setHorizontalGroup(
            pnlFormUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFormUserLayout.createSequentialGroup()
                .addGroup(pnlFormUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlFormUserLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(88, 88, 88))
                    .addGroup(pnlFormUserLayout.createSequentialGroup()
                        .addGap(211, 211, 211)
                        .addComponent(btnAddUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(75, 75, 75)
                        .addComponent(btnUpdateUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37)
                        .addComponent(btnDeleteUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(14, 14, 14)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbRole, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5))
            .addGroup(pnlFormUserLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtSearchUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );
        pnlFormUserLayout.setVerticalGroup(
            pnlFormUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFormUserLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlFormUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSearchUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addGap(22, 22, 22)
                .addGroup(pnlFormUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(cbRole, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(84, 84, 84)
                .addGroup(pnlFormUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpdateUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDeleteUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(46, Short.MAX_VALUE))
        );

        tabUsers.add(pnlFormUser, java.awt.BorderLayout.PAGE_START);

        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        tableUsers.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Noms", "Rôles"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tableUsers);
        if (tableUsers.getColumnModel().getColumnCount() > 0) {
            tableUsers.getColumnModel().getColumn(0).setResizable(false);
            tableUsers.getColumnModel().getColumn(1).setResizable(false);
            tableUsers.getColumnModel().getColumn(2).setResizable(false);
        }

        tabUsers.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("UTILISATEURS", tabUsers);

        add(jTabbedPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddRoomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddRoomActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAddRoomActionPerformed

    private void btnAddUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddUserActionPerformed
        
        // Récupération et nettoyage des données 
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());
        String role = cbRole.getSelectedItem().toString();
        
        // Vérifier si les champs ne sont pas vide
        if(username.isEmpty() || password.isEmpty() || role.equals("--- Choisir un rôle ---")) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs !");
            return;
        }
        
       // Taille max et min du mot de passe
        if (password.length() < 4 || password.length() > 8) 
       {
            JOptionPane.showMessageDialog(this, "Le mot de passe doit contenir entre 4 et 8 caractères.");
            return;
        }
        
       // Initialisation du DAO
       UserDAO dao = new UserDAO();
       
       // Vérifier si le username existe déjà
       if(dao.isUsernameTaken(username)) {
            JOptionPane.showMessageDialog(this, "Ce nom d'utilisateur est déjà utilisé !", "Erreur", JOptionPane.WARNING_MESSAGE);
            return; 
        }
       
       // Hashage du mot de passe
       String mdpHashe = hashPassword(password);
       
       // 5. Création de l'objet et Enregistrement
       Users newuser = new Users(username, mdpHashe, role);
       
       if(dao.addUser(newuser)) 
       {
            JOptionPane.showMessageDialog(this, "Utilisateur ajouté avec succès !");

            // Vider les champs
            txtUsername.setText("");
            txtPassword.setText("");
            cbRole.setSelectedIndex(0);
            
            // Raffraîchir le tableau
            loadUserData();
        } 
       else 
       {
            JOptionPane.showMessageDialog(this, "Erreur technique lors de l'enregistrement.", "Erreur", JOptionPane.ERROR_MESSAGE);
       }
    }//GEN-LAST:event_btnAddUserActionPerformed

    private void btnUpdateUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateUserActionPerformed
        
        int selectedRow = tableUsers.getSelectedRow();
    
        // vérifier si une ligne a été selectionée
        if(selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner une ligne dans le tableau !");
            return; 
        }

        // Récupérer et nettoyer les données
        int id = Integer.parseInt(tableUsers.getValueAt(selectedRow, 0).toString());
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());
        String role = cbRole.getSelectedItem().toString();

        // Vérifier si le champ Nom et Rôle sont vides
        if(username.isEmpty() || role.equals("--- Choisir un rôle ---")) {
            JOptionPane.showMessageDialog(this, "Les champs Nom et Rôle doivent obligatoirement être remplis !");
            return; 
        }

        // Initialisation de l'indicateur de réussite et instanciation de la DAO
        boolean success = false;
        UserDAO dao = new UserDAO(); 

        // Vérifier si le mot de passe est viode
        if(password.isEmpty()) {
            // CAS A : Pas de changement de mot de passe
            Users user = new Users(id,username, role);
            success = dao.updateUserWithoutPassword(user);
        } 
        else {
            // CAS B : Changement de mot de passe et vérification de la taille
            if(password.length() < 4 || password.length() > 8) {
                JOptionPane.showMessageDialog(this, "Le nouveau mot de passe doit faire entre 4 et 8 caractères !");
                return;
            }
            
            // Hasher le mot de passe
            String mdpHashe = hashPassword(password);
            
            Users user = new Users(id, username, mdpHashe, role); 
            success = dao.updateUser(user);
        }

        // Résultat final
        if(success) {
            JOptionPane.showMessageDialog(this, "Utilisateur mis à jour avec succès !");
            // Mise à jour de l'interface
            loadUserData(); 
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Erreur lors de la mise à jour en base de données.");
        }
    }//GEN-LAST:event_btnUpdateUserActionPerformed

    private void btnDeleteUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteUserActionPerformed
        
        int selectedRow = tableUsers.getSelectedRow();
    
        // Vérifier si une ligne a été sélectionnée
        if(selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner l'utilisateur à supprimer !");
            return; 
        }
    
    // Récupérer l'id à supprimer
    int id = Integer.parseInt(tableUsers.getValueAt(selectedRow, 0).toString());
    
    // Demander la confirmation
    int opt = JOptionPane.showConfirmDialog(this, 
            "Êtes-vous sûr de vouloir supprimer définitivement cet utilisateur ?", 
            "Confirmation de suppression", 
            JOptionPane.YES_NO_OPTION, 
            JOptionPane.WARNING_MESSAGE); 
            
    if (opt == JOptionPane.YES_OPTION) {
        UserDAO dao = new UserDAO();
        
        // Exécuter la suppression
        if (dao.deleteUser(id)) {
            JOptionPane.showMessageDialog(this, "Utilisateur supprimé avec succès !");
            
            // Mise à jour de l'interface
            loadUserData(); 
            clearFields(); 
        } else {
            JOptionPane.showMessageDialog(this, "Erreur : Impossible de supprimer l'utilisateur.");
        }
    }
    }//GEN-LAST:event_btnDeleteUserActionPerformed

    private void txtSearchUserKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchUserKeyReleased
        // On récupère le texte et on lance la recherche
        loadSearchData(txtSearchUser.getText().trim());
    }//GEN-LAST:event_txtSearchUserKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddRoom;
    private javax.swing.JButton btnAddUser;
    private javax.swing.JButton btnDeleteRoom;
    private javax.swing.JButton btnDeleteUser;
    private javax.swing.JButton btnUpdateRoom;
    private javax.swing.JButton btnUpdateUser;
    private javax.swing.JComboBox<String> cbCategory;
    private javax.swing.JComboBox<String> cbRole;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JPanel pnlFomrCham;
    private javax.swing.JPanel pnlFormUser;
    private javax.swing.JSpinner spnMaxCapacity;
    private javax.swing.JSpinner spnPriceNight;
    private javax.swing.JPanel tabRooms;
    private javax.swing.JPanel tabUsers;
    private javax.swing.JTable tableChambres;
    private javax.swing.JTable tableUsers;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtRoomNumber;
    private javax.swing.JTextField txtSearchRoom;
    private javax.swing.JTextField txtSearchUser;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
