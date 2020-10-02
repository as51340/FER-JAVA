#include<stdio.h>
#include<stdlib.h>
#include<pthread.h>
#include<iostream>
#include<unistd.h>
//#include<stdatomic.h>

int a;

using namespace std;


void *func(void *x) {
	//stra *my_struct = (stra *) x;
	//printf("U dretvi %d\n", my_struct->i);
//	int *m  =(int *) x;
	for(int i = 0; i < *((int *) x); i++) {
		//printf("Iteracija %d\n", i );
		a++;
		//sleep(1);
	}
	return NULL;
}



int main(int argc, char **argv) {
	int n, m;
	n = atoi(argv[1]);
	m = atoi(argv[2]);

	pthread_t thr[n];

	a = 0;





	for(int i = 0; i < n; i++) {
		/*stra my_struct;
		my_struct.m = m;
		my_struct.i = i;*/
		if(pthread_create(&thr[i], NULL, func, &m) != 0) {
			exit(1);
		}
		/*else {
			pthread_join(thr[i], NULL);
		}
		sleep(1)/*/
	}


	for(int i = 0; i < n; i++) {
		pthread_join(thr[i], NULL);
	}

	cout <<"A=" << a << endl;
	return 0;
}


