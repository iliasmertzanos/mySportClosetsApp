package com.Beans;

import java.util.List;

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
public class AthleteService implements ClosetServiceLocal {
	
	@PersistenceContext(unitName="SportClosets")
    EntityManager entityManager;

    /**
     * Default constructor. 
     */
    public AthleteService() {
        // TODO Auto-generated constructor stub
    }
    
    public Athlete addAthlete(Athlete singleAthlete) {
    	entityManager.persist(singleAthlete);   
    	return singleAthlete;
    }
    
  //Difference between SELECT query and java coded query is that the java coded query is safer
    // If there is any problem in the code it want compile, whereas the SELCT query will compile but throw exceptions or give errors back
    
    public List<Athlete> getAthletesList(){
    	TypedQuery <Athlete> athleteQuery=entityManager.createQuery("SELECT a FROM Athlete a ",Athlete.class);
    	List<Athlete> athleteList =athleteQuery.getResultList();
    	return athleteList;
    }
    
    public Athlete findAthlete(String athleteId) {
    	//Find Athlete
    	CriteriaBuilder builder=entityManager.getCriteriaBuilder();
    	CriteriaQuery<Athlete> queryAthlete=builder.createQuery(Athlete.class);
    	
    	Root<Athlete> rootAthelete=queryAthlete.from(Athlete.class);
    	queryAthlete.
    	select(rootAthelete).
    	where(builder.equal(rootAthelete.get("athleteId").as(Integer.class),athleteId));
    	Athlete singleAthlete=entityManager.createQuery(queryAthlete).getSingleResult();
    	return singleAthlete;
    	
    }

}
