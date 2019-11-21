# Forcing bash shell
SHELL := /bin/bash

.PHONY: default all mkdirs clear clear-all c C test-ex1 test-ex2 test-ex3 test-ex4 test test-list test-editdistance test-union test-graph download list ex1 ex2 ex3 setup-ex1 setup-ex2 setup-ex3

default:
	@echo
	@echo -e "Usage of this project's makefile:"
	@echo
	@echo -e "To build the project use \`make build\`"
	@echo -e "To build a single exercise use \`make exN\`"
	@echo -e "To test the project use \`make test\`"
	@echo -e "To test a single exercise use \`make text-exN\`"
	@echo
	@echo -e "To clear from subproduct files use \`make clear\` or \`make c\`"
	@echo -e "To clear from all product files use \`make clear-all\` or \`make C\`"
	@echo
	@echo -e "ATTENTION: USE AT YOUR OWN RISK THE FOLLOWING COMMANDS"
	@echo -e "In case of compilation or test errors, try \`make reset\` to cancel all possible files causing conflicts"
	@echo -e "In case anything else fails, try \`make brute\` for a hard try compile"
	@echo

# Sources and Binaries directories
SRC := src/
BIN := bin/
LIB := $(BIN)libs/
CUN := Resources/C/Unity/
JUN := Resources/Java/JUnit/*

# Getting all Source files in src folder, for both C and Java
OBJC := $(patsubst ./%,%,$(patsubst %.c,%.o,$(shell find src -type f -name '*.c'))) $(CUN)unity.o
OBJJ := $(patsubst ./%,%,$(patsubst %.java,%.class,$(shell find src -type f -name '*.java')))
OBJV := $(filter %/Merge.o,$(OBJC)) $(filter %/ListTest.o,$(OBJC)) $(filter %/MergeTest.o,$(OBJC))

JC := javac
JFLAGS := -d $(BIN) -sourcepath $(SRC) -cp ".:$(JUN)"

build: mkdirs $(OBJC) $(OBJJ) list usage
ex1: mkdirs setup-ex1 list usage
ex2 ex3 ex4: %: mkdirs setup-% usage

######################
# Source compilation #
######################

# Compiling implementations for Direct Access and Void Pointing List versions
$(OBJV): %.o: %.c
	@echo Compiling all $^ implementations...
	@mkdir -p $(LIB)$(dir $(patsubst $(shell echo $@ | cut -d/ -f 1-2)/%,%,$@))
	@$(CC) $(CFLAGS) -D DIRECT_ACCESS -D ARRAY_LIST -c $^ -o $(LIB)DAAL_$(notdir $@)
	@$(CC) $(CFLAGS) -D DIRECT_ACCESS -D DOUBLY_LINKED_LIST -c $^ -o $(LIB)DADLL_$(notdir $@)
	@$(CC) $(CFLAGS) -D VOID_POINTER -D ARRAY_LIST -c $^ -o $(LIB)VPAL_$(notdir $@)
	@$(CC) $(CFLAGS) -D VOID_POINTER -D DOUBLY_LINKED_LIST -c $^ -o $(LIB)VPDLL_$(notdir $@)

# Default C compiling target
%.o: %.c
	@echo Compiling $^...
	@mkdir -p $(LIB)$(dir $(patsubst $(shell echo $@ | cut -d/ -f 1-2)/%,%,$@))
	@$(CC) $(CFLAGS) -c $^ -o $(LIB)$(patsubst $(shell echo $@ | cut -d/ -f 1-2)/%,%,$@)

# Default Java compiling target
%.class: %.java
	@echo Compiling $^...
	@mkdir -p $(BIN)$(dir $(patsubst $(SRC)%,%,$^))
	@$(JC) $(JFLAGS) $^

########################
# Binaries' generation #
########################

# List implementations builder
list:
	@$(MAKE) -f $@.mk

###################
# Utility targets #
###################

# Compiling only selected files for each exercise
setup-ex1: $(filter $(SRC)exercise1/%,$(OBJC)) $(filter $(SRC)tests/List%,$(OBJC)) $(filter $(SRC)tests/Merge%,$(OBJC)) $(CUN)unity.o
setup-ex2: $(filter $(SRC)exercise1/%,$(OBJJ)) $(filter $(SRC)exercise2/%,$(OBJJ)) $(filter $(SRC)tests/Checker%,$(OBJJ)) $(filter $(SRC)tests/EditDistance%,$(OBJJ))
setup-ex3: $(filter $(SRC)exercise3/%,$(OBJJ)) $(filter $(SRC)tests/UnionFindSet%,$(OBJJ))
setup-ex4: $(filter $(SRC)exercise3/%,$(OBJJ)) $(filter $(SRC)exercise4/%,$(OBJJ)) $(filter $(SRC)tests/Directed%,$(OBJJ)) $(filter $(SRC)tests/Undirected%,$(OBJJ)) $(filter $(SRC)tests/Graph%,$(OBJJ)) $(filter $(SRC)tests/Kruskal%,$(OBJJ)) $(filter $(SRC)tests/R%,$(OBJJ)) $(filter $(SRC)tests/Italian%,$(OBJJ))

# Testing selected exercise
test-ex1: test-list
test-ex2: test-editdistance
test-ex3: test-union
test-ex4: test-graph

# Creating build directories
mkdirs: download
	@echo Creating binaries\' directories...
	@mkdir -p $(BIN)
	@mkdir -p $(LIB)

# Deleting subproduct files
clear c:
	@echo Clearing object files...
	@rm -rf $(LIB)

# Deleting all product files
clear-all C: clear
	@echo Clearing all binaries...
	@rm -rf $(BIN)*

# Hard reset of entire project folder.
# >>> !!! USE WITH CAUTION !!! <<<
reset: clear-all
	@echo Resetting entire workspace...
	@find . -name "*.o" -type f -delete
	@find . -name "*.out" -type f -delete
	@find . -name "*.class" -type f -delete
	@find . -name "*.txt" -type f -delete
	@find . -name "*.csv" -type f -delete

test: test-list test-editdistance test-union test-graph

test-list test-editdistance test-union test-graph: %:
	@$(MAKE) -f $(patsubst test-%,%,$@).mk test

usage:
	@echo
	@echo Everything generated correctly.
	@echo
	@echo "In the bin/ folder you will find the test suites for the implementations of this project."
	@echo "Or you could use \`make test\` to briefly see if the tests pass without much informations."
	@echo

# Downloading necessary files. If already present, move them manually to Resources/ folder to avoid downloading the files
download:
ifeq (,$(wildcard Resources/dictionary.txt))
	curl https://datacloud.di.unito.it/index.php/s/KmbnHZ4QrkKlr6d/download -o Resources/dictionary.txt
endif
ifeq (,$(wildcard Resources/correctme.txt))
	curl https://datacloud.di.unito.it/index.php/s/noOgmdQ0SBrPNGX/download -o Resources/correctme.txt
endif
ifeq (,$(wildcard Resources/italian_dist_graph.csv))
	curl https://datacloud.di.unito.it/index.php/s/F86PWWUBgqrCeGn/download -o Resources/italian_dist_graph.csv
endif

# Bruteforce method, only in case of everything else failing
brute:
# Creating Dirs
	mkdir -p bin/
	mkdir -p bin/libs/
	mkdir -p bin/libs/direct-access
	mkdir -p bin/libs/void-pointer
	mkdir -p bin/libs/Unity
	mkdir -p bin/exercise2/
	mkdir -p bin/exercise3/
	mkdir -p bin/exercise4/
	mkdir -p bin/tests/
# Exercise 1
	cc -c src/exercise1/direct-access/ArrayList.c -o bin/libs/direct-access/ArrayList.o
	cc -c src/exercise1/direct-access/DoublyLinkedList.c -o bin/libs/direct-access/DoublyLinkedList.o
	cc -c src/exercise1/void-pointer/ArrayList.c -o bin/libs/void-pointer/ArrayList.o
	cc -c src/exercise1/void-pointer/DoublyLinkedList.c -o bin/libs/void-pointer/DoublyLinkedList.o
	cc -c Resources/C/Unity/unity.c -o bin/libs/Unity/unity.o
	cc -c src/exercise1/Merge.c -D DIRECT_ACCESS -D ARRAY_LIST -o bin/libs/DAAL_Merge.o
	cc -c src/exercise1/Merge.c -D DIRECT_ACCESS -D DOUBLY_LINKED_LIST -o bin/libs/DADLL_Merge.o
	cc -c src/exercise1/Merge.c -D VOID_POINTER -D ARRAY_LIST -o bin/libs/VPAL_Merge.o
	cc -c src/exercise1/Merge.c -D VOID_POINTER -D DOUBLY_LINKED_LIST -o bin/libs/VPDLL_Merge.o
	cc -c src/tests/ListTest.c -D DIRECT_ACCESS -D ARRAY_LIST -o bin/libs/DAAL_ListTest.o
	cc -c src/tests/ListTest.c -D DIRECT_ACCESS -D DOUBLY_LINKED_LIST -o bin/libs/DADLL_ListTest.o
	cc -c src/tests/ListTest.c -D VOID_POINTER -D ARRAY_LIST -o bin/libs/VPAL_ListTest.o
	cc -c src/tests/ListTest.c -D VOID_POINTER -D DOUBLY_LINKED_LIST -o bin/libs/VPDLL_ListTest.o
	cc -c src/tests/MergeTest.c -D DIRECT_ACCESS -D ARRAY_LIST -o bin/libs/DAAL_MergeTest.o
	cc -c src/tests/MergeTest.c -D DIRECT_ACCESS -D DOUBLY_LINKED_LIST -o bin/libs/DADLL_MergeTest.o
	cc -c src/tests/MergeTest.c -D VOID_POINTER -D ARRAY_LIST -o bin/libs/VPAL_MergeTest.o
	cc -c src/tests/MergeTest.c -D VOID_POINTER -D DOUBLY_LINKED_LIST -o bin/libs/VPDLL_MergeTest.o
	cc -o bin/DAAL_ListTest.out bin/libs/direct-access/ArrayList.o bin/libs/Unity/unity.o bin/libs/DAAL_ListTest.o -lm
	cc -o bin/DADLL_ListTest.out bin/libs/direct-access/DoublyLinkedList.o bin/libs/Unity/unity.o bin/libs/DADLL_ListTest.o -lm
	cc -o bin/VPAL_ListTest.out bin/libs/void-pointer/ArrayList.o bin/libs/Unity/unity.o bin/libs/VPAL_ListTest.o -lm
	cc -o bin/VPDLL_ListTest.out bin/libs/void-pointer/DoublyLinkedList.o bin/libs/Unity/unity.o bin/libs/VPDLL_ListTest.o -lm
	cc -o bin/DAAL_MergeTest.out bin/libs/direct-access/ArrayList.o bin/libs/Unity/unity.o bin/libs/DAAL_Merge.o bin/libs/DAAL_MergeTest.o -lm
	cc -o bin/DADLL_MergeTest.out bin/libs/direct-access/DoublyLinkedList.o bin/libs/Unity/unity.o bin/libs/DADLL_Merge.o bin/libs/DADLL_MergeTest.o -lm
	cc -o bin/VPAL_MergeTest.out bin/libs/void-pointer/ArrayList.o bin/libs/Unity/unity.o bin/libs/VPAL_Merge.o bin/libs/VPAL_MergeTest.o -lm
	cc -o bin/VPDLL_MergeTest.out bin/libs/void-pointer/DoublyLinkedList.o bin/libs/Unity/unity.o bin/libs/VPDLL_Merge.o bin/libs/VPDLL_MergeTest.o -lm
# Exercise 2
	javac -d bin/ -sourcepath src/ -cp "Resources/Java/JUnit/*" src/exercise2/EditDistance.java
	javac -d bin/ -sourcepath src/ -cp "Resources/Java/JUnit/*" src/exercise2/LengthComparator.java
	javac -d bin/ -sourcepath src/ -cp "Resources/Java/JUnit/*" src/exercise2/Checker.java
	javac -d bin/ -sourcepath src/ -cp "Resources/Java/JUnit/*" src/tests/EditDistance_Test.java
	javac -d bin/ -sourcepath src/ -cp "Resources/Java/JUnit/*" src/tests/EditDistance_TestRunner.java
	javac -d bin/ -sourcepath src/ -cp "Resources/Java/JUnit/*" src/tests/Checker_Test.java
# Exercise 3
	javac -d bin/ -sourcepath src/ -cp "Resources/Java/JUnit/*" src/exercise3/Pair.java
	javac -d bin/ -sourcepath src/ -cp "Resources/Java/JUnit/*" src/exercise3/UnionFindSet.java
	javac -d bin/ -sourcepath src/ -cp "Resources/Java/JUnit/*" src/tests/UnionFindSet_Test.java
	javac -d bin/ -sourcepath src/ -cp "Resources/Java/JUnit/*" src/tests/UnionFindSet_TestRunner.java
# Exercise 4
	javac -d bin/ -sourcepath src/ -cp "Resources/Java/JUnit/*" src/exercise4/Edge.java
	javac -d bin/ -sourcepath src/ -cp "Resources/Java/JUnit/*" src/exercise4/DirectedEdge.java
	javac -d bin/ -sourcepath src/ -cp "Resources/Java/JUnit/*" src/exercise4/UndirectedEdge.java
	javac -d bin/ -sourcepath src/ -cp "Resources/Java/JUnit/*" src/exercise4/Graph.java
	javac -d bin/ -sourcepath src/ -cp "Resources/Java/JUnit/*" src/exercise4/DirectedGraph.java
	javac -d bin/ -sourcepath src/ -cp "Resources/Java/JUnit/*" src/exercise4/UndirectedGraph.java
	javac -d bin/ -sourcepath src/ -cp "Resources/Java/JUnit/*" src/exercise4/Kruskal.java
	javac -d bin/ -sourcepath src/ -cp "Resources/Java/JUnit/*" src/tests/DirectedGraph_Test.java
	javac -d bin/ -sourcepath src/ -cp "Resources/Java/JUnit/*" src/tests/UndirectedGraph_Test.java
	javac -d bin/ -sourcepath src/ -cp "Resources/Java/JUnit/*" src/tests/Graph_TestRunner.java
	javac -d bin/ -sourcepath src/ -cp "Resources/Java/JUnit/*" src/tests/Kruskal_Test.java
	javac -d bin/ -sourcepath src/ -cp "Resources/Java/JUnit/*" src/tests/Kruskal_TestRunner.java
	javac -d bin/ -sourcepath src/ -cp "Resources/Java/JUnit/*" src/tests/Reader.java
	javac -d bin/ -sourcepath src/ -cp "Resources/Java/JUnit/*" src/tests/Record.java
	javac -d bin/ -sourcepath src/ -cp "Resources/Java/JUnit/*" src/tests/ItalianDistGraphMST.java
