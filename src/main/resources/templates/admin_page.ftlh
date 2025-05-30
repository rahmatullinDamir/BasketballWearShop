<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Admin Page</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<#include "header.ftlh">
<#if message??>
    <p> ${message}</p>
</#if>
<div class="addProductContainer">

    <h1>Добавить товар</h1>

    <form method="post" action="/products/save" enctype="multipart/form-data" id = "productForm">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
        <label>Название:</label><br/>
        <input type="text" name="name" required/><br/><br/>

        <label>Описание:</label><br/>
        <textarea name="description"></textarea><br/><br/>

        <label>Цена:</label><br/>
        <input type="number" step="0.01" name="price" required/><br/><br/>


        <!-- Скрытый инпут для передачи размеров -->
        <input type="hidden" name="sizesInput" id="sizesInput"/>

        <!-- Чекбоксы для выбора размеров -->
        <label>Размеры:</label><br/>
        <div class="checkbox-group">
            <label><input type="checkbox" value="XS" class="size-checkbox"> XS</label>
            <label><input type="checkbox" value="S" class="size-checkbox"> S</label>
            <label><input type="checkbox" value="M" class="size-checkbox"> M</label>
            <label><input type="checkbox" value="L" class="size-checkbox"> L</label>
            <label><input type="checkbox" value="XL" class="size-checkbox"> XL</label>
        </div><br/>

        <label>Изображения:</label><br/>
        <input type="file" name="images" multiple accept="image/*"/><br/><br/>

        <button type="submit">Сохранить</button>
    </form>
</div>
<div class="addBadgeContainer">
    <h1>Создать значок</h1>
    <form method="post" action="/badges/save" enctype="multipart/form-data">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />

        <label>Название:</label><br/>
        <input type="text" name="name" required/><br/><br/>

        <label>Описание:</label><br/>
        <textarea name="description"></textarea><br/><br/>

        <label>Требуемые баллы:</label><br/>
        <input type="number" name="requiredPoints" min="0" required/><br/><br/>

        <label>Иконка:</label><br/>
        <input type="file" name="icon" id="iconInput" accept="image/*"/>

        <div class="preview-container">
            <img id="iconPreview" src="" alt="Предпросмотр иконки" style="display: none; max-width: 150px; max-height: 150px;"/>
        </div>

        <button type="submit">Сохранить значок</button>
    </form>
</div>

<#--<h1>Products: </h1>-->
<#--<section class="products">-->
<#--    <#list products as product>-->
<#--        <div class="product">-->
<#--            <div class="product-tag">NEW<br>-20%</div>-->
<#--            <img src="http://localhost:8080/files/${product.images[0].storageName}" alt="img" class="product-image">-->
<#--            <h3>${product.name}</h3>-->
<#--            <div class="price">1890 ₽. <span>${product.price}</span></div>-->
<#--            <button class="add-to-cart">В корзину</button>-->
<#--        </div>-->
<#--    </#list>-->
<#--</section>-->
</body>
<#include "footer.ftlh">
<script>
    document.getElementById('productForm').addEventListener('submit', function (e) {
        e.preventDefault(); // Останавливаем стандартную отправку формы
        const checkboxes = document.querySelectorAll('.size-checkbox:checked');
        const selectedSizes = Array.from(checkboxes).map(cb => cb.value);
        if (selectedSizes.length === 0) {
            alert("Пожалуйста, выберите хотя бы один размер.");
            return;
        }

        document.getElementById('sizesInput').value = selectedSizes.join(',');

        this.submit();
    });
</script>

<script>
    document.getElementById('iconInput').addEventListener('change', function () {
        const file = this.files[0];
        const preview = document.getElementById('iconPreview');

        if (file) {
            const reader = new FileReader();

            reader.onload = function (e) {
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
</html>