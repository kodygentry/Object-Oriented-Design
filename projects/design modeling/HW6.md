# Homework - Functional Model of Shipping Store System 
## Due date on Canvas 

This homework can be done either individually or with a team. 
* If you are working with a partner, upload it to one repository, note both authors

https://git.txstate.edu/CS3354/ID.git 

# Goal: Create a functional model of a shipping store system
Analyze the requirements of a shipping store management software and come up with an appropriate UML design. Use StarUML software to draw class and state machine UML diagrams.  The designed system needs to include software components for Inventory, Transaction, Accounting, and User, and Store web site management. 

# Inventory Management
The software should maintain information about the packages currently in the inventory of the shipping store. A package is in the inventory if it has been shipped but not yet delivered to its destination.  All package types share some common attributes: 
* Tracking number (string of length 5)
* Specification (string – A short description of the contents)
* Mailing Class (string – the destination address)
* Location (sting - the address where the last scan of the package happened)
Additional attributes for specific types of package that need to be in recorded are:

|Package Type   | Additional Information                        |
| ------------- | ----------------------------------------------|
| Envelope      | Height and widths in inches, integer          |
| Box           | Linear inches, integer                        |
| Crate         | Content (String); Max load weight in lb, float|
| Drum          | Material (String); diameter, float            |

# Transaction Management
A shipping transaction can be created either by a customer through the store’s website (see below) or by an employee. A transaction can be in three different states: Pending: when the shipping transaction record has been created but the package was not dropped off to the store yet or picked up by an Employee - it has not been added to the inventory; Active: when the package has been dropped off to the store or picked up by an Employee and it has been added to its inventory; Completed: when the package has been delivered to its destination and has been removed from the inventory. 

For each transaction, the system keeps the following information: Customer id: who shipped the package;  Package Tracking Number; Cost of shipping; Employee ID: employee who collected the package in the store or picked it up from residence (empty if package has not been collected yet); Date shipping transaction was created; Employee pickup or dropped at the store; Date of final delivery: empty if the package has not yet been delivered; Employee ID: employee who delivered the package (empty if the package has not yet been delivered); Status: Pending, Active,  and Completed.  
When the employee collects the package from the customer (or the customer drops off the package in the store), the status of the transaction is set to Active and the package is added to the inventory. When an employee delivers a package to its destination, the package is removed from the inventory and the status of the transaction is set to Completed. The package should exist in the inventory only when the shipping transaction status is Active.


# Accounting Management
The accounting management part of the software allows the software administrators to keep track of the income, expenses and profit, and print Financial Documents like Balance Sheets, Statements of Cash Flow and Invoices. To keep things simple, we will assume that all the income comes from package shipping transaction sales and all the expenses come from employee salaries and space rental. Every time a new package is shipped, or an expense is paid the accounting must be updated and optionally a financial document can be printed.


# User Management
The system supports three types of users: Customers, Employees and Administrators. For all user types, the system keeps some basic information, like their Name, Phone Number and Email. In addition, for customers, the system keeps record of their address. For employees, the system keeps records of their SSN, Monthly Salary, their Bank Account Number, a flag indicating if they are Active or Inactive and their PIN number. Past employees remain in the system but are considered inactive.  The administrators are special types of employees with some extra system access rights.  Administrator can add/edit an employee or another administrator to the system and can access the accounting management part of the software, from where they can pay expenses and print financial documents at any time.  Regular employees can only create shipping transactions, collect and deliver packages, add/edit customer info to the system, and reply to customer messages.

# Shipping Store Website
The shipping store has a website which allows customers to create an account (and edit it), create shipping transactions, track packages, and contact the shipping store through a web form about possible questions that they may have. When a customer creates a shipping transaction through the website, initially the Employee ID is left empty (since there is no employee involved yet) and the transaction status is set to Pending. Later, when the package is dropped off to the store or picked up by an employee, the employee id is populated with the id of the employee who collected the package, and the transaction status is set to Active. Note that a shipping transaction can be created either by a customer through the website or by an employee at the store. The customer (and/or the employee) can also optionally print a receipt and/or a shipping label when they create the shipping transaction.   The website also offers the option to customers to contact the store by sending a message through a web form. To send a message, the customers need to have first created an account. The message is added to a queue of unanswered messages. Subsequently, an employee (whoever sees the message first) can respond to the customer via email. The response of the employee is also recorded, along with the Employee ID. Each answer is associated with one customer question and is added to a pool of answered messages. 
 
# Homework #6 Tasks:  Assume that the above are the requirements of the system, as described by the owner of the Shipping Store. You are asked to do the following:

 * Task 1[30 points] Come up with a set of Use Cases that best capture the requirements as described above. Use free text to describe flows of events for each individual use case. Create a usecase Diagram using StarUML. Export it as an image. Include in your markdown file, see [README](README.md) for template.
 * Task 2 [40 points] Create a set of CRC cards (as tables) and show Class discovery process. Create a Class Diagram using StarUML that shows the relationships between classes (dependences, inheritance, aggregations, compositions, multiplicities, etc.). In each class of the class diagram, include most important fields and methods. 
 * Task 3 [20 points] Assume a class named “Transactions” which represents shipping transactions. Draw a State Machine Diagram in StarUML that shows the possible states of objects of that class and the transitions based on different events from the moment a shipping transaction is first created by a customer through the Website, until the moment the package has been delivered to its final destination. Export it as an image. Include in your markdown file, see [README](README.md) for template.
 
 * Submission [10 points] Submit your solution to git.txstate.edu/ID.git  -> HW6 folder 
   * using git desktop tool
   * Update README.md template in HW6 folder with diagrams and CRC cards as tables.
   * Submission files with timestamps before **due date**

## Beginners guide to markdown https://help.github.com/en/github/writing-on-github/basic-writing-and-formatting-syntax

Export the StarUML diagrams as images and add them to your markdown document.  There is no single correct solution. All solutions that are reasonable, well documented and follow the standards that we saw in class, will be accepted. If you are unsure about certain decisions and need to make assumptions, please state your assumptions clearly in your solution document. This assignment can be done either individually or with a partner. If you are working with a partner, please both submit, and specify both names in the submitted document.
