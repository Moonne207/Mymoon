/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.procot.View;

import com.procot.DAO.accountDAO;
import com.procot.Util.tableUtil;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.procot.Model.accountModel;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import raven.modal.ModalDialog;
import raven.modal.Toast;
import raven.modal.component.SimpleModalBorder;
import raven.modal.option.Location;
import raven.modal.option.Option;

/**
 *
 * @author skmyg
 */
public class accountForm extends javax.swing.JPanel {

    /**
     * Creates new form classForm
     */
    ArrayList<accountModel> dataList;

    public accountForm() {
        initComponents();
        loadTableAll();
    }

    // Phương thức in tất cả những thứ cần thiết lên table
    public void loadTableAll() {
        String[] column = new String[]{"Tên tài khoản", "Email","Tên đăng nhập","Mật khẩu","Vai trò"};   // Ghi tên bạn muốn cho từng column table
        tableUtil.loadTableColumn(tblAccount, column);                                // Thêm tên cho từng column table
        dataList = accountDAO.getInstance().selectAll();                              // Lấy dự liệu từ Database và gán vào ArrayList
        // Thêm dự liệu vào table
        tableUtil.loadTableData(tblAccount, dataList, am -> new Object[]{
            am.getAccountName(),
            am.getEmail(),
            am.getUserName(),
            am.getPassword(),
            am.getroleName(),
        });
        tableUtil.rendererTable(tblAccount);                      // Căn lề cho dự liệu trong table
        tblAccount.setAutoCreateRowSorter(true);                  // Tự động sắp xếp theo thứ tự [A-Z] và ngược lại
        txtSearch.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Tìm kiếm"); // Đặt placehoder cho textfiel
        txtSearch.putClientProperty(FlatClientProperties.TEXT_FIELD_LEADING_ICON, new FlatSVGIcon("Icon/search.svg"));
        lblTotal.setText("Số lượng: " + dataList.size()); // Tính só lượng dự liệu
        btnAdd.setIcon(new FlatSVGIcon("Icon/plus.svg"));
        btnEdit.setIcon(new FlatSVGIcon("Icon/edit.svg"));
        btnDelete.setIcon(new FlatSVGIcon("Icon/trash.svg"));
    }

    // Phương thức lấy dự liệu được chọn từ table
    public accountModel getSelected() {
        int index = tblAccount.getSelectedRow(); // Lấy dòng được chọn
        if (index == -1) {
            return null;
        } else {
            return dataList.get(index);
        }
    }

    // Phương thức search dự liệu
    public void search() {
        String select = (String) cbxSearch.getSelectedItem();
        String keyword = txtSearch.getText().toLowerCase();
        switch (select) {
            case "Tất cả":
                dataList = accountDAO.getInstance().searchAll(keyword);
                break;
            case "#":
                dataList = accountDAO.getInstance().search("Malop", keyword);
                break;
            case "Tên tài khoản":
                dataList = accountDAO.getInstance().search("TenLop", keyword);
                break;
            case "Email":
                dataList = accountDAO.getInstance().search("ChuyenNganh", keyword);
                break;
            case "":
                dataList = accountDAO.getInstance().search("ChuyenNganh", keyword);
                break;
        }

        tableUtil.loadTableData(tblAccount, dataList, am -> new Object[]{
            am.getAccountName(),
            am.getEmail(),
            am.getUserName(),
            am.getPassword(),
            am.getroleName(),
        });
    }

    // Phương thức mở bản nhập dự liệu
    public void openInsertDialog() {
        // Sử dụng thư viện swing modal của DJ Raven

        // Tạo option cho ModalDialog
        Option option = ModalDialog.createOption();

        // Thêm các cài đặt cho ModalDialog
        option.getLayoutOption().setSize(-1, 1f)                // Kích thước
                .setLocation(Location.TRAILING, Location.TOP)   // Vị trí xuất hiện
                .setAnimateDistance(0.7f, 0);                   // Hoạt ảnh

        // Tạo ModalDialog và hiện thị nó lên
        // ModalDialog.showModal([Lớp cha], new SimpleModalBorder([Panel muốn hiện thị],[Tên tiêu đề]),[Cài đặt],[ID của ModalDialog]);
        String nameTitle = "<html><h2>Add new user</h2></html>";
        ModalDialog.showModal(this, new SimpleModalBorder(new accountInsertDialog(this), nameTitle), option, "accountInsert");
    }

