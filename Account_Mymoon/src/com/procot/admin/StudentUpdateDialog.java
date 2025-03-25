/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.procot.Admin;
import com.procot.dao.ClassDAO;
import com.procot.dao.StudentDAO;
import com.procot.model.Class;
import com.procot.model.Student;
import java.awt.Color;
import java.util.ArrayList;
import java.sql.Date;
import javax.swing.JFormattedTextField;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import raven.datetime.DatePicker;
import raven.modal.ModalDialog;
import raven.modal.Toast;
/**
 *
 * @author Admin
 */
public class StudentUpdateDialog extends javax.swing.JFrame {
    StudentForm panel;
    DatePicker datePicker = new DatePicker();
    
    //Lấy danh sách lớp
    ArrayList<Class> ClassList = ClassDAO.getInstance().selectAll();
    /**
     * Creates new form StudentUpdateDialog
     */
    public StudentUpdateDialog(StudentForm panel) {
        initComponents();
        this.panel = panel;
        datePicker.setEditor((JFormattedTextField) txtDateOfBirth);
        loadComboBox();
        displayData();
    }
    
    //Lấy ID dựa theo tên
    private int getIDByName(String ClassName, ArrayList<Class> list) {
        for (Class cl : list) {
            if (cl.getClassName().equals(ClassName)) {
                return cl.getClassID();
            }
        }
        return -1;      //Trả về -1 nếu không tìm thấy
    }
    
    //Tải danh sách chuyên ngành vào combobox
    public void loadComboBox() {
        for (Class cl : ClassList) {
            cboChooseClass.addItem(cl.getClassName());
        }
        // Tìm kiếm nhanh trong combobox bằng swingX
        AutoCompleteDecorator.decorate(cboChooseClass);
    }
    
    //Hiểm thị thông tin lên form
    public void displayData(){
        Student st = panel.getSelected();
        txtFullName.setText(st.getFullName());
        txtDateOfBirth.setText(st.getFormattedDateOfBirth());
        cboChooseGender.setSelectedItem(st.getGender());
        txtaAddress.setText(st.getAddress());
        txtPhoneNumber.setText(st.getPhoneNumber());
        txtEmail.setText(st.getEmail());
        cboChooseClass.setSelectedItem(st.getClassName());
    }
    
