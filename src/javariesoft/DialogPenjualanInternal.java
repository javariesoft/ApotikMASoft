/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javariesoft;

import com.eigher.db.loghistoryDao;
import com.eigher.model.loghistory;
import com.erv.db.BarangstokDao;
import com.erv.db.KontrolTanggalDao;
import com.erv.db.bankDao;
import com.erv.db.barangDao;
import com.erv.db.jurnalDao;
import com.erv.db.koneksi;
import com.erv.db.pelangganDao;
import com.erv.db.penjualanDao;
import com.erv.db.piutangDao;
import com.erv.db.piutangbayarDao;
import com.erv.db.returDao;
import com.erv.db.rincipenjualanDao;
import com.erv.db.rincireturDao;
import com.erv.db.salesDao;
import com.erv.db.settingDao;
import com.erv.db.stokDao;
import com.erv.exception.JavarieException;
import com.erv.function.JDBCAdapter;
import com.erv.function.Util;
import com.erv.fungsi.DecimalFormatRenderer;
import com.erv.model.Barangstok;
import com.erv.model.bank;
import com.erv.model.barang;
import com.erv.model.jurnal;
import com.erv.model.pelanggan;
import com.erv.model.penjualan;
import com.erv.model.pesan;
import com.erv.model.piutang;
import com.erv.model.piutangbayar;
import com.erv.model.retur;
import com.erv.model.rincipenjualan;
import com.erv.model.rinciretur;
import com.erv.model.sales;
import com.erv.model.stok;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import org.jdesktop.application.ResourceMap;
import simple.escp.SimpleEscp;
import simple.escp.Template;
import simple.escp.json.JsonTemplate;

/**
 *
 * @author TI-PNP
 */
public class DialogPenjualanInternal extends javax.swing.JInternalFrame {

    /**
     * Creates new form DialogPenjualanInternal
     */
    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    String nofak = "";
    String id[];
    double total = 0, ttotalbayar = 0, PPNItem = 0, DiskonItem = 0, diskont = 0, hrgjual = 0;
    double totalhutang = 0;
    double sisa = 0;
    int IDJurnal = 0;
    double tppn = 0;
    double hpp = 0;
    int IDjual = 0, IDretur = 0, nomor = 0;
    float tdp = 0;
    int sisaBarang = 0;
    Connection cm;
    //Statement statm;
    java.text.DateFormat d = new SimpleDateFormat("yyyy-MM-dd");
    DecimalFormat df = new DecimalFormat("###,###,###,###.0");
    DecimalFormat df0 = new DecimalFormat("0.0");
    penjualan p = null;
    retur r = null;
    penjualanDao dbpenjualan;
    barangDao dbbarang;
    bank bank;
    //bankDao dbbank;
    sales sls;
    salesDao dbsales;
    Statement stat = null;
    Statement stat1 = null;
    String pilPelanggan = "0";
    barang brg;
    String pil = "0";
    loghistory lh;
    loghistoryDao lhdao;
    com.erv.function.Util u = new com.erv.function.Util();
    String aksilog = "";
    List<pesan> pesan = new ArrayList<pesan>();
    DefaultTableModel model;
    int posisi = -1;
    public static DecimalFormatRenderer dcfr = new DecimalFormatRenderer();
    pelanggan plg = null;
    double cogs = 0;
    String[] expire;
    ResourceMap resourceMap1 = org.jdesktop.application.Application.getInstance(javariesoft.JavarieSoftApp.class).getContext().getResourceMap(DialogPenjualan.class);
    Connection c = null;

