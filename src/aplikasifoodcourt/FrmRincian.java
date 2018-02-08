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
public class FrmRincian extends javax.swing.JFrame {

    /**
     * Creates new form Kasir_Form3
     */
    public void lebarKolom(){
        TableColumn column;
        tblRincianTransaksi.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        column = tblRincianTransaksi.getColumnModel().getColumn(0);
        column.setPreferredWidth(90);
        column = tblRincianTransaksi.getColumnModel().getColumn(1);
        column.setPreferredWidth(165);     
        column = tblRincianTransaksi.getColumnModel().getColumn(2);
        column.setPreferredWidth(150);
        column = tblRincianTransaksi.getColumnModel().getColumn(3);
        column.setPreferredWidth(70); 
        column = tblRincianTransaksi.getColumnModel().getColumn(4);
        column.setPreferredWidth(170); 
    }

    Connection conn;
    PreparedStatement ps;
    int ctr;
    Statement st;
    ResultSet rs;
    private DefaultTableModel model;
    String pId = UserID.getId();
    boolean terbayar = false;
    int meja, total;
    String tId;
            
    public FrmRincian() {
        initComponents();
        
//        String[] tblHeader = new String[] {"Nama Menu", "Harga", "Banyak", "Sub Total"};
//        dtm = new DefaultTableModel(tblHeader, 0);
//        tblRincianTransaksi.setModel(dtm);
//        
//
//        dtm.addRow(new Object[] {
//            "Pempek Selam",
//            "10000",
//            "3",
//            "30000"
//        });
//        
//        dtm.addRow(new Object[] {
//            "Pempek Lenjer",
//            "10000",
//            "4",
//            "40000",
//        });
    }
    
