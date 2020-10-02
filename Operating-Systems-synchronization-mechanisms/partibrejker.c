#include<stdio.h>
#include<pthread.h>
#include<time.h>
#include<stdlib.h>
#include<unistd.h>

pthread_mutex_t monitor; //monitor
pthread_cond_t red[2]; //red 0-> student ceka, 1-> red za partibrejkera
int studenti_soba = 0; // broj studenata u sobi
int svi_prolasci = 0; //svi prolasci svakog studenta
int studenti_sustav = 0; //broj studenata sve skupa
int partibrejk = 0; // 0 nije u sobi, 1 u sobi je 

void *student(void *arg) {
  srand(time(NULL)); //inicijaliziraj random
  int stud = (* (int * ) arg) + 1;
  usleep(1000 * (rand() % 401 + 100));
  int i = 3;
  while (i > 0) {
    pthread_mutex_lock(&monitor); //udi u monitor
    while (partibrejk != 0) {
      pthread_cond_wait(&red[0], &monitor); //blokiraj studente
    }
    //student usao u sobu
    printf("Student %d je ušao u sobu\n", stud);
    studenti_soba++;
    if (studenti_soba >= 3) {
      pthread_cond_signal(&red[1]); //mozda partibrejker ude
    }
    pthread_mutex_unlock(&monitor); //izadi iz monitora
    usleep(1000 * (rand() % 1001 + 1000)); //zabavi se
    pthread_mutex_lock(&monitor); //udi u monitor
    printf("Student %d je izasao iz sobe\n", stud);
    studenti_soba--;
    svi_prolasci++;
    if (svi_prolasci >= 3 * studenti_sustav) { //ocisti kraj, ako ne lockdown nakraju
      pthread_cond_signal(&red[1]); //mozda partibrejker izade
    }
    pthread_mutex_unlock(&monitor); //izađi iz monitora
    usleep(1000 * (rand() % 1001 + 1000));
    i--;
  }
}

void *partibrejker(void * p) {
  while (svi_prolasci < 3 * studenti_sustav) { //uvjet dok je studenata u sustavu
    usleep(1000 * (rand() % 901 + 100)); //pocetno spavanje
    pthread_mutex_lock(&monitor);
    while (studenti_soba < 3 && svi_prolasci < 3 * studenti_sustav) {
      pthread_cond_wait(&red[1], &monitor); //partibrejker ceka ulaz
    }
    if (svi_prolasci >= 3 * studenti_sustav) { //ako su prosli svi studenti odi doma
      return NULL;
    }
    printf("Partibrejker je usao u sobu\n");
    partibrejk = 1; //usao je
    usleep(1000 * 200); //partibrejker unutra
    pthread_mutex_unlock(&monitor);
    while (studenti_soba > 0) {} //cekaj na izlaz
    pthread_mutex_lock(&monitor);
    partibrejk = 0;
    printf("Partibrejker je izasao iz sobe\n");
    pthread_cond_broadcast(&red[0]); //oslobodi studente koji čekaju za ulaz
    pthread_mutex_unlock(&monitor);
  }
}

int main(int argc, char ** argv) {
  if (argc < 1) {
    printf("Greška, broj studenata nije unesen\n");
    exit(1);
  }
  int *num = (int * ) malloc(sizeof(int) * studenti_sustav);
  //inicijaliziraj sustav
  studenti_sustav = atoi(argv[1]);
  pthread_t dretve[studenti_sustav + 1];
  pthread_mutex_init(&monitor, NULL);
  pthread_cond_init(&red[0], NULL);
  pthread_cond_init(&red[1], NULL);
  if (pthread_create(&dretve[studenti_sustav], NULL, partibrejker, NULL) != 0) {
    printf("Greška pri stvaranju partibrejkera!\n");
    exit(1);
  }
  for (int i = 0; i < studenti_sustav; i++) {
    num[i] = i;
    if (pthread_create(&dretve[i], NULL, student, &num[i]) != 0) {
      printf("Greška pri stvaranju studenta!\n");
      exit(1);
    }
    usleep((rand() % 500 + 1000 ) * 1000); //malo uspavaj stvaranje novih studenata
  }

  for (int i = 0; i <= studenti_sustav; i++) { //cekaj da zavrse svi
    pthread_join(dretve[i], NULL);
  }
  free(num);
  pthread_cond_destroy(&red[0]);
  pthread_cond_destroy(&red[1]);
  pthread_mutex_destroy(&monitor); //oslobodi memoriju

  return 0;
}
