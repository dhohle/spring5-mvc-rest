package guru.springfamework.bootstrap;

import guru.springfamework.domain.Category;
import guru.springfamework.domain.Customer;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.CategoryRepository;
import guru.springfamework.repositories.CustomerRepository;
import guru.springfamework.repositories.VendorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final CustomerRepository customerRepository;
    private final VendorRepository vendorRepository;

    public Bootstrap(CategoryRepository categoryRepository, CustomerRepository customerRepository, VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadCategories();
        loadCustomers();
        loadVendors();

        System.out.println("Data Loaded Cats = "+categoryRepository.count());
        System.out.println("Data Loaded Cust = "+customerRepository.count());
        System.out.println("Data Loaded Vens = "+vendorRepository.count());

    }

    private void loadCustomers() {
        customerRepository.save(new Customer(1L, "Joe", "Newman"));
        customerRepository.save(new Customer(2L, "Michael", "Lachappele"));
        customerRepository.save(new Customer(3L, "David", "Boye"));
        customerRepository.save(new Customer(4L, "Freddy", "Meyers"));
    }

    private void loadCategories() {
        Category fruits = new Category("Fruits");
        Category dried = new Category("Dried");
        Category fresh = new Category("Fresh");
        Category exotic = new Category("Exotic");
        Category nuts = new Category("Nuts");

        categoryRepository.save(fruits);
        categoryRepository.save(dried);
        categoryRepository.save(fresh);
        categoryRepository.save(exotic);
        categoryRepository.save(nuts);
    }

    private void loadVendors(){
        this.vendorRepository.save(new Vendor("Vendor 1"));
        this.vendorRepository.save(new Vendor("Vendor 2"));
    }
}
