package com.example.demo.commons;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class ExcelExtract {

    public static List<List<Map<String, String>>> getData(String sheetName) throws IOException {
// fileInputStream argument
        ArrayList<String> a = new ArrayList<String>();

        FileInputStream fis = new FileInputStream("C:\\Users\\saila\\Documents\\Book1.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(fis);

        List<String> headers = new ArrayList<>();

        List<List<Map<String, String>>> mainList = new ArrayList<List<Map<String, String>>>();

        int sheets = workbook.getNumberOfSheets();

        for (int i = 0; i < sheets; i++) {
            if (workbook.getSheetName(i).equalsIgnoreCase(sheetName)) {
                XSSFSheet sheet = workbook.getSheetAt(i);

                Iterator<Row> rows = sheet.iterator();
                Row firstrow = rows.next();
                Iterator<Cell> ce = firstrow.cellIterator();
                while (ce.hasNext()) {
                    Cell value = ce.next();
                    headers.add(value.toString());

                }
                while (rows.hasNext()) {
                    List<Map<String, String>> listMap = new ArrayList<Map<String, String>>();
                    Row currentRow = rows.next();

                    Iterator<Cell> ces = currentRow.cellIterator();
                    List<String> correspondingData = new ArrayList<>();

                    while (ces.hasNext()) {
                        Cell value = ces.next();
                    }

                    for (int z = 0; z < correspondingData.size(); z++) {
                        Map<String, String> map = new HashMap<String, String>();
                        map.put(headers.get(z), correspondingData.get(z));
                        listMap.add(map);

                    }
                    mainList.add(listMap);

                }

            }
        }
        return mainList;

    }

    public static void main(String[] args) throws IOException {

        getData("sheet1");

    }
}
