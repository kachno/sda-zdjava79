package pl.sdacademy.java.spring.hellocontext.example.interfaces;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);

        Document document1 = new Document("Ala ma kota", true);
        Document document2 = new Document("Kot ma Alę", false);

        DocumentHandler documentHandler = applicationContext.getBean(DocumentHandler.class);
        documentHandler.handleDocument(document1);
        documentHandler.handleDocument(document2);

    }
}
