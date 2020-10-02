#include<stdio.h>
#include<stdlib.h>
#include<unistd.h>
#include<signal.h>

#define MAX_STEADY_THREADS 10
#define NUMBER_THREADS 6

//threads with larger priority are started before!!!
struct thread {
	int id;
	int timeLeft;
	int prior;
	int sch; //FIFO=1, RR=0
	int timeCreate; //attribute used when 2 threads are started at the same time
};

typedef struct thread thread;

int time = 0;
thread **steadyThreads; //active + waiting threads
int systemThreads[NUMBER_THREADS][5] =
{
	/* creation time, id, processing time, priority, scheduling type */
	{ 1,  3, 5, 3, 0 }, 
	{ 3,  5, 6, 5, 0 },
	{ 7,  2, 3, 5, 1 },
	{ 12, 1, 5, 3, 1 },
	{ 20, 6, 3, 6, 0 },
	{ 20, 7, 4, 7, 0 },
};

void print(int header) {
	int i;
	if(header == 1) {
		printf(" t    ACT" );
		for (i = 1; i < MAX_STEADY_THREADS; i++) {
			printf("     PR%d",i);
		}
		printf("\n");
	}
	printf("%3d ",time);
	for (i = 0; i < MAX_STEADY_THREADS; i++ ) {
		if (steadyThreads[i] != NULL) {
			printf("  %d/%d/%d ",steadyThreads[i]->id, steadyThreads[i]->prior, steadyThreads[i]->timeLeft);
		} else {
			printf ("  -/-/- ");
		}
	}
	printf("\n");
}

int main(void) {
	int steadyPos = 0; //position in array
	int threadCounter = NUMBER_THREADS;
	steadyThreads = (thread**) malloc(sizeof(thread) * MAX_STEADY_THREADS); //allocate thread
	int first = 1; //flag for printing
	while(threadCounter > 0) { //if there are more threads in system
		if(steadyThreads[0] != NULL) { //if active thread exists
			steadyThreads[0]->timeLeft--;
			if(steadyThreads[0]->timeLeft <= 0) { //thread finished
				printf("%3d Thread with id %d ended \n", time, steadyThreads[0]->id);
				free(steadyThreads[0]);
				if(steadyPos == 1) {
					steadyThreads[0] = NULL;
				} else {
					for(int j = 0; j < steadyPos -1; j++) {
						steadyThreads[j] = steadyThreads[j+1];
					}
				}
				threadCounter--;
				steadyThreads[--steadyPos] = NULL;
			} else if(steadyThreads[0]->sch == 0) { //Quant of time finished, thread is moved in front of first thread that has smaller priority
				if(steadyThreads[1] != NULL && steadyThreads[1]->prior >= steadyThreads[0]->prior) {
					thread *tmp = (steadyThreads[0]);
					steadyThreads[0] = steadyThreads[1]; //active thread is thread that has been beyond
					steadyThreads[1] = NULL; //put NULL on index 1
					int j = 1;
					for(int k = j; j <=steadyPos; j++) { //shift all threads and find the one with smaller priortiy
						steadyThreads[k] = steadyThreads[k+1];
					}
					j = 1;
					while(steadyThreads[j] != NULL && tmp->prior <= steadyThreads[j]->prior) {
						j++;
					}
					for(int k = steadyPos-1; k > j; k--) { //shift all threads that are before j
						steadyThreads[k] = steadyThreads[k-1];
					}
					steadyThreads[j] = tmp;
				}
			}
		}
		for(int i = 0; i < NUMBER_THREADS; i++) {
			if(time == systemThreads[i][0]) {
				thread *newThread = (thread*) malloc(sizeof(thread)); //create new thread
				newThread->id = systemThreads[i][1];
				newThread->timeLeft = systemThreads[i][2];
				newThread->prior = systemThreads[i][3];
				newThread->sch = systemThreads[i][4];
				newThread->timeCreate = systemThreads[i][0];
				printf("%3d New thread created with id %d, processing time %d, priority %d, sch %d\n", time, newThread->id, newThread->timeLeft, newThread->prior, newThread->sch);
				if(steadyThreads[0] == NULL) { //if there are no active threads
					steadyThreads[0] = newThread;
				} else if(newThread->prior > steadyThreads[0]->prior || (newThread->prior == steadyThreads[0]->prior && steadyThreads[0]->sch == 0 && time == steadyThreads[0]->timeCreate)){ //if new thrad has larger priority or it has equal priority comparing to the active one when active thread has RR scheduling but it's not created at the same time as this new thread
					thread *tmp2 = (steadyThreads[0]);  //remember active thread
					steadyThreads[0] = newThread; //new thread is now active 
					int j = 1;
					while(steadyThreads[j] != NULL && tmp2->prior <= steadyThreads[j]->prior) {  //find place for old thrrad
						j++;
					}
					for(int k = steadyPos+1; k > j; k--) { //shift all threads that are before j
						steadyThreads[k] = steadyThreads[k-1];
					}
					steadyThreads[j] = tmp2;
				} else { //put new thread in front of thread with smaller priority
					int j = 1;
					while(j < steadyPos && newThread->prior <= steadyThreads[j]->prior) {//find first thread with smaller priority
						j++;
					}
					for(int k = steadyPos; k > j; k--) { //shift all threads that are before j
						steadyThreads[k] = steadyThreads[k-1];
					}
					steadyThreads[j] = newThread;
				} 
				steadyPos++;
			}
		}
		if(first == 1) {
			print(1);
			first = 0;
		} else {
			print(0);
		}
		time++;
		sleep(1);
	}
	free(steadyThreads); //free allocated array space
	return 0;
}

