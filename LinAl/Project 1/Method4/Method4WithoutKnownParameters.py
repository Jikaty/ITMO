import numpy as np

P = list(map(float, input().split()))
P = np.array(P)

# Вершины тетраэдра
A = np.array([-2, 0, -4])
B = np.array([-1, 7, 1])
C = np.array([4, -8, -4])
D = np.array([1, -4, 6])


# Функция проверки грани
def granCheck(V1, V2, V3, opposite):
    normal = np.cross(V2 - V1, V3 - V1)
    dotOpp = np.dot(normal, opposite - V1)
    dotP = np.dot(normal, P - V1)
    if dotOpp < 0:
        normal = -normal
        dotP = -dotP
    if dotP >= 0:
        return True
    else:
        return False

gran1 = granCheck(B, C, D, A)  # грань BCD, вершина A напротив
gran2 = granCheck(A, C, D, B)  # грань ACD, вершина B напротив
gran3 = granCheck(A, B, D, C)  # грань ABD, вершина C напротив
gran4 = granCheck(A, B, C, D)  # грань ABC, вершина D напротив

if gran1 and gran2 and gran3 and gran4:
    print("YES")
else:
    print("NO")