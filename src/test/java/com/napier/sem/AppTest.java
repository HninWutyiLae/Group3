package com.napier.sem;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppTest
{
    static App app;

    @BeforeAll
    static void init()
    {
        app = new App();
    }
    
     @Test
    void printCountries()
    {
        ArrayList<Country> countries = new ArrayList<Country>();
        Country emp = new Country();
        emp.code = "NAT";
        emp.name = "Aruba";
        emp.population = 120000;
        emp.continent = "North America";
        countries.add(emp);
        app.printCountries(countries);
    }
    
    @Test
    void printCity()
    {
        ArrayList<city> city = new ArrayList<city>();
        city emp = new city();
        emp.Name = "NAT";
        emp.District = "Aruba";
        emp.Population = 120000;

        city.add(emp);
        app.printCities(city);
    }
    
    
    @Test
void unitTest2()
{
    assertEquals(5, 5);
}


   
}
