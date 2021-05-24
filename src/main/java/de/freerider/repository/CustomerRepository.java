package de.freerider.repository;

import java.lang.StackWalker.Option;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import org.springframework.stereotype.Component;

import de.freerider.model.Customer;

@Component
public class CustomerRepository implements CrudRepository<Customer, String> {

    private final IDGenerator idGen = new IDGenerator("C", IDGenerator.IDTYPE.NUM, 6);

    private HashMap<String, Customer> data;

    public CustomerRepository() {
        this.data = new HashMap<String, Customer>();
    }

    @Override
    public <S extends Customer> S save(S customer) {
        if (!data.containsValue(customer)) {
            String id = customer.getId();
            if (id == null || id.equals("")) {
                id = idGen.nextId();
                while (data.containsKey(id)) {
                    id = idGen.nextId();
                }
            }
            customer.setId(id);
            data.put(customer.getId(), customer);
        }
        return customer;
    }

    @Override
    public <S extends Customer> Iterable<S> saveAll(Iterable<S> entities) {
        for (Customer customer : entities) {
            if (!data.containsValue(customer)) {
                String id = customer.getId();
                if (id == null || id.equals("")) {
                    id = idGen.nextId();
                    while (data.containsKey(id)) {
                        id = idGen.nextId();
                    }
                }
                customer.setId(id);
                data.put(customer.getId(), customer);
            }
        }
        return entities;
    }

    @Override
    public Optional<Customer> findById(String id) {
        Customer customer = data.get(id);
        Optional<Customer> optional = Optional.of(customer);
        return optional;
    }

    @Override
    public boolean existsById(String id) {
        Boolean state = findById(id).isPresent();
        return state;
    }

    @Override
    public Iterable<Customer> findAll() {
        return data.values();
    }

    @Override
    public Iterable<Customer> findAllById(Iterable<String> ids) {
        ArrayList<Customer> customers = new ArrayList<Customer>();
        for (String id : ids) {
            customers.add(findById(id).get());
        }
        return customers;
    }

    @Override
    public long count() {
        return data.size();
    }

    @Override
    public void deleteById(String id) {
        data.remove(id);
    }

    @Override
    public void delete(Customer entity) {
        data.remove(entity.getId());
    }

    @Override
    public void deleteAllById(Iterable<? extends String> ids) {
        for (String id : ids) {
            data.remove(id);
        }
    }

    @Override
    public void deleteAll(Iterable<? extends Customer> entities) {
        for (Customer customer : entities) {
            data.remove(customer.getId());
        }
    }

    @Override
    public void deleteAll() {
        data.clear();
    }
}
