package guru.springfamework.repositories;

import guru.springfamework.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    public Optional<Customer> findByFirstname(final String firstname);
    public Optional<Customer> findByLastname(final String lastname);

}
