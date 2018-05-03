#include "includesVoids.h"
void mainProgram(){
	//get the mathematical operator: 
		while(functionMath.functionType==-1){
			cout<<"Welcome to the string computation program!\n";
			cout<<"Enter the operator for the function to perform:\n";
			cout<<"Addition: ( + ), Subtraction: ( - ), Multiplication: ( * )\nFunction: ";
				cin>>functionMath.functionTypeUserInput;
			if(functionMath.functionTypeUserInput.length()>1){
				system("CLS");
				cout<<"--- Please enter a valid operator! ---\n";
				continue;
			}
			switch((char)functionMath.functionTypeUserInput[0]){
				case '+':	functionMath.functionType=0;break;
				case '-':	functionMath.functionType=1;break;
				case '*':	functionMath.functionType=2;break;
				default:	system("CLS");cout<<"--- Please enter a valid operator! ---\n";break;
			}		
		}
	//get the user input for number one and number two: 
		cout<<"Enter a number: ";
			cin>>functionMath.userInput[0];
		cout<<"Enter another number: ";
			cin>>functionMath.userInput[1];
	//cout the numbers that the user inputted with the correct function:
		cout<<functionMath.userInput[0]<<" "<<functionMath.functionTypeUserInput<<" "<<functionMath.userInput[1]<<" =\n";
	//calculate the function: 
		mathFunctions(functionMath.functionType);//fill with the math function type;
	//output the answer: 
		cout<<calculationAnswer<<"\n";
	//reset the window and set init value to true:  
		system("PAUSE");system("CLS");init_=true;init();		
}
