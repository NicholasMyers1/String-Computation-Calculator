package StringMath;
import java.math.BigInteger;
import java.util.List;

public abstract class StringMath {
	enum StringMathOperation{ // enum for operations:
		ADD, SUB, MULT, DIV, 
	} // public static methods:
	public static String toStringNumber() { return "0"; } // set to 0 for some reason. Necessary?
	public static String toStringNumber(StringNumber x) { return ( toStringNumber(x) ); } // Set a StringNumber to itself. Necessary?
	public static String toStringNumber(int x) { return ( toStringNumber(Integer.toString(x)) ); }
	public static String toStringNumber(double x) { return (toStringNumber(Double.toString(x))); }
	public static String toStringNumber(float x) { return (toStringNumber(Float.toString(x))); }
	public static String toStringNumber(long x) { return (toStringNumber(Long.toString(x))); }
	public static String toStringNumber(BigInteger n ){ return (toStringNumber(n.toString())); }
	public static String toStringNumber(boolean x) { return ( x ? "1" : "0" ); }
	public static String toStringNumber(String x) { // Deletes leading/trailing zeros, non-number characters, and out of place decimals and negatives as of now;
		String stringNumberTemp = "";
		boolean isNegative = false;
		boolean isDecimal = false;
		// Create a valid StringNumber;
		for(int n=0;n<x.length();n++) {
			if( x.charAt(n) == '-' && !isNegative && !isDecimal ) {
				isNegative = true;
			} else if( x.charAt(n) == '.' && !isDecimal ) {
				stringNumberTemp += "."; isDecimal = true;
			} else if( x.charAt(n) >= 48 && x.charAt(n) <= 57 ) 
				stringNumberTemp += x.charAt(n);
		} // Remove unnecessary zeros: then add in the appropriate zeros and negative sign if necessary;
		String r = stringNumberTemp;
		if( r.length() > 1 ) {
			for( int n = 0; !r.equals("0") && stringNumberTemp.charAt(n) == '0'; n++ ) 
				r = r.substring(1, r.length());
			for( int n = stringNumberTemp.length()-1; n >= 0 && stringNumberTemp.charAt(n) == '0' && isDecimal ; n-- )
				r = r.substring(0, r.length()-1);
			r = ( r.charAt(0) == '.' ? "0" + r : r ) + ( r.charAt(r.length()-1) == '.' ? "0" : "" );
		} if( isNegative && !r.equals("0") ) r = "-" + r;
		return( r.length() == 0 || r.equals(".") || r.equals("-") || r.equals("0.0") || r.equals("-0.0") || r.equals("-.") || r.equals("-0") ? "0" : 
				r.length() > 1 && r.charAt(r.length()-2) == '.' && r.charAt(r.length()-1) == '0' ? r.substring(0, r.length()-2) : r );
	} // ADD: 
	public static String add( String... x ) {
		String ret = new StringNumber( x[0] ).get();
		for(int n=1;n<x.length;n++) ret = parseToPerformOperation( ret, x[n], StringMathOperation.ADD );
		return ret;
	} public static StringNumber add( StringNumber... x ) {
		String ret = x[0].get();
		for(int n=1;n<x.length;n++) ret = parseToPerformOperation( ret, x[n].get(), StringMathOperation.ADD );
		return new StringNumber(ret);
	} @SafeVarargs public static StringNumber add( List<Object>...x ) {
		String ret = ( x[0].get(0) instanceof Boolean ? ( (Boolean)x[0].get(0) ?  "1" : "0" ) : new StringNumber( x[0].get(0).toString() ).get() );
		for(int l=0;l<x.length;l++)
			for(int n= (l==0?1:0);n<x[l].size();n++)
				ret = parseToPerformOperation( ret, 
					( x[l].get(n) instanceof Boolean ? ( (Boolean)x[l].get(n) ?  "1" : "0" ) : new StringNumber( x[l].get(n).toString() ).get() ),
					StringMathOperation.ADD );
		return new StringNumber(ret);
	} // SUB: 
	public static String sub( String... x ) {
		String ret = new StringNumber( x[0] ).get();
		for(int n=1;n<x.length;n++) ret = parseToPerformOperation( ret, x[n], StringMathOperation.SUB );
		return ret;
	} public static StringNumber sub( StringNumber... x ) {
		String ret = x[0].get();
		for(int n=1;n<x.length;n++) ret = parseToPerformOperation( ret, x[n].get(), StringMathOperation.SUB );
		return new StringNumber(ret);
	} @SafeVarargs public static StringNumber sub( List<Object>...x ) {
		String ret = ( x[0].get(0) instanceof Boolean ? ( (Boolean)x[0].get(0) ?  "1" : "0" ) : new StringNumber( x[0].get(0).toString() ).get() );
		for(int l=0;l<x.length;l++)
			for(int n = (l==0?1:0);n<x[l].size();n++)
				ret = parseToPerformOperation( ret, 
					( x[l].get(n) instanceof Boolean ? ( (Boolean)x[l].get(n) ?  "1" : "0" ) : new StringNumber( x[l].get(n).toString() ).get() ),
					StringMathOperation.SUB );
		return new StringNumber(ret);
	} // MULT: 
	public static String mult( String... x ) {
		String ret = new StringNumber( x[0] ).get(); if( ret.equals("0") ) return "0";
		for(int n=1;n<x.length;n++) {
			ret = parseToPerformOperation( ret, x[n], StringMathOperation.MULT ); if( ret.equals("0") ) return "0";
		} return ret;
	} public static StringNumber mult( StringNumber... x ) {
		String ret = x[0].get(); if( ret.equals("0") ) return x[0];
		for(int n=1;n<x.length;n++) {
			ret = parseToPerformOperation( ret, x[n].get(), StringMathOperation.MULT ); if( ret.equals("0") ) return new StringNumber();
		} return new StringNumber(ret);
	} @SafeVarargs public static StringNumber mult( List<Object>...x ) {
		String ret = ( x[0].get(0) instanceof Boolean ? ( (Boolean)x[0].get(0) ?  "1" : "0" ) : new StringNumber( x[0].get(0).toString() ).get() );
		if( ret.equals("0") ) return new StringNumber();
		for(int l=0;l<x.length;l++)
			for(int n= (l==0?1:0);n<x[l].size();n++) {
				ret = parseToPerformOperation( ret, 
					( x[l].get(n) instanceof Boolean ? ( (Boolean)x[l].get(n) ?  "1" : "0" ) : new StringNumber( x[l].get(n).toString() ).get() ),
					StringMathOperation.MULT ); if( ret.equals("0") ) return new StringNumber();
			} return new StringNumber(ret);
	} // DIV: // TODO;
	public static String div( String... x ) {
		String ret = new StringNumber( x[0] ).get(); if( ret.equals("0") ) return "0";
		for(int n=1;n<x.length;n++) {
			ret = parseToPerformOperation( ret, x[n], StringMathOperation.DIV ); if( ret.equals("0") ) return "0";
		} return ret;
	} public static StringNumber div( StringNumber... x ) {
		String ret = x[0].get(); if( ret.equals("0") ) return x[0];
		for(int n=1;n<x.length;n++) {
			ret = parseToPerformOperation( ret, x[n].get(), StringMathOperation.DIV ); if( ret.equals("0") ) return new StringNumber();
		} return new StringNumber(ret);
	} @SafeVarargs public static StringNumber div( List<Object>...x ) {
		String ret = ( x[0].get(0) instanceof Boolean ? ( (Boolean)x[0].get(0) ?  "1" : "0" ) : new StringNumber( x[0].get(0).toString() ).get() );
		if( ret.equals("0") ) return new StringNumber();
		for(int l=0;l<x.length;l++)
			for(int n= (l==0?1:0);n<x[l].size();n++) {
				ret = parseToPerformOperation( ret, 
					( x[l].get(n) instanceof Boolean ? ( (Boolean)x[l].get(n) ?  "1" : "0" ) : new StringNumber( x[l].get(n).toString() ).get() ),
					StringMathOperation.DIV ); if( ret.equals("0") ) return new StringNumber();
			} return new StringNumber(ret);
	} // local methods: 
	private static String parseToPerformOperation(String a, String b, StringMathOperation operation) {
		// set the strings to valid StringNumbers;
		String n[] = { toStringNumber(a) , toStringNumber(b) };
		// set negative if necessary, then remove the negative signs;
		boolean isNegative[] = { n[0].charAt(0) == '-', n[1].charAt(0) == '-' };
		// get the decimal positions if any exist and insert if necessary;
		int decimalPosition[] = { -1, -1 };
		for(int c=0;c<n.length;c++) {
			if ( isNegative[c] ) n[c] = n[c].substring(1, n[c].length());
			for(int d=0;d<n[c].length();d++) 
				if( n[c].charAt(d) == '.' ) { decimalPosition[c] = d; break; }
			if( decimalPosition[c] == -1 ) { decimalPosition[c] = n[c].length(); n[c] += "."; }
		}		
		int lengthLeft[] = { decimalPosition[1]-1, decimalPosition[0]-1 };
		int lengthRight[] = { n[1].length()-1-decimalPosition[1], n[0].length()-1-decimalPosition[0] };
		// add in correct number of zeros to each number before and after the decimal point:
		for(int c = 0; c < n.length; c++) {
			for(int d=0; d<lengthLeft[c] && lengthLeft[0]!=lengthLeft[1]; d++)
				{ n[c] = "0" + n[c]; decimalPosition[c]+=1; }
			for(int d=0; d<lengthRight[c] && lengthRight[0]!=lengthRight[1]; d++)
				n[c] += "0";
		} // swap so the largest number is on top:
		boolean isSwapped = false;
		for(int c=0;c<n[0].length();c++) {
			if( n[0].charAt(c) < n[1].charAt(c) ) { 
				String s = n[0]; n[0] = n[1]; n[1] = s; 
				boolean neg = isNegative[0]; isNegative[0] = isNegative[1]; isNegative[1] = neg; 
				isSwapped = true; break;
			} else if( n[0].charAt(c) > n[1].charAt(c) ) break;
		} if ( n[0].length() == 0 && n[1].length() == 0 ) return "0"; // return 0 if necessary: 
		String result = "";
		switch( operation ) { // perform the appropriate function: 
			case ADD: {
				result = ( isNegative[0] && isNegative[1] || !isNegative[0] && !isNegative[1] ? addFunction( n[0], n[1] ) : subtractFunction( n[0], n[1] ) );
				return toStringNumber( isNegative[0] && !isNegative[1] || isNegative[1] && isNegative[0] ? "-" + result : result );
			} case SUB: {
				result = ( isNegative[0] && isNegative[1] || !isNegative[0] && !isNegative[1] ? subtractFunction( n[0], n[1] ) : addFunction( n[0], n[1] ) );
				return toStringNumber( isSwapped && !isNegative[0] && !isNegative[1] || isSwapped && !isNegative[0] && isNegative[1] || 
						!isSwapped && isNegative[0] && isNegative[1] || !isSwapped && isNegative[0] && !isNegative[1] ? "-" + result : result );
			} case MULT: {
				result = multiplyFunction( n[0].replace(".", ""), n[1].replace(".", "") ); // must take out decimals, or multiplication returns wrong answer;
				int decimalPositionForMultiplication = ( n[0].length()-decimalPosition[0]-1 ) + ( n[1].length()-decimalPosition[1]-1 ) ;
				if( decimalPositionForMultiplication >= 0 ) 
					result =	result.substring( 0,result.length()-decimalPositionForMultiplication ) + "." +
								result.substring( result.length()-decimalPositionForMultiplication,result.length() );
				return toStringNumber( isNegative[0] && !isNegative[1] || !isNegative[0] && isNegative[1] ? "-" + result : result );
			} case DIV: {
				result = divideFunction( n[0], n[1] );
				return toStringNumber( result );
			} default: return "";
		}
	}
	private static String addFunction(String a, String b) { // perform the add function: 
		String result = "";
		boolean carry = false;
		for( int x= a.length()-1; x >= 0; x-- ) {
			if( a.charAt(x) == '.' ) { result = '.' + result; continue; }
			int r = ( Character.getNumericValue( a.charAt(x) ) + Character.getNumericValue( b.charAt(x) ) ) + ( carry ? 1 : 0 );
			carry = ( r > 9 ? true : false );
			result = String.valueOf( ( r > 9 ? r-10 : r ) ) + result;
		} return ( carry ? "1" + result : result);
	}
	private static String subtractFunction(String a, String b) { // perform the subtract function:
		String result = "";
		boolean borrow = false;
		for( int x= a.length()-1; x >= 0; x-- ) {
			if( a.charAt(x) == '.' ) { result = "." + result; continue; }
			int top = Character.getNumericValue( a.charAt(x) ) + ( borrow ? -1 : 0 );
			int bottom = Character.getNumericValue( b.charAt(x) );
			if( top < bottom ) { top += 10 ; borrow = true; }
			else borrow = false;
			result = String.valueOf( ( top-bottom > 9 ? top-bottom-10 : top-bottom ) ) + result;
		} return result;
	}
	private static String multiplyFunction(String a, String b) { // perform the multiplication function: 
		String result = "";
		String zerosToAddPerIteration = ""; // zeros must be added to the end of the number after each iteration because of multiplication;
		for( int x=a.length()-1; x >= 0; x-- ) {
			String iterationResult = "";
			String carry = "0";
			for( int y=a.length()-1; y >= 0; y-- ) {
				String innerResult = "0";
				for( int z=0;z < ( a.charAt(y)>b.charAt(x) ? Character.getNumericValue(b.charAt(x)) : Character.getNumericValue(a.charAt(y)) );z++ ){
					String tempA = Character.toString( ( a.charAt(y) > b.charAt(x) ? a.charAt(y) : b.charAt(x) ) );
					innerResult = addFunction( innerResult, ( tempA.length()<innerResult.length()) ? "0"+tempA : tempA );
				}
				innerResult = addFunction( innerResult, ( carry.length()<innerResult.length() ? "0"+carry : carry ) );
				carry = ( innerResult.length() > 1 ? Character.toString( innerResult.charAt(0) ) : "0" );
				iterationResult = innerResult.charAt( innerResult.length()-1 ) + iterationResult;
			}
			iterationResult = carry + iterationResult + zerosToAddPerIteration;
			zerosToAddPerIteration += "0";
			result = (	
				x == a.length()-1 ? iterationResult : addFunction( iterationResult, ( result.length() < iterationResult.length() ? "0" + result : result ) ));
		} return result;
	}
	private static String divideFunction(String a, String b) {
		return "test";
	}
}