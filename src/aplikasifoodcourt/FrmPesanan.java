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
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.Timer;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author User
 */


public class FrmPesanan extends javax.swing.JFrame {
    java.util.Date tglsekarang = new java.util.Date();
    private SimpleDateFormat smpdtfmt = new SimpleDateFormat("yyyy-M-dd", Locale.getDefault());
    private String tanggal = smpdtfmt.format(tglsekarang);
    /**
     * Creates new form pesanan
     */
    
        public void lebarKolom(){
        TableColumn column;
        tblMenu.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        column = tblMenu.getColumnModel().getColumn(0);
        column.setPreferredWidth(80);
        column = tblMenu.getColumnModel().getColumn(1);
        column.setPreferredWidth(160);     
        column = tblMenu.getColumnModel().getColumn(2);
        column.setPreferredWidth(121);
                
        
    }
        
            public void lebarKolom2(){
        TableColumn column;
        tblPesanan.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        column = tblPesanan.getColumnModel().getColumn(0);
        column.setPreferredWidth(70);
        column = tblPesanan.getColumnModel().getColumn(1);
        column.setPreferredWidth(110);     
        column = tblPesanan.getColumnModel().getColumn(2);
        column.setPreferredWidth(130);
        column = tblPesanan.getColumnModel().getColumn(3);
        column.setPreferredWidth(58);        
        
    }
    
    Connection conn;
    PreparedStatement ps;
    int ctr;
    Statement st;
    ResultSet rs;
    private DefaultTableModel model;
    private DefaultComboBoxModel<String> cbbm;
    String ID = UserID.getUserLogin();
    boolean load = false;
    
    public FrmPesanan() {
        initComponents();
        
        try { 
            conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/foodcourt",
                    "root", "");
        } catch (SQLException ex) {
            Logger.getLogger(FrmPesanan.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        tampil_vendor();
        tampil_menu();
        nama_vendor();
        model = new DefaultTableModel ( );
        tblPesanan.setModel(model);
        tblPesanan.setDefaultEditor(Object.class, null);
        tblMenu.setDefaultEditor(Object.class, null);
        model.addColumn("Kode");
        model.addColumn("Menu");
        model.addColumn("Harga");
        model.addColumn("Jumlah");
        
        idpelayan();
        sum();
        waktu();
        jLabelWaktu.setVisible(false);
        lblIdPelayan.setVisible(false);
        lblNamaPelayan.setText(ID);
        load = true;
        
                
        
    }
    
    private void waktu(){
        ActionListener taskPerformer = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent evt) {
        String nol_jam = "", nol_menit = "",nol_detik = "";

        java.util.Date dateTime = new java.util.Date();
        int nilai_jam = dateTime.getHours();
        int nilai_menit = dateTime.getMinutes();
        int nilai_detik = dateTime.getSeconds();

        if(nilai_jam <= 9) nol_jam= "0";
        if(nilai_menit <= 9) nol_menit= "0";
        if(nilai_detik <= 9) nol_detik= "0";

        String jam = nol_jam + Integer.toString(nilai_jam);
        String menit = nol_menit + Integer.toString(nilai_menit);
        String detik = nol_detik + Integer.toString(nilai_detik);
        
        jLabelWaktu.setText(tanggal+" "+jam+":"+menit+":"+detik+"");
        }
    };
        new Timer(1000, taskPerformer).start();
    }
    
    private void nama_vendor(){
        try {
            String sql = "select * from tblvendor where Nama = '"+cbbVendor.getSelectedItem()+"'";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                jLabelNamaVendor.setText(rs.getString("Nama"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(FrmPesanan.class.getName()).log(Level.SEVERE, null, ex);
        }
}
    
    private void idpelayan(){      
        try {
            String sql = "select * from tbluser where Username = '"+UserID.getUserLogin()+"'";
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                lblIdPelayan.setText(rs.getString("Id"));
            }
        } catch (SQLException e) {
        }
    }
    
    private void sum(){
        int total = 0, harga, amount;
        
        for (int i =0; i< tblPesanan.getRowCount(); i++){
               harga = (int) tblPesanan.getValueAt(i, 2);
               amount = (int) tblPesanan.getValueAt(i, 3);
               total += amount*harga;
        }
        lblTotal.setText(String.format("%,d,00", total));    
    }
    
    private int autokode(){

        int kodeterakhir, jum;
        String sql = "SELECT IdTransaksi FROM tbltransaksi ORDER BY IdTransaksi DESC LIMIT 1";
        try {
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            rs.next();
            kodeterakhir = rs.getInt(1);
            jum = kodeterakhir + 1;
            rs.close();
            return jum;
        } catch (SQLException ex) {
            Logger.getLogger(FrmPesanan.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
        
    }
    
    private void tampil_menu(){
        try {
            model = new DefaultTableModel ( );
            tblMenu.setModel(model);
            model.addColumn("Kode");
            model.addColumn("Nama");
            model.addColumn("Harga");
            String sql = "select tblmenu.IdMenu,tblmenu.Nama,tblmenu.Harga,tblmenu.IdVendor,tblVendor.Nama "
                    + "from tblmenu,tblvendor "
                    + "where tblmenu.IdVendor = tblvendor.IdVendor and "+ "tblVendor.Nama = '"+cbbVendor.getSelectedItem()+"'";
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString(1),
                    rs.getString(2),
                    rs.getInt(3)});
            }
            tblMenu.setModel(model);
        } catch (SQLException ex) {
            Logger.getLogger(FrmPesanan.class.getName()).log(Level.SEVERE, null, ex);
        }
        setCellFormat(2);
        
        lebarKolom();
        
}
    
