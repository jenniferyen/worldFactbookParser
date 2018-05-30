# WorldFactbookParser
web scraper that searches for information from the CIA World Factbook to answer specific questions, project coded in Java--spring 2k18, freshman year at UPenn. 

Dependencies:
jsoup: http://jsoup.org/

Instruction: 
After compiling all files, run Main.java, which loads the user interface based on inputs from the user. 

Main.java: contains the main user interface, generates instructions and questions that the user can answer. 
CollectData.java : collects all the names and URLs of relevant countries. I hardcoded it to ignore territories and areas that
are NOT countries.
CountryObject.java: contains all necessary getter and setter methods for relevant data. 
ParseCountry.java: parses each country for relevant information, incorporates methods from CountryObject.java. 
CompareElecConsump.java: a comparator to sort electricity consumption per capita. 
