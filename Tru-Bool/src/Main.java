import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;


public class Main {

	private static int variables;
	private static int clauses;
	private static String test;
	private static HashMap<Integer,Boolean> map = new HashMap<Integer,Boolean>();
	private static ArrayList<Integer> variableList = new ArrayList<Integer>();
	private static ArrayList<ArrayList<Integer>> varTemp = new ArrayList<ArrayList<Integer>>();	
	private static ArrayList<Boolean> list = new ArrayList<Boolean>();
	
	public static void main(String[] args) {
			Scanner s = new Scanner(System.in);
			readProblem(s);
	}
	
	/**
	 * The main function calls this function.
	 * This parses the input into problems and sends each instance to be solved and printed before going on to the next problem.
	 * It therefore, also calls all the other important and necessary functions
	 * @param input
	 */
	public static void readProblem(Scanner input) {
		int ct;
		while(input.hasNextLine()) {
			test = input.nextLine();
			String[] numVals = input.nextLine().split(" ");
			variables = Integer.parseInt(numVals[0]);
			clauses = Integer.parseInt(numVals[1]);
			ct = 0;
			
			while(ct < clauses) {
				String line = input.nextLine();
				String[] terms = line.split(" ");
				ArrayList<Integer> tempList = new ArrayList<Integer>();
				for (int k = 0; k < terms.length; k++) {
					tempList.add(Integer.parseInt(terms[k]));
				}
				varTemp.add(tempList);
				ct++;
			}
			createVarList();
			print();
			variableList.clear();
			varTemp.clear();
			list.clear();
		}
	}
	
	/**
	 * This is a helper function that creates a variable list arraylist. It is used when mapping of boolean
	 * expressions to particular variable values is required.
	 */
	public static void createVarList() {
		for (int i = 0; i < varTemp.size(); i++) {
			for (int j = 0; j < varTemp.get(i).size(); j++) {
				if (!variableList.contains(varTemp.get(i).get(j))) {
					variableList.add(varTemp.get(i).get(j));
				}
			}
		}
	}
	
	/**
	 * The main boolean expression checker. Checks if the 'and' of all the clauses returns a truth value of false or true.
	 * Depending on the boolean assignment sent in, it returns a true or a false.
	 * @param list
	 * @return
	 */
	public static boolean testBool(ArrayList<Boolean> list) {
		boolean temp = boolEval(varTemp.get(0));
		for (int i = 1; i < varTemp.size(); i++) {
			temp = temp && boolEval(varTemp.get(i));
		}
		return temp;
	}
	
	/**
	 * This function maps the boolean value to the appropriate integer value in VariableList
	 * @param bools
	 */
	public static void boolMap(ArrayList<Boolean> bools) {
		int j = 0;
		int ct = 0;
		for (int i = 0; i < bools.size(); i++) {
			j = ct;
			while (j < variableList.size()) {
				map.put(Math.abs(variableList.get(j)), bools.get(i));
				j++;
			}
			ct++;
		}
	}
	/**
	 * This function evaluates each clause by checking with the truth operator 'OR'
	 * It the returns the final result of the boolean evaluation
	 * @param list
	 * @return
	 */
	public static boolean boolEval(ArrayList<Integer> list) { 
		boolean temp = map.get(Math.abs(list.get(0)));
		for (int i = 0; i < list.size(); i++) {
			if (i == 0) {
				if (list.get(i) < 0) {
					temp = !map.get(Math.abs(list.get(i)));
					//continue;
				}
			}
			if (list.get(i) < 0) {
				temp = temp || !map.get(Math.abs(list.get(i)));
			}
			else {
				temp = temp || map.get(Math.abs(list.get(i)));
			}
		}
		return temp;
	}
	
	public static boolean recFunc(int numVar) {
		if (numVar < 0) {
			return false;
		}
		boolean b1 = false;
		boolean b2 = false;
		
		if (testBool(list)) {
			return true;
		}
		if (numVar > 0) {
			list.set(numVar-1, true);
			boolMap(list);
			b1 = recFunc(numVar - 1);
			if (b1) {
				return true;
			}
		}
		if (numVar > 0) {
			list.set(numVar-1, false);
			boolMap(list);
			b2 = recFunc(numVar - 1);
			if (b2) {
				return true;
			}
		}
		if (b1 || b2) {
			return true;
		}
		else return false;
		
		/*if (list.size() == variables) {
			boolMap(list);
			if (testBool(list)) {
				return list;
			}
			else return null;
		}
		else {
			list.add(exp);
			ArrayList<Boolean> temp = list;
			ArrayList<Boolean> temp1 = list;
			if (recFunc(temp,exp) != null) {
				return recFunc(temp,exp); 
			}
			else if (recFunc(temp1,!exp) != null) {
				return recFunc(temp1,!exp);
			}
			else {
				return recFunc(temp,exp);
			}
		}*/
	}
	
	/**
	 * This is the printer function that prints the final result of exhaustive recursive check.
	 * If the problem is satisfiable (returns true), it prints out "Satisfiable" and the prints out the boolean assignments that led to the answer
	 * Else, it prints out "Unsatisfiable"
	 */
	public static void print() {
		System.out.println(test + ": " + variables + " Variable(s) " + clauses +" Clause(s)");
		for (int i = 0; i < variables; i++) {
			list.add(true);
			boolMap(list);
		}
		if (recFunc(variables)) {
			System.out.println("Satisfiable");
			for (int i = 0; i < list.size(); i++) {
				System.out.print(list.get(i) + " ");
			}
			System.out.println();
		}
		else {
			System.out.println("Unsatisfiable");
		}
	}
	
}
