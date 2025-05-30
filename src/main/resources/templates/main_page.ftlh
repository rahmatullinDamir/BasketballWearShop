<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Baller Merch</title>
    <link rel="stylesheet" href="/css/style.css">
    <script src="/js/showModalAddress.js"></script>
    <script src="/js/showModalAbout.js"></script>
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;700&display=swap" rel="stylesheet">
</head>
<body>
<section class="main">
    <#include "header.ftlh">
    <div class="cart-icon"></div> <!-- Корзина -->
    <div class="container">
        <section class="banner">
            <img src="/images/background.webp" alt="Мерч для ценителей баскетбола">
        </section>
        <#if products?has_content>
            <section class="products">
                <#list products as product>
                    <div class="product">
                        <#if product.discount != 0>
                            <div class="product-tag">NEW<br>${product.discount}%</div>
                        </#if>
                        <img src="http://localhost:8080/files/${product.images[0].storageName}" alt="img"
                             class="product-image">
                        <h3>${product.name}</h3>
                        <#if product.discount != 0>
                            <div class="price">${product.discountPrice} ₽. <span>${product.price}</span></div>
                        <#else>
                            <div class="price">${product.price} ₽.</div>
                        </#if>
                        <button class="add-to-cart">В корзину</button>
                    </div>
                </#list>
            </section>
        <#else>
            <p>Нет доступных товаров.</p>
        </#if>
    </div>

    <#include "modalWindowAddress.ftlh">
    <#include "modalWindowAbout.ftlh">

    <#include "footer.ftlh">
    <script>
        <#if userAddress?has_content && userAddress.isEmpty()>
            window.onload = function () {
                setTimeout(showWindow, 500);
            };
        </#if>
    </script>
</section>
</body>
</html>