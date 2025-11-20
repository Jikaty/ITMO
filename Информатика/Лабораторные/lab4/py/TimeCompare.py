import yaml
import tomli_w
import timeit

def time_counter():
    with open("../config/Laba4.yaml", "r", encoding='utf-8') as f:
        config = yaml.safe_load(f)

    with open("../../../../../config.toml", "wb") as f:
        tomli_w.dump(config, f)

total_time = timeit.timeit(time_counter, number=100)
print(total_time)

def time_counter2():
    def parse_yaml_manual(text):
        result = {}
        lines = text.strip().split('\n')
        stack = [(-1, result)]
        for i in range(len(lines)):
            line = lines[i]
            indent = len(line) - len(line.lstrip())
            content = line.lstrip()
            while indent <= stack[-1][0]:
                stack.pop()
            main = stack[-1][1]
            if content.startswith('- '):
                new_item = {}
                if isinstance(main, list):
                    main.append(new_item)
                stack.append((indent, new_item))
                item_content = content[2:].strip()
                if ':' in item_content:
                    key, val_str = item_content.split(':', 1)
                    val_str = val_str.strip()
                    if val_str == 'True':
                        parsed_val = True
                    elif val_str == 'False':
                        parsed_val = False
                    elif val_str.isdigit():
                        parsed_val = int(val_str)
                    else:
                        parsed_val = val_str
                    new_item[key] = parsed_val
            elif ':' in content:
                key, val_str = content.split(':', 1)
                key = key.strip()
                val_str = val_str.strip()
                if val_str:
                    if val_str == 'True':
                        parsed_val = True
                    elif val_str == 'False':
                        parsed_val = False
                    elif val_str.isdigit():
                        parsed_val = int(val_str)
                    else:
                        parsed_val = val_str
                    main[key] = parsed_val
                else:
                    new_obj = {}
                    if i + 1 < len(lines):
                        next_content = lines[i + 1].lstrip()
                        if next_content.startswith('- '):
                            new_obj = []
                    main[key] = new_obj
                    stack.append((indent, new_obj))
        return result

    def readfile(filename):
        with open(filename, 'r', encoding='utf-8') as f:
            return f.read()

    yaml_text = readfile('../config/Laba4.yaml')

    config = parse_yaml_manual(yaml_text)
    #print(config)

    file = open('../config/Laba4.toml', 'w', encoding='utf-8')
    i = 0
    stack = []
    iterator = iter(config)
    while i < len(config):
        ans = next(iterator)
        value = str(config[ans])
        if type(config[ans]) is list:
            x = 0
            stack.append(ans)
            # print(stack[0])
            while x < len(config[ans]):
                file.write(f'[[{ans}]]\n')
                iterator1 = iter(config[ans][x])

                u = 0
                # print(config[ans][x])
                while u < len(config[ans][x]):
                    dict = config[ans][x]
                    dopAns = next(iterator1)
                    if "{" not in str(dict[dopAns]):
                        dict[dopAns] = str(dict[dopAns])
                        if ":" in dict[dopAns]:
                            dict[dopAns] = dict[dopAns].replace(":", '=')
                        # print(dict[dopAns])
                        if dict[dopAns] == "True":
                            file.write(f'{dopAns}= true\n')
                        elif dict[dopAns] == "False":
                            file.write(f'{dopAns}= false\n')
                        elif dict[dopAns].isdigit():
                            file.write(f'{dopAns}= {dict[dopAns]}\n')
                        else:
                            file.write(f'{dopAns}= "{dict[dopAns]}"\n')
                    # print(dopAns,str(dict[dopAns]))
                    u += 1
                iterator3 = iter(config[ans][x])
                y = 0
                while y < len(config[ans][x]):
                    dict = config[ans][x]
                    dopAns = next(iterator3)
                    if "{" in str(dict[dopAns]):
                        sp = dict[dopAns]
                        iterator2 = iter(sp)
                        o = 0
                        sN = ''
                        sK = ''
                        lenSp = len(sp)
                        while o < len(sp):
                            if lenSp > 1:
                                spDic = next(iterator2)
                                spAns = str(sp[spDic])
                                # print(spAns)
                                if value == "True":
                                    sN += f'{spDic}= true\n'
                                elif value == "False":
                                    sN += f'{spDic}= false\n'
                                elif value.isdigit():
                                    sN += f'{spDic}= {spAns}\n'
                                else:
                                    sN += f'{spDic}= "{spAns}"\n'
                                lenSp = lenSp - 1
                                ss = sN + sK
                            else:
                                spDic = next(iterator2)
                                spAns = str(sp[spDic])
                                # print(spAns)
                                if value == "True":
                                    sN += f'{spDic}= true'
                                elif value == "False":
                                    sN += f'{spDic}= false'
                                elif value.isdigit():
                                    sN += f'{spDic}= {spAns}'
                                else:
                                    sN += f'{spDic}= "{spAns}"'
                                    ss = sN + sK
                                    file.write(f'[{stack[0]}.{dopAns}]\n{ss}\n')
                            o += 1
                    y += 1
                x += 1
        else:
            if value == "True":
                file.write(f'{ans}= true\n')
            elif value == "False":
                file.write(f'{ans}= false\n')
            elif value.isdigit():
                file.write(f'{ans}= {value}\n')
            else:
                file.write(f'{ans}= "{value}"\n')
        i += 1
total_time1 = timeit.timeit(time_counter2, number=100)
print(total_time1)