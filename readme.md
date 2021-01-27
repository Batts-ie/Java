# holiday calculator
Dependencies: MariaDB on Port 3306 with root access - JavaFX version 15.01 Library and a JSON Handler - you will also need a driver for your database: https://prnt.sc/vfc8df <- drivers here

Program:
Gets all holidays from Austria on a user preferenced amount of time and puts the values on a GUI so you can see the days: "comparision"


![ask](https://user-images.githubusercontent.com/56489878/99804224-f432ce80-2b3a-11eb-87ad-4a5d330e08c1.png) <- there you will be asked, what you want to do.


![alles](https://user-images.githubusercontent.com/56489878/99804306-175d7e00-2b3b-11eb-8c6e-b5575f3d58a0.png) <- grafical user interrface that shows you the days printed in the terminal


# stock rates
https://www.alphavantage.co - registring
next steps: getting API
handler, that can output the final results of the api 
formatting

dependencies: database on port 3306 with root access - JavaFX version 15.01 Library and a JSON Handler - database driver

Program:
Get the stocks on a decent amount of time - output with date and number
![Aktiepreview](https://user-images.githubusercontent.com/56489878/104446456-c4cc9b00-559a-11eb-8e59-c36b5df8efe2.png) => graph is variating if you choose another stocks type, in this example I used Tesla TSLA

![Terminal](https://user-images.githubusercontent.com/56489878/104446481-cdbd6c80-559a-11eb-9cf5-955d689660e6.png) => this is the output and the questioning of the terminal, I wrote my code to automatically create a table. Selecting statement also works if you say yes in the terminal.

Selecting Statement: ![DateandValue](https://user-images.githubusercontent.com/56489878/106044150-a2309b00-60df-11eb-979a-e15eb4059ebc.png)

![Database HeidiSQL Preview](https://user-images.githubusercontent.com/56489878/104446876-589e6700-559b-11eb-8807-a46bde9e3c54.png) => here you can see the database. I used HeidiSQL and the service MariaDB. Now, the insert statement is not working - I will try to fix this as fast as possible. 

