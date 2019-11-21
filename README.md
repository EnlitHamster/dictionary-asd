# Building

To build a project use the bundled makefiles. Just call `makefile` to see its usage.

# Conventions

This file contains the conventions used by the developers while coding this project, including those used in:
- [Git](#git) for branching, pushing, committing, merge requesting, conflict solving and reviewing;
- the source files for naming, spacing, commenting and documenting both [Java](#java-source) and [C](#c-source);
- the [tests](#tests) offered for the given algorithms;

Some general conventions are:
- in the root folder of the project there should be **no source files**, those should be only in the `src` folder;
- *intermediate generated* files (object files of C files) and *binaries* (executables and .class files) should not be uploaded in any place in the repo;
- library errors must be handled through **errno** and, if necessary, *exit*, not through other output channels (errors must be communicated to the dev, not to the end user);
- in `src`, folders must be divided in `exercise N` and `tests`, where the first contains source codes and header file implementations, while the second contains the testing suites;

## Git

Git is the most complex of the above conventions, for it has many different areas that must be regulated to make it easier to be used and the entire process and result more coherent. The most critical parts that will be covered by this convention are **branching**, **committing**, **merge requesting** and **reviewing**. Other parts will be covered too, but more briefly being them a less important factor to the quality of the resulting code.

### Branching

Given the dimensions of the project and the importance of the correct use of Git, in this project branches will represent specific problems to which the code added through said branch give a solution. Given this basic principle, the `master` branch has the *only purpose of a release branch*, that means it will only contain the final results and should thus contain the least possible number of bugs.
The name given at a branch should be of at most a 3/4 words, in snake case and in lower characters, that represents the problem being solved in said branch. For example `list` will be the branch in which the devs worked/are working on the list data structure, whilst `fix-sorting` will be a branch in which the devs worked/are working on a fix on a sorting algorithm. Being the branches not permanent, their name must be significant for the time while the problem is immidiate, thus making it useless for a more specific and complex naming system.

### Pushing

Commit pushing may only be done on branches, never on `master` branch. Only exceptions are the **initializing commits** and changes to the **documentation files**.

### Committing

Commits should contain only a small amount of files and should be significant of a specific change. This is because of the amount of files and changes the project will subjected to, making it necessary to **specialize informations** to make it easier to identify problems and bugs and to be effective in case of rollbacks and cherrypicks. Along the same lines, the message (and description, even though not compulsory) should be explanatory of the single change being made, for example `introduced new sorting algorithm for lists` with a description that briefly illustrates the actual changes to the files like
```
- [List.java]: implemented bubble sort algorithm and public wrapper
- [Main.java]: updated the calls of List.sort for the new algorithm
```

### Merge requesting & Reviewing

These two conventions will be analysed together because of the mechanism that will be used in this project to update the `master` branch. Merge requesting of a branch may be issued only after having completed the implementation of the solution for which the branch was created in the first place. Merge requests may only be accepted by another dev part of the team that has the duty to review the code of the branch in exam. If the reviewer makes significant changes to the code of the merge request, he will become the new owner of the merge request and another dev, different from the last one before, will have to review the request. Whenever a reviewer feels the code to be acceptable, he will approve the merge request and finally close the branch, merging it in `master`.

## Java source

This section will cover the important parts of coding conventions of the Java source files. This part will be shorter than the Git part because it is meant as a general guideline and not of a heavy enforcement of rule. This being said, some rules will be **marked** making them the few ones that are **compulsory**.

| Convention | Description |
|-------------:|:-------------|
| **Language** | English |
| **Indentation** | 4 soft tabs |
| **Package naming** | Starting with it.unito.edu, lowercase |
| Class naming | Camel case, uppercase initial |
| Interface naming | Camel case, uppercase initial |
| Enum naming | Camel case, uppercase initial |
| Method naming | Camel case, lowercase initial |
| Variable naming | Camel case, lowercase initial |
| Constant naming | Snake case, uppercase |
| **Brackets** | immediately after head |
| **Documentation summary** | Brief summary of the method's meaning and detailed description of the used algorithm. If the entity has any, the Pre-Conditions have to be expressed (possibly contracted in mathematical language). If the complexity is non trivial, it has to be specified |
| **Documentation parameters** | If the described entity has paramters, all of them have to be documentated with a brief descrition of their meaning |
| **Documentation return** | If the described entity has a return value, this has to be documented with a brief description of the meaning of the value. If the entity has any, the Post-Conditions have to be expressed (possibly in mathematical language) |
| Comments | Brief and only when necessary for explanatory reasons |

## C Source

This section will cover the important parts of coding conventions of the C source files. This part will be shorter than the Git part because it is meant as a general guideline and not of a heavy enforcement of rule. This being said, some rules will be **marked** making them the few ones that are **compulsory**.

The documentation should be complete in the header file and only a very brief summary in the source file (thus maintaing the source file fairly easy to read).

| Convention | Description |
|-------------:|:-------------|
| **Language** | English |
| **Indentation** | 4 soft tabs |
| File naming | Camel case, uppercase initial |
| Function naming | Snake case, lowercase |
| Variable naming | Camel case, lowercase initial |
| Struct naming | Snake case, lowercase |
| Type naming | Camel case, uppercase initial (ending in `_t` optionally) |
| Constant naming | Snake case, uppercase |
| Macro naming | Snake case, uppercase |
| **Brackets** | on new line, same indent as head |
| **Documentation summary** | Brief summary of the method's meaning and detailed description of the used algorithm. If the entity has any, the Pre-Conditions have to be expressed (possibly contracted in mathematical language). If the complexity is non trivial, it has to be specified |
| **Documentation parameters** | If the described entity has paramters, all of them have to be documentated with a brief descrition of their meaning |
| **Documentation return** | If the described entity has a return value, this has to be documented with a brief description of the meaning of the value. If the entity has any, the Post-Conditions have to be expressed (possibly in mathematical language) |
| Comments | Brief and only when necessary for explanatory reasons |

## Tests

Every major algorithm has to be bundled with a method/function called `test` with a single parameter and boolean return value that contains assertive unit tests for limit cases and heavy duty cases. The method/function returns true if all the unit tests are positive, false otherwise.
Depending of the language, the function will:
- In C, dump the output about the result of every unit test on the `FILE*` that is the parameter of the function
- In Java, dump the output about the result of every unit test on the `OutputStream` that is the parameter of the method
For a complete guide on how to write a Unit Test, refer to [Docs > UNITTESTING.md](https://github.com/EnlitHamster/dictionary-asd/blob/master/Docs/GIT.md).
