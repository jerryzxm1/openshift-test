package test.openshift.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * NO transaction
 * <ul>
 *     <li>there's concurrency issue</li>
 *     <li>better use lua to do multiple operation in a transaction.</li>
 * </ul>
 */
@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
