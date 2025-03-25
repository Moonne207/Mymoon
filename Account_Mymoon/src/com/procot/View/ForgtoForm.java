/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package View;

import com.formdev.flatlaf.FlatClientProperties;
import java.awt.Color;
import java.time.Duration;
import java.time.Instant;
import java.util.Properties;
import java.util.Random;
import javax.mail.*;
import javax.mail.internet.*;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import raven.modal.Toast;


/**
 *
 * @author anh74
 */
public class ForgtoForm extends javax.swing.JPanel {

    private String generatedOTP;
    private Instant otpGeneratedTime;
    private static final long OTP_EXPIRATION_TIME = 3 * 60; // 3 phút (tính bằng giây)
    private Timer timer;
    private int timeLeft = 60;
    /**
     * Creates new form LoginForm
     */

   public ForgtoForm() {
        initComponents();
        this.putClientProperty(FlatClientProperties.STYLE, ""
                + "arc:20;"
                + "[light]background:darken(@background,3%);"
                + "[dark]background:lighten(@background,3%)");
    }

    private String generateOTP() {
        Random random = new Random();
        int otp = 1000000 + random.nextInt(9000000);
        return String.valueOf(otp);
    }

    private void sendEmail(String recipientEmail, String otpCode) {
        final String senderEmail = "anhne252@gmail.com"; // Thay email của bạn
        final String senderPassword = "uaeg uwkr gccp uzhm"; // Mật khẩu ứng dụng Google

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("Mã OTP xác nhận");
            message.setText("Mã OTP của bạn là: " + otpCode);

            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private boolean isValidInput() {
        if (txtEmail.getText().isEmpty()) {
            txtEmail.putClientProperty("JComponent.outline", "warning");
            lblEmailError.setText("Vui lòng nhập email!");
            lblEmailError.setForeground(Color.ORANGE);
            return false;
        } else {
            txtEmail.putClientProperty("JComponent.outline", "null");
            lblEmailError.setText("");
        }
        if (txtOTP.getText().isEmpty()) {
            txtOTP.putClientProperty("JComponent.outline", "warning");
            lblOTPError.setText("Vui lòng điền mã OTP!");
            lblOTPError.setForeground(Color.ORANGE);
            return false;
        } else {
            txtOTP.putClientProperty("JComponent.outline", "null");
            lblOTPError.setText("");
        }
        return true;
    }

    private void sendOTP() {
    String email = txtEmail.getText().trim();

    if (email.isEmpty()) {
        txtEmail.putClientProperty("JComponent.outline", "warning");
        lblEmailError.setText("Vui lòng nhập email!");
        lblEmailError.setForeground(Color.ORANGE);
        return;
    }

    btnSendOTP.setEnabled(false);
    timeLeft = 60;

    // Bắt đầu đếm ngược
    timer = new Timer(1000, e -> {
        timeLeft--;
        btnSendOTP.setText(timeLeft + "s");
        if (timeLeft <= 0) {
            timer.stop();
            btnSendOTP.setText("Gửi mã");
            btnSendOTP.setEnabled(true);
        }
    });
    timer.start();

    new Thread(() -> {
        try {
            if (!DAO.accountDAO.getInstance().forgetPassword(email)) {
                SwingUtilities.invokeLater(() -> {
                    txtEmail.putClientProperty("JComponent.outline", "error");
                    lblEmailError.setText("Email không tồn tại trong hệ thống!");
                    lblEmailError.setForeground(Color.RED);

                    timer.stop();
                    btnSendOTP.setText("Gửi mã");
                    btnSendOTP.setEnabled(true);
                });
                return;
            }

            // Tạo OTP mới
            generatedOTP = generateOTP();
            otpGeneratedTime = Instant.now();

            // Debug: In OTP để kiểm tra

            sendEmail(email, generatedOTP);

            SwingUtilities.invokeLater(() -> {
                lblEmailError.setText("");
                Toast.show(btnSendOTP, Toast.Type.SUCCESS, "Mã OTP đã được gửi đến email!");
            });
        } catch (Exception ex) {
            SwingUtilities.invokeLater(() -> {
                lblEmailError.setText(ex.getMessage());
                lblEmailError.setForeground(Color.RED);

                timer.stop();
                btnSendOTP.setText("Gửi mã");
                btnSendOTP.setEnabled(true);
            });
        }
    }).start();
}


    private void verifyOTP() {
    String inputOTP = txtOTP.getText().trim(); // Trim khoảng trắng
    String email = txtEmail.getText().trim();

    // Kiểm tra OTP đã được tạo chưa
    if (generatedOTP == null) {
        txtOTP.putClientProperty("JComponent.outline", "error");
        lblOTPError.setText("Vui lòng yêu cầu mã OTP trước!");
        lblOTPError.setForeground(Color.RED);
        return;
    }

    // Kiểm tra thời gian hết hạn
    if (otpGeneratedTime == null || Duration.between(otpGeneratedTime, Instant.now()).getSeconds() > OTP_EXPIRATION_TIME) {
        txtOTP.putClientProperty("JComponent.outline", "warning");
        lblOTPError.setText("Mã OTP đã hết hạn! Vui lòng yêu cầu mã mới.");
        lblOTPError.setForeground(Color.ORANGE);
        generatedOTP = null; // Reset OTP
        otpGeneratedTime = null;
        return;
    }

    // Debug: In giá trị OTP (chỉ dùng khi kiểm tra)
    System.out.println("Done");
    System.out.println("Done");

    // So sánh OTP
    if (inputOTP.equals(generatedOTP)) {
        Toast.show(this, Toast.Type.SUCCESS, "Xác minh OTP thành công!");
        generatedOTP = null; // Xóa OTP sau khi thành công
        otpGeneratedTime = null;
        main.getInstance().resetPasswordView(email);
    } else {
        txtOTP.putClientProperty("JComponent.outline", "error");
        lblOTPError.setText("Mã OTP không đúng!");
        lblOTPError.setForeground(Color.RED);
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
        txtEmail = new javax.swing.JTextField();
        btncontiue = new javax.swing.JButton();
        lblPasswordError = new javax.swing.JLabel();
        lblUsernameError = new javax.swing.JLabel();
        btnSendOTP = new javax.swing.JButton();
        lblBack = new javax.swing.JLabel();
        lblEmailError = new javax.swing.JLabel();
        lblOTPError = new javax.swing.JLabel();
        txtOTP = new javax.swing.JTextField();

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel1.setText("Forgto Password");

        txtEmail.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        txtEmail.setText("\n\n");
        txtEmail.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(102, 102, 102)));

        btncontiue.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btncontiue.setText("Continue");
        btncontiue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncontiueActionPerformed(evt);
            }
        });

        btnSendOTP.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnSendOTP.setText("Send");
        btnSendOTP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSendOTPActionPerformed(evt);
            }
        });

        lblBack.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblBack.setForeground(javax.swing.UIManager.getDefaults().getColor("Button.default.background"));
        lblBack.setText("<html><u>Back to login</u></html>");
        lblBack.setToolTipText("");
        lblBack.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblBack.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblBackMouseClicked(evt);
            }
        });

        txtOTP.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        txtOTP.setText("\n\n");
        txtOTP.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(102, 102, 102)));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblUsernameError, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblEmailError, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(lblPasswordError, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtOTP, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnSendOTP, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblOTPError, javax.swing.GroupLayout.PREFERRED_SIZE, 377, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btncontiue, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6))
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 6, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblBack, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(161, 161, 161))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblUsernameError)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblEmailError)
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSendOTP, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtOTP, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblOTPError)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(82, 82, 82)
                        .addComponent(lblPasswordError)
                        .addGap(36, 36, 36))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btncontiue, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addComponent(lblBack, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(40, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btncontiueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncontiueActionPerformed
        // TODO add your handling code here:
        if (isValidInput()) {
            verifyOTP();
        }
    }//GEN-LAST:event_btncontiueActionPerformed

    private void lblBackMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBackMouseClicked
        // TODO add your handling code here:
        main.getInstance().loginView();
    }//GEN-LAST:event_lblBackMouseClicked

    private void btnSendOTPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSendOTPActionPerformed
        sendOTP();
    }//GEN-LAST:event_btnSendOTPActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSendOTP;
    private javax.swing.JButton btncontiue;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lblBack;
    private javax.swing.JLabel lblEmailError;
    private javax.swing.JLabel lblOTPError;
    private javax.swing.JLabel lblPasswordError;
    private javax.swing.JLabel lblUsernameError;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtOTP;
    // End of variables declaration//GEN-END:variables
}
