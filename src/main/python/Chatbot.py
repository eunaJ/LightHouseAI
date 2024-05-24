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

def cafe_cheap(location):
    #TB_TRAVEL_VISITOR_CAFE에서 location이 location이며 price가 낮은 데이터를 추출
    #dbconn = conn.cursor()
    #dbconn.execute(f"SELECT * FROM TB_TRAVEL_VISITOR_CAFE WHERE location = '{location}' ORDER BY price ASC")
    #TB_TRAVEL_VISITOR_CAFE에서 price가 낮은 데이터를 추출
    dbconn = conn.cursor()
    dbconn.execute(f"SELECT * FROM TB_TRAVEL_VISITOR_CAFE WHERE location like '%{location}%' ORDER BY price ASC")
    result = dbconn.fetchall()

    print(result)

    df_travel_visitor_cafe = pd.DataFrame(result)
    df_travel_visitor_cafe.columns = [
        'id',
        'createdAt',
        'modifiedAt',
        'closetime',
        'content',
        'image_url',
        'location',
        'menu',
        'opentime',
        'price',
        'cafe_id',
        'travel_id',
        'user_id', 
    ]

    #cafe_id가 중복되는 데이터를 제거
    df_travel_visitor_cafe = df_travel_visitor_cafe.drop_duplicates('cafe_id')

    #travel_id를 10개 추출
    travel_id = df_travel_visitor_cafe['travel_id'].tolist()

    #travel_id를 10개 추출
    travel_id = travel_id[:10]

    return travel_id

def restaurant_cheap(location):
    #TB_TRAVEL_VISITOR_RESTAURANT에서 price가 낮은 데이터를 추출
    dbconn = conn.cursor()
    dbconn.execute(f"SELECT * FROM TB_TRAVEL_VISITOR_RESTAURANT WHERE location like '%{location}%' ORDER BY price ASC")
    result = dbconn.fetchall()

    print(result)

    df_travel_visitor_restaurant = pd.DataFrame(result)
    df_travel_visitor_restaurant.columns = [
        'id',
        'createdAt',
        'modifiedAt',
        'closetime',
        'content',
        'image_url',
        'location',
        'menu',
        'opentime',
        'price',
        'restaurant_id',
        'travel_id',
        'user_id', 
    ]

    #restaurant_id가 중복되는 데이터를 제거
    df_travel_visitor_restaurant = df_travel_visitor_restaurant.drop_duplicates('restaurant_id')

    #travel_id를 추출
    travel_id = df_travel_visitor_restaurant['travel_id'].tolist()

    #travel_id를 10개 추출
    travel_id = travel_id[:10]

    return travel_id

def shopping_cheap(location):
    #TB_TRAVEL_VISITOR_SHOPPING에서 price가 낮은 데이터를 추출
    dbconn = conn.cursor()
    dbconn.execute(f"SELECT * FROM TB_TRAVEL_VISITOR_SHOPPINGMALL WHERE location like '%{location}%' ORDER BY price ASC")
    result = dbconn.fetchall()

    df_travel_visitor_shopping = pd.DataFrame(result)
    df_travel_visitor_shopping.columns = [
        'id',
        'createdAt',
        'modifiedAt',
        'closetime',
        'content',
        'image_url',
        'location',
        'opentime',
        'price',
        'shopping_id',
        'travel_id',
        'user_id', 
    ]

    #shopping_id가 중복되는 데이터를 제거
    df_travel_visitor_shopping = df_travel_visitor_shopping.drop_duplicates('shopping_id')

    #travel_id를 추출
    travel_id = df_travel_visitor_shopping['travel_id'].tolist()

    #travel_id를 10개 추출
    travel_id = travel_id[:10]

    return travel_id

