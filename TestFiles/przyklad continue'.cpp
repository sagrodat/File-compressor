#include <iostream>
using namespace std;

int main()



{
int i;
cin >> i;


    for (i; i>0; i--)
    {
        cout << "Wiek : " << i << " " << endl;
        if (i<18) continue;
        cout << "jest to osoba pelnoletnia" << endl;
    }
}
