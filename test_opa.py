import requests
import json

# URL OPA
url = "http://localhost:8181/v1/data/auth/allow"

# body requestnya sama kyk yg di curl td
body = {
    "input": {
        "role": "teacher",
        "action": "read",
        "resource": "student_grade"
    }
}

# POST request ke OPA
response = requests.post(url, json=body)

# hasilnya diprint
if response.status_code == 200:
    print("Response JSON:", response.json())
    result = response.json().get("result")
    print("Access allowed?" , result)
else:
    print("Request failed with status code:", response.status_code)