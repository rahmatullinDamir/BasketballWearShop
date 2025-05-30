<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>sign in</title>
</head>
<body>
<form action="/signIn" method="POST">
    <input type="email" name="email" placeholder="Email" required />
    <input type="password" name="password" placeholder="Password" required />
    <!-- CSRF Token -->
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />

    <input type="submit" value="Войти" />
</form>
<#if confirmEmail??>
    <p class="confirmEmail">${confirmEmail}</p>
</#if>
<a href="/signUp">Нет аккаунта? Зарегистрируйтесь.</a>
</body>
</html>