    public DialogPenjualanInternal() {
        initComponents();
        setting();
        kosongFaktur();
        kosongBarang();
        kosongHasil();
        kosongRetur();
        pilihanKertasPendek();
        jScrollPane2.setVisible(false);
        jScrollPane3.setVisible(false);
//        Connection c = null;
        try {
            c = koneksi.getKoneksiJ();
            dbpenjualan = new penjualanDao();
            r = new retur();
            dbbarang = new barangDao();
            setFaktur(c);
            cm = koneksi.getKoneksiM();
            stat = cm.createStatement();
            buatTabel();
            sls = new sales();
            dbsales = new salesDao();
            bank = new bank();
            //dbbank = new bankDao();
            lh = new loghistory();
            lhdao = new loghistoryDao();
            model = (DefaultTableModel) jTable1.getModel();
            jTabbedPane1.setEnabledAt(1, false);
            tgl.setDateFormat(d);
            tgllunas.setDateFormat(d);
            tglRetur.setDateFormat(d);
            this.setLocation(((int) dim.getWidth() - this.getWidth()) / 2, ((int) dim.getHeight() - this.getHeight()) / 2);
        } catch (ClassNotFoundException | SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.toString());
        }
    }

    public DialogPenjualanInternal(int id, double tot, int pil) {
        initComponents();
        setting();
        kosongFaktur();
        kosongBarang();
        kosongHasil();
        jScrollPane2.setVisible(false);
        jScrollPane3.setVisible(false);
//        Connection c = null;
        try {
            c = koneksi.getKoneksiJ();
            dbpenjualan = new penjualanDao();
            r = new retur();
            IDretur = returDao.getID(c);
            dbbarang = new barangDao();
            setFaktur(c);
            cm = koneksi.getKoneksiM();
            stat = cm.createStatement();
            buatTabel();
            totalhutang = tot;
            lh = new loghistory();
            lhdao = new loghistoryDao();
            model = (DefaultTableModel) jTable1.getModel();
            jTabbedPane1.setEnabledAt(0, false);
            tgl.setDateFormat(d);
            tgllunas.setDateFormat(d);
            tglRetur.setDateFormat(d);

            // TODO add your handling code here:
            p = dbpenjualan.getDetails(c, id);
            IDjual = p.getID();
            nofaktur.setText(p.getFAKTUR());
            Calendar cld = Calendar.getInstance();
            cld.setTime(d.parse(p.getTANGGAL()));
            tgl.setSelectedDate(cld);
            kodepelanggan.setText(p.getKODEPELANGGAN());
            kodepelanggan1.setText(p.getKODEPELANGGAN());
            pelanggan plg = new pelangganDao(c).getDetails(p.getKODEPELANGGAN());
            namapelanggan.setText(plg.getNAMA());
            namapelanggan1.setText(plg.getNAMA());
            //sales sls = new salesDao(c).getDetails(p.getKODEPELANGGAN());
            sls = salesDao.getDetails(c, p.getIDSALES());
            txtSales.setText(sls.getIDSALES());
            txtNamaSales.setText(sls.getNAMA());
            //txtKodeDO.setText();
            namapelanggan.setText(plg.getNAMA());
            namapelanggan1.setText(plg.getNAMA());
            statusbayar.setSelectedIndex(Integer.parseInt(p.getCASH()));
            cld.setTime(d.parse(p.getTGLLUNAS()));
            tgllunas.setSelectedDate(cld);
            diskon.setText("" + p.getDISKON());
            cboStatDiskon.setSelectedIndex(0);
            DiskonTambah.setText("" + p.getDISKON());
            CboTipeHarga.setSelectedItem(p.getJENISTRANS());
            hasilBayar.setText("" + p.getDP());
            if (p.getPPN() > 0) {
                cbPPN.setSelected(true);
            } else {
                cbPPN.setSelected(false);
            }
            cogs = 0;
            int x = JOptionPane.showConfirmDialog(this, "Tampilkan Data Barang ?", "", JOptionPane.YES_NO_OPTION);
            if (x == 0) {
                model = (DefaultTableModel) jTable1.getModel();
                ResultSet re = c.createStatement().executeQuery("select *, barang.namabarang from RINCIPENJUALAN, BARANG where RINCIPENJUALAN.KODEBARANG=BARANG.KODEBARANG and IDPENJUALAN=" + p.getID() + "");
//                hapusTabel();
//                buatTabel();
                while (re.next()) {
                    nomor++;
                    //brg = new barangDao().getDetails(re.getString(3));
                    Vector data = new Vector();
                    data.add(0, nomor);
                    data.add(1, re.getString("KODEBARANG"));
                    data.add(2, re.getString("NAMABARANG"));
                    data.add(3, re.getString("KODEBATCH"));
                    data.add(4, re.getString("EXPIRE"));
                    data.add(5, re.getString("JUMLAH"));
                    data.add(6, re.getString("SATUAN"));
                    data.add(7, re.getString("HARGA"));
                    data.add(8, re.getString("DISKON"));
                    data.add(9, re.getString("PPN"));
                    data.add(10, re.getString("TOTAL"));
                    data.add(11, re.getString("IDDO"));
                    data.add(12, re.getString("COGS"));
                    data.add(13, re.getString("JUMLAHKECIL"));
                    data.add(14, re.getString("DISKONP"));
                    data.add(15, re.getString("BONUS"));

                    model.addRow(data);
                }
                total = 0;
                hpp = 0;
                //hasilAkhir();
                reloadData(c);
                hasilBayar.setText("" + p.getDP());

                re.close();
            } else {
                kodebarang.requestFocus();
                jTabbedPane1.setSelectedIndex(1);
            }
            if (pil == 0) {
                jTabbedPane1.setSelectedIndex(0);
                jTabbedPane1.setEnabledAt(1, false);
                jTabbedPane1.setEnabledAt(0, true);
            } else {
                jTabbedPane1.setSelectedIndex(1);
                jTabbedPane1.setEnabledAt(0, false);
                jTabbedPane1.setEnabledAt(1, true);
            }
            this.setLocation(((int) dim.getWidth() - this.getWidth()) / 2, ((int) dim.getHeight() - this.getHeight()) / 2);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
//        finally {
//            if (c != null) {
//                try {
//                    c.close();
//                } catch (SQLException ex) {
//                    Logger.getLogger(DialogPenjualan.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelCool2 = new com.erv.function.PanelCool();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        nofaktur = new javax.swing.JTextField();
        tgl = new datechooser.beans.DateChooserCombo();
        jLabel1 = new javax.swing.JLabel();
        statusbayar = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        tgllunas = new datechooser.beans.DateChooserCombo();
        jLabel6 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        kodepelanggan = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        diskon = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        namapelanggan = new javax.swing.JTextField();
        ppn = new javax.swing.JTextField();
        cboStatDiskon = new javax.swing.JComboBox();
        cboStatPPN = new javax.swing.JComboBox();
        btnBaru = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        txtSales = new javax.swing.JTextField();
        txtNamaSales = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        LabelKodeDO = new javax.swing.JLabel();
        txtKodeDO = new javax.swing.JTextField();
        txtNamaDo = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable5 = new javax.swing.JTable();
        BtnGenNum = new javax.swing.JButton();
        CboNamaBank = new javax.swing.JComboBox();
        tbnKeluar = new javax.swing.JButton();
        btnNewPlgn = new javax.swing.JButton();
        CboTipeHarga = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        cbPPN = new javax.swing.JCheckBox();
        jLabel10 = new javax.swing.JLabel();
        cbStatusDO = new javax.swing.JCheckBox();
        jPanel4 = new javax.swing.JPanel();
        txtkodeRetur = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        tglRetur = new datechooser.beans.DateChooserCombo();
        jLabel7 = new javax.swing.JLabel();
        kodepelanggan1 = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        namapelanggan1 = new javax.swing.JTextField();
        btnKeluar1 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        kodebarang = new javax.swing.JTextField();
        namabarang = new javax.swing.JTextField();
        jumlah = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        harga = new javax.swing.JTextField();
        btninsert = new javax.swing.JButton();
        btndelete = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        cboStatDiskonItem = new javax.swing.JComboBox();
        jLabel25 = new javax.swing.JLabel();
        diskonitem = new javax.swing.JFormattedTextField();
        jTextField1 = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        cboSatuan = new javax.swing.JComboBox();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        tglExpire = new datechooser.beans.DateChooserCombo();
        txtBatch = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        cbBonus = new javax.swing.JCheckBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        hasilTotal = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        hasilDiskon = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        hasilBayar = new javax.swing.JTextField();
        hasilSisa = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        hasilTotalBayar = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        hasilppn = new javax.swing.JTextField();
        btnSimpanHasil = new javax.swing.JButton();
        btnFaktur = new javax.swing.JButton();
        btnCetak = new javax.swing.JButton();
        txtNofakturTemp = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        DiskonTambah = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        RadioPendek = new javax.swing.JRadioButton();
        RadioPanjang = new javax.swing.JRadioButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();

        setClosable(true);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(javariesoft.JavarieSoftApp.class).getContext().getResourceMap(DialogPenjualanInternal.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N

        panelCool2.setName("panelCool2"); // NOI18N
        panelCool2.setLayout(null);

        jTabbedPane1.setFont(resourceMap.getFont("jTabbedPane1.font")); // NOI18N
        jTabbedPane1.setName("jTabbedPane1"); // NOI18N
        jTabbedPane1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jTabbedPane1StateChanged(evt);
            }
        });
        jTabbedPane1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTabbedPane1KeyPressed(evt);
            }
        });

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setLayout(null);

        jLabel2.setFont(resourceMap.getFont("jLabel2.font")); // NOI18N
        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N
        jPanel1.add(jLabel2);
        jLabel2.setBounds(20, 10, 110, 20);

        nofaktur.setEditable(false);
        nofaktur.setFont(resourceMap.getFont("nofaktur.font")); // NOI18N
        nofaktur.setName("nofaktur"); // NOI18N
        nofaktur.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                nofakturFocusLost(evt);
            }
        });
        nofaktur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nofakturKeyPressed(evt);
            }
        });
        jPanel1.add(nofaktur);
        nofaktur.setBounds(160, 10, 150, 22);

        tgl.setFieldFont(resourceMap.getFont("tgl.dch_combo_fieldFont")); // NOI18N
        tgl.setLocale(new java.util.Locale("in", "ID", ""));
        tgl.addCommitListener(new datechooser.events.CommitListener() {
            public void onCommit(datechooser.events.CommitEvent evt) {
                tglOnCommit(evt);
            }
        });
        tgl.addSelectionChangedListener(new datechooser.events.SelectionChangedListener() {
            public void onSelectionChange(datechooser.events.SelectionChangedEvent evt) {
                tglOnSelectionChange(evt);
            }
        });
        jPanel1.add(tgl);
        tgl.setBounds(570, 10, 150, 20);

        jLabel1.setFont(resourceMap.getFont("jLabel1.font")); // NOI18N
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N
        jPanel1.add(jLabel1);
        jLabel1.setBounds(370, 70, 0, 20);

        statusbayar.setFont(resourceMap.getFont("statusbayar.font")); // NOI18N
        statusbayar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "CASH", "KREDIT", "BANK", "-" }));
        statusbayar.setSelectedIndex(3);
        statusbayar.setName("statusbayar"); // NOI18N
        statusbayar.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                statusbayarItemStateChanged(evt);
            }
        });
        statusbayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                statusbayarKeyPressed(evt);
            }
        });
        jPanel1.add(statusbayar);
        statusbayar.setBounds(160, 35, 90, 22);

        jLabel5.setFont(resourceMap.getFont("jLabel2.font")); // NOI18N
        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N
        jPanel1.add(jLabel5);
        jLabel5.setBounds(20, 35, 110, 20);

        tgllunas.setNothingAllowed(false);
        tgllunas.setFieldFont(resourceMap.getFont("tgllunas.dch_combo_fieldFont")); // NOI18N
        tgllunas.setLocale(new java.util.Locale("in", "ID", ""));
        jPanel1.add(tgllunas);
        tgllunas.setBounds(570, 35, 150, 20);

        jLabel6.setFont(resourceMap.getFont("jLabel6.font")); // NOI18N
        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N
        jPanel1.add(jLabel6);
        jLabel6.setBounds(450, 35, 110, 20);

        jLabel3.setFont(resourceMap.getFont("jLabel6.font")); // NOI18N
        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N
        jPanel1.add(jLabel3);
        jLabel3.setBounds(450, 10, 100, 20);

        jScrollPane3.setName("jScrollPane3"); // NOI18N

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable3.setName("jTable3"); // NOI18N
        jTable3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable3KeyPressed(evt);
            }
        });
        jScrollPane3.setViewportView(jTable3);

        jPanel1.add(jScrollPane3);
        jScrollPane3.setBounds(160, 80, 40, 30);

        kodepelanggan.setFont(resourceMap.getFont("kodepelanggan.font")); // NOI18N
        kodepelanggan.setName("kodepelanggan"); // NOI18N
        kodepelanggan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kodepelangganActionPerformed(evt);
            }
        });
        kodepelanggan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kodepelangganKeyPressed(evt);
            }
        });
        jPanel1.add(kodepelanggan);
        kodepelanggan.setBounds(160, 60, 150, 22);

        jLabel4.setFont(resourceMap.getFont("jLabel2.font")); // NOI18N
        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N
        jPanel1.add(jLabel4);
        jLabel4.setBounds(20, 60, 120, 20);

        diskon.setFont(resourceMap.getFont("diskon.font")); // NOI18N
        diskon.setName("diskon"); // NOI18N
        diskon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                diskonKeyPressed(evt);
            }
        });
        jPanel1.add(diskon);
        diskon.setBounds(490, 70, 0, 22);

        jLabel18.setFont(resourceMap.getFont("jLabel18.font")); // NOI18N
        jLabel18.setText(resourceMap.getString("jLabel18.text")); // NOI18N
        jLabel18.setName("jLabel18"); // NOI18N
        jPanel1.add(jLabel18);
        jLabel18.setBounds(20, 85, 120, 20);

        namapelanggan.setFont(resourceMap.getFont("namapelanggan.font")); // NOI18N
        namapelanggan.setName("namapelanggan"); // NOI18N
        jPanel1.add(namapelanggan);
        namapelanggan.setBounds(160, 85, 250, 22);

        ppn.setFont(resourceMap.getFont("ppn.font")); // NOI18N
        ppn.setName("ppn"); // NOI18N
        ppn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ppnKeyPressed(evt);
            }
        });
        jPanel1.add(ppn);
        ppn.setBounds(490, 100, 0, 22);

        cboStatDiskon.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "%", "CASH" }));
        cboStatDiskon.setName("cboStatDiskon"); // NOI18N
        jPanel1.add(cboStatDiskon);
        cboStatDiskon.setBounds(650, 70, 0, 20);

        cboStatPPN.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "%", "CASH" }));
        cboStatPPN.setName("cboStatPPN"); // NOI18N
        jPanel1.add(cboStatPPN);
        cboStatPPN.setBounds(650, 100, 0, 20);

        btnBaru.setFont(resourceMap.getFont("btnBaru.font")); // NOI18N
        btnBaru.setIcon(resourceMap.getIcon("btnBaru.icon")); // NOI18N
        btnBaru.setText(resourceMap.getString("btnBaru.text")); // NOI18N
        btnBaru.setName("btnBaru"); // NOI18N
        btnBaru.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBaruActionPerformed(evt);
            }
        });
        jPanel1.add(btnBaru);
        btnBaru.setBounds(740, 40, 140, 30);

        jLabel9.setEnabled(false);
        jLabel9.setName("jLabel9"); // NOI18N
        jPanel1.add(jLabel9);
        jLabel9.setBounds(170, 140, 90, 20);

        jLabel26.setFont(resourceMap.getFont("jLabel26.font")); // NOI18N
        jLabel26.setText(resourceMap.getString("jLabel26.text")); // NOI18N
        jLabel26.setName("jLabel26"); // NOI18N
        jPanel1.add(jLabel26);
        jLabel26.setBounds(370, 100, 0, 20);

        txtSales.setFont(resourceMap.getFont("txtSales.font")); // NOI18N
        txtSales.setText(resourceMap.getString("txtSales.text")); // NOI18N
        txtSales.setName("txtSales"); // NOI18N
        txtSales.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSalesActionPerformed(evt);
            }
        });
        txtSales.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSalesKeyPressed(evt);
            }
        });
        jPanel1.add(txtSales);
        txtSales.setBounds(570, 60, 120, 22);

        txtNamaSales.setEditable(false);
        txtNamaSales.setFont(resourceMap.getFont("txtNamaSales.font")); // NOI18N
        txtNamaSales.setText(resourceMap.getString("txtNamaSales.text")); // NOI18N
        txtNamaSales.setName("txtNamaSales"); // NOI18N
        jPanel1.add(txtNamaSales);
        txtNamaSales.setBounds(570, 85, 310, 22);

        jLabel27.setFont(resourceMap.getFont("jLabel6.font")); // NOI18N
        jLabel27.setText(resourceMap.getString("jLabel27.text")); // NOI18N
        jLabel27.setName("jLabel27"); // NOI18N
        jPanel1.add(jLabel27);
        jLabel27.setBounds(450, 60, 100, 20);

        LabelKodeDO.setFont(resourceMap.getFont("jLabel6.font")); // NOI18N
        LabelKodeDO.setText(resourceMap.getString("LabelKodeDO.text")); // NOI18N
        LabelKodeDO.setName("LabelKodeDO"); // NOI18N
        jPanel1.add(LabelKodeDO);
        LabelKodeDO.setBounds(450, 110, 100, 20);

        txtKodeDO.setEditable(false);
        txtKodeDO.setFont(resourceMap.getFont("txtKodeDO.font")); // NOI18N
        txtKodeDO.setText(resourceMap.getString("txtKodeDO.text")); // NOI18N
        txtKodeDO.setName("txtKodeDO"); // NOI18N
        txtKodeDO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtKodeDOActionPerformed(evt);
            }
        });
        txtKodeDO.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtKodeDOKeyPressed(evt);
            }
        });
        jPanel1.add(txtKodeDO);
        txtKodeDO.setBounds(600, 110, 70, 22);

        txtNamaDo.setFont(resourceMap.getFont("txtNamaDo.font")); // NOI18N
        txtNamaDo.setText(resourceMap.getString("txtNamaDo.text")); // NOI18N
        txtNamaDo.setName("txtNamaDo"); // NOI18N
        jPanel1.add(txtNamaDo);
        txtNamaDo.setBounds(680, 110, 200, 22);

        jLabel29.setFont(resourceMap.getFont("jLabel6.font")); // NOI18N
        jLabel29.setText(resourceMap.getString("jLabel29.text")); // NOI18N
        jLabel29.setName("jLabel29"); // NOI18N
        jPanel1.add(jLabel29);
        jLabel29.setBounds(450, 85, 100, 20);

        jScrollPane5.setName("jScrollPane5"); // NOI18N

        jTable5.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jTable5.setName("jTable5"); // NOI18N
        jTable5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable5KeyPressed(evt);
            }
        });
        jScrollPane5.setViewportView(jTable5);

        jPanel1.add(jScrollPane5);
        jScrollPane5.setBounds(900, 30, 90, 40);

        BtnGenNum.setFont(resourceMap.getFont("BtnGenNum.font")); // NOI18N
        BtnGenNum.setText(resourceMap.getString("BtnGenNum.text")); // NOI18N
        BtnGenNum.setName("BtnGenNum"); // NOI18N
        BtnGenNum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnGenNumActionPerformed(evt);
            }
        });
        jPanel1.add(BtnGenNum);
        BtnGenNum.setBounds(320, 10, 90, 25);

        CboNamaBank.setFont(resourceMap.getFont("CboNamaBank.font")); // NOI18N
        CboNamaBank.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        CboNamaBank.setName("CboNamaBank"); // NOI18N
        jPanel1.add(CboNamaBank);
        CboNamaBank.setBounds(260, 35, 150, 22);

        tbnKeluar.setFont(resourceMap.getFont("tbnKeluar.font")); // NOI18N
        tbnKeluar.setIcon(resourceMap.getIcon("tbnKeluar.icon")); // NOI18N
        tbnKeluar.setText(resourceMap.getString("tbnKeluar.text")); // NOI18N
        tbnKeluar.setName("tbnKeluar"); // NOI18N
        tbnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbnKeluarActionPerformed(evt);
            }
        });
        jPanel1.add(tbnKeluar);
        tbnKeluar.setBounds(740, 5, 140, 30);

        btnNewPlgn.setText(resourceMap.getString("btnNewPlgn.text")); // NOI18N
        btnNewPlgn.setName("btnNewPlgn"); // NOI18N
        btnNewPlgn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewPlgnActionPerformed(evt);
            }
        });
        jPanel1.add(btnNewPlgn);
        btnNewPlgn.setBounds(310, 60, 40, 23);

        CboTipeHarga.setFont(resourceMap.getFont("CboTipeHarga.font")); // NOI18N
        CboTipeHarga.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Rutin", "Tender" }));
        CboTipeHarga.setName("CboTipeHarga"); // NOI18N
        jPanel1.add(CboTipeHarga);
        CboTipeHarga.setBounds(160, 110, 150, 22);

        jLabel8.setFont(resourceMap.getFont("jLabel8.font")); // NOI18N
        jLabel8.setText(resourceMap.getString("jLabel8.text")); // NOI18N
        jLabel8.setName("jLabel8"); // NOI18N
        jPanel1.add(jLabel8);
        jLabel8.setBounds(20, 110, 130, 20);

        cbPPN.setFont(resourceMap.getFont("cbPPN.font")); // NOI18N
        cbPPN.setName("cbPPN"); // NOI18N
        cbPPN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbPPNActionPerformed(evt);
            }
        });
        cbPPN.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cbPPNKeyPressed(evt);
            }
        });
        jPanel1.add(cbPPN);
        cbPPN.setBounds(380, 110, 21, 20);

        jLabel10.setFont(resourceMap.getFont("jLabel10.font")); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel10.setText(resourceMap.getString("jLabel10.text")); // NOI18N
        jLabel10.setName("jLabel10"); // NOI18N
        jPanel1.add(jLabel10);
        jLabel10.setBounds(330, 110, 50, 20);

        cbStatusDO.setFont(resourceMap.getFont("cbStatusDO.font")); // NOI18N
        cbStatusDO.setName("cbStatusDO"); // NOI18N
        cbStatusDO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbStatusDOActionPerformed(evt);
            }
        });
        cbStatusDO.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cbStatusDOKeyPressed(evt);
            }
        });
        jPanel1.add(cbStatusDO);
        cbStatusDO.setBounds(570, 110, 21, 20);

        jTabbedPane1.addTab(resourceMap.getString("jPanel1.TabConstraints.tabTitle"), jPanel1); // NOI18N

        jPanel4.setName("jPanel4"); // NOI18N
        jPanel4.setLayout(null);

        txtkodeRetur.setFont(resourceMap.getFont("txtkodeRetur.font")); // NOI18N
        txtkodeRetur.setText(resourceMap.getString("txtkodeRetur.text")); // NOI18N
        txtkodeRetur.setEnabled(false);
        txtkodeRetur.setName("txtkodeRetur"); // NOI18N
        txtkodeRetur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtkodeReturActionPerformed(evt);
            }
        });
        jPanel4.add(txtkodeRetur);
        txtkodeRetur.setBounds(160, 10, 140, 22);

        jLabel20.setFont(resourceMap.getFont("jLabel20.font")); // NOI18N
        jLabel20.setText(resourceMap.getString("jLabel20.text")); // NOI18N
        jLabel20.setName("jLabel20"); // NOI18N
        jPanel4.add(jLabel20);
        jLabel20.setBounds(20, 40, 90, 20);

        jLabel21.setFont(resourceMap.getFont("jLabel21.font")); // NOI18N
        jLabel21.setText(resourceMap.getString("jLabel21.text")); // NOI18N
        jLabel21.setName("jLabel21"); // NOI18N
        jPanel4.add(jLabel21);
        jLabel21.setBounds(20, 10, 90, 20);
        jPanel4.add(tglRetur);
        tglRetur.setBounds(160, 40, 150, 20);

        jLabel7.setFont(resourceMap.getFont("jLabel7.font")); // NOI18N
        jLabel7.setText(resourceMap.getString("jLabel7.text")); // NOI18N
        jLabel7.setName("jLabel7"); // NOI18N
        jPanel4.add(jLabel7);
        jLabel7.setBounds(20, 70, 120, 20);

        kodepelanggan1.setFont(resourceMap.getFont("kodepelanggan1.font")); // NOI18N
        kodepelanggan1.setEnabled(false);
        kodepelanggan1.setName("kodepelanggan1"); // NOI18N
        kodepelanggan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kodepelanggan1ActionPerformed(evt);
            }
        });
        kodepelanggan1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kodepelanggan1KeyPressed(evt);
            }
        });
        jPanel4.add(kodepelanggan1);
        kodepelanggan1.setBounds(160, 70, 150, 22);

        jLabel24.setFont(resourceMap.getFont("jLabel24.font")); // NOI18N
        jLabel24.setText(resourceMap.getString("jLabel24.text")); // NOI18N
        jLabel24.setName("jLabel24"); // NOI18N
        jPanel4.add(jLabel24);
        jLabel24.setBounds(20, 100, 120, 20);

        namapelanggan1.setFont(resourceMap.getFont("namapelanggan1.font")); // NOI18N
        namapelanggan1.setEnabled(false);
        namapelanggan1.setName("namapelanggan1"); // NOI18N
        jPanel4.add(namapelanggan1);
        namapelanggan1.setBounds(160, 100, 270, 22);

        btnKeluar1.setFont(resourceMap.getFont("btnKeluar1.font")); // NOI18N
        btnKeluar1.setText(resourceMap.getString("btnKeluar1.text")); // NOI18N
        btnKeluar1.setName("btnKeluar1"); // NOI18N
        btnKeluar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKeluar1ActionPerformed(evt);
            }
        });
        jPanel4.add(btnKeluar1);
        btnKeluar1.setBounds(770, 103, 120, 30);

        jTabbedPane1.addTab(resourceMap.getString("jPanel4.TabConstraints.tabTitle"), jPanel4); // NOI18N

        panelCool2.add(jTabbedPane1);
        jTabbedPane1.setBounds(10, 10, 1060, 170);

        jPanel5.setName("jPanel5"); // NOI18N
        jPanel5.setLayout(null);

        kodebarang.setFont(resourceMap.getFont("kodebarang.font")); // NOI18N
        kodebarang.setName("kodebarang"); // NOI18N
        kodebarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kodebarangActionPerformed(evt);
            }
        });
        kodebarang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kodebarangKeyPressed(evt);
            }
        });
        jPanel5.add(kodebarang);
        kodebarang.setBounds(0, 20, 85, 22);

        namabarang.setFont(resourceMap.getFont("namabarang.font")); // NOI18N
        namabarang.setEnabled(false);
        namabarang.setName("namabarang"); // NOI18N
        jPanel5.add(namabarang);
        namabarang.setBounds(86, 20, 320, 22);

        jumlah.setFont(resourceMap.getFont("jumlah.font")); // NOI18N
        jumlah.setName("jumlah"); // NOI18N
        jumlah.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jumlahCaretUpdate(evt);
            }
        });
        jumlah.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jumlahFocusLost(evt);
            }
        });
        jumlah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jumlahKeyPressed(evt);
            }
        });
        jPanel5.add(jumlah);
        jumlah.setBounds(810, 20, 50, 20);

        jLabel22.setFont(resourceMap.getFont("jLabel22.font")); // NOI18N
        jLabel22.setText(resourceMap.getString("jLabel22.text")); // NOI18N
        jLabel22.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel22.setName("jLabel22"); // NOI18N
        jPanel5.add(jLabel22);
        jLabel22.setBounds(0, 0, 86, 20);

        jLabel23.setFont(resourceMap.getFont("jLabel23.font")); // NOI18N
        jLabel23.setText(resourceMap.getString("jLabel23.text")); // NOI18N
        jLabel23.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel23.setName("jLabel23"); // NOI18N
        jPanel5.add(jLabel23);
        jLabel23.setBounds(86, 0, 320, 20);

        harga.setFont(resourceMap.getFont("harga.font")); // NOI18N
        harga.setName("harga"); // NOI18N
        harga.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                hargaFocusLost(evt);
            }
        });
        harga.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                hargaKeyPressed(evt);
            }
        });
        jPanel5.add(harga);
        harga.setBounds(713, 20, 96, 20);

        btninsert.setFont(resourceMap.getFont("btninsert.font")); // NOI18N
        btninsert.setText(resourceMap.getString("btninsert.text")); // NOI18N
        btninsert.setName("btninsert"); // NOI18N
        btninsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btninsertActionPerformed(evt);
            }
        });
        jPanel5.add(btninsert);
        btninsert.setBounds(880, 40, 90, 20);

        btndelete.setFont(resourceMap.getFont("btndelete.font")); // NOI18N
        btndelete.setText(resourceMap.getString("btndelete.text")); // NOI18N
        btndelete.setName("btndelete"); // NOI18N
        btndelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndeleteActionPerformed(evt);
            }
        });
        jPanel5.add(btndelete);
        btndelete.setBounds(970, 40, 90, 20);

        jLabel16.setFont(resourceMap.getFont("jLabel16.font")); // NOI18N
        jLabel16.setText(resourceMap.getString("jLabel16.text")); // NOI18N
        jLabel16.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel16.setName("jLabel16"); // NOI18N
        jPanel5.add(jLabel16);
        jLabel16.setBounds(810, 0, 50, 20);

        cboStatDiskonItem.setFont(resourceMap.getFont("cboStatDiskonItem.font")); // NOI18N
        cboStatDiskonItem.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "%", "Rp" }));
        cboStatDiskonItem.setName("cboStatDiskonItem"); // NOI18N
        cboStatDiskonItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboStatDiskonItemActionPerformed(evt);
            }
        });
        cboStatDiskonItem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cboStatDiskonItemKeyPressed(evt);
            }
        });
        jPanel5.add(cboStatDiskonItem);
        cboStatDiskonItem.setBounds(950, 20, 50, 20);

        jLabel25.setFont(resourceMap.getFont("jLabel25.font")); // NOI18N
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setText(resourceMap.getString("jLabel25.text")); // NOI18N
        jLabel25.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel25.setName("jLabel25"); // NOI18N
        jPanel5.add(jLabel25);
        jLabel25.setBounds(860, 0, 140, 20);

        diskonitem.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        diskonitem.setFont(resourceMap.getFont("diskonitem.font")); // NOI18N
        diskonitem.setName("diskonitem"); // NOI18N
        diskonitem.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                diskonitemFocusLost(evt);
            }
        });
        diskonitem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                diskonitemKeyPressed(evt);
            }
        });
        jPanel5.add(diskonitem);
        diskonitem.setBounds(860, 20, 90, 20);

        jTextField1.setFont(resourceMap.getFont("jTextField1.font")); // NOI18N
        jTextField1.setEnabled(false);
        jTextField1.setName("jTextField1"); // NOI18N
        jPanel5.add(jTextField1);
        jTextField1.setBounds(740, 90, 20, 22);

        jLabel19.setFont(resourceMap.getFont("jLabel19.font")); // NOI18N
        jLabel19.setText(resourceMap.getString("jLabel19.text")); // NOI18N
        jLabel19.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel19.setName("jLabel19"); // NOI18N
        jPanel5.add(jLabel19);
        jLabel19.setBounds(713, 0, 96, 20);

        jLabel30.setFont(resourceMap.getFont("jLabel30.font")); // NOI18N
        jLabel30.setText(resourceMap.getString("jLabel30.text")); // NOI18N
        jLabel30.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel30.setMaximumSize(new java.awt.Dimension(52, 20));
        jLabel30.setMinimumSize(new java.awt.Dimension(52, 20));
        jLabel30.setName("jLabel30"); // NOI18N
        jLabel30.setPreferredSize(new java.awt.Dimension(52, 20));
        jPanel5.add(jLabel30);
        jLabel30.setBounds(618, 0, 93, 20);

        cboSatuan.setFont(resourceMap.getFont("cboSatuan.font")); // NOI18N
        cboSatuan.setName("cboSatuan"); // NOI18N
        cboSatuan.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboSatuanItemStateChanged(evt);
            }
        });
        cboSatuan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cboSatuanKeyPressed(evt);
            }
        });
        jPanel5.add(cboSatuan);
        cboSatuan.setBounds(618, 20, 93, 20);

        jLabel31.setFont(resourceMap.getFont("jLabel31.font")); // NOI18N
        jLabel31.setText(resourceMap.getString("jLabel31.text")); // NOI18N
        jLabel31.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel31.setName("jLabel31"); // NOI18N
        jPanel5.add(jLabel31);
        jLabel31.setBounds(407, 0, 100, 20);

        jLabel32.setFont(resourceMap.getFont("jLabel32.font")); // NOI18N
        jLabel32.setText(resourceMap.getString("jLabel32.text")); // NOI18N
        jLabel32.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel32.setName("jLabel32"); // NOI18N
        jPanel5.add(jLabel32);
        jLabel32.setBounds(507, 0, 110, 20);

        tglExpire.addCommitListener(new datechooser.events.CommitListener() {
            public void onCommit(datechooser.events.CommitEvent evt) {
                tglExpireOnCommit(evt);
            }
        });
        jPanel5.add(tglExpire);
        tglExpire.setBounds(507, 20, 110, 20);
        tglExpire.setDateFormat(d);

        txtBatch.setFont(resourceMap.getFont("txtBatch.font")); // NOI18N
        txtBatch.setText(resourceMap.getString("txtBatch.text")); // NOI18N
        txtBatch.setEnabled(false);
        txtBatch.setName("txtBatch"); // NOI18N
        jPanel5.add(txtBatch);
        txtBatch.setBounds(407, 20, 99, 22);

        jLabel33.setFont(resourceMap.getFont("jLabel33.font")); // NOI18N
        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel33.setText(resourceMap.getString("jLabel33.text")); // NOI18N
        jLabel33.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel33.setName("jLabel33"); // NOI18N
        jPanel5.add(jLabel33);
        jLabel33.setBounds(1000, 0, 60, 20);

        cbBonus.setFont(resourceMap.getFont("cbBonus.font")); // NOI18N
        cbBonus.setName("cbBonus"); // NOI18N
        cbBonus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbBonusActionPerformed(evt);
            }
        });
        cbBonus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cbBonusKeyPressed(evt);
            }
        });
        jPanel5.add(cbBonus);
        cbBonus.setBounds(1020, 20, 30, 20);

        panelCool2.add(jPanel5);
        jPanel5.setBounds(10, 180, 1060, 60);

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jTable1.setFont(resourceMap.getFont("jTable1.font")); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No", "Kode", "Nama Barang", "Batch", "Expire", "Jumlah", "Satuan", "Harga", "Diskon", "PPN", "Total", "DO", "Cogs", "JumlahKecil", "Diskon %", "Bonus"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setFocusable(false);
        jTable1.setName("jTable1"); // NOI18N
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        panelCool2.add(jScrollPane1);
        jScrollPane1.setBounds(10, 240, 1060, 300);

        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setLayout(null);

        jLabel11.setFont(resourceMap.getFont("jLabel11.font")); // NOI18N
        jLabel11.setText(resourceMap.getString("jLabel11.text")); // NOI18N
        jLabel11.setName("jLabel11"); // NOI18N
        jPanel3.add(jLabel11);
        jLabel11.setBounds(20, 10, 110, 20);

        hasilTotal.setEditable(false);
        hasilTotal.setFont(resourceMap.getFont("hasilTotal.font")); // NOI18N
        hasilTotal.setName("hasilTotal"); // NOI18N
        jPanel3.add(hasilTotal);
        hasilTotal.setBounds(100, 10, 160, 22);

        jLabel12.setFont(resourceMap.getFont("jLabel12.font")); // NOI18N
        jLabel12.setText(resourceMap.getString("jLabel12.text")); // NOI18N
        jLabel12.setName("jLabel12"); // NOI18N
        jPanel3.add(jLabel12);
        jLabel12.setBounds(20, 34, 100, 20);

        hasilDiskon.setEditable(false);
        hasilDiskon.setFont(resourceMap.getFont("hasilDiskon.font")); // NOI18N
        hasilDiskon.setName("hasilDiskon"); // NOI18N
        hasilDiskon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                hasilDiskonKeyPressed(evt);
            }
        });
        jPanel3.add(hasilDiskon);
        hasilDiskon.setBounds(100, 34, 160, 22);

        jLabel13.setFont(resourceMap.getFont("jLabel13.font")); // NOI18N
        jLabel13.setText(resourceMap.getString("jLabel13.text")); // NOI18N
        jLabel13.setName("jLabel13"); // NOI18N
        jPanel3.add(jLabel13);
        jLabel13.setBounds(300, 10, 110, 20);

        jLabel14.setFont(resourceMap.getFont("jLabel14.font")); // NOI18N
        jLabel14.setText(resourceMap.getString("jLabel14.text")); // NOI18N
        jLabel14.setName("jLabel14"); // NOI18N
        jPanel3.add(jLabel14);
        jLabel14.setBounds(300, 58, 90, 20);

        hasilBayar.setFont(resourceMap.getFont("hasilBayar.font")); // NOI18N
        hasilBayar.setName("hasilBayar"); // NOI18N
        hasilBayar.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                hasilBayarCaretUpdate(evt);
            }
        });
        hasilBayar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                hasilBayarFocusGained(evt);
            }
        });
        hasilBayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                hasilBayarKeyPressed(evt);
            }
        });
        jPanel3.add(hasilBayar);
        hasilBayar.setBounds(420, 34, 180, 22);

        hasilSisa.setEditable(false);
        hasilSisa.setFont(resourceMap.getFont("hasilSisa.font")); // NOI18N
        hasilSisa.setName("hasilSisa"); // NOI18N
        jPanel3.add(hasilSisa);
        hasilSisa.setBounds(420, 58, 180, 22);

        jLabel17.setFont(resourceMap.getFont("jLabel17.font")); // NOI18N
        jLabel17.setText(resourceMap.getString("jLabel17.text")); // NOI18N
        jLabel17.setName("jLabel17"); // NOI18N
        jPanel3.add(jLabel17);
        jLabel17.setBounds(20, 82, 110, 20);

        hasilTotalBayar.setEditable(false);
        hasilTotalBayar.setFont(resourceMap.getFont("hasilTotalBayar.font")); // NOI18N
        hasilTotalBayar.setName("hasilTotalBayar"); // NOI18N
        jPanel3.add(hasilTotalBayar);
        hasilTotalBayar.setBounds(100, 82, 160, 22);

        jLabel15.setFont(resourceMap.getFont("jLabel15.font")); // NOI18N
        jLabel15.setText(resourceMap.getString("jLabel15.text")); // NOI18N
        jLabel15.setName("jLabel15"); // NOI18N
        jPanel3.add(jLabel15);
        jLabel15.setBounds(20, 58, 70, 14);

        hasilppn.setEditable(false);
        hasilppn.setFont(resourceMap.getFont("hasilppn.font")); // NOI18N
        hasilppn.setName("hasilppn"); // NOI18N
        jPanel3.add(hasilppn);
        hasilppn.setBounds(100, 58, 160, 22);

        btnSimpanHasil.setFont(resourceMap.getFont("btnSimpanHasil.font")); // NOI18N
        btnSimpanHasil.setIcon(resourceMap.getIcon("btnSimpanHasil.icon")); // NOI18N
        btnSimpanHasil.setText(resourceMap.getString("btnSimpanHasil.text")); // NOI18N
        btnSimpanHasil.setName("btnSimpanHasil"); // NOI18N
        btnSimpanHasil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanHasilActionPerformed(evt);
            }
        });
        jPanel3.add(btnSimpanHasil);
        btnSimpanHasil.setBounds(610, 10, 110, 90);

        btnFaktur.setFont(resourceMap.getFont("btnFaktur.font")); // NOI18N
        btnFaktur.setIcon(resourceMap.getIcon("btnFaktur.icon")); // NOI18N
        btnFaktur.setText(resourceMap.getString("btnFaktur.text")); // NOI18N
        btnFaktur.setName("btnFaktur"); // NOI18N
        btnFaktur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFakturActionPerformed(evt);
            }
        });
        jPanel3.add(btnFaktur);
        btnFaktur.setBounds(730, 34, 160, 30);

        btnCetak.setFont(resourceMap.getFont("btnCetak.font")); // NOI18N
        btnCetak.setIcon(resourceMap.getIcon("btnCetak.icon")); // NOI18N
        btnCetak.setText(resourceMap.getString("btnCetak.text")); // NOI18N
        btnCetak.setName("btnCetak"); // NOI18N
        btnCetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCetakActionPerformed(evt);
            }
        });
        jPanel3.add(btnCetak);
        btnCetak.setBounds(730, 66, 160, 40);

        txtNofakturTemp.setFont(resourceMap.getFont("txtNofakturTemp.font")); // NOI18N
        txtNofakturTemp.setName("txtNofakturTemp"); // NOI18N
        jPanel3.add(txtNofakturTemp);
        txtNofakturTemp.setBounds(730, 10, 160, 22);

        jLabel28.setFont(resourceMap.getFont("jLabel28.font")); // NOI18N
        jLabel28.setText(resourceMap.getString("jLabel28.text")); // NOI18N
        jLabel28.setName("jLabel28"); // NOI18N
        jPanel3.add(jLabel28);
        jLabel28.setBounds(300, 34, 100, 20);

        DiskonTambah.setFont(resourceMap.getFont("DiskonTambah.font")); // NOI18N
        DiskonTambah.setName("DiskonTambah"); // NOI18N
        DiskonTambah.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                DiskonTambahCaretUpdate(evt);
            }
        });
        DiskonTambah.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                DiskonTambahFocusLost(evt);
            }
        });
        DiskonTambah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiskonTambahKeyPressed(evt);
            }
        });
        jPanel3.add(DiskonTambah);
        DiskonTambah.setBounds(420, 10, 180, 22);

        jLabel35.setFont(resourceMap.getFont("jLabel35.font")); // NOI18N
        jLabel35.setText(resourceMap.getString("jLabel35.text")); // NOI18N
        jLabel35.setName("jLabel35"); // NOI18N
        jPanel3.add(jLabel35);
        jLabel35.setBounds(920, 20, 110, 16);

        RadioPendek.setFont(resourceMap.getFont("RadioPendek.font")); // NOI18N
        RadioPendek.setText(resourceMap.getString("RadioPendek.text")); // NOI18N
        RadioPendek.setName("RadioPendek"); // NOI18N
        RadioPendek.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RadioPendekActionPerformed(evt);
            }
        });
        jPanel3.add(RadioPendek);
        RadioPendek.setBounds(910, 40, 120, 25);

        RadioPanjang.setFont(resourceMap.getFont("RadioPanjang.font")); // NOI18N
        RadioPanjang.setText(resourceMap.getString("RadioPanjang.text")); // NOI18N
        RadioPanjang.setName("RadioPanjang"); // NOI18N
        RadioPanjang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RadioPanjangActionPerformed(evt);
            }
        });
        jPanel3.add(RadioPanjang);
        RadioPanjang.setBounds(910, 60, 120, 25);

        panelCool2.add(jPanel3);
        jPanel3.setBounds(10, 540, 1060, 110);

        jScrollPane2.setBackground(resourceMap.getColor("jScrollPane2.background")); // NOI18N
        jScrollPane2.setName("jScrollPane2"); // NOI18N

        jTable2.setForeground(resourceMap.getColor("jTable2.foreground")); // NOI18N
        jTable2.setName("jTable2"); // NOI18N
        jTable2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable2KeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);

        panelCool2.add(jScrollPane2);
        jScrollPane2.setBounds(10, 220, 400, 20);

        getContentPane().add(panelCool2, java.awt.BorderLayout.CENTER);

        setBounds(0, 0, 1096, 685);
    }// </editor-fold>//GEN-END:initComponents

    private void nofakturFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_nofakturFocusLost
        try {
            // TODO add your handling code here:
            //Connection c = koneksi.getKoneksiJ();
            if (!penjualanDao.cekFaktur(c, nofaktur.getText())) {
                tgl.requestFocus();
            } else {
                JOptionPane.showMessageDialog(null, "Nomor Faktur Penjualan Ini Sudah Ada.. !");
                nofaktur.requestFocus();
            }
            //c.close();
        } catch (Exception ex) {
            Logger.getLogger(DialogPenjualan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_nofakturFocusLost

    private void nofakturKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nofakturKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == 10) {
            tgl.requestFocus();
        }
    }//GEN-LAST:event_nofakturKeyPressed

    private void tglOnCommit(datechooser.events.CommitEvent evt) {//GEN-FIRST:event_tglOnCommit
        // TODO add your handling code here:
        //settingTgl();
    }//GEN-LAST:event_tglOnCommit

    private void tglOnSelectionChange(datechooser.events.SelectionChangedEvent evt) {//GEN-FIRST:event_tglOnSelectionChange
        // TODO add your handling code here:
    }//GEN-LAST:event_tglOnSelectionChange

    private void jTable3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable3KeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == 10) {
            //Connection c = null;
            try {
                //c = koneksi.getKoneksiJ();
                pelangganDao dbpelanggan = new pelangganDao(c);
                plg = dbpelanggan.getDetails(jTable3.getValueAt(jTable3.getSelectedRow(), 0).toString());
                if (pilPelanggan.equals("0")) {
                    kodepelanggan.setText(plg.getKODEPELANGGAN());
                    namapelanggan.setText(plg.getNAMA());
                    jScrollPane3.setVisible(false);
                    txtSales.requestFocus();
                } else if (pilPelanggan.equals("1")) {
                    kodepelanggan1.setText(plg.getKODEPELANGGAN());
                    namapelanggan1.setText(plg.getNAMA());
                    jScrollPane3.setVisible(false);
                    kodebarang.requestFocus();
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
            //        finally {
            //            if (c != null) {
            //                try {
            //                    c.close();
            //                } catch (SQLException ex) {
            //                    Logger.getLogger(DialogPenjualan.class.getName()).log(Level.SEVERE, null, ex);
            //                }
            //            }
            //        }
        }
    }//GEN-LAST:event_jTable3KeyPressed

    private void kodepelangganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kodepelangganActionPerformed
        //Connection c = null;
        try {
            // TODO add your handling code here:
            //c = koneksi.getKoneksiJ();
            pilPelanggan = "0";
            jScrollPane3.setVisible(true);
            JDBCAdapter ja = new JDBCAdapter(c);
            //    ja.executeQuery("select KODEPELANGGAN,NAMA,ALAMAT,"
            //            + "(select sum(vp.JUMLAH - vp.JUMLAHBAYAR) from view_piutang vp "
            //            + "where vp.idpelanggan=pelanggan.kodepelanggan) as totpiutang "
            //            + "from PELANGGAN where STATUSCABANG<>'1' and (KODEPELANGGAN like '" + kodepelanggan.getText() + "%' or lower(NAMA) like '%" + kodepelanggan.getText().toLowerCase() + "%') order by NAMA");
            ja.executeQuery("select KODEPELANGGAN,NAMA,ALAMAT,"
                    + "ifnull((select sum(vp.JUMLAH - vp.JUMLAHBAYAR) from view_piutang vp "
                    + "where vp.idpelanggan=pelanggan.kodepelanggan),0) AS TOTPIUTANG  "
                    + "from PELANGGAN where STATUSAKTIF='0' AND STATUSCABANG<>'1' and (KODEPELANGGAN like '" + kodepelanggan.getText() + "%' or lower(NAMA) like '%" + kodepelanggan.getText().toLowerCase() + "%') order by NAMA");
            jScrollPane3.getViewport().remove(jTable3);
            jTable3 = new JTable(ja);
            jTable3.addKeyListener(new java.awt.event.KeyAdapter() {
                @Override
                public void keyPressed(java.awt.event.KeyEvent evt) {
                    jTable3KeyPressed(evt);
                }
            });
            jTable3.getColumnModel().getColumn(3).setCellRenderer(dcfr);
            TableColumn col = jTable3.getColumnModel().getColumn(0);
            col.setPreferredWidth(30);
            col = jTable3.getColumnModel().getColumn(1);
            col.setPreferredWidth(200);
            col = jTable3.getColumnModel().getColumn(2);
            col.setPreferredWidth(100);
            col = jTable3.getColumnModel().getColumn(3);
            col.setPreferredWidth(100);

            jTable3.setFont(new Font("Tahoma", Font.BOLD, 12));
            jScrollPane3.getViewport().add(jTable3);
            jScrollPane3.repaint();
        } catch (Exception ex) {
            Logger.getLogger(DialogPenjualan.class.getName()).log(Level.SEVERE, null, ex);
        }
        //    finally {
        //        if (c != null) {
        //            try {
        //                c.close();
        //            } catch (SQLException ex) {
        //                Logger.getLogger(DialogPenjualan.class.getName()).log(Level.SEVERE, null, ex);
        //            }
        //        }
        //    }
    }//GEN-LAST:event_kodepelangganActionPerformed

    private void kodepelangganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kodepelangganKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == 40) {
            jScrollPane3.setVisible(true);
            jTable3.requestFocus();
            jTable3.getSelectionModel().setSelectionInterval(0, 0);
        }
        if (evt.getKeyCode() == 27) {
            jScrollPane3.setVisible(false);
        }
    }//GEN-LAST:event_kodepelangganKeyPressed

    private void diskonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_diskonKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == 10) {
            try {
                //Connection c = koneksi.getKoneksiJ();
                hasilAkhir(c);
                //c.close();
                ppn.requestFocus();
            } catch (SQLException ex) {
                Logger.getLogger(DialogPenjualan.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_diskonKeyPressed

    private void ppnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ppnKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == 10) {
            try {
                //Connection c = koneksi.getKoneksiJ();
                hasilAkhir(c);
                //c.close();
                txtSales.requestFocus();
            } catch (SQLException ex) {
                Logger.getLogger(DialogPenjualan.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_ppnKeyPressed

    private void btnBaruActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBaruActionPerformed
        //Connection c = null;
        try {
            // TODO add your handling code here:
            ////c = koneksi.getKoneksiJ();
            jTable1.setModel(new javax.swing.table.DefaultTableModel(
                    new Object[][]{},
                    new String[]{
                        "No", "Kode", "Nama Barang", "Batch", "Expire", "Jumlah", "Satuan", "Harga", "Diskon", "PPN", "Total", "DO", "Cogs", "JumlahKecil", "Diskon %", "Bonus"
                    }
            ) {
                boolean[] canEdit = new boolean[]{
                    false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
                };

                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return false;
                }
            });
            model = (DefaultTableModel) jTable1.getModel();
            total = 0;
            hpp = 0;
            DiskonItem = 0;
            PPNItem = 0;
            reloadData(c);
            kosongFaktur();
            kosongBarang();
            kosongHasil();
            //hapusTabel();
            //buatTabel();
            setFaktur(c);
            kodepelanggan.requestFocus();
        } catch (SQLException ex) {
            Logger.getLogger(DialogPenjualan.class.getName()).log(Level.SEVERE, null, ex);
        }
        //    finally {
        //        if (c != null) {
        //            try {
        //                c.close();
        //            } catch (SQLException ex) {
        //                Logger.getLogger(DialogPenjualan.class.getName()).log(Level.SEVERE, null, ex);
        //            }
        //        }
        //    }
    }//GEN-LAST:event_btnBaruActionPerformed

    private void txtSalesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSalesActionPerformed
        //Connection c = null;
        try {
            // TODO add your handling code here:
            //c = koneksi.getKoneksiJ();
            pil = "0";
            jScrollPane5.setVisible(true);
            JDBCAdapter ja = new JDBCAdapter(c);
            ja.executeQuery("select IDSALES,NAMA from SALES where IDSALES like '" + txtSales.getText() + "%' or lower(NAMA) like '" + txtSales.getText().toLowerCase() + "%'");
            jScrollPane5.getViewport().remove(jTable5);
            jTable5 = new JTable(ja);
            jTable5.addKeyListener(new java.awt.event.KeyAdapter() {
                @Override
                public void keyPressed(java.awt.event.KeyEvent evt) {
                    jTable5KeyPressed(evt);
                }
            });
            jTable5.setFont(new Font("Tahoma", Font.BOLD, 12));
            jScrollPane5.getViewport().add(jTable5);
            jScrollPane5.repaint();
        } catch (Exception ex) {
            Logger.getLogger(DialogPenjualan.class.getName()).log(Level.SEVERE, null, ex);
        }
        //    finally {
        //        if (c != null) {
        //            try {
        //                c.close();
        //            } catch (SQLException ex) {
        //                Logger.getLogger(DialogPenjualan.class.getName()).log(Level.SEVERE, null, ex);
        //            }
        //        }
        //    }
    }//GEN-LAST:event_txtSalesActionPerformed

    private void txtSalesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSalesKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == 40) {
            jScrollPane5.setVisible(true);
            jTable5.requestFocus();
            jTable5.getSelectionModel().setSelectionInterval(0, 0);
        }
        if (evt.getKeyCode() == 27) {
            jScrollPane5.setVisible(false);
        }
    }//GEN-LAST:event_txtSalesKeyPressed

    private void txtKodeDOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtKodeDOActionPerformed
        //Connection c = null;
        if (!txtKodeDO.getText().equals("0")) {
            try {
                // TODO add your handling code here:
                // c = koneksi.getKoneksiJ();
                pil = "1";
                jScrollPane5.setVisible(true);
                JDBCAdapter ja = new JDBCAdapter(c);
                ja.executeQuery("select d.ID,d.KODEDO,s.NAMA "
                        + "from DO d, PELANGGAN s "
                        + "where d.KODEPELANGGAN=s.KODEPELANGGAN "
                        + "AND d.STATUS='A' AND d.STATUSAKTIF='1' AND (d.KODEDO like '" + txtKodeDO.getText() + "%') and (s.KODEPELANGGAN='" + kodepelanggan.getText() + "')");
                jScrollPane5.getViewport().remove(jTable5);
                jTable5 = new JTable(ja);
                jTable5.addKeyListener(new java.awt.event.KeyAdapter() {
                    @Override
                    public void keyPressed(java.awt.event.KeyEvent evt) {
                        jTable5KeyPressed(evt);
                    }
                });
                jTable5.setFont(new Font("Tahoma", Font.BOLD, 12));
                jScrollPane5.getViewport().add(jTable5);
                jScrollPane5.repaint();
            } catch (Exception ex) {
                Logger.getLogger(DialogPenjualan.class.getName()).log(Level.SEVERE, null, ex);
            }
            //        finally {
            //            if (c != null) {
            //                try {
            //                    c.close();
            //                } catch (SQLException ex) {
            //                    Logger.getLogger(DialogPenjualan.class.getName()).log(Level.SEVERE, null, ex);
            //                }
            //            }
            //        }
        } else {
            kodebarang.requestFocus();
        }
    }//GEN-LAST:event_txtKodeDOActionPerformed

    private void txtKodeDOKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKodeDOKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == 40) {
            jScrollPane5.setVisible(true);
            jTable5.requestFocus();
            jTable5.getSelectionModel().setSelectionInterval(0, 0);
        }
        if (evt.getKeyCode() == 27) {
            jScrollPane5.setVisible(false);
        }
    }//GEN-LAST:event_txtKodeDOKeyPressed

    private void jTable5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable5KeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == 10) {
            try {
                if (pil.equals("0")) {
                    txtSales.setText(jTable5.getValueAt(jTable5.getSelectedRow(), 0).toString());
                    txtNamaSales.setText(jTable5.getValueAt(jTable5.getSelectedRow(), 1).toString());
                    jScrollPane5.setVisible(false);
                    cbStatusDO.requestFocus();
                } else {
                    txtKodeDO.setText(jTable5.getValueAt(jTable5.getSelectedRow(), 0).toString());
                    txtNamaDo.setText(jTable5.getValueAt(jTable5.getSelectedRow(), 1).toString());
                    jScrollPane5.setVisible(false);
                    kodebarang.requestFocus();
                }

            } catch (Exception ex) {
            }
        }
    }//GEN-LAST:event_jTable5KeyPressed

    private void BtnGenNumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnGenNumActionPerformed
        try {
            // TODO add your handling code here:
            //Connection c = koneksi.getKoneksiJ();
            setFaktur(c);
            //c.close();
        } catch (SQLException ex) {
            Logger.getLogger(DialogPenjualan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_BtnGenNumActionPerformed

    private void tbnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbnKeluarActionPerformed
        int x = JOptionPane.showConfirmDialog(this, "Yakin Akan Keluar ?", "", JOptionPane.YES_NO_OPTION);
        if (x == 0) {
            dispose();
        } else {
            kodepelanggan.requestFocus();
        }
    }//GEN-LAST:event_tbnKeluarActionPerformed

    private void btnNewPlgnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewPlgnActionPerformed
        // TODO add your handling code here:
        FormPelangganMini p = new FormPelangganMini();
        p.toFront();
        JavarieSoftView.panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_btnNewPlgnActionPerformed

    private void cbPPNKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbPPNKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btninsert.requestFocus();
        }
    }//GEN-LAST:event_cbPPNKeyPressed

    private void cbStatusDOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbStatusDOActionPerformed
        // TODO add your handling code here:
        if (cbStatusDO.isSelected()) {
            txtKodeDO.setEditable(true);
            txtKodeDO.setText("");
            txtKodeDO.requestFocus();
        } else {
            txtKodeDO.setText("0");
            txtKodeDO.setEditable(false);
        }
    }//GEN-LAST:event_cbStatusDOActionPerformed

    private void cbStatusDOKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbStatusDOKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            kodebarang.requestFocus();
        }
    }//GEN-LAST:event_cbStatusDOKeyPressed

    private void txtkodeReturActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtkodeReturActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtkodeReturActionPerformed

    private void kodepelanggan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kodepelanggan1ActionPerformed
        //Connection c = null;
        try {
            // TODO add your handling code here:
            //c = koneksi.getKoneksiJ();
            pilPelanggan = "1";
            setting();
            jScrollPane3.setVisible(true);
            JDBCAdapter ja = new JDBCAdapter(c);
            ja.executeQuery("select KODEPELANGGAN,NAMA from PELANGGAN where KODEPELANGGAN like '" + kodepelanggan.getText() + "%' or lower(NAMA) like '" + kodepelanggan.getText().toLowerCase() + "%'");
            jScrollPane3.getViewport().remove(jTable3);
            jTable3 = new JTable(ja);
            jTable3.addKeyListener(new java.awt.event.KeyAdapter() {
                @Override
                public void keyPressed(java.awt.event.KeyEvent evt) {
                    jTable3KeyPressed(evt);
                }
            });
            jTable3.setFont(new Font("Tahoma", Font.BOLD, 12));
            jScrollPane3.getViewport().add(jTable3);
            jScrollPane3.repaint();
        } catch (Exception ex) {
            Logger.getLogger(DialogPenjualan.class.getName()).log(Level.SEVERE, null, ex);
        }
        //    finally {
        //        if (c != null) {
        //            try {
        //                c.close();
        //            } catch (SQLException ex) {
        //                Logger.getLogger(DialogPenjualan.class.getName()).log(Level.SEVERE, null, ex);
        //            }
        //        }
        //    }
    }//GEN-LAST:event_kodepelanggan1ActionPerformed

    private void kodepelanggan1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kodepelanggan1KeyPressed
        // TODO add your handling code here:
        System.out.print(evt.getKeyCode());
        if (evt.getKeyCode() == 40) {
            jScrollPane3.setVisible(true);
            jTable3.requestFocus();
            jTable3.getSelectionModel().setSelectionInterval(0, 0);
        }
        if (evt.getKeyCode() == 27) {
            jScrollPane3.setVisible(false);
        }
    }//GEN-LAST:event_kodepelanggan1KeyPressed

    private void btnKeluar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKeluar1ActionPerformed
        dispose();
    }//GEN-LAST:event_btnKeluar1ActionPerformed

    private void jTabbedPane1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jTabbedPane1StateChanged
        // Connection c = null;
        try {
            //TODO add your handling code here:
            // c = koneksi.getKoneksiJ();
            if (jTabbedPane1.getSelectedIndex() == 0) {
                kodepelanggan.requestFocus();
            } else if (jTabbedPane1.getSelectedIndex() == 1) {
                setRetur(c);
                kodepelanggan1.requestFocus();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DialogPenjualan.class.getName()).log(Level.SEVERE, null, ex);
        }
        //    finally {
        //        if (c != null) {
        //            try {
        //                c.close();
        //            } catch (SQLException ex) {
        //                Logger.getLogger(DialogPenjualan.class.getName()).log(Level.SEVERE, null, ex);
        //            }
        //        }
        //    }
    }//GEN-LAST:event_jTabbedPane1StateChanged

    private void kodebarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kodebarangActionPerformed
        //Connection c = null;
        try {
            // TODO add your handling code here:
            //c = koneksi.getKoneksiJ();
            jScrollPane2.setVisible(true);
            JDBCAdapter ja = new JDBCAdapter(c);
            String sql = "";
            if (txtKodeDO.getText().equals("0")) {
                sql = "select BARANG.KODEBARANG,BARANG.NAMABARANG,"
                        + "bsb.KODEBATCH , EXPIRE,"
                        + "bs.HARGAJUAL as `Jual`,"
                        + "CASEWHEN(KODEBATCH is null,bs.STOK,bsb.STOK) as STOK, "
                        + "JENISBARANG.JENIS "
                        + "from BARANG,JENISBARANG,KATEGORI "
                        + "inner join BARANGSTOK bs on bs.KODEBARANG=BARANG.KODEBARANG "
                        + "left join BARANGSTOKBATCH bsb on bs.ID=bsb.IDBARANGSTOK "
                        + "where BARANG.IDJENIS=JENISBARANG.ID "
                        + "AND BARANG.IDKATEGORI=KATEGORI.IDKATEGORI AND BARANG.STATUS='0' "
                        + "AND (BARANG.KODEBARANG like '" + kodebarang.getText() + "%' "
                        + "OR lower(BARANG.NAMABARANG) like '%" + kodebarang.getText().toLowerCase() + "%' "
                        + "OR bsb.KODEBATCH like '%" + kodebarang.getText() + "%')";
            } else {
                sql = "Select DR.KODEBARANG,BR.NAMABARANG, DR.KODEBATCH, DR.EXPIRE, \n"
                        + "0 as HARGAJUAL, "
                        + "     DR.JUMLAHKECIL -\n"
                        + "IFNULL((SELECT\n"
                        + "     RETURDORINCI.\"JUMLAHKECIL\" AS RETURDORINCI_JUMLAHKECIL\n"
                        + "FROM\n"
                        + "     \"PUBLIC\".\"RETURDO\" RETURDO INNER JOIN \"PUBLIC\".\"RETURDORINCI\" RETURDORINCI ON RETURDO.\"ID\" = RETURDORINCI.\"IDRETURDO\"\n"
                        + "     where RETURDO.\"IDDO\"=DO.ID and DR.KODEBARANG = RETURDORINCI.\"KODEBARANG\" and RETURDORINCI.\"KODEBATCH\"= DR.KODEBATCH),0) "
                        + "- IFNULL((select RP.JUMLAHKECIL from RINCIPENJUALAN RP where RP.KODEBARANG = DR.KODEBARANG AND RP.IDDO=DO.ID),0) as STOK"
                        + "      , JB.JENIS \n"
                        + "FROM DO inner join DORINCI DR on DO.ID = DR.IDDO\n"
                        + "inner join BARANG BR on BR.KODEBARANG=DR.KODEBARANG \n"
                        + "inner join JENISBARANG JB on BR.IDJENIS = JB.ID "
                        + "where DO.ID = '" + txtKodeDO.getText() + "' and KODEPELANGGAN='" + kodepelanggan.getText() + "'";
            }
//            System.out.println(sql);
            ja.executeQuery(sql);
            jScrollPane2.getViewport().remove(jTable2);
            jTable2 = new JTable(ja);
            jTable2.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
            TableColumn col = jTable2.getColumnModel().getColumn(0);
            col.setPreferredWidth(100);
            col = jTable2.getColumnModel().getColumn(1);
            col.setPreferredWidth(300);
            jTable2.addKeyListener(new java.awt.event.KeyAdapter() {
                @Override
                public void keyPressed(java.awt.event.KeyEvent evt) {
                    jTable2KeyPressed(evt);
                }
            });
            jTable2.setFont(new Font("Tahoma", Font.BOLD, 12));
            jScrollPane2.getViewport().add(jTable2);
            jScrollPane2.repaint();
        } catch (Exception ex) {
            Logger.getLogger(DialogPenjualan.class.getName()).log(Level.SEVERE, null, ex);
        }
        //    finally {
        //        if (c != null) {
        //            try {
        //                c.close();
        //            } catch (SQLException ex) {
        //                Logger.getLogger(DialogPenjualan.class.getName()).log(Level.SEVERE, null, ex);
        //            }
        //        }
        //    }
    }//GEN-LAST:event_kodebarangActionPerformed

    private void kodebarangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kodebarangKeyPressed
        // TODO add your handling code here:
        System.out.print(evt.getKeyCode());
        if (evt.getKeyCode() == 40) {
            jScrollPane2.setVisible(true);
            jTable2.requestFocus();
            jTable2.getSelectionModel().setSelectionInterval(0, 0);
        }
        if (evt.getKeyCode() == 27) {
            jScrollPane2.setVisible(false);
        }
    }//GEN-LAST:event_kodebarangKeyPressed

    private void jumlahCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jumlahCaretUpdate
        // TODO add your handling code here:
        //        Connection c = null;
        //        try {
        //            c = koneksi.getKoneksiJ();
        //            Barangstok bs = BarangstokDao.getDetailKodeBarang(c, kodebarang.getText());
        //            if (!jumlah.getText().equals("")) {
        //                if (sisaBarang < Integer.parseInt(jumlah.getText())) {
        //                    JOptionPane.showMessageDialog(null, "Stok Tinggal " + bs.getSTOK());
        //                    jumlah.requestFocus();
        //                }
        //            }
        //            bs = null;
        //        } catch (Exception ex) {
        //            JOptionPane.showMessageDialog(null, ex.toString());
        //        } finally {
        //            if (c != null) {
        //                try {
        //                    c.close();
        //                } catch (SQLException ex) {
        //                    Logger.getLogger(DialogPenjualan.class.getName()).log(Level.SEVERE, null, ex);
        //                }
        //            }
        //        }
    }//GEN-LAST:event_jumlahCaretUpdate

    private void jumlahFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jumlahFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jumlahFocusLost

    private void jumlahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jumlahKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == 10) {
            //        if (jumlah.getText().equals("")) {
            //            //JOptionPane.showMessageDialog(this, "Jumlah Tidak Boleh Kosong");
            //            jumlah.setText("1");
            diskonitem.requestFocus();
            //        } else {
            //diskonitem.requestFocus();
            //        }
        }
    }//GEN-LAST:event_jumlahKeyPressed

    private void hargaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_hargaFocusLost
        // TODO add your handling code here:
        //        try {
        //            Connection c = koneksi.getKoneksiJ();
        //            Barangstok b = new Barangstok();
        //            barang br = new barangDao().getDetails(c, kodebarang.getText());
        //            b = BarangstokDao.getDetailKodeBarang(c, kodebarang.getText());
        //
        //            if ((b.getCOGS() * br.getJumlahSatuan(cboSatuan.getSelectedItem().toString())) > Double.parseDouble(harga.getText())) {
        //                JOptionPane.showMessageDialog(null, "Harga Dibawah Modal");
        //                harga.requestFocus();
        //            }
        //
        //            if (CboTipeHarga.getSelectedIndex() == 1) {
        //                if (Double.parseDouble(harga.getText()) < hrgjual) {
        //                    JOptionPane.showMessageDialog(null, "Harga Tidak Boleh Turun..");
        //                    harga.requestFocus();
        //                } else {
        //                    //harga.setText("0");
        //                    jumlah.requestFocus();
        //
        //                }
        //            }
        //            b = null;
        //            c.close();
        //        } catch (Exception ex) {
        //            JOptionPane.showMessageDialog(null, ex.toString());
        //        }
    }//GEN-LAST:event_hargaFocusLost

    private void hargaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_hargaKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == 10) {
            //        if(CboTipeHarga.getSelectedIndex()==1){
            //            if(Double.parseDouble(harga.getText())<hrgjual){
            //                JOptionPane.showMessageDialog(null, "Harga Tidak Boleh Turun..");
            //                harga.requestFocus();
            //            }else{
            //                jumlah.requestFocus();
            //            }
            //        }else{
            jumlah.requestFocus();
            //        }

        }
    }//GEN-LAST:event_hargaKeyPressed

    private void btninsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btninsertActionPerformed
        //Connection c = null;
        try {
            //c = koneksi.getKoneksiJ();
            boolean cekbarang = false;
            double ppn = 0;
            barang b = new barangDao().getDetails(c, kodebarang.getText());
            Barangstok bs = BarangstokDao.getDetailKodeBarang(c, b.getKODEBARANG());
            boolean hmodal = false;
            double modal = bs.getCOGS() * b.getJumlahSatuan(cboSatuan.getSelectedItem().toString());
            double hdisk = getTextDouble(harga) - ((Double.parseDouble(diskonitem.getValue().toString()) / 100) * getTextDouble(harga));
//            if (modal > (getTextDouble(harga) - (getDiskon(Integer.parseInt(jumlah.getText()), getTextDouble(harga))))) {
//                hmodal = true;
//            }
//            if (cbBonus.isSelected()) {
//                hmodal = false;
//            }
            
            if(cbPPN.isSelected()){
                cbPPN.setEnabled(false);
            }else{
                cbPPN.setEnabled(false);
            }
            if (modal > hdisk) {
                hmodal = true;
            }
            if (cbBonus.isSelected()) {
                hmodal = false;
            }
            if (jTabbedPane1.getSelectedIndex() == 0) {
//                if (hmodal) {
//                    JOptionPane.showMessageDialog(null, "Harga Jual Dibawah Harga Modal");
//                    harga.requestFocus();
//                if (hmodal) {
//                    JOptionPane.showMessageDialog(null, "Harga Jual Dibawah Harga Modal");
//                    harga.requestFocus();
//                }else 
                if (modal <= 0) {
                    JOptionPane.showMessageDialog(null, "Harga Modal Masih Nol (Tidak Benar) Harap Update Harga Modal");
                    harga.requestFocus();
                } else if (barangDao.cekKodeBarang(c, kodebarang.getText()) != true) {
                    JOptionPane.showMessageDialog(null, "Produk Ini Tidak Ada..!!");
                    kosongBarang();
                    kodebarang.requestFocus();
                } else if (kodebarang.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Data Produk Belum Diisi..!!");
                    kosongBarang();
                    kodebarang.requestFocus();
                } else if (Integer.parseInt(jumlah.getText()) <= 0) {
                    JOptionPane.showMessageDialog(null, "Jumlah Tidak Boleh Minus");
                    kosongBarang();
                    kodebarang.requestFocus();
                } else if (bs.getSTOK() < Integer.parseInt(jumlah.getText())) {
                    JOptionPane.showMessageDialog(null, "Jumlah Kuantiti Berlebih");
                    kosongBarang();
                    kodebarang.requestFocus();
                } else {
                    if (hmodal) {
                        JOptionPane.showMessageDialog(null, "Harga Jual Dibawah Harga Modal");
                        harga.requestFocus();
                    }
                    double tot = (Integer.parseInt(jumlah.getText()) * Double.parseDouble(harga.getText()));
                    //double tdiskonD = Double.parseDouble(diskonitem.getValue().toString()) / 100 * tot; Persen
                    double tdiskonD = getDiskon(Integer.parseInt(jumlah.getText()), Double.parseDouble(harga.getText()));
                    //double tppnD = (Double.parseDouble(txtPPN.getValue().toString()) / 100) * (tot - tdiskonD); Lama
                    double tppnD = getPPN(tot - tdiskonD);
                    double tdiskonP = getDiskonPersen(Integer.parseInt(jumlah.getText()), Double.parseDouble(harga.getText()));
                    //jScrollPane1.getViewport().remove(jTable1);
                    //DefaultTableModel model = new DefaultTableModel(col, 0);
                    //nomor=0;
                    Vector data = new Vector();
                    //                if (model.getRowCount() != 0) {
                    //                    for (int i = 0; i < jTable1.getRowCount(); i++) {
                    //                        if (kodebarang.getText().equals(jTable1.getValueAt(i, 1).toString()) && txtBatch.getText().equals(jTable1.getValueAt(i, 3).toString())) {
                    //                            cekbarang = true;
                    //                            ///break;
                    //                        }
                    //                    }
                    //                }

                    //                nomor = jTable1.getRowCount() + 1;
                    if (cekbarang) {
                        JOptionPane.showMessageDialog(null, "Produk Ini Sudah Dientrikan..");
                        kosongBarang();
                        kodebarang.requestFocus();
                    } else {

                        if (posisi != -1) {
                            data.add(0, posisi + 1);
                            model.insertRow(posisi, data);
                        } else {
                            nomor = jTable1.getRowCount() + 1;
                            data.add(0, nomor);
                            model.addRow(data);
                        }
                        data.add(1, kodebarang.getText());
                        data.add(2, namabarang.getText());
                        if (txtBatch.getText().length() > 0) {
                            data.add(3, txtBatch.getText());
                            data.add(4, tglExpire.getText());
                        } else {
                            data.add(3, "");
                            data.add(4, "");
                        }
                        data.add(5, jumlah.getText());
                        data.add(6, cboSatuan.getSelectedItem());
                        data.add(7, harga.getText());
                        data.add(8, df0.format(tdiskonD));
                        data.add(9, df0.format(tppnD));
                        data.add(10, df0.format((tot - tdiskonD + tppnD)));
                        data.add(11, ((txtKodeDO.getText().length() != 0) ? (Integer.parseInt(txtKodeDO.getText())) : 0));

                        data.add(12, bs.getCOGS());
                        data.add(13, b.getJumlah(Integer.parseInt(jumlah.getText()), cboSatuan.getSelectedItem().toString()));
                        data.add(14, tdiskonP);
                        data.add(15, (cbBonus.isSelected() ? "Bonus" : ""));

                        jTable1.setRowSelectionInterval(jTable1.getRowCount() - 1, jTable1.getRowCount() - 1);
                        jTable1.changeSelection(jTable1.getRowCount() - 1, 0, false, false);
                    }
                    //Insert first position

                    total = 0;
                    hpp = 0;
                    DiskonItem = 0;
                    PPNItem = 0;
                    reloadData(c);
                    posisi = -1;
                    kosongBarang();
                    expire = new String[]{};
                    cboStatDiskonItem.setSelectedIndex(0);
                    //CboTipeHarga.setSelectedIndex(0);
                }
            } else {

                if (barangDao.cekKodeBarang(c, kodebarang.getText()) != true) {
                    JOptionPane.showMessageDialog(null, "Produk Ini Tidak Ada..!!");
                    kosongBarang();
                    kodebarang.requestFocus();
                } else if (kodebarang.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Data Produk Belum Diisi..!!");
                    kosongBarang();
                    kodebarang.requestFocus();
                } else {
                    double tot = (Integer.parseInt(jumlah.getText()) * Double.parseDouble(harga.getText()));
                    //double tdiskonD = Double.parseDouble(diskonitem.getValue().toString()) / 100 * tot; Persen
                    double tdiskonD = getDiskon(Integer.parseInt(jumlah.getText()), Double.parseDouble(harga.getText()));
                    //double tppnD = (Double.parseDouble(txtPPN.getValue().toString()) / 100) * (tot - tdiskonD); Lama
                    double tppnD = getPPN(tot - tdiskonD);
                    double tdiskonP = getDiskonPersen(Integer.parseInt(jumlah.getText()), Double.parseDouble(harga.getText()));

                    //jScrollPane1.getViewport().remove(jTable1);
                    //DefaultTableModel model = new DefaultTableModel(col, 0);
                    Vector data = new Vector();
                    //                if (model.getRowCount() != 0) {
                    //                    for (int i = 0; i < jTable1.getRowCount(); i++) {
                    //                        if (kodebarang.getText().equals(jTable1.getValueAt(i, 0).toString())) {
                    //                            cekbarang = true;
                    //
                    //                            ///break;
                    //                        }
                    //                    }
                    //                }

                    //nomor = jTable1.getRowCount() + 1;
                    if (cekbarang) {
                        JOptionPane.showMessageDialog(null, "Produk Ini Sudah Dientrikan..");
                        kosongBarang();
                        kodebarang.requestFocus();
                    } else {
                        if (posisi != -1) {
                            data.add(0, posisi + 1);
                            model.insertRow(posisi, data);
                        } else {
                            nomor = jTable1.getRowCount() + 1;
                            data.add(0, nomor);
                            model.addRow(data);
                        }
                        data.add(1, kodebarang.getText());
                        data.add(2, namabarang.getText());
                        if (txtBatch.getText().length() > 0) {
                            data.add(3, txtBatch.getText());
                            data.add(4, tglExpire.getText());
                        } else {
                            data.add(3, "");
                            data.add(4, "");
                        }
                        data.add(5, jumlah.getText());
                        data.add(6, cboSatuan.getSelectedItem());
                        data.add(7, harga.getText());
                        data.add(8, df0.format(tdiskonD));
                        data.add(9, df0.format(tppnD));
                        data.add(10, df0.format((tot - tdiskonD + tppnD)));
                        data.add(11, ((txtKodeDO.getText().length() != 0) ? (Integer.parseInt(txtKodeDO.getText())) : 0));

                        data.add(12, bs.getCOGS());
                        data.add(13, b.getJumlah(Integer.parseInt(jumlah.getText()), cboSatuan.getSelectedItem().toString()));
                        data.add(14, tdiskonP);
                        data.add(15, (cbBonus.isSelected() ? "Bonus" : ""));

                        jTable1.setRowSelectionInterval(jTable1.getRowCount() - 1, jTable1.getRowCount() - 1);
                        jTable1.changeSelection(jTable1.getRowCount() - 1, 0, false, false);
                    }
                    //Insert first position

                    total = 0;
                    hpp = 0;
                    DiskonItem = 0;
                    PPNItem = 0;
                    reloadData(c);
                    kosongBarang();
                    CboTipeHarga.setSelectedIndex(0);
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.toString());
        }
        //    finally {
        //        if (c != null) {
        //            try {
        //                c.close();
        //            } catch (SQLException ex) {
        //                Logger.getLogger(DialogPenjualan.class.getName()).log(Level.SEVERE, null, ex);
        //            }
        //        }
        //    }
    }//GEN-LAST:event_btninsertActionPerformed

    private void btndeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndeleteActionPerformed
        try {
            // TODO add your handling codJOptionPane.showMessageDialog(null,jTable1.getSelectedRow() );e here:
            //model.removeRow(jTable1.getSelectedRow());
            //reloadData();
            kosongBarang();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.toString());
        }
    }//GEN-LAST:event_btndeleteActionPerformed

    private void cboStatDiskonItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboStatDiskonItemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboStatDiskonItemActionPerformed

    private void cboStatDiskonItemKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cboStatDiskonItemKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            cbPPN.requestFocus();
        }
    }//GEN-LAST:event_cboStatDiskonItemKeyPressed

    private void diskonitemFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_diskonitemFocusLost
        // TODO add your handling code here:
        //    txtPPN.requestFocus();
        //    diskonitem.setValue(getDiskonitem());
        //    txtPPN.setValue(getPPNItem());

        if (diskonitem.getText().equals("")) {
            diskonitem.setText("0");
            //JOptionPane.showMessageDialog(null, "Diskon Tidak Boleh Kosong");
            //diskonitem.requestFocus();
            //    } else if(diskonitem.getValue().equals("0")){
            //        JOptionPane.showMessageDialog(null, "Diskon Tidak Boleh Kosong");
            //        diskonitem.requestFocus();
            //    }else{
        }
    }//GEN-LAST:event_diskonitemFocusLost

    private void diskonitemKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_diskonitemKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == 10) {
            cbBonus.requestFocus();
        }
    }//GEN-LAST:event_diskonitemKeyPressed

    private void cboSatuanItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboSatuanItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_cboSatuanItemStateChanged

    private void cboSatuanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cboSatuanKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            try {
                //Connection c = koneksi.getKoneksiJ();
                barang b = new barangDao().getDetails(c, kodebarang.getText());
                double hk = Fungsi.getHargaKini(c, kodepelanggan.getText(), b.getKODEBARANG(), cboSatuan.getSelectedItem().toString());
                harga.setText("" + hk);
                //harga.setText("" + b.getHarga(cboSatuan.getSelectedItem().toString()));
                harga.requestFocus();
            } catch (SQLException ex) {
                Logger.getLogger(DialogPenjualan.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_cboSatuanKeyPressed

    private void tglExpireOnCommit(datechooser.events.CommitEvent evt) {//GEN-FIRST:event_tglExpireOnCommit
        // TODO add your handling code here:
    }//GEN-LAST:event_tglExpireOnCommit

    private void cbBonusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbBonusActionPerformed
        // TODO add your handling code here:
        harga.setText("0");
        diskonitem.setText("0");
    }//GEN-LAST:event_cbBonusActionPerformed

    private void cbBonusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbBonusKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btninsert.requestFocus();
        }
    }//GEN-LAST:event_cbBonusKeyPressed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            try {
                //Connection c = koneksi.getKoneksiJ();
                JTable target = (JTable) evt.getSource();
                int row = target.getSelectedRow();

                barang bc = new barangDao().getDetails(c, jTable1.getValueAt(jTable1.getSelectedRow(), 1).toString());
                //                Barangstok bs = BarangstokDao.getDetailKodeBarang(c, bc.getKODEBARANG());
                try {
                    cboSatuan.removeAllItems();
                } catch (Exception e) {
                }
                for (Object a : bc.getItemSatuan()) {
                    if (!a.equals("-")) {
                        cboSatuan.addItem(a);
                    }
                }

                kodebarang.setText(jTable1.getValueAt(jTable1.getSelectedRow(), 1).toString());
                namabarang.setText(jTable1.getValueAt(jTable1.getSelectedRow(), 2).toString());
                harga.setText(jTable1.getValueAt(jTable1.getSelectedRow(), 7).toString());
                jumlah.setText(jTable1.getValueAt(jTable1.getSelectedRow(), 5).toString());
                cboStatDiskonItem.setSelectedIndex(0);
                Object a = Double.parseDouble(jTable1.getValueAt(jTable1.getSelectedRow(), 14).toString());
                diskonitem.setValue(a);
                cboSatuan.setSelectedItem(jTable1.getValueAt(jTable1.getSelectedRow(), 6));
                cogs = Double.parseDouble(jTable1.getValueAt(jTable1.getSelectedRow(), 12).toString());
                if (jTable1.getValueAt(jTable1.getSelectedRow(), 3) != "") {
                    txtBatch.setText(jTable1.getValueAt(jTable1.getSelectedRow(), 3).toString());
                    Calendar cld = Calendar.getInstance();
                    cld.setTime(d.parse(jTable1.getValueAt(jTable1.getSelectedRow(), 4).toString()));
                    tglExpire.setSelectedDate(cld);
                }
                if (Double.parseDouble(jTable1.getValueAt(jTable1.getSelectedRow(), 9).toString()) != 0) {
                    cbPPN.setSelected(true);
                } else {
                    cbPPN.setSelected(false);
                }

                posisi = jTable1.getSelectedRow();
                //        notemp = Integer.parseInt(jTable1.getValueAt(jTable1.getSelectedRow(), 8).toString());
                //        tkod = jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString();
                model.removeRow(jTable1.getSelectedRow());
                reloadData(c);
                //c.close();
            } catch (SQLException ex) {
                Logger.getLogger(DialogPenjualan.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger(DialogPenjualan.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void hasilDiskonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_hasilDiskonKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == 10) {
            try {
                //thasilDiskon = Double.parseDouble(hasilDiskon.getText());
                hasilBayar.requestFocus();
                // Connection c = koneksi.getKoneksiJ();
                hasilAkhir(c);
                // c.close();
            } //hasilDiskon.setText(com.erv.function.Util.toMoney(thasilDiskon));
            catch (SQLException ex) {
                Logger.getLogger(DialogPenjualan.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_hasilDiskonKeyPressed

    private void hasilBayarCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_hasilBayarCaretUpdate
        // TODO add your handling code here:
        //cekpembayaran();
        try {
            if (hasilBayar.getText().length() > 0) {
                sisa = ttotalbayar - Double.parseDouble(hasilBayar.getText());
                hasilSisa.setText(com.erv.function.Util.toMoney(sisa));
            } else {
                hasilSisa.setText("0");
            }
        } catch (NumberFormatException e) {
            //JOptionPane.showMessageDialog(null, "Kesalahan..!"+e);
        }

        //    } else if(ttotalbayar!=0){
        //        if(ttotalbayar < getTextDouble(hasilBayar)){
        //            JOptionPane.showMessageDialog(null, "Jumlah Bayar Salah.. !");
        //            kodepelanggan.requestFocus();
        //        }else{
        //            sisa = ttotalbayar - Double.parseDouble(hasilBayar.getText());
        //            hasilSisa.setText(com.erv.function.Util.toMoney(sisa));
        //        }
        //    }
    }//GEN-LAST:event_hasilBayarCaretUpdate

    private void hasilBayarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_hasilBayarFocusGained
        // TODO add your handling code here:
        //cekpembayaran();
        //        if (statusbayar.getSelectedIndex() == 0) {
        //            hasilBayar.setText("" + ttotalbayar);
        //            btnSimpanHasil.requestFocus();
        //        } else if (statusbayar.getSelectedIndex() == 2) {
        //            hasilBayar.setText("" + ttotalbayar);
        //            btnSimpanHasil.requestFocus();
        //        }
    }//GEN-LAST:event_hasilBayarFocusGained

    private void hasilBayarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_hasilBayarKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == 10) {
            //        if (sisa > 0) {
            //            statusbayar.setSelectedIndex(1);
            //            settingTgl();
            //        } else {
            //            statusbayar.setSelectedIndex(0);
            //        }
            btnSimpanHasil.requestFocus();
        }
    }//GEN-LAST:event_hasilBayarKeyPressed

    private void btnSimpanHasilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanHasilActionPerformed
        // Connection c = null;
        try {
            // c = koneksi.getKoneksiJ();
            c.createStatement().execute("set autocommit false");
            sisa = ttotalbayar - Double.parseDouble(hasilBayar.getText());
            hasilSisa.setText(com.erv.function.Util.toMoney(sisa));
            tdp = Float.parseFloat(hasilBayar.getText());
            hasilAkhir(c);
            int x = JOptionPane.showConfirmDialog(this, "Apakah Data akan Disimpan?", "", JOptionPane.YES_NO_OPTION);
            if (x == 0) {
                if (jTabbedPane1.getSelectedIndex() == 0) {
                    if (!penjualanDao.cekFaktur(c, nofaktur.getText())) {
                        if ((kodepelanggan.getText().equals("")) || (txtSales.getText().equals("")) || (txtKodeDO.getText().equals(""))) {
                            JOptionPane.showMessageDialog(null, "Data Belum Lengkap.. !");
                            kodepelanggan.requestFocus();
                        } else if (sisa < 0) {
                            JOptionPane.showMessageDialog(null, "Jumlah Bayar Besar dari Total Belanja.. !");
                            hasilBayar.requestFocus();
                        } else if (ttotalbayar <= 0) {
                            JOptionPane.showMessageDialog(null, "Pengisian Data Belum Benar.. !");
                            kodepelanggan.requestFocus();
                        } else if (statusbayar.getSelectedItem().equals("-")) {
                            JOptionPane.showMessageDialog(null, "Cara Pembayaran Belum Di pilih..!");
                            kodepelanggan.requestFocus();
//                        } else if (!KontrolTanggalDao.cekHarian(c, tgl.getText())) {
//                            JOptionPane.showMessageDialog(null, "Transaksi Tidak Bisa Dilakukan Karena :\n"
//                                + "1.Transaksi Untuk Tanggal Ini Sudah Tutup atau\n"
//                                + "2.Transaksi Untuk Tanggal Ini Belum Dibuka");
                        } else {
                            String tgal[] = Util.split(tgl.getText(), "-");
                            String per = tgal[0] + "." + Integer.parseInt(tgal[1]);
                            if (cekperiodeAda(c, per)) {
                                if (cekperiode(c, per)) {
                                    aksilog = "InsertJual";
                                    prosesSimpan(c);
                                    prosesUpdateLog(c);
                                    selesai(c);
                                    //                                int a = JOptionPane.showConfirmDialog(null, "Cetak PPN ?", "Pesan", JOptionPane.YES_NO_OPTION);
                                    //                                if (a == 0) {
                                    cetakFaktur(c, "0");
                                    txtNofakturTemp.setText(nofak);
                                    //                                } else {
                                    //                                    cetakFaktur("1");
                                    //                                    txtNofakturTemp.setText(nofak);
                                    //                                }
                                    c.commit();
                                    JOptionPane.showMessageDialog(this, "Transaksi Penjualan Ok..!");
                                } else {
                                    JOptionPane.showMessageDialog(null, "Transaksi Untuk Periode Ini Sudah Di Tutup.. !");
                                    kodepelanggan.requestFocus();
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "Transaksi Untuk Periode Ini Belum Dibuka.. !");
                                kodepelanggan.requestFocus();
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "No Faktur Sudah Ada,Klik Tombol Generate Ulang No Faktur Lalu Simpan !");
                    }
                } else {
                    if ((kodepelanggan1.getText().equals(""))) {
                        JOptionPane.showMessageDialog(null, "Data Belum Lengkap.. !");
                        kodepelanggan1.requestFocus();
//                    } else if(!KontrolTanggalDao.cekHarian(c, tglRetur.getText())){
//                        JOptionPane.showMessageDialog(null, "Transaksi Tidak Bisa Dilakukan Karena :\n"
//                        + "1.Transaksi Untuk Tanggal Ini Sudah Tutup atau\n"
//                        + "2.Transaksi Untuk Tanggal Ini Belum Dibuka");
                    } else {
                        String tgal[] = Util.split(tglRetur.getText(), "-");
                        String per = tgal[0] + "." + Integer.parseInt(tgal[1]);
                        if (cekperiodeAda(c, per)) {
                            if (cekperiode(c, per)) {
                                aksilog = "InsertReturJual";
                                prosesSimpanRetur(c);
                                //insertJurnal(c, "RETUR");
                                //insertRinciJurnalRetur(c);
                                prosesUpdateLog(c);
                                selesai(c);
                                cetakRetur(c);
                                c.commit();
                                JOptionPane.showMessageDialog(this, "Transaksi Retur Penjualan Ok..!");
                            } else {
                                JOptionPane.showMessageDialog(null, "Transaksi Untuk Periode Ini Sudah Di Tutup.. !");
                                kodepelanggan.requestFocus();
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Transaksi Untuk Periode Ini Belum Dibuka.. !");
                            kodepelanggan.requestFocus();
                        }
                    }
                }
            } else {
                System.out.print("tidak");
            }
        } catch (Exception ex) {
            try {
                c.rollback();
                JOptionPane.showMessageDialog(this, "Rollback " + ex.getMessage());
            } catch (SQLException ex1) {
                Logger.getLogger(DialogPenjualan.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
//                if (c != null) {
            try {
                c.createStatement().execute("set autocommit true");
//                        c.close();
            } catch (SQLException ex) {
                Logger.getLogger(DialogPenjualan.class.getName()).log(Level.SEVERE, null, ex);
            }
//                }
        }
    }//GEN-LAST:event_btnSimpanHasilActionPerformed

    private void btnFakturActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFakturActionPerformed
        // TODO add your handling code here:
        if (txtNofakturTemp.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Isi No Faktur yang akan di Preview.. !");
            txtNofakturTemp.requestFocus();
        } else {
            try {
                //        int a = JOptionPane.showConfirmDialog(null, "Cetak PPN ?", "Pesan", JOptionPane.YES_NO_OPTION);
                //        if (a == 0) {
                //Connection c = koneksi.getKoneksiJ();
//                cetakFaktur1(c, "0");
                //c.close();
                //        } else {
                //            cetakFaktur1("1");
                //        }

                if (RadioPendek.isSelected()) {
                    viewFakturPendek(txtNofakturTemp.getText());
                } else if (RadioPanjang.isSelected()) {
                    viewFakturPanjang(txtNofakturTemp.getText());
                }
            } catch (Exception ex) {
                Logger.getLogger(DialogPenjualan.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btnFakturActionPerformed

    private void btnCetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCetakActionPerformed
        // TODO add your handling code here:

        if (txtNofakturTemp.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Isi No Faktur yang akan di Preview.. !");
            txtNofakturTemp.requestFocus();
        } else {
//            ClassPrintLama c1 = new ClassPrintLama();
//            try {
//                if (txtNofakturTemp.getText().equals("")) {
//                    JOptionPane.showMessageDialog(null, "Isi No Faktur yang akan di Preview.. !");
//                    txtNofakturTemp.requestFocus();
//                } else {
//                    //c1.cetak(c, nofaktur.getText());
//                    //c1.cetak(c, nofak);
//                    c1.cetakfaktur(txtNofakturTemp.getText());
//                    //c1.cetakPrinter("c:\\temp\\write.txt");
//                }

            try {
                if (txtNofakturTemp.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Isi No Faktur yang akan di Preview.. !");
                    txtNofakturTemp.requestFocus();
                } else {
                    if (RadioPendek.isSelected()) {
                        CetakFakturPendek(txtNofakturTemp.getText());
                    } else if (RadioPanjang.isSelected()) {
                        CetakFakturPanjang(txtNofakturTemp.getText());
                    }

                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.toString());
            }
        }
        //        HashMap parameter = new HashMap();
        //        JasperPrint jasperPrint = null;
        //        parameter.put("nofaktur", txtNofakturTemp.getText());
        //        try {
        //            URL url;
        //            InputStream in;
        //            if (OptPendek.isSelected()) {
        //                url = new URL(global.REPORT + "/LapPerFakturPendek.jasper");
        //                in = url.openStream();
        //                jasperPrint = JasperFillManager.fillReport(in, parameter, c);
        //            } else if (OptPanjang.isSelected()) {
        //                url = new URL(global.REPORT + "/LapPerFakturPanjang.jasper");
        //                in = url.openStream();
        //                jasperPrint = JasperFillManager.fillReport(in, parameter, c);
        //
        //            }
        //
        //            PrintService printService = PrintServiceLookup.lookupDefaultPrintService();
        //            if (printService == null) {
        //                //error message
        //                throw new PrinterException("No Printer Attached / Shared to the server");
        //            } else {
        //                JRExporter exporter = new JRPrintServiceExporter();
        //                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        //                exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, printService.getAttributes());
        //                exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
        //                exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.TRUE);
        //                exporter.exportReport();
        //            }
        //            //JasperViewer.viewReport(jasperPrint, false);
        //        } catch (Exception ex) {
        //            System.out.print(ex.toString());
        //            //Logger.getLogger(formlaporan.class.getName()).log(Level.SEVERE, null, ex);
        //        }
        //   }
        //    CetakFaktur c1 = new CetakFaktur();
        //    try {
        //        if (txtNofakturTemp.getText().equals("")) {
        //            JOptionPane.showMessageDialog(null, "Isi No Faktur yang akan di Preview.. !");
        //            txtNofakturTemp.requestFocus();
        //        } else {
        //            //c1.cetak(c, nofaktur.getText());
        //            //c1.cetak(c, nofak);
        //            c1.cetak(c, txtNofakturTemp.getText());
        //            c1.cetakPrinter("c:\\temp\\write.txt");
        //        }
        //    } catch (Exception ex) {
        //        JOptionPane.showMessageDialog(null, ex.toString());
        //    }
    }//GEN-LAST:event_btnCetakActionPerformed

    private void DiskonTambahCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_DiskonTambahCaretUpdate
        // TODO add your handling code here:
        if (DiskonTambah.getText().length() > 0) {
            try {
                //Connection c = koneksi.getKoneksiJ();
                reloadData(c);
                //c.close();
            } catch (Exception ex) {
                Logger.getLogger(DialogPembelian.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            //DiskonTambah.setText("0");
            //reloadData();
        }
    }//GEN-LAST:event_DiskonTambahCaretUpdate

    private void DiskonTambahFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_DiskonTambahFocusLost
        // TODO add your handling code here:
        if (getTextInteger(DiskonTambah) < 0) {
            JOptionPane.showMessageDialog(null, "Nilai Diskon Salah !");
            DiskonTambah.setText("0");
        } else {
            //reloadData();
            hasilBayar.requestFocus();
            //cekpembayaran();
        }
    }//GEN-LAST:event_DiskonTambahFocusLost

    private void DiskonTambahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiskonTambahKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == 10) {
            try {
                //Connection c = koneksi.getKoneksiJ();
                reloadData(c);
                hasilBayar.requestFocus();
                //c.close();
            } catch (Exception ex) {
                Logger.getLogger(DialogPenjualan.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_DiskonTambahKeyPressed

    private void jTable2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable2KeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == 10) {
            //Connection c = null;
            if (txtKodeDO.getText().equals("0")) {
                try {
                    //c = koneksi.getKoneksiJ();
                    barang b;
                    Barangstok bs;
                    b = dbbarang.getDetails(c, jTable2.getValueAt(jTable2.getSelectedRow(), 0).toString());
                    bs = BarangstokDao.getDetailKodeBarang(c, jTable2.getValueAt(jTable2.getSelectedRow(), 0).toString());
                    if (jTabbedPane1.getSelectedIndex() == 0) {
                        cboSatuan.removeAllItems();
                        for (Iterator<Object> it = b.getItemSatuan().iterator(); it.hasNext();) {
                            Object a = it.next();
                            if (!a.equals("-")) {
                                cboSatuan.addItem(a);
                            }
                        }

                        kodebarang.setText(b.getKODEBARANG());
                        namabarang.setText(b.getNAMABARANG());
                        if (jTable2.getValueAt(jTable2.getSelectedRow(), 2) != null) {
                            txtBatch.setText(jTable2.getValueAt(jTable2.getSelectedRow(), 2).toString());
                            Calendar cld = Calendar.getInstance();
                            cld.setTime(d.parse(jTable2.getValueAt(jTable2.getSelectedRow(), 3).toString()));
                            tglExpire.setSelectedDate(cld);
                        }

                        if (CboTipeHarga.getSelectedIndex() == 0) {
                            //harga.setText("" + b.getHarga(cboSatuan.getSelectedItem().toString()));
                            double hk = Fungsi.getHargaKini(c, kodepelanggan.getText(), b.getKODEBARANG(), cboSatuan.getSelectedItem().toString());
                            harga.setText("" + hk);
                            harga.setEnabled(true);
                            double disItem = Fungsi.getDiskonItemJualKini(c, kodepelanggan.getText(), b.getKODEBARANG(), cboSatuan.getSelectedItem().toString());
                            diskonitem.setText("" + disItem);
                            cboSatuan.requestFocus();
                        } else {
                            //hrgjual = b.getHarga(cboSatuan.getSelectedItem().toString());
                            //harga.setText("" + b.getHARGA());
                            double hk = Fungsi.getHargaKini(c, kodepelanggan.getText(), b.getKODEBARANG(), cboSatuan.getSelectedItem().toString());
                            harga.setText("" + hk);
                            harga.requestFocus();
                            //harga.setText("0");
                            harga.setEnabled(true);
                            double disItem = Fungsi.getDiskonItemJualKini(c, kodepelanggan.getText(), b.getKODEBARANG(), cboSatuan.getSelectedItem().toString());
                            diskonitem.setText("" + disItem);
                            cboSatuan.requestFocus();
                        }
                        //harga.setText("");
                        sisaBarang = Integer.parseInt(jTable2.getValueAt(jTable2.getSelectedRow(), 5).toString());
                        jScrollPane2.setVisible(false);
                    } else {
                        cboSatuan.removeAllItems();
                        for (Iterator<Object> it = b.getItemSatuan().iterator(); it.hasNext();) {
                            Object a = it.next();
                            if (!a.equals("-")) {
                                cboSatuan.addItem(a);
                            }
                        }

                        kodebarang.setText(b.getKODEBARANG());
                        namabarang.setText(b.getNAMABARANG());
                        if (jTable2.getValueAt(jTable2.getSelectedRow(), 2) != null) {
                            txtBatch.setText(jTable2.getValueAt(jTable2.getSelectedRow(), 2).toString());
                            Calendar cld = Calendar.getInstance();
                            cld.setTime(d.parse(jTable2.getValueAt(jTable2.getSelectedRow(), 3).toString()));
                            tglExpire.setSelectedDate(cld);
                        }

                        if (CboTipeHarga.getSelectedIndex() == 0) {
                            //harga.setText("" + b.getHarga(cboSatuan.getSelectedItem().toString()));
                            harga.setEnabled(true);
                            cboSatuan.requestFocus();
                        } else {
                            //hrgjual = b.getHarga(cboSatuan.getSelectedItem().toString());
                            //harga.setText("" + b.getHARGA());
                            harga.requestFocus();
                            //harga.setText("0");
                            harga.setEnabled(true);
                            cboSatuan.requestFocus();
                        }
                        //harga.setText("");
                        sisaBarang = Integer.parseInt(jTable2.getValueAt(jTable2.getSelectedRow(), 5).toString());
                        jScrollPane2.setVisible(false);
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex.toString());
                } catch (ParseException ex) {
                    Logger.getLogger(DialogPenjualan.class.getName()).log(Level.SEVERE, null, ex);
                }
                //            finally {
                //                if (c != null) {
                //                    try {
                //                        c.close();
                //                    } catch (SQLException ex) {
                //                        Logger.getLogger(DialogPenjualan.class.getName()).log(Level.SEVERE, null, ex);
                //                    }
                //                }
                //            }
            } else {
                try {
                    //c = koneksi.getKoneksiJ();
                    barang b = dbbarang.getDetails(c, jTable2.getValueAt(jTable2.getSelectedRow(), 0).toString());
                    kodebarang.setText(jTable2.getValueAt(jTable2.getSelectedRow(), 0).toString());
                    namabarang.setText(jTable2.getValueAt(jTable2.getSelectedRow(), 1).toString());
                    if (jTable2.getValueAt(jTable2.getSelectedRow(), 2) != null) {
                        txtBatch.setText(jTable2.getValueAt(jTable2.getSelectedRow(), 2).toString());
                        Calendar cld = Calendar.getInstance();
                        cld.setTime(d.parse(jTable2.getValueAt(jTable2.getSelectedRow(), 3).toString()));
                        tglExpire.setSelectedDate(cld);
                    }
                    cboSatuan.removeAllItems();
                    for (Iterator<Object> it = b.getItemSatuan().iterator(); it.hasNext();) {
                        Object a = it.next();
                        if (!a.equals("-")) {
                            cboSatuan.addItem(a);
                        }
                    }
                    int jumlahkecil = Integer.parseInt(jTable2.getValueAt(jTable2.getSelectedRow(), 5).toString());

                    int[] j = com.erv.fungsi.Fungsi.getKonversiSatuan(jumlahkecil, b.getJUMLAH1(), b.getJUMLAH2());
                    int hasil = 0;
                    int idx = 0;
                    for (int i = 0; i < 3; i++) {
                        int k = j[i];
                        if (k != 0) {
                            idx = i;
                            hasil++;
                        }
                    }
                    if (hasil == 1) {
                        if (idx == 2) {
                            cboSatuan.setSelectedItem(b.getSATUAN());
                            jumlah.setText("" + j[2]);
                        } else if (idx == 1) {
                            cboSatuan.setSelectedItem(b.getSATUAN1());
                            jumlah.setText("" + j[1]);
                        } else if (idx == 0) {
                            cboSatuan.setSelectedItem(b.getSATUAN2());
                            jumlah.setText("" + j[0]);
                        }
                    }
                    //harga.setText("" + b.getHarga(cboSatuan.getSelectedItem().toString()));
                    //jumlah.setText(jTable2.getValueAt(jTable2.getSelectedRow(), 5).toString());
                    diskonitem.setText("0");
                    jScrollPane2.setVisible(false);
                    harga.requestFocus();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage());
                } catch (ParseException ex) {
                    Logger.getLogger(DialogPenjualan.class.getName()).log(Level.SEVERE, null, ex);
                }
                //            finally {
                //                if (c != null) {
                //                    try {
                //                        c.close();
                //                    } catch (SQLException ex) {
                //                        Logger.getLogger(DialogPenjualan.class.getName()).log(Level.SEVERE, null, ex);
                //                    }
                //                }
                //            }
            }

        }
    }//GEN-LAST:event_jTable2KeyPressed

    private void RadioPendekActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RadioPendekActionPerformed
        // TODO add your handling code here:
        pilihanKertasPendek();
    }//GEN-LAST:event_RadioPendekActionPerformed

    private void RadioPanjangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RadioPanjangActionPerformed
        // TODO add your handling code here:
        pilihanKertasPanjang();
    }//GEN-LAST:event_RadioPanjangActionPerformed

    private void jTabbedPane1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTabbedPane1KeyPressed
        // TODO add your handling code here:
        if (jTabbedPane1.getSelectedIndex() == 0) {
            statusbayar.requestFocus();
        } else {
            tglRetur.requestFocus();
        }
    }//GEN-LAST:event_jTabbedPane1KeyPressed

    private void statusbayarItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_statusbayarItemStateChanged
        // TODO add your handling code here:
        try {
            cekpembayaran(c);
        } catch (SQLException ex) {
            Logger.getLogger(DialogPenjualan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_statusbayarItemStateChanged

    private void statusbayarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_statusbayarKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == 10) {
            kodepelanggan.requestFocus();
        }
    }//GEN-LAST:event_statusbayarKeyPressed

    private void cbPPNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbPPNActionPerformed
        try {
            // TODO add your handling code here:
            setFaktur(c);
            cbPPN.setEnabled(false);
            statusbayar.requestFocus();
        } catch (SQLException ex) {
            Logger.getLogger(DialogPenjualanInternal.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_cbPPNActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnGenNum;
    private javax.swing.JComboBox CboNamaBank;
    private javax.swing.JComboBox CboTipeHarga;
    private javax.swing.JTextField DiskonTambah;
    private javax.swing.JLabel LabelKodeDO;
    private javax.swing.JRadioButton RadioPanjang;
    private javax.swing.JRadioButton RadioPendek;
    private javax.swing.JButton btnBaru;
    private javax.swing.JButton btnCetak;
    private javax.swing.JButton btnFaktur;
    private javax.swing.JButton btnKeluar1;
    private javax.swing.JButton btnNewPlgn;
    private javax.swing.JButton btnSimpanHasil;
    private javax.swing.JButton btndelete;
    private javax.swing.JButton btninsert;
    private javax.swing.JCheckBox cbBonus;
    private javax.swing.JCheckBox cbPPN;
    private javax.swing.JCheckBox cbStatusDO;
    private javax.swing.JComboBox cboSatuan;
    private javax.swing.JComboBox cboStatDiskon;
    private javax.swing.JComboBox cboStatDiskonItem;
    private javax.swing.JComboBox cboStatPPN;
    private javax.swing.JTextField diskon;
    private javax.swing.JFormattedTextField diskonitem;
    private javax.swing.JTextField harga;
    private javax.swing.JTextField hasilBayar;
    private javax.swing.JTextField hasilDiskon;
    private javax.swing.JTextField hasilSisa;
    private javax.swing.JTextField hasilTotal;
    private javax.swing.JTextField hasilTotalBayar;
    private javax.swing.JTextField hasilppn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable5;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jumlah;
    private javax.swing.JTextField kodebarang;
    public javax.swing.JTextField kodepelanggan;
    private javax.swing.JTextField kodepelanggan1;
    private javax.swing.JTextField namabarang;
    public javax.swing.JTextField namapelanggan;
    private javax.swing.JTextField namapelanggan1;
    private javax.swing.JTextField nofaktur;
    private com.erv.function.PanelCool panelCool2;
    private javax.swing.JTextField ppn;
    private javax.swing.JComboBox statusbayar;
    private javax.swing.JButton tbnKeluar;
    private datechooser.beans.DateChooserCombo tgl;
    private datechooser.beans.DateChooserCombo tglExpire;
    private datechooser.beans.DateChooserCombo tglRetur;
    private datechooser.beans.DateChooserCombo tgllunas;
    private javax.swing.JTextField txtBatch;
    private javax.swing.JTextField txtKodeDO;
    private javax.swing.JTextField txtNamaDo;
    private javax.swing.JTextField txtNamaSales;
    private javax.swing.JTextField txtNofakturTemp;
    private javax.swing.JTextField txtSales;
    private javax.swing.JTextField txtkodeRetur;
    // End of variables declaration//GEN-END:variables

    private void reloadData(Connection c) {
        jTable1.setFont(new Font("Tahoma", Font.BOLD, 12));
        TableColumn col = jTable1.getColumnModel().getColumn(0);
        col.setPreferredWidth(30);
        col = jTable1.getColumnModel().getColumn(1);
        col.setPreferredWidth(50);
        col = jTable1.getColumnModel().getColumn(2);
        col.setPreferredWidth(250);
//        col = jTable1.getColumnModel().getColumn(7);
//        col.setPreferredWidth(10);
//        col = jTable1.getColumnModel().getColumn(9);
//        col.setPreferredWidth(5);
        col = jTable1.getColumnModel().getColumn(11);
        col.setPreferredWidth(30);
        TableColumn col1 = jTable1.getColumnModel().getColumn(12);
        col1.setMinWidth(0);
        col1.setMaxWidth(0);
        col1 = jTable1.getColumnModel().getColumn(13);
        col1.setMinWidth(0);
        col1.setMaxWidth(0);

        jScrollPane1.setFocusable(false); //
        jTable1.setFocusable(false); //
        jScrollPane1.getViewport().add(jTable1); //
        jScrollPane1.repaint(); //
        try {
            total = 0;
            hpp = 0;
            DiskonItem = 0;
            PPNItem = 0;
            if (model != null) {
                if (model.getRowCount() != 0) {
                    for (int i = 0; i < jTable1.getRowCount(); i++) {
                        total += Double.parseDouble(jTable1.getValueAt(i, 5).toString()) * Double.parseDouble(jTable1.getValueAt(i, 7).toString());
                        //hpp += dbbarang.getDetails(jTable1.getValueAt(i, 0).toString()).getCOGS() * Double.parseDouble(jTable1.getValueAt(i, 2).toString());
                        hpp += Double.parseDouble(jTable1.getValueAt(i, 12).toString()) * Double.parseDouble(jTable1.getValueAt(i, 13).toString());
                        //DiskonItem += (Double.parseDouble(jTable1.getValueAt(i, 4).toString()) / 100) * (Double.parseDouble(jTable1.getValueAt(i, 2).toString()) * Double.parseDouble(jTable1.getValueAt(i, 3).toString())); Diskon Persen
                        DiskonItem += Double.parseDouble(jTable1.getValueAt(i, 8).toString());

                        //PPNItem += (Double.parseDouble(jTable1.getValueAt(i, 7).toString()) / 100) * ((Double.parseDouble(jTable1.getValueAt(i, 3).toString()) * Double.parseDouble(jTable1.getValueAt(i, 5).toString())) - ((Double.parseDouble(jTable1.getValueAt(i, 4).toString()) / 100) * (Double.parseDouble(jTable1.getValueAt(i, 2).toString()) * Double.parseDouble(jTable1.getValueAt(i, 3).toString()))));
                        PPNItem += (Double.parseDouble(jTable1.getValueAt(i, 9).toString()));
                    }
                } else {
                    total = 0;
                    hpp = 0;
                    DiskonItem = 0;
                    PPNItem = 0;
                    diskont = 0;
                }
                hasilAkhir(c);
            }
//            r1.close();
//            s.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.toString());
            DiskonTambah.setText("0");
        }
    }

    void hasilAkhir(Connection c) throws SQLException {
        //tppn = getPPN();
        //tdiskon = getDiskon();
        //ttotalbayar = total + tppn - tdiskon;
        // hasilBayar.setText(com.erv.function.Util.toMoney(tdp));      
        diskont = getTextDouble(DiskonTambah) + DiskonItem;
        tppn = 0;
        if (cbPPN.isSelected()) {
            tppn = (total - diskont) * 0.1;
        }
        ttotalbayar = total + tppn - diskont;
        cekpembayaran(c);
        hasilppn.setText(com.erv.function.Util.toMoney(tppn));
        hasilTotal.setText(com.erv.function.Util.toMoney(total));
        hasilDiskon.setText(com.erv.function.Util.toMoney(diskont));
        hasilTotalBayar.setText(com.erv.function.Util.toMoney(ttotalbayar));
        //cekpembayaran();
    }

    double getDiskon(int jumlah, double harga) {
        double hasil = 0;
        if (cboStatDiskonItem.getSelectedIndex() == 0) {
            hasil = (Double.parseDouble(diskonitem.getValue().toString()) / 100) * (jumlah * harga);
//            hasil = (Double.parseDouble(diskonitem.getValue().toString()) / 100) * (harga);
        } else if (cboStatDiskonItem.getSelectedIndex() == 1) {
            hasil = Double.parseDouble(diskonitem.getValue().toString());
        }
        return hasil;
    }

    double getDiskonPersen(int jumlah, double harga) {
        double hasil = 0;
        if (cboStatDiskon.getSelectedIndex() == 0) {
            hasil = Double.parseDouble(diskonitem.getValue().toString());
        } else if (cboStatDiskon.getSelectedIndex() == 1) {
            double d = Double.parseDouble(diskonitem.getValue().toString());
            if (d != 0) {
                hasil = (Double.parseDouble(diskonitem.getValue().toString()) * 100) / (jumlah * harga);
            }
        }
        return hasil;
    }

    double getPPN(double nilai) {
        double hasil = 0;
        if (cbPPN.isSelected()) {
            hasil = 0.1 * nilai;
        }
        return hasil;
    }

    private void kosongFaktur() {
        //nofaktur.setText("");
        kodepelanggan.setText("");
        diskon.setText("0");
        ppn.setText("0");
        namapelanggan.setText("");
        txtSales.setText("");
        txtNamaSales.setText("");
        txtNamaDo.setText("");
        txtKodeDO.setVisible(true);
        txtNamaDo.setVisible(true);
        LabelKodeDO.setVisible(true);
        statusbayar.setSelectedIndex(3);
        CboNamaBank.setVisible(false);
        btnBaru.setVisible(true);
        btnNewPlgn.setVisible(false);
        cbPPN.setEnabled(true);
        cbPPN.setSelected(false);

    }

    private void kosongBarang() {
        kodebarang.setText("");
        namabarang.setText("");
        harga.setText("");
        diskonitem.setText("");
        jumlah.setText("");
        txtBatch.setText("");
        cbBonus.setSelected(false);
        kodebarang.requestFocus();
    }

    private void kosongHasil() {
        hasilBayar.setText("0");
        hasilDiskon.setText("0");
        hasilSisa.setText("0");
        hasilTotal.setText("0");
        hasilTotalBayar.setText("0");
        hasilppn.setText("0");
        DiskonTambah.setText("0");
        jLabel13.setVisible(false);
        DiskonTambah.setVisible(false);
    }

    private void buatTabel() {
        String sqlCreate = "create table rinci (KODEBARANG varchar(20) primary key, "
                + "NAMABARANG varchar(200), "
                + "JUMLAH int, "
                + "HARGA double, "
                + "DISKON double, "
                + "PPN float, "
                + "TOTAL double, "
                + "DO int)";
        String sqlJurnal = "create table jurnal ( "
                + "KODEPERKIRAAN varchar(20), "
                + "DEBET float, "
                + "KREDIT float)";
        //String sqlDO = "create table dopenjualan(IDPENJUALAN int,IDDO int, KODEBARANG varchar(20))";
        try {
            stat.execute(sqlCreate);
            stat.execute(sqlJurnal);
            //  stat.execute(sqlDO);
        } catch (SQLException ex) {
        }
    }

    private void hapusTabel() {
        try {
            stat.execute("drop table rinci");
            stat.execute("drop table jurnal");
            stat.execute("drop table dopenjualan");
        } catch (SQLException ex) {
        }
    }

    private void setFaktur(Connection c) throws SQLException {
        if (cbPPN.isSelected()) {
//            IDjual = dbpenjualan.getID(c);
//            nofaktur.setText(penjualanDao.setFaktur(c));
            IDjual = dbpenjualan.getID(c);
            nofaktur.setText(penjualanDao.setFakturPajak(c));
        }else{
            IDjual = dbpenjualan.getID(c);
            nofaktur.setText(penjualanDao.setFakturTanpaPajak(c));
        }
    }

    void setRetur(Connection c) throws SQLException {
        IDretur = returDao.getID(c);
        txtkodeRetur.setText(returDao.setRetur(c));
    }

    public void prosesSimpan(Connection c) throws SQLException, JavarieException, ClassNotFoundException {
        if (p == null) {
            p = new penjualan();
            p.setID(0);
            p.setFAKTUR("");
        } else {
            p.setID(IDjual);
            p.setFAKTUR(nofaktur.getText());
        }
        //p.setTANGGAL(java.sql.Date.valueOf(tgl.getText()));
        p.setTANGGAL(tgl.getText());
        p.setKODEPELANGGAN(kodepelanggan.getText());
        p.setCASH(String.valueOf(statusbayar.getSelectedIndex()));
        p.setSTATUS(String.valueOf(statusbayar.getSelectedIndex()));
        //p.setTGLLUNAS(java.sql.Date.valueOf(tgllunas.getText()));
        p.setTGLLUNAS(tgllunas.getText());
        p.setPPN(tppn);
        p.setIDSALES(txtSales.getText());
        p.setDP(tdp);
        p.setDISKON(Double.parseDouble(DiskonTambah.getText()));
        p.setTAMBAHANTOTAL(0);
        if (statusbayar.getSelectedItem().equals("BANK")) {
            String a[] = Util.split(CboNamaBank.getSelectedItem().toString(), "-");
            p.setIDBANK(Integer.parseInt(a[0]));
        } else {
            p.setIDBANK(0);
        }
        p.setPELANGGAN(namapelanggan.getText());
        p.setJENISTRANS(CboTipeHarga.getSelectedItem().toString());
        p.setSTATUSDO((cbStatusDO.isSelected()) ? "1" : "0");
        List<penjualan> penList = new ArrayList<penjualan>();
        penList.add(p);
        List<rincipenjualan> rpList = new ArrayList<rincipenjualan>();
        rincipenjualan rp;
        for (int i = 0; i < jTable1.getRowCount(); i++) {
            rp = new rincipenjualan();
            rp.setID(0);
            rp.setIDPENJUALAN(IDjual);
            rp.setKODEBARANG(jTable1.getValueAt(i, 1).toString());
            rp.setJUMLAH(Integer.parseInt(jTable1.getValueAt(i, 5).toString()));
            rp.setHARGA(Double.parseDouble(jTable1.getValueAt(i, 7).toString()));
            rp.setDISKON(Double.parseDouble(jTable1.getValueAt(i, 8).toString()));
            rp.setPPN(Double.parseDouble(jTable1.getValueAt(i, 9).toString()));
            rp.setTOTAL(Double.parseDouble(jTable1.getValueAt(i, 10).toString()));
            rp.setIDDO(Integer.parseInt(jTable1.getValueAt(i, 11).toString()));
            rp.setCOGS(Double.parseDouble(jTable1.getValueAt(i, 12).toString()));
            rp.setSATUAN(jTable1.getValueAt(i, 6).toString());
            rp.setJUMLAHKECIL(Integer.parseInt(jTable1.getValueAt(i, 13).toString()));
            rp.setKODEBATCH(jTable1.getValueAt(i, 3).toString());
            rp.setDISKONP(Double.parseDouble(jTable1.getValueAt(i, 14).toString()));
            rp.setBONUS(jTable1.getValueAt(i, 15).toString());
            String tempexpire = "";
            if (jTable1.getValueAt(i, 4) == null) {
                tempexpire = "";
            } else {
                tempexpire = jTable1.getValueAt(i, 4).toString();
            }
            if (tempexpire == "") {
                rp.setEXPIRE(null);
            } else {
                rp.setEXPIRE(tempexpire);
            }
            rpList.add(rp);
        }
        dbpenjualan.createPenjualan(c, penList, rpList);
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "No", "Kode", "Nama Barang", "Batch", "Expire", "Jumlah", "Satuan", "Harga", "Diskon", "PPN", "Total", "DO", "Cogs", "JumlahKecil", "Diskon %", "Bonus"
                }
        ) {
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        });
        model = (DefaultTableModel) jTable1.getModel();
    }

    void prosesSimpanRetur(Connection c) throws SQLException {
        r.setID(IDretur);
        r.setKODERETUR(txtkodeRetur.getText());
        r.setKODEPELANGGAN(kodepelanggan1.getText());
        r.setTANGGAL(tglRetur.getText());
        r.setKETERANGAN("Pengembalian Brg Dari " + namapelanggan1.getText());
        r.setIDPENJUALAN(IDjual);
        r.setTAMBAHANTOTALRETUR(0);
        r.setTOTALRETUR(total);
        r.setTOTALDISKON(diskont);
        r.setTOTALPPN(tppn);
        r.setTOTALHPP(hpp);

        if (returDao.insertIntoRETUR(c, r)) {
            JOptionPane.showMessageDialog(this, "Entri Retur Ok");
            Statement s = cm.createStatement();
//            ResultSet r4 = s.executeQuery("select * from rinci");
            rinciretur rr = new rinciretur();
            stok st = new stok();
            for (int i = 0; i < jTable1.getRowCount(); i++) {
                rr.setID(rincireturDao.getID(c));
                rr.setIDRETUR(IDretur);
                rr.setKODEBARANG(jTable1.getValueAt(i, 1).toString());
                rr.setJUMLAH(Integer.parseInt(jTable1.getValueAt(i, 5).toString()));
                rr.setHARGA(Float.parseFloat(jTable1.getValueAt(i, 7).toString()));
                rr.setTOTAL(Float.parseFloat(jTable1.getValueAt(i, 10).toString()));
                rr.setDISKON(Float.parseFloat(jTable1.getValueAt(i, 8).toString()));
                rr.setPPN(Float.parseFloat(jTable1.getValueAt(i, 9).toString()));
                rr.setIDDO(Integer.parseInt(jTable1.getValueAt(i, 11).toString()));
                rr.setSATUAN(jTable1.getValueAt(i, 6).toString());
                rr.setJUMLAHKECIL(Integer.parseInt(jTable1.getValueAt(i, 13).toString()));
                rr.setCOGS(Double.parseDouble(jTable1.getValueAt(i, 12).toString()));
                rr.setKODEBATCH(jTable1.getValueAt(i, 3).toString());
                if (rr.getKODEBATCH().equals("")) {
                    rr.setEXPIRE(null);
                } else {
                    rr.setEXPIRE(jTable1.getValueAt(i, 4).toString());
                }
                rincireturDao.insertIntoRINCIRETUR(c, rr);
////                st.setIDPENJUALAN(IDretur);
////                st.setKODEBARANG(jTable1.getValueAt(i, 0).toString());
////                //st.setTANGGAL(java.sql.Date.valueOf(tglRetur.getText()));
////                st.setTANGGAL(tglRetur.getText());
////                st.setIN(Integer.parseInt(jTable1.getValueAt(i, 13).toString()));
////                st.setKODETRANS("R");
////                st.setOUT(0);
////                st.setKODEBATCH(rr.getKODEBATCH());
////                stokDao.insertIntoSTOK(c, st);
            }
            jTable1.setModel(new javax.swing.table.DefaultTableModel(
                    new Object[][]{},
                    new String[]{
                        "No", "Kode", "Nama Barang", "Batch", "Expire", "Jumlah", "Satuan", "Harga", "Diskon", "PPN", "Total", "DO", "", ""
                    }));
            model = (DefaultTableModel) jTable1.getModel();
            s.close();
        } else {
            JOptionPane.showMessageDialog(this, "Entri Retur Gagal");
        }
    }

    public static void processUpdateCounts(int[] updateCounts) {
        for (int i = 0; i < updateCounts.length; i++) {
            if (updateCounts[i] >= 0) {
                // Successfully executed; the number represents number of affected rows
                System.out.println(updateCounts[i]);
            } else if (updateCounts[i] == Statement.SUCCESS_NO_INFO) {
                // Successfully executed; number of affected rows not available
            } else if (updateCounts[i] == Statement.EXECUTE_FAILED) {
                // Failed to execute
            }
        }
    }

//    void prosesSimpanRetur() {
//        try {
//            retur r=new retur();
//            IDretur=returDao.getID(c);
//            r.setID(IDretur);
//            r.setKODERETUR(txtkodeRetur.getText());
//            r.setTANGGAL(java.sql.Date.valueOf(tglRetur.getText()));
//            r.setKODEPELANGGAN(kodepelanggan1.getText());
//            r.setKETERANGAN("Return dari "+namapelanggan1.getText());
//            if (returDao.insertIntoRETUR(c,r)) {
//                JOptionPane.showMessageDialog(this, "Entri Retur Ok");
//                Statement s = cm.createStatement();
//                ResultSet rs = s.executeQuery("select * from rinci");
//                rinciretur rr = new rinciretur();
//                stok st = new stok();
//                while (rs.next()) {
//                    rr.setID(rincireturDao.getID(c));
//                    rr.setIDRETUR(IDretur);
//                    rr.setKODEBARANG(rs.getString(1));
//                    rr.setJUMLAH(rs.getInt(3));
//                    rr.setHARGA(rs.getFloat(4));
//                    rr.setTOTAL(rs.getFloat(6));
//                    rincireturDao.insertIntoRINCIRETUR(c,rr);
//                    st.setIDPENJUALAN(IDretur);
//                    st.setKODEBARANG(rs.getString(1));
//                    st.setTANGGAL(java.sql.Date.valueOf(tglRetur.getText()));
//                    st.setIN(rs.getInt(3));
//                    st.setOUT(0);
//                    st.setKODETRANS("R"); 
//                    stokDao.insertIntoSTOK(c, st);
//                }
//
//            } else {
//                JOptionPane.showMessageDialog(this, "Entri Retur Gagal");
//            }
//        } catch (Exception ex) {
//            JOptionPane.showMessageDialog(this, ex.toString());
//            ex.printStackTrace();
//        }
//    }
    void prosesUpdate(Connection c) {
        try {
            p.setID(IDjual);
            p.setFAKTUR(nofaktur.getText());
            p.setTANGGAL(tgl.getText());
            p.setKODEPELANGGAN(kodepelanggan.getText());
            p.setCASH(String.valueOf(statusbayar.getSelectedIndex()));
            //p.setTGLLUNAS(java.sql.Date.valueOf(tgllunas.getText()));
            p.setTGLLUNAS(tgllunas.getText());
            p.setPPN(PPNItem);
            if (statusbayar.getSelectedIndex() == 1) {
                p.setDP(tdp);
            } else {
                p.setDP(0);
            }
            p.setDISKON(0);
            if (!dbpenjualan.update(c, p)) {
                JOptionPane.showMessageDialog(this, "Update Penjualan Ok");
                Statement s = cm.createStatement();
                ResultSet r3 = s.executeQuery("select * from rinci");
                rincipenjualan rp = new rincipenjualan();
                stok st = new stok();
                rincipenjualanDao dbrp = new rincipenjualanDao();
                Statement sf = c.createStatement();
                sf.execute("delete from RINCIPENJUALAN where IDPENJUALAN=" + IDjual + "");
                sf.execute("delete from JURNAL where KODEJURNAL='" + nofaktur.getText() + "'");
                sf.execute("delete from stok where IDPENJUALAN=" + IDjual + " and KODETRANS='J'");
                sf.execute("delete from PIUTANG where IDPENJUALAN=" + IDjual + "");
                while (r3.next()) {
                    rp.setID(dbrp.getID(c));
                    rp.setIDPENJUALAN(IDjual);
                    rp.setKODEBARANG(r3.getString(1));
                    rp.setJUMLAH(r3.getInt(3));
                    rp.setHARGA(r3.getDouble(4));
                    rp.setDISKON(r3.getFloat(5));
                    rp.setTOTAL(r3.getDouble(6));
                    dbrp.insert(c, rp);
                    st.setIDPENJUALAN(IDjual);
                    st.setKODEBARANG(r3.getString(1));
                    //st.setTANGGAL(java.sql.Date.valueOf(tgl.getText()));
                    st.setTANGGAL(tgl.getText());
                    st.setIN(0);
                    st.setOUT(r3.getInt(3));
                    st.setKODETRANS("J");
                    stokDao.insertIntoSTOK(c, st);
                }
                r3.close();
                sf.close();
                s.close();

            } else {
                JOptionPane.showMessageDialog(this, "Update Penjualan Gagal");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.toString());
        }
    }

    void prosesDelete(Connection c) {
        try {
            Statement sf = c.createStatement();
            //sf.execute("delete from RINCIPENJUALAN where IDPENJUALAN=" + IDjual + "");
            sf.execute("delete from PENJUALAN where ID=" + IDjual + "");
            JOptionPane.showMessageDialog(this, "Delete Ok");
            sf.close();
            hapusTabel();
            buatTabel();
            hpp = 0;
            total = 0;
            reloadData(c);
            kosongFaktur();
            kosongBarang();
            kosongHasil();
            jTabbedPane1.setSelectedIndex(0);
            setFaktur(c);
            kodepelanggan.requestFocus();
            sf.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.toString());
        }
    }

    void insertJurnal(Connection c, String pil) {
        jurnal j = new jurnal();
        try {
            IDJurnal = jurnalDao.getIDJurnal(c);
            j.setID(IDJurnal);
            if (pil.equals("JUAL")) {
                j.setTANGGAL(tgl.getText());
                j.setKODEJURNAL(nofaktur.getText());
                if (statusbayar.getSelectedIndex() == 2) {
                    j.setDESKRIPSI("Penjualan Kepada " + namapelanggan.getText() + " Via Bank " + CboNamaBank.getSelectedItem().toString());
                } else {
                    j.setDESKRIPSI("Penjualan Kepada " + namapelanggan.getText());
                }
            } else if (pil.equals("RETUR")) {
                //j.setTANGGAL(java.sql.Date.valueOf(tglRetur.getText()));
                j.setTANGGAL(tglRetur.getText());
                j.setKODEJURNAL(txtkodeRetur.getText());
                j.setDESKRIPSI("Pengembalian Barang Dari " + namapelanggan1.getText());
            }
            jurnalDao.insertIntoJURNAL(c, j);
            pesan.add(new pesan("S", "Entri Jurnal Ok", "" + j.getID()));
        } catch (SQLException ex) {
            pesan.add(new pesan("E", "Entri Jurnal Gagal", "" + j.getID()));
        }
    }

