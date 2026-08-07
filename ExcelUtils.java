package utilities;

import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {

    // Write hotel data (List<Map<String,String>>) into Excel
    public void writeHotelData(List<Map<String, String>> hotelData, String fileName) throws Exception {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Hotels");

        // Header row
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Name");
        header.createCell(1).setCellValue("Price per Night");
        header.createCell(2).setCellValue("Taxes");
        header.createCell(3).setCellValue("Total Price");

        // Data rows
        int rowNum = 1;
        for (Map<String, String> hotel : hotelData) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(hotel.get("name"));
            row.createCell(1).setCellValue(hotel.get("pricePerNight"));
            row.createCell(2).setCellValue(hotel.get("taxes"));
            row.createCell(3).setCellValue(hotel.get("totalPrice"));
        }

        // Autosize columns
        for (int i = 0; i < 4; i++) {
            sheet.autoSizeColumn(i);
        }

        // Write to file
        FileOutputStream fos = new FileOutputStream(fileName);
        workbook.write(fos);
        fos.close();
        workbook.close();

        System.out.println("Hotel data written to " + fileName);
    }

    // Write cruise data (multiple lists) into Excel
    public void writeCruiseData(List<String> inclusions, List<String> exclusions,
                                List<String> paymentPolicy, List<String> cancellationPolicy,
                                String fileName) throws Exception {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Cruises");

        // Inclusions
        int rowNum = 0;
        rowNum = writeListToSheet(sheet, "Inclusions", inclusions, rowNum);

        // Exclusions
        rowNum = writeListToSheet(sheet, "Exclusions", exclusions, rowNum + 2);

        // Payment Policy
        rowNum = writeListToSheet(sheet, "Payment Policy", paymentPolicy, rowNum + 2);

        // Cancellation Policy
        rowNum = writeListToSheet(sheet, "Cancellation Policy", cancellationPolicy, rowNum + 2);

        // Autosize
        sheet.autoSizeColumn(0);

        // Write to file
        FileOutputStream fos = new FileOutputStream(fileName);
        workbook.write(fos);
        fos.close();
        workbook.close();

        System.out.println("Cruise data written to " + fileName);
    }

    // Helper method
    private int writeListToSheet(Sheet sheet, String title, List<String> data, int startRow) {
        Row titleRow = sheet.createRow(startRow);
        titleRow.createCell(0).setCellValue(title);

        int rowNum = startRow + 1;
        for (String item : data) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(item);
        }
        return rowNum;
    }
}