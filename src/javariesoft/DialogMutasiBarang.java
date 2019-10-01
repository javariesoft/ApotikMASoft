/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DialogMutasiBarang.java
 *
 * Created on Nov 26, 2011, 9:48:03 PM
 */
package javariesoft;

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
import com.erv.db.settingDao;
import com.erv.db.stokDao;
import com.erv.function.JDBCAdapter;
import com.erv.fungsi.Fungsi;
import com.erv.model.barang;
import com.erv.model.jurnal;
import com.erv.model.pelanggan;
import com.erv.model.penjualan;
import com.erv.model.piutang;
import com.erv.model.piutangbayar;
import com.erv.model.retur;
import com.erv.model.rincipenjualan;
import com.erv.model.rinciretur;
import com.erv.model.stok;
import java.awt.Font;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import com.erv.function.Util;
import java.sql.BatchUpdateException;
import com.eigher.db.loghistoryDao;
import com.eigher.model.loghistory;
import com.erv.db.salesDao;
import com.erv.model.pesan;
import com.erv.model.sales;
import java.io.InputStream;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Vector;
import javax.swing.JFormattedTextField;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author erwadi
 */
public class DialogMutasiBarang extends javax.swing.JDialog {

    String nofak = "";
    String id[];
    double total = 0, ttotalbayar = 0, PPNItem = 0, DiskonItem = 0, TambahTotal = 0, ttambahtotalakhir = 0, ttotalbayarakhir = 0;
    double totalhutang = 0;
    double sisa = 0;
    int IDJurnal = 0;
    double tppn = 0;
    double tdiskon = 0;
    double hpp = 0;
    int IDjual, IDretur = 0, nomor = 0;
    float tdp = 0;
    int sisaBarang = 0;
    Connection cm;
    java.text.DateFormat d = new SimpleDateFormat("yyyy-MM-dd");
    DecimalFormat df = new DecimalFormat("0");
    DecimalFormat df0 = new DecimalFormat("0.0");
    DecimalFormat df1 = new DecimalFormat("###,###,###,###");
    penjualan p = null;
    retur r = null;
    penjualanDao dbpenjualan;
    barangDao dbbarang;
    sales sls;
    salesDao dbsales;
    Statement stat = null;
    String pilPelanggan = "0";
    barang brg;
    String pil = "0";
    loghistory lh;
    loghistoryDao lhdao;
    com.erv.function.Util u = new com.erv.function.Util();
    String aksilog = "";
    List<pesan> pesan = new ArrayList<pesan>();
    DefaultTableModel model;

    /**
     * Creates new form DialogPenjualan
     */
    public DialogMutasiBarang(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setting();
        kosongFaktur();
        kosongBarang();
        kosongHasil();
        kosongRetur();
        jScrollPane2.setVisible(false);
        jScrollPane3.setVisible(false);
        Connection c = null;
        try {
            dbpenjualan = new penjualanDao();
            p = new penjualan();
            r = new retur();
            c = koneksi.getKoneksiJ();
            IDjual = dbpenjualan.getID(c);
            dbbarang = new barangDao();
            setFaktur(c);
            cm = koneksi.getKoneksiM();
            stat = cm.createStatement();
            buatTabel();
            sls = new sales();
            dbsales = new salesDao();
            lh = new loghistory();
            lhdao = new loghistoryDao();
            model = (DefaultTableModel) jTable1.getModel();
            jTabbedPane1.setEnabledAt(0, true);
            tgl.setDateFormat(d);
            tgllunas.setDateFormat(d);
            tglRetur.setDateFormat(d);
            statusbayar.setSelectedIndex(1);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.toString());
        } finally {
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DialogMutasiBarang.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    public DialogMutasiBarang(java.awt.Frame parent, boolean modal, int id, double tot) {
        super(parent, modal);
        initComponents();
        setting();
        kosongFaktur();
        kosongBarang();
        kosongHasil();
        jScrollPane2.setVisible(false);
        jScrollPane3.setVisible(false);
        Connection c = null;
        try {
            dbpenjualan = new penjualanDao();
            p = new penjualan();
            r = new retur();
            c = koneksi.getKoneksiJ();
            IDjual = dbpenjualan.getID(c);
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
            jTabbedPane1.setEnabledAt(0, true);
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
            cboStatDiskon.setSelectedIndex(1);
            int x = JOptionPane.showConfirmDialog(this, "Tampilkan Data Barang ?", "", JOptionPane.YES_NO_OPTION);
            if (x == 0) {
                model = (DefaultTableModel) jTable1.getModel();
                ResultSet re = c.createStatement().executeQuery("select * from RINCIPENJUALAN where IDPENJUALAN=" + p.getID() + "");
                hapusTabel();
                buatTabel();
                while (re.next()) {
                    brg = new barangDao().getDetails(c, re.getString(3));
                    Vector data = new Vector();
                    data.add(0, re.getString(3));
                    data.add(1, brg.getNAMABARANG());
                    data.add(2, re.getInt(4));
                    data.add(3, re.getString(5));
                    data.add(4, re.getString(6));
                    data.add(5, re.getString(8));
                    data.add(6, re.getString(7));
                    data.add(7, re.getInt(9));
                    data.add(8, nomor);
                    model.addRow(data);
                }
                total = 0;
                hpp = 0;
                //hasilAkhir();
                reloadData(c);
                hasilBayar.setText("" + p.getDP());
                jTabbedPane1.setSelectedIndex(1);
                re.close();
            } else {
                kodebarang.requestFocus();
                jTabbedPane1.setSelectedIndex(1);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.toString());
        } finally {
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DialogMutasiBarang.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
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
        jPanel2 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
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
        btnKeluar = new javax.swing.JButton();
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
        txtTambahTotal = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        kodebarang = new javax.swing.JTextField();
        namabarang = new javax.swing.JTextField();
        jumlah = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        harga = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        btninsert = new javax.swing.JButton();
        btndelete = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        cboStatDiskonItem = new javax.swing.JComboBox();
        jLabel25 = new javax.swing.JLabel();
        diskonitem = new javax.swing.JFormattedTextField();
        txtPPN = new javax.swing.JFormattedTextField();
        LabelPersen = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(javariesoft.JavarieSoftApp.class).getContext().getResourceMap(DialogMutasiBarang.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        panelCool1.setName("panelCool1"); // NOI18N
        panelCool1.setLayout(null);

        jPanel2.setName("jPanel2"); // NOI18N

        jScrollPane4.setName("jScrollPane4"); // NOI18N

        jTable4.setName("jTable4"); // NOI18N
        jScrollPane4.setViewportView(jTable4);

        jPanel2.add(jScrollPane4);

        panelCool1.add(jPanel2);
        jPanel2.setBounds(910, 50, 80, 40);

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        jTable2.setName("jTable2"); // NOI18N
        jTable2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable2KeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);

        panelCool1.add(jScrollPane2);
        jScrollPane2.setBounds(10, 220, 480, 100);

        jTabbedPane1.setFont(resourceMap.getFont("jTabbedPane1.font")); // NOI18N
        jTabbedPane1.setName("jTabbedPane1"); // NOI18N
        jTabbedPane1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jTabbedPane1StateChanged(evt);
            }
        });

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setLayout(null);

        jLabel2.setFont(resourceMap.getFont("jLabel2.font")); // NOI18N
        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N
        jPanel1.add(jLabel2);
        jLabel2.setBounds(20, 10, 110, 20);

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
        nofaktur.setBounds(160, 10, 150, 21);

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
        tgl.setBounds(160, 35, 150, 20);

        jLabel1.setFont(resourceMap.getFont("jLabel1.font")); // NOI18N
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N
        jPanel1.add(jLabel1);
        jLabel1.setBounds(370, 70, 0, 20);

