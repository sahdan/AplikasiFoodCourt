/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplikasifoodcourt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author Syabith
 */
public class FrmUser extends javax.swing.JFrame {

    /**
     * Creates new form NewJFrame
     */
    public void lebarKolom(){
        TableColumn column;
        tblUser.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        column = tblUser.getColumnModel().getColumn(0);
        column.setPreferredWidth(60);
        column = tblUser.getColumnModel().getColumn(1);
        column.setPreferredWidth(80);     
        column = tblUser.getColumnModel().getColumn(2);
        column.setPreferredWidth(140);
        column = tblUser.getColumnModel().getColumn(3);
        column.setPreferredWidth(140);    
        column = tblUser.getColumnModel().getColumn(4);
        column.setPreferredWidth(70);
    }

    Connection conn;
    PreparedStatement ps;
    int ctr;
    Statement st;
    ResultSet rs;
    ArrayList<String[]> arr;
    private DefaultTableModel model;
    private DefaultComboBoxModel<String> cbbm;
    public FrmUser() {
        initComponents();
        
        arr = new ArrayList<>();
        model = new DefaultTableModel();
        cbbm = new DefaultComboBoxModel<>();
        tblUser.setModel(model);
        model.addColumn("IdUser");
        model.addColumn("Id");
        model.addColumn("Username");
        model.addColumn("Fungsi");
        model.addColumn("Aktif");
        try {
            conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/foodcourt",
                    "root", "");
        } catch (SQLException ex) {
            Logger.getLogger(FrmUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        // For ComboBox
        try { 
            // To get the list of Vendor
            String sql = "select IdVendor, Nama from tblvendor";
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                cbbm.addElement(rs.getString(2));
                arr.add(new String[] {rs.getString(1),
                    rs.getString(2)});
            }
            cbbId.setModel(cbbm);
            cbbId.setSelectedIndex(-1);
        } catch (SQLException ex) {
            Logger.getLogger(FrmUser.class.getName()).log(Level.SEVERE, null, ex);
        } 
        refreshTable();
        lblKeterangan.setVisible(false);
    }
    
    private void resetField() {
        lblId.setText("[ID]");
        cbbId.setSelectedIndex(-1);
        buttonGroup1.clearSelection();
        tflUsername.setText("");
        pflPassword.setText("");
        pflPassword1.setText("");
        cbbFungsi.setSelectedIndex(-1);
        lblKeterangan.setVisible(false);
    }
    
