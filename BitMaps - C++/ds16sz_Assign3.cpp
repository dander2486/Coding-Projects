#include <iostream>
#include <cmath>

//Daniel Sokic 6164545

int choice; //The function choice
int graduations; //Number of graduations per line
int bitORval; //Display as bitmap or just values
double minx = -4; //Minimum X value
double maxx = 6; //Maximum X value
double miny = -12; //Minimum Y value
double maxy = 5; //Maximum Y value

/*
	This procedure displays the function, indicated by the user eariler, in a bitmap contained within a character array. This is done partly by incrementing 
	through each value of x and y by the difference calculated using the mins and maxs divided by the number of graduations. As they increment, x and y are 
	used in the function to calculate a value. This value is identified as positive or negative, if positive, the char array is given an 'O' at the respective 
	position, if negative, the char array is given an 'X' at the respective position. The array's contents are streamed as they are added, until completion.
*/

void bitmap(){
	
	double differencex = (maxx-minx)/graduations;
	double differencey = (maxy-miny)/graduations;
	double x = minx;
	double y = miny;
	int count = 1;
	char display[(int)pow(graduations,2)];
	
	switch(choice) {
		case(1):
			for(int i=0; i<sizeof(display); i++){
				double val = sin(x)*cos(y);
				
				if(val > 0){
					display[i] = 'O';
				}
				else{
					display[i] = 'X';
				}
				std::cout<<display[i];
				x += differencex;
				if(count%graduations==0){
					std::cout<<"\n";
					y += differencey;
					x = minx;
				}
				count += 1;
			}
			break;
		case(2):
			for(int i=0; i<sizeof(display); i++){
				double val = sin(x)+(pow(cos(y/2.0),2.0))-(x/y);
				
				if(val > 0){
					display[i] = 'O';
				}
				else{
					display[i] = 'X';
				}
				std::cout<<display[i];
				x += differencex;
				if(count%graduations==0){
					std::cout<<"\n";
					y += differencey;
					x = minx;
				}
				count += 1;
			}
			break;
		case(3):
			for(int i=0; i<sizeof(display); i++){
				double val = (0.5*sin(x))+(0.5*cos(y));
				
				if(val > 0){
					display[i] = 'O';
				}
				else{
					display[i] = 'X';
				}
				std::cout<<display[i];
				x += differencex;
				if(count%graduations==0){
					std::cout<<"\n";
					y += differencey;
					x = minx;
				}
				count += 1;
			}
			break;
		case(4):
			for(int i=0; i<sizeof(display); i++){
				double val = (0.5*sin(x))+(x*cos((y*3.0)));
				
				if(val > 0){
					display[i] = 'O';
				}
				else{
					display[i] = 'X';
				}
				std::cout<<display[i];
				x += differencex;
				if(count%graduations==0){
					std::cout<<"\n";
					y += differencey;
					x = minx;
				}
				count += 1;
			}
			break;
	}
		
	
}

/*
	This procedure displays the function's, indicated by the user eariler, values contained within a double array. This is done partly by incrementing 
	through each value of x and y by the difference calculated using the mins and maxs divided by the number of graduations. As they increment, x and y are 
	used in the function to calculate a value. This value is then assigned the respective position within the array, then streamed out. This repeats until completion.
*/

void values(){
	
	double differencex = (maxx-minx)/graduations;
	double differencey = (maxy-miny)/graduations;
	double x = minx;
	double y = miny;
	int count = 1;
	double display[(int)pow(graduations,2)];
	
	switch(choice) {
		case(1):
			for(int i=0; i<sizeof(display); i++){
				double val = sin(x)*cos(y);
				
				display[i] = val;
				
				std::cout<<display[i];
				x += differencex;
				if(count%graduations==0){
					std::cout<<"\n";
					y += differencey;
					x = minx;
				}
				count += 1;
			}
			break;
		case(2):
			for(int i=0; i<sizeof(display); i++){
				double val = sin(x)+(pow(cos(y/2.0),2.0))-(x/y);
				
				display[i] = val;
				
				std::cout<<display[i];
				x += differencex;
				if(count%graduations==0){
					std::cout<<"\n";
					y += differencey;
					x = minx;
				}
				count += 1;
			}
			break;
		case(3):
			for(int i=0; i<sizeof(display); i++){
				double val = (0.5*sin(x))+(0.5*cos(y));
				
				display[i] = val;
				
				std::cout<<display[i];
				x += differencex;
				if(count%graduations==0){
					std::cout<<"\n";
					y += differencey;
					x = minx;
				}
				count += 1;
			}
			break;
		case(4):
			for(int i=0; i<sizeof(display); i++){
				double val = (0.5*sin(x))+(x*cos((y*3.0)));
				
				display[i] = val;
				
				std::cout<<display[i];
				x += differencex;
				if(count%graduations==0){
					std::cout<<"\n";
					y += differencey;
					x = minx;
				}
				count += 1;
			}
			break;
	}
		
	
}

/*
	This function prompts the user for their choice of function, graduations per line, and whether to display the function as a bitmap or just the values.
	Then, depending on whether bitmap or values was selected, runs the indicated display function. Afterwards, the function loops until the user decides
	to quit.
*/

int main() {
	
	do{
		std::cerr<< "Select your function:\n1. sin(x)cos(y) \n2. sin(x)+cos^2(y/2)-x/y \n3. 1/2 sin(x) + 1/2 cos(y) \n4. 1/2 sin(x) + xcos(3y) \n0. Quit\n";
		std::cin>>choice;
	
		if(choice == 0){
			return 0;
		}
		
		do{
			std::cerr<< "Number of graduations per axis: "; std::cin>>graduations;
		} while (graduations <= 0);
		
		do{
			std::cerr<< "(0) Bitmap or (1) Values? "; std::cin>>bitORval;
		} while (bitORval != 0 && bitORval != 1);
		
		if( bitORval ){
			values();
		}
		else{
			bitmap();
		}
	} while(true);
		

}