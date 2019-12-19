package Ex1;

//import com.sun.org.apache.bcel.internal.generic.RETURN;

public class ComplexFunction implements complex_function
{
  
  private function f1;
  private function f2;
  private Operation oper = Operation.None;
  
  public ComplexFunction ()
  { 
  }
  
  public ComplexFunction (Operation op, function f1, function f2)
  {
    //f1 can't be a null
    if (f1 == null) throw new RuntimeException();
    this.oper = op;
    this.f1 = f1;
    this.f2 = f2;
  }
  
  public ComplexFunction (function f1)
  {
    //f1 can't be a null
    if (f1 == null) throw new RuntimeException();
    this.f1 = f1;
    this.f2 = null;
    this.oper = Operation.None;
  }
  
  public ComplexFunction(String oper,function f1,function f2)
  {
    //f1 can't be a null
    if (f1 == null) throw new RuntimeException();
    oper = oper.toLowerCase();
    switch (oper)
    {
      case "plus":
        this.oper = Operation.Plus;
      break;
      case "mul":
        this.oper = Operation.Times;
      break; 
      case "div":
        this.oper = Operation.Divid;
      break; 
      case "max":
        this.oper = Operation.Max;
      break; 
      case "min":
        this.oper = Operation.Min;
      break; 
      case "comp":
        this.oper = Operation.Comp;
      break;
      case "none":
        this.oper = Operation.None;
      break; 
      case "error":
        this.oper = Operation.Error;
      break;
      default :
        this.oper = Operation.None;
    }
    this.f1 = f1;
    this.f2 = f2; 
  }
  
  
  
  /** Add to this complex_function the f1 complex_function
    * 
    * @param f1 the complex_function which will be added to this complex_function.
    */
  
  public void plus(function f1)
  {
    if (this.f2 == null)
      this.f2 = f1;
    else
      this.f1 = new ComplexFunction (this.oper, this.f1, this.f2);
    this.oper = Operation.Plus;
  }
  
  /** Multiply this complex_function with the f1 complex_function
    * 
    * @param f1 the complex_function which will be multiply be this complex_function.
    */
  
  public void mul(function f1)
  {
    if (this.f2 == null)
      this.f2 = f1;
    else
      this.f1 = new ComplexFunction (this.oper, this.f1, this.f2);
    this.oper = Operation.Times;
  }
  
  /** Divides this complex_function with the f1 complex_function
    * 
    * @param f1 the complex_function which will be divid this complex_function.
    */
  
  public void div(function f1)
  {
    if (this.f2 == null)
      this.f2 = f1;
    else
      this.f1 = new ComplexFunction (this.oper, this.f1, this.f2);
    this.oper = Operation.Divid;
  }
  
  /** Computes the maximum over this complex_function and the f1 complex_function
    * 
    * @param f1 the complex_function which will be compared with this complex_function - to compute the maximum.
    */
  
  public void max(function f1)
  {
    if (this.f2 == null)
      this.f2 = f1;
    else
    {
      this.f1 = new ComplexFunction (this.oper, this.f1, this.f2);
      this.f2 = f1;
    }
    this.oper = Operation.Max;
  }
  
  /** Computes the minimum over this complex_function and the f1 complex_function
    * 
    * @param f1 the complex_function which will be compared with this complex_function - to compute the minimum.
    */
  
  public void min(function f1)
  {
    if (this.f2 == null)
      this.f2 = f1;
    else
      this.f1 = new ComplexFunction (this.oper, this.f1, this.f2);
    this.oper = Operation.Min;
  }
  
  /**
   * This method wrap the f1 complex_function with this function: this.f(f1(x))
   * @param f1 complex function
   */
  
  public void comp(function f1)
  {
    if (this.f2 == null)
      this.f2 = f1;
    else
      this.f1 = new ComplexFunction (this.oper, this.f1, this.f2);
    this.oper = Operation.Comp;
  }
  
  /** returns the left side of the complex function - this side should always exists (should NOT be null).
    * @return a function representing the left side of this complex funcation
    */
  
  public function left()
  {
    return this.f1;
  }
  
  /** returns the right side of the complex function - this side might not exists (aka equals null).
    * @return a function representing the left side of this complex funcation
    */
  
  public function right()
  {
    return this.f2;
  }
  
  /**
   * The complex_function oparation: plus, mul, div, max, min, comp
   * @return
   */
  
  public Operation getOp()
  {
    return this.oper;
  }
  
