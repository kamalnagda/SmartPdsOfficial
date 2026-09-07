package com.SmartPdsOfficial.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.SmartPdsOfficial.model.Beneficiary;

@Service
public class ExcelDataService {
	
	private String filePath = "src/main/resources/beneficiaries.xlsx" ;
	
//	public List<Beneficiary> readBeneficiariesFromExcel() {
//		
//		List<Beneficiary> beneficiaries = new ArrayList<>();
//		
//        try (FileInputStream file = new FileInputStream(filePath);
//                Workbook workbook = new XSSFWorkbook(file)) {
//
//               Sheet sheet = workbook.getSheetAt(0);
//
//               // Skip title row and header row
//               for (int i = 2; i <= sheet.getLastRowNum(); i++) {
//
//                   Row row = sheet.getRow(i);
//
//                   if (row == null) {
//                       continue;
//                   }
//
//                   Beneficiary beneficiary = new Beneficiary();
//
//                   beneficiary.setSn((int) row.getCell(0).getNumericCellValue());
//                   beneficiary.setFpsCode(getCellValue(row.getCell(1)));
//                   beneficiary.setPanchayat(getCellValue(row.getCell(2)));
//                   beneficiary.setVillage(getCellValue(row.getCell(3)));
//                   beneficiary.setFamilyId(getCellValue(row.getCell(4)));
//                   beneficiary.setHeadOfFamily(getCellValue(row.getCell(5)));
//                   beneficiary.setMobile(getCellValue(row.getCell(6)));
//
//                   beneficiaries.add(beneficiary);
//               }
//
//       } catch (IOException e) {
//           e.printStackTrace();
//       }
//		
//        return beneficiaries;
//		
//	}
    private String getCellValue(Cell cell) {

        if (cell == null) {
            return "";
        }

        cell.setCellType(CellType.STRING);

        return cell.getStringCellValue().trim();
    }
    
    //for render
    public List<Beneficiary> getBeneficiariesByFpsId(String fpsId) {

        List<Beneficiary> beneficiaries = new ArrayList<>();

        long startTime = System.currentTimeMillis();

        System.out.println("1. Starting Excel reading");

        try {

            System.out.println("1.1 Before getting ClassLoader");

            ClassLoader classLoader = getClass().getClassLoader();

            System.out.println("1.2 ClassLoader received");

            System.out.println("1.3 Before getResourceAsStream");

            InputStream file = classLoader
                    .getResourceAsStream("beneficiaries.xlsx");

            System.out.println("1.4 After getResourceAsStream");

            if (file == null) {
                throw new RuntimeException(
                        "beneficiaries.xlsx not found!"
                );
            }

            System.out.println("2. Excel file stream opened");

            System.out.println("2.1 Before XSSFWorkbook");

            Workbook workbook = new XSSFWorkbook(file);

            System.out.println("3. Workbook loaded successfully");

            Sheet sheet = workbook.getSheetAt(0);

            System.out.println("4. Total rows: "
                    + sheet.getLastRowNum());

            for (int i = 2; i <= sheet.getLastRowNum(); i++) {

                Row row = sheet.getRow(i);

                if (row == null) {
                    continue;
                }

                String currentFpsId =
                        getCellValue(row.getCell(1));

                if (!currentFpsId.equals(fpsId)) {
                    continue;
                }

                Beneficiary beneficiary = new Beneficiary();

                beneficiary.setSn(
                        (int) row.getCell(0).getNumericCellValue()
                );

                beneficiary.setFpsCode(currentFpsId);

                beneficiary.setPanchayat(
                        getCellValue(row.getCell(2))
                );

                beneficiary.setVillage(
                        getCellValue(row.getCell(3))
                );

                beneficiary.setFamilyId(
                        getCellValue(row.getCell(4))
                );

                beneficiary.setHeadOfFamily(
                        getCellValue(row.getCell(5))
                );

                beneficiary.setMobile(
                        getCellValue(row.getCell(6))
                );

                beneficiaries.add(beneficiary);

                // Progress log every 5000 rows
                if (i % 5000 == 0) {
                    System.out.println(
                            "Processed rows: " + i
                    );
                }
            }

            workbook.close();
            file.close();

            System.out.println("5. Excel processing completed");

        } catch (Exception e) {

            System.err.println("ERROR READING EXCEL:");
            e.printStackTrace();

        }

        long endTime = System.currentTimeMillis();

        System.out.println(
                "Excel reading time: "
                        + (endTime - startTime) + " ms"
        );

        System.out.println(
                "Total Coupon in Excel data service: "
                        + beneficiaries.size()
        );

        return beneficiaries;
    }

//for local host
//    public List<Beneficiary> getBeneficiariesByFpsId(String fpsId) {
//
//        List<Beneficiary> beneficiaries = new ArrayList<>();
//
//        try (FileInputStream file = new FileInputStream(filePath);
//             Workbook workbook = new XSSFWorkbook(file)) {
//
//            Sheet sheet = workbook.getSheetAt(0);
//
//            // Skip title row and header row
//            for (int i = 2; i <= sheet.getLastRowNum(); i++) {
//
//                Row row = sheet.getRow(i);
//
//                if (row == null) {
//                    continue;
//                }
//
//                String currentFpsId = getCellValue(row.getCell(1));
//                
//                
//
//                // Only add matching FPS ID
//                if (!currentFpsId.equals(fpsId)) {
//                	
//                    continue;
//                }
//         
//                Beneficiary beneficiary = new Beneficiary();
//
//                beneficiary.setSn((int) row.getCell(0).getNumericCellValue());
//                beneficiary.setFpsCode(currentFpsId);
//                beneficiary.setPanchayat(getCellValue(row.getCell(2)));
//                beneficiary.setVillage(getCellValue(row.getCell(3)));
//                beneficiary.setFamilyId(getCellValue(row.getCell(4)));
//                beneficiary.setHeadOfFamily(getCellValue(row.getCell(5)));
//                beneficiary.setMobile(getCellValue(row.getCell(6)));
//
//                beneficiaries.add(beneficiary);
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        System.out.println("Total Coupon : in Excle data service:"+beneficiaries.size());
//        return beneficiaries;
//    }
  
    public byte[] createExcelFile(List<Beneficiary> beneficiaries)
            throws IOException {

        try (XSSFWorkbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream outputStream =
                     new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet("Non Visited");

            Row header = sheet.createRow(0);

            header.createCell(0).setCellValue("S.N.");
            header.createCell(1).setCellValue("FPS Code");
            header.createCell(2).setCellValue("Panchayat");
            header.createCell(3).setCellValue("Village");
            header.createCell(4).setCellValue("Family ID");
            header.createCell(5).setCellValue("Head of Family");
            header.createCell(6).setCellValue("Mobile");

            int rowNumber = 1;

            for (Beneficiary b : beneficiaries) {

                Row row = sheet.createRow(rowNumber++);

                row.createCell(0).setCellValue(b.getSn());
                row.createCell(1).setCellValue(b.getFpsCode());
                row.createCell(2).setCellValue(b.getPanchayat());
                row.createCell(3).setCellValue(b.getVillage());
                row.createCell(4).setCellValue(b.getFamilyId());
                row.createCell(5).setCellValue(b.getHeadOfFamily());
                row.createCell(6).setCellValue(b.getMobile());
            }

            workbook.write(outputStream);

            return outputStream.toByteArray();
        }
    }
}
