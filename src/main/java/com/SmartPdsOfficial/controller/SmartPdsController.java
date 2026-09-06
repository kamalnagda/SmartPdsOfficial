package com.SmartPdsOfficial.controller;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.SmartPdsOfficial.model.Beneficiary;
import com.SmartPdsOfficial.service.ExcelDataService;
import com.SmartPdsOfficial.service.NonVisitedService;
import com.SmartPdsOfficial.service.SmartPdsService;
import com.SmartPdsOfficial.view.SmartPdsHome;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@RestController
public class SmartPdsController {
	
	@Autowired
	private SmartPdsHome smartPdsHome ;
	@Autowired
	private SmartPdsService pdsService;
	@Autowired
	private ExcelDataService excelDataService;
	@Autowired
	private NonVisitedService nonVisitedService;
	
	
	@GetMapping(value = "/", produces = "text/html")
	public String home() {

	    return smartPdsHome.getHomePage();
	}
	
	@PostMapping(value = "/", produces = "text/html")
	public String smartPds(
	        @RequestParam String dist_code,
	        @RequestParam String fps_id,
	        @RequestParam String month,
	        @RequestParam String year,
	        HttpSession session) {

	    Set<String> visitedIds;

	    try {
	        visitedIds = pdsService.fetchVisitedIdFromWeb(
	                dist_code, fps_id, month, year);

	    } catch (Exception e) {
	        e.printStackTrace();

	        return "<h2>Error while fetching data from PDS website</h2>"
	                + "<p>" + e.getMessage() + "</p>";
	    }

	    List<Beneficiary> allBeneficiaries = //excelDataService.readBeneficiariesFromExcel();
	    			excelDataService.getBeneficiariesByFpsId(fps_id);
	    
	    System.out.println("in controller "+allBeneficiaries);
	    
	    List<Beneficiary> nonVisited =
	            nonVisitedService.getNonVisitedBeneficiaries(
	                    allBeneficiaries,
	                    List.copyOf(visitedIds)
	            );

	    session.setAttribute("result", nonVisited);

	    return smartPdsHome.getResultPage(nonVisited);
	}
	
	@GetMapping("/download-excel")
	public ResponseEntity<byte[]> downloadExcel(HttpSession session) {

	    List<Beneficiary> beneficiaries =
	            (List<Beneficiary>) session.getAttribute("result");

	    if (beneficiaries == null) {
	        return ResponseEntity.notFound().build();
	    }

	    try {

	        byte[] excelFile =
	                excelDataService.createExcelFile(beneficiaries);

	        return ResponseEntity.ok()
	                .header(
	                    HttpHeaders.CONTENT_DISPOSITION,
	                    "attachment; filename=non-visited-beneficiaries.xlsx"
	                )
	                .contentType(
	                    MediaType.APPLICATION_OCTET_STREAM
	                )
	                .body(excelFile);

	    } catch (IOException e) {

	        e.printStackTrace();

	        return ResponseEntity.internalServerError().build();
	    }
	}
	

	
}
