package hh.ohjelmistokehityksenteknologioita.Workhoursmanagementapp.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface AccountRepository extends CrudRepository<Account, Long> {
	Account findByUsername(String username);
}
