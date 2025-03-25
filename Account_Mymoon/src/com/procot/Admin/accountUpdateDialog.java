/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.procot.Admin;

import com.procot.Admin.accountForm;
import com.procot.DAO.accountDAO;
import com.procot.DAO.roleDAO;
import com.procot.Model.accountModel;
import com.procot.Model.lecturerModel;
import com.procot.Model.roleModel;
import com.procot.Model.studentModel;
import com.procot.Util.jdbcHelper;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import org.mindrot.jbcrypt.BCrypt;
import raven.modal.ModalDialog;
import raven.modal.Toast;

/**
 *
 * @author skmyg
 */
public class accountUpdateDialog extends javax.swing.JPanel {

    /**
     * Creates new form classInsertDialog
     */
    accountForm panel;

    public accountUpdateDialog(accountForm panel) {
        initComponents();
        this.panel = panel;
        loadComboBox();
        displayData();
    }

    // Phương thức lấy dự liệu từ table và hiện lên textfield
    public void displayData() {
        accountModel am = panel.getSelected();
        txtAccountName.setText(am.getFullnamee());
        txtEmail.setText(am.getEmail());
        List<roleModel> roleList = roleDAO.getInstance().getAllRoless();
    cbxRole.removeAllItems(); // Xóa dữ liệu cũ
    cbxRole.addItem("Select role"); // Thêm lựa chọn mặc định

    // Thêm tất cả role vào combobox
    for (roleModel role : roleList) {
        cbxRole.addItem(role.getRoleName()); // Hiển thị tên vai trò
    }

    // Chọn đúng RoleID của tài khoản trong ComboBox
    if (am.getRoleID() > 0) {
        String selectedRole = getRoleNameByID(am.getRoleID(), roleList);
        cbxRole.setSelectedItem(selectedRole);
    }

    // Cập nhật danh sách giảng viên
    cbxLecturer.removeAllItems();
    cbxLecturer.addItem("Select lecturer");
    for (lecturerModel lecturer : lecturerList) {
        cbxLecturer.addItem(lecturer.getLecturerName());
    }
    if (am.getLecturerName() != null && !am.getLecturerName().isEmpty()) {
        cbxLecturer.setSelectedItem(am.getLecturerName());
    }

    // Cập nhật danh sách sinh viên
    cbxStudent.removeAllItems();
    cbxStudent.addItem("Select student");
    for (studentModel student : studentList) {
        cbxStudent.addItem(student.getStudentName());
    }
    if (am.getStudentName() != null && !am.getStudentName().isEmpty()) {
        cbxStudent.setSelectedItem(am.getStudentName());
    }
}
    
    public int getRoleIDByName(String roleName, List<roleModel> roleList) {
    for (roleModel role : roleList) {
        if (role.getRoleName().equals(roleName)) {
            return role.getRoleID();
        }
    }
    return -1; // Trả về -1 nếu không tìm thấy
}
    
    private String getRoleNameByID(int roleID, List<roleModel> roleList) {
    for (roleModel role : roleList) {
        if (role.getRoleID() == roleID) {
            return role.getRoleName();
        }
    }
    return "Select role";
}

    private int getIDByStudentName(String studentName, ArrayList<studentModel> list) {
        for (studentModel sm : list) {
            if (sm.getStudentName().equals(studentName)) {
                return sm.getStudentID();
            }
        }
        return -1;
    }

    private int getIDByLecturerName(String lecturerName, ArrayList<lecturerModel> list) {
        for (lecturerModel lm : list) {
            if (lm.getLecturerName().equals(lecturerName)) {
                return lm.getLecturerID();
            }
        }
        return -1;
    }

    // In dự liệu lên combobox
    public void loadComboBox() {
        for (studentModel sm : studentList) {
            cbxStudent.addItem(sm.getStudentName());
        }
        AutoCompleteDecorator.decorate(cbxStudent); // Tìm kiếm nhanh trong combobox bằng swingX
        for (lecturerModel lm : lecturerList) {
            cbxLecturer.addItem(lm.getLecturerName());
        }
        AutoCompleteDecorator.decorate(cbxLecturer); // Tìm kiếm nhanh trong combobox bằng swingX
    }