//    void insertRinciJurnal(Connection c) {
//        Statement s;
//        try {
//            s = c.createStatement();
//            s.execute("set autocommit false");
//            if (statusbayar.getSelectedIndex() == 0) {
//                s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "KAS") + "'," + ttotalbayar + ",0,1)");
//                if (diskont != 0.0) {
//                    s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "DISKONJUAL") + "'," + diskont + ",0,2)");
//                }
//                if (PPNItem != 0) {
//                    s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "PPNJUAL") + "',0," + PPNItem + ",3)");
//                }
//                s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "PENJUALAN") + "',0," + total + ",4)");
//                s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "HPP") + "'," + hpp + ",0,5)");
//                s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "PERSEDIAAN") + "',0," + hpp + ",6)");
//
////                if (DiskonItem == 0 && PPNItem == 0) {
////                    s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "KAS") + "'," + ttotalbayar + ",0,1)");
////                    s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "PENJUALAN") + "',0," + ttotalbayar + ",2)");
////                    s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "HPP") + "'," + hpp + ",0,3)");
////                    s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "PERSEDIAAN") + "',0," + hpp + ",4)");
////                } else if (DiskonItem != 0 && PPNItem == 0) {
////                    s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "KAS") + "'," + ttotalbayar + ",0,1)");
////                    s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "DISKONJUAL") + "'," + DiskonItem + ",0,2)");
////                    s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "PENJUALAN") + "',0," + total + ",3)");
////                    s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "HPP") + "'," + hpp + ",0,4)");
////                    s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "PERSEDIAAN") + "',0," + hpp + ",5)");
////                } else if (DiskonItem == 0 && PPNItem != 0) {
////                    s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "KAS") + "'," + ttotalbayar + ",0,1)");
////                    s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "PPNJUAL") + "',0," + PPNItem + ",2)");
////                    s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "PENJUALAN") + "',0," + total + ",3)");
////                    s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "HPP") + "'," + hpp + ",0,4)");
////                    s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "PERSEDIAAN") + "',0," + hpp + ",5)");
////                } else if (DiskonItem != 0 && PPNItem != 0) {
////                    s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "KAS") + "'," + ttotalbayar + ",0,1)");
////                    s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "DISKONJUAL") + "'," + DiskonItem + ",0,2)");
////                    s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "PPNJUAL") + "',0," + PPNItem + ",3)");
////                    s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "PENJUALAN") + "',0," + total + ",4)");
////                    s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "HPP") + "'," + hpp + ",0,5)");
////                    s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "PERSEDIAAN") + "',0," + hpp + ",6)");
////                }
//                s.executeBatch();
//            } else if (statusbayar.getSelectedIndex() == 1) {
//                pelangganDao dbplg = new pelangganDao(c);
//
//                if (tdp > 0) {
//                    s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "KAS") + "'," + tdp + ",0,1)");
//                }
//                s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + dbplg.getDetails(kodepelanggan.getText()).getKODEAKUN() + "'," + sisa + ",0,1)");
//                if (diskont != 0.0) {
//                    s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "DISKONJUAL") + "'," + diskont + ",0,2)");
//                }
//                if (PPNItem != 0) {
//                    s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "PPNJUAL") + "',0," + PPNItem + ",3)");
//                }
//
//                s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "PENJUALAN") + "',0," + total + ",4)");
//                s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "HPP") + "'," + hpp + ",0,5)");
//                s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "PERSEDIAAN") + "',0," + hpp + ",6)");
//                s.executeBatch();
////                if (DiskonItem == 0 && PPNItem == 0) {
////                    if (tdp > 0) {
////                        s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "KAS") + "'," + tdp + ",0,1)");
////                    }
////                    s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + dbplg.getDetails(kodepelanggan.getText()).getKODEAKUN() + "'," + sisa + ",0,2)");
////                    s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "PENJUALAN") + "',0," + ttotalbayar + ",3)");
////                    s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "HPP") + "'," + hpp + ",0,4)");
////                    s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "PERSEDIAAN") + "',0," + hpp + ",5)");
////                }
////                if (DiskonItem != 0 && PPNItem == 0) {
////                    if (tdp > 0) {
////                        s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "KAS") + "'," + tdp + ",0,1)");
////                    }
////                    s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + dbplg.getDetails(kodepelanggan.getText()).getKODEAKUN() + "'," + sisa + ",0,1)");
////                    s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "DISKONJUAL") + "'," + DiskonItem + ",0,2)");
////                    s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "PENJUALAN") + "',0," + total + ",3)");
////                    s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "HPP") + "'," + hpp + ",0,4)");
////                    s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "PERSEDIAAN") + "',0," + hpp + ",5)");
////                }
////                if (DiskonItem == 0 && PPNItem != 0) {
////                    if (tdp > 0) {
////                        s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "KAS") + "'," + tdp + ",0,1)");
////                    }
////                    s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + dbplg.getDetails(kodepelanggan.getText()).getKODEAKUN() + "'," + sisa + ",0,1)");
////                    s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "PPNJUAL") + "',0," + PPNItem + ",2)");
////                    s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "PENJUALAN") + "',0," + total + ",3)");
////                    s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "HPP") + "'," + hpp + ",0,4)");
////                    s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "PERSEDIAAN") + "',0," + hpp + ",5)");
////                }
////                if (DiskonItem != 0 && PPNItem != 0) {
////                    if (tdp > 0) {
////                        s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "KAS") + "'," + tdp + ",0,1)");
////                    }
////                    s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + dbplg.getDetails(kodepelanggan.getText()).getKODEAKUN() + "'," + sisa + ",0,1)");
////                    s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "DISKONJUAL") + "'," + DiskonItem + ",0,2)");
////                    s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "PPNJUAL") + "',0," + PPNItem + ",3)");
////                    s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "PENJUALAN") + "',0," + total + ",4)");
////                    s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "HPP") + "'," + hpp + ",0,5)");
////                    s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "PERSEDIAAN") + "',0," + hpp + ",6)");
////                }
////                s.executeBatch();
//                dbplg = null;
//                prosesSimpanHutang(c);
//            } else if (statusbayar.getSelectedIndex() == 2) {
//                String[] a = Util.split(CboNamaBank.getSelectedItem().toString(), "-");
//                try {
//                    bank = bankDao.getDetails(c, Integer.parseInt(a[0]));
//                } catch (Exception e) {
//                }
//
//                s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + bank.getKODEAKUN() + "'," + ttotalbayar + ",0,1)");
//                if (diskont != 0.0) {
//                    s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "DISKONJUAL") + "'," + diskont + ",0,2)");
//                }
//                if (PPNItem != 0) {
//                    s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "PPNJUAL") + "',0," + PPNItem + ",3)");
//                }
//                s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "PENJUALAN") + "',0," + total + ",4)");
//                s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "HPP") + "'," + hpp + ",0,5)");
//                s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "PERSEDIAAN") + "',0," + hpp + ",6)");
//
////                if (DiskonItem == 0 && PPNItem == 0) {
////
////                    s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + bank.getKODEAKUN() + "'," + ttotalbayar + ",0,1)");
////                    s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "PENJUALAN") + "',0," + ttotalbayar + ",2)");
////                    s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "HPP") + "'," + hpp + ",0,3)");
////                    s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "PERSEDIAAN") + "',0," + hpp + ",4)");
////                } else if (DiskonItem != 0 && PPNItem == 0) {
////                    s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + bank.getKODEAKUN() + "'," + ttotalbayar + ",0,1)");
////                    s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "DISKONJUAL") + "'," + DiskonItem + ",0,2)");
////                    s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "PENJUALAN") + "',0," + total + ",3)");
////                    s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "HPP") + "'," + hpp + ",0,4)");
////                    s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "PERSEDIAAN") + "',0," + hpp + ",5)");
////                } else if (DiskonItem == 0 && PPNItem != 0) {
////                    s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + bank.getKODEAKUN() + "'," + ttotalbayar + ",0,1)");
////                    s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "PPNJUAL") + "',0," + PPNItem + ",2)");
////                    s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "PENJUALAN") + "',0," + total + ",3)");
////                    s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "HPP") + "'," + hpp + ",0,4)");
////                    s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "PERSEDIAAN") + "',0," + hpp + ",5)");
////                } else if (DiskonItem != 0 && PPNItem != 0) {
////                    s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + bank.getKODEAKUN() + "'," + ttotalbayar + ",0,1)");
////                    s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "DISKONJUAL") + "'," + DiskonItem + ",0,2)");
////                    s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "PPNJUAL") + "',0," + PPNItem + ",3)");
////                    s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "PENJUALAN") + "',0," + total + ",4)");
////                    s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "HPP") + "'," + hpp + ",0,5)");
////                    s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "PERSEDIAAN") + "',0," + hpp + ",6)");
////                }
//                s.executeBatch();
//            }
//            c.commit();
//            s.execute("set autocommit true");
//            pesan.add(new pesan("S", "Entri Rinci Jurnal Ok", ""));
//            s.close();
//        } catch (SQLException ex) {
//            try {
//                c.rollback();
//                pesan.add(new pesan("S", "Entri Rinci Jurnal Ok", ""));
//            } catch (SQLException ex1) {
//                pesan.add(new pesan("E", "Rollback gagal", ""));
//            }
//        }
//    }
    void insertRinciJurnalRetur(Connection c) throws SQLException, ClassNotFoundException {
        Statement s;
        piutangbayar pb = new piutangbayar();
        s = c.createStatement();
        if (totalhutang >= ttotalbayar) {
            s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "RETURJUAL") + "'," + total + ",0,1,'')");
            s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + new pelangganDao(c).getDetails(kodepelanggan1.getText()).getKODEAKUN() + "',0," + ttotalbayar + ",2,'')");
            if (diskont > 0) {
                s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "DISKONJUAL") + "',0," + diskont + ",3,'')");
            }
            if (tppn > 0) {
                s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "PPNJUAL") + "'," + tppn + ",0,4,'')");
            }
            s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "PERSEDIAAN") + "'," + hpp + ",0,5,'')");
            s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "HPP") + "',0," + hpp + ",6,'')");
            pb.setID(piutangbayarDao.getID(c));
            pb.setIDPIUTANG(piutangDao.getDetailPiutangperJual(c, IDjual).getID());
            pb.setKODEPIUTANGBAYAR(piutangbayarDao.getKodePiutangBayar(c));
            pb.setJUMLAH(ttotalbayar);
            //pb.setTANGGAL(java.sql.Date.valueOf(tglRetur.getText()));
            pb.setTANGGAL(tglRetur.getText());
            pb.setREF(txtkodeRetur.getText());
            piutangbayarDao.insertIntoPIUTANGBAYAR(c, pb);
        } else {
            int idpiutang = 0;
            double sisapiutang = 0;
            boolean cekstat = false;
            Statement spiutang = null;
            Statement spiutangtot = null;
            try {
                String sqlpiutang = "select ID,IDPELANGGAN ,JUMLAH-JUMLAHBAYAR as sisa "
                        + "from VIEW_PIUTANG where IDPELANGGAN='" + kodepelanggan1.getText() + "'";
                spiutang = c.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet rspiutang = spiutang.executeQuery(sqlpiutang);
                if (rspiutang.next()) {
                    cekstat = true;
                }
                if (cekstat) {

                    String sqlpiutangtot = "select sum(JUMLAH-JUMLAHBAYAR) as totpiutang "
                            + "from VIEW_PIUTANG where IDPELANGGAN='" + kodepelanggan1.getText() + "'";
                    spiutangtot = c.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    ResultSet rspiutangtot = spiutangtot.executeQuery(sqlpiutangtot);
                    double sisakaspiutang = 0;
                    rspiutangtot.next();
                    sisakaspiutang = ttotalbayar - rspiutangtot.getDouble(1);

                    s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "RETURJUAL") + "'," + total + ",0,1,'')");
//                    if (ttotalbayar > rspiutangtot.getDouble(1)) {
//                        s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + new pelangganDao(c).getDetails(kodepelanggan1.getText()).getKODEAKUN() + "',0," + rspiutangtot.getDouble(1) + ",2)");
//                    } else {
                    s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + new pelangganDao(c).getDetails(kodepelanggan1.getText()).getKODEAKUN() + "',0," + ttotalbayar + ",2,'')");
