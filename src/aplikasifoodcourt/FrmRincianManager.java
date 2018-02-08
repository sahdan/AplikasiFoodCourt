/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplikasifoodcourt;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author Windows 7
 */
public class FrmRincianManager extends javax.swing.JFrame {

    /**
     * Creates new form Kasir_Form3
     */
    public void lebarKolom(){
        TableColumn column;
        tblRincianTransaksi.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        column = tblRincianTransaksi.getColumnModel().getColumn(0);
        column.setPreferredWidth(120);
        column = tblRincianTransaksi.getColumnModel().getColumn(1);
        column.setPreferredWidth(160);     
        column = tblRincianTransaksi.getColumnModel().getColumn(2);
        column.setPreferredWidth(140);
        column = tblRincianTransaksi.getColumnModel().getColumn(3);
        column.setPreferredWidth(98);     
        column = tblRincianTransaksi.getColumnModel().getColumn(4);
        column.setPreferredWidth(160);
    }

    Connection conn;
    PreparedStatement ps;
    int ctr;
    Statement st;
    ResultSet rs;
    private DefaultTableModel model;
            
    public FrmRincianManager() {
        initComponents();
    }
    
    public FrmRincianManager(String tId, int total) {
        initComponents();
        
        //Setting Table
        String[] tblHeader = new String[] {"ID Menu", "Nama Menu", "Harga", "Jumlah", "Sub Total"};
        model = new DefaultTableModel(tblHeader, 0);
        tblRincianTransaksi.setModel(model);
        tblRincianTransaksi.setDefaultEditor(Object.class, null);
        lebarKolom();
        
        //Setting Labels
        lblTransaksiId.setText(tId);
        lblTotal.setText(total +"");
        
        //Isi Table
        try {
            conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/foodcourt",
                    "root", "");
            String sql = "SELECT a.IdMenu, b.Nama, b.Harga, a.Jumlah, (b.Harga * a.Jumlah) as SubTotal "
                    + "FROM tblrinciantransaksi a,tblmenu b, tblVendor c "
                    + "WHERE a.IdMenu = b.IdMenu AND b.IdVendor = c.IdVendor "
                    + "AND a.IdTransaksi = '"+tId+"'";
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString(1),
                    rs.getString(2),
                    rs.getInt(3),
                    rs.getInt(4),
                    rs.getInt(5)
                    });
            }
        } catch (SQLException ex) {
            Logger.getLogger(FrmRincianManager.class.getName()).log(Level.SEVERE, null, ex);
        }  
        setCellFormat(2);
        setCellFormat(4);
    }
    
    public FrmRincianManager(String tId, String vId, int total) {
        initComponents();
        
        //Setting Table
        String[] tblHeader = new String[] {"ID Menu", "Nama Menu", "Harga", "Jumlah", "Sub Total"};
        model = new DefaultTableModel(tblHeader, 0);
        tblRincianTransaksi.setModel(model);
        tblRincianTransaksi.setDefaultEditor(Object.class, null);
        lebarKolom();
        
        //Setting Labels
        lblTransaksiId.setText(tId);
        lblTotal.setText(total +"");
        
        //Isi Table
        try {
            conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/foodcourt",
                    "root", "");
            String sql = "SELECT a.IdMenu, b.Nama, b.Harga, a.Jumlah, (b.Harga * a.Jumlah) as SubTotal "
                    + "FROM tblrinciantransaksi a,tblmenu b, tblVendor c "
                    + "WHERE a.IdMenu = b.IdMenu AND b.IdVendor = c.IdVendor "
                    + "AND a.IdTransaksi = '"+tId+"' AND c.IdVendor = '"+vId+"'";
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString(1),
                    rs.getString(2),
                    rs.getInt(3),
                    rs.getInt(4),
                    rs.getInt(5)
                    });
            }
        } catch (SQLException ex) {
            Logger.getLogger(FrmRincianManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
        private void setCellFormat(int vColIndex) {
        // Install the custom renderer on the first visible column
    TableColumn col = tblRincianTransaksi.getColumnModel().getColumn(vColIndex);
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

        jScrollPane1 = new javax.swing.JScrollPane();
        tblRincianTransaksi = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        lblTransaksiId = new javax.swing.JLabel();
        lblTransaksiId1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        tblRincianTransaksi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Id Menu", "Nama Menu", "Harga", "Banyak", "Jumlah"
            }
        ));
        jScrollPane1.setViewportView(tblRincianTransaksi);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Rincian Transaksi");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel12.setText("Total (RP):");

        lblTotal.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblTotal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTotal.setText("[Total]");

        lblTransaksiId.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblTransaksiId.setText("[TRANSAKSI ID]");

        lblTransaksiId1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblTransaksiId1.setText("Transaksi ID:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(221, 221, 221)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblTransaksiId1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblTransaksiId)))
                        .addGap(0, 203, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTransaksiId1)
                    .addComponent(lblTransaksiId))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(lblTotal))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(FrmRincianManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmRincianManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmRincianManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmRincianManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
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
                new FrmRincianManager().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JLabel lblTransaksiId;
    private javax.swing.JLabel lblTransaksiId1;
    private javax.swing.JTable tblRincianTransaksi;
    // End of variables declaration//GEN-END:variables
}