    // Phương thức mở bản sửa dự liệu
    public void openUpdateDialog() {
        // Sử dụng thư viện swing modal của DJ Raven
        if (tblAccount.getSelectedRow() == -1) {
            Toast.show(this, Toast.Type.WARNING, "Vui lòng chọn người dùng để sửa");
        } else {
            // Tạo option cho ModalDialog
            Option option = ModalDialog.createOption();

            // Thêm các cài đặt cho ModalDialog
            option.getLayoutOption().setSize(-1, 1f) // Kích thước
                    .setLocation(Location.TRAILING, Location.TOP) // Vị trí xuất hiện
                    .setAnimateDistance(0.7f, 0);                   // Hoạt ảnh

            // Tạo ModalDialog và hiện thị nó lên
            // ModalDialog.showModal([Lớp cha], new SimpleModalBorder([Panel muốn hiện thị],[Tên tiêu đề]),[Cài đặt],[ID của ModalDialog]);
            String nameTitle = "<html><h2>Edit user information</h2></html>";
            ModalDialog.showModal(this, new SimpleModalBorder(new accountUpdateDialog(this), nameTitle), option, "accountUpdate");
        }
    }

    // Phương thức xóa dự liệu
        public void delete() {
    if (tblAccount.getSelectedRow() == -1) {
        Toast.show(this, Toast.Type.WARNING, "Please select the user to delete");
        return;
    }

    int output = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete this account?\n"
                + "The data will be permanently deleted and cannot be recovered!",
                "Confirm",
            JOptionPane.YES_NO_OPTION);

    if (output == JOptionPane.YES_OPTION) {
        try {
            accountModel selectedAccount = getSelectedAccount(); // Lấy model của tài khoản
            if (selectedAccount == null) {
                Toast.show(this, Toast.Type.ERROR, "No account found to delete!");
                return;
            }

            int result = accountDAO.getInstance().delete(selectedAccount);
            if (result > 0) {
                loadTableAll();
                Toast.show(this, Toast.Type.SUCCESS, "Delete successful");
            } else {
                Toast.show(this, Toast.Type.WARNING, "Account does not exist or cannot be deleted!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.show(this, Toast.Type.ERROR, "Error: " + e.getMessage());
        }
    }
}
        private accountModel getSelectedAccount() {
    int selectedRow = tblAccount.getSelectedRow();
    if (selectedRow == -1) {
        return null;
    }
    
    String username = tblAccount.getValueAt(selectedRow, 1).toString(); // Giả sử cột 1 là Username
    accountModel model = new accountModel();
    model.setUserName(username);
    return model;
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
        cbxSearch = new javax.swing.JComboBox<>();
        txtSearch = new javax.swing.JTextField();
        btnDelete = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblAccount = new javax.swing.JTable();
        lblTotal = new javax.swing.JLabel();

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Thông tin người dùng");

        cbxSearch.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cbxSearch.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả", "#", "Tên lớp", "Chuyên ngành" }));

        txtSearch.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
        });

        btnDelete.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnDelete.setText("Xóa");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnEdit.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnEdit.setText("Sửa");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnAdd.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnAdd.setText("Thêm");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        tblAccount.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "null", "null", "null", "null"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblAccount);
        if (tblAccount.getColumnModel().getColumnCount() > 0) {
            tblAccount.getColumnModel().getColumn(0).setMaxWidth(40);
        }

        lblTotal.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblTotal.setText("Số lượng: 0");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cbxSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
                        .addComponent(btnAdd)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEdit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDelete))
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(lblTotal))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDelete)
                    .addComponent(btnEdit)
                    .addComponent(btnAdd))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 387, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTotal)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
        openInsertDialog();
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // TODO add your handling code here:
        openUpdateDialog();
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        delete();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        // TODO add your handling code here:
        search();
    }//GEN-LAST:event_txtSearchKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JComboBox<String> cbxSearch;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JTable tblAccount;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
