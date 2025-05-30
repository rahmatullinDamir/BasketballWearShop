<div class="modal" id="addressModal">
    <div class="modal-content">
        <h3>Пожалуйста введите ваш адрес</h3>
        <form id="addressForm">
            <label for="city">Город:</label>
            <input type="text" id="city" name="city" required>

            <label for="street">Улица:</label>
            <input type="text" id="street" name="street" required>

            <label for="postalCode">Почтовый индекс:</label>
            <input type="text" id="postalCode" name="postalCode" required
                   placeholder="Почтовый индекс должен состоять из 5 или 6 цифр.">

            <label for="country">Страна:</label>
            <input type="text" id="country" name="country" required>

            <input type="hidden" id="csrf" value="${_csrf.token}"/>

            <button type="submit">Сохранить</button>
        </form>
    </div>
</div>