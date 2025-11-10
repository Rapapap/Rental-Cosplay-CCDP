/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rental.cosplay.view.kostum;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import rental.cosplay.model.KostumModel;
import rental.cosplay.controller.KostumController;
import rental.cosplay.view.kostum.EditKostumView;
import rental.cosplay.components.CustomTextField;
import rental.cosplay.components.RoundedPanel;
import rental.cosplay.components.ScrollBarCustom;
import rental.cosplay.components.ShadowPanel;
/**
 * Panel katalog kostum yang menampilkan daftar kostum dan fitur pencarian,
 * refresh, dan tambah data.
 *
 * <p>Komponen ini mengatur tampilan daftar kostum dalam sebuah panel scroll,
 * serta menyediakan interaksi untuk melihat detail dan mengedit data kostum.</p>
 *
 * @author FX517ZC
 */
public class KostumPanel extends javax.swing.JPanel {

    TambahKostumView tambahKostumView;
    EditKostumView editKostumView;
    
    List<KostumModel> kostumList;
    KostumController kostumController;
    
    /**
     * Membuat panel katalog kostum baru dan mempersiapkan event handler serta
     * memuat data awal dari controller.
     */
    public KostumPanel() {
        initComponents();
        tambahKostumView = new TambahKostumView();

        ScrollBarCustom sb = new ScrollBarCustom();
        jScrollPane1.setVerticalScrollBar(sb);

        kostumController = new KostumController();

        loadDataKostum();

        setupHoverEffect(searchButton, Color.decode("#508D4E"), Color.BLACK);
        setupHoverEffect(refreshButton, Color.decode("#D9D9D9"), Color.BLACK);
        setupHoverEffect(addButton, Color.decode("#D9D9D9"), Color.BLACK);
    }

    @SuppressWarnings("unchecked")
    

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jHeaderCatalog = new javax.swing.JPanel();
        jLabelKatalog = new javax.swing.JLabel();
        jSearchKatalog = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        searchButton = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        refreshButton = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        addButton = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanelCatalog = new javax.swing.JPanel();

        setBackground(new java.awt.Color(254, 235, 237));
        setMinimumSize(new java.awt.Dimension(1040, 720));
        setPreferredSize(new java.awt.Dimension(1040, 720));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jHeaderCatalog.setBackground(new java.awt.Color(255, 248, 248));
        jHeaderCatalog.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jHeaderCatalog.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabelKatalog.setFont(new java.awt.Font("Tw Cen MT", 0, 20)); // NOI18N
        jLabelKatalog.setText("Catalog Kostum");
        jHeaderCatalog.add(jLabelKatalog, new org.netbeans.lib.awtextra.AbsoluteConstraints(105, 10, 310, 30));

