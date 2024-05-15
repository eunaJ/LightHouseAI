import pandas as pd
import os
import csv
import pymysql
import json
import numpy as np

from sklearn.model_selection import train_test_split
from sklearn.preprocessing import LabelEncoder
from catboost import CatBoostRegressor, Pool
from geopy.geocoders import Nominatim
from urllib import parse
from urllib.request import urlopen
from urllib.request import Request
from urllib.error import HTTPError
from bs4 import BeautifulSoup

global model

global titles

global model_cafe
global cafe_le
global cafe_titles

def makeTravel(birth, serving, user_id, expense, location, days, people):
    print(s)

def dataTrain():
    global model
    global titles
    #db에서 데이터를 가져와서 학습
    conn = pymysql.connect(
        host='localhost',
        user='root',
        password='1231',
        port=3306,
        db='lighthouseAI'
    )
    dbconn = conn.cursor()
    
    #TB_USER을 가져와 데이터프레임으로 변환
    dbconn.execute('SELECT * FROM TB_USER')
    rows = dbconn.fetchall()

    df_user = pd.DataFrame(rows)
    df_user.columns = [
        'id',
        'createdAt',
        'modifiedAt',
        'email',
        'nickname',
        'password',
        'role',
        'birth',
        'profile_img_url',
    ]

    #TB_REGION을 가져와 데이터프레임으로 변환
    dbconn.execute('SELECT * FROM TB_REGION')
    rows = dbconn.fetchall()
    df_region = pd.DataFrame(rows)
    df_region.columns = [
        'id',
        'createdAt',
        'modifiedAt',
        'region_name',
    ]
    
    #TB_CONSTITUENCY을 가져와 데이터프레임으로 변환
    dbconn.execute('SELECT * FROM TB_CONSTITUENCY')
    rows = dbconn.fetchall()
    df_constituency = pd.DataFrame(rows)
    df_constituency.columns = [
        'id',
        'createdAt',
        'modifiedAt',
        'constituency',
        'region_id',
    ]
    
    #두 데이터프레임을 병합
    df_2 = pd.concat([df_region.set_index('id'), df_constituency.set_index('region_id')], axis=1, join='inner')
    
    #df_1과 df_2를 병합
    df = pd.merge(df_1, df_2, left_on='region_id', right_on='id', how='left')
    
    #필요한 컬럼만 추출
    df_filter = df.copy()

    df_filter = df_filter[[
        'birth',
        'region_id',
        'serving',
        'title',
        'travel_expense',
        'star',
        'region_name',
        'constituency'
    ]]
    
    df_filter = df_filter.dropna()
    
    #birth 컬럼을 나이대로 변환
    df_filter['birth'] = 2024 - df_filter['birth'].astype(int)
    
    #df_filter에 칼럼 loc_x, loc_y를 추가
    df_filter['loc_x'] = 0
    df_filter['loc_y'] = 0
    
    df_filter['loc_x'] = 126.890115
    df_filter['loc_y'] = 37.487221
    df_filter['loc_x'] = 1000000 * df_filter['loc_x']
    df_filter['loc_y'] = 1000000 * df_filter['loc_y']
    
    print(df_filter)

    train_data, test_data = train_test_split(df_filter, test_size=0.2, random_state=42)

    #
    categorical_features_name = [
        'title',
        'region_name',
        'constituency'
    ]

    #df_filter[categorical_features_name[1:-1]] = df_filter[categorical_features_name[1:-1]].astype(int)

    le = LabelEncoder()
    for cat in categorical_features_name:
        df_filter[cat] = le.fit_transform(df_filter[cat])
        

    #df_filter_1을 학습 데이터와 테스트 데이터로 나눔
    train_pool = Pool(train_data.drop('star', axis=1), label=train_data['star'], cat_features=categorical_features_name)
    test_pool = Pool(test_data.drop('star', axis=1), label=test_data['star'], cat_features=categorical_features_name)


    #categorical_features_index = [train_data.columns.get_loc(c) for c in categorical_features_name if c in train_data]

    #train_pool = Pool(train_data.drop('star', axis=1), label=train_data['star'], cat_features=categorical_features_index)
    #test_pool = Pool(test_data.drop('star', axis=1), label=test_data['star'], cat_features=categorical_features_index)
    
    model = CatBoostRegressor(
        depth=6,
        learning_rate=0.01,
        loss_function='RMSE',
        task_type='GPU',
        eval_metric='MAE',
        n_estimators=2000)
    
    model.fit(
        train_pool,
        eval_set=test_pool,
        verbose=500,
        plot=True)
    
    titles = df_filter['title'].drop_duplicates()

    print(model.get_feature_importance(prettified=True))
    
