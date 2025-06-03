<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Регистрация | Baller Merch</title>
    <link rel="stylesheet" href="/css/style.css">
    <link rel="stylesheet" href="/css/signInPage.css">
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;500;600;700&display=swap" rel="stylesheet">
</head>
<body>
<div class="auth-container">
    <div class="auth-box">
        <div class="auth-header">
            <h1>Регистрация</h1>
            <p>Создайте аккаунт для доступа ко всем возможностям</p>
        </div>
        
        <form action="/signUp" method="POST" class="auth-form" id="signUpForm">
            <div class="form-group">
                <label for="username">Имя пользователя</label>
                <input type="text" id="username" name="username" placeholder="Введите ваше имя" required>
            </div>

            <div class="form-group">
                <label for="email">Email</label>
                <input type="email" id="email" name="email" placeholder="Введите ваш email" required>
            </div>
            
            <div class="form-group">
                <label for="password">Пароль</label>
                <input type="password" id="password" name="password" placeholder="Придумайте пароль" required>
                <span class="password-hint">Минимум 8 символов, включая буквы и цифры</span>
            </div>

            <button type="submit" id="signUpSubmitBtn" class="btn btn-primary">Зарегистрироваться</button>
        </form>

        <div class="auth-links">
            <a href="/signIn" class="signin-link">Уже есть аккаунт? Войдите</a>
            <a href="/" class="home-link">Вернуться на главную</a>
        </div>
    </div>
</div>

<script>
document.addEventListener('DOMContentLoaded', function() {
    const form = document.querySelector('.auth-form');
    const passwordInput = document.getElementById('password');
    
    form.addEventListener('submit', function(e) {
        if (passwordInput.value.length < 8) {
            e.preventDefault();
            alert('Пароль должен содержать минимум 8 символов');
            return;
        }
        
        if (!/\d/.test(passwordInput.value) || !/[a-zA-Z]/.test(passwordInput.value)) {
            e.preventDefault();
            alert('Пароль должен содержать как буквы, так и цифры');
            return;
        }
    });
});
</script>

<script src="/js/formProtection.js"></script>
</body>
</html>