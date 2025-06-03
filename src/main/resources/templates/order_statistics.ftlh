<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta http-equiv="X-UA-Compatible" content="ie=edge"/>
    <title>Статистика заказов | Baller Merch</title>
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;500;600;700&display=swap" rel="stylesheet"/>
    <link rel="stylesheet" href="/css/style.css"/>
    <link rel="stylesheet" href="/css/orderStatistics.css"/>
</head>
<body>
<#include "header.ftlh">

<div class="statistics-container">
    <div class="statistics-header">
        <h1>Статистика заказов</h1>
    </div>

    <!-- Общая статистика -->
    <div class="statistics-section">
        <h2>Ваша статистика покупок</h2>
        <div class="statistics-grid">
            <div class="stat-card">
                <span class="stat-label">Количество заказов</span>
                <span class="stat-value"><#if statistics??>${statistics.orderCount!0}<#else>0</#if></span>
            </div>
            <div class="stat-card">
                <span class="stat-label">Общая сумма покупок</span>
                <span class="stat-value"><#if statistics??>${statistics.totalSpent!0}<#else>0</#if> ₽</span>
            </div>
            <div class="stat-card">
                <span class="stat-label">Средняя сумма заказа</span>
                <span class="stat-value"><#if statistics??>${statistics.averageOrderValue!0}<#else>0</#if> ₽</span>
            </div>
            <div class="stat-card">
                <span class="stat-label">Максимальная сумма заказа</span>
                <span class="stat-value"><#if statistics??>${statistics.maxOrderValue!0}<#else>0</#if> ₽</span>
            </div>
        </div>
    </div>
</div>
<#include "footer.ftlh">
</body>
</html> 