        statusbayar.setFont(resourceMap.getFont("statusbayar.font")); // NOI18N
        statusbayar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "CASH", "KREDIT" }));
        statusbayar.setEnabled(false);
        statusbayar.setName("statusbayar"); // NOI18N
        statusbayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                statusbayarActionPerformed(evt);
            }
        });
        jPanel1.add(statusbayar);
        statusbayar.setBounds(570, 10, 150, 21);

        jLabel5.setFont(resourceMap.getFont("jLabel5.font")); // NOI18N
        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N
        jPanel1.add(jLabel5);
        jLabel5.setBounds(450, 10, 110, 20);

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

        jLabel3.setFont(resourceMap.getFont("jLabel3.font")); // NOI18N
        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N
        jPanel1.add(jLabel3);
        jLabel3.setBounds(20, 35, 100, 20);

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
        kodepelanggan.setBounds(160, 60, 150, 21);

        jLabel4.setFont(resourceMap.getFont("jLabel4.font")); // NOI18N
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
        diskon.setBounds(490, 70, 0, 21);

        jLabel18.setFont(resourceMap.getFont("jLabel18.font")); // NOI18N
        jLabel18.setText(resourceMap.getString("jLabel18.text")); // NOI18N
        jLabel18.setName("jLabel18"); // NOI18N
        jPanel1.add(jLabel18);
        jLabel18.setBounds(20, 85, 120, 20);

        namapelanggan.setFont(resourceMap.getFont("namapelanggan.font")); // NOI18N
        namapelanggan.setEnabled(false);
        namapelanggan.setName("namapelanggan"); // NOI18N
        jPanel1.add(namapelanggan);
        namapelanggan.setBounds(160, 85, 250, 21);

        ppn.setFont(resourceMap.getFont("diskon.font")); // NOI18N
        ppn.setName("ppn"); // NOI18N
        ppn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ppnKeyPressed(evt);
            }
        });
        jPanel1.add(ppn);
        ppn.setBounds(490, 100, 0, 21);

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
        btnBaru.setBounds(20, 110, 110, 30);

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
        txtSales.setBounds(570, 60, 120, 21);

        txtNamaSales.setEditable(false);
        txtNamaSales.setFont(resourceMap.getFont("txtNamaSales.font")); // NOI18N
        txtNamaSales.setText(resourceMap.getString("txtNamaSales.text")); // NOI18N
        txtNamaSales.setName("txtNamaSales"); // NOI18N
        jPanel1.add(txtNamaSales);
        txtNamaSales.setBounds(570, 85, 300, 21);

        jLabel27.setFont(resourceMap.getFont("jLabel27.font")); // NOI18N
        jLabel27.setText(resourceMap.getString("jLabel27.text")); // NOI18N
        jLabel27.setName("jLabel27"); // NOI18N
        jPanel1.add(jLabel27);
        jLabel27.setBounds(450, 60, 100, 20);

        LabelKodeDO.setFont(resourceMap.getFont("LabelKodeDO.font")); // NOI18N
        LabelKodeDO.setText(resourceMap.getString("LabelKodeDO.text")); // NOI18N
        LabelKodeDO.setName("LabelKodeDO"); // NOI18N
        jPanel1.add(LabelKodeDO);
        LabelKodeDO.setBounds(450, 110, 100, 20);

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
        txtKodeDO.setBounds(570, 110, 70, 21);

        txtNamaDo.setFont(resourceMap.getFont("txtNamaDo.font")); // NOI18N
        txtNamaDo.setText(resourceMap.getString("txtNamaDo.text")); // NOI18N
        txtNamaDo.setName("txtNamaDo"); // NOI18N
        jPanel1.add(txtNamaDo);
        txtNamaDo.setBounds(650, 110, 220, 21);

        jLabel29.setFont(resourceMap.getFont("jLabel29.font")); // NOI18N
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
        jScrollPane5.setBounds(770, 40, 90, 40);

        BtnGenNum.setFont(resourceMap.getFont("BtnGenNum.font")); // NOI18N
        BtnGenNum.setText(resourceMap.getString("BtnGenNum.text")); // NOI18N
        BtnGenNum.setName("BtnGenNum"); // NOI18N
        BtnGenNum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnGenNumActionPerformed(evt);
            }
        });
        jPanel1.add(BtnGenNum);
        BtnGenNum.setBounds(320, 10, 90, 23);

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
        btnKeluar.setBounds(133, 110, 110, 30);

        jTabbedPane1.addTab("Faktur", jPanel1);

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
        txtkodeRetur.setBounds(160, 10, 140, 21);

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

        tglRetur.setFieldFont(resourceMap.getFont("tglRetur.dch_combo_fieldFont")); // NOI18N
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
        btnKeluar1.setIcon(resourceMap.getIcon("btnKeluar1.icon")); // NOI18N
        btnKeluar1.setText(resourceMap.getString("btnKeluar1.text")); // NOI18N
        btnKeluar1.setName("btnKeluar1"); // NOI18N
        btnKeluar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKeluar1ActionPerformed(evt);
            }
        });
        jPanel4.add(btnKeluar1);
        btnKeluar1.setBounds(753, 103, 120, 30);

        jTabbedPane1.addTab(resourceMap.getString("jPanel4.TabConstraints.tabTitle"), jPanel4); // NOI18N

        panelCool1.add(jTabbedPane1);
        jTabbedPane1.setBounds(10, 10, 900, 170);

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Kode Barang", "Nama Barang", "Jumlah", "Harga", "Diskon", "PPN", "Total", "DO", "NO"
            }
        ));
        jTable1.setFocusable(false);
        jTable1.setName("jTable1"); // NOI18N
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        panelCool1.add(jScrollPane1);
        jScrollPane1.setBounds(10, 240, 900, 320);

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
        hasilTotal.setBounds(114, 10, 160, 22);

        jLabel12.setFont(resourceMap.getFont("jLabel12.font")); // NOI18N
        jLabel12.setText(resourceMap.getString("jLabel12.text")); // NOI18N
        jLabel12.setName("jLabel12"); // NOI18N
        jPanel3.add(jLabel12);
        jLabel12.setBounds(20, 36, 100, 20);

        hasilDiskon.setEditable(false);
        hasilDiskon.setFont(resourceMap.getFont("hasilDiskon.font")); // NOI18N
        hasilDiskon.setName("hasilDiskon"); // NOI18N
        hasilDiskon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                hasilDiskonKeyPressed(evt);
            }
        });
        jPanel3.add(hasilDiskon);
        hasilDiskon.setBounds(114, 36, 160, 22);

        jLabel13.setFont(resourceMap.getFont("jLabel13.font")); // NOI18N
        jLabel13.setText(resourceMap.getString("jLabel13.text")); // NOI18N
        jLabel13.setName("jLabel13"); // NOI18N
        jPanel3.add(jLabel13);
        jLabel13.setBounds(310, 10, 100, 20);

        jLabel14.setFont(resourceMap.getFont("jLabel14.font")); // NOI18N
        jLabel14.setText(resourceMap.getString("jLabel14.text")); // NOI18N
        jLabel14.setName("jLabel14"); // NOI18N
        jPanel3.add(jLabel14);
        jLabel14.setBounds(310, 62, 90, 20);

        hasilBayar.setEditable(false);
        hasilBayar.setFont(resourceMap.getFont("hasilBayar.font")); // NOI18N
        hasilBayar.setName("hasilBayar"); // NOI18N
        hasilBayar.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                hasilBayarCaretUpdate(evt);
            }
        });
        hasilBayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                hasilBayarKeyPressed(evt);
            }
        });
        jPanel3.add(hasilBayar);
        hasilBayar.setBounds(410, 36, 180, 22);

        hasilSisa.setEditable(false);
        hasilSisa.setFont(resourceMap.getFont("hasilSisa.font")); // NOI18N
        hasilSisa.setName("hasilSisa"); // NOI18N
        jPanel3.add(hasilSisa);
        hasilSisa.setBounds(410, 62, 180, 22);

        jLabel17.setFont(resourceMap.getFont("jLabel17.font")); // NOI18N
        jLabel17.setText(resourceMap.getString("jLabel17.text")); // NOI18N
        jLabel17.setName("jLabel17"); // NOI18N
        jPanel3.add(jLabel17);
        jLabel17.setBounds(20, 90, 110, 20);

        hasilTotalBayar.setEditable(false);
        hasilTotalBayar.setFont(resourceMap.getFont("hasilTotalBayar.font")); // NOI18N
        hasilTotalBayar.setName("hasilTotalBayar"); // NOI18N
        jPanel3.add(hasilTotalBayar);
        hasilTotalBayar.setBounds(114, 90, 160, 22);

        jLabel15.setFont(resourceMap.getFont("jLabel15.font")); // NOI18N
        jLabel15.setText(resourceMap.getString("jLabel15.text")); // NOI18N
        jLabel15.setName("jLabel15"); // NOI18N
        jPanel3.add(jLabel15);
        jLabel15.setBounds(20, 62, 70, 14);

        hasilppn.setEditable(false);
        hasilppn.setFont(resourceMap.getFont("hasilppn.font")); // NOI18N
        hasilppn.setName("hasilppn"); // NOI18N
        jPanel3.add(hasilppn);
        hasilppn.setBounds(114, 62, 160, 22);

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
        btnFaktur.setBounds(730, 34, 160, 40);

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
        btnCetak.setBounds(730, 75, 160, 40);

        txtNofakturTemp.setFont(resourceMap.getFont("txtNofakturTemp.font")); // NOI18N
        txtNofakturTemp.setText(resourceMap.getString("txtNofakturTemp.text")); // NOI18N
        txtNofakturTemp.setName("txtNofakturTemp"); // NOI18N
        jPanel3.add(txtNofakturTemp);
        txtNofakturTemp.setBounds(730, 10, 160, 21);

        txtTambahTotal.setEditable(false);
        txtTambahTotal.setFont(resourceMap.getFont("txtTambahTotal.font")); // NOI18N
        txtTambahTotal.setName("txtTambahTotal"); // NOI18N
        txtTambahTotal.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTambahTotalFocusLost(evt);
            }
        });
        txtTambahTotal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTambahTotalKeyPressed(evt);
            }
        });
        jPanel3.add(txtTambahTotal);
        txtTambahTotal.setBounds(410, 10, 180, 22);

        jLabel19.setFont(resourceMap.getFont("jLabel19.font")); // NOI18N
        jLabel19.setText(resourceMap.getString("jLabel19.text")); // NOI18N
        jLabel19.setName("jLabel19"); // NOI18N
        jPanel3.add(jLabel19);
        jLabel19.setBounds(310, 36, 100, 20);

        panelCool1.add(jPanel3);
        jPanel3.setBounds(10, 560, 900, 120);

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
        kodebarang.setBounds(0, 20, 130, 21);

        namabarang.setFont(resourceMap.getFont("kodebarang.font")); // NOI18N
        namabarang.setEnabled(false);
        namabarang.setName("namabarang"); // NOI18N
        jPanel5.add(namabarang);
        namabarang.setBounds(130, 20, 510, 21);

        jumlah.setFont(resourceMap.getFont("kodebarang.font")); // NOI18N
        jumlah.setName("jumlah"); // NOI18N
        jumlah.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jumlahFocusGained(evt);
            }
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
        jumlah.setBounds(790, 20, 108, 21);

        jLabel22.setFont(resourceMap.getFont("jLabel22.font")); // NOI18N
        jLabel22.setText(resourceMap.getString("jLabel22.text")); // NOI18N
        jLabel22.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel22.setName("jLabel22"); // NOI18N
        jPanel5.add(jLabel22);
        jLabel22.setBounds(0, 0, 130, 20);

        jLabel23.setFont(resourceMap.getFont("jLabel23.font")); // NOI18N
        jLabel23.setText(resourceMap.getString("jLabel23.text")); // NOI18N
        jLabel23.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel23.setName("jLabel23"); // NOI18N
        jPanel5.add(jLabel23);
        jLabel23.setBounds(130, 0, 510, 20);

        harga.setFont(resourceMap.getFont("kodebarang.font")); // NOI18N
        harga.setEnabled(false);
        harga.setName("harga"); // NOI18N
        harga.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                hargaKeyPressed(evt);
            }
        });
        jPanel5.add(harga);
        harga.setBounds(640, 20, 150, 21);

        jLabel8.setFont(resourceMap.getFont("jLabel8.font")); // NOI18N
        jLabel8.setText(resourceMap.getString("jLabel8.text")); // NOI18N
        jLabel8.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel8.setName("jLabel8"); // NOI18N
        jPanel5.add(jLabel8);
        jLabel8.setBounds(640, 0, 150, 20);

        jLabel10.setFont(resourceMap.getFont("jLabel10.font")); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText(resourceMap.getString("jLabel10.text")); // NOI18N
        jLabel10.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel10.setName("jLabel10"); // NOI18N
        jPanel5.add(jLabel10);
        jLabel10.setBounds(720, 70, 60, 20);

        btninsert.setFont(resourceMap.getFont("btninsert.font")); // NOI18N
        btninsert.setText(resourceMap.getString("btninsert.text")); // NOI18N
        btninsert.setName("btninsert"); // NOI18N
        btninsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btninsertActionPerformed(evt);
            }
        });
        jPanel5.add(btninsert);
        btninsert.setBounds(700, 40, 90, 20);

        btndelete.setFont(resourceMap.getFont("btndelete.font")); // NOI18N
        btndelete.setText(resourceMap.getString("btndelete.text")); // NOI18N
        btndelete.setName("btndelete"); // NOI18N
        btndelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndeleteActionPerformed(evt);
            }
        });
        jPanel5.add(btndelete);
        btndelete.setBounds(790, 40, 90, 20);

        jLabel16.setFont(resourceMap.getFont("jLabel16.font")); // NOI18N
        jLabel16.setText(resourceMap.getString("jLabel16.text")); // NOI18N
        jLabel16.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel16.setName("jLabel16"); // NOI18N
        jPanel5.add(jLabel16);
        jLabel16.setBounds(790, 0, 108, 20);

        cboStatDiskonItem.setEditable(true);
        cboStatDiskonItem.setFont(resourceMap.getFont("cboStatDiskonItem.font")); // NOI18N
        cboStatDiskonItem.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Rp" }));
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
        cboStatDiskonItem.setBounds(660, 70, 50, 21);

        jLabel25.setFont(resourceMap.getFont("jLabel25.font")); // NOI18N
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setText(resourceMap.getString("jLabel25.text")); // NOI18N
        jLabel25.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel25.setName("jLabel25"); // NOI18N
        jPanel5.add(jLabel25);
        jLabel25.setBounds(440, 70, 150, 20);

        diskonitem.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        diskonitem.setFont(resourceMap.getFont("diskonitem.font")); // NOI18N
        diskonitem.setName("diskonitem"); // NOI18N
        diskonitem.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                diskonitemFocusGained(evt);
            }
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
        diskonitem.setBounds(560, 70, 100, 20);

        txtPPN.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        txtPPN.setEnabled(false);
        txtPPN.setFont(resourceMap.getFont("txtPPN.font")); // NOI18N
        txtPPN.setName("txtPPN"); // NOI18N
        txtPPN.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPPNKeyPressed(evt);
            }
        });
        jPanel5.add(txtPPN);
        txtPPN.setBounds(790, 70, 30, 21);

        LabelPersen.setFont(resourceMap.getFont("LabelPersen.font")); // NOI18N
        LabelPersen.setText(resourceMap.getString("LabelPersen.text")); // NOI18N
        LabelPersen.setEnabled(false);
        LabelPersen.setName("LabelPersen"); // NOI18N
        jPanel5.add(LabelPersen);
        LabelPersen.setBounds(820, 70, 30, 20);

        panelCool1.add(jPanel5);
        jPanel5.setBounds(10, 180, 900, 60);

        getContentPane().add(panelCool1, java.awt.BorderLayout.CENTER);

        setSize(new java.awt.Dimension(934, 725));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

