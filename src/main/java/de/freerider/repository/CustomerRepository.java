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
        if (customer != null) {
            if (!data.containsValue(customer)) {
                String id = customer.getId();
                if (!data.containsKey(id)) {
                    if (id == null || id.equals("")) {
                        id = idGen.nextId();
                        while (data.containsKey(id)) {
                            id = idGen.nextId();
                        }
                    }
                    customer.setId(id);
                    data.put(customer.getId(), customer);
                } else {
                    Customer oldCustomer = data.get(id);
                    data.remove(customer.getId());
                    data.put(customer.getId(), customer);
                    return (S) oldCustomer;
                }
            }
        } else {
            throw new IllegalArgumentException();
        }
        return customer;
    }

    @Override
    public <S extends Customer> Iterable<S> saveAll(Iterable<S> entities) {
        if (entities != null) {
            for (Customer customer : entities) {
                if (!data.containsValue(customer) && customer != null) {
                    String id = customer.getId();
                    if (!data.containsKey(id)) {
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
            }
        } else {
            throw new IllegalArgumentException();
        }
        return entities;
    }

    @Override
    public Optional<Customer> findById(String id) {
        if (id == null) {
            throw new IllegalArgumentException();
        } else {
            Customer customer = data.get(id);
            Optional<Customer> optional = Optional.ofNullable(customer);
            return optional;
        }
    }

    @Override
    public boolean existsById(String id) {
        if (id == null) {
            throw new IllegalArgumentException();
        } else {
            if (data.containsKey(id)) {
                /* Boolean state = findById(id).isPresent(); */
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public Iterable<Customer> findAll() {
        return data.values();
    }

    @Override
    public Iterable<Customer> findAllById(Iterable<String> ids) {
        if (ids == null) {
            throw new IllegalArgumentException();
        } else {
            ArrayList<Customer> customers = new ArrayList<Customer>();
            if (ids != null) {
                for (String id : ids) {
                    if (data.containsKey(id)) {
                        customers.add(findById(id).get());
                    }
                }
            }
            return customers;
        }
    }

    @Override
    public long count() {
        return data.size();
    }

    @Override
    public void deleteById(String id) {
        if (id == null) {
            throw new IllegalArgumentException();
        } else {
            data.remove(id);
        }
    }

    @Override
    public void delete(Customer entity) {
        if (entity == null || entity.getId() == null) {
            throw new IllegalArgumentException();
        } else {
            data.remove(entity.getId());
        }
    }

    @Override
    public void deleteAllById(Iterable<? extends String> ids) {
        if (ids == null) {
            throw new IllegalArgumentException();
        } else {
            for (String id : ids) {
                data.remove(id);
            }
        }
    }

    @Override
    public void deleteAll(Iterable<? extends Customer> entities) {
        if (entities == null) {
            throw new IllegalArgumentException();
        } else {
            for (Customer customer : entities) {
                if (customer != null) {
                    if (customer.getId() == null) {
                        throw new IllegalArgumentException();
                    }
                    data.remove(customer.getId());
                }
            }
        }
    }

    @Override
    public void deleteAll() {
        data.clear();
    }
}
