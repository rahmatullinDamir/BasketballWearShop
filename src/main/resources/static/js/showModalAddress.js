function showWindow() {
    const modal = document.getElementById('addressModal');
    modal.classList.add('show');
}
document.addEventListener("DOMContentLoaded", function () {
    const modal = document.getElementById('addressModal');
    const form = document.getElementById('addressForm');

    // Функция для показа модального окна


    // Функция для скрытия модального окна
    function hideModal() {
        modal.classList.remove('show');
    }

    // Закрытие при клике вне окна
    window.addEventListener('click', function (event) {
        if (event.target === modal) {
            hideModal();
        }
    });

    // Обработка отправки формы
    form.addEventListener('submit', function (e) {
        e.preventDefault();

        const city = document.querySelector('#city').value.trim();
        const street = document.querySelector('#street').value.trim();
        const postalCode = document.querySelector('#postalCode').value.trim();
        const country = document.querySelector('#country').value.trim();
        const csrf = document.querySelector('#csrf').value;

        // Проверка заполненности
        if (!city || !street || !postalCode || !country) {
            alert('Все поля обязательны для заполнения!');
            return;
        }

        // Проверка длины
        if (city.length < 2 || street.length < 2 || country.length < 2) {
            alert('Город, улица и страна должны содержать не менее 2 символов.');
            return;
        }

        // Проверка почтового индекса (только цифры, 5–6 знаков)
        const postalCodeRegex = /^\d{5,6}$/;
        if (!postalCodeRegex.test(postalCode)) {
            alert('Почтовый индекс должен состоять из 5 или 6 цифр.');
            return;
        }

        // Если всё ок — отправляем
        const addressData = {city, street, postalCode, country};

        fetch('/address', {
            method: 'POST',
            headers: {
                "X-CSRF-TOKEN": csrf,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(addressData)
        })
            .then(response => {
                if (response.ok) {
                    alert('Адрес успешно сохранён!');
                    modal.style.display = 'none';
                } else {
                    return response.text().then(text => {
                        throw new Error(text)
                    });
                }
            })
            .catch(error => {
                console.error('Ошибка:', error);
                alert('Не удалось сохранить адрес.');
            });
    });
});