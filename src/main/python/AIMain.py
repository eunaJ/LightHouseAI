import pandas as pd
import os
import csv
import pymysql
import json

from sklearn.model_selection import train_test_split
from catboost import CatBoostRegressor, Pool
from geopy.geocoders import Nominatim
from urllib import parse
from urllib.request import urlopen
from urllib.request import Request
from urllib.error import HTTPError
from bs4 import BeautifulSoup

"""
Client_ID = 'vkba6txhxb'
Client_Secret = 'IPKKtWyJkMm3bgW8NOLaXg6TtwNglNrPkNhpZZTn'
api_url = 'https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query='
"""

global model

def dataTrain():
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
    
    #TB_TRAVEL을 가져와 데이터프레임으로 변환
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
        'modifiedAt'
    ]
    
    #두 데이터프레임을 join
    df_1 = pd.merge(df_user.set_index('id'), df_travel.set_index('user_id'), left_index=True, right_index=True, how='inner')
    
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
    
    #constituency를 받아 loc_x, loc_y에 x, y좌표를 추가
    #...하고 싶지만 일단은 임의로 좌표 추가
    """
    request = Request(url)
    request.add_header('X-NCP-APIGW-API-KEY-ID', Client_ID)
    request.add_header('X-NCP-APIGW-API-KEY', Client_Secret)

    try:
        response = urlopen(request)

    except HTTPError as e:
        print('HTTP Error')
        latitude, longitude = None, None

    else:
        rescode = response.getcode()
        
        if rescode == 200:
            response_body = response.read().decode('utf-8')
            response_body = json.loads(response_body)
            
            if response_body['addresses'] == []:
                print('No result')
            else:
                latitude = response_body['addresses'][0]['y']
                longitude = response_body['addresses'][0]['x']
                print('Success')
        else:
            print(f'Response error, rescode:{rescode}')
            latitude, longitude = None, None    
    """
    df_filter['loc_x'] = 126.890115
    df_filter['loc_y'] = 37.487221

    categoricla_features_name = [
        'title',
        'region_name',
        'constituency'
    ]
    
    df_filter['loc_x'] = 1000000 * df_filter['loc_x']
    df_filter['loc_y'] = 1000000 * df_filter['loc_y']
    
    print(df_filter)

    #df_filter_1을 학습 데이터와 테스트 데이터로 나눔
    train_data, test_data = train_test_split(df_filter, test_size=0.2, random_state=42)
    
    train_pool = Pool(train_data.drop('star', axis=1), label=train_data['star'], cat_features=categoricla_features_name)
    test_pool = Pool(test_data.drop('star', axis=1), label=test_data['star'], cat_features=categoricla_features_name)
    
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
        verbose=1,
        plot=True)
    
def dataPredict(birth, region_id, serving, travel_expense ,loc_x, loc_y):
    #모델을 사용하여 예측
    prediction = model.predict([[birth, region_id, serving, travel_expense, loc_x, loc_y]])
    
    return prediction
    
if __name__ == '__main__':
    dataTrain()