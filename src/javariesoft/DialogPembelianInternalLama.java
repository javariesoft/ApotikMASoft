/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javariesoft;

import com.eigher.db.loghistoryDao;
import com.eigher.model.loghistory;
import com.erv.db.BarangstokDao;
import com.erv.db.BarangstokbatchDao;
import com.erv.db.bankDao;
import com.erv.db.barangDao;
import com.erv.db.hutangDao;
import com.erv.db.hutangbayarDao;
import com.erv.db.jurnalDao;
import com.erv.db.koneksi;
import com.erv.db.pembelianDao;
import com.erv.db.returbeliDao;
import com.erv.db.returbelirinciDao;
import com.erv.db.rincipembelianDao;
import com.erv.db.settingDao;
import com.erv.db.stokDao;
import com.erv.db.supplierDao;
import com.erv.function.JDBCAdapter;
import com.erv.function.Util;
import com.erv.model.Barangstok;
import com.erv.model.Barangstokbatch;
import com.erv.model.bank;
import com.erv.model.barang;
import com.erv.model.hutang;
import com.erv.model.hutangbayar;
import com.erv.model.jurnal;
import com.erv.model.pembelian;
import com.erv.model.returbeli;
import com.erv.model.returbelirinci;
import com.erv.model.rincipembelian;
import com.erv.model.stok;
import com.erv.model.supplier;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableColumn;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author TI-PNP
 */
public class DialogPembelianInternalLama extends javax.swing.JInternalFrame {

    /**
     * Creates new form DialogPembelianInternal
     */
    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    public static boolean status = false;
    String pesan = "";
    double totalhutang = 0;
    String nofak = "", noretur = "";
    java.text.DateFormat d = new SimpleDateFormat("yyyy-MM-dd");
    DecimalFormat df0 = new DecimalFormat("0.0");
    double tppn = 0, tdiskon, ttotalbayar, tdp, total, hpp, PPNItem = 0, DISKONItem = 0, diskont = 0;
    double totJurnal = 0;
    Connection cm;
    int IDBELI = 0, IDretur = 0;
    pembelian p = null;
    Statement stat = null;
    barangDao dbbarang = null;
    BarangstokDao dbBarangstokDao = null;
    BarangstokbatchDao dbBarangstokbatchDao = null;
    int IDJurnal = 0;
    String pilSupplier = "0";
    returbeli r = null;
    barang brg;
    hutang ht;
    bank bank;
    double sisa = 0;
    loghistory lh;
    loghistoryDao lhdao;
    com.erv.function.Util u = new com.erv.function.Util();
    String aksilog = "";
    int no = 0;
    boolean statedit = false;
    int nolama = 0;
    private Connection c;

