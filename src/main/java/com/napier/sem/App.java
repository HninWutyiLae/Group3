package com.napier.sem;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
public class App {

    /**
     * Connection to MySQL database.
     */
    private Connection con = null;

    public static void main(String[] args)
    {
        // Create new Application
        App a = new App();

        if(args.length < 1){
            a.connect("localhost:33060", 30000);
        }else{
            a.connect(args[0], Integer.parseInt(args[1]));
        }

//        // Connect to database
//        a.connect("db:3306", 30000);
        System.out.println("Never say Never !!!!");
        /**
         * Report for countries information
         */
        // Extract countries information in the world
        ArrayList<Country> cou = a.getAllCountry();
        // Extract countries information in a continent
        ArrayList<Country> con = a.getAllContinent();

        // Extract countries information in a region
        ArrayList<Country> reg = a.getAllRegion();

        // Extract countries information in a region
        ArrayList<Country> capitalinW = a.getAllCapitalinW();

        // Display results
//        a.displayCountry(cou);
//      a.displayContinent(con);
//      a.displayRegion(reg);
//        a.displayCapitalinW(capitalinW);

        /**
         * Report for city information
         */
        // Extract city information in a country
        ArrayList<city> cit = a.getAllCity();
        // Extract city information in world
        ArrayList<city> cityinW = a.getAllCityinW();
        // Extract city information in continent
        ArrayList<city> cityinC = a.getAllCityContinent();

        ArrayList<Country> capitalinContinent = a.getAllCapitalinContinent("Asia");

        ArrayList<city> TopCityinW = a.getAllTopCityinW(5);

        ArrayList<city> TopCityinCONTIN = a.getAllTopCityinCONTIN(5, "Asia");

        // Display results
//        a.displayCity(cit);
        // Display results
//        a.displayCityinW(cityinW);
        // Display results
//        a.displayCityContinent(cityinC);

//        a.displayTopCityinW(TopCityinW);
//
//        a.displayTopCityinCONTIN(TopCityinCONTIN);

        a.displayCapitalinContinent(capitalinContinent);
        // Disconnect from database
        a.disconnect();
    }
    /**
     * Connect to the MySQL database.
     */
    public void connect(String conString, int delay)
    {
        try {
            // Load Database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        int retries = 10;
        for (int i = 0; i < retries; ++i) {
            System.out.println("Connecting to database...");
            try {
                // Wait a bit for db to start
                Thread.sleep(delay);
                // Connect to database
                //Added allowPublicKeyRetrieval=true to get Integration Tests to work. Possibly due to accessing from another class?
                con = DriverManager.getConnection("jdbc:mysql://" + conString + "/world?allowPublicKeyRetrieval=true&useSSL=false", "root", "group3");
                System.out.println("Successfully connected");
                break;
            } catch (SQLException sqle) {
                System.out.println("Failed to connect to database attempt " + Integer.toString(i));
                System.out.println(sqle.getMessage());
            } catch (InterruptedException ie) {
                System.out.println("Thread interrupted? Should not happen.");
            }
        }
    }

    /**
     * Disconnect from the MySQL database.
     */
    public void disconnect()
    {
        if (con != null)
        {
            try
            {
                // Close connection
                con.close();
            }
            catch (Exception e)
            {
                System.out.println("Error closing connection to database");
            }
        }
    }


    /**
     * Gets all the countires in the world.
     * @return A list of all countries and population, or null if there is an error.
     */
    public ArrayList<Country> getAllCountry()
    {
        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect = "select * from country order by Population desc;";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Extract countries information
            ArrayList<Country> Country = new ArrayList<Country>();
            while (rset.next())
            {
                Country emp = new Country();
                emp.code = rset.getString("country.Code");
                emp.name = rset.getString("country.Name");
                emp.continent = rset.getString("country.continent");
                emp.region = rset.getString("country.region");
                emp.population = rset.getInt("country.population");

//                System.out.println(emp.continent);
                Country.add(emp);
            }
            return Country;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country in the world details");
            return null;
        }
    }

