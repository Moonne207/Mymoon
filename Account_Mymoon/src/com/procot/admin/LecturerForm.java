/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.procot.Admin;

import com.formdev.flatlaf.FlatClientProperties;
import com.procot.Dao.LecturerDAO;
import com.procot.Model.Lecturer;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import raven.modal.ModalDialog;
import raven.modal.Toast;
import raven.modal.component.SimpleModalBorder;
import raven.modal.option.Location;
import raven.modal.option.Option;

/**
 *
 * @author Admin
 */
public class LecturerForm extends javax.swing.JFrame {
    // khai báo danh sách chứa các đối tượng Lecturer
    ArrayList<Lecturer> LecturerList;
    /**
     * Creates new form LecturerForm
     */
    public LecturerForm() {
        initComponents();
    }
    
    //Tải dữ liệu từ database lên bảng
    public void loadTableAll() {
        //Định nghĩa tên các cột trong bảng
        String[] column = new String[]{"#", "FullName", "PhoneNumber" , "Email", "Address" , "Major" , "Status"};
        //Thêm tên vào bảng
        TableUtil.loadTableColumn(tblLecturer, column);
        
        //Lấy danh sách giảng viên từ database
        LecturerList = LecturerDAO.getInstance().selectAll();
        // Thêm dữ liệu vào table
        TableUtil.loadTableData(tblLecturer, LecturerList, lr -> new Object[]{
            lr.getLecturerID(),
            lr.getFullName(),
            lr.getPhoneNumber(),
            lr.getEmail(),
            lr.getAddress(),
            lr.getMajorName(),
            lr.getStatus()
        });
        //Căn lề cho dữ liệu trong bảng
        TableUtil.alignTableCells(tblLecturer , SwingConstants.CENTER);
        //Giới hạn độ rộng cột đầu tiên : 50
        tblLecturer.getColumnModel().getColumn(0).setMaxWidth(50);
        //Sắp xếp tự động cho bảng
        tblLecturer.setAutoCreateRowSorter(true);
        //Đặt placeholder
        txtSearch.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Search");
        //Hiển thị tổng số giảng viên
        lblTotal.setText("Số lượng: " + LecturerList.size());
    }
    
    // Lấy thông tin giảng viên được chọn từ bảng
    public Lecturer getSelected() {
        int index = tblLecturer.getSelectedRow(); // Lấy chỉ số dòng được chọn
        if (index == -1) { //Không có dòng nào được chọn
            return null; //Trả về null
        } else {
            return LecturerList.get(index); // Trả về đối tượng giảng viên ở dòng được chọn

        }
    }
    
    //Phương thức mở hộp thoại thêm giảng viên
    public void openInsertDialog() {
        // Sử dụng thư viện swing modal của DJ Raven

        // Tạo option cho ModalDialog
        Option option = ModalDialog.createOption();

        // Thêm các cài đặt cho ModalDialog
        option.getLayoutOption().setSize(-1, 1f)                // Kích thước
                .setLocation(Location.TRAILING, Location.TOP)   // Vị trí xuất hiện
                .setAnimateDistance(0.7f, 0);                   // Hoạt ảnh

        // Hiển thị ModalDialog để thêm giảng viên mới
        // ModalDialog.showModal([Lớp cha], new SimpleModalBorder([Panel muốn hiện thị],[Tên tiêu đề]),[Cài đặt],[ID của ModalDialog]);
        String Title = "<html><h2>Add new lecturer</h2></html>";
        ModalDialog.showModal(this, new SimpleModalBorder(new LecturerInsertDialog(this), Title), option, "LecturerInsert");
    }
    
    // Phương thức mở hộp thoại cập nhật giảng viên
    public void openUpdateDialog() {
        // Sử dụng thư viện swing modal của DJ Raven
        // Kiểm tra xem đã chọn dòng nào trong bảng chưa
        if (tblLecturer.getSelectedRow() == -1) {
            Toast.show(this, Toast.Type.WARNING, "Please select a lecturer to update");
        } else {
            // Tạo option cho ModalDialog
            Option option = ModalDialog.createOption();

            // Thêm các cài đặt cho ModalDialog
            option.getLayoutOption().setSize(-1, 1f)                // Kích thước
                    .setLocation(Location.TRAILING, Location.TOP)   // Vị trí xuất hiện
                    .setAnimateDistance(0.7f, 0);                   // Hoạt ảnh

            // Hiển thị ModalDialog để cập nhật thông tin giảng viên
            // ModalDialog.showModal([Lớp cha], new SimpleModalBorder([Panel muốn hiện thị],[Tên tiêu đề]),[Cài đặt],[ID của ModalDialog]);
            String nameTitle = "<html><h2>Update lecturer information</h2></html>";
            ModalDialog.showModal(this, new SimpleModalBorder(new LecturerUpdateDialog(this), nameTitle), option, "LecturerUpdate");
        }
    }
    
    // Phương thức xóa giảng viên
    public void delete() {
        // Kiểm tra xem đã chọn dòng nào trong bảng chưa
        if (tblLecturer.getSelectedRow() == -1) {
            Toast.show(this, Toast.Type.WARNING, "Please select a lecturer to delete");
        } else {
            // Hiển thị hộp thoại xác nhận xóa
            int output = JOptionPane.showConfirmDialog(this,
                    "       Are you sure you want to delete this lecturer ?\n"
                    + "Data will be permanently deleted and cannot be recovered.",
                    "Confirm",
                    JOptionPane.YES_NO_OPTION);
            // Nếu người dùng xác nhận xóa
            if (output == JOptionPane.YES_OPTION) {
                // Xóa giảng viên được chọn
                LecturerDAO.getInstance().delete(getSelected());
                // Tải lại bảng dữ liệu
                loadTableAll();
                // Hiển thị thông báo thành công
                Toast.show(this, Toast.Type.SUCCESS, "Deleted successfully");
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

        lblTitle = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        txtSearch = new javax.swing.JTextField();
        btnNew = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblLecturer = new javax.swing.JTable();
        btnCSV = new javax.swing.JButton();
        btnExcel = new javax.swing.JButton();
        btnPDF = new javax.swing.JButton();
        lblTotal = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblTitle.setText("Lecturer information");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "#" }));

        btnNew.setText("New");
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });

        btnEdit.setText("Edit");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        tblLecturer.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblLecturer);

        btnCSV.setText("CSV");

        btnExcel.setText("Excel");

        btnPDF.setText("PDF");

        lblTotal.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblTotal.setText("Total: 0");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 74, Short.MAX_VALUE)
                        .addComponent(btnNew)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEdit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDelete))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTitle)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnCSV)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnExcel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnPDF)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNew)
                    .addComponent(btnEdit)
                    .addComponent(btnDelete))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 431, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCSV)
                    .addComponent(btnExcel)
                    .addComponent(btnPDF)
                    .addComponent(lblTotal))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        // TODO add your handling code here:
        openInsertDialog();
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // TODO add your handling code here:
        openUpdateDialog();
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        delete();
    }//GEN-LAST:event_btnDeleteActionPerformed

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
            java.util.logging.Logger.getLogger(LecturerForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LecturerForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LecturerForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LecturerForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LecturerForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCSV;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnExcel;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnPDF;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JTable tblLecturer;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
