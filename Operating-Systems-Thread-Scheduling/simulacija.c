#include<stdio.h>
#include<stdlib.h>
#include<unistd.h>

#define MAX_STEADY_THREADS 5 //maximum number of threads that can be stored in queue
#define NUMBER_THREADS 6//threads in system

struct thread {
	int id; //thread's id
	int timeLeft; //processing time left
	int prior; //priority of thread-here it's only used for printing considering FIFO scheduling FIFO is used
};

typedef struct thread thread;
	
thread **steadyThreads; //active + waiting threads
int time = 0; //current time in system
int systemThreads[NUMBER_THREADS][4] = {
	/* creation time, id, processing time, priority  */
	{ 1,  3, 5, 3}, 
	{ 3,  5, 6, 5},
	{ 7,  2, 3, 5},
	{ 12, 1, 5, 3},
	{ 20, 6, 3, 6},
	{ 20, 7, 4, 7},
};

void print(int header) { //function for printing current state of system
	int i;
	if(header) {
		printf("  t    ACT");
		for (i = 1; i < MAX_STEADY_THREADS;i++) { 
			printf ( "     PR%d", i );
		}
		printf("\n");
	}
	printf ( "%3d ", time);
	for(i = 0;i<MAX_STEADY_THREADS;i++) {
		if(steadyThreads[i] != NULL) {
			printf("  %d/%d/%d ", steadyThreads[i]->id, steadyThreads[i]->prior, steadyThreads[i]->timeLeft);
		}
		else {
			printf("  -/-/- ");
		}
	}
	printf("\n");
}

int main(void) {
	steadyThreads = (thread **) malloc(sizeof(thread*) * MAX_STEADY_THREADS); //allocation of memory for array where threads are stored
	int steadyPos = 0; //position in array
	int cnt = 0; //flag so we can know if header has to be printed or not
	int threadCounter = NUMBER_THREADS;
	while(threadCounter > 0) {
		if(steadyThreads[0] != NULL) { //if active thread exists
			steadyThreads[0]->timeLeft--;
			if(steadyThreads[0]->timeLeft  <= 0) {	//if this thread has finished
				printf("%3d Thread with id %d ended\n", time, steadyThreads[0]->id);
				free(steadyThreads[0]); //free memory for this thread
				if(steadyPos == 1) { //active thread has finished and there are no other threads in system
					steadyThreads[0] = NULL;
				} else {
					for(int j = 0; j < steadyPos-1; j++) { //shift all threads
						steadyThreads[j] = steadyThreads[j+1];
					}
				}
				threadCounter--;
				steadyThreads[--steadyPos] = NULL; //one thread less
			}
		}
		for(int i = 0; i < NUMBER_THREADS; i++) {
			if(time == systemThreads[i][0]) {
				thread *newThread = (thread*) malloc(sizeof(thread)); //allocate memory for new thread
				newThread->id = systemThreads[i][1];
				newThread->timeLeft = systemThreads[i][2];
				newThread->prior = systemThreads[i][3];
				printf("%3d New thread created with id %d, processing time %d\n", time, newThread->id, newThread->timeLeft);
				steadyThreads[steadyPos++] = newThread; //put thread in array
			}
		}
		if(cnt == 0) {
			print(1);
			cnt = 1;
		} else {
			print(0);
		}
		time++;
		sleep(1);
	}
	free(steadyThreads); //free array space
			
	
	return 0;
}
