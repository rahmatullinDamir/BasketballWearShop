<div class="modal" id="addressModal">
    <div class="modal-content">
        <span class="close-btn" onclick="hideModal()">&times;</span>
        <h3>Пожалуйста введите ваш адрес</h3>
        <form id="addressForm">
            <div class="form-group">
                <label for="city">Город:</label>
                <input type="text" id="city" name="city" required 
                       placeholder="Введите город">
            </div>

            <div class="form-group">
                <label for="street">Улица:</label>
                <input type="text" id="street" name="street" required
                       placeholder="Введите улицу">
            </div>

            <div class="form-group">
                <label for="postalCode">Почтовый индекс:</label>
                <input type="text" id="postalCode" name="postalCode" required
                       placeholder="Почтовый индекс должен состоять из 5 или 6 цифр">
            </div>

            <div class="form-group">
                <label for="country">Страна:</label>
                <input type="text" id="country" name="country" required
                       placeholder="Введите страну">
            </div>

            <input type="hidden" id="csrf" value="${_csrf.token}"/>

            <button type="submit" class="submit-btn">Сохранить</button>
        </form>
    </div>
</div>