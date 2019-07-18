/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author zenocandragufa
 */

import com.toedter.calendar.JDateChooser;
import java.text.SimpleDateFormat;
import java.util.Date;
import Koneksi.koneksiDB;
import java.sql.*;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class frmTransaksi extends javax.swing.JInternalFrame {

    //membuat objek    
    private DefaultTableModel model;
    
    //deklarasi variabel
    String no_transaksi, tgl_bayar, id_penyewa, nama_penyewa, id_kamar, nama_kamar, jenis_kamar, keterangan, dateChooser;
    //Date tgl_bayar;
    int harga_kamar, bayar, kekurangan ;
    /**
     * Creates new form frmTransaksi
     */
    public frmTransaksi() {
        initComponents();
        
        //membuat obyek
        model = new DefaultTableModel();
        
        //memberi nama header pada tabel
        tabel.setModel(model);
        model.addColumn("NO TRANSAKSI");
        model.addColumn("TANGGAL BAYAR");
        model.addColumn("ID PENYEWA");
        model.addColumn("NAMA PENYEWA");
        model.addColumn("ID KAMAR");
        model.addColumn("NAMA KAMAR");
        model.addColumn("JENIS KAMAR");
        model.addColumn("HARGA KAMAR");
        model.addColumn("BAYAR");
        model.addColumn("KEKURANGAN");
        model.addColumn("KETERANGAN");
                
        //fungsi ambil data
        getData();
        tampilComboPenyewa();
        tampilComboKamar();
    }
    
    //fungsi membaca data kategori
    public void getData(){
        //kosongkan tabel
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        
        //eksekusi koneksi dan kirimkan query ke database
        try{
            //tes koneksi
            Statement stat = (Statement) koneksiDB.getKoneksi().createStatement();
            
            //perintah sql untuk membaca data dari tabel gaji        
            String sql = "SELECT * FROM transaksi";
            ResultSet res = stat.executeQuery(sql);
            
            //baca data
            while(res.next()){
                //membuat obyek berjenis array
                Object[] obj = new Object[12];
                obj[0]=res.getString("no_transaksi");
                obj[1]=res.getString("tgl_bayar");
                obj[2]=res.getString("id_penyewa");
                obj[3]=res.getString("nama_penyewa");
                obj[4]=res.getString("id_kamar");
                obj[5]=res.getString("nama_kamar");
                obj[6]=res.getString("jenis_kamar");
                obj[7]=res.getString("harga_kamar");
                obj[8]=res.getString("bayar");
                obj[9]=res.getString("kekurangan");
                obj[10]=res.getString("keterangan");
                model.addRow(obj);
            }
        }catch(SQLException err){
           JOptionPane.showMessageDialog(null, err.getMessage());
        }
    }
    
    public void getDataComboPenyewa(){
        //eksekusi koneksi dan kirimkan query ke database
        try{
            //tes koneksi
            Statement stat = (Statement) koneksiDB.getKoneksi().createStatement();
            
            //perintah sql untuk membaca data dari tabel gaji        
            String sql = "SELECT * FROM penyewa WHERE id_penyewa = '"+ /*Integer.toString((int)*/ idpenyewa.getSelectedItem() +"'";
            ResultSet res = stat.executeQuery(sql);
            
            //baca data
            while(res.next()){
                //membuat obyek berjenis array
                namapenyewa.setText(res.getString("nama_penyewa"));
            }
        }catch(SQLException err){
           JOptionPane.showMessageDialog(null, err.getMessage());
        }
    }
    
   public void getDataComboKamar(){
        //eksekusi koneksi dan kirimkan query ke database
        try{
            //tes koneksi
            Statement stat = (Statement) koneksiDB.getKoneksi().createStatement();
            
            //perintah sql untuk membaca data dari tabel gaji        
            String sql = "SELECT * FROM kamar WHERE id_kamar = '"+ /*Integer.toString((int)*/ idkamar.getSelectedItem() +"'";
            ResultSet res = stat.executeQuery(sql);
            
            //baca data
            while(res.next()){
                //membuat obyek berjenis array
                namakamar.setText(res.getString("nama_kamar"));
                jeniskamar.setText(res.getString("jenis_kamar"));
                hargakamar.setText(res.getString("harga_kamar"));
            }
        }catch(SQLException err){
           JOptionPane.showMessageDialog(null, err.getMessage());
        }
    }
   
   public void tanggal(){
       if(tgl.getDate()!=null){
       SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
       tgl_bayar = String.valueOf(format.format(tgl.getDate()));
       }
   }
   
    public void loadData(){
        tanggal();
                       
        //mengambil data dari textbox dan menyimpan nilainya pada variabel
        no_transaksi = notransaksi.getText();
        //tgl_bayar = tgl.getText();
        id_penyewa = (String) idpenyewa.getSelectedItem();
        nama_penyewa = namapenyewa.getText();
        id_kamar = (String) idkamar.getSelectedItem();
        nama_kamar = namakamar.getText();
        jenis_kamar = jeniskamar.getText();
        harga_kamar = Integer.parseInt(hargakamar.getText());
        bayar = Integer.parseInt(tbayar.getText());
        kekurangan = Integer.parseInt(tkekurangan.getText());
        keterangan = tketerangan.getText();
    
    }
    
    public void dataSelect(){
        //deklarasi variabel
        tanggal();
        int i = tabel.getSelectedRow();
        
        //uji adakah data di tabel?
        if(i == -1){
            //tidak ada yang terpilih atau dipilih.
            return;
        }
        
         //int index = tabel.getSelectedRow();
            Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse((String)model.getValueAt(i,1));
        } catch (ParseException ex) {
            Logger.getLogger(frmTransaksi.class.getName()).log(Level.SEVERE, null, ex);
        }
            tgl.setDate(date);
        
        notransaksi.setText(""+model.getValueAt(i,0));
        idpenyewa.setSelectedItem(""+model.getValueAt(i,2));
        namapenyewa.setText(""+model.getValueAt(i,3));
        idkamar.setSelectedItem(""+model.getValueAt(i,4));
        namakamar.setText(""+model.getValueAt(i,5));
        jeniskamar.setText(""+model.getValueAt(i,6));
        hargakamar.setText(""+model.getValueAt(i,7));
        tbayar.setText(""+model.getValueAt(i,8));
        tkekurangan.setText(""+model.getValueAt(i,9));
        tketerangan.setText(""+model.getValueAt(i,10));
        //nama.setText(""+model.getValueAt(i,1));
        //cojenis.setSelectedItem(""+model.getValueAt(i,2));
    }
    
    public void tampilComboPenyewa(){
        try {
            //test koneksi
            Statement stat = (Statement) koneksiDB.getKoneksi().createStatement();
            String sql = "select id_penyewa from penyewa order by id_penyewa asc";      
            ResultSet res = stat.executeQuery(sql);                               
        
            while(res.next()){
            Object[] ob = new Object[3];
            ob[0] = res.getString(1);
            
            idpenyewa.addItem((String) ob[0]);                                     
        }
            res.close(); stat.close();
         
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void tampilComboKamar(){
        try {
            //test koneksi
            Statement stat = (Statement) koneksiDB.getKoneksi().createStatement();
            String sql = "select id_kamar from kamar order by id_kamar asc";      
            ResultSet res = stat.executeQuery(sql);                               
        
            while(res.next()){
            Object[] ob = new Object[3];
            ob[0] = res.getString(1);
            
            idkamar.addItem((String) ob[0]);                                     
        }
            res.close(); stat.close();
         
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void reset(){
        no_transaksi = "";
        tgl_bayar = "";
        id_penyewa = "--pilih--";
        nama_penyewa = "";
        id_kamar = "--pilih--";
        nama_kamar = "";
        jenis_kamar = "";
        harga_kamar = 0;
        bayar = 0;
        kekurangan = 0;
        keterangan = "";
        
        notransaksi.setText(no_transaksi);
        tgl.setDate(null);
        idpenyewa.setSelectedItem(id_penyewa);
        namapenyewa.setText(nama_penyewa);
        idkamar.setSelectedItem(id_kamar);
        namakamar.setText(nama_kamar);
        jeniskamar.setText(jenis_kamar);
        hargakamar.setText("");
        tbayar.setText("");
        tkekurangan.setText("");
        tketerangan.setText(keterangan);
    }
    
    public void simpan(){
         //panggil fungsi load data
        loadData();
        
        //uji koneksi dan eksekusi perintah
        try{
            //test koneksi
            Statement stat = (Statement) koneksiDB.getKoneksi().createStatement();
            
            //perintah sql untuk simpan data
            String  sql =   "INSERT INTO transaksi(no_transaksi, tgl_bayar, id_penyewa, nama_penyewa, id_kamar, nama_kamar, jenis_kamar, harga_kamar, bayar, kekurangan, keterangan)"
                            + "VALUES('"+ no_transaksi +"','"
                    + tgl_bayar +"','"
                    + id_penyewa +"','"
                    + nama_penyewa +"','"
                    + id_kamar +"','"
                    + nama_kamar +"','"
                    + jenis_kamar +"','"
                    + harga_kamar +"','"
                    + bayar +"','"
                    + kekurangan +"','"
                    + keterangan +"')";
            PreparedStatement p = (PreparedStatement) koneksiDB.getKoneksi().prepareStatement(sql);
            p.executeUpdate();
            
            //ambil data
            getData();
        }catch(SQLException err){
            JOptionPane.showMessageDialog(null, err.getMessage());
        }
        reset();
    }
    
    public void ubah(){
          //panggil fungsi load data
        loadData();
        tanggal();
        
       /*   SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
            tgl = format.format(jDate
            //String tanggal = String.valueOf(fm.format(tgl.getDate()));
        */
        
        //uji koneksi dan eksekusi perintah
        try{
            //test koneksi
            Statement stat = (Statement) koneksiDB.getKoneksi().createStatement();
            
            //perintah sql untuk simpan data
            String sql  =   "UPDATE transaksi SET no_transaksi = '"+ no_transaksi +"',"
                            + "tgl_bayar  = '"+ tgl_bayar +"',"
                            + "id_penyewa = '"+ id_penyewa +"',"
                            + "nama_penyewa = '"+ nama_penyewa +"',"
                            + "id_kamar = '"+ id_kamar +"',"
                            + "nama_kamar  = '"+ nama_kamar +"',"
                            + "jenis_kamar  = '"+ jenis_kamar +"',"
                            + "harga_kamar  = '"+ harga_kamar +"',"
                            + "kekurangan  = '"+ kekurangan +"',"
                            + "keterangan  = '"+ keterangan +"'"
                            + "WHERE no_transaksi = '"+ no_transaksi +"'";
            PreparedStatement p = (PreparedStatement) koneksiDB.getKoneksi().prepareStatement(sql);
            p.executeUpdate();
            
            //ambil data
            getData();
        }catch(SQLException err){
            JOptionPane.showMessageDialog(null, err.getMessage());
        }
        reset();
    }
    
    
    public void hapus(){
        //panggil fungsi ambil data
        loadData();
        
        //Beri peringatan sebelum melakukan penghapusan data
        int pesan = JOptionPane.showConfirmDialog(null, "HAPUS DATA"+ no_transaksi +"?","KONFIRMASI", JOptionPane.OK_CANCEL_OPTION);
        
        //jika pengguna memilih OK lanjutkan proses hapus data
        if(pesan == JOptionPane.OK_OPTION){
            //uji koneksi
            try{
                //buka koneksi ke database
                Statement stat = (Statement) koneksiDB.getKoneksi().createStatement();
                
                //perintah hapus data
                String sql = "DELETE FROM transaksi WHERE no_transaksi ='"+ no_transaksi +"'";
                PreparedStatement p =(PreparedStatement)koneksiDB.getKoneksi().prepareStatement(sql);
                p.executeUpdate();
                
                //fungsi ambil data
                getData();
                
                //fungsi reset data
                reset();
                JOptionPane.showMessageDialog(null, "DATA BERHASIL DIHAPUS");
            }catch(SQLException err){
                JOptionPane.showMessageDialog(null, err.getMessage());
            }
        }
    }

    public void hitung(){
        int pharga, pbayar, pkekurangan;
        pharga = Integer.parseInt(hargakamar.getText());
        pbayar = Integer.parseInt(tbayar.getText());
        
        pkekurangan = pharga-pbayar;
        //tkekurangan.setValue(pkekurangan);
        tkekurangan.setText(String.valueOf(pkekurangan));
     
        
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
        jScrollPane2 = new javax.swing.JScrollPane();
        tabel = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        notransaksi = new javax.swing.JTextField();
        namakamar = new javax.swing.JTextField();
        jeniskamar = new javax.swing.JTextField();
        hargakamar = new javax.swing.JTextField();
        tbayar = new javax.swing.JTextField();
        tkekurangan = new javax.swing.JTextField();
        tketerangan = new javax.swing.JTextField();
        tgl = new com.toedter.calendar.JDateChooser();
        idpenyewa = new javax.swing.JComboBox<>();
        idkamar = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        namapenyewa = new javax.swing.JTextField();
        simpan = new javax.swing.JButton();
        reset = new javax.swing.JButton();
        ubah = new javax.swing.JButton();
        hapus = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setIconifiable(true);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setText("TRANSAKSI PEMBAYARAN KOST");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("NO TRANSAKSI");

        tabel.setModel(new javax.swing.table.DefaultTableModel(
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
        tabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tabel);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("TANGGAL BAYAR");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("ID KAMAR");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("ID PENYEWA");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("JENIS KAMAR");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("BAYAR");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("HARGA KAMAR");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText("NAMA KAMAR");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setText("KEKURANGAN");

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setText("KETERANGAN");

        notransaksi.setPreferredSize(new java.awt.Dimension(59, 30));

        namakamar.setEditable(false);
        namakamar.setPreferredSize(new java.awt.Dimension(120, 30));

        jeniskamar.setEditable(false);
        jeniskamar.setPreferredSize(new java.awt.Dimension(120, 30));

        hargakamar.setEditable(false);
        hargakamar.setPreferredSize(new java.awt.Dimension(120, 30));

        tbayar.setPreferredSize(new java.awt.Dimension(120, 30));
        tbayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbayarActionPerformed(evt);
            }
        });

        tkekurangan.setPreferredSize(new java.awt.Dimension(120, 30));
        tkekurangan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tkekuranganActionPerformed(evt);
            }
        });

        tketerangan.setPreferredSize(new java.awt.Dimension(120, 30));

        tgl.setToolTipText("");
        tgl.setPreferredSize(new java.awt.Dimension(59, 30));
        tgl.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tglPropertyChange(evt);
            }
        });

        idpenyewa.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--pilih--" }));
        idpenyewa.setPreferredSize(new java.awt.Dimension(59, 30));
        idpenyewa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idpenyewaActionPerformed(evt);
            }
        });

        idkamar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--pilih--" }));
        idkamar.setPreferredSize(new java.awt.Dimension(59, 30));
        idkamar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idkamarActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setText("NAMA PENYEWA");

        namapenyewa.setEditable(false);
        namapenyewa.setPreferredSize(new java.awt.Dimension(59, 30));

        simpan.setText("SIMPAN");
        simpan.setPreferredSize(new java.awt.Dimension(50, 35));
        simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simpanActionPerformed(evt);
            }
        });

        reset.setText("RESET");
        reset.setPreferredSize(new java.awt.Dimension(50, 35));
        reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetActionPerformed(evt);
            }
        });

        ubah.setText("UBAH");
        ubah.setPreferredSize(new java.awt.Dimension(50, 35));
        ubah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ubahActionPerformed(evt);
            }
        });

        hapus.setText("HAPUS");
        hapus.setPreferredSize(new java.awt.Dimension(50, 35));
        hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hapusActionPerformed(evt);
            }
        });

        jButton1.setText("BAYAR");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(simpan, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
                                    .addComponent(reset, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(hapus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(ubah, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 12, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(jLabel10)
                                        .addGap(31, 31, 31)
                                        .addComponent(namapenyewa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel2)
                                            .addComponent(jLabel5)
                                            .addComponent(jLabel3))
                                        .addGap(30, 30, 30)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(tgl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(idpenyewa, 0, 120, Short.MAX_VALUE)
                                            .addComponent(notransaksi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel4)
                                                .addGap(72, 72, 72))
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jeniskamar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(namakamar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(hargakamar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(tbayar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(idkamar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(tkekurangan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(tketerangan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 927, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 490, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(notransaksi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tgl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(idpenyewa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(namapenyewa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(idkamar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(namakamar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jeniskamar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(hargakamar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tbayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tkekurangan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tketerangan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(simpan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(reset, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(ubah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(hapus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(21, 21, 21))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelMouseClicked
        // TODO add your handling code here:
        dataSelect();
    }//GEN-LAST:event_tabelMouseClicked

    private void simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simpanActionPerformed
        // TODO add your handling code here:
        simpan();
    }//GEN-LAST:event_simpanActionPerformed

    private void resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetActionPerformed
        // TODO add your handling code here:
        reset();
    }//GEN-LAST:event_resetActionPerformed

    private void ubahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ubahActionPerformed
        // TODO add your handling code here:
        ubah();
    }//GEN-LAST:event_ubahActionPerformed

    private void hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hapusActionPerformed
        // TODO add your handling code here:
        hapus();
    }//GEN-LAST:event_hapusActionPerformed

    private void idkamarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idkamarActionPerformed
        // TODO add your handling code here:
        getDataComboKamar();
    }//GEN-LAST:event_idkamarActionPerformed

    private void idpenyewaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idpenyewaActionPerformed
        // TODO add your handling code here:
        getDataComboPenyewa();
    }//GEN-LAST:event_idpenyewaActionPerformed

    private void tglPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tglPropertyChange
        // TODO add your handling code here:
       /*
                String pattern = "yyyy-MM-dd";
                SimpleDateFormat format = new SimpleDateFormat(pattern);
                dateChooser= String.valueOf(format.format(tgl.getDate()));
                  */
    }//GEN-LAST:event_tglPropertyChange

    private void tbayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbayarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbayarActionPerformed

    private void tkekuranganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tkekuranganActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tkekuranganActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        hitung();
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(frmTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmTransaksi().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton hapus;
    private javax.swing.JTextField hargakamar;
    private javax.swing.JComboBox<String> idkamar;
    private javax.swing.JComboBox<String> idpenyewa;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jeniskamar;
    private javax.swing.JTextField namakamar;
    private javax.swing.JTextField namapenyewa;
    private javax.swing.JTextField notransaksi;
    private javax.swing.JButton reset;
    private javax.swing.JButton simpan;
    private javax.swing.JTable tabel;
    private javax.swing.JTextField tbayar;
    private com.toedter.calendar.JDateChooser tgl;
    private javax.swing.JTextField tkekurangan;
    private javax.swing.JTextField tketerangan;
    private javax.swing.JButton ubah;
    // End of variables declaration//GEN-END:variables
}
