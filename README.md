# Overview
This project is a Java console program that solves a linear system with any number of equations using the
**Gaussian Elimination** method with partial pivoting.
---
While running the program, the following steps are completed: <br> <br>
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
Represents the linear system: <br>
2x<sub>1</sub> – x<sub>2</sub> = 1 <br>
-x<sub>1</sub> + 3x<sub>2</sub> – x<sub>3</sub> = 8 <br>
-x<sub>2</sub> + 2x<sub>3</sub> = -5 <br>
