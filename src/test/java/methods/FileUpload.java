package methods;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUpload  {

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
}
