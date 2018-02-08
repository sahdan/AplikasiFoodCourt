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
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author Syabith
 */
public class FrmLaporanVendor2 extends javax.swing.JFrame {

    /**
     * Creates new form FrmLaporanFC
     */
    public void lebarKolom(){
        TableColumn column;
        tblLaporan.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        column = tblLaporan.getColumnModel().getColumn(0);
        column.setPreferredWidth(138);
        column = tblLaporan.getColumnModel().getColumn(1);
        column.setPreferredWidth(60);     
        column = tblLaporan.getColumnModel().getColumn(2);
        column.setPreferredWidth(115);
        column = tblLaporan.getColumnModel().getColumn(3);
        column.setPreferredWidth(140);        
        
    }

    Connection conn;
    PreparedStatement ps;
    int ctr;
    Statement st;
    ResultSet rs;
    ArrayList<String[]> arr; //For ComboBox
    ArrayList<Object[]> arrLC; //For LineChart DataSet
    private DefaultTableModel model;
    DefaultTableModel dtm;
    String vId = UserID.getId();
    
    
    public FrmLaporanVendor2() {
        initComponents();
        
        arr = new ArrayList<>();
        model = new DefaultTableModel();
        tblLaporan.setModel(model);
        tblLaporan.setDefaultEditor(Object.class, null);
        model.addColumn("Nama Menu");
        model.addColumn("Jumlah");
        model.addColumn("Harga");
        model.addColumn("Total");
        try {
            conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/foodcourt",
                    "root", "");
        } catch (SQLException ex) {
            Logger.getLogger(FrmUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        // For Label
        try {
            
            // To get the name of Vendor
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
        lebarKolom();
    }
    
    private void genChart(String judul) {
        DefaultPieDataset objDataset = new DefaultPieDataset();
        //Looping through tblLaporan. Loop ini sudah memperhitungkan baris total diakhir tabel
        for (int i = 0; i < tblLaporan.getRowCount()-1; i++) {
            String menu = tblLaporan.getValueAt(i,0).toString();
            int jumlah = (int) tblLaporan.getValueAt(i,1);
            objDataset.setValue(menu, jumlah);
        }
        
        
        JFreeChart objChart = ChartFactory.createPieChart (
        judul,   //Chart title
        objDataset,          //Chart Data 
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
    
    private void genLineChart(String dari, String sampai) {
        DefaultCategoryDataset objDataset = new DefaultCategoryDataset();
        //Looping through tblLaporan. Loop ini sudah memperhitungkan baris total diakhir tabel
        for (int i = 0; i < arrLC.size(); i++) {
            String date = arrLC.get(i)[0].toString();
            String menu = arrLC.get(i)[1].toString();
            int jumlah = (int) arrLC.get(i)[2];
            System.out.println(date + " " + jumlah);
            objDataset.addValue(jumlah, menu, date);
        }
        
        JFreeChart objChart = ChartFactory.createLineChart (
        dari + " - " + sampai,   //Chart title
        "Tanggal",              // domain axis label
        "Jumlah",           //range axis label
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
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        btnMenuBulan = new javax.swing.JButton();
        btnMenuMinggu = new javax.swing.JButton();
        btnMenuHari = new javax.swing.JButton();
        btnCsv = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblLaporan = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        pnlChart = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

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

        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Jumlah Penjualan Tiap Menu");

        btnMenuBulan.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnMenuBulan.setText("Bulan Ini");
        btnMenuBulan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMenuBulanActionPerformed(evt);
            }
        });

        btnMenuMinggu.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnMenuMinggu.setText("Minggu Ini");
        btnMenuMinggu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMenuMingguActionPerformed(evt);
            }
        });

