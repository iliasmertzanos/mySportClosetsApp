package com.Beans;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.models.Athlete;
import com.models.Closet;

/**
 * Session Bean implementation class ClosetService
 */
@Stateless
@LocalBean
public class ClosetService implements ClosetServiceLocal {
	
	@PersistenceContext(unitName="SportClosets")
    EntityManager entityManager;
	
	@EJB
	AthleteService athleteService;

    /**
     * Default constructor. 
     */
    public ClosetService() {
        // TODO Auto-generated constructor stub
    }
    
    public Closet addSingleCloset(Closet singleCloset) {
    	entityManager.persist(singleCloset);   
    	return singleCloset;
    }
    
  //Difference between SELECT query and java coded query is that the java coded query is safer
    // If there is any problem in the code it want compile, whereas the SELCT query will compile but throw exceptions or give errors back
    
    public List<Closet> getClosetsList(){
    	TypedQuery <Closet> closetQuery=entityManager.createQuery("SELECT c FROM Closet c ",Closet.class);
    	List<Closet> closetList =closetQuery.getResultList();
    	return closetList;
    }
    
    public Closet addNewAthleteToCloset(Athlete singleAthlete, String closetId) {
    	//Find Closet
    	CriteriaBuilder builder=entityManager.getCriteriaBuilder();
    	CriteriaQuery<Closet> queryCloset=builder.createQuery(Closet.class);
    	
    	Root<Closet> rootCloset=queryCloset.from(Closet.class);
    	queryCloset.select(rootCloset).where(builder.equal(rootCloset.get("closetId").as(Integer.class),closetId));
    	Closet singleCloset=entityManager.createQuery(queryCloset).getSingleResult();
    	
    	singleCloset.setAthlete(singleAthlete);
    	singleAthlete.setCloset(singleCloset);
    	
    	return singleCloset;
    }
    
    public Closet addExistingAthleteToCloset(Athlete singleAthlete, String closetId) {
    	//Find Closet
    	CriteriaBuilder builder=entityManager.getCriteriaBuilder();
    	CriteriaQuery<Closet> queryCloset=builder.createQuery(Closet.class);
    	
    	Root<Closet> rootCloset=queryCloset.from(Closet.class);
    	queryCloset.select(rootCloset).where(builder.equal(rootCloset.get("closetId").as(Integer.class),closetId));
    	Closet singleCloset=entityManager.createQuery(queryCloset).getSingleResult();
    	
    	singleCloset.setAthlete(singleAthlete);
    	singleCloset=entityManager.merge(singleCloset);
    	
    	return singleCloset;
    }
    
    public Closet removeAthlete(String closetId) {
    	//Find Closet
    	CriteriaBuilder builder=entityManager.getCriteriaBuilder();
    	CriteriaQuery<Closet> queryCloset=builder.createQuery(Closet.class);
    	
    	Root<Closet> rootCloset=queryCloset.from(Closet.class);
    	queryCloset.select(rootCloset).where(builder.equal(rootCloset.get("closetId").as(Integer.class),closetId));
    	Closet singleCloset=entityManager.createQuery(queryCloset).getSingleResult();
    	
    	Athlete singleAthlete =singleCloset.getAthlete();
    	singleAthlete.setCloset(null);
    	singleCloset.setAthlete(null);
//    	singleCloset=entityManager.merge(singleCloset);
    	
    	return singleCloset;
    }
    
    public void addAthleteIntoWaitingList(String closetId, String athleteId ) {
    	Closet singleCloset=entityManager.find(Closet.class, Integer.parseInt(closetId));
    	
    	//Find Closet
//    	CriteriaBuilder builder=entityManager.getCriteriaBuilder();
//    	CriteriaQuery<Closet> queryCloset=builder.createQuery(Closet.class);
//    	
//    	Root<Closet> rootCloset=queryCloset.from(Closet.class);
//    	queryCloset.select(rootCloset).where(builder.equal(rootCloset.get("closetId").as(Integer.class),closetId));
//    	Closet singleCloset=entityManager.createQuery(queryCloset).getSingleResult();
    	
    	//Find Athlete
//    	builder=entityManager.getCriteriaBuilder();
//    	CriteriaQuery<Athlete> queryAthlete=builder.createQuery(Athlete.class);
//    	
//    	Root<Athlete> rootAthelete=queryAthlete.from(Athlete.class);
//    	queryAthlete.
//    	select(rootAthelete).
//    	where(builder.equal(rootAthelete.get("athleteId").as(Integer.class),athleteId));
//    	Athlete singleAthlete=entityManager.createQuery(queryAthlete).getSingleResult();
    	
    	Athlete singleAthlete=entityManager.find(Athlete.class, Integer.parseInt(athleteId));
    	
    	if(isAthleteInWaitingList(athleteId)) { 
    		System.out.println("Athlete is already in a waiting list");
    		return;
    	}
    	if(hasCloset(athleteId))  { 
    		System.out.println("Athlete has already a closet");
    		return;
    	}
    	
    	List<Athlete> waitingList=singleCloset.getWaitingList();    	
    	waitingList.add(singleAthlete);    	
    	singleCloset.setWaitingList(waitingList);
    	
    	singleAthlete.setClosetWaiting(singleCloset);
    	
//    	entityManager.merge(singleCloset); 
    }
    
    public boolean isAthleteInWaitingList(String athleteId) {
    	Athlete singleAthlete=entityManager.find(Athlete.class, Integer.parseInt(athleteId));
    	Closet singleCloset=singleAthlete.getClosetWaiting();
    	return singleCloset!=null;
    }
    
    public boolean hasCloset(String athleteId) {
    	Athlete singleAthlete=entityManager.find(Athlete.class, Integer.parseInt(athleteId));
    	
    	return singleAthlete.getCloset()!=null;
    }
    
    public void clearWaitingList(String closetId) {
    	Closet singleCloset=entityManager.find(Closet.class, Integer.parseInt(closetId));
    	List<Athlete> waitingList=singleCloset.getWaitingList();
    	for(Athlete singleAthlete:waitingList){
    		singleAthlete.setClosetWaiting(null);
    		entityManager.merge(singleAthlete);
    	}
    	singleCloset.setWaitingList(null);
    }

}