        jSearchKatalog.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jSearchKatalog.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jSearchKatalogKeyTyped(evt);
            }
        });
        jHeaderCatalog.add(jSearchKatalog, new org.netbeans.lib.awtextra.AbsoluteConstraints(618, 34, 221, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rental/cosplay/img/LogoKecil.png"))); // NOI18N
        jHeaderCatalog.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 2, 255, 106));

        searchButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                searchButtonMouseClicked(evt);
            }
        });
        searchButton.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rental/cosplay/view/kostum/search_icon.png"))); // NOI18N
        searchButton.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 30, 30));

        jHeaderCatalog.add(searchButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(844, 32, 30, 30));

        refreshButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                refreshButtonMouseClicked(evt);
            }
        });
        refreshButton.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rental/cosplay/view/kostum/refresh_icon.png"))); // NOI18N
        refreshButton.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 30, 30));

        jHeaderCatalog.add(refreshButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 32, 30, 30));

        addButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addButtonMouseClicked(evt);
            }
        });
        addButton.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rental/cosplay/view/kostum/add_icon.png"))); // NOI18N
        addButton.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 30, 30));

        jHeaderCatalog.add(addButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(914, 32, 30, 30));

        add(jHeaderCatalog, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, 950, 110));

        jPanelCatalog.setBackground(new java.awt.Color(255, 255, 255));
        jPanelCatalog.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanelCatalog.setPreferredSize(new java.awt.Dimension(948, 1000));

        javax.swing.GroupLayout jPanelCatalogLayout = new javax.swing.GroupLayout(jPanelCatalog);
        jPanelCatalog.setLayout(jPanelCatalogLayout);
        jPanelCatalogLayout.setHorizontalGroup(
            jPanelCatalogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 944, Short.MAX_VALUE)
        );
        jPanelCatalogLayout.setVerticalGroup(
            jPanelCatalogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 996, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(jPanelCatalog);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 160, 950, 530));
    }// </editor-fold>//GEN-END:initComponents

    private void jSearchKatalogKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jSearchKatalogKeyTyped
        performSearch();
    }//GEN-LAST:event_jSearchKatalogKeyTyped

    private void searchButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchButtonMouseClicked
        performSearch();
    }//GEN-LAST:event_searchButtonMouseClicked

    private void refreshButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_refreshButtonMouseClicked
        loadDataKostum();
        displayKostum(kostumList);
    }//GEN-LAST:event_refreshButtonMouseClicked

    private void addButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addButtonMouseClicked
        tambahKostumView.setVisible(true);
    }//GEN-LAST:event_addButtonMouseClicked

    private void performSearch() {
        String searchText = jSearchKatalog.getText().trim().toLowerCase();

        if (searchText.isEmpty()) {
            loadDataKostum();
            displayKostum(kostumList);
        } else {
            List<KostumModel> filteredPatients = kostumList.stream()
                    .filter(o -> o.getNama().toLowerCase().contains(searchText)
                    || o.getId_kostum().toLowerCase().contains(searchText)
                    || o.getUkuran().toLowerCase().contains(searchText))
                    .collect(Collectors.toList());

            if (filteredPatients.isEmpty()) {
                displayNoDataFound();
            } else {
                displayKostum(filteredPatients);
            }
        }
    }
    
    public void loadDataKostum() {
        this.kostumList = kostumController.getAllKostum();
        if (kostumList == null || kostumList.isEmpty()) {
            emptyDataDisplay();
        } else {
            displayKostum(kostumList);
        }
    }
    
    private void displayNoDataFound() {
        jPanelCatalog.removeAll();

        JLabel noDataLabel = new JLabel("Maaf, " + "`" + jSearchKatalog.getText().trim() + "`" + " tidak ditemukan.");
        


        noDataLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jPanelCatalog.setLayout(null);  // Set layout to null for AbsoluteLayout
        jPanelCatalog.setBounds(0, 0, 1040, 400);

        int imageWidth = 128;  // Adjust this to the actual width of your image
        int imageHeight = 128; // Adjust this to the actual height of your image
        int labelHeight = 30;  // Height of the label
        int panelWidth = jPanelCatalog.getWidth();
        int panelHeight = jPanelCatalog.getHeight();

        int imageX = (panelWidth - imageWidth) / 2;
        int imageY = (panelHeight - imageHeight - labelHeight) / 2;
        int labelX = (panelWidth - noDataLabel.getPreferredSize().width) / 2;
        int labelY = imageY + imageHeight + 10;

        
        noDataLabel.setBounds(labelX, labelY, noDataLabel.getPreferredSize().width, labelHeight);

        jPanelCatalog.add(noDataLabel);

        jPanelCatalog.revalidate();
        jPanelCatalog.repaint();
    }
    
    private void emptyDataDisplay() {
        jPanelCatalog.removeAll();

        JLabel noDataLabel = new JLabel("Tidak Ada Data Kostum");



        noDataLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jPanelCatalog.setLayout(null);
        jPanelCatalog.setBounds(0, 0, 1040, 400);

        int imageWidth = 128;
        int imageHeight = 128;
        int labelHeight = 30;
        int panelWidth = jPanelCatalog.getWidth();
        int panelHeight = jPanelCatalog.getHeight();

        int imageX = (panelWidth - imageWidth) / 2;
        int imageY = (panelHeight - imageHeight - labelHeight) / 2;
        int labelX = (panelWidth - noDataLabel.getPreferredSize().width) / 2;
        int labelY = imageY + imageHeight + 10;

        noDataLabel.setBounds(labelX, labelY, noDataLabel.getPreferredSize().width, labelHeight);

        jPanelCatalog.add(noDataLabel);

        jPanelCatalog.revalidate();
        jPanelCatalog.repaint();
    }
    
    private void displayKostum(List<KostumModel> costumes) {
        jPanelCatalog.removeAll();
        jPanelCatalog.repaint();
        jPanelCatalog.revalidate();

        if (costumes == null || costumes.isEmpty()) {
            emptyDataDisplay();
        } else {
            jPanelCatalog.setLayout(null);

            int x = 20, y = 20;
            int cardWidth = 285, cardHeight = 420;
            int padding = 20;
            int cardsPerRow = 3;

            for (int i = 0; i < costumes.size(); i++) {
                KostumModel kostum = costumes.get(i);
                ShadowPanel cardKostum = new ShadowPanel(8, Color.decode("#DFDFDF"));
                cardKostum.setBackground(Color.WHITE);
                cardKostum.setBounds(x, y, cardWidth, cardHeight);

                JLabel labelId = new JLabel("ID: " + kostum.getId_kostum());
                JLabel titleNama = new JLabel("Nama Kostum");
                JLabel labelNama = new JLabel(kostum.getNama());
                JLabel titleUkuran = new JLabel("Ukuran");
                JLabel labelUkuran = new JLabel(kostum.getUkuran());
                JLabel titleHarga = new JLabel("Harga");
                JLabel labelHarga = new JLabel("Rp " + String.valueOf(kostum.getHarga()));
                JLabel titleDeskripsi = new JLabel("Deskripsi");
                JTextArea labelDeskripsi = new JTextArea(kostum.getDeskripsi());

                labelDeskripsi.setEditable(false);
                labelDeskripsi.setRows(4);
                labelDeskripsi.setColumns(20);
                labelDeskripsi.setLineWrap(true);
                
                RoundedPanel buttonEdit = new RoundedPanel(12, Color.white);
                JLabel editLabel = new JLabel("Edit Kostum");
                editLabel.setForeground(Color.WHITE);
                buttonEdit.add(editLabel);
                buttonEdit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

                RoundedPanel buttonHapus = new RoundedPanel(12, Color.white);
                JLabel hapusLabel = new JLabel("Hapus Kostum");
                hapusLabel.setForeground(Color.RED); // Set text color
                buttonHapus.add(hapusLabel);
                buttonHapus.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

                setupHoverEffect(buttonEdit, Color.decode("#508D4E"), Color.BLACK);
                setupHoverEffect(buttonHapus, Color.WHITE, Color.RED);

                buttonEdit.addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        editKostumView = new EditKostumView(kostum);
                        editKostumView.setVisible(true);
                    }
                });

                buttonHapus.addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseEntered(java.awt.event.MouseEvent evt) {
                        hapusLabel.setForeground(Color.WHITE);
                    }

                    @Override
                    public void mouseExited(java.awt.event.MouseEvent evt) {
                        hapusLabel.setForeground(Color.RED);
                    }

                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        int response = JOptionPane.showConfirmDialog(null, "Anda yakin ingin menghapus kostum " + kostum.getNama(), "Konfirmasi", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                        if (response == JOptionPane.YES_OPTION) {
                            kostumController.hapusKostum(kostum.getId_kostum());

                            loadDataKostum();
                        }
                    }
                });

                buttonHapus.setPreferredSize(new java.awt.Dimension(240, 36));
                buttonEdit.setPreferredSize(new java.awt.Dimension(240, 36));

                cardKostum.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
                cardKostum.add(labelId, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));
                cardKostum.add(titleNama, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, -1, -1));
                cardKostum.add(labelNama, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, -1));
                cardKostum.add(titleUkuran, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, -1, -1));
                cardKostum.add(labelUkuran, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, -1, -1));
                cardKostum.add(titleHarga, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, -1, -1));
                cardKostum.add(labelHarga, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, -1, -1));
                cardKostum.add(titleDeskripsi, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, -1, -1));
                cardKostum.add(labelDeskripsi, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, -1, -1));
                cardKostum.add(buttonEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 320, -1, -1));
                cardKostum.add(buttonHapus, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 360, -1, -1));

                jPanelCatalog.add(cardKostum);

                if ((i + 1) % cardsPerRow == 0) {
                    x = 20;
                    y += cardHeight + padding;
                } else {
                    x += cardWidth + padding;
                }
            }

            jPanelCatalog.setPreferredSize(new Dimension(jPanelCatalog.getWidth(), y + cardHeight + padding));
            jPanelCatalog.revalidate();
            jPanelCatalog.repaint();
        }
    }
    
    private void setupHoverEffect(JPanel button, Color defaultColor, Color hoverColor) {
        button.setBackground(defaultColor);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(defaultColor);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel addButton;
    private javax.swing.JPanel jHeaderCatalog;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabelKatalog;
    private javax.swing.JPanel jPanelCatalog;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jSearchKatalog;
    private javax.swing.JPanel refreshButton;
    private javax.swing.JPanel searchButton;
    // End of variables declaration//GEN-END:variables
}
