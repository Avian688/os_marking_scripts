# README #

This is a script to help with the marking of the coursework for 'Operating Systems' module at the University of Sussex

### What is this repository for? ###

* Quick summary: A repo for the code written for 'Operating Systems' module at Sussex university, spring 2021
* Version: 1.0

### How do I get set up? ###

* You need to have `find`, `rename`, `timeout`, `awk` and `xlsx2csv` commands installed on your machine, and you should be able to access them via bash.
* Please first put the related information inside the `marks.xlsx` file.
* You should NOT have any *new line* (i.e. `\n`) in your `marks.csv` file, so that the command would be able to translate it correctly into text files with studetns candicate numbers. If you do please replace them all to spaces before you run the `create_comment` script. Also, the delimiter of the `marks.csv` file is `$`. 
* Simply copy the `submissions.zip` file into the main directory of the repo and run `make`

### Who do I talk to? ###

* All the bash script implementations using to call the marking codes are done by Amir Naseredini for the University of Sussex 
* Module Convenor: George Parisis
