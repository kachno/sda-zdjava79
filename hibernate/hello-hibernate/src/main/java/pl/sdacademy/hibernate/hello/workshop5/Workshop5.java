package pl.sdacademy.hibernate.hello.workshop5;

import javax.persistence.*;
import java.util.*;

public class Workshop5 {
    public static void main(String[] args) {
        System.out.println("Podaj kody krajów rozdzielone przecinkami:");
        final String countryCodes = new Scanner(System.in).nextLine();

        Map<String,List<String>> map = getCities(countryCodes.split(","));

        final StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            sb.append(entry.getKey()).append("\n");
            for (String city : entry.getValue()) {
                sb.append("\t").append(city).append("\n");
            }
        }

        System.out.println(sb);
    }

    public static Map<String,List<String>> getCities(String... codes) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("HelloHibernatePU");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        String query =
                "SELECT ci.name as cityName, ci.country.name as countryName FROM City ci  JOIN  ci.country where ci.country.code in :countryCodes ORDER BY ci.name";

        try {
            TypedQuery<Tuple> typedQuery = entityManager.createQuery(query, Tuple.class);
            List<Tuple> listTuple =  typedQuery.setParameter("countryCodes",Set.of(codes)).getResultList();
            Map<String,List<String>> tupleMap = new LinkedHashMap<>();
            for (Tuple tuple:listTuple) {
                String cityName = tuple.get("cityName",String.class);
                String countryName = tuple.get("countryName",String.class);
                tupleMap.computeIfAbsent(countryName, x -> new LinkedList<>()).add(cityName);
            }
            return tupleMap;
        } finally {
            entityManagerFactory.close();
        }
    }
}
