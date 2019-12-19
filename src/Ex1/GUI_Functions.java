package Ex1;

import java.awt.*;
import java.io.*;
import java.util.*;
import org.json.*;
import org.json.simple.parser.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
public class GUI_Functions implements functions
{
	private LinkedList<function> fColl;
	private static Color[] colorArray = {Color.black, Color.blue, Color.cyan, 
										 Color.darkGray, Color.gray, Color.green,
										 Color.lightGray, Color.red, Color.orange, 
										 Color.pink, Color.magenta, Color.yellow};
	
	public GUI_Functions()
	{
		fColl = new LinkedList<function>();
	}
	
	@Override
	public int size() 
	{
		return fColl.size();
	}

	@Override
	public boolean isEmpty()
	{
		return fColl.isEmpty();
	}

	@Override
	public boolean contains(Object o) 
	{
		return fColl.contains(o);
	}

	@Override
	public Iterator<function> iterator() 
	{
		return fColl.iterator();
	}

	@Override
	public Object[] toArray() {
		return fColl.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return fColl.toArray(a);
	}

	@Override
	public boolean add(function e)
	{
		return fColl.add(e);
	}

	@Override
	public boolean remove(Object o) 
	{
		return fColl.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) 
	{
		return fColl.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends function> c) 
	{
		return fColl.addAll(c);
	}

	@Override
	public boolean removeAll(Collection<?> c)
	{
		return fColl.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) 
	{
		return fColl.retainAll(c);
	}

	@Override
	public void clear() 
	{
		fColl.clear();
	}

	/**
	 * Init a new collection of functions from a file
	 * @param file - the file name
	 * @throws IOException if the file does not exists of unreadable (wrong format)
	 */

	@Override
	public void initFromFile(String file) throws IOException
	{
		try
		{
			String s = "";
            // FileReader reads text files in the default encoding.
            FileReader reader = new FileReader(file);

            // Always wrap FileReader in BufferedReader.
            BufferedReader buff = new BufferedReader(reader);

            while((s = buff.readLine()) != null)
            {
            	ComplexFunction f = new ComplexFunction();
                fColl.add(f.initFromString(s));     
            }   

            // Always close files.
            buff.close();         
        }
        catch(FileNotFoundException ex)
		{
            System.out.println("Unable to open file '" + file + "'");                
        }
        catch(IOException ex)
		{
            System.out.println("Error reading file '"  + file + "'");                  
            
        }	
	}
	
	/**
	 * 
	 * @param file - the file name
	 * @throws IOException if the file is not writable
	 */
	
	@Override
	public void saveToFile(String file) throws IOException
	{
		String s = "";
		 try {
	            // Assume default encoding.
	            FileWriter writer =new FileWriter(file);

	            // Always wrap FileWriter in BufferedWriter.
	            BufferedWriter buff = new BufferedWriter(writer);

	            
	            for (function f : fColl)
	            {
	            	s = f.toString();
	            	buff.write(s);
	            	buff.write("\r\n");
	            }
	            
	            // Always close files.
	            buff.close();
	        }
	        catch(IOException ex) 
		 {
	            System.out.println("Error writing to file '" + file + "'");
	            
		 }
	}
		
	
	private void drawColorLine(double x0, double y0, double x1, double y1, Color color)
	{
		StdDraw.setPenColor(color);
		StdDraw.line(x0, y0, x1, y1);
	}
	
	
	
	
	/**
	 * Draws all the functions in the collection in a GUI window using the
	 * given parameters for the GUI windo and the range & resolution
	 * @param width - the width of the window - in pixels
	 * @param height - the height of the window - in pixels
	 * @param rx - the range of the horizontal axis
	 * @param ry - the range of the vertical axis
	 * @param resolution - the number of samples with in rx: the X_step = rx/resulution
	 */

