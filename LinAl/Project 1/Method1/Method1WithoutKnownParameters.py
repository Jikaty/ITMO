import numpy as np

# Читаем точку P
P = list(map(float, input().split()))
P = np.array(P)

# Вершины тетраэдра
A = np.array([-2, 0, -4])
B = np.array([-1, 7, 1])
C = np.array([4, -8, -4])
D = np.array([1, -4, 6])

# Функция объёма тетраэдра
def vT(p1, p2, p3, p4):
    v1 = p2 - p1
    v2 = p3 - p1
    v3 = p4 - p1
    return np.dot(v1, np.cross(v2, v3))

# Вычисляем все 5 объёмов
V = vT(A, B, C, D)
V1 = vT(P, B, C, D)
V2 = vT(A, P, C, D)
V3 = vT(A, B, P, D)
V4 = vT(A, B, C, P)

# Проверяем, что все объёмы одного знака (или ноль)
allPlus = (V >= 0 and V1 >= 0 and V2 >= 0 and V3 >= 0 and V4 >= 0)
allMinus = (V <= 0 and V1 <= 0 and V2 <= 0 and V3 <= 0 and V4 <= 0)

if allPlus or allMinus:
    print("YES")
else:
    print("NO")