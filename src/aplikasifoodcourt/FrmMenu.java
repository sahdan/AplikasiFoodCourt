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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author Syabith
 */
public class FrmMenu extends javax.swing.JFrame {

    /**
     * Creates new form NewJFrame
     */
    public void lebarKolom(){
        TableColumn column;
        tblMenu.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        column = tblMenu.getColumnModel().getColumn(0);
        column.setPreferredWidth(70);
        column = tblMenu.getColumnModel().getColumn(1);
        column.setPreferredWidth(200);     
        column = tblMenu.getColumnModel().getColumn(2);
        column.setPreferredWidth(165);
        column = tblMenu.getColumnModel().getColumn(3);
        column.setPreferredWidth(60);
    }

    Connection conn;
    PreparedStatement ps;
    int ctr;
    Statement st;
    ResultSet rs;
    private DefaultTableModel model;
    String id;
    public FrmMenu() {
        initComponents();
    }
    
    public FrmMenu(String id) {
        initComponents();
        
        this.id = id;
        model = new DefaultTableModel ( );
        tblMenu.setModel(model);
        tblMenu.setDefaultEditor(Object.class, null);
        model.addColumn("Id");
        model.addColumn("Menu");
        model.addColumn("Harga");
        model.addColumn("Aktif");
        
        try { 
            conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/foodcourt",
                    "root", "");
        } catch (SQLException ex) {
            Logger.getLogger(FrmMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        refreshTable();
    }
    
    private void resetField() {
        lblIdMenu.setText("[ID]");
        tflNamaMenu.setText("");
        tflHarga.setText("");
        buttonGroup1.clearSelection();
    }
    
    private void refreshTable() {
        model.setRowCount(0);
        try {
            String sql = "SELECT a.IdMenu, a.Nama, a.Harga, a.Aktif FROM tblmenu a WHERE a.IdVendor = '" + id + "'";
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString(1),
                    rs.getString(2),
                    rs.getInt(3),
                    rs.getString(4)
                });
            }
            tblMenu.setModel(model);
            lebarKolom();
           
