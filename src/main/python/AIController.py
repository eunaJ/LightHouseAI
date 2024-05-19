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
    if(command["command"] == "test"):
        msg = command["msg"]
        ans = chatbot.agenerate_response(msg)
        return ans
    """
    elif command["command"] == "train":
        ai.dataTrain()
        return "Train command executed successfully"
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
    elif command["command"] == "cafe_train":
        ai.cafeTrain()
        return "cafe train command executed successfully"
    elif command["command"] == "cafe_predict":
        #args에 데이터를 넣어서 predict 실행
        user_id = command["user_id"]
        serving = command["serving"]
        constituency_id = command["constituency_id"]
        rank_s = command["rank_s"]
        rank_e = command["rank_e"]
        result = ai.cafePredict(user_id, serving, constituency_id, rank_s, rank_e)
        print(result)
        return result
    """
    
@app.route('/api/v1/run_command', methods=['POST'])
def command():
    command = request.json
    response = process_command(command)
    print(response)
    return jsonify({"response":response})

if __name__ == '__main__':
    app.run(port=5000)