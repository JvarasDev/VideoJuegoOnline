import re

with open('c:\\Users\\juanm\\Desktop\\fullstack_git\\VideoJuegoOnline\\usuario-service\\src\\main\\java\\cl\\videojuego\\usuario_service\\controller\\UsuarioController.java', 'r', encoding='utf-8') as f:
    code = f.read()

def replacer(match):
    implementation = match.group(1)
    example_val = match.group(2)
    
    result = 'content = @Content(\n                mediaType = "application/json",\n                schema = @Schema(implementation = ' + implementation + ')'
    
    if example_val:
        # Reformat the escaped JSON string into a text block
        # Replace escaped quotes with normal quotes, and \n with newlines
        clean_json = example_val.replace('\\"', '"').replace('\\n', '\n')
        
        # Add indentation to the JSON block
        indented_json = '\n'.join(['                    ' + line if line.strip() else line for line in clean_json.split('\n')])
        
        result += ',\n                examples = @ExampleObject(\n                    value = """\n' + indented_json + '\n                    """\n                )'
    
    result += '\n            )'
    return result

new_code = re.sub(r'content = @Content\(mediaType = MediaType\.APPLICATION_JSON_VALUE, schema = @Schema\(implementation = (.*?)\)(?:, examples = @ExampleObject\(value = "(.*?)"\))?\)', replacer, code)

with open('c:\\Users\\juanm\\Desktop\\fullstack_git\\VideoJuegoOnline\\usuario-service\\src\\main\\java\\cl\\videojuego\\usuario_service\\controller\\UsuarioController.java', 'w', encoding='utf-8') as f:
    f.write(new_code)

print('Success')
