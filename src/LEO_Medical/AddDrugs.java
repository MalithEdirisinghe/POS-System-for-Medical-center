/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package LEO_Medical;

import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Malith Edirisinghe
 */
public class AddDrugs extends javax.swing.JFrame {

    /**
     * Creates new form AddDrugs
     */
    public AddDrugs() {
        initComponents();
        Connect();
        load();
        autoID();
        Calculate_tot();
    }

    Connection con;
    PreparedStatement pst;
    DefaultTableModel df;

    public void autoID() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3308/leo_medical", "root", "");
            Statement s = con.createStatement();

            ResultSet rs = s.executeQuery("select  Max(drug_id) from drugs");
            rs.next();
            rs.getString("Max(drug_id)");

            if (rs.getString("Max(drug_id)") == null) {

                id_lbl.setText("DR0001");

            } else {
                long id = Long.parseLong(rs.getString("Max(drug_id)").substring(2, rs.getString("Max(drug_id)").length()));
                id++;
                id_lbl.setText("DR" + String.format("%04d", id));
            }

        } catch (SQLException ex) {
            Logger.getLogger(AddDrugs.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AddDrugs.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void Calculate_tot() {
        // Calculate the Total
        int numrow = drug_table.getRowCount();
        double tot = 0;
        for (int i = 0; i < numrow; i++) {
            double val = 0;
            try {
                val = Double.valueOf(drug_table.getValueAt(i, 6).toString());
            } catch (NumberFormatException e) {
                // handle the exception, for example by displaying an error message
                System.err.println("Error: " + e.getMessage());
            }
            tot += val;
        }
        tot_lbl.setText("Rs." + Double.toString(tot) + "/=");
    }

    public void Connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3308/leo_medical", "root", "");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AddDrugs.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AddDrugs.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void load() {
        int a;
        try {
            pst = con.prepareStatement("select * from drugs");
            ResultSet rs = pst.executeQuery();

            ResultSetMetaData rd = rs.getMetaData();
            a = rd.getColumnCount();
            df = (DefaultTableModel) drug_table.getModel();
            df.setRowCount(0);

            while (rs.next()) {
                Vector v2 = new Vector();
                for (int i = 1; i <= a; i++) {
                    v2.add(rs.getString("id"));
                    v2.add(rs.getString("drug_id"));
                    v2.add(rs.getString("name"));
                    v2.add(rs.getString("brand"));
                    v2.add(rs.getString("price"));
                    v2.add(rs.getString("quantity"));
                    v2.add(rs.getString("total"));
                }
                df.addRow(v2);
            }

        } catch (SQLException ex) {
            Logger.getLogger(AddDrugs.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void search(String str) {
        int a;
        try {
            pst = con.prepareStatement("select * from drugs where name like ?");
            pst.setString(1, "%" + str + "%"); // set parameter value
            ResultSet rs = pst.executeQuery();
            ResultSetMetaData rd = rs.getMetaData();
            a = rd.getColumnCount();
            df = (DefaultTableModel) drug_table.getModel();
            df.setRowCount(0);
            df.fireTableDataChanged(); // notify JTable of data change

            while (rs.next()) {
                Vector v2 = new Vector();
                for (int i = 1; i <= a; i++) {
                    v2.add(rs.getString("id"));
                    v2.add(rs.getString("drug_id"));
                    v2.add(rs.getString("name"));
                    v2.add(rs.getString("brand"));
                    v2.add(rs.getString("price"));
                    v2.add(rs.getString("quantity"));
                    v2.add(rs.getString("total"));
                }
                df.addRow(v2);
            }

        } catch (SQLException ex) {
            Logger.getLogger(AddDrugs.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        id_lbl = new javax.swing.JLabel();
        name_txt = new javax.swing.JTextField();
        brand_txt = new javax.swing.JTextField();
        price_txt = new javax.swing.JTextField();
        quant_txt = new javax.swing.JTextField();
        total_pricelbl = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        drug_table = new javax.swing.JTable();
        add_btn = new javax.swing.JButton();
        update_btn = new javax.swing.JButton();
        delete_btn = new javax.swing.JButton();
        clear_btn = new javax.swing.JButton();
        back_btn = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        tot_lbl = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        search_txt = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("LEO Doctor House");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("LEO DOCTOR HOUSE MEDICAL CENTER - ADD DRUGS");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Drug ID");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Name");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("Brand");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("Price");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Quantity");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setText("Total");

        brand_txt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                brand_txtActionPerformed(evt);
            }
        });

        price_txt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                price_txtFocusLost(evt);
            }
        });

        quant_txt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                quant_txtFocusLost(evt);
            }
        });
        quant_txt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quant_txtActionPerformed(evt);
            }
        });
        quant_txt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                quant_txtKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                quant_txtKeyTyped(evt);
            }
        });

        total_pricelbl.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                total_pricelblAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        total_pricelbl.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                total_pricelblFocusGained(evt);
            }
        });
        total_pricelbl.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                total_pricelblComponentShown(evt);
            }
        });

        drug_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Drug ID", "Name", "Brand", "Price", "Quantity", "Total"
            }
        ));
        drug_table.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                drug_tableFocusGained(evt);
            }
        });
        drug_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                drug_tableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(drug_table);

        add_btn.setBackground(new java.awt.Color(255, 255, 0));
        add_btn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        add_btn.setText("ADD");
        add_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add_btnActionPerformed(evt);
            }
        });

        update_btn.setBackground(new java.awt.Color(51, 204, 255));
        update_btn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        update_btn.setText("UPDATE");
        update_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                update_btnActionPerformed(evt);
            }
        });

        delete_btn.setBackground(new java.awt.Color(255, 0, 0));
        delete_btn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        delete_btn.setText("DELETE");
        delete_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delete_btnActionPerformed(evt);
            }
        });

        clear_btn.setBackground(new java.awt.Color(255, 102, 102));
        clear_btn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        clear_btn.setText("CLEAR");
        clear_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clear_btnActionPerformed(evt);
            }
        });

        back_btn.setBackground(new java.awt.Color(153, 153, 153));
        back_btn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        back_btn.setText("BACK");
        back_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                back_btnActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("TOTAL :");

        tot_lbl.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tot_lbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Search Drugs");

        search_txt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                search_txtActionPerformed(evt);
            }
        });
        search_txt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                search_txtKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                search_txtKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 599, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(tot_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(brand_txt)
                                            .addComponent(total_pricelbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(quant_txt)
                                            .addComponent(price_txt)
                                            .addComponent(name_txt)
                                            .addComponent(id_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(delete_btn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(add_btn))
                                        .addGap(31, 31, 31)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(update_btn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(clear_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(31, 31, 31))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(back_btn)
                                        .addGap(83, 83, 83)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 513, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(349, 349, 349)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(search_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(85, 85, 85)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(37, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                    .addComponent(search_txt))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(id_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(name_txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(17, 17, 17)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(brand_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(price_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(quant_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(total_pricelbl, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(add_btn)
                            .addComponent(update_btn))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(delete_btn)
                            .addComponent(clear_btn))
                        .addGap(18, 18, 18)
                        .addComponent(back_btn))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 382, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tot_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void brand_txtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_brand_txtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_brand_txtActionPerformed

    private void quant_txtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quant_txtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_quant_txtActionPerformed

    private void total_pricelblComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_total_pricelblComponentShown
        // TODO add your handling code here:
    }//GEN-LAST:event_total_pricelblComponentShown

    private void total_pricelblFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_total_pricelblFocusGained
        // TODO add your handling code here:

    }//GEN-LAST:event_total_pricelblFocusGained

    private void back_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_back_btnActionPerformed
        // TODO add your handling code here:
        this.toBack();
        Pharmacy main = new Pharmacy();
        main.setVisible(true);
        main.toFront();
        this.dispose();
    }//GEN-LAST:event_back_btnActionPerformed

    private void add_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add_btnActionPerformed
        // TODO add your handling code here:
        String id = id_lbl.getText();
        String name = name_txt.getText();
        String brand = brand_txt.getText();
        String prices = price_txt.getText();
        String quantity = quant_txt.getText();
        String total = total_pricelbl.getText();

        if (!prices.isEmpty() && !quantity.isEmpty() && !name.isEmpty() && !brand.isEmpty()) {
            try {
                pst = con.prepareStatement("INSERT INTO `drugs`(`drug_id`, `name`, `brand`, `price`, `quantity`, `total`) VALUES (?,?,?,?,?,?)");
                pst.setString(1, id);
                pst.setString(2, name);
                pst.setString(3, brand);
                pst.setString(4, prices);
                pst.setString(5, quantity);
                pst.setString(6, total);
                pst.executeUpdate();

                JOptionPane.showMessageDialog(this, "Drug Added");

                id_lbl.setText("");
                name_txt.setText("");
                brand_txt.setText("");
                price_txt.setText("");
                quant_txt.setText("");
                total_pricelbl.setText("");
                autoID();
                id_lbl.requestFocus();
                load();
                this.getContentPane().repaint();
                //add_btn.setEnabled(false);
                Calculate_tot();

            } catch (SQLException ex) {
                Logger.getLogger(AddDrugs.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please Enter the all value");
        }


    }//GEN-LAST:event_add_btnActionPerformed

    private void quant_txtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_quant_txtKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_quant_txtKeyTyped

    private void quant_txtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_quant_txtKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_quant_txtKeyPressed

    private void total_pricelblAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_total_pricelblAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_total_pricelblAncestorAdded

    private void quant_txtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_quant_txtFocusLost
        // TODO add your handling code here:
        String prices = price_txt.getText();
        String quantity = quant_txt.getText();

        try {
            float i = Float.parseFloat(prices);
            float j = Float.parseFloat(quantity);

            float tot = i * j;

            total_pricelbl.setText(Float.toString(tot));

            System.out.println("total" + tot);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid Input");
        }
    }//GEN-LAST:event_quant_txtFocusLost

    private void update_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_update_btnActionPerformed
        // TODO add your handling code here:
        df = (DefaultTableModel) drug_table.getModel();
        int selected = drug_table.getSelectedRow();

        int id = Integer.parseInt(df.getValueAt(selected, 0).toString());
        String did = id_lbl.getText();
        String name = name_txt.getText();
        String brand = brand_txt.getText();
        String prices = price_txt.getText();
        String quantity = quant_txt.getText();
        String total = total_pricelbl.getText();

        try {
            pst = con.prepareStatement("UPDATE `drugs` SET `drug_id`=?,`name`=?,`brand`=?,`price`=?,`quantity`=?,`total`=? WHERE id =?");

            pst.setString(1, did);
            pst.setString(2, name);
            pst.setString(3, brand);
            pst.setString(4, prices);
            pst.setString(5, quantity);
            pst.setString(6, total);
            pst.setInt(7, id);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Drug Updated  Sucessfully");

            id_lbl.setText("");
            name_txt.setText("");
            brand_txt.setText("");
            price_txt.setText("");
            quant_txt.setText("");
            total_pricelbl.setText("");
            total_pricelbl.requestFocus();
            load();
            Calculate_tot();

        } catch (SQLException ex) {
            Logger.getLogger(AddDrugs.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_update_btnActionPerformed

    private void drug_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_drug_tableMouseClicked
        if (search_txt.getText().isEmpty()) {
            df = (DefaultTableModel) drug_table.getModel();
            int selected = drug_table.getSelectedRow();

            int id = Integer.parseInt(df.getValueAt(selected, 0).toString());
            id_lbl.setText(df.getValueAt(selected, 1).toString());
            name_txt.setText(df.getValueAt(selected, 2).toString());
            brand_txt.setText(df.getValueAt(selected, 3).toString());
            price_txt.setText(df.getValueAt(selected, 4).toString());
            quant_txt.setText(df.getValueAt(selected, 5).toString());
            total_pricelbl.setText(df.getValueAt(selected, 6).toString());

            add_btn.setEnabled(false);

            // add a print statement to display the values in the text fields
            System.out.println(id_lbl.getText() + " " + name_txt.getText() + " " + brand_txt.getText() + " " + price_txt.getText() + " " + quant_txt.getText() + " " + total_pricelbl.getText());
            // TODO add your handling code here:
        } else {
            df = (DefaultTableModel) drug_table.getModel();
            int selected = drug_table.getSelectedRow();
//            int id = Integer.parseInt(df.getValueAt(selected, 0).toString());
            id_lbl.setText(df.getValueAt(selected, 1).toString());
            name_txt.setText(df.getValueAt(selected, 2).toString());
            brand_txt.setText(df.getValueAt(selected, 3).toString());
            price_txt.setText(df.getValueAt(selected, 4).toString());
            quant_txt.setText(df.getValueAt(selected, 5).toString());
            total_pricelbl.setText(df.getValueAt(selected, 6).toString());

            add_btn.setEnabled(false);

            // add a print statement to display the values in the text fields
            System.out.println(id_lbl.getText() + " " + name_txt.getText() + " " + brand_txt.getText() + " " + price_txt.getText() + " " + quant_txt.getText() + " " + total_pricelbl.getText());
            // TODO add your handling code here:
        }

    }//GEN-LAST:event_drug_tableMouseClicked

    private void clear_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clear_btnActionPerformed
        // TODO add your handling code here:
        id_lbl.setText("");
        name_txt.setText("");
        brand_txt.setText("");
        price_txt.setText("");
        quant_txt.setText("");
        total_pricelbl.setText("");
        autoID();
        id_lbl.requestFocus();
        add_btn.setEnabled(true);
    }//GEN-LAST:event_clear_btnActionPerformed

    private void delete_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delete_btnActionPerformed
        // TODO add your handling code here:
        df = (DefaultTableModel) drug_table.getModel();
        int selected = drug_table.getSelectedRow();

        int id = Integer.parseInt(df.getValueAt(selected, 0).toString());

        try {
            pst = con.prepareStatement("delete from drugs where id = ?");

            pst.setInt(1, id);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Drug deleted  Sucessfully");

            id_lbl.setText("");
            name_txt.setText("");
            brand_txt.setText("");
            price_txt.setText("");
            quant_txt.setText("");
            total_pricelbl.requestFocus();
            load();
            add_btn.setEnabled(false);
            Calculate_tot();

        } catch (SQLException ex) {
            Logger.getLogger(AddDrugs.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_delete_btnActionPerformed

    private void drug_tableFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_drug_tableFocusGained
        // TODO add your handling code here:
        // Calculate the Total
        Calculate_tot();
    }//GEN-LAST:event_drug_tableFocusGained

    private void price_txtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_price_txtFocusLost
        // TODO add your handling code here:
        String prices = price_txt.getText();
        String quantity = quant_txt.getText();

        try {
            float i = Float.parseFloat(prices);
            float j = Float.parseFloat(quantity);

            float tot = i * j;

            total_pricelbl.setText(Float.toString(tot));

            System.out.println("total" + tot);
        } catch (NumberFormatException ex) {
            //JOptionPane.showMessageDialog(this, "Invalid Input");
        }
    }//GEN-LAST:event_price_txtFocusLost

    private void search_txtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_search_txtKeyReleased
        // TODO add your handling code here:
        String searchString = search_txt.getText();
        search(searchString);
    }//GEN-LAST:event_search_txtKeyReleased

    private void search_txtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_search_txtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_search_txtActionPerformed

    private void search_txtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_search_txtKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_search_txtKeyPressed

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
            java.util.logging.Logger.getLogger(AddDrugs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddDrugs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddDrugs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddDrugs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AddDrugs().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton add_btn;
    private javax.swing.JButton back_btn;
    private javax.swing.JTextField brand_txt;
    private javax.swing.JButton clear_btn;
    private javax.swing.JButton delete_btn;
    private javax.swing.JTable drug_table;
    private javax.swing.JLabel id_lbl;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField name_txt;
    private javax.swing.JTextField price_txt;
    private javax.swing.JTextField quant_txt;
    private javax.swing.JTextField search_txt;
    private javax.swing.JLabel tot_lbl;
    private javax.swing.JLabel total_pricelbl;
    private javax.swing.JButton update_btn;
    // End of variables declaration//GEN-END:variables
}
