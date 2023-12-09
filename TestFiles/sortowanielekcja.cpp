#include <iostream>
#include <cmath>
#include <time.h>
#include <iomanip>
#include <cstdlib>
using namespace std;

const int N = 20;
int d[N];

int main()
{
    srand (time(NULL)); // aby losowe liczby zmienaily sie wraz z czasem  komputera a nie byly takie same w jednym uruchomieniu codeblocks
    for (int i=0; i<N; i++) d[i] = rand() % 100;
    for (int i=0; i<N; i++) cout << d[i] << endl;
}