def dataPredict(birth, region_id, serving, title, travel_expense, star, region_name_, constituency, loc_x, loc_y):
    global model
    global titles
    #모델을 사용하여 예측
    
    traveler = {
        'birth':birth,
        'region_id': region_id,
        'serving': serving,
        'title': title,
        'travel_expense': travel_expense,
        'region_name': region_name_,
        'constituency': constituency,
        'loc_x': loc_x,
        'loc_y': loc_y
    }

    results = pd.DataFrame([], columns=['title', 'stars'])

    for title in titles:
        input = list(traveler.values())
        input.append(title)

        stars = model.predict(input)

        results = pd.concat([results, pd.DataFrame([[title, stars]], columns=['title', 'stars'])])

    results.sort_values('stars', ascending=False)[:10]
    #prediction = model.predict([[birth, region_id, serving, title, travel_expense, region_name_, constituency, loc_x, loc_y]])
    
    return results
    
def cafeTrain():
    global cafe_le
    global model_cafe
    global cafe_titles
    #db에서 데이터를 가져와서 학습
    conn = pymysql.connect(
        host='localhost',
        user='root',
        password='1231',
        port=3306,
        db='lighthouseAI'
    )
    dbconn = conn.cursor()

    #TB_USER을 가져와 데이터프레임으로 변환
    dbconn.execute('SELECT * FROM TB_USER')
    rows = dbconn.fetchall()

    df_user = pd.DataFrame(rows)
    df_user.columns = [
        'id',
        'createdAt',
        'modifiedAt',
        'email',
        'nickname',
        'password',
        'role',
        'birth',
        'profile_img_url',
        'folderName',
    ]

    #df_user에서 필요한 컬럼만 추출
    df_user = df_user[['id', 'birth']]

    #태어난 년도를 나이로 변환
    df_user['birth'] = 2024 - df_user['birth'].astype(int)

    #10대, 20대, 30대, 40대, 50대로 변환
    df_user['birth'] = df_user['birth'] // 10 * 10
    
    #TB_TRAVEL을 가져와 데이터프레임으로 변환.
    dbconn.execute('SELECT * FROM TB_TRAVEL')
    rows = dbconn.fetchall()

    df_travel = pd.DataFrame(rows)
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

    #df_travel에서 필요한 컬럼만 추출
    df_travel = df_travel[['id', 'serving', 'star', 'travel_expense', 'user_id']]

    #TB_CAFE을 가져와 데이터프레임으로 변환
    dbconn.execute('SELECT * FROM TB_CAFE')
    rows = dbconn.fetchall()

    df_cafe = pd.DataFrame(rows)
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

    #df_cafe에서 필요한 컬럼만 추출
    df_cafe = df_cafe[['id','title', 'location']]

    #TB_TRAVEL_VISITOR_CAFE를 가져와 데이터프레임으로 변환
    dbconn.execute('SELECT * FROM TB_TRAVEL_VISITOR_CAFE')
    rows = dbconn.fetchall()

    df_cafe_visitor = pd.DataFrame(rows)
    df_cafe_visitor.columns = [
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
        'travel_id',
    ]

    #df_cafe_visitor에서 필요한 컬럼만 추출
    df_cafe_visitor = df_cafe_visitor[['cafe_id', 'price','travel_id']]

    #TB_USER과 TB_TRAVEL을 병합
    df_1 = pd.merge(df_user, df_travel, left_on='id', right_on='user_id', how='right')

    #df_1에서 필요한 컬럼만 추출
    df_1 = df_1[['id_y', 'user_id', 'birth', 'serving', 'star', 'travel_expense' ]]
    #id_y를 id로 변경
    df_1 = df_1.rename(columns={'id_y': 'id'})

    print("df_1")   
    print(df_1)

    #TB_CAFE와 TB_TRAVEL_VISITOR_CAFE를 병합
    df_2 = pd.merge(df_cafe, df_cafe_visitor, left_on='id', right_on='cafe_id', how='right')

    #df_2에서 필요한 컬럼만 추출
    df_2 = df_2[['travel_id', 'cafe_id', 'price']]
    
    print("df_2")
    print(df_2)

    #df_1과 df_2를 병합
    df = pd.merge(df_1, df_2, left_on='id', right_on='travel_id', how='left')

    #df에서 필요한 컬럼만 추출
    df = df[['user_id', 'cafe_id', 'birth', 'serving', 'price', 'star']]

    print("df")
    print(df)

    #df를 학습 데이터와 테스트 데이터로 나눔
    train_data, test_data = train_test_split(df, test_size=0.2, random_state=42)

    train_pool = Pool(train_data.drop('star', axis=1), label=train_data['star'])
    test_pool = Pool(test_data.drop('star', axis=1), label=test_data['star'])

    model_cafe = CatBoostRegressor(
        depth=6,
        learning_rate=0.01,
        loss_function='RMSE',
        task_type='GPU',
        eval_metric='MAE',
        n_estimators=2000)
    
    model_cafe.fit(
        train_pool,
        eval_set=test_pool,
        verbose=500,
        plot=True)
    
    print(model_cafe.get_feature_importance(prettified=True))

