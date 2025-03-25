/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.procot.View;

import com.procot.Admin.adminForm;
import com.procot.Model.accountModel;
import com.formdev.flatlaf.FlatClientProperties;
import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.prefs.Preferences;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

/**
 *
 * @author anh74
 */
public class LoginForm extends javax.swing.JPanel {

    /**
     * Creates new form LoginForm
     */
    private Preferences prefs;
    public LoginForm() {
        initComponents();
    prefs = Preferences.userRoot().node(this.getClass().getName());
    txtpassword.putClientProperty(FlatClientProperties.STYLE, "showRevealButton:true");
    txtusername.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Username");
        txtpassword.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Password");
        txtusername.setText(prefs.get("username", ""));
        txtpassword.setText(prefs.get("password", ""));
        chkremember.setSelected(!txtusername.getText().isEmpty());
        this.putClientProperty(FlatClientProperties.STYLE, ""
                + "arc:20;"
                + "[light]background:darken(@background,3%);"
                + "[dark]background:lighten(@background,3%)");
        txtpassword.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    btnlogin.doClick(); // Giả lập click vào nút Login
                }
            }
        });
        txtusername.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    btnlogin.doClick(); // Giả lập click vào nút Login
                }
            }
        });
        
            
        
    }
    
    
    public void login() {
    String username = txtusername.getText();
    String password = new String(txtpassword.getPassword());
    accountModel am = com.procot.DAO.accountDAO.getInstance().checkLogin(username, password);
    
    if (am != null) {
        if (chkremember.isSelected()) {
            prefs.put("username", username);
            prefs.put("password", password);
        } else {
            prefs.remove("username");
            prefs.remove("password");
        }

        int roleID = am.getRoleID(); // Lấy roleID từ model

        if (roleID == 3) { // 1: Sinh viên (SV)
            int studentId = am.getStudentID();
            String accountName = am.getFullnamee();
            //userForm user = new userForm(accountName, studentId);
            //user.setVisible(true);
            javax.swing.SwingUtilities.getWindowAncestor(this).dispose();

        } else if (roleID == 2) { // 2: Giảng viên (GV)
            int lecturerId = am.getLecturerID();
            String accountName = am.getFullnamee();
            //staffForm staff = new staffForm(accountName, lecturerId);
            //staff.setVisible(true);
            javax.swing.SwingUtilities.getWindowAncestor(this).dispose();

        } else if (roleID == 1) { // 3: Admin (AD)
            String accountName = am.getFullnamee();
            adminForm admin = new adminForm(accountName);
            admin.setVisible(true);
            javax.swing.SwingUtilities.getWindowAncestor(this).dispose();
        }

    } else {
        lblPasswordError.setText("Sai tài khoản hoặc mật khẩu. Vui lòng đăng nhập lại");
        lblPasswordError.setForeground(Color.red);
        txtusername.putClientProperty("JComponent.outline", "error");
        txtpassword.putClientProperty("JComponent.outline", "error");
    }
}
    
    private boolean isValidInput() {
        if (txtusername.getText().isEmpty()) {
            txtusername.putClientProperty("JComponent.outline", "warning");
            lblUsernameError.setText("Vui lòng điền thông tin tên đăng nhập!");
            lblUsernameError.setForeground(Color.ORANGE);
            return false;
        } else {
            txtusername.putClientProperty("JComponent.outline", "null");
            lblUsernameError.setText("");
        }
        if (new String(txtpassword.getPassword()).isEmpty()) {
            txtpassword.putClientProperty("JComponent.outline", "warning");
            lblPasswordError.setText("Vui lòng điền mật khẩu!");
            lblPasswordError.setForeground(Color.ORANGE);
            return false;
        } else {
            txtpassword.putClientProperty("JComponent.outline", "null");
            lblPasswordError.setText("");
        }
        return true;
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
        txtusername = new javax.swing.JTextField();
        btnlogin = new javax.swing.JButton();
        chkremember = new javax.swing.JCheckBox();
        txtpassword = new javax.swing.JPasswordField();
        lblPasswordError = new javax.swing.JLabel();
        lblUsernameError = new javax.swing.JLabel();
        lblForgot = new javax.swing.JLabel();

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel1.setText("Login");

        txtusername.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        txtusername.setText("\n\n");
        txtusername.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(102, 102, 102)));

        btnlogin.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnlogin.setText("Login");
        btnlogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnloginActionPerformed(evt);
            }
        });

        chkremember.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        chkremember.setText("Remember Me");
        chkremember.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkrememberActionPerformed(evt);
            }
        });

        txtpassword.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        txtpassword.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(102, 102, 102)));
        txtpassword.setPreferredSize(new java.awt.Dimension(64, 17));

        lblForgot.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblForgot.setForeground(javax.swing.UIManager.getDefaults().getColor("Button.default.background"));
        lblForgot.setText("<html><u>Forgot Password</u></html>");
        lblForgot.setToolTipText("");
        lblForgot.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblForgot.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblForgotMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(chkremember, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblForgot, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnlogin, javax.swing.GroupLayout.DEFAULT_SIZE, 377, Short.MAX_VALUE)
                            .addComponent(txtusername)
                            .addComponent(txtpassword, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblPasswordError, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblUsernameError, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 15, Short.MAX_VALUE))))
            .addGroup(layout.createSequentialGroup()
                .addGap(157, 157, 157)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addComponent(txtusername, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblUsernameError)
                .addGap(32, 32, 32)
                .addComponent(txtpassword, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblPasswordError)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 66, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chkremember)
                    .addComponent(lblForgot, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnlogin, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnloginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnloginActionPerformed
        // TODO add your handling code here:
        
        if (isValidInput()) {
            login();
        }
    }//GEN-LAST:event_btnloginActionPerformed

    private void chkrememberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkrememberActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkrememberActionPerformed

    private void lblForgotMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblForgotMouseClicked
        // TODO add your handling code here:
        main.getInstance().forgotPasswordView();
    }//GEN-LAST:event_lblForgotMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnlogin;
    private javax.swing.JCheckBox chkremember;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lblForgot;
    private javax.swing.JLabel lblPasswordError;
    private javax.swing.JLabel lblUsernameError;
    private javax.swing.JPasswordField txtpassword;
    private javax.swing.JTextField txtusername;
    // End of variables declaration//GEN-END:variables
}
