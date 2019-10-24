/*
 * JavarieSoftView.java
 */
package javariesoft;

import com.eigher.form.FLapRekapHutangSupplier;
import com.eigher.form.FLapRekapPiutangPelanggan;
import com.eigher.form.LaporanKartuStokTanggal;
import com.erv.db.DatabaseBackup;
import com.erv.db.H2DatabaseBackup;
import com.erv.db.koneksi;
import com.erv.view.FormLapPajakBeli;
import com.erv.view.FormLapPajakJual;
import com.erv.view.FormLapPajakReturBeli;
import com.erv.view.FormLapPajakReturJual;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * The application's main frame.
 */
public class JavarieSoftView extends FrameView {
//public static String jenisuser="";

//     public static Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

    public JavarieSoftView(SingleFrameApplication app) {
        super(app);

        initComponents();
//        jLabel1.setSize(dim.width - 40, 80);
//        jLabel1.setLocation(jLabel1.getX() + 350, jLabel1.getY() + jLabel1.getHeight() - 32);
//        jLabel2.setSize(dim.width - 40, 60);
//        jLabel2.setLocation(jLabel2.getX() + 470, jLabel2.getY() + jLabel2.getHeight() - 16);
        jLabel1.setSize(dim.width - 40, 80);       
        jLabel1.setLocation(dim.width / 2 - jLabel1.getWidth() /2, jLabel1.getY() + jLabel1.getHeight() - 14);        
        jLabel2.setSize(dim.width - 40, 60);
        jLabel2.setLocation(dim.width / 2 - jLabel2.getWidth() / 2, jLabel2.getY() + jLabel2.getHeight() + 10);
        nonaktif();
        aktif();                                                                       
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(JavarieSoftView.class.getName()).log(Level.SEVERE, null, ex);
        }
//        try {
//            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
//        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(JavarieSoftView.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            Logger.getLogger(JavarieSoftView.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            Logger.getLogger(JavarieSoftView.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (UnsupportedLookAndFeelException ex) {
//            Logger.getLogger(JavarieSoftView.class.getName()).log(Level.SEVERE, null, ex);
//        }
        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                statusMessageLabel.setText("");
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        statusAnimationLabel.setIcon(idleIcon);
        progressBar.setVisible(false);

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {

            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    String text = (String) (evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer) (evt.getNewValue());
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(value);
                }
            }
        });
        //this.getFrame().setUndecorated(true);
        this.getFrame().addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }

        });
    }

    private void formWindowClosing(WindowEvent evt) {
        backupDB();
    }

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = JavarieSoftApp.getApplication().getMainFrame();
            aboutBox = new JavarieSoftAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        JavarieSoftApp.getApplication().show(aboutBox);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        panelCool1 = new com.erv.model.DesktopCool();
        LblJenis = new javax.swing.JLabel();
        LblJenis1 = new javax.swing.JLabel();
        LblJenis2 = new javax.swing.JLabel();
        LblJenis3 = new javax.swing.JLabel();
        LblJenis4 = new javax.swing.JLabel();
        Lblgroup = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        Mlogin = new javax.swing.JMenu();
        Mloginuser = new javax.swing.JMenuItem();
        MlogOf = new javax.swing.JMenuItem();
        Mkonfigurasi = new javax.swing.JMenu();
        MBackupdatabase = new javax.swing.JMenuItem();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        MuserAccount = new javax.swing.JMenuItem();
        MeditPassword = new javax.swing.JMenuItem();
        MaktivaTetap = new javax.swing.JMenuItem();
        Mperkiraan = new javax.swing.JMenuItem();
        Mbarang = new javax.swing.JMenu();
        Msatuan = new javax.swing.JMenuItem();
        Mmerk = new javax.swing.JMenuItem();
        Mkategori = new javax.swing.JMenuItem();
        Mdatabarang = new javax.swing.JMenuItem();
        Mpelanggan = new javax.swing.JMenuItem();
        Msupplier = new javax.swing.JMenuItem();
        Msales = new javax.swing.JMenuItem();
        Mbank = new javax.swing.JMenuItem();
        Msetting = new javax.swing.JMenuItem();
        MsettingStatPeriode = new javax.swing.JMenuItem();
        MKontrolTanggal = new javax.swing.JMenuItem();
        MlogHistory = new javax.swing.JMenuItem();
        Manalisa = new javax.swing.JMenu();
        Manalisastokbrg = new javax.swing.JMenuItem();
        MSearchingBarang = new javax.swing.JMenuItem();
        MNomorPajak = new javax.swing.JMenuItem();
        Mtransaksi = new javax.swing.JMenu();
        Mjurnal = new javax.swing.JMenuItem();
        Mpenjualan = new javax.swing.JMenuItem();
        Mpembelian = new javax.swing.JMenuItem();
        Mhutang = new javax.swing.JMenuItem();
        Mgirokeluar = new javax.swing.JMenuItem();
        Mpiutang = new javax.swing.JMenuItem();
        Mgiromasuk = new javax.swing.JMenuItem();
        MkoreksiStok = new javax.swing.JMenuItem();
        Mmutasibarang = new javax.swing.JMenuItem();
        MValisasiRetur = new javax.swing.JMenuItem();
        MDeliveryOrder = new javax.swing.JMenuItem();
        Mtutupstok = new javax.swing.JMenuItem();
        Mtutupbuku = new javax.swing.JMenuItem();
        MTutupHarian = new javax.swing.JMenuItem();
        MPajak = new javax.swing.JMenuItem();
        Mlaporan = new javax.swing.JMenu();
        Mlappembelian = new javax.swing.JMenu();
        MLapPembelianPerFaktur = new javax.swing.JMenuItem();
        MLapPembelianHarian = new javax.swing.JMenuItem();
        MRekapPembelianHarian = new javax.swing.JMenuItem();
        Mreturpembelian = new javax.swing.JMenu();
        Mreturbelifaktur = new javax.swing.JMenuItem();
        Mrekapreturbeli = new javax.swing.JMenuItem();
        MlapPenjualan = new javax.swing.JMenu();
        jMenuItem11 = new javax.swing.JMenuItem();
        jMenuItem26 = new javax.swing.JMenuItem();
        jMenuItem27 = new javax.swing.JMenuItem();
        jMenuItem28 = new javax.swing.JMenuItem();
        jMenuItem21 = new javax.swing.JMenuItem();
        Mreturpenjualan = new javax.swing.JMenu();
        Mreturjualfaktur = new javax.swing.JMenuItem();
        Mrekapreturjual = new javax.swing.JMenuItem();
        MlapPengiriman = new javax.swing.JMenu();
        MLapPengirimanPerFaktur = new javax.swing.JMenuItem();
        MdetailPengiriman = new javax.swing.JMenuItem();
        MLapRekapPengiriman = new javax.swing.JMenuItem();
        MReturCabang = new javax.swing.JMenu();
        MReturCabangFaktur = new javax.swing.JMenuItem();
        MRekapReturCabang = new javax.swing.JMenuItem();
        Mlapdeliveryorder = new javax.swing.JMenu();
        MlapFakturDO = new javax.swing.JMenuItem();
        MrekapDO = new javax.swing.JMenuItem();
        MlapBarang = new javax.swing.JMenu();
        MlapBarangPerMerk = new javax.swing.JMenuItem();
        MLapBarangPerKategori = new javax.swing.JMenuItem();
        MLapBrgAkhirPeriode = new javax.swing.JMenuItem();
        MLapKartuStokBulan = new javax.swing.JMenuItem();
        MLapKartuStokTanggal = new javax.swing.JMenuItem();
        MLapPersediaanBrgDagang = new javax.swing.JMenuItem();
        MLapKatalogBarang = new javax.swing.JMenuItem();
        MLapStokBarang = new javax.swing.JMenuItem();
        MLapRekapBarangPerMerk = new javax.swing.JMenuItem();
        MlapHutang = new javax.swing.JMenu();
        MlapUmurHutang = new javax.swing.JMenuItem();
        MRekapHutangSupplier = new javax.swing.JMenuItem();
        MlapPiutang = new javax.swing.JMenu();
        MlapUmurPiutang = new javax.swing.JMenuItem();
        MlapUmurPiutangPersales = new javax.swing.JMenuItem();
        MRekapPiutangPelanggan = new javax.swing.JMenuItem();
        MLapPerkiraan = new javax.swing.JMenuItem();
        MlapFakturPajak = new javax.swing.JMenuItem();
        MlapTutupBuku = new javax.swing.JMenuItem();
        MlapJurnal = new javax.swing.JMenuItem();
        MLapBukuBesar = new javax.swing.JMenu();
        MlapBukuBesarHarian = new javax.swing.JMenuItem();
        MlapBukuBesarBulanan = new javax.swing.JMenuItem();
        MLapBukuBesarBiayaTahunan = new javax.swing.JMenuItem();
        MlapNeraca = new javax.swing.JMenuItem();
        MlapRugiLaba = new javax.swing.JMenuItem();
        MlapEkuitas = new javax.swing.JMenuItem();
        MLapPajak = new javax.swing.JMenu();
        MLapPajakPembelian = new javax.swing.JMenuItem();
        MLapPajakReturPembelian = new javax.swing.JMenuItem();
        MLapPajakPenjualan = new javax.swing.JMenuItem();
        MLapPajakReturPenjualan = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        Mkeluar = new javax.swing.JMenu();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();

        mainPanel.setName("mainPanel"); // NOI18N
        mainPanel.setLayout(new java.awt.BorderLayout());

        panelCool1.setName("panelCool1"); // NOI18N
        panelCool1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelCool1MouseClicked(evt);
            }
        });
        panelCool1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                panelCool1MouseMoved(evt);
            }
        });

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance().getContext().getResourceMap(JavarieSoftView.class);
        LblJenis.setFont(resourceMap.getFont("LblJenis4.font")); // NOI18N
        LblJenis.setForeground(resourceMap.getColor("LblJenis.foreground")); // NOI18N
        LblJenis.setText(resourceMap.getString("LblJenis.text")); // NOI18N
        LblJenis.setName("LblJenis"); // NOI18N
        panelCool1.add(LblJenis);
        LblJenis.setBounds(120, 0, 200, 20);

        LblJenis1.setFont(resourceMap.getFont("LblJenis4.font")); // NOI18N
        LblJenis1.setForeground(resourceMap.getColor("LblJenis1.foreground")); // NOI18N
        LblJenis1.setText(resourceMap.getString("LblJenis1.text")); // NOI18N
        LblJenis1.setName("LblJenis1"); // NOI18N
        panelCool1.add(LblJenis1);
        LblJenis1.setBounds(100, 0, 10, 20);

        LblJenis2.setFont(resourceMap.getFont("LblJenis4.font")); // NOI18N
        LblJenis2.setForeground(resourceMap.getColor("LblJenis2.foreground")); // NOI18N
        LblJenis2.setText(resourceMap.getString("LblJenis2.text")); // NOI18N
        LblJenis2.setName("LblJenis2"); // NOI18N
        panelCool1.add(LblJenis2);
        LblJenis2.setBounds(10, 0, 80, 20);

        LblJenis3.setFont(resourceMap.getFont("LblJenis4.font")); // NOI18N
        LblJenis3.setForeground(resourceMap.getColor("LblJenis3.foreground")); // NOI18N
        LblJenis3.setText(resourceMap.getString("LblJenis3.text")); // NOI18N
        LblJenis3.setName("LblJenis3"); // NOI18N
        panelCool1.add(LblJenis3);
        LblJenis3.setBounds(10, 20, 80, 20);

        LblJenis4.setFont(resourceMap.getFont("LblJenis4.font")); // NOI18N
        LblJenis4.setForeground(resourceMap.getColor("LblJenis4.foreground")); // NOI18N
        LblJenis4.setText(resourceMap.getString("LblJenis4.text")); // NOI18N
        LblJenis4.setName("LblJenis4"); // NOI18N
        panelCool1.add(LblJenis4);
        LblJenis4.setBounds(100, 20, 10, 20);

        Lblgroup.setFont(resourceMap.getFont("LblJenis4.font")); // NOI18N
        Lblgroup.setForeground(resourceMap.getColor("Lblgroup.foreground")); // NOI18N
        Lblgroup.setText(resourceMap.getString("Lblgroup.text")); // NOI18N
        Lblgroup.setName("Lblgroup"); // NOI18N
        panelCool1.add(Lblgroup);
        Lblgroup.setBounds(120, 20, 200, 20);

        jLabel1.setFont(resourceMap.getFont("jLabel1.font")); // NOI18N
        jLabel1.setForeground(resourceMap.getColor("jLabel1.foreground")); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N
        panelCool1.add(jLabel1);
        jLabel1.setBounds(80, 30, 640, 50);

        jLabel2.setFont(resourceMap.getFont("jLabel2.font")); // NOI18N
        jLabel2.setForeground(resourceMap.getColor("jLabel2.foreground")); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N
        panelCool1.add(jLabel2);
        jLabel2.setBounds(260, 70, 460, 40);

        mainPanel.add(panelCool1, java.awt.BorderLayout.CENTER);

        menuBar.setName("menuBar"); // NOI18N

        Mlogin.setIcon(resourceMap.getIcon("Mlogin.icon")); // NOI18N
        Mlogin.setText(resourceMap.getString("Mlogin.text")); // NOI18N
        Mlogin.setName("Mlogin"); // NOI18N

        Mloginuser.setIcon(resourceMap.getIcon("Mloginuser.icon")); // NOI18N
        Mloginuser.setText(resourceMap.getString("Mloginuser.text")); // NOI18N
        Mloginuser.setName("Mloginuser"); // NOI18N
        Mloginuser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MloginuserActionPerformed(evt);
            }
        });
        Mlogin.add(Mloginuser);

        MlogOf.setIcon(resourceMap.getIcon("MlogOf.icon")); // NOI18N
        MlogOf.setText(resourceMap.getString("MlogOf.text")); // NOI18N
        MlogOf.setName("MlogOf"); // NOI18N
        MlogOf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MlogOfActionPerformed(evt);
            }
        });
        Mlogin.add(MlogOf);

        menuBar.add(Mlogin);

        Mkonfigurasi.setIcon(resourceMap.getIcon("Mkonfigurasi.icon")); // NOI18N
        Mkonfigurasi.setText(resourceMap.getString("Mkonfigurasi.text")); // NOI18N
        Mkonfigurasi.setName("Mkonfigurasi"); // NOI18N

        MBackupdatabase.setIcon(resourceMap.getIcon("MBackupdatabase.icon")); // NOI18N
        MBackupdatabase.setText(resourceMap.getString("MBackupdatabase.text")); // NOI18N
        MBackupdatabase.setName("MBackupdatabase"); // NOI18N
        MBackupdatabase.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MBackupdatabaseActionPerformed(evt);
            }
        });
        Mkonfigurasi.add(MBackupdatabase);

        menuBar.add(Mkonfigurasi);

        fileMenu.setIcon(resourceMap.getIcon("fileMenu.icon")); // NOI18N
        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        MuserAccount.setIcon(resourceMap.getIcon("MuserAccount.icon")); // NOI18N
        MuserAccount.setText(resourceMap.getString("MuserAccount.text")); // NOI18N
        MuserAccount.setName("MuserAccount"); // NOI18N
        MuserAccount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MuserAccountActionPerformed(evt);
            }
        });
        fileMenu.add(MuserAccount);

        MeditPassword.setIcon(resourceMap.getIcon("MeditPassword.icon")); // NOI18N
        MeditPassword.setText(resourceMap.getString("MeditPassword.text")); // NOI18N
        MeditPassword.setName("MeditPassword"); // NOI18N
        MeditPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MeditPasswordActionPerformed(evt);
            }
        });
        fileMenu.add(MeditPassword);

        MaktivaTetap.setIcon(resourceMap.getIcon("MaktivaTetap.icon")); // NOI18N
        MaktivaTetap.setText(resourceMap.getString("MaktivaTetap.text")); // NOI18N
        MaktivaTetap.setName("MaktivaTetap"); // NOI18N
        MaktivaTetap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MaktivaTetapActionPerformed(evt);
            }
        });
        fileMenu.add(MaktivaTetap);

        Mperkiraan.setIcon(resourceMap.getIcon("Mperkiraan.icon")); // NOI18N
        Mperkiraan.setText(resourceMap.getString("Mperkiraan.text")); // NOI18N
        Mperkiraan.setName("Mperkiraan"); // NOI18N
        Mperkiraan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MperkiraanActionPerformed(evt);
            }
        });
        fileMenu.add(Mperkiraan);

        Mbarang.setIcon(resourceMap.getIcon("Mbarang.icon")); // NOI18N
        Mbarang.setText(resourceMap.getString("Mbarang.text")); // NOI18N
        Mbarang.setName("Mbarang"); // NOI18N

        Msatuan.setIcon(resourceMap.getIcon("Msatuan.icon")); // NOI18N
        Msatuan.setText(resourceMap.getString("Msatuan.text")); // NOI18N
        Msatuan.setName("Msatuan"); // NOI18N
        Msatuan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MsatuanActionPerformed(evt);
            }
        });
        Mbarang.add(Msatuan);

        Mmerk.setIcon(resourceMap.getIcon("Mmerk.icon")); // NOI18N
        Mmerk.setText(resourceMap.getString("Mmerk.text")); // NOI18N
        Mmerk.setName("Mmerk"); // NOI18N
        Mmerk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MmerkActionPerformed(evt);
            }
        });
        Mbarang.add(Mmerk);

        Mkategori.setIcon(resourceMap.getIcon("Mkategori.icon")); // NOI18N
        Mkategori.setText(resourceMap.getString("Mkategori.text")); // NOI18N
        Mkategori.setName("Mkategori"); // NOI18N
        Mkategori.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MkategoriActionPerformed(evt);
            }
        });
        Mbarang.add(Mkategori);

        Mdatabarang.setIcon(resourceMap.getIcon("Mdatabarang.icon")); // NOI18N
        Mdatabarang.setText(resourceMap.getString("Mdatabarang.text")); // NOI18N
        Mdatabarang.setName("Mdatabarang"); // NOI18N
        Mdatabarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MdatabarangActionPerformed(evt);
            }
        });
        Mbarang.add(Mdatabarang);

        fileMenu.add(Mbarang);

        Mpelanggan.setIcon(resourceMap.getIcon("Mpelanggan.icon")); // NOI18N
        Mpelanggan.setText(resourceMap.getString("Mpelanggan.text")); // NOI18N
        Mpelanggan.setName("Mpelanggan"); // NOI18N
        Mpelanggan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MpelangganActionPerformed(evt);
            }
        });
        fileMenu.add(Mpelanggan);

        Msupplier.setIcon(resourceMap.getIcon("Msupplier.icon")); // NOI18N
        Msupplier.setText(resourceMap.getString("Msupplier.text")); // NOI18N
        Msupplier.setName("Msupplier"); // NOI18N
        Msupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MsupplierActionPerformed(evt);
            }
        });
        fileMenu.add(Msupplier);

        Msales.setIcon(resourceMap.getIcon("Msales.icon")); // NOI18N
        Msales.setText(resourceMap.getString("Msales.text")); // NOI18N
        Msales.setName("Msales"); // NOI18N
        Msales.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MsalesActionPerformed(evt);
            }
        });
        fileMenu.add(Msales);

        Mbank.setIcon(resourceMap.getIcon("Mbank.icon")); // NOI18N
        Mbank.setText(resourceMap.getString("Mbank.text")); // NOI18N
        Mbank.setName("Mbank"); // NOI18N
        Mbank.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MbankActionPerformed(evt);
            }
        });
        fileMenu.add(Mbank);

        Msetting.setIcon(resourceMap.getIcon("Msetting.icon")); // NOI18N
        Msetting.setText(resourceMap.getString("Msetting.text")); // NOI18N
        Msetting.setName("Msetting"); // NOI18N
        Msetting.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MsettingActionPerformed(evt);
            }
        });
        fileMenu.add(Msetting);

        MsettingStatPeriode.setIcon(resourceMap.getIcon("MsettingStatPeriode.icon")); // NOI18N
        MsettingStatPeriode.setText(resourceMap.getString("MsettingStatPeriode.text")); // NOI18N
        MsettingStatPeriode.setName("MsettingStatPeriode"); // NOI18N
        MsettingStatPeriode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MsettingStatPeriodeActionPerformed(evt);
            }
        });
        fileMenu.add(MsettingStatPeriode);

        MKontrolTanggal.setIcon(resourceMap.getIcon("MKontrolTanggal.icon")); // NOI18N
        MKontrolTanggal.setText(resourceMap.getString("MKontrolTanggal.text")); // NOI18N
        MKontrolTanggal.setName("MKontrolTanggal"); // NOI18N
        MKontrolTanggal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MKontrolTanggalActionPerformed(evt);
            }
        });
        fileMenu.add(MKontrolTanggal);

        MlogHistory.setIcon(resourceMap.getIcon("MlogHistory.icon")); // NOI18N
        MlogHistory.setText(resourceMap.getString("MlogHistory.text")); // NOI18N
        MlogHistory.setName("MlogHistory"); // NOI18N
        MlogHistory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MlogHistoryActionPerformed(evt);
            }
        });
        fileMenu.add(MlogHistory);

        Manalisa.setIcon(resourceMap.getIcon("Manalisa.icon")); // NOI18N
        Manalisa.setText(resourceMap.getString("Manalisa.text")); // NOI18N
        Manalisa.setName("Manalisa"); // NOI18N

        Manalisastokbrg.setText(resourceMap.getString("Manalisastokbrg.text")); // NOI18N
        Manalisastokbrg.setName("Manalisastokbrg"); // NOI18N
        Manalisastokbrg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ManalisastokbrgActionPerformed(evt);
            }
        });
        Manalisa.add(Manalisastokbrg);

        fileMenu.add(Manalisa);

        MSearchingBarang.setIcon(resourceMap.getIcon("MSearchingBarang.icon")); // NOI18N
        MSearchingBarang.setText(resourceMap.getString("MSearchingBarang.text")); // NOI18N
        MSearchingBarang.setName("MSearchingBarang"); // NOI18N
        MSearchingBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MSearchingBarangActionPerformed(evt);
            }
        });
        fileMenu.add(MSearchingBarang);

        MNomorPajak.setIcon(resourceMap.getIcon("MNomorPajak.icon")); // NOI18N
        MNomorPajak.setText(resourceMap.getString("MNomorPajak.text")); // NOI18N
        MNomorPajak.setName("MNomorPajak"); // NOI18N
        MNomorPajak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MNomorPajakActionPerformed(evt);
            }
        });
        fileMenu.add(MNomorPajak);

        menuBar.add(fileMenu);

        Mtransaksi.setIcon(resourceMap.getIcon("Mtransaksi.icon")); // NOI18N
        Mtransaksi.setText(resourceMap.getString("Mtransaksi.text")); // NOI18N
        Mtransaksi.setName("Mtransaksi"); // NOI18N
        Mtransaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MtransaksiActionPerformed(evt);
            }
        });

        Mjurnal.setIcon(resourceMap.getIcon("Mjurnal.icon")); // NOI18N
        Mjurnal.setText(resourceMap.getString("Mjurnal.text")); // NOI18N
        Mjurnal.setName("Mjurnal"); // NOI18N
        Mjurnal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MjurnalActionPerformed(evt);
            }
        });
        Mtransaksi.add(Mjurnal);

        Mpenjualan.setIcon(resourceMap.getIcon("Mpenjualan.icon")); // NOI18N
        Mpenjualan.setText(resourceMap.getString("Mpenjualan.text")); // NOI18N
        Mpenjualan.setName("Mpenjualan"); // NOI18N
        Mpenjualan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MpenjualanActionPerformed(evt);
            }
        });
        Mtransaksi.add(Mpenjualan);

        Mpembelian.setIcon(resourceMap.getIcon("Mpembelian.icon")); // NOI18N
        Mpembelian.setText(resourceMap.getString("Mpembelian.text")); // NOI18N
        Mpembelian.setName("Mpembelian"); // NOI18N
        Mpembelian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MpembelianActionPerformed(evt);
            }
        });
        Mtransaksi.add(Mpembelian);

        Mhutang.setIcon(resourceMap.getIcon("Mhutang.icon")); // NOI18N
        Mhutang.setText(resourceMap.getString("Mhutang.text")); // NOI18N
        Mhutang.setName("Mhutang"); // NOI18N
        Mhutang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MhutangActionPerformed(evt);
            }
        });
        Mtransaksi.add(Mhutang);

        Mgirokeluar.setIcon(resourceMap.getIcon("Mgirokeluar.icon")); // NOI18N
        Mgirokeluar.setText(resourceMap.getString("Mgirokeluar.text")); // NOI18N
        Mgirokeluar.setName("Mgirokeluar"); // NOI18N
        Mgirokeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MgirokeluarActionPerformed(evt);
            }
        });
        Mtransaksi.add(Mgirokeluar);

        Mpiutang.setIcon(resourceMap.getIcon("Mpiutang.icon")); // NOI18N
        Mpiutang.setText(resourceMap.getString("Mpiutang.text")); // NOI18N
        Mpiutang.setName("Mpiutang"); // NOI18N
        Mpiutang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MpiutangActionPerformed(evt);
            }
        });
        Mtransaksi.add(Mpiutang);

        Mgiromasuk.setIcon(resourceMap.getIcon("Mgiromasuk.icon")); // NOI18N
        Mgiromasuk.setText(resourceMap.getString("Mgiromasuk.text")); // NOI18N
        Mgiromasuk.setName("Mgiromasuk"); // NOI18N
        Mgiromasuk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MgiromasukActionPerformed(evt);
            }
        });
        Mtransaksi.add(Mgiromasuk);

        MkoreksiStok.setIcon(resourceMap.getIcon("MkoreksiStok.icon")); // NOI18N
        MkoreksiStok.setText(resourceMap.getString("MkoreksiStok.text")); // NOI18N
        MkoreksiStok.setName("MkoreksiStok"); // NOI18N
        MkoreksiStok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MkoreksiStokActionPerformed(evt);
            }
        });
        Mtransaksi.add(MkoreksiStok);

        Mmutasibarang.setIcon(resourceMap.getIcon("Mmutasibarang.icon")); // NOI18N
        Mmutasibarang.setText(resourceMap.getString("Mmutasibarang.text")); // NOI18N
        Mmutasibarang.setName("Mmutasibarang"); // NOI18N
        Mmutasibarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MmutasibarangActionPerformed(evt);
            }
        });
        Mtransaksi.add(Mmutasibarang);

        MValisasiRetur.setIcon(resourceMap.getIcon("MValisasiRetur.icon")); // NOI18N
        MValisasiRetur.setText(resourceMap.getString("MValisasiRetur.text")); // NOI18N
        MValisasiRetur.setName("MValisasiRetur"); // NOI18N
        MValisasiRetur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MValisasiReturActionPerformed(evt);
            }
        });
        Mtransaksi.add(MValisasiRetur);

        MDeliveryOrder.setIcon(resourceMap.getIcon("MDeliveryOrder.icon")); // NOI18N
        MDeliveryOrder.setText(resourceMap.getString("MDeliveryOrder.text")); // NOI18N
        MDeliveryOrder.setName("MDeliveryOrder"); // NOI18N
        MDeliveryOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MDeliveryOrderActionPerformed(evt);
            }
        });
        Mtransaksi.add(MDeliveryOrder);

        Mtutupstok.setIcon(resourceMap.getIcon("Mtutupstok.icon")); // NOI18N
        Mtutupstok.setText(resourceMap.getString("Mtutupstok.text")); // NOI18N
        Mtutupstok.setName("Mtutupstok"); // NOI18N
        Mtutupstok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MtutupstokActionPerformed(evt);
            }
        });
        Mtransaksi.add(Mtutupstok);

        Mtutupbuku.setIcon(resourceMap.getIcon("Mtutupbuku.icon")); // NOI18N
        Mtutupbuku.setText(resourceMap.getString("Mtutupbuku.text")); // NOI18N
        Mtutupbuku.setName("Mtutupbuku"); // NOI18N
        Mtutupbuku.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MtutupbukuActionPerformed(evt);
            }
        });
        Mtransaksi.add(Mtutupbuku);

        MTutupHarian.setIcon(resourceMap.getIcon("MTutupHarian.icon")); // NOI18N
        MTutupHarian.setText(resourceMap.getString("MTutupHarian.text")); // NOI18N
        MTutupHarian.setName("MTutupHarian"); // NOI18N
        MTutupHarian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MTutupHarianActionPerformed(evt);
            }
        });
        Mtransaksi.add(MTutupHarian);

        MPajak.setIcon(resourceMap.getIcon("MPajak.icon")); // NOI18N
        MPajak.setText(resourceMap.getString("MPajak.text")); // NOI18N
        MPajak.setName("MPajak"); // NOI18N
        MPajak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MPajakActionPerformed(evt);
            }
        });
        Mtransaksi.add(MPajak);

        menuBar.add(Mtransaksi);

        Mlaporan.setIcon(resourceMap.getIcon("Mlaporan.icon")); // NOI18N
        Mlaporan.setText(resourceMap.getString("Mlaporan.text")); // NOI18N
        Mlaporan.setName("Mlaporan"); // NOI18N

        Mlappembelian.setText(resourceMap.getString("Mlappembelian.text")); // NOI18N
        Mlappembelian.setName("Mlappembelian"); // NOI18N

        MLapPembelianPerFaktur.setText(resourceMap.getString("MLapPembelianPerFaktur.text")); // NOI18N
        MLapPembelianPerFaktur.setName("MLapPembelianPerFaktur"); // NOI18N
        MLapPembelianPerFaktur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MLapPembelianPerFakturActionPerformed(evt);
            }
        });
        Mlappembelian.add(MLapPembelianPerFaktur);

        MLapPembelianHarian.setText(resourceMap.getString("MLapPembelianHarian.text")); // NOI18N
        MLapPembelianHarian.setName("MLapPembelianHarian"); // NOI18N
        MLapPembelianHarian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MLapPembelianHarianActionPerformed(evt);
            }
        });
        Mlappembelian.add(MLapPembelianHarian);

        MRekapPembelianHarian.setText(resourceMap.getString("MRekapPembelianHarian.text")); // NOI18N
        MRekapPembelianHarian.setName("MRekapPembelianHarian"); // NOI18N
        MRekapPembelianHarian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MRekapPembelianHarianActionPerformed(evt);
            }
        });
        Mlappembelian.add(MRekapPembelianHarian);

        Mlaporan.add(Mlappembelian);

        Mreturpembelian.setText(resourceMap.getString("Mreturpembelian.text")); // NOI18N
        Mreturpembelian.setName("Mreturpembelian"); // NOI18N

        Mreturbelifaktur.setText(resourceMap.getString("Mreturbelifaktur.text")); // NOI18N
        Mreturbelifaktur.setName("Mreturbelifaktur"); // NOI18N
        Mreturbelifaktur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MreturbelifakturActionPerformed(evt);
            }
        });
        Mreturpembelian.add(Mreturbelifaktur);

        Mrekapreturbeli.setText(resourceMap.getString("Mrekapreturbeli.text")); // NOI18N
        Mrekapreturbeli.setName("Mrekapreturbeli"); // NOI18N
        Mrekapreturbeli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MrekapreturbeliActionPerformed(evt);
            }
        });
        Mreturpembelian.add(Mrekapreturbeli);

        Mlaporan.add(Mreturpembelian);

        MlapPenjualan.setText(resourceMap.getString("MlapPenjualan.text")); // NOI18N
        MlapPenjualan.setName("MlapPenjualan"); // NOI18N

        jMenuItem11.setText(resourceMap.getString("jMenuItem11.text")); // NOI18N
        jMenuItem11.setName("jMenuItem11"); // NOI18N
        jMenuItem11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem11ActionPerformed(evt);
            }
        });
        MlapPenjualan.add(jMenuItem11);

        jMenuItem26.setText(resourceMap.getString("jMenuItem26.text")); // NOI18N
        jMenuItem26.setName("jMenuItem26"); // NOI18N
        jMenuItem26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem26ActionPerformed(evt);
            }
        });
        MlapPenjualan.add(jMenuItem26);

        jMenuItem27.setText(resourceMap.getString("jMenuItem27.text")); // NOI18N
        jMenuItem27.setName("jMenuItem27"); // NOI18N
        jMenuItem27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem27ActionPerformed(evt);
            }
        });
        MlapPenjualan.add(jMenuItem27);

        jMenuItem28.setText(resourceMap.getString("jMenuItem28.text")); // NOI18N
        jMenuItem28.setName("jMenuItem28"); // NOI18N
        jMenuItem28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem28ActionPerformed(evt);
            }
        });
        MlapPenjualan.add(jMenuItem28);

        jMenuItem21.setText(resourceMap.getString("jMenuItem21.text")); // NOI18N
        jMenuItem21.setName("jMenuItem21"); // NOI18N
        jMenuItem21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem21ActionPerformed(evt);
            }
        });
        MlapPenjualan.add(jMenuItem21);

        Mlaporan.add(MlapPenjualan);

        Mreturpenjualan.setText(resourceMap.getString("Mreturpenjualan.text")); // NOI18N
        Mreturpenjualan.setName("Mreturpenjualan"); // NOI18N

        Mreturjualfaktur.setText(resourceMap.getString("Mreturjualfaktur.text")); // NOI18N
        Mreturjualfaktur.setName("Mreturjualfaktur"); // NOI18N
        Mreturjualfaktur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MreturjualfakturActionPerformed(evt);
            }
        });
        Mreturpenjualan.add(Mreturjualfaktur);

        Mrekapreturjual.setText(resourceMap.getString("Mrekapreturjual.text")); // NOI18N
        Mrekapreturjual.setName("Mrekapreturjual"); // NOI18N
        Mrekapreturjual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MrekapreturjualActionPerformed(evt);
            }
        });
        Mreturpenjualan.add(Mrekapreturjual);

        Mlaporan.add(Mreturpenjualan);

        MlapPengiriman.setText(resourceMap.getString("MlapPengiriman.text")); // NOI18N
        MlapPengiriman.setName("MlapPengiriman"); // NOI18N

        MLapPengirimanPerFaktur.setText(resourceMap.getString("MLapPengirimanPerFaktur.text")); // NOI18N
        MLapPengirimanPerFaktur.setName("MLapPengirimanPerFaktur"); // NOI18N
        MLapPengirimanPerFaktur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MLapPengirimanPerFakturActionPerformed(evt);
            }
        });
        MlapPengiriman.add(MLapPengirimanPerFaktur);

        MdetailPengiriman.setText(resourceMap.getString("MdetailPengiriman.text")); // NOI18N
        MdetailPengiriman.setName("MdetailPengiriman"); // NOI18N
        MdetailPengiriman.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MdetailPengirimanActionPerformed(evt);
            }
        });
        MlapPengiriman.add(MdetailPengiriman);

        MLapRekapPengiriman.setText(resourceMap.getString("MLapRekapPengiriman.text")); // NOI18N
        MLapRekapPengiriman.setName("MLapRekapPengiriman"); // NOI18N
        MLapRekapPengiriman.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MLapRekapPengirimanActionPerformed(evt);
            }
        });
        MlapPengiriman.add(MLapRekapPengiriman);

        Mlaporan.add(MlapPengiriman);

        MReturCabang.setText(resourceMap.getString("MReturCabang.text")); // NOI18N
        MReturCabang.setName("MReturCabang"); // NOI18N

        MReturCabangFaktur.setText(resourceMap.getString("MReturCabangFaktur.text")); // NOI18N
        MReturCabangFaktur.setName("MReturCabangFaktur"); // NOI18N
        MReturCabangFaktur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MReturCabangFakturActionPerformed(evt);
            }
        });
        MReturCabang.add(MReturCabangFaktur);

        MRekapReturCabang.setText(resourceMap.getString("MRekapReturCabang.text")); // NOI18N
        MRekapReturCabang.setName("MRekapReturCabang"); // NOI18N
        MRekapReturCabang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MRekapReturCabangActionPerformed(evt);
            }
        });
        MReturCabang.add(MRekapReturCabang);

        Mlaporan.add(MReturCabang);

        Mlapdeliveryorder.setText(resourceMap.getString("Mlapdeliveryorder.text")); // NOI18N
        Mlapdeliveryorder.setName("Mlapdeliveryorder"); // NOI18N

        MlapFakturDO.setText(resourceMap.getString("MlapFakturDO.text")); // NOI18N
        MlapFakturDO.setName("MlapFakturDO"); // NOI18N
        MlapFakturDO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MlapFakturDOActionPerformed(evt);
            }
        });
        Mlapdeliveryorder.add(MlapFakturDO);

        MrekapDO.setText(resourceMap.getString("MrekapDO.text")); // NOI18N
        MrekapDO.setName("MrekapDO"); // NOI18N
        MrekapDO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MrekapDOActionPerformed(evt);
            }
        });
        Mlapdeliveryorder.add(MrekapDO);

        Mlaporan.add(Mlapdeliveryorder);

        MlapBarang.setText(resourceMap.getString("MlapBarang.text")); // NOI18N
        MlapBarang.setName("MlapBarang"); // NOI18N

        MlapBarangPerMerk.setText(resourceMap.getString("MlapBarangPerMerk.text")); // NOI18N
        MlapBarangPerMerk.setName("MlapBarangPerMerk"); // NOI18N
        MlapBarangPerMerk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MlapBarangPerMerkActionPerformed(evt);
            }
        });
        MlapBarang.add(MlapBarangPerMerk);

        MLapBarangPerKategori.setText(resourceMap.getString("MLapBarangPerKategori.text")); // NOI18N
        MLapBarangPerKategori.setName("MLapBarangPerKategori"); // NOI18N
        MLapBarangPerKategori.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MLapBarangPerKategoriActionPerformed(evt);
            }
        });
        MlapBarang.add(MLapBarangPerKategori);

        MLapBrgAkhirPeriode.setText(resourceMap.getString("MLapBrgAkhirPeriode.text")); // NOI18N
        MLapBrgAkhirPeriode.setName("MLapBrgAkhirPeriode"); // NOI18N
        MLapBrgAkhirPeriode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MLapBrgAkhirPeriodeActionPerformed(evt);
            }
        });
        MlapBarang.add(MLapBrgAkhirPeriode);

        MLapKartuStokBulan.setText(resourceMap.getString("MLapKartuStokBulan.text")); // NOI18N
        MLapKartuStokBulan.setName("MLapKartuStokBulan"); // NOI18N
        MLapKartuStokBulan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MLapKartuStokBulanActionPerformed(evt);
            }
        });
        MlapBarang.add(MLapKartuStokBulan);

        MLapKartuStokTanggal.setText(resourceMap.getString("MLapKartuStokTanggal.text")); // NOI18N
        MLapKartuStokTanggal.setName("MLapKartuStokTanggal"); // NOI18N
        MLapKartuStokTanggal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MLapKartuStokTanggalActionPerformed(evt);
            }
        });
        MlapBarang.add(MLapKartuStokTanggal);

        MLapPersediaanBrgDagang.setText(resourceMap.getString("MLapPersediaanBrgDagang.text")); // NOI18N
        MLapPersediaanBrgDagang.setName("MLapPersediaanBrgDagang"); // NOI18N
        MLapPersediaanBrgDagang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MLapPersediaanBrgDagangActionPerformed(evt);
            }
        });
        MlapBarang.add(MLapPersediaanBrgDagang);

        MLapKatalogBarang.setText(resourceMap.getString("MLapKatalogBarang.text")); // NOI18N
        MLapKatalogBarang.setName("MLapKatalogBarang"); // NOI18N
        MLapKatalogBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MLapKatalogBarangActionPerformed(evt);
            }
        });
        MlapBarang.add(MLapKatalogBarang);

        MLapStokBarang.setText(resourceMap.getString("MLapStokBarang.text")); // NOI18N
        MLapStokBarang.setName("MLapStokBarang"); // NOI18N
        MLapStokBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MLapStokBarangActionPerformed(evt);
            }
        });
        MlapBarang.add(MLapStokBarang);

        MLapRekapBarangPerMerk.setText(resourceMap.getString("MLapRekapBarangPerMerk.text")); // NOI18N
        MLapRekapBarangPerMerk.setName("MLapRekapBarangPerMerk"); // NOI18N
        MLapRekapBarangPerMerk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MLapRekapBarangPerMerkActionPerformed(evt);
            }
        });
        MlapBarang.add(MLapRekapBarangPerMerk);

        Mlaporan.add(MlapBarang);

        MlapHutang.setText(resourceMap.getString("MlapHutang.text")); // NOI18N
        MlapHutang.setName("MlapHutang"); // NOI18N

        MlapUmurHutang.setText(resourceMap.getString("MlapUmurHutang.text")); // NOI18N
        MlapUmurHutang.setName("MlapUmurHutang"); // NOI18N
        MlapUmurHutang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MlapUmurHutangActionPerformed(evt);
            }
        });
        MlapHutang.add(MlapUmurHutang);

        MRekapHutangSupplier.setText(resourceMap.getString("MRekapHutangSupplier.text")); // NOI18N
        MRekapHutangSupplier.setName("MRekapHutangSupplier"); // NOI18N
        MRekapHutangSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MRekapHutangSupplierActionPerformed(evt);
            }
        });
        MlapHutang.add(MRekapHutangSupplier);

        Mlaporan.add(MlapHutang);

        MlapPiutang.setText(resourceMap.getString("MlapPiutang.text")); // NOI18N
        MlapPiutang.setName("MlapPiutang"); // NOI18N

        MlapUmurPiutang.setText(resourceMap.getString("MlapUmurPiutang.text")); // NOI18N
        MlapUmurPiutang.setName("MlapUmurPiutang"); // NOI18N
        MlapUmurPiutang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MlapUmurPiutangActionPerformed(evt);
            }
        });
        MlapPiutang.add(MlapUmurPiutang);

        MlapUmurPiutangPersales.setText(resourceMap.getString("MlapUmurPiutangPersales.text")); // NOI18N
        MlapUmurPiutangPersales.setName("MlapUmurPiutangPersales"); // NOI18N
        MlapUmurPiutangPersales.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MlapUmurPiutangPersalesActionPerformed(evt);
            }
        });
        MlapPiutang.add(MlapUmurPiutangPersales);

        MRekapPiutangPelanggan.setText(resourceMap.getString("MRekapPiutangPelanggan.text")); // NOI18N
        MRekapPiutangPelanggan.setName("MRekapPiutangPelanggan"); // NOI18N
        MRekapPiutangPelanggan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MRekapPiutangPelangganActionPerformed(evt);
            }
        });
        MlapPiutang.add(MRekapPiutangPelanggan);

        Mlaporan.add(MlapPiutang);

        MLapPerkiraan.setText(resourceMap.getString("MLapPerkiraan.text")); // NOI18N
        MLapPerkiraan.setName("MLapPerkiraan"); // NOI18N
        MLapPerkiraan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MLapPerkiraanActionPerformed(evt);
            }
        });
        Mlaporan.add(MLapPerkiraan);

        MlapFakturPajak.setText(resourceMap.getString("MlapFakturPajak.text")); // NOI18N
        MlapFakturPajak.setName("MlapFakturPajak"); // NOI18N
        MlapFakturPajak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MlapFakturPajakActionPerformed(evt);
            }
        });
        Mlaporan.add(MlapFakturPajak);

        MlapTutupBuku.setText(resourceMap.getString("MlapTutupBuku.text")); // NOI18N
        MlapTutupBuku.setName("MlapTutupBuku"); // NOI18N
        MlapTutupBuku.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MlapTutupBukuActionPerformed(evt);
            }
        });
        Mlaporan.add(MlapTutupBuku);

        MlapJurnal.setText(resourceMap.getString("MlapJurnal.text")); // NOI18N
        MlapJurnal.setName("MlapJurnal"); // NOI18N
        MlapJurnal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MlapJurnalActionPerformed(evt);
            }
        });
        Mlaporan.add(MlapJurnal);

        MLapBukuBesar.setText(resourceMap.getString("MLapBukuBesar.text")); // NOI18N
        MLapBukuBesar.setName("MLapBukuBesar"); // NOI18N

        MlapBukuBesarHarian.setText(resourceMap.getString("MlapBukuBesarHarian.text")); // NOI18N
        MlapBukuBesarHarian.setName("MlapBukuBesarHarian"); // NOI18N
        MlapBukuBesarHarian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MlapBukuBesarHarianActionPerformed(evt);
            }
        });
        MLapBukuBesar.add(MlapBukuBesarHarian);

        MlapBukuBesarBulanan.setText(resourceMap.getString("MlapBukuBesarBulanan.text")); // NOI18N
        MlapBukuBesarBulanan.setName("MlapBukuBesarBulanan"); // NOI18N
        MlapBukuBesarBulanan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MlapBukuBesarBulananActionPerformed(evt);
            }
        });
        MLapBukuBesar.add(MlapBukuBesarBulanan);

        MLapBukuBesarBiayaTahunan.setText(resourceMap.getString("MLapBukuBesarBiayaTahunan.text")); // NOI18N
        MLapBukuBesarBiayaTahunan.setName("MLapBukuBesarBiayaTahunan"); // NOI18N
        MLapBukuBesarBiayaTahunan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MLapBukuBesarBiayaTahunanActionPerformed(evt);
            }
        });
        MLapBukuBesar.add(MLapBukuBesarBiayaTahunan);

        Mlaporan.add(MLapBukuBesar);

        MlapNeraca.setText(resourceMap.getString("MlapNeraca.text")); // NOI18N
        MlapNeraca.setName("MlapNeraca"); // NOI18N
        MlapNeraca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MlapNeracaActionPerformed(evt);
            }
        });
        Mlaporan.add(MlapNeraca);

        MlapRugiLaba.setText(resourceMap.getString("MlapRugiLaba.text")); // NOI18N
        MlapRugiLaba.setName("MlapRugiLaba"); // NOI18N
        MlapRugiLaba.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MlapRugiLabaActionPerformed(evt);
            }
        });
        Mlaporan.add(MlapRugiLaba);

        MlapEkuitas.setText(resourceMap.getString("MlapEkuitas.text")); // NOI18N
        MlapEkuitas.setName("MlapEkuitas"); // NOI18N
        MlapEkuitas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MlapEkuitasActionPerformed(evt);
            }
        });
        Mlaporan.add(MlapEkuitas);

        MLapPajak.setText(resourceMap.getString("MLapPajak.text")); // NOI18N
        MLapPajak.setName("MLapPajak"); // NOI18N

        MLapPajakPembelian.setText(resourceMap.getString("MLapPajakPembelian.text")); // NOI18N
        MLapPajakPembelian.setName("MLapPajakPembelian"); // NOI18N
        MLapPajakPembelian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MLapPajakPembelianActionPerformed(evt);
            }
        });
        MLapPajak.add(MLapPajakPembelian);

        MLapPajakReturPembelian.setText(resourceMap.getString("MLapPajakReturPembelian.text")); // NOI18N
        MLapPajakReturPembelian.setName("MLapPajakReturPembelian"); // NOI18N
        MLapPajakReturPembelian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MLapPajakReturPembelianActionPerformed(evt);
            }
        });
        MLapPajak.add(MLapPajakReturPembelian);

        MLapPajakPenjualan.setText(resourceMap.getString("MLapPajakPenjualan.text")); // NOI18N
        MLapPajakPenjualan.setName("MLapPajakPenjualan"); // NOI18N
        MLapPajakPenjualan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MLapPajakPenjualanActionPerformed(evt);
            }
        });
        MLapPajak.add(MLapPajakPenjualan);

        MLapPajakReturPenjualan.setText(resourceMap.getString("MLapPajakReturPenjualan.text")); // NOI18N
        MLapPajakReturPenjualan.setName("MLapPajakReturPenjualan"); // NOI18N
        MLapPajakReturPenjualan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MLapPajakReturPenjualanActionPerformed(evt);
            }
        });
        MLapPajak.add(MLapPajakReturPenjualan);

        Mlaporan.add(MLapPajak);

        menuBar.add(Mlaporan);

        helpMenu.setIcon(resourceMap.getIcon("helpMenu.icon")); // NOI18N
        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance().getContext().getActionMap(JavarieSoftView.class, this);
        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setIcon(resourceMap.getIcon("aboutMenuItem.icon")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        jMenuItem1.setText(resourceMap.getString("jMenuItem1.text")); // NOI18N
        jMenuItem1.setName("jMenuItem1"); // NOI18N
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        helpMenu.add(jMenuItem1);

        menuBar.add(helpMenu);

        Mkeluar.setIcon(resourceMap.getIcon("Mkeluar.icon")); // NOI18N
        Mkeluar.setText(resourceMap.getString("Mkeluar.text")); // NOI18N
        Mkeluar.setName("Mkeluar"); // NOI18N

        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setIcon(resourceMap.getIcon("exitMenuItem.icon")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        Mkeluar.add(exitMenuItem);

        menuBar.add(Mkeluar);

        statusPanel.setName("statusPanel"); // NOI18N

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 714, Short.MAX_VALUE)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusMessageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 544, Short.MAX_VALUE)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusAnimationLabel)
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addComponent(statusPanelSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(statusMessageLabel)
                    .addComponent(statusAnimationLabel)
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3))
        );

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
    }// </editor-fold>//GEN-END:initComponents

