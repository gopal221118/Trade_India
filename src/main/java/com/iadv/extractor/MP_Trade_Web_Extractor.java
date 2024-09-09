package com.iadv.extractor;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class MP_Trade_Web_Extractor {
	
	public static void extractMPTradeValues(String html)
	{

		try
		{
			Document doc = Jsoup.parse(html);

			// Extract the value of 'action' from the form with id
			// 'sap.client.SsrClient.form'
			Element formElement = doc.getElementById("sap.client.SsrClient.form");
			String actionValue = formElement.attr("action");

			// Extract the value from the id 'sap-wd-secure-id'
			Element secureIdElement = doc.getElementById("sap-wd-secure-id");
			String secureIdValue = secureIdElement.attr("value");

			// Print the results
			String actionx = actionValue.replace("?", "~");
			String contextid[] = actionx.split("~");
			String conNsec = contextid[1].trim() + "~" + secureIdValue;
			System.out.println("Action Value: " + contextid[1].trim());
			System.out.println("Secure Id Value: " + secureIdValue);	
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
