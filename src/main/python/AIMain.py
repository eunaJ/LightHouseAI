import pandas as pd
import os
import csv
import pymysql

def dataLoad():
    conn = pymysql.connect(
        host='localhost',
        user='root',
        password='1231',
        port=3306,
        db='test'
    )
    input_file_1 = 'data/tn_travel_여행_A.csv'
    input_file_2 = 'data/tn_travel_master_여행객 Master_A.csv'
    input_file_3 = 'data/tn_visit_area_info_방문지정보_A.csv'
    dbconn = conn.cursor()



def aiTrain():
    # 데이터 로드
    print(os.getcwd())
    df_place = pd.read_csv('./src/main/python/data/tn_visit_area_info_방문지정보_A.csv')
    df_travel = pd.read_csv('./src/main/python/data/tn_travel_여행_A.csv')
    df_traveler = pd.read_csv('./src/main/python/data/tn_traveller_master_여행객 Master_A.csv')
    
    # 데이터 전처리
    df = pd.merge(df_place,df_travel, on='TRAVEL_ID', how='left')
    df = pd.merge(df, df_traveler, on='TRAVELER_ID', how='left')
    
    df_filter = df[~df['TRAVEL_MISSION_CHECK'].isnull()].copy()
    df_filter.loc[:, 'TRAVEL_MISSION_INT'] = df_filter['TRAVEL_MISSION_CHECK'].str.split(';').str[0].astype(int)
    
    df_filter = df_filter[[
        'GENDER',
        'AGE_GRP',
        'TRAVEL_STYL_1', 'TRAVEL_STYL_2', 'TRAVEL_STYL_3', 'TRAVEL_STYL_4', 'TRAVEL_STYL_5', 'TRAVEL_STYL_6', 'TRAVEL_STYL_7', 'TRAVEL_STYL_8',
        'TRAVEL_MOTIVE_1',
        'TRAVEL_COMPANIONS_NUM',
        'TRAVEL_MISSION_INT',
        'VISIT_AREA_NM',
        'DGSTFN',
    ]]
    df_filter = df_filter.dropna()
    
    categorical_features_names = [
        'GENDER',
        # 'AGE_GRP',
        'TRAVEL_STYL_1', 'TRAVEL_STYL_2', 'TRAVEL_STYL_3', 'TRAVEL_STYL_4', 'TRAVEL_STYL_5', 'TRAVEL_STYL_6', 'TRAVEL_STYL_7', 'TRAVEL_STYL_8',
        'TRAVEL_MOTIVE_1',
        # 'TRAVEL_COMPANIONS_NUM',
        'TRAVEL_MISSION_INT',
        'VISIT_AREA_NM',
        # 'DGSTFN',
    ]
    df_filter[categorical_features_names[1:-1]] = df_filter[categorical_features_names[1:-1]].astype(int)

    #train, test 데이터 분리
    from sklearn.model_selection import train_test_split
    
    train_data, test_data = train_test_split(df_filter, test_size=0.2, random_state=42)
    
    from catboost import CatBoostRegressor, Pool

    train_pool = Pool(train_data.drop(['DGSTFN'], axis=1),
        label=train_data['DGSTFN'],
        cat_features=categorical_features_names)

    test_pool = Pool(test_data.drop(['DGSTFN'], 1),
        label=test_data['DGSTFN'],
        cat_features=categorical_features_names)
    
    # 모델 학습
    model = CatBoostRegressor(
        loss_function='RMSE',
        eval_metric='MAE',
        task_type='GPU',
        depth=6,
        learning_rate=0.01,
        n_estimators=2000)

    model.fit(
        train_pool,
        eval_set=test_pool,
        verbose=500,
        plot=True)

    
    
if __name__ == '__main__':
    aiTrain()