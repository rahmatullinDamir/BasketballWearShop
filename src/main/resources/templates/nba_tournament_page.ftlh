<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>NBA Таблица | Baller Merch</title>
    <link rel="stylesheet" href="/css/style.css">
    <link rel="stylesheet" href="/css/tournamentTable.css">
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;500;600;700&display=swap" rel="stylesheet">
</head>
<body>
<#include "header.ftlh">

<div class="tournament-container">
    <div class="tournament-header">
        <h1>Таблица НБА 2025</h1>
        <div class="tournament-stats">
            <div class="stat-card">
                <span class="stat-icon">🏀</span>
                <div class="stat-info">
                    <span class="stat-label">Всего команд</span>
                    <span class="stat-value">${tableData?size}</span>
                </div>
            </div>
            <div class="stat-card">
                <span class="stat-icon">🏆</span>
                <div class="stat-info">
                    <span class="stat-label">Сезон</span>
                    <span class="stat-value">2024-25</span>
                </div>
            </div>
        </div>
    </div>

    <div class="tournament-content">
        <div class="table-container">
            <div class="table-wrapper">
                <table class="tournament-table">
                    <thead>
                        <tr>
                            <th>Команда</th>
                            <th>Город</th>
                            <th class="numeric">В</th>
                            <th class="numeric">П</th>
                            <th class="numeric">%</th>
                            <th>Конф.</th>
                            <th class="numeric">М.К.</th>
                            <th>Див.</th>
                            <th class="numeric">М.Д.</th>
                            <th>Серия</th>
                            <th class="numeric">О.З.</th>
                            <th class="numeric">О.П.</th>
                        </tr>
                    </thead>
                    <tbody>
                        <#list tableData as team>
                            <tr>
                                <td class="team-name">${team.name}</td>
                                <td>${team.city}</td>
                                <td class="numeric">${team.wins}</td>
                                <td class="numeric">${team.losses}</td>
                                <td class="numeric">${team.percentage?string["0.000"]}</td>
                                <td>${team.conference}</td>
                                <td class="numeric">${team.conferenceRank}</td>
                                <td>${team.division}</td>
                                <td class="numeric">${team.divisionRank}</td>
                                <td class="streak <#if team.streakDescription?starts_with('W')>win<#else>loss</#if>">
                                    ${team.streakDescription}
                                </td>
                                <td class="numeric">${team.pointsPerGameFor?string["0.00"]}</td>
                                <td class="numeric">${team.pointsPerGameAgainst?string["0.00"]}</td>
                            </tr>
                        </#list>
                    </tbody>
                </table>
            </div>
        </div>

        <div class="table-legend">
            <div class="legend-item">
                <span class="legend-color win"></span>
                <span class="legend-text">Победная серия</span>
            </div>
            <div class="legend-item">
                <span class="legend-color loss"></span>
                <span class="legend-text">Серия поражений</span>
            </div>
            <div class="legend-item">
                <span class="legend-text small">В - Победы, П - Поражения, М.К. - Место в конференции,</span>
            </div>
            <div class="legend-item">
                <span class="legend-text small">М.Д. - Место в дивизионе, О.З. - Очки за игру, О.П. - Очки пропущенные</span>
            </div>
        </div>
    </div>
</div>

<#include "footer.ftlh">
</body>
</html>