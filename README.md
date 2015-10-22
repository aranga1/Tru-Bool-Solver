# Tru-Bool-Solver
The Tru Bool Solver is a boolean satisfiability problem solver that checks for solutions to a given boolean expression and returns wether the expression is satisfiable or not.

If an expression is satisfiable, the solver returns the assignments made to it that made it satisfiable.

The input is give in the following form:

Test 1 //A name given to this particular test

3 3 //The number of variables and clauses in this problem respectively

-1 //first clause

-2 //second clause

3 //third clause

This is one problem. The user has an option of inputing multiple such problems at the same time. Input reading is ended when a user enters CTRL-D or an EOF is reached.
