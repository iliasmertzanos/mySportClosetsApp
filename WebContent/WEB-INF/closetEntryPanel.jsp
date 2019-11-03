<%@ page import="java.util.*, com.models.*" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<link rel="stylesheet" href="resources/css/jpaStyles.css" />
<title>Closets overview</title>
</head>
<body>


<h2>Available closets overview</h2>

<%List<Closet> closetList = (List<Closet>) request.getAttribute("closetsList");

for(Closet singleCloset:closetList){
 String closetId=singleCloset.getClosetId()+"";
 String buttonIdAddNew="ADD_NEW_";
 String buttonIdAddExisting="ADD_EXIST_";
 String buttonIdRemove="REMOVE_ATHLETE_";
 String firstName="";
 boolean isBooked=singleCloset.getAthlete()!=null;
 Athlete singleAthlete=null;
 if(isBooked){
 	singleAthlete=singleCloset.getAthlete();
 }
 
%>
<div class="flip-card">
  <div class="flip-card-inner">
    <div class="flip-card-front">
      <img src="https://tse2.mm.bing.net/th?id=OIP.hFAFCCBCvAFUpULbKqOvrAHaHa&pid=Api" alt="Avatar" style="width:300px;height:300px;">
      <div class="top-left"><font color="white"><%= singleCloset.getClosetId() +(isBooked?" is booked by Athlete: "+singleAthlete.getAthleteId() :"") %></font></div>
    </div>
    <div class="flip-card-back">
      <form method="POST" action="ManageClosets">
		<br /><br />
		
		First name:  

		<input <%=isBooked?"disabled":""%> name="firstName" type="text" value="<%=singleCloset.getAthlete()!=null?singleCloset.getAthlete().getFirstName():""  %>"></input>

		<br /><br />

		Last name:  

		<input <%=isBooked?"disabled":""%> name="lastName" type="text">  </input>

		<br /><br />

		Phone Number:  
	
		<input <%=isBooked?"disabled":""%> name="phoneNumber" type="text"></input>

		<br /><br />
		
		Gender:
		
		<select <%=isBooked?"disabled":""%> name="gender">
			<option value="Male">Male</option>
			<option value="Female">Female</option>
		</select>

		<button <%=isBooked?"disabled":""%> value="<%=closetId%>" type="submit" name="<%=buttonIdAddNew%>">Add new athlete to locker</button>
		<br /><br />
		
		Number Id:  

		<input name="atheteID" type="text"></input>
		<%if(!isBooked){%>
			<button type="submit" value="<%=closetId%>" name="<%=buttonIdAddExisting%>">Add existing athlete to locker</button>
		<%}else{%>
			<button type="submit" value="<%=closetId%>" name="ADD_WAITING_">Add existing athlete to waiting list</button>
			<button type="submit" value="<%=closetId%>" name="CLEAR_WAITING_">Clear waiting list</button>
		<%}%>
		
		
		<%
		
		if(isBooked){%>
			<button value="<%=closetId%>" type="submit" name="<%=buttonIdRemove%>">Remove athlete from locker</button>
		<%}%>
		<br /><br />
		
		<hr />

	</form>
    </div>
  </div>
</div>
<%}%>


</body>
</html>