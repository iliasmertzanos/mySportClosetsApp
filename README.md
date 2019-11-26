# mySportClosetsApp
------------
## This app provides a solution for a sport club in order to manage its sports closets, like assigne a closet to a club member and so on.

## Tech
It is based on JAVA EE using GlassFish web server.

## App description

The main entities that are being managed from this app are the `Athlete` and the `Closet`. 
This are to be found in the `com.models` package.

The main controller `ManageCloset` provides the following endpoints in order to manage the entities: 

- `protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException`

- `protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException `

Both of them are accessible over the user interface `closetEntryPanel.jsp` in the package WebContent/WEB-INF.

There is also a service layer in the package `com.Beans` that handles the access to the database. 
Inserts, updates, deletes and reads objects from the database.




