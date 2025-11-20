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
                elif val_str.lstrip('-').isdigit():
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
                elif val_str.lstrip('-').isdigit():
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

yaml_text = readfile('Laba4.yaml')

config = parse_yaml_manual(yaml_text)


def xml_base(config, filename):
    def escape_text(text):
        if text is None:
            return ""
        text = str(text)
        return text

    def dict_to_xml(data, indent=0):
        spaces = "  " * indent
        result = []
        for key, value in data.items():
            tag = str(key).replace(' ', '_')
            if isinstance(value, dict):
                result.append(f"{spaces}<{tag}>")
                result.append(dict_to_xml(value, indent + 1))
                result.append(f"{spaces}</{tag}>")
            elif isinstance(value, list):
                result.append(f"{spaces}<{tag}>")
                for item in value:
                    if isinstance(item, dict):
                        result.append(f"{spaces}  <entry>")
                        result.append(dict_to_xml(item, indent + 2))
                        result.append(f"{spaces}  </entry>")
                result.append(f"{spaces}</{tag}>")
            else:
                escaped = escape_text(value)
                result.append(f"{spaces}<{tag}>{escaped}</{tag}>")

        return "\n".join(result)


    xml_content = "<config>\n"
    xml_content += dict_to_xml(config, 1)
    xml_content += "\n</config>"

    with open(filename, 'w', encoding='utf-8') as file:
        file.write(xml_content)

xml_base(config, 'Test.xml')
