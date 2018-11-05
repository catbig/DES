# Implement-DES-using-Java-Card
This is my first time project using Java Card
=====================================
* Author : Catur Adi Nugroho
* Time   : 20160309 21:56
* Content: Implement DES on Java card

=====================================

Information:
Java Card only support:
boolean, byte, short
int (optional)

Not supported:
long, double, char, String, float

Not supported features:
* Threads
* Dynamic loading
* Garbage Collector until version 2.2
* Cloning
* Multi-dimension arrays
* Static array initialization.

Project Information:
1. Javacard Project build by Javacos (http://www.javacos.com)
2. Compile and export with Javacard 2.2.2
3. Export dir \bin\com\confident\crypto\javacard\
   a. crypto.cap
   b. crypto.exp
   c. crypto.jca

Package export & running on Javacard 2.2.2 successfully

reference:
des.c
