from flask import Flask, request, jsonify
import json
import Chatbot as chatbot

app = Flask(__name__)

def process_command(command):
    if command["command"] == "run_script":
        msg = command["msg"]
        print(msg)
        ans = chatbot.generate_response(msg)
        return ans
    if(command["command"] == "travelList"):
        id = command["id"]
        print("id: ", id)
        ans = chatbot.find_using_id(id)
        return ans
    if(command["command"] == "test"):
        msg = command["msg"]
        ans = chatbot.agenerate_response(msg)
        return ans
    
@app.route('/api/v1/run_command', methods=['POST'])
def command():
    command = request.json
    response = process_command(command)
    print(response)
    return jsonify({"response":response})

if __name__ == '__main__':
    app.run(port=5000)