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
* Application will start on `8080` port. If already used please change on `server.port=8080` accordingly on `./task_manager/src/main/resources/application.properties` file
* Application will search  `3306` port on localhost for `mysql`. If you used different port for mysql then change accordingly on `./task_manager/src/main/resources/application.properties` file
* Change Mysql Database credential `spring.datasource.username=db_username` and `spring.datasource.password=db_password` accordingly on `./task_manager/src/main/resources/application.properties` file

   ```
        spring.datasource.url=jdbc:mysql://db_url:db_port/task_manager?createDatabaseIfNotExist=true&useUnicode=yes&characterEncoding=UTF-8&useSSL=false&autoReconnect=true
        spring.datasource.username=db_username
        spring.datasource.password=db_password
        .
        .
        .
        server.port=8080
   ```
### TECHNOLOGY USED
 * DB: MySQL
 * Language: Java 11
 * FrameWork/Library: SpringBoot, spring security,
  Spring data, hibernate, JWT Web Token.
 * Build Tool: Maven
 * Server: Inbuilt Tomcat
### SETUP INSTRUCTIONS

Before running this application you should have to confirm following: 
* JDK 11 installed.
* maven installed.
* mysql installed.
 ##### Then run the following command on terminal:
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
At Runtime User for ADMIN and USER role will be create.
* ADMIN Role:
   ``` 
        username:admin
        password:admin
   ```

* USER Role:
   ``` 
        username:user
        password:user
   ```

