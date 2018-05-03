#include "includesVoids.h"
void mathFunctions(unsigned __int64 functionType){
	//manipulate input strings to correct format: 
		stringInputManipulation(functionMath.userInput[0],functionMath.userInput[1]);
	switch(functionType){
		case 0: functionMath.add(functionMath.userInput[0],functionMath.userInput[1]);break;
		case 1: functionMath.subtract(functionMath.userInput[0],functionMath.userInput[1]);break;
		case 2: functionMath.multiply(functionMath.userInput[0],functionMath.userInput[1]);break;
		case 3: break;
		case 4: break;
	}
	calculationAnswer=functionMath.finalCalculation;
}
