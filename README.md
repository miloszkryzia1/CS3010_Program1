# Overview
This project is a Java console program that solves a linear system with any number of equations using the
**Gaussian Elimination** method with partial pivoting.
---
While running the program, the following steps are completed:
**I) Take user input**
The user is asked for the number of equations to be solved. For the input,
the user is given 2 options: (1) from console and (2) from a file.
1) **Input from console** – the user is asked to enter all coefficients and b-values, row by row.
2) **Input from a file** – the user is asked to enter a file name or path. The file should contain
all coefficients and corresponding b-values in separate rows. An example of that would be 

For example, the input:
```
2 -1 0 1
-1 3 -1 8
0 -1 2 -5
```
Represents the linear system:
2x~1~ – x~2~ = 1
-x~1~ + 3x~2~ – x~3~ = 8
-x~2~ + 2x~3~ = -5
