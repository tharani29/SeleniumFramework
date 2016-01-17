## Synopsis

This is a small test automation framework that combines Selenium & TestNG in a Maven build.
Features:
- More stable way of finding web elements
- TestNG goodies
- A bank for re-use of web elements
- Screenshots upon test failure
- Test logging
- Element interaction with highlighting

## Motivation

The framework was developed for experimentation purposes on my free time in order to realize mistakes and hopefully get some feedback. Currently used for a small example task but with expectations for the future.

## Installation

* Install Maven
* Set JAVA_HOME to a java 8
* Clone the repository
* Open the project with your favourite IDE (tested with Intellij & Eclipse)
* Small example test included

## How to write new tests
* Create a page Object
If not already there, create a new page object for your page or component.
http://martinfowler.com/bliki/PageObject.html

* Create functionality driven tests  
Create your tests based on a user story scenario e.g. “As a user I want to get a puppy”

* Add your tests to a test suite in testng.xml
Decide which suite is better for your tests (daily, weekly smoke tests, etc). If none matches, feel free to create new test suite

## Best practises
* Always closeDriver(); (!!!) 
In the end of every test use the @AfterTest annotation to call closeDriver(), otherwise selenium server will keep the sessions open and eventually run out of memory

* Use dependsOnMethods (http://www.mkyong.com/unittest/testng-tutorial-7-dependency-test/)
Make sure you set the dependencies of each @Test with dependsOnMethods
 
* Re-use web elements
Before adding a new web element in webElementBank.csv, make sure it is not there already

* Adding new web elements
For easy finding, name your new web elements with the format typeName e.g. buttonLogin

* Re-use page objects
It’s important to re-use page objects in order to keep the framework maintainable

* Don’t use double quotes in the webElementBank.csv
use single instead (java will not parse it correctly)

* 10-50-500 Rule
10: No package can have more than 10 classes.
50: No method can have more than 50 lines of code.
500: No class can have more than 500 lines of code.

* Use Equals over ==
== compares object references, it checks to see if the two operands point to the same object (not equivalent objects, the same object).
On the other hand, “equals” perform actual comparison of two strings.

* Preferred selector order
id > name > css > xpath

* Avoid hardcoding hosts
Use the config.properties file instead

* Avoid Thead.sleep
Wait for visible elements instead.
If you have to, use Wait or FluentWait