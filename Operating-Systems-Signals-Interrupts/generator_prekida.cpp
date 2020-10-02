#include<iostream>
#include<stdio.h>
#include<signal.h>
#include<stdlib.h>
#include<ctime>
#include<cstdlib>
#include<unistd.h>

int pid = 0;

using namespace std;

void prekidna_rutina(int sig) {
	kill(pid, SIGKILL);
	exit(0);
}

int main(int argc, char *argv[]) {
	pid = atoi(argv[1]);
	sigset(SIGINT, prekidna_rutina);
	srand(static_cast <unsigned> (time(0)));
	while(1) {
		//float secs = 3 + static_cast <float> (rand()) / (static_cast <float> (RAND_MAX / 2));
		int sig = rand() % 4 + 1;
		//cout << "Signal: " << sig << endl;
		int secs = 3 + rand() % 3;
		//cout << secs << endl;
		sleep(secs);
		switch(sig) {
			case 1:
				kill(pid, SIGUSR1);
				break;
			case 2:
				kill(pid, SIGUSR2);
				break;
			case 3:
				kill(pid, SIGTERM);
				break;
			case 4:
				kill(pid, SIGQUIT);
				break;
		}
	}
	return 0;
}