def tourist_cheap(location):
    #TB_TRAVEL_VISITOR_TOURIST에서 price가 낮은 데이터를 추출
    dbconn = conn.cursor()
    dbconn.execute(f"SELECT * FROM TB_TRAVEL_VISITOR_TOUR_LIST WHERE location like '%{location}%' ORDER BY price ASC")
    result = dbconn.fetchall()

    df_travel_visitor_tourist = pd.DataFrame(result)
    df_travel_visitor_tourist.columns = [
        'id',
        'createdAt',
        'modifiedAt',
        'closetime', 
        'content',
        'image_url',
        'location',
        'opentime',
        'price',
        'tourist_id',
        'travel_id',
        'user_id',       
    ]

    #tourist_id가 중복되는 데이터를 제거
    df_travel_visitor_tourist = df_travel_visitor_tourist.drop_duplicates('tourist_id')

    #travel_id를 추출
    travel_id = df_travel_visitor_tourist['travel_id'].tolist()

    #travel_id를 10개 추출
    travel_id = travel_id[:10]

    return travel_id

def other_cheap(location):
    #TB_TRAVEL_VISITOR_OTHER에서 price가 낮은 데이터를 추출
    dbconn = conn.cursor()
    dbconn.execute(f"SELECT * FROM TB_TRAVEL_VISITOR_OTHER_SERVICE WHERE location like '%{location}%' ORDER BY price ASC")
    result = dbconn.fetchall()

    df_travel_visitor_other = pd.DataFrame(result)
    df_travel_visitor_other.columns = [
        'id',
        'createdAt',
        'modifiedAt',
        'closetime', 
        'content',
        'image_url',
        'location',
        'opentime',
        'price',
        'otherservice_id',
        'travel_id',
        'user_id',       
    ]

    #other_id가 중복되는 데이터를 제거
    df_travel_visitor_other = df_travel_visitor_other.drop_duplicates('otherservice_id')

    #travel_id를 추출
    travel_id = df_travel_visitor_other['travel_id'].tolist()

    #travel_id를 10개 추출
    travel_id = travel_id[:10]

    return travel_id

