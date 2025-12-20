import re
from re import *

# №2 - 2

ss = "ashdh asds dd 014 * 440 = 320 SAD as 667as 8asd7 9a6 75d 550 - 120 = 33 880 + 1230 = 2130"
ss1 = "adrfsdghjq3 7q734 777 + 123 = 123"
ss2 = "asd ha sa wer 555 _ 99 = 0 0987 - 123 = 11"
ss3 = " 65 * 1235 =33 8877 hoi8 67 * 2 = 134"
ss4 = " kkashd gthn 77 - 70 = 7"


match = re.findall(r'[1-9](?:\d)*\s[+*-]+\s[1-9](?:\d)*\s[=]\s[1-9](?:\d)*',ss)
for _ in range(0,len(match)):
    s = match[_]
    match1 = re.findall(r'[+*-]+', s)
    #print(match1)
    res = ''
    if match1[0] == '+':
        s = s.replace('=','').replace('+','')
        s = s.split('  ')
        res += str(5*int(s[0])**3 - 13) + ' + '
        res += str(5*int(s[1])**3 - 13) + ' = '
        res += str(5*int(s[2])**3 - 13)
    if match1[0] == '-':
        s = s.replace('=', '').replace('-', '')
        s = s.split('  ')
        res += str(5 * int(s[0]) ** 3 - 13) + ' - '
        res += str(5 * int(s[1]) ** 3 - 13) + ' = '
        res += str(5 * int(s[2]) ** 3 - 13)
    if match1[0] == '*':
        s = s.replace('=', '').replace('*', '')
        s = s.split('  ')
        res += str(5 * int(s[0]) ** 3 - 13) + ' * '
        res += str(5 * int(s[1]) ** 3 - 13) + ' = '
        res += str(5 * int(s[2]) ** 3 - 13)
    print(res)
print(match)