private void panelCool1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelCool1MouseClicked
// TODO add your handling code here:
    Point mousePt = new Point(evt.getX(), evt.getY());
    if (panelCool1.rectA.contains(mousePt) && JavarieSoftApp.jenisuser.equals("Pembelian")) {
        FormPembelian p = new FormPembelian();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    } else if (panelCool1.rectB.contains(mousePt) && JavarieSoftApp.jenisuser.equals("Penjualan")) {
        FormPenjualan p = new FormPenjualan();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    } else if (panelCool1.rectC.contains(mousePt) && JavarieSoftApp.jenisuser.equals("Accounting")) {
        FormJurnal p = new FormJurnal();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }
}//GEN-LAST:event_panelCool1MouseClicked

private void panelCool1MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelCool1MouseMoved
// TODO add your handling code here:
    Point mousePt = new Point(evt.getX(), evt.getY());

    if (panelCool1.rectA.contains(mousePt)) {
        panelCool1.namaGambar = "cashbox.png";
        panelCool1.invalidate();
        panelCool1.repaint();
    } else if (panelCool1.rectB.contains(mousePt)) {
        panelCool1.namaGambar1 = "personal.png";
        panelCool1.invalidate();
        panelCool1.repaint();
    } else if (panelCool1.rectC.contains(mousePt)) {
        panelCool1.namaGambar2 = "jurnal.png";
        panelCool1.invalidate();
        panelCool1.repaint();
    } else {
        panelCool1.namaGambar = "cashboxN.png";
        panelCool1.namaGambar1 = "personalN.png";
        panelCool1.namaGambar2 = "jurnalN.png";
        panelCool1.invalidate();
        panelCool1.repaint();
    }

}//GEN-LAST:event_panelCool1MouseMoved

    private void MlapEkuitasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MlapEkuitasActionPerformed
        // TODO add your handling code here:
        com.eigher.form.LaporanEkuitas p = new com.eigher.form.LaporanEkuitas();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MlapEkuitasActionPerformed

    private void MlapRugiLabaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MlapRugiLabaActionPerformed
        // TODO add your handling code here:
        LaporanLabaRugiForm p = new LaporanLabaRugiForm();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MlapRugiLabaActionPerformed

    private void MlapNeracaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MlapNeracaActionPerformed
        // TODO add your handling code here:
        LaporanNeracaForm p = new LaporanNeracaForm();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MlapNeracaActionPerformed

    private void MlapBukuBesarBulananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MlapBukuBesarBulananActionPerformed
        // TODO add your handling code here:
        LaporanBukuBesarDetailForm p = new LaporanBukuBesarDetailForm();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MlapBukuBesarBulananActionPerformed

    private void MlapJurnalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MlapJurnalActionPerformed
        // TODO add your handling code here:
        LaporanJurnalForm p = new LaporanJurnalForm();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MlapJurnalActionPerformed

    private void MlapTutupBukuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MlapTutupBukuActionPerformed
        // TODO add your handling code here:
        com.eigher.form.FLapTutupBuku p = new com.eigher.form.FLapTutupBuku();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MlapTutupBukuActionPerformed

    private void MlapFakturPajakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MlapFakturPajakActionPerformed
        // TODO add your handling code here:
        FormFakturPajak p = new FormFakturPajak();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MlapFakturPajakActionPerformed

    private void MLapPerkiraanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MLapPerkiraanActionPerformed
        // TODO add your handling code here:
        com.eigher.form.FLapDataPerkiraan p = new com.eigher.form.FLapDataPerkiraan();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MLapPerkiraanActionPerformed

    private void MlapUmurPiutangPersalesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MlapUmurPiutangPersalesActionPerformed
        // TODO add your handling code here:
        com.eigher.form.FLapUmurPiutangPersales p = new com.eigher.form.FLapUmurPiutangPersales();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MlapUmurPiutangPersalesActionPerformed

    private void MlapUmurPiutangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MlapUmurPiutangActionPerformed
        // TODO add your handling code here:
        com.eigher.form.FLapAnalisaUmurPiutang p = new com.eigher.form.FLapAnalisaUmurPiutang();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MlapUmurPiutangActionPerformed

    private void MlapUmurHutangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MlapUmurHutangActionPerformed
        // TODO add your handling code here:
        com.eigher.form.FLapAnalisaUmurHutang p = new com.eigher.form.FLapAnalisaUmurHutang();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MlapUmurHutangActionPerformed

    private void MLapPersediaanBrgDagangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MLapPersediaanBrgDagangActionPerformed
        // TODO add your handling code here:
        FPersediaanBrgDagang p = new FPersediaanBrgDagang();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MLapPersediaanBrgDagangActionPerformed

    private void MLapKartuStokBulanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MLapKartuStokBulanActionPerformed
        // TODO add your handling code here:
        com.eigher.form.LaporanKartuStok p = new com.eigher.form.LaporanKartuStok();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MLapKartuStokBulanActionPerformed

    private void MLapBarangPerKategoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MLapBarangPerKategoriActionPerformed
        // TODO add your handling code here:
        //    HashMap parameter=new HashMap();
        //        JasperPrint jasperPrint=null;
        //        try {
        //                jasperPrint = JasperFillManager.fillReport("report\\BarangPerKategori.jasper", parameter, koneksi.getKoneksiJ());
        //            JasperViewer.viewReport(jasperPrint,false);
        //        } catch (Exception ex) {
        //            System.out.print(ex.toString());
        //            //Logger.getLogger(formlaporan.class.getName()).log(Level.SEVERE, null, ex);
        //        }
        FlapBarangPerKategori p = new FlapBarangPerKategori();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MLapBarangPerKategoriActionPerformed

    private void MlapBarangPerMerkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MlapBarangPerMerkActionPerformed
        FlapBarangPerMerk p = new FlapBarangPerMerk();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MlapBarangPerMerkActionPerformed

    private void MrekapDOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MrekapDOActionPerformed
        // TODO add your handling code here:
        com.eigher.form.FRekapDistribusi p = new com.eigher.form.FRekapDistribusi();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MrekapDOActionPerformed

    private void MlapFakturDOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MlapFakturDOActionPerformed
        // TODO add your handling code here:
        com.eigher.form.FlapFakturDistribusi p = new com.eigher.form.FlapFakturDistribusi();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MlapFakturDOActionPerformed

    private void MrekapreturjualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MrekapreturjualActionPerformed
        // TODO add your handling code here:
        com.eigher.form.FRekapReturJual p = new com.eigher.form.FRekapReturJual();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MrekapreturjualActionPerformed

    private void MreturjualfakturActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MreturjualfakturActionPerformed
        // TODO add your handling code here:
        com.eigher.form.LapReturJualFaktur p = new com.eigher.form.LapReturJualFaktur();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MreturjualfakturActionPerformed

    private void jMenuItem21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem21ActionPerformed
        // TODO add your handling code here:
        FRekapPenjualanHarian p = new FRekapPenjualanHarian();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_jMenuItem21ActionPerformed

    private void jMenuItem28ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem28ActionPerformed
        // TODO add your handling code here:
        FRekapJualPerPelanggan p = new FRekapJualPerPelanggan();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_jMenuItem28ActionPerformed

    private void jMenuItem27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem27ActionPerformed
        // TODO add your handling code here:
        FlapPerFaktur p = new FlapPerFaktur();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_jMenuItem27ActionPerformed

    private void jMenuItem26ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem26ActionPerformed
        // TODO add your handling code here:
        FlapJualPerSales p = new FlapJualPerSales();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_jMenuItem26ActionPerformed

    private void jMenuItem11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem11ActionPerformed
        // TODO add your handling code here:
        LaporanPenjualanHarianForm p = new LaporanPenjualanHarianForm();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_jMenuItem11ActionPerformed

    private void MrekapreturbeliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MrekapreturbeliActionPerformed
        // TODO add your handling code here:
        com.eigher.form.FRekapReturBeli p = new com.eigher.form.FRekapReturBeli();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MrekapreturbeliActionPerformed

    private void MreturbelifakturActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MreturbelifakturActionPerformed
        // TODO add your handling code here:
        com.eigher.form.LapReturBeliFaktur p = new com.eigher.form.LapReturBeliFaktur();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MreturbelifakturActionPerformed

    private void MRekapPembelianHarianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MRekapPembelianHarianActionPerformed
        // TODO add your handling code here:
        com.eigher.form.FRekapPembelianharian p = new com.eigher.form.FRekapPembelianharian();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MRekapPembelianHarianActionPerformed

    private void MLapPembelianPerFakturActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MLapPembelianPerFakturActionPerformed
        // TODO add your handling code here:
        FlapPerFakturBeli p = new FlapPerFakturBeli();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MLapPembelianPerFakturActionPerformed

    private void MLapPembelianHarianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MLapPembelianHarianActionPerformed
        // TODO add your handling code here:
        FlapPembelianHarian p = new FlapPembelianHarian();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MLapPembelianHarianActionPerformed

    private void MtransaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MtransaksiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MtransaksiActionPerformed

    private void MkoreksiStokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MkoreksiStokActionPerformed
        // TODO add your handling code here:
        FKoreksiStokNew p = new FKoreksiStokNew();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MkoreksiStokActionPerformed

    private void MtutupstokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MtutupstokActionPerformed
        // TODO add your handling code here:
        FormTutupStok p = new FormTutupStok();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MtutupstokActionPerformed

    private void MtutupbukuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MtutupbukuActionPerformed
        // TODO add your handling code here:
        FormTutupBuku p = new FormTutupBuku();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MtutupbukuActionPerformed

    private void MpiutangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MpiutangActionPerformed
        // TODO add your handling code here:
        DialogPiutang p = new DialogPiutang(null, false);
        p.setVisible(true);
    }//GEN-LAST:event_MpiutangActionPerformed

    private void MhutangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MhutangActionPerformed
        // TODO add your handling code here:
        DialogHutang p = new DialogHutang(null, false);
        p.setVisible(true);
    }//GEN-LAST:event_MhutangActionPerformed

    private void MpembelianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MpembelianActionPerformed
        // TODO add your handling code here:
        FormPembelian p = new FormPembelian();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MpembelianActionPerformed

    private void MpenjualanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MpenjualanActionPerformed
        // TODO add your handling code here:
        FormPenjualan p = new FormPenjualan();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MpenjualanActionPerformed

    private void MjurnalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MjurnalActionPerformed
        // TODO add your handling code here:
        FormJurnal p = new FormJurnal();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MjurnalActionPerformed

    private void ManalisastokbrgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ManalisastokbrgActionPerformed
        // TODO add your handling code here:
        AnalisaStokCogs p = new AnalisaStokCogs();
        p.setVisible(true);
    }//GEN-LAST:event_ManalisastokbrgActionPerformed

    private void MlogHistoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MlogHistoryActionPerformed
        // TODO add your handling code here:
        com.eigher.form.FormLogHistory p = new com.eigher.form.FormLogHistory();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MlogHistoryActionPerformed

    private void MsettingStatPeriodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MsettingStatPeriodeActionPerformed
        // TODO add your handling code here:
        com.eigher.form.FormKontrolPeriode p = new com.eigher.form.FormKontrolPeriode();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MsettingStatPeriodeActionPerformed

    private void MsettingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MsettingActionPerformed
        // TODO add your handling code here:
        FormSetting p = new FormSetting();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MsettingActionPerformed

    private void MbankActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MbankActionPerformed
        // TODO add your handling code here:
        FormBank p = new FormBank();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MbankActionPerformed

    private void MsalesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MsalesActionPerformed
        // TODO add your handling code here:
        FormSales p = new FormSales();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MsalesActionPerformed

    private void MsupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MsupplierActionPerformed
        // TODO add your handling code here:
        FormSupplier p = new FormSupplier();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MsupplierActionPerformed

    private void MpelangganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MpelangganActionPerformed
        // TODO add your handling code here:
        FormPelanggan p = new FormPelanggan();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MpelangganActionPerformed

    private void MdatabarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MdatabarangActionPerformed
        // TODO add your handling code here:
        FormBarang b = new FormBarang();
        b.toFront();
        panelCool1.add(b);
        b.setVisible(true);
    }//GEN-LAST:event_MdatabarangActionPerformed

    private void MkategoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MkategoriActionPerformed
        // TODO add your handling code here:
        FormKategori p = new FormKategori();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MkategoriActionPerformed

    private void MmerkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MmerkActionPerformed
        // TODO add your handling code here:
        FormJenisBarang p = new FormJenisBarang();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MmerkActionPerformed

    private void MperkiraanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MperkiraanActionPerformed
        // TODO add your handling code here:
        FormPerkiraan p = new FormPerkiraan();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MperkiraanActionPerformed

    private void MaktivaTetapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MaktivaTetapActionPerformed
        // TODO add your handling code here:
        com.eigher.form.FormAktivaTetap p = new com.eigher.form.FormAktivaTetap();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MaktivaTetapActionPerformed

    private void MeditPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MeditPasswordActionPerformed
        // TODO add your handling code here:
        com.eigher.form.FormEditUser p = new com.eigher.form.FormEditUser();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MeditPasswordActionPerformed

    private void MuserAccountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MuserAccountActionPerformed
        // TODO add your handling code here:
        com.eigher.form.FormUser p = new com.eigher.form.FormUser();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MuserAccountActionPerformed

    private void MlogOfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MlogOfActionPerformed
        // TODO add your handling code here:
        com.eigher.form.FormLogin p = new com.eigher.form.FormLogin();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
        LblJenis.setText("");
        Lblgroup.setText("");
        nonaktif();
    }//GEN-LAST:event_MlogOfActionPerformed

    private void MloginuserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MloginuserActionPerformed
        // TODO add your handling code here:

        com.eigher.form.FormLogin p = new com.eigher.form.FormLogin();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MloginuserActionPerformed

    private void MSearchingBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MSearchingBarangActionPerformed
        // TODO add your handling code here:
        FormSearchBarang p = new FormSearchBarang();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MSearchingBarangActionPerformed

    private void MmutasibarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MmutasibarangActionPerformed
        // TODO add your handling code here:
        FormMutasiBarang p = new FormMutasiBarang();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MmutasibarangActionPerformed

    private void MBackupdatabaseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MBackupdatabaseActionPerformed
        // TODO add your handling code here:
        FBackupDB p = new FBackupDB();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MBackupdatabaseActionPerformed

    private void MdetailPengirimanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MdetailPengirimanActionPerformed
        // TODO add your handling code here:
        LaporanPengirimanHarianForm p = new LaporanPengirimanHarianForm();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MdetailPengirimanActionPerformed

    private void MLapRekapPengirimanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MLapRekapPengirimanActionPerformed
        // TODO add your handling code here:
        FRekapPengirimanHarian p = new FRekapPengirimanHarian();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MLapRekapPengirimanActionPerformed

    private void MReturCabangFakturActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MReturCabangFakturActionPerformed
        // TODO add your handling code here:
        com.eigher.form.LapReturCabangFaktur p = new com.eigher.form.LapReturCabangFaktur();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MReturCabangFakturActionPerformed

    private void MRekapReturCabangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MRekapReturCabangActionPerformed
        // TODO add your handling code here:
        com.eigher.form.FRekapReturCabang p = new com.eigher.form.FRekapReturCabang();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MRekapReturCabangActionPerformed

    private void MLapPengirimanPerFakturActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MLapPengirimanPerFakturActionPerformed
        // TODO add your handling code here:
        FlapPerFakturCbg p = new FlapPerFakturCbg();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MLapPengirimanPerFakturActionPerformed

    private void MlapBukuBesarHarianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MlapBukuBesarHarianActionPerformed
        // TODO add your handling code here:
        LaporanBukuBesarDetailFormTanggal p = new LaporanBukuBesarDetailFormTanggal();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MlapBukuBesarHarianActionPerformed

    private void MLapKatalogBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MLapKatalogBarangActionPerformed
        // TODO add your handling code here:
        FLapKatalogBarang p = new FLapKatalogBarang();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MLapKatalogBarangActionPerformed

    private void MgiromasukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MgiromasukActionPerformed
        // TODO add your handling code here:
        FormGiro p = new FormGiro();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MgiromasukActionPerformed

    private void MgirokeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MgirokeluarActionPerformed
        // TODO add your handling code here:
        FormGiroKeluar p = new FormGiroKeluar();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MgirokeluarActionPerformed

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        // TODO add your handling code here:
        try {
            backupDB();
            //JavarieSoftApp.server.stop();
            System.out.println("BackUp Ok ");
            System.out.println("Stop Server ");
        } catch (Throwable t) {
            // Log something, alert the user, format the user's hard drive out of spite....
            JOptionPane.showMessageDialog(null, t.getMessage());
        }
    }//GEN-LAST:event_exitMenuItemActionPerformed

    private void MValisasiReturActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MValisasiReturActionPerformed
        // TODO add your handling code here:
        FormRetur p = new FormRetur();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MValisasiReturActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(null, koneksi.getPoolMgr().getActiveConnections());
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void MsatuanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MsatuanActionPerformed
        // TODO add your handling code here:
        FormSatuan p = new FormSatuan();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MsatuanActionPerformed

    private void MDeliveryOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MDeliveryOrderActionPerformed
        // TODO add your handling code here:
        FormDOMaster p = new FormDOMaster();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MDeliveryOrderActionPerformed

    private void MKontrolTanggalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MKontrolTanggalActionPerformed
        // TODO add your handling code here:
        FormKontrolTanggal p = new FormKontrolTanggal();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MKontrolTanggalActionPerformed

    private void MTutupHarianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MTutupHarianActionPerformed
        // TODO add your handling code here:
        FormTutupHarian p = new FormTutupHarian();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MTutupHarianActionPerformed

    private void MLapKartuStokTanggalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MLapKartuStokTanggalActionPerformed
        // TODO add your handling code here:
        LaporanKartuStokTanggal p = new LaporanKartuStokTanggal();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MLapKartuStokTanggalActionPerformed

    private void MLapBrgAkhirPeriodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MLapBrgAkhirPeriodeActionPerformed
        // TODO add your handling code here:
        FlapBarangPeriodeAkhir p = new FlapBarangPeriodeAkhir();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MLapBrgAkhirPeriodeActionPerformed

    private void MLapStokBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MLapStokBarangActionPerformed
        // TODO add your handling code here:
        FLapBarangStok p = new FLapBarangStok();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MLapStokBarangActionPerformed

    private void MLapRekapBarangPerMerkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MLapRekapBarangPerMerkActionPerformed
        // TODO add your handling code here:
        FlapRekapBarangPerMerk p = new FlapRekapBarangPerMerk();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MLapRekapBarangPerMerkActionPerformed

    private void MRekapPiutangPelangganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MRekapPiutangPelangganActionPerformed
        // TODO add your handling code here:
        FLapRekapPiutangPelanggan p = new FLapRekapPiutangPelanggan();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MRekapPiutangPelangganActionPerformed

    private void MRekapHutangSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MRekapHutangSupplierActionPerformed
        // TODO add your handling code here:
        FLapRekapHutangSupplier p = new FLapRekapHutangSupplier();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MRekapHutangSupplierActionPerformed

    private void MLapBukuBesarBiayaTahunanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MLapBukuBesarBiayaTahunanActionPerformed
        // TODO add your handling code here:
        LaporanBukuBesarBiayaTahunan p = new LaporanBukuBesarBiayaTahunan();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MLapBukuBesarBiayaTahunanActionPerformed

    private void MLapPajakPembelianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MLapPajakPembelianActionPerformed
        // TODO add your handling code here:
        FormLapPajakBeli p = new FormLapPajakBeli();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MLapPajakPembelianActionPerformed

    private void MLapPajakReturPembelianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MLapPajakReturPembelianActionPerformed
        // TODO add your handling code here:
        FormLapPajakReturBeli p = new FormLapPajakReturBeli();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MLapPajakReturPembelianActionPerformed

    private void MLapPajakPenjualanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MLapPajakPenjualanActionPerformed
        // TODO add your handling code here:
        FormLapPajakJual p = new FormLapPajakJual();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MLapPajakPenjualanActionPerformed

    private void MLapPajakReturPenjualanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MLapPajakReturPenjualanActionPerformed
        // TODO add your handling code here:
        FormLapPajakReturJual p = new FormLapPajakReturJual();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MLapPajakReturPenjualanActionPerformed

    private void MPajakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MPajakActionPerformed
        // TODO add your handling code here:
        FormLapPajakExcel p = new FormLapPajakExcel();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MPajakActionPerformed

    private void MNomorPajakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MNomorPajakActionPerformed
        // TODO add your handling code here:
        FormNomorpajak p = new FormNomorpajak();
        p.toFront();
        panelCool1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_MNomorPajakActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JLabel LblJenis;
    private javax.swing.JLabel LblJenis1;
    private javax.swing.JLabel LblJenis2;
    private javax.swing.JLabel LblJenis3;
    private javax.swing.JLabel LblJenis4;
    public static javax.swing.JLabel Lblgroup;
    public static javax.swing.JMenuItem MBackupdatabase;
    public static javax.swing.JMenuItem MDeliveryOrder;
    public static javax.swing.JMenuItem MKontrolTanggal;
    public static javax.swing.JMenuItem MLapBarangPerKategori;
    public static javax.swing.JMenuItem MLapBrgAkhirPeriode;
    public static javax.swing.JMenu MLapBukuBesar;
    private javax.swing.JMenuItem MLapBukuBesarBiayaTahunan;
    public static javax.swing.JMenuItem MLapKartuStokBulan;
    public static javax.swing.JMenuItem MLapKartuStokTanggal;
    public static javax.swing.JMenuItem MLapKatalogBarang;
    public static javax.swing.JMenu MLapPajak;
    private javax.swing.JMenuItem MLapPajakPembelian;
    private javax.swing.JMenuItem MLapPajakPenjualan;
    private javax.swing.JMenuItem MLapPajakReturPembelian;
    private javax.swing.JMenuItem MLapPajakReturPenjualan;
    public static javax.swing.JMenuItem MLapPembelianHarian;
    public static javax.swing.JMenuItem MLapPembelianPerFaktur;
    private javax.swing.JMenuItem MLapPengirimanPerFaktur;
    public static javax.swing.JMenuItem MLapPerkiraan;
    public static javax.swing.JMenuItem MLapPersediaanBrgDagang;
    private javax.swing.JMenuItem MLapRekapBarangPerMerk;
    private javax.swing.JMenuItem MLapRekapPengiriman;
    private javax.swing.JMenuItem MLapStokBarang;
    private static javax.swing.JMenuItem MNomorPajak;
    private static javax.swing.JMenuItem MPajak;
    private javax.swing.JMenuItem MRekapHutangSupplier;
    public static javax.swing.JMenuItem MRekapPembelianHarian;
    private javax.swing.JMenuItem MRekapPiutangPelanggan;
    private javax.swing.JMenuItem MRekapReturCabang;
    public static javax.swing.JMenu MReturCabang;
    private javax.swing.JMenuItem MReturCabangFaktur;
    public static javax.swing.JMenuItem MSearchingBarang;
    public static javax.swing.JMenuItem MTutupHarian;
    public static javax.swing.JMenuItem MValisasiRetur;
    public static javax.swing.JMenuItem MaktivaTetap;
    public static javax.swing.JMenu Manalisa;
    public static javax.swing.JMenuItem Manalisastokbrg;
    public static javax.swing.JMenuItem Mbank;
    public static javax.swing.JMenu Mbarang;
    public static javax.swing.JMenuItem Mdatabarang;
    private javax.swing.JMenuItem MdetailPengiriman;
    public static javax.swing.JMenuItem MeditPassword;
    public static javax.swing.JMenuItem Mgirokeluar;
    public static javax.swing.JMenuItem Mgiromasuk;
    public static javax.swing.JMenuItem Mhutang;
    public static javax.swing.JMenuItem Mjurnal;
    public static javax.swing.JMenuItem Mkategori;
    private javax.swing.JMenu Mkeluar;
    public static javax.swing.JMenu Mkonfigurasi;
    public static javax.swing.JMenuItem MkoreksiStok;
    public static javax.swing.JMenu MlapBarang;
    public static javax.swing.JMenuItem MlapBarangPerMerk;
    public static javax.swing.JMenuItem MlapBukuBesarBulanan;
    private javax.swing.JMenuItem MlapBukuBesarHarian;
    public static javax.swing.JMenuItem MlapEkuitas;
    private javax.swing.JMenuItem MlapFakturDO;
    public static javax.swing.JMenuItem MlapFakturPajak;
    public static javax.swing.JMenu MlapHutang;
    public static javax.swing.JMenuItem MlapJurnal;
    public static javax.swing.JMenuItem MlapNeraca;
    public static javax.swing.JMenu MlapPengiriman;
    public static javax.swing.JMenu MlapPenjualan;
    public static javax.swing.JMenu MlapPiutang;
    public static javax.swing.JMenuItem MlapRugiLaba;
    public static javax.swing.JMenuItem MlapTutupBuku;
    private javax.swing.JMenuItem MlapUmurHutang;
    private javax.swing.JMenuItem MlapUmurPiutang;
    private javax.swing.JMenuItem MlapUmurPiutangPersales;
    public static javax.swing.JMenu Mlapdeliveryorder;
    private javax.swing.JMenu Mlaporan;
    public static javax.swing.JMenu Mlappembelian;
    public static javax.swing.JMenuItem MlogHistory;
    public static javax.swing.JMenuItem MlogOf;
    private javax.swing.JMenu Mlogin;
    public static javax.swing.JMenuItem Mloginuser;
    public static javax.swing.JMenuItem Mmerk;
    public static javax.swing.JMenuItem Mmutasibarang;
    public static javax.swing.JMenuItem Mpelanggan;
    public static javax.swing.JMenuItem Mpembelian;
    public static javax.swing.JMenuItem Mpenjualan;
    public static javax.swing.JMenuItem Mperkiraan;
    public static javax.swing.JMenuItem Mpiutang;
    private javax.swing.JMenuItem MrekapDO;
    private javax.swing.JMenuItem Mrekapreturbeli;
    private javax.swing.JMenuItem Mrekapreturjual;
    private javax.swing.JMenuItem Mreturbelifaktur;
    private javax.swing.JMenuItem Mreturjualfaktur;
    public static javax.swing.JMenu Mreturpembelian;
    public static javax.swing.JMenu Mreturpenjualan;
    public static javax.swing.JMenuItem Msales;
    public static javax.swing.JMenuItem Msatuan;
    public static javax.swing.JMenuItem Msetting;
    public static javax.swing.JMenuItem MsettingStatPeriode;
    public static javax.swing.JMenuItem Msupplier;
    public static javax.swing.JMenu Mtransaksi;
    public static javax.swing.JMenuItem Mtutupbuku;
    public static javax.swing.JMenuItem Mtutupstok;
    public static javax.swing.JMenuItem MuserAccount;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem21;
    private javax.swing.JMenuItem jMenuItem26;
    private javax.swing.JMenuItem jMenuItem27;
    private javax.swing.JMenuItem jMenuItem28;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    public static com.erv.model.DesktopCool panelCool1;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    // End of variables declaration//GEN-END:variables
    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;
    private JDialog aboutBox;

    public static void aktif() {
        Mkonfigurasi.setVisible(true);
        MBackupdatabase.setVisible(true);
        MuserAccount.setVisible(true);
        MeditPassword.setVisible(true);
        Mperkiraan.setVisible(true);
        MaktivaTetap.setVisible(true);
        Mbarang.setVisible(true);
        Msatuan.setVisible(true);
        Mmerk.setVisible(true);
        Mkategori.setVisible(true);
        Mdatabarang.setVisible(true);
        Mpelanggan.setVisible(true);
        Msupplier.setVisible(true);
        Msales.setVisible(true);
        Mbank.setVisible(true);
        Msetting.setVisible(false);
        MsettingStatPeriode.setVisible(true);
        MKontrolTanggal.setVisible(true);
        MlogHistory.setVisible(true);
        Manalisa.setVisible(false);
        MSearchingBarang.setVisible(true);
        Mtransaksi.setVisible(true);
        Mjurnal.setVisible(true);
        Mpenjualan.setVisible(true);
        Mpembelian.setVisible(true);
        Mhutang.setVisible(true);
        Mgirokeluar.setVisible(true);
        Mpiutang.setVisible(true);
        Mgiromasuk.setVisible(true);
        Mtutupbuku.setVisible(true);
        Mtutupstok.setVisible(true);
        MTutupHarian.setVisible(true);
        MkoreksiStok.setVisible(true);
        Mmutasibarang.setVisible(false);
        MValisasiRetur.setVisible(true);
        MDeliveryOrder.setVisible(true);
        Mlappembelian.setVisible(true);
        MLapPembelianHarian.setVisible(true);
        MLapPembelianPerFaktur.setVisible(true);
        MRekapPembelianHarian.setVisible(true);
        MlapPenjualan.setVisible(true);
        MlapPengiriman.setVisible(false);
        Mreturpembelian.setVisible(true);
        Mreturpenjualan.setVisible(true);
        MReturCabang.setVisible(false);
        Mlapdeliveryorder.setVisible(true);
        MlapBarang.setVisible(true);
        MLapPersediaanBrgDagang.setVisible(false);
        MlapHutang.setVisible(true);
        MlapPiutang.setVisible(true);
        MLapPerkiraan.setVisible(true);
        MlapFakturPajak.setVisible(false);
        MlapTutupBuku.setVisible(false);
        MlapJurnal.setVisible(true);
        MLapBukuBesar.setVisible(true);
        MlapNeraca.setVisible(true);
        MlapRugiLaba.setVisible(true);
        MlapEkuitas.setVisible(true);
        MLapPajak.setVisible(true);
        MNomorPajak.setVisible(true);
        MPajak.setVisible(true);
    }

    public static void nonaktif() {
        Mkonfigurasi.setVisible(false);
        MBackupdatabase.setVisible(false);
        MuserAccount.setVisible(false);
        MeditPassword.setVisible(false);
        Mperkiraan.setVisible(false);
        MaktivaTetap.setVisible(false);
        Mbarang.setVisible(false);
        Msatuan.setVisible(false);
        Mmerk.setVisible(false);
        Mkategori.setVisible(false);
        Mdatabarang.setVisible(false);
        Mpelanggan.setVisible(false);
        Msupplier.setVisible(false);
        Msales.setVisible(false);
        Mbank.setVisible(false);
        Msetting.setVisible(false);
        MsettingStatPeriode.setVisible(false);
        MKontrolTanggal.setVisible(false);
        MlogHistory.setVisible(false);
        Manalisa.setVisible(false);
        MSearchingBarang.setVisible(false);
        //Mtransaksi.setVisible(false);
        Mjurnal.setVisible(false);
        Mpenjualan.setVisible(false);
        Mpembelian.setVisible(false);
        Mhutang.setVisible(false);
        Mgirokeluar.setVisible(false);
        Mpiutang.setVisible(false);
        Mgiromasuk.setVisible(false);
        Mtutupbuku.setVisible(false);
        Mtutupstok.setVisible(false);
        MTutupHarian.setVisible(false);
        MkoreksiStok.setVisible(false);
        Mmutasibarang.setVisible(false);
        MValisasiRetur.setVisible(false); 
        MDeliveryOrder.setVisible(false);
        Mlappembelian.setVisible(false);
        MLapPembelianHarian.setVisible(false);
        MLapPembelianPerFaktur.setVisible(false);
        MRekapPembelianHarian.setVisible(false);
        MlapPenjualan.setVisible(false);
        MlapPengiriman.setVisible(false);
        Mreturpembelian.setVisible(false);
        Mreturpenjualan.setVisible(false);
        MReturCabang.setVisible(false);
        Mlapdeliveryorder.setVisible(false);
        MlapBarang.setVisible(false);
        MLapPersediaanBrgDagang.setVisible(false);
        MlapHutang.setVisible(false);
        MlapPiutang.setVisible(false);
        MLapPerkiraan.setVisible(false);
        MlapFakturPajak.setVisible(false);
        MlapTutupBuku.setVisible(false);
        MlapJurnal.setVisible(false);
        MLapBukuBesar.setVisible(false);
        MlapNeraca.setVisible(false);
        MlapRugiLaba.setVisible(false);
        MlapEkuitas.setVisible(false);
        MLapPajak.setVisible(false);
        MNomorPajak.setVisible(false);
        MPajak.setVisible(false);
    }

