/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rental.cosplay.view.rental;

import java.awt.Color;
import rental.cosplay.view.rental.TambahRental;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.ComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import rental.cosplay.DatabaseConnection;
import rental.cosplay.model.RentalModel;
import rental.cosplay.controller.FormController;
import rental.cosplay.controller.KostumController;
import rental.cosplay.model.KostumModel;

/**
 * View for editing rental records.
 * Allows modification of rental information including customer details, costume selection, and rental dates.
 *
 * @author LENOVO LOOQ
 */
public class RentalEditView extends javax.swing.JFrame {

    DatabaseConnection dbConnection;
    RentalModel rentalModel;
    FormController formController;
    private List<KostumModel> listKostum = new ArrayList<>();
    private KostumController kostumController = new KostumController();
    ButtonGroup sizeGroup = new ButtonGroup();   // Ensures only one can be selected

    // Jangan pakai jComboBoxKostum dari designer GUI nya
    private javax.swing.JComboBox<KostumModel> comboKostumManual; // ini ngerubah dropdown nya 

    /**
     * Creates new form FormEditView.
     *
     * @param idRental the ID of the rental record to edit
     */
    public RentalEditView(String idRental) {
        initComponents();
        this.formController = new FormController();
        rentalModel = formController.getDataById(idRental);

        if (rentalModel == null) {
            JOptionPane.showMessageDialog(this, "Rental dengan ID tersebut tidak ditemukan....");
            return;
        }

        isiDataForm(rentalModel); // isi semua field di form

        setVisible(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    }

    private void isiDataForm(RentalModel rentalModel) {
        jTextFieldNama.setText(rentalModel.getNama());
        jTextFieldNomor.setText(rentalModel.getNomorTelp());
        jTextAreaAlamat.setText(rentalModel.getAlamat());

        listKostum = kostumController.getAllKostum();
        jComboBoxKostum.removeAllItems();
        String idKostumSelected = rentalModel.getIdKostum();
        int selectedIndex = -1;

        for (int i = 0; i < listKostum.size(); i++) {
            KostumModel kostum = listKostum.get(i);
            jComboBoxKostum.addItem(kostum.getNama());

            if (kostum.getId_kostum().equals(idKostumSelected)) {
                selectedIndex = i;
            }
        }

        if (selectedIndex != -1) {
            jComboBoxKostum.setSelectedIndex(selectedIndex);
        }

        jComboBox1.setSelectedItem(String.valueOf(rentalModel.getDurasiPinjam()));

        int selectedStatusIndex = -1;

        ComboBoxModel model = jComboBox2.getModel();

        for (int i = 0; i < model.getSize(); i++) {
            Object item = model.getElementAt(i);
            if (rentalModel.getStatus().equals(item)) {
                selectedStatusIndex = i;
                break;
            }
        }

        jComboBox2.setSelectedIndex(selectedStatusIndex);

//        switch (rentalModel.getUkuran()){
//            case "L" : 
//                jRadioButton1.setSelected(true);
//                break;
//            case "XL" : 
//                jRadioButton2.setSelected(true);
//                break;
//            case "XXL" : 
//                jRadioButton3.setSelected(true);
//                break;
//        }
        for (Component comp : jPanel4.getComponents()) {
            if (comp instanceof JRadioButton) {
                JRadioButton radio = (JRadioButton) comp;
                if (rentalModel.getUkuran().equals(radio.getActionCommand())) {
                    radio.setSelected(true); 
                    break;                   
                }
            }
        }

    }

    private void simpanPerubahan() {
        //ambil durasi pinjam dari combobox
        int durasi = Integer.parseInt((String) jComboBox1.getSelectedItem());

        //ngambil informasi kostum yang di pilih nya
        int selectedIndex = jComboBoxKostum.getSelectedIndex();
        if (selectedIndex < 0 || selectedIndex >= listKostum.size()) { // ini supaya kostum nya dipilih aja
            JOptionPane.showMessageDialog(this, "Silahkan pilih dengan benar..");
        }
        KostumModel selectedKostum = listKostum.get(selectedIndex);

        int harga = selectedKostum.getHarga();
        String id_Kostum = selectedKostum.getId_kostum();

        int totalBiaya = harga * durasi;

        String ukuran = sizeGroup.getSelection().getActionCommand();

//        if (jRadioButton1.isSelected()){
//            ukuran = "L";
//        } else if(jRadioButton2.isSelected()){
//            ukuran = "XL";
//        } else {
//            ukuran = "XXL";
//        }
        RentalModel update = new RentalModel();

        update.setNama(jTextFieldNama.getText());
        update.setNomorTelp(jTextFieldNomor.getText());
        update.setAlamat(jTextAreaAlamat.getText());
        update.setIdKostum(id_Kostum);
        update.setUkuran(ukuran);
        update.setDurasiPinjam(durasi);
        update.setHarga(selectedKostum.getHarga());
        update.setIdRental(rentalModel.getIdRental());
        update.setStatus(jComboBox2.getItemAt(jComboBox2.getSelectedIndex()));

        if (formController == null || update == null) {
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat menyimpan data.");
            return;
        }

        boolean berhasil = formController.UpdateData(update);
        if (berhasil) {
            JOptionPane.showMessageDialog(this, "Data Berhasil DiUpdate");
            new TambahRental().setVisible(true); // tampilkan kembali frame utama
            this.dispose(); // tutup frame edit atau lebih tepat nya form saat ini 
        } else {
            JOptionPane.showMessageDialog(this, "Gagal MengUpdate Data..");
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

        UkuranKostum = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldNama = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextFieldNomor = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextAreaAlamat = new javax.swing.JTextArea();
        jLabel8 = new javax.swing.JLabel();
        jComboBoxKostum = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        SaveData = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jPanel4 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(248, 187, 208));
        jPanel1.setPreferredSize(new java.awt.Dimension(530, 800));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(254, 235, 237));
        jPanel2.setPreferredSize(new java.awt.Dimension(490, 150));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rental/cosplay/img/LogoKecil.png"))); // NOI18N
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, 90));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel2.setText("Edit Form Rental");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 110, -1, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 15, 490, -1));

        jPanel3.setBackground(new java.awt.Color(254, 235, 237));
        jPanel3.setPreferredSize(new java.awt.Dimension(490, 600));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("Nama Lengkap");
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, -1, -1));

        jTextFieldNama.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextFieldNama.setPreferredSize(new java.awt.Dimension(430, 40));
        jPanel3.add(jTextFieldNama, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, -1, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setText("Nomor Telepon");
        jPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, -1, -1));

        jTextFieldNomor.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextFieldNomor.setPreferredSize(new java.awt.Dimension(430, 40));
        jPanel3.add(jTextFieldNomor, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, -1, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setText("Alamat");
        jPanel3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 190, -1, -1));

        jScrollPane2.setPreferredSize(new java.awt.Dimension(430, 96));

        jTextAreaAlamat.setColumns(20);
        jTextAreaAlamat.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextAreaAlamat.setRows(5);
        jScrollPane2.setViewportView(jTextAreaAlamat);

        jPanel3.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 220, -1, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel8.setText("Kostum");
        jPanel3.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 330, -1, -1));

        jComboBoxKostum.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jComboBoxKostum.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Kostum1", "Kostum2", "Kostum3" }));
        jComboBoxKostum.setPreferredSize(new java.awt.Dimension(300, 40));
        jComboBoxKostum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxKostumActionPerformed(evt);
            }
        });
        jPanel3.add(jComboBoxKostum, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 360, -1, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setText("Durasi Pinjam");
        jPanel3.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 410, -1, -1));

        jComboBox1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3" }));
        jComboBox1.setPreferredSize(new java.awt.Dimension(50, 40));
        jPanel3.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 440, -1, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel7.setText("Ukuran Kostum");
        jPanel3.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 410, -1, -1));

        SaveData.setText("Save Data");
        SaveData.setPreferredSize(new java.awt.Dimension(430, 50));
        SaveData.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SaveDataMouseClicked(evt);
            }
        });
        jPanel3.add(SaveData, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 530, -1, -1));

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Status");
        jPanel3.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 410, 160, -1));

        jComboBox2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Belum Kembali", "Sudah Kembali" }));
        jComboBox2.setPreferredSize(new java.awt.Dimension(50, 40));
        jPanel3.add(jComboBox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 440, 160, -1));

        jPanel4.setBackground(new java.awt.Color(254, 235, 237));
        jPanel4.setLayout(new javax.swing.BoxLayout(jPanel4, javax.swing.BoxLayout.LINE_AXIS));
        jPanel3.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 440, 190, 30));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void SaveDataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SaveDataMouseClicked
        // TODO add your handling code here:
        simpanPerubahan();
    }//GEN-LAST:event_SaveDataMouseClicked

    private void jComboBoxKostumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxKostumActionPerformed
        jPanel4.removeAll();
        int selectedIndex = jComboBoxKostum.getSelectedIndex() != -1 ? jComboBoxKostum.getSelectedIndex() : 0;
        System.out.println(selectedIndex);
        if (selectedIndex < 0 || selectedIndex >= listKostum.size()) { // ini supaya kostum nya dipilih aja
            JOptionPane.showMessageDialog(this, "Silahkan pilih dengan benar..");
        }
        KostumModel selectedKostum = listKostum.get(selectedIndex);

        String sizes = selectedKostum.getUkuran();
        String[] sizeArray = sizes.split(",\\s*");

        for (String size : sizeArray) {
            javax.swing.JRadioButton radioButton = new javax.swing.JRadioButton(size);
            radioButton.setActionCommand(size);       // Set value for later retrieval
            radioButton.setBackground(Color.decode("#FFF8F8"));
            sizeGroup.add(radioButton);
            jPanel4.add(radioButton);
        }

        jPanel4.revalidate();  // Refresh layout
        jPanel4.repaint();
    }//GEN-LAST:event_jComboBoxKostumActionPerformed

//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(FormEditView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(FormEditView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(FormEditView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(FormEditView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new FormEditView().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton SaveData;
    private javax.swing.ButtonGroup UkuranKostum;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBoxKostum;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextAreaAlamat;
    private javax.swing.JTextField jTextFieldNama;
    private javax.swing.JTextField jTextFieldNomor;
    // End of variables declaration//GEN-END:variables
}
