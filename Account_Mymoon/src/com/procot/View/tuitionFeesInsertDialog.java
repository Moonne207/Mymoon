/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.procot.View;

import com.procot.View.tuitionFeesForm;
import com.procot.DAO.tuitionFeesDAO;
import com.procot.Model.studentModel;
import com.procot.Model.semesterModel;
import com.procot.DAO.studentDAO;
import com.procot.Model.tuitionFeesModel;
import java.awt.Color;
import java.util.ArrayList;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import raven.modal.ModalDialog;
import raven.modal.Toast;

/**
 *
 * @author skmyg
 */
public class tuitionFeesInsertDialog extends javax.swing.JPanel{

    /**
     * Creates new form classInsertDialog
     */
    tuitionFeesForm panel;
    ArrayList<studentModel> studentList = com.procot.DAO.studentDAO.getInstance().selectAll(); 
    ArrayList<semesterModel> semesterList = com.procot.DAO.semesterDAO.getInstance().selectAll(); 
    
    public tuitionFeesInsertDialog(tuitionFeesForm panel) {
        initComponents();
        this.panel = panel;
        loadComboBox();

    }
    private int getIDByFullName(String fullName, ArrayList<studentModel> list) {
        for (studentModel sm : list) {
            if (sm.getFullName().equals(fullName)) {
                return sm.getStudentId();
            }
        }
        return -1;
    }
    private int getIDBySemesterName(String semesterName, ArrayList<semesterModel> list) {
        for (semesterModel sm : list) {
            if (sm.getSemesterName().equals(semesterName)) {
                return sm.getSemesterId();
            }
        }
        return -1;
    }
    public void loadComboBox() {
        for (studentModel sm : studentList) {
            cbofulllname.addItem(sm.getFullName());
        }
        AutoCompleteDecorator.decorate(cbofulllname);
        for (semesterModel sm : semesterList) {
            cbosemester.addItem(sm.getSemesterName());
        }
        AutoCompleteDecorator.decorate(cbosemester);
    }

    

