package pl.sdacademy.jdbc.hello.workshop4;

import pl.sdacademy.jdbc.hello.common.City;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class Workshop4 {
    public static void main(String[] args) throws SQLException {
        List<City> cities = List.of(
                new City("Aaaaaa", "POL", "Mazowieckie", 100),
                new City("Bbbbbb", "POL", "Mazowieckie", 200),
                new City("Cccccc", "POL", "Mazowieckie", 200)
        );

        addCities(cities);
    }

    private static void addCities(List<City> cities) throws SQLException {
        try (final Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:6306/world", "root", "example")) {
            connection.setAutoCommit(false);
            try (final PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO city(name, countryCode, district, population) VALUES (?,?,?,?);")) {
                for (City city : cities) {
                    preparedStatement.setString(1, city.getName());
                    preparedStatement.setString(2, city.getCountryCode());
                    preparedStatement.setString(3, city.getDistrict());
                    preparedStatement.setInt(4, city.getPopulation());
                    preparedStatement.addBatch();
                }
                preparedStatement.executeBatch();
                connection.commit();
            } catch (SQLException sqlException) {
                connection.rollback();
                throw sqlException;
            }
        }
    }
}
