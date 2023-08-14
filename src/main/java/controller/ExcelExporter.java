package controller;import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelExporter {

    public static void exportToExcel(JTable table, String[] columnNames, String filename) throws IOException {
        // Create a workbook
        Workbook workbook = new XSSFWorkbook();

        // Create a sheet
        Sheet sheet = workbook.createSheet("Data");

        // Write column names to the sheet
        Row headerRow = sheet.createRow(0);
        for (int colIdx = 0; colIdx < columnNames.length; colIdx++) {
            Cell cell = headerRow.createCell(colIdx);
            cell.setCellValue(columnNames[colIdx]);
        }

        // Get the table data and write it to the sheet
        for (int rowIdx = 0; rowIdx < table.getRowCount(); rowIdx++) {
            Row row = sheet.createRow(rowIdx + 1);
            for (int colIdx = 0; colIdx < table.getColumnCount(); colIdx++) {
                Cell cell = row.createCell(colIdx);
                Object value = table.getValueAt(rowIdx, colIdx);
                if (value != null) {
                    if (value instanceof String) {
                        cell.setCellValue((String) value);
                    } else if (value instanceof Number) {
                        cell.setCellValue(((Number) value).doubleValue());
                    } else {
                        cell.setCellValue(value.toString());
                    }
                }
            }
        }

        // Auto-size columns to fit content
        for (int colIdx = 0; colIdx < table.getColumnCount(); colIdx++) {
            sheet.autoSizeColumn(colIdx);
        }

        // Save the workbook to a file
        FileOutputStream outputStream = new FileOutputStream(filename);
        workbook.write(outputStream);
        outputStream.close();
    }

    public static void main(String[] args) {
        // Create a sample JTable with some data
        String[] columnNames = {"Name", "Age", "City"};
        Object[][] data = {
                {"John Doe", 30, "New York"},
                {"Jane Smith", 25, "San Francisco"},
                {"Mike Johnson", 40, "Chicago"}
        };
        JTable table = new JTable(data, columnNames);

        try {
            exportToExcel(table, columnNames, "output.xlsx");
            System.out.println("Data exported to Excel successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
