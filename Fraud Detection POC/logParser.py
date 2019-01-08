import re
import json
#import time
import itertools

#start_time = time.time()

#file = open("payments/readpayments.txt", 'r')
file = open("payments/payments.txt", 'r')
generalRegex = ' - \[-*\w(.*)\.net(.*)\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}'
ipRegex = '\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}'
userIDRegex = '-*\w(.*)\.net'
jsonRegex = '\{(.*)\}'
dictOfAccts = {}

#Reads each line of passed in file
lines = file.readlines()

#The object where we hold all values associated with an account
class accountInfo:
    def __init__(self, numOfID, numOfIP, numOfPay, avgPay, numOfCC):
        self.numOfID = numOfID
        self.numOfIP = numOfIP
        self.numOfPay = numOfPay
        self.avgPay = avgPay
        self.numOfCC = numOfCC
    
    #Gets UserID
    def getID(self):
        return self.numOfID
    
    #Gets User's IP
    def getIP(self):
        return self.numOfIP
    
    #Gets the amount paid for that log entry
    def getPay(self):
        return self.numOfPay
    
    #Gets the amount paid for that log entry (averages out amount paid)
    def getAvgPay(self):
        return self.avgPay
    
    #Gets the credit card number for associated account
    def getCC(self):
        return self.numOfCC
    
    #Makes any User IDs in the object a list
    def addID(self, user):
        temp = self.numOfID
        if isinstance(user, list):
            user.append(temp)
            self.numOfID = user
        else:
            lister = []
            lister.append(user)
            lister.append(temp)
            self.numOfID = lister
    
    #Makes any User IPs in the object a list
    def addIP(self, user):
        temp = self.numOfIP
        if isinstance(user, list):
            user.append(temp)
            self.numOfIP = user
        else:
            lister = []
            lister.append(user)
            lister.append(temp)
            self.numOfIP = lister
        
    #Makes any Amount Paid in the object a list
    def addPay(self, user):
        temp = self.numOfPay
        if isinstance(user, list):
            user.append(temp)
            self.numOfPay = user
        else:
            lister = []
            lister.append(user)
            lister.append(temp)
            self.numOfPay = lister
        
    #Makes any Amount Paid in the object a list
    def addAvgPay(self, user):
        temp = self.avgPay
        if isinstance(user, list):
            user.append(temp)
            self.avgPay = user
        else:
            lister = []
            lister.append(user)
            lister.append(temp)
            self.avgPay = lister
        
    #Makes any credit cards in the object a list
    def addCC(self, user):
        temp = self.numOfCC
        if isinstance(user, list):
            user.append(temp)
            self.numOfCC = user
        else:
            lister = []
            lister.append(user)
            lister.append(temp)
            self.numOfCC = lister
            
    #Counts up the list of items in list
    def idCounter(self):
        temp = self.numOfID
        tempCounter = 1
        if isinstance(temp, str):
            temp = 1
        elif isinstance(temp, list):
            for a, b in itertools.combinations(temp, 2):
                if(a == b):
                    tempCounter = 1
                elif(a != b):
                    tempCounter = tempCounter + 1
            temp = tempCounter
        self.numOfID = temp
    
    #Counts up the list of items in list
    def ipCounter(self):
        temp = self.numOfIP
        tempCounter = 1
        if isinstance(temp, str):
            temp = 1
        elif isinstance(temp, list):
            for a, b in itertools.combinations(temp, 2):
                if(a == b):
                    tempCounter = 1
                elif(a != b):
                    tempCounter = tempCounter + 1
            temp = tempCounter
        self.numOfIP = temp
    
    #Counts up the list of items in list
    def payCounter(self):
        temp = self.numOfPay
        tempCounter = 1
        if isinstance(temp, float) or isinstance(temp, str):
            if temp == 'NoPaymentsKey' or temp == 'NoTendered':
                temp = 0
            else:
                temp = 1
        elif isinstance(temp, list):
            for a, b in itertools.combinations(temp, 2):
                if((a == 'NoPaymentsKey' or a == 'NoTendered') and (b == 'NoPaymentsKey' or b == 'NoTendered')):
                    tempCounter =  tempCounter + 0
                elif(a == b):
                    if(tempCounter == 1):
                        tempCounter = tempCounter + 1
                    else:
                        tempCounter = 1
                elif(a != b):
                    tempCounter = tempCounter + 1
            temp = tempCounter
        self.numOfPay = temp
            
    #Counts up the list of items in list AND averages the payment amounts       
    def avgPayCounter(self):
        temp = self.avgPay
        tempCounter = 0.0
        if isinstance(temp, float) or isinstance(temp, str):
            if temp == 'NoPaymentsKey' or temp == 'NoEnding':
                temp = 0.0
        elif isinstance(temp, list):
            tempLen = len(temp)
            for a in range(len(temp)):
                if(temp[a] == 'NoPaymentsKey' or temp[a] == 'NoEnding'):
                    temp[a] = 0.0
                    if(tempLen > 1):
                        tempLen = tempLen - 1
                tempCounter = tempCounter + temp[a]
            temp = tempCounter / tempLen
        self.avgPay = temp
    
    #Counts up the list of items in list    
    def ccCounter(self):
        temp = self.numOfCC
        tempCounter = 1
        if isinstance(temp, str):
            if temp == 'NoPaymentsKey' or temp == 'NoCC':
                temp = 0
            else:
                temp = 1
        elif isinstance(temp, list):
            for a, b in itertools.combinations(temp, 2):
                if((a == 'NoPaymentsKey' or a == 'NoCC') and (b == 'NoPaymentsKey' or b == 'NoCC')):
                    tempCounter =  tempCounter + 0
                elif(a == b):
                    if(tempCounter == 1):
                        tempCounter = tempCounter + 1
                    else:
                        tempCounter = 1
                elif(a != b):
                    tempCounter = tempCounter + 1
            temp = tempCounter
        self.numOfCC = temp
        
    #Allows us to print out the objects in a "string" form (Used for testing purposes)
    def __repr__(self):
        return "%s, %s, %s, %s, %s" % (self.numOfID, self.numOfIP, self.numOfPay, self.avgPay, self.numOfCC)
    def __str__(self):
        return "%s, %s, %s, %s, %s" % (self.numOfID, self.numOfIP, self.numOfPay, self.avgPay, self.numOfCC)


