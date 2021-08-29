# Task Management REST API

This is a back-end code repository to manage project and task of that project.
##### Features included:
 *  REST API for task managing using JSON format for interchange
 *  Secure endpoints with Spring Security. There should be two roles: USER and ADMIN.
 *  USER can only access own tasks and projects
 *  Authentication credentials has been stored in DB
  ##### USER and ADMIN should be able to:
  * Create project
  * Get all projects
  * Delete project
  * Create task
  * Edit task (change description, status, due date). Closed tasks cannot be edited.
  * Search tasks
    * Get all by project
    * Get expired tasks (due date in the past)
    * By status
  ##### ADMIN additionally should be able to:
  * Get all tasks by user
  * Get all projects by user
* REST API will validate data and return validation errors if data is invalid
* DB should be automatically created (if doesn't exist) on application startup
* Application will start on `8080` port. If already used please change on `server.port=8080` accordingly on `application.properties` file
* Application will search  `3306` port on localhost for `mysql`. If you used different port for mysql then change accordingly on `application.properties` file
### TECHNOLOGY USED
 * DB: MySQL
 * Language: Java 11
 * FrameWork/Library: SpringBoot, spring security,
  Spring data, hibernate, JWT Web Token etc  
 * Build Tool: Maven
 * Server: Inbuilt Tomcat
### SETUP INSTRUCTIONS

Before running this application you should have to confirm following instruction: 
 * JDK 11 installed
 * maven installed
 * mysql installed
 ##### Then run the following command on termina:
   ```
        git clone https://masudrana@bitbucket.org/masudrana/task_manager.git
        cd task_manager
        mvn spring-boot:run
   ```
Successfully running application will show the message like bellow on terminal:
   ``` 
        o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
        com.ovalhr.taskmanager.Application       : Started Application in 5.286 seconds (JVM running for 5.772)
   ```
Brows [http://localhost:8080/](http://localhost:8080/)

### API DETAILS
#### USER API:
 #####API 1:
 * End-point: /api/user/global/create
 * Method: GET
 * Accessibility: ALL 
 * Purpose: To create first `admin` user whose password also `admin` and role `ADMIN`

 #####API 2:
 * end-point: /api/user/global/sign-in
 * Method: POST
 * Accessibility: ALL
 * Purpose: To sign in and get access token. you should have to keep the return token for using any end-point 
 * cURL : 
   ```
        curl -X POST \
          http://localhost:8080/api/global/user/sign-in \
          -H 'content-type: application/json' \
          -d '{
        	 "username": "admin",
             "password": "admin"
            }'
   ```
* API 3:
  * end-point: /api/user/sign-up
  * Method: POST
  * Accessibility: ADMIN
  * Purpose: To create new user.
  * cURL : 
    ```
        curl -X POST \
          http://localhost:8080/api/user/sign-up \
          -H 'authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTYzMDA5MDA4MCwiZXhwIjoxNjMyNjgyMDgwfQ.WHS0C1BaxxTt_GHAIoweCV5vn9MzRA0X1qw5JbFXI2Ny6GFZ-vm1SFUfzvMAZ8PPOD29a2VzYwR37_Icmu-6rg' \
          -H 'content-type: application/json' \
          -d '{
        	"username":"user1",
        	"plainPassword":"user",
        	"role":"ROLE_USER",
        	"enabled":true
        }'
    ```
           
##### PROJECT API:
  
##### TASK API:
