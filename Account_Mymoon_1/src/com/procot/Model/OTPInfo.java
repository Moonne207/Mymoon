/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.procot.Model;

/**
 *
 * @author anh74
 */

public class OTPInfo {
    private String otp;
    private long timestamp; // Timestamp theo giây

    public OTPInfo(String otp, long timestamp) {
        this.otp = otp;
        this.timestamp = timestamp;
    }

    public String getOtp() {
        return otp;
    }

    public long getTimestamp() {
        return timestamp;
    }
    public boolean isExpired(int expirationTime) {
        long currentTimestamp = System.currentTimeMillis() / 1000;
        return (currentTimestamp - timestamp) > expirationTime;
    }
}
