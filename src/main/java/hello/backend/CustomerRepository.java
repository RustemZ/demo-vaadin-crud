package hello.backend;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CustomerRepository extends JpaRepository<ContactPerson, Long> {

	List<ContactPerson> findByLastNameStartsWithIgnoreCase(String lastName);
}