    public void displayCountry(ArrayList<Country> cou)
    {
        StringBuilder sb = new StringBuilder();

        // Check country is not null
        if (cou == null)
        {
            System.out.println("No country");
            return;
        }

        // Print header
        System.out.println(String.format("%-10s %-15s %-20s %-8s", "Code", "Name", "Population", "Continent"));
        System.out.println("Reporting all the countries in the world organised by largest population to smallest ");

        for (Country emp : cou)
        {

            System.out.println (
                    emp.name + " "
                            + emp.continent + " "
                            + "\n");
        }
//        try {
//            new File("./reports/").mkdir();
//            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(".//reports//" + filename)));
//            writer.write(sb.toString());
//            writer.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
    /**
     * Gets all the current city in a country.
     * @return A list of all city, or null if there is an error.
     */
    public ArrayList<city> getAllCity()
    {
        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect = "SELECT * FROM country, city WHERE country.Code = city.Countrycode ORDER BY city.Population desc";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Extract employee information
            ArrayList<city> city = new ArrayList<city>();
            while (rset.next())
            {
                city emp = new city();
//                emp.code = rset.getString("country.Code");
                emp.Name = rset.getString("city.Name");
                emp.CountryCode = rset.getString("city.CountryCode");
               emp.District = rset.getString("city.District");
//                emp.Population = rset.getString("city.Population");


                city.add(emp);
            }
            return city;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get City details");
            return null;
        }
    }

    public void displayCity(ArrayList<city> cou)
    {
        StringBuilder sb = new StringBuilder();
        System.out.println("report all the cities in a country organised by largest population to smallest #12\n ");
        for (city emp : cou)
        {

            System.out.println (
                    emp.Name + " "
                            + emp.CountryCode + " "
                            + "\n");
        }
//        try {
//            new File("./reports/").mkdir();
//            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(".//reports//" + filename)));
//            writer.write(sb.toString());
//            writer.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    /**
     * Gets all the current employees and salaries.
     * @return A list of all employees and salaries, or null if there is an error.
     */
    public ArrayList<city> getAllCityinW()
    {
        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect = "select * from city order by Population desc;";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Extract employee information
            ArrayList<city> city = new ArrayList<city>();
            while (rset.next())
            {
                city emp = new city();
//                emp.code = rset.getString("city.Code");
                emp.Name = rset.getString("city.Name");
                emp.Population = rset.getInt("city.Population");
//                emp.region = rset.getString("city.region");

                city.add(emp);
            }
            return city;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get salary details");
            return null;
        }
    }

    public void displayCityinW(ArrayList<city> cou)
    {
        StringBuilder sb = new StringBuilder();
        System.out.println("Report all the cities in the world organised by largest population to smallest.\n ");
        for (city emp : cou)
        {

            System.out.println (
                    emp.Name + " "
                            + emp.Population + " "
                            + "\n");
        }
//        try {
//            new File("./reports/").mkdir();
//            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(".//reports//" + filename)));
//            writer.write(sb.toString());
//            writer.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public ArrayList<city> getAllCityContinent()
    {
        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String cont = "North America";
            String strSelect = "SELECT *"
                    + "FROM country, city "
                    + "WHERE country.Code= city.CountryCode AND country.Continent =" + "'" + cont + "'"
                    + "ORDER BY city.Population DESC";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Extract employee information
            ArrayList<city> city = new ArrayList<city>();
            while (rset.next())
            {
                city emp = new city();
//                emp.code = rset.getString("city.Code");
                emp.Name = rset.getString("city.Name");
                emp.Population = rset.getInt("city.Population");
//                emp.region = rset.getString("city.region");

                city.add(emp);


            }
            return city;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get population in continent");
            return null;
        }
    }

