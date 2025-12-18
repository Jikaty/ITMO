import numpy as np
x,y,z = [float(x) for x in input().split()]
cnt = 0
if 40*x + 30*y - 50*z - 120 <= 0:
    cnt += 1
if 90*x + 5*y - 25*z + 80 >= 0:
    cnt += 1
if 80*x + 60*y + 160 >= 0:
    cnt += 1
if -130*x - 35*y - 25*z + 140 >= 0:
    cnt += 1
if cnt == 4: print("YES")
else: print("NO")