    private void refreshTable() {
        try { 
            model.setRowCount(0);
            conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/foodcourt",
                    "root", "");
            String sql = "select IdUser, Id, Username, Fungsi, Aktif from tbluser WHERE Id like 'V%'";
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5)
                });
            }
            tblUser.setModel(model);
            lebarKolom();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(FrmUser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        tflCari = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblUser = new javax.swing.JTable();
        btnCari = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        tflUsername = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        cbbFungsi = new javax.swing.JComboBox<String>();
        pflPassword = new javax.swing.JPasswordField();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btnTambah = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        pflPassword1 = new javax.swing.JPasswordField();
        jLabel4 = new javax.swing.JLabel();
        cbbId = new javax.swing.JComboBox<String>();
        jLabel9 = new javax.swing.JLabel();
        lblId = new javax.swing.JLabel();
        lblKeterangan = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jRadioButtonaktif = new javax.swing.JRadioButton();
        jRadioButtontidakaktif = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setName("Entry Data Pegawai"); // NOI18N

        jLabel1.setFont(new java.awt.Font("Algerian", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Entry User");

        jPanel1.setBackground(new java.awt.Color(153, 153, 153));

        tflCari.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        tflCari.setToolTipText("Masukkan Username");

        tblUser.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        tblUser.setModel(new javax.swing.table.DefaultTableModel(
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
        tblUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblUserMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblUser);

        btnCari.setBackground(new java.awt.Color(255, 255, 255));
        btnCari.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnCari.setText("Cari");
        btnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(tflCari, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnCari))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 492, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tflCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCari))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel6.setFont(new java.awt.Font("Algerian", 1, 18)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Table User");

        jPanel2.setBackground(new java.awt.Color(153, 153, 153));

        tflUsername.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("Username:");

        cbbFungsi.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        cbbFungsi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Manager Vendor", "Dapur" }));
        cbbFungsi.setToolTipText("");

        pflPassword.setToolTipText("");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("Vendor:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("Password:");

        btnTambah.setBackground(new java.awt.Color(255, 255, 255));
        btnTambah.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnTambah.setText("Tambah");
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

        btnUpdate.setBackground(new java.awt.Color(255, 255, 255));
        btnUpdate.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setText("Konfirm Password:");

        pflPassword1.setToolTipText("");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("Fungsi:");

        cbbId.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        cbbId.setMaximumRowCount(5);
        cbbId.setToolTipText("");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setText("ID User:");

        lblId.setText("[ID]");

        lblKeterangan.setForeground(new java.awt.Color(153, 0, 0));
        lblKeterangan.setText("Kosongkan password jika tidak diubah");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setText("Aktif:");

        jRadioButtonaktif.setBackground(new java.awt.Color(153, 153, 153));
        buttonGroup1.add(jRadioButtonaktif);
        jRadioButtonaktif.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jRadioButtonaktif.setText("Aktif");

        jRadioButtontidakaktif.setBackground(new java.awt.Color(153, 153, 153));
        buttonGroup1.add(jRadioButtontidakaktif);
        jRadioButtontidakaktif.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jRadioButtontidakaktif.setText("Tidak Aktif");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lblKeterangan)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                        .addComponent(btnTambah)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnUpdate))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel7)
                                                    .addComponent(jLabel2)
                                                    .addComponent(jLabel9)
                                                    .addComponent(jLabel4))
                                                .addGap(58, 58, 58))
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                                .addComponent(jLabel10)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(jRadioButtonaktif)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jRadioButtontidakaktif))
                                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(lblId, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(cbbId, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(cbbFungsi, 0, 173, Short.MAX_VALUE)
                                                .addComponent(tflUsername))))))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(58, 58, 58)
                                .addComponent(pflPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addGap(6, 6, 6)
                                .addComponent(pflPassword1)))
                        .addContainerGap())))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(lblId))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cbbId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbbFungsi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(tflUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jRadioButtonaktif)
                    .addComponent(jRadioButtontidakaktif))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblKeterangan)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(pflPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(pflPassword1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnUpdate)
                    .addComponent(btnTambah))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 512, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariActionPerformed
        try {
            // TODO add your handling code here:
            st = conn.createStatement();
            String search = tflCari.getText();
            rs = st.executeQuery("SELECT IdUser, Id, Username, Fungsi, Aktif FROM tbluser WHERE Id like 'V%' AND (IdUser like '%" + search + "%' OR Username like '%"
                    +search+ "%' OR Id like '%" + search + "%' OR Fungsi like '%" + search + "%')");
            model = (DefaultTableModel) tblUser.getModel();

            model.setRowCount(0);
            String [] data = new String[5];

            while(rs.next())
            {
                data[0] = rs.getString(1);
                data[1] = rs.getString(2);
                data[2] = rs.getString(3);
                data[3] = rs.getString(4);
                data[4] = rs.getString(5);
                model.addRow(data);
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(FrmUser.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }//GEN-LAST:event_btnCariActionPerformed

    private void tblUserMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblUserMousePressed
        // TODO add your handling code here:
        int baris;
        String kode, id, username, fungsi, nama, aktif;
        baris = tblUser.getSelectedRow();
        kode = tblUser.getModel().getValueAt(baris,0).toString();
        id = tblUser.getModel().getValueAt(baris,1).toString();
        username = tblUser.getModel().getValueAt(baris,2).toString();
        fungsi = tblUser.getModel().getValueAt(baris,3).toString();
        aktif = tblUser.getModel().getValueAt(baris,4).toString();
        lblId.setText(kode);
        int index = 0;
        for (int i = 0; i < arr.size();i++) {
            if (arr.get(i)[0].equals(id))
                index = i;
        }
        if("Aktif".equals(tblUser.getModel().getValueAt(baris, 4).toString())){
            jRadioButtonaktif.setSelected(true);
        }else{
            jRadioButtontidakaktif.setSelected(true);
        }
        nama = arr.get(index)[1];
        cbbId.setSelectedItem(nama);
        tflUsername.setText(username);
        cbbFungsi.setSelectedItem(fungsi);
        pflPassword.setText("");
        pflPassword1.setText("");
        lblKeterangan.setVisible(true);
    }//GEN-LAST:event_tblUserMousePressed

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        // TODO add your handling code here:
        String kode, id, fungsi, username, password, kpassword, kodeterakhir, aktif;
        String hash = "";
        password = new String(pflPassword.getPassword());
        kpassword = new String(pflPassword1.getPassword());
        if(jRadioButtonaktif.isSelected()){
            aktif = "Aktif";
        }else{
            aktif = "Tidak Aktif";
        }
        if(!password.equals(kpassword)) {
            JOptionPane.showMessageDialog(this, "Konfirmasi Password tidak sama");
        } else {
                
            try {
                username = tflUsername.getText();
                st = conn.createStatement();
                rs = st.executeQuery("SELECT Username FROM tbluser WHERE Username = '" + username + "'");
                if (rs.next()) {
                    JOptionPane.showMessageDialog(this, "Username sudah terpakai");
                } else {
                    int index = cbbId.getSelectedIndex();
                    id = arr.get(index)[0];
                    fungsi = cbbFungsi.getSelectedItem().toString();
                    int jum = 1001;

                    st = conn.createStatement();
                    rs = st.executeQuery("SELECT IdUser FROM tbluser ORDER BY IdUser DESC LIMIT 1");
                    rs.next();
                    kodeterakhir = rs.getString(1);
                    jum += Integer.parseInt(kodeterakhir.substring(1));
                    rs.close();
                    kode = "U" + String.valueOf(jum).substring(1);
                    
                    //Password Hashing
                    MessageDigest md;
                    try {
                        md = MessageDigest.getInstance("MD5");
                        md.update(password.getBytes());
                        byte[] digest = md.digest();
                        hash = DatatypeConverter.printHexBinary(digest).toUpperCase();
                    } catch (NoSuchAlgorithmException ex) {
                        Logger.getLogger(FrmUser.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    String query = "INSERT INTO tbluser VALUES (?,?,?,?,?,?)";
                    
                    ps = conn.prepareStatement(query);
                    ps.setString(1, kode);
                    ps.setString(2, username);
                    ps.setString(3, hash);
                    ps.setString(4, id);
                    ps.setString(5, fungsi);
                    ps.setString(6, aktif);
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(this, "User berhasil ditambahkan");
                    
                    resetField();
                    refreshTable();
                }
            } catch (SQLException ex) {
                Logger.getLogger(FrmUser.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        if (lblId.getText().equals("[ID]")) {
            JOptionPane.showMessageDialog(this, "Pilih user untuk diupdate");
        } else {
            String kode, id, fungsi, username, password, kpassword, kodeterakhir, aktif;
            String hash = "";
            kode = lblId.getText();
            int index = cbbId.getSelectedIndex();
            id = arr.get(index)[0];
            fungsi = cbbFungsi.getSelectedItem().toString();
            username = tflUsername.getText();
            password = new String(pflPassword.getPassword());
            kpassword = new String(pflPassword1.getPassword());
            if(jRadioButtonaktif.isSelected()){
                aktif = "Aktif";
            }else{
                aktif = "Tidak Aktif";
            }
            try {
                if(password.equals("")) {
                    String query = "UPDATE tbluser SET Username = ?, Id = ?, Fungsi = ?, Aktif = ? WHERE IdUser = ?";
                    ps = conn.prepareStatement(query);
                    ps.setString(1, username);
                    ps.setString(2, id);
                    ps.setString(3, fungsi);
                    ps.setString(4, aktif);
                    ps.setString(5, kode);
                } else if(!password.equals(kpassword)) {
                    JOptionPane.showMessageDialog(this, "Konfirmasi Password tidak sama");
                    return;
                } else {
                    MessageDigest md;
                    try {
                        md = MessageDigest.getInstance("MD5");
                        md.update(password.getBytes());
                        byte[] digest = md.digest();
                        hash = DatatypeConverter.printHexBinary(digest).toUpperCase();
                    } catch (NoSuchAlgorithmException ex) {
                        Logger.getLogger(FrmUser.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    String query = "UPDATE tbluser SET Username = ?, Password = ?, Id = ?, Fungsi = ?, Aktif = ? WHERE IdUser = ?";
                    ps = conn.prepareStatement(query);
                    ps.setString(1, username);
                    ps.setString(2, hash);
                    ps.setString(3, id);
                    ps.setString(4, fungsi);
                    ps.setString(5, aktif);
                    ps.setString(6, kode);
                }
                
                int pilihan = JOptionPane.showConfirmDialog(this, "Anda Yaking ingin mengupdate user " + kode, "Konfirmasi",JOptionPane.YES_NO_OPTION);
                if (pilihan == JOptionPane.YES_OPTION) {
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(this, "User berhasil diupdate");
                }
            } catch (SQLException ex) {
                Logger.getLogger(FrmPegawai.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            resetField();
            refreshTable();
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    /**
     * @param args the command line arguments
    */ 
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmPegawai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmPegawai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmPegawai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmPegawai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmUser().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCari;
    private javax.swing.JButton btnTambah;
    private javax.swing.JButton btnUpdate;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cbbFungsi;
    private javax.swing.JComboBox<String> cbbId;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JRadioButton jRadioButtonaktif;
    private javax.swing.JRadioButton jRadioButtontidakaktif;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblId;
    private javax.swing.JLabel lblKeterangan;
    private javax.swing.JPasswordField pflPassword;
    private javax.swing.JPasswordField pflPassword1;
    private javax.swing.JTable tblUser;
    private javax.swing.JTextField tflCari;
    private javax.swing.JTextField tflUsername;
    // End of variables declaration//GEN-END:variables
}