        btnMenuHari.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnMenuHari.setText("Hari ini");
        btnMenuHari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMenuHariActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnMenuHari, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnMenuMinggu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnMenuBulan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnMenuHari)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnMenuMinggu)
                .addGap(7, 7, 7)
                .addComponent(btnMenuBulan)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnCsv.setText("Import CSV");
        btnCsv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCsvActionPerformed(evt);
            }
        });

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
        jScrollPane1.setViewportView(tblLaporan);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 460, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Tabel Laporan Manager Vendor", jPanel3);

        javax.swing.GroupLayout pnlChartLayout = new javax.swing.GroupLayout(pnlChart);
        pnlChart.setLayout(pnlChartLayout);
        pnlChartLayout.setHorizontalGroup(
            pnlChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 460, Short.MAX_VALUE)
        );
        pnlChartLayout.setVerticalGroup(
            pnlChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 325, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlChart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlChart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Diagram Laporan Manager Vendor", jPanel4);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblNamaVendor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(xdpDari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(xdpSampai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnTampilkan))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnCsv))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 485, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(xdpDari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(xdpSampai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(btnTampilkan)
                    .addComponent(lblNamaVendor))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCsv)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnTampilkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTampilkanActionPerformed
        // TODO add your handling code here:
        model = new DefaultTableModel();
        tblLaporan.setModel(model);
        model.addColumn("Nama Menu");
        model.addColumn("Jumlah");
        model.addColumn("Harga");
        model.addColumn("Total");

        Date dtDari = xdpDari.getDate();
        SimpleDateFormat dar = new SimpleDateFormat("yyyy-MM-dd");
        String dari = dar.format(dtDari) + " 00:00:00";
        Date dtSampai = xdpSampai.getDate();
        SimpleDateFormat sam = new SimpleDateFormat("yyyy-MM-dd");
        String sampai = sam.format(dtSampai) + " 23:59:59";

        try { 
            model.setRowCount(0);
            String sql = "SELECT Nama, Jumlah, Harga, (Jumlah * Harga) as Total "
                    + "FROM (SELECT b.Nama as Nama, SUM(c.Jumlah) as Jumlah, b.Harga as Harga "
                    + "FROM tblvendor a, tblmenu b, tblrinciantransaksi c, tbltransaksi d "
                    + "WHERE a.IdVendor = b.IdVendor AND c.IdMenu = b.IdMenu AND c.IdTransaksi = d.IdTransaksi "
                    + "AND a.IdVendor = '"+vId+"' AND d.WaktuTransaksi BETWEEN ? AND ? GROUP BY b.Nama) as SubQuery GROUP BY Nama";
            ps = conn.prepareStatement(sql);
            ps.setString(1, dari);
            ps.setString(2, sampai);
            rs = ps.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString(1),
                    rs.getInt(2),
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
            
            // For Line Chart, Grabbing the data set
            arrLC = new ArrayList<>();
            sql = "SELECT tgl, Nama, Jumlah "
                    + "FROM (SELECT DATE(d.WaktuTransaksi) as tgl, b.Nama as Nama, SUM(c.Jumlah) as Jumlah, b.Harga as Harga "
                    + "FROM tblvendor a, tblmenu b, tblrinciantransaksi c, tbltransaksi d "
                    + "WHERE a.IdVendor = b.IdVendor AND c.IdMenu = b.IdMenu AND c.IdTransaksi = d.IdTransaksi "
                    + "AND a.IdVendor = '"+vId+"' AND d.WaktuTransaksi BETWEEN ? AND ? GROUP BY tgl, Nama) as SubQuery GROUP BY tgl, Nama";
            ps = conn.prepareStatement(sql);
            ps.setString(1, dari);
            ps.setString(2, sampai);
            rs = ps.executeQuery();
            while (rs.next()) {
                arrLC.add(new Object[]{
                            new SimpleDateFormat("dd-MM-yyyy").format(rs.getTimestamp(1)),
                            rs.getString(2),
                            rs.getInt(3)
                        });
            }
            rs.close();
            
            dari = dar.format(dtDari);
            sampai = sam.format(dtSampai);
            genLineChart(dari, sampai);
        } catch (SQLException ex) {
            Logger.getLogger(FrmLaporanFC.class.getName()).log(Level.SEVERE, null, ex);
        }
        setCellFormat(2);
        setCellFormat(3);
        lebarKolom();
    }//GEN-LAST:event_btnTampilkanActionPerformed

    private void btnMenuHariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenuHariActionPerformed
        // TODO add your handling code here:
        model = new DefaultTableModel();
        tblLaporan.setModel(model);
        model.addColumn("Nama Menu");
        model.addColumn("Jumlah");
        model.addColumn("Harga");
        model.addColumn("Total");
        try { 
            model.setRowCount(0);
            String sql = "SELECT Nama, Jumlah, Harga, (Jumlah * Harga) as Total "
                    + "FROM (SELECT b.Nama as Nama, SUM(c.Jumlah) as Jumlah, b.Harga as Harga "
                    + "FROM tblvendor a, tblmenu b, tblrinciantransaksi c, tbltransaksi d "
                    + "WHERE a.IdVendor = b.IdVendor AND c.IdMenu = b.IdMenu AND c.IdTransaksi = d.IdTransaksi "
                    + "AND a.IdVendor = '"+vId+"' AND d.WaktuTransaksi > CURDATE() GROUP BY b.Nama) as SubQuery GROUP BY Nama";
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString(1),
                    rs.getInt(2),
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
            genChart("Penjualan Hari Ini");
        } catch (SQLException ex) {
            Logger.getLogger(FrmLaporanFC.class.getName()).log(Level.SEVERE, null, ex);
        }
        setCellFormat(2);
        setCellFormat(3);
        lebarKolom();
    }//GEN-LAST:event_btnMenuHariActionPerformed

    private void btnMenuMingguActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenuMingguActionPerformed
        // TODO add your handling code here:
        model = new DefaultTableModel();
        tblLaporan.setModel(model);
        model.addColumn("Nama Menu");
        model.addColumn("Jumlah");
        model.addColumn("Harga");
        model.addColumn("Total");
        try { 
            model.setRowCount(0);
            String sql = "SELECT Nama, Jumlah, Harga, (Jumlah * Harga) as Total "
                    + "FROM (SELECT b.Nama as Nama, SUM(c.Jumlah) as Jumlah, b.Harga as Harga "
                    + "FROM tblvendor a, tblmenu b, tblrinciantransaksi c, tbltransaksi d "
                    + "WHERE a.IdVendor = b.IdVendor AND c.IdMenu = b.IdMenu AND c.IdTransaksi = d.IdTransaksi "
                    + "AND a.IdVendor = '"+vId+"' AND WEEKOFYEAR(WaktuTransaksi)=WEEKOFYEAR(NOW()) GROUP BY b.Nama) as SubQuery GROUP BY Nama";
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString(1),
                    rs.getInt(2),
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
            genChart("Penjualan Minggu Ini");
        } catch (SQLException ex) {
            Logger.getLogger(FrmLaporanFC.class.getName()).log(Level.SEVERE, null, ex);
        }
        setCellFormat(2);
        setCellFormat(3);
        lebarKolom();
    }//GEN-LAST:event_btnMenuMingguActionPerformed

    private void btnMenuBulanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenuBulanActionPerformed
        // TODO add your handling code here:
        model = new DefaultTableModel();
        tblLaporan.setModel(model);
        model.addColumn("Nama Menu");
        model.addColumn("Jumlah");
        model.addColumn("Harga");
        model.addColumn("Total");
        try { 
            model.setRowCount(0);
            String sql = "SELECT Nama, Jumlah, Harga, (Jumlah * Harga) as Total "
                    + "FROM (SELECT b.Nama as Nama, SUM(c.Jumlah) as Jumlah, b.Harga as Harga "
                    + "FROM tblvendor a, tblmenu b, tblrinciantransaksi c, tbltransaksi d "
                    + "WHERE a.IdVendor = b.IdVendor AND c.IdMenu = b.IdMenu AND c.IdTransaksi = d.IdTransaksi "
                    + "AND a.IdVendor = '"+vId+"' AND YEAR(d.WaktuTransaksi) = YEAR(NOW()) AND MONTH(d.WaktuTransaksi)=MONTH(NOW()) GROUP BY b.Nama) as SubQuery GROUP BY Nama";
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString(1),
                    rs.getInt(2),
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
            genChart("Penjualan Bulan Ini");
        } catch (SQLException ex) {
            Logger.getLogger(FrmLaporanFC.class.getName()).log(Level.SEVERE, null, ex);
        }
        setCellFormat(2);
        setCellFormat(3);
        lebarKolom();
    }//GEN-LAST:event_btnMenuBulanActionPerformed

    private void btnCsvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCsvActionPerformed
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
            java.util.logging.Logger.getLogger(FrmLaporanVendor2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmLaporanVendor2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmLaporanVendor2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmLaporanVendor2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new FrmLaporanVendor2().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCsv;
    private javax.swing.JButton btnMenuBulan;
    private javax.swing.JButton btnMenuHari;
    private javax.swing.JButton btnMenuMinggu;
    private javax.swing.JButton btnTampilkan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblNamaVendor;
    private javax.swing.JPanel pnlChart;
    private javax.swing.JTable tblLaporan;
    private org.jdesktop.swingx.JXDatePicker xdpDari;
    private org.jdesktop.swingx.JXDatePicker xdpSampai;
    // End of variables declaration//GEN-END:variables
}
