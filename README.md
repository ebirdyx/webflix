Webflix: online movie rental application
========================================

# Introduction

The company Webflix wants to compete with the company Netflix in the online movie
rental market. To develop its rental system, this company calls on your small but very
promising consulting firm. Following a meeting between your firm and the managers of
the companyWebflix, it was agreed that two (2) applications will be developed:
1. An on-line application allowing customers to subscribe, browse the system's list of
   films using various search criteria, consult the film profile, and rent certain films;
2. A second application allowing company employees to update the list of available films, return copies of films to inventory, and administer client files.

The functionalities of these two applications are specified using eight (8) use cases.

# Online movie rental application

### Case 1: Online subscription

From the website ofWebflix, a customer can subscribe to the movie rental service. Three
packages are available:

| Flat rate | Cost | Max rentals | Max duration | Code |
|-----------|------|-------------|--------------|------|
| Beginner     | $ 5 / month  | 1 movie  | 10 days   | D    |
| Intermediate | $ 10 / month | 5 films  | 30 days   | I    |
| Advanced     | $ 15 / month | 10 films | unlimited | AT   |

For these three packages, the following rules apply. 
- If a customer has fewer films in his possession than the number of rentals allowed by his package, he can rent new ones as long as
the sum of the number of films rented and those in his possession does not exceed the allowed
limit (max rentals). 
- Otherwise, he must return at least one film by post before making any
  new rentals. 
- In addition, each rental must be returned before the maximum number of days
  allowed by the package (max duration).

To subscribe, the customer must choose their package and enter each the following information:
1. Last name and first name;
2. E-mail ;
3. Phone number;
4. Address: civic number, street, city, province and postal code;
5. Credit card: type (VISA / MasterCard / Amex), number, expiration date (month and year) and
CVV;
6. Date of Birth ;
7. Password.

The system then validates the information:
- None of these values can be empty;
- The customer cannot enter an email address already in use;
- The credit card must not be expired;
- The client must be at least 18 years old;
- The password must contain at least 5 alpha-numeric characters.

For each error case, a message significant must be displayed to the customer

### Case 2: Connection to the system

A registered customer can log into the system by identifying himself using his Email Address
and his password. If the email address is unknown or the password is incorrect, the system
displays an error message asking the customer to enter new values.

### Case 3: Viewing films

A client connected to the system can view the system's movie list. To search for a particular
movie, the customer can enter any combination of the following criteria:

- A string of characters contained in the title of the movie;
- An interval [yearMin, yearMax] containing the year the film was released;
- The name of one of the producing countries;
- The original language of the film;
- The name of one of the genres of the film (ex: Comedy, Action, Drama, etc.);
- A string of characters contained in the director's name;
- A character string contained in the name of one of the actors.

**Note:** The same criterion can be used more than once. 
For example, the customer can search for movies starring two different actors.

The system gives the customer the list of satisfactory films all these criteria. Each item in the
list has the following format:(movie title)((release year)). 
For example :Titanic (1997). If the system does not find any movie matching the search criteria, it displays an error message
to the client.

####**A customer** can consult the file of one of the films on the list, presenting the following information:
- The title of the film;
- The year the film was released;
- The name of all the countries of production;
- The original language of the film;
- The duration of the film (in minutes);
- The list of all genres of the film;
- The name of the director;
- The name of all screenwriters;
- The name of all the actors and their character (s) in the film;
- The summary of the film's script;
- The movie poster ;
- The link to all movie trailers;

#### In addition, by selecting the name of the director or an actor, the client can obtain the following
information about that person:
- His name (first and last name);
- His date of birth ;
- His birth place ;
- The person's photo;
- A short biography on this person;

### Case 4: Rental of films
A customer connected to the system can rent a film from the description sheet if the following
constraints are met:
1. A copy of this film remains in inventory;
2. The customer's package allows rental (see Case 1).

If one of these constraints is not satisfied, an error message significant is presented to the client.
   Otherwise, a copy of the film is removed from inventory and mailed to the customer's address. The
   inventory is updated to take this rental into account.

### Case 5: Connection of an employee
An employee can log into the system through the same portal as the customers. However, an
employee identifies himself with his registration number 7 digits and password. If this personnel
number is unknown or if the password is invalid, the system displays a message asking the
employee to enter new values.

**Note**: the system has a file for each employee, containing the same information as for a
customer, except for the credit card information which does notice not registered
for an employee.

### Case 6: Return of films
Once logged into the system, an employee can return films received by mail from
customers. To make a return, an employee enters the code number of the returned copy.
If this code is valid (ie if the copy exists in the system), the inventory is updated. Otherwise,
an error message is given.

### Case 7: Adding / removing films
To add a new movie to the system, a connected employee enters:
- The description of the film (see Case 3);
- The number of prints initially available for this movie.

If the code entered does not already exist in the system and the number of copies is at least 1, the system
is updated. Otherwise, the error is reported to the employee. To remove a film from the system, the
employee searches for the film (s) to be removed using the procedure in Case 3. A film can only be
removed from the system ifall copies of this film have been returned.


### Case 8: Administration of files
A logged in employee can search for a customer's record using the following criteria:
- A string of characters contained in the customer's first or last name;
- The customer's email address;
- The customer's phone number;

If no customer matches the search criterion (s), an error message is displayed. Otherwise, a list
of which each element is of the form(Last name First Name)((phone number)) is presented to
the employee. After selecting a customer from the list, the employee can:
1. Delete a client's file, at provided that all customer rentals have been returned;
2. Modify the client's file;
3. Consult the list of films rented by a client. Each film on the list is identified by its title
   and year of release. In addition, overdue movies also displayed the number of days
   overdue.