            sql = "SELECT a.Nama FROM tblvendor a WHERE a.IdVendor = '" + id + "'";
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            rs.next()  ;
            lblNamaVendor.setText(rs.getString(1));
        } catch (SQLException e) {
        }
        setCellFormat(2);
    }
        private void setCellFormat(int vColIndex) {
        // Install the custom renderer on the first visible column
    TableColumn col = tblMenu.getColumnModel().getColumn(vColIndex);
    col.setCellRenderer(new RupiahCellRenderer());

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
        tblMenu = new javax.swing.JTable();
        btnCari = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        tflNamaMenu = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        lblNamaVendor = new javax.swing.JLabel();
        tflHarga = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblIdMenu = new javax.swing.JLabel();
        btnTambah = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        jRadioButtonAktif = new javax.swing.JRadioButton();
        jRadioButtonTidakAktif = new javax.swing.JRadioButton();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setName("Entry Data Pegawai"); // NOI18N

        jLabel1.setFont(new java.awt.Font("Algerian", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Entry Menu");

        jPanel1.setBackground(new java.awt.Color(153, 153, 153));

        tflCari.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        tflCari.setToolTipText("Masukkan Nama");

        tblMenu.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        tblMenu.setModel(new javax.swing.table.DefaultTableModel(
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
        tblMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblMenuMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblMenu);

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
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(tflCari, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnCari))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 499, javax.swing.GroupLayout.PREFERRED_SIZE))
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

        tflCari.getAccessibleContext().setAccessibleName("");
        tflCari.getAccessibleContext().setAccessibleDescription("");

        jLabel6.setFont(new java.awt.Font("Algerian", 1, 18)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Table Menu");

        jPanel2.setBackground(new java.awt.Color(153, 153, 153));

        tflNamaMenu.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("Nama Menu:");

        lblNamaVendor.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblNamaVendor.setText("[Nama]");

        tflHarga.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("ID Menu:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("Nama Vendor:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("Harga:");

        lblIdMenu.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblIdMenu.setText("[ID]");

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

        jRadioButtonAktif.setBackground(new java.awt.Color(153, 153, 153));
        buttonGroup1.add(jRadioButtonAktif);
        jRadioButtonAktif.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jRadioButtonAktif.setText("Aktif");

        jRadioButtonTidakAktif.setBackground(new java.awt.Color(153, 153, 153));
        buttonGroup1.add(jRadioButtonTidakAktif);
        jRadioButtonTidakAktif.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jRadioButtonTidakAktif.setText("Tidak Aktif");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText("Aktif:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel3)
                                .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING))
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tflNamaMenu)
                            .addComponent(tflHarga)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblIdMenu)
                                    .addComponent(lblNamaVendor)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jRadioButtonAktif)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jRadioButtonTidakAktif)))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnTambah)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnUpdate)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(lblNamaVendor))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lblIdMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(tflNamaMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(tflHarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButtonAktif)
                    .addComponent(jRadioButtonTidakAktif)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTambah)
                    .addComponent(btnUpdate))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 512, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariActionPerformed
        // TODO add your handling code here:
        try
        {
            st = conn.createStatement();
            String search = tflCari.getText();
            rs = st.executeQuery("SELECT * FROM tblmenu WHERE (IdVendor = '" + id + "') AND " +
                    "(IdMenu like '%" + search + "%' OR Nama like '%"
                    +search+ "%' OR Harga like '" + search + "')");
            model = (DefaultTableModel) tblMenu.getModel();

            model.setRowCount(0);
            String [] data = new String[4];
            int i = 1;

            while(rs.next())
            {
                data[0] = rs.getString("IdMenu");
                data[1] = rs.getString("Nama");
                data[2] = rs.getString("Harga");
                data[3] = rs.getString("Aktif");
                model.addRow(data);
                i++;
            }
            rs.close();
        }
        catch(SQLException ex)  {
            JOptionPane.showMessageDialog(null,"Data yang Anda cari Tidak dapat Ditemukan");
            System.err.println("error (search) : " +ex);
        }
    }//GEN-LAST:event_btnCariActionPerformed

    private void tblMenuMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMenuMousePressed
        // TODO add your handling code here:
        int baris;
        String kode, nama, harga;
        baris = tblMenu.getSelectedRow();
        if("Aktif".equals(tblMenu.getModel().getValueAt(baris, 3).toString())){
            jRadioButtonAktif.setSelected(true);
        }else{
            jRadioButtonTidakAktif.setSelected(true);
        }
        kode = tblMenu.getModel().getValueAt(baris,0).toString();
        nama = tblMenu.getModel().getValueAt(baris,1).toString();
        harga = tblMenu.getModel().getValueAt(baris,2).toString();
        lblIdMenu.setText(kode);
        tflNamaMenu.setText(nama);
        tflHarga.setText(harga);
    }//GEN-LAST:event_tblMenuMousePressed

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        // TODO add your handling code here:
        String kode, nama, harga, kodeterakhir, aktif;
        int jum = 10001;
        if(jRadioButtonAktif.isSelected()){
            aktif = "Aktif";
        }else{
            aktif = "Tidak Aktif";
        }
        try
        {
            st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT IdMenu FROM tblmenu ORDER BY IdMenu DESC LIMIT 1");
            rs.next();
            kodeterakhir = rs.getString(1);
            jum += Integer.parseInt(kodeterakhir.substring(1));
            rs.close();
        }
        catch(SQLException ex)  {
        }
        kode = "M" + String.valueOf(jum).substring(1);
        nama = tflNamaMenu.getText();
        harga = tflHarga.getText();
        String query = "INSERT INTO tblmenu VALUES (?,?,?,?,?)";
        
        try {
            ps = conn.prepareStatement(query);
            ps.setString(1, kode);
            ps.setString(2, nama);
            ps.setInt(3, Integer.parseInt(harga));
            ps.setString(4, id);
            ps.setString(5, aktif);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Menu berhasil ditambahkan");
        }
        catch(SQLException ex)  {
             Logger.getLogger(FrmMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
        resetField();
        refreshTable();
        
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        if (lblIdMenu.getText().equals("[ID]")) {
            JOptionPane.showMessageDialog(this, "Pilih Menu untuk diupdate");
        } else {
            String kode, nama, harga, aktif;
            if(jRadioButtonAktif.isSelected()){
                aktif = "Aktif";
            }else{
                aktif = "Tidak Aktif";
            }
            kode = lblIdMenu.getText();
            nama = tflNamaMenu.getText();
            harga = tflHarga.getText();
            String query = "UPDATE tblmenu SET Nama = ?, Harga = ?, Aktif = ? WHERE IdMenu = ?";

            try {
                ps = conn.prepareStatement(query);
                ps.setString(1, nama);
                ps.setInt(2, Integer.parseInt(harga));
                ps.setString(3, aktif);
                ps.setString(4, kode);
                int pilihan = JOptionPane.showConfirmDialog(this, "Anda Yakin ingin mengupdate menu " + kode, "Konfirmasi",JOptionPane.YES_NO_OPTION);
                if (pilihan == JOptionPane.YES_OPTION) {
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Menu berhasil diupdate");
                }
            } catch (SQLException ex) {
                Logger.getLogger(FrmMenu.class.getName()).log(Level.SEVERE, null, ex);
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
                new FrmMenu().setVisible(true);
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
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JRadioButton jRadioButtonAktif;
    private javax.swing.JRadioButton jRadioButtonTidakAktif;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblIdMenu;
    private javax.swing.JLabel lblNamaVendor;
    private javax.swing.JTable tblMenu;
    private javax.swing.JTextField tflCari;
    private javax.swing.JTextField tflHarga;
    private javax.swing.JTextField tflNamaMenu;
    // End of variables declaration//GEN-END:variables
}