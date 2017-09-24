package can.tools.dbc2xml;

//: strings/BetterRead.java
import java.util.*;

public class BetterRead {
	public static void main(String[] args) {
	  Scanner stdin = new Scanner(SimpleRead.input); //input = "Sir Robin of Camelot\n22 1.61803"
	  System.out.println("What is your name?");
	  String name = stdin.nextLine(); //Sir Robin of Camelot
	  System.out.println(name);
	  System.out.println(
	    "How old are you? What is your favorite double?");
	  System.out.println("(input: <age> <double>)");
	  int age = stdin.nextInt(); //age = 22
	  double favorite = stdin.nextDouble(); //favorite = 1.61803
	  System.out.println(age);
	  System.out.println(favorite);
	  System.out.format("Hi %s.\n", name);
	  System.out.format("In 5 years you will be %d.\n",
	    age + 5);
	  System.out.format("My favorite double is %f.", favorite / 2);
	  stdin.close();
	}
} /* Output:
What is your name?
Sir Robin of Camelot
How old are you? What is your favorite double?
(input: <age> <double>)
22
1.61803
Hi Sir Robin of Camelot.
In 5 years you will be 27.
My favorite double is 0.809015.
*///:~

