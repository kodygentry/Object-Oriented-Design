## [PROJECT1](PROJECT1.md) - project 1 description, tasks, and formulation 

## [METHODS](METHODS.md) - explained methods to be implemented

## [RUBRIC](RUBRIC.md) - project 1 grading rubric

## [README](README.md) - setup and submission

* Navigate to your class project central
```
cd CS3354
```
* Update the class codebase

```
cd 2021Spring
git pull
cs ..
```
* get the data folder
```
cd CS3354
git clone https://git.txstate.edu/CS3354/data.git
```
* get your project central folder (when created). NetID or TeamID is **ID** or <ID> in the text below. Starting point for project1 is in your team **ID** repository. 
```
git clone https://git.txstate.edu/CS3354/<ID>.git
```

You should see at least 3 folders in your CS3354 local folder: 2021Spring, data, and ID. 

* Starting point is your ID/project1 folder 
  * maven repository so open it with IDE
  * starting src code is in ID/project1/src/main/java/project1 folder

## How to use git repositories for project1 submission

1. project1 folder is in your **ID** repository, open the folder w your iDE
   * contact TA if it is not 
   * project1/src/main/java/project1 - instructor provided starting point
2. you have already setup **data** repository above, and you can use it for your project
   * cannot push it to 2021Spring
   * do not push data folder to your project repo 
3. make changes to java code in **ID/project1/src/main/java/project1** folder and save changes using IDE or text editor of choice 
  * Visual Studio Code
  * IntelliJ
  * Eclipse
  * Netbeans
  * Notepad++
  * Atom 
4. check in changes to  **ID** repository - make sure you are in **ID** folder for git commandline:
```
cd CS3354/<ID>
git add project1/src/main/java/project1/*.java
git commit -m "Project 1 code update comment here"
gitk
```
gitk will show you the status, close it to continue
```
git push origin master
```
this will push the changes to the server
5. Repeat step 4 often to save your work until done. 
  * it allows you to re-trace your steps
  * individual team members are graded based on number of check-ins
  * do not forget to list **ALL** team members under @author javadoc tag
6. Done! 

## [PROJECT1](PROJECT1.md) - project 1 description, tasks, and formulation 

## [RUBRIC](RUBRIC.md) - project 1 grading rubric

## [README](README.md) - setup and submission

## Other Class resources 
* Canvas [class](https://canvas.txstate.edu/courses/1659054) central
* Reading [material](https://git.txstate.edu/CS3354/2021Spring/tree/master/reading)
