#include <stdio.h>
#include <semaphore.h>
#include <unistd.h>
#include <stdlib.h>
#include <sys/shm.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <time.h>
#include<signal.h>


int idMem; //za memoriju init
struct init { // memorija koju cemo zauzet
  sem_t ko, stol_prazan, pusac1, pusac2, pusac0;
  int sastojak1, sastojak2;
};

struct init *semafori;

void trgovac() { // 0 papir 1 duhan 2 sibice
  srand(time(NULL)); //inicijaliziraj random
  int sas1, sas2;
  while (1) {
    sas1 = rand() % 3; //randomiziraj sastojke
    while ((sas2 = rand() % 3) == sas1) {}
    sem_wait(&(semafori->ko)); //cekaj kriticni odsjecak
    printf("\nTrgovac stavlja: ");
    if (sas1 == 0) {
      printf("papir i ");
    } else if (sas1 == 1) {
      printf("duhan i ");
    } else if (sas1 == 2) {
      printf("sibice i ");
    }
    if (sas2 == 0) {
      printf("papir\n");
    } else if (sas2 == 1) {
      printf("duhan\n");
    } else if (sas2 == 2) {
      printf("sibice\n");
    }
    semafori->sastojak1 = sas1;
    semafori-> sastojak2 = sas2; //postavi sastojke semaforu
    sem_post(&(semafori->ko));
    sem_post(&(semafori->pusac0));
    sem_post(&(semafori->pusac1));
    sem_post(&(semafori->pusac2));
    sem_wait(&(semafori->stol_prazan));
    sleep(1);
  }
}

void pusac(int i) { //koji je to pusac

  int r1, r2; //odredi koji sastojci fale
  if (i == 0) {
    r1 = 1;
    r2 = 2;
  } else if (i == 1) {
    r1 = 0;
    r2 = 2;
  } else if (i == 2) {
    r1 = 0;
    r2 = 1;
  }

  while (1) {
    if (i == 0) {
      sem_wait(&(semafori->pusac0));
    } else if (i == 1) {
      sem_wait(&(semafori->pusac1));
    } else if (i == 2) {
      sem_wait(&(semafori->pusac2));
    }

    sem_wait(&(semafori->ko)); //cekaj k.o.
    if ((r1 == semafori-> sastojak1 && r2 == semafori-> sastojak2) || (r1 == semafori-> sastojak2 && r2 == semafori->sastojak1)) {
      usleep(1000 * 200); //uzmi sastojke
      printf("Pusac %d uzima sastojke i ...\n", i + 1);
      sem_post(&(semafori->ko)); //postavi semafore
      sem_post(&(semafori->stol_prazan)); //postavi stol
      usleep(1000 * 600); //zapali i puši
    } else {
      sem_post(&(semafori->ko));
    }
  }
}

void prekidna_rutina(int sig) { //pocisti za sobom
  sem_destroy(&(semafori->ko));
  sem_destroy(&(semafori->stol_prazan));
  sem_destroy(&(semafori->pusac0));
  sem_destroy(&(semafori->pusac1));
  sem_destroy(&(semafori->pusac2));
  shmdt(semafori);
}

int main(void) {
  idMem = shmget(IPC_PRIVATE, sizeof(struct init), 0600);
  if (idMem == -1) {
    printf("Greška pri inicijalizaciji\n");
    exit(1);
  }
  sigset(SIGINT, prekidna_rutina);
  semafori = (struct init * ) shmat(idMem, NULL, 0);
  shmctl(idMem, IPC_RMID, NULL);
  sem_init(&(semafori->ko), 1, 1);
  sem_init(&(semafori->pusac0), 1, 0);
  sem_init(&(semafori->pusac1), 1, 0);
  sem_init(&(semafori->pusac2), 1, 0);
  sem_init(&(semafori->stol_prazan), 1, 0);

  printf("Pusac 1 ima papir\n");
  printf("Pusac 2 ima duhan\n");
  printf("Pusac 3 ima sibice\n");

  int pid = fork();
  if (pid == -1) {
    printf("Greska pri stvaranju procesa trgovca\n");
    exit(1);
  } else if (pid == 0) {
    trgovac();
    exit(0);
  }

  int arr[] = {
    0,
    1,
    2
  };
  for (int i = 0; i < 3; i++) {
    pid = fork();
    if (pid == 0) {
      pusac(arr[i]);
      exit(0);
    } else if (pid == -1) {
      printf("Ne mogu stvoriti novi proces\n");
      exit(1);
    }
  }
  for (int i = 0; i < 4; i++) // cekaj da zavrse 
    wait(NULL);

  return 0;
}
