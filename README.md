When we start we have to build project with all dependencies.
To do it we to make command
gradle build
When project is build we start application by command
java -jar build/libs/gs-rest-service-1.0-SNAPSHOT.jar
When we want to run all tests we use command
gradle test


For creating objects I have tried to use factories.
I think it was good choice because it simplified code and made it cleaner.

Because in assignment I was told that in future we maybe will add additional sources of counting discounts I use strategy
pattern here as I think it suited most. Because it wasn't told directly I made assumptions that if we will have more sources
of discount we sum them together.

My solution is basing on assumption that we keep products in one csv file.

Hope it is written good enough :)