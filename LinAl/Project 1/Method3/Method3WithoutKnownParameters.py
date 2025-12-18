import numpy as np
P = np.array([float(x) for x in input().split()])
A = np.array([-2, 0, -4])
B = np.array([-1, 7, 1])
C = np.array([4, -8, -4])
D = np.array([1, -4, 6])

def znak(x):
    return 1 if x > 0 else (-1 if x < 0 else 0)
def check_point():
    # Грань A1A2A3 (против D)
    n1 = np.cross(B - A, C - A)
    D1 = -np.dot(n1, A)
    val_P1 = np.dot(n1, P) + D1
    val_D_1 = np.dot(n1, D) + D1

    # Грань A1A2A4 (против C)
    n2 = np.cross(B - A, D - A)
    D2 = -np.dot(n2, A)
    val_P2 = np.dot(n2, P) + D2
    val_C_2 = np.dot(n2, C) + D2

    # Грань A1A3A4 (против B)
    n3 = np.cross(C - A, D - A)
    D3 = -np.dot(n3, A)
    val_P3 = np.dot(n3, P) + D3
    val_B_3 = np.dot(n3, B) + D3

    # Грань A2A3A4 (против A)
    n4 = np.cross(C - B, D - B)
    D4 = -np.dot(n4, B)
    val_P4 = np.dot(n4, P) + D4
    val_A_4 = np.dot(n4, A) + D4

    # Проверяем все условия
    if ((znak(val_P1) == znak(val_D_1) or val_P1==0) and
            (znak(val_P2) == znak(val_C_2) or val_P2 == 0) and
            (znak(val_P3) == znak(val_B_3) or val_P3 ==0) and
            (znak(val_P4) == znak(val_A_4) or val_P4 == 0)):
        print("YES")
    else:
        print("NO")

check_point()

