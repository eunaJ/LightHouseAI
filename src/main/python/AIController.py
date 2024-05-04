from flask import Flask, request, jsonify
import json

app = Flask(__name__)

def process_command(command):
    if command["command"] == "run_script":
        script_path = command["script_path"]
        args = command["args"]
        options = command["options"]
        print("command")
        return "Script executed successfully"
    elif command["command"] == "test":
        return "Test command executed successfully"
    
@app.route('/api/v1/run_command', methods=['POST'])
def command():
    command = request.json
    response = process_command(command)
    print(response)
    return jsonify({"response": response})

if __name__ == '__main__':
    app.run(port=5000)