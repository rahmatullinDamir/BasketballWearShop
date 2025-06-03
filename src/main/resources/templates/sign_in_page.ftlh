<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Вход | Baller Merch</title>
    <link rel="stylesheet" href="/css/style.css">
    <link rel="stylesheet" href="/css/signInPage.css">
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;500;600;700&display=swap" rel="stylesheet">
</head>
<body>
<div class="auth-container">
    <div class="auth-box">
        <div class="auth-header">
            <h1>Вход в аккаунт</h1>
            <p>Добро пожаловать обратно!</p>
        </div>
        
        <form action="/signIn" method="POST" class="auth-form" id="signInForm">
            <div class="form-group">
                <label for="email">Email</label>
                <input type="email" id="email" name="email" placeholder="Введите ваш email" required />
            </div>
            
            <div class="form-group">
                <label for="password">Пароль</label>
                <input type="password" id="password" name="password" placeholder="Введите ваш пароль" required />
            </div>

            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />

            <button type="submit" id="signInSubmitBtn" class="btn btn-primary">Войти</button>
        </form>

        <#if confirmEmail??>
            <div class="auth-message ${confirmEmail?contains('успешно')?then('success','error')}">
                ${confirmEmail}
            </div>
        </#if>

        <div class="auth-links">
            <a href="/signUp" class="signup-link">Нет аккаунта? Зарегистрируйтесь</a>
            <a href="/" class="home-link">Вернуться на главную</a>
        </div>
    </div>
</div>
<script src="/js/formProtection.js"></script>
</body>
</html>