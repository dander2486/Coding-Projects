#include "Set.h"

//Daniel Sokic

Set::Set(){
	this->capacity = 255; //intialize set with capacity at 255
	for(int i=0; i<255; i++){
		elements[i] = false;
	}
}

Set::Set(short capacity){
	this->capacity = capacity; //initialize set with given capacity
	for(int i=0; i<255; i++){
		elements[i] = false;
	}
}

Set Set::operator+(const Set &other) const{ //Return union of original and other set
	short firstC = getCapacity();
	short secondC = other.getCapacity();
	short max = (firstC<secondC)?secondC:firstC; //take max capacity of before and after the operator
	Set newset(max); //new set has max capacity
	
	for(int i=0; i<max; i++){
		if(elements[i] || other.elements[i]){ //if either are true, add to the new set
			newset.elements[i] = true;
		}
	}
	
	return newset;
}

Set Set::operator+(const int &other) const{ //Add element defined after the operator
	Set newset(getCapacity()); 
	
	for(int i=0; i<getCapacity(); i++){
		if(elements[i] || i==other){ //The new set has elements of the original set, and the new element if it's in range
			newset.elements[i] = true;
		}
	}
	
	return newset;
}

Set Set::operator-(const Set &other) const{ //Returns the original set without the values within other 
	Set newset(getCapacity());
	
	for(int i=0; i<getCapacity(); i++){
		if(elements[i] && !(other.elements[i])){ //If the value's in the original set, but not in the other, it'll be in the new set
			newset.elements[i] = true;
		}
	}
	
	return newset;
}

Set Set::operator-(const int &other) const{ //Remove the defined element
	Set newset(getCapacity());
	
	for(int i=0; i<getCapacity(); i++){
		if(i == other){ //If the element is reached, then remove it
			newset.elements[i] = false;
		}
		else if(elements[i]){ //Ensure all other elements are included
			newset.elements[i] = true;
		}
	}
	
	return newset;
}

Set Set::operator^(const Set &other) const{ //Returns the intersection of the original and other set 
	short firstC = getCapacity();
	short secondC = other.getCapacity();
	short max = (firstC<secondC)?secondC:firstC; //take max capacity of before and after the operator
	Set newset(max); //new set has max capacity
	
	for(int i=0; i<max; i++){
		if(elements[i] && other.elements[i]){ //if both are true, add to the new set
			newset.elements[i] = true;
		}
	}
	
	return newset;
	
}

Set Set::operator^(const int &other) const{ //Returns the intersection of the original set and an element
	Set newset(getCapacity());
	
	for(int i=0; i<getCapacity(); i++){
		if(elements[i] && (i == other)){ //If the element is within the original set, then add it to the new set
			newset.elements[i] = true;
		}
	}
	
	return newset;
	
}

Set Set::operator~() const{ //Returns the set of the possible values not in the original set
	Set newset(getCapacity());
	
	for(int i=0; i<getCapacity(); i++){
		if(elements[i]){ //If the element is within the original set, then remove it
			newset.elements[i] = false;
		}
		else{ //If the element was not in the original set, add it
			newset.elements[i] = true;
		}
	}
	
	return newset;
	
}
	
Set Set::operator+() const{ //Returns all possible elements of the original set
	Set newset(getCapacity());
	
	for(int i=0; i<getCapacity(); i++){
		newset.elements[i] = true; //Add all possible elements to the new set
	}
	
	return newset;
}

Set Set::operator-() const{ //Returns an empty set with the capacity of the original set
	Set newset(getCapacity());
	return newset;
}

bool Set::operator<=(const Set &other) const{ //Tests if all original set elements are within the other set
	
	for(int i=0; i<getCapacity(); i++){
		if(elements[i] && !(other.elements[i])){ //If an original element does not exist in the other set, return false immediately
			return false;
		}
	}
	return true;
}

bool Set::operator<(const Set &other) const{ //Tests if all original set elements are within the other set but do not equal the other set
	int firstC = getCapacity();
	int secondC = other.getCapacity();
	int max = (firstC<secondC)?secondC:firstC; //take max capacity of before and after the operator
	bool EqualCheck = true; //Initially assumed equal
	
	for(int i=0; i<max; i++){ //Iterates through the larger capacity set
		if(elements[i] && !(other.elements[i])){ //If an original element does not exist in the other set, return false immediately
			return false;
		}
		if(other.elements[i] && !(elements[i])){ //If the other set has an element not within the original set, then they are not equal
			EqualCheck = false;
		}
	}
	
	if(EqualCheck){ //If they're equal return false
		return false;
	}
	return true;
}

