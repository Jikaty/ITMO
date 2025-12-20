import re
from re import *

#№3 - 0


ss = " - 30 14 * * * asdasn hj - 0/5 * * * * asdasd 33123 4324 - 1`23 `1123 - 3 333 3 567 776 - 0 0 1 1 *"
ss1 = " - 54 1234 ))) 98*& 77 - 0/9 * * 12 * - 7 8 1 2 1"


minute = r'(?:- [0-9]|- [1-5][0-9]|- [*]|- 0/[1-9])'
hours = r'(?:1[0-9]|2[0-3]|[0-9]|[*])'
days = r'(?:[1-9]|[1-2][0-9]|31|[*])'
month = r'(?:[1-9]|1[0-2]|[*])'
weekday = r'(?:[0-6]|[*])'

matches = re.findall(rf'({minute})\s({hours})\s({days})\s({month})\s({weekday})', ss1)


result = [' '.join(i) for i in matches]

print(result)