package com.SmartPdsOfficial.view;

import java.util.List;

import org.springframework.stereotype.Component;

import com.SmartPdsOfficial.model.Beneficiary;

@Component
public class SmartPdsHome {
	
	public String getHomePage() {

        return """
                <!DOCTYPE html>
                <html>
                <head>
                    <title>Smart PDS Official</title>

                    <style>

                        body {
                            font-family: Arial, sans-serif;
                            background-color: #f4f6f9;
                            margin: 0;
                            padding: 0;
                        }

                        .container {
                            width: 500px;
                            margin: 60px auto;
                            background: white;
                            padding: 30px;
                            border-radius: 10px;
                            box-shadow: 0 4px 12px rgba(0,0,0,0.15);
                        }

                        h1 {
                            text-align: center;
                            color: #333;
                        }

                        label {
                            display: block;
                            margin-top: 15px;
                            font-weight: bold;
                        }

                        input, select {
                            width: 100%;
                            padding: 10px;
                            margin-top: 5px;
                            box-sizing: border-box;
                            border: 1px solid #ccc;
                            border-radius: 5px;
                        }

                        button {
                            width: 100%;
                            margin-top: 25px;
                            padding: 12px;
                            border: none;
                            border-radius: 5px;
                            background: #007bff;
                            color: white;
                            font-size: 16px;
                            cursor: pointer;
                        }

                        button:hover {
                            background: #0056b3;
                        }

                    </style>

                </head>

                <body>

                    <div class="container">

                        <h1>Smart PDS Official</h1>

                        <form method="post" action="/">

                        <label>District Code</label>
                            <input type="text"
                                   name="dist_code"
                                   required value="432">  

                            <label>FPS ID</label>
                            <input type="text"
                                   name="fps_id"
                                   required value="4402083">

                            <label>Month</label>

                            <select name="month" required>
                                <option value="">Select Month</option>
                                <option value="1">January</option>
                                <option value="2">February</option>
                                <option value="3">March</option>
                                <option value="4">April</option>
                                <option value="5">May</option>
                                <option value="6">June</option>
                                <option value="7">July</option>
                                <option value="8">August</option>
                                <option value="9">September</option>
                                <option value="10">October</option>
                                <option value="11">November</option>
                                <option value="12">December</option>
                            </select>

        		           <label>Year</label>

        		         	<input type="number"
                                   name="year"
                                   min="2020"
                                   max="2100"
                                   required value="2026"> 

                            <button type="submit">
                                Search
                            </button>

                        </form>

                    </div>

                </body>
                </html>
                """;
	}

	
	public String getResultPage(List<Beneficiary> beneficiaries) {

	    StringBuilder html = new StringBuilder();

	    html.append("""
	        <!DOCTYPE html>
	        <html>
	        <head>
	            <title>Smart PDS Result</title>
	            <style>
	                body {
	                    font-family: Arial;
	                    margin: 30px;
	                }

	                table {
	                    width: 100%;
	                    border-collapse: collapse;
	                }

	                th, td {
	                    border: 1px solid #ccc;
	                    padding: 10px;
	                    text-align: left;
	                }

	                th {
	                    background-color: #f2f2f2;
	                }

	                .download {
	                    display: inline-block;
	                    margin-bottom: 20px;
	                    padding: 10px 15px;
	                    background: #198754;
	                    color: white;
	                    text-decoration: none;
	                }
	            </style>
	        </head>
	        <body>

	        <h1>Non-Visited Beneficiaries</h1>

	        <p>Total Non-Visited: 
	        """);

	    html.append(beneficiaries.size());

	    html.append("""
	        </p>

	        <a class="download" href="/download-excel">
	            Download Excel
	        </a>

	        <table>
	            <thead>
	                <tr>
	                    <th>S.N.</th>
	                    <th>FPS Code</th>
	                    <th>Panchayat</th>
	                    <th>Village</th>
	                    <th>Family ID</th>
	                    <th>Head of Family</th>
	                    <th>Mobile</th>
	                </tr>
	            </thead>
	            <tbody>
	        """);

	    for (Beneficiary b : beneficiaries) {

	        html.append("<tr>");

	        html.append("<td>")
	             .append(b.getSn())
	             .append("</td>");

	        html.append("<td>")
	             .append(b.getFpsCode())
	             .append("</td>");

	        html.append("<td>")
	             .append(b.getPanchayat())
	             .append("</td>");

	        html.append("<td>")
	             .append(b.getVillage())
	             .append("</td>");

	        html.append("<td>")
	             .append(b.getFamilyId())
	             .append("</td>");

	        html.append("<td>")
	             .append(b.getHeadOfFamily())
	             .append("</td>");

	        html.append("<td>")
	             .append(b.getMobile())
	             .append("</td>");

	        html.append("</tr>");
	    }

	    html.append("""
	            </tbody>
	        </table>

	        </body>
	        </html>
	        """);

	    return html.toString();
	}
	

}
