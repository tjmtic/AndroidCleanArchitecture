package com.farhan.tanvir.androidcleanarchitecture.util;

public interface QRCodeFoundListener {
    void onQRCodeFound(String qrCode);
    void qrCodeNotFound();
}