Brows [http://localhost:8080/](http://localhost:8080/).

### API DETAILS

#### USER API:

 * API 1:
    * end-point: /api/user/global/sign-in
    * Method: POST
    * Accessibility: ADMIN, USER
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
 
 * API 2:
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


 * API 1:
    * end-point: /api/project
    * Method: POST
    * Accessibility: ADMIN,USER
    * Purpose: To Create New Project
    * cURL : 
        ```
            curl -X POST \
             http://localhost:8080/api/project \
             -H 'authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTYzMDA5MDA4MCwiZXhwIjoxNjMyNjgyMDgwfQ.WHS0C1BaxxTt_GHAIoweCV5vn9MzRA0X1qw5JbFXI2Ny6GFZ-vm1SFUfzvMAZ8PPOD29a2VzYwR37_Icmu-6rg' \
             -H 'cache-control: no-cache' \
             -H 'content-type: application/json' \
             -H 'postman-token: 9bfea6f3-4d54-3f9f-4792-0856a6ad6ec7' \
             -d '{
            "name":"Test Project"
           }'
        ```
 
 * API 2:
    * end-point: /api/user/project{id}
    * Method: PUT
    * Accessibility: ADMIN,USER
    * Purpose: To Update project.
    * cURL : 
        ```
            curl -X PUT \
              http://localhost:8080/api/project/1 \
              -H 'authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTYzMDA5MDA4MCwiZXhwIjoxNjMyNjgyMDgwfQ.WHS0C1BaxxTt_GHAIoweCV5vn9MzRA0X1qw5JbFXI2Ny6GFZ-vm1SFUfzvMAZ8PPOD29a2VzYwR37_Icmu-6rg' \
              -H 'cache-control: no-cache' \
              -H 'content-type: application/json' \
              -H 'postman-token: 0f799b15-bbfc-cf35-8959-89f99a8ab798' \
              -d '{
            	"name":"Test Project w"
            }'
        ```
 
 * API 3:
    * end-point: /api/user/project/{id}
    * Method: DELETE
    * Accessibility: ADMIN,USER
    * Purpose: To Delete Project.
    * cURL : 
        ```
            curl -X DELETE \
              http://localhost:8080/api/project/3 \
              -H 'authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTYzMDA5MDA4MCwiZXhwIjoxNjMyNjgyMDgwfQ.WHS0C1BaxxTt_GHAIoweCV5vn9MzRA0X1qw5JbFXI2Ny6GFZ-vm1SFUfzvMAZ8PPOD29a2VzYwR37_Icmu-6rg' \
              -H 'cache-control: no-cache' \
              -H 'content-type: application/json' \
              -H 'postman-token: 224bc348-2a60-406e-2130-04a7521bc6c4'
        ```
 
 * API 4:
    * end-point: /api/user/project/find-all
    * Method: GET
    * Accessibility: ADMIN,USER
    * Purpose: To get all Project.
    * cURL : 
        ```
            curl -X POST \
              http://localhost:8080/api/project/find-all \
              -H 'authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTYzMDA5MDA4MCwiZXhwIjoxNjMyNjgyMDgwfQ.WHS0C1BaxxTt_GHAIoweCV5vn9MzRA0X1qw5JbFXI2Ny6GFZ-vm1SFUfzvMAZ8PPOD29a2VzYwR37_Icmu-6rg' \
              -H 'cache-control: no-cache' \
              -H 'content-type: application/json' \
              -H 'postman-token: 99783075-7941-6131-efa0-7b3dd344a606'
        ```
 
 * API 5:
    * end-point: /api/user/project/find-all/by-user/{username}
    * Method: GET
    * Accessibility: ADMIN
    * Purpose: To get all projects by user.
    * cURL : 
        ```
            curl -X GET \
              http://localhost:8080/api/project/find-all/by-user/user2 \
              -H 'authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTYzMDA5MDA4MCwiZXhwIjoxNjMyNjgyMDgwfQ.WHS0C1BaxxTt_GHAIoweCV5vn9MzRA0X1qw5JbFXI2Ny6GFZ-vm1SFUfzvMAZ8PPOD29a2VzYwR37_Icmu-6rg' \
              -H 'cache-control: no-cache' \
              -H 'content-type: application/json' \
              -H 'postman-token: 4b3d2ce4-b2dc-0c5d-325c-95a9cfe84a8c'
        ```
 
 * API 5:
    * end-point: /api/user/project/{id}
    * Method: GET
    * Accessibility: ADMIN, USER
    * Purpose: To get specific projects by id.
    * cURL : 
        ```
            curl -X GET \
              http://localhost:8080/api/project/5 \
              -H 'authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTYzMDA5MDA4MCwiZXhwIjoxNjMyNjgyMDgwfQ.WHS0C1BaxxTt_GHAIoweCV5vn9MzRA0X1qw5JbFXI2Ny6GFZ-vm1SFUfzvMAZ8PPOD29a2VzYwR37_Icmu-6rg' \
              -H 'cache-control: no-cache' \
              -H 'content-type: application/json' \
              -H 'postman-token: 4b3d2ce4-b2dc-0c5d-325c-95a9cfe84a8c'
        ```

##### TASK API:


 * API 1:
    * end-point: /api/task
    * Method: POST
    * Accessibility: ADMIN,USER
    * Purpose: To create new task for a specific project
    * cURL : 
        ```
        curl -X POST \
          http://localhost:8080/api/task/ \
          -H 'authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTYzMDA5MDA4MCwiZXhwIjoxNjMyNjgyMDgwfQ.WHS0C1BaxxTt_GHAIoweCV5vn9MzRA0X1qw5JbFXI2Ny6GFZ-vm1SFUfzvMAZ8PPOD29a2VzYwR37_Icmu-6rg' \
          -H 'cache-control: no-cache' \
          -H 'content-type: application/json' \
          -H 'postman-token: b7654df8-b23f-ea1a-0b0b-e0c5958034c0' \
          -d '{
        	"description":"Test Taks",
        	"dueDate":"20-10-2021",
        	"project":1,
        	"taskStatus":"OPEN"	
        }'
        ```
 
 * API 2:
    * end-point: /api/task/{id}
    * Method: PUT
    * Accessibility: ADMIN,USER
    * Purpose: To update existing task.
    * cURL : 
        ```
            curl -X PUT \
              http://localhost:8080/api/task/52 \
              -H 'authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTYzMDA5MDA4MCwiZXhwIjoxNjMyNjgyMDgwfQ.WHS0C1BaxxTt_GHAIoweCV5vn9MzRA0X1qw5JbFXI2Ny6GFZ-vm1SFUfzvMAZ8PPOD29a2VzYwR37_Icmu-6rg' \
              -H 'cache-control: no-cache' \
              -H 'content-type: application/json' \
              -H 'postman-token: 9626dc9b-aa76-3e20-ef02-819903938a06' \
              -d '{
            	"description":"Test Taks",
            	"dueDate":"20-10-2021",
            	"project":1,
            	"taskStatus":"CLOSED"	
            }'
        ```
 
 * API 3:
    * end-point: /api/task/{id}
    * Method: DELETE
    * Accessibility: ADMIN,USER
    * Purpose: To delete a task.
    * cURL : 
        ```
            curl -X DELETE \
              http://localhost:8080/api/task/52 \
              -H 'authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTYzMDA5MDA4MCwiZXhwIjoxNjMyNjgyMDgwfQ.WHS0C1BaxxTt_GHAIoweCV5vn9MzRA0X1qw5JbFXI2Ny6GFZ-vm1SFUfzvMAZ8PPOD29a2VzYwR37_Icmu-6rg' \
              -H 'cache-control: no-cache' \
              -H 'content-type: application/json' \
              -H 'postman-token: 35c101b8-a443-fc7d-eb95-35d2ecfc6523'
        ```
 
 * API 4:
    * end-point: /api/task/find-all
    * Method: GET
    * Accessibility: ADMIN,USER
    * Purpose: To get all task.
    * cURL : 
        ```
            curl -X GET \
              http://localhost:8080/api/task/find-all \
              -H 'authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTYzMDA5MDA4MCwiZXhwIjoxNjMyNjgyMDgwfQ.WHS0C1BaxxTt_GHAIoweCV5vn9MzRA0X1qw5JbFXI2Ny6GFZ-vm1SFUfzvMAZ8PPOD29a2VzYwR37_Icmu-6rg' \
              -H 'cache-control: no-cache' \
              -H 'content-type: application/json' \
              -H 'postman-token: c36f06f8-983e-2ac3-9661-8c73280af7cd'
        ```
 
 * API 5:
    * end-point: /api/task/find-all/by-project/{projectId}
    * Method: GET
    * Accessibility: ADMIN,USER
    * Purpose: To get all task by project.
    * cURL : 
        ```
            curl -X GET \
              http://localhost:8080/api/task/find-all/by-project/1 \
              -H 'authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTYzMDA5MDA4MCwiZXhwIjoxNjMyNjgyMDgwfQ.WHS0C1BaxxTt_GHAIoweCV5vn9MzRA0X1qw5JbFXI2Ny6GFZ-vm1SFUfzvMAZ8PPOD29a2VzYwR37_Icmu-6rg' \
              -H 'cache-control: no-cache' \
              -H 'content-type: application/json' \
              -H 'postman-token: 0f94fbfc-23fa-cbb7-6d64-8a33d044cb64'
        ```
 
 * API 6:
    * end-point: /api/task/find-all/expired
    * Method: GET
    * Accessibility: ADMIN,USER
    * Purpose: To get all expired task.
    * cURL : 
        ```
            curl -X GET \
              http://localhost:8080/api/task/find-all/expired \
              -H 'authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTYzMDA5MDA4MCwiZXhwIjoxNjMyNjgyMDgwfQ.WHS0C1BaxxTt_GHAIoweCV5vn9MzRA0X1qw5JbFXI2Ny6GFZ-vm1SFUfzvMAZ8PPOD29a2VzYwR37_Icmu-6rg' \
              -H 'cache-control: no-cache' \
              -H 'content-type: application/json' \
              -H 'postman-token: 3981a85e-c102-2ed8-0b77-6ee3cb0d5a08'
        ```
  
 * API 7:
    * end-point: /api/task/find-all/by-status/{status}
    * Method: GET
    * Accessibility: ADMIN,USER
    * Purpose: To get all task by status.
    * cURL : 
        ```
            curl -X GET \
              http://localhost:8080/api/task/find-all/by-status/OPEN \
              -H 'authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTYzMDA5MDA4MCwiZXhwIjoxNjMyNjgyMDgwfQ.WHS0C1BaxxTt_GHAIoweCV5vn9MzRA0X1qw5JbFXI2Ny6GFZ-vm1SFUfzvMAZ8PPOD29a2VzYwR37_Icmu-6rg' \
              -H 'cache-control: no-cache' \
              -H 'content-type: application/json' \
              -H 'postman-token: 6a39798d-9837-6797-cb8c-ba42bb92a381'
        ```
 
 * API 8:
    * end-point: /api/task/find-all/by-user/{username}
    * Method: GET
    * Accessibility: ADMIN
    * Purpose: To get all task by user.
    * cURL : 
        ```
            curl -X GET \
              http://localhost:8080/api/task/find-all/by-user/user1 \
              -H 'authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTYzMDA5MDA4MCwiZXhwIjoxNjMyNjgyMDgwfQ.WHS0C1BaxxTt_GHAIoweCV5vn9MzRA0X1qw5JbFXI2Ny6GFZ-vm1SFUfzvMAZ8PPOD29a2VzYwR37_Icmu-6rg' \
              -H 'cache-control: no-cache' \
              -H 'content-type: application/json' \
              -H 'postman-token: c8348083-be7b-37d7-6a80-aed7dd77c299'
        ```

 * API 9:
    * end-point: /api/task/{id}
    * Method: GET
    * Accessibility: ADMIN,USER
    * Purpose: To get specific task by id.
    * cURL : 
        ```
            curl -X GET \
              http://localhost:8080/api/task/55 \
              -H 'authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTYzMDA5MDA4MCwiZXhwIjoxNjMyNjgyMDgwfQ.WHS0C1BaxxTt_GHAIoweCV5vn9MzRA0X1qw5JbFXI2Ny6GFZ-vm1SFUfzvMAZ8PPOD29a2VzYwR37_Icmu-6rg' \
              -H 'cache-control: no-cache' \
              -H 'content-type: application/json' \
              -H 'postman-token: e1cc1c66-786e-364f-2030-88ec0ec0db39'
        ```