    public FrmRincian(String tId, int meja, int total) {
        initComponents();
        this.meja = meja;
        this.total = total;
        this.tId = tId;
        
        //Setting Table
        String[] tblHeader = new String[] {"ID Menu", "Nama Menu", "Harga", "Jumlah", "Sub Total"};
        model = new DefaultTableModel(tblHeader, 0);
        tblRincianTransaksi.setModel(model);
        tblRincianTransaksi.setDefaultEditor(Object.class, null);
        lebarKolom();
        
        setCellFormat(4);
        
        //Setting Labels
        lblMeja.setText(meja + "");
        lblTransaksiId.setText(tId);
        lblTotal.setText(String.format("%,d,00", total));
        lblKembali.setText("");
        
        //Isi Table
        try {
            conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/foodcourt",
                    "root", "");
            String sql = "SELECT a.IdMenu, b.Nama, b.Harga, a.Jumlah, (b.Harga * a.Jumlah) as SubTotal "
                    + "FROM tblrinciantransaksi a,tblmenu b "
                    + "WHERE a.IdMenu = b.IdMenu AND a.IdTransaksi = '"+tId+"'";
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
            Logger.getLogger(FrmRincian.class.getName()).log(Level.SEVERE, null, ex);
        }  
        setCellFormat(2);
    }
    
    private void playSound() {
        File url = new File("cash.wav");
        Clip clip;
        try {
            clip = AudioSystem.getClip();
            AudioInputStream ais = AudioSystem.
            getAudioInputStream(url);
        clip.open(ais);
        clip.loop(0);
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            }
        });
        } catch (LineUnavailableException ex) {
            Logger.getLogger(FrmLoginPelayan.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(FrmLoginPelayan.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FrmLoginPelayan.class.getName()).log(Level.SEVERE, null, ex);
        }
        // getAudioInputStream() also accepts a File or InputStream
        
    }
    
    private void printReceipt() {
        Date skrg = new Date();
        SimpleDateFormat kal = new SimpleDateFormat("dd/MM/yyyy");
        String tgl = kal.format(skrg);
        int size = tblRincianTransaksi.getRowCount();
        String[][] arr = new String[size][3]; //3 for Columns
        for (int i = 0; i < arr.length;i++) {
            arr[i][0] = tblRincianTransaksi.getValueAt(i, 1).toString(); // Fetching Name
            arr[i][1] = tblRincianTransaksi.getValueAt(i, 3).toString(); // Fetching Qty
            arr[i][2] = tblRincianTransaksi.getValueAt(i, 4).toString(); // Fetching SubTotal
        } 
        
        
        ArrayList<PrintableLine> arrPl = new ArrayList<>();
        
        //Receipt Header
        String lineH1 = "******************************************";
        String lineH2 = String.format("%5s %-38s", "", "Food Court STTS");
        String lineH3 = String.format("%5s %-38s", "", "Jl. Ngagel Jaya Tengah 73-77");
        String lineH4 = String.format("%5s %-38s", "", "Surabaya");
        String lineH4A = "******************************************";
        String lineH4B = String.format("%-16s %-8s %2d %8s", tgl, "Meja No.", meja, tId);
        String lineH4C = "******************************************";
        String lineH5 = String.format("%5s %-25s %10s", "Qty", "Nama Menu", "Sub Total");
        String lineH6 = String.format("%5s %-25s %10s", "*****", "*************************", "**********");
        
        //Recepit Contents
        String[] lines = new String[size]; //For PrintableLine
        for (int i = 0; i < arr.length;i++) {
            lines[i] = String.format("%5s %-25s %10s", arr[i][1], arr[i][0], arr[i][2]);
        } 
        String lineLimit = "******************************************";
        String lineTotal = String.format("%30s %10d", "Total", total);
        String lineBayar = String.format("%30s %10s", "Bayar", tflBayar.getText());
        String lineKembali = String.format("%30s %10s", "Kembali", lblKembali.getText());
        String lineFooterPadding = "\n";
        String footer1 = "******************************************";
        String footer2 = String.format("%5s %-38s", "", "Terima kasih dan sampai jumpa");
        String footer3 = "******************************************";
        arrPl.add(new PrintableLine(lineH1));
        arrPl.add(new PrintableLine(lineH2));
        arrPl.add(new PrintableLine(lineH3));
        arrPl.add(new PrintableLine(lineH4));
        arrPl.add(new PrintableLine(lineH4A));
        arrPl.add(new PrintableLine(lineH4B));
        arrPl.add(new PrintableLine(lineH4C));
        arrPl.add(new PrintableLine(lineH5));
        arrPl.add(new PrintableLine(lineH6));
        for (String line : lines) {
            arrPl.add(new PrintableLine(line));
        }
        arrPl.add(new PrintableLine(lineLimit));
        arrPl.add(new PrintableLine(lineTotal));
        arrPl.add(new PrintableLine(lineBayar));
        arrPl.add(new PrintableLine(lineKembali));
        arrPl.add(new PrintableLine(lineFooterPadding));
        arrPl.add(new PrintableLine(footer1));
        arrPl.add(new PrintableLine(footer2));
        arrPl.add(new PrintableLine(footer3));
        PrintReceipt pr = new PrintReceipt(arrPl);
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
        jLabel13 = new javax.swing.JLabel();
        tflBayar = new javax.swing.JTextField();
        lblKembali = new javax.swing.JLabel();
        btnBayar = new javax.swing.JButton();
        lblTotal = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        lblTransaksiId = new javax.swing.JLabel();
        lblMeja = new javax.swing.JLabel();
        lblTransaksiId1 = new javax.swing.JLabel();
        lblMeja1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

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
        jLabel12.setText("Total Beli (Rp):");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel13.setText("Bayar (Rp):");

        tflBayar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        lblKembali.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblKembali.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblKembali.setText("[Kembalian]");
        lblKembali.setToolTipText("");

        btnBayar.setBackground(new java.awt.Color(255, 255, 255));
        btnBayar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnBayar.setText("Bayar");
        btnBayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBayarActionPerformed(evt);
            }
        });

        lblTotal.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblTotal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTotal.setText("[Total]");

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel14.setText("Kembali (Rp):");

        lblTransaksiId.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblTransaksiId.setText("[TRANSAKSI ID]");

        lblMeja.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblMeja.setText("[Meja]");

        lblTransaksiId1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblTransaksiId1.setText("Transaksi ID:");

        lblMeja1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblMeja1.setText("Meja:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(231, 231, 231)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(lblTransaksiId1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblTransaksiId)
                        .addGap(22, 22, 22)
                        .addComponent(lblMeja1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblMeja)))
                .addContainerGap(213, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(52, 52, 52))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(45, 45, 45))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(btnBayar, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addGap(44, 44, 44)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tflBayar, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblKembali, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(44, 44, 44))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 660, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTransaksiId1)
                    .addComponent(lblMeja1)
                    .addComponent(lblTransaksiId)
                    .addComponent(lblMeja))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(lblTotal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(tflBayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnBayar, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(lblKembali))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnBayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBayarActionPerformed
        // TODO add your handling code here:
        if (!terbayar) {
            
            int bayar = Integer.parseInt(tflBayar.getText());
            if (bayar < total) {
                JOptionPane.showMessageDialog(this, "Uang yang dibayar kurang");
                return;
            }
            lblKembali.setText(String.format("%,d,00", (bayar-total)));
            playSound();

            String sql = "UPDATE tbltransaksi SET Status = 'terbayar', IdKasir = '"+pId
                    + "' WHERE IdTransaksi = '"+tId+"'";
            try {
                st = conn.createStatement();
                st.executeUpdate(sql);
                // Bikin Thread baru sehingga lblKembali.setText tidak terdelay
                Thread t = new Thread(){
                public void run(){
                        printReceipt();
                }
                };
                t.start();
                terbayar = true;
            } catch (SQLException ex) {
                Logger.getLogger(FrmRincian.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Tagihan sudah dibayar");
        }
        
    }//GEN-LAST:event_btnBayarActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        FrmKasir fr = new FrmKasir(pId);
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
            java.util.logging.Logger.getLogger(FrmRincian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmRincian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmRincian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmRincian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new FrmRincian().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBayar;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblKembali;
    private javax.swing.JLabel lblMeja;
    private javax.swing.JLabel lblMeja1;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JLabel lblTransaksiId;
    private javax.swing.JLabel lblTransaksiId1;
    private javax.swing.JTable tblRincianTransaksi;
    private javax.swing.JTextField tflBayar;
    // End of variables declaration//GEN-END:variables
}