private void jTabbedPane1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jTabbedPane1StateChanged
    Connection c = null;
    try {
        //TODO add your handling code here:
        c = koneksi.getKoneksiJ();
        if (jTabbedPane1.getSelectedIndex() == 0) {
            kodepelanggan.requestFocus();
        } else if (jTabbedPane1.getSelectedIndex() == 1) {
            setRetur(c);
            kodepelanggan1.requestFocus();
        }
    } catch (SQLException ex) {
        Logger.getLogger(DialogMutasiBarang.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
        if (c != null) {
            try {
                c.close();
            } catch (SQLException ex) {
                Logger.getLogger(DialogMutasiBarang.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}//GEN-LAST:event_jTabbedPane1StateChanged

private void btndeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndeleteActionPerformed
    Connection c = null;
    try {
        c = koneksi.getKoneksiJ();
        kosongBarang();
        cekpembayaran(c);
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, ex.toString());
    } finally {
        if (c != null) {
            try {
                c.close();
            } catch (SQLException ex) {
                Logger.getLogger(DialogMutasiBarang.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}//GEN-LAST:event_btndeleteActionPerformed

private void btninsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btninsertActionPerformed
    Connection c = null;
    try {
        // TODO add your handling code here:
        c = koneksi.getKoneksiJ();
        boolean cekbarang = false;
        double ppn = 0;
        if (jTabbedPane1.getSelectedIndex() == 0) {
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
                //double tdiskonD = Double.parseDouble(diskonitem.getValue().toString());
                double tdiskonD = 0;
                //double tppnD = (Double.parseDouble(txtPPN.getValue().toString()) / 100) * (tot - tdiskonD); Lama
                double tppnD = (ppn / 100) * (tot - tdiskonD);
                //jScrollPane1.getViewport().remove(jTable1);
                //DefaultTableModel model = new DefaultTableModel(col, 0);

                Vector data = new Vector();
                if (model.getRowCount() != 0) {
                    for (int i = 0; i < jTable1.getRowCount(); i++) {
                        if (kodebarang.getText().equals(jTable1.getValueAt(i, 0).toString())) {
                            cekbarang = true;
                            ///break;
                        }
                    }
                }

                nomor = jTable1.getRowCount() + 1;
                if (cekbarang) {
                    JOptionPane.showMessageDialog(null, "Produk Ini Sudah Dientrikan..");
                    kosongBarang();
                    kodebarang.requestFocus();
                } else {
                    data.add(0, kodebarang.getText());
                    data.add(1, namabarang.getText());
                    data.add(2, jumlah.getText());
                    data.add(3, harga.getText());
                    data.add(4, diskonitem.getText());
                    data.add(5, ppn);
                    data.add(6, (tot - tdiskonD + tppnD));
                    data.add(7, ((txtKodeDO.getText().length() != 0) ? (Integer.parseInt(txtKodeDO.getText())) : 0));
                    data.add(8, nomor);
                    model.addRow(data);
                }
                //Insert first position

                total = 0;
                hpp = 0;
                DiskonItem = 0;
                PPNItem = 0;
                reloadData(c);
                cekpembayaran(c);
                kosongBarang();
                //JOptionPane.showMessageDialog(null, "Total"+ttotalbayarakhir);
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
                //double tdiskonD = Double.parseDouble(diskonitem.getValue().toString());
                double tdiskonD = 0;
                //double tppnD = (Double.parseDouble(txtPPN.getValue().toString()) / 100) * (tot - tdiskonD); Lama
                double tppnD = (ppn / 100) * (tot - tdiskonD);
                //jScrollPane1.getViewport().remove(jTable1);
                //DefaultTableModel model = new DefaultTableModel(col, 0);

                Vector data = new Vector();
                if (model.getRowCount() != 0) {
                    for (int i = 0; i < jTable1.getRowCount(); i++) {
                        if (kodebarang.getText().equals(jTable1.getValueAt(i, 0).toString())) {
                            cekbarang = true;
                            ///break;
                        }
                    }
                }

                nomor = jTable1.getRowCount() + 1;

                if (cekbarang) {
                    JOptionPane.showMessageDialog(null, "Produk Ini Sudah Dientrikan..");
                    kosongBarang();
                    kodebarang.requestFocus();
                } else {
                    data.add(0, kodebarang.getText());
                    data.add(1, namabarang.getText());
                    data.add(2, jumlah.getText());
                    data.add(3, harga.getText());
                    data.add(4, diskonitem.getText());
                    data.add(5, ppn);
                    data.add(6, (tot - tdiskonD + tppnD));
                    data.add(7, ((txtKodeDO.getText().length() != 0) ? (Integer.parseInt(txtKodeDO.getText())) : 0));
                    data.add(8, nomor);
                    model.addRow(data);
                }
                //Insert first position

//        stat.execute("insert into rinci values('" + kodebarang.getText()
//                + "','" + namabarang.getText()
//                + "'," + jumlah.getText()
//                + "," + harga.getText()
//                + "," + diskonitem.getValue()
//                + "," + txtPPN.getText()
//                + "," + (tot - tdiskonD + tppnD)
//                + "," + ((txtKodeDO.getText().length() != 0) ? (Integer.parseInt(txtKodeDO.getText())) : 0)
//                + ")");
                total = 0;
                hpp = 0;
                DiskonItem = 0;
                PPNItem = 0;
                reloadData(c);
                cekpembayaran(c);
                kosongBarang();
            }
        }

    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, ex.toString());
    } finally {
        if (c != null) {
            try {
                c.close();
            } catch (SQLException ex) {
                Logger.getLogger(DialogMutasiBarang.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}//GEN-LAST:event_btninsertActionPerformed

private void jumlahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jumlahKeyPressed
// TODO add your handling code here:
    if (evt.getKeyCode() == 10) {
        if (diskonitem.getText().equals("")) {
            diskonitem.setText("0");
        }
        btninsert.requestFocus();
    }
}//GEN-LAST:event_jumlahKeyPressed

private void hargaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_hargaKeyPressed
// TODO add your handling code here:
    if (evt.getKeyCode() == 10) {
        jumlah.requestFocus();
    }
}//GEN-LAST:event_hargaKeyPressed

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

private void kodebarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kodebarangActionPerformed
    Connection c = null;
    try {
        // TODO add your handling code here
        c = koneksi.getKoneksiJ();
        jScrollPane2.setVisible(true);
        JDBCAdapter ja = new JDBCAdapter(c);
        String sql = "";
        if (txtKodeDO.getText().equals("0")) {
            sql = "select b.KODEBARANG,b.NAMABARANG,j.JENIS as Merk,b.STOK,b.COGS "
                    + "from BARANG b,JENISBARANG j "
                    + "where b.IDJENIS = j.ID "
                    + "AND (b.KODEBARANG like '" + kodebarang.getText() + "%' or lower(b.NAMABARANG) like '%" + kodebarang.getText().toLowerCase() + "%') order by b.KODEBARANG";
        } else {
            sql = "SELECT DORINCI.KODEBARANG AS KODEBARANG,"
                    + "BARANG.NAMABARANG AS NAMABARANG,"
                    + "JENISBARANG.JENIS AS MERK,"
                    + "DORINCI.JUMLAH AS JUMLAH"
                    + " FROM"
                    + " PUBLIC.BARANG BARANG INNER JOIN PUBLIC.DORINCI DORINCI ON BARANG.KODEBARANG = DORINCI.KODEBARANG"
                    + " INNER JOIN PUBLIC.JENISBARANG JENISBARANG ON BARANG.IDJENIS = JENISBARANG.ID"
                    + " where DORINCI.IDDO='" + txtKodeDO.getText() + "' AND (DORINCI.KODEBARANG like '" + kodebarang.getText() + "%' or lower(BARANG.NAMABARANG) like '%" + kodebarang.getText().toLowerCase() + "%') order by DORINCI.KODEBARANG";
        }
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
    } catch (SQLException ex) {
        Logger.getLogger(DialogMutasiBarang.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
        if (c != null) {
            try {
                c.close();
            } catch (SQLException ex) {
                Logger.getLogger(DialogMutasiBarang.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}//GEN-LAST:event_kodebarangActionPerformed

private void jTable2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable2KeyPressed
// TODO add your handling code here:
    Connection c = null;
    if (evt.getKeyCode() == 10) {
        try {
            c = koneksi.getKoneksiJ();
            double hrgbaru = 0;
            barang b = new barang();
            b = dbbarang.getDetails(c, jTable2.getValueAt(jTable2.getSelectedRow(), 0).toString());
            hrgbaru = ((2.5 / 100) * b.getCOGS()) + b.getCOGS();
            if (jTabbedPane1.getSelectedIndex() == 0) {
                if (b.getSTOK() == 0) {
                    JOptionPane.showMessageDialog(null, "Stok Kosong");
                    kodebarang.requestFocus();
                } else if (b.getHARGA() == 0) {
                    JOptionPane.showMessageDialog(null, "Harga Jual Masih Kosong");
                    kodebarang.requestFocus();
                } else {
                    kodebarang.setText(b.getKODEBARANG());
                    namabarang.setText(b.getNAMABARANG());
                    harga.setText("" + b.getCOGS());
                    //harga.setText("" + df.format(b.getCOGS()));
                    //harga.setEnabled(false);
                    sisaBarang = Integer.parseInt(jTable2.getValueAt(jTable2.getSelectedRow(), 3).toString());
                    jScrollPane2.setVisible(false);
                    jumlah.requestFocus();
                }
            } else {
                kodebarang.setText(b.getKODEBARANG());
                namabarang.setText(b.getNAMABARANG());
                harga.setText("" + b.getCOGS());
                //harga.setText("" + df.format(b.getCOGS()));
                //harga.setEnabled(false);
                sisaBarang = Integer.parseInt(jTable2.getValueAt(jTable2.getSelectedRow(), 3).toString());
                jScrollPane2.setVisible(false);
                jumlah.requestFocus();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.toString());
        } finally {
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DialogMutasiBarang.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }
}//GEN-LAST:event_jTable2KeyPressed

private void btnBaruActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBaruActionPerformed
    Connection c = null;
    try {
        // TODO add your handling code here:
        c = koneksi.getKoneksiJ();
        kosongFaktur();
        kosongBarang();
        kosongHasil();
        hapusTabel();
        buatTabel();
        total = 0;
        hpp = 0;
        reloadData(c);
        setFaktur(c);
        kodepelanggan.requestFocus();
    } catch (SQLException ex) {
        Logger.getLogger(DialogMutasiBarang.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
        if (c != null) {
            try {
                c.close();
            } catch (SQLException ex) {
                Logger.getLogger(DialogMutasiBarang.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}//GEN-LAST:event_btnBaruActionPerformed

private void ppnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ppnKeyPressed
// TODO add your handling code here:
    if (evt.getKeyCode() == 10) {
        Connection c = null;
        try {
            c = koneksi.getKoneksiJ();
            hasilAkhir(c);
            txtSales.requestFocus();
        } catch (SQLException ex) {
            Logger.getLogger(DialogMutasiBarang.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DialogMutasiBarang.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}//GEN-LAST:event_ppnKeyPressed

private void diskonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_diskonKeyPressed
// TODO add your handling code here:
    if (evt.getKeyCode() == 10) {
        Connection c = null;
        try {
            c = koneksi.getKoneksiJ();
            hasilAkhir(c);
            ppn.requestFocus();
        } catch (SQLException ex) {
            Logger.getLogger(DialogMutasiBarang.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DialogMutasiBarang.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}//GEN-LAST:event_diskonKeyPressed

private void kodepelangganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kodepelangganKeyPressed
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
}//GEN-LAST:event_kodepelangganKeyPressed

private void kodepelangganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kodepelangganActionPerformed
    Connection c = null;
    try {
        // TODO add your handling code here:            
        c = koneksi.getKoneksiJ();
        pilPelanggan = "0";
        jScrollPane3.setVisible(true);
        JDBCAdapter ja = new JDBCAdapter(c);
        ja.executeQuery("select KODEPELANGGAN,NAMA from PELANGGAN where STATUSCABANG='1' and (KODEPELANGGAN like '" + kodepelanggan.getText() + "%' or lower(NAMA) like '%" + kodepelanggan.getText().toLowerCase() + "%')");
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
    } catch (SQLException ex) {
        Logger.getLogger(DialogMutasiBarang.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
        if (c != null) {
            try {
                c.close();
            } catch (SQLException ex) {
                Logger.getLogger(DialogMutasiBarang.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}//GEN-LAST:event_kodepelangganActionPerformed

private void statusbayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_statusbayarActionPerformed
    try {
        // TODO add your handling code here:
//    if (statusbayar.getSelectedIndex() == 1) {
//        settingTgl();
//    }
        Connection c = koneksi.getKoneksiJ();
        cekpembayaran(c);
        c.close();
    } catch (SQLException ex) {
        Logger.getLogger(DialogMutasiBarang.class.getName()).log(Level.SEVERE, null, ex);
    }
}//GEN-LAST:event_statusbayarActionPerformed

private void hasilDiskonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_hasilDiskonKeyPressed
// TODO add your handling code here:
    if (evt.getKeyCode() == 10) {
        //thasilDiskon = Double.parseDouble(hasilDiskon.getText());
        Connection c = null;
        try {
            c = koneksi.getKoneksiJ();
            hasilBayar.requestFocus();
            hasilAkhir(c);
        } catch (SQLException ex) {
            Logger.getLogger(DialogMutasiBarang.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DialogMutasiBarang.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    //hasilDiskon.setText(com.erv.function.Util.toMoney(thasilDiskon));
}//GEN-LAST:event_hasilDiskonKeyPressed

private void hasilBayarCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_hasilBayarCaretUpdate
// TODO add your handling code here:
    if (hasilBayar.getText().length() > 0) {
        sisa = ttotalbayarakhir - Double.parseDouble(hasilBayar.getText());
        hasilSisa.setText(com.erv.function.Util.toMoney(sisa));
    } else {
        hasilSisa.setText("");
    }
}//GEN-LAST:event_hasilBayarCaretUpdate

private void hasilBayarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_hasilBayarKeyPressed
// TODO add your handling code here:
    if (evt.getKeyCode() == 10) {
        Connection c = null;
        try {
            c=koneksi.getKoneksiJ();
            if (sisa > 0) {
                statusbayar.setSelectedIndex(1);
                settingTgl(c);
            } else {
                statusbayar.setSelectedIndex(0);
            }
            btnSimpanHasil.requestFocus();
        } catch (SQLException ex) {
            Logger.getLogger(DialogMutasiBarang.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if(c!=null){
                try {
                    c.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DialogMutasiBarang.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}//GEN-LAST:event_hasilBayarKeyPressed

private void btnSimpanHasilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanHasilActionPerformed
    Connection c = null;
    try {
        c = koneksi.getKoneksiJ();
        sisa = ttotalbayarakhir - Double.parseDouble(hasilBayar.getText());
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
                    } else {
                        String tgal[] = Util.split(tgl.getText(), "-");
                        String per = tgal[0] + "." + Integer.parseInt(tgal[1]);
                        if (cekperiodeAda(c, per)) {
                            if (cekperiode(c,per)) {
                                aksilog = "InsertJual";
                                prosesSimpan(c);
                                insertJurnal(c, "JUAL");
                                insertRinciJurnal(c);
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
                            } else {
                                pesan.add(new pesan("E", "Transaksi Untuk Periode Ini Sudah Di Tutup.. !", ""));
                                //JOptionPane.showMessageDialog(null, "Transaksi Untuk Periode Ini Sudah Di Tutup.. !");
                                kodepelanggan.requestFocus();
                            }
                        } else {
                            pesan.add(new pesan("E", "Transaksi Untuk Periode Ini Belum Dibuka.. !", ""));
                            //JOptionPane.showMessageDialog(null, "Transaksi Untuk Periode Ini Belum Dibuka.. !");
                            kodepelanggan.requestFocus();
                        }
                    }

                    String msg = "";
                    boolean ok = false;
                    for (ListIterator<pesan> lt = pesan.listIterator(); lt.hasNext();) {
                        pesan psn = lt.next();
                        if (psn.getHeader().equals("E")) {
                            msg += psn.getDeskripsi() + "\n";
                        } else {
                            ok = true;
                        }
                    }
                    if (ok) {
                        JOptionPane.showMessageDialog(null, "Sukses");
                        pesan.clear();
                    } else if (!msg.equals("")) {
                        JOptionPane.showMessageDialog(null, msg);
                        pesan.clear();

                        new penjualanDao().deleteDetails(c, IDjual);

                    }

                } else {
                    JOptionPane.showMessageDialog(null, "No Faktur Sudah Ada,Klik Tombol Generate Ulang No Faktur Lalu Simpan !");
                }
            } else {
                try {
                    if ((kodepelanggan1.getText().equals(""))) {
                        JOptionPane.showMessageDialog(null, "Data Belum Lengkap.. !");
                        kodepelanggan1.requestFocus();
                    } else {
                        String tgal[] = Util.split(tglRetur.getText(), "-");
                        String per = tgal[0] + "." + Integer.parseInt(tgal[1]);
                        if (cekperiodeAda(c, per)) {
                            if (cekperiode(c,per)) {
                                aksilog = "InsertReturJual";
                                prosesSimpanRetur(c);
                                insertJurnal(c, "RETUR");
                                insertRinciJurnalRetur(c);
                                prosesUpdateLog(c);
                                selesai(c);
                                cetakRetur(c);
                            } else {
                                JOptionPane.showMessageDialog(null, "Transaksi Untuk Periode Ini Sudah Di Tutup.. !");
                                kodepelanggan.requestFocus();
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Transaksi Untuk Periode Ini Belum Dibuka.. !");
                            kodepelanggan.requestFocus();
                        }
                    }
                } catch (ClassNotFoundException ex) {
                    JOptionPane.showMessageDialog(null, ex.toString());
                    try {
                        returDao.deleteFromRETUR(c, IDretur);
                    } catch (SQLException ex1) {
                        Logger.getLogger(DialogMutasiBarang.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex.toString());
                    try {
                        returDao.deleteFromRETUR(c, IDretur);
                    } catch (SQLException ex1) {
                        Logger.getLogger(DialogMutasiBarang.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                }
            }
            //selesai();

//        } catch (SQLException ex) {
//            Logger.getLogger(DialogPembelian.class.getName()).log(Level.SEVERE, null, ex);
//        }
        } else {
            System.out.print("tidak");
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e.getMessage());
    } finally {
        if (c != null) {
            try {
                c.close();
            } catch (SQLException ex) {
                Logger.getLogger(DialogMutasiBarang.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}//GEN-LAST:event_btnSimpanHasilActionPerformed

private void jTable3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable3KeyPressed
// TODO add your handling code here:
    if (evt.getKeyCode() == 10) {
        Connection c = null;
        try {
            c = koneksi.getKoneksiJ();
            pelanggan b = new pelanggan();
            pelangganDao dbpelanggan = new pelangganDao(c);
            b = dbpelanggan.getDetails(jTable3.getValueAt(jTable3.getSelectedRow(), 0).toString());
            if (pilPelanggan.equals("0")) {
                kodepelanggan.setText(b.getKODEPELANGGAN());
                namapelanggan.setText(b.getNAMA());
                jScrollPane3.setVisible(false);
                txtSales.requestFocus();
            } else if (pilPelanggan.equals("1")) {
                kodepelanggan1.setText(b.getKODEPELANGGAN());
                namapelanggan1.setText(b.getNAMA());
                jScrollPane3.setVisible(false);
                kodebarang.requestFocus();
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        } finally {
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DialogMutasiBarang.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}//GEN-LAST:event_jTable3KeyPressed

private void txtkodeReturActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtkodeReturActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_txtkodeReturActionPerformed

private void kodepelanggan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kodepelanggan1ActionPerformed
    Connection c = null;
    try {
        // TODO add your handling code here:
        c = koneksi.getKoneksiJ();
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
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, ex.getMessage());
    } finally {
        try {
            c.close();
        } catch (SQLException ex) {
            Logger.getLogger(DialogMutasiBarang.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
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

private void btnFakturActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFakturActionPerformed
// TODO add your handling code here:
    if (txtNofakturTemp.getText().equals("")) {
        JOptionPane.showMessageDialog(null, "Isi No Faktur yang akan di Preview.. !");
        txtNofakturTemp.requestFocus();
    } else {
        Connection c = null;
        try {            
            c = koneksi.getKoneksiJ();
            cetakFaktur1(c,"1");
        } catch (SQLException ex) {
            Logger.getLogger(DialogMutasiBarang.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if(c!=null){
                try {
                    c.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DialogMutasiBarang.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}//GEN-LAST:event_btnFakturActionPerformed

private void diskonitemKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_diskonitemKeyPressed
// TODO add your handling code here:
    if (evt.getKeyCode() == 10) {
        btninsert.requestFocus();
    }
}//GEN-LAST:event_diskonitemKeyPressed

private void cboStatDiskonItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboStatDiskonItemActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_cboStatDiskonItemActionPerformed

private void cboStatDiskonItemKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cboStatDiskonItemKeyPressed
}//GEN-LAST:event_cboStatDiskonItemKeyPressed

private void diskonitemFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_diskonitemFocusLost
// TODO add your handling code here:
//    txtPPN.requestFocus();
//    diskonitem.setValue(getDiskonitem());
//    txtPPN.setValue(getPPNItem());
    if (diskonitem.getText().equals("")) {
        diskonitem.setText("0");
        //btninsert.requestFocus();
    } else {
        btninsert.requestFocus();
    }
}//GEN-LAST:event_diskonitemFocusLost

private void txtPPNKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPPNKeyPressed
// TODO add your handling code here:
//    if (evt.getKeyCode() == 10) {
//        btninsert.requestFocus();
//    }
}//GEN-LAST:event_txtPPNKeyPressed

private void txtSalesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSalesActionPerformed
    Connection c = null;
    try {
        // TODO add your handling code here:
        c = koneksi.getKoneksiJ();
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
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, ex.getMessage());
    } finally {
        if (c != null) {
            try {
                c.close();
            } catch (SQLException ex) {
                Logger.getLogger(DialogMutasiBarang.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}//GEN-LAST:event_txtSalesActionPerformed

private void jTable5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable5KeyPressed
// TODO add your handling code here:
    if (evt.getKeyCode() == 10) {
        try {
            if (pil.equals("0")) {
                txtSales.setText(jTable5.getValueAt(jTable5.getSelectedRow(), 0).toString());
                txtNamaSales.setText(jTable5.getValueAt(jTable5.getSelectedRow(), 1).toString());
                jScrollPane5.setVisible(false);
                kodebarang.requestFocus();
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

private void tglOnCommit(datechooser.events.CommitEvent evt) {//GEN-FIRST:event_tglOnCommit
// TODO add your handling code here:
    //settingTgl();
}//GEN-LAST:event_tglOnCommit

private void tglOnSelectionChange(datechooser.events.SelectionChangedEvent evt) {//GEN-FIRST:event_tglOnSelectionChange
// TODO add your handling code here:
}//GEN-LAST:event_tglOnSelectionChange

private void jumlahFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jumlahFocusGained
// TODO add your handling code here:
//    try {
//        // TODO add your handling code here:
//        barang b = new barangDao(c).getDetails(kodebarang.getText());
//        if (b.getCOGS() > Double.parseDouble(harga.getText())) {
//            harga.requestFocus();
//            JOptionPane.showMessageDialog(null, "Harga Dibawah Modal");
//
//        }
//        b = null;
//    } catch (SQLException ex) {
//        Logger.getLogger(DialogMutasiBarang.class.getName()).log(Level.SEVERE, null, ex);
//    }
}//GEN-LAST:event_jumlahFocusGained

private void diskonitemFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_diskonitemFocusGained
// TODO add your handling code here:
//    try {
//        // TODO add your handling code here:
//        barang b = new barangDao(c).getDetails(kodebarang.getText());
//        if (sisaBarang < Integer.parseInt(jumlah.getText())) {
//            jumlah.requestFocus();
//            JOptionPane.showMessageDialog(null, "Stok Tinggal " + b.getSTOK());
//
//        }
//        b = null;
//    } catch (SQLException ex) {
//        Logger.getLogger(DialogMutasiBarang.class.getName()).log(Level.SEVERE, null, ex);
//    }
}//GEN-LAST:event_diskonitemFocusGained

private void btnCetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCetakActionPerformed
// TODO add your handling code here:
    if (txtNofakturTemp.getText().equals("")) {
        JOptionPane.showMessageDialog(null, "Isi No Faktur yang akan di Preview.. !");
        txtNofakturTemp.requestFocus();
    } else {
        ClassPrintCbg cpcbg = new ClassPrintCbg();
        try {
            if (txtNofakturTemp.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Isi No Faktur yang akan di Preview.. !");
                txtNofakturTemp.requestFocus();
            } else {
                //c1.cetak(c, nofaktur.getText());
                //c1.cetak(c, nofak);
                cpcbg.cetakfaktur(txtNofakturTemp.getText());
                //c1.cetakPrinter("c:\\temp\\write.txt");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.toString());
        }
    }

//    int item = 0;
//    if (txtNofakturTemp.getText().equals("")) {
//        JOptionPane.showMessageDialog(null, "Isi No Faktur yang akan di Preview.. !");
//        txtNofakturTemp.requestFocus();
//    } else {
//        ClassPrint c1 = new ClassPrint();
//        HashMap parameter = new HashMap();
//        JasperPrint jasperPrint = null;
//        parameter.put("nofaktur", txtNofakturTemp.getText());
//        try {
//            URL url;
//            InputStream in;
//            if (OptPendek.isSelected()) {
////                rincipenjualan rp = new rincipenjualan();
////                rincipenjualanDao dbrp = new rincipenjualanDao(c);
////                //dbpenjualan.getDetailID(txtNofakturTemp.getText());
////                item = dbrp.getJumlahItem(dbpenjualan.getDetailID(txtNofakturTemp.getText()).getID());
////                if (item > 15) {
////                    JOptionPane.showMessageDialog(null, "Gunakan Faktur Panjang,Jumlah item diatas 15 .. !");
////                    //OptPendek.setSelected(false);
//////                    OptPanjang.setSelected(true);
//////                    if (OptPanjang.isSelected()) {
//////                        OptPendek.setSelected(false);
//////                    } else {
//////                        OptPanjang.setSelected(true);
//////                    }
////                    //JOptionPane.showMessageDialog(null, "Gunakan Faktur Panjang,Jumlah item diatas 15 .. !" + item);
////                } else {
//                url = new URL(global.REPORT + "/LapPerFakturPendek.jasper");
//                in = url.openStream();
//                jasperPrint = JasperFillManager.fillReport(in, parameter, c);
////                }
//
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
//    }
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

private void txtKodeDOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtKodeDOActionPerformed
    Connection c = null;
    try {
        // TODO add your handling code here:
        c = koneksi.getKoneksiJ();
        pil = "1";
        jScrollPane5.setVisible(true);
        JDBCAdapter ja = new JDBCAdapter(c);
        ja.executeQuery("select d.ID,d.KODEDO,s.NAMA "
                + "from DO d, PELANGGAN s "
                + "where d.KODEPELANGGAN=s.KODEPELANGGAN "
                + "AND d.STATUS='N' AND d.STATUSAKTIF='1' AND (d.KODEDO like '" + txtKodeDO.getText() + "%')");
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
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, ex.getMessage());
    } finally {
        if (c != null) {
            try {
                c.close();
            } catch (SQLException ex) {
                Logger.getLogger(DialogMutasiBarang.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
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

private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
    try {
        // TODO add your handling code here:
        stat.close();
        cm.close();
    } catch (SQLException ex) {
        Logger.getLogger(DialogMutasiBarang.class.getName()).log(Level.SEVERE, null, ex);
    }

}//GEN-LAST:event_formWindowClosing

    private void nofakturKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nofakturKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == 10) {
            tgl.requestFocus();
        }

    }//GEN-LAST:event_nofakturKeyPressed

    private void nofakturFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_nofakturFocusLost
        Connection c = null;
        try {
            // TODO add your handling code here:
            c = koneksi.getKoneksiJ();
            if (!penjualanDao.cekFaktur(c, nofaktur.getText())) {
                tgl.requestFocus();
            } else {
                JOptionPane.showMessageDialog(null, "Nomor Faktur Penjualan Ini Sudah Ada.. !");
                nofaktur.requestFocus();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DialogMutasiBarang.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DialogMutasiBarang.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_nofakturFocusLost

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        Connection c = null;
        try {
            // TODO add your handling code here:
            c = koneksi.getKoneksiJ();
            kodebarang.setText(jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString());
            namabarang.setText(jTable1.getValueAt(jTable1.getSelectedRow(), 1).toString());
            harga.setText(jTable1.getValueAt(jTable1.getSelectedRow(), 3).toString());
            jumlah.setText(jTable1.getValueAt(jTable1.getSelectedRow(), 2).toString());
            cboStatDiskonItem.setSelectedIndex(0);
            Object a = Double.parseDouble(jTable1.getValueAt(jTable1.getSelectedRow(), 4).toString());
            diskonitem.setValue(a);
            txtPPN.setValue(0);
            model.removeRow(jTable1.getSelectedRow());
            reloadData(c);
        } catch (SQLException ex) {
            Logger.getLogger(DialogMutasiBarang.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DialogMutasiBarang.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }//GEN-LAST:event_jTable1MouseClicked

    private void BtnGenNumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnGenNumActionPerformed
        try {
            // TODO add your handling code here:
            Connection c = null;
            c = koneksi.getKoneksiJ();
            setFaktur(c);
            c.close();
        } catch (SQLException ex) {
            Logger.getLogger(DialogMutasiBarang.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_BtnGenNumActionPerformed

    private void btnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_btnKeluarActionPerformed

    private void btnKeluar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKeluar1ActionPerformed
        dispose();
    }//GEN-LAST:event_btnKeluar1ActionPerformed

    private void jumlahFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jumlahFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jumlahFocusLost

    private void txtTambahTotalFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTambahTotalFocusLost
        // TODO add your handling code here:
        if (getTextInteger(txtTambahTotal) < 0) {
            JOptionPane.showMessageDialog(null, "Nilai Diskon Salah !");
            txtTambahTotal.setText("0");
        } else {
            //reloadData();
            hasilBayar.requestFocus();
            //cekpembayaran();
        }
    }//GEN-LAST:event_txtTambahTotalFocusLost

    private void txtTambahTotalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTambahTotalKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == 10) {
            Connection c = null;
            try {
                c = koneksi.getKoneksiJ();
                reloadData(c);
                hasilBayar.requestFocus();
            } catch (SQLException ex) {
                Logger.getLogger(DialogMutasiBarang.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (c != null) {
                    try {
                        c.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(DialogMutasiBarang.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }//GEN-LAST:event_txtTambahTotalKeyPressed

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
            java.util.logging.Logger.getLogger(DialogMutasiBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DialogMutasiBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DialogMutasiBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DialogMutasiBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DialogMutasiBarang dialog = new DialogMutasiBarang(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnGenNum;
    private javax.swing.JLabel LabelKodeDO;
    private javax.swing.JTextField LabelPersen;
    private javax.swing.JButton btnBaru;
    private javax.swing.JButton btnCetak;
    private javax.swing.JButton btnFaktur;
    private javax.swing.JButton btnKeluar;
    private javax.swing.JButton btnKeluar1;
    private javax.swing.JButton btnSimpanHasil;
    private javax.swing.JButton btndelete;
    private javax.swing.JButton btninsert;
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
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
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
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JTable jTable5;
    private javax.swing.JTextField jumlah;
    private javax.swing.JTextField kodebarang;
    private javax.swing.JTextField kodepelanggan;
    private javax.swing.JTextField kodepelanggan1;
    private javax.swing.JTextField namabarang;
    private javax.swing.JTextField namapelanggan;
    private javax.swing.JTextField namapelanggan1;
    private javax.swing.JTextField nofaktur;
    private com.erv.function.PanelCool panelCool1;
    private javax.swing.JTextField ppn;
    private javax.swing.JComboBox statusbayar;
    private datechooser.beans.DateChooserCombo tgl;
    private datechooser.beans.DateChooserCombo tglRetur;
    private datechooser.beans.DateChooserCombo tgllunas;
    private javax.swing.JTextField txtKodeDO;
    private javax.swing.JTextField txtNamaDo;
    private javax.swing.JTextField txtNamaSales;
    private javax.swing.JTextField txtNofakturTemp;
    private javax.swing.JFormattedTextField txtPPN;
    private javax.swing.JTextField txtSales;
    private javax.swing.JTextField txtTambahTotal;
    private javax.swing.JTextField txtkodeRetur;
    // End of variables declaration//GEN-END:variables

    private void reloadData(Connection c) {
        jTable1.setFont(new Font("Tahoma", Font.BOLD, 12));
        TableColumn col = jTable1.getColumnModel().getColumn(0);
        col.setPreferredWidth(50);
        col = jTable1.getColumnModel().getColumn(1);
        col.setPreferredWidth(250);
        col = jTable1.getColumnModel().getColumn(7);
        col.setPreferredWidth(10);
        col = jTable1.getColumnModel().getColumn(8);
        col.setPreferredWidth(5);

        jScrollPane1.setFocusable(false); //
        jTable1.setFocusable(false); //
        jScrollPane1.getViewport().add(jTable1); //
        jScrollPane1.repaint(); //

        try {
            total = 0;
            hpp = 0;
            DiskonItem = 0;
            PPNItem = 0;

            if (model.getRowCount() != 0) {
                for (int i = 0; i < jTable1.getRowCount(); i++) {
                    total += Double.parseDouble(jTable1.getValueAt(i, 2).toString()) * Double.parseDouble(jTable1.getValueAt(i, 3).toString());
                    hpp += dbbarang.getDetails(c, jTable1.getValueAt(i, 0).toString()).getCOGS() * Double.parseDouble(jTable1.getValueAt(i, 2).toString());
                    DiskonItem += (Double.parseDouble(jTable1.getValueAt(i, 4).toString()) / 100) * (Double.parseDouble(jTable1.getValueAt(i, 2).toString()) * Double.parseDouble(jTable1.getValueAt(i, 3).toString()));
                    PPNItem += (Double.parseDouble(jTable1.getValueAt(i, 5).toString()) / 100) * ((Double.parseDouble(jTable1.getValueAt(i, 2).toString()) * Double.parseDouble(jTable1.getValueAt(i, 3).toString())) - ((Double.parseDouble(jTable1.getValueAt(i, 4).toString()) / 100) * (Double.parseDouble(jTable1.getValueAt(i, 2).toString()) * Double.parseDouble(jTable1.getValueAt(i, 3).toString()))));
                }
            } else {
                total = 0;
                hpp = 0;
                DiskonItem = 0;
                PPNItem = 0;
            }
            hasilAkhir(c);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.toString());
        } finally {
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DialogMutasiBarang.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    void hasilAkhir(Connection c) throws SQLException {
        String TtambahTotal = "";
        ttotalbayar = total + PPNItem - DiskonItem;
        if (cekCabangKhusus(c).equals(kodepelanggan.getText())) {
            TambahTotal = 0;
        } else {
            TambahTotal = (2.5 / 100) * ttotalbayar;
        }

        TtambahTotal = df.format(TambahTotal);
        ttambahtotalakhir = Double.parseDouble(TtambahTotal);
        ttotalbayarakhir = ttotalbayar + ttambahtotalakhir;
        txtTambahTotal.setText(com.erv.function.Util.toMoney(ttambahtotalakhir));
        hasilppn.setText(com.erv.function.Util.toMoney(PPNItem));
        hasilTotal.setText(com.erv.function.Util.toMoney(total));
        hasilDiskon.setText(com.erv.function.Util.toMoney(DiskonItem));
        hasilTotalBayar.setText(com.erv.function.Util.toMoney(ttotalbayar));

    }

    double getDiskon() {
        double hasil = 0;
        if (cboStatDiskon.getSelectedIndex() == 0) {
            hasil = (Double.parseDouble(diskon.getText()) / 100) * total;
        } else if (cboStatDiskon.getSelectedIndex() == 1) {
            hasil = Double.parseDouble(diskon.getText());
        }
        return hasil;
    }

    double getPPN() {
        double hasil = 0;
        if (cboStatPPN.getSelectedIndex() == 0) {
            hasil = (Double.parseDouble(ppn.getText()) / 100) * (float) total;
        } else if (cboStatPPN.getSelectedIndex() == 1) {
            hasil = Double.parseDouble(ppn.getText());
        }
        return hasil;
    }

    double getPPNItem() {
        double hasil = 0;
        hasil = 0.1 * (Double.parseDouble(harga.getText()) * Integer.parseInt(jumlah.getText()) - Float.parseFloat(diskonitem.getValue().toString()));
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
        txtKodeDO.setVisible(false);
        txtNamaDo.setVisible(false);
        LabelKodeDO.setVisible(false);

//        OptPendek.setSelected(true);
//        if (OptPendek.isSelected()) {
//            OptPanjang.setSelected(false);
//        } else {
//            OptPendek.setSelected(true);
//        }
    }

    private void kosongBarang() {
        kodebarang.setText("");
        namabarang.setText("");
        harga.setText("");
        diskonitem.setText("");
        jumlah.setText("");
        kodebarang.requestFocus();
        txtPPN.setText("0");
    }

    private void kosongHasil() {
        hasilBayar.setText("0");
        hasilDiskon.setText("0");
        hasilSisa.setText("0");
        hasilTotal.setText("0");
        hasilTotalBayar.setText("0");
        hasilppn.setText("0");
        txtTambahTotal.setText("0");
    }

    double getDiskonitem() {
        double hasil = 0;
        if (cboStatDiskonItem.getSelectedIndex() == 0) {
            hasil = (Double.parseDouble(diskonitem.getValue().toString()) / 100) * (Double.parseDouble(harga.getText()) * Double.parseDouble(jumlah.getText()));
        } else if (cboStatDiskonItem.getSelectedIndex() == 1) {
            hasil = Double.parseDouble(diskonitem.getValue().toString());
        }
        return hasil;
    }

    private void buatTabel() {
        String sqlCreate = "create table rinci (KODEBARANG varchar(20) primary key, "
                + "NAMABARANG varchar(200), "
                + "JUMLAH int, "
                + "HARGA double, "
                + "DISKON double, "
                + "PPN float, "
                + "TOTAL double,DO int)";
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

    private void setFaktur(Connection c) {
        try {
            IDjual = dbpenjualan.getID(c);
            nofaktur.setText(penjualanDao.setFakturPajak(c));
        } catch (Exception ex) {
            Logger.getLogger(DialogMutasiBarang.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void setRetur(Connection c) {
        try {
            IDretur = returDao.getID(c);
            txtkodeRetur.setText(returDao.setRetur(c));
        } catch (SQLException ex) {
            Logger.getLogger(DialogMutasiBarang.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void prosesSimpan(Connection c) throws SQLException {
        int idtemp = dbpenjualan.getID(c);
        if (idtemp >= IDjual) {
            IDjual = idtemp;
        }
        p.setID(IDjual);
        p.setFAKTUR(nofaktur.getText());
        p.setTANGGAL(tgl.getText());
        p.setKODEPELANGGAN(kodepelanggan.getText());
        p.setCASH(String.valueOf(statusbayar.getSelectedIndex()));
        p.setSTATUS(String.valueOf(statusbayar.getSelectedIndex()));
        p.setTGLLUNAS(tgllunas.getText());
        p.setPPN((float) getPPN());
        p.setIDSALES(txtSales.getText());
        p.setDP(tdp);
        p.setDISKON((float) getDiskon());
        p.setTAMBAHANTOTAL((float) ttambahtotalakhir);
        if (!dbpenjualan.insert(c, p)) {
            JOptionPane.showMessageDialog(this, "Entri Penjualan Ok");
            pesan.add(new pesan("S", "Entri Penjualan Ok", "" + p.getID()));
            Statement s = cm.createStatement();
            rincipenjualan rp = new rincipenjualan();
            stok st = new stok();
            rincipenjualanDao dbrp = new rincipenjualanDao();
            int count = 0;
            Statement statJual = c.createStatement();
            int IDjualRinci = dbrp.getID(c);
            try {
                statJual.execute("set autocommit false");
                for (int i = 0; i < jTable1.getRowCount(); i++) {
                    rp.setID(IDjualRinci);
                    rp.setIDPENJUALAN(IDjual);
                    rp.setKODEBARANG(jTable1.getValueAt(i, 0).toString());
                    rp.setJUMLAH(Integer.parseInt(jTable1.getValueAt(i, 2).toString()));
                    rp.setHARGA(Double.parseDouble(jTable1.getValueAt(i, 3).toString()));
                    rp.setDISKON(Float.parseFloat(jTable1.getValueAt(i, 4).toString()));
                    rp.setPPN(Float.parseFloat(jTable1.getValueAt(i, 5).toString()));
                    rp.setTOTAL(Double.parseDouble(jTable1.getValueAt(i, 6).toString()));
                    rp.setIDDO(Integer.parseInt(jTable1.getValueAt(i, 7).toString()));
                    statJual.addBatch("insert into RINCIPENJUALAN values(" + rp.getID() + ","
                            + rp.getIDPENJUALAN() + ",'"
                            + rp.getKODEBARANG() + "',"
                            + rp.getJUMLAH() + ","
                            + rp.getHARGA() + ","
                            + rp.getDISKON() + ","
                            + rp.getTOTAL() + ","
                            + rp.getPPN() + ","
                            + rp.getIDDO() + ")");
                    //dbrp.insert(rp);
                    st.setIDPENJUALAN(IDjual);
                    st.setKODEBARANG(jTable1.getValueAt(i, 0).toString());
                    st.setTANGGAL(tgl.getText());
                    st.setIN(0);
                    st.setOUT(Integer.parseInt(jTable1.getValueAt(i, 2).toString()));
                    st.setKODETRANS("J");
                    statJual.addBatch("insert into stok values(" + st.getIDPENJUALAN() + ",'"
                            + st.getKODEBARANG() + "','"
                            + st.getTANGGAL() + "',"
                            + st.getIN() + ","
                            + st.getOUT() + ",'"
                            + st.getKODETRANS() + "')");
                    count++;
                    if (count > 5) {
                        int[] updateCounts = statJual.executeBatch();
                        processUpdateCounts(updateCounts);
                        count = 0;
                        statJual.clearBatch();
                    }
                    IDjualRinci++;
                    //stokDao.insertIntoSTOK(c, st);
                }
                int[] updateCounts = statJual.executeBatch();
                processUpdateCounts(updateCounts);
                c.commit();
                statJual.execute("set autocommit true");
                pesan.add(new pesan("S", "Entri Penjualan Rinci dan Stok Ok", ""));
            } catch (BatchUpdateException e) {
                int[] updateCounts = e.getUpdateCounts();
                processUpdateCounts(updateCounts);
                pesan.add(new pesan("E", "Entri Penjualan Rinci / Stok Gagal", ""));
                // Either commit the successfully executed statements or rollback the entire batch
                c.rollback();
                statJual.execute("set autocommit true");
                System.out.println(e.toString());
            }
            jTable1.setModel(new javax.swing.table.DefaultTableModel(
                    new Object[][]{},
                    new String[]{
                        "Kode Barang", "Nama Barang", "Jumlah", "Harga", "Diskon", "PPN", "Total", "DO", "NO"
                    }));
            model = (DefaultTableModel) jTable1.getModel();
            s.close();
            statJual.close();
        } else {
            pesan.add(new pesan("E", "Entri Penjualan Gagal", "" + p.getID()));
            //JOptionPane.showMessageDialog(this, "Entri Penjualan Gagal");
        }
    }

    void prosesSimpanRetur(Connection c) throws SQLException {
        r.setID(IDretur);
        r.setKODERETUR(txtkodeRetur.getText());
        r.setKODEPELANGGAN(kodepelanggan1.getText());
        r.setTANGGAL(tglRetur.getText());
        r.setKETERANGAN("Pengembalian Brg Dari " + namapelanggan1.getText());
        r.setIDPENJUALAN(IDjual);
        r.setTAMBAHANTOTALRETUR((float) ttambahtotalakhir);
        if (returDao.insertIntoRETUR(c, r)) {
            JOptionPane.showMessageDialog(this, "Entri Retur Ok");
            Statement s = cm.createStatement();
//            ResultSet r4 = s.executeQuery("select * from rinci");
            rinciretur rr = new rinciretur();
            stok st = new stok();
            for (int i = 0; i < jTable1.getRowCount(); i++) {
                rr.setID(rincireturDao.getID(c));
                rr.setIDRETUR(IDretur);
                rr.setKODEBARANG(jTable1.getValueAt(i, 0).toString());
                rr.setJUMLAH(Integer.parseInt(jTable1.getValueAt(i, 2).toString()));
                rr.setHARGA(Float.parseFloat(jTable1.getValueAt(i, 3).toString()));
                rr.setTOTAL(Float.parseFloat(jTable1.getValueAt(i, 6).toString()));
                rr.setDISKON(Float.parseFloat(jTable1.getValueAt(i, 4).toString()));
                rr.setPPN(Float.parseFloat(jTable1.getValueAt(i, 5).toString()));
                rincireturDao.insertIntoRINCIRETUR(c, rr);
                st.setIDPENJUALAN(IDretur);
                st.setKODEBARANG(jTable1.getValueAt(i, 0).toString());
                st.setTANGGAL(tglRetur.getText());
                st.setIN(Integer.parseInt(jTable1.getValueAt(i, 2).toString()));
                st.setKODETRANS("R");
                st.setOUT(0);
                stokDao.insertIntoSTOK(c, st);
            }
            jTable1.setModel(new javax.swing.table.DefaultTableModel(
                    new Object[][]{},
                    new String[]{
                        "Kode Barang", "Nama Barang", "Jumlah", "Harga", "Diskon", "PPN", "Total", "DO", "NO"
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

    void prosesUpdate(Connection c) throws SQLException {
        p.setID(IDjual);
        p.setFAKTUR(nofaktur.getText());
        p.setTANGGAL(tgl.getText());
        p.setKODEPELANGGAN(kodepelanggan.getText());
        p.setCASH(String.valueOf(statusbayar.getSelectedIndex()));
        p.setTGLLUNAS(tgllunas.getText());
        p.setTGLLUNAS(tgllunas.getText());
        p.setPPN((float) getPPN());
        if (statusbayar.getSelectedIndex() == 1) {
            p.setDP(tdp);
        } else {
            p.setDP(0f);
        }
        p.setDISKON((float) getDiskon());
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
    }

    void prosesDelete(Connection c) throws SQLException {
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
    }

    void insertJurnal(Connection c, String pil) throws SQLException {
        jurnal j = new jurnal();
        IDJurnal = jurnalDao.getIDJurnal(c);
        j.setID(IDJurnal);
        if (pil.equals("JUAL")) {
            j.setTANGGAL(tgl.getText());
            j.setKODEJURNAL(nofaktur.getText());
            j.setDESKRIPSI("Pengiriman Barang Ke " + namapelanggan.getText());
        } else if (pil.equals("RETUR")) {
            j.setTANGGAL(tglRetur.getText());
            j.setKODEJURNAL(txtkodeRetur.getText());
            j.setDESKRIPSI("Pengembalian Barang Dari " + namapelanggan1.getText());
        }
        jurnalDao.insertIntoJURNAL(c, j);
    }

    void insertRinciJurnal(Connection c) throws SQLException {
        Statement s = null;
        s = c.createStatement();
        if (statusbayar.getSelectedIndex() == 0) {
            s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "KAS") + "'," + ttotalbayarakhir + ",0,1,'')");
//                if (diskont != 0.0) {
//                    s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "DISKONJUAL") + "'," + diskont + ",0,2)");
//                }
            if (PPNItem != 0) {
                s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "PPNJUAL") + "',0," + PPNItem + ",3,'')");
            }
            s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "PDPTCBG") + "',0," + ttambahtotalakhir + ",4,'')");
            //s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "HPP") + "'," + hpp + ",0,5)");
            s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "PERSEDIAAN") + "',0," + hpp + ",6,'')");

            s.executeBatch();
        } else if (statusbayar.getSelectedIndex() == 1) {
            pelangganDao dbplg = new pelangganDao(c);
            if (tdp > 0) {
                s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "KAS") + "'," + tdp + ",0,1,'')");
            }
            s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + dbplg.getDetails(kodepelanggan.getText()).getKODEAKUN() + "'," + sisa + ",0,1,'')");
            if (PPNItem != 0) {
                s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "PPNJUAL") + "',0," + PPNItem + ",3,'')");
            }

            s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "PDPTCBG") + "',0," + ttambahtotalakhir + ",4,'')");
            //s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "HPP") + "'," + hpp + ",0,5)");
            s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "PERSEDIAAN") + "',0," + hpp + ",5,'')");
            s.executeBatch();
            dbplg = null;
            prosesSimpanHutang(c);
        }
        s.close();
    }

    void insertRinciJurnalRetur(Connection c) throws SQLException, ClassNotFoundException {
        Statement s;
        piutangbayar pb = new piutangbayar();
        s = c.createStatement();
        if (totalhutang >= ttotalbayarakhir) {
            s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "PDPTCBG") + "'," + ttambahtotalakhir + ",0,1,'')");
            s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "PERSEDIAAN") + "'," + hpp + ",0,2,'')");
            s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + new pelangganDao(c).getDetails(kodepelanggan1.getText()).getKODEAKUN() + "',0," + ttotalbayarakhir + ",3,'')");
            //s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "RETURJUAL") + "'," + total + ",0,1)");
//            s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + new pelangganDao(c).getDetails(kodepelanggan1.getText()).getKODEAKUN() + "',0," + ttotalbayarakhir + ",1)");
//            //s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "DISKONJUAL") + "',0," + DiskonItem + ",3)");
//            s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "PDPTCBG") + "'," + ttambahtotalakhir + ",0,2)");
//            s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "PERSEDIAAN") + "'," + hpp + ",0,3)");
            //s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "HPP") + "',0," + hpp + ",6)");
            pb.setID(piutangbayarDao.getID(c));
            pb.setIDPIUTANG(piutangDao.getDetailPiutangperJual(c, IDjual).getID());
            pb.setKODEPIUTANGBAYAR(piutangbayarDao.getKodePiutangBayar(c));
            pb.setJUMLAH(ttotalbayarakhir);
            pb.setTANGGAL(tglRetur.getText());
            pb.setREF(txtkodeRetur.getText());
            piutangbayarDao.insertIntoPIUTANGBAYAR(c, pb);
        } else {
            int idpiutang = 0;
            double sisapiutang = 0;
            boolean cekstat = false;
            Statement spiutang = null;

//                String sqlpiutang = "select ID,IDPELANGGAN ,JUMLAH-JUMLAHBAYAR as sisa "
//                        + "from VIEW_PIUTANG where IDPELANGGAN='" + kodepelanggan1.getText() + "' "
//                        + "and (JUMLAH-JUMLAHBAYAR)>'" + ttotalbayar + "'";
            String sqlpiutang = "select ID,IDPELANGGAN ,JUMLAH-JUMLAHBAYAR as sisa "
                    + "from VIEW_PIUTANG where IDPELANGGAN='" + kodepelanggan1.getText() + "'";
            spiutang = c.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rspiutang = spiutang.executeQuery(sqlpiutang);
            if (rspiutang.next()) {
//                    idpiutang = rspiutang.getInt(1);
//                    sisapiutang = rspiutang.getDouble(3);
                cekstat = true;
            }

            if (cekstat) {
                s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "PDPTCBG") + "'," + ttambahtotalakhir + ",0,1,'')");
                s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "PERSEDIAAN") + "'," + hpp + ",0,2,'')");
                s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + new pelangganDao(c).getDetails(kodepelanggan1.getText()).getKODEAKUN() + "',0," + ttotalbayarakhir + ",3,'')");

//                    s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "RETURJUAL") + "'," + total + ",0,1)");
//                    s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + new pelangganDao(c).getDetails(kodepelanggan1.getText()).getKODEAKUN() + "',0," + ttotalbayarakhir + ",2)");
//                    s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "DISKONJUAL") + "',0," + DiskonItem + ",3)");
//                    s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "PPNJUAL") + "'," + PPNItem + ",0,4)");
//                    s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "PERSEDIAAN") + "'," + hpp + ",0,5)");
//                    s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "HPP") + "',0," + hpp + ",6)");
                rspiutang.beforeFirst();
                while (ttotalbayarakhir > 0) {
                    if (rspiutang.next()) {
                        if (rspiutang.getDouble(3) != 0) {
                            pb.setID(piutangbayarDao.getID(c));
                            //pb.setIDPIUTANG(piutangDao.getDetailPiutangperJual(c, IDjual).getID());
                            pb.setIDPIUTANG(rspiutang.getInt(1));
                            pb.setKODEPIUTANGBAYAR(piutangbayarDao.getKodePiutangBayar(c));
                            //pb.setJUMLAH(ttotalbayar);
                            if (ttotalbayarakhir >= rspiutang.getDouble(3)) {
                                pb.setJUMLAH(rspiutang.getDouble(3));
                                piutang pt = piutangDao.getDetails(c, rspiutang.getInt(1));
                                pt.setSTATUS("0");
                                piutangDao.updatePIUTANG(c, pt);
                            } else {
                                pb.setJUMLAH(ttotalbayarakhir);
                            }
                            pb.setTANGGAL(tglRetur.getText());
                            pb.setREF(txtkodeRetur.getText());
                            piutangbayarDao.insertIntoPIUTANGBAYAR(c, pb);
                            ttotalbayarakhir = ttotalbayarakhir - rspiutang.getDouble(3);
                        }
                        //System.out.println("error"+rspiutang.getDouble(3));
                    } else {
                        s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "PDPTCBG") + "'," + ttambahtotalakhir + ",0,1,'')");
                        s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "PERSEDIAAN") + "'," + hpp + ",0,2,'')");
                        s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "KAS") + "',0," + ttotalbayarakhir + ",3,'')");

//                            s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "RETURJUAL") + "'," + total + ",0,1)");
//                            s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "KAS") + "',0," + ttotalbayarakhir + ",2)");
//                            s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "DISKONJUAL") + "',0," + DiskonItem + ",3)");
//                            s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "PPNJUAL") + "'," + PPNItem + ",0,4)");
//                            s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "PERSEDIAAN") + "'," + hpp + ",0,5)");
//                            s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "HPP") + "',0," + hpp + ",6)");
                        break;
                    }

                }
            }
//                } else {
//                    s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "RETURJUAL") + "'," + total + ",0,1)");
//                    s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "KAS") + "',0," + ttotalbayar + ",2)");
//                    s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "DISKONJUAL") + "',0," + DiskonItem + ",3)");
//                    s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "PPNJUAL") + "'," + PPNItem + ",0,4)");
//                    s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "PERSEDIAAN") + "'," + hpp + ",0,5)");
//                    s.execute("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(c, "HPP") + "',0," + hpp + ",6)");
//                }

        }
        s.close();
        pb = null;

    }

    void selesai(Connection c) {
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
        panelCool1.add(jScrollPane3);
        jScrollPane3.setBounds(150, 120, 400, 150);
        panelCool1.setComponentZOrder(jScrollPane3, 0);
        jScrollPane3.setVisible(false);

        //jScrollPane3.setSize(500, 150);
        jScrollPane2.setSize(600, 150);
        panelCool1.add(jScrollPane5);
        jScrollPane5.setBounds(450, 170, 400, 100);
        panelCool1.setComponentZOrder(jScrollPane5, 0);
        jScrollPane5.setVisible(false);

//        panelCool1.add(jScrollPane4);
//        jScrollPane4.setBounds(160, 90, 400, 100);
//        panelCool1.setComponentZOrder(jScrollPane4, 1);
    }

    void prosesSimpanHutang(Connection c) throws SQLException {
        piutang ht = new piutang();
        ht.setKODEPIUTANG(piutangDao.getKodePiutang(c));
        ht.setIDPENJUALAN(IDjual);
        ht.setTANGGAL(tgl.getText());
        ht.setJUMLAH(sisa);
        ht.setIDPELANGGAN(kodepelanggan.getText());
        ht.setJATUHTEMPO(tgllunas.getText());
        ht.setKETERANGAN("Piutang Kepada " + namapelanggan.getText());
        ht.setSTATUS("1");
        ht.setID(piutangDao.getID(c));
        piutangDao.insertIntoPIUTANG(c, ht);
    }

    void cetakFaktur(Connection c, String pil) {
        HashMap parameter = new HashMap();
        JasperPrint jasperPrint = null;
        parameter.put("nofaktur", nofak);
        try {
            URL url;
            InputStream in;
            if (pil.equals("0")) {
                url = new URL(global.REPORT + "/LapPerFakturCbg.jasper");
                in = url.openStream();
                jasperPrint = JasperFillManager.fillReport(in, parameter, c);
            } else {
                url = new URL(global.REPORT + "/LapPerFakturCbg.jasper");
                in = url.openStream();
                jasperPrint = JasperFillManager.fillReport(in, parameter, c);
            }
            JasperViewer.viewReport(jasperPrint, false);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    void cetakFaktur1(Connection c, String pil) {
        HashMap parameter = new HashMap();
        JasperPrint jasperPrint = null;
        parameter.put("nofaktur", txtNofakturTemp.getText());
        try {
            URL url;
            InputStream in;
            if (pil.equals("0")) {
                url = new URL(global.REPORT + "/LapPerFakturCbg.jasper");
                in = url.openStream();
                jasperPrint = JasperFillManager.fillReport(in, parameter, c);
            } else {
                url = new URL(global.REPORT + "/LapPerFakturCbg.jasper");
                in = url.openStream();
                jasperPrint = JasperFillManager.fillReport(in, parameter, c);

            }
            JasperViewer.viewReport(jasperPrint, false);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    void cetakRetur(Connection c) {
        HashMap parameter = new HashMap();
        JasperPrint jasperPrint = null;
        parameter.put("koderetur", txtkodeRetur.getText());
        try {
            URL url = new URL(global.REPORT + "/ReturCbg.jasper");
            InputStream in = url.openStream();
            jasperPrint = JasperFillManager.fillReport(in, parameter, c);
            JasperViewer.viewReport(jasperPrint, false);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    public void settingTgl(Connection c) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(Fungsi.dateAdd(c, "DAY", 30, java.sql.Date.valueOf(tgl.getText())));
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

    public String cekCabangKhusus(Connection c) throws SQLException {
        //String periode = thn + "." + bln;
        String ada = "";
        Statement s = c.createStatement();
        ResultSet rs = s.executeQuery("select * from PELANGGAN where KODEPELANGGAN='2'");
        if (rs.next()) {
            if (rs.getString(1) != null) {
                ada = rs.getString(1);
            }
        }
        rs.close();
        s.close();
        return ada;
    }

    void prosesUpdateLog(Connection c) {
        //java.sql.Date tanggallog;
        String tanggallog;
        String jamlog = u.jamsekarang + ":" + u.menitsekarang + ":" + u.detiksekarang;
        //tanggallog = java.sql.Date.valueOf(u.thnsekarang + "-" + u.blnsekarang + "-" + u.tglsekarang);
        tanggallog = u.thnsekarang + "-" + u.blnsekarang + "-" + u.tglsekarang;
        try {
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
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.toString());
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

    void cekpembayaran(Connection c) {
        reloadData(c);
        if (statusbayar.getSelectedIndex() == 0) {
            hasilBayar.setText("" + df0.format(ttotalbayarakhir));
        } else if (statusbayar.getSelectedIndex() == 1) {
            settingTgl(c);
            if (getTextDouble(hasilBayar) == ttotalbayarakhir) {
                hasilBayar.setText("0");
                sisa = ttotalbayarakhir - Double.parseDouble(hasilBayar.getText());
                hasilSisa.setText(com.erv.function.Util.toMoney(sisa));
            } else if (Double.parseDouble(hasilBayar.getText()) > 0) {
                sisa = ttotalbayarakhir - Double.parseDouble(hasilBayar.getText());
                hasilSisa.setText(com.erv.function.Util.toMoney(sisa));
            } else {
                hasilBayar.setText("0");
                sisa = ttotalbayarakhir - Double.parseDouble(hasilBayar.getText());
                hasilSisa.setText(com.erv.function.Util.toMoney(sisa));
            }
        }
    }

}