def find_at_travel_id(travel_id):
    #TB_TRAVEL_VISITOR_CAFE에서 travel_id가 travel_id인 데이터를 추출
    dbconn = conn.cursor()
    dbconn.execute(f"SELECT * FROM TB_TRAVEL_VISITOR_CAFE WHERE travel_id = {travel_id}")

    result = dbconn.fetchall()

    df_travel_visitor_cafe = pd.DataFrame(result)
    df_travel_visitor_cafe.columns = [
        'id',
        'createdAt',
        'modifiedAt',
        'closetime', 
        'content',
        'image_url',
        'location',
        'menu',
        'opentime',
        'price',
        'cafe_id',
        'travel_id',
        'user_id',       
    ]

    #createAT, modifiedAt 컬럼을 제거
    df_travel_visitor_cafe = df_travel_visitor_cafe.drop(['createdAt', 'modifiedAt'], axis=1)

    #id를 TRAVEL_CAFE_ID로 변경
    df_travel_visitor_cafe = df_travel_visitor_cafe.rename(columns={'id': 'travel_cafe_id'})

    #TB_AI_CREATE_TRAVEL_LIST에 데이터를 삽입
    engine = create_engine('mysql+pymysql://root:1231@localhost:3306/lighthouseAI', echo=False)
    df_travel_visitor_cafe.to_sql('TB_AI_CREATE_TRAVEL_LIST', con=engine, if_exists='append', index=False)
    
    #TB_TRAVEL_VISITOR_RESTAURANT에서 travel_id가 travel_id인 데이터를 추출
    dbconn = conn.cursor()
    dbconn.execute(f"SELECT * FROM TB_TRAVEL_VISITOR_RESTAURANT WHERE travel_id = {travel_id}")

    result = dbconn.fetchall()

    df_travel_visitor_restaurant = pd.DataFrame(result)
    df_travel_visitor_restaurant.columns = [
        'id',
        'createdAt',
        'modifiedAt',
        'closetime', 
        'content',
        'image_url',
        'location',
        'menu',
        'opentime',
        'price',
        'restaurant_id',
        'travel_id',
        'user_id',       
    ]

    #createAT, modifiedAt 컬럼을 제거
    df_travel_visitor_restaurant = df_travel_visitor_restaurant.drop(['createdAt', 'modifiedAt'], axis=1)

    #id를 TRAVEL_RESTAURANT_ID로 변경
    df_travel_visitor_restaurant = df_travel_visitor_restaurant.rename(columns={'id': 'travel_restaurant_id'})

    #TB_AI_CREATE_TRAVEL_LIST에 데이터를 삽입
    engine = create_engine('mysql+pymysql://root:1231@localhost:3306/lighthouseAI', echo=False)
    df_travel_visitor_restaurant.to_sql('TB_AI_CREATE_TRAVEL_LIST', con=engine, if_exists='append', index=False)

    #TB_TRAVEL_VISITOR_SHOPPINGMALL에서 travel_id가 travel_id인 데이터를 추출
    dbconn = conn.cursor()
    dbconn.execute(f"SELECT * FROM TB_TRAVEL_VISITOR_SHOPPINGMALL WHERE travel_id = {travel_id}")

    result = dbconn.fetchall()

    df_travel_visitor_shopping = pd.DataFrame(result)
    df_travel_visitor_shopping.columns = [
        'id',
        'createdAt',
        'modifiedAt',
        'closetime', 
        'content',
        'image_url',
        'location',
        'opentime',
        'price',
        'shoppingmall_id',
        'travel_id',
        'user_id',       
    ]

    #createAT, modifiedAt 컬럼을 제거
    df_travel_visitor_shopping = df_travel_visitor_shopping.drop(['createdAt', 'modifiedAt'], axis=1)

    #id를 TRAVEL_SHOPPING_ID로 변경
    df_travel_visitor_shopping = df_travel_visitor_shopping.rename(columns={'id': 'travel_shoppingmall_id'})

    #TB_AI_CREATE_TRAVEL_LIST에 데이터를 삽입
    engine = create_engine('mysql+pymysql://root:1231@localhost:3306/lighthouseAI', echo=False)
    df_travel_visitor_shopping.to_sql('TB_AI_CREATE_TRAVEL_LIST', con=engine, if_exists='append', index=False)

    #TB_TRAVEL_VISITOR_TOUR_LIST에서 travel_id가 travel_id인 데이터를 추출
    dbconn = conn.cursor()
    dbconn.execute(f"SELECT * FROM TB_TRAVEL_VISITOR_TOUR_LIST WHERE travel_id = {travel_id}")

    result = dbconn.fetchall()

    df_travel_visitor_tourist = pd.DataFrame(result)
    df_travel_visitor_tourist.columns = [
        'id',
        'createdAt',
        'modifiedAt',
        'closetime', 
        'content',
        'image_url',
        'location',
        'opentime',
        'price',
        'tourlist_id',
        'travel_id',
        'user_id',       
    ]

    #createAT, modifiedAt 컬럼을 제거
    df_travel_visitor_tourist = df_travel_visitor_tourist.drop(['createdAt', 'modifiedAt'], axis=1)

    #id를 TRAVEL_TOUR_ID로 변경
    df_travel_visitor_tourist = df_travel_visitor_tourist.rename(columns={'id': 'travel_tourlist_id'})

    #TB_AI_CREATE_TRAVEL_LIST에 데이터를 삽입
    engine = create_engine('mysql+pymysql://root:1231@localhost:3306/lighthouseAI', echo=False)
    df_travel_visitor_tourist.to_sql('TB_AI_CREATE_TRAVEL_LIST', con=engine, if_exists='append', index=False)


    #TB_TRAVEL_VISITOR_OTHER_SERVICE에서 travel_id가 travel_id인 데이터를 추출
    dbconn = conn.cursor()
    dbconn.execute(f"SELECT * FROM TB_TRAVEL_VISITOR_OTHER_SERVICE WHERE travel_id = {travel_id}")

    result = dbconn.fetchall()

    df_travel_visitor_other = pd.DataFrame(result)
    df_travel_visitor_other.columns = [
        'id',
        'createdAt',
        'modifiedAt',
        'closetime', 
        'content', 
        'image_url',
        'location',
        'opentime',
        'price',
        'otherservice_id',
        'travel_id',
        'user_id',      
    ]

    #createAT, modifiedAt 컬럼을 제거
    df_travel_visitor_other = df_travel_visitor_other.drop(['createdAt', 'modifiedAt'], axis=1)

    #id를 TRAVEL_OTHER_ID로 변경
    df_travel_visitor_other = df_travel_visitor_other.rename(columns={'id': 'travel_otherservice_id'})

    #TB_AI_CREATE_TRAVEL_LIST에 데이터를 삽입
    engine = create_engine('mysql+pymysql://root:1231@localhost:3306/lighthouseAI', echo=False)
    df_travel_visitor_other.to_sql('TB_AI_CREATE_TRAVEL_LIST', con=engine, if_exists='append', index=False)
    

