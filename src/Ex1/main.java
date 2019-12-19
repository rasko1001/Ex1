package Ex1;

import java.io.IOException;

//import com.sun.tools.javac.code.Type.ForAll;

public class main 
{
	public static void main(String[] args) throws IOException
	{
		
			
		GUI_Functions gui = new GUI_Functions();
		
		/*
		function f11=new Polynom("x^2+5");
		gui.add(f11);
		function f12=new Polynom("x^3+3x^2-5");
		gui.add(f12);
		function f13=new Polynom("5x^2");
		gui.add(f13);
		function f14=new Polynom("7x^2+5");
		gui.add(f14);
		*/
		//gui.drawFunctions(500, 500, new Range(-20,20), new Range(-20,20), 1000);
		gui.initFromFile("c:\\noam\\functions.txt");
		gui.saveToFile("c:\\noam\\functions1.txt");
		
		gui.drawFunctions("c:\\noam\\json.txt");

		
		
		function f1=new Polynom("5x+2x^3-4x");	//5x+2x^3-4x
		function f2=new Polynom("4x^3-2+3x^2-5x");//4x^3-2+3x^2-5x
		Operation cfoper1=Operation.Times;	
		function f3=new Polynom("9x^3-4x^2-5x");//9x^3-4x^2-5x	
		function f4=new Polynom("12x+2x^3+9");//12x+2x^3+9
		String cfoper2="div";
		function f5=new Polynom("5x+2x^3-6x");//5x+2x^3-6x


		ComplexFunction cf1=new ComplexFunction(cfoper1,f1,f2);
		ComplexFunction cf2=new ComplexFunction(cfoper2,f3,f4);
		ComplexFunction cf3=new ComplexFunction(f5);

		System.out.println(cf1.toString());
		System.out.println(cf2.toString());
		System.out.println(cf3.toString());
		System.out.println();
		String s1 = "mul(2X^3+X,4X^3+3X^2-5X-2)";
		String s2 = "div(9X^3+4X^2+5X,2X^3+12X+9)";
		String s3 = "2X^3-X";
		String s4 = cf1.toString();
		String s5 = cf2.toString();
		String s6 = cf3.toString();
		int l1 = s1.length();
		int l2 = s2.length();
		int l3 = s3.length();
		int l4 = s4.length();
		int l5 = s5.length();
		int l6 = s6.length();
		System.out.println("l1 " + l1 + " l4 " + l4 );
		System.out.println("s1 " + s1 );
		System.out.println("s4 " + s4 );
		System.out.println();
		System.out.println("l2 " + l2 + " l5 " + l5 );
		System.out.println("s2 " + s2 );
		System.out.println("s5 " + s5 );
		System.out.println("f3 " + f3.toString());		
		System.out.println();
		System.out.println("l3 " + l3 + " l6 " + l6 );
		System.out.println("s3 " + s3 );
		System.out.println("s6 " + s6 );
		for (int i = 0; i < f3.toString().length(); i++)
		{
			System.out.print(f3.toString().charAt(i));
		}
		System.out.println();
		for (int i = 0; i < s1.length(); i++)
		{
			System.out.print(s1.charAt(i));
		}
		System.out.println();
		for (int i = 0; i < s4.length(); i++)
		{
			System.out.print(s4.charAt(i));
		}			
	}
}
