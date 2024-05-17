import openai
import pymysql
import yaml
import json
import pandas as pd
import os
import jsonify
from sqlalchemy import create_engine

def generate_response(msg, model = "gpt-3.5-turbo"):

    file_path = os.path.join(os.path.dirname(__file__), '..', 'resources', 'application-dev.yml')
    with open(file_path, 'r') as stream:
        config = yaml.safe_load(stream)

    conn = pymysql.connect(
        host='localhost',
        user=config['spring']['datasource']['username'],
        password=str(config['spring']['datasource']['password']).encode('utf-8'),
        port=3306,
        db='lighthouseAI'
    )
    dbconn = conn.cursor()

    openai.api_key = config['openai']['api']['key']

    prompt = "너는 여행 계획을 만들어 주는 챗봇이야. '''" + msg + "'''위 문장에서 어느 지역으로 여행을 가고 싶은지, 몇명에서 놀러가는지, 비용은 얼마인지, 식당, 카페, 관광, 쇼핑, 기타 중 어느 곳을 중점으로 가고 싶은지를 다음 키로 JSON형식으로 제공해 줘:지역-location, 인원-serving, 비용-expense, 카테고리-category. 예를 들어, {'location':'서울', 'serving':2, 'expense':12000, 'category':'식당'}와 같이 입력해 줘."

    response = openai.chat.completions.create(
        model=model,
        messages=[
            {"role": "user", "content": prompt},
        ],
        temperature=0,
    )

    output_text = response.choices[0].message.content

    #output_text이 str이므로 JSON으로 변환
    output_dict = json.loads(output_text)

    #JSON으로 변환된 output_dict에서 location, serving, expense, category를 추출
    location = output_dict['location']
    serving = output_dict['serving']
    expense = output_dict['expense']
    category = output_dict['category']

    sub_category = ''
    if category == '식당':
        category = 'TB_TRAVEL_VISITOR_RESTAURANT'
        sub_category = 'TB_RESTAURANT'
    elif category == '카페':
        category = 'TB_TRAVEL_VISITOR_CAFE'
        sub_category = 'TB_CAFE'
    elif category == '관광':
        category = 'TB_TRAVEL_VISITOR_TOUR_LIST'
        sub_category = 'TB_TOURLIST'
    elif category == '쇼핑':
        category = 'TB_TRAVEL_VISITOR_SHOPPINGMALL'
        sub_category = 'TB_SHOPPINGMALL'
    else:
        category = 'TB_TRAVEL_VISITOR_OTHER_SERVICE'
        sub_category = 'TB_OTHER_SERVICE'

    

    #TB_CONSTITUENCY에서 칼럼constituency이 locationrhk location과 일치하는 데이터를 추출하여 id를 추출
    dbconn.execute(f"SELECT id FROM TB_CONSTITUENCY WHERE constituency = '{location}'")
    result = dbconn.fetchall()

    #result에서 id를 추출
    #location = result[0][0]

    print(location, serving, expense, category)

    #DB에서 category에 해당하는 데이터를 추출. 단 지역은 location을 포함, 비용은 expense보다 작거나 같고, 인원은 serving보다 크거나 같은 데이터를 추출
    dbconn.execute(f"SELECT * FROM {category} WHERE location like '%{location}%' AND price <= {expense}")
    result = dbconn.fetchall()

    if category == 'TB_TRAVEL_VISITOR_CAFE':
        df_travel_visitor_cafe = pd.DataFrame(result)
        df_travel_visitor_cafe.columns = [
            'id',
            'closetime',
            'image_url',
            'location',
            'opentime',
            'price',
            'cafe_id',
            'user_id',
            'createdAt',
            'modifiedAt',
            'menu',
            'travel_id'
        ]

        #필요한 데이터만 추출
        df_travel_visitor_cafe = df_travel_visitor_cafe[['location', 'cafe_id', 'menu', 'price','travel_id']]

        print(df_travel_visitor_cafe)

        #TB_CAFE에서 데이터를 추출
        dbconn.execute(f"SELECT * FROM {sub_category}")
        result = dbconn.fetchall()

        df_cafe = pd.DataFrame(result)
        df_cafe.columns = [
            'id',
            'createdAt',
            'modifiedAt',
            'location',
            'menu',
            'price',
            'title',
            'user_id',
            'closetime',
            'opentime',
            'constituency_id',
        ]

        #필요한 데이터만 추출
        df_cafe = df_cafe[['id', 'title']]

        #두 데이터를 merge
        df = pd.merge(df_travel_visitor_cafe, df_cafe, left_on='cafe_id', right_on='id', how='left')

        df = df[['cafe_id', 'menu', 'price', 'location', 'title','travel_id']]

        #TB_TRAVEl에서 데이터를 추출
        dbconn.execute(f"SELECT * FROM TB_TRAVEL")
        result = dbconn.fetchall()

        df_travel = pd.DataFrame(result)
        df_travel.columns = [
            'id',
            'image_url',
            'serving',
            'star',
            'title',
            'travel_expense',
            'region_id',
            'user_id',
            'createdAt',
            'modifiedAt',
            'folderName',
            'constituency_id',
        ]

        #필요한 데이터만 추출
        df_travel = df_travel[['id','star']]

        #두 데이터를 merge
        df = pd.merge(df, df_travel, left_on='travel_id', right_on='id', how='left')

        df = df[['cafe_id', 'star', 'menu', 'price','title', 'location']]

        #star를 기준으로 내림차순 정렬
        df = df.sort_values(by='star', ascending=False)[0:10]

        #TB_AI_CREATE에 df를 저장
        engine = create_engine(f"mysql+pymysql://{config['spring']['datasource']['username']}:{config['spring']['datasource']['password']}@localhost/lighthouseAI")
        dbconn.execute("DELETE FROM TB_AI_CREATE_CAFE")
        conn.commit()
        df.to_sql('TB_AI_CREATE_CAFE', engine, if_exists='append', index=False)

    elif category == 'TB_TRAVEL_VISITOR_RESTAURANT':
        df_travel_visitor_restaurant = pd.DataFrame(result)
        df_travel_visitor_restaurant.columns = [
            'id',
            'closetime',
            'image_url',
            'location',
            'opentime',
            'price',
            'restaurant_id',
            'user_id',
            'createdAt',
            'modifiedAt',
            'menu',
            'travel_id'
        ]

        #필요한 데이터만 추출
        df_travel_visitor_restaurant = df_travel_visitor_restaurant[['location', 'restaurant_id', 'menu', 'price','travel_id']]

        print(df_travel_visitor_restaurant)

        #TB_CAFE에서 데이터를 추출
        dbconn.execute(f"SELECT * FROM {sub_category}")
        result = dbconn.fetchall()

        df_other_service = pd.DataFrame(result)
        df_other_service.columns = [
            'id',
            'createdAt',
            'modifiedAt',
            'location',
            'menu',
            'price',
            'title',
            'user_id',
            'closetime',
            'opentime',
            'constituency_id',
        ]

        #필요한 데이터만 추출
        df_other_service = df_other_service[['id', 'title']]

        #두 데이터를 merge
        df = pd.merge(df_travel_visitor_restaurant, df_other_service, left_on='restaurant_id', right_on='id', how='left')

        df = df[['restaurant_id', 'menu', 'price', 'location', 'title','travel_id']]

        #TB_TRAVEl에서 데이터를 추출
        dbconn.execute(f"SELECT * FROM TB_TRAVEL")
        result = dbconn.fetchall()

        df_travel = pd.DataFrame(result)
        df_travel.columns = [
            'id',
            'image_url',
            'serving',
            'star',
            'title',
            'travel_expense',
            'region_id',
            'user_id',
            'createdAt',
            'modifiedAt',
            'folderName',
            'constituency_id',
        ]

        #필요한 데이터만 추출
        df_travel = df_travel[['id','star']]

        #두 데이터를 merge
        df = pd.merge(df, df_travel, left_on='travel_id', right_on='id', how='left')

        df = df[['restaurant_id', 'star', 'menu', 'price','title', 'location']]

        #star를 기준으로 내림차순 정렬
        df = df.sort_values(by='star', ascending=False)[0:10]

        #TB_AI_CREATE에 df를 저장
        engine = create_engine(f"mysql+pymysql://{config['spring']['datasource']['username']}:{config['spring']['datasource']['password']}@localhost/lighthouseAI")
        dbconn.execute("DELETE FROM TB_AI_CREATE_RESTAURANT")
        conn.commit()
        df.to_sql('TB_AI_CREATE_RESTAURANT', engine, if_exists='append', index=False)

    elif category == 'TB_TRAVEL_VISITOR_TOUR_LIST':
        df_travel_visitor_tourlist = pd.DataFrame(result)
        df_travel_visitor_tourlist.columns = [
            'id',
            'closetime',
            'image_url',
            'location',
            'opentime',
            'price',
            'tourlist_id',
            'user_id',
            'createdAt',
            'modifiedAt',
            'travel_id'
        ]

        #필요한 데이터만 추출
        df_travel_visitor_tourlist = df_travel_visitor_tourlist[['location', 'restaurant_id', 'price','travel_id']]

        print(df_travel_visitor_tourlist)

        #TB_CAFE에서 데이터를 추출
        dbconn.execute(f"SELECT * FROM {sub_category}")
        result = dbconn.fetchall()

        df_tourlist = pd.DataFrame(result)
        df_tourlist.columns = [
            'id',
            'createdAt',
            'modifiedAt',
            'location',
            'price',
            'title',
            'closetime',
            'opentime',
            'constituency_id',
            'user_id',
        ]

        #필요한 데이터만 추출
        df_other_service = df_other_service[['id', 'title']]

        #두 데이터를 merge
        df = pd.merge(df_travel_visitor_restaurant, df_other_service, left_on='tourlist_id', right_on='id', how='left')

        df = df[['tourlist_id', 'price', 'location', 'title','travel_id']]

        #TB_TRAVEl에서 데이터를 추출
        dbconn.execute(f"SELECT * FROM TB_TRAVEL")
        result = dbconn.fetchall()

        df_travel = pd.DataFrame(result)
        df_travel.columns = [
            'id',
            'image_url',
            'serving',
            'star',
            'title',
            'travel_expense',
            'region_id',
            'user_id',
            'createdAt',
            'modifiedAt',
            'folderName',
            'constituency_id',
        ]

        #필요한 데이터만 추출
        df_travel = df_travel[['id','star']]

        #두 데이터를 merge
        df = pd.merge(df, df_travel, left_on='travel_id', right_on='id', how='left')

        df = df[['tourlist_id', 'star', 'menu', 'price','title', 'location']]

        #star를 기준으로 내림차순 정렬
        df = df.sort_values(by='star', ascending=False)[0:10]

        #TB_AI_CREATE에 df를 저장
        engine = create_engine(f"mysql+pymysql://{config['spring']['datasource']['username']}:{config['spring']['datasource']['password']}@localhost/lighthouseAI")
        dbconn.execute("DELETE FROM TB_AI_CREATE_TOUR_LIST")
        conn.commit()
        df.to_sql('TB_AI_CREATE_TOUR_LIST', engine, if_exists='append', index=False)

    elif category == 'TB_TRAVEL_VISITOR_SHOPPINGMALL':
        df_travel_visitor_shoppingmall = pd.DataFrame(result)
        df_travel_visitor_shoppingmall.columns = [
            'id',
            'closetime',
            'image_url',
            'location',
            'opentime',
            'price',
            'shoppingmall_id',
            'user_id',
            'createdAt',
            'modifiedAt',
            'travel_id'
        ]

        #필요한 데이터만 추출
        df_travel_visitor_shoppingmall = df_travel_visitor_shoppingmall[['location', 'shoppingmall_id', 'price','travel_id']]

        print(df_travel_visitor_shoppingmall)

        #TB_CAFE에서 데이터를 추출
        dbconn.execute(f"SELECT * FROM {sub_category}")
        result = dbconn.fetchall()

        df_shoppingmall = pd.DataFrame(result)
        df_shoppingmall.columns = [
            'id',
            'createdAt',
            'modifiedAt',
            'closetime',
            'location',
            'opentime',
            'title', 
            'constituency_id',
            'user_id',
        ]

        #필요한 데이터만 추출
        df_shoppingmall = df_shoppingmall[['id', 'title']]

        #두 데이터를 merge
        df = pd.merge(df_travel_visitor_shoppingmall, df_shoppingmall, left_on='shoppingmall_id', right_on='id', how='left')

        df = df[['shoppingmall_id', 'location', 'title','travel_id']]

        #TB_TRAVEl에서 데이터를 추출
        dbconn.execute(f"SELECT * FROM TB_TRAVEL")
        result = dbconn.fetchall()

        df_travel = pd.DataFrame(result)
        df_travel.columns = [
            'id',
            'image_url',
            'serving',
            'star',
            'title',
            'travel_expense',
            'region_id',
            'user_id',
            'createdAt',
            'modifiedAt',
            'folderName',
            'constituency_id',
        ]

        #필요한 데이터만 추출
        df_travel = df_travel[['id','star']]

        #두 데이터를 merge
        df = pd.merge(df, df_travel, left_on='travel_id', right_on='id', how='left')

        df = df[['shoppingmall_id', 'star', 'menu', 'title', 'location']]

        #star를 기준으로 내림차순 정렬
        df = df.sort_values(by='star', ascending=False)[0:10]

        #TB_AI_CREATE에 df를 저장
        engine = create_engine(f"mysql+pymysql://{config['spring']['datasource']['username']}:{config['spring']['datasource']['password']}@localhost/lighthouseAI")
        dbconn.execute("DELETE FROM TB_AI_CREATE_SHOPPINGMALL")
        conn.commit()
        df.to_sql('TB_AI_CREATE_SHOPPINGMALL', engine, if_exists='append', index=False)

    elif category == 'TB_TRAVEL_VISITOR_OTHER_SERVICE':
        df_travel_visitor_other_service = pd.DataFrame(result)
        df_travel_visitor_other_service.columns = [
            'id',
            'closetime',
            'image_url',
            'location',
            'opentime',
            'price',
            'other_service_id',
            'user_id',
            'createdAt',
            'modifiedAt',
            'travel_id'
        ]

        #필요한 데이터만 추출
        df_travel_visitor_other_service = df_travel_visitor_other_service[['location', 'other_service_id', 'price','travel_id']]

        print(df_travel_visitor_other_service)

        #TB_CAFE에서 데이터를 추출
        dbconn.execute(f"SELECT * FROM {sub_category}")
        result = dbconn.fetchall()

        df_other_service = pd.DataFrame(result)
        df_other_service.columns = [
            'id',
            'createdAt',
            'modifiedAt',
            'closetime',
            'location',
            'opentime',
            'title', 
            'constituency_id',
            'user_id',
        ]

        #필요한 데이터만 추출
        df_other_service = df_other_service[['id', 'title']]

        #두 데이터를 merge
        df = pd.merge(df_travel_visitor_other_service, df_other_service, left_on='other_service_id', right_on='id', how='left')

        df = df[['other_service_id', 'location', 'title','travel_id']]

        #TB_TRAVEl에서 데이터를 추출
        dbconn.execute(f"SELECT * FROM TB_TRAVEL")
        result = dbconn.fetchall()

        df_travel = pd.DataFrame(result)
        df_travel.columns = [
            'id',
            'image_url',
            'serving',
            'star',
            'title',
            'travel_expense',
            'region_id',
            'user_id',
            'createdAt',
            'modifiedAt',
            'folderName',
            'constituency_id',
        ]

        #필요한 데이터만 추출
        df_travel = df_travel[['id','star']]

        #두 데이터를 merge
        df = pd.merge(df, df_travel, left_on='travel_id', right_on='id', how='left')

        df = df[['shoppingmall_id', 'star', 'menu', 'title', 'location']]

        #star를 기준으로 내림차순 정렬
        df = df.sort_values(by='star', ascending=False)[0:10]

        #TB_AI_CREATE에 df를 저장
        engine = create_engine(f"mysql+pymysql://{config['spring']['datasource']['username']}:{config['spring']['datasource']['password']}@localhost/lighthouseAI")
        dbconn.execute("DELETE FROM TB_AI_CREATE_OTHER_SERVICE")
        conn.commit()
        df.to_sql('TB_AI_CREATE_OTHER_SERVI', engine, if_exists='append', index=False)

    print(df)

    data_dict = df.to_dict(orient='records')

    return data_dict
