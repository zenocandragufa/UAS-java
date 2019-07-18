/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author zenocandragufa
 */

import Koneksi.koneksiDB;
import java.sql.*;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class frmKamar extends javax.swing.JInternalFrame {

    //membuat objek    
    private DefaultTableModel model;
    
    //deklarasi variabel
    String nama_kamar, id_jenis, jenis_kamar;
    int id_kamar, harga_kamar;
    /**
     * Creates new form frmKamar
     */
    public frmKamar() {
        initComponents();
        
        //membuat obyek
        model = new DefaultTableModel();
        
        //memberi nama header pada tabel
        tabel.setModel(model);
        model.addColumn("ID KAMAR");
        model.addColumn("NAMA KAMAR");
        model.addColumn("JENIS KAMAR");
        model.addColumn("HARGA KAMAR");
                
        //fungsi ambil data
        getData();
        tampilCombo();
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
            String sql = "SELECT * FROM kamar";
            ResultSet res = stat.executeQuery(sql);
            
            //baca data
            while(res.next()){
                //membuat obyek berjenis array
                Object[] obj = new Object[4];
                obj[0]=res.getString("id_kamar");
                obj[1]=res.getString("nama_kamar");
                obj[2]=res.getString("jenis_kamar");
                obj[3]=res.getString("harga_kamar");
                model.addRow(obj);
            }
        }catch(SQLException err){
           JOptionPane.showMessageDialog(null, err.getMessage());
        }
    }
    
    public void getDataCombo(){
        //eksekusi koneksi dan kirimkan query ke database
        try{
            //tes koneksi
            Statement stat = (Statement) koneksiDB.getKoneksi().createStatement();
            
            //perintah sql untuk membaca data dari tabel gaji        
            String sql = "SELECT * FROM jenis WHERE id_jenis = '"+ /*Integer.toString((int)*/ cojenis.getSelectedItem() +"'";
            ResultSet res = stat.executeQuery(sql);
            
            //baca data
            while(res.next()){
                //membuat obyek berjenis array
                jenis.setText(res.getString("jenis_kamar"));
                harga.setText(res.getString("harga_kamar"));
            }
        }catch(SQLException err){
           JOptionPane.showMessageDialog(null, err.getMessage());
        }
    }
    
    public void loadData(){
        //mengambil data dari textbox dan menyimpan nilainya pada variabel
        id_kamar = Integer.parseInt(id.getText());
        nama_kamar = nama.getText();
        id_jenis = (String) cojenis.getSelectedItem();
        jenis_kamar = jenis.getText();
        harga_kamar = Integer.parseInt(harga.getText());
    }
    
    public void dataSelect(){
        //deklarasi variabel
        int i = tabel.getSelectedRow();
        
        //uji adakah data di tabel?
        if(i == -1){
            //tidak ada yang terpilih atau dipilih.
            return;
        }
        id.setText(""+model.getValueAt(i,0));
        nama.setText(""+model.getValueAt(i,1));
        //cojenis.setSelectedItem(""+model.getValueAt(i,2));
        jenis.setText(""+model.getValueAt(i,2));
        harga.setText(""+model.getValueAt(i,3));
    }
    
    public void tampilCombo(){
        try {
            //test koneksi
            Statement stat = (Statement) koneksiDB.getKoneksi().createStatement();
            String sql = "select id_jenis from jenis order by id_jenis asc";      
            ResultSet res = stat.executeQuery(sql);                               
        
            while(res.next()){
            Object[] ob = new Object[3];
            ob[0] = res.getString(1);
            
            cojenis.addItem((String) ob[0]);                                     
        }
            res.close(); stat.close();
         
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void reset(){
        id_kamar = 0;
        nama_kamar = "";
        jenis_kamar = "";
        harga_kamar = 0;
        
        id.setText("");
        nama.setText(nama_kamar);
        cojenis.setSelectedItem("- Pilih -");
        harga.setText("");
    }
    
    public void simpan(){
         //panggil fungsi load data
        loadData();
        
        //uji koneksi dan eksekusi perintah
        try{
            //test koneksi
            Statement stat = (Statement) koneksiDB.getKoneksi().createStatement();
            
            //perintah sql untuk simpan data
            String  sql =   "INSERT INTO kamar(id_kamar, nama_kamar, jenis_kamar, harga_kamar)"
                            + "VALUES('"+ id_kamar +"','"+ nama_kamar +"','"+ jenis_kamar +"','"+ harga_kamar +"')";
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
        
        //uji koneksi dan eksekusi perintah
        try{
            //test koneksi
            Statement stat = (Statement) koneksiDB.getKoneksi().createStatement();
            
            //perintah sql untuk simpan data
            String sql  =   "UPDATE kamar SET id_kamar = '"+ id_kamar +"',"
                            + "nama_kamar  = '"+ nama_kamar +"',"
                            + "jenis_kamar  = '"+ jenis_kamar +"',"
                            + "harga_kamar  = '"+ harga_kamar +"'"
                            + "WHERE id_kamar  = '"+ id_kamar +"'";
            PreparedStatement p = (PreparedStatement) koneksiDB.getKoneksi().prepareStatement(sql);
            p.executeUpdate();
            
            //ambil data
            getData();
        }catch(SQLException err){
            JOptionPane.showMessageDialog(null, err.getMessage());
        }
    }
    
    public void hapus(){
        //panggil fungsi ambil data
        loadData(); 
        
        //Beri peringatan sebelum melakukan penghapusan data
        int pesan = JOptionPane.showConfirmDialog(null, "HAPUS DATA"+ id_kamar +"?","KONFIRMASI", JOptionPane.OK_CANCEL_OPTION);
        
        //jika pengguna memilih OK lanjutkan proses hapus data
        if(pesan == JOptionPane.OK_OPTION){
            //uji koneksi
            try{
                //buka koneksi ke database
                Statement stat = (Statement) koneksiDB.getKoneksi().createStatement();
                
                //perintah hapus data
                String sql = "DELETE FROM kamar WHERE id_kamar='"+ id_kamar +"'";
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        id = new javax.swing.JTextField();
        nama = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabel = new javax.swing.JTable();
        simpan = new javax.swing.JButton();
        reset = new javax.swing.JButton();
        ubah = new javax.swing.JButton();
        hapus = new javax.swing.JButton();
        cojenis = new javax.swing.JComboBox<>();
        jenis = new javax.swing.JTextField();
        harga = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTable1);

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setIconifiable(true);
        setTitle("Data Kamar Kost");
        setToolTipText("");
        setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setText("DATA KAMAR KOST");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("ID KAMAR");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("NAMA KAMAR");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("HARGA KAMAR");

        id.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        nama.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

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

        simpan.setText("SIMPAN");
        simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simpanActionPerformed(evt);
            }
        });

        reset.setText("RESET");
        reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetActionPerformed(evt);
            }
        });

        ubah.setText("UBAH");
        ubah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ubahActionPerformed(evt);
            }
        });

        hapus.setText("HAPUS");
        hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hapusActionPerformed(evt);
            }
        });

        cojenis.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "- Pilih -" }));
        cojenis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cojenisActionPerformed(evt);
            }
        });

        jenis.setEditable(false);

        harga.setEditable(false);

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("JENIS KAMAR");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel2)
                                .addComponent(jLabel4)
                                .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING))
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(harga)
                            .addComponent(nama, javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(id, javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(cojenis, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jenis))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(hapus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ubah, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(reset, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(simpan, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addComponent(simpan)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(reset)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ubah)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(hapus))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)
                        .addGap(11, 11, 11)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(nama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cojenis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jenis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(harga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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

    private void tabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelMouseClicked
        // TODO add your handling code here:
        dataSelect();
    }//GEN-LAST:event_tabelMouseClicked

    private void cojenisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cojenisActionPerformed
        // TODO add your handling code here:
        getDataCombo();
    }//GEN-LAST:event_cojenisActionPerformed

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
            java.util.logging.Logger.getLogger(frmKamar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmKamar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmKamar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmKamar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmKamar().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cojenis;
    private javax.swing.JButton hapus;
    private javax.swing.JTextField harga;
    private javax.swing.JTextField id;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jenis;
    private javax.swing.JTextField nama;
    private javax.swing.JButton reset;
    private javax.swing.JButton simpan;
    private javax.swing.JTable tabel;
    private javax.swing.JButton ubah;
    // End of variables declaration//GEN-END:variables
}
