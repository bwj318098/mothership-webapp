# mothership-webapp
The mothership-webapp which houses the core web application and will contain the restful modules.

default profile is dev:

	mvn -P dev clean install tomcat7:run

production profile is prod:

	mvn -P prod clean install tomcat7:run

test profile is test

mvn -P test clean install tomcat7:run