    public void displayCityContinent(ArrayList<city> con)
    {
        StringBuilder sb = new StringBuilder();
        System.out.println("Report all the cities in a continent organised by largest population to smallest.\n");
        System.out.println("-----------------------------------------------------------------------------------\n");
        System.out.println("|   Name    |   Population");
        for (city emp : con)
        {
                            System.out.println (
                                    emp.Name + " "
                                            + emp.Population + " "
                                            + "\n");

        }
//        try {
//            new File("./reports/").mkdir();
//            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(".//reports//" + filename)));
//            writer.write(sb.toString());
//            writer.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    /**
     * Gets all the countries in a continent organised by largest population to smallest.
     * @return the countries in a continent organised by largest population to smallest., or null if there is an error.
     */
    public ArrayList<Country> getAllContinent()
    {
        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String cont = "North America";
            String strSelect = "SELECT * "
                    + "FROM country "
                    + " WHERE Continent =" + "'" + cont + "'"
                    + " ORDER BY Population DESC";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Extract list of country information in a continent
            ArrayList<Country> Country = new ArrayList<Country>();
            while (rset.next())
            {
                Country emp = new Country();
                emp.code = rset.getString("country.code");
                emp.name = rset.getString("country.name");
                emp.continent = rset.getString("country.continent");
                emp.region = rset.getString("country.region");
                emp.population = rset.getInt("country.population");
                Country.add(emp);

            }
            return Country;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get population in continent");
            return null;
        }
    }
    public void displayContinent(ArrayList<Country> con)
    {
        StringBuilder sb = new StringBuilder();
        System.out.println("All the countries in a region organised by largest population to smallest.");

        for (Country emp : con)
        {
            // Display country list information
            System.out.println
                    (
                            emp.code + " "
                                    + emp.name + " " + emp.population
                                    + "\n");

        }
//        try {
//            new File("./reports/").mkdir();
//            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(".//reports//" + filename)));
//            writer.write(sb.toString());
//            writer.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    //All the countries in a region organised by largest population to smallest.

    public ArrayList<Country> getAllRegion()
    {
        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String reg = "Southern Europe";
            String strSelect = "SELECT * "
                    + "FROM country "
                    + " WHERE Region =" + "'" + reg + "'"
                    + " ORDER BY Population DESC";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Extract list of country information in a region
            ArrayList<Country> country = new ArrayList<Country>();
            while (rset.next())
            {
                Country emp = new Country();
                emp.code = rset.getString("country.code");
                emp.name = rset.getString("country.name");
                emp.continent = rset.getString("country.continent");
                emp.region = rset.getString("country.region");
                emp.population = rset.getInt("country.population");
                country.add(emp);

            }
            return country;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get population in a region");
            return null;
        }
    }

    public void displayRegion(ArrayList<Country> reg)
    {
        StringBuilder sb = new StringBuilder();
        System.out.println("All the countries in a continent organised by largest population to smallest.");
        for (Country emp : reg)
        {
            System.out.println
                    (
                            emp.code + " "
                                    + emp.name + " " + emp.population
                                    + "\n");

        }
//        try {
//            new File("./reports/").mkdir();
//            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(".//reports//" + filename)));
//            writer.write(sb.toString());
//            writer.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public void printCountries(ArrayList<Country> countries)
    {
        // Check country is not null
        if (countries == null)
        {
            System.out.println("No country");
            return;
        }
        // Print header
        System.out.println(String.format("%-10s %-15s %-20s %-8s", "Code", "Name", "Population", "Continent"));
        // Loop over all employees in the list
        for (Country emp : countries)
        {
            String emp_string =
                    String.format("%-10s %-15s %-20s %-8s",
                            emp.code, emp.name, emp.population, emp.continent);
            System.out.println(emp_string);
        }
    }
    /**
     * Gets all the Capital in the world.
     * @return A list of all city, or null if there is an error.
     */
    public ArrayList<Country> getAllCapitalinW()
    {
        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect = "SELECT * FROM country, city WHERE country.Capital = city.ID ORDER BY city.Population desc";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Extract employee information
            ArrayList<Country> Country = new ArrayList<Country>();


            while (rset.next())
            {
                Country emp = new Country();

//                emp.code = rset.getString("country.Code");
                emp.name = rset.getString("Country.name");
                emp.capital_n = rset.getString("city.name");
                emp.population = rset.getInt("city.population");
//                emp.Population = rset.getString("city.Population");

                Country.add(emp);
            }
            return Country;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get City details");
            return null;
        }
    }

