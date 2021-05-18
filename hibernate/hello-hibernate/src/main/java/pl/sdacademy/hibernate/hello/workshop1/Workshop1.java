package pl.sdacademy.hibernate.hello.workshop1;

import pl.sdacademy.hibernate.hello.common.Country;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Scanner;

public class Workshop1 {
    public static void main(String[] args) {
        System.out.println("Podaj kod kraju:");
        final String code = new Scanner(System.in).nextLine();
        final Country country = loadCountryByCode(code);
        System.out.println(country);
    }

    public static Country loadCountryByCode(String code) {
        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("HelloHibernatePU");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.find(Country.class, code);
        } finally {
            entityManagerFactory.close();
        }
    }
}