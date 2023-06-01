# gameBackend

## About this project

This project is a technical task. 

The objective of the task is to develop a backend for a game, 
where player bets sends a bet, and a whole number from 1 to 100 to a server. 

Then server generates random whole number from 1 to 100,
and if the player's number is greater, calculates win and sends it back to the player. 

Winnings depend on formula: = bet * (99 / (100 - number)), \
as an example, if player selected the number 50 and bet 40.5, the win would be 80.19.

### NB
I do not know whether it is my misunderstanding, or the task is incorrect. 

Using number 100 causes the division by zero which results in Infinity value of the winnings. \
Because of this I made adjustments to the task. \

* Player can pick number from 1 to 99 included.
* There is nothing mentioning tie in the task (when player number is the same as server number), so I skipped implementation of this.
* While random generates number for the server it checks if it is in the range from 1 to 99 included.
```java
    public int getRandomNumber() {
        int number = 0;
        do {
            number = (int) Math.abs(ThreadLocalRandom.current().nextGaussian() * 100.0);
        } while (number >= 100 || number < 1); // if 100 or more, then it crashes the bet * (99.0f / (100 - clientNumber)) formula
        return number;
    }
```

Also, using formula _bet * (99 / (100 - number))_, player can almost guarantee the win by simply using number 99. \
Because computer uses random number generation, it is highly unlikely that it will generate 99 every time to prevent player from winning,
while player can choose 99 every time.

Furthermore, it is strange, that the higher the number player chooses - the higher the win, when it should be vice versa.
- player chose 99. bet is 10
```
winnings = bet * (99 / 100 - 99) = bet * (99) = 990
```
- player chose 10. bet is 10
```
winnings = bet * (99 / 100 - 10) = bet * (1,1) = 11
```

### Optional task
In the optional task both player and server choose random numbers and bets from 1 to 99 using getRandomNumber().

While the implementation of threading is in my opinion correct, the results (RTP) are strange. \
Mean RTP is around 330-350%, which should be around 95-99% (house always wins). \
I think the problem here is again in the winning formula, because even if player loses the majority of the time,
high bets and use of big numbers cover the losses.

If using number 50, instead of random, RTP is around 110%, so it is already an "improvement".

## Prerequisites

* [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)

  ```sh
  https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html
  ```

* [Maven](https://maven.apache.org/download.cgi)

  ```sh
  https://maven.apache.org/download.cgi
  ```

## Build

### Build and run using Maven

1. Download .zip or clone project from GitHub.

2. Unzip and/or go to project location in __terminal__.

3. Build the project using maven
   ```sh
   mvn clean package
   ```

4. Run the project
   ```sh
   java -jar ./target/gameBackend-1.0-SNAPSHOT.jar
   ```

5. Now you can use the project.

6. Use [link](http://localhost:8080) to use the game.

## Tests

There are unit tests and integration tests.

By default, while building the project using ```mvn clean package``` only unit tests run automatically.
If you want to run the integration tests, refer to the instruction below.

### How to run from maven

* Integration tests

    - Run integration tests separately
      ```sh
      mvn test -Dtest=**IT.java
      ```
    - Run as verify
      ```sh
      mvn clean verify
      ```
    - Run as install
      ```sh
      mvn clean install
      ```

- Unit tests

    - Build the project using maven (unit tests run automatically)
       ```sh
       mvn clean package
       ```

    - To run only the application unit tests just type the following command:
      ```sh
      mvn test
      ```
      
## Optional task

- Run the test
  
  ```sh
   mvn test -Dtest=GameSimulationTest.java
   ```

