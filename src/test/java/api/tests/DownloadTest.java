package api.tests;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.io.File;

import static utils.PdfUtils.savePdf;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class DownloadTest {
    @Test
    void downloadTest() {
        String endpointURI = "https://cdn.tbank.ru/static/documents/9e3b3324-f3a4-4060-927b-9147ede2958c.pdf";
        String downloadDir = "downloads/";
        String fileName = downloadDir + "TbankDownloaded.pdf";
        // Убедитесь, что папка существует
        new File(downloadDir).mkdirs();

        Response response = given()
                .when()
                .get(endpointURI)
                .then()
                .contentType("application/pdf")
                .statusCode(200)
                .extract().response();

        savePdf(response, fileName);

        File downloadFile = new File(fileName);
        assertThat(downloadFile).exists();
    }
}
