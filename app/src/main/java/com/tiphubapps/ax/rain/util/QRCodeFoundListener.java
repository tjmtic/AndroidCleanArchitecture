package com.tiphubapps.ax.rain.util;

public interface QRCodeFoundListener {
    void onQRCodeFound(String qrCode);
    void qrCodeNotFound();
}
