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
 * @author Windows 7
 */
public class FrmKasir extends javax.swing.JFrame {

    /**
     * Creates new form Kasir_Form1
     */
    Connection conn;
    PreparedStatement ps;
    int ctr;
    Statement st;
    ResultSet rs;
    private DefaultTableModel model;
    
    DefaultTableModel dtm;
    public FrmKasir() {
        initComponents();
    }
    public FrmKasir(String id) {
        initComponents();
        
        String[] tblHeader = new String[] {"ID Transaksi", "No. Meja", "Total"};
        model = new DefaultTableModel(tblHeader, 0);
        tblTransaksi.setModel(model);
        tblTransaksi.setDefaultEditor(Object.class, null);
        setTanggal();
        
        try {
            conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/foodcourt",
                    "root", "");
           
        } catch (SQLException ex) {
            Logger.getLogger(FrmKasir.class.getName()).log(Level.SEVERE, null, ex);
        }
        refreshTable();
        jLabel16.setText(id);
        
    }
    
    private void refreshTable() {
        String sql = "SELECT IdTransaksi, Meja, Total FROM tbltransaksi WHERE Status = ''";
        try {
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("IdTransaksi"),
                    rs.getInt("Meja"),
                    rs.getInt("Total")
                    });
            }
        } catch (SQLException ex) {
            Logger.getLogger(FrmKasir.class.getName()).log(Level.SEVERE, null, ex);
        }
         setCellFormat(2);
    }
    private void setTanggal(){
        java.util.Date skrg = new java.util.Date();
        java.text.SimpleDateFormat kal = new java.text.SimpleDateFormat("dd/MM/yyyy");
        lblTanggal.setText(kal.format(skrg));    
    }
    
        private void setCellFormat(int vColIndex) {
        // Install the custom renderer on the first visible column
    TableColumn col = tblTransaksi.getColumnModel().getColumn(vColIndex);
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

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        tflMeja = new javax.swing.JTextField();
        lblTanggal = new javax.swing.JLabel();
        btnCari = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblTransaksi = new javax.swing.JTable();
        jLabel16 = new javax.swing.JLabel();
        btnRincian = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Algerian", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("TRANSAKSI");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("ID Pegawai:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("No Meja:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("Tanggal:");

        tflMeja.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        lblTanggal.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblTanggal.setText("[DD/MM/YY]");

        btnCari.setBackground(new java.awt.Color(255, 255, 255));
        btnCari.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnCari.setText("Cari");
        btnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariActionPerformed(evt);
            }
        });

        tblTransaksi.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        tblTransaksi.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblTransaksi);

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel16.setText("[ID]");

        btnRincian.setBackground(new java.awt.Color(255, 255, 255));
        btnRincian.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnRincian.setText("Rincian");
        btnRincian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRincianActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 648, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnRincian))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(tflMeja, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addGap(18, 18, 18)
                                        .addComponent(lblTanggal))
                                    .addComponent(btnCari)))
                            .addComponent(jLabel16))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel16)
                    .addComponent(jLabel4)
                    .addComponent(lblTanggal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(tflMeja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCari))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRincian)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariActionPerformed
        // TODO add your handling code here:
        try {
            st = conn.createStatement();
            
            String search = tflMeja.getText();
            rs = st.executeQuery("SELECT IdTransaksi, Meja, Total "
                    + "FROM tbltransaksi WHERE Status = '' AND Meja like '%"+search+"%'");
            dtm = (DefaultTableModel) tblTransaksi.getModel();

            dtm.setRowCount(0);
            Object [] data = new Object[6];
            int i = 1;

            while(rs.next())
            {
                data[0] = rs.getString("IdTransaksi");
                data[1] = rs.getInt("Meja");
                data[2] = rs.getInt("Total");
                dtm.addRow(data);
                i++;
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(FrmKasir.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnCariActionPerformed

    private void btnRincianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRincianActionPerformed
        // TODO add your handling code here:
        int baris = tblTransaksi.getSelectedRow();
        if (baris < 0)
            JOptionPane.showMessageDialog(this, "Pilih transaki");
        else {
            String id = tblTransaksi.getValueAt(baris, 0).toString();
            int meja = (int) tblTransaksi.getValueAt(baris, 1);
            int total = (int) tblTransaksi.getValueAt(baris, 2);
            FrmRincian fr = new FrmRincian(id, meja, total);
            fr.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_btnRincianActionPerformed

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
            java.util.logging.Logger.getLogger(FrmKasir.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmKasir.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmKasir.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmKasir.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new FrmKasir().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCari;
    private javax.swing.JButton btnRincian;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblTanggal;
    private javax.swing.JTable tblTransaksi;
    private javax.swing.JTextField tflMeja;
    // End of variables declaration//GEN-END:variables
}
