#include<iostream>
#include<stdio.h>
#include<signal.h>
#include<sys/types.h>
#include<sys/ipc.h>
#include<sys/shm.h>
#include<unistd.h>
#include<stdlib.h>
#include<sys/wait.h>

using namespace std;

int id;
int *A;

void proces(int m) {
	for(int j = 0; j < m; j++) {
		*A = *A + 1;
	}
	//cout << *A << endl;
}

void brisi(int sig) {
	(void) shmdt((char *) A);
	(void) shmctl(id, IPC_RMID, NULL);
	exit(0);
}



int main(int argc, char **argv) {
	int n, m;
	n = atoi(argv[1]);
	m = atoi(argv[2]);
	sigset(SIGINT, brisi);

	id = shmget(IPC_PRIVATE, sizeof(int), 0600); //velicina int, 0600 za citanje pisanje-korisnik
	if(id == -1)
		exit(1); //greska nemoremo rezervirat memoriju


	A = (int *) shmat(id, NULL, 0);
	*A = 0;
	int pid;
	for(int i = 0; i < n; i++) {
		if(fork() == 0){
			proces(m);
			exit(0);
		}
	}

	for(int j = 0; j < n; j++) {
		 wait(NULL);
	}
	cout << "A=" << *A << endl;
	shmdt((char *) A);
	shmctl(id, IPC_RMID, NULL);

	return 0;
}
