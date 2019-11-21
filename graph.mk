SHELL := /bin/bash

BIN := bin/

JVM := java
JFLAGS := -cp ".:../Resources/Java/JUnit/hamcrest-core-1.3.jar:../Resources/Java/JUnit/junit-4.12.jar"

DIPS := Resources/italian_dist_graph.csv

.PHONY: test

test:
	@echo
	@echo -e "Test for Graphs:\t$$(cd $(BIN);$(JVM) $(JFLAGS) tests.Graph_TestRunner)"
	@echo -e "Test for Kruskal:\t$$(cd $(BIN);$(JVM) $(JFLAGS) tests.Kruskal_TestRunner)"
	@test=$$(cd $(BIN);$(JVM) tests.ItalianDistGraphMST $(addprefix ../,$(DIPS)));echo -e "Test for Italian Distance Graph (Kruskal implementation):\n$$test"
	@echo
