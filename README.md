**Požadavky:**  
*  MySQL 5.6 nebo novější  
*  JDK 1.8 nebo novější  
*  OS Linux

**Spuštění databáze**  
* Nejprve je potřeba nainstalovat databázi - na linuxových systémech příkazem   
`sudo apt install mysql-server-5.7`  
* Po instalaci a spuštění pomocí příkazu `mysql`, MySQL je potřeba nakonfigurovat databázi pomocí sekvence příkazů  
`create database TattooPro;`  
`create user 'springuser'@'%' identified by 'ThePassword';`  
`grant all on TattooPro.* to 'springuser'@'%';`  

**Stažení repozitáře a spuštění aplikace**  
*  Nejprve je potřeba si k sobě stáhnout zdrojové kódy pomocí příkazu  
`git clone`  
*  Ve složce kde se nachází soubor `pom.xml` spustit příkaz  
`mvn install`
*  Nakonec je potřeba spustit výstupní soubor pomocí  
`java -jar ./target/<Jmeno vystupniho souboru z instalace>`
* Aplikace je poté dostupná na\
http://localhost:8080/

**Aplikace nemá klientskou část. Příjmá pouze HTTP požadavky.**

Např. tyto:

http://localhost:8080/car

http://localhost:8080/owner

http://localhost:8080/parkingspot

http://localhost:8080/tires


Další příklady se nachází v souboru [example_requests.http](https://gitlab.fit.cvut.cz/zunigjor/tjv_semestralka_parkoviste/blob/924efc20f1ec34296375a41a3aa659fa5970403c/example_requests.http)
