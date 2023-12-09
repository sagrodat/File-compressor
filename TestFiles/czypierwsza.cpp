#include <iostream>
#include <cmath>
using namespace std;

int main()
{
int n;
cin >> n;
bool czypierwsza=true;


for(int i=2;i<=sqrt(n);i++)
{
    if((n%i) == 0)
    {
        czypierwsza = false;
        cout << "Liczba " << i << " jest dzielnikiem" << endl;
        break;
    }

}
if (czypierwsza == false) cout << "Liczba ta nie jest pierwsza"<< endl;
else cout << "Liczba ta jest pierwsza";
}
