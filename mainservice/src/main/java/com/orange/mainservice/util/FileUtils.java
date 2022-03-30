package com.orange.mainservice.util;

import com.orange.mainservice.exception.FileDownloadException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileUtils {

    public static byte[] downloadFile(String fileUrl) {
        try {
            URLConnection conn = new URL(fileUrl).openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.connect();

            var byteArrayOutputStream = new ByteArrayOutputStream();
            IOUtils.copy(conn.getInputStream(), byteArrayOutputStream);

            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new FileDownloadException(fileUrl);
        }
    }
}
