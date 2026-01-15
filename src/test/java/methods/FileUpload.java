package methods;

import base.BaseUITest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUpload {
    private final WebDriver driver;

    public FileUpload(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Получить абсолютный путь к файлу из resources
     */
    public static String getPath(String fileName) throws URISyntaxException, IOException {
        //Получаем ресурс по имени файла
        URL resource = FileUpload.class.getClassLoader().getResource(fileName);
        if (resource == null) {
            throw new FileNotFoundException("Файл " + fileName + "не найден в ресурсах");
        }
        //Преобразуем URL в Path
        Path path = Paths.get(resource.toURI());
        // Читаем содержимое
        return path.toAbsolutePath().toString();
    }

    /**
     * Загрузить файл в input - работает локально и удаленно
     */
    public void uploadFile(WebElement fileInput, String absolutePath) {
        File file = new File(absolutePath);
        if (!file.exists()) {
            throw new IllegalArgumentException("Файл не найден: " + absolutePath);
        }
        if (BaseUITest.IS_REMOTE) {
            ((RemoteWebDriver) driver).setFileDetector(new LocalFileDetector());
            System.out.println("RemoteWebDriver detected, using LocalFileDetector");
        } else {
            System.out.println("Local WebDriver detected, uploading directly");
        }
        fileInput.sendKeys(absolutePath);
        System.out.println("Файл загружен: " + file.getName());
    }

    /**
     * Удобный метод: получить путь и сразу выгрузить файл из resources
     */
    public void uploadFileFromResources(WebElement fileInput, String fileName)
            throws URISyntaxException, IOException {
        String absolutePath = getPath(fileName);
        uploadFile(fileInput, absolutePath);
    }
}
    /*
     public static String readFileAsString(String fileName) throws IOException, URISyntaxException {
        Path path = Paths.get(FileUpload.class.getClassLoader().getResource(fileName).toURI());
        return Files.readString(path);
    }

    public static List<String> readFileLines(String fileName) throws IOException, URISyntaxException {
        Path path = Paths.get(FileUpload.class.getClassLoader().getResource(fileName).toURI());
        return Files.readAllLines(path);
    }

    public static Path copyToTemp(String fileName) throws IOException, URISyntaxException {
        Path source = Paths.get(FileUpload.class.getClassLoader().getResource(fileName).toURI());
        Path temp = Files.createTempFile("upload-", "-" + fileName);
        return Files.copy(source, temp, StandardCopyOption.REPLACE_EXISTING);
    }

    public static boolean exists(String fileName) {
        return FileUpload.class.getClassLoader().getResource(fileName) != null;
    }
     */

