import java.util.List;

abstract class StringMath {	
	// enum for operations:
	enum StringMathOperation{
		ADD, SUB, MULT, DIV, 
	}
	// static methods:
	public static String toStringNumber(int x) { return ( toStringNumber(Integer.toString(x)) ); }
	public static String toStringNumber(double x) { return (toStringNumber(Double.toString(x))); }
	public static String toStringNumber(float x) { return (toStringNumber(Float.toString(x))); }
	public static String toStringNumber(boolean x) { return ( x ? "1" : "0" ); }
	public static String toStringNumber(String x) {
		// StringNumbers have no leading or trailing zeros, no non-number characters, except a decimal point and or negative symbol if necessary as of now;
		String stringNumberTemp = "";
		boolean isNegative = false;
		boolean isDecimal = false;
		// Create a valid StringNumber;
		for(int n=0;n<x.length();n++) {
			if( x.charAt(n) == '-' && !isNegative && !isDecimal )
				isNegative = true;
			else if( x.charAt(n) == '.' && !isDecimal ) {
				isDecimal = true;
				stringNumberTemp += ".";
			} else if( x.charAt(n) >= 48 && x.charAt(n) <= 57 ) 
				stringNumberTemp += x.charAt(n);
		}
		// Remove unnecessary zeros: then add in the appropriate zeros and negative sign if necessary;
		String returnStringNumber = stringNumberTemp;
		if( returnStringNumber.length() > 1 ) {
			for( int n = 0; stringNumberTemp.charAt(n) == '0'; n++ ) 
				returnStringNumber = returnStringNumber.substring(1, returnStringNumber.length());
			if( isDecimal )
				for( int n = stringNumberTemp.length()-1; n >= 0 && stringNumberTemp.charAt(n) == '0' ; n-- )
					returnStringNumber = returnStringNumber.substring(0, returnStringNumber.length()-1);
			if( returnStringNumber.charAt(0) == '.' ) returnStringNumber = "0" + returnStringNumber;
			if( returnStringNumber.charAt(returnStringNumber.length()-1) == '.' ) returnStringNumber += "0";
		} // could be '', '.', '-', 0.0, -0.0, 0.x, -0.x, x.0, -x.0, x.x, -x.x,x x, -x;
		if( isNegative ) returnStringNumber = "-" + returnStringNumber;
		return (
			returnStringNumber.length() == 0 || returnStringNumber.equals(".") || returnStringNumber.equals("-") || 
			returnStringNumber.equals("0.0") || returnStringNumber.equals("-0.0") || returnStringNumber.equals("-.") ? "0" : returnStringNumber );
	}
	
