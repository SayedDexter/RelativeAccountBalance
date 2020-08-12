# RelativeAccountBalance
As an user will enter following details

1) Input file location - existing data file against which operation will be performed
2) Account Number - This should be from account number
3) Starting date - Start date and time criteria
4) Ending date - End date and time criteria

Option available to run the application

1) Copy existing jar and run following command on command prompt
java -cp RelativeAccountBalance.jar main.RelativeAccountBalanceCalculatorPlease enter input file location
file location
account number
start date
end date

for example: 
java -cp RelativeAccountBalance.jar main.RelativeAccountBalanceCalculator
Please enter input file location
d:\input.csv --- input file which will work as dictionary
Please enter account number
ACC334455
Please enter start date and time
20/10/2018 12:00:00
Please enter start date and time
20/10/2018 19:00:00

2) Import project in IDE and run : RelativeAccountBalanceCalculator
file location
account number
start date
end date

for example: On console of IDE 

Please enter input file location
d:\input.csv
Please enter account number
ACC334455
Please enter start date and time
20/10/2018 12:00:00
Please enter start date and time
20/10/2018 19:00:00


Assumption :

1) We will always have formatted file and account will be used is fromAccountId only.
2) One transaction will have only one Reversal with same amount

Running build 
mvn clean install
