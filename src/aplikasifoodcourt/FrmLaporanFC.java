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
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author Syabith
 */
public class FrmLaporanFC extends javax.swing.JFrame {

    /**
     * Creates new form FrmLaporanFC
     */
        
    public void lebarKolom(){
        TableColumn column;
        tblLaporan.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        column = tblLaporan.getColumnModel().getColumn(0);
        column.setPreferredWidth(150);
        column = tblLaporan.getColumnModel().getColumn(1);
        column.setPreferredWidth(130);     
        column = tblLaporan.getColumnModel().getColumn(2);
        column.setPreferredWidth(110);
        column = tblLaporan.getColumnModel().getColumn(3);
        column.setPreferredWidth(120);        
    }
    
    public void lebarKolom2(){
        TableColumn column;
        tblLaporan.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        column = tblLaporan.getColumnModel().getColumn(0);
        column.setPreferredWidth(100);
        column = tblLaporan.getColumnModel().getColumn(1);
        column.setPreferredWidth(180);     
        column = tblLaporan.getColumnModel().getColumn(2);
        column.setPreferredWidth(50);
        column = tblLaporan.getColumnModel().getColumn(3);
        column.setPreferredWidth(180);        
    }

    Connection conn;
    PreparedStatement ps;
    int ctr;
    Statement st;
    ResultSet rs;
    ArrayList<String[]> arr; // Untuk ComboBox
    private DefaultTableModel model;
    private DefaultComboBoxModel<String> cbbm;
    ArrayList<Object[]> arrLC; // Untuk LineChart
    
    
    public FrmLaporanFC() {
        initComponents();
        
        arr = new ArrayList<>();
        model = new DefaultTableModel();
        cbbm = new DefaultComboBoxModel<>();
        cbbm.addElement("Semua");
        arr.add(new String[] {"Semua",""});
        tblLaporan.setModel(model);
        tblLaporan.setDefaultEditor(Object.class, null);
        model.addColumn("Nama Vendor");
        model.addColumn("Total Penjualan");
        model.addColumn("Vendor (90%)");
        model.addColumn("Food Court(10%)");
        //lebarKolom();
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
            cbbVendor.setModel(cbbm);
            cbbVendor.setSelectedIndex(0); // "Semua" sebagai Default
        } catch (SQLException ex) {
            Logger.getLogger(FrmUser.class.getName()).log(Level.SEVERE, null, ex);
        } 
        xdpDari.setFormats("dd-MM-yyyy");
        xdpDari.setDate(new Date());
        xdpSampai.setFormats("dd-MM-yyyy");
        xdpSampai.setDate(new Date());
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
        btnHari = new javax.swing.JButton();
        btnMinggu = new javax.swing.JButton();
        xdpDari = new org.jdesktop.swingx.JXDatePicker();
        xdpSampai = new org.jdesktop.swingx.JXDatePicker();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btnTampilkan = new javax.swing.JButton();
        cbbVendor = new javax.swing.JComboBox<>();
        btnBulan = new javax.swing.JButton();
        btnCsv = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanelTable = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblLaporan = new javax.swing.JTable();
        jPanelDiagram = new javax.swing.JPanel();
        pnlChart = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Algerian", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Laporan Manager Food Court");

        btnHari.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnHari.setText("Total Hari ini");
        btnHari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHariActionPerformed(evt);
            }
        });

        btnMinggu.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnMinggu.setText("Total Minggu Ini");
        btnMinggu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMingguActionPerformed(evt);
            }
        });

        xdpDari.setBackground(new java.awt.Color(255, 255, 0));
        xdpDari.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        xdpSampai.setBackground(new java.awt.Color(255, 255, 0));
        xdpSampai.setFocusable(false);
        xdpSampai.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
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

        cbbVendor.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        cbbVendor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "C001", "C002", "C003", "C004" }));

        btnBulan.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnBulan.setText("Total Bulan Ini");
        btnBulan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBulanActionPerformed(evt);
            }
        });

        btnCsv.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnCsv.setText("Import CSV");
        btnCsv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCsvActionPerformed(evt);
            }
        });

        tblLaporan.setAutoCreateRowSorter(true);
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

        javax.swing.GroupLayout jPanelTableLayout = new javax.swing.GroupLayout(jPanelTable);
        jPanelTable.setLayout(jPanelTableLayout);
        jPanelTableLayout.setHorizontalGroup(
            jPanelTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTableLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 517, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanelTableLayout.setVerticalGroup(
            jPanelTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTableLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 330, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Tabel Laporan Manager Food Court", jPanelTable);

        javax.swing.GroupLayout pnlChartLayout = new javax.swing.GroupLayout(pnlChart);
        pnlChart.setLayout(pnlChartLayout);
        pnlChartLayout.setHorizontalGroup(
            pnlChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 517, Short.MAX_VALUE)
        );
        pnlChartLayout.setVerticalGroup(
            pnlChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 330, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanelDiagramLayout = new javax.swing.GroupLayout(jPanelDiagram);
        jPanelDiagram.setLayout(jPanelDiagramLayout);
        jPanelDiagramLayout.setHorizontalGroup(
            jPanelDiagramLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDiagramLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlChart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanelDiagramLayout.setVerticalGroup(
            jPanelDiagramLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDiagramLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlChart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Diagram Laporan Manager Food Court", jPanelDiagram);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(cbbVendor, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(xdpDari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(xdpSampai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnTampilkan))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnMinggu, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
                            .addComponent(btnBulan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnHari, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 542, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(xdpDari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(xdpSampai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(btnTampilkan)
                    .addComponent(cbbVendor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnHari)
                        .addGap(18, 18, 18)
                        .addComponent(btnMinggu)
                        .addGap(18, 18, 18)
                        .addComponent(btnBulan))
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCsv)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnHariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHariActionPerformed
        // TODO add your handling code here:
        
        model = new DefaultTableModel();
        tblLaporan.setModel(model);
        model.addColumn("Nama Vendor");
        model.addColumn("Total Penjualan");
        model.addColumn("Vendor (90%)");
        model.addColumn("Food Court(10%)");
        try { 
            model.setRowCount(0);
            String sql = "SELECT Nama, Total, (Total * 0.9) as Vendor, (Total * 0.1) as FoodCourt "
                    + "FROM (SELECT a.Nama as Nama, SUM(b.Harga * c.Jumlah) as Total "
                    + "FROM tblvendor a, tblmenu b, tblrinciantransaksi c, tbltransaksi d "
                    + "WHERE a.IdVendor = b.IdVendor AND c.IdMenu = b.IdMenu AND c.IdTransaksi = d.IdTransaksi "
                    + "AND d.WaktuTransaksi > CURDATE() "
                    + "GROUP BY a.Nama) As subQuery GROUP BY Nama";
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
            genChart("Total Hari ini");
        } catch (SQLException ex) {
            Logger.getLogger(FrmLaporanFC.class.getName()).log(Level.SEVERE, null, ex);
        }
        setCellFormat(1);
        setCellFormat(2);
        setCellFormat(3);
        lebarKolom();
    }//GEN-LAST:event_btnHariActionPerformed

    private void btnMingguActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMingguActionPerformed
        // TODO add your handling code here:
        model = new DefaultTableModel();
        tblLaporan.setModel(model);
        model.addColumn("Nama Vendor");
        model.addColumn("Total Penjualan");
        model.addColumn("Vendor (90%)");
        model.addColumn("Food Court(10%)");
        try { 
            model.setRowCount(0);
            String sql = "SELECT Nama, Total, (Total * 0.9) as Vendor, (Total * 0.1) as FoodCourt "
                    + "FROM (SELECT a.Nama as Nama, SUM(b.Harga * c.Jumlah) as Total "
                    + "FROM tblvendor a, tblmenu b, tblrinciantransaksi c, tbltransaksi d "
                    + "WHERE a.IdVendor = b.IdVendor AND c.IdMenu = b.IdMenu AND c.IdTransaksi = d.IdTransaksi "
                    + "AND WEEKOFYEAR(WaktuTransaksi)=WEEKOFYEAR(NOW()) "
                    + "GROUP BY a.Nama) As subQuery GROUP BY Nama";
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
            genChart("Total Minggu ini");
        } catch (SQLException ex) {
            Logger.getLogger(FrmLaporanFC.class.getName()).log(Level.SEVERE, null, ex);
        }
        setCellFormat(1);
        setCellFormat(2);
        setCellFormat(3);
        lebarKolom();
    }//GEN-LAST:event_btnMingguActionPerformed

    private void btnBulanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBulanActionPerformed
        // TODO add your handling code here:
        model = new DefaultTableModel();
        tblLaporan.setModel(model);
        model.addColumn("Nama Vendor");
        model.addColumn("Total Penjualan");
        model.addColumn("Vendor (90%)");
        model.addColumn("Food Court(10%)");
        // lebar kolom dan format cell
        try { 
            model.setRowCount(0);
            String sql = "SELECT Nama, Total, (Total * 0.9) as Vendor, (Total * 0.1) as FoodCourt "
                    + "FROM (SELECT a.Nama as Nama, SUM(b.Harga * c.Jumlah) as Total "
                    + "FROM tblvendor a, tblmenu b, tblrinciantransaksi c, tbltransaksi d "
                    + "WHERE a.IdVendor = b.IdVendor AND c.IdMenu = b.IdMenu AND c.IdTransaksi = d.IdTransaksi "
                    + "AND YEAR(d.WaktuTransaksi) = YEAR(NOW()) AND MONTH(d.WaktuTransaksi)=MONTH(NOW()) "
                    + "GROUP BY a.Nama) As subQuery GROUP BY Nama";
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
            genChart("Total Bulan ini");
        } catch (SQLException ex) {
            Logger.getLogger(FrmLaporanFC.class.getName()).log(Level.SEVERE, null, ex);
        }
        setCellFormat(1);
        setCellFormat(2);
        setCellFormat(3);
        lebarKolom();
    }//GEN-LAST:event_btnBulanActionPerformed

    private void btnTampilkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTampilkanActionPerformed
        // TODO add your handling code here:
        if (cbbVendor.getSelectedIndex() < 0) {
            JOptionPane.showMessageDialog(this, "Pilih Vendor");
        } else {
            model = new DefaultTableModel();
            tblLaporan.setModel(model);
            model.addColumn("Id Transaksi");
            model.addColumn("Waktu Transaksi");
            model.addColumn("Jumlah");
            model.addColumn("SubTotal");
            
            

            String vId = arr.get(cbbVendor.getSelectedIndex())[0];
            Date dtDari = xdpDari.getDate();
            SimpleDateFormat dar = new SimpleDateFormat("yyyy-MM-dd");
            String dari = dar.format(dtDari) + " 00:00:00";
            Date dtSampai = xdpSampai.getDate();
            SimpleDateFormat sam = new SimpleDateFormat("yyyy-MM-dd");
            String sampai = sam.format(dtSampai) + " 23:59:59";
            
            if(vId.equals("Semua")) {
                try { 
                    model.setRowCount(0);
                    String sql = "SELECT b.IdTransaksi, a.WaktuTransaksi, SUM(b.Jumlah) as Jumlah, SUM(b.Jumlah * c.Harga) as SubTotal "
                            + "FROM tbltransaksi a, tblrinciantransaksi b, tblmenu c, tblvendor d "
                            + "WHERE a.IdTransaksi = b.IdTransaksi AND b.IdMenu = c.IdMenu AND c.IdVendor = d.IdVendor "
                            + "AND a.WaktuTransaksi BETWEEN ? AND ? "
                            + "GROUP BY b.IdTransaksi";
                    ps = conn.prepareStatement(sql);
                    ps.setString(1, dari);
                    ps.setString(2, sampai);
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
                    
                    // Untuk LineChart nya, dimasukkan ke Array Khusus
                    arrLC = new ArrayList<>();
                    sql = "SELECT DATE(WaktuTransaksi) as Date, SUM(total) as Total FROM tbltransaksi "
                            + "WHERE WaktuTransaksi BETWEEN ? AND ? "
                            + "GROUP BY YEAR(WaktuTransaksi), MONTH(WaktuTransaksi), DATE(WaktuTransaksi)";
                    ps = conn.prepareStatement(sql);
                    ps.setString(1, dari);
                    ps.setString(2, sampai);
                    rs = ps.executeQuery();
                    
                    while (rs.next()) {
                        arrLC.add(new Object[]{
                            new SimpleDateFormat("dd-MM-yyyy").format(rs.getTimestamp(1)),
                            rs.getInt(2)
                        });
                    }
                    
                    dari = dar.format(dtDari);
                    sampai = sam.format(dtSampai);
                    genLineChart(vId,dari, sampai);
                    
                } catch (SQLException ex) {
                    Logger.getLogger(FrmLaporanFC.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
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
                    
                    // Untuk LineChart nya, dimasukkan ke Array Khusus
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
                    String vendor = cbbVendor.getSelectedItem().toString();
                    dari = dar.format(dtDari);
                    sampai = sam.format(dtSampai);
                    genLineChart(vendor, dari, sampai);
                    
                } catch (SQLException ex) {
                    Logger.getLogger(FrmLaporanFC.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            
        }

        setCellFormat(3);
        lebarKolom2();
        
    }//GEN-LAST:event_btnTampilkanActionPerformed

    private void tblLaporanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblLaporanMouseClicked
        // TODO add your handling code here:
        int baris = tblLaporan.getSelectedRow();
        String tId = tblLaporan.getValueAt(baris,0).toString();
        String vId = arr.get(cbbVendor.getSelectedIndex())[0];
        int total = (int) tblLaporan.getValueAt(baris,3);
        if (vId.equals("Semua")) {
            FrmRincianManager fr = new FrmRincianManager(tId, total);
            fr.setVisible(true);
        } else {
            FrmRincianManager fr = new FrmRincianManager(tId, vId, total);
            fr.setVisible(true);
        }
        
    }//GEN-LAST:event_tblLaporanMouseClicked

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
            java.util.logging.Logger.getLogger(FrmLaporanFC.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmLaporanFC.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmLaporanFC.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmLaporanFC.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmLaporanFC().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBulan;
    private javax.swing.JButton btnCsv;
    private javax.swing.JButton btnHari;
    private javax.swing.JButton btnMinggu;
    private javax.swing.JButton btnTampilkan;
    private javax.swing.JComboBox<String> cbbVendor;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanelDiagram;
    private javax.swing.JPanel jPanelTable;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JPanel pnlChart;
    private javax.swing.JTable tblLaporan;
    private org.jdesktop.swingx.JXDatePicker xdpDari;
    private org.jdesktop.swingx.JXDatePicker xdpSampai;
    // End of variables declaration//GEN-END:variables
}
