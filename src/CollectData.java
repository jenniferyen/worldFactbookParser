import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/*
 * This class gets the names and URLs of all countries of interest
 * from the CIA World Factbook.  
 */

public class CollectData {
	
	String url;
	public CollectData(String url) {
		this.url = url;
	}
	
	public ArrayList<CountryObject> getCountryURLs() {
		ArrayList<CountryObject> allCountries = new ArrayList<CountryObject>();
		
		try {
			Document doc = Jsoup.connect(url).get();
			
			Elements allLinks = doc.select("a[href]");
			boolean check = false;
			
			for (Element link : allLinks) {
				
				// start at "World" page 
				if (link.text().equals("World")) check = true;
				
				if (link.attr("href").equals("#wfbtop")) break;
				
				// parse for links
				if (check) {
					String name = link.text();
					
					// disregard entries that are not countries
					if (name.equals("Akrotiri") || name.equals("American Samoa") || name.equals("Anguilla") || 
					    name.equals("Arctic Ocean") || name.equals("Aruba") || name.equals("Ashmore and Cartier Islands") ||
					    name.equals("Atlantic Ocean") ||name.equals("Baker Island") || name.equals("Bermuda") || 
					    name.equals("Bouvet Island") || name.equals("British Indian Ocean Territory") || 
					    name.equals("British Virgin Islands") || name.equals("Cayman Islands") || name.equals("Christmas Island") ||
					    name.equals("Clipperton Island") || name.equals("Cocos (Keeling) Islands") || name.equals("Cook Islands") ||
					    name.equals("Coral Sea Islands") || name.equals("Curacao") || name.equals("Dhekelia") || name.equals("European Union") ||
					    name.equals("Falkland Islands (Islas Malvinas)") || name.equals("Faroe Islands") || name.equals("French Polynesia") ||
					    name.equals("French Southern and Antarctic Lands") || name.equals("Gaza Strip") || name.equals("Gibraltar") || 
					    name.equals("Greenland") || name.equals("Guam") || name.equals("Guernsey") || name.equals("Heard Island and McDonald Islands") ||
					    name.equals("Holy See (Vatican City)") || name.equals("Hong Kong") || name.equals("Howland Island") ||
					    name.equals("Indian Ocean") || name.equals("Isle of Man") || name.equals("Jan Mayen") || name.equals("Jarvis Island") ||
					    name.equals("Jersey") || name.equals("Johnston Atoll") || name.equals("Kingman Reef") || name.equals("Macau") || 
					    name.equals("Midway Islands") || name.equals("Montserrat") || name.equals("Navassa Island") || name.equals("New Caledonia") ||
					    name.equals("Niue") || name.equals("Norfolk Island") || name.equals("Northern Mariana Islands") || name.equals("Pacific Ocean") ||
					    name.equals("Palmyra Atoll") || name.equals("Paracel Islands") || name.equals("Pitcairn Islands") || name.equals("Puerto Rico") ||
					    name.equals("Saint Barthelemy") || name.equals("Saint Helena, Ascension, and Tristan da Cunha") || name.equals("Saint Martin") ||
					    name.equals("Saint Pierre and Miquelon") || name.equals("Sint Maarten") || name.equals("South Georgia and South Sandwich Islands") ||
					    name.equals("Southern Ocean") || name.equals("Spratly Islands") || name.equals("Svalbard") || name.equals("Tokelau") || 
					    name.equals("Turks and Caicos Islands") || name.equals("United States Pacific Island Wildlife Refuges") || 
					    name.equals("Virgin Islands") || name.equals("Wake Island") || name.equals("Wallis and Futuna") || name.equals("West Bank") || 
					    name.equals("Western Sahara") || name.equals("World") || name.equals("Antarctica")) {
						continue;
					}
					
					String url = link.attr("href");
					url = url.substring(2);
					
					// Create new CountryObject for every country
					CountryObject current = new CountryObject(name, url);
					allCountries.add(current);
				}
			}
			
		} catch (IOException e) {
			System.out.println("Error connecting to CIA World Factbook main page.");
			e.printStackTrace();
		}
		
		return allCountries;
	}

}
