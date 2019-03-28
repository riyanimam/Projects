#So, for the challenege, I will use Python and I select problem number 2!

#Write a function in any language that multiplies any two integers 
#without using the multiplication or division operators. 
#Aim for the most the elegant code you can.

def multiply(x,y):
    counter = 0
    total = 0
    if (x == 0 or y == 0):
        return total
    elif (y < 0):
        while (counter != y):
            total = total - x 
            counter = counter - 1
        return total
    elif (y > 0):
        while (counter != y):
            total = x + total 
            counter = counter + 1
        return total

print(multiply(-9,4))