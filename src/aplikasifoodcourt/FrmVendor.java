/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplikasifoodcourt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;  
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;


/**
 *
 * @author Syabith
 */
public class FrmVendor extends javax.swing.JFrame {

    /**
     * Creates new form NewJFrame
     */
      public void lebarKolom(){
        TableColumn column;
        tblVendor.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        column = tblVendor.getColumnModel().getColumn(0);
        column.setPreferredWidth(65);
        column = tblVendor.getColumnModel().getColumn(1);
        column.setPreferredWidth(130);     
        column = tblVendor.getColumnModel().getColumn(2);
        column.setPreferredWidth(140);
        column = tblVendor.getColumnModel().getColumn(3);
        column.setPreferredWidth(120);   
        column = tblVendor.getColumnModel().getColumn(4);
        column.setPreferredWidth(80); 
    }

    
    Connection conn;
    PreparedStatement ps;
    int ctr;
    Statement st;
    ResultSet rs;
    DefaultTableModel model;
    public FrmVendor() {
        initComponents();
        
        model = new DefaultTableModel ( );
        tblVendor.setModel(model);
        tblVendor.setDefaultEditor(Object.class, null);
        model.addColumn("Id Vendor");
        model.addColumn("Nama");
        model.addColumn("Nama Manager");
        model.addColumn("Tanggal Gabung");
        model.addColumn("Aktif");
        
        try { 
            conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/foodcourt",
                    "root", "");
        } catch (SQLException ex) {
            Logger.getLogger(FrmVendor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        refreshTable();
        tblVendor.setModel(model);
        xdpGabung.setFormats("dd-MM-yyyy");
        xdpGabung.setDate(new Date());
        
    }
    
    private void resetField() {
        lblIdVendor.setText("[ID]");
        tflNamaVendor.setText("");
        tflNamaManager.setText("");
        xdpGabung.setDate(new Date());
        buttonGroup1.clearSelection();
    }
    
    private void refreshTable() {
        model.setRowCount(0);
        String sql = "select * from tblvendor";
        try {
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    new SimpleDateFormat("dd-MM-yyyy").format(rs.getTimestamp(4)),
                    rs.getString(5)});
            }
        } catch (SQLException ex) {
            Logger.getLogger(FrmVendor.class.getName()).log(Level.SEVERE, null, ex);
        }
          lebarKolom();  
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
        tblVendor = new javax.swing.JTable();
        btnCari = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        lblIdVendor = new javax.swing.JLabel();
        tflNamaVendor = new javax.swing.JTextField();
        tflNamaManager = new javax.swing.JTextField();
        xdpGabung = new org.jdesktop.swingx.JXDatePicker();
        btnUpdate = new javax.swing.JButton();
        btnTambah = new javax.swing.JButton();
        rbtnAktif = new javax.swing.JRadioButton();
        rbtnTidakAktif = new javax.swing.JRadioButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(0, 255, 102));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setName("Entry Data Pegawai"); // NOI18N

        jLabel1.setFont(new java.awt.Font("Algerian", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Entry Vendor");

        jPanel1.setBackground(new java.awt.Color(153, 153, 153));

        tflCari.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        tflCari.setToolTipText("Masukkan Nama");

        tblVendor.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        tblVendor.setModel(new javax.swing.table.DefaultTableModel(
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
        tblVendor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblVendorMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblVendor);

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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(tflCari, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnCari))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 492, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tflCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCari))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel6.setFont(new java.awt.Font("Algerian", 1, 18)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Table Vendor");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 184, Short.MAX_VALUE)
        );

        jPanel3.setBackground(new java.awt.Color(153, 153, 153));

        lblIdVendor.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblIdVendor.setText("[ID]");

        tflNamaVendor.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        tflNamaManager.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        xdpGabung.setBackground(new java.awt.Color(0, 204, 0));
        xdpGabung.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N

        btnUpdate.setBackground(new java.awt.Color(255, 255, 255));
        btnUpdate.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnTambah.setBackground(new java.awt.Color(255, 255, 255));
        btnTambah.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnTambah.setText("Tambah");
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

        rbtnAktif.setBackground(new java.awt.Color(153, 153, 153));
        buttonGroup1.add(rbtnAktif);
        rbtnAktif.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        rbtnAktif.setText("Aktif");

        rbtnTidakAktif.setBackground(new java.awt.Color(153, 153, 153));
        buttonGroup1.add(rbtnTidakAktif);
        rbtnTidakAktif.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        rbtnTidakAktif.setText("Tidak Aktif");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setText("Aktif:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("Tanggal Gabung:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("Manager Vendor:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("Nama Vendor:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("ID Vendor:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel8))
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(26, 26, 26)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(lblIdVendor, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(xdpGabung, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(tflNamaManager, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(rbtnAktif, javax.swing.GroupLayout.Alignment.LEADING)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(tflNamaVendor, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(194, 194, 194)
                                .addComponent(rbtnTidakAktif)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnTambah)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnUpdate)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lblIdVendor, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(tflNamaVendor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(tflNamaManager, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(xdpGabung, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(rbtnAktif)
                    .addComponent(rbtnTidakAktif))
                .addGap(8, 8, 8)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTambah)
                    .addComponent(btnUpdate))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 512, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariActionPerformed
        try {
            // TODO add your handling code here:
            st = conn.createStatement();
            String search = tflCari.getText();
            rs = st.executeQuery("SELECT * FROM tblvendor WHERE IdVendor like '%" + search + "%' OR Nama like '%"
                    +search+ "%' OR NamaManager like '%" + search + "%' OR TglGabung like '%" + search + "%'");
            model = (DefaultTableModel) tblVendor.getModel();

            model.setRowCount(0);
            Object [] data = new Object[5];

            while(rs.next())
            {
                data[0] = rs.getString("IdVendor");
                data[1] = rs.getString("Nama");
                data[2] = rs.getString("NamaManager");
                data[3] = new SimpleDateFormat("dd-MM-yyyy").format(rs.getTimestamp(4));
                data[4] = rs.getString("Aktif");
                model.addRow(data);
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(FrmVendor.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }//GEN-LAST:event_btnCariActionPerformed

    private void tblVendorMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblVendorMousePressed
        // TODO add your handling code here:
        int baris;
        String kode, nama, manager, strGabung, aktif;
        Date dtGabung;
        baris = tblVendor.getSelectedRow();
        
        kode = tblVendor.getModel().getValueAt(baris,0).toString();
        nama = tblVendor.getModel().getValueAt(baris,1).toString();
        manager = tblVendor.getModel().getValueAt(baris,2).toString();
        strGabung = tblVendor.getModel().getValueAt(baris,3).toString();
        aktif = tblVendor.getModel().getValueAt(baris,4).toString();
        lblIdVendor.setText(kode);
        tflNamaVendor.setText(nama);
        tflNamaManager.setText(manager);
        try {  
            dtGabung = new SimpleDateFormat("dd-MM-yyyy").parse(strGabung);
            xdpGabung.setDate(dtGabung);
        } catch (ParseException ex) {
            Logger.getLogger(FrmVendor.class.getName()).log(Level.SEVERE, null, ex);
        }
        if("Aktif".equals(tblVendor.getModel().getValueAt(baris, 4).toString())){
            rbtnAktif.setSelected(true);
        }else{
            rbtnTidakAktif.setSelected(true);
        }
        
    }//GEN-LAST:event_tblVendorMousePressed

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        // TODO add your handling code here:
        String kode, nama, manager, kodeterakhir, aktif;
        if(rbtnAktif.isSelected()){
            aktif = "Aktif";
        }else{
            aktif = "Tidak Aktif";
        }
        Date dtGabung = xdpGabung.getDate();
        Timestamp gabung = new Timestamp(dtGabung.getTime());
        nama = tflNamaVendor.getText();
        manager = tflNamaManager.getText();
        int jum = 1001;
        
        try {
            st = conn.createStatement();
            rs = st.executeQuery("SELECT IdVendor FROM tblvendor ORDER BY Idvendor DESC LIMIT 1");
            rs.next();
            kodeterakhir = rs.getString(1);
            jum += Integer.parseInt(kodeterakhir.substring(1));
            rs.close();
        }
        catch(SQLException ex)  {
            Logger.getLogger(FrmVendor.class.getName()).log(Level.SEVERE, null, ex);
        }
        kode = "V" + String.valueOf(jum).substring(1);
        
        String query = "INSERT INTO tblvendor VALUES (?,?,?,?,?)";
        
        try {
            ps = conn.prepareStatement(query);
            ps.setString(1, kode);
            ps.setString(2, nama);
            ps.setString(3, manager);
            ps.setTimestamp(4, gabung);
            ps.setString(5, aktif);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Vendor berhasil ditambahkan");
        }
        catch(SQLException ex)  {
             Logger.getLogger(FrmPegawai.class.getName()).log(Level.SEVERE, null, ex);
        }
        resetField();
        refreshTable();
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        if (lblIdVendor.getText().equals("[ID]")) {
            JOptionPane.showMessageDialog(this, "Pilih Vendor untuk diupdate");
        } else {
            String kode, nama, manager, aktif;
            if(rbtnAktif.isSelected()){
                aktif = "Aktif";
            }else{
                aktif = "Tidak Aktif";
            }
            Date dtGabung = xdpGabung.getDate();
            Timestamp gabung = new Timestamp(dtGabung.getTime());
            kode = lblIdVendor.getText();
            nama = tflNamaVendor.getText();
            manager = tflNamaManager.getText();
            
            String query = "UPDATE tblvendor SET Nama = ?, NamaManager = ?, TglGabung = ?, Aktif = ? WHERE IdVendor = ?";

            try {
                ps = conn.prepareStatement(query);
                ps.setString(1, nama);
                ps.setString(2, manager);
                ps.setTimestamp(3, gabung);
                ps.setString(4, aktif);
                ps.setString(5, kode);
                int pilihan = JOptionPane.showConfirmDialog(this, "Anda yakin ingin mengupdate vendor " + kode, "Konfirmasi",JOptionPane.YES_NO_OPTION);
                if (pilihan == JOptionPane.YES_OPTION) {
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Vendor berhasil diupdate");
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
                new FrmPegawai().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCari;
    private javax.swing.JButton btnTambah;
    private javax.swing.JButton btnUpdate;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblIdVendor;
    private javax.swing.JRadioButton rbtnAktif;
    private javax.swing.JRadioButton rbtnTidakAktif;
    private javax.swing.JTable tblVendor;
    private javax.swing.JTextField tflCari;
    private javax.swing.JTextField tflNamaManager;
    private javax.swing.JTextField tflNamaVendor;
    private org.jdesktop.swingx.JXDatePicker xdpGabung;
    // End of variables declaration//GEN-END:variables
}
