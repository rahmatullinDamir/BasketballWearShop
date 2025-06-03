<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Корзина | Baller Merch</title>
    <link rel="stylesheet" href="/css/style.css">
    <link rel="stylesheet" href="/css/cartPage.css">
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;500;600;700&display=swap" rel="stylesheet">
</head>
<body>
<#include "header.ftlh">

<div class="cart-container">
    <div class="cart-header">
        <h1>Корзина</h1>
        <a href="/" class="continue-shopping">Продолжить покупки</a>
    </div>

    <div class="cart-content">
        <div class="cart-items">
            <#if cart.items?size == 0>
                <div class="empty-cart">
                    <span class="empty-cart-icon">🛒</span>
                    <p>Ваша корзина пуста</p>
                    <a href="/" class="cart-button primary">Перейти к покупкам</a>
                </div>
            <#else>
                <div class="cart-list">
                    <#list cart.items as item>
                        <div class="cart-item">
                            <div class="item-info">
                                <h3 class="item-name">${item.productName}</h3>
                                <div class="item-details">
                                    <span class="item-size">Размер: ${item.productSize}</span>
                                    <span class="item-price">${item.price} ₽</span>
                                </div>
                            </div>
                            <div class="item-actions">
                                <div class="quantity-control">
                                    <input type="number" class="quantity-input" id="quantity" 
                                           data-item-id="${item.id}" value="${item.quantity!1}" min="1">
                                    <button class="update-cart-btn" data-item-id="${item.id}">
                                        <span class="update-icon">↻</span>
                                    </button>
                                </div>
                                <div class="item-total">
                                    <span id="total-price-${item.id}">${item.price * item.quantity} ₽</span>
                                    <button class="delete-cart-btn" data-item-id="${item.id}">
                                        <span class="delete-icon">×</span>
                                    </button>
                                </div>
                            </div>
                        </div>
                    </#list>
                </div>
            </#if>
        </div>

        <#if cart.items?size gt 0>
            <div class="cart-sidebar">
                <!-- Секция подарочного сертификата -->
                <div class="gift-certificate-section">
                    <div class="section-header">
                        <h2>Подарочный сертификат</h2>
                        <span class="section-icon">🎁</span>
                    </div>
                    
                    <#if cart.appliedCertificate??>
                        <div class="applied-certificate">
                            <div class="certificate-info">
                                <p class="certificate-code">Код: ${cart.appliedCertificate.code}</p>
                                <p class="certificate-amount">Сумма: ${cart.appliedCertificate.amount} ₽</p>
                            </div>
                            <button id="remove-certificate" class="cart-button secondary">
                                Удалить сертификат
                            </button>
                        </div>
                    <#else>
                        <div class="certificate-form">
                            <input type="text" id="certificate-code" 
                                   placeholder="Введите код сертификата" class="certificate-input">
                            <button id="apply-certificate" class="cart-button primary">
                                Применить сертификат
                            </button>
                        </div>
                    </#if>
                    <div id="certificate-message" class="certificate-message"></div>
                </div>

                <!-- Итоговая информация -->
                <div class="cart-summary">
                    <div class="summary-row">
                        <span>Сумма заказа:</span>
                        <span id="cart-total">${cart.total} ₽</span>
                    </div>
                    <#if cart.discount gt 0>
                        <div class="summary-row discount">
                            <span>Скидка:</span>
                            <span id="cart-discount">-${cart.discount}%</span>
                        </div>
                    </#if>
                    <#if cart.appliedCertificate??>
                        <div class="summary-row certificate">
                            <span>Сертификат:</span>
                            <span>-${cart.appliedCertificate.amount} ₽</span>
                        </div>
                    </#if>
                    <div class="summary-row total">
                        <span>Итого к оплате:</span>
                        <span id="cart-final-total">${cart.total} ₽</span>
                    </div>

                    <#if userAddress??>
                        <form action="/orders/create" method="post" id="orderForm">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <button type="submit" id="orderSubmitBtn" class="cart-button checkout">Оформить заказ</button>
                        </form>
                    <#else>
                        <div class="checkout-section">
                            <button type="button" onclick="showWindow()" class="cart-button checkout">
                                Оформить заказ
                            </button>
                            <p class="address-warning">
                                Для оформления заказа необходимо указать адрес доставки
                            </p>
                        </div>
                        <#include "modalWindowEditAddress.ftlh">
                    </#if>
                </div>
            </div>
        </#if>
    </div>

    <#if RequestParameters.error??>
        <div class="error-message">
            ${RequestParameters.error}
        </div>
    </#if>
