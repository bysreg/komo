JCC = javac
JI = java

default: Jothello.class Main.class State.class

Jothello.class: Jothello.java
	$(JCC) Jothello.java

Main.class: Main.java
	$(JCC) Main.java

State.class: State.java
	$(JCC) State.java
		
clean: 
	$(RM) *.class
	
run: 
	$(JI) Main