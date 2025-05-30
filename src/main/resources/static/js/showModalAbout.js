function showWindowAbout() {
    const modal = document.getElementById('aboutModal');
    modal.classList.add('show');
}
document.addEventListener("DOMContentLoaded", function () {
    const modal = document.getElementById('aboutModal');

    document.getElementById("aboutBrand").addEventListener("click", showWindowAbout);
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

});