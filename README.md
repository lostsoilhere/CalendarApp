## Assumptions

1. Appointments are always 60 minutes long.
2. The system doesn't handle timezone differences. All times are assumed to be in the same timezone.
3. There's no authentication system in place. In a real-world scenario, we would implement proper authentication and authorization.
4. We are only using in memory data structures with no persistence.
5. The input and output will be driven from standard IO.
6. The availability start and end time is same for all days.

## Set up the code locally

### Prerequisites 
1. You should have Java 17 installed in your computer, please refer [this](https://www3.cs.stonybrook.edu/~amione/CSE114_Course/materials/resources/InstallingJava17.pdf) guide to install Java 17 in your computer. 
2. You should have git installed in your computer, please refer to [this](https://git-scm.com/book/en/v2/Getting-Started-Installing-Git) guide to install git on your computer. 

### Steps
1. Clone the git repository using `git clone https://github.com/lostsoilhere/CalendarApp.git`
2. Go to the directory where the cloned package exists using `cd` command in your terminal. 
3. Run `javac -d bin -sourcepath src src/com/example/CalendarApp/driver/Main.java` to compile the project.
4. Run `java -classpath bin com.example.CalendarApp.driver.Main` to run the program.


<!---
1. Add unit test for all the public methods. 
2. Refactor the classes according to SOLID principles. 
3. Add documentation for the design
-->
