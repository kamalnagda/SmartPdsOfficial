package com.SmartPdsOfficial.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Service
public class SmartPdsService {
	
	public Set<String> fetchVisitedIdFromWeb(String dist_code, String fps_id, String month , String year) throws Exception {
		String url =  "https://epos.mp.gov.in/FPS_Trans_Details.jsp";
		
		String formData = 
				"dist_code=" + "432" +
				"&fps_id=" + fps_id +
				"&month=" + month +
				"&year=" + LocalDate.now().getYear();
		
		HttpClient client = HttpClient.newHttpClient();
		
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(url))
				.header("Content-Type",
						"application/x-www-form-urlencoded")
				.header("User-Agent",
						"Mozilla/5.0)")
				.POST(HttpRequest.BodyPublishers.ofString(formData))
				.build();
		
		HttpResponse<String> response = 
				client.send(
						request,
						HttpResponse.BodyHandlers.ofString()
						);
		
		System.out.println("Status : in smart pds service : " + response.statusCode());
		
		String html = response.body();
		Document document = Jsoup.parse(html);
		
		Element table = document.selectFirst("#Report");
		
		if(table == null) {
			throw new RuntimeException("Transaction table not found");
		}
		
		Elements rows = table.select("tbody tr");
		
		Set<String> rcNumbers = new LinkedHashSet<>();
		
		for(Element row : rows) {
			
			Elements cells = row.select("td");
			
			if(cells.size() >= 2) {
				String rcNo = cells.get(1).text().trim();
				
                if (!rcNo.isEmpty()) {
                    rcNumbers.add(rcNo);
                }
				
			}
		}
		
		System.out.println("total distibuted coupon no.: in smart pds service :" + rcNumbers.size());
		
		return rcNumbers;
	}
	

}
