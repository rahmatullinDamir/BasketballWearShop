function protectForm(formId, submitButtonId, loadingText = 'Отправка...', originalText = null) {
    const form = document.getElementById(formId);
    if (!form) return;

    const submitButton = document.getElementById(submitButtonId);
    if (!submitButton) return;

    const buttonText = originalText || submitButton.innerHTML;

    form.addEventListener('submit', function(e) {
        if (form.dataset.submitting === 'true') {
            e.preventDefault();
            return;
        }

        form.dataset.submitting = 'true';

        submitButton.disabled = true;
        submitButton.innerHTML = loadingText;

        setTimeout(function() {
            form.dataset.submitting = 'false';
            submitButton.disabled = false;
            submitButton.innerHTML = buttonText;
        }, 10000);
    });
}

function initializeFormProtection() {
    protectForm('productForm', 'productSubmitBtn', 'Добавление продукта...', 'Добавить продукт');

    protectForm('badgeForm', 'badgeSubmitBtn', 'Сохранение значка...', 'Сохранить значок');

    protectForm('signUpForm', 'signUpSubmitBtn', 'Регистрация...', 'Зарегистрироваться');

    protectForm('signInForm', 'signInSubmitBtn', 'Вход...', 'Войти');

    protectForm('orderForm', 'orderSubmitBtn', 'Оформление заказа...', 'Оформить заказ');
}

document.addEventListener('DOMContentLoaded', initializeFormProtection); 