//    private void pemesanan(){
//        try {
//            String sql = "select * from tblpemesanan where NamaPelayan = '"+UserID.getUserLogin()+"'";
//            Statement st = conn.createStatement();
//            ResultSet rs = st.executeQuery(sql);
//            while (rs.next()) {
//                model.addRow(new Object[]{
//                    rs.getString(1),
//                    rs.getString(2),
//                    rs.getString(3)
//                });
//            }
//           tblPesanan.setModel(model);
//        } catch (Exception e) {
//        }
//    }
    
    private void tampil_vendor() {
    cbbm = new DefaultComboBoxModel<>();
    cbbVendor.setModel(cbbm);
    String sql = "Select * from tblvendor";
    try {
        st = conn.createStatement();
        rs = st.executeQuery(sql);
        while (rs.next()) {
            cbbm.addElement(rs.getString("Nama"));
        }
    } catch (SQLException ex) {
        Logger.getLogger(FrmPesanan.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    
    
}
        private void setCellFormat(int vColIndex) {
        // Install the custom renderer on the first visible column
    TableColumn col = tblMenu.getColumnModel().getColumn(vColIndex);
    col.setCellRenderer(new RupiahCellRenderer());

    }
                private void setCellFormat2(int vColIndex) {
        // Install the custom renderer on the first visible column
    TableColumn col = tblPesanan.getColumnModel().getColumn(vColIndex);
    col.setCellRenderer(new RupiahCellRenderer());

    }
//                       private void setCellFormat3(int vColIndex) {
//        // Install the custom renderer on the first visible column
//    TableColumn col = tblPesanan.getColumnModel().getColumn(vColIndex);
//    col.setCellRenderer(new RupiahCellRenderer());
//
//    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel11 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMenu = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        cbbVendor = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabelNamaVendor = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblPesanan = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        btnTambah = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        lblTotal = new javax.swing.JLabel();
        btnPesan = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        lblNamaPelayan = new javax.swing.JLabel();
        tflMeja = new javax.swing.JTextField();
        lblIdPelayan = new javax.swing.JLabel();
        jLabelWaktu = new javax.swing.JLabel();

        jLabel11.setText("jLabel11");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));
        jPanel1.setPreferredSize(new java.awt.Dimension(340, 170));

        tblMenu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null}
            },
            new String [] {
                "Title 1", "Title 2"
            }
        ));
        tblMenu.setRowHeight(32);
        tblMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblMenuMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblMenu);

        jPanel4.setBackground(new java.awt.Color(153, 153, 153));

        cbbVendor.setActionCommand("");
        cbbVendor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbVendorActionPerformed(evt);
            }
        });

        jLabel2.setText("Pilih Vendor");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(85, 85, 85))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbbVendor, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbbVendor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(153, 153, 153));

        jLabelNamaVendor.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabelNamaVendor.setText("[Nama Vendor]");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setText("Daftar Menu");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabelNamaVendor))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelNamaVendor)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 368, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(204, 204, 204));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("Order");

        jLabel4.setText("Nomor Meja");

        jLabel7.setText("Nama Pelayan");

        tblPesanan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3"
            }
        ));
        tblPesanan.setRowHeight(32);
        jScrollPane3.setViewportView(tblPesanan);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel8.setText("Total Harga (Rp):");

        btnTambah.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnTambah.setText("+");
        btnTambah.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                btnTambahItemStateChanged(evt);
            }
        });
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

        btnHapus.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnHapus.setText("-");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        lblTotal.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblTotal.setText("[Harga]");

        btnPesan.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnPesan.setText("Pesan");
        btnPesan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesanActionPerformed(evt);
            }
        });

        btnReset.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnReset.setText("Reset");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        lblNamaPelayan.setText("[Nama Pelayan]");

        tflMeja.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tflMejaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tflMejaKeyTyped(evt);
            }
        });

        lblIdPelayan.setForeground(new java.awt.Color(255, 0, 0));
        lblIdPelayan.setText("[ID]");

        jLabelWaktu.setForeground(new java.awt.Color(255, 0, 0));
        jLabelWaktu.setText("[Waktu]");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(25, 25, 25)
                        .addComponent(lblIdPelayan)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelWaktu)
                        .addGap(0, 225, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(9, 9, 9)
                        .addComponent(lblNamaPelayan)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tflMeja, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(btnTambah)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnHapus)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnPesan))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel8)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnReset)
                            .addComponent(lblTotal, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lblIdPelayan)
                    .addComponent(jLabelWaktu))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(lblNamaPelayan)
                    .addComponent(jLabel4)
                    .addComponent(tflMeja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTambah)
                    .addComponent(btnHapus))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTotal)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPesan)
                    .addComponent(btnReset))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 478, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void cbbVendorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbVendorActionPerformed
        // TODO add your handling code here:
        if (load) {
            tampil_menu();
            nama_vendor();
        }
    }//GEN-LAST:event_cbbVendorActionPerformed

    private void tblMenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMenuMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblMenuMouseClicked

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        // TODO add your handling code here:
        if (tblMenu.getSelectedRow() < 0)
            JOptionPane.showMessageDialog(this, "Pilih menu untuk ditambah");
        else {
            int baris = tblMenu.getSelectedRow();
            int jumlah = Integer.parseInt(JOptionPane.showInputDialog(this, "Berapa porsi? "));
            model = (DefaultTableModel) tblPesanan.getModel();
            model.addRow(new Object[] {
                tblMenu.getValueAt(baris, 0),
                tblMenu.getValueAt(baris, 1),
                tblMenu.getValueAt(baris, 2),
                jumlah
            });
            sum();
        }
        lebarKolom2();
        setCellFormat2(2);
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        // TODO add your handling code here:
        model = (DefaultTableModel) tblPesanan.getModel();
        model.setRowCount(0);
        tflMeja.setText("");
        sum();
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        // TODO add your handling code here:
        if (tblPesanan.getSelectedRow() < 0)
            JOptionPane.showMessageDialog(this, "Pilih pesanan untuk dihapus");
        else {
            model = (DefaultTableModel) tblPesanan.getModel();
            model.removeRow(tblPesanan.getSelectedRow());
            sum();
        }
    }//GEN-LAST:event_btnHapusActionPerformed

    private void btnTambahItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_btnTambahItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_btnTambahItemStateChanged

    private void btnPesanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesanActionPerformed
        // TODO add your handling code here:
        int konfirm = JOptionPane.showConfirmDialog(this, "Mohon tinjau kembali pesanan dan konfirmasi",
                        "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if(konfirm == JOptionPane.YES_OPTION) {
            // Mngisi Tabel Transaksi
            if(tflMeja.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Anda Belum Mengisi Nomor Meja");
                return;
            }
            String WaktuTransaksi = jLabelWaktu.getText();
            String IdPelayan = lblIdPelayan.getText();
            String Total = lblTotal.getText();
            String Meja = tflMeja.getText();
            int kode = autokode();
            try {
                ps = conn.prepareStatement("insert into tbltransaksi values (?,?,?,?,?,?,?)");
                ps.setInt (1, kode);
                ps.setString (2, WaktuTransaksi);
                ps.setString (3, IdPelayan);
                ps.setString (4, "");
                ps.setString (5, Total);
                ps.setString (6, Meja);
                ps.setString (7, "");
                ps.executeUpdate();
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(FrmPesanan.class.getName()).log(Level.SEVERE, null, ex);
                setCellFormat2(2);
            }
            
            // Mengisi Tabel Rincian Transaksi
            for (int i = 0; i < tblPesanan.getRowCount(); i++) {
                String IdMenu = tblPesanan.getValueAt(i,0).toString();
                int jumlah = (int) tblPesanan.getValueAt(i,3);
                try {
                ps = conn.prepareStatement("insert into tblrinciantransaksi values (?,?,?,?)");
                ps.setInt (1, kode);
                ps.setString (2, IdMenu);
                ps.setInt (3, jumlah);
                ps.setString (4, "pending");
                ps.executeUpdate();
                ps.close();
                } catch (SQLException ex) {
                    Logger.getLogger(FrmPesanan.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
            
            model = (DefaultTableModel) tblPesanan.getModel();
            model.setRowCount(0);
            tflMeja.setText("");
            sum();
            
                
        }
        
    }//GEN-LAST:event_btnPesanActionPerformed

    private void tflMejaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tflMejaKeyTyped
        // TODO add your handling code here:
        char karakter = evt.getKeyChar();
        if(!((karakter >= '0') && (karakter <= '9') || (karakter == KeyEvent.VK_BACK_SPACE) || (karakter == KeyEvent.VK_DELETE))){
            getToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_tflMejaKeyTyped

    private void tflMejaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tflMejaKeyReleased
        // TODO add your handling code here:
        String input = tflMeja.getText();
        if(input.length()>2){
            JOptionPane.showMessageDialog(rootPane,"Jumlah Input Melebihi Batas");
            tflMeja.setText("");
        }
    }//GEN-LAST:event_tflMejaKeyReleased

  
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        FrmLoginPelayan fr = new FrmLoginPelayan();
        fr.setVisible(true);
    }//GEN-LAST:event_formWindowClosing

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
            java.util.logging.Logger.getLogger(FrmPesanan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmPesanan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmPesanan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmPesanan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmPesanan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnPesan;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnTambah;
    private javax.swing.JComboBox<String> cbbVendor;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabelNamaVendor;
    private javax.swing.JLabel jLabelWaktu;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblIdPelayan;
    private javax.swing.JLabel lblNamaPelayan;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JTable tblMenu;
    private javax.swing.JTable tblPesanan;
    private javax.swing.JTextField tflMeja;
    // End of variables declaration//GEN-END:variables
}