    // Phương thức check lỗi để trống thông tin 
    public boolean isValidInput(){
        if(cbofulllname.getSelectedItem().equals("Select student")){
            cbofulllname.putClientProperty("JComponent.outline", "warning");
            lblErrorFullName.setForeground(Color.orange);
            lblErrorFullName.setText("Please select student");
            return false;
        }
        else {
            cbofulllname.putClientProperty("JComponent.outline", "null");
            lblErrorFullName.setText("");
        }
        if(cbosemester.getSelectedItem().equals("Select semester")){
            cbosemester.putClientProperty("JComponent.outline", "warning");
            lblErrorSemeterName.setForeground(Color.orange);
            lblErrorSemeterName.setText("Please select semester");
            return false;
        }
        else {
            cbosemester.putClientProperty("JComponent.outline", "null");
            lblErrorSemeterName.setText("");
        }
        if(txtAmount.getText().isEmpty()){
            txtAmount.putClientProperty("JComponent.outline", "warning");
            lblErrorAmount.setForeground(Color.orange);
            lblErrorAmount.setText("Please enter amount");
            return false;
        }
        else {
            txtAmount.putClientProperty("JComponent.outline", "null");
            lblErrorAmount.setText("");
        }
////        if (!isValidEmail(txtEmail.getText())) {
////            return false;
////        }
//        if(txtUsername.getText().isEmpty()){
//            txtUsername.putClientProperty("JComponent.outline", "warning");
//            lblErrorUsername.setForeground(Color.orange);
//            lblErrorUsername.setText("Please enter username");
//            return false;
//        }
//        else {
//            txtUsername.putClientProperty("JComponent.outline", "null");
//            lblErrorUsername.setText("");
//        }
//        if(txtPassword.getText().isEmpty()){
//            txtPassword.putClientProperty("JComponent.outline", "warning");
//            lblErrorPassword.setForeground(Color.orange);
//            lblErrorPassword.setText("Please enter password");
//            return false;
//        }
//        else {
//            txtPassword.putClientProperty("JComponent.outline", "null");
//            lblErrorPassword.setText("");
//        }
//        if(cbxRole.getSelectedItem().equals("Select role")){
//            cbxRole.putClientProperty("JComponent.outline", "warning");
//            lblErrorRole.setForeground(Color.orange);
//            lblErrorRole.setText("Please select role");
//            return false;
//        }
//        else {
//            cbxRole.putClientProperty("JComponent.outline", "null");
//            lblErrorRole.setText("");
//        }
        return true;
    }
    // Kiểm tra Email
//    public boolean isValidEmail(String email) {
//        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
//        if (!email.matches(regex)) {
//            txtEmail.putClientProperty("JComponent.outline", "error");
//            lblErrorEmail.setText("Email is not in correct format.!");
//            lblErrorEmail.setForeground(Color.RED);
//            return false;
//        } else {
//            txtEmail.putClientProperty("JComponent.outline", "null");
//            lblErrorEmail.setText("");
//        }
//        if (accountDAO.getInstance().selectByEmail(email) != null) {
//            txtEmail.putClientProperty("JComponent.outline", "warning");
//            lblErrorEmail.setText("Email already exists!");
//            lblErrorEmail.setForeground(Color.ORANGE);
//            return false;
//        } else {
//            txtEmail.putClientProperty("JComponent.outline", "null");
//            lblErrorEmail.setText("");
//        }
//        return true;
//    }
    // Phương thức lưu dự liệu vào Database
    public void insert() {
    if (!isValidInput()) {
    int student = getIDByFullName(cbofulllname.getSelectedItem().toString(), studentList);
    int semester = getIDBySemesterName(cbosemester.getSelectedItem().toString(), semesterList);
    double amount = Double.parseDouble(txtAmount.getText());
    LocalDate localDate = LocalDate.parse(txtDueDate.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    Date dueDate = Date.valueOf(localDate);
    boolean paymentStatus = cbostatus.getSelectedIndex() == 1; 
    if (tuitionFeesDAO.getInstance().isFeeExists(student, semester)) {
        Toast.show(panel, Toast.Type.ERROR, "Học phí của sinh viên này trong học kỳ này đã tồn tại!");
        return;
    }
    tuitionFeesModel fee = new tuitionFeesModel(student, semester, amount, dueDate, paymentStatus);
    tuitionFeesDAO.getInstance().insert(fee);
    panel.loadTableAll();
    Toast.show(panel, Toast.Type.SUCCESS, "Add tuition successfully!");
    ModalDialog.closeModal("tuitionFeesInsert");
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
        lblErrorFullName = new javax.swing.JLabel();
        txtAmount = new javax.swing.JTextField();
        lblErrorAmount = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btnCancel = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        lblErrorStatus = new javax.swing.JLabel();
        cbofulllname = new javax.swing.JComboBox<>();
        lblErrorSemeterName = new javax.swing.JLabel();
        cbosemester = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        txtDueDate = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        lblErrorDueDate = new javax.swing.JLabel();
        cbostatus = new javax.swing.JComboBox<>();

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setText("Fullname");

        txtAmount.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Amount");

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

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Status");

        cbofulllname.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        cbofulllname.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " " }));

        cbosemester.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        cbosemester.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " " }));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Semester Name");

        txtDueDate.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Due Date");

        cbostatus.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        cbostatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Unpaid", "Paid" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 146, Short.MAX_VALUE)
                        .addComponent(btnAdd)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancel))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel4)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel5))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblErrorFullName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblErrorAmount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtAmount)
                            .addComponent(lblErrorStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbofulllname, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblErrorSemeterName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbosemester, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtDueDate)
                            .addComponent(lblErrorDueDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbostatus, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cbofulllname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblErrorFullName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cbosemester, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblErrorSemeterName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblErrorAmount)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDueDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblErrorDueDate)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbostatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(10, 10, 10)
                .addComponent(lblErrorStatus)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 120, Short.MAX_VALUE)
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
    private javax.swing.JComboBox<String> cbofulllname;
    private javax.swing.JComboBox<String> cbosemester;
    private javax.swing.JComboBox<String> cbostatus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel lblErrorAmount;
    private javax.swing.JLabel lblErrorDueDate;
    private javax.swing.JLabel lblErrorFullName;
    private javax.swing.JLabel lblErrorSemeterName;
    private javax.swing.JLabel lblErrorStatus;
    private javax.swing.JTextField txtAmount;
    private javax.swing.JTextField txtDueDate;
    // End of variables declaration//GEN-END:variables
}
