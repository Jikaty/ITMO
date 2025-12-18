import numpy as np

P = np.array([float(x) for x in input().split()])

def mixed_numpy(v1, v2, v3):
    return np.dot(v1, np.cross(v2, v3))

def znak(i):
    return 1 if i > 1e-10 else (-1 if i < -1e-10 else 0)

# Вершины тетраэдра (ABCD)
A = np.array([-2, 0, -4])
B = np.array([-1, 7, 1])
C = np.array([4, -8, -4])
D = np.array([1, -4, 6])

def proverka():
    # Грань BCD (противоположная вершина A)
    v1 = C - B
    v2 = D - B
    p1_P = mixed_numpy(v1, v2, P - B)
    p1_ref = mixed_numpy(v1, v2, A - B)

    # Грань ACD (противоположная вершина B)
    v1 = C - A
    v2 = D - A
    p2_P = mixed_numpy(v1, v2, P - A)
    p2_ref = mixed_numpy(v1, v2, B - A)

    # Грань ABD (противоположная вершина C)
    v1 = B - A
    v2 = D - A
    p3_P = mixed_numpy(v1, v2, P - A)
    p3_ref = mixed_numpy(v1, v2, C - A)

    # Грань ABC (противоположная вершина D)
    v1 = B - A
    v2 = C - A
    p4_P = mixed_numpy(v1, v2, P - A)
    p4_ref = mixed_numpy(v1, v2, D - A)

    print(f"Грань BCD: P={p1_P:.2f}, A={p1_ref:.2f}")
    print(f"Грань ACD: P={p2_P:.2f}, B={p2_ref:.2f}")
    print(f"Грань ABD: P={p3_P:.2f}, C={p3_ref:.2f}")
    print(f"Грань ABC: P={p4_P:.2f}, D={p4_ref:.2f}")

    # Проверяем совпадение знаков для граней
    if (znak(p1_P) == znak(p1_ref) and
            znak(p2_P) == znak(p2_ref) and
            znak(p3_P) == znak(p3_ref) and
            znak(p4_P) == znak(p4_ref)):
        print("Точка внутри тетраэдра")
        return 1
    else:
        # Проверка на принадлежность граням
        signs = [znak(p1_P) * znak(p1_ref), znak(p2_P) * znak(p2_ref),
                 znak(p3_P) * znak(p3_ref), znak(p4_P) * znak(p4_ref)]
        if 0 in signs:
            print("На грани, ребре или совпадает с вершиной")
            return 0
        print("Точка снаружи")
        return 0

proverka()