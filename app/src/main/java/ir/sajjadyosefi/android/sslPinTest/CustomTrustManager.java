package ir.sajjadyosefi.android.sslPinTest;

import android.util.Base64;

import javax.net.ssl.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class CustomTrustManager implements X509TrustManager {

    @Override
    public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
        // اگر هیچ چیزی را نمی‌خواهید تایید کنید، اینجا را خالی بگذارید یا استثنا ایجاد کنید
        throw new CertificateException("Client not trusted");
    }

    @Override
    public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
        // اگر فایل پین غیرفعال شده است، اتصال را رد کنید

        // تعریف گواهی‌نامه‌های معتبر به‌صورت SHA-256
        String expectedPin = "sha256/tr0KDvDiF22ccuLlujt0e/N9Ma0it5awTTZAokDILLiY=";

        for (X509Certificate cert : chain) {
            String pin = "sha256/" + Base64.encodeToString(cert.getSignature(), Base64.NO_WRAP);
            if (pin.equals(expectedPin)) {
                return; // اگر گواهی‌نامه معتبر باشد، ادامه دهید
            }
        }

        throw new CertificateException("Certificate pinning failure!"); // اگر هیچ کدام معتبر نبودند


    }

    @Override
    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
        return new java.security.cert.X509Certificate[0];
    }
}
