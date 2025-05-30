<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta http-equiv="X-UA-Compatible" content="ie=edge"/>
    <title>Профиль пользователя</title>
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;700&display=swap" rel="stylesheet"/>
    <script src="/js/showModalAddress.js"></script>
    <script src="/js/showModalAbout.js"></script>
    <link rel="stylesheet" href="/css/style.css"/>
</head>
<body>
<#include "header.ftlh">

<!-- Основной контент -->
<main class="container">
    <h1>Профиль</h1>

    <!-- Информация о пользователе -->
    <section class="user-info">
        <p>Имя: <strong>${user.username}</strong></p>
        <p>Почта: <strong>${user.email}</strong></p>
    </section>

    <!-- Адрес -->
    <section class="user-address">
        <h2>Ваш адрес</h2>
        <#if !userAddress?has_content>
            <button type="button" onclick="showWindow()">Добавить адрес</button>
            <!-- Модальное окно -->
            <#include "modalWindowAddress.ftlh">
        <#else>
            <div class="addressCard">
                <p>Город: ${userAddress.city}</p>
                <p>Улица: ${userAddress.street}</p>
                <p>Почтовый индекс: ${userAddress.postalCode}</p>
                <p>Страна: ${userAddress.country}</p>
                <button>Удалить адрес</button>
                <button>Изменить адрес</button>
            </div>
        </#if>
    </section>

    <!-- Значки -->
    <section class="user-badges">
        <#if user.badges?has_content>
            <h2>Ваши значки:</h2>
            <div class="badges-grid">
                <#list user.badges as badge>
                    <div class="badge-card">
                        <img src="http://localhost:8080/files/${badge.iconImageInfo.storageName}" alt="Значок"
                             class="badge-image"/>
                        <h4>${badge.name}</h4>
                        <p>${badge.description}</p>
                        <p>Требуется баллов: ${badge.requiredPoints}</p>
                    </div>
                </#list>
            </div>
        <#else>
            <h2>У вас еще нет значков</h2>
        </#if>
    </section>
    <#if user.role == "ADMIN">
        <button><a href="/admin">Админ Панель</a></button>
    </#if>
    <button type="button" onclick="document.getElementById('logout-form').submit();">Выйти</button>
</main>
<form id="logout-form" action="/logout" method="post" style="display: none;">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>
<#include "modalWindowAbout.ftlh">
<!-- Подвал -->
<#include "footer.ftlh">
</body>
</html>