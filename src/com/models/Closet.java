package com.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Closet
 *
 */
@Entity

public class Closet implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer closetId;
	
	private boolean isOccupied=false;
	
	private boolean isBroken=false;
	
	@Enumerated(EnumType.STRING)
	private Rank closetRank=Rank.REGULAR;
	
	//define that property Airplane is going to be saved as a data set in the table
	@OneToOne (cascade= {CascadeType.PERSIST,CascadeType.REMOVE})
	//define the name of the column that will point to the joint Column in the Table Airplane
	@JoinColumn(name="athleteId")
	private Athlete athlete;
	
//	private List<Athlete> bookingList;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date reservationStarted;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date reservationEnded;
	
	@Enumerated(EnumType.STRING)
	private Gender gender;
	
	@OneToMany(mappedBy="closetWaiting")
	private List<Athlete> waitingList;
	
	public Integer getClosetId() {
		return closetId;
	}

	public List<Athlete> getWaitingList() {
		return waitingList;
	}

	public void setWaitingList(List<Athlete> waitingList) {
		this.waitingList = waitingList;
	}

	public void setClosetId(Integer closetId) {
		this.closetId = closetId;
	}

	public boolean isOccupied() {
		return isOccupied;
	}

	public void setOccupied(boolean isOccupied) {
		this.isOccupied = isOccupied;
	}

	public boolean isBroken() {
		return isBroken;
	}

	public void setBroken(boolean isBroken) {
		this.isBroken = isBroken;
	}

	public Rank getClosetRank() {
		return closetRank;
	}

	public void setClosetRank(Rank closetRank) {
		this.closetRank = closetRank;
	}

	public Athlete getAthlete() {
		return athlete;
	}

	public void setAthlete(Athlete athlete) {
		this.athlete = athlete;
	}

	public Date getReservationStarted() {
		return reservationStarted;
	}

	public void setReservationStarted(Date reservationStarted) {
		this.reservationStarted = reservationStarted;
	}

	public Date getReservationEnded() {
		return reservationEnded;
	}

	public void setReservationEnded(Date reservationEnded) {
		this.reservationEnded = reservationEnded;
	}

	public Closet() {
		super();
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}
   
}