def cafePredict(user_id, serving, constituency_id, rank_s, rank_e):
    global model_cafe

    results = pd.DataFrame([], columns=['title', 'stars'])

    """
    #cafe_titles에서 location 삭제
    cafe_titles = cafe_titles.drop('location', axis=1)

    cafelist = cafe_le.inverse_transform(cafe_titles)

    titles = cafe_titles['title']
    """

    #TB_CAFE에서 데이터를 가져와 데이터프레임으로 변환
    conn = pymysql.connect(
        host='localhost',
        user='root',
        password='1231',
        port=3306,
        db='lighthouseAI'
    )
    dbconn = conn.cursor()

    #TB_CAFE에서 constiuency_id가 일치하는 데이터를 가져와 데이터프레임으로 변환
    dbconn.execute('SELECT * FROM TB_CAFE WHERE constituency_id = %s', (constituency_id))
    rows = dbconn.fetchall()

    df_cafe = pd.DataFrame(rows)
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

    #df_cafe에서 필요한 컬럼만 추출하여 df_cafe_copy에 저장
    df_cafe = df_cafe[['id', 'price','title']]
    df_cafe_copy = df_cafe.copy()

    #df_cafe에서 필요한 컬럼만 추출
    df_cafe = df_cafe[['id', 'price']]

    #df_cafe에 user_id, serving 컬럼 추가
    df_cafe['user_id'] = user_id
    df_cafe['serving'] = serving

    print("df_cafe")
    print(df_cafe)

    #TB_USER에서 user_id가 일치하는 데이터를 가져와 데이터프레임으로 변환
    dbconn.execute('SELECT * FROM TB_USER WHERE id = %s', (user_id))
    rows = dbconn.fetchall()

    df_user = pd.DataFrame(rows)
    df_user.columns = [
        'id',
        'createdAt',
        'modifiedAt',
        'email',
        'nickname',
        'password',
        'role',
        'birth',
        'profile_img_url',
        'folderName',
    ]

    #df_user에서 필요한 컬럼만 추출
    df_user = df_user[['id', 'birth']]

    #태어난 년도를 나이로 변환
    df_user['birth'] = 2024 - df_user['birth'].astype(int)

    #10대, 20대, 30대, 40대, 50대로 변환
    df_user['birth'] = df_user['birth'] // 10 * 10

    for index, row in df_cafe.iterrows():

        user_id = int(row['user_id'])
        user_birth = df_user[df_user['id'] == user_id]['birth'].values[0]

        input = {
            'user_id': user_id,
            'birth': int(user_birth),
            'serving': int(row['serving']),
            'price': int(row['price']),
            'cafe_id': int(row['id'])
        }

        input_df = pd.DataFrame([input])

        stars = model_cafe.predict(input_df)

        results = pd.concat([results, pd.DataFrame([[input['cafe_id'], stars[0]]], columns=['id', 'stars'])])
    
    
    results = pd.merge(results, df_cafe_copy, left_on='id', right_on='id', how='left')

    #title_x 컬럼을 삭제
    results = results.drop('title_x', axis=1)

    #title_y 컬럼을 title로 변경
    results = results.rename(columns={'title_y': 'title'})
    
    result = results.sort_values('stars', ascending=False)[rank_s:rank_e]

    print(result)

    #json형태로 변환
    result = result.to_json(orient="records")

    return result
