/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.procot.Admin;

import com.procot.dao.LecturerDAO;
import com.procot.dao.MajorDAO;
import com.procot.model.Lecturer;
import com.procot.model.Major;
import java.awt.Color;
import java.util.ArrayList;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import raven.modal.ModalDialog;
import raven.modal.Toast;

/**
 *
 * @author Admin
 */
public class LecturerInsertDialog extends javax.swing.JFrame {
    LecturerForm panel;
    //Lấy danh sách chuyên ngành
    ArrayList<Major> MajorList = MajorDAO.getInstance().selectAll();

    /**
     * Creates new form LecturerInsertDialog
     */
    public LecturerInsertDialog(LecturerForm panel) {
        initComponents();
        this.panel = panel;
        loadComboBox();
    }
    //Lấy ID dựa theo tên
    private int getIDByName(String MajorName, ArrayList<Major> list) {
        for (Major mj : list) {
            if (mj.getMajorName().equals(MajorName)) {
                return mj.getMajorID();
            }
        }
        return -1;   //Trả về -1 nếu không tìm thấy
    }
    
    //Tải danh sách chuyên ngành vào combobox
    public void loadComboBox() {
        for (Major mj : MajorList) {
            cboChooseMajor.addItem(mj.getMajorName());
        }
        // Tìm kiếm nhanh trong combobox bằng swingX
        AutoCompleteDecorator.decorate(cboChooseMajor);
    }
    
    //Phương thức kiểm tra lỗi để trống thông tin
    public boolean isValidInput(){
        //Kiểm tra tên giảng viên
        if (txtFullName.getText().isEmpty()) {
            txtFullName.putClientProperty("JComponent.outline", "warning");
            lblErrorFullName.setText("Please enter lecturer name!");
            lblErrorFullName.setForeground(Color.RED);
            return false;
        } else {
            txtFullName.putClientProperty("JComponent.outline", "null");
            lblErrorFullName.setText("");
        }
        
        //Kiểm tra số điện thoại
        if (txtPhoneNumber.getText().isEmpty()) {
            txtPhoneNumber.putClientProperty("JComponent.outline", "warning");
            lblErrorPhoneNumber.setText("Please enter phone number!");
            lblErrorPhoneNumber.setForeground(Color.RED);
            return false;
        } else {
            txtPhoneNumber.putClientProperty("JComponent.outline", "null");
            lblErrorPhoneNumber.setText("");
        }
        if (!isValidPhoneNumber(txtPhoneNumber.getText())) {
            return false;
        }
        
        //Kiểm tra email
        if (txtEmail.getText().isEmpty()) {
            txtEmail.putClientProperty("JComponent.outline", "warning");
            lblErrorEmail.setText("Please enter email!");
            lblErrorEmail.setForeground(Color.RED);
            return false;
        } else {
            txtEmail.putClientProperty("JComponent.outline", "null");
            lblErrorEmail.setText("");
        }
        if (!isValidEmail(txtEmail.getText())){
            return false;
        }
        
        //Kiểm tra địa chỉ
        if (txtaAddress.getText().isEmpty()) {
            txtaAddress.putClientProperty("JComponent.outline", "warning");
            lblErrorAddress.setText("Please enter address!");
            lblErrorAddress.setForeground(Color.RED);
            return false;
        } else {
            txtaAddress.putClientProperty("JComponent.outline", "null");
            lblErrorAddress.setText("");
        }
        
        //Kiểm tra chuyên ngành
        if (cboChooseMajor.getSelectedItem().equals("Select a major")) {
            cboChooseMajor.putClientProperty("JComponent.outline", "warning");
            lblErrorMajor.setText("Please select a major!");
            lblErrorMajor.setForeground(Color.RED);
            return false;
        } else {
            cboChooseMajor.putClientProperty("JComponent.outline", "null");
            lblErrorMajor.setText("");
        }
    
        //Kiểm tra trạng thái      
        if (txtStatus.getText().isEmpty()) {
            txtStatus.putClientProperty("JComponent.outline", "warning");
            lblErrorStatus.setText("Please enter status!");
            lblErrorStatus.setForeground(Color.RED);
            return false;
        } else {
            txtStatus.putClientProperty("JComponent.outline", "null");
            lblErrorStatus.setText("");
        }
        return true;
    }
    
    // Kiểm tra định dạng email và sự tồn tại
    public boolean isValidEmail(String email) {
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        if (!email.matches(regex)) {
            txtEmail.putClientProperty("JComponent.outline", "error");
            lblErrorEmail.setText("Email is not in correct format!");
            lblErrorEmail.setForeground(Color.RED);
            return false;
        } else {
            txtEmail.putClientProperty("JComponent.outline", "null");
            lblErrorEmail.setText("");
        }
        if (LecturerDAO.getInstance().selectByEmail(email) != null) {
            txtEmail.putClientProperty("JComponent.outline", "warning");
            lblErrorEmail.setText("Email already exists!");
            lblErrorEmail.setForeground(Color.RED);
            return false;
        } else {
            txtEmail.putClientProperty("JComponent.outline", "null");
            lblErrorEmail.setText("");
        }
        return true;
    }
    
