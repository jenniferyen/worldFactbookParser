import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/*
 * This class pulls relevant data about a country from the World Factbook.
 * It gets the country's name and URL and parses relevant information.
 */

public class ParseCountry {
	
	private CountryObject country;
	private Document document;
	private Map<String, Long> numberMap;
	
	private void createNumberMap() {
		numberMap = new HashMap<String, Long>();
		numberMap.put("million", 1000000L);
		numberMap.put("billion", 1000000000L);
		numberMap.put("trillion", 1000000000000L);
	}
	
	// gets the URL for every country to parse relevant information
	public ParseCountry(CountryObject country) {
		this.country = country;
		System.out.print("Country: " + country.getName());
		try {
			String url = Main.FACTBOOK_URL;
			url = url.substring(0, 59);
			url += country.getURL();
			// System.out.println("URL: " + url);
			document = Jsoup.connect(url).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public CountryObject parseInformation() {
		createNumberMap();
		System.out.println();
		parseContinent(); // WORKS   
		parseHazards(); // WORKS
		parseStarInFlag(); // WORKS
		parsePopulation(); // WORKS
		parseArea(); // WORKS
		areaLessThanPennsylvania(); // WORKS
		parseDomReligion(); // WORKS
		parseLandlocked(); // WORKS
		parseElevation(); // WORKS
		elevationAboveSeaLevel(); // WORKS
		parseElecConsump();
		System.out.println("Electricity Consumption Per Capita: " + calculateElecConsumpPerCapita());
		System.out.println("*****************");
		System.out.println();
		return country;
	}
	
	// searches for country's continent
	public void parseContinent() {
		Element countryRegion = document.selectFirst("div.printHeader*");
		System.out.println(countryRegion.text());
			if (countryRegion.text().contains("Africa")) {
				country.setContinent("Africa");
				System.out.println("Continent: " + country.getContinent());
			}
			if (countryRegion.text().contains("Asia")) {
				country.setContinent("Asia");
				System.out.println("Continent: " + country.getContinent());
			}
			if (countryRegion.text().contains("Australia-Oceania")) {
				country.setContinent("Australia-Oceania");
				System.out.println("Continent: " + country.getContinent());
			}
			if (countryRegion.text().contains("Europe")) {
				country.setContinent("Europe");
				System.out.println("Continent: " + country.getContinent());
			}
			if (countryRegion.text().contains("North America")) {
				country.setContinent("North America");
				System.out.println("Continent: " + country.getContinent());
			}
			if (countryRegion.text().contains("South America")) {
				country.setContinent("South America");
				System.out.println("Continent: " + country.getContinent());
			}
//		}
	}
	
	// searches for country's hazard(s)
	public void parseHazards() {
		Element countryHazard = document.getElementsContainingText("Natural hazards").first();
		if (countryHazard != null) {
			String text = countryHazard.text();
			for (String x : Main.hazards) {
				if (text.contains(x)) {
					country.addHazard(x);
				}
			}
			System.out.print("Hazards: ");
			for (String y : country.getHazards()) {
				System.out.print(y);
			}
		}
		System.out.println();
	}
	
	// searches for whether or not there is a star in the flag
	public void parseStarInFlag() {
		Elements countryStarInFlag = document.select("div.category_data");
		boolean objectInFlag = false;
		for (Element x : countryStarInFlag) {
			if (x.text().contains(" star ") || x.text().contains(" stars ")) {
				country.setStarInFlag(true);
				objectInFlag = true;
			}
		}
		System.out.println("Star in flag: " + objectInFlag);
	}
	
	
	// searches for country's population
	public void parsePopulation() {
		Element population = null;
		Elements countryPopulation = document.select("div.category, div.category_data");
		boolean check = false;
		for (Element x : countryPopulation) {
			if (check) {
				population = x;
				break;
			}
			if (x.text().contains("Population:")) {
				check = true;
			}
		}
		// Population will be, for example, 326,625,791 (July 2017 est.)
		// Calling split() to split this into {326,625,791, (July 2017 est.)}
		if (population != null) {
			String[] splitText = population.text().split(" ");
			String commasRemoved = splitText[0].replace(",", "");
					
			try {
				int correctPopulation = Integer.parseInt(commasRemoved);
				country.setPopulation(correctPopulation);
			} catch (NumberFormatException e) {
						e.printStackTrace();
			}
			System.out.println("Population: " + country.getPopulation());
		}
		else System.out.println("Population: No Indigenous Inhabitants");
	}
	
	// searches for country's total area
	public void parseArea() {
		Element area = null;
		Elements countryArea = document.select("div#field");
		for (Element x : countryArea) {
			if (x.text().contains("Area:")) {
				area = x;
			}
		}
		if (area != null) {
			Element correctArea = area.nextElementSibling();
			String areaToString = correctArea.text();
			String[] splitText = areaToString.split(" ");
			String isolatedArea = splitText[1].replace(",", "");
			try {
				country.setArea(isolatedArea);
				System.out.println("Total Area (sq km): " + country.getArea());
			} catch (NumberFormatException e) {
				System.out.println("Total Area: Unavailable");
			}
		}
	}
	
	// verifies whether or not the country's total area is less than that of Pennsylvania's
	public void areaLessThanPennsylvania() {
		double areaPennsylvania = 119280;
		double doubleOfCountryArea = Double.parseDouble(country.getArea());
		boolean lessThanPennsylvania = false;
		if (areaPennsylvania > doubleOfCountryArea) {
			lessThanPennsylvania = true;
			country.setAreaLessThanPennsylvania(true);
		}
		System.out.println("Country area is less than Pennsylvania: " + lessThanPennsylvania);
	}
	
	// searches for country's dominant religions
	private void parseDomReligion() {
		Element religion = null;
		Elements countryReligion = document.select("div.category, div.category_data");
		boolean check = false;
		for (Element x: countryReligion) {
			if (check) {
				religion = x;
				break;
			}
			if (x.text().contains("Religions:")) {
				check = true;
			}
		}
		if (religion != null) {
			String[] splitData = religion.text().split(",");

			System.out.println(religion.text());
			
			String first = splitData[0];  // this is the dominant religion only 
			try {
				double percentage = 0.0;

				// average out if there is a range
				if (first.contains("-")) {
					String[] splitNumbers = first.split("-");
					String number1 = 
							splitNumbers[0].replaceAll("[^\\d.]", "");
					String number2 = splitNumbers[1].replaceAll(
							"[^\\d.]", "");
					percentage = (Double.parseDouble(number1)
							+ Double.parseDouble(number2)) / 2;
				}
				else {
					String number = first.replaceAll("[^\\d.]", "");
					percentage = Double.parseDouble(number);
				}
				country.setDomReligion(percentage, first);
				System.out.println("Dominant religion: " 
						+ country.getDomReligion() + 
						" " + country.getDomReligionPercent());
			}
			catch (NumberFormatException e) {
				System.out.println("Dominant religion percentage: Unavailable");
			}
		}
		else System.out.println("Dominant religion percentage: Unavailable");
	}
	
	// searches for whether the country is landlocked
	public void parseLandlocked() {
		Elements countryLandlocked = document.select("div.category, div.category_data");
		boolean landlocked = false;
		for (Element x : countryLandlocked) {
			if (x.text().contains("landlocked")) {
				country.setLandlocked(true);
				landlocked = true;
			}
		}
		System.out.println("Landlocked: " + landlocked);
	}
	
	// searches for country's elevation
	public void parseElevation() {
		Element meanElevation = null;
		Elements countryMeanElevation = document.select("div#field");
		for (Element x : countryMeanElevation) {
			if (x.text().contains("Elevation:")) {
				meanElevation = x;
			}
		}
		if (meanElevation != null) {
			Element correctElevation = meanElevation.nextElementSibling();
			String elevationToString = correctElevation.text();
			// System.out.println(elevationToString);
			String[] splitText = elevationToString.split(" ");
			String isolatedElevation = splitText[2].replace(",", "");
			try {
				country.setElevation(Integer.parseInt(isolatedElevation));
				System.out.println("Mean Elevation (m): " + country.getElevation());
			} catch (NumberFormatException e) {
				System.out.println("Mean Elevation: Unavailable");
			}
		}
	}
	
	// verifies whether or not the country's sea level is above that of the US
	public void elevationAboveSeaLevel() {
		int seaLevel = 760;
		int countryElevation = country.getElevation();
		boolean aboveSeaLevel = false;
		if (countryElevation > seaLevel) {
			aboveSeaLevel = true;
			country.setElevationAboveSeaLevel(true);
		}
		System.out.println("Mean elevation is above that of the US: " + aboveSeaLevel);
	}
	
	// searches for country's electricity consumption
	public void parseElecConsump() {
		Element elecData = null;
		Elements categoryData = document.select("div.category, div.category_data");

		boolean check = false;
		for (Element x: categoryData) {
			if (check) {
				elecData = x;
				break;
			}
			if (x.text().contains("Electricity - consumption:"))
				check = true;
		}
		if (elecData != null) {
			String[] splitData = elecData.text().split(" ");

			String magnitude = splitData[1];
			double elec = 0.0;
			if (magnitude.equals("million") || magnitude.equals("billion")
					|| magnitude.equals("trillion")) {
				long multiplier = numberMap.get(splitData[1]);
				elec = Double.parseDouble(splitData[0].replace(",", "")) * multiplier;
			}
			else elec = Double.parseDouble(splitData[0].replace(",", ""));
			country.setElecConsump((long) elec); 
			System.out.println("Electricity Consumption: " 
					+ country.getElecConsump());
		}
		else System.out.println("Electricity Consumption: N/A");
	}
	
	// calculate country's electricity consumption per capita
	public int calculateElecConsumpPerCapita() {
		int perCapita = (int) (country.getElecConsump() / country.getPopulation());
		return perCapita;
	}
	
}