//                    }
                    if (diskont > 0) {
                        s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "DISKONJUAL") + "',0," + diskont + ",3,'')");
                    }
                    if (tppn > 0) {
                        s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "PPNJUAL") + "'," + tppn + ",0,4,'')");
                    }
                    s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "PERSEDIAAN") + "'," + hpp + ",0,5,'')");
                    s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "HPP") + "',0," + hpp + ",6,'')");
                    rspiutang.beforeFirst();
                    while (ttotalbayar > 0) {
                        if (rspiutang.next()) {
                            if (rspiutang.getDouble(3) != 0) {
                                pb.setID(piutangbayarDao.getID(c));
                                //pb.setIDPIUTANG(piutangDao.getDetailPiutangperJual(c, IDjual).getID());
                                pb.setIDPIUTANG(rspiutang.getInt(1));
                                pb.setKODEPIUTANGBAYAR(piutangbayarDao.getKodePiutangBayar(c));
                                //pb.setJUMLAH(ttotalbayar);
                                if (ttotalbayar >= rspiutang.getDouble(3)) {
                                    pb.setJUMLAH(rspiutang.getDouble(3));
                                    piutang pt = piutangDao.getDetails(c, rspiutang.getInt(1));
                                    pt.setSTATUS("0");
                                    piutangDao.updatePIUTANG(c, pt);
                                } else {
                                    pb.setJUMLAH(ttotalbayar);
                                }
                                pb.setTANGGAL(tglRetur.getText());
                                pb.setREF(txtkodeRetur.getText());
                                piutangbayarDao.insertIntoPIUTANGBAYAR(c, pb);
                                ttotalbayar = ttotalbayar - rspiutang.getDouble(3);
                            }
                            //System.out.println("error"+rspiutang.getDouble(3));
                        } else {
                            //s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "KAS") + "',0," + sisakaspiutang + ",5)");
//                            s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "RETURJUAL") + "'," + total + ",0,1)");
//                            s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "KAS") + "',0," + ttotalbayar + ",2)");
//                            s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "DISKONJUAL") + "',0," + DiskonItem + ",3)");
//                            s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "PPNJUAL") + "'," + PPNItem + ",0,4)");
//                            s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "PERSEDIAAN") + "'," + hpp + ",0,5)");
//                            s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "HPP") + "',0," + hpp + ",6)");
                            break;
                        }

                    }
                } else {
                    s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "RETURJUAL") + "'," + total + ",0,1,'')");
                    s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + new pelangganDao(c).getDetails(kodepelanggan1.getText()).getKODEAKUN() + "',0," + ttotalbayar + ",2,'')");
                    if (diskont > 0) {
                        s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "DISKONJUAL") + "',0," + diskont + ",3,'')");
                    }
                    if (tppn > 0) {
                        s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "PPNJUAL") + "'," + tppn + ",0,4,'')");
                    }
                    s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "PERSEDIAAN") + "'," + hpp + ",0,5,'')");
                    s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "HPP") + "',0," + hpp + ",6,'')");

                }
            } catch (Exception e) {
                System.out.println("errr" + e);
            }

        }
        s.close();
        pb = null;

    }

    void selesai(Connection c) throws SQLException {
        hapusTabel();
        buatTabel();
        total = 0;
        hpp = 0;
        DiskonItem = 0;
        PPNItem = 0;
        nofak = nofaktur.getText();
        reloadData(c);
        kosongFaktur();
        kosongBarang();
        kosongHasil();
        setFaktur(c);
        jTabbedPane1.setSelectedIndex(0);
        kodepelanggan.requestFocus();
    }

    private void kosongRetur() {
        txtkodeRetur.setText("");
        kodepelanggan1.setText("");
        namapelanggan1.setText("");
    }

    private void setting() {
        panelCool2.add(jScrollPane3);
        jScrollPane3.setBounds(150, 120, 550, 150);
        panelCool2.setComponentZOrder(jScrollPane3, 0);
        jScrollPane3.setVisible(false);

        //jScrollPane3.setSize(500, 150);
        jScrollPane2.setSize(800, 150);
        panelCool2.add(jScrollPane5);
        jScrollPane5.setBounds(450, 170, 400, 100);
        panelCool2.setComponentZOrder(jScrollPane5, 0);
        jScrollPane5.setVisible(false);

//        panelCool1.add(jScrollPane4);
//        jScrollPane4.setBounds(160, 90, 400, 100);
//        panelCool1.setComponentZOrder(jScrollPane4, 1);
    }

