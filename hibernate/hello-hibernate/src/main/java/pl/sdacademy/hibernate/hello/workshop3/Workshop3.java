package pl.sdacademy.hibernate.hello.workshop3;

import pl.sdacademy.hibernate.hello.common.Country;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Workshop3 {
    public static void main(String[] args) {
        System.out.println("Podaj nazwę kontynentu:");
        final String continent = new Scanner(System.in).nextLine();

        final List<Country> country = getCountries(continent);
        final String countriesString = country.stream().map(Country::toString).collect(Collectors.joining("\n"));
        System.out.println(countriesString);
    }

    public static List<Country> getCountries(String continent) {
        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("HelloHibernatePU");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            TypedQuery<Country> typedQuery = entityManager.createQuery(
                    "SELECT c FROM Country c WHERE c.continent = :continent " +
                            "ORDER BY c.name", Country.class
            );
            typedQuery.setParameter("continent", continent);
            return typedQuery.getResultList();
        } finally {
            entityManagerFactory.close();
        }
    }
}
