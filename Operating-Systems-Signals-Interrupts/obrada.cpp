#include<iostream>
#include<stdio.h>
#include<unistd.h>
#include<signal.h>
#define N 6

using namespace std;


int sig[] = {SIGUSR1, SIGUSR2, SIGTERM, SIGQUIT, SIGINT};
int prioritet[] = {0,0,0,0,0,0};
int oznaka_cekanja[] = {0,0,0,0,0,0};
int tekuci_prioritet = 0;


void zabrani_prekidanje() {
	for(int i = 0; i < 5; i++) {
		sighold(sig[i]);
	}
}


void dozvoli_prekidanje() {
	for(int i = 0; i < 5; i++) {
		sigrelse(sig[i]);
	}
}

void obrada_signala(int i) {
	switch(i) {
	case 1:
		cout << " -  P  -  -  -  -" << endl;
		break;
	case 2:
		cout << " -  -  P  -  -  -" << endl;
		break;
	case 3:
		cout << " -  -  -  P  -  -" << endl;
		break;
	case 4:
		cout << " -  -  -  -  P  -" << endl;
		break;
	case 5:
		cout << " -  -  -  -  -  P" << endl;
		break;
	}


	

	switch(i) {

		case 1:
			for(int j = 1; j <= 5; j++) {
				cout << " -  " << j << "  -  -  -  -" << endl;	
				sleep(1);
			}
			
			break;
		case 2:
			for(int j = 1; j <= 5; j++) {
			cout << " -  -  " << j << "  -  -  -" << endl;
			sleep(1);
			}
			break;
		case 3:
			for(int j = 1; j <= 5; j++) {
			cout << " -  -  -  " << j << "  -  -" << endl;
			sleep(1);
			}
			break;
		case 4:
			for(int j = 1; j <= 5; j++) {
			cout << " -  -  -  -  " << j << "  -" << endl;
			sleep(1);
			}
			break;
		case 5:
			for(int j = 1; j <= 5; j++) {
			cout << " -  -  -  -  -  " << j << endl;
			sleep(1);
			}
			break;
	}
	


	switch(i) {
	case 1:
		cout << " -  K  -  -  -  -" << endl;
		break;
	case 2:
		cout << " -  -  K  -  -  -" << endl;
		break;
	case 3:
		cout << " -  -  -  K  -  -" << endl;
		break;
	case 4:
		cout << " -  -  -  -  K  -" << endl;
		break;
	case 5:
		cout << " -  -  -  -  -  K" << endl;
		break;
	}
}


void prekidna_rutina(int sig) {
	zabrani_prekidanje();
	int n = -1;
	
	switch(sig) {
	case SIGUSR1:
		n = 1;
		cout << " -  X  -  -  -  -" << endl;
		break;
	case SIGUSR2:
		n = 2;
		cout << " -  -  X  -  -  -" << endl;
		break;
	case SIGTERM:
		n = 3;
		cout << " -  -  -  X  -  -" << endl;
		break;
	case SIGQUIT:
		n = 4;
		cout << " -  -  -  -  X  -" << endl;
		break;
	case SIGINT:
		n = 5;
		cout << " -  -  -  -  -  X" << endl;
		break;
	}

	oznaka_cekanja[n]++;
	

	while(true) {
		int x = 0;
		for (int j = tekuci_prioritet + 1; j < N; j++) {
			if(oznaka_cekanja[j] != 0) {
				x = j;
			}
		}
		
		if(x > 0) {
			oznaka_cekanja[x]--;
			prioritet[x] = tekuci_prioritet;
			tekuci_prioritet = x;
			dozvoli_prekidanje();
			obrada_signala(x);
			zabrani_prekidanje();
			tekuci_prioritet = prioritet[x];
		}
		else
			break;
	} 
	
	dozvoli_prekidanje();
}





int main(void) {
	sigset(SIGUSR1, prekidna_rutina);
	sigset(SIGUSR2, prekidna_rutina);
	sigset(SIGTERM, prekidna_rutina);
	sigset(SIGQUIT, prekidna_rutina);
	sigset(SIGINT, prekidna_rutina);
	
	cout <<"Proces obrade prekida, PID=" << getpid() << endl;
	cout <<"GP S1 S2 S3 S4 S5" << endl;
	cout <<"-----------------" << endl;

	int y = 1;

	while(true) {
		printf("%2d  -  -  -  -  -\n",y); 
		y++;
		sleep(1);
	}

	cout << "Zavrsio osnovni program" << endl;
	return 0;
	
}


