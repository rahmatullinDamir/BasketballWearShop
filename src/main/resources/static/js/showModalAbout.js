function showWindowAbout() {
    const modal = document.getElementById('aboutModal');
    modal.classList.add('show');
}
document.addEventListener("DOMContentLoaded", function () {
    const modal = document.getElementById('aboutModal');

    document.getElementById("aboutBrand").addEventListener("click", showWindowAbout);
    function hideModal() {
        modal.classList.remove('show');
    }


    window.addEventListener('click', function (event) {
        if (event.target === modal) {
            hideModal();
        }
    });


});