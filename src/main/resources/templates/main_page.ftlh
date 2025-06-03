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
    <a href="/cart" class="cart-icon" data-count="0"></a>
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
                        <button class="add-to-cart" data-product-id="${product.id}">В корзину</button>
                    </div>
                </#list>
            </section>
        <#else>
            <p>Нет доступных товаров.</p>
        </#if>
    </div>

    <#include "modalWindowAddress.ftlh">
    <#include "modalWindowAbout.ftlh">
    <#include "modalWindowProduct.ftlh">

    <#include "footer.ftlh">
    <script>
        <#if userAddress?has_content && userAddress.isEmpty()>
        window.onload = function () {
            setTimeout(showWindow, 500);
        };
        </#if>


        document.addEventListener('DOMContentLoaded', function () {
            const addToCartButtons = document.querySelectorAll('.add-to-cart');
            const modal = document.getElementById('productModal');
            const modalTitle = modal.querySelector('.modal-title');
            const modalDescription = modal.querySelector('.modal-description');
            const modalPrice = modal.querySelector('.modal-price');
            const sizeSelector = modal.querySelector('.modal-size-selector');
            const closeModalButton = modal.querySelector('.close');
            const imageSlider = modal.querySelector('.image-slider');

            // Функция открытия модального окна
            function openModal(productId) {
                fetch('/api/products/' + productId)
                    .then(res => res.json())
                    .then(product => {
                        modalTitle.textContent = product.name;
                        modalDescription.textContent = product.description;
                        modalPrice.textContent = product.price + "₽";

                        // Очищаем слайдер
                        imageSlider.innerHTML = '';

                        // Добавляем изображения в слайдер
                        if (product.images && product.images.length > 0) {
                            product.images.forEach((image, index) => {
                                const img = document.createElement('img');
                                img.src = "http://localhost:8080/files/" + image.storageName;
                                img.alt = product.name;

                                if (index === 0) {
                                    img.classList.add('active'); // Первое изображение активно
                                }

                                imageSlider.appendChild(img);
                            });

                            // Добавляем кнопки навигации
                            const prevButton = document.createElement('div');
                            prevButton.className = 'prev';
                            prevButton.textContent = '<'; // Кнопка "Предыдущее"

                            const nextButton = document.createElement('div');
                            nextButton.className = 'next';
                            nextButton.textContent = '>'; // Кнопка "Следующее"

                            imageSlider.appendChild(prevButton);
                            imageSlider.appendChild(nextButton);

                            // Обработчик кликов на кнопки
                            prevButton.addEventListener('click', () => changeImage(-1));
                            nextButton.addEventListener('click', () => changeImage(1));

                            // Инициализируем текущий индекс
                            let currentImageIndex = 0;

                            // Функция для изменения изображения
                            function changeImage(step) {
                                const images = imageSlider.querySelectorAll('img');
                                images[currentImageIndex].classList.remove('active');
                                currentImageIndex = (currentImageIndex + step + images.length) % images.length;
                                images[currentImageIndex].classList.add('active');
                            }
                        }

                        // Заполняем размеры
                        sizeSelector.innerHTML = '<option value="">Выберите размер</option>';
                        if (product.sizes && product.sizes.length > 0) {
                            product.sizes.forEach(sizeObj => {
                                const option = document.createElement('option');
                                option.value = sizeObj;
                                option.textContent = sizeObj;
                                sizeSelector.appendChild(option);
                            });
                        }

                        modal.style.display = 'block';

                        document.getElementById("add-to-cart-modal").addEventListener('click', function () {
                            <#if isAuth>
                            const selectedSize = sizeSelector.value;
                            const selectedQuantity = document.getElementById('quantity').value;
                            const productId = document.querySelector(`.add-to-cart[data-product-id]`).getAttribute('data-product-id');

                            if (!selectedSize) {
                                alert('Пожалуйста, выберите размер.');
                                return;
                            }

                            if (!selectedQuantity || selectedQuantity < 1) {
                                alert('Пожалуйста, укажите корректное количество.');
                                return;
                            }

                            fetch("/cart/add?productId=" + productId + "&size=" + selectedSize + "&quantity=" + selectedQuantity, {
                                method: 'POST',
                                headers: {
                                    'Content-Type': 'application/json',
                                    'X-CSRF-TOKEN': '${_csrf.token}'
                                }
                            })
                                .then(response => response.text())
                                .then(message => {
                                    alert(message);
                                    modal.style.display = 'none';
                                    // Обновляем счетчик корзины
                                    fetch('/cart/count')
                                        .then(res => res.json())
                                        .then(data => {
                                            document.querySelector('.cart-icon').setAttribute('data-count', data.count);
                                        });
                                })
                                .catch(error => {
                                    console.error('Ошибка:', error);
                                    alert('Не удалось добавить товар в корзину');
                                });
                            <#else>
                            alert("Для добавления товара в корзину необходимо войти на сайт.")
                            location.href = "/signIn";
                            </#if>
                        })
                    }).catch(err => console.error('Ошибка загрузки данных:', err));
            }

            // Обработчики событий

            addToCartButtons.forEach(button => {
                button.addEventListener('click', function () {
                    const productId = this.getAttribute('data-product-id');
                    openModal(productId);
                });
            });

            closeModalButton.addEventListener('click', function () {
                modal.style.display = 'none';
            });

            window.addEventListener('click', function (e) {
                if (e.target === modal) {
                    modal.style.display = 'none';
                }
            });
        });
    </script>
</section>
</body>
</html>