    // Phương thức kiểm tra lỗi để trống thông tin 
    public boolean isValidInput(){
        //Kiểm tra tên sinh viên
        if (txtFullName.getText().isEmpty()) {
            txtFullName.putClientProperty("JComponent.outline", "warning");
            lblErrorFullName.setText("Please enter student name!");
            lblErrorFullName.setForeground(Color.RED);
            return false;
        } else {
            txtFullName.putClientProperty("JComponent.outline", "null");
            lblErrorFullName.setText("");
        }
        
        //Kiểm tra ngày sinh
        if (txtDateOfBirth.getText().equals("--/--/----")) {
            txtDateOfBirth.putClientProperty("JComponent.outline", "warning");
            lblErrorDateOfBirth.setText("Please enter date of birth!");
            lblErrorDateOfBirth.setForeground(Color.RED);
            return false;
        } else {
            txtDateOfBirth.putClientProperty("JComponent.outline", "null");
            lblErrorDateOfBirth.setText("");
        }
        
        //Kiểm tra giới tính
        if (cboChooseGender.getSelectedItem().equals("Select a gender")) {
            cboChooseGender.putClientProperty("JComponent.outline", "warning");
            lblErrorGender.setText("Please select a gender!");
            lblErrorGender.setForeground(Color.RED);
            return false;
        } else {
            cboChooseGender.putClientProperty("JComponent.outline", "null");
            lblErrorGender.setText("");
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
        
        //Kiểm tra lớp
        if (cboChooseClass.getSelectedItem().equals("Select a class")) {
            cboChooseClass.putClientProperty("JComponent.outline", "warning");
            lblErrorClass.setText("Please select a class!");
            lblErrorClass.setForeground(Color.RED);
            return false;
        } else {
            cboChooseClass.putClientProperty("JComponent.outline", "null");
            lblErrorClass.setText("");
        }
        return true;
    }
    
    // Kiểm tra định dạng Email + sự tồn tại
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
        if (StudentDAO.getInstance().selectByEmail(email) != null) {
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
        if (StudentDAO.getInstance().selectByPhoneNumber(phoneNumber) != null) {
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
    
    // Phương thức lưu dữ liệu vào Database
    public void update(){
        //Kiểm tra dữ liệu đầu vào
        if(isValidInput()){
            
            //Lấy thông tin từ ô nhập dữ liệu
            int studentID = panel.getSelected().getStudentID();
            String fullName = txtFullName.getText();
            Date dateOfBirth = Date.valueOf(datePicker.getSelectedDate());
            int gender = getIDByName(cboChooseGender.getSelectedItem().toString(),ClassList);
            String address = txtaAddress.getText();
            String phoneNumber = txtPhoneNumber.getText();
            String email = txtEmail.getText();
            int classID = getIDByName(cboChooseClass.getSelectedItem().toString(),ClassList);
            
            // Sử dụng constructor để tạo đối tượng
            Student st = new Student(studentID , fullName , dateOfBirth , gender , address , phoneNumber , email , classID);
            
            //Cập nhật thông tin sinh viên
            StudentDAO.getInstance().update(st);
            
            panel.loadTableAll();
            Toast.show(panel, Toast.Type.SUCCESS,"Updated successfully");
            ModalDialog.closeModal("StudentUpdate");
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
        txtDateOfBirth = new javax.swing.JTextField();
        lblDateOfBirth = new javax.swing.JLabel();
        lblGender = new javax.swing.JLabel();
        lblAddress = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtaAddress = new javax.swing.JTextArea();
        lblPhoneNumber = new javax.swing.JLabel();
        txtPhoneNumber = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        lblEmail = new javax.swing.JLabel();
        cboChooseClass = new javax.swing.JComboBox<>();
        lbl_ClassName = new javax.swing.JLabel();
        btnSave = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        cboChooseGender = new javax.swing.JComboBox<>();
        lblErrorFullName = new javax.swing.JLabel();
        lblErrorDateOfBirth = new javax.swing.JLabel();
        lblErrorGender = new javax.swing.JLabel();
        lblErrorAddress = new javax.swing.JLabel();
        lblErrorPhoneNumber = new javax.swing.JLabel();
        lblErrorEmail = new javax.swing.JLabel();
        lblErrorClass = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblFullName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblFullName.setText("FullName");

        lblDateOfBirth.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblDateOfBirth.setText("DateOfBirth");

        lblGender.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblGender.setText("Gender");

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

        cboChooseClass.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select a class" }));

        lbl_ClassName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbl_ClassName.setText("Class");

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

        cboChooseGender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select a gender", "Male", "Female" }));

        lblErrorFullName.setText("jLabel1");

        lblErrorDateOfBirth.setText("jLabel2");

        lblErrorGender.setText("jLabel1");

        lblErrorAddress.setText("jLabel2");

        lblErrorPhoneNumber.setText("jLabel3");

        lblErrorEmail.setText("jLabel1");

        lblErrorClass.setText("jLabel2");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblErrorDateOfBirth, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnSave)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancel))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblFullName)
                            .addComponent(lblDateOfBirth)
                            .addComponent(lblGender)
                            .addComponent(lblAddress)
                            .addComponent(lblPhoneNumber)
                            .addComponent(lblEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_ClassName))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtFullName)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE)
                            .addComponent(txtPhoneNumber)
                            .addComponent(txtEmail)
                            .addComponent(cboChooseClass, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblErrorFullName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtDateOfBirth, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(cboChooseGender, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblErrorGender, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblErrorAddress, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblErrorPhoneNumber, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblErrorEmail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblErrorClass, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
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
                    .addComponent(txtDateOfBirth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDateOfBirth, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblErrorDateOfBirth, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblGender)
                    .addComponent(cboChooseGender, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblErrorGender, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblAddress)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblErrorAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPhoneNumber)
                    .addComponent(txtPhoneNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblErrorPhoneNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEmail))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblErrorEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbl_ClassName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cboChooseClass))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblErrorClass, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
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
        update();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
        //Đóng cửa sổ hiện tại
        ModalDialog.closeModal("StudentUpdate");
    }//GEN-LAST:event_btnCancelActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnSave;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cboChooseClass;
    private javax.swing.JComboBox<String> cboChooseGender;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAddress;
    private javax.swing.JLabel lblDateOfBirth;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblErrorAddress;
    private javax.swing.JLabel lblErrorClass;
    private javax.swing.JLabel lblErrorDateOfBirth;
    private javax.swing.JLabel lblErrorEmail;
    private javax.swing.JLabel lblErrorFullName;
    private javax.swing.JLabel lblErrorGender;
    private javax.swing.JLabel lblErrorPhoneNumber;
    private javax.swing.JLabel lblFullName;
    private javax.swing.JLabel lblGender;
    private javax.swing.JLabel lblPhoneNumber;
    private javax.swing.JLabel lbl_ClassName;
    private javax.swing.JTextField txtDateOfBirth;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtFullName;
    private javax.swing.JTextField txtPhoneNumber;
    private javax.swing.JTextArea txtaAddress;
    // End of variables declaration//GEN-END:variables
}
