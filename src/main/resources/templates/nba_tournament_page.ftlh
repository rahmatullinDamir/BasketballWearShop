<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>NBA Таблица</title>
    <link rel="stylesheet" href="/css/style.css">
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;700&display=swap" rel="stylesheet">
</head>
<body>
<#include "header.ftlh">
<h1>Таблица НБА 2025</h1>
<!-- Таблица -->
<table>
    <thead>
    <tr>
        <th>Команда</th>
        <th>Город</th>
        <th>Победы</th>
        <th>Поражения</th>
        <th>% побед</th>
        <th>Конференция</th>
        <th>Место в конф.</th>
        <th>Дивизион</th>
        <th>Место в див.</th>
        <th>Серия</th>
        <th>Очки за игру</th>
        <th>Пропущено очков</th>
    </tr>
    </thead>
    <tbody>
    <#list tableData as team>
        <tr>
            <td>${team.name}</td>
            <td>${team.city}</td>
            <td>${team.wins}</td>
            <td>${team.losses}</td>
            <td>${team.percentage?string["0.000"]}</td>
            <td>${team.conference}</td>
            <td>${team.conferenceRank}</td>
            <td>${team.division}</td>
            <td>${team.divisionRank}</td>
            <td class="<#if team.streakDescription?starts_with('W')>win<#else>loss</#if>">
                ${team.streakDescription}
            </td>
            <td>${team.pointsPerGameFor?string["0.00"]}</td>
            <td>${team.pointsPerGameAgainst?string["0.00"]}</td>
        </tr>
    </#list>
    </tbody>
</table>
<#include "footer.ftlh">
</body>
</html>