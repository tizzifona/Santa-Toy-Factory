# Santa Toy Factory 🎅🏼

## 🎄 Project Description

The Santa Claus Toy Factory is a console-based application aimed at modernizing the toy management system used by Santa and his elves. This system allows elves to register toys, manage inventory, and delete toys from the catalog. Santa can review the lists of toys intended for good and bad children, and save the full list of toys as a CSV file for external verification. The application uses the MVC (Model-View-Controller) pattern and applies principles such as the Repository Pattern and Dependency Inversion to structure the code in a modular and scalable way.

The goal of the project is to simulate a basic toy management system, while introducing architectural patterns to make the codebase cleaner and easier to maintain.

## 🎄 Tech Stack

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white) ![Apache Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white) ![SQLite](https://img.shields.io/badge/sqlite-%2307405e.svg?style=for-the-badge&logo=sqlite&logoColor=white) ![Junit](https://img.shields.io/badge/Junit5-25A162?style=for-the-badge&logo=junit5&logoColor=white) ![Visual Studio Code](https://img.shields.io/badge/Visual%20Studio%20Code-0078d7.svg?style=for-the-badge&logo=visual-studio-code&logoColor=white)

## 🎄 Pre-requisites

Before running this project, ensure that you have the following software installed:

Java Development Kit (JDK) - Version 8 or higher.
Maven (optional, if you want to build the project with Maven).
IDE (like IntelliJ IDEA, Eclipse, or Visual Studio Code) to edit and run the project.
Database (Optional) - A MySQL database connection is required for persistent storage, though this project can run in a basic mode without database integration for testing purposes.

## 🎄 Steps for Installation

1. Clone the repository: Open a terminal and run the following command to clone the repository:

```git clone https://github.com/yourusername/santa-claus-toy-factory.git```

2. Build the project: Navigate to the project directory and run the following Maven command (if you are using Maven):

```mvn clean install```

3. Database setup (Optional): If you want to use the database for persistent storage:
   
Set up a MySQL database.
Edit the ```DatabaseConnection.java``` file to include your database credentials.
The database will need a ```toys``` table for storing the toy records. You can run the ```CREATE TABLE IF NOT EXISTS``` query provided in the project.

4. Run the project: Once the dependencies are installed and the database (optional) is set up, you can run the application from the terminal:

```mvn exec:java```

5. Test the application: Once the application is running, follow the prompts in the console. You can log in as either an elf or Santa Claus and perform the toy management operations.


## 🎄 Test Execution

[![temp-Imagen-R4-RWb.avif](https://i.postimg.cc/1zhSFGqQ/temp-Imagen-R4-RWb.avif)](https://postimg.cc/K1NCSgfq)


## 🎄 Diagrams

[![temp-Image93zht-O.avif](https://i.postimg.cc/rsz33srj/temp-Image93zht-O.avif)](https://postimg.cc/Fk5TJ9Gk)

[![temp-Image3e1dta.avif](https://i.postimg.cc/nrsSTWyw/temp-Image3e1dta.avif)](https://postimg.cc/H8gtk6sQ)

[![temp-Image4-Z9-XLR.avif](https://i.postimg.cc/8k30s1W8/temp-Image4-Z9-XLR.avif)](https://postimg.cc/Sj6rvbDV)

## 🎄 Authors

Nadiia Alaieva - Project Developer 

<a href='https://www.linkedin.com/in/nadiia-alaieva/'><img src="https://i.postimg.cc/3RLmssnH/linkedin-3.png" alt="linkedin icon" width="30" height="30"></a>
