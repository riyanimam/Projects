#So, for the challenege, I will use Python and I select problem number 2!

#Write a function in any language that multiplies any two integers 
#without using the multiplication or division operators. 
#Aim for the most the elegant code you can.

def multiply(x,y):
    counter = 0
    total = 0
    #A '0 times whatever' case
    if (x == 0 or y == 0):
        return total
    else:
        while (counter != y):
            #If the y-value is negative, this case will return a negative value
            if (y < 0):
                total = total - x 
                counter = counter - 1
            #If the y-value is positive OR the x-value is either positive or
            #negative, this will return a value based on which is which
            #(It's black magic, I swear)
            elif (y > 0):
                total = x + total 
                counter = counter + 1
        return total

print(multiply(9,0))
print(multiply(0,2))
print(multiply(-9,3))
print(multiply(2,-4))

#A little write-up!
#Elegance in code, to me at least, is being able to explain and
#dissect what the code is trying to do without much confusion.
#If code is commented neatly, concisely, and with the occasional
#joke or two, then that is elegant in my eyes. I can get to work
#and enjoy myself almost immediately. I saw solutions where a good
#amount of recursion was used and people only used about 10 lines of
#code, but I could not trace the code back as quickly as I could with
#a blatant solution. Definitely not the prettiest pieces of code I have 
#written, but it is readable. In the end, readability and understandability
#is pure elegance to me.