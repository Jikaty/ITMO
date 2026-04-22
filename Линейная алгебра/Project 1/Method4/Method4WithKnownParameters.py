import numpy as np

x,y,z = [float(x) for x in input().split()]

# Неравенства для нормалей (уже ориентированных внутрь):
# Нормаль к грани BCD: (-130, -35, -25)
cond1 = -130*x - 35*y - 25*z + 140 >= 0

# Нормаль к грани ACD: (80, 60, 0)
cond2 = 80*x + 60*y + 160 >= 0

# Нормаль к грани ABD: (90, 5, -25)
cond3 = 90*x + 5*y - 25*z + 80 >= 0

# Нормаль к грани ABC: (-40, -30, 50)
cond4 = -40*x - 30*y + 50*z + 120 >= 0

print("YES" if cond1 and cond2 and cond3 and cond4 else "NO")