package utils;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class DocUtils {
    public static boolean searchText(String filePath, String textToFind) throws FileNotFoundException {
        try (FileInputStream fis = new FileInputStream(filePath);
             XWPFDocument document = new XWPFDocument(fis)) { //XWPFDocument - парсит .docx и даёт доступ к структуре документа
            boolean textFound = false;

            for (XWPFParagraph paragraph : document.getParagraphs()) { //XWPFParagraph - Абзац (параграф) в документе
                if (paragraph.getText().contains(textToFind)) {//paragraph.getText() — объединяет
                    // всё содержимое абзаца (включая форматирование) в одну строку
                    textFound = true;
                    break;
                }
            }

            if (textFound) {
                System.out.println("The document contains the specified text.");
                return true;
            } else {
                System.out.println("The document does not contain the specified text.");
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
