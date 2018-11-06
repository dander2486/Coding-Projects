#include <iostream>
#include <iomanip>
#include <fstream>
#include <cmath>
using std::cout;
using std::cin;
using std::ofstream;
using std::setw;


//Daniel Sokic
//October 8, 2018

int gradNum; //Number of graduations
int frameNum; //Number of frames
double minx = -4; //Minimum X value
double maxx = 6; //Maximum X value
double miny = -12; //Minimum Y value
double maxy = 5; //Maximum Y value
double minz = 0; //Minimum Z value
double maxz = 6.28318530718; //Maximum Z value

/* This procedue receives the number of graduations and frames indicated by the user and uses
  them to initialize the necessary variables for calculations. [1a] the file name is stored as
  a character array so that at the end of a while loop cycle, [1b]another one may be created by
  incrementing the numbers by one. [2] The while loop continues until the required amount of
  frames are created or the z variable reaches its limit. The indicated file name is created or
  opened, and given the header data for a pgm file. [3] As the for loop increments through each
  location of the array, the x and y values are appropriately incremented and the values added, 
  then output to the created file. z is incremented each time a pgm file is completed to ensure
  the correct product. */


int main() {
	
	cout << "Input number of graduations: "; cin>> gradNum;
	cout << "Input number of desired frames: "; cin>> frameNum;
	double imgdisplay[gradNum*gradNum]; //The array used to store the greyscale data, size of graduation^2
	double differencex = (maxx-minx)/gradNum; //The amount x will increment after each column
	double differencey = (maxy-miny)/gradNum; //The amount y will increment after each row
	double differencez = (maxz-minz)/frameNum; //The amount z will increment after each created frame
	double z = minz; //Set to minimum to ensure proper incrementation
	char fileName[] = "anim0000.pgm"; //[1a]
	int fileNum = 0;
	
	while(z != maxz && fileNum != frameNum){ //[2]
		ofstream myFile;
		myFile.open(fileName);
	
		myFile << "P2 \n"<< gradNum << " " << gradNum << "\n255\n";
		
		double x = minx; //Set to minimum to ensure proper incrementation
		double y = miny; //Set to minimum to ensure proper incrementation
		int count = 1; //Used to track positioning in the array
	
		for(int i=0; i<gradNum*gradNum; i++){ //[3]
			double val = cos(z)*((0.5*sin(x))+(0.5*cos(y)));
			val = val + 1;
			int greyval = (int)(val*127.5);
			
			imgdisplay[i] = greyval;
				
			myFile << setw(3) << imgdisplay[i]<< " ";
			x += differencex;
			if(count%gradNum==0){
				myFile <<"\n";
				y += differencey;
				x = minx; //Set to minimum since a new row of columns will be traversed
				}
			count += 1;
		}
		
		myFile.close();
		z += differencez;
		fileNum += 1; //[1b]
		if(fileNum%10==0){
			fileName[6] += 1;
			fileName[7] = '0';
		}
		else if(fileNum%100==0){
			fileName[5] += 1;
			fileName[6] = '0';
			fileName[7] = '0';
		}
		else if(fileNum%1000==0){
			fileName[4] += 1;
			fileName[5] = '0';
			fileName[6] = '0';
			fileName[7] = '0';
		}
		else{
			fileName[7] += 1;
		}
	}
	
	return 0;
}