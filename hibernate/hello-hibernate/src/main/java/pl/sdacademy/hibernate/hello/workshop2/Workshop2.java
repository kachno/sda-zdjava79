package pl.sdacademy.hibernate.hello.workshop2;

import pl.sdacademy.hibernate.hello.common.Country;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.stream.Collectors;

public class Workshop2 {
    public static void main(String[] args) {
        final List<Country> country = getCountries();
        final String countriesString = country.stream().map(Country::toString).collect(Collectors.joining("\n"));
        System.out.println(countriesString);
    }

    public static List<Country> getCountries() {
        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("HelloHibernatePU");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            TypedQuery<Country> typedQuery = entityManager.createQuery(
                    "SELECT c FROM Country c WHERE c.continent = 'Europe' " +
                            "ORDER BY c.name", Country.class
            );
            return typedQuery.getResultList();
        } finally {
            entityManagerFactory.close();
        }
    }
}
