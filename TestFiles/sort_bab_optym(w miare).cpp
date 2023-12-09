#include <iostream>
using namespace std;

int main()
{
    int n, pom, raz;
    cin >> n;
    int tab[n];
    for (int i=0; i<n; i++)
    {
        cin >> tab[i];
    }

    for(int j=0; j<n; j++)
    {
        if (raz == (n-1)) break; //  gdy wszystkie cyfry sa ulozone przerwij sortowanie kompletnie
        raz = 0;

        for(int i=0; i<n-1; i++)
        {
            pom = tab[i+1];
            if (tab[i] < tab[i+1]) // gdy 2 kolejne ulozone razem przerywamy wewnetrzna petle
            {
                raz ++;
                break;
            }
            else if (tab[i] > tab[i+1])
            {
                tab[i+1] = tab[i];
                tab[i] = pom;
            }
        }
    }
    for (int i=0; i<n; i++)
    {
        cout << i << " : " << tab[i] << endl;
    }

}

// DLACZEGO n-1?
// Sprawdzamy kazda liczbe z jej nastepna jej s¹siaduj¹c¹, gdy jest wieksza robimy zamiane np. 5 , 2 = > 2 , 5.
// Nasza 2 petle wykonuje wyzej wspomniana czynnosc, puszcona raz sprawdzi tylko raz sasiednie liczby np : 5, 2, 8 ,4 => 2 , 8, 5, 4 liczby nadal nie s¹ posortowane odpowiednio
// Dlatego nasza 2 petla musi wykonac sie wiekszosc ilosc razy a dokladniej n-1. Nasza 2 petla bedzie wykonana najwiecej razy w przypadku gdy musimy przeniesc ostatnia liczbe na 1 miejsce
// przyklad 4, 3, 2,1 => 3, 2, 1, 4 (po 1 wykonaniu) => 2, 1, 3, 4 (po 2 wykonaniu) => 1 ,2, 3 ,4 (po 3 wykonaniu), petla zostala wykonana 3 razy w przypadku 4 cyfr, (n=4),petla
// wykonala sie zatem n-1 razy dla przypadku w ktorym musi wykonac sie najwiecej razy

