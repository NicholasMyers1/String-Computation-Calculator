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
		if( x.equals("0") || x.length() == 0 || x.equals("-") || x.equals(".") ) return "0";
		boolean foundNegative = false;
		boolean foundDecimal = false;
		String strNum = "";
		for(int n=0;n<x.length();n++) { 
			// remove any out of place decimals, negative symbols, and remove any non-numbers that are not out of place decimals or negative symbols;
			if( x.charAt(n) == '.' ) {
				if( !foundNegative ) foundNegative = true;
				if( !foundDecimal ) {
					strNum += x.charAt(n);
					foundDecimal = true;
				} continue;
			}
			if( x.charAt(n) == '-' ) {
				if( !foundNegative ) {
					strNum += x.charAt(n);
					foundNegative = true;
				} continue;
			}
			if( x.charAt(n) >= 48 && x.charAt(n) <= 57 ) { strNum += x.charAt(n); }
		}
		// add or remove 0's in appropriate places: 
		if ( strNum.length() == 0 || strNum.equals("-") || strNum.equals(".") || strNum.equals("-.") ) return "0";
		if ( strNum.charAt(strNum.length()-1) == '.' ) strNum += "0"; 
		if ( strNum.charAt(0) == '.' ) strNum = "0" + strNum;
		else if ( strNum.charAt(0) == '-' && strNum.charAt(1) == '.' ) strNum = strNum.substring(0,1) + "0" + strNum.substring(1,strNum.length());
		boolean hasPassedFirstNonZero = false;
		boolean decimalExists = false;
		String temp = ( strNum.charAt(0) == '-' ? "-" : "" );
		for( int n=temp.length();n<strNum.length();n++) { // remove any zero before any non-zero and ignore the negative sign;
			if( strNum.charAt(n) == '.' ) {
				decimalExists = true;
				if( temp.equals("-") || temp.length() == 0 ) temp += "0";
			}
			if( strNum.charAt(n) != '0' || hasPassedFirstNonZero ) {
				hasPassedFirstNonZero = true;
				temp += strNum.charAt(n);
			}
		}
		// return "0" if not a valid strNum;
		if( temp.equals("-.0") || temp.equals("-0.0") || temp.equals(".0") || temp.equals("0.0") || temp.length() == 0 ) return "0";
		// set temp to strNum;
		strNum = temp;
		// remove any non-valid zero's after the decimal if one exists: 
		if( decimalExists ) { // remove any zero's after the final non-zero number after the decimal point;
			int positionToRemoveExtraZeros = 0;
			for(int n=strNum.length()-1;n>=0;n--) if( strNum.charAt(n) != '0' ) { positionToRemoveExtraZeros = n+1; break; }
			strNum = strNum.substring(0,positionToRemoveExtraZeros);
		}
		if( strNum.length() > 1 && strNum.charAt(strNum.length()-1) == '.' ) return strNum.substring(0,strNum.length()-1);
		if( strNum.equals("-") ) return "0";
		return strNum;
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
		return new StringNumber(ret);//}
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
	// MULT: 
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
		// return 0 if both strings are empty;
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
				// while each first digit is the same, remove the first digit from both numbers and set the decimal length to n-1: 
				while( n[0].length() > 0 && n[1].length() > 0 && n[0].charAt(0) == n[1].charAt(0) )
					for( int x=0;x<n.length;x++ ) { n[x] = n[x].substring(1, n[x].length()); decimalPosition[x] -= 1; }
				// while each last digit is the same, remove the last digit from both numbers: 
				while( n[0].length() > 0 && n[1].length() > 0 && n[0].charAt(n[0].length()-1) == n[1].charAt(n[1].length()-1) )
				for( int x=0;x<n.length;x++ ) { n[x] = n[x].substring(0, n[x].length()-1); }
				if( a.equals("0") ) return b; if( b.equals("0") ) return a;//break;//
				if ( n[0].length() == 0 && n[1].length() == 0 ) return "0";
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
		// add back in decimal at position from length - decimalPosition;
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
			if( top < bottom ) { top += 10 ; borrow = true; }
			else borrow = false;
			result = String.valueOf( ( top-bottom > 9 ? top-bottom-10 : top-bottom ) ) + result;
		} return result;
	}
	private static String multiplyFunction(String a, String b) { // perform the multiplication function: 
		String result = "";
		String zerosToAddPerIteration = ""; // zeros must be added to the end of the number after each iteration because multiplication;
		for( int x=a.length()-1; x >= 0; x-- ) {
			String resultToAddToResult = "";
			String carry = "0";
			for( int y=a.length()-1; y >= 0; y-- ) {
				String innerResult = "0";
				for( int z=0;z < ( a.charAt(y)>b.charAt(x) ? Character.getNumericValue(b.charAt(x)) : Character.getNumericValue(a.charAt(y)) );z++ ){
					String tempA = Character.toString( ( a.charAt(y) > b.charAt(x) ? a.charAt(y) : b.charAt(x) ) );
					while(tempA.length()<innerResult.length()) tempA = "0" + tempA;
					innerResult = addFunction( innerResult, tempA );
				}
				while(carry.length()<innerResult.length()) carry = "0" + carry;
				innerResult = addFunction( innerResult, carry);
				carry = ( innerResult.length() > 1 ? Character.toString( innerResult.charAt(0) ) : "0" );
				resultToAddToResult = innerResult.charAt(innerResult.length()-1) + resultToAddToResult;
			}
			resultToAddToResult = carry + resultToAddToResult + zerosToAddPerIteration;
			zerosToAddPerIteration += "0";
			while( result.length() < resultToAddToResult.length() ) result = "0" + result;
			result = addFunction( resultToAddToResult, result );
		} return result;
	}
	private static String divideFunction(String a, String b) {
		return "test";
	}
}