    // Phương thức check lỗi để trống thông tin 
    public boolean isValidInput() {
        if (txtAccountName.getText().isEmpty()) {
            txtAccountName.putClientProperty("JComponent.outline", "warning");
            lblErrorAccountName.setForeground(Color.orange);
            lblErrorAccountName.setText("Please enter username");
            return false;
        } else {
            txtAccountName.putClientProperty("JComponent.outline", "null");
            lblErrorAccountName.setText("");
        }
        if (txtEmail.getText().isEmpty()) {
            txtEmail.putClientProperty("JComponent.outline", "warning");
            lblErrorEmail.setForeground(Color.orange);
            lblErrorEmail.setText("Please enter email");
            return false;
        } else {
            txtEmail.putClientProperty("JComponent.outline", "null");
            lblErrorEmail.setText("");
        }
        if (!isValidEmail(txtEmail.getText())) {
            return false;
        }
        if (txtPassword.getText().isEmpty()) {
            txtPassword.putClientProperty("JComponent.outline", "warning");
            lblErrorPassword.setForeground(Color.orange);
            lblErrorPassword.setText("Please enter password");
            return false;
        } else {
            txtPassword.putClientProperty("JComponent.outline", "null");
            lblErrorPassword.setText("");
        }
        if (cbxRole.getSelectedItem().equals("Chọn vai trò")) {
            cbxRole.putClientProperty("JComponent.outline", "warning");
            lblErrorRole.setForeground(Color.orange);
            lblErrorRole.setText("Please enter role");
            return false;
        } else {
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
        accountModel am = panel.getSelected();
        String currentEmail = am.getEmail();
        if (email.equals(currentEmail)) {
            return true;
        }
        if (accountDAO.getInstance().selectByEmail(email) != null) {
            txtEmail.putClientProperty("JComponent.outline", "warning");
            lblErrorEmail.setText("Email already exists !");
            lblErrorEmail.setForeground(Color.ORANGE);
            return false;
        } else {
            txtEmail.putClientProperty("JComponent.outline", "null");
            lblErrorEmail.setText("");
        }
        return true;
    }

    // Phương thức sửa dự liệu vào Database
     public void update() { 
    if (isValidInput()) { 
        // 🔹 Lấy tài khoản đang chọn từ panel
        accountModel am = panel.getSelected();
        if (am == null) { 
            Toast.show(panel, Toast.Type.ERROR, "No account found to update!");
            return; 
        } 
        
        // ✅ Cập nhật các thông tin từ form vào model
        am.setFullname(txtAccountName.getText());
        am.setEmail(txtEmail.getText());
        am.setPassword(new String(txtPassword.getPassword())); // Lấy mật khẩu từ form
        am.setRoleID(getRoleIDByName(cbxRole.getSelectedItem().toString(), roleDAO.getInstance().getAllRoless()));

        // Lấy StudentID & LecturerID từ ComboBox
//        String selectedStudent = cbxStudent.getSelectedItem() != null ? cbxStudent.getSelectedItem().toString() : "";
//        String selectedLecturer = cbxLecturer.getSelectedItem() != null ? cbxLecturer.getSelectedItem().toString() : "";
//        am.setStudentID(getIDByStudentName(selectedStudent, studentList));
//        am.setLecturerID(getIDByLecturerName(selectedLecturer, lecturerList));

        // ✅ Cập nhật tài khoản trong database
        int result = accountDAO.getInstance().update(am);
        if (result > 0) {
            panel.loadTableAll(); // ✅ Load lại bảng sau khi cập nhật
            Toast.show(panel, Toast.Type.SUCCESS, "Cập nhật thành công!");
            ModalDialog.closeModal("accountUpdate"); // ✅ Đóng form cập nhật
        } else {
            Toast.show(panel, Toast.Type.ERROR, "Cập nhật thất bại! Có thể Email đã tồn tại.");
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

        jLabel1 = new javax.swing.JLabel();
        txtAccountName = new javax.swing.JTextField();
        lblErrorAccountName = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        lblErrorEmail = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btnCancel = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        txtPassword = new javax.swing.JPasswordField();
        lblErrorPassword = new javax.swing.JLabel();
        cbxRole = new javax.swing.JComboBox<>();
        lblErrorRole = new javax.swing.JLabel();
        cbxStudent = new javax.swing.JComboBox<>();
        cbxLecturer = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();

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
        btnAdd.setText("Edit");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        txtPassword.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        cbxRole.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cbxRole.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chọn vai trò", "AD", "GV", "SV" }));

        cbxStudent.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cbxStudent.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chọn sinh viên" }));

        cbxLecturer.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cbxLecturer.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chọn giảng viên" }));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setText("Password");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel10.setText("Role");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel11.setText("Student");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel12.setText("Lecturer");

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
                            .addComponent(jLabel9)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11)
                            .addComponent(jLabel12))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblErrorAccountName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtAccountName)
                            .addComponent(lblErrorEmail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtEmail)
                            .addComponent(txtPassword)
                            .addComponent(cbxRole, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblErrorRole, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbxStudent, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbxLecturer, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxStudent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxLecturer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 100, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancel)
                    .addComponent(btnAdd))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
        update();
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
        // Đóng ModalDialog bằng cách chuyền ID vào
        ModalDialog.closeModal("accountUpdate");
    }//GEN-LAST:event_btnCancelActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnCancel;
    private javax.swing.JComboBox<String> cbxLecturer;
    private javax.swing.JComboBox<String> cbxRole;
    private javax.swing.JComboBox<String> cbxStudent;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel lblErrorAccountName;
    private javax.swing.JLabel lblErrorEmail;
    private javax.swing.JLabel lblErrorPassword;
    private javax.swing.JLabel lblErrorRole;
    private javax.swing.JTextField txtAccountName;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JPasswordField txtPassword;
    // End of variables declaration//GEN-END:variables
}
