package pl.sdacademy.jdbc.hello.workshop3;

import pl.sdacademy.jdbc.hello.common.City;

import java.sql.*;
import java.util.Scanner;

public class Workshop3b {
    public static void main(String[] args) throws SQLException {
        System.out.println("Podaj nazwę miasta:");
        final String name = new Scanner(System.in).nextLine();

        System.out.println("Podaj kod kraju:");
        final String countryCode = new Scanner(System.in).nextLine();

        System.out.println("Podaj jednostkę administracyjną:");
        final String district = new Scanner(System.in).nextLine();

        System.out.println("Podaj liczbę mieszkańców:");
        final int population = new Scanner(System.in).nextInt();

        final City city = new City(name, countryCode, district, population);
        boolean result = addCity(city);
        System.out.println(result);
    }

    private static boolean addCity(City city) throws SQLException {
        final boolean exists;

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:6306/world", "root", "example")) {
            try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT EXISTS (SELECT * FROM city WHERE name=? AND countryCode = ?);")) {

                preparedStatement.setString(1, city.getName());
                preparedStatement.setString(2, city.getCountryCode());
                ResultSet resultSet = preparedStatement.executeQuery();
                resultSet.next();
                exists = resultSet.getBoolean(1);
            }

            if(exists) {
                try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE city SET district=?, population=? WHERE countryCode=? AND name=?;")) {
                    preparedStatement.setString(1, city.getDistrict());
                    preparedStatement.setInt(2, city.getPopulation());
                    preparedStatement.setString(3, city.getCountryCode());
                    preparedStatement.setString(4, city.getName());
                    preparedStatement.executeUpdate();
                }
            }
            else {
                throw new UnsupportedOperationException("TODO: Obsługa INSERT");
            }
        }

        return !exists;
    }
}
