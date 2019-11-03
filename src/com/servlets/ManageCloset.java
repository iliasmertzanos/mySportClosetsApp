package com.servlets;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Beans.AthleteService;
import com.Beans.ClosetService;
import com.models.Athlete;
import com.models.Closet;
import com.models.Gender;
import com.sun.xml.ws.util.StringUtils;

/**
 * Servlet implementation class ManageCloset
 */
@WebServlet("/ManageClosets")
public class ManageCloset extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@EJB
	ClosetService closetService;
	
	@EJB
	AthleteService athleteService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ManageCloset() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int closetsAmmount=10;
		List<Closet> closetsList=new ArrayList<Closet>();
		
		closetsList=closetService.getClosetsList();
		
		if( closetsList.isEmpty()) {
			int i=1;
			while(i<=closetsAmmount) {
				Closet singleCloset=new Closet();					
				closetsList.add(closetService.addSingleCloset(singleCloset));
				i++;
			}
		}
		
		request.setAttribute("closetsList", closetsList);
		
		RequestDispatcher view =request.getRequestDispatcher("WEB-INF/closetEntryPanel.jsp");
		
		view.forward(request, response);		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher view =request.getRequestDispatcher("WEB-INF/closetEntryPanel.jsp");
		
		String closetId="";
		String athleteID="";
		String firstName="";
		String lastName="";
		String phoneNumber="";
		Gender gender;
		Athlete singleAthlete=null;
		
	    if(request.getParameter("ADD_NEW_") != null) {
	    	closetId=request.getParameter("ADD_NEW_");
	    			
	    	singleAthlete=new Athlete();
			
			String gdStr=request.getParameter("gender");
			gender=Gender.valueOf(gdStr);
			singleAthlete.setGender(gender);
			
			firstName=request.getParameter("firstName");
			singleAthlete.setFirstName(firstName);
			
			lastName=request.getParameter("lastName");
			singleAthlete.setLastName(lastName);
			
			phoneNumber=request.getParameter("phoneNumber");
			singleAthlete.setPhoneNumber(Long.parseLong(phoneNumber));
			
			closetService.addNewAthleteToCloset(singleAthlete, closetId);
	    }else if(request.getParameter("ADD_EXIST_") != null) {
	    	closetId=request.getParameter("ADD_EXIST_");
	    	athleteID=request.getParameter("atheteID");
	    	singleAthlete=athleteService.findAthlete(athleteID);
			closetService.addExistingAthleteToCloset(singleAthlete, closetId);
	    }else if(request.getParameter("REMOVE_ATHLETE_") != null) {
	    	closetId=request.getParameter("REMOVE_ATHLETE_");
	    	closetService.removeAthlete(closetId);
	    }else if(request.getParameter("ADD_WAITING_") != null) {
	    	closetId=request.getParameter("ADD_WAITING_");
	    	athleteID=request.getParameter("atheteID");
//	    	singleAthlete=athleteService.findAthlete(athleteID);
	    	closetService.addAthleteIntoWaitingList(closetId,athleteID);
	    }else if(request.getParameter("CLEAR_WAITING_") != null) {
	    	closetId=request.getParameter("CLEAR_WAITING_");
	    	closetService.clearWaitingList(closetId);
	    }
	    
		List<Closet> closetsList=closetService.getClosetsList();
		request.setAttribute("closetsList", closetsList);
		view.forward(request, response);		
	}

}
