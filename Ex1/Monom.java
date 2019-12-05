
package Ex1;

import java.util.Comparator;
import java.text.DecimalFormat;

/**
 * This class represents a simple "Monom" of shape a*x^b, where a is a real number and a is an integer (summed a none negative), 
 * see: https://en.wikipedia.org/wiki/Monomial 
 * The class implements function and support simple operations as: construction, value at x, derivative, add and multiply. 
 * @author Boaz
 *
 */
public class Monom implements function{
	public static final Monom ZERO = new Monom(0,0);
	public static final Monom MINUS1 = new Monom(-1,0);
	public static final double EPSILON = 0.0000001;
	public static final Comparator<Monom> _Comp = new Monom_Comperator();
	public static Comparator<Monom> getComp() {return _Comp;}
        
	public Monom(double a, int b)
    {
		this.set_coefficient(a);
		this.set_power(b);
	}
	public Monom(Monom ot)
        {
		this(ot.get_coefficient(), ot.get_power());
	}
	
	public double get_coefficient()
        {
		return this._coefficient;
	}
	public int get_power()
        {
		return this._power;
	}
	/** 
	 * this method returns the derivative monom of this.
	 * @return
	 */
	public Monom derivative() 
        {
		if(this.get_power()==0) {return getNewZeroMonom();}
		return new Monom(this.get_coefficient()*this.get_power(), this.get_power()-1);
	}
	public double f(double x) 
        {
		double ans=0;
		double p = this.get_power();
		ans = this.get_coefficient()*Math.pow(x, p);
		return ans;
	} 
	public boolean isZero()
        {
            return this.get_coefficient() == 0;
        }
	// ***************** add your code below **********************
	public Monom(String s) throws NumberFormatException
    {
        
        int power = 0;
        double coef = 0;

        try
        {
	        s = s.toLowerCase();
	
	        // find coef
	        int pos = s.indexOf("x");
	        if (pos != -1)
	        {
	
	            String s1 = s.substring(0, pos);
	            switch (s1)
	            {
	                case "+":
	                case "":
	                    coef = 1.0;
	                    break;
	
	                case "-":
	                    coef = -1.0;
	                    break;
	
	                default:
	                    coef = Double.parseDouble(s1);
	                    break;
	            }
	
	            // find power
	            pos = s.indexOf("^");
	            if (pos != -1)
	                power = Integer.parseInt(s.substring(pos + 1, s.length()));
	            else
	                power = 1;
	            
	        }
	        else
	        {
	            coef = Double.parseDouble(s);
	            power = 0;
	        }
	          
	        this.set_coefficient(coef);
	        this.set_power(power);
        }
    	catch (NumberFormatException ex)
		{
    		this.set_coefficient(0);
	        this.set_power(0);
			throw new RuntimeException("Invalid monom string format, monom has been initialized to zero");
		}
    }
	public function initFromString(String s) 
	{
		Monom m = new Monom (s);   
        return m;
	}
             
	public function copy()
	{
		Monom m = new Monom (this.toString());
			return m;
	}
	
	public boolean equals(Object obj)
	{
		return (this.toString().compareTo(obj.toString()) == 0);
	}
	
	public void add(Monom m)
    {
		if (this.isZero())
		{
			this.set_coefficient(m.get_coefficient());
			this.set_power(m.get_power());
		}
		else
		{
	        if (m.get_power()==this.get_power())
	            this._coefficient+=m.get_coefficient();
	        else
	        	throw new RuntimeException("you can't add a monom with a different power");
		}
        
    }
	
	public void multiply(Monom m) 
        {
            if (m.get_coefficient() == 0.0)
            {
                this._coefficient = 0;
                this._power = 0;
            }
            else
            {
            	this._coefficient = this._coefficient*m.get_coefficient();
                this._power += m.get_power();
            }
        }
	public String toString() 
        {
            if (this.get_power() == 0)
                return (df2.format(this.get_coefficient()));
            
                       
            String s = "";
            
            if (this.get_coefficient() == -1)
            {
                s = "-X";
            }
            else
            {           
                s = "X";
                if (this.get_coefficient() != 1)
                    s = df2.format(this.get_coefficient()) + s;
            }
            
            if (this.get_power() > 1)
                s += "^" + Integer.toString(this.get_power());
            
            return (s);
	}
	// you may (always) add other methods.

	//****************** Private Methods and Data *****************
	
	private static DecimalFormat df2 = new DecimalFormat("#.##");
	private void set_coefficient(double a){
		this._coefficient = a;
	}
	private void set_power(int p) {
		if(p<0) {throw new RuntimeException("ERR the power of Monom should not be negative, got: "+p);}
		this._power = p;
	}
	private static Monom getNewZeroMonom() {return new Monom(ZERO);}
	private double _coefficient; 
	private int _power;
	
	
}
