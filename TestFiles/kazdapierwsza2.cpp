#include <iostream>
#include <cmath>
using namespace std;

int main()
{
    for(int i=1;i<INT_MAX;i++)
    {
        for(int j=2;j<=sqrt(i);j++)
        {
            if(i%j==0)
            {
                break;
            }
            if (j==(int)sqrt(i))
            {
                cout << i << endl;
            }
        }
    }
}