	@Override
	public void drawFunctions(int width, int height, Range rx, Range ry, int resolution) 
	{
		//function f=new Polynom("x^2");
		
		double x0 = rx.get_min();
		double x1 = rx.get_max();
		double y0 = ry.get_min();
		double y1 = ry.get_max();
		
		double res = (x1 - x0) / resolution;
		
		StdDraw.setCanvasSize(width, height);
		StdDraw.setXscale(rx.get_min(), rx.get_max());
		StdDraw.setYscale(ry.get_min(), ry.get_max());
		
				
		for (double d = 0; d < x1; d++)
		{
			drawColorLine(d, y0, d, y1, StdDraw.LIGHT_GRAY);
			drawColorLine(d, -0.5, d, 0.5, StdDraw.BLACK);
		}
		for (double d = 0; d > x0; d--)
		{
			drawColorLine(d, y0, d, y1, StdDraw.LIGHT_GRAY);
			drawColorLine(d, -0.5, d, 0.5, StdDraw.BLACK);
		}
		
		for (double d = 0; d < y1; d++)
		{
			drawColorLine(x0, d, x1, d, StdDraw.LIGHT_GRAY);
			drawColorLine(-0.5, d, 0.5, d, StdDraw.BLACK);
		}
		for (double d = 0; d > y0; d--)
		{
			drawColorLine(x0, d, x1, d, StdDraw.LIGHT_GRAY);
			drawColorLine(-0.5, d, 0.5, d, StdDraw.BLACK);
		}
		
		drawColorLine(x0, 0, x1, 0, StdDraw.BLACK);
		drawColorLine(0, y0, 0, y1, StdDraw.BLACK);
		
		
		int colorIndex = 0;
		
		for (function f : fColl)
		{
			Color clr = colorArray[colorIndex++];
			if (colorIndex == colorArray.length)
				colorIndex = 0;
			
			double preX = x0;
			double preY = f.f(x0);
			
			for (double d = x0; d < x1; d+=res)
			{
				double newY = f.f(d);
				drawColorLine(preX, preY, d, newY, clr);
				preX = d;
				preY = newY;		
				
			}
		}
		
	}
	
	/**
	 * Draws all the functions in the collection in a GUI window using the given JSON file
	 * @param json_file - the file with all the parameters for the GUI window. 
	 * Note: is the file id not readable or in wrong format should use default values. 
	 */
	@Override
	public void drawFunctions(String json_file) 
	{
		
		JSONParser parser = new JSONParser();

		try 
		{   
			int i = 0;
			Object obj = parser.parse(new FileReader(json_file));

			JSONObject jsonObject =  (JSONObject) obj;

			int width = ((Long)jsonObject.get("Width")).intValue();
			int height = ((Long)jsonObject.get("Height")).intValue();
			int resolution = ((Long)jsonObject.get("Resolution")).intValue();		
			JSONArray Range_X = (JSONArray)jsonObject.get("Range_X");
			JSONArray Range_Y = (JSONArray)jsonObject.get("Range_Y");
			double x0 = 0,x1 =0 ,y0 = 0 ,y1 = 0;
			
			
	        
	         //Iterating the contents of the array
	         Iterator<Long> itX = Range_X.iterator();
	         while(itX.hasNext()) 
	         {
	        	  x0 = itX.next().doubleValue();
	        	  x1 = itX.next().doubleValue();
	         }
	         
	         Iterator<Long> itY = Range_Y.iterator();
	         while(itY.hasNext()) 
	         {
	        	  y0 = itY.next().doubleValue();
	        	  y1 = itY.next().doubleValue();
	         }
	         
	         
	         Range RangeX = new Range (x0,x1);
	         Range RangeY = new Range (y0,y1);
	         
	         drawFunctions(width, height, RangeX, RangeY, resolution);
          
			
			
		}
		
		catch (FileNotFoundException e) 
		{			
			drawFunctions(500, 500, new Range(-20,20), new Range(-20,20), 1000);        
		}
		
		catch  (IOException e)
		{		
			drawFunctions(500, 500, new Range(-20,20), new Range(-20,20), 1000);        
		}
		
		catch (ParseException e) 
		{	
			drawFunctions(500, 500, new Range(-20,20), new Range(-20,20), 1000);        
		}
	}

}