  @Override
  public double f(double x)
  {
    if (f2 == null)
      return this.f1.f(x);
    else
    {
      switch (oper)
      {
        case Plus:
        	
          return (this.f1.f(x) + this.f2.f(x));
	case Times:
          return (this.f1.f(x) * this.f2.f(x));
        case Divid:
          if (this.f2.f(x) == 0) 
          throw new ArithmeticException ("can not be divided bt zero");
        return (this.f1.f(x) / this.f2.f(x));
        case Max:
          return Math.max(this.f1.f(x),this.f2.f(x));
        case Min:
          return Math.min(this.f1.f(x),this.f2.f(x));
        case Comp:
          return this.f1.f(this.f2.f(x));
        case None:
          if (this.f2 == null)
          return this.f1.f(x);
        throw new ArithmeticException ("Invild operation between 2 function");
        case Error:
          throw new ArithmeticException ("An Error in operation");
        default :
          throw new ArithmeticException ("can't difined the operation");
      }
    }   
  }
  
  @Override 
  public function initFromString(String s)
  {
    
    char openBracket = '(';
    char closeBracket = ')';
    int openBracketCount = 0;
    int closeBracketCount= 0;
    int posOfCom = 0;
    boolean flag = false;
    s = s.toLowerCase();
    if (s.contains(","))
    {
	    for (int i = 0; i < s.length(); i++)
	    {
	      
	      if (s.charAt(i) == openBracket) 
	        openBracketCount++;
	      else if (s.charAt(i) == closeBracket) 
	        closeBracketCount++;
	      else if ((openBracketCount == (closeBracketCount+1)) && ((s.charAt(i) == ',')&&(!flag)))
	      {
	        posOfCom = i;
	        flag = true;
	      }
	    }
	    if ((openBracketCount != closeBracketCount) || ((posOfCom ==0) && (openBracketCount != 0)))
	      throw new RuntimeException("Invalid cf string format");
	    
	    
	    if (openBracketCount != 0)
	    {
	      String op = s.substring(0,s.indexOf ("("));
	      String s1 = s.substring((s.indexOf ("(")+1),posOfCom);
	      String s2 = s.substring(posOfCom+1,(s.length()-1));
	      function f1 = this.initFromString (s1);
	      function f2 = this.initFromString (s2);
	      ComplexFunction cf=new ComplexFunction(op,f1,f2);
	      return cf; 
	    }
    }
    Polynom p1 = new Polynom (s);
    ComplexFunction cf=new ComplexFunction(p1);
    return cf;
  }  
  
  @Override 
  public function copy()
  {
    ComplexFunction copyFunc = new ComplexFunction (this.oper, this.f1 , this.f2);
    return copyFunc;
  }
  
  @Override
  public String toString()
  {
	  
	String s = "";

	if (f2 == null)
    {
    	s=this.f1.toString();
      	return s;
    }
	
    switch (oper)
    {
      case Plus:
    	  s = "plus(" + this.f1.toString() + "," + this.f2.toString() + ")";
    	  break;
      case Times:
    	  s = "mul("  + this.f1.toString() + "," + this.f2.toString()+")";
    	  break;
      case Divid:
    	  s =  "div("  + this.f1.toString() + "," + this.f2.toString()+")";
    	  break;
      case Max:
    	  s = "max("  + this.f1.toString() + "," + this.f2.toString()+")";
    	  break;
      case Min:
    	  s = "min("  + this.f1.toString() + "," + this.f2.toString()+")";
    	  break;
      case Comp:
    	  s =  "comp("  + this.f1.toString() + "," + this.f2.toString()+")";
    	  break;
      case None:
    	  s = "none("  + this.f1.toString() + "," + this.f2.toString()+")";
    	  break;
      case Error:
    	  s = "error("  + this.f1.toString() + "," + this.f2.toString()+")";
    	  break;
      default :
    	  s = "Error";
    	  break;
    }
    
    s = s.replace(" " , "");
    s = s.replace("+-" , "-");
    s = s.replace("\u200e" , "");

    return s;
  }
  
  public boolean equals(Object obj)
  {
	  if (!(obj instanceof function))
		  return false;
	  function fun = (function) obj;
	  double i=-100;
	  if (this.toString() == fun.toString())
		  return true;
	  while (i<100)
	  {
		  if (this.f(i) != fun.f(i))
			  return false;
		   i += 0.01; 
	  }
	  return true;
  }
  
}
