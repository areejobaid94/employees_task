# switch_task

## Web service to handle employees

### Made By: Areej Obaid

### Check the file to lear more about the requirement =>  [Task Requirements](https://drive.google.com/file/d/1X-yXeJw4FegF9ADyUYsFUfJ8IlXXxAqN/view?usp=sharing)

### [The Live Website](https://drive.google.com/file/d/1X-yXeJw4FegF9ADyUYsFUfJ8IlXXxAqN/view?usp=sharing)

### Note: I have completed the task perfectly, and I have added the following:

* I created a table for departments, and add a one-to-many relationship between the department table and the employee table.
* I created the app_user table, and add a one-to-one relationship between the employee table and the department table.
* The app_user table contains information such as username, password, and is_adimn.
* After the admin adds a new employee, the System will automatically add the new_app user with username equal finstname + lastname and password equal username and a random number between 0 and 1000.
* The app_user table helps the employee logging in, after logging in the employee can add notes.
* I also created a note table with many to one relationship with the employee table.
* To make the login process easier, I used JWT authentication spring boot, using the data in the app_user table.
* Using is_admin in the app_user table defines the permissions of the employee so that no employee can add, modify, remove, or search by name or department for an employee if he is an admin.
* The admin can also search for employees who have a salary higher than the input salary.
* The admin can also add a department.
* The admin can see all departments.
* After logging in, an employee can add or see his notes.
* The ADMIN can search for active and not active employees.
* The admin can see all the employees.
* The admin can update the salaries based on a ratio.
* I deployed the app to Heroku.

### ER diagram for the SQL database

![](img/Employees_Schema.png)


## Table of all API's

| The API | HTTP request method | The Usage | Notes |
|---------| ------------------- |-----------|-------|
| '/login' | Post | login using password and username |-|
| '/department' | Post | Add department | You need to login as an admin and also to send the token in the header. |
| '/departments' | Get | Get All departments | You need to login as an admin and also to send the token in the header. |      
| '/employee' | Post | Add employee | You need to login as an admin and also to send the token in the header. |
| '/employee/{id}' | Delete | Delete employee | You need to login as an admin and also to send the token in the header. |
| '/employee' | Put | update employee | You need to login as an admin and also to send the token in the header. |
| '/active_employee' | Get | Get all active or not active employees | You need to login as an admin and also to send the token in the header. |
| '/employees' | Get | Get all employees | You need to login as an admin and also to send the token in the header. |
| '/search_employees' | Get | Search for employees using firstname or/and lastname or/and department | You need to login as an admin and also to send the token in the header. |
| '/salary_employee' | Get | Search for employees using the salary, get employees with salary more than or equal | You need to login as an admin and also to send the token in the header. |
| '/salary_employee' | Put | Update the salary usinf ratio | You need to login as an admin and also to send the token in the header. |
| '/notes' | Get | Get logged employee notes | You need to login also you need to send the token in the header. |
| '/note' | Post | Add note | You need to login also you need to send the token in the header. |