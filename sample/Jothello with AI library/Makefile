JCC = javac
JI = java
CLASSPATH = -cp luaj-jse-3.0-alpha3.jar;.

default: Ai.class Jothello.class Main.class State.class
	
Ai.class: Ai.java
	$(JCC) $(CLASSPATH) Ai.java

Jothello.class: Jothello.java
	$(JCC) $(CLASSPATH) Jothello.java

Main.class: Main.java
	$(JCC) $(CLASSPATH) Main.java

State.class: State.java
	$(JCC) $(CLASSPATH) State.java
		
clean: 
	$(RM) *.class
	
run: 
	$(JI) $(CLASSPATH) Main