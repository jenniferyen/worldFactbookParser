import java.util.Comparator;

public class CompareElecConsump implements Comparator<CountryObject>{
	
	// override to compare two country's electricity consumption per capita
	@Override
	public int compare(CountryObject a, CountryObject b) {
		double aElec = (a.getElecConsump() / a.getPopulation());
		double bElec = (b.getElecConsump() / b.getPopulation());
		if (aElec >= bElec) return 0;
		else return 1;
	}
	
}
