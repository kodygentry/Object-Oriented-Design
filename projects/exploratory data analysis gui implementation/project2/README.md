# Project 2

 * [PROJECT2](PROJECT2.md) - project 2 description, tasks, and formulation 
 * [README](README.md) - setup and submission

* Navigate to your class project central
```
cd CS3354
```

* Get the <ID>, projects, and data folder. <ID> is your team or individual project directory. 

  * Option 1: get them for the first time
  ```
   cd CS3354
   git clone https://git.txstate.edu/CS3354/<ID>.git
   git clone https://git.txstate.edu/CS3354/projects.git
   git clone https://git.txstate.edu/CS3354/data.git
   ```
  * Option 2: update the codebase 
  
   ```
   cd <ID>
   git pull
   cd ../projects
   git pull
   cd ../data
   git pull
   cd ..    
   ```
* Copy project2 from projects folder to <ID> folder 
   ```
   cp -r projects/project2/ <ID>

   ```
  * Starting point is your ID/project2 folder - maven repository so open it with IDE
  * starting src code is in ID/project1/src/main/java/project2 folder
  
* Make changes  to code  as instructed in [PROJECT2](PROJECT2.md) and push to repository 
   ```
   cd CS3354/<ID>
   git add project2/src/main/java/project2/*.java
   git commit -m "Project 2 code update comment here"
   gitk
   ```
   * gitk will show you the status, **close** it to continue
   ```
   git push origin master
   ```

## Other Class resources 
* Canvas [class](https://canvas.txstate.edu/courses/1659054) central
* Reading [material](https://git.txstate.edu/CS3354/2021Spring/tree/master/reading)
