/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplikasifoodcourt;

import java.awt.BorderLayout;
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
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author Syabith
 */
public class FrmLaporanVendor extends javax.swing.JFrame {

    /**
     * Creates new form FrmLaporanFC
     */
    public void lebarKolom(){
        TableColumn column;
        tblLaporan.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        column = tblLaporan.getColumnModel().getColumn(0);
        column.setPreferredWidth(80);
        column = tblLaporan.getColumnModel().getColumn(1);
        column.setPreferredWidth(150);     
        column = tblLaporan.getColumnModel().getColumn(2);
        column.setPreferredWidth(70);
        column = tblLaporan.getColumnModel().getColumn(3);
        column.setPreferredWidth(135);        
        
    }

    Connection conn;
    PreparedStatement ps;
    int ctr;
    Statement st;
    ResultSet rs;
    ArrayList<String[]> arr; // Untuk ComboBox
    ArrayList<Object[]> arrLC; // Untuk LineChart
    private DefaultTableModel model;
    DefaultTableModel dtm;
    String vId = UserID.getId();
    public FrmLaporanVendor() {
        initComponents();
        
        arr = new ArrayList<>();
        model = new DefaultTableModel();
        tblLaporan.setModel(model);
        tblLaporan.setDefaultEditor(Object.class, null);
        model.addColumn("Id Transaksi");
        model.addColumn("Waktu Transaksi");
        model.addColumn("Jumlah");
        model.addColumn("SubTotal");
        try {
            conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/foodcourt",
                    "root", "");
        } catch (SQLException ex) {
            Logger.getLogger(FrmUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        // For Label
        try {
            
            // To get the list of Vendor
            String sql = "select Nama from tblvendor where IdVendor = '" + vId + "'";
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                lblNamaVendor.setText(rs.getString(1));
                //lebarKolom();
            }
        } catch (SQLException ex) {
            Logger.getLogger(FrmUser.class.getName()).log(Level.SEVERE, null, ex);
        } 
        xdpDari.setFormats("dd-MM-yyyy");
        xdpDari.setDate(new Date());
        xdpSampai.setFormats("dd-MM-yyyy");
        xdpSampai.setDate(new Date());
        
    }
    
    private void genLineChart(String vendor, String dari, String sampai) {
        DefaultCategoryDataset objDataset = new DefaultCategoryDataset();
        //Looping through tblLaporan. Loop ini sudah memperhitungkan baris total diakhir tabel
        for (int i = 0; i < arrLC.size(); i++) {
            String date = arrLC.get(i)[0].toString();
            int jumlah = (int) arrLC.get(i)[1];
            objDataset.addValue(jumlah, vendor, date);
        }
        
        JFreeChart objChart = ChartFactory.createLineChart (
        dari + " - " + sampai,   //Chart title
        "Tanggal",              // domain axis label
        "Total",           //range axis label
        objDataset,          //Chart Data
        PlotOrientation.VERTICAL,  // orientation
        true,               // include legend?
        true,               // include tooltips?
        false               // include URLs?
        );
        
        pnlChart.removeAll();
        pnlChart.setLayout(new java.awt.BorderLayout());
        ChartPanel CP = new ChartPanel(objChart);
        pnlChart.add(CP,BorderLayout.CENTER);
        pnlChart.validate();
        
//        ChartFrame frame = new ChartFrame("Demo", objChart);
//        frame.pack();
//        frame.setVisible(true);
    }
            private void setCellFormat(int vColIndex) {
        // Install the custom renderer on the first visible column
    TableColumn col = tblLaporan.getColumnModel().getColumn(vColIndex);
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
        xdpDari = new org.jdesktop.swingx.JXDatePicker();
        xdpSampai = new org.jdesktop.swingx.JXDatePicker();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btnTampilkan = new javax.swing.JButton();
        lblNamaVendor = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btnPendapatanHari = new javax.swing.JButton();
        btnPendapatanMinggu = new javax.swing.JButton();
        btnPendapatanBulan = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        lblBruto = new javax.swing.JLabel();
        JLabel7 = new javax.swing.JLabel();
        JLabel8 = new javax.swing.JLabel();
        lblNetto = new javax.swing.JLabel();
        btnCsv = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblLaporan = new javax.swing.JTable();
        pnlChart = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(729, 511));

