package Ex1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.function.Predicate;

/**
 * This class represents a Polynom with add, multiply functionality, it also
 * should support the following: 1. Riemann's Integral:
 * https://en.wikipedia.org/wiki/Riemann_integral 2. Finding a numerical value
 * between two values (currently support root only f(x)=0). 3. Derivative
 *
 * @author Boaz
 *
 */
public class Polynom implements Polynom_able {

    /**
     * Zero (empty polynom)
     */
    ArrayList<Monom> polynomMonoms;

    public Polynom() {
        this.polynomMonoms = new ArrayList<Monom>();
    }

    /**
     * init a Polynom from a String such as: {"x", "3+1.4X^3-34x",
     * "(2x^2-4)*(-1.2x-7.1)", "(3-3.4x+1)*((3.1x-1.2)-(3X^2-3.1))"};
     *
     * @param s: is a string represents a Polynom
     */
    
    
    public Polynom(String s)
    {
    	this();
   
        s = s.replace(" ", "");
        s = s.replace("-", "+-");
        String[] tokens = s.split("\\+");
        for (String t : tokens)
        {
        	if (t.length() > 0)
        	{
        		Monom m = new Monom(t);
        		this.add(m);
        	}
        }
    	
       
    }
    
    @Override
    public double f(double x) {
        double ans = 0.0;
        
        for (Monom m : polynomMonoms)
            ans += m.f(x);

        return (ans);
    }

    @Override
    public void add(Polynom_able p1) {
    	
    	Polynom_able p = (Polynom_able)p1.copy(); 
    	
        Iterator<Monom> it = p.iteretor();
        
        while (it.hasNext())
        {
        	Monom m = it.next();
            this.add(new Monom(m));
        }

    }

    @Override
    public void add(Monom m1) 
    {
        for (Monom m : polynomMonoms)
        {
            if (m1.get_power() == m.get_power()) 
            {
                m.add(new Monom(m1));
                if (m.get_coefficient() == 0)
                	polynomMonoms.remove(m);
                return;
            }
        }
        polynomMonoms.add(new Monom(m1));
    }

    @Override
    public void substract(Polynom_able p1) 
    {
        Polynom_able p = (Polynom_able)p1.copy();
        Monom m = new Monom(-1,0);
        p.multiply(m);
        
        this.add(p);
    }

    @Override
    public void multiply(Polynom_able p1) 
    {    	
    	Polynom newp = new Polynom();        
        Iterator<Monom> it1 = p1.iteretor();                
        while (it1.hasNext())
        {
            Monom m3 = it1.next();
            for (Monom m2 : polynomMonoms)
            {
                Monom m4 = new Monom(m2);
                m4.multiply(m3);
                newp.add(m4);
            }
            
        }
        
        this.polynomMonoms = new ArrayList<Monom>();
        Iterator<Monom> it3 = newp.iteretor();
        while (it3.hasNext())
            this.add(it3.next());
    }

    @Override
  
    public boolean equals(Object obj)
    {
    		
    	if ((obj instanceof Polynom) || (obj instanceof Monom))	
    	{
    		return (this.toString().equals(obj.toString()));
    	}
    	else if (obj instanceof ComplexFunction)
    	{
    		ComplexFunction cf = (ComplexFunction) obj;
    		double  i = -100;
    		if  (this.toString().equals(cf.toString()))
    			return true;
    		while (i<100)
    		{
    			if (this.f(i) != cf.f(i))
    				return false;
    			i += 0.01; 
    		}
    		return true;
    	}
    	return false;	
    			
    }
      
    @Override
    public boolean isZero() {
        if (polynomMonoms.size() == 0)
            return true;
        
        if ((polynomMonoms.size() > 0) && (polynomMonoms.get(0).get_coefficient() == 0))
            return true;
        
        return false;
    }

    @Override
    public double root(double x0, double x1, double eps) {
    	
    	if (x1 < x0)
    		throw new RuntimeException("x1 must be bigger then x0");
    	double t =0.0;
        /*double x2=x0;
        while (x2<x1)
        {
            if ((Math.abs(this.f(x2)))<eps)
                    return x2;
            x2+=eps;
        }*/
        
        while (x0<x1)
        {
        	t = (x1+x0)/2.0;
        	if ((Math.abs(this.f(t)))<eps)
                return t;
        	if (this.f(t)*this.f(x0) < 0)
        		x1 = t;
        	else
        		x0 = t;  		
        }
    
    
        
        throw new RuntimeException("there is no root");
        
    }

    @Override
    public Polynom_able copy() {
        Polynom p=new Polynom (this.toString());
        return p;
    }

    @Override
    public Polynom_able derivative() {
        Polynom p = new Polynom();
        Iterator<Monom> it = this.iteretor();
        while (it.hasNext())
        {
            p.add(it.next().derivative());
        }
       
        return p;
    }

    @Override
    public double area(double x0, double x1, double eps)
    {
    	if (x1 < x0)
    		throw new RuntimeException("x1 must be bigger then x0");
    	double f_x = 0;	
        double area = 0.0;
        while(x0<x1) 
        {
        	f_x = this.f(x0);
        	if (f_x>0)       	
        		area+=(f_x*eps);
            x0+=eps;        	
        }
        return area;
    }

    @Override
    public Iterator<Monom> iteretor() 
    {
        
        Iterator iter = polynomMonoms.iterator();
       
        return iter;
    }

    @Override
    public void multiply(Monom m1) 
    {
        for (Monom m : polynomMonoms)
            m.multiply(m1);
    }

    @Override
    public String toString() 
    {
    	String t ="";
    	if (polynomMonoms.size() == 0)
    		return "0";
    	
        Collections.sort(polynomMonoms, new Monom_Comperator());
        Iterator<Monom> it = this.iteretor();
        while (it.hasNext())
        {
        	if (it.next().get_coefficient()==0.0)
        		it.remove();
        }
        
        if (polynomMonoms.size() == 0)
        	return ("0");
        
        String s = polynomMonoms.get(0).toString();


        for (int i = 1; i < polynomMonoms.size(); i++) 
        {
        	t = polynomMonoms.get(i).toString();
        	if (t.charAt(0) == ' ')
        		s += t;
        	else if (t.charAt(0) == '-')
        		s += t;
        	else 
        		s += "+" + t;       
        }
        //s = s.replace("+","-");
        //s = s.replace("+ý", "-");
        s = s.replace("+ý-", "-");  
        s = s.replace(".00", "");
        s = s.replace("--", "-");
        return s;

    }

	@Override
	public function initFromString(String s) 
	{
		Polynom p = new Polynom (s);
		return p;
	}
	

}
