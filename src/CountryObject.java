
import java.util.ArrayList;

/*
 * Country object that includes all the data that it should contain
 * and methods for setting and getting the data.
 */

public class CountryObject {

	// private fields for every CountryObject
	private String name;
	private String endURL;
	private String continent;
	private ArrayList<String> hazards;
	private boolean starInFlag;
	private double population;
	private String totalArea;
	private boolean areaLessThanPennsylvania;
	private String dominantReligion;
	private double dominantReligionPercent;
	private boolean landlocked;
	private double elecConsump;
	private int meanElevation;
	private boolean aboveSeaLevel;
	
	public CountryObject(String name, String url) {
		this.name = name;
		this.endURL = url;
		this.hazards = new ArrayList<String>();
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setURL(String url) {
		this.endURL = url;
	}
	
	public String getURL() {
		return this.endURL;
	}
	
	public void setContinent(String continent) {
		this.continent = continent;
	}
	
	public String getContinent() {
		return this.continent;
	}
	
	public void addHazard(String hazard) {
		hazards.add(hazard);
	}
	
	public ArrayList<String> getHazards() {
		return hazards;
	}
	
	public void setStarInFlag(boolean s) {
		this.starInFlag = s;
	}
	
	public boolean getStarInFlag() {
		return this.starInFlag;
	}
	
	public void setPopulation(double population) {
		this.population = population;
	}
	
	public double getPopulation() {
		return this.population;
	}
	
	public void setArea(String totalArea) {
		this.totalArea = totalArea;
	}
	
	public String getArea() {
		return this.totalArea;
	}
	
	public void setAreaLessThanPennsylvania(boolean a) {
		this.areaLessThanPennsylvania = a;
	}
	
	public boolean getAreaLessThanPennsylvania() {
		return this.areaLessThanPennsylvania;
	}
	
	public void setDomReligion(double percent, String religion) {
		this.dominantReligionPercent = percent;
		this.dominantReligion = religion;
	}
	
	public double getDomReligionPercent() {
		return this.dominantReligionPercent;
	}
	
	public String getDomReligion() {
		return this.dominantReligion;
	}
	
	public void setElevation(int e) {
		this.meanElevation = e;
	}
	
	public int getElevation() {
		return this.meanElevation;
	}
	
	public void setElevationAboveSeaLevel(boolean e) {
		this.aboveSeaLevel = e;
	}
	
	public boolean getElevationAboveSeaLevel() {
		return this.aboveSeaLevel;
	}
	
	public void setLandlocked(boolean l) {
		this.landlocked = l;
	}
	
	public boolean getLandlocked() {
		return this.landlocked;
	}
	
	public void setElecConsump(double elecConsump) {
		this.elecConsump= elecConsump;
	}
	
	public double getElecConsump() {
		return this.elecConsump;
	}
	
	public void print() {
		System.out.println(this.name);
	}
	
}