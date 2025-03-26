/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.procot.View;

import com.procot.View.accountForm;
import com.procot.DAO.accountDAO;
import com.procot.DAO.roleDAO;
import com.procot.Model.accountModel;
import com.procot.Model.roleModel;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import raven.modal.ModalDialog;
import raven.modal.Toast;

/**
 *
 * @author skmyg
 */
public class accountInsertDialog extends javax.swing.JPanel{

    /**
     * Creates new form classInsertDialog
     */
    accountForm panel;
    

    public accountInsertDialog(accountForm panel) {
        initComponents();
        this.panel = panel;
        loadRoleToComboBox();

    }
    public int getRoleIDByName(String roleName, List<roleModel> roleList) {
    for (roleModel role : roleList) {
        if (role.getRoleName().equals(roleName)) {
            return role.getRoleID();
        }
    }
    return -1; // Trả về -1 nếu không tìm thấy
}
    
   
    public void loadRoleToComboBox() {
    List<String> roles = roleDAO.getInstance().getAllRoleNames(); // Lấy danh sách role từ DB
    cbxRole.removeAllItems(); // Xóa các mục cũ nếu có
    
    cbxRole.addItem("Select Role"); // Thêm tùy chọn mặc định lên đầu
    
    for (String role : roles) {
        cbxRole.addItem(role); // Thêm các role vào ComboBox
    }
    
    cbxRole.setSelectedIndex(0); // Chọn mặc định là "Select Role"
}


    // Phương thức check lỗi để trống thông tin 
    public boolean isValidInput(){
        if(txtAccountName.getText().isEmpty()){
            txtAccountName.putClientProperty("JComponent.outline", "warning");
            lblErrorAccountName.setForeground(Color.orange);
            lblErrorAccountName.setText("Please enter fullname");
            return false;
        }
        else {
            txtAccountName.putClientProperty("JComponent.outline", "null");
            lblErrorAccountName.setText("");
        }
        if(txtEmail.getText().isEmpty()){
            txtEmail.putClientProperty("JComponent.outline", "warning");
            lblErrorEmail.setForeground(Color.orange);
            lblErrorEmail.setText("Please enter eamil");
            return false;
        }
        else {
            txtEmail.putClientProperty("JComponent.outline", "null");
            lblErrorEmail.setText("");
        }
        if (!isValidEmail(txtEmail.getText())) {
            return false;
        }
        if(txtUsername.getText().isEmpty()){
            txtUsername.putClientProperty("JComponent.outline", "warning");
            lblErrorUsername.setForeground(Color.orange);
            lblErrorUsername.setText("Please enter username");
            return false;
        }
        else {
            txtUsername.putClientProperty("JComponent.outline", "null");
            lblErrorUsername.setText("");
        }
        if(txtPassword.getText().isEmpty()){
            txtPassword.putClientProperty("JComponent.outline", "warning");
            lblErrorPassword.setForeground(Color.orange);
            lblErrorPassword.setText("Please enter password");
            return false;
        }
        else {
            txtPassword.putClientProperty("JComponent.outline", "null");
            lblErrorPassword.setText("");
        }
        if(cbxRole.getSelectedItem().equals("Select role")){
            cbxRole.putClientProperty("JComponent.outline", "warning");
            lblErrorRole.setForeground(Color.orange);
            lblErrorRole.setText("Please select role");
            return false;
        }
        else {
            cbxRole.putClientProperty("JComponent.outline", "null");
            lblErrorRole.setText("");
        }
        return true;
    }
    // Kiểm tra Email
    public boolean isValidEmail(String email) {
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        if (!email.matches(regex)) {
            txtEmail.putClientProperty("JComponent.outline", "error");
            lblErrorEmail.setText("Email is not in correct format.!");
            lblErrorEmail.setForeground(Color.RED);
            return false;
        } else {
            txtEmail.putClientProperty("JComponent.outline", "null");
            lblErrorEmail.setText("");
        }
        if (accountDAO.getInstance().selectByEmail(email) != null) {
            txtEmail.putClientProperty("JComponent.outline", "warning");
            lblErrorEmail.setText("Email already exists!");
            lblErrorEmail.setForeground(Color.ORANGE);
            return false;
        } else {
            txtEmail.putClientProperty("JComponent.outline", "null");
            lblErrorEmail.setText("");
        }
        return true;
    }
    // Phương thức lưu dự liệu vào Database
    public void insert() {
    if (isValidInput()) {
        String name = txtAccountName.getText();
        String email = txtEmail.getText();
        String user = txtUsername.getText();
        String password = new String(txtPassword.getPassword());

        // Lấy danh sách Role từ Database
        List<roleModel> roleList = roleDAO.getInstance().getAllRoless();
        int role = getRoleIDByName(cbxRole.getSelectedItem().toString(), roleList);

        if (role == -1) {
                Toast.show(panel, Toast.Type.ERROR, "Error: Invalid Role!");
            return;
        }

        // KIỂM TRA TRÙNG USERNAME HOẶC EMAIL
        if (accountDAO.getInstance().isUsernameExists(user)) {
                Toast.show(panel, Toast.Type.ERROR, "Username already exists! Please choose another name.");
            return;
        }
        if (accountDAO.getInstance().isEmailExists(email)) {
                Toast.show(panel, Toast.Type.ERROR, "Email already exists! Please choose another email.");
            return;
        }

        // Nếu hợp lệ, tiếp tục thêm tài khoản
        accountModel am = new accountModel(name, email, user, password, role);
        accountDAO.getInstance().insert(am);

        // Load lại bảng sau khi thêm
        panel.loadTableAll();
        Toast.show(panel, Toast.Type.SUCCESS, "More success!");
        ModalDialog.closeModal("accountInsert");
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

        jLabel1 = new javax.swing.JLabel();
        txtAccountName = new javax.swing.JTextField();
        lblErrorAccountName = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        lblErrorEmail = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btnCancel = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        txtUsername = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        lblErrorUsername = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        lblErrorPassword = new javax.swing.JLabel();
        cbxRole = new javax.swing.JComboBox<>();
        lblErrorRole = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setText("Fullname");

        txtAccountName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        txtEmail.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Email");

        btnCancel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnAdd.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnAdd.setText("Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        txtUsername.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Username");

        txtPassword.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        cbxRole.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cbxRole.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " " }));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setText("Password");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel10.setText("Role");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnAdd)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancel))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel4)
                            .addComponent(jLabel2)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblErrorAccountName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtAccountName, javax.swing.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
                            .addComponent(lblErrorEmail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtEmail)
                            .addComponent(txtUsername)
                            .addComponent(lblErrorUsername, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtPassword)
                            .addComponent(cbxRole, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblErrorRole, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblErrorPassword, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtAccountName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblErrorAccountName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblErrorEmail)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblErrorUsername)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblErrorPassword)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxRole, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblErrorRole)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 184, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancel)
                    .addComponent(btnAdd))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
        insert();
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
        // Đóng ModalDialog bằng cách chuyền ID vào
        ModalDialog.closeModal("accountInsert");
    }//GEN-LAST:event_btnCancelActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnCancel;
    private javax.swing.JComboBox<String> cbxRole;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel lblErrorAccountName;
    private javax.swing.JLabel lblErrorEmail;
    private javax.swing.JLabel lblErrorPassword;
    private javax.swing.JLabel lblErrorRole;
    private javax.swing.JLabel lblErrorUsername;
    private javax.swing.JTextField txtAccountName;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
