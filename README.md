Test Value Change
=================

This project sets up a simple Table using [Vaadin](vaadin.com) and  gets changes made in its cells by listening to *onBlur* events.

Running
-------

1. download the project
2. download [Maven](http://maven.apache.org/download.html) if necessary
3. `cd` into the project home (the folder with the pom.xml in it)
4. execute `mvn clean install tomcat7:run`
5. open your favorite browser.  The URL will be in the messages, but it should be [http://localhost:9090/test-value-change](http://localhost:9090/test-value-change)

*Note:* You can also run this in the Eclipse IDE by importing the project and running with the [m2e](http://www.eclipse.org/m2e/download/) plugin.

Things To Notice
----------------

- make sure you can see the command window and the browser
- tab through the cells and their values are displayed in the command window
- type in "Boris" as a name and you get "42" as a number
- type in "0" as a number and you get "Zero" as a name