	// public static methods: 
	// ADD: 
	public static String add( String... x ) {
		String ret = x[0];
		for(int n=1;n<x.length;n++) ret = parseToPerformOperation( ret, x[n], StringMathOperation.ADD );
		return ret;
	} 
	public static StringNumber add( StringNumber... x ) {
		String ret = x[0].get();
		for(int n=1;n<x.length;n++) ret = parseToPerformOperation( ret, x[n].get(), StringMathOperation.ADD );
		return new StringNumber(ret);
	} @SafeVarargs public static StringNumber add( List<Object>...x ) {
		String ret = ( x[0].get(0) instanceof Boolean ? ( (Boolean)x[0].get(0) ?  "1" : "0" ) : new StringNumber( x[0].get(0).toString() ).get() );
		for(int l=0;l<x.length;l++)
			for(int n=1;n<x[l].size();n++)
				ret = parseToPerformOperation( ret, 
					( x[l].get(n) instanceof Boolean ? ( (Boolean)x[l].get(n) ?  "1" : "0" ) : new StringNumber( x[l].get(n).toString() ).get() ),
					StringMathOperation.ADD );
		return new StringNumber(ret);
	}
	// SUB: 
	public static String sub( String... x ) {
		String ret = x[0];
		for(int n=1;n<x.length;n++) ret = parseToPerformOperation( ret, x[n], StringMathOperation.SUB );
		return ret;
	} public static StringNumber sub( StringNumber... x ) {
		String ret = x[0].get();
		for(int n=1;n<x.length;n++) ret = parseToPerformOperation( ret, x[n].get(), StringMathOperation.SUB );
		return new StringNumber(ret);
	}
	// MULT: 
	public static String mult( String... x ) {
		String ret = x[0];
		for(int n=0;n<x.length;n++) if(toStringNumber(x[n]).equals("0")) return "0";
		for(int n=1;n<x.length;n++) ret = parseToPerformOperation( ret, x[n], StringMathOperation.MULT );
		return ret;
	}
	public static String div( String... x ) {
		String ret = x[0];
		for(int n=0;n<x.length;n++) if(toStringNumber(x[n]).equals("0")) return "0";
		for(int n=1;n<x.length;n++) ret = parseToPerformOperation( ret, x[n], StringMathOperation.DIV );
		return ret;
	}
	// local methods: 
	private static String parseToPerformOperation(String a, String b, StringMathOperation operation) {
		a = toStringNumber(a);
		b = toStringNumber(b);
		// set n to each string for simplicity;
		String n[] = {a,b};
		// get signs and remove: 
		boolean isNegative[] = { n[0].charAt(0) == '-', n[1].charAt(0) == '-' };
		for(int x=0;x<n.length;x++) if( isNegative[x] ) n[x] = n[x].substring(1,n[x].length());
		// get decimal point positions;
		boolean decimalExists = false;
		int decimalPosition[] = { 0, 0 };
		for(int x=0;x<n.length;x++)
			for(int y=0;y<n[x].length();y++)
				if( n[x].charAt(y) == '.' || y == n[x].length()-1 ) { decimalPosition[x] = ( y==n[x].length()-1?y+1:y); decimalExists = true; break; }
		// add in decimal point if necessary;
		for(int x=0;x<decimalPosition.length;x++) if( decimalPosition[x] == -1 ) { n[x] += "."; decimalPosition[x] = n[x].length()-1; }
		// set the decimal length from the ends of both numbers to the decimal position for multiplication;
		int decimalPositionForMultiplication = ( n[0].length()-decimalPosition[0]-1 ) + ( n[1].length()-decimalPosition[1]-1 ) ;
		// set lengths equal left of decimal with zeros;
		int addZerosTo = ( decimalPosition[0] > decimalPosition[1] ? 1 : decimalPosition[1] > decimalPosition[0] ? 0 : -1 );
		if( addZerosTo > -1 ) {
			for( int x=decimalPosition[addZerosTo];x<decimalPosition[ (addZerosTo == 0 ? 1 : 0 ) ];x++ )
				n[addZerosTo] = "0" + n[addZerosTo];
			decimalPosition[addZerosTo] = decimalPosition[ (addZerosTo == 0 ? 1 : 0 ) ];
		}
		// set lengths equal right of decimal with zeros;
		int afterDecimal[] = { n[0].length()-decimalPosition[0], n[1].length()-decimalPosition[1] };
		addZerosTo = (	afterDecimal[0] > afterDecimal[1] ? 1 : afterDecimal[1] > afterDecimal[0] ? 0 : -1 );
		if( addZerosTo > -1 ) {
			for( int x=afterDecimal[addZerosTo];x<afterDecimal[ (addZerosTo == 0 ? 1 : 0 ) ];x++ ) {
				n[addZerosTo] += "0";
				decimalPositionForMultiplication += 1;
			}
		}
		// remove decimal;
		for(int x=0;x<n.length;x++)
			if( decimalPosition[x] != n[x].length()) 
				n[x] = n[x].substring( 0, decimalPosition[x] ) + n[x].substring( decimalPosition[x]+1, n[x].length() );
		// return 0 if necessary: 
		if ( n[0].length() == 0 && n[1].length() == 0 ) return "0";
		// do the function: 
		String result = "";
		/*	SUB::: --- = SUB; +-- = ADD; --+ = ADD; +-+ = SUB;
			ADD::: -+- = ADD; ++- = SUB; -++ = SUB; +++ = ADD; 
			MULT::: ADD N TIMES;
			If subtracting, swap so the smaller number is on top/first; */
		boolean isOutputNegative = true;
		boolean isTopNumberSmaller = ( n[0].charAt(0) < n[1].charAt(0) );
		switch( operation ) {
			case ADD: {
				if( a.equals("0") ) return b; if( b.equals("0") ) return a;//break;//
				result = (	isNegative[0] && isNegative[1] || !isNegative[0] && !isNegative[1] ? 
								addFunction( n[0], n[1] ) : isTopNumberSmaller ? 
									subtractFunction( n[1], n[0] ) : subtractFunction( n[0], n[1] ) );
				if( !isNegative[0] && !isNegative[1] || 
					isTopNumberSmaller && isNegative[0] && !isNegative[1] || 
					!isTopNumberSmaller && !isNegative[0] && isNegative[1] ) isOutputNegative = false;
			} break;
			case SUB: {
				// while each first/last digit is the same, remove the first/last digit from both numbers and set the decimal length to n-1: 
				while( n[0].length() > 0 && n[1].length() > 0 && n[0].charAt(0) == n[1].charAt(0) )
					for( int x=0;x<n.length;x++ ) { n[x] = n[x].substring(1, n[x].length()); decimalPosition[x] -= 1; }
				while( n[0].length() > 0 && n[1].length() > 0 && n[0].charAt(n[0].length()-1) == n[1].charAt(n[1].length()-1) )
					for( int x=0;x<n.length;x++ ) { n[x] = n[x].substring(0, n[x].length()-1); }
				if ( n[0].length() == 0 && n[1].length() == 0 ) return "0";
				if( a.equals("0") ) return b; if( b.equals("0") ) return a;
				isTopNumberSmaller = ( n[0].charAt(0) < n[1].charAt(0) );
				result = (	isNegative[0] && isNegative[1] || !isNegative[0] && !isNegative[1] ? 
								( isTopNumberSmaller ? subtractFunction( n[1], n[0] ) : subtractFunction( n[0], n[1] ) ): 
									addFunction( n[0], n[1] ) );
				if( !isNegative[0] && isNegative[1] || 
					isTopNumberSmaller && isNegative[0] && isNegative[1] || 
					!isTopNumberSmaller && !isNegative[0] && !isNegative[1] ) isOutputNegative = false;
			} break;
			case MULT: {
				result = multiplyFunction( n[0], n[1] );
				isOutputNegative = ( isNegative[0] && !isNegative[1] || !isNegative[0] && isNegative[1] );
				if( decimalPositionForMultiplication >= 0 && decimalExists ) 
					result =	result.substring( 0,result.length()-decimalPositionForMultiplication ) + "." +
								result.substring( result.length()-decimalPositionForMultiplication,result.length() );
				decimalExists = false;
			} break;
			case DIV: {
				result = divideFunction( n[0], n[1] );
				isOutputNegative = ( isNegative[0] && !isNegative[1] || !isNegative[0] && isNegative[1] );
			}
		}
		// add in the decimal if necessary: 
		if( decimalExists ) {
			if( decimalPosition[0] < 0 ) {
				for( int x=decimalPosition[0];x<0;x++ )
					result = "0" + result;
				result = "0." + result;
			} else if( decimalPosition[0] != result.length() ) {
				int decimalPositionNew = decimalPosition[0] + ( result.charAt(0) == '1' && result.length() > n[0].length() ? 1 : 0 );
				result = result.substring( 0, decimalPositionNew ) + "." + result.substring(decimalPositionNew, result.length());
			}
		}
		// add in negative if necessary: 
		if( isOutputNegative ) result = "-" + result;
		return toStringNumber(result); // return a valid string number result;
	}
	private static String addFunction(String a, String b) { // perform the add function: 
		String result = "";
		boolean carry = false;
		for( int x= a.length()-1; x >= 0; x-- ) {
			int r = ( Character.getNumericValue( a.charAt(x) ) + Character.getNumericValue( b.charAt(x) ) ) + ( carry ? 1 : 0 );
			carry = ( r > 9 ? true : false );
			if( r > 9 ) r -= 10;
			result = String.valueOf(r) + result;
		} if( carry ) result = "1" + result;
		return result;
	}
	private static String subtractFunction(String a, String b) { // perform the subtract function:
		String result = "";
		boolean borrow = false;
		for( int x= a.length()-1; x >= 0; x-- ) {
			int top = Character.getNumericValue( a.charAt(x) ) + ( borrow ? -1 : 0 );
			int bottom = Character.getNumericValue( b.charAt(x) );
//			if( a.charAt(x) == b.charAt(x) ) { result = "0" + result; borrow = false; continue;}
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
				iterationResult = innerResult.charAt(innerResult.length()-1) + iterationResult;
			}
			iterationResult = carry + iterationResult + zerosToAddPerIteration;
			zerosToAddPerIteration += "0";
			result = (	x == a.length()-1 ? 
							iterationResult : addFunction( iterationResult, ( result.length() < iterationResult.length() ? "0" + result : result ) ));
		} return result;
	}
	private static String divideFunction(String a, String b) {
		return "test";
	}
}