        jLabel1.setFont(new java.awt.Font("Algerian", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Laporan Manager Vendor");

        xdpDari.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        xdpSampai.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Dari");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("Sampai");

        btnTampilkan.setBackground(new java.awt.Color(255, 255, 255));
        btnTampilkan.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnTampilkan.setText("Tampilkan Laporan");
        btnTampilkan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTampilkanActionPerformed(evt);
            }
        });

        lblNamaVendor.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblNamaVendor.setText("[Nama Vendor]");

        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        btnPendapatanHari.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnPendapatanHari.setText("Hari ini");
        btnPendapatanHari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPendapatanHariActionPerformed(evt);
            }
        });

        btnPendapatanMinggu.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnPendapatanMinggu.setText("Minggu Ini");
        btnPendapatanMinggu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPendapatanMingguActionPerformed(evt);
            }
        });

        btnPendapatanBulan.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnPendapatanBulan.setText("Bulan Ini");
        btnPendapatanBulan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPendapatanBulanActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Total Pendapatan");

        lblBruto.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblBruto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblBruto.setText("[BRUTO]");

        JLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        JLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        JLabel7.setText("Bruto:");

        JLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        JLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        JLabel8.setText("Netto:");

        lblNetto.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblNetto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNetto.setText("[NETTO]");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                    .addComponent(btnPendapatanHari, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnPendapatanMinggu, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnPendapatanBulan, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(JLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblBruto))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(JLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblNetto)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPendapatanHari)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPendapatanMinggu)
                .addGap(7, 7, 7)
                .addComponent(btnPendapatanBulan)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblBruto)
                    .addComponent(JLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNetto)
                    .addComponent(JLabel8))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnCsv.setText("Import CSV");
        btnCsv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCsvActionPerformed(evt);
            }
        });

        jTabbedPane1.setPreferredSize(new java.awt.Dimension(485, 375));

        tblLaporan.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        tblLaporan.setModel(new javax.swing.table.DefaultTableModel(
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
        tblLaporan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblLaporanMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblLaporan);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 333, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Table Laporan Manager Vendor", jPanel2);

        javax.swing.GroupLayout pnlChartLayout = new javax.swing.GroupLayout(pnlChart);
        pnlChart.setLayout(pnlChartLayout);
        pnlChartLayout.setHorizontalGroup(
            pnlChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 508, Short.MAX_VALUE)
        );
        pnlChartLayout.setVerticalGroup(
            pnlChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 344, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Diagram Laporan Manager Vendor", pnlChart);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblNamaVendor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(12, 12, 12)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(xdpDari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(14, 14, 14)
                        .addComponent(xdpSampai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnTampilkan))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 513, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnCsv)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(xdpDari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(xdpSampai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(btnTampilkan)
                    .addComponent(lblNamaVendor))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 372, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCsv)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnPendapatanHariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPendapatanHariActionPerformed
        // TODO add your handling code here:
        try { 
            model.setRowCount(0);
            String sql = "SELECT Total, (Total * 0.9) as Vendor "
                    + "FROM (SELECT SUM(b.Harga * c.Jumlah) as Total "
                    + "FROM tblvendor a, tblmenu b, tblrinciantransaksi c, tbltransaksi d WHERE a.IdVendor = b.IdVendor AND c.IdMenu = b.IdMenu AND c.IdTransaksi = d.IdTransaksi "
                    + "AND a.IdVendor = 'V001' AND d.WaktuTransaksi > CURDATE()) As subQuery";
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                lblBruto.setText(String.format("%-3s %,d,00", "Rp.", rs.getInt(1)));
                lblNetto.setText(String.format("%-3s %,d,00", "Rp.", rs.getInt(2)));
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(FrmLaporanFC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnPendapatanHariActionPerformed

    private void btnPendapatanMingguActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPendapatanMingguActionPerformed
        // TODO add your handling code here:
        try { 
            model.setRowCount(0);
            String sql = "SELECT Total, (Total * 0.9) as Vendor "
                    + "FROM (SELECT SUM(b.Harga * c.Jumlah) as Total "
                    + "FROM tblvendor a, tblmenu b, tblrinciantransaksi c, tbltransaksi d WHERE a.IdVendor = b.IdVendor AND c.IdMenu = b.IdMenu AND c.IdTransaksi = d.IdTransaksi "
                    + "AND a.IdVendor = 'V001' AND WEEKOFYEAR(WaktuTransaksi)=WEEKOFYEAR(NOW())) As subQuery";
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                lblBruto.setText(String.format("%-3s %,d,00", "Rp.", rs.getInt(1)));
                lblNetto.setText(String.format("%-3s %,d,00", "Rp.", rs.getInt(2)));
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(FrmLaporanFC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnPendapatanMingguActionPerformed

    private void btnPendapatanBulanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPendapatanBulanActionPerformed
        // TODO add your handling code here:
        try { 
            model.setRowCount(0);
            String sql = "SELECT Total, (Total * 0.9) as Vendor "
                    + "FROM (SELECT SUM(b.Harga * c.Jumlah) as Total "
                    + "FROM tblvendor a, tblmenu b, tblrinciantransaksi c, tbltransaksi d WHERE a.IdVendor = b.IdVendor AND c.IdMenu = b.IdMenu AND c.IdTransaksi = d.IdTransaksi "
                    + "AND a.IdVendor = 'V001' AND YEAR(d.WaktuTransaksi) = YEAR(NOW()) AND MONTH(d.WaktuTransaksi)=MONTH(NOW())) As subQuery";
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                lblBruto.setText(String.format("%-3s %,d,00", "Rp.", rs.getInt(1)));
                lblNetto.setText(String.format("%-3s %,d,00", "Rp.", rs.getInt(2)));
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(FrmLaporanFC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnPendapatanBulanActionPerformed

    private void btnTampilkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTampilkanActionPerformed
        // TODO add your handling code here:
        model = new DefaultTableModel();
        tblLaporan.setModel(model);
        model.addColumn("Id");
        model.addColumn("Waktu Transaksi");
        model.addColumn("Jumlah");
        model.addColumn("SubTotal");

        Date dtDari = xdpDari.getDate();
        SimpleDateFormat dar = new SimpleDateFormat("yyyy-MM-dd");
        String dari = dar.format(dtDari) + " 00:00:00";
        Date dtSampai = xdpSampai.getDate();
        SimpleDateFormat sam = new SimpleDateFormat("yyyy-MM-dd");
        String sampai = sam.format(dtSampai) + " 23:59:59";

        try { 
            model.setRowCount(0);
            String sql = "SELECT b.IdTransaksi, a.WaktuTransaksi, SUM(b.Jumlah) as Jumlah, SUM(b.Jumlah * c.Harga) as SubTotal "
                    + "FROM tbltransaksi a, tblrinciantransaksi b, tblmenu c, tblvendor d "
                    + "WHERE a.IdTransaksi = b.IdTransaksi AND b.IdMenu = c.IdMenu AND c.IdVendor = d.IdVendor "
                    + "AND d.IdVendor = ? AND a.WaktuTransaksi BETWEEN ? AND ? "
                    + "GROUP BY b.IdTransaksi";
            ps = conn.prepareStatement(sql);
            ps.setString(1, vId);
            ps.setString(2, dari);
            ps.setString(3, sampai);
            rs = ps.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString(1),
                    new SimpleDateFormat("dd-MM-yyyy HH:mm").format(rs.getTimestamp(2)),
                    rs.getInt(3),
                    rs.getInt(4)});
            }
            tblLaporan.setModel(model);
            int total = 0;
            for (int i = 0; i < model.getRowCount();i++) {
                total += (int) tblLaporan.getValueAt(i, 3);
            }
            model.addRow(new Object[] {"","","Total", total});
            rs.close();
            
            // Untuk Line Chart
            arrLC = new ArrayList<>();
            sql = "SELECT DATE(a.WaktuTransaksi), SUM(b.Jumlah * c.Harga) as SubTotal "
                    + "FROM tbltransaksi a, tblrinciantransaksi b, tblmenu c, tblvendor d "
                    + "WHERE a.IdTransaksi = b.IdTransaksi AND b.IdMenu = c.IdMenu AND c.IdVendor = d.IdVendor "
                    + "AND d.IdVendor = ? AND a.WaktuTransaksi BETWEEN ? AND ? "
                    + "GROUP BY DATE(a.WaktuTransaksi)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, vId);
            ps.setString(2, dari);
            ps.setString(3, sampai);
            rs = ps.executeQuery();

            while (rs.next()) {
                arrLC.add(new Object[]{
                    new SimpleDateFormat("dd-MM-yyyy").format(rs.getTimestamp(1)),
                    rs.getInt(2)
                });
            }
            String vendor = lblNamaVendor.getText();
            dari = dar.format(dtDari);
            sampai = sam.format(dtSampai);
            genLineChart(vendor, dari, sampai);
        } catch (SQLException ex) {
            Logger.getLogger(FrmLaporanFC.class.getName()).log(Level.SEVERE, null, ex);
        }
        lebarKolom();
        setCellFormat(3);
    }//GEN-LAST:event_btnTampilkanActionPerformed

    private void btnCsvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCsvActionPerformed
        // TODO add your handling code here:
        // TODO add your handling code here:
        JFileChooser fc = new JFileChooser();
        FileFilter csv = new ExtensionFilter("CSV Files", ".csv");
        fc.addChoosableFileFilter(csv);
        fc.setFileFilter(csv);
        
        String fileName, dirName;
        if (fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            fileName = fc.getSelectedFile().getName();
            dirName = fc.getCurrentDirectory().toString();
            if(CsvFileWriter.writeCsvFile(tblLaporan, fileName, dirName))
                JOptionPane.showMessageDialog(this,"File berhasil disimpan di " + dirName);
        }
    }//GEN-LAST:event_btnCsvActionPerformed

    private void tblLaporanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblLaporanMouseClicked
        // TODO add your handling code here:
        int baris = tblLaporan.getSelectedRow();
        String tId = tblLaporan.getValueAt(baris,0).toString();
        int total = (int) tblLaporan.getValueAt(baris,3);
        FrmRincianManager fr = new FrmRincianManager(tId, vId, total);
        fr.setVisible(true);
    }//GEN-LAST:event_tblLaporanMouseClicked

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
            java.util.logging.Logger.getLogger(FrmLaporanVendor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmLaporanVendor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmLaporanVendor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmLaporanVendor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmLaporanVendor().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel JLabel7;
    private javax.swing.JLabel JLabel8;
    private javax.swing.JButton btnCsv;
    private javax.swing.JButton btnPendapatanBulan;
    private javax.swing.JButton btnPendapatanHari;
    private javax.swing.JButton btnPendapatanMinggu;
    private javax.swing.JButton btnTampilkan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblBruto;
    private javax.swing.JLabel lblNamaVendor;
    private javax.swing.JLabel lblNetto;
    private javax.swing.JPanel pnlChart;
    private javax.swing.JTable tblLaporan;
    private org.jdesktop.swingx.JXDatePicker xdpDari;
    private org.jdesktop.swingx.JXDatePicker xdpSampai;
    // End of variables declaration//GEN-END:variables
}