def find_using_id():
    
    return



def generate_response(msg):
    model = "gpt-3.5-turbo"
    dbconn = conn.cursor()

    response = openai.chat.completions.create(
        model=model,
        messages=[
            {"role": "system", "content": '너는 사용자가 어느 여행을 하고 싶은지 알아내는 챗봇이야. 1.어느 지역으로 여행을 가고 싶은지, 2. 카페, 식당, 쇼핑, 관광, 기타 중 어느 카테고리로 가고 싶은지 다음 키로 JSON형식으로 제공해 줘:지역-location, 예를 들어, {"location": "서울", "category": "카페"}와 같이 입력해 줘. '},
            {"role": "user", "content": msg},
        ],
        temperature=0,
    )
    
    output_text = response.choices[0].message.content   
    #output_text이 str이므로 JSON으로 변환
    output_dict = json.loads(output_text)


    print("output dict : ", output_dict)

    #JSON으로 변환된 output_dict에서 location, serving, expense, category를 추출
    location = output_dict['location']
    #serving = output_dict['serving']
    #expense = output_dict['expense']
    category = output_dict['category']

    print(location, category)

    if category == '카페':
        travel_id = cafe_cheap(location)
    elif category == '식당':
        travel_id = restaurant_cheap(location)
    elif category == '쇼핑':
        travel_id = shopping_cheap(location)
    elif category == '관광':
        travel_id = tourist_cheap(location)
    else:
        travel_id = other_cheap(location)

    #TB_TRAVEL에서 travel_id가 travel_id에 포함되는 데이터를 10개 추출
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

    #travel_id만 추출
    travel_id = df_travel['id'].tolist()
    
    #createAt, modifiedAt, folderName 컬럼을 제거
    df_travel = df_travel.drop(['createdAt', 'modifiedAt', 'folderName'], axis=1)
    
    travels = df_travel.to_dict(orient='records')

    #TB_AI_CREATE_TRAVEL_LIST를 초기화
    dbconn.execute(f"DELETE FROM TB_AI_CREATE_TRAVEL_LIST")
    conn.commit()


    #travel_id의 개수만큼 반복
    for i in travel_id:
        find_at_travel_id(i)

    conn.commit()
    
    dbconn.execute(f"SELECT travel_id, cafe_id, restaurant_id, shoppingmall_id, tourlist_id, otherservice_id FROM TB_AI_CREATE_TRAVEL_LIST")
    result = dbconn.fetchall()
    
    df_travel = pd.DataFrame(result)
    df_travel.columns = [
        'travel_id',
        'cafe_id',
        'restaurant_id',
        'shoppingmall_id',
        'tourlist_id',
        'otherservice_id',
    ]
    
    print(df_travel)
    
    
    
    #travel_id가 같은 데이터끼리 리스트로 묶음
    df_travel = df_travel.groupby('travel_id').agg(lambda x: x.tolist()).reset_index()
    
    #각 리스트에서 nan을 제거
    df_travel = df_travel.applymap(lambda x: [i for i in x if str(i) != 'nan'] if isinstance(x, list) else x)
    
    print(df_travel)
    
    #df_travel을 json으로 변환
    df_travel_json = df_travel.to_json(orient='records')


    return travels