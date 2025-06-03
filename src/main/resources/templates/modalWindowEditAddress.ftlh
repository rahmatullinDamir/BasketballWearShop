<div id="editAddressModal" class="modal">
    <div class="modal-content">
        <span onclick="closeEditAddressModal()" class="close-btn">&times;</span>
        <h2>Изменить адрес</h2>
        <form id="editAddressForm">
            <input type="hidden" id="editAddressId"/>

            <label for="editCity">Город:</label>
            <input type="text" id="editCity" name="city" placeholder="Введите город" required/>

            <label for="editStreet">Улица:</label>
            <input type="text" id="editStreet" name="street" placeholder="Введите улицу" required/>

            <label for="editPostalCode">Почтовый индекс:</label>
            <input type="text" id="editPostalCode" name="postalCode" placeholder="Введите индекс" required/>

            <label for="editCountry">Страна:</label>
            <input type="text" id="editCountry" name="country" placeholder="Введите страну" required/>
            <input type="hidden" id="csrf" value="${_csrf.token}"/>

            <button type="submit">Сохранить изменения</button>
        </form>
    </div>
</div>