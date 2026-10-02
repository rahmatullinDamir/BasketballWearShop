<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta http-equiv="X-UA-Compatible" content="ie=edge"/>
    <title>Профиль | Baller Merch</title>
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;500;600;700&display=swap" rel="stylesheet"/>
    <script src="/js/showModalAbout.js"></script>
    <link rel="stylesheet" href="/css/style.css"/>
</head>
<body>
<#include "header.ftlh">

<div class="profile-container">
    <div class="profile-header">
        <h1>Личный кабинет</h1>
        <p>Добро пожаловать, ${user.username}!</p>
    </div>

    <div class="profile-content">
        <!-- Основная информация -->
        <div class="profile-section user-info-section">
            <div class="section-header">
                <h2>Основная информация</h2>
                <span class="section-icon">👤</span>
            </div>
            <div class="info-content">
                <div class="info-item">
                    <span class="info-label">Имя:</span>
                    <span class="info-value">${user.username}</span>
                </div>
                <div class="info-item">
                    <span class="info-label">Email:</span>
                    <span class="info-value">${user.email}</span>
                </div>
            </div>
        </div>

        <!-- Адрес -->
        <div class="profile-section address-section">
            <div class="section-header">
                <h2>Адрес доставки</h2>
                <span class="section-icon">📍</span>
            </div>
            <div class="address-content">
                <#if !userAddress?has_content>
                    <div class="no-address">
                        <p>У вас пока нет сохраненного адреса доставки</p>
                        <button class="profile-button" onclick="showWindow()">Добавить адрес</button>
                    </div>
                <#else>
                    <div class="address-card">
                        <div class="address-details">
                            <div class="address-item">
                                <span class="address-label">Город:</span>
                                <span class="address-value">${userAddress.city}</span>
                            </div>
                            <div class="address-item">
                                <span class="address-label">Улица:</span>
                                <span class="address-value">${userAddress.street}</span>
                            </div>
                            <div class="address-item">
                                <span class="address-label">Индекс:</span>
                                <span class="address-value">${userAddress.postalCode}</span>
                            </div>
                            <div class="address-item">
                                <span class="address-label">Страна:</span>
                                <span class="address-value">${userAddress.country}</span>
                            </div>
                        </div>
                        <div class="address-actions">
                            <button class="profile-button secondary" onclick="changeAddress(${userAddress.id})">Изменить</button>
                            <button class="profile-button danger" onclick="deleteAddress(${userAddress.id})">Удалить</button>
                        </div>
                    </div>
                </#if>
            </div>
        </div>

        <!-- Значки -->
        <div class="profile-section badges-section">
            <div class="section-header">
                <h2>Мои значки</h2>
                <span class="section-icon">🏆</span>
            </div>
            <div class="badges-content">
                <#if user.badges?has_content>
                    <div class="badges-grid">
                        <#list user.badges as badge>
                            <div class="badge-card">
                                <div class="badge-icon">
                                       <#if badge.iconImageInfo?? && badge.iconImageInfo.imageUrl??>
                                           <img src="${badge.iconImageInfo.imageUrl}" alt="${badge.name}">
                                       <#else>
                                           <#assign badgeFileName = "default-badge">

                                           <#if badge.name == 'Новичок'>
                                               <#assign badgeFileName = "novice">
                                           <#elseif badge.name == 'Боллер'>
                                               <#assign badgeFileName = "boller">
                                           <#elseif badge.name == 'Знаток доставки'>
                                               <#assign badgeFileName = "delivery">
                                           </#if>

                                           <img src="/images/badges/${badgeFileName}.png" alt="${badge.name}">
                                       </#if>
                                </div>
                                <div class="badge-info">
                                    <h4>${badge.name}</h4>
                                    <p class="badge-description">${badge.description}</p>
                                    <span class="badge-points">Баллов: ${badge.requiredPoints}</span>
                                </div>
                            </div>
                        </#list>
                    </div>
                <#else>
                    <div class="no-badges">
                        <p>У вас пока нет значков</p>
                        <p class="hint">Совершайте покупки, чтобы получать значки!</p>
                    </div>
                </#if>
            </div>
        </div>

        <div class="profile-section orders-section">
            <div class="section-header">
                <h2>История заказов</h2>
                <span class="section-icon">📦</span>
            </div>
            <div class="orders-content">
                <#if orders?? && orders?size gt 0>
                    <div class="orders-list">
                        <#list orders as order>
                            <div class="order-card">
                                <div class="order-header">
                                    <div class="order-info">
                                        <span class="order-number">Заказ #${order.id}</span>
                                        <span class="order-date">${order.createdAt}</span>
                                    </div>
                                    <div class="order-status">
                                        <span class="status-badge">Выполнен</span>
                                    </div>
                                </div>
                                <div class="order-items">
                                    <#list order.items as item>
                                        <div class="order-item">
                                            <div class="item-details">
                                                <span class="item-name">${item.productName}</span>
                                                <div class="item-meta">
                                                    <span class="item-size">Размер: ${item.size}</span>
                                                    <span class="item-quantity">×${item.quantity}</span>
                                                </div>
                                            </div>
                                            <span class="item-price">${item.price} ₽</span>
                                        </div>
                                    </#list>
                                </div>
                                <div class="order-footer">
                                    <div class="order-summary">
                                        <#if order.discount gt 0>
                                            <div class="discount-info">
                                                <span class="discount-label">Скидка:</span>
                                                <span class="discount-value">-${order.discount}%</span>
                                            </div>
                                        </#if>
                                        <div class="total-info">
                                            <span class="total-label">Итого:</span>
                                            <span class="total-value">${order.total} ₽</span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </#list>
                    </div>
                <#else>
                    <div class="no-orders">
                        <p>У вас пока нет заказов</p>
                        <a href="/" class="profile-button">Перейти к покупкам</a>
                    </div>
                </#if>
            </div>
        </div>

        <div class="profile-actions">
            <#if user.role == "ADMIN">
                <a href="/admin" class="profile-button admin">Панель администратора</a>
            </#if>
            <a href="/statistics" class="profile-button">Статистика заказов</a>
            <button type="button" class="profile-button danger" onclick="document.getElementById('logout-form').submit();">
                Выйти из аккаунта
            </button>
        </div>
    </div>
</div>

<form id="logout-form" action="/logout" method="post" style="display: none;">
    <input type="hidden" id="csrf" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>

<#include "modalWindowAbout.ftlh">
<#include "modalWindowEditAddress.ftlh">
<#include "modalWindowAddress.ftlh">
<#include "footer.ftlh">
<script src="/js/deleteAndUpdateAddress.js"></script>
<script src="/js/showModalAddress.js"></script>
</body>
</html>