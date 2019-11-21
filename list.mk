SHELL := /bin/bash

BIN := bin/
LIB := $(BIN)libs/

LF := %MergeTest.o %Merge.o
MF := %ListTest.o

DA := $(LIB)direct-access/
VP := $(LIB)void-pointer/

IMPL2 := ListTest.o MergeTest.o
IMPL := Merge.o $(IMPL2)
VERS := DAAL DADLL VPAL VPDLL

.PHONY: $(VERS)

all: $(VERS)

DAAL: %: $(DA)ArrayList.o $(LIB)Unity/unity.o $(addprefix $(LIB)%_,$(IMPL))
	@echo Generating DirectAccess ArrayList\'s tests...
	@$(CC) $(CFLAGS) $(filter-out $(LF),$^) -lm -o $(BIN)$@_ListTest.out
	@$(CC) $(CFLAGS) $(filter-out $(MF),$^) -lm -o $(BIN)$@_MergeTest.out

DADLL: %: $(DA)DoublyLinkedList.o $(LIB)Unity/unity.o $(addprefix $(LIB)%_,$(IMPL))
	@echo Generating DirectAccess DoublyLinkedList\'s tests...
	@$(CC) $(CFLAGS) $(filter-out $(LF),$^) -lm -o $(BIN)$@_ListTest.out
	@$(CC) $(CFLAGS) $(filter-out $(MF),$^) -lm -o $(BIN)$@_MergeTest.out

VPAL: %: $(VP)ArrayList.o $(LIB)Unity/unity.o $(addprefix $(LIB)%_,$(IMPL))
	@echo Generating VoidPointer ArrayList\'s tests...
	@$(CC) $(CFLAGS) $(filter-out $(LF),$^) -lm -o $(BIN)$@_ListTest.out
	@$(CC) $(CFLAGS) $(filter-out $(MF),$^) -lm -o $(BIN)$@_MergeTest.out

VPDLL: %: $(VP)DoublyLinkedList.o $(LIB)Unity/unity.o $(addprefix $(LIB)%_,$(IMPL))
	@echo Generating VoidPointer DoublyLinkedList\'s tests...
	@$(CC) $(CFLAGS) $(filter-out $(LF),$^) -lm -o $(BIN)$@_ListTest.out
	@$(CC) $(CFLAGS) $(filter-out $(MF),$^) -lm -o $(BIN)$@_MergeTest.out

test:
	@echo
	@echo -e 'DA = Direct Access\tAL = Array List'
	@echo -e 'VP = Void Pointing\tDLL = Doubly Linked List'
	@echo
	@echo -e 'Test for DAAL:\t\t$(shell ./bin/DAAL_ListTest.out | grep " Tests ")'
	@echo -e 'Test for DADLL:\t\t$(shell ./bin/DADLL_ListTest.out | grep " Tests ")'
	@echo -e 'Test for VPAL:\t\t$(shell ./bin/VPAL_ListTest.out | grep " Tests ")'
	@echo -e 'Test for VPDLL:\t\t$(shell ./bin/VPDLL_ListTest.out | grep " Tests ")'
	@echo
	@echo -e 'Test for DAAL Merge:\t$(shell ./bin/DAAL_MergeTest.out | grep " Tests ")'
	@echo -e 'Test for DADLL Merge:\t$(shell ./bin/DADLL_MergeTest.out | grep " Tests ")'
	@echo -e 'Test for VPAL Merge:\t$(shell ./bin/VPAL_MergeTest.out | grep " Tests ")'
	@echo -e 'Test for VPDLL Merge:\t$(shell ./bin/VPDLL_MergeTest.out | grep " Tests ")'
	@echo
