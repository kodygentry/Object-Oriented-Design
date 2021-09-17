# Project 3 JUnit5

Due date on Canvas

**Goal:** The goal of the project is to help students understand the use of JUnit to test Java code.  You will create a set of unit tests, to test the behavior of the code in projects.git -> project3

**Unit testing** is used to test the behavior of the code (or parts of the code) written, without having to run the whole program. Assume that the front-end (RatingStatsApp console user interface) part of the program and the back-end part of the program (RatingSummary) are written by two different developers.  Unit testing ensures that the back-end code works correctly without having access to the front-end. 

## Tasks:

Your task is to create 20+ JUnit tests to test the methods of class RatingSummary in src/main/java/project3  **including the ones inherited from AbstractRatingSummary**. 

* Implement the JUnit tests to test only the class RatingSummary.java (and methods implemented in AbstractRatingSummary.java).
    * RatingSummaryTest.java - JUnit tests
    * RatingSummaryHamcrestTest.java - Hamcrest tests

* Be creative by coming up with test cases that can test as many different situations as possible. You **do not** test the other classes.

* Start with testing the behavior of the class/methods under normal operation scenarios.  
* Mock objects can be created either in the same test method or before any test methods are run, using the @BeforeAll or @BeforeEach annotation. 

## Code steps 

1. Copy project3 folder from projects.git, to ID folder 
  * ID stands for NetID if individual submission
  * ID stands for TeamID if team submission  
2. Open the folder with IDE of choice 
  * Maven-IDE integration [link](https://maven.apache.org/ide.html) 
  * Maven-VS Code integration [link](https://code.visualstudio.com/docs/java/java-build)
  * Build a project. Run tests. 
3. (Optional) install Java testing extension for specific IDE  
  * [Visual Studio Code JUnit integration](https://code.visualstudio.com/docs/java/java-testing)
  * Run tests
4. Add more test methods to RatingSummaryTest.java and to RatingSummaryHamcrestTest.java (under src/test/java/project3). Test changes and make sure the tests are passing. All tests need to pass and need to be non-trivial. You will get 0 credit for trivial or failed tests 

  * Option 1. Run Maven project once (package) it should coimpile and run all tests and install all extensions. Run new tests in VS Code using Run Tests button in the code as shown in the class 

  * Option 2: Compile and run JUnit5 testing, within your IDE or download junit-platform-console-standalone-1.7.0.jar from [here]( https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/1.7.0) and place it in lib folder inside **ID/project3** 

```
cd project3
javac -d bin –cp ".;bin\;lib\*"  src\main\java\project3\*.java src\test\java\project3\*.java
java -cp ".;bin\;lib\*" org.junit.platform.console.ConsoleLauncher --exclude-engine=junit-vintage --include-engine=junit-jupiter --fail-if-no-tests --scan-classpath
```
**Your code will be tested against project3/src/main/java/project3/ code enforcing no source code modifications**

5. Commit and submit the work as listed below 


## How to use git repositories for project3 submission

0. ID or TeamID is **ID** in the text below 

1. 
```
cd projects
git pull
```

2. Move to your working directory
```
cd ..
cd ID
git pull
cp ../projects/project3 
```

3. Make and save changes using IDE or text editor of choice 

* add methods to RatingSummaryTest.java and to RatingSummaryHamcrestTest.java (under src/test/java/project3)

Repeat once:
```
git add project3/pom.xml
git add project3/src/main/java/*.java
git add project3/src/test/java/*.java
git commit -m "project3 baseline added"
git push origin
```

4. Test all new test methods and make sure they compile and run, as listed above. 

5. Check in changes often to  **ID** repository 
-> make sure you are in **ID** folder for git commandline:

Repeat multiple times: 
```
git add project3/src/test/java/*.java
git commit -m "project3 tests ABCD added"
gitk
```
gitk will show you the status, close it to continue
```
git push origin:<ID>
```

6. Repeat step 4 and 5 often to save your work until done. 
  * it allows you to re-trace your steps
  * do not forget to list **ALL** team members under @author javadoc tag

7. Done! 
