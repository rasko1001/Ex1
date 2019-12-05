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
    	
    	Polynom_able p = p1.copy(); 
    	
        Iterator<Monom> it = p.iterator();
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
        Polynom_able p = p1.copy();
        Monom m = new Monom(-1,0);
        p.multiply(m);
        
        this.add(p);
    }

    @Override
    public void multiply(Polynom_able p1) 
    {    	
    	Polynom newp = new Polynom();        
        Iterator<Monom> it1 = p1.iterator();                
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
        Iterator<Monom> it3 = newp.iterator();
        while (it3.hasNext())
            this.add(it3.next());
    }

    @Override
    public boolean equals(Polynom_able p1) {
        // TODO Auto-generated method stub
        return (this.toString().compareTo(p1.toString()) == 0);
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
    	
        double x2=x0;
        while (x2<x1)
        {
            if (Math.abs(this.f(x2))<eps)
                    return x2;
            x2+=eps;
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
        Iterator<Monom> it = this.iterator();
        while (it.hasNext())
        {
            p.add(it.next().derivative());
        }
       
        return p;
    }

    @Override
    public double area(double x0, double x1, double eps) {
    	if (x1 < x0)
    		throw new RuntimeException("x1 must be bigger then x0");
    	
        double area = 0;
        while(x0<x1) 
        {
            area+=(this.f(x0))*eps;
            x0+=eps;
        }
        return area;
    }

    @Override
    public Iterator<Monom> iterator() 
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
    	if (polynomMonoms.size() == 0)
    		return "0";
    	
        Collections.sort(polynomMonoms, new Monom_Comperator());
        Iterator<Monom> it = this.iterator();
        while (it.hasNext())
        {
        	if (it.next().get_coefficient()==0.0)
        		it.remove();
        }

        String s = polynomMonoms.get(0).toString();

        for (int i = 1; i < polynomMonoms.size(); i++) 
        {
            s += "+" + polynomMonoms.get(i).toString();
            
        }
       
        s = s.replace("+-", "-");
        s = s.replace(".00", "");
        return s;

    }
   

}
