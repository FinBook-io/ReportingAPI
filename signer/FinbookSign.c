#include <unistd.h>
#include<stdio.h>
#include <string.h>
#include <locale.h>

int main(int argc, char *argv[])
{
char command[256];
strcpy(command, "java -jar C:/src/reporting/signer/Firma.jar");
 for (int i = 1; i < argc; ++i){
    strcat(command, argv[i]);
    printf("%s",argv[i]);
 }
system(command);
return 0;
}


