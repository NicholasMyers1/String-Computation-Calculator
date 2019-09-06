
public class StringNumber extends StringMath{
	// Used for specific instance of StringNumber;
	private String stringNumber;
	private String initialStringNumber;
	// ADD: 
	public void add(StringNumber x) { stringNumber = add(stringNumber, x.get()); } 
	public void add(String x) { stringNumber = add(stringNumber, x); } 
	// SUB:
	public void sub(StringNumber x) { stringNumber = sub(stringNumber, x.get()); } 
	public void sub(String x) { stringNumber = sub(stringNumber, x); }
	// MULT: 
	public void mult(StringNumber x) { stringNumber = mult(stringNumber, x.get());}
	public void mult(String x) { stringNumber = mult(stringNumber, x);}
	// DIV: TO BE DONE:
	public void div(StringNumber x) { stringNumber = div(stringNumber, x.get());}
	public void div(String x) { stringNumber = div(stringNumber, x);}
	// Constructor: 
	StringNumber() { stringNumber = "0"; initialStringNumber = stringNumber; }
	StringNumber( StringNumber x ) { stringNumber = x.get(); initialStringNumber = stringNumber; }
	StringNumber( String n ){ set(n); initialStringNumber = stringNumber; }
	StringNumber( int n ){ set(Integer.toString(n)); initialStringNumber = stringNumber; }
	StringNumber( double n ){ set(Double.toString(n)); initialStringNumber = stringNumber; }
	StringNumber( float n ){ set(Float.toString(n)); initialStringNumber = stringNumber; }
	StringNumber( boolean n ){ set(Boolean.toString(n)); initialStringNumber = stringNumber; }
	// Get and Set: 
	String get() { return stringNumber; }
	public void reset() { stringNumber = initialStringNumber; }
	public void set() { stringNumber = "0"; }
	public void set(StringNumber x) { stringNumber = x.get(); }
	public void set(String x) { stringNumber = toStringNumber(x); }
	public void set( int n ){ set(Integer.toString(n)); }
	public void set( double n ){ set(Double.toString(n)); }
	public void set( float n ){ set(Float.toString(n)); }
	public void set( boolean n ){ stringNumber = (n ? "1" : "0" ); }
	// Override the toString method, so the hashString isn't returned;
	@Override public String toString(){ return stringNumber; }
}