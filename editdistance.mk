SHELL := /bin/bash

BIN := bin/

JVM := java
JFLAGS := -cp ".:../Resources/Java/JUnit/hamcrest-core-1.3.jar:../Resources/Java/JUnit/junit-4.12.jar"

DIPS := Resources/dictionary.txt Resources/correctme.txt

.PHONY: test

test:
	@echo
	@echo -e "Test for Recursive EditDistance:\t$$(cd $(BIN);$(JVM) $(JFLAGS) tests.EditDistance_TestRunner rec)"
	@echo -e "Test for Dynamic EditDistance:\t\t$$(cd $(BIN);$(JVM) $(JFLAGS) tests.EditDistance_TestRunner dyn)"
	@test=$$(cd $(BIN);$(JVM) tests.Checker_Test $(addprefix ../,$(DIPS)));echo -e "Test for Checker (EditDistance implementation):\n$$test"
	@echo
