#include "Set.h"
#include <iostream> 

//Daniel Sokic

//Test all member functions in the Set class, along with user input
int main() {
	
	Set setH(6),setL(10);
	setH = setH + 1;
	setH = setH + 3;
	setH = setH + 5;
	setL = setL + 9;
	setL = setL + 8;
	setL = setL + 7;
	setL = setL + 1;
	setL = setL + 3;
	setL = setL + 5;
	std::cout<<"setH = " << setH << " with capacity 6"<<std::endl;
	std::cout<<"setL = " << setL << " with capacity 10"<<std::endl;
	std::cout<<"setH + setL = " << (setH + setL) <<std::endl;
	std::cout<<"setH + 2 = " << (setH + 2) <<std::endl;
	std::cout<<"setL - setH = " << (setL - setH) <<std::endl;
	std::cout<<"setL - 8 = " << (setL - 8) <<std::endl;
	std::cout<<"setH ^ setL = " << (setH ^ setL) <<std::endl;
	std::cout<<"setH ^ 1 = " << (setH ^ 1) <<std::endl;
	std::cout<<"~setL = " << (~setL) <<std::endl;
	std::cout<<"Is setH <= setL? " << ((setH<=setL)?"YES":"NO") <<std::endl;
	std::cout<<"Is setH < setL? " << ((setH<setL)?"YES":"NO") <<std::endl;
	std::cout<<"Is setH >= setL? " << ((setH>=setL)?"YES":"NO") <<std::endl;
	std::cout<<"Is setH > setL? " << ((setH>setL)?"YES":"NO") <<std::endl;
	std::cout<<"Does setH == setL? " << ((setH==setL)?"YES":"NO") <<std::endl;
	std::cout<<"Does setH != setL? " << ((setH!=setL)?"YES":"NO") <<std::endl;
	std::cout<<"Is setH Empty? " << ((!setH)?"YES":"NO") <<std::endl;
	std::cout<<"Cardinality of SetL: " << setL() <<std::endl;
	std::cout<<"Is 7 in SetH? " << ((setH[7])?"YES":"NO") <<std::endl;
	std::cout<<"Is 7 in SetL? " << ((setL[7])?"YES":"NO") <<std::endl;
	
	int capacity;
	std::cout<<"Indicate Capacity (0-255): "; std::cin>>capacity;
	if(capacity <= 255 && capacity >= 0){
		Set newset(capacity);
		std::cout<<"Set with all possible values with given capacity: " << +newset << std::endl;
		std::cout<<"Empty Set with same capacity: " << -newset << std::endl;
	}
	else{
		std::cout<<"INVALID!! Moving on..." << std::endl;
	}
	
	std::cout<<"I/O Test with syntax 'A: {a,b,...,z}' :" << std::endl;
	Set s1(10),s2(6),s3(3),s4;
	std::cout<<"First set ((x,y,z)): ";
	std::cin >> s1;
	std::cout<<"A: "<<s1<<std::endl;
	std::cout<<"Capacity A: 10" << std::endl;
	std::cout<<"Second set: ";
	std::cin>>s2;
	std::cout<<"B: "<<s2<<std::endl;
	std::cout<<"Capacity B: 6" << std::endl;
	std::cout<<"Third set: ";
	std::cin>>s3;
	std::cout<<"C: "<<s3<<std::endl;
	std::cout<<"Capacity C: 3" << std::endl;
	std::cout<<"Fourth set: ";
	std::cin>>s4;
	std::cout<<"D: "<<s4<<std::endl;
	std::cout<<"Capacity D: Null" << std::endl;
	std::cout<<"(B\\(U\\A+D))^C: "<<((s2-((~s1)+s4))^s3)<<std::endl;
}
	