    public DialogPembelianInternalLama() {
        initComponents();
        setStatedit(false);
        setNolama(0);
        try {
            c = koneksi.getKoneksiJ();
            awal(c);
            jTabbedPane2.setEnabledAt(1, false);
        } catch (SQLException ex) {
            Logger.getLogger(DialogPembelian.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DialogPembelian.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setLocation(((int) dim.getWidth() - this.getWidth()) / 2, ((int) dim.getHeight() - this.getHeight()) / 2);

    }

    public DialogPembelianInternalLama(int id, double tot, int pil) {
        initComponents();
        setStatedit(false);
        setNolama(0);
        //Connection c = null;
        try {
            c = koneksi.getKoneksiJ();
            awal(c);
            // TODO add your handling code here:
            p = pembelianDao.getDetails(c, id);
            IDBELI = p.getID();
            txtkodeFaktur.setText(p.getNOFAKTUR());
            nofaktur.setText(p.getNOFAKTURSUPPLIER());
            Calendar cld = Calendar.getInstance();
            cld.setTime(d.parse(p.getTGLMASUK()));
            txtTglMasuk.setSelectedDate(cld);
            cld.setTime(d.parse(p.getTANGGAL()));
            tanggal.setSelectedDate(cld);
            kodeSupplier.setText(p.getIDSUPPLIER());
            kodeSupplier1.setText(p.getIDSUPPLIER());
            supplier plg = supplierDao.getDetails(c, p.getIDSUPPLIER());
            namaSupplier.setText(plg.getNAMA());
            namaSupplier1.setText(plg.getNAMA());
            cbostatusBeli.setSelectedIndex(Integer.parseInt(p.getCASH()));
            cld.setTime(d.parse(p.getTGLBAYAR()));
            tglBayar.setSelectedDate(cld);
            diskon.setText("" + p.getDISKON());
            cboStatDiskon.setSelectedIndex(0);
            DiskonTambah.setText("" + p.getDISKON());
            if (p.getPAJAK() > 0) {
                cbPPN.setSelected(true);
            } else {
                cbPPN.setSelected(false);
            }
            totalhutang = tot;
//            hasilAkhir(c);
            int x = JOptionPane.showConfirmDialog(this, "Tampilkan Data Barang ?", "", JOptionPane.YES_NO_OPTION);
            if (x == 0) {
                ResultSet re = c.createStatement().executeQuery("select * from RINCIPEMBELIAN where IDPEMBELIAN=" + p.getID() + "");
                hapusTabel();
                buatTabel();
                int no = 0;
                while (re.next()) {
                    no++;
                    brg = new barangDao().getDetails(c, re.getString(3));
                    stat.execute("insert into rinci values("
                            + no
                            + ",'" + re.getString("KODEBARANG")
                            + "','" + brg.getNAMABARANG()
                            + "','" + re.getString("KODEBATCH") //jumlah
                            + "','" + ((re.getString("EXPIRE") == null) ? "" : re.getString("EXPIRE")) //harga
                            + "'," + re.getString("JUMLAH") //
                            + "," + re.getString("HARGA")
                            + "," + re.getString("DISKON")
                            + "," + re.getString("PPN")
                            + ",'" + re.getString("SATUAN")
                            + "'," + re.getString("TOTAL")
                            + "," + re.getString("JUMLAHKECIL")
                            + "," + re.getString("DISKONP")
                            + ",'" + re.getString("BONUS")
                            + "')");
                }
                total = 0;
                hpp = 0;
                hasilBayar.setText("0");
                reloadData(c);
                jTabbedPane2.setSelectedIndex(1);
                jTabbedPane2.setEnabledAt(0, false);
                re.close();
            }
            if (pil == 1) {
                jTabbedPane2.setSelectedIndex(0);
                jTabbedPane2.setEnabledAt(0, true);
                jTabbedPane2.setEnabledAt(1, false);
            } else {
                jTabbedPane2.setSelectedIndex(1);
                jTabbedPane2.setEnabledAt(0, false);
                jTabbedPane2.setEnabledAt(1, true);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
//        finally {
//            if (c != null) {
//                try {
//                    c.close();
//                } catch (SQLException ex) {
//                    Logger.getLogger(DialogPembelian.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        }
        this.setLocation(((int) dim.getWidth() - this.getWidth()) / 2, ((int) dim.getHeight() - this.getHeight()) / 2);
    }

    public boolean isStatedit() {
        return statedit;
    }

    public void setStatedit(boolean statedit) {
        this.statedit = statedit;
    }

    public int getNolama() {
        return nolama;
    }

    public void setNolama(int nolama) {
        this.nolama = nolama;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelCool1 = new com.erv.function.PanelCool();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        nofaktur = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        tanggal = new datechooser.beans.DateChooserCombo();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        kodeSupplier = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        namaSupplier = new javax.swing.JTextField();
        btnTambah = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        cbostatusBeli = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        tglBayar = new datechooser.beans.DateChooserCombo();
        jLabel10 = new javax.swing.JLabel();
        diskon = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        ppn = new javax.swing.JTextField();
        txtkodeFaktur = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        txtTglMasuk = new datechooser.beans.DateChooserCombo();
        CboNamaBank = new javax.swing.JComboBox();
        btnKeluar = new javax.swing.JButton();
        btnGenerate = new javax.swing.JButton();
        cboStatPPN = new javax.swing.JComboBox();
        cbPPN = new javax.swing.JCheckBox();
        jLabel30 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        txtkodeRetur = new javax.swing.JTextField();
        tglRetur = new datechooser.beans.DateChooserCombo();
        jLabel21 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        kodeSupplier1 = new javax.swing.JTextField();
        namaSupplier1 = new javax.swing.JTextField();
        btnKeluar1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        kodeBarang = new javax.swing.JTextField();
        namaBarang = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jumlahBarang = new javax.swing.JTextField();
        btnInsert = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        txtPPN = new javax.swing.JFormattedTextField();
        jTextField1 = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        hargaBarang = new javax.swing.JFormattedTextField();
        txtDiskon = new javax.swing.JFormattedTextField();
        jLabel27 = new javax.swing.JLabel();
        cboSatuan = new javax.swing.JComboBox();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        txtNoBatch = new javax.swing.JTextField();
        tglExpire = new datechooser.beans.DateChooserCombo();
        cboStatDiskon = new javax.swing.JComboBox();
        jLabel31 = new javax.swing.JLabel();
        cbBonus = new javax.swing.JCheckBox();
        jPanel3 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        hasilTotal = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        hasilDiskon = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        hasilBayar = new javax.swing.JTextField();
        hasilSisa = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        hasilTotalBayar = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        hasilppn = new javax.swing.JTextField();
        btnSimpanHasil = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        btnFaktur = new javax.swing.JButton();
        jLabel26 = new javax.swing.JLabel();
        DiskonTambah = new javax.swing.JTextField();

        setClosable(true);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(javariesoft.JavarieSoftApp.class).getContext().getResourceMap(DialogPembelianInternalLama.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N

        panelCool1.setName("panelCool1"); // NOI18N
        panelCool1.setLayout(null);

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        jTable2.setName("jTable2"); // NOI18N
        jTable2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable2KeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);

        panelCool1.add(jScrollPane2);
        jScrollPane2.setBounds(10, 220, 40, 20);

        jTabbedPane2.setFont(resourceMap.getFont("jTabbedPane2.font")); // NOI18N
        jTabbedPane2.setName("jTabbedPane2"); // NOI18N
        jTabbedPane2.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jTabbedPane2StateChanged(evt);
            }
        });

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setLayout(null);

        jLabel1.setFont(resourceMap.getFont("jLabel1.font")); // NOI18N
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N
        jPanel1.add(jLabel1);
        jLabel1.setBounds(20, 11, 80, 16);

        nofaktur.setFont(resourceMap.getFont("nofaktur.font")); // NOI18N
        nofaktur.setText(resourceMap.getString("nofaktur.text")); // NOI18N
        nofaktur.setName("nofaktur"); // NOI18N
        nofaktur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nofakturKeyPressed(evt);
            }
        });
        jPanel1.add(nofaktur);
        nofaktur.setBounds(240, 10, 200, 22);

        jLabel2.setFont(resourceMap.getFont("jLabel2.font")); // NOI18N
        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N
        jPanel1.add(jLabel2);
        jLabel2.setBounds(470, 10, 110, 16);

        tanggal.addCommitListener(new datechooser.events.CommitListener() {
            public void onCommit(datechooser.events.CommitEvent evt) {
                tanggalOnCommit(evt);
            }
        });
        jPanel1.add(tanggal);
        tanggal.setBounds(580, 35, 155, 20);

        jLabel3.setFont(resourceMap.getFont("jLabel3.font")); // NOI18N
        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N
        jPanel1.add(jLabel3);
        jLabel3.setBounds(20, 60, 100, 16);

        jScrollPane3.setName("jScrollPane3"); // NOI18N

        jTable3.setName("jTable3"); // NOI18N
        jTable3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable3KeyPressed(evt);
            }
        });
        jScrollPane3.setViewportView(jTable3);

        jPanel1.add(jScrollPane3);
        jScrollPane3.setBounds(130, 80, 20, 20);

        kodeSupplier.setFont(resourceMap.getFont("kodeSupplier.font")); // NOI18N
        kodeSupplier.setText(resourceMap.getString("kodeSupplier.text")); // NOI18N
        kodeSupplier.setName("kodeSupplier"); // NOI18N
        kodeSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kodeSupplierActionPerformed(evt);
            }
        });
        kodeSupplier.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kodeSupplierKeyPressed(evt);
            }
        });
        jPanel1.add(kodeSupplier);
        kodeSupplier.setBounds(130, 60, 130, 22);

        jLabel4.setFont(resourceMap.getFont("jLabel4.font")); // NOI18N
        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N
        jPanel1.add(jLabel4);
        jLabel4.setBounds(20, 85, 100, 16);

        namaSupplier.setFont(resourceMap.getFont("namaSupplier.font")); // NOI18N
        namaSupplier.setText(resourceMap.getString("namaSupplier.text")); // NOI18N
        namaSupplier.setName("namaSupplier"); // NOI18N
        jPanel1.add(namaSupplier);
        namaSupplier.setBounds(130, 85, 310, 22);

        btnTambah.setFont(resourceMap.getFont("btnTambah.font")); // NOI18N
        btnTambah.setIcon(resourceMap.getIcon("btnTambah.icon")); // NOI18N
        btnTambah.setText(resourceMap.getString("btnTambah.text")); // NOI18N
        btnTambah.setName("btnTambah"); // NOI18N
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });
        jPanel1.add(btnTambah);
        btnTambah.setBounds(750, 53, 110, 40);

        jLabel8.setFont(resourceMap.getFont("jLabel8.font")); // NOI18N
        jLabel8.setText(resourceMap.getString("jLabel8.text")); // NOI18N
        jLabel8.setName("jLabel8"); // NOI18N
        jPanel1.add(jLabel8);
        jLabel8.setBounds(20, 35, 120, 16);

        cbostatusBeli.setFont(resourceMap.getFont("cbostatusBeli.font")); // NOI18N
        cbostatusBeli.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "CASH", "KREDIT", "BANK", "-" }));
        cbostatusBeli.setSelectedIndex(3);
        cbostatusBeli.setName("cbostatusBeli"); // NOI18N
        cbostatusBeli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbostatusBeliActionPerformed(evt);
            }
        });
        jPanel1.add(cbostatusBeli);
        cbostatusBeli.setBounds(130, 35, 80, 22);

        jLabel9.setFont(resourceMap.getFont("jLabel9.font")); // NOI18N
        jLabel9.setText(resourceMap.getString("jLabel9.text")); // NOI18N
        jLabel9.setName("jLabel9"); // NOI18N
        jPanel1.add(jLabel9);
        jLabel9.setBounds(470, 60, 90, 16);
        jPanel1.add(tglBayar);
        tglBayar.setBounds(580, 60, 155, 20);

        jLabel10.setFont(resourceMap.getFont("jLabel10.font")); // NOI18N
        jLabel10.setText(resourceMap.getString("jLabel10.text")); // NOI18N
        jLabel10.setName("jLabel10"); // NOI18N
        jPanel1.add(jLabel10);
        jLabel10.setBounds(370, 70, 0, 16);

        diskon.setFont(resourceMap.getFont("diskon.font")); // NOI18N
        diskon.setFocusable(false);
        diskon.setName("diskon"); // NOI18N
        diskon.setRequestFocusEnabled(false);
        diskon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                diskonKeyPressed(evt);
            }
        });
        jPanel1.add(diskon);
        diskon.setBounds(480, 70, 0, 22);

        jLabel11.setFont(resourceMap.getFont("jLabel11.font")); // NOI18N
        jLabel11.setText(resourceMap.getString("jLabel11.text")); // NOI18N
        jLabel11.setName("jLabel11"); // NOI18N
        jPanel1.add(jLabel11);
        jLabel11.setBounds(370, 100, 0, 16);

        ppn.setFont(resourceMap.getFont("ppn.font")); // NOI18N
        ppn.setName("ppn"); // NOI18N
        ppn.setRequestFocusEnabled(false);
        ppn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ppnKeyPressed(evt);
            }
        });
        jPanel1.add(ppn);
        ppn.setBounds(480, 100, 0, 22);

        txtkodeFaktur.setFont(resourceMap.getFont("txtkodeFaktur.font")); // NOI18N
        txtkodeFaktur.setText(resourceMap.getString("txtkodeFaktur.text")); // NOI18N
        txtkodeFaktur.setName("txtkodeFaktur"); // NOI18N
        txtkodeFaktur.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtkodeFakturFocusLost(evt);
            }
        });
        txtkodeFaktur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtkodeFakturKeyPressed(evt);
            }
        });
        jPanel1.add(txtkodeFaktur);
        txtkodeFaktur.setBounds(130, 10, 100, 22);

        jLabel25.setFont(resourceMap.getFont("jLabel25.font")); // NOI18N
        jLabel25.setText(resourceMap.getString("jLabel25.text")); // NOI18N
        jLabel25.setName("jLabel25"); // NOI18N
        jPanel1.add(jLabel25);
        jLabel25.setBounds(470, 35, 100, 16);

        txtTglMasuk.addCommitListener(new datechooser.events.CommitListener() {
            public void onCommit(datechooser.events.CommitEvent evt) {
                txtTglMasukOnCommit(evt);
            }
        });
        jPanel1.add(txtTglMasuk);
        txtTglMasuk.setBounds(580, 10, 155, 20);

        CboNamaBank.setFont(resourceMap.getFont("CboNamaBank.font")); // NOI18N
        CboNamaBank.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        CboNamaBank.setName("CboNamaBank"); // NOI18N
        jPanel1.add(CboNamaBank);
        CboNamaBank.setBounds(220, 35, 140, 22);

        btnKeluar.setFont(resourceMap.getFont("btnKeluar.font")); // NOI18N
        btnKeluar.setIcon(resourceMap.getIcon("btnKeluar.icon")); // NOI18N
        btnKeluar.setText(resourceMap.getString("btnKeluar.text")); // NOI18N
        btnKeluar.setName("btnKeluar"); // NOI18N
        btnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKeluarActionPerformed(evt);
            }
        });
        jPanel1.add(btnKeluar);
        btnKeluar.setBounds(750, 10, 110, 40);

        btnGenerate.setFont(resourceMap.getFont("btnGenerate.font")); // NOI18N
        btnGenerate.setText(resourceMap.getString("btnGenerate.text")); // NOI18N
        btnGenerate.setName("btnGenerate"); // NOI18N
        btnGenerate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerateActionPerformed(evt);
            }
        });
        jPanel1.add(btnGenerate);
        btnGenerate.setBounds(370, 30, 70, 30);

        cboStatPPN.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "%", "CASH" }));
        cboStatPPN.setName("cboStatPPN"); // NOI18N
        cboStatPPN.setRequestFocusEnabled(false);
        jPanel1.add(cboStatPPN);
        cboStatPPN.setBounds(610, 100, 0, 20);

        cbPPN.setFont(resourceMap.getFont("cbPPN.font")); // NOI18N
        cbPPN.setName("cbPPN"); // NOI18N
        jPanel1.add(cbPPN);
        cbPPN.setBounds(130, 110, 30, 20);

        jLabel30.setFont(resourceMap.getFont("jLabel30.font")); // NOI18N
        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel30.setText(resourceMap.getString("jLabel30.text")); // NOI18N
        jLabel30.setName("jLabel30"); // NOI18N
        jPanel1.add(jLabel30);
        jLabel30.setBounds(20, 110, 40, 16);

        jTabbedPane2.addTab(resourceMap.getString("jPanel1.TabConstraints.tabTitle"), jPanel1); // NOI18N

        jPanel2.setName("jPanel2"); // NOI18N
        jPanel2.setLayout(null);

        txtkodeRetur.setEditable(false);
        txtkodeRetur.setFont(resourceMap.getFont("txtkodeRetur.font")); // NOI18N
        txtkodeRetur.setText(resourceMap.getString("txtkodeRetur.text")); // NOI18N
        txtkodeRetur.setName("txtkodeRetur"); // NOI18N
        txtkodeRetur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtkodeReturActionPerformed(evt);
            }
        });
        jPanel2.add(txtkodeRetur);
        txtkodeRetur.setBounds(130, 10, 140, 22);
        jPanel2.add(tglRetur);
        tglRetur.setBounds(130, 40, 150, 20);

        jLabel21.setFont(resourceMap.getFont("jLabel21.font")); // NOI18N
        jLabel21.setText(resourceMap.getString("jLabel21.text")); // NOI18N
        jLabel21.setName("jLabel21"); // NOI18N
        jPanel2.add(jLabel21);
        jLabel21.setBounds(20, 10, 90, 20);

        jLabel20.setFont(resourceMap.getFont("jLabel20.font")); // NOI18N
        jLabel20.setText(resourceMap.getString("jLabel20.text")); // NOI18N
        jLabel20.setName("jLabel20"); // NOI18N
        jPanel2.add(jLabel20);
        jLabel20.setBounds(20, 40, 90, 20);

        jLabel22.setFont(resourceMap.getFont("jLabel22.font")); // NOI18N
        jLabel22.setText(resourceMap.getString("jLabel22.text")); // NOI18N
        jLabel22.setName("jLabel22"); // NOI18N
        jPanel2.add(jLabel22);
        jLabel22.setBounds(20, 70, 120, 20);

        jLabel24.setFont(resourceMap.getFont("jLabel24.font")); // NOI18N
        jLabel24.setText(resourceMap.getString("jLabel24.text")); // NOI18N
        jLabel24.setName("jLabel24"); // NOI18N
        jPanel2.add(jLabel24);
        jLabel24.setBounds(20, 100, 120, 20);

        kodeSupplier1.setEditable(false);
        kodeSupplier1.setFont(resourceMap.getFont("kodeSupplier1.font")); // NOI18N
        kodeSupplier1.setName("kodeSupplier1"); // NOI18N
        kodeSupplier1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kodeSupplier1ActionPerformed(evt);
            }
        });
        kodeSupplier1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kodeSupplier1KeyPressed(evt);
            }
        });
        jPanel2.add(kodeSupplier1);
        kodeSupplier1.setBounds(130, 70, 150, 22);

        namaSupplier1.setEditable(false);
        namaSupplier1.setFont(resourceMap.getFont("namaSupplier1.font")); // NOI18N
        namaSupplier1.setName("namaSupplier1"); // NOI18N
        jPanel2.add(namaSupplier1);
        namaSupplier1.setBounds(130, 100, 270, 22);

        btnKeluar1.setFont(resourceMap.getFont("btnKeluar1.font")); // NOI18N
        btnKeluar1.setText(resourceMap.getString("btnKeluar1.text")); // NOI18N
        btnKeluar1.setName("btnKeluar1"); // NOI18N
        btnKeluar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKeluar1ActionPerformed(evt);
            }
        });
        jPanel2.add(btnKeluar1);
        btnKeluar1.setBounds(750, 103, 120, 30);

        jTabbedPane2.addTab(resourceMap.getString("jPanel2.TabConstraints.tabTitle"), jPanel2); // NOI18N

        panelCool1.add(jTabbedPane2);
        jTabbedPane2.setBounds(10, 10, 1070, 170);

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jTable1.setFont(resourceMap.getFont("jTable1.font")); // NOI18N
        jTable1.setName("jTable1"); // NOI18N
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        panelCool1.add(jScrollPane1);
        jScrollPane1.setBounds(10, 240, 1070, 300);

        jPanel4.setName("jPanel4"); // NOI18N
        jPanel4.setLayout(null);

        jLabel5.setFont(resourceMap.getFont("jLabel5.font")); // NOI18N
        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel5.setName("jLabel5"); // NOI18N
        jPanel4.add(jLabel5);
        jLabel5.setBounds(90, 0, 300, 20);

        kodeBarang.setFont(resourceMap.getFont("kodeBarang.font")); // NOI18N
        kodeBarang.setText(resourceMap.getString("kodeBarang.text")); // NOI18N
        kodeBarang.setName("kodeBarang"); // NOI18N
        kodeBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kodeBarangActionPerformed(evt);
            }
        });
        kodeBarang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kodeBarangKeyPressed(evt);
            }
        });
        jPanel4.add(kodeBarang);
        kodeBarang.setBounds(0, 20, 90, 22);

        namaBarang.setFont(resourceMap.getFont("namaBarang.font")); // NOI18N
        namaBarang.setText(resourceMap.getString("namaBarang.text")); // NOI18N
        namaBarang.setEnabled(false);
        namaBarang.setName("namaBarang"); // NOI18N
        jPanel4.add(namaBarang);
        namaBarang.setBounds(90, 20, 300, 22);

        jLabel18.setFont(resourceMap.getFont("jLabel18.font")); // NOI18N
        jLabel18.setText(resourceMap.getString("jLabel18.text")); // NOI18N
        jLabel18.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel18.setName("jLabel18"); // NOI18N
        jPanel4.add(jLabel18);
        jLabel18.setBounds(0, 0, 90, 20);

        jLabel7.setFont(resourceMap.getFont("jLabel7.font")); // NOI18N
        jLabel7.setText(resourceMap.getString("jLabel7.text")); // NOI18N
        jLabel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel7.setName("jLabel7"); // NOI18N
        jPanel4.add(jLabel7);
        jLabel7.setBounds(640, 0, 120, 20);

        jLabel6.setFont(resourceMap.getFont("jLabel6.font")); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel6.setName("jLabel6"); // NOI18N
        jPanel4.add(jLabel6);
        jLabel6.setBounds(620, 70, 90, 20);

        jumlahBarang.setFont(resourceMap.getFont("jumlahBarang.font")); // NOI18N
        jumlahBarang.setText(resourceMap.getString("jumlahBarang.text")); // NOI18N
        jumlahBarang.setName("jumlahBarang"); // NOI18N
        jumlahBarang.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jumlahBarangFocusLost(evt);
            }
        });
        jumlahBarang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jumlahBarangKeyPressed(evt);
            }
        });
        jPanel4.add(jumlahBarang);
        jumlahBarang.setBounds(760, 20, 90, 22);

        btnInsert.setFont(resourceMap.getFont("btnInsert.font")); // NOI18N
        btnInsert.setText(resourceMap.getString("btnInsert.text")); // NOI18N
        btnInsert.setName("btnInsert"); // NOI18N
        btnInsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertActionPerformed(evt);
            }
        });
        jPanel4.add(btnInsert);
        btnInsert.setBounds(910, 40, 80, 20);

        btnDelete.setFont(resourceMap.getFont("btnDelete.font")); // NOI18N
        btnDelete.setText(resourceMap.getString("btnDelete.text")); // NOI18N
        btnDelete.setName("btnDelete"); // NOI18N
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        jPanel4.add(btnDelete);
        btnDelete.setBounds(990, 40, 80, 20);

        jLabel19.setFont(resourceMap.getFont("jLabel19.font")); // NOI18N
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText(resourceMap.getString("jLabel19.text")); // NOI18N
        jLabel19.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel19.setName("jLabel19"); // NOI18N
        jPanel4.add(jLabel19);
        jLabel19.setBounds(1010, 0, 60, 20);

        txtPPN.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        txtPPN.setEnabled(false);
        txtPPN.setFont(resourceMap.getFont("txtPPN.font")); // NOI18N
        txtPPN.setName("txtPPN"); // NOI18N
        txtPPN.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPPNKeyPressed(evt);
            }
        });
        jPanel4.add(txtPPN);
        txtPPN.setBounds(620, 90, 70, 22);

        jTextField1.setFont(resourceMap.getFont("jTextField1.font")); // NOI18N
        jTextField1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField1.setText(resourceMap.getString("jTextField1.text")); // NOI18N
        jTextField1.setName("jTextField1"); // NOI18N
        jPanel4.add(jTextField1);
        jTextField1.setBounds(690, 90, 20, 22);

        jLabel23.setFont(resourceMap.getFont("jLabel23.font")); // NOI18N
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText(resourceMap.getString("jLabel23.text")); // NOI18N
        jLabel23.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel23.setName("jLabel23"); // NOI18N
        jPanel4.add(jLabel23);
        jLabel23.setBounds(850, 0, 160, 20);

        hargaBarang.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        hargaBarang.setFont(resourceMap.getFont("hargaBarang.font")); // NOI18N
        hargaBarang.setName("hargaBarang"); // NOI18N
        hargaBarang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                hargaBarangKeyPressed(evt);
            }
        });
        jPanel4.add(hargaBarang);
        hargaBarang.setBounds(640, 20, 120, 22);

        txtDiskon.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtDiskon.setFont(resourceMap.getFont("txtDiskon.font")); // NOI18N
        txtDiskon.setName("txtDiskon"); // NOI18N
        txtDiskon.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDiskonFocusLost(evt);
            }
        });
        txtDiskon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDiskonKeyPressed(evt);
            }
        });
        jPanel4.add(txtDiskon);
        txtDiskon.setBounds(850, 20, 110, 22);

        jLabel27.setFont(resourceMap.getFont("jLabel27.font")); // NOI18N
        jLabel27.setText(resourceMap.getString("jLabel27.text")); // NOI18N
        jLabel27.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel27.setName("jLabel27"); // NOI18N
        jPanel4.add(jLabel27);
        jLabel27.setBounds(580, 0, 60, 20);

        cboSatuan.setFont(resourceMap.getFont("cboSatuan.font")); // NOI18N
        cboSatuan.setName("cboSatuan"); // NOI18N
        cboSatuan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cboSatuanKeyPressed(evt);
            }
        });
        jPanel4.add(cboSatuan);
        cboSatuan.setBounds(580, 20, 60, 20);

        jLabel28.setFont(resourceMap.getFont("jLabel28.font")); // NOI18N
        jLabel28.setText(resourceMap.getString("jLabel28.text")); // NOI18N
        jLabel28.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel28.setName("jLabel28"); // NOI18N
        jPanel4.add(jLabel28);
        jLabel28.setBounds(390, 0, 80, 20);

        jLabel29.setFont(resourceMap.getFont("jLabel29.font")); // NOI18N
        jLabel29.setText(resourceMap.getString("jLabel29.text")); // NOI18N
        jLabel29.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel29.setName("jLabel29"); // NOI18N
        jPanel4.add(jLabel29);
        jLabel29.setBounds(470, 0, 110, 20);

        txtNoBatch.setFont(resourceMap.getFont("txtNoBatch.font")); // NOI18N
        txtNoBatch.setEnabled(false);
        txtNoBatch.setName("txtNoBatch"); // NOI18N
        txtNoBatch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNoBatchKeyPressed(evt);
            }
        });
        jPanel4.add(txtNoBatch);
        txtNoBatch.setBounds(390, 20, 80, 22);

        tglExpire.addCommitListener(new datechooser.events.CommitListener() {
            public void onCommit(datechooser.events.CommitEvent evt) {
                tglExpireOnCommit(evt);
            }
        });
        jPanel4.add(tglExpire);
        tglExpire.setBounds(470, 20, 110, 20);

        cboStatDiskon.setFont(resourceMap.getFont("cboStatDiskon.font")); // NOI18N
        cboStatDiskon.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "%", "Rp" }));
        cboStatDiskon.setName("cboStatDiskon"); // NOI18N
        cboStatDiskon.setRequestFocusEnabled(false);
        jPanel4.add(cboStatDiskon);
        cboStatDiskon.setBounds(960, 20, 50, 22);

        jLabel31.setFont(resourceMap.getFont("jLabel31.font")); // NOI18N
        jLabel31.setText(resourceMap.getString("jLabel31.text")); // NOI18N
        jLabel31.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel31.setName("jLabel31"); // NOI18N
        jPanel4.add(jLabel31);
        jLabel31.setBounds(760, 0, 90, 20);

        cbBonus.setFont(resourceMap.getFont("cbBonus.font")); // NOI18N
        cbBonus.setName("cbBonus"); // NOI18N
        cbBonus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cbBonusKeyPressed(evt);
            }
        });
        jPanel4.add(cbBonus);
        cbBonus.setBounds(1030, 20, 30, 20);

        panelCool1.add(jPanel4);
        jPanel4.setBounds(10, 180, 1070, 60);

        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setLayout(null);

        jLabel12.setFont(resourceMap.getFont("jLabel12.font")); // NOI18N
        jLabel12.setText(resourceMap.getString("jLabel12.text")); // NOI18N
        jLabel12.setName("jLabel12"); // NOI18N
        jPanel3.add(jLabel12);
        jLabel12.setBounds(20, 6, 110, 20);

        hasilTotal.setEditable(false);
        hasilTotal.setFont(resourceMap.getFont("hasilTotal.font")); // NOI18N
        hasilTotal.setName("hasilTotal"); // NOI18N
        jPanel3.add(hasilTotal);
        hasilTotal.setBounds(110, 6, 180, 22);

        jLabel13.setFont(resourceMap.getFont("jLabel13.font")); // NOI18N
        jLabel13.setText(resourceMap.getString("jLabel13.text")); // NOI18N
        jLabel13.setName("jLabel13"); // NOI18N
        jPanel3.add(jLabel13);
        jLabel13.setBounds(20, 30, 100, 20);

        hasilDiskon.setEditable(false);
        hasilDiskon.setFont(resourceMap.getFont("hasilDiskon.font")); // NOI18N
        hasilDiskon.setName("hasilDiskon"); // NOI18N
        hasilDiskon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                hasilDiskonKeyPressed(evt);
            }
        });
        jPanel3.add(hasilDiskon);
        hasilDiskon.setBounds(110, 30, 180, 22);

        jLabel14.setFont(resourceMap.getFont("jLabel14.font")); // NOI18N
        jLabel14.setText(resourceMap.getString("jLabel14.text")); // NOI18N
        jLabel14.setName("jLabel14"); // NOI18N
        jPanel3.add(jLabel14);
        jLabel14.setBounds(330, 8, 120, 20);

        jLabel15.setFont(resourceMap.getFont("jLabel15.font")); // NOI18N
        jLabel15.setText(resourceMap.getString("jLabel15.text")); // NOI18N
        jLabel15.setName("jLabel15"); // NOI18N
        jPanel3.add(jLabel15);
        jLabel15.setBounds(330, 55, 90, 20);

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
        hasilBayar.setBounds(440, 30, 190, 22);

        hasilSisa.setEditable(false);
        hasilSisa.setFont(resourceMap.getFont("hasilSisa.font")); // NOI18N
        hasilSisa.setName("hasilSisa"); // NOI18N
        jPanel3.add(hasilSisa);
        hasilSisa.setBounds(440, 54, 190, 22);

        jLabel17.setFont(resourceMap.getFont("jLabel17.font")); // NOI18N
        jLabel17.setText(resourceMap.getString("jLabel17.text")); // NOI18N
        jLabel17.setName("jLabel17"); // NOI18N
        jPanel3.add(jLabel17);
        jLabel17.setBounds(20, 80, 110, 20);

        hasilTotalBayar.setEditable(false);
        hasilTotalBayar.setFont(resourceMap.getFont("hasilTotalBayar.font")); // NOI18N
        hasilTotalBayar.setName("hasilTotalBayar"); // NOI18N
        jPanel3.add(hasilTotalBayar);
        hasilTotalBayar.setBounds(110, 80, 180, 22);

        jLabel16.setFont(resourceMap.getFont("jLabel16.font")); // NOI18N
        jLabel16.setText(resourceMap.getString("jLabel16.text")); // NOI18N
        jLabel16.setName("jLabel16"); // NOI18N
        jPanel3.add(jLabel16);
        jLabel16.setBounds(20, 60, 70, 14);

        hasilppn.setEditable(false);
        hasilppn.setFont(resourceMap.getFont("hasilppn.font")); // NOI18N
        hasilppn.setName("hasilppn"); // NOI18N
        jPanel3.add(hasilppn);
        hasilppn.setBounds(110, 56, 180, 22);

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
        btnSimpanHasil.setBounds(680, 8, 150, 50);

        jButton3.setFont(resourceMap.getFont("jButton3.font")); // NOI18N
        jButton3.setText(resourceMap.getString("jButton3.text")); // NOI18N
        jButton3.setName("jButton3"); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton3);
        jButton3.setBounds(680, 40, 0, 25);

        btnFaktur.setFont(resourceMap.getFont("btnFaktur.font")); // NOI18N
        btnFaktur.setText(resourceMap.getString("btnFaktur.text")); // NOI18N
        btnFaktur.setName("btnFaktur"); // NOI18N
        btnFaktur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFakturActionPerformed(evt);
            }
        });
        jPanel3.add(btnFaktur);
        btnFaktur.setBounds(680, 60, 150, 40);

        jLabel26.setFont(resourceMap.getFont("jLabel26.font")); // NOI18N
        jLabel26.setText(resourceMap.getString("jLabel26.text")); // NOI18N
        jLabel26.setName("jLabel26"); // NOI18N
        jPanel3.add(jLabel26);
        jLabel26.setBounds(330, 30, 100, 20);

        DiskonTambah.setFont(resourceMap.getFont("DiskonTambah.font")); // NOI18N
        DiskonTambah.setName("DiskonTambah"); // NOI18N
        DiskonTambah.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                DiskonTambahCaretUpdate(evt);
            }
        });
        DiskonTambah.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                DiskonTambahFocusGained(evt);
            }
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
        DiskonTambah.setBounds(440, 6, 190, 22);

        panelCool1.add(jPanel3);
        jPanel3.setBounds(10, 540, 1070, 120);

        getContentPane().add(panelCool1, java.awt.BorderLayout.CENTER);

        setBounds(0, 0, 1105, 713);
    }// </editor-fold>//GEN-END:initComponents

    private void jTable2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable2KeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == 10) {
            //        Connection c = null;
            try {
                //            c = koneksi.getKoneksiJ();
                barang b = new barang();
                b = dbbarang.getDetails(c, jTable2.getValueAt(jTable2.getSelectedRow(), 0).toString());
                try {
                    cboSatuan.removeAllItems();
                } catch (Exception e) {
                }
                for (Iterator<Object> it = b.getItemSatuan().iterator(); it.hasNext();) {
                    Object a = it.next();
                    if (!a.equals("-")) {
                        cboSatuan.addItem(a);
                    }
                }
                kodeBarang.setText(b.getKODEBARANG());
                namaBarang.setText(b.getNAMABARANG());
                if (jTable2.getValueAt(jTable2.getSelectedRow(), 2) != null) {
                    txtNoBatch.setText(jTable2.getValueAt(jTable2.getSelectedRow(), 2).toString());
                    Calendar cld = Calendar.getInstance();
                    cld.setTime(d.parse(jTable2.getValueAt(jTable2.getSelectedRow(), 3).toString()));
                    tglExpire.setSelectedDate(cld);
                }
                jScrollPane2.setVisible(false);
                cboSatuan.requestFocus();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
            //        finally {
            //            if (c != null) {
            //                try {
            //                    c.close();
            //                } catch (SQLException ex) {
            //                    Logger.getLogger(DialogPembelian.class.getName()).log(Level.SEVERE, null, ex);
            //                }
            //            }
            //        }

        }
    }//GEN-LAST:event_jTable2KeyPressed

    private void nofakturKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nofakturKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == 10) {
            kodeSupplier.requestFocus();
        }
    }//GEN-LAST:event_nofakturKeyPressed

    private void tanggalOnCommit(datechooser.events.CommitEvent evt) {//GEN-FIRST:event_tanggalOnCommit
        try {
            // TODO add your handling code here:
            //Connection c = koneksi.getKoneksiJ();
            settingTgl(c);
            //c.close();
        } catch (Exception ex) {
            Logger.getLogger(DialogPembelian.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_tanggalOnCommit

    private void jTable3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable3KeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == 10) {
            //Connection c = null;
            try {
                //c = koneksi.getKoneksiJ();
                supplier b = new supplier();
                b = supplierDao.getDetails(c, jTable3.getValueAt(jTable3.getSelectedRow(), 0).toString());
                if (pilSupplier.equals("0")) {
                    kodeSupplier.setText(b.getIDSUPPLIER());
                    namaSupplier.setText(b.getNAMA());
                    jScrollPane3.setVisible(false);
                    kodeBarang.requestFocus();
                } else if (pilSupplier.equals("1")) {
                    kodeSupplier1.setText(b.getIDSUPPLIER());
                    namaSupplier1.setText(b.getNAMA());
                    jScrollPane3.setVisible(false);
                    kodeBarang.requestFocus();
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
            //        finally {
            //            if (c != null) {
            //                try {
            //                    c.close();
            //                } catch (SQLException ex) {
            //                    Logger.getLogger(DialogPembelian.class.getName()).log(Level.SEVERE, null, ex);
            //                }
            //            }
            //        }
        }
    }//GEN-LAST:event_jTable3KeyPressed

    private void kodeSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kodeSupplierActionPerformed
        //Connection c = null;
        try {
            //c = koneksi.getKoneksiJ();
            // TODO add your handling code here:
            jScrollPane3.setVisible(true);
            JDBCAdapter ja = new JDBCAdapter(c);
            ja.executeQuery("select IDSUPPLIER,NAMA,ALAMAT from SUPPLIER where STATUSAKTIF='0' AND IDSUPPLIER like '" + kodeSupplier.getText() + "%' or lower(NAMA) like '%" + kodeSupplier.getText().toLowerCase() + "%'");
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
            ja.close();
        } catch (SQLException ex) {
            Logger.getLogger(DialogPembelian.class.getName()).log(Level.SEVERE, null, ex);
        }
        //    finally {
        //        if (c != null) {
        //            try {
        //                c.close();
        //            } catch (SQLException ex) {
        //                Logger.getLogger(DialogPembelian.class.getName()).log(Level.SEVERE, null, ex);
        //            }
        //        }
        //    }
    }//GEN-LAST:event_kodeSupplierActionPerformed

    private void kodeSupplierKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kodeSupplierKeyPressed
        // TODO add your handling code here:
        System.out.print(evt.getKeyCode());
        if (evt.getKeyCode() == 40) {
            pilSupplier = "0";
            jScrollPane3.setVisible(true);
            jTable3.requestFocus();
            jTable3.getSelectionModel().setSelectionInterval(0, 0);
        }
        if (evt.getKeyCode() == 27) {
            jScrollPane3.setVisible(false);
        }
    }//GEN-LAST:event_kodeSupplierKeyPressed

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        try {
            // TODO add your handling code here:
            //Connection c = koneksi.getKoneksiJ();
            kosongFaktur();
            kosongBarang();
            kosongHasil();
            hapusTabel();
            buatTabel();
            total = 0;
            hpp = 0;
            setFaktur(c);
            reloadData(c);
            //c.close();
        } catch (SQLException ex) {
            Logger.getLogger(DialogPembelian.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnTambahActionPerformed

    private void cbostatusBeliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbostatusBeliActionPerformed
        try {
            // TODO add your handling code here:
            //Connection c = koneksi.getKoneksiJ();
            cekpembayaran(c);
            //c.close();
        } catch (SQLException ex) {
            Logger.getLogger(DialogPembelian.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_cbostatusBeliActionPerformed

    private void diskonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_diskonKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == 10) {
            try {
                //Connection c = koneksi.getKoneksiJ();
                hasilAkhir(c);
                //c.close();
                ppn.requestFocus();
            } catch (SQLException ex) {
                Logger.getLogger(DialogPembelian.class.getName()).log(Level.SEVERE, null, ex);
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
                kodeBarang.requestFocus();
            } catch (SQLException ex) {
                Logger.getLogger(DialogPembelian.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_ppnKeyPressed

    private void txtkodeFakturFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtkodeFakturFocusLost
        //Connection c = null;
        try {
            //c = koneksi.getKoneksiJ();
            // TODO add your handling code here:
            if (!pembelianDao.cekFaktur(c, txtkodeFaktur.getText())) {
                nofaktur.requestFocus();
            } else {
                JOptionPane.showMessageDialog(null, "Nomor Faktur Pembelian Ini Sudah Ada.. !");
                txtkodeFaktur.requestFocus();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DialogPembelian.class.getName()).log(Level.SEVERE, null, ex);
        }
        //        finally {
        //            if (c != null) {
        //                try {
        //                    c.close();
        //                } catch (SQLException ex) {
        //                    Logger.getLogger(DialogPembelian.class.getName()).log(Level.SEVERE, null, ex);
        //                }
        //            }
        //        }
    }//GEN-LAST:event_txtkodeFakturFocusLost

    private void txtkodeFakturKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtkodeFakturKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == 10) {
            nofaktur.requestFocus();
        }
    }//GEN-LAST:event_txtkodeFakturKeyPressed

    private void txtTglMasukOnCommit(datechooser.events.CommitEvent evt) {//GEN-FIRST:event_txtTglMasukOnCommit
        // TODO add your handling code here:
        Calendar cal = Calendar.getInstance();
        //cal.setTime(Fungsi.dateAdd(c, "DAY", 30, java.sql.Date.valueOf(tanggal.getText())));
        cal.setTime(java.sql.Date.valueOf(txtTglMasuk.getText()));
        tanggal.setSelectedDate(cal);
    }//GEN-LAST:event_txtTglMasukOnCommit

    private void btnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_btnKeluarActionPerformed

    private void btnGenerateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerateActionPerformed
        try {
            // TODO add your handling code here:
            //Connection c = koneksi.getKoneksiJ();
            setFaktur(c);
            //c.close();
        } catch (SQLException ex) {
            Logger.getLogger(DialogPembelian.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnGenerateActionPerformed

    private void txtkodeReturActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtkodeReturActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtkodeReturActionPerformed

    private void kodeSupplier1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kodeSupplier1ActionPerformed
        //    Connection c = null;
        try {
            // TODO add your handling code here:
            //        c = koneksi.getKoneksiJ();
            pilSupplier = "1";
            jScrollPane3.setVisible(true);
            JDBCAdapter ja = new JDBCAdapter(c);
            ja.executeQuery("select IDSUPPLIER,NAMA from SUPPLIER where IDSUPPLIER like '" + kodeSupplier1.getText() + "%' or lower(NAMA) like '" + kodeSupplier1.getText().toLowerCase() + "%'");
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
            Logger.getLogger(DialogPembelian.class.getName()).log(Level.SEVERE, null, ex);
        }
        //    finally {
        //        if (c != null) {
        //            try {
        //                c.close();
        //            } catch (SQLException ex) {
        //                Logger.getLogger(DialogPembelian.class.getName()).log(Level.SEVERE, null, ex);
        //            }
        //        }
        //    }
    }//GEN-LAST:event_kodeSupplier1ActionPerformed

    private void kodeSupplier1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kodeSupplier1KeyPressed
        // TODO add your handling code here:
        System.out.print(evt.getKeyCode());
        if (evt.getKeyCode() == 40) {
            pilSupplier = "1";
            jScrollPane3.setVisible(true);
            jTable3.requestFocus();
            jTable3.getSelectionModel().setSelectionInterval(0, 0);
        }
        if (evt.getKeyCode() == 27) {
            jScrollPane3.setVisible(false);
        }
    }//GEN-LAST:event_kodeSupplier1KeyPressed

    private void btnKeluar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKeluar1ActionPerformed
        dispose();
    }//GEN-LAST:event_btnKeluar1ActionPerformed

    private void jTabbedPane2StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jTabbedPane2StateChanged
        try {
            // TODO add your handling code here:
            //Connection c = koneksi.getKoneksiJ();
            if (jTabbedPane2.getSelectedIndex() == 0) {
                kodeSupplier.requestFocus();
            } else if (jTabbedPane2.getSelectedIndex() == 1) {
                setRetur(c);
                kodeSupplier1.requestFocus();
            }
            //c.close();
        } catch (SQLException ex) {
            Logger.getLogger(DialogPembelian.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jTabbedPane2StateChanged

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        // TODO add your handling code here:
        if (evt.getClickCount() == 2 && !isStatedit()) {
            setStatedit(true);
            //Connection c = null;
            try {
                //c = koneksi.getKoneksiJ();
                JTable target = (JTable) evt.getSource();
                int row = target.getSelectedRow();
                Statement s = cm.createStatement();
                setNolama(Integer.parseInt(jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString()));
                ResultSet rs = s.executeQuery("select * from rinci where NO=" + jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString() + "");
                if (rs.next()) {
                    no = rs.getInt(1);
                    kodeBarang.setText(rs.getString(2));
                    namaBarang.setText(rs.getString(3));
                    txtNoBatch.setText(rs.getString(4));
                    if ((!rs.getString("EXPIRE").equals("")) && (rs.getString("EXPIRE") != null)) {
                        Calendar cld = Calendar.getInstance();
                        cld.setTime(d.parse(rs.getString("EXPIRE")));
                        tglExpire.setSelectedDate(cld);
                    }
                    jumlahBarang.setText(rs.getString(6));
                    hargaBarang.setValue(rs.getDouble(7));
                    txtDiskon.setValue(rs.getDouble(13));
                    cboStatDiskon.setSelectedIndex(0);
                    if (rs.getDouble(9) > 0) {
                        cbPPN.setSelected(true);
                    } else {
                        cbPPN.setSelected(false);
                    }
                    barang b = new barangDao().getDetails(c, kodeBarang.getText());
                    try {
                        cboSatuan.removeAllItems();
                    } catch (Exception e) {
                    }
                    for (Object a : b.getItemSatuan()) {
                        if (!a.equals("-")) {
                            cboSatuan.addItem(a);
                        }
                    }
                    cboSatuan.setSelectedItem(rs.getString("SATUAN"));
                    if (rs.getString("BONUS").equals("Bonus")) {
                        cbBonus.setSelected(true);
                    }
                }
                s.execute("delete from rinci where NO='" + jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString() + "'");
                total = 0;
                hpp = 0;
                DISKONItem = 0;
                PPNItem = 0;
                reloadData(c);
                rs.close();
                s.close();
            } catch (SQLException ex) {
                Logger.getLogger(DialogPembelian.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger(DialogPembelian.class.getName()).log(Level.SEVERE, null, ex);
            }
            //        finally {
            //            if (c != null) {
            //                try {
            //                    c.close();
            //                } catch (SQLException ex) {
            //                    Logger.getLogger(DialogPembelian.class.getName()).log(Level.SEVERE, null, ex);
            //                }
            //            }
            //        }
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void kodeBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kodeBarangActionPerformed
        //Connection c = null;
        try {
            //c = koneksi.getKoneksiJ();
            // TODO add your handling code here:
            jScrollPane2.setVisible(true);
            JDBCAdapter ja = new JDBCAdapter(c);
            String sql = "select BARANG.KODEBARANG,BARANG.NAMABARANG,"
                    + "bsb.KODEBATCH , EXPIRE,"
                    + "bs.HARGAJUAL as `Jual`,"
                    + "CASEWHEN(KODEBATCH is null,bs.STOK,bsb.STOK) as STOK, "
                    + "JENISBARANG.JENIS "
                    + "from BARANG,JENISBARANG,KATEGORI "
                    + "inner join BARANGSTOK bs on bs.KODEBARANG=BARANG.KODEBARANG "
                    + "left join BARANGSTOKBATCH bsb on bs.ID=bsb.IDBARANGSTOK "
                    + "where BARANG.IDJENIS=JENISBARANG.ID AND BARANG.STATUS='0' "
                    + "AND BARANG.IDKATEGORI=KATEGORI.IDKATEGORI "
                    + "AND (BARANG.KODEBARANG like '" + kodeBarang.getText() + "%' "
                    + "OR lower(BARANG.NAMABARANG) like '%" + kodeBarang.getText().toLowerCase() + "%' "
                    + "OR bsb.KODEBATCH like '%" + kodeBarang.getText() + "%')";
            //        ja.executeQuery("select b.KODEBARANG,b.NAMABARANG, j.JENIS as Merk,bs.STOK,bs.COGS "
            //                + "from BARANG b, JENISBARANG j, barangstok as bs "
            //                + "where b.IDJENIS=j.ID and b.kodebarang=bs.kodebarang "
            //                + "AND (b.KODEBARANG like '" + kodeBarang.getText() + "%' or lower(b.NAMABARANG) like '%" + kodeBarang.getText().toLowerCase() + "%') order by b.KODEBARANG");
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
            ja.close();
        } catch (SQLException ex) {
            Logger.getLogger(DialogPembelian.class.getName()).log(Level.SEVERE, null, ex);
        }
        //    finally {
        //        if (c != null) {
        //            try {
        //                c.close();
        //            } catch (SQLException ex) {
        //                Logger.getLogger(DialogPembelian.class.getName()).log(Level.SEVERE, null, ex);
        //            }
        //        }
        //    }
    }//GEN-LAST:event_kodeBarangActionPerformed

    private void kodeBarangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kodeBarangKeyPressed
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
    }//GEN-LAST:event_kodeBarangKeyPressed

    private void jumlahBarangFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jumlahBarangFocusLost
        // TODO add your handling code here:
        if (jumlahBarang.getText().length() == 0) {
            jumlahBarang.setText("0");
        }
    }//GEN-LAST:event_jumlahBarangFocusLost

    private void jumlahBarangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jumlahBarangKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == 10) {
            txtDiskon.requestFocus();
        }
    }//GEN-LAST:event_jumlahBarangKeyPressed

    private void btnInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertActionPerformed
        // TODO add your handling code here:
        //    Connection c = null;
        try {
            //        c = koneksi.getKoneksiJ();
            if (barangDao.cekKodeBarang(c, kodeBarang.getText()) != true) {
                JOptionPane.showMessageDialog(null, "Produk Ini Tidak Ada..!!");
                kosongBarang();
                kodeBarang.requestFocus();
            } else if (kodeBarang.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Data Produk Belum Diisi..!!");
                kosongBarang();
                kodeBarang.requestFocus();
            } else if (Integer.parseInt(jumlahBarang.getText()) <= 0) {
                JOptionPane.showMessageDialog(null, "Jumlah Barang tidak Valid");
                jumlahBarang.requestFocus();
            } //        else if (cekBarang(c)) {
            //            JOptionPane.showMessageDialog(null, "Barang Ini Sudah Di Input");
            //            kosongBarang();
            //            kodeBarang.requestFocus();
            //        }
            else {
                double ppn = 0;
                barang b = new barangDao().getDetails(c, kodeBarang.getText());
                double tot = Integer.parseInt(jumlahBarang.getText()) * Double.parseDouble(hargaBarang.getValue().toString());
                double tdiskon1 = getDiskon(Integer.parseInt(jumlahBarang.getText()), Double.parseDouble(hargaBarang.getValue().toString()));
                //double tppn1 = (Double.parseDouble(txtPPN.getValue().toString()) / 100) * (tot - tdiskon1); Lama
                double tdiskonpersen = getDiskonPersen(Integer.parseInt(jumlahBarang.getText()), Double.parseDouble(hargaBarang.getValue().toString()));
                double tppn1 = getPPN(tot - tdiskon1);

                int no = jTable1.getRowCount() + 1;
                if (statedit) {
                    no = getNolama();
                }
                stat.execute("insert into rinci values("
//                        + no //0
                        + ",'" + kodeBarang.getText() //1
                        + "','" + namaBarang.getText() //2
                        + "','" + txtNoBatch.getText() //3
                        + "','" + ((txtNoBatch.getText().equals("")) ? "" : tglExpire.getText()) //4
                        + "'," + jumlahBarang.getText() //5
                        + "," + hargaBarang.getValue() //6
                        + "," + tdiskon1 //7
                        + "," + tppn1 //8
                        + ",'" + cboSatuan.getSelectedItem().toString() //9
                        + "'," + (tot - tdiskon1 + tppn1) //10
                        + "," + b.getJumlah(Integer.parseInt(jumlahBarang.getText()), cboSatuan.getSelectedItem().toString()) //11
                        + "," + tdiskonpersen //12
                        + ",'" + ((cbBonus.isSelected()) ? "Bonus" : "") //13
                        + "')");
                total = 0;
                hpp = 0;
                PPNItem = 0;
                DISKONItem = 0;
                diskont = 0;
                reloadData(c);
                kosongBarang();
                kodeBarang.requestFocus();
                setStatedit(false);
                setNolama(0);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.toString());
        }
        //    finally {
        //        if (c != null) {
        //            try {
        //                c.close();
        //            } catch (SQLException ex) {
        //                Logger.getLogger(DialogPembelian.class.getName()).log(Level.SEVERE, null, ex);
        //            }
        //        }
        //    }
    }//GEN-LAST:event_btnInsertActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        try {
            // TODO add your handling code here:
            //Connection c = koneksi.getKoneksiJ();
            total = 0;
            hpp = 0;
            DISKONItem = 0;
            PPNItem = 0;
            reloadData(c);
            kosongBarang();
            kodeBarang.requestFocus();
            setStatedit(false);
            //c.close();
        } catch (Exception ex) {
            Logger.getLogger(DialogPembelian.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void txtPPNKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPPNKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == 10) {
            btnInsert.requestFocus();
        }
    }//GEN-LAST:event_txtPPNKeyPressed

    private void hargaBarangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_hargaBarangKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == 10) {
            jumlahBarang.requestFocus();
        }
    }//GEN-LAST:event_hargaBarangKeyPressed

    private void txtDiskonFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDiskonFocusLost
        // TODO add your handling code here:
        if (txtDiskon.getText().equals("")) {
            txtDiskon.setText("0");
            cbBonus.requestFocus();
        } else {
            cbBonus.requestFocus();
        }
    }//GEN-LAST:event_txtDiskonFocusLost

    private void txtDiskonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDiskonKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == 10) {
            //txtPPN.setValue(10);
            cbBonus.requestFocus();
        }
    }//GEN-LAST:event_txtDiskonKeyPressed

    private void cboSatuanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cboSatuanKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            hargaBarang.requestFocus();
        }
    }//GEN-LAST:event_cboSatuanKeyPressed

    private void txtNoBatchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNoBatchKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            cboSatuan.requestFocus();
        }
    }//GEN-LAST:event_txtNoBatchKeyPressed

    private void tglExpireOnCommit(datechooser.events.CommitEvent evt) {//GEN-FIRST:event_tglExpireOnCommit
        // TODO add your handling code here:
    }//GEN-LAST:event_tglExpireOnCommit

    private void cbBonusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbBonusKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnInsert.requestFocus();
        }
    }//GEN-LAST:event_cbBonusKeyPressed

    private void hasilDiskonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_hasilDiskonKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == 10) {
            try {
                //thasilDiskon = Double.parseDouble(hasilDiskon.getText());
                //Connection c = koneksi.getKoneksiJ();
                hasilBayar.requestFocus();
                hasilAkhir(c);
                //c.close();
            } //hasilDiskon.setText(com.erv.function.Util.toMoney(thasilDiskon));
            catch (SQLException ex) {
                Logger.getLogger(DialogPembelian.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_hasilDiskonKeyPressed

    private void hasilBayarCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_hasilBayarCaretUpdate
        //// TODO add your handling code here:
        //    if (getTextDouble(hasilBayar) > sisa) {
        //        JOptionPane.showMessageDialog(null, "Jumlah Bayar Tidak Boleh Besar Dari Nilai Hutang.. !");
        //
        //        hasilBayar.requestFocus();
        //    } else
        //    cekpembayaran();

        //    if (cbostatusBeli.getSelectedIndex() == 1) {
        //        hasilBayar.setText("0");
        //
        //        if (getTextDouble(hasilBayar) > sisa) {
        //            JOptionPane.showMessageDialog(null, "Jumlah Bayar Tidak Boleh Besar Dari Nilai Hutang.. !");
        //            hasilBayar.setText("0");
        //            hasilBayar.requestFocus();
        //        }else{
        if (hasilBayar.getText().length() > 0) {
            sisa = ttotalbayar - getTextDouble(hasilBayar);
            hasilSisa.setText(com.erv.function.Util.toMoney(sisa));
        } else {
            hasilSisa.setText(com.erv.function.Util.toMoney(0));
        }
                //        }
        //    }
    }//GEN-LAST:event_hasilBayarCaretUpdate

    private void hasilBayarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_hasilBayarFocusGained
        // TODO add your handling code here:
        if (cbostatusBeli.getSelectedIndex() == 0) {
            //reloadData();
            hasilBayar.setText("" + df0.format(ttotalbayar));
            btnSimpanHasil.requestFocus();
        } else if (cbostatusBeli.getSelectedIndex() == 2) {
            //hasilBayar.setText("0");
            //reloadData();
            hasilBayar.setText("" + df0.format(ttotalbayar));
            btnSimpanHasil.requestFocus();
        }
    }//GEN-LAST:event_hasilBayarFocusGained

    private void hasilBayarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_hasilBayarKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == 10) {
            try {
                //Connection c = koneksi.getKoneksiJ();
                if (sisa > 0) {
                    cbostatusBeli.setSelectedIndex(1);
                    //settingTgl(c);
                } else {
                    cbostatusBeli.setSelectedIndex(0);
                }
                //c.close();
                btnSimpanHasil.requestFocus();
            } catch (Exception ex) {
                Logger.getLogger(DialogPembelian.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_hasilBayarKeyPressed

    private void btnSimpanHasilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanHasilActionPerformed
        //Connection c = null;
        try {
            // TODO add your handling code here:
            //c = koneksi.getKoneksiJ();
            c.createStatement().execute("set autocommit false");
            if (hasilBayar.getText().equals("")) {
                hasilBayar.setText("0");
            }
            sisa = ttotalbayar - Double.parseDouble(hasilBayar.getText());
            hasilSisa.setText(com.erv.function.Util.toMoney(sisa));
            tdp = Float.parseFloat(hasilBayar.getText());
            hasilAkhir(c);
            int x = JOptionPane.showConfirmDialog(this, "Apakah Data akan Disimpan?", "", JOptionPane.YES_NO_OPTION);
            if (x == 0) {
                if (jTabbedPane2.getSelectedIndex() == 0) {
                    if (!pembelianDao.cekFaktur(c, txtkodeFaktur.getText())) {
                        if (kodeSupplier.getText().equals("")) {
                            JOptionPane.showMessageDialog(null, "Data Belum Lengkap.. !");
                            kodeSupplier.requestFocus();
                        } else if (cbostatusBeli.getSelectedItem().equals("-")) {
                            JOptionPane.showMessageDialog(null, "Cara Bayar Belum Di pilih !");
                            cbostatusBeli.requestFocus();
                        } else {
                            String tgl[] = Util.split(txtTglMasuk.getText(), "-");
                            String per = tgl[0] + "." + Integer.parseInt(tgl[1]);
                            if (cekperiodeAda(c, per)) {
                                if (cekperiode(c, per)) {
                                    aksilog = "InsertBeli";
                                    prosesSimpan(c);
                                    insertJurnal(c, "BELI");
                                    insertRinciJurnal(c);
                                    prosesUpdateLog(c);
                                    selesai(c);
                                    cetakFaktur(c);
                                    c.commit();
                                    IDBELI=0;
                                    JOptionPane.showMessageDialog(null, "Transaksi Pembelian Ok..");
                                } else {
                                    JOptionPane.showMessageDialog(null, "Transaksi Untuk Periode Ini Sudah Di Tutup.. !");
                                    kodeSupplier.requestFocus();
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "Transaksi Untuk Periode Ini Belum Dibuka.. !");
                                kodeSupplier.requestFocus();
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "No Faktur Sudah Ada !");
                    }
                } else {
                    if (kodeSupplier.getText().equals("")) {
                        JOptionPane.showMessageDialog(null, "Data Belum Lengkap.. !");
                        kodeSupplier1.requestFocus();
                    } else {
                        String tgl[] = Util.split(tglRetur.getText(), "-");
                        String per = tgl[0] + "." + Integer.parseInt(tgl[1]);
                        if (cekperiodeAda(c, per)) {
                            if (cekperiode(c, per)) {
                                aksilog = "InsertReturBeli";
                                prosesSimpanRetur(c);
                                //insertJurnal(c, "RETUR");
                                //insertRinciJurnalRetur(c);
                                prosesUpdateLog(c);
                                selesai(c);
                                cetakRetur(c);
                                c.commit();
                                IDBELI=0;
                                JOptionPane.showMessageDialog(null, "Transaksi Retur Pembelian Ok..");
                            } else {
                                JOptionPane.showMessageDialog(null, "Transaksi Untuk Periode Ini Sudah Di Tutup.. !");
                                kodeSupplier.requestFocus();
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Transaksi Untuk Periode Ini Belum Dibuka.. !");
                            kodeSupplier.requestFocus();
                        }
                    }
                }
            } else {
                System.out.print("tidak");
            }
        } catch (SQLException ex) {
            try {
                c.rollback();
                JOptionPane.showMessageDialog(this, "Rollback :" + ex.getMessage());
            } catch (SQLException ex1) {
                Logger.getLogger(DialogPembelian.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DialogPembelian.class.getName()).log(Level.SEVERE, null, ex);
        }
        //    finally {
        //        if (c != null) {
        //            try {
        //                c.createStatement().execute("set autocommit true");
        //                c.close();
        //            } catch (SQLException ex) {
        //                Logger.getLogger(DialogPembelian.class.getName()).log(Level.SEVERE, null, ex);
        //            }
        //        }
        //    }
    }//GEN-LAST:event_btnSimpanHasilActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        sisa = ttotalbayar - Double.parseDouble(hasilBayar.getText());
        hasilSisa.setText(com.erv.function.Util.toMoney(sisa));
        tdp = Float.parseFloat(hasilBayar.getText());
        int x = JOptionPane.showConfirmDialog(this, "Apakah Data Di Update?", "", JOptionPane.YES_NO_OPTION);
        if (x == 0) {
            //Connection c = null;
            try {
                //c = koneksi.getKoneksiJ();
                hasilAkhir(c);
                prosesUpdate(c);
                insertJurnal(c, "BELI");
                insertRinciJurnal(c);
                selesai(c);
            } catch (SQLException ex) {
                Logger.getLogger(DialogPembelian.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(DialogPembelian.class.getName()).log(Level.SEVERE, null, ex);
            }
            //        finally {
            //            if (c != null) {
            //                try {
            //                    c.close();
            //                } catch (SQLException ex) {
            //                    Logger.getLogger(DialogPembelian.class.getName()).log(Level.SEVERE, null, ex);
            //                }
            //            }
            //        }
        } else {
            System.out.print("tidak");
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void btnFakturActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFakturActionPerformed
        try {
            // TODO add your handling code here:
            //Connection c = koneksi.getKoneksiJ();
            cetakFaktur(c);
            //c.close();
        } catch (Exception ex) {
            Logger.getLogger(DialogPembelian.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnFakturActionPerformed

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

    private void DiskonTambahFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_DiskonTambahFocusGained
        // TODO add your handling code here:

        //        if (cbostatusBeli.getSelectedIndex() == 0) {
        //            reloadData();
        //            hasilBayar.setText("" + ttotalbayar);
        //            btnSimpanHasil.requestFocus();
        //        } else if (cbostatusBeli.getSelectedIndex() == 1) {
        //            hasilBayar.setText("0");
        //            reloadData();
        //            hasilBayar.requestFocus();
        //        }
    }//GEN-LAST:event_DiskonTambahFocusGained

    private void DiskonTambahFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_DiskonTambahFocusLost
        try {
            // TODO add your handling code here:
            //Connection c = koneksi.getKoneksiJ();
            reloadData(c);
            //c.close();
        } catch (Exception ex) {
            Logger.getLogger(DialogPembelian.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_DiskonTambahFocusLost

    private void DiskonTambahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiskonTambahKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == 10) {
            try {
                if (DiskonTambah.getText().equals("")) {
                    DiskonTambah.setText("0");
                }
                hasilBayar.requestFocus();
                //Connection c = koneksi.getKoneksiJ();
                reloadData(c);
                //c.close();
            } catch (Exception ex) {
                Logger.getLogger(DialogPembelian.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_DiskonTambahKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox CboNamaBank;
    private javax.swing.JTextField DiskonTambah;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnFaktur;
    private javax.swing.JButton btnGenerate;
    private javax.swing.JButton btnInsert;
    private javax.swing.JButton btnKeluar;
    private javax.swing.JButton btnKeluar1;
    private javax.swing.JButton btnSimpanHasil;
    private javax.swing.JButton btnTambah;
    private javax.swing.JCheckBox cbBonus;
    private javax.swing.JCheckBox cbPPN;
    private javax.swing.JComboBox cboSatuan;
    private javax.swing.JComboBox cboStatDiskon;
    private javax.swing.JComboBox cboStatPPN;
    private javax.swing.JComboBox cbostatusBeli;
    private javax.swing.JTextField diskon;
    private javax.swing.JFormattedTextField hargaBarang;
    private javax.swing.JTextField hasilBayar;
    private javax.swing.JTextField hasilDiskon;
    private javax.swing.JTextField hasilSisa;
    private javax.swing.JTextField hasilTotal;
    private javax.swing.JTextField hasilTotalBayar;
    private javax.swing.JTextField hasilppn;
    private javax.swing.JButton jButton3;
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
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jumlahBarang;
    private javax.swing.JTextField kodeBarang;
    private javax.swing.JTextField kodeSupplier;
    private javax.swing.JTextField kodeSupplier1;
    private javax.swing.JTextField namaBarang;
    private javax.swing.JTextField namaSupplier;
    private javax.swing.JTextField namaSupplier1;
    private javax.swing.JTextField nofaktur;
    private com.erv.function.PanelCool panelCool1;
    private javax.swing.JTextField ppn;
    private datechooser.beans.DateChooserCombo tanggal;
    private datechooser.beans.DateChooserCombo tglBayar;
    private datechooser.beans.DateChooserCombo tglExpire;
    private datechooser.beans.DateChooserCombo tglRetur;
    private javax.swing.JFormattedTextField txtDiskon;
    private javax.swing.JTextField txtNoBatch;
    private javax.swing.JFormattedTextField txtPPN;
    private datechooser.beans.DateChooserCombo txtTglMasuk;
    private javax.swing.JTextField txtkodeFaktur;
    private javax.swing.JTextField txtkodeRetur;
    // End of variables declaration//GEN-END:variables

    private void awal(Connection c) throws SQLException, ClassNotFoundException {
        panelCool1.add(jScrollPane3);
        jScrollPane3.setBounds(140, 120, 400, 150);
        panelCool1.setComponentZOrder(jScrollPane3, 0);
        //jScrollPane3.setVisible(false);
        //jScrollPane3.setSize(500, 150);
        jScrollPane2.setSize(jPanel4.getWidth() - 50, 150);
        tanggal.setDateFormat(d);
        tglBayar.setDateFormat(d);
        tglRetur.setDateFormat(d);
        txtTglMasuk.setDateFormat(d);
        tglExpire.setDateFormat(d);
        p = new pembelian();
        jScrollPane2.setVisible(false);
        jScrollPane3.setVisible(false);
        cm = koneksi.getKoneksiM();
        stat = cm.createStatement();
        dbbarang = new barangDao();
        r = new returbeli();
        ht = new hutang();
        buatTabel();
        lh = new loghistory();
        lhdao = new loghistoryDao();
        kosongFaktur();
        kosongBarang();
        kosongHasil();
        kosongRetur();
        setFaktur(c);
        nofaktur.requestFocus();
    }

    void kosongFaktur() {
        nofak = txtkodeFaktur.getText();
        noretur = txtkodeRetur.getText();
        nofaktur.setText("");
        kodeSupplier.setText("");
        namaSupplier.setText("");
        diskon.setText("0");
        ppn.setText("0");
        txtkodeFaktur.setText("");
        CboNamaBank.setVisible(false);
        btnTambah.setVisible(true);
    }

    void kosongBarang() {
        kodeBarang.setText("");
        namaBarang.setText("");
        jumlahBarang.setText("");
        hargaBarang.setText("");
        txtDiskon.setText("");
        txtPPN.setText("0.00");
        txtNoBatch.setText("");
        cbBonus.setSelected(false);
    }

    private void hasilAkhir(Connection c) throws SQLException {
        //tppn=getPPN();
        //tdiskon=getDiskon();                    
        diskont = DISKONItem + getTextDouble(DiskonTambah);
        //ttotalbayar = total + PPNItem - DISKONItem; lama
        tppn = 0;
        if (cbPPN.isSelected()) {
            tppn = (total - diskont) * 0.1;
        }
        ttotalbayar = total - diskont + tppn;
        // hasilBayar.setText(com.erv.function.Util.toMoney(tdp));
        cekpembayaran(c);
        hasilppn.setText(com.erv.function.Util.toMoney(tppn));
        hasilTotal.setText(com.erv.function.Util.toMoney(total));
        //hasilDiskon.setText(com.erv.function.Util.toMoney(DISKONItem));
        hasilDiskon.setText(com.erv.function.Util.toMoney(diskont));
        hasilTotalBayar.setText(com.erv.function.Util.toMoney(ttotalbayar));
    }

    double getDiskon(int jumlah, double harga) {
        double hasil = 0;
        if (cboStatDiskon.getSelectedIndex() == 0) {
            hasil = Double.parseDouble(txtDiskon.getValue().toString()) / 100 * (jumlah * harga);
        } else if (cboStatDiskon.getSelectedIndex() == 1) {
            hasil = Double.parseDouble(txtDiskon.getValue().toString());
        }
        return hasil;
    }

    double getDiskonPersen(int jumlah, double harga) {
        double hasil = 0;
        if (cboStatDiskon.getSelectedIndex() == 0) {
            hasil = Double.parseDouble(txtDiskon.getValue().toString());
        } else if (cboStatDiskon.getSelectedIndex() == 1) {
            double d = Double.parseDouble(txtDiskon.getValue().toString());
            if (d != 0) {
                hasil = (Double.parseDouble(txtDiskon.getValue().toString()) * 100) / (jumlah * harga);
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

    private void buatTabel() {
        String sqlCreate = "create table rinci ("
                + "NO int primary key, " //1
                + "KODE varchar(20) , " //2
                + "NAMABARANG varchar(200), " //3
                + "BATCH varchar(16)," //4
                + "EXPIRE  varchar(10)," // 5
                + "JUMLAH int, " // 6
                + "HARGA double, " // 7
                + "DISKON double, " // 8
                + "PPN double, " // 9
                + "SATUAN varchar(20), " // 10
                + "TOTAL double, " //11
                + "JUMLAHKECIL int, " //12
                + "DISKONP double, "
                + "BONUS varchar(10)"
                + ")";
        try {
            stat.execute(sqlCreate);
        } catch (SQLException ex) {
        }
    }

    private void hapusTabel() {
        try {
            stat.execute("drop table rinci");
        } catch (SQLException ex) {
        }
    }

    private void reloadData(Connection c) {
        JDBCAdapter j = new JDBCAdapter(cm);
        j.executeQuery("select * from rinci order by no");
        jScrollPane1.getViewport().remove(jTable1);
        jTable1 = new JTable(j);
        jTable1.setFont(new Font("Tahoma", Font.BOLD, 12));

        TableColumn col = jTable1.getColumnModel().getColumn(0);
        col.setPreferredWidth(30);
        col = jTable1.getColumnModel().getColumn(2);
        col.setPreferredWidth(200);
        TableColumn col1 = jTable1.getColumnModel().getColumn(11);
        //col1.setPreferredWidth(1);
        col1.setMinWidth(0);
        col1.setMaxWidth(0);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });

        jScrollPane1.setFocusable(false);
        jTable1.setFocusable(false);
        jScrollPane1.getViewport().add(jTable1);
        jScrollPane1.repaint();
        try {
            j.close();
        } catch (SQLException ex) {
            Logger.getLogger(DialogPembelian.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            total = 0;
            hpp = 0;
            PPNItem = 0;
            DISKONItem = 0;
            diskont = 0;
            Statement s = cm.createStatement();
            ResultSet r1 = s.executeQuery("select * from rinci");
            while (r1.next()) {
                double ttot = (r1.getDouble("JUMLAH") * r1.getDouble("HARGA"));
                double tdisk = r1.getDouble("DISKON");
                double tppn1 = r1.getDouble("PPN");
                double thpp = (ttot - tdisk + tppn1);
                total += ttot;
                hpp += thpp;
                DISKONItem += tdisk;
                PPNItem += tppn1;
                //diskont=0;
            }
            hasilAkhir(c);
            r1.close();
            s.close();
        } catch (Exception ex) {
            System.out.println("Error " + ex.toString());
        }

    }

    void prosesSimpan(Connection c) throws SQLException {
//        int idtemp = pembelianDao.getID(c);
//        if (idtemp >= IDBELI) {
//            IDBELI = idtemp;
//        }
        if (IDBELI != 0) {
            p.setID(IDBELI);
            p.setNOFAKTUR(txtkodeFaktur.getText());
        } else {
            p.setID(pembelianDao.getID(c));
            IDBELI = p.getID();
            p.setNOFAKTUR(pembelianDao.setFaktur(c));
        }        
        p.setIDSUPPLIER(kodeSupplier.getText());
        p.setCASH(cbostatusBeli.getSelectedIndex() + "");
        p.setSTATUS(cbostatusBeli.getSelectedIndex() + "");
        p.setTGLBAYAR(tglBayar.getText());
        p.setPAJAK(tppn);
        p.setDP(tdp);
        p.setDISKON(Double.parseDouble(DiskonTambah.getText()));
        p.setTANGGAL(tanggal.getText());
        p.setNOFAKTURSUPPLIER(nofaktur.getText());
        p.setTGLMASUK(txtTglMasuk.getText());
        if (!pembelianDao.insertIntoPEMBELIAN(c, p)) {
            Statement s = cm.createStatement();
            ResultSet r1 = s.executeQuery("select * from rinci");
            stok st = new stok();
            barang b;
            Barangstok bs;
            rincipembelian rp = new rincipembelian();
            while (r1.next()) {
                rp.setID(rincipembelianDao.getID(c));
                rp.setIDPEMBELIAN(IDBELI);
                rp.setKODEBARANG(r1.getString(2));
                rp.setJUMLAH(r1.getInt(6));
                rp.setHARGA(r1.getDouble(7));
                rp.setPPN(r1.getDouble(9));
                rp.setTOTAL(r1.getDouble(11));
                rp.setDISKON(r1.getDouble(8));
                rp.setSATUAN(r1.getString(10));
                rp.setJUMLAHKECIL(r1.getInt(12));
                rp.setKODEBATCH(r1.getString(4));
                rp.setEXPIRE(r1.getString(5));
                if (r1.getString(5).equals("")) {
                    rp.setEXPIRE(null);
                }
                rp.setDISKONP(r1.getDouble("DISKONP"));
                rp.setBONUS(r1.getString("BONUS"));
                rincipembelianDao.insertIntoRINCIPEMBELIAN(c, rp);
                b = new barangDao().getDetails(c, rp.getKODEBARANG());
                double ttot = rp.getHARGA() * rp.getJUMLAH();
                //double tdisk = (r1.getDouble(5) / 100) * (ttot);
                double tdisk = rp.getDISKON();
                double tppn1 = rp.getPPN();
                //double ttotbeli = (ttot - tdisk + tppn1);
                double ttotbeli = (ttot - tdisk + tppn1);
                bs = BarangstokDao.getDetailKodeBarang(c, rp.getKODEBARANG());
                int idbarangstok = 0;
                if (bs == null) {
                    bs = new Barangstok();
                    bs.setIDGUDANG(0);
                    bs.setKODEBARANG(rp.getKODEBARANG());
                    bs.setSTOK(rp.getJUMLAHKECIL());
                    bs.setCOGS(0);
                    bs.setHARGAJUAL(0);
                    idbarangstok = BarangstokDao.insertIntoBARANGSTOK(c, bs);
                    bs.setID(idbarangstok);
                }
                double ttotcogs = bs.getCOGS() * bs.getSTOK();
                double thpp;
                int tjumbrg = rp.getJUMLAHKECIL() + bs.getSTOK();
                thpp = ((ttot - tdisk) + ttotcogs) / tjumbrg;
                //hpp+= 
                if (bs.getCOGS() == 0) {
                    bs.setCOGS((ttot - tdisk) / rp.getJUMLAHKECIL());
                } else {
                    bs.setCOGS(thpp);
                }                //
                BarangstokDao.updateBARANGSTOK(c, bs);
                if (rp.getKODEBATCH().length() > 0) {
                    Barangstokbatch br = BarangstokbatchDao.getDetailKodeBatch(c, rp.getKODEBATCH(), bs.getID());
                    if (br == null) {
                        br = new Barangstokbatch();
                        br.setIDBARANGSTOK(bs.getID());
                        br.setKODEBATCH(rp.getKODEBATCH());
                        br.setEXPIRE(r1.getString(5));
                        //br.setSTOK(rp.getJUMLAH());
                        br.setSTOK(0);
                        BarangstokbatchDao.insertIntoBARANGSTOKBATCH(c, br);
                    }
//                    else{
//                        br.setSTOK(br.getSTOK()+rp.getJUMLAHKECIL()); 
//                        BarangstokbatchDao.updateBARANGSTOKBATCH(c, br);
//                    }
                }
                st.setIDPENJUALAN(IDBELI);
                st.setKODEBARANG(rp.getKODEBARANG());
                st.setIN(rp.getJUMLAHKECIL());
                st.setOUT(0);
                //st.setTANGGAL(java.sql.Date.valueOf(txtTglMasuk.getText()));
                st.setTANGGAL(txtTglMasuk.getText());
                st.setKODETRANS("B");
                st.setKODEBATCH(rp.getKODEBATCH());
                stokDao.insertIntoSTOK(c, st);
            }
            r1.close();
            s.close();
        } else {
            JOptionPane.showMessageDialog(this, "Entri Pembelian Gagal");
            throw new SQLException();

        }
    }

    void insertJurnal(Connection c, String pil) throws SQLException {
        jurnal j = new jurnal();
        IDJurnal = jurnalDao.getIDJurnal(c);
        j.setID(IDJurnal);
        if (pil.equals("BELI")) {
            j.setTANGGAL(txtTglMasuk.getText());
            j.setKODEJURNAL(txtkodeFaktur.getText());
            if (cbostatusBeli.getSelectedIndex() == 2) {
                j.setDESKRIPSI("Pembelian Dari " + namaSupplier.getText() + " Via Bank " + CboNamaBank.getSelectedItem().toString());
            } else {
                j.setDESKRIPSI("Pembelian Dari " + namaSupplier.getText());
            }
        } else if (pil.equals("RETUR")) {
            j.setTANGGAL(tglRetur.getText());
            j.setKODEJURNAL(txtkodeRetur.getText());
            j.setDESKRIPSI("Pengembalian Barang Ke " + namaSupplier.getText());
        }
        jurnalDao.insertIntoJURNAL(c, j);
    }

    void insertRinciJurnal(Connection c) throws SQLException, ClassNotFoundException {
        Statement s;
        s = c.createStatement();
        if (cbostatusBeli.getSelectedIndex() == 0) {
            s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "PERSEDIAAN") + "'," + total + ",0,1,'')");
            if (diskont != 0) {
                s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "DISKONBELI") + "',0," + diskont + ",2,'" + settingDao.getDetails(c, "DISKONBELI").getKODE() + "')");
            }
            if (tppn > 0) {
                s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "PPNBELI") + "'," + tppn + ",0,1,'')");
            }
            s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "KAS") + "',0," + ttotalbayar + ",2,'')");

        } else if (cbostatusBeli.getSelectedIndex() == 1) {

            s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "PERSEDIAAN") + "'," + total + ",0,1,'')");
            if (tdp != 0) {
                s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "KAS") + "',0," + tdp + ",2,'')");
            }
            if (diskont != 0) {
                s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "DISKONBELI") + "',0," + diskont + ",2,'" + settingDao.getDetails(c, "DISKONBELI").getKODE() + "')");
            }
            if (tppn != 0) {
                s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "PPNBELI") + "'," + tppn + ",0,3,'')");
            }
            s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + supplierDao.getDetails(c, kodeSupplier.getText()).getKODEAKUN() + "',0," + sisa + ",4,'')");
            prosesSimpanHutang(c);
        } else if (cbostatusBeli.getSelectedIndex() == 2) {
            String[] a = Util.split(CboNamaBank.getSelectedItem().toString(), "-");
            bank = bankDao.getDetails(c, Integer.parseInt(a[0]));
            s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "PERSEDIAAN") + "'," + total + ",0,1,'')");
            if (diskont != 0) {
                s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "DISKONBELI") + "',0," + diskont + ",2,'" + settingDao.getDetails(c, "DISKONBELI").getKODE() + "')");
            }
            if (tppn > 0) {
                s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "PPNBELI") + "'," + tppn + ",0,1,'')");
            }
            s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + bank.getKODEAKUN() + "',0," + ttotalbayar + ",2,'')");
        }
        s.close();
    }

    void insertRinciJurnalRetur(Connection c) throws SQLException, ClassNotFoundException {
        Statement s;
        s = c.createStatement();
        hutangbayar hb = new hutangbayar();
        if (totalhutang >= ttotalbayar) {
            s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "PERSEDIAAN") + "',0," + (total - diskont) + ",1,'')");
            if (tppn != 0) {
                s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "PPNBELI") + "',0," + tppn + ",3,'')");
            }
            s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + supplierDao.getDetails(c, kodeSupplier.getText()).getKODEAKUN() + "'," + ttotalbayar + ",0,4,'')");
            hb.setID(hutangbayarDao.getID(c));
            hb.setIDHUTANG(hutangDao.getDetailsBeli(c, IDBELI).getID());
            hb.setKODEHUTANGBAYAR(hutangbayarDao.getKodeHutangBayar(c));
            hb.setTANGGAL(tglRetur.getText());
            hb.setJUMLAH(ttotalbayar);
            hb.setREF(txtkodeRetur.getText());
            hutangbayarDao.insertIntoHUTANGBAYAR(c, hb);
        } else {
//            DialogHutang h = new DialogHutang(null, true, String.valueOf(IDBELI));
//            DialogHutang.txtJumlahBayar.setValue(ttotalbayar);
//            DialogHutang.ref = txtkodeRetur.getText();
//            h.setVisible(true);//            DialogHutang h = new DialogHutang(null, true, String.valueOf(IDBELI));
//            if (status) {
//                s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "PERSEDIAAN") + "',0," + (hpp - PPNItem) + ",1)");
//                s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "PPNBELI") + "',0," + PPNItem + ",1)");
//                s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + supplierDao.getDetails(c, kodeSupplier.getText()).getKODEAKUN() + "'," + ttotalbayar + ",0,3)");
//            } else {
//                pesan = "[Hutang]";
//            }
            int idhutang = 0;
            double sisahutang = 0;
            boolean cekstat = false;
            Statement shutang = null;
            Statement shutangtot = null;
            String sqlhutang = "select ID,IDSUPPLIER ,JUMLAH-JUMLAHBAYAR as sisa "
                    + "from VIEW_HUTANG where IDSUPPLIER='" + kodeSupplier1.getText() + "'";
            shutang = c.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rshutang = shutang.executeQuery(sqlhutang);
            if (rshutang.next()) {
                cekstat = true;
            }
            if (cekstat) {
                String sqlhutangtot = "select sum(JUMLAH-JUMLAHBAYAR) as tothutang "
                        + "from VIEW_HUTANG where IDSUPPLIER='" + kodeSupplier1.getText() + "'";
                shutangtot = c.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet rshutangtot = shutangtot.executeQuery(sqlhutangtot);
                double sisakashutang = 0;
                rshutangtot.next();
                sisakashutang = ttotalbayar - rshutangtot.getDouble(1);

                s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "PERSEDIAAN") + "',0," + (total - diskont) + ",1,'')");
                if (tppn != 0) {
                    s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "PPNBELI") + "',0," + tppn + ",2,'')");
                }
