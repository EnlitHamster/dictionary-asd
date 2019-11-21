SHELL := /bin/bash

BIN := bin/

JVM := java
JFLAGS := -cp ".:../Resources/Java/JUnit/hamcrest-core-1.3.jar:../Resources/Java/JUnit/junit-4.12.jar"

.PHONY: test

test:
	@echo
	@echo -e "Test for UnionFindSet:\t$$(cd $(BIN);$(JVM) $(JFLAGS) tests.UnionFindSet_TestRunner)"
	@echo
