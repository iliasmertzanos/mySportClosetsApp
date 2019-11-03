package com.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Athelt
 *
 */
@Entity

public class Athlete implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer athleteId;
	
	private String firstName;
	
	private String lastName;
	
//	private List<Sport> sportAttending;
	private Long phoneNumber;
	
	@Enumerated(EnumType.STRING)
	private Gender gender;
	
	//define the variable in the Entity Class that points to the current entity class
	@OneToOne(mappedBy="athlete")
	private Closet closet;
	
	@ManyToOne
	@JoinColumn(name="closetWaiting")
	private Closet closetWaiting; 
		
	public Closet getClosetWaiting() {
		return closetWaiting;
	}

	public void setClosetWaiting(Closet closetWaiting) {
		this.closetWaiting = closetWaiting;
	}

	public Integer getAthleteId() {
		return athleteId;
	}

	public void setAthleteId(Integer athleteId) {
		this.athleteId = athleteId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(Long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Closet getCloset() {
		return closet;
	}

	public void setCloset(Closet closet) {
		this.closet = closet;
	}
	
	public Athlete() {
		super();
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}
   
}
