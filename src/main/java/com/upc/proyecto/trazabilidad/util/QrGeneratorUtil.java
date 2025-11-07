package com.upc.proyecto.trazabilidad.util;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.client.j2se.MatrixToImageWriter;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

public class QrGeneratorUtil {

    public static String generarQr(String texto, String nombreArchivo) throws Exception {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(texto, BarcodeFormat.QR_CODE, 300, 300);

        String rutaCarpeta = "qrcodes/";
        Path directorio = FileSystems.getDefault().getPath(rutaCarpeta);
        if (!Files.exists(directorio)) {
            Files.createDirectories(directorio);
        }

        String nombre = nombreArchivo + ".png";
        Path path = directorio.resolve(nombre);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

        return rutaCarpeta + nombre; // Ruta a guardar en BD
    }

    public static String generarTextoQrUnico() {
        return "EMPAQUE-" + UUID.randomUUID();
    }
}
