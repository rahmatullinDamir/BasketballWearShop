function updateCartCount() {
    fetch('/cart/count')
        .then(response => response.json())
        .then(data => {
            const cartIcon = document.querySelector('.cart-icon');
            if (cartIcon) {
                cartIcon.setAttribute('data-count', data.count);
            }
        })
        .catch(error => console.error('Error updating cart count:', error));
}

function addToCart(productId, size, quantity = 1) {
    const formData = new URLSearchParams();
    formData.append('productId', productId);
    formData.append('size', size);
    formData.append('quantity', quantity);

    fetch('/cart/add', {
        method: 'POST',
        body: formData,
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        }
    })
    .then(response => response.text())
    .then(message => {
        alert(message);
        updateCartCount();
    })
    .catch(error => console.error('Error adding to cart:', error));
}

document.addEventListener('DOMContentLoaded', updateCartCount);

window.addToCart = addToCart;
window.updateCartCount = updateCartCount; 