//                if (ttotalbayar > rshutangtot.getDouble(1)) {
//                    s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + supplierDao.getDetails(c, kodeSupplier.getText()).getKODEAKUN() + "'," + (rshutangtot.getDouble(1) - diskont + tppn) + ",0,4,'')");
//                } else {
                s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + supplierDao.getDetails(c, kodeSupplier.getText()).getKODEAKUN() + "'," + ttotalbayar + ",0,3,'')");
//                }
                rshutang.beforeFirst();
                while (ttotalbayar > 0) {
                    if (rshutang.next()) {
                        if (rshutang.getDouble(3) != 0) {
                            hb.setID(hutangbayarDao.getID(c));
                            hb.setIDHUTANG(rshutang.getInt(1));
                            hb.setKODEHUTANGBAYAR(hutangbayarDao.getKodeHutangBayar(c));
                            if (ttotalbayar >= rshutang.getDouble(3)) {
                                hb.setJUMLAH(rshutang.getDouble(3));
                                hutang htg = hutangDao.getDetails(c, rshutang.getInt(1));
                                htg.setSTATUS("0");
                                hutangDao.updateHUTANG(c, rshutang.getInt(1), htg);
                            } else {
                                hb.setJUMLAH(ttotalbayar);
                            }
                            hb.setTANGGAL(tglRetur.getText());
                            hb.setREF(txtkodeRetur.getText());
                            hutangbayarDao.insertIntoHUTANGBAYAR(c, hb);
                            ttotalbayar = ttotalbayar - rshutang.getDouble(3);
                        }
                        //System.out.println("error"+rspiutang.getDouble(3));
                    } else {
//                        s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "PERSEDIAAN") + "'," + sisakashutang + ",0,1,'')");
//                        s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "KAS") + "',0," + sisakashutang + ",2,'')");
//                        JOptionPane.showMessageDialog(this, "Kembali Uang Sebanyak "+ Util.toMoney(sisakashutang)); 
                        ttotalbayar = 0;
                    }
                }
                rshutangtot.close();
            } else {
                s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "PERSEDIAAN") + "',0," + (total - tdiskon) + ",1,'')");
                if (tppn != 0) {
                    s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "PPNBELI") + "',0," + tppn + ",2,'')");
                }
                s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + supplierDao.getDetails(c, kodeSupplier.getText()).getKODEAKUN() + "'," + ttotalbayar + ",0,3,'')");
            }
            rshutang.close();
        }
        s.close();
    }

    void selesai(Connection c) throws SQLException {
        hapusTabel();
        buatTabel();
        total = 0;
        hpp = 0;
        reloadData(c);
        kosongFaktur();
        kosongBarang();
        kosongHasil();
        jTabbedPane2.setSelectedIndex(0);
        setFaktur(c);
        cbostatusBeli.setSelectedIndex(3);
        nofaktur.requestFocus();
    }

    void kosongHasil() {
        hasilBayar.setText("0");
        hasilDiskon.setText("0");
        hasilSisa.setText("0");
        hasilTotal.setText("0");
        hasilTotalBayar.setText("0");
        hasilppn.setText("0");
        DiskonTambah.setText("0");
    }

    void prosesUpdate(Connection c) throws SQLException {
        p.setID(IDBELI);
        p.setNOFAKTUR(txtkodeFaktur.getText());
        p.setIDSUPPLIER(kodeSupplier.getText());
        p.setCASH(cbostatusBeli.getSelectedIndex() + "");
        p.setSTATUS(cbostatusBeli.getSelectedIndex() + "");
        p.setTGLBAYAR(tanggal.getText());
        p.setPAJAK(PPNItem);
        p.setDP(tdp);
        p.setDISKON(DISKONItem);
        p.setTANGGAL(tanggal.getText());
        p.setNOFAKTURSUPPLIER(nofaktur.getText());
        if (!pembelianDao.updatePEMBELIAN(c, IDBELI, p)) {
            JOptionPane.showMessageDialog(this, "Update Pembelian Ok");
            Statement s = cm.createStatement();
            ResultSet r1 = s.executeQuery("select * from rinci");
            rincipembelian rp = new rincipembelian();
            stok st = new stok();
            Statement sf = c.createStatement();
            sf.execute("delete from RINCIPEMBELIAN where IDPEMBELIAN=" + IDBELI + "");
            sf.execute("delete from JURNAL where KODEJURNAL='" + txtkodeFaktur.getText() + "'");
            sf.execute("delete from STOK where IDPENJUALAN=" + IDBELI + " AND KODETRANS='B'");
            while (r1.next()) {
                rp.setID(rincipembelianDao.getID(c));
                rp.setIDPEMBELIAN(IDBELI);
                rp.setKODEBARANG(r1.getString(1));
                rp.setJUMLAH(r1.getInt(3));
                rp.setHARGA(r1.getDouble(4));
                rp.setTOTAL(r1.getDouble(5));
                rincipembelianDao.insertIntoRINCIPEMBELIAN(c, rp);
                st.setIDPENJUALAN(IDBELI);
                st.setKODEBARANG(r1.getString(1));
                st.setIN(r1.getInt(3));
                st.setOUT(0);
                //st.setTANGGAL(java.sql.Date.valueOf(tanggal.getText()));
                st.setTANGGAL(tanggal.getText());
                st.setKODETRANS("B");
                stokDao.insertIntoSTOK(c, st);
            }
            r1.close();
            sf.close();
            s.close();

        } else {
            JOptionPane.showMessageDialog(this, "Update Penjualan Gagal");
        }
    }

    void prosesDelete(Connection c) throws SQLException {
        Statement sf = c.createStatement();
        //sf.execute("delete from RINCIPENJUALAN where IDPENJUALAN=" + IDjual + "");
        sf.execute("delete from PEMBELIAN where ID=" + IDBELI + "");
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
        jTabbedPane2.setSelectedIndex(0);
        nofaktur.requestFocus();
        sf.close();
    }

    float getDiskonitem() {
        float hasil = 0;
        return hasil;
    }

    void kosongRetur() {
        txtkodeRetur.setText("");
        kodeSupplier1.setText("");
        namaSupplier1.setText("");
    }

    void prosesSimpanRetur(Connection c) throws SQLException {
        r.setID(IDretur);
        r.setKODERETURBELI(txtkodeRetur.getText());
        r.setTANGGAL(tglRetur.getText());
        r.setIDSUPPLIER(kodeSupplier1.getText());
        r.setKETERANGAN("Pengembalian Barang Ke " + namaSupplier1.getText());
        r.setIDPEMBELIAN(IDBELI);
        r.setSTATUS(0);
        r.setTOTALRETUR(total);
        r.setTOTALDISKON(diskont);
        r.setTOTALPPN(tppn);
        if (returbeliDao.insertIntoRETURBELI(c, r)) {
            JOptionPane.showMessageDialog(this, "Entri Retur Ok");
            Statement s = cm.createStatement();
            ResultSet r1 = s.executeQuery("select * from rinci");
            returbelirinci rr = new returbelirinci();
            stok st = new stok();
            while (r1.next()) {
                rr.setID(returbelirinciDao.getID(c));
                rr.setIDRETURBELI(IDretur);
                rr.setKODEBARANG(r1.getString(2));
                rr.setJUMLAH(r1.getInt(6));
                rr.setHARGA(r1.getFloat(7));
                rr.setTOTAL(r1.getFloat("TOTAL"));
                rr.setSATUAN(r1.getString("SATUAN"));
                rr.setJUMLAHKECIL(r1.getInt(12));
                rr.setKODEBATCH(r1.getString("BATCH"));
                rr.setEXPIRE(r1.getString("EXPIRE"));
                if (r1.getString("EXPIRE").equals("")) {
                    rr.setEXPIRE(null);
                }
                rr.setDISKON(r1.getDouble("DISKON"));
                rr.setPPN(r1.getDouble("PPN"));
                returbelirinciDao.insertIntoRETURBELIRINCI(c, rr);
                //
////                st.setIDPENJUALAN(IDretur);
////                st.setKODEBARANG(rr.getKODEBARANG());
////                st.setTANGGAL(tglRetur.getText());
////                st.setIN(0);
////                st.setKODETRANS("K");
////                st.setKODEBATCH(rr.getKODEBATCH());
////                st.setOUT(rr.getJUMLAHKECIL());
////                stokDao.insertIntoSTOK(c, st);
            }
            r1.close();
            s.close();
        } else {
            JOptionPane.showMessageDialog(this, "Entri Retur Gagal");
        }
    }

    void setRetur(Connection c) throws SQLException {
        String tgl = com.erv.function.Util.toDateStringSql(new Date());
        IDretur = returbeliDao.getID(c);
        txtkodeRetur.setText("K" + com.erv.function.Util.getbln(tgl) + com.erv.function.Util.getthn(tgl) + IDretur);
    }

    void setFaktur(Connection c) throws SQLException {
        //IDBELI = pembelianDao.getID(c);
        txtkodeFaktur.setText(pembelianDao.setFaktur(c));
    }

    void prosesSimpanHutang(Connection c) throws SQLException {
        ht.setKODEHUTANG(hutangDao.getKodeHutang(c));
        ht.setIDPEMBELIAN(IDBELI);
        ht.setTANGGAL(tanggal.getText());
        ht.setJUMLAH(sisa);
        ht.setIDSUPPLIER(kodeSupplier.getText());
        ht.setJATUHTEMPO(tglBayar.getText());
        ht.setKETERANGAN("Hutang Kepada " + namaSupplier.getText());
        ht.setSTATUS("1");
        ht.setID(hutangDao.getID(c));
        hutangDao.insertIntoHUTANG(c, ht);
    }

    void cetakFaktur(Connection c) {
        HashMap parameter = new HashMap();
        JasperPrint jasperPrint = null;
        parameter.put("nofaktur", nofak);
        try {
            URL url = new URL(global.REPORT + "/FakturBeli.jasper");
            InputStream in = url.openStream();
            jasperPrint = JasperFillManager.fillReport(in, parameter, c);
            JasperViewer.viewReport(jasperPrint, false);
        } catch (Exception ex) {
            System.out.print(ex.toString());
            //Logger.getLogger(formlaporan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void cetakRetur(Connection c) {
        HashMap parameter = new HashMap();
        JasperPrint jasperPrint = null;
        parameter.put("koderetur", noretur);
        try {
            URL url = new URL(global.REPORT + "/ReturBeli.jasper");
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
        cal.setTime(com.erv.fungsi.Fungsi.dateAdd(c, "DAY", 30, java.sql.Date.valueOf(tanggal.getText())));
        tglBayar.setSelectedDate(cal);
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
        if (aksilog.equals("InsertBeli")) {
            lh.setTABEL("TPEMBELIAN");
            lh.setNOREFF(txtkodeFaktur.getText());
            ketlog = "Insert Data Pembelian NoFaktur " + txtkodeFaktur.getText() + " Tgl Masuk " + java.sql.Date.valueOf(txtTglMasuk.getText()) + ", Tgl Faktur " + java.sql.Date.valueOf(tanggal.getText()) + ", Supplier " + namaSupplier.getText() + " (" + kodeSupplier.getText() + ")";
        } else if (aksilog.equals("InsertReturBeli")) {
            lh.setTABEL("TRETURBELI");
            lh.setNOREFF(txtkodeRetur.getText());
            ketlog = "Insert Draf Retur Pembelian " + txtkodeRetur.getText() + " Tgl Retur " + java.sql.Date.valueOf(tglRetur.getText()) + " Atas Faktur " + txtkodeFaktur.getText() + " Supplier " + namaSupplier1.getText() + " (" + kodeSupplier1.getText() + ")";
        }
        lh.setAKSI(aksilog);
        lh.setKETERANGAN(ketlog);
        lhdao.insert(c, lh);
    }

    public double getTextDouble(JTextField t) {
        double h = 0;
        if (t.getText() != null) {
            h = Double.parseDouble(t.getText());
        } else {
            h = 0;
        }
        return h;
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

    void cekpembayaran(Connection c) throws SQLException {
        if (cbostatusBeli.getSelectedIndex() == 0) {
            CboNamaBank.setVisible(false);
            hasilBayar.setText("" + df0.format(ttotalbayar));
            hasilBayar.setEnabled(false);
        } else if (cbostatusBeli.getSelectedIndex() == 1) {
//            settingTgl(c);
            CboNamaBank.setVisible(false);
            hasilBayar.setEnabled(true);
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
        } else if (cbostatusBeli.getSelectedIndex() == 2) {
            CboNamaBank.setVisible(true);
            hasilBayar.setEnabled(false);
            hasilBayar.setText("" + df0.format(ttotalbayar));
            reloadbank(c);
        }
    }

    boolean cekBarang(Connection c) throws SQLException {
        Statement s = cm.createStatement();
        ResultSet rs = s.executeQuery("select * from rinci where KODE='" + kodeBarang.getText() + "' AND BATCH='" + txtNoBatch.getText() + "'");
        return rs.next();
    }

}
