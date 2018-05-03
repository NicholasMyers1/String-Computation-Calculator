#include "includesVoids.h"
void stringInputManipulation(string var1,string var2){//delete unncessary characters, unncessary 0's, decimal points, and negative signs. 
	//find if negative and erase negative sign: 
		if(var1[0]=='-'){functionMath.negative[0]=true;var1.erase(0,1);}
		if(var2[0]=='-'){functionMath.negative[1]=true;var2.erase(0,1);}
	//set longest/shortest length: 
		functionMath.longestStringLength=	(var1.length()>=var2.length()?var1.length():var2.length());
		functionMath.shortestStringLength=	(var1.length()<	var2.length()?var1.length():var2.length());
		for(unsigned __int64 n=0;n<functionMath.longestStringLength;n++){
			bool eraseChars[2]={false,false};//erase characters that are not allowed in a number;
			//find decimal and erase unncessary characters: 
				if(var1[n]=='.'&&functionMath.decimalExists[0]==false&&n<var1.length()){functionMath.decimalExists[0]=true;functionMath.decimalPosition[0]=n;}
				else if(var1[n]<(int)48&&functionMath.decimalPosition[0]!=n&&n<var1.length()||var1[n]>(int)57&&n<var1.length()||var1[0]=='0'){var1.erase(var1.begin()+n);eraseChars[0]=true;}
				if(var2[n]=='.'&&functionMath.decimalExists[1]==false&&n<var2.length()){functionMath.decimalExists[1]=true;functionMath.decimalPosition[1]=n;}
				else if(var2[n]<(int)48&&functionMath.decimalPosition[1]!=n&&n<var2.length()||var2[n]>(int)57&&n<var2.length()||var2[0]=='0'){var2.erase(var2.begin()+n);eraseChars[1]=true;}
			if(eraseChars[0]==true||eraseChars[1]==true){n=-1;}
		}
	//insert the decimal if there is none: 
		if(functionMath.decimalExists[0]==false){var1.insert(var1.length(),".");functionMath.decimalPosition[0]=var1.length()-1;}
		if(functionMath.decimalExists[1]==false){var2.insert(var2.length(),".");functionMath.decimalPosition[1]=var2.length()-1;}
	//make both strings the same length with adding 0's before and after the decimal point in the appropriate positions: 
		if(functionMath.decimalPosition[1]>functionMath.decimalPosition[0]/*functionMath.decimalPosition[1]-functionMath.decimalPosition[0]>0*/){
			var1.insert(0,functionMath.decimalPosition[1]-functionMath.decimalPosition[0],'0');		functionMath.decimalPosition[0]=functionMath.decimalPosition[1];
		}
		if(var2.length()-functionMath.decimalPosition[1]>var1.length()-functionMath.decimalPosition[0])
			var1.insert(var1.length(),(var2.length()-functionMath.decimalPosition[1])-(var1.length()-functionMath.decimalPosition[0]),'0');
		if(functionMath.decimalPosition[0]>functionMath.decimalPosition[1]/*functionMath.decimalPosition[0]-functionMath.decimalPosition[1]>0*/){
			var2.insert(0,functionMath.decimalPosition[0]-functionMath.decimalPosition[1],'0');		functionMath.decimalPosition[1]=functionMath.decimalPosition[0];
		}
		if(var1.length()-functionMath.decimalPosition[0]>var2.length()-functionMath.decimalPosition[1])
			var2.insert(var2.length(),(var1.length()-functionMath.decimalPosition[0])-(var2.length()-functionMath.decimalPosition[1]),'0');
	//erase the decimals in the strings leaving only the numbers, and if the string is empty, add a 0 as a placeholder: 
		var1.erase(functionMath.decimalPosition[0],1);var2.erase(functionMath.decimalPosition[1],1);
		if(var1.length()==0)var1.insert(0,"0");		if(var2.length()==0)var2.insert(0,"0");
	//set equal string inputs: 
		functionMath.userInput[0]=var1;		functionMath.userInput[1]=var2;
	//set longest/shortest length after unnecessary characters removed: 
		functionMath.longestStringLength=var1.length();	//	functionMath.shortestStringLength=functionMath.longestStringLength;
	//switch smallest number to bottom if necessay: 
		string copyTempUserInput="\0";
		bool copyTempNegative=false;
		for(unsigned __int64 n=0;n<functionMath.longestStringLength;n++){
			if((int)functionMath.userInput[0][n]==(int)functionMath.userInput[1][n])continue;
			if((int)functionMath.userInput[0][n]<(int)functionMath.userInput[1][n]){//swap: 
				copyTempUserInput=functionMath.userInput[1];	functionMath.userInput[1]=functionMath.userInput[0];	functionMath.userInput[0]=copyTempUserInput;
				copyTempNegative=functionMath.negative[1];		functionMath.negative[1]=functionMath.negative[0];		functionMath.negative[0]=copyTempNegative;
				functionMath.switchSign=true;
				break;
			}
			if((int)functionMath.userInput[0][n]>(int)functionMath.userInput[1][n])break;
		}
}
string deleteVoidZerosAddDecimalNegativeSigns(string finalAns){
	//insert negatve sign if necessary and decimal point: 
		finalAns.insert(functionMath.decimalPosition[0]+((int)finalAns.length()>functionMath.longestStringLength?true:false),".");//insert decimal;
		if(functionMath.outcomeNegOrPos==1)finalAns.insert(0,"-");//insert negative;
	for(unsigned __int64 n=0;n<finalAns.length();n++){
		if(finalAns[n]=='-')continue;
		if(finalAns[n]=='0'){finalAns.erase(n,1);if(n-1>=0)n-=1;continue;}
		else break;
	}
	if(finalAns[0]=='.')finalAns.insert(0,"0");
	else if(finalAns[0]=='-'&&finalAns[1]=='.')finalAns.insert(1,"0");
	if(finalAns[finalAns.length()-1]=='.')finalAns.insert(finalAns.length(),"0");
	if(finalAns[0]=='-'&&finalAns[1]=='0'&&finalAns[2]=='.'&&finalAns[3]=='0'&&finalAns.length()==4||finalAns[0]=='-'&&finalAns[1]=='-')finalAns.erase(0,1);
	return finalAns;
}