for x in range(len(lines)):
    
    #Reads log as a list entry
    lister = lines[x]
    #Isolates where the IP Address and User ID is located
    ipAndUser = re.search(generalRegex, lister)
    ipAndUserText = ipAndUser.group(0)
    
    #Gets all User IDs
    userID = re.search(userIDRegex, ipAndUserText)
    #Gets all IP Addresses 
    ipAddress = re.search(ipRegex, ipAndUserText)
    
    #Isolates JSON object
    logJson = re.search(jsonRegex, lister)
    jsonResponse = logJson.group(0)
    
    #Replaces and fixes invalid JSON
    jsonResponse = jsonResponse.replace("****MASKED****","\"****MASKED****\"")
    
    #Converts log strings into actual JSON
    jsonedLog = json.loads(jsonResponse)
    
    #Reads each individual log statement and grabs the data needed
    try:
        accountNumber = jsonedLog['payments'][0]['accountNumber']
        usableUserID = userID.group(0)
        usableIP = ipAddress.group(0)
        paymentMade = float(jsonedLog['payments'][0]['amountTendered'])
        endingBalance = float(jsonedLog['payments'][0]['balanceEnd'])
        usableCC = jsonedLog['payments'][0]['cardNumberEncrypted']

    #Error handling/ exception catching
    except Exception as error:
        if str(error) == "\'payments\'":
            accountNumber = 'None'
            paymentMade = "NoPaymentsKey"
            endingBalance = "NoPaymentsKey"
            usableCC = "NoPaymentsKey"
            usableUserID = userID.group(0)
            usableIP = ipAddress.group(0)
        elif str(error) == "\'amountTendered\'":
            try:
                paymentMade = "NoTendered"
                endingBalance = float(jsonedLog['payments'][0]['balanceEnd'])
                usableCC = jsonedLog['payments'][0]['cardNumberEncrypted']
            except Exception as error1:
                if str(error1) == "\'balanceEnd\'":
                    try:
                        endingBalance = "NoEnding"
                        usableCC = jsonedLog['payments'][0]['cardNumberEncrypted']
                    except Exception as error2:
                        if str(error2) == "\'cardNumberEncrypted\'":
                            usableCC = "NoCC"
                elif str(error1) == "\'cardNumberEncrypted\'":
                    usableCC = "NoCC"
        elif str(error) == "\'balanceEnd\'":
            try:
                endingBalance = "NoEnding"
                usableCC = jsonedLog['payments'][0]['cardNumberEncrypted']
            except Exception as error1:
                if str(error1) == "\'cardNumberEncrypted\'":
                    usableCC = "NoCC"
        elif str(error) == "\'cardNumberEncrypted\'":
            usableCC = "NoCC"
    
    #Creating 'accountInfo' object and assigning values generated above
    accountList = accountInfo(usableUserID, usableIP, paymentMade, endingBalance, usableCC)
    
    #Searches if there is already an account with the same account number and combines the information under one account
    if str(accountNumber) in dictOfAccts:
        if str(accountNumber) != 'None':
            accountList.addID(dictOfAccts[accountNumber].getID())
            accountList.addIP(dictOfAccts[accountNumber].getIP())
            accountList.addPay(dictOfAccts[accountNumber].getPay())
            accountList.addAvgPay(dictOfAccts[accountNumber].getAvgPay())
            accountList.addCC(dictOfAccts[accountNumber].getCC())
    
    #Makes the accountList variable a value to our account number key
    dictOfAccts[accountNumber] = accountList
    
#Counts the number of elements in each section of our object and returns an integer
#Basically preping the data for the model
for k in dictOfAccts:
    dictOfAccts[str(k)].idCounter()
    dictOfAccts[str(k)].ipCounter()
    dictOfAccts[str(k)].payCounter()
    dictOfAccts[str(k)].avgPayCounter()
    dictOfAccts[str(k)].ccCounter()

#Viewing data changes and testing time efficiency of parser
print(dictOfAccts)
print(" ")  
#print(time.time()-start_time)  

#Closes out of file that was being parsed   
file.close()