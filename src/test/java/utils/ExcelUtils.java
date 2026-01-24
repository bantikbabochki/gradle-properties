package utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class ExcelUtils {
    /*
    Workbook - Весь файл Excel
    Sheet - Один лист (вкладка) в книге
    Row -  Строка на листе
    Cell - Ячейка в строке
     */
    public static void printText(String filePath) throws FileNotFoundException {
        try(InputStream fis = new FileInputStream(filePath);
            Workbook workbook = new XSSFWorkbook(fis)) {

            // Получаем первый лист
            Sheet sheet = workbook.getSheetAt(0);

            // Итерация по строкам и ячейкам листа
            for (Row row : sheet) {
                for(Cell cell : row) {
                    // Получаем значение ячейки в зависимости от типа данных
                    switch (cell.getCellType()) {
                        case STRING -> System.out.println(cell.getStringCellValue() + "\t");
                        case NUMERIC -> {
                            if (DateUtil.isCellDateFormatted(cell)) {
                                System.out.println(cell.getDateCellValue() + "\t");
                            } else {
                                System.out.println(cell.getNumericCellValue() + "\t");
                            }
                        }
                        case BOOLEAN -> System.out.println(cell.getBooleanCellValue() + "\t");
                        case FORMULA -> System.out.println(cell.getCellFormula() + "\t");
                        default -> System.out.println("UNKNOWN\t");
                    }
                }
                System.out.println();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean searchText(String filePath, String textToSearch) {
        try(InputStream fis = new FileInputStream(filePath);
        Workbook workbook = new XSSFWorkbook(fis)) {

            // Получаем первый лист
            Sheet sheet = workbook.getSheetAt(0);

            // Итерация по строкам и ячейкам листа
            for (Row row : sheet) {
               for (Cell cell : row) {
                   // Получаем значение ячейки
                   if (cell.getCellType().equals(CellType.STRING)) {
                       String text = cell.getStringCellValue();
                       if (text.equals(textToSearch)) {
                           return true;
                       }
                   }
               }
                System.out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
