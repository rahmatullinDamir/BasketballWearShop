<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Статистика размеров</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
    <link rel="stylesheet" href="/css/style.css"/>
</head>
<body>
    <#include "../header.ftlh">

    <div class="container mt-5">
        <h2>Статистика популярности размеров</h2>
        <p class="text-muted mb-4">Анализ заказов по размерам для каждого продукта</p>

        <div class="table-responsive">
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>Продукт</th>
                        <th>Размер</th>
                        <th>Количество заказов</th>
                        <th>Общее количество</th>
                    </tr>
                </thead>
                <tbody>
                    <#assign currentProduct = "">
                    <#list statistics as stat>
                        <tr class="${(currentProduct != stat.productName)?then('table-primary', '')}">
                            <#if currentProduct != stat.productName>
                                <#assign currentProduct = stat.productName>
                            </#if>
                            <td>${stat.productName}</td>
                            <td>${stat.size}</td>
                            <td>${stat.orderCount}</td>
                            <td>${stat.totalQuantity}</td>
                        </tr>
                    <#else>
                        <tr>
                            <td colspan="5" class="text-center">
                                Нет данных о заказах
                            </td>
                        </tr>
                    </#list>
                </tbody>
            </table>
        </div>

        <div class="mt-4">
            <h3>Рекомендации</h3>
            <ul class="list-group">
                <#assign prevProduct = "">
                <#list statistics as stat>
                    <#if prevProduct != stat.productName>
                        <#assign prevProduct = stat.productName>
                        <li class="list-group-item">
                            <strong>${stat.productName}</strong>: 
                            Самый популярный размер - ${stat.size} 
                            (заказан ${stat.totalQuantity} раз)
                        </li>
                    </#if>
                </#list>
            </ul>
        </div>
    </div>

    <#include "../footer.ftlh">

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
</body>
</html> 