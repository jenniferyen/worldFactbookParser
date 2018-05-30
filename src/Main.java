import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/*
 * This class collects and analyzes data from the CIA World Factbook by calling on methods
 * from all other classes. 
 */
public class Main {
	private static ArrayList<CountryObject> allCountries;
	public final static String FACTBOOK_URL = 
			"https://www.cia.gov/library/publications/the-world-factbook/print/textversion.html";
	
	public static ArrayList<String> continents;
	public static ArrayList<String> hazards;
	
	public static void main(String[] args) {
		// add relevant continents
		continents = new ArrayList<String>();
		continents.add("Africa");
		continents.add("Asia");
		continents.add("Australia-Oceania");
		continents.add("Europe");
		continents.add("North America");
		continents.add("South America");
		
		// add relevant hazards
		hazards = new ArrayList<String>();
		hazards.add("cyclonic storm");
		hazards.add("drought");
		hazards.add("earthquake");
		hazards.add("forest fire");
		hazards.add("hurricane");
		hazards.add("typhoon");
		hazards.add("volcanism");
		
		// source URL
		CollectData homepage = new CollectData(FACTBOOK_URL);
		allCountries = homepage.getCountryURLs();
		
		for (CountryObject x : allCountries) {
			ParseCountry current = new ParseCountry(x);
			current.parseInformation();
		}
		
		// questions to be answered
		String questions = 
				  "NOTE: Due to some inconsistencies with the way the World Factbook presents information such as whether or not population is represented with scientific notation, "
				+ "some of these questions may not return the correct answers because of different formatting from country to country. I apologize for the inconvenience. -- WILL TRY TO DEBUG"
				+ "\n 1. Countries in <insert continent> that are prone to <insert natural hazard>. Possibilities include earthquake, "
				  + "flood, windstorm, typhoon, hurricane, drought, landslide, hailstorm" 
				+ "\n 2. Countries that have an star in their flag" 
				+ "\n 3. Countries with the smallest population in <insert continent>"
				+ "\n 4. Countries in <insert continent> that have a smaller total area than PA" 
				+ "\n 5. Countries with dominant religion <choose more / less> than <insert percentage> of population"
				+ "\n 6. Countries that are landlocked" 
				+ "\n 7. Countries above sea level of that of the US (760 m) in <insert continent>"
				+ "\n 8. Top <insert number> countries with the highest electricity consumption per capita"; 
		
		Scanner in = new Scanner(System.in);
		System.out.println(questions);
		System.out.println("Choose a question to answer (type in a number from 1-8)");
		String chooseQuestion = in.nextLine();
		
		// 1. Countries in <insert continent> that are prone to <insert natural hazard>
		if (chooseQuestion.equals("1")) {
			System.out.println("Enter a continent and a hazard (separate by return key)");
			String continent = in.nextLine();
			String hazard = in.nextLine();
			
			// filter by continent given
			ArrayList<CountryObject> sortedByContinent = new ArrayList<CountryObject>();
			for (CountryObject x : allCountries) {
				String countryContinent = x.getContinent();
				if (continent.equals(countryContinent)) {
					sortedByContinent.add(x);
				}
			}
			
			// filter by hazard given using ArrayList of filtered continents
			ArrayList<CountryObject> sortedByContinentAndHazard = new ArrayList<CountryObject>();
			for (CountryObject x : sortedByContinent) {
				ArrayList<String> countryHazards = x.getHazards();
				if (countryHazards.contains(hazard)) {
					sortedByContinentAndHazard.add(x);
				}
			}
			
			// print answers
			for (CountryObject x : sortedByContinentAndHazard) {
				x.print();
			}
			
		}
		
		// 2. Countries that have a star in their flag
		if (chooseQuestion.equals("2")) {		
			for (CountryObject x : allCountries) {
				if (x.getStarInFlag()) {
					x.print();
				}
			}
		}
		
		
		// 3. Countries with the smallest population in <insert continent>
		if (chooseQuestion.equals("3")) {
			System.out.println("Enter a continent");
			String continent = in.nextLine();
			
			// go thru all countries and get ArrayList that corresponds w the continent
			ArrayList<CountryObject> sortedByContinent = new ArrayList<CountryObject>();
			for (CountryObject x : allCountries) {
				String countryContinent = x.getContinent();
				if (continent.equals(countryContinent)) {
					sortedByContinent.add(x);
				}
			}
			
			// search for country with smallest population in filtered ArrayList from above
			String countryWithSmallestPop = "";
			double smallestPop = Double.POSITIVE_INFINITY;
			for (CountryObject x : sortedByContinent) {
				if (x.getPopulation() < smallestPop) {
					smallestPop = x.getPopulation();
				}
				countryWithSmallestPop = x.getName();
			}
			System.out.println(countryWithSmallestPop);
		}
		
		// 4. Countries in <insert continent> that have a smaller total area than PA
		if (chooseQuestion.equals("4")) {
			System.out.println("Enter continent");
			String continent = in.nextLine();
			
			// filter by continent given
			ArrayList<CountryObject> sortedByContinent = new ArrayList<CountryObject>();
			for (CountryObject x : allCountries) {
				String countryContinent = x.getContinent();
				if (continent.equals(countryContinent)) {
					sortedByContinent.add(x);
				}
			}
			// filter by area less than that of pennsylvania
			for (CountryObject x : sortedByContinent) {
				if (x.getAreaLessThanPennsylvania()) {
					x.print();
				}
			}
		}
		
		// 5. Countries with dominant religion <choose more / less> than <insert percentage> of population
		if (chooseQuestion.equals("5")) {
			
		}
		
		// 6. Countries that are landlocked
		if (chooseQuestion.equals("6")) {
			for (CountryObject x : allCountries) {
				if (x.getLandlocked()) {
					x.print();
				}
			}
		}
		
		// 7. Countries that are above sea level in <insert continent>
		if (chooseQuestion.equals("7")) {
			System.out.println("Enter continent");
			String continent = in.nextLine();
			
			// filter by continent given
			ArrayList<CountryObject> sortedByContinent = new ArrayList<CountryObject>();
			for (CountryObject x : allCountries) {
				String countryContinent = x.getContinent();
				if (continent.equals(countryContinent)) {
					sortedByContinent.add(x);
				}
			}
			// filter by sea level above that of the US
			for (CountryObject x : sortedByContinent) {
				if (x.getElevationAboveSeaLevel()) {
					x.print();
				}
			}
		}
		
		// 8. Top <insert number> countries with the highest electricity consumption per capita
		//    NOTE: There is a bug somewhere causing me to simply return the first 5 countries in the Factbook.
		if (chooseQuestion.equals("8")) {
			
			System.out.println("Insert number of countries");
			String numCountries = in.nextLine();
			
			ArrayList<CountryObject> highestElecConsump = new ArrayList<CountryObject>();
			for (CountryObject x : allCountries) {
				if ((x.getElecConsump() / x.getPopulation()) != 0) {
					highestElecConsump.add(x);
				}
			}
			Collections.sort(highestElecConsump, new CompareElecConsump());
			ArrayList<CountryObject> sortedArray = new ArrayList<CountryObject>();
			int count = 0;
			for (CountryObject y : highestElecConsump) {
				if (count > (Integer.parseInt(numCountries) - 1)) {
					break;
				}
				sortedArray.add(y);
				count++;
			}
			
			try { 
				for (CountryObject z : sortedArray) {
					System.out.println(z.getName());
				}
			} catch (NumberFormatException e) {
				System.out.println("Invalid input");
			}		
		}
	}
}
