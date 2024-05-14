import openai
import yaml
import os

def generate_response(msg, model = "gpt-3.5-turbo"):

    file_path = os.path.join(os.path.dirname(__file__), '..', 'resources', 'application-dev.yml')
    with open(file_path, 'r') as stream:
        config = yaml.safe_load(stream)

    openai.api_key = config['openai']['api']['key']

    prompt = "너는 여행 계획을 만들어 주는 챗봇이야. '''" + msg + "'''위 문장에서 어느 지역으로 여행을 가고 싶은지, 몇일 동안 놀러가고 싶은지, 몇명에서 놀러가는지, 비용은 얼마인지를 다음 키로 JSON형식으로 제공해 줘:지역-location, 일수-days, 인원-people, 비용-expense. 예를 들어, {'location':'서울', 'days':3, 'people':2, 'expense':12000}와 같이 입력해 줘."

    print(msg)

    response = openai.chat.completions.create(
        model=model,
        messages=[
            {"role": "user", "content": prompt},
        ],
        temperature=0,
    )

    output_text = response.choices[0].message.content
    return output_text