</div>

<script src="/js/showModalAddress.js"></script>
<script>
    document.addEventListener('DOMContentLoaded', function () {
        const buttons = document.querySelectorAll('.update-cart-btn');
        const deleteButtons = document.querySelectorAll('.delete-cart-btn');
        const applyCertificateBtn = document.getElementById('apply-certificate');
        const removeCertificateBtn = document.getElementById('remove-certificate');

        if (applyCertificateBtn) {
            applyCertificateBtn.addEventListener('click', function() {
                const code = document.getElementById('certificate-code').value;
                if (!code) {
                    showCertificateMessage('Введите код сертификата', 'error');
                    return;
                }

                fetch('/cart/apply-certificate?code=' + encodeURIComponent(code), {
                    method: 'POST',
                    headers: {
                        'X-CSRF-TOKEN': '${_csrf.token}',
                    }
                })
                .then(response => {
                    if (!response.ok) {
                        return response.text().then(text => { throw new Error(text) });
                    }
                    return response.json();
                })
                .then(data => {
                    location.reload();
                })
                .catch(error => {
                    showCertificateMessage(error.message, 'error');
                });
            });
        }

        if (removeCertificateBtn) {
            removeCertificateBtn.addEventListener('click', function() {
                fetch('/cart/remove-certificate', {
                    method: 'POST',
                    headers: {
                        'X-CSRF-TOKEN': '${_csrf.token}'
                    }
                })
                .then(response => {
                    if (!response.ok) {
                        return response.text().then(text => { throw new Error(text) });
                    }
                    return response.json();
                })
                .then(data => {
                    location.reload();
                })
                .catch(error => {
                    showCertificateMessage(error.message, 'error');
                });
            });
        }

        function showCertificateMessage(message, type) {
            const messageElement = document.getElementById('certificate-message');
            messageElement.textContent = message;
            messageElement.className = 'certificate-message ' + type;
            setTimeout(() => {
                messageElement.textContent = '';
                messageElement.className = 'certificate-message';
            }, 5000);
        }

        deleteButtons.forEach(button => {
            button.addEventListener('click', function () {
                const itemId = this.getAttribute('data-item-id');

                fetch('/cart/remove', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                        'X-CSRF-TOKEN': '${_csrf.token}'
                    },
                    body: JSON.stringify({
                        itemId: itemId
                    })
                })
                    .then(response => {
                        if (!response.ok) {
                            throw new Error('Ошибка сети');
                        }
                        return response.text();
                    })
                    .then(() => {
                        location.reload();
                    })
                    .catch(error => {
                        console.error('Ошибка:', error);
                        alert('Не удалось удалить товар из корзины');
                    });
            });
        });

        buttons.forEach(button => {
            button.addEventListener('click', function () {
                const itemId = this.getAttribute('data-item-id');
                const quantityInput = document.querySelector("input[data-item-id='" + itemId + "']");
                const quantity = quantityInput.value;

                fetch('/cart/update-quantity', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                        'X-CSRF-TOKEN': '${_csrf.token}'
                    },
                    body: JSON.stringify({
                        itemId: itemId,
                        quantity: quantity
                    })
                })
                    .then(response => {
                        if (!response.ok) {
                            throw new Error('Ошибка сети');
                        }
                        return response.json();
                    })
                    .then(response => {
                        const totalElement = document.getElementById('cart-total');
                        totalElement.textContent = response.total + ' ₽';

                        const discountElement = document.getElementById('cart-discount');
                        if (response.discount > 0) {
                            discountElement.textContent = '-' + response.discount + '%';
                        } else if (discountElement) {
                            discountElement.parentElement.remove();
                        }

                        response.items.forEach(item => {
                            const priceElement = document.getElementById("total-price-" + itemId);
                            if (priceElement) {
                                priceElement.textContent = item.totalPrice + ' ₽';
                            }
                        });
                    })
                    .catch(error => {
                        console.error('Ошибка:', error);
                        alert('Не удалось обновить корзину');
                    });
            });
        });
    });
</script>
<#include "footer.ftlh">
<script src="/js/formProtection.js"></script>
</body>
</html>