    // Kiểm tra định dạng số điện thoại và sự tồn tại
    public boolean isValidPhoneNumber(String phoneNumber) {
        String regex = "^\\+?[0-9]{10,15}$";
        if (!phoneNumber.matches(regex)) {
            txtPhoneNumber.putClientProperty("JComponent.outline", "error");
            lblErrorPhoneNumber.setText("Phone number is not in correct format!");
            lblErrorPhoneNumber.setForeground(Color.RED);
            return false;
        } else {
            txtPhoneNumber.putClientProperty("JComponent.outline", "null");
            lblErrorPhoneNumber.setText("");
        }
        if (LecturerDAO.getInstance().selectByPhoneNumber(phoneNumber) != null) {
            txtPhoneNumber.putClientProperty("JComponent.outline", "warning");
            lblErrorPhoneNumber.setText("Phone number already exists!");
            lblErrorPhoneNumber.setForeground(Color.RED);
            return false;
        } else {
            txtPhoneNumber.putClientProperty("JComponent.outline", "null");
            lblErrorPhoneNumber.setText("");
        }
        return true;
    }
    
    //Phương thức thêm dữ liệu vào database
    public void insert(){
        //Kiểm tra dữ liệu đầu vào
        if(isValidInput()){
            
            //Lấy thông tin từ ô nhập dữ liệu
            String fullName = txtFullName.getText();
            String phoneNumber = txtPhoneNumber.getText();
            String email = txtEmail.getText();
            String address = txtaAddress.getText();  
            int majorID = getIDByName(cboChooseMajor.getSelectedItem().toString(),MajorList);
            String status = txtStatus.getText();
            
            //Tạo đối tượng Lecturer mới
            Lecturer lr = new Lecturer(fullName, phoneNumber , email , address , majorID , status);
            
            //Thêm giảng viên mới
            LecturerDAO.getInstance().insert(lr);
            
            // Tải lại bảng sau khi thêm
            panel.loadTableAll();
            
            //Thông báo thành công
            Toast.show(panel, Toast.Type.SUCCESS,"Added successfully");
            ModalDialog.closeModal("LecturerInsert");
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        txtFullName = new javax.swing.JTextField();
        lblFullName = new javax.swing.JLabel();
        lblAddress = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtaAddress = new javax.swing.JTextArea();
        lblPhoneNumber = new javax.swing.JLabel();
        txtPhoneNumber = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        lblEmail = new javax.swing.JLabel();
        cboChooseMajor = new javax.swing.JComboBox<>();
        lbl_MajorName = new javax.swing.JLabel();
        btnSave = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        lblStatus = new javax.swing.JLabel();
        txtStatus = new javax.swing.JTextField();
        lblErrorFullName = new javax.swing.JLabel();
        lblErrorPhoneNumber = new javax.swing.JLabel();
        lblErrorEmail = new javax.swing.JLabel();
        lblErrorAddress = new javax.swing.JLabel();
        lblErrorMajor = new javax.swing.JLabel();
        lblErrorStatus = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblFullName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblFullName.setText("FullName");

        lblAddress.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblAddress.setText("Address");

        txtaAddress.setColumns(20);
        txtaAddress.setRows(5);
        jScrollPane1.setViewportView(txtaAddress);

        lblPhoneNumber.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblPhoneNumber.setText("PhoneNumber");

        txtEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmailActionPerformed(evt);
            }
        });

        lblEmail.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblEmail.setText("Email");

        cboChooseMajor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select a major" }));

        lbl_MajorName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbl_MajorName.setText("Major");

        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        lblStatus.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblStatus.setText("Status");

        lblErrorFullName.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        lblErrorFullName.setText("jLabel1");

        lblErrorEmail.setText("jLabel1");

        lblErrorAddress.setText("jLabel1");

        lblErrorMajor.setText("jLabel1");

        lblErrorStatus.setText("jLabel2");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnSave)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancel))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblFullName)
                            .addComponent(lblAddress)
                            .addComponent(lblPhoneNumber)
                            .addComponent(lblEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_MajorName)
                            .addComponent(lblStatus))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtFullName)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblErrorFullName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(140, 140, 140))
                            .addComponent(txtEmail)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblErrorAddress, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(131, 131, 131))
                            .addComponent(txtStatus)
                            .addComponent(lblErrorPhoneNumber, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtPhoneNumber, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblErrorEmail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cboChooseMajor, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblErrorMajor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblErrorStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtFullName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblFullName, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblErrorFullName, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPhoneNumber)
                    .addComponent(txtPhoneNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblErrorPhoneNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 1, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEmail)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblErrorEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblAddress)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblErrorAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_MajorName, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboChooseMajor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblErrorMajor, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblStatus)
                    .addComponent(txtStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblErrorStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSave)
                    .addComponent(btnCancel))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
        insert();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
        //Đóng cửa sổ hiện tại
        ModalDialog.closeModal("LecturerInsert");
    }//GEN-LAST:event_btnCancelActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnSave;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cboChooseMajor;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAddress;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblErrorAddress;
    private javax.swing.JLabel lblErrorEmail;
    private javax.swing.JLabel lblErrorFullName;
    private javax.swing.JLabel lblErrorMajor;
    private javax.swing.JLabel lblErrorPhoneNumber;
    private javax.swing.JLabel lblErrorStatus;
    private javax.swing.JLabel lblFullName;
    private javax.swing.JLabel lblPhoneNumber;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JLabel lbl_MajorName;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtFullName;
    private javax.swing.JTextField txtPhoneNumber;
    private javax.swing.JTextField txtStatus;
    private javax.swing.JTextArea txtaAddress;
    // End of variables declaration//GEN-END:variables
}
