package hh.ohjelmistokehityksenteknologioita.Workhoursmanagementapp.domain;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import hh.ohjelmistokehityksenteknologioita.Workhoursmanagementapp.domain.Workday;

public interface WorkdayRepository extends CrudRepository<Workday, Long>{

	List<Workday> findByDate(String date);
	List<Workday> findByOwner(String owner);
	List<Workday> findByPaycycle(Long paycycleId);
}
