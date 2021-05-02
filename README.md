# DealerStat
The aim of the project is to give an independent rating to traders
game items (CS: GO, Fifa, Dota, Team Fortress, etc.). The rating is based on
reviews that everyone offers, while reviews are thorough
verification by trusted persons. The overall top is based on these ratings.
traders in a particular category of games

<h2>Technologies</h2>

<ul class="discharged">
    <li>servlet: Spring MVC</li>
    <li>user authorization: Spring Security</li>
    <li>data access: Spring Data JPA, Hibernate</li>
    <li>RESTful service: Spring MVC</li>
    <li>tests: JUnit, Mockito</li>
    <li>database: MySQL</li>
    <li>servlet container: Apache Tomcat</li>
</ul>

<h2>How to</h2>

<p>
<p>build: <code>package</code> Maven task
<p>run: <code></code>
<p>logs: <code></code>

<h2>Functionality</h2>

1. Creation of traders pages
2. Creating reviews for traders
3. Calculation of the trader's rating
4. Calculation of the overall top traders based on their ratings
5. Filter by games and min-max ratings

    Anonim's opportunities:
            <p> <li> view trader's page</li> </p>
            <p><li>leaves a review</li></p>
            <p><li>placing an order</li></p>
            <p><li>storing in the database a shopping cart of a registered customer</li></p>
  
    Trader's opportunities:
            <p><li>fills out a questionnaire to create his page</li></p>
            <p><li>have game objects for sale</li></p>
        </ul>        
    Administator's opportunities:
            <p><li>checks the review</li></p>
            <p><li>checks questionnaire</li></p>
        </ul>

<h2>Model of database</h2>
<p>The application database consists of 13 related tables.</p>

