package hh.ohjelmistokehityksenteknologioita.Workhoursmanagementapp.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface PaycycleRepository extends CrudRepository<Paycycle, Long>{
	
	List<Paycycle>findByName(String name);
	List<Paycycle>findByOwner(String owner);
	List<Paycycle>findByOwnerAndName(String owner, String name);

}