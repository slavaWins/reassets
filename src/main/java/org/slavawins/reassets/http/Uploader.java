package org.slavawins.reassets.http;

import org.slavawins.reassets.ConfigHelper;
import org.slavawins.reassets.Reassets;
import org.slavawins.reassets.contracts.UploadResponseContract;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class Uploader {

    public static UploadResponseContract SendRP() throws IOException {

        UploadResponseContract responseContract = new UploadResponseContract();



        URL url = new URL(ConfigHelper.GetConfig().getString("upload-url"));


        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");



        // Установка параметров запроса
        connection.setRequestProperty("Content-Type", "multipart/form-data;");
        connection.setRequestProperty("Content-Disposition", "attachment; filename=\"resourcepack.zip\"");
        // Создание потока для записи данных в соединение
        // OutputStream outputStream = connection.getOutputStream();

       // System.out.println("----------send x3");
        // Чтение файла и запись его в поток
        File file = new File(Reassets.myDataFolder.getPath() + "/resourcepack.zip");


        DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
        outputStream.writeBytes("Content-Disposition: form-data; name=\"file\"; filename=\"" + file.getName() + "\"\r\n");
        outputStream.writeBytes("Content-Type: application/octet-stream\r\n");

        outputStream.writeBytes("\r\n");
        // outputStream.write("Content-Disposition: form-data; name=\"file\"; filename=\"" + file.getName() + "\"\r\n");

        if (!file.exists()) {
            System.out.println("----------send ERROR FILE: " + file.getAbsolutePath());
            return UploadResponseContract.Error("Not file rp in " + file.getAbsolutePath());
        }

        //System.out.println("----------send x4");
        FileInputStream fileInputStream = new FileInputStream(file);

        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = fileInputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }

        //System.out.println("----------send x5");
        // Закрытие потока и соединения
        fileInputStream.close();
        outputStream.close();

        // Получение ответного кода от сервера
        int responseCode = connection.getResponseCode();

        //System.out.println("----------send x5");
        // Проверка успешности запроса
        if (responseCode == HttpURLConnection.HTTP_OK) {
            // Чтение ответа сервера
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String response = reader.readLine();

            // Обработка ответа
          //  System.out.println("----------RESPONSE SERVER:");
          //  System.out.println(response);

            // Закрытие потока чтения
            reader.close();

            responseContract = UploadResponseContract.Parse(response);
        } else {
            // Обработка ошибки
            System.out.println("Error conection send, code: " + responseCode);
        }

        // Закрытие соединения
        connection.disconnect();

        return responseContract;
    }

}
