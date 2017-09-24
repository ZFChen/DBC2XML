package can.tools.dbc2xml;

//: strings/SimpleRead.java
import java.io.*;

public class SimpleRead {
	/* (1) StringReader将String转化成可读的流对象
	 * (2) 构造BufferedReader对象 */
public static BufferedReader input = new BufferedReader(
  new StringReader("Sir Robin of Camelot\n22 1.61803"));
public static void main(String[] args) {
  try {
    System.out.println("What is your name?");
    String name = input.readLine();  //读一行，遇到回车符结束
    System.out.println(name);
    System.out.println(
      "How old are you? What is your favorite double?");
    System.out.println("(input: <age> <double>)");
    String numbers = input.readLine();
    System.out.println(numbers);
    
    String[] numArray = numbers.split(" ");  //使用 空格("") 对读到的字符串进行拆分
    int age = Integer.parseInt(numArray[0]); //将字符串转化成整型(有符号)
    
    double favorite = Double.parseDouble(numArray[1]); //将字符串转化成浮点型
    System.out.format("Hi %s.\n", name);
    System.out.format("In 5 years you will be %d.\n",
      age + 5);
    System.out.format("My favorite double is %f.",
      favorite / 2);
  } catch(IOException e) {
    System.err.println("I/O exception");
  }
}
} /* Output:
What is your name?
Sir Robin of Camelot
How old are you? What is your favorite double?
(input: <age> <double>)
22 1.61803
Hi Sir Robin of Camelot.
In 5 years you will be 27.
My favorite double is 0.809015.
*///:~

