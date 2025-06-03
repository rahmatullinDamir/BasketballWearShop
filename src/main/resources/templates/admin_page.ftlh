<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Админ панель | Baller Merch</title>
    <link rel="stylesheet" href="/css/style.css">
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;500;600;700&display=swap" rel="stylesheet">
</head>
<body>
<#include "header.ftlh">

<div class="admin-container">
    <div class="admin-header">
        <h1>Панель администратора</h1>
        <#if message??>
            <div class="admin-message ${message?contains('успешно')?then('success','error')}">
                ${message}
            </div>
        </#if>
    </div>

    <div class="admin-content">
        <!-- Секция добавления товара -->
        <div class="admin-section product-section">
            <div class="section-header">
                <h2>Добавить товар</h2>
                <span class="section-icon">🏀</span>
            </div>

            <form method="post" action="/products/save" enctype="multipart/form-data" id="productForm" class="admin-form">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                
                <div class="form-group">
                    <label for="productName">Название:</label>
                    <input type="text" id="productName" name="name" required/>
                </div>

                <div class="form-group">
                    <label for="productDescription">Описание:</label>
                    <textarea id="productDescription" name="description" rows="4"></textarea>
                </div>

                <div class="form-group">
                    <label for="productPrice">Цена:</label>
                    <input type="number" id="productPrice" step="0.01" name="price" required/>
                </div>

                <input type="hidden" name="sizesInput" id="sizesInput"/>

                <div class="form-group">
                    <label>Размеры:</label>
                    <div class="size-checkboxes">
                        <label class="size-checkbox">
                            <input type="checkbox" value="XS" class="size-checkbox-input">
                            <span class="size-checkbox-label">XS</span>
                        </label>
                        <label class="size-checkbox">
                            <input type="checkbox" value="S" class="size-checkbox-input">
                            <span class="size-checkbox-label">S</span>
                        </label>
                        <label class="size-checkbox">
                            <input type="checkbox" value="M" class="size-checkbox-input">
                            <span class="size-checkbox-label">M</span>
                        </label>
                        <label class="size-checkbox">
                            <input type="checkbox" value="L" class="size-checkbox-input">
                            <span class="size-checkbox-label">L</span>
                        </label>
                        <label class="size-checkbox">
                            <input type="checkbox" value="XL" class="size-checkbox-input">
                            <span class="size-checkbox-label">XL</span>
                        </label>
                    </div>
                </div>

                <div class="form-group">
                    <label for="productImages">Изображения:</label>
                    <div class="file-input-wrapper">
                        <input type="file" id="productImages" name="images" multiple accept="image/*" class="file-input"/>
                        <label for="productImages" class="file-input-label">
                            <span class="file-input-text">Выберите файлы</span>
                            <span class="file-input-button">Обзор</span>
                        </label>
                    </div>
                    <div id="imagePreviewContainer" class="image-preview-container"></div>
                </div>

                <button type="submit" id="productSubmitBtn" class="btn btn-primary">Добавить продукт</button>
            </form>
        </div>

        <!-- Секция создания значка -->
        <div class="admin-section badge-section">
            <div class="section-header">
                <h2>Создать значок</h2>
                <span class="section-icon">🏆</span>
            </div>

            <form method="post" action="/badges/save" enctype="multipart/form-data" id="badgeForm" class="admin-form">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />

                <div class="form-group">
                    <label for="badgeName">Название:</label>
                    <input type="text" id="badgeName" name="name" required/>
                </div>

                <div class="form-group">
                    <label for="badgeDescription">Описание:</label>
                    <textarea id="badgeDescription" name="description" rows="4"></textarea>
                </div>

                <div class="form-group">
                    <label for="badgePoints">Требуемые баллы:</label>
                    <input type="number" id="badgePoints" name="requiredPoints" min="0" required/>
                </div>

                <div class="form-group">
                    <label for="iconInput">Иконка:</label>
                    <div class="file-input-wrapper">
                        <input type="file" id="iconInput" name="icon" accept="image/*" class="file-input"/>
                        <label for="iconInput" class="file-input-label">
                            <span class="file-input-text">Выберите файл</span>
                            <span class="file-input-button">Обзор</span>
                        </label>
                    </div>
                    <div class="preview-container">
                        <img id="iconPreview" src="" alt="Предпросмотр иконки" style="display: none;"/>
                    </div>
                </div>

                <button type="submit" id="badgeSubmitBtn" class="btn btn-primary">Сохранить значок</button>

            </form>
            <a href="/products/size-statistics">Получить статистику по популярности размеров.</a>
        </div>
    </div>
</div>

<#include "footer.ftlh">

<script>
// Обработка формы товара
document.getElementById('productForm').addEventListener('submit', function(e) {
    e.preventDefault();
    const checkboxes = document.querySelectorAll('.size-checkbox-input:checked');
    const selectedSizes = Array.from(checkboxes).map(cb => cb.value);
    
    if (selectedSizes.length === 0) {
        alert("Пожалуйста, выберите хотя бы один размер.");
        return;
    }

    document.getElementById('sizesInput').value = selectedSizes.join(',');
    this.submit();
});

// Предпросмотр изображений товара
document.getElementById('productImages').addEventListener('change', function(e) {
    const container = document.getElementById('imagePreviewContainer');
    container.innerHTML = '';
    
    Array.from(this.files).forEach(file => {
        const reader = new FileReader();
        reader.onload = function(e) {
            const img = document.createElement('img');
            img.src = e.target.result;
            img.classList.add('image-preview');
            container.appendChild(img);
        }
        reader.readAsDataURL(file);
    });
});

// Предпросмотр иконки значка
document.getElementById('iconInput').addEventListener('change', function() {
    const file = this.files[0];
    const preview = document.getElementById('iconPreview');

    if (file) {
        const reader = new FileReader();
        reader.onload = function(e) {
            preview.src = e.target.result;
            preview.style.display = 'block';
        };
        reader.readAsDataURL(file);
    } else {
        preview.src = '';
        preview.style.display = 'none';
    }
});
</script>

<script src="/js/form-protection.js"></script>
</body>
</html>