package api.utils;

import io.restassured.response.Response;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class PdfUtils {
    public static void savePdf(Response response, String fileName) {
        if (response != null) {
            // Получение содержимого ответа в виде InputStream
            try (InputStream inputStream = response.getBody().asInputStream()) {
                // Создание файла для сохранения PDF
                OutputStream outputStream = new FileOutputStream(fileName);

                // Копирование содержимого InputStream в файл
                int bytesRead;
                byte[] buffer = new byte[4096];
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }

                // Закрытие потоков
                inputStream.close();
                outputStream.close();

                System.out.println("PDF успешно сохранен в файл: " + fileName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
