package pl.sdacademy.hibernate.sakila.workshop8;

import pl.sdacademy.hibernate.sakila.workhop6.Staff;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Warsztat 8a – ładowanie personelu razem z adresem
 * • Przygotuj zapytanie ładujące cały personel
 * • Jakie zapytania się wykonują i dlaczego?
 * • Przebuduj zapytanie JPQL tak, by ładować komplet danych jednym
 * zapytaniem SQL
 */
public class Workshop8a {
    public static void main(String[] args) {
        final List<Staff> staff = findAllStaff();
        System.out.println(staffToString(staff));
    }

    public static String staffToString(List<Staff> staff) {
        return staff.stream().map(Staff::toString).collect(Collectors.joining("\n"));
    }

    public static List<Staff> findAllStaff() {
        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("SakilaPU");
        final EntityManager em = emf.createEntityManager();
        try {
            final TypedQuery<Staff> filmsQuery = em.createQuery(
                    "SELECT s FROM Staff s " +
                            "JOIN FETCH s.address a " +
                            "JOIN FETCH a.city c " +
                            "JOIN FETCH c.country", Staff.class);
            return filmsQuery.getResultList();
        } finally {
            emf.close();
        }
    }
}
