/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.hestia.view;

// Les imports
import com.hestia.dao.UserDAO;
import com.hestia.model.Users;
import com.hestia.dao.CategorieDAO;
import com.hestia.model.Categories;
import com.hestia.dao.RoomDAO;
import com.hestia.model.Rooms;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author hp
 */
public final class panelSettings extends javax.swing.JPanel {

    /**
     * Creates new form panelUsers
     */
    public panelSettings() {
        initComponents();
        loadUserRoles();
        loadUserTable();
        setupUserTableSelectionListener();
        loadCategoriesTable();
        setupCategoryTableSelectionListener();
        fillRoomCategoryCombo();
        loadRoomsTable();
        setupRoomTableSelectionListener();
        tableUsers.setDefaultEditor(Object.class, null);
    }
    
    // Méthode pour charger les rôles des utilisateurs
    private void loadUserRoles()
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
    public void loadUserTable()
    {
        // Récupérer le modèle du tableau 
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
            
            // Ajouter la ligne au tableau
            model.addRow(row);
        }
    }
    
    // Méthode pour remplir les champs des utilisateurs à la selection d'une ligne
    private void setupUserTableSelectionListener() {
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
    private void filterUsersTable(String searchText) {
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
    
    // Méthode pour afficher les informations des catégories des chambres
    public void loadCategoriesTable()
    {
        // Récupérer le modèle du tableau
        DefaultTableModel model = (DefaultTableModel) tableCategories.getModel();
        
        // Vider le tableau
        model.setRowCount(0);
        
        // Appeler la DAO pour récupérer la liste
        CategorieDAO dao = new CategorieDAO();
        List<Categories> maListe = dao.getAllCategories();
        
        // Boucler pour rajourte chaque catégorie au tableau
        for(Categories c : maListe)
        {
            // Créer un bojet pour représenter une ligne
            Object[] row = {
                c.getCategoryid(),
                c.getCategorytype(),
                c.getNightlyprice(),
                c.getMaxcapacity()
            };
            
            // Ajouter la ligne au tableau
            model.addRow(row);
        }
         
    }
    
    // Méthode pour remplir les champs des catégories à la selection d'une ligne
    private void setupCategoryTableSelectionListener()
    {
        tableCategories.getSelectionModel().addListSelectionListener((ListSelectionEvent event) -> {
            // Agir si l'action de selection est terminée
            if (!event.getValueIsAdjusting()) {
                // Vérifier si une ligne a été selectionée
                int row = tableCategories.getSelectedRow();
                if (row != -1) {
                    // Extraire les donneés des cellules du tableau
                    String category_type = tableCategories.getValueAt(row, 1).toString();
                    String nightly_price = tableCategories.getValueAt(row, 2).toString();
                    String capacity_max = tableCategories.getValueAt(row, 3).toString();
                    int capacity = Integer.parseInt(capacity_max);
                    
                    // Renvoyer les données vers les champs
                    txtCategoryType.setText(category_type);
                    txtNightlyPrice.setText(nightly_price);
                    spnMaxCapacity.setValue(capacity);
                    
                }
            }
        });
    }
    
    // Méthode pour filtrer une Catégorie
    private void filterCategoriesTable(String searchText) {
        CategorieDAO dao = new CategorieDAO();
        
        // Récupérer la liste filtrée depuis le DAO
        List<Categories> categories = dao.searchCategories(searchText);

        DefaultTableModel model = (DefaultTableModel) tableCategories.getModel();
        // on vide le tableau pour rafraîchir
        model.setRowCount(0); 

        for (Categories c : categories) {
            Object[] row = {
                c.getCategoryid(),
                c.getCategorytype(),
                c.getNightlyprice(),
                c.getMaxcapacity()
            };
            model.addRow(row);
        }
    }
    
    // Méthode pour charger les catégories dans la combo de tabRooms
    public void fillRoomCategoryCombo()
    {
         try {
            CategorieDAO dao = new CategorieDAO();
            List<Categories> liste = dao.getAllCategories();

            // On récupère le modèle interne de la JComboBox. 
            javax.swing.DefaultComboBoxModel model = (javax.swing.DefaultComboBoxModel) cbCategory.getModel();

            // On vide proprement
            model.removeAllElements();

            if (liste != null) {
                for (Categories c : liste) {
                    // On ajoute l'objet au modèle
                    model.addElement(c); 
                }
            }
        } catch (Exception e) {
            System.err.println("Erreur de chargement : " + e.getMessage());
        }
    }
    
    // Méthode pour afficher les informations des chambres dans tabRooms
    private void loadRoomsTable()
    {
        // Récupérer le modèle du tableau
        DefaultTableModel model = (DefaultTableModel) tableRooms.getModel();
        
        // Vider le tableau
        model.setRowCount(0);
        
        // Appel de la DAO pour récupérer la liste 
        RoomDAO rdao = new RoomDAO();
        List<Rooms> liste = rdao.getAllRooms();
        
        // Boucler pour ajouter chaligne au tableau
        for(Rooms r : liste)
        {
            Object[] row = 
            {
                r.getRoomid(),
                r.getRoomnumber(),
                r.getCategorytype(),
                r.getStatus()
            };
            model.addRow(row);
        }
    }
    
    // Méthode pour remplir les champs des chambres à la selection d'une ligne
    private void setupRoomTableSelectionListener() {
        tableRooms.getSelectionModel().addListSelectionListener((ListSelectionEvent event) -> {
            // Agir si l'action de selection est terminée
            if (!event.getValueIsAdjusting()) {
                // Vérifier si une ligne a été selectionée
                int row = tableRooms.getSelectedRow();
                if (row != -1) {
                    // Extraire les donneés des cellules du tableau
                    String number = tableRooms.getValueAt(row, 1).toString();
                    String category = tableRooms.getValueAt(row, 2).toString();
                    
                    // Renvoyer les données vers les champs
                    txtRoomNumber.setText(number);
                    // On boucle sur les objets de la combo pour trouver celui qui a le même nom
                    for (int i = 0; i < cbCategory.getItemCount(); i++) {
                        Categories cat = (Categories) cbCategory.getItemAt(i);
                        if (cat.getCategorytype().equals(category)) {
                            cbCategory.setSelectedItem(cat);
                            break;
                        }
                    }
                }
            }
        });
    }
    
    
    // Méthode pour vider les champs du formulaire Utilisateur
    private void clearUserFields() 
    {
        txtUsername.setText("");
        txtPassword.setText("");
        cbRole.setSelectedIndex(0); 
        tableUsers.clearSelection(); 
    }
    
    // Méthode pour vider les champs du formulaire Catégorie
    private void clearCategoryFields()
    {
        txtCategoryType.setText("");
        spnMaxCapacity.setValue(1);
        txtNightlyPrice.setText("");
        tableCategories.clearSelection();
    }
    
    // Métode pour vider les champs du formulaire chambre
    private void clearRoomFields()
    {
        txtRoomNumber.setText("");
        cbCategory.setSelectedIndex(0);
        tableRooms.clearSelection();
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
        jLabel8 = new javax.swing.JLabel();
        cbCategory = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        btnAddRoom = new javax.swing.JButton();
        btnUpdateRoom = new javax.swing.JButton();
        btnDeleteRoom = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        txtSearchRoom = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableRooms = new javax.swing.JTable();
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
        tabCategories = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        spnMaxCapacity = new javax.swing.JSpinner();
        jLabel7 = new javax.swing.JLabel();
        txtNightlyPrice = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtCategoryType = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtSearchCategory = new javax.swing.JTextField();
        btnAddCategory = new javax.swing.JButton();
        btnUpdateCategory = new javax.swing.JButton();
        btnDeleteCategory = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableCategories = new javax.swing.JTable();

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

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Catégorie");

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
        btnUpdateRoom.addActionListener(this::btnUpdateRoomActionPerformed);

        btnDeleteRoom.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnDeleteRoom.setText("SUPPRIMER");
        btnDeleteRoom.setMaximumSize(new java.awt.Dimension(120, 30));
        btnDeleteRoom.setMinimumSize(new java.awt.Dimension(120, 30));
        btnDeleteRoom.setPreferredSize(new java.awt.Dimension(120, 30));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("RECHERCHE");

        txtSearchRoom.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtSearchRoom.setMaximumSize(new java.awt.Dimension(200, 30));
        txtSearchRoom.setMinimumSize(new java.awt.Dimension(200, 30));
        txtSearchRoom.setPreferredSize(new java.awt.Dimension(200, 30));

        javax.swing.GroupLayout pnlFomrChamLayout = new javax.swing.GroupLayout(pnlFomrCham);
        pnlFomrCham.setLayout(pnlFomrChamLayout);
        pnlFomrChamLayout.setHorizontalGroup(
            pnlFomrChamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFomrChamLayout.createSequentialGroup()
                .addContainerGap(59, Short.MAX_VALUE)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addComponent(txtRoomNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(64, 64, 64)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(101, 101, 101))
            .addGroup(pnlFomrChamLayout.createSequentialGroup()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(txtSearchRoom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
            .addGroup(pnlFomrChamLayout.createSequentialGroup()
                .addGap(159, 159, 159)
                .addComponent(btnAddRoom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(137, 137, 137)
                .addComponent(btnUpdateRoom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(132, 132, 132)
                .addComponent(btnDeleteRoom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlFomrChamLayout.setVerticalGroup(
            pnlFomrChamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFomrChamLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlFomrChamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel10)
                    .addComponent(txtSearchRoom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(69, 69, 69)
                .addGroup(pnlFomrChamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(txtRoomNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addGroup(pnlFomrChamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddRoom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpdateRoom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDeleteRoom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(97, 97, 97))
        );

        tabRooms.add(pnlFomrCham, java.awt.BorderLayout.PAGE_START);

        tableRooms.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tableRooms.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "NUMEROS CHAMBRES", "CATEGORIES", "STATUS"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tableRooms);
        if (tableRooms.getColumnModel().getColumnCount() > 0) {
            tableRooms.getColumnModel().getColumn(0).setResizable(false);
            tableRooms.getColumnModel().getColumn(1).setResizable(false);
            tableRooms.getColumnModel().getColumn(2).setResizable(false);
            tableRooms.getColumnModel().getColumn(3).setResizable(false);
        }

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
        txtSearchUser.addActionListener(this::txtSearchUserActionPerformed);
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

        tableUsers.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tableUsers.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "NOMS", "ROLES"
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

        tabCategories.setLayout(new java.awt.BorderLayout());

        jPanel1.setMaximumSize(new java.awt.Dimension(900, 300));
        jPanel1.setMinimumSize(new java.awt.Dimension(900, 300));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Capacité max");

        spnMaxCapacity.setModel(new javax.swing.SpinnerNumberModel(1, 1, 10, 1));
        spnMaxCapacity.setMaximumSize(new java.awt.Dimension(80, 30));
        spnMaxCapacity.setMinimumSize(new java.awt.Dimension(80, 30));
        spnMaxCapacity.setPreferredSize(new java.awt.Dimension(80, 30));
        spnMaxCapacity.setRequestFocusEnabled(false);

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Prix/Nuit ($)");

        txtNightlyPrice.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNightlyPrice.setMaximumSize(new java.awt.Dimension(150, 30));
        txtNightlyPrice.setMinimumSize(new java.awt.Dimension(150, 30));
        txtNightlyPrice.setPreferredSize(new java.awt.Dimension(150, 30));
        txtNightlyPrice.addActionListener(this::txtNightlyPriceActionPerformed);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("TYPE");

        txtCategoryType.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCategoryType.setMaximumSize(new java.awt.Dimension(150, 30));
        txtCategoryType.setMinimumSize(new java.awt.Dimension(150, 30));
        txtCategoryType.setName(""); // NOI18N
        txtCategoryType.setPreferredSize(new java.awt.Dimension(150, 30));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("RECHERCHE");

        txtSearchCategory.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtSearchCategory.setMaximumSize(new java.awt.Dimension(200, 30));
        txtSearchCategory.setMinimumSize(new java.awt.Dimension(200, 30));
        txtSearchCategory.setPreferredSize(new java.awt.Dimension(200, 30));
        txtSearchCategory.addActionListener(this::txtSearchCategoryActionPerformed);
        txtSearchCategory.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchCategoryKeyReleased(evt);
            }
        });

        btnAddCategory.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnAddCategory.setText("AJOUTER");
        btnAddCategory.setMaximumSize(new java.awt.Dimension(120, 30));
        btnAddCategory.setMinimumSize(new java.awt.Dimension(120, 30));
        btnAddCategory.setPreferredSize(new java.awt.Dimension(120, 30));
        btnAddCategory.addActionListener(this::btnAddCategoryActionPerformed);

        btnUpdateCategory.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnUpdateCategory.setText("MODIFIER");
        btnUpdateCategory.setMaximumSize(new java.awt.Dimension(120, 30));
        btnUpdateCategory.setMinimumSize(new java.awt.Dimension(120, 30));
        btnUpdateCategory.setPreferredSize(new java.awt.Dimension(120, 30));
        btnUpdateCategory.addActionListener(this::btnUpdateCategoryActionPerformed);

        btnDeleteCategory.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnDeleteCategory.setText("SUPPRIMER");
        btnDeleteCategory.setMaximumSize(new java.awt.Dimension(120, 30));
        btnDeleteCategory.setMinimumSize(new java.awt.Dimension(120, 30));
        btnDeleteCategory.setPreferredSize(new java.awt.Dimension(120, 30));
        btnDeleteCategory.addActionListener(this::btnDeleteCategoryActionPerformed);

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("AJOUTER UNE CATEGORIE DES CHAMBRES");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(162, 162, 162)
                .addComponent(btnAddCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(99, 99, 99)
                .addComponent(btnUpdateCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnDeleteCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(109, 109, 109))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 443, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtSearchCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(txtCategoryType, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(73, 73, 73)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(spnMaxCapacity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(57, 57, 57)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42)
                        .addComponent(txtNightlyPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtSearchCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addGap(95, 95, 95)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNightlyPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(spnMaxCapacity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(jLabel6)
                    .addComponent(txtCategoryType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(61, 61, 61)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpdateCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDeleteCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(62, Short.MAX_VALUE))
        );

        tabCategories.add(jPanel1, java.awt.BorderLayout.PAGE_START);

        tableCategories.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tableCategories.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "TYPES", "PRIX/NUIT", "CAPACITE MAX"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tableCategories);
        if (tableCategories.getColumnModel().getColumnCount() > 0) {
            tableCategories.getColumnModel().getColumn(0).setResizable(false);
            tableCategories.getColumnModel().getColumn(1).setResizable(false);
            tableCategories.getColumnModel().getColumn(2).setResizable(false);
            tableCategories.getColumnModel().getColumn(3).setResizable(false);
        }

        tabCategories.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("Categories", tabCategories);

        add(jTabbedPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddRoomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddRoomActionPerformed
        
        // Récupérer et nettoyer les données
        String room_number = txtRoomNumber.getText().trim();
        String status = "DISPONIBLE"; 
    
        // Vérifier si le numéro de la chambre est vide
        if(room_number.isEmpty()) 
        {
            JOptionPane.showMessageDialog(this, "Veuillez saisir un numéro de chambre !");
            return;
        }
    
        // Instanciation de la DAO
        RoomDAO rDAO = new RoomDAO();

        // Vérififier si une chambre existe
        if(rDAO.isRoomNumberExists(room_number)) 
        {
            JOptionPane.showMessageDialog(this, "Ce numéro de chambre existe déjà. Veuillez en choisir un autre.");
            return; 
        }
    
        // 3. Récupérer la catégorie
        Categories selectedCat = (Categories) cbCategory.getSelectedItem();
        if(selectedCat == null) 
        {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner une catégorie !");
            return;
        }
    
        // Création de l'objet et enregistrement
        Rooms room = new Rooms(room_number, selectedCat.getCategoryid(), status);
        
        if(rDAO.addRoom(room)) 
        {
            JOptionPane.showMessageDialog(this, "Chambre ajoutée avec succès !");
            
            // Mise à jour de l'interface
            loadRoomsTable();
            clearRoomFields();
            txtRoomNumber.requestFocus();
        } 
        else 
        {
            JOptionPane.showMessageDialog(this, "Une erreur est survenue lors de l'enregistrement.");
        }
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
       
       // Création de l'objet et Enregistrement
       Users newuser = new Users(username, mdpHashe, role);
       
       if(dao.addUser(newuser)) 
       {
            JOptionPane.showMessageDialog(this, "Utilisateur ajouté avec succès !");

            // Mise à jour de l'interface
            loadUserTable();
            clearUserFields();
            txtUsername.requestFocus();
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
            loadUserTable(); 
            clearUserFields();
            txtUsername.requestFocus();
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
                loadUserTable(); 
                clearUserFields(); 
            } else {
                JOptionPane.showMessageDialog(this, "Erreur : Impossible de supprimer l'utilisateur.");
            }
        }
    }//GEN-LAST:event_btnDeleteUserActionPerformed

    private void txtSearchUserKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchUserKeyReleased
        // On récupère le texte et on lance la recherche
        filterUsersTable(txtSearchUser.getText().trim());
    }//GEN-LAST:event_txtSearchUserKeyReleased

    private void txtNightlyPriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNightlyPriceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNightlyPriceActionPerformed

    private void btnAddCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddCategoryActionPerformed
        
        // Récupérer et nettoyer les données 
        String category_type = txtCategoryType.getText().trim();
        int max_capacity = (int) spnMaxCapacity.getValue();
        String nightly_price = txtNightlyPrice.getText().trim();
        double price = 0;
        
        // Vérifier si les champs ne sont pas vides
        if(category_type.isEmpty() || nightly_price.isEmpty())
        {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs !");
            return;
        }
        
        // Vérifier que la capité maximum n'est pas  nulle
        if(max_capacity <= 0)
        {
            JOptionPane.showMessageDialog(this, "La capacité doit être d'au moins une personne !");
            return;
        }
        
        // Parser le prix
        try
        {
            //on remplace la virgule par un point
            price = Double.parseDouble(nightly_price.replace(",", "."));
            
            // Vérifier si le prix n'est pas négatif
            if(price <= 0)
            {
                JOptionPane.showMessageDialog(this, "Le prix doit être supérieur à 0");
                return;
            }
        }
        catch(NumberFormatException e)
        {
            JOptionPane.showMessageDialog(this, "Veuillez saisir un prix numérique valide.");
            return;
        }
        
        // Initialisation du DAO
        CategorieDAO dao = new CategorieDAO();
        
        // Vérifier si la catégorie existe déjà
        if(dao.isTypeDuplicate(category_type))
        {
            JOptionPane.showMessageDialog(this, "Cette catégorie existe déjà !", "Erreur", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Créer l'objet de l'enregistrement 
        Categories newcategory = new Categories(category_type, price, max_capacity);
        
        if(dao.addCategory(newcategory))
        {
            JOptionPane.showMessageDialog(this, "Catégorie ajoutée avec succès ! ");
            
            // Mise à jour de l'interface
            loadCategoriesTable();
            clearCategoryFields();
            txtCategoryType.requestFocus();
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Erreur technique lors de l'enregistrement.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnAddCategoryActionPerformed

    private void btnUpdateCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateCategoryActionPerformed
        
        int selectedRow = tableCategories.getSelectedRow();

        // Vérifier si une ligne est selectionnée
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner une catégorie dans le tableau !");
            return;
        }

        // Récupérer et nettoyer les données
        int id = Integer.parseInt(tableCategories.getValueAt(selectedRow, 0).toString());
        String category_type = txtCategoryType.getText().trim();
        String nightly_price = txtNightlyPrice.getText().trim().replace(",", ".");
        int capacity_max = (int) spnMaxCapacity.getValue();

        // Vérifier si les champs sont vides
        if (category_type.isEmpty() || nightly_price.isEmpty() || capacity_max <= 0) {
            JOptionPane.showMessageDialog(this, "Tous les champs doivent être Remplis !");
            return;
        }

        try 
        {
            double price = Double.parseDouble(nightly_price);
            
            // Appel à la DAO
            Categories cat = new Categories(id, category_type, price, capacity_max);
            CategorieDAO dao = new CategorieDAO();

            if (dao.updateCategory(cat)) 
            {
                JOptionPane.showMessageDialog(this, "Catégorie mise à jour avec succès!");
                // Mise à jour de l'interface
                loadCategoriesTable();
                clearCategoryFields(); 
                txtCategoryType.requestFocus();
            } 
            else 
            {
                JOptionPane.showMessageDialog(this, "Erreur lors de la mise à jour.");
            }
        } 
        catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Prix invalide !");
        }
    }//GEN-LAST:event_btnUpdateCategoryActionPerformed

    private void btnDeleteCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteCategoryActionPerformed
        
        int selectedRow = tableCategories.getSelectedRow();
    
        // Vérifier si une ligne a été sélectionnée
        if(selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner la catégorie à supprimer !");
            return; 
        }
    
        // Récupérer l'id à supprimer
        int id = Integer.parseInt(tableCategories.getValueAt(selectedRow, 0).toString());

        // Demander la confirmation
        int opt = JOptionPane.showConfirmDialog(this, 
                "Êtes-vous sûr de vouloir supprimer définitivement cette catégorie ?", 
                "Confirmation de suppression", 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.WARNING_MESSAGE); 
            
        if (opt == JOptionPane.YES_OPTION) {
            CategorieDAO dao = new CategorieDAO();

            // Exécuter la suppression
            if (dao.deleteCategory(id)) {
                JOptionPane.showMessageDialog(this, "Catégorie supprimée avec succès !");

                // Mise à jour de l'interface
                loadCategoriesTable(); 
                clearCategoryFields(); 
            } else {
                JOptionPane.showMessageDialog(this, "Erreur : Impossible de supprimer la catégorie.");
            }
        }
    }//GEN-LAST:event_btnDeleteCategoryActionPerformed

    private void txtSearchUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchUserActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchUserActionPerformed

    private void txtSearchCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchCategoryActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchCategoryActionPerformed

    private void txtSearchCategoryKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchCategoryKeyReleased
        // On récupère le texte et on lance la recherche
        filterCategoriesTable(txtSearchCategory.getText().trim());
    }//GEN-LAST:event_txtSearchCategoryKeyReleased

    private void btnUpdateRoomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateRoomActionPerformed
        
        //  Vérifier si une ligne est sélectionnée
        int selectedRow = tableRooms.getSelectedRow();
        
        if(selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner une chambre dans le tableau !");
            return;
        }

        // Récupérer et nettoyer les données
        int id = (int) tableRooms.getValueAt(selectedRow, 0);
        String room_number = txtRoomNumber.getText().trim();
        String status = "DISPONIBLE"; 

        if(room_number.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez saisir un numéro de chambre !");
            return;
        }

        // Récupérer la catégorie sélectionnée
        Categories selectedCat = (Categories) cbCategory.getSelectedItem();
        if(selectedCat == null) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner une catégorie !");
            return;
        }

        // Créer l'objet avec l'ID et appeler la DAO
        Rooms room = new Rooms(id, room_number, selectedCat.getCategoryid(), status);
        RoomDAO rDAO = new RoomDAO();

        if(rDAO.updateRoom(room)) 
        {
            JOptionPane.showMessageDialog(this, "Chambre mise à jour avec succès !");
            
            // Mise à jour de l'interface
            loadRoomsTable();
            clearRoomFields();
            txtRoomNumber.requestFocus();
        }
        else 
        {
            JOptionPane.showMessageDialog(this, "Erreur lors de la mise à jour.");
        }
    }//GEN-LAST:event_btnUpdateRoomActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddCategory;
    private javax.swing.JButton btnAddRoom;
    private javax.swing.JButton btnAddUser;
    private javax.swing.JButton btnDeleteCategory;
    private javax.swing.JButton btnDeleteRoom;
    private javax.swing.JButton btnDeleteUser;
    private javax.swing.JButton btnUpdateCategory;
    private javax.swing.JButton btnUpdateRoom;
    private javax.swing.JButton btnUpdateUser;
    private javax.swing.JComboBox<Categories> cbCategory;
    private javax.swing.JComboBox<String> cbRole;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JPanel pnlFomrCham;
    private javax.swing.JPanel pnlFormUser;
    private javax.swing.JSpinner spnMaxCapacity;
    private javax.swing.JPanel tabCategories;
    private javax.swing.JPanel tabRooms;
    private javax.swing.JPanel tabUsers;
    private javax.swing.JTable tableCategories;
    private javax.swing.JTable tableRooms;
    private javax.swing.JTable tableUsers;
    private javax.swing.JTextField txtCategoryType;
    private javax.swing.JTextField txtNightlyPrice;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtRoomNumber;
    private javax.swing.JTextField txtSearchCategory;
    private javax.swing.JTextField txtSearchRoom;
    private javax.swing.JTextField txtSearchUser;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
