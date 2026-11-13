import time
import requests
import re
import uuid
import warnings
import urllib3
from datetime import datetime

warnings.simplefilter('ignore', urllib3.exceptions.NotOpenSSLWarning)

BASE_URL = "http://localhost:8080"
LOGIN_URL = f"{BASE_URL}/signIn"

LOGIN_DATA = {
    "email": "admin@ballermerch.com",
    "password": "admin123"
}

session = requests.Session()


def get_csrf_token(html_text):
    match = re.search(r'([a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12})', html_text)
    if match:
        return match.group(1)

    patterns = [
        r'name=["\']_csrf["\'].*?value=["\']([^"\']+)["\']',
        r'value=["\']([^"\']+)["\'].*?name=["\']_csrf["\']',
        r'content=["\']([^"\']+)["\'].*?name=["\']_csrf["\']'
    ]
    for p in patterns:
        match = re.search(p, html_text)
        if match:
            return match.group(1)
    return None

try:
    login_page = session.get(LOGIN_URL, timeout=5)
    initial_csrf = get_csrf_token(login_page.text)

    if not initial_csrf:
        exit(1)

    LOGIN_DATA["_csrf"] = initial_csrf
except Exception as e:
    exit(1)

login_response = session.post(LOGIN_URL, data=LOGIN_DATA, timeout=10)

if "error" in login_response.url or login_response.status_code == 403:
    print("ОШИБКА АВТОРИЗАЦИИ: Неверный логин или пароль!")
    exit(1)
else:
    print("Авторизация успешна")

main_page = session.get(f"{BASE_URL}/", timeout=5)
active_csrf = get_csrf_token(main_page.text)

if not active_csrf:
    profile_page = session.get(f"{BASE_URL}/profile", timeout=5)
    active_csrf = get_csrf_token(profile_page.text)

if not active_csrf:
    print("Возможное решение: временно отключите CSRF в SecurityConfig.java для тестирования метрик.")
    exit(1)

while True:
    try:
        unique_id = str(uuid.uuid4())[:8]

        signup_payload = {
            "_csrf": active_csrf,
            "email": f"user_{unique_id}@example.com",
            "password": "password123",
            "firstName": "Test",
            "lastName": "User"
        }
        resp_signup = session.post(f"{BASE_URL}/sign-up", data=signup_payload, timeout=2)

        cart_payload = {
            "_csrf": active_csrf,
            "productId": 1,
            "sizeId": 1
        }
        session.post(f"{BASE_URL}/cart", data=cart_payload, timeout=2)

        order_payload = {
            "_csrf": active_csrf,
            "addressId": 1
        }
        resp_order = session.post(f"{BASE_URL}/order", data=order_payload, timeout=2)

        cert_payload = {
            "_csrf": active_csrf,
            "amount": 1000,
            "recipientEmail": f"friend_{unique_id}@example.com"
        }
        resp_cert = session.post(f"{BASE_URL}/gift-certificate", data=cert_payload, timeout=2)

        status_order = resp_order.status_code
        status_signup = resp_signup.status_code
        status_cert = resp_cert.status_code

        current_time = datetime.now().strftime("%H:%M:%S")

        if status_order < 500 and status_signup < 500 and status_cert < 500:
            print(
                f"{current_time} - Метрики обновлены (Order: {status_order}, SignUp: {status_signup}, Cert: {status_cert})")
        else:
            print(
                f"{current_time} - БД лежит! Order: {status_order} | SignUp: {status_signup} | Cert: {status_cert}")

    except requests.exceptions.RequestException:
        current_time = datetime.now().strftime("%H:%M:%S")
        print(f"{current_time} - Сервер недоступен.

    time.sleep(1)