![Image alt](https://github.com/NikitaGolik/DealerStat/raw/main/src/main/resources/mydb.png)

<p>The data access layer is implemented using the Spring Data JPA repository.</p>

<h2>User classes Spring</h2>
<p>
The functionality of the Spring MVC and Spring Security frameworks has been extended with the following classes:</p>
<ul class="discharged">
    <li><code>UserService</code> extends interface <code>UserDetailsService</code>
        and ensures that the user profile is retrieved from the database;</li>  
   </ul>
   
   <h1>REST web service</h1>
   <p>The REST interface of the application provides access to the trader's game objects: it allows you to register traders, 
   request information about game objects, add comments and ratings for game objects.</p>
   <p>Data exchange between the user and the web service of trade area is carried out in JSON format, authentication is 
   performed using Basic Authentication.</p>
   
<h2>Interacting with the web service</h2>   
<p>Store resources can be accessed using any HTTP client,
       supporting Basic authentication</p>
<h2>Comments</h2> 
<p>So accessing a resource <code>http://localhost:8080/</code>
           returns a list of all comments:</p>
    
           
<p>To create comment for chosen game object you should do POST request: <code>localhost:8080/objects/1/comments</code></p>
           
![Image alt](https://github.com/NikitaGolik/DealerStat/raw/main/src/main/webapp/WEB-INF/images/allComments.png)

<p>Information about one chosen comment by id <code>http://localhost:8080/comments/2</code></p>

![Image alt](https://github.com/NikitaGolik/DealerStat/raw/main/src/main/webapp/WEB-INF/images/getCommentById.png)

<p>All comments by chosen user by his id <code>http://localhost:8080/users/1/comments</code></p>

![Image alt](https://github.com/NikitaGolik/DealerStat/raw/main/src/main/webapp/WEB-INF/images/COmmentsByUser.png)

<p>Also you can see a comment by comment's id, that didn't accept by admin. Available with admin root<code>http://localhost:8080/objects/1/unapproved</code></p>

![Image alt](https://github.com/NikitaGolik/DealerStat/raw/main/src/main/webapp/WEB-INF/images/GEtUnapprovedComment.png)
<p>To update comment (only for user, who wrote this comment): PUT request <code>http://localhost:8080/comments/1</code></p>
<p>Also admin can approve this comment by comment's id <code>http://localhost:8080/comments/{id}/approve</code></p>

<h2> Games</h2>
<p>You can see a list of all games on resource: <code>http://localhost:8080/games</code></p>

![Image alt](https://github.com/NikitaGolik/DealerStat/raw/main/src/main/webapp/WEB-INF/images/GamesAll.png)

<p>Also you can see a chosen game by game id <code>http://localhost:8080/games/1</code></p>

![Image alt](https://github.com/NikitaGolik/DealerStat/raw/main/src/main/webapp/WEB-INF/images/GameById.png)

<p>Admin root allows to update game: request PUT <code>http://localhost:8080/games/1</code></p>

![Image alt](https://github.com/NikitaGolik/DealerStat/raw/main/src/main/webapp/WEB-INF/images/updateGame.png)

<p>Admin root allows to delete game: request DELETE <code>http://localhost:8080/games/1</code></p>
<p>Admin root allows to create game: request POST <code>http://localhost:8080/games/</code></p>

![Image alt](https://github.com/NikitaGolik/DealerStat/raw/main/src/main/webapp/WEB-INF/images/CreateGame.png)

<h2>Game Objects </h2>

<p>You can create game object (object id) with POST request: <code>http://localhost:8080/objects/1/comments</code></p>
<p>You can see Game object by id:<code>http://localhost:8080/objects/3</code></p>
<p>You can see all your game objects on resource: <code>http://localhost:8080/objects/my</code></p>
<p>YOu can update information about your game object (object id): PUT request <code>http://localhost:8080/objects/1</code></p>
<p>YOu can delete your game object (object id): DELETE request <code>http://localhost:8080/objects/1</code></p>

![Image alt](https://github.com/NikitaGolik/DealerStat/raw/main/src/main/webapp/WEB-INF/images/GameObjectById.png)

<p>Admin root allows to get unapproved game objects: <code>http://localhost:8080/objects/1/unapproved</code></p>

![Image alt](https://github.com/NikitaGolik/DealerStat/raw/main/src/main/webapp/WEB-INF/images/GEtUnapprovedGameobject.png)

<p>Admin can approve unapproved game object with POST request on resource: <code>http://localhost:8080/objects/1/approve</code></p>

<p>You can get list of all games from resource <code>http://localhost:8080/objects/games</code> </p>

![Image alt](https://github.com/NikitaGolik/DealerStat/raw/main/src/main/webapp/WEB-INF/images/FindGameObjectByGame.png)

<h2>Registration and Authorization</h2>
The user registers in the system, then makes a request to generate a token. Then he can make authorized requests to the 
server using the token.
If the username and password I gave is correct, You will get back a JWT token containing the login, roles, and ID. after 
that you call the custom authentication service which checks if the user id already exists in its database.
To restore password ask the user to verify their identity by entering their email.
The token is generated and sent to a postal address.

<p>For register new User: POST request <code>http://localhost:8080/auth/register</code> </p>

![Image alt](https://github.com/NikitaGolik/DealerStat/raw/main/src/main/webapp/WEB-INF/images/Register.png)
<p>For Authorization: POST request <code>http://localhost:8080/auth/</code> </p>

![Image alt](https://github.com/NikitaGolik/DealerStat/raw/main/src/main/webapp/WEB-INF/images/Register.png)
<p>For Confirming user (with token): GET request <code>http://localhost:8080/auth/confirm/e896ac9e-93ef-4aba-b9e3-a0fd7c2d74b8</code></p>
<p>If user forgot password, he can reset it: POST request <code>http://localhost:8080/auth/forgot-password</code></p>
<p>WHen user restores password, hi should confirm new password with new code on his email address<code>http://localhost:8080/auth/reset</code></p>
<h2>Users</h2>
<p>You can get all users, all traders, or all anons. For this you can enter this resources:
<code>http://localhost:8080/users</code>
<code>http://localhost:8080/traders</code>
<code>http://localhost:8080/readers</code>
</p>
<p>Find USer by Id: <code>http://localhost:8080/users/3</code></p>
<p>Admin can change a role of user: PATCH request <code>http://localhost:8080/users/1/change-role</code></p>
<p>Admin can delete user: DELETE request <code>http://localhost:8080/users/1/</code></p>





