<!-- Модальное окно -->
<div id="productModal" class="modalProduct">
    <div class="modal-content-product">
        <div class="modal-header">
            <h2 class="modal-title"></h2>
            <span class="close">&times;</span>
        </div>
        <div class="modal-body">
            <div class="product-details">
                <div class="image-slider">
                    <!-- Сюда будут добавляться изображения -->
                </div>
                <div class="product-info">
                    <p class="modal-description"></p>
                    <p class="modal-price"></p>
                    <div class="size-selection">
                        <label for="size-select">Размер:</label>
                        <select id="size-select" class="modal-size-selector">
                            <option value="">Выберите размер</option>
                        </select>
                    </div>
                    <div class="quantity-selection">
                        <label for="quantity">Количество:</label>
                        <div class="quantity-controls">
                            <button class="quantity-btn minus">-</button>
                            <input type="number" id="quantity" value="1" min="1" max="10">
                            <button class="quantity-btn plus">+</button>
                        </div>
                    </div>
                    <button class="add-to-cart-modal" id="add-to-cart-modal">
                        <span class="button-text">Добавить в корзину</span>
                        <span class="button-icon">🛒</span>
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
document.addEventListener('DOMContentLoaded', function() {
    // Обработчики для кнопок количества
    const quantityInput = document.getElementById('quantity');
    const minusBtn = document.querySelector('.quantity-btn.minus');
    const plusBtn = document.querySelector('.quantity-btn.plus');

    minusBtn.addEventListener('click', function() {
        let currentValue = parseInt(quantityInput.value);
        if (currentValue > 1) {
            quantityInput.value = currentValue - 1;
        }
    });

    plusBtn.addEventListener('click', function() {
        let currentValue = parseInt(quantityInput.value);
        if (currentValue < 10) {
            quantityInput.value = currentValue + 1;
        }
    });

    // Валидация ввода количества
    quantityInput.addEventListener('change', function() {
        let value = parseInt(this.value);
        if (isNaN(value) || value < 1) {
            this.value = 1;
        } else if (value > 10) {
            this.value = 10;
        }
    });

    // Анимация закрытия
    const modal = document.getElementById('productModal');
    const closeBtn = document.querySelector('.close');

    closeBtn.addEventListener('click', function() {
        modal.style.animation = 'modalFadeOut 0.3s ease-in-out';
        setTimeout(() => {
            modal.style.display = 'none';
            modal.style.animation = '';
        }, 300);
    });

    // Обработчик клика вне модального окна
    window.addEventListener('click', function(e) {
        if (e.target === modal) {
            modal.style.animation = 'modalFadeOut 0.3s ease-in-out';
            setTimeout(() => {
                modal.style.display = 'none';
                modal.style.animation = '';
            }, 300);
        }
    });
});
</script>