//    void prosesSimpanHutang(Connection c) {
//        piutang ht = new piutang();
//        try {
//            ht.setKODEPIUTANG(piutangDao.getKodePiutang(c));
//            ht.setIDPENJUALAN(IDjual);
//            ht.setTANGGAL(tgl.getText());
//            ht.setJUMLAH(sisa);
//            ht.setIDPELANGGAN(kodepelanggan.getText());
//            ht.setJATUHTEMPO(tgllunas.getText());
//            ht.setKETERANGAN("Piutang Kepada " + namapelanggan.getText());
//            ht.setSTATUS("1");
//            ht.setID(piutangDao.getID(c));
//            if (piutangDao.insertIntoPIUTANG(c, ht)) {
//                pesan.add(new pesan("S", "Update Piutang Ok", "" + ht.getID()));
//                //JOptionPane.showMessageDialog(this, "Update Piutang Ok");
//            } else {
//                pesan.add(new pesan("E", "Update Piutang Gagal", "" + ht.getID()));
//                //JOptionPane.showMessageDialog(this, "Update Data Gagal");
//            }
//        } catch (SQLException ex) {
//            pesan.add(new pesan("E", "Update Piutang Gagal", "" + ht.getID()));
//        }
//    }
    void cetakFaktur(Connection c, String pil) throws MalformedURLException, IOException, JRException {
        HashMap parameter = new HashMap();
        JasperPrint jasperPrint = null;
        parameter.put("nofaktur", nofak);
        URL url;
        InputStream in;
        if (pil.equals("0")) {
            url = new URL(global.REPORT + "/LapPerFaktur.jasper");
            in = url.openStream();
            jasperPrint = JasperFillManager.fillReport(in, parameter, c);
        } else {
            //url = new URL(global.REPORT + "/LapPerFakturNoPPN.jasper");
            url = new URL(global.REPORT + "/LapPerFaktur.jasper");
            in = url.openStream();
            jasperPrint = JasperFillManager.fillReport(in, parameter, c);
        }
        JasperViewer.viewReport(jasperPrint, false);
    }

    void viewFakturPendek(String nofaktur) {
        Map<String, Object> p = ClassPrint.cetakfakturMap(c, nofaktur);
        ViewFaktur f = new ViewFaktur(p);
        JavarieSoftView.panelCool1.add(f);
        f.setSize(800, 400);
        f.toFront();
        f.setVisible(true);

    }

    void viewFakturPanjang(String nofaktur) {
        Map<String, Object> p = ClassPrintKertasPanjang.cetakfakturMap(c, nofaktur);
        ViewFakturPanjang f = new ViewFakturPanjang(p);
        JavarieSoftView.panelCool1.add(f);
        f.setSize(800, 400);
        f.toFront();
        f.setVisible(true);

    }

    void CetakFakturPendek(String nofaktur) {
        Map<String, Object> map = ClassPrint.cetakfakturMap(c, nofaktur);
        Template template = null;
        try {
            template = new JsonTemplate(ViewFaktur.class.getResourceAsStream("faktur.json"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
//        SimpleEscp simpleEscp = new SimpleEscp("\\\\Mypc-pc\\EPSON LX-300+ /II"); //printer herman    
//        SimpleEscp simpleEscp = new SimpleEscp("Epson LX-300+"); //printer kak novi Epson LX-300+
        SimpleEscp simpleEscp = new SimpleEscp("EPSON LX-310 ESC/P"); //printer kak novi EPSON LX-310 ESC/P

        simpleEscp.print(template, map);
    }

    void CetakFakturPanjang(String nofaktur) {
        Map<String, Object> map = ClassPrintKertasPanjang.cetakfakturMap(c, nofaktur);
        Template template = null;
        try {
            template = new JsonTemplate(ViewFakturPanjang.class.getResourceAsStream("fakturPanjang.json"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
//        SimpleEscp simpleEscp = new SimpleEscp("\\\\Mypc-pc\\EPSON LX-300+ /II"); //printer herman    
//        SimpleEscp simpleEscp = new SimpleEscp("Epson LX-300+"); //printer kak novi Epson LX-300+
        SimpleEscp simpleEscp = new SimpleEscp("EPSON LX-310 ESC/P"); //printer kak novi EPSON LX-310 ESC/P
        simpleEscp.print(template, map);
    }

    void cetakFaktur1(Connection c, String pil) {
        HashMap parameter = new HashMap();
        JasperPrint jasperPrint = null;
        parameter.put("nofaktur", txtNofakturTemp.getText());
        try {
            URL url;
            InputStream in;
            if (pil.equals("0")) {
                url = new URL(global.REPORT + "/LapPerFaktur.jasper");
                in = url.openStream();
                jasperPrint = JasperFillManager.fillReport(in, parameter, c);
            } else {
                url = new URL(global.REPORT + "/LapPerFaktur.jasper");
                in = url.openStream();
                jasperPrint = JasperFillManager.fillReport(in, parameter, c);

            }
            JasperViewer.viewReport(jasperPrint, false);
//            PrintService printService = PrintServiceLookup.lookupDefaultPrintService();
//            if (printService == null) {
//                //error message                
//                throw new PrinterException("No Printer Attached / Shared to the server");
//            } else {
//                JRExporter exporter = new JRPrintServiceExporter();
//                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
//                exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, printService.getAttributes());
//                exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
//                exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.TRUE);
//                exporter.exportReport();
//            }

        } catch (Exception ex) {
            System.out.print(ex.toString());
            //Logger.getLogger(formlaporan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void cetakRetur(Connection c) {
        HashMap parameter = new HashMap();
        JasperPrint jasperPrint = null;
        parameter.put("koderetur", txtkodeRetur.getText());
        try {
            URL url = new URL(global.REPORT + "/Retur.jasper");
            InputStream in = url.openStream();
            jasperPrint = JasperFillManager.fillReport(in, parameter, c);
            JasperViewer.viewReport(jasperPrint, false);
        } catch (Exception ex) {
            System.out.print(ex.toString());
            //Logger.getLogger(formlaporan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void settingTgl(Connection c) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(com.erv.fungsi.Fungsi.dateAdd(c, "DAY", 30, java.sql.Date.valueOf(tgl.getText())));
        tgllunas.setSelectedDate(cal);
    }

    public boolean cekperiode(Connection c, String periode) throws SQLException {
        //String periode = thn + "." + bln;
        boolean hasil1 = false;
        Statement s = c.createStatement();
        ResultSet rs = s.executeQuery("select * from KONTROLPERIODE where PERIODE='" + periode + "' and STATUSSTOK='1'");

        if (rs.next()) {
            if (rs.getString(1) != null) {
                hasil1 = true;
            }
        }
        rs.close();
        s.close();
        return hasil1;
    }

    public boolean cekperiodeAda(Connection c, String bul) throws SQLException {
        //String periode = thn + "." + bln;
        boolean ada = false;
        Statement s = c.createStatement();
        ResultSet rs = s.executeQuery("select * from KONTROLPERIODE where PERIODE='" + bul + "'");
        if (rs.next()) {
            if (rs.getString(1) != null) {
                ada = true;
            }
        }
        rs.close();
        s.close();
        return ada;
    }

    void prosesUpdateLog(Connection c) throws SQLException {
        //java.sql.Date tanggallog;
        String tanggallog;
        String jamlog = u.jamsekarang + ":" + u.menitsekarang + ":" + u.detiksekarang;
        //tanggallog = java.sql.Date.valueOf(u.thnsekarang + "-" + u.blnsekarang + "-" + u.tglsekarang);
        tanggallog = u.thnsekarang + "-" + u.blnsekarang + "-" + u.tglsekarang;
        String ketlog = "";
        lh.setUSERIDENTITY(JavarieSoftApp.jenisuser);
        lh.setTANGGAL(tanggallog);
        lh.setJAM(jamlog);
        if (aksilog.equals("InsertJual")) {
            lh.setTABEL("TPENJUALAN");
            lh.setNOREFF(nofaktur.getText());
            ketlog = "Insert Data Penjualan No Faktur " + nofaktur.getText() + " Tgl Faktur " + java.sql.Date.valueOf(tgl.getText()) + " Pelanggan " + namapelanggan.getText() + " (" + kodepelanggan.getText() + ")";
        } else if (aksilog.equals("InsertReturJual")) {
            lh.setTABEL("TRETURJUAL");
            lh.setNOREFF(txtkodeRetur.getText());
            ketlog = "Insert Data Retur Penjualan " + txtkodeRetur.getText() + " Tgl Retur " + java.sql.Date.valueOf(tglRetur.getText()) + " Atas Faktur " + nofaktur.getText() + " Pelanggan " + namapelanggan1.getText() + " (" + kodepelanggan1.getText() + ")";
        }
        lh.setAKSI(aksilog);
        lh.setKETERANGAN(ketlog);
        lhdao.insert(c, lh);
    }

    void reloadbank(Connection c) throws SQLException {
        CboNamaBank.removeAllItems();
        List bank = bankDao.getAlldetails(c);
        Iterator it = bank.iterator();
        while (it.hasNext()) {
            bank b = (bank) it.next();
            CboNamaBank.addItem(b.getIDBANK() + "-" + b.getNAMABANK());
            //System.out.println(b.getNAMABANK());
        }
    }

    private void CekPiutangBerisi(Connection c) throws SQLException {
        double temp = 0.0;
        ResultSet rs = stat.executeQuery("select p.ID AS IDJUAL,pl.kodepelanggan,\n"
                + "ifnull((SELECT PIUTANG.ID FROM PIUTANG WHERE PIUTANG.IDPENJUALAN = p.ID),0) as PIUTANGID,\n"
                + "ifnull((SELECT CONVERT(PIUTANG.JUMLAH,LONG) FROM PIUTANG WHERE PIUTANG.IDPENJUALAN = p.ID),0) as HUTANG , \n"
                + "ifnull((select sum(CONVERT(PIUTANGBAYAR.JUMLAH,LONG)) from PIUTANGBAYAR \n"
                + "inner join PIUTANG on PIUTANGBAYAR.IDPIUTANG=PIUTANG.ID \n"
                + "where PIUTANG.IDPENJUALAN=p.ID),0) as BAYAR, \n"
                + "ifnull((SELECT CONVERT(PIUTANG.JUMLAH,LONG) FROM PIUTANG WHERE PIUTANG.IDPENJUALAN = p.ID),0) - \n"
                + "ifnull((select sum(CONVERT(PIUTANGBAYAR.JUMLAH,LONG)) from PIUTANGBAYAR \n"
                + "inner join PIUTANG on PIUTANGBAYAR.IDPIUTANG=PIUTANG.ID \n"
                + "where PIUTANG.IDPENJUALAN=p.ID),0) as SISAHUTANG \n"
                + "from PENJUALAN p,PELANGGAN pl \n"
                + "where p.KODEPELANGGAN=pl.KODEPELANGGAN");
        if (rs.next()) {
            temp = rs.getDouble(1);
        }
    }

    public double getFormatTextDouble(JFormattedTextField t) {
        double h = 0;
        if (t.getValue() != null) {
            h = Double.parseDouble(t.getValue().toString());
        }
        return h;
    }

    public double getTextDouble(JTextField t) {
        double h = 0;
        if (t.getText() != null) {
            h = Double.parseDouble(t.getText());
        }
        return h;
    }

    public int getTextInteger(JTextField t) {
        int h = 0;
        if (t != null) {
            h = Integer.parseInt(t.getText());
        }
        return h;
    }

    void cekpembayaran(Connection c) throws SQLException {
        if (statusbayar.getSelectedIndex() == 0) {
            CboNamaBank.setVisible(false);
            hasilBayar.setText("" + df0.format(ttotalbayar));
        } else if (statusbayar.getSelectedIndex() == 1) {
            settingTgl(c);
            CboNamaBank.setVisible(false);
            if (getTextDouble(hasilBayar) == ttotalbayar) {
                hasilBayar.setText("0");
                sisa = ttotalbayar - Double.parseDouble(hasilBayar.getText());
                hasilSisa.setText(com.erv.function.Util.toMoney(sisa));
            } else if (Double.parseDouble(hasilBayar.getText()) > 0) {
                sisa = ttotalbayar - Double.parseDouble(hasilBayar.getText());
                hasilSisa.setText(com.erv.function.Util.toMoney(sisa));
            } else {
                hasilBayar.setText("0");
                sisa = ttotalbayar - Double.parseDouble(hasilBayar.getText());
                hasilSisa.setText(com.erv.function.Util.toMoney(sisa));
            }
        } else if (statusbayar.getSelectedIndex() == 2) {
            CboNamaBank.setVisible(true);
            hasilBayar.setText("" + df0.format(ttotalbayar));
            reloadbank(c);
        }
    }

    void pilihanKertasPendek() {
        RadioPendek.setSelected(true);
        RadioPanjang.setSelected(false);
    }

    void pilihanKertasPanjang() {
        RadioPendek.setSelected(false);
        RadioPanjang.setSelected(true);
    }

//    public void isiComboBatch(List<Barangstokbatch> ls) {
//        //cboBatch.removeAllItems();
//        jPanel5.remove(cboBatch);
//        cboBatch = new JComboBox();
//        expire = new String[ls.size()];
//        int count = 0;
//        for (Iterator<Barangstokbatch> it = ls.iterator(); it.hasNext();) {
//            Barangstokbatch barangstokbatch = it.next();
//            cboBatch.addItem(barangstokbatch.getKODEBATCH());
//            expire[count] = barangstokbatch.getEXPIRE().toString();
//            count++;
//        }
//        Calendar cld = Calendar.getInstance();
//        try {
//            cld.setTime(d.parse(expire[0]));
//        } catch (ParseException ex) {
//            Logger.getLogger(DialogPenjualan.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        tglExpire.setSelectedDate(cld);
//        cboBatch.addItemListener(new java.awt.event.ItemListener() {
//            public void itemStateChanged(java.awt.event.ItemEvent evt) {
//                cboBatchItemStateChanged(evt);
//            }
//        });
//        cboBatch.addKeyListener(new java.awt.event.KeyAdapter() {
//            public void keyPressed(java.awt.event.KeyEvent evt) {
//                cboBatchKeyPressed(evt);
//            }
//        });
//        cboBatch.setFont(resourceMap1.getFont("cboBatch.font")); // NOI18N
//        jPanel5.add(cboBatch);
//        cboBatch.setBounds(430, 20, 100, 20);
//
//    }
}
