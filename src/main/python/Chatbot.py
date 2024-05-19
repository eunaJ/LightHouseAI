import openai
import pymysql
import yaml
import json
import pandas as pd
import os
import jsonify
from sqlalchemy import create_engine

file_path = os.path.join(os.path.dirname(__file__), '..', 'resources', 'application-dev.yml')

with open(file_path, 'r') as stream:
    config = yaml.safe_load(stream)

openai.api_key = config['openai']['api']['key']

conn = pymysql.connect( 
    host='localhost',
    user=config['spring']['datasource']['username'],
    password=str(config['spring']['datasource']['password']).encode('utf-8'),
    port=3306,
    db='lighthouseAI'
)

def get_db_connection():
    connection = conn.cursor()
    return connection

def fetch_places(table_name):
    connection = get_db_connection()
    cursor = connection.cursor(dictionary=True)
    cursor.execute(f'SELECT * FROM {table_name}')
    places = cursor.fetchall()
    cursor.close()
    connection.close()
    return places

def agenerate_response(msg, model = "gpt-3.5-turbo"):
    data = msg
    user_input = data

    # 각 테이블의 데이터를 가져오기
    categories = ['TB_CAFE', 'TB_RESTAURANT', 'TB_SHOPPINGMALL', 'TB_TOUR_LIST', 'TB_TRAVEL_VISITOR_OTHER_SERVICE']
    all_places_info = []

    for category in categories:
        places = fetch_places(category)
        places_info = "\n".join([f"{place['name']} - {place['description']} in {place['location']}" for place in places])
        all_places_info.append(f"{category.capitalize()}:\n{places_info}")

    combined_places_info = "\n\n".join(all_places_info)

    # ChatGPT에 질문을 생성
    prompt = f"사용자가 다음과 같은 여행지 추천을 요청했습니다: {user_input}\n여기 다양한 장소 목록이 있습니다:\n{combined_places_info}\n어떤 장소를 추천하시겠습니까?"

    response = openai.Completion.create(
        model="text-davinci-003",
        prompt=prompt,
        max_tokens=150
    )

    recommendation = response.choices[0].text.strip()
    
    return jsonify({'recommendation': recommendation})

    dbconn = conn.cursor()

    


def generate_response(msg):
    model = "gpt-3.5-turbo"
    #TB_TRAVEL_VISITOR_CAFE에서 price가 낮은 데이터를 추출
    dbconn = conn.cursor()
    dbconn.execute("SELECT * FROM TB_TRAVEL_VISITOR_CAFE ORDER BY price ASC")
    result = dbconn.fetchall()

    df_travel_visitor_cafe = pd.DataFrame(result)
    df_travel_visitor_cafe.columns = [
        'id',
        'createdAt',
        'modifiedAt',
        'closetime',
        'image_url',
        'location',
        'menu',
        'opentime',
        'price',
        'cafe_id',
        'travel_id',
        'user_id', 
        'content',       
    ]

    #cafe_id가 중복되는 데이터를 제거
    df_travel_visitor_cafe = df_travel_visitor_cafe.drop_duplicates('cafe_id')

    #travel_id를 10개 추출
    travel_id = df_travel_visitor_cafe['travel_id'].tolist()

    #TB_TRAVEL에서 travel_id가 travel_id에 포함되는 데이터를 추출
    dbconn.execute(f"SELECT * FROM TB_TRAVEL WHERE id IN {tuple(travel_id)}")
    result = dbconn.fetchall()

    df_travel = pd.DataFrame(result)
    df_travel.columns = [
        'id',
        'createdAt',
        'modifiedAt',
        'folderName',
        'image_url',
        'serving',
        'star',
        'title',
        'travel_expense',
        'constituency_id',
        'user_id',
    ]

    print(df_travel)

    #df_travel을 json으로 변환
    df_travel_json = df_travel.to_json(orient='records')

    return df_travel_json


