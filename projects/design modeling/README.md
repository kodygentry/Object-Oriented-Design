# Homework 6 Submission Template
  * Due date on Canvas
  * Tasks on how to build a Functional Model of Shipping Store System: [HW6](HW6.md)
  * Authors: Kody Gentry, Adam McBay

## Task 1 Usecases
  [30 points] Come up with a set of Use Cases that best capture the requirements as described above. Use free text to describe flows of events for each individual use case. Create a usecase Diagram using StarUML. Export it as an image. 

  ### Use Cases
  * Use Case: Contact Store
    * includes Use Case: Respond to Customer
      - Employee responds via email, response recording with Employee ID
      - Answer added with question to pool of answered questions
    - After account creation, add message to queue
    * extends Use Case: Create Account
      * includes Use Case: Edit Account
  * Use Case: Create Transaction
    - Placed by Customer/Employee
    - Store Customer ID, Tracking Number, Employee ID at collection, Date created, Employee pickup/drop, date delivery, Employee ID at delivery, status
    * includes Use Case: Add User to Database 
      - Add Customer/Employee/Admin to database with Name, Phone Number and Email
      - For Customers include Address
      - For Employees include SSN, Salary, Bank Account Number, Status and PIN
      - For Admins include same as Employees + access rights to Accounting Managment and add/edit Employees/Admins
    * includes Use Case: Track Package
    * includes (Optional)Use Case: Print Reciept
  * Use Case: Edit Customer Info
  * Use Case: Add Employee
    * includes Use Case: Edit Employee Info
  * Use Case: Add Administrator
    * includes Use Case: Edit Administrator Info
  * Use Case: Accounting Managment
    * includes Use Case: Update Income
    * includes Use Case: Update Expenses and Profit
    * extends Use Case: Print Documents
  * Use Case: Collect Package
  * Use Case: Deliver Package
	
  ### Usecase StarUML image
  ![alt text](https://git.txstate.edu/CS3354/WGM/blob/master/HW6/Capture.PNG)

  

## Task 2 Class Discovery
  [40 points] Create a set of CRC cards (as tables) and show Class discovery process. Create a Class Diagram using StarUML that shows the relationships between classes (dependences, inheritance, aggregations, compositions, multiplicities, etc.). 
  
  ### CRC Cards
  
  *****************************************
  IF TEXT ISNT FORMATTED CHECK CRC.PNG !!!
  *****************************************	
	
  | --------------------------------------------|| --------------------------------------------|
  |                   Purchase                  ||                     Sale                    |
  | ----------------------|---------------------|| ----------------------|---------------------|
  | handles who purchased | Supplier ID: string || handles info of       |                     |
  |                       |                     || purchasser            | customer ID: string |
  | ----------------------|---------------------|| ----------------------|---------------------|
  | --------------------------------------------|| --------------------------------------------|
  |              Transaction records            ||             Accounting managers             |
  | ----------------------|---------------------|| ----------------------|---------------------|
  | handles transaction   | date: string        || only accessable by    | profit, records     |
  | info                  | employee ID         || admin, handles money  | Income, Expenses    |
  | ----------------------|---------------------|| ----------------------|---------------------|
  | --------------------------------------------|| --------------------------------------------|
  |                   customer                  ||                  employee                   |
  | ----------------------|---------------------|| ----------------------|---------------------|
  | customer info         | Email: string       || employee info         | SSN, salary,bank,pin|
  |                       | Phone number: int   ||                       | activity: bool      |   
  | ----------------------|---------------------|| ----------------------|---------------------|
  | --------------------------------------------|| --------------------------------------------|
  |              user manangement               ||                  Inventory                  |
  | ----------------------|---------------------|| ----------------------|---------------------|
  | admin/employee acess  | ID: string          || decides if package    | package obj         |
  | add user to database  | name: string        || is in inventory       |                     |
  | ----------------------|---------------------|| ----------------------|---------------------|
  | --------------------------------------------|| --------------------------------------------|
  |                   Package                   ||             Shipping store system           |
  | ----------------------|---------------------|| ----------------------|---------------------|
  | handles all info      | type obj, location  || handles info of       | User Records        |
  | for package           | mailing class,      || transaction records   | transaction records |
  |                       | specification       || and everything regard | inventory           |
  |                       | Tracking num/status || ing transaction       | message             |
  | ----------------------|---------------------|| ----------------------|---------------------|
  | --------------------------------------------|| --------------------------------------------|
  |              user records                   ||             package type                    |
  | ----------------------|---------------------|| ----------------------|---------------------|
  | handles user records  | user management     || stores type of package| type: String        |
  | info                  |                     ||                       |                     |
  | ----------------------|---------------------|| ----------------------|---------------------|
  | --------------------------------------------|| 
  |                   message                   ||
  | ----------------------|---------------------|| 
  | handles user msg      | msg                 || 
  | along w their userinfo| user records obj    || 
  | and trans info        |                     || 
  | ----------------------|---------------------|| 

  
  ### Class StarUMl Diagram.
  ![alt text](https://git.txstate.edu/CS3354/WGM/blob/master/HW6/ClassDiagram.png)
   
## Task 3 State Machine Diagram 
  [20 points] Assume a class named “Transactions” which represents shipping transactions. Draw a State Machine Diagram in StarUML that shows the possible states of objects of that class and the transitions based on different events from the moment a shipping transaction is first created by a customer through the Website, until the moment the package has been delivered to its final destination. 
  
  ![alt text](https://git.txstate.edu/CS3354/WGM/blob/master/HW6/statemachine.png)



