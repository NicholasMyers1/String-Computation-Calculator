#ifndef includesVoids_h
#define includesVoids_h
	#include <iostream>
	#include <windows.h>
	using namespace std;
	//unsigned __int64=18,446,744,073,709,551,615;  18446744073709551615
	//voids:
		void init();
		void mainProgram();
			void stringInputManipulation(string var1,string var2);
			string deleteVoidZerosAddDecimalNegativeSigns(string finalAns);
		void mathFunctions(unsigned __int64 functionType);
	//variables: 
		extern string calculationAnswer;
		extern bool init_;
	//classes: 
	class basicMathFunctions{
		private: 
			int baseMin=48,baseMax=57;//min 48 max 57; for the ascii numbers;
			string charToInsert="\0";
			bool enteredInitialFunction=false;
			void setPrivate(){
				baseMin=48,baseMax=57;
				charToInsert="\0";
			}
		public: 
			bool negative[2]={false,false};
			unsigned __int64 longestStringLength=0,shortestStringLength=0;
			bool decimalExists[2]={false,false};
			unsigned __int64 decimalPosition[2]={18446744073709551615,18446744073709551615};
			string userInput[2]={"\0","\0"};
			string finalCalculation="\0";
			int functionType=-1;
			string functionTypeUserInput="\0";
			int outcomeNegOrPos=-1;//-1=init; 0=positive; 1=negative;
			bool switchSign=false;
			int finalFunctionType=-1;
			void setPublic(){
				negative[0]=false,negative[1]=false;
				longestStringLength=0,shortestStringLength=0;
				decimalExists[0]=false,decimalExists[1]=false;
				decimalPosition[0]=18446744073709551615;decimalPosition[1]=18446744073709551615;
				userInput[0]="\0";userInput[1]="\0";
				finalCalculation="\0";
				functionType=-1;
				functionTypeUserInput="\0";
				outcomeNegOrPos=-1;
				switchSign=false;
				finalFunctionType=-1;
				setPrivate();
			}
			string add(string var1,string var2){
				if(outcomeNegOrPos==-1){
					if(negative[0]==false&&negative[1]==false||negative[0]==false&&negative[1]==true)outcomeNegOrPos=0;
					else outcomeNegOrPos=1;
					if(negative[0]==false&&negative[1]==false||negative[0]==true&&negative[1]==true){finalFunctionType=0;add(var1,var2);}
					else {finalFunctionType=1;subtract(var1,var2);}
				}
				else if(finalFunctionType==0||finalFunctionType==2){
					for(unsigned __int64 n=longestStringLength-1;n>=0;n--){
						charToInsert=baseMin+(((int)var1[n]-baseMin)+((int)var2[n]-baseMin));
						if((int)charToInsert[0]>baseMax){
							charToInsert=((int)charToInsert[0]-10);
							if(n>0)	var2[n-1]=(int)var2[n-1]+1;
							else{	finalCalculation.insert(0,charToInsert);finalCalculation.insert(0,"1");break;}
						}
						finalCalculation.insert(0,charToInsert);if(n==0)break;
					}
					if(finalFunctionType==0){finalCalculation=deleteVoidZerosAddDecimalNegativeSigns(finalCalculation);}
					return finalCalculation;
				}			
			}
			string subtract(string var1, string var2){
				if(outcomeNegOrPos==-1){
					if(	switchSign==false&&negative[0]==true&&negative[1]==false||switchSign==false&&negative[0]==true&&negative[1]==true||
						switchSign==true&&negative[0]==false&&negative[1]==true||switchSign==true&&negative[0]==false&&negative[1]==false
					)outcomeNegOrPos=1;
					if(	switchSign==false&&negative[0]==false&&negative[1]==true||switchSign==false&&negative[0]==false&&negative[1]==false||
						switchSign==true&&negative[0]==true&&negative[1]==true||switchSign==true&&negative[0]==true&&negative[1]==false
					)outcomeNegOrPos=0;
					if(negative[0]==false&&negative[1]==false||negative[0]==true&&negative[1]==true){finalFunctionType=1;subtract(var1,var2);}
					if(negative[0]==true&&negative[1]==false||negative[0]==false&&negative[1]==true){finalFunctionType=0;add(var1,var2);}
				}
				else if(finalFunctionType==1){
					for(unsigned __int64 n=longestStringLength-1;n>=0;n--){
						if((int)var1[n]<(int)var2[n]){
							if(n-1>=0)var1[n-1]=(int)var1[n-1]-1;
							var1[n]=(int)var1[n]+10;
						}
						charToInsert=baseMin+((int)var1[n]-(int)var2[n]);
						finalCalculation.insert(0,charToInsert);if(n==0)break;
					}
					finalCalculation=deleteVoidZerosAddDecimalNegativeSigns(finalCalculation);
					return finalCalculation;
				}
			}
			string multiply(string var1, string var2){
				string tmp="\0";
				string tempFinal="\0";
				int count=0;
				int zerosToInsert=0;
				if(outcomeNegOrPos==-1){
					finalFunctionType=2;
					if(negative[0]==false&&negative[1]==false||negative[0]==true&&negative[1]==true)outcomeNegOrPos=0;
					if(negative[0]==false&&negative[1]==true||negative[0]==true&&negative[1]==false)outcomeNegOrPos=1;
				}
				for(unsigned __int64 n=longestStringLength-1;n>=0;n--){
					if(zerosToInsert!=0)tmp.insert(0,zerosToInsert,'0');
					cout<<"inserted "<<zerosToInsert<<" zeros"<<"\n";
					for(unsigned __int64 m=longestStringLength-1;m>=0;m--){
						count=0;
						int loopShortestNTimes=((int)userInput[0][m]<=(int)userInput[1][n]?(int)userInput[0][m]-baseMin:(int)userInput[1][n]-baseMin);
						int addHighestNum=((int)userInput[0][m]>(int)userInput[1][n]?(int)userInput[0][m]-baseMin:(int)userInput[1][n]-baseMin);
						for(unsigned __int64 o=0;o<loopShortestNTimes;o++)charToInsert[0]+=addHighestNum;
					//	for(unsigned __int64 o=0;o<((int)userInput[0][m]<=(int)userInput[1][n]?(int)userInput[0][m]-baseMin:(int)userInput[1][n]-baseMin);o++)charToInsert[0]+=((int)userInput[0][m]>(int)userInput[1][n]?(int)userInput[0][m]-baseMin:(int)userInput[1][n]-baseMin);
					
						while((int)charToInsert[0]>9){charToInsert=(int)charToInsert[0]-10;count+=1;}						
						
						if((int)charToInsert[0]<1)charToInsert[0]=0;
						
						cout<<"multiplying: "<<userInput[1][n]<<" and "<<userInput[0][m]<<" times\n";
						cout<<count<<", "<<(char)(charToInsert[0]+48)<<"<--chartoinsert\n";
						
						if(m==longestStringLength-1){
							charToInsert=(int)charToInsert[0]+baseMin;
							tmp.insert(0,charToInsert);
						}
						else{
							if(charToInsert!=""&&tmp[0]!='0'){
								tmp[0]=(int)tmp[0]+(int)charToInsert[0];
								if((int)tmp[0]>baseMax){
									tmp[0]=(int)tmp[0]-10;
									count+=1;
								}
							}
							else{
								//tmp.insert(0,"0");//"0");
								charToInsert=(int)charToInsert[0]+baseMin;
								tmp.insert(0,charToInsert);
							}
							//else							tmp.insert(0,"1");
						}
					/*	else if(tmp[0]!='0'){
							tmp[0]=(int)tmp[0]+(int)charToInsert[0];
							if((int)tmp[0]>baseMax){
								tmp[0]=(int)tmp[0]-10;
								count+=1;
							}
						}*/
		/*				else if(charToInsert[0]!='0'){
							tmp.insert(0,"0");//(char)(int)tmp[0]+(int)charToInsert[0]);
							tmp[0]=(int)tmp[0]+(int)charToInsert[0];
							if((int)tmp[0]>baseMax){
								tmp[0]=(int)tmp[0]-10;
								count+=1;
							}
						}
		*/		


						if(count>0){charToInsert[0]=count+baseMin;tmp.insert(0,charToInsert);}
						charToInsert="\0";
cout<<"combining: "<<tmp<<"\n";

//						if(loopShortestNTimes==0&&m!=longestStringLength-1)tmp.insert(0,"0");
					//	if(((int)userInput[0][m]<=(int)userInput[1][n]?(int)userInput[0][m]-baseMin:(int)userInput[1][n]-baseMin))tmp.insert(0,"0");




						if(m==0)break;
					}
					if(tmp.length()>tempFinal.length())	tempFinal.insert(0,tmp.length()-tempFinal.length(),'0');
					longestStringLength=tmp.length();
cout<<tmp<<"<--final\n";
					tempFinal=add(tmp,tempFinal);
					longestStringLength=userInput[0].length();
					charToInsert="\0";
					finalCalculation="\0";
					tmp="\0";
					zerosToInsert+=1;
					if(n==0||n==longestStringLength-shortestStringLength)break;
				}
				finalCalculation=tempFinal;
				decimalPosition[0]=finalCalculation.length()-((var1.length()-decimalPosition[0])+(var1.length()-decimalPosition[0]))-1;
				finalCalculation=deleteVoidZerosAddDecimalNegativeSigns(finalCalculation);
				return finalCalculation;
			}
	};extern basicMathFunctions functionMath;
#endif 
