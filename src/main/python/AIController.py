from flask import Flask, request, jsonify
import json
import AIMain as ai

app = Flask(__name__)

def process_command(command):
    if command["command"] == "run_script":
        script_path = command["script_path"]
        args = command["args"]
        options = command["options"]
        print("command")
        return "Script executed successfully"
    elif command["command"] == "train":
        ai.dataTrain()
        return "Test command executed successfully"
    elif command["command"] == "predict":
        #args에 데이터를 넣어서 predict 실행
        birth = command["birth"]
        reg_id = command["reg_id"]
        serving = command["serving"]
        title = command["title"]
        travel_expense = command["travel_expense"]
        star = command["star"]
        region_name = command["region_name"]
        constituency = command["constituency"]
        loc_x = command["loc_x"]
        loc_y = command["loc_y"]
        result = ai.dataPredict(birth, reg_id, serving, title, travel_expense, star, region_name, constituency, loc_x, loc_y)
        print(result)
        return "Predict command executed successfully"
    
@app.route('/api/v1/run_command', methods=['POST'])
def command():
    command = request.json
    response = process_command(command)
    print(response)
    return jsonify({"response": response})

if __name__ == '__main__':
    app.run(port=5000)