bool Set::operator>=(const Set &other) const{ //Tests if all other set elements are within the original set
	int firstC = getCapacity();
	int secondC = other.getCapacity();
	bool EqualCheck = (firstC==secondC)?true:false;
	
	for(int i=0; i<getCapacity(); i++){
		if(other.elements[i] && !(elements[i])){ //If an other element does not exist in the original set, return false immediately
			return false;
		}
	}
	return EqualCheck;
}

bool Set::operator>(const Set &other) const{ //Tests if all other set elements are within the original set but do not equal each other
	int firstC = getCapacity();
	int secondC = other.getCapacity();
	int max = (firstC<secondC)?secondC:firstC; //take max capacity of before and after the operator
	bool EqualCheck = true; //Initially assumed equal
	
	for(int i=0; i<max; i++){ //Iterates through the larger capacity set
		if(other.elements[i] && !(elements[i])){ //If an other element does not exist in the original set, return false immediately
			return false;
		}
		if(elements[i] && !(other.elements[i])){ //If the original set has an element not within the other set, then they are not equal
			EqualCheck = false;
		}
	}
	
	if(EqualCheck){ //If they're equal return false
		return false;
	}
	return true;
}

bool Set::operator==(const Set &other) const{ //Tests if the original and other set have the exact same elements
	int firstC = getCapacity();
	int secondC = other.getCapacity();
	int max = (firstC<secondC)?secondC:firstC; //take max capacity of before and after the operator
	
	for(int i=0; i<max; i++){ //Iterates through the larger capacity set
		if(elements[i] != other.elements[i]){ //If one set has an element the other does not, return false
			return false;
		}
	}
	
	return true; //Returns true if both sets are equal
}

bool Set::operator!=(const Set &other) const{ //Tests if the original and other set have a differing element
	int firstC = getCapacity();
	int secondC = other.getCapacity();
	int max = (firstC<secondC)?secondC:firstC; //take max capacity of before and after the operator
	
	for(int i=0; i<max; i++){ //Iterates through the larger capacity set
		if(elements[i] != other.elements[i]){ //If one set has an element the other does not, return true
			return true;
		}
	}
	
	return false; //Returns false if both sets are equal
}

bool Set::operator!() const{ //Tests for an empty set
	
	for(int i=0; i<getCapacity(); i++){
		if(elements[i]){ //If a value is found, return false
			return false;
		}
	}
	return true;
}

int Set::operator()() const{ //Returns the Cardinality of the set
	
	int Cardinality = 0;
	
	for(int i=0; i<getCapacity(); i++){
		if(elements[i]){ //If a value is found, increment the cardinality counter
			Cardinality++;
		}
	}
	return Cardinality;
}

bool Set::operator[](const int &pos) const{ //Tests if an element is in the set	
	if(elements[pos]){ //If the element is in the set, return true
		return true;
	}
	return false;
}

std::ostream& operator<<(std::ostream &out, const Set &set){ //Defines how to output a set
	
	out << '{';
	bool firstCheck = true;
	for(int i=0; i<set.getCapacity(); i++){
		if(set.elements[i]){ //If the value exists in the set, output it
			if(firstCheck){
				out << i;
				firstCheck = false;
			}
			else{
				out << ", " << i; 
			}
		}
	}
	out << '}';
}

std::istream& operator>>(std::istream &in, Set &set){ //Defines how to input a set
	
	//Only functions with the given syntax of "A:{a,b,...,z}"
	
	char input[255];
	in >> input;
	int i=0;
	char val;
	int index;
	
	while(true){
		if(input[i] == '}' && input[i] != ','){ //If end of input set is reached, end the stream
			break;
		}
		if(input[i] != ',' && input[i] != '{' && input[i] != '}'){ //Convert any found num character to an integer
			val = input[i];
			index = val - '0';
		while(input[i+1] != ',' && input[i+1] != '}'){ //If there are more num characters, append them to the inital integer
			i++;
			val = input[i];
			index = (index*10) + (val - '0');
		}
			set.elements[index] = true; //Insert the value indicated by the stream
		}	
		i++;
	}
}

int Set::getCapacity() const{ //Return the capacity
	return capacity;
}
	