package de.freerider;

import java.util.ArrayList;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import de.freerider.model.Customer;
import de.freerider.repository.CustomerRepository;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

        // Creating Repo + Customers
        CustomerRepository repo = new CustomerRepository();
        Customer customer1 = new Customer("Saalfeld", "Paul-Ole", "Podewilsstraße 9");
        Customer customer2 = new Customer("Müller", "Dieter", "Atillastraße 2");
        Customer customer3 = new Customer("Bäcker", "Ursula", "Albrechtstraße 5");
        Customer customer4 = new Customer("Stiefel", "Nikolas", "Tempehoferdamm 10");
        Customer customer5 = new Customer("Schütz", "Karl-Heinz", "Wilhelm-Kabus-Straße 22");
        System.out.println("Created repo & customers");
        System.out.println("Starting Testing..");
        System.out.println("");

        // Testing save() & count()
        System.out.println("Testing save() & count() - 1 should be displayed:");
        repo.save(customer1);
        System.out.println("" + repo.count());
        System.out.println("");

        // Testing saveAll()
        System.out.println("Testing saveAll() - 5 should be displayed:");
        ArrayList<Customer> entities = new ArrayList<Customer>();
        entities.add(customer2);
        entities.add(customer3);
        entities.add(customer4);
        entities.add(customer5);
        repo.saveAll(entities);
        System.out.println("" + repo.count());
        System.out.println("");

        // Testing findById()
        System.out.println("Testing findById() - Saalfeld should be displayed:");
        System.out.println(repo.findById(customer1.getId()).get().getLastName());
        System.out.println("");

        // Testing existsById()
        System.out.println("Testing existsById() - true should be displayed:");
        System.out.println("" + repo.existsById(customer1.getId()));
        System.out.println("");

        // Testing findAll()
        System.out.println(
                "Testing findAll() - Saalfeld, Müller, Bäcker, Stiefel, Schütz, should be displayed (not ordered):");
        String output = "";
        for (Customer customer : repo.findAll()) {
            output += customer.getLastName() + ", ";
        }
        System.out.println(output);
        System.out.println("");

        // Testing findAllById()
        System.out.println("Testing findAllById() - Müller, Bäcker, Stiefel, should be displayed (not ordered):");
        ArrayList<String> ids = new ArrayList<String>();
        ids.add(customer2.getId());
        ids.add(customer3.getId());
        ids.add(customer4.getId());
        output = "";
        for (Customer customer : repo.findAllById(ids)) {
            output += customer.getLastName() + ", ";
        }
        System.out.println(output);
        System.out.println("");

        // Testing deleteById()
        System.out
                .println("Testing deleteById() - Saalfeld, Müller, Bäcker, Schütz, should be displayed (not ordered):");
        repo.deleteById(customer4.getId());
        output = "";
        for (Customer customer : repo.findAll()) {
            output += customer.getLastName() + ", ";
        }
        System.out.println(output);
        System.out.println("");

        // Readding customer4
        repo.save(customer4);

        // Testing delete()
        System.out.println("Testing delete() - Saalfeld, Bäcker, Stiefel, Schütz, should be displayed (not ordered):");
        repo.delete(customer2);
        output = "";
        for (Customer customer : repo.findAll()) {
            output += customer.getLastName() + ", ";
        }
        System.out.println(output);
        System.out.println("");

        // Readding customer2
        repo.save(customer2);

        // Testing deleteAllById()
        System.out.println("Testing deleteAllById() - Saalfeld, Schütz, should be displayed (not ordered):");
        repo.deleteAllById(ids);
        output = "";
        for (Customer customer : repo.findAll()) {
            output += customer.getLastName() + ", ";
        }
        System.out.println(output);
        System.out.println("");

        // Readding customer2, customer3, customer4
        repo.save(customer2);
        repo.save(customer3);
        repo.save(customer4);

        // Testing deleteAll(entities)
        System.out.println("Testing deleteAll(entities) - 1 should be displayed:");
        repo.deleteAll(entities);
        System.out.println(repo.count());
        System.out.println("");

        // Readding customer2, customer3, customer4, customer5
        repo.saveAll(entities);

        // Testing deleteAll()
        System.out.println("Testing deleteAll() - 0 should be displayed:");
        repo.deleteAll();
        System.out.println(repo.count());
        System.out.println("");

        // Readding customer1, customer2, customer3, customer4, customer5
        repo.save(customer1);
        repo.saveAll(entities);

        // Testing saving the same customer twice
        System.out.println("Testing saving the same customer twice - 5 should be displayed:");
        repo.save(customer1);
        System.out.println(repo.count());
        System.out.println("");
        System.out.println("Testing finished..");
    }

}
