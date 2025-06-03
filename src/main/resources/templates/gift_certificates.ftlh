<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Подарочные сертификаты</title>
    <link rel="stylesheet" href="/css/style.css">
    <link rel="stylesheet" href="/css/giftCertificatePage.css">
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;500;600;700&display=swap" rel="stylesheet"/>
</head>
<body>
<#include "header.ftlh">

<div class="container">
    <h1>Подарочные сертификаты</h1>

    <!-- Форма покупки сертификата -->
    <section class="purchase-certificate">
        <h2>Купить подарочный сертификат</h2>
        <form id="purchaseCertificateForm" class="certificate-form">
            <div class="form-group">
                <label for="amount">Сумма сертификата (₽):</label>
                <input type="number" id="amount" name="amount" min="500" max="100000" step="100" required>
            </div>
            <button type="submit" class="btn-primary">Купить сертификат</button>
        </form>
    </section>

    <!-- Купленные сертификаты -->
    <section class="purchased-certificates">
        <h2>Мои сертификаты</h2>
        <#if purchasedCertificates?size == 0>
            <p>У вас нет купленных сертификатов</p>
        <#else>
            <div class="certificates-grid">
                <#list purchasedCertificates as cert>
                    <div class="certificate-card">
                        <div class="certificate-header">
                            <span class="certificate-code">${cert.code}</span>
                            <span class="certificate-amount">${cert.amount} ₽</span>
                        </div>
                        <div class="certificate-body">
                            <p>Статус: ${cert.status}</p>
                            <p>Создан: ${cert.createdAt}</p>
                            <p>Действителен до: ${cert.expiresAt}</p>
                            <#if cert.status == 'ACTIVE'>
                                <div class="certificate-actions">
                                    <button class="btn-copy" onclick="copyToClipboard('${cert.code}')">
                                        Скопировать код
                                    </button>
                                </div>
                            </#if>
                        </div>
                    </div>
                </#list>
            </div>
        </#if>
    </section>

    <!-- Использованные сертификаты -->
    <section class="used-certificates">
        <h2>Использованные сертификаты</h2>
        <#if usedCertificates?size == 0>
            <p>У вас нет использованных сертификатов</p>
        <#else>
            <div class="certificates-grid">
                <#list usedCertificates as cert>
                    <div class="certificate-card">
                        <div class="certificate-header">
                            <span class="certificate-code">${cert.code}</span>
                            <span class="certificate-amount">${cert.amount} ₽</span>
                        </div>
                        <div class="certificate-body">
                            <p>Использован: ${cert.usedAt}</p>
                            <p>Покупатель: ${cert.buyerEmail}</p>
                        </div>
                    </div>
                </#list>
            </div>
        </#if>
    </section>

    <#if success??>
        <div class="alert alert-success">
            ${success}
        </div>
    </#if>

    <#if error??>
        <div class="alert alert-error">
            ${error}
        </div>
    </#if>
</div>

<script>
document.getElementById('purchaseCertificateForm').addEventListener('submit', function(e) {
    e.preventDefault();

    const formData = {
        amount: document.getElementById('amount').value
    };

    fetch('/gift-certificates/purchase', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'X-CSRF-TOKEN': '${_csrf.token}'
        },
        body: JSON.stringify(formData)
    })
    .then(response => {
        if (!response.ok) {
            return response.text().then(text => { throw new Error(text) });
        }
        return response.json();
    })
    .then(() => {
        location.reload();
    })
    .catch(error => {
        alert(error.message);
    });
});

function copyToClipboard(text) {
    navigator.clipboard.writeText(text).then(() => {
        alert('Код сертификата скопирован в буфер обмена');
    }).catch(err => {
        console.error('Ошибка при копировании: ', err);
    });
}
</script>
</body>
</html> 