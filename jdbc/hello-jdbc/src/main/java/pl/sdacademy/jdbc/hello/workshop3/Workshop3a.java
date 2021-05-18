package pl.sdacademy.jdbc.hello.workshop3;

import pl.sdacademy.jdbc.hello.common.City;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Workshop3a {
    public static void main(String[] args) throws SQLException {
        System.out.println("Podaj kod kraju:");
        final String countryCode = new Scanner(System.in).nextLine();

        List<City> cities = getCities(countryCode);
        final String citiesString = cities.stream().map(City::toString).collect(Collectors.joining("\n"));
        System.out.println(citiesString);
    }

    private static List<City> getCities(String countryCode) throws SQLException {
        final List<City> cities = new LinkedList<>();
        try (final Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:6306/world", "root", "example")) {
            try(final PreparedStatement statement = connection.prepareStatement("SELECT * FROM city WHERE countryCode = ? ORDER BY name;")) {
                statement.setString(1, countryCode);
                final ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    cities.add(new City(
                            resultSet.getString("name"),
                            resultSet.getString("countryCode"),
                            resultSet.getString("district"),
                            resultSet.getInt("population")
                    ));
                }
            }
        }

        return cities;
    }
}
