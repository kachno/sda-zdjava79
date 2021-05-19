package pl.sdacademy.hibernate.hello.workshop4;

import pl.sdacademy.hibernate.hello.common.City;
import pl.sdacademy.hibernate.hello.common.Country;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Workshop4 {
    public static void main(String[] args) {
        System.out.println("Podaj kod kraju:");
        final String countryCode = new Scanner(System.in).nextLine();

        List<City> cities = getCities(countryCode);
        final String citiesString = cities.stream().map(City::toString).collect(Collectors.joining("\n"));
        System.out.println(citiesString);
    }

    public static List<City> getCities(String countryCode) {
        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("HelloHibernatePU");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            TypedQuery<City> typedQuery = entityManager.createQuery(
                    "SELECT c FROM City c JOIN FETCH c.country WHERE c.country.code = :countryCode " +
                            "ORDER BY c.name", City.class
            );
            typedQuery.setParameter("countryCode", countryCode);
            return typedQuery.getResultList();
        } finally {
            entityManagerFactory.close();
        }
    }
}
