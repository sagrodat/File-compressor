#include <iostream>
#include <cmath>
using namespace std;

int main()
{
int a = 2, b = 3, c = 5;
int n;
cin >> n;
int tab[n];

    for(int i=0; i<n; i++) // robimy tablice wszystkich liczb
    {
        tab[i] = i;
    }
    for(int i=1; i<n; i++) // jesli jakas liczba nie jest wielokrotonoscia 2,3,5 to jest liczba pierwsza
    {
         if((tab[i]%2!=0) && (tab[i]%3!=0) && (tab[i]%5!=0) && (tab[i]%7!=0) && (tab[i]%11!=0) && (tab[i]%13!=0))
            cout << tab[i] << endl;
    }

}