//    public static void pembelianakses() {
//        Mkonfigurasi.setVisible(false);
//        MBackupdatabase.setVisible(false);
//        MuserAccount.setVisible(false);
//        MeditPassword.setVisible(true);
//        Mperkiraan.setVisible(false);
//        MaktivaTetap.setVisible(false);
//        Mbarang.setVisible(false);
//        Mmerk.setVisible(false);
//        Mkategori.setVisible(false);
//        Mdatabarang.setVisible(false);
//        Mpelanggan.setVisible(false);
//        Msupplier.setVisible(true);
//        Msales.setVisible(false);
//        Mbank.setVisible(false);
//        Msetting.setVisible(false);
//        MsettingStatPeriode.setVisible(false);
//        MlogHistory.setVisible(false);
//        Manalisa.setVisible(false);
//        MSearchingBarang.setVisible(true);
//        Mjurnal.setVisible(false);
//        Mpenjualan.setVisible(false);
//        Mpembelian.setVisible(true);
////    FormPembelian.btnBayarPenerimaanHutang.setVisible(false);
////    FormPembelian.btnDeletePembelianAll.setVisible(false);
//        Mhutang.setVisible(false);
//        Mgirokeluar.setVisible(false);
//        Mpiutang.setVisible(false);
//        Mgiromasuk.setVisible(false);
//        Mtutupbuku.setVisible(false);
//        Mtutupstok.setVisible(false);
//        MkoreksiStok.setVisible(false);
//        Mmutasibarang.setVisible(false);
//        Mlappembelian.setVisible(true);
//        MlapPenjualan.setVisible(true);
//        MlapPengiriman.setVisible(true);
//        Mreturpembelian.setVisible(true);
//        Mreturpenjualan.setVisible(false);
//        MReturCabang.setVisible(false);
//        Mlapdeliveryorder.setVisible(false);
//        MlapBarang.setVisible(true);
//        MLapPersediaanBrgDagang.setVisible(false);
//        MlapHutang.setVisible(false);
//        MlapPiutang.setVisible(false);
//        MLapPerkiraan.setVisible(false);
//        MlapFakturPajak.setVisible(false);
//        MlapTutupBuku.setVisible(false);
//        MlapJurnal.setVisible(false);
//        MLapBukuBesar.setVisible(false);
//        MlapNeraca.setVisible(false);
//        MlapRugiLaba.setVisible(false);
//        MlapEkuitas.setVisible(false);
//    }

