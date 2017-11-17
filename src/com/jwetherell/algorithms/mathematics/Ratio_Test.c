#include<stdio.h>
int main (){
	printf("\t\t\t\t\t****RATIO TEST****\n\n\n");
	printf("Numerator : \n");
	 int n,k,m;
		   printf("Maximum degree of the curve : ");
		   scanf("%d",&n);
		   int arr[n+1];
		   printf("Coefficients of the curve :");
		   for(k=0;k<=n;k++){
		   	scanf("%d",&arr[k]);
		   }

	printf("\nDenominator : \n");
		   printf("Maximum degree of the curve : ");
		   scanf("%d",&m);
		   int brr[m+1];
		   printf("Coefficients of the curve :");
		   for(k=0;k<=m;k++){
		   	scanf("%d",&brr[k]);
		   }
		if(m>n)
		printf("\nSequence converges !\n");
		else if(m==n)
		{
			if(brr[0]>=arr[0])
			printf("Sequence converges !\n");
			else
			printf("Sequence diverges !\n");
		}
		else
		printf("\nSequence diverges !\n");

}

