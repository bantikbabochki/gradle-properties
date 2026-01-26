package api.tests;

import api.tests.base.BaseApiTest;
import io.restassured.response.Response;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static utils.PdfUtils.savePdf;

public class DownloadTest extends BaseApiTest {
    // Константы для читаемости и поддержки
    private static final String TBANK_OFFER_PDF_URL =
            "https://cdn.tbank.ru/static/documents/9e3b3324-f3a4-4060-927b-9147ede2958c.pdf";
    private static final String DOWNLOAD_DIR = "downloads/";
    private static final String FILE_NAME = DOWNLOAD_DIR + "TbankDownloaded.pdf";

    @Test
    @DisplayName("Успешное скачивание и валидация публичной оферты Т-Банка")
    void downloadTest() throws IOException {
        // Arrange
        new File(DOWNLOAD_DIR).mkdirs();

        //Скачивание документа
        Response response = given()
                .when()
                .get(TBANK_OFFER_PDF_URL.trim())
                .then()
                .contentType("application/pdf")
                .statusCode(200)
                .extract().response();

        savePdf(response, FILE_NAME);
        File downloadFile = new File(FILE_NAME);

        //Комплексная валидация
        assertAll("Валидация скачанного PDF-документа",
                //Проверка существования файла
                () -> assertThat(downloadFile)
                        .as("Файл должен существовать")
                        .exists(),
                //Проверка размера файла
                () -> assertThat(downloadFile.length())
                        .as("Размер файла должен быть в диапазоне 50 КБ – 600 КБ (реальный размер ~180 КБ)")
                        .isBetween(50_000L, 600_000L), //50 КБ – 600 КБ (50% от текущего размера, 300% от тек.разм)
                //Проверка байтов
                () -> {
                    byte[] content = Files.readAllBytes(downloadFile.toPath());
                    assertThat(content)
                            .as("Файл должен начинаться с сигнатуры PDF")
                            .startsWith("%PDF-".getBytes(StandardCharsets.US_ASCII));
                },
                //Проверка метаданных
                () -> {
                    try (PDDocument document = PDDocument.load(downloadFile)) {
                        assertThat(document.getNumberOfPages())
                                .as("Документ должен содержать минимум 8 страницы")
                                .isGreaterThanOrEqualTo(8);
                    }
                }
        );
    }
}