//    public static void penjualanakses() {
//        Mkonfigurasi.setVisible(false);
//        MBackupdatabase.setVisible(false);
//        MuserAccount.setVisible(false);
//        MeditPassword.setVisible(true);
//        Mperkiraan.setVisible(false);
//        MaktivaTetap.setVisible(false);
//        Mbarang.setVisible(false);
//        Mmerk.setVisible(false);
//        Mkategori.setVisible(false);
//        Mdatabarang.setVisible(false);
//        Mpelanggan.setVisible(true);
//        Msupplier.setVisible(false);
//        Msales.setVisible(false);
//        Mbank.setVisible(false);
//        Msetting.setVisible(false);
//        MsettingStatPeriode.setVisible(false);
//        MlogHistory.setVisible(false);
//        Manalisa.setVisible(false);
//        MSearchingBarang.setVisible(true);
//        Mjurnal.setVisible(false);
//        Mpenjualan.setVisible(true);
////    FormPenjualan.btnBayarPenerimaanPiutang.setVisible(false);
////    FormPenjualan.btnDeletePenjualanAll.setVisible(false);
//        Mpembelian.setVisible(false);
//        Mhutang.setVisible(false);
//        Mgirokeluar.setVisible(false);
//        Mpiutang.setVisible(false);
//        Mgiromasuk.setVisible(false);
//        Mtutupbuku.setVisible(false);
//        Mtutupstok.setVisible(false);
//        MkoreksiStok.setVisible(false);
//        Mmutasibarang.setVisible(false);
//        Mlappembelian.setVisible(false);
//        MlapPenjualan.setVisible(true);
//        MlapPengiriman.setVisible(false);
//        Mreturpembelian.setVisible(false);
//        Mreturpenjualan.setVisible(true);
//        MReturCabang.setVisible(false);
//        Mlapdeliveryorder.setVisible(false);
//        MlapBarang.setVisible(false);
//        MLapPersediaanBrgDagang.setVisible(false);
//        MlapHutang.setVisible(false);
//        MlapPiutang.setVisible(false);
//        MLapPerkiraan.setVisible(false);
//        MlapFakturPajak.setVisible(false);
//        MlapTutupBuku.setVisible(false);
//        MlapJurnal.setVisible(false);
//        MLapBukuBesar.setVisible(false);
//        MlapNeraca.setVisible(false);
//        MlapRugiLaba.setVisible(false);
//        MlapEkuitas.setVisible(false);
//    }

    public static void accountingakses() {
        Mkonfigurasi.setVisible(true);
        MBackupdatabase.setVisible(true);
        MuserAccount.setVisible(false);
        MeditPassword.setVisible(true);
        Mperkiraan.setVisible(true);
        MaktivaTetap.setVisible(false);
        Mbarang.setVisible(false);
        Msatuan.setVisible(false);
        Mmerk.setVisible(false);
        Mkategori.setVisible(false);
        Mdatabarang.setVisible(false);
        Mpelanggan.setVisible(true);
        Msupplier.setVisible(false);
        Msales.setVisible(false);
        Mbank.setVisible(false);
        Msetting.setVisible(false);
        MsettingStatPeriode.setVisible(false);
        MKontrolTanggal.setVisible(false);
        MlogHistory.setVisible(false);
        Manalisa.setVisible(false);
        MSearchingBarang.setVisible(true);
        Mtransaksi.setVisible(true);
        Mjurnal.setVisible(true);
        Mpenjualan.setVisible(false);
        Mpembelian.setVisible(false);
        Mhutang.setVisible(true);
        Mgirokeluar.setVisible(true);
        Mpiutang.setVisible(true);
        Mgiromasuk.setVisible(true);
        Mtutupbuku.setVisible(false);
        Mtutupstok.setVisible(false);
        MTutupHarian.setVisible(false);
        MkoreksiStok.setVisible(false);
        Mmutasibarang.setVisible(false);
        Mlappembelian.setVisible(true);
        MLapPembelianHarian.setVisible(true);
        MLapPembelianPerFaktur.setVisible(true);
        MRekapPembelianHarian.setVisible(true);
        MlapPenjualan.setVisible(true);
        MlapPengiriman.setVisible(false);
        Mreturpembelian.setVisible(true);
        Mreturpenjualan.setVisible(true);
        MReturCabang.setVisible(false);
        Mlapdeliveryorder.setVisible(false);
        MlapBarang.setVisible(true);
        MLapPersediaanBrgDagang.setVisible(false);
        MlapHutang.setVisible(true);
        MlapPiutang.setVisible(true);
        MLapPerkiraan.setVisible(true);
        MlapFakturPajak.setVisible(false);
        MlapTutupBuku.setVisible(false);
        MlapJurnal.setVisible(true);
        MLapBukuBesar.setVisible(true);
        MlapNeraca.setVisible(false);
        MlapRugiLaba.setVisible(false);
        MlapEkuitas.setVisible(false);
        MLapPajak.setVisible(true);
        MNomorPajak.setVisible(false);
        MPajak.setVisible(false);
    }

    public static void pajakakses() {
        Mkonfigurasi.setVisible(false);
        MBackupdatabase.setVisible(false);
        MuserAccount.setVisible(false);
        MeditPassword.setVisible(true);
        Mperkiraan.setVisible(false);
        MaktivaTetap.setVisible(false);
        Mbarang.setVisible(false);
        Msatuan.setVisible(false);
        Mmerk.setVisible(false);
        Mkategori.setVisible(false);
        Mdatabarang.setVisible(false);
        Mpelanggan.setVisible(false);
        Msupplier.setVisible(false);
        Msales.setVisible(false);
        Mbank.setVisible(false);
        Msetting.setVisible(false);
        MsettingStatPeriode.setVisible(false);
        MKontrolTanggal.setVisible(false);
        MlogHistory.setVisible(false);
        Manalisa.setVisible(false);
        MSearchingBarang.setVisible(false);
        Mtransaksi.setVisible(false);
        Mjurnal.setVisible(false);
        Mpenjualan.setVisible(false);
        Mpembelian.setVisible(false);
        Mhutang.setVisible(false);
        Mgirokeluar.setVisible(false);
        Mpiutang.setVisible(false);
        Mgiromasuk.setVisible(false);
        MkoreksiStok.setVisible(false);
        Mmutasibarang.setVisible(false);
        MValisasiRetur.setVisible(false);
        MDeliveryOrder.setVisible(false);
        Mtutupbuku.setVisible(false);
        Mtutupstok.setVisible(false);
        MTutupHarian.setVisible(false);
        Mlappembelian.setVisible(false);
        MLapPembelianHarian.setVisible(false);
        MLapPembelianPerFaktur.setVisible(false);
        MRekapPembelianHarian.setVisible(false);
        MlapPenjualan.setVisible(false);
        MlapPengiriman.setVisible(false);
        Mreturpembelian.setVisible(false);
        Mreturpenjualan.setVisible(false);
        MReturCabang.setVisible(false);
        Mlapdeliveryorder.setVisible(false);
        MlapBarang.setVisible(false);
        MLapPersediaanBrgDagang.setVisible(false);
        MlapHutang.setVisible(false);
        MlapPiutang.setVisible(false);
        MLapPerkiraan.setVisible(false);
        MlapFakturPajak.setVisible(false);
        MlapTutupBuku.setVisible(false);
        MlapJurnal.setVisible(false);
        MLapBukuBesar.setVisible(false);
        MlapNeraca.setVisible(false);
        MlapRugiLaba.setVisible(false);
        MlapEkuitas.setVisible(false);
        MLapPajak.setVisible(false);
        MNomorPajak.setVisible(true);
        MPajak.setVisible(true);
    }

    public static void operatorakses() {
        Mkonfigurasi.setVisible(true);
        MBackupdatabase.setVisible(true);
        MuserAccount.setVisible(false);
        MeditPassword.setVisible(true);
        Mperkiraan.setVisible(false);
        MaktivaTetap.setVisible(false);
        Mbarang.setVisible(true);
        Msatuan.setVisible(true);
        Mmerk.setVisible(true);
        Mkategori.setVisible(true);
        Mdatabarang.setVisible(true);
        Mpelanggan.setVisible(true);
        Msupplier.setVisible(true);
        Msales.setVisible(true);
        Mbank.setVisible(false);
        Msetting.setVisible(false);
        MsettingStatPeriode.setVisible(false);
        MKontrolTanggal.setVisible(false);
        MlogHistory.setVisible(false);
        Manalisa.setVisible(false);
        MSearchingBarang.setVisible(true);
        Mtransaksi.setVisible(true);
        Mjurnal.setVisible(false);
        Mpenjualan.setVisible(true);
        Mpembelian.setVisible(true);
        Mhutang.setVisible(false);
        Mgirokeluar.setVisible(false);
        Mpiutang.setVisible(false);
        Mgiromasuk.setVisible(false);
        MkoreksiStok.setVisible(false);
        Mmutasibarang.setVisible(false);
        MValisasiRetur.setVisible(false);
        MDeliveryOrder.setVisible(false);
        Mtutupbuku.setVisible(false);
        Mtutupstok.setVisible(false);
        MTutupHarian.setVisible(false);
        Mlappembelian.setVisible(true);
        MLapPembelianHarian.setVisible(true);
        MLapPembelianPerFaktur.setVisible(true);
        MRekapPembelianHarian.setVisible(true);
        MlapPenjualan.setVisible(true);
        MlapPengiriman.setVisible(false);
        Mreturpembelian.setVisible(true);
        Mreturpenjualan.setVisible(true);
        MReturCabang.setVisible(false);
        Mlapdeliveryorder.setVisible(false);
        MlapBarang.setVisible(true);
        MLapPersediaanBrgDagang.setVisible(false);
        MlapHutang.setVisible(false);
        MlapPiutang.setVisible(false);
        MLapPerkiraan.setVisible(false);
        MlapFakturPajak.setVisible(false);
        MlapTutupBuku.setVisible(false);
        MlapJurnal.setVisible(false);
        MLapBukuBesar.setVisible(false);
        MlapNeraca.setVisible(false);
        MlapRugiLaba.setVisible(false);
        MlapEkuitas.setVisible(false);
        MLapPajak.setVisible(false);
        MNomorPajak.setVisible(true);
        MPajak.setVisible(true);
    }