    public void displayCapitalinW(ArrayList<Country> world)
    {
        StringBuilder sb = new StringBuilder();
        System.out.println("All the countries in a continent organised by largest population to smallest.");
        for (Country emp : world)
        {
            System.out.println
                    (emp.name + " "
                                    + emp.capital_n + " " + emp.population
                                    + "\n");
        }
//        try {
//            new File("./reports/").mkdir();
//            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(".//reports//" + filename)));
//            writer.write(sb.toString());
//            writer.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    /**
     * Gets all the Capital in the continent.
     * @return A list of all city, or null if there is an error.
     */
    public ArrayList<Country> getAllCapitalinContinent(String continent)
    {
        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect = "SELECT * FROM country, city WHERE country.Capital = city.ID and country.Continent = " + "'" + continent + "'" + "ORDER BY city.Population desc";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Extract employee information
            ArrayList<Country> Country = new ArrayList<Country>();


            while (rset.next())
            {
                Country emp = new Country();

//                emp.code = rset.getString("country.Code");
                emp.name = rset.getString("Country.name");
                emp.capital_n = rset.getString("city.name");
                emp.population = rset.getInt("city.population");
//                emp.Population = rset.getString("city.Population");

                Country.add(emp);
            }
            return Country;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get City details");
            return null;
        }
    }


    public void displayCapitalinContinent(ArrayList<Country> world)
    {
        StringBuilder sb = new StringBuilder();
        System.out.println("All the capital cities in a continent organised by largest population to smallest.");
        for (Country emp : world)
        {
            System.out.println
                    (emp.name + " "
                            + emp.capital_n + " " + emp.population
                            + "\n");
        }
//        try {
//            new File("./reports/").mkdir();
//            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(".//reports//" + filename)));
//            writer.write(sb.toString());
//            writer.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    /**
     * Gets all in the top city in the world
     * @return A list of all city, or null if there is an error.
     */
    public ArrayList<city> getAllTopCityinW(int Num)
    {
        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect = "SELECT * FROM country, city WHERE country.Code = city.CountryCode ORDER BY city.Population desc limit " + Num;
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Extract employee information
            ArrayList<city> city = new ArrayList<city>();


            while (rset.next())
            {
                city emp = new city();

//                emp.code = rset.getString("country.Code");
                emp.cName = rset.getString("Country.name");
                emp.Name = rset.getString("city.name");
                emp.Population = rset.getInt("city.population");
//                emp.Population = rset.getString("city.Population");

                city.add(emp);
            }
            return city;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get City details");
            return null;
        }
    }

    public void displayTopCityinW(ArrayList<city> world)
    {
        StringBuilder sb = new StringBuilder();
        System.out.println("The top N populated cities in the world where N is provided by the user.");
        for (city emp : world)
        {
            System.out.println
                    (emp.cName + " "
                            + emp.Name + " " + emp.Population
                            + "\n");
        }
//        try {
//            new File("./reports/").mkdir();
//            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(".//reports//" + filename)));
//            writer.write(sb.toString());
//            writer.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }


    /**
     * Gets all in the top city in the continent
     * @return A list of all city, or null if there is an error.
     */
    public ArrayList<city> getAllTopCityinCONTIN(int Num, String ContiN)
    {
        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect = "SELECT * FROM country, city WHERE country.Code = city.CountryCode and city.Continent = " + "'" + ContiN + "'" + "ORDER BY city.Population desc limit " + Num;
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Extract employee information
            ArrayList<city> city = new ArrayList<city>();


            while (rset.next())
            {
                city emp = new city();

//                emp.code = rset.getString("country.Code");
                emp.cName = rset.getString("Country.name");
                emp.Name = rset.getString("city.name");
                emp.Population = rset.getInt("city.population");
//                emp.Population = rset.getString("city.Population");

                city.add(emp);
            }
            return city;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get City details");
            return null;
        }
    }

    public void displayTopCityinCONTIN(ArrayList<city> world)
    {
        StringBuilder sb = new StringBuilder();
        System.out.println("The top N populated cities in a continent where N is provided by the user.");
        for (city emp : world)
        {
            System.out.println
                    (emp.cName + " "
                            + emp.Name + " " + emp.Population
                            + "\n");
        }
//        try {
//            new File("./reports/").mkdir();
//            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(".//reports//" + filename)));
//            writer.write(sb.toString());
//            writer.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }


}

}

