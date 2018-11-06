#include "PQheader.h"
#include <string>

/* Daniel Sokic 6164545 */


/* This method takes the item and order of a record, along with the starting record to add to.
	With these, a new record is made of the order and item provided [1], and a pointer is set
	to the starting record[2]. If the pointer results in pointing to null space, then the newly
	created record is assigned to n[3]. Also, if the first record in the queue is lower priority,
	then it is replaced with the new node[4], and made the second record in the queue[5].
	Otherwise the pointer traverses the queue [6] until the next record is either null[7] or has 
	a higher order number than the newer record[8]. If null, then the new record is immdeiately 
	appended  to the end[9]. If the next order is greater than the new order, which indicates the 
	next order is less important, then the new record's next pointer is assigned to this record [10]
	while the reference's next pointer is assigned  to the new record[11].*/
	
void add(const std::string &i, const long &o, PRecord* &n){

	PRecord *newn = new PRecord;
	newn->item = i; //[1]
	newn->order = o; //[1]
	PRecord *ptr = n; //[2]
	
	if(ptr == NULL){
		n = newn; //[3]
	}
	else if(ptr->order > o){
		n = newn;//[4]
		n->next = ptr; //[5]
	}
	else{
		while(true){
			if(ptr->next == NULL){ //[7]
				ptr->next = newn; //[9]
				break;
			}
			else if(ptr->next->order > o){ //[8]
				newn->next = ptr->next; //[10]
				ptr->next = newn; //[11]
				break;			
			}
			else{
				ptr = ptr->next; //[6]
			}
		}
	}
	
};

/* This method takes in the reference to the first record of a queue n. First it assigns a pointer to the record in memory[1],
	then stores its item is stored[2]. Followed by removal from the queue by assigning the reference to the next item in queue[3]. 
	Finally, the removed record is deleted from memory [4], and the item is returned[5]. */
std::string remove(PRecord* &n){
	PRecord *ptr = n;//[1]
	std::string value = ptr->item; //[2]
	n = n->next; //[3]
	delete ptr;//[4]
	return value; //[5]
}

/** The main method begins by initializing the reference for the first record in the queue [1]. Followed by variables
	for checking what to perform next[2], stores a string [3], and stores a long [4]. Afterwards, a request is made to
	indicate what action should be performed next[5]. If the user requests to add, then they are asked what string they
	would like to be in the record[6], followed by a valid[8] priority[7]. Once provided, the values, along with the first
	record reference is sent to the add method[9]. In the case of removing a record, the method refuses if the queue is empty[10],
	otherwise text is sent indicating the priority and item of the record that was removed[11]. Followed by calling the remove
	method, assigning the returned value to the 'item' variable for potential future use[12]. If the user decides to quit,
	then the program ends[13]. If an input not listed by the program is input, a default response is given and the user is
	prompted again[14].*/
int main(){
	PRecord *first = NULL; //[1]
	int actioncheck;//[2]
	std::string item;//[3]
	long priority;//[4]
	
	while(true){
		std::cout << "What action would you like to perform:" << std::endl;
		std::cout << "1. Add to queue, 2. Remove the highest priority item, 3. Quit" << std::endl;
		std::cin >> actioncheck; //[5]
		switch(actioncheck){
			case(1):
				std::cout << "Indicate the string you wish to add: "; std::cin>> item; //[6]
				while(true){
					std::cout << "Indicate level of priority, 1 being highest priority: "; std::cin>> priority; //[7]
					if(priority <= 0){ //[8]
						std::cout << "Please enter a number that is 1 or higher" << std::endl;
					}
					else{
						break;
					}
				}
				add(item, priority, first); //[9]
				break;
			case(2):
				if(first == NULL) { //[10]
					std::cout << "Empty Queue! Cannot remove!" << std::endl;
				}
				else{
					std::cout << "With a priority of " << first->order << ", the item " << first->item << " has been removed!" << std::endl; //[11]
					item = remove(first); //[12]
				}
				break;
			case(3):
				return 0; //[13]
			default:
				std::cout << "INVALID RESPONSE!!!!!!!!!!!!!!" << std::endl; //[14]
				break;
		}
	}
};