//    public static void gudangakses() {
//        Mkonfigurasi.setVisible(false);
//        MBackupdatabase.setVisible(false);
//        MuserAccount.setVisible(false);
//        MeditPassword.setVisible(true);
//        Mperkiraan.setVisible(false);
//        MaktivaTetap.setVisible(false);
//        Mbarang.setVisible(false);
//        Msatuan.setVisible(false);
//        Mmerk.setVisible(false);
//        Mkategori.setVisible(false);
//        Mdatabarang.setVisible(true);
//        Mpelanggan.setVisible(false);
//        Msupplier.setVisible(false);
//        Msales.setVisible(false);
//        Mbank.setVisible(false);
//        Msetting.setVisible(false);
//        MsettingStatPeriode.setVisible(false);
//        MKontrolTanggal.setVisible(false);
//        MlogHistory.setVisible(false);
//        Manalisa.setVisible(false);
//        MSearchingBarang.setVisible(true);
//        Mjurnal.setVisible(false);
//        Mpenjualan.setVisible(false);
//        Mpembelian.setVisible(false);
//        Mhutang.setVisible(false);
//        Mgirokeluar.setVisible(false);
//        Mpiutang.setVisible(false);
//        Mgiromasuk.setVisible(false);
//        Mtutupbuku.setVisible(false);
//        Mtutupstok.setVisible(false);
//        MTutupHarian.setVisible(false);
//        MkoreksiStok.setVisible(false);
//        Mmutasibarang.setVisible(false);
//        Mlappembelian.setVisible(true);
//        MLapPembelianHarian.setVisible(false);
//        MLapPembelianPerFaktur.setVisible(true);
//        MRekapPembelianHarian.setVisible(false);
//        MlapPenjualan.setVisible(false);
//        MlapPengiriman.setVisible(false);
//        Mreturpembelian.setVisible(false);
//        Mreturpenjualan.setVisible(false);
//        MReturCabang.setVisible(false);
//        Mlapdeliveryorder.setVisible(false);
//        MlapBarang.setVisible(true);
//        MLapPersediaanBrgDagang.setVisible(false);
//        MlapHutang.setVisible(false);
//        MlapPiutang.setVisible(false);
//        MLapPerkiraan.setVisible(false);
//        MlapFakturPajak.setVisible(false);
//        MlapTutupBuku.setVisible(false);
//        MlapJurnal.setVisible(false);
//        MLapBukuBesar.setVisible(false);
//        MlapNeraca.setVisible(false);
//        MlapRugiLaba.setVisible(false);
//        MlapEkuitas.setVisible(false);
//    }
    
    public static void apotekerakses() {
        Mkonfigurasi.setVisible(true);
        MBackupdatabase.setVisible(true);
        MuserAccount.setVisible(false);
        MeditPassword.setVisible(true);
        Mperkiraan.setVisible(false);
        MaktivaTetap.setVisible(false);
        Mbarang.setVisible(true);
        Msatuan.setVisible(false);
        Mmerk.setVisible(false);
        Mkategori.setVisible(false);
        Mdatabarang.setVisible(true);
        Mpelanggan.setVisible(false);
        Msupplier.setVisible(false);
        Msales.setVisible(false);
        Mbank.setVisible(false);
        Msetting.setVisible(false);
        MsettingStatPeriode.setVisible(false);
        MKontrolTanggal.setVisible(false);
        MlogHistory.setVisible(false);
        Manalisa.setVisible(false);
        MSearchingBarang.setVisible(true);
        Mtransaksi.setVisible(false);
        Mjurnal.setVisible(false);
        Mpenjualan.setVisible(false);
        Mpembelian.setVisible(false);
        Mhutang.setVisible(false);
        Mgirokeluar.setVisible(false);
        Mpiutang.setVisible(false);
        Mgiromasuk.setVisible(false);
        MkoreksiStok.setVisible(false);
        Mmutasibarang.setVisible(false);
        MValisasiRetur.setVisible(false);
        MDeliveryOrder.setVisible(false);
        Mtutupbuku.setVisible(false);
        Mtutupstok.setVisible(false);
        MTutupHarian.setVisible(false);
        Mlappembelian.setVisible(true);
        MLapPembelianPerFaktur.setVisible(true);
        MLapPembelianHarian.setVisible(true);
        MRekapPembelianHarian.setVisible(true);
        MlapPenjualan.setVisible(true);
        MlapPengiriman.setVisible(false);
        Mreturpembelian.setVisible(true);
        Mreturpenjualan.setVisible(true);
        MReturCabang.setVisible(false);
        Mlapdeliveryorder.setVisible(false);
        MlapBarang.setVisible(true);
        MlapBarangPerMerk.setVisible(true);
        MLapBarangPerKategori.setVisible(true);
        MLapBrgAkhirPeriode.setVisible(true);
        MLapKartuStokBulan.setVisible(false);
        MLapKartuStokTanggal.setVisible(true);
        MLapPersediaanBrgDagang.setVisible(false);
        MLapKatalogBarang.setVisible(true);
        MlapHutang.setVisible(false);
        MlapPiutang.setVisible(false);
        MLapPerkiraan.setVisible(false);
        MlapFakturPajak.setVisible(false);
        MlapTutupBuku.setVisible(false);
        MlapJurnal.setVisible(false);
        MLapBukuBesar.setVisible(false);
        MlapNeraca.setVisible(false);
        MlapRugiLaba.setVisible(false);
        MlapEkuitas.setVisible(false);
        MLapPajak.setVisible(false);
        MNomorPajak.setVisible(false);
        MPajak.setVisible(false);
    }
    
    public static void generalakses() {
        Mkonfigurasi.setVisible(false);
        MBackupdatabase.setVisible(false);
        MuserAccount.setVisible(false);
        MeditPassword.setVisible(false);
        Mperkiraan.setVisible(false);
        MaktivaTetap.setVisible(false);
        Mbarang.setVisible(false);
        Msatuan.setVisible(false);
        Mmerk.setVisible(false);
        Mkategori.setVisible(false);
        Mdatabarang.setVisible(false);
        Mpelanggan.setVisible(false);
        Msupplier.setVisible(false);
        Msales.setVisible(false);
        Mbank.setVisible(false);
        Msetting.setVisible(false);
        MsettingStatPeriode.setVisible(false);
        MKontrolTanggal.setVisible(false);
        MlogHistory.setVisible(false);
        Manalisa.setVisible(false);
        MSearchingBarang.setVisible(false);
        Mtransaksi.setVisible(false);
        Mjurnal.setVisible(false);
        Mpenjualan.setVisible(false);
        Mpembelian.setVisible(false);
        Mhutang.setVisible(false);
        Mgirokeluar.setVisible(false);
        Mpiutang.setVisible(false);
        Mgiromasuk.setVisible(false);
        MkoreksiStok.setVisible(false);
        Mmutasibarang.setVisible(false);
        MValisasiRetur.setVisible(false);
        MDeliveryOrder.setVisible(false);
        Mtutupbuku.setVisible(false);
        Mtutupstok.setVisible(false);
        MTutupHarian.setVisible(false);
        Mlappembelian.setVisible(false);
        MlapPenjualan.setVisible(false);
        MlapPengiriman.setVisible(false);
        Mreturpembelian.setVisible(false);
        Mreturpenjualan.setVisible(false);
        MReturCabang.setVisible(false);
        Mlapdeliveryorder.setVisible(false);
        MlapBarang.setVisible(false);
        MLapPersediaanBrgDagang.setVisible(false);
        MlapHutang.setVisible(false);
        MlapPiutang.setVisible(false);
        MLapPerkiraan.setVisible(false);
        MlapFakturPajak.setVisible(false);
        MlapTutupBuku.setVisible(false);
        MlapJurnal.setVisible(false);
        MLapBukuBesar.setVisible(false);
        MlapNeraca.setVisible(false);
        MlapRugiLaba.setVisible(false);
        MlapEkuitas.setVisible(false);
        MLapPajak.setVisible(false);
        MNomorPajak.setVisible(false);
        MPajak.setVisible(false);
    }
    
    public static void asistenadminakses() {
        Mkonfigurasi.setVisible(true);
        MBackupdatabase.setVisible(true);
        MuserAccount.setVisible(false);
        MeditPassword.setVisible(true);
        Mperkiraan.setVisible(false);
        MaktivaTetap.setVisible(false);
        Mbarang.setVisible(true);
        Msatuan.setVisible(true);
        Mmerk.setVisible(true);
        Mkategori.setVisible(true);
        Mdatabarang.setVisible(true);
        Mpelanggan.setVisible(true);
        Msupplier.setVisible(true);
        Msales.setVisible(true);
        Mbank.setVisible(false);
        Msetting.setVisible(false);
        MsettingStatPeriode.setVisible(false);
        MKontrolTanggal.setVisible(false);
        MlogHistory.setVisible(false);
        Manalisa.setVisible(false);
        MSearchingBarang.setVisible(true);
        Mtransaksi.setVisible(true);
        Mjurnal.setVisible(false);
        Mpenjualan.setVisible(false);
        Mpembelian.setVisible(false);
        Mhutang.setVisible(false);
        Mgirokeluar.setVisible(false);
        Mpiutang.setVisible(false);
        Mgiromasuk.setVisible(false);
        MkoreksiStok.setVisible(false);
        Mmutasibarang.setVisible(false);
        MValisasiRetur.setVisible(true);
        MDeliveryOrder.setVisible(false);
        Mtutupbuku.setVisible(false);
        Mtutupstok.setVisible(false);
        MTutupHarian.setVisible(true);
        Mlappembelian.setVisible(true);
        MLapPembelianPerFaktur.setVisible(true);
        MLapPembelianHarian.setVisible(true);
        MRekapPembelianHarian.setVisible(true);
        MlapPenjualan.setVisible(true);
        MlapPengiriman.setVisible(false);
        Mreturpembelian.setVisible(true);
        Mreturpenjualan.setVisible(true);
        MReturCabang.setVisible(false);
        Mlapdeliveryorder.setVisible(false);
        MlapBarang.setVisible(true);
        MLapPersediaanBrgDagang.setVisible(false);
        MlapHutang.setVisible(true);
        MlapPiutang.setVisible(true);
        MLapPerkiraan.setVisible(false);
        MlapFakturPajak.setVisible(false);
        MlapTutupBuku.setVisible(false);
        MlapJurnal.setVisible(false);
        MLapBukuBesar.setVisible(false);
        MlapNeraca.setVisible(false);
        MlapRugiLaba.setVisible(false);
        MlapEkuitas.setVisible(false);
        MLapPajak.setVisible(false);
        MNomorPajak.setVisible(true);
        MPajak.setVisible(true);
    }
    
    public static void masterdataakses() {
        Mkonfigurasi.setVisible(true);
        MBackupdatabase.setVisible(true);
        MuserAccount.setVisible(false);
        MeditPassword.setVisible(true);
        Mperkiraan.setVisible(false);
        MaktivaTetap.setVisible(false);
        Mbarang.setVisible(true);
        Msatuan.setVisible(true);
        Mmerk.setVisible(true);
        Mkategori.setVisible(true);
        Mdatabarang.setVisible(true);
        Mpelanggan.setVisible(true);
        Msupplier.setVisible(true);
        Msales.setVisible(true);
        Mbank.setVisible(false);
        Msetting.setVisible(false);
        MsettingStatPeriode.setVisible(false);
        MKontrolTanggal.setVisible(false);
        MlogHistory.setVisible(false);
        Manalisa.setVisible(false);
        MSearchingBarang.setVisible(true);
        Mtransaksi.setVisible(false);
        Mjurnal.setVisible(false);
        Mpenjualan.setVisible(false);
        Mpembelian.setVisible(false);
        Mhutang.setVisible(false);
        Mgirokeluar.setVisible(false);
        Mpiutang.setVisible(false);
        Mgiromasuk.setVisible(false);
        MkoreksiStok.setVisible(false);
        Mmutasibarang.setVisible(false);
        MValisasiRetur.setVisible(false);
        MDeliveryOrder.setVisible(false);
        Mtutupbuku.setVisible(false);
        Mtutupstok.setVisible(false);
        MTutupHarian.setVisible(false);
        Mlappembelian.setVisible(true);
        MlapPenjualan.setVisible(true);
        MlapPengiriman.setVisible(false);
        Mreturpembelian.setVisible(true);
        Mreturpenjualan.setVisible(true);
        MReturCabang.setVisible(false);
        Mlapdeliveryorder.setVisible(false);
        MlapBarang.setVisible(true);
        MLapPersediaanBrgDagang.setVisible(false);
        MlapHutang.setVisible(true);
        MlapPiutang.setVisible(true);
        MLapPerkiraan.setVisible(false);
        MlapFakturPajak.setVisible(false);
        MlapTutupBuku.setVisible(false);
        MlapJurnal.setVisible(false);
        MLapBukuBesar.setVisible(false);
        MlapNeraca.setVisible(false);
        MlapRugiLaba.setVisible(false);
        MlapEkuitas.setVisible(false);
        MLapPajak.setVisible(false);
        MNomorPajak.setVisible(false);
        MPajak.setVisible(false);
    }

    void konek() {
//        try {
//            if(con==null || con.isClosed()){
//            Class.forName("org.h2.Driver").getInterfaces();
//            con = DriverManager.getConnection("jdbc:h2:tcp://"+IPDB+"/~/penjualan", "", "");
//            koneksi.con = con;
//            }
//        } catch (Exception ex) {
//            JOptionPane.showMessageDialog(panelCool1, ex.toString()+" "+IPDB);
//        }
    }
    
    void backupDB(){
        java.text.DateFormat d = new SimpleDateFormat("yyyyMMdd");
        java.util.Date tgl=new java.util.Date();
        JOptionPane.showMessageDialog(null, "Keluar Windows");
        DatabaseBackup backup = new H2DatabaseBackup();
        Calendar cal = Calendar.getInstance();
        String fileName = "D:/BACKUPDB/BackupApotikMA" + cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DATE) + "-" + cal.get(Calendar.HOUR_OF_DAY) + cal.get(Calendar.MINUTE) + ".zip";

        try {
//            backup.backupDatabase(koneksi.getKoneksiJ(), fileName);
//            JavarieSoftApp.server.stop();
            String sql="BACKUP TO '"+fileName+"'";         
            koneksi.getKoneksiJ().createStatement().executeUpdate(sql);          
            System.out.println("BackUp Ok " + fileName);
            System.out.println("Server Stop ");
        } catch (Throwable t) {
            // Log something, alert the user, format the user's hard drive out of spite....
            JOptionPane.showMessageDialog(null, t.getMessage());
        }      
    }
}
