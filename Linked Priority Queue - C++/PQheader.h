#include <iostream>
#include <string>

/* Daniel Sokic

	This header defines the struct of PRecord, with its variables being the item
	it holds, the priority in queue it has, and the reference to the next PRecord
	in the structure. It also initializes the methods to add to and remove from a PRecord structure,
	along with a method to view the PRecord */

struct PRecord {
	std::string item; //item of the record
	long order; //priority of the record
	PRecord *next; //reference to the next record
};

void add(const std::string &i, const long &o, PRecord* &n); //adds to the PRecord strucutre

std::string remove(PRecord* &n); //removes the first PRecord in the structure and returns its item

void peek(PRecord* &n); //displays the PRecords in the structure
