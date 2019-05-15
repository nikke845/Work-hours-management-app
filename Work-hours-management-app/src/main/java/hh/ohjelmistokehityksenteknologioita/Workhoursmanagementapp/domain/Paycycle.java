package hh.ohjelmistokehityksenteknologioita.Workhoursmanagementapp.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Paycycle {
	@Id
	@GeneratedValue(strategy =GenerationType.AUTO)
	private long paycycleid;
	private String name;
	private String owner;
	


	@OneToMany(cascade =CascadeType.ALL, mappedBy = "paycycle")
	private List<Workday> workdays;
	
	public Paycycle() {
		
	}
	
	public Paycycle (String name, String owner) {
		super();
		this.name = name;
		this.owner = owner;
	}
	
	public Long getPaycycleid() {
		return paycycleid;
	}
	
	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public void setPaycycleid(Long paycycleid) {
		this.paycycleid = paycycleid;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public List<Workday> getWorkdays(){
		return workdays;
	}
	
	public void setWorkdays(List<Workday> workdays) {
		this.workdays = workdays;
	}
	
	public String getUsername() {
		return owner;
	}

	public void setUsername(String username) {
		this.owner = username;
	}
	
	public String toString() {
		return "Paycycle [paycycleid=" + paycycleid + ", name=" + name + ", username=" + owner+ "]";
	}
}
