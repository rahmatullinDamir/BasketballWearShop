function deleteAddress(addressId) {
    const csrf = document.getElementById("csrf").value;
    console.log(csrf);
    if (!confirm("Вы уверены, что хотите удалить адрес?")) {
        return;
    }

    fetch("/address/" + addressId, {
        method: "DELETE",
        headers: {
            "X-CSRF-TOKEN": csrf
        }
    })
        .then(response => {
            if (response.ok) {
                alert("Адрес успешно удален");
                location.reload();
            } else {
                alert("Ошибка при удалении адреса");
            }
        })
        .catch(error => {
            console.error("Ошибка:", error);
            alert("Произошла ошибка при удалении адреса");
        });
}


function changeAddress(addressId) {
    fetch("/address/" + addressId)
        .then(response => response.json())
        .then(data => {
            document.getElementById('editAddressId').value = data.id;
            document.getElementById('editCity').value = data.city;
            document.getElementById('editStreet').value = data.street;
            document.getElementById('editPostalCode').value = data.postalCode;
            document.getElementById('editCountry').value = data.country;

            document.getElementById('editAddressModal').classList.add('show');
        })
        .catch(error => {
            console.error("Ошибка загрузки данных:", error);
            alert("Не удалось загрузить данные адреса");
        });
}

function closeEditAddressModal() {
    document.getElementById('editAddressModal').classList.remove('show');
}

document.getElementById('editAddressForm').addEventListener('submit', function (event) {
    event.preventDefault();

    const addressId = document.getElementById('editAddressId').value;
    const updatedData = {
        city: document.getElementById('editCity').value,
        street: document.getElementById('editStreet').value,
        postalCode: document.getElementById('editPostalCode').value,
        country: document.getElementById('editCountry').value
    };

    const csrf = document.getElementById("csrf").value;

    fetch("/address/" + addressId, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
            "X-CSRF-TOKEN": csrf
        },
        body: JSON.stringify(updatedData)
    })
        .then(response => {
            if (response.ok) {
                alert("Адрес успешно изменен");
                location.reload();
            } else {
                alert("Ошибка при сохранении изменений");
            }
        })
        .catch(error => {
            console.error("Ошибка:", error);
            alert("Произошла ошибка при изменении адреса");
        });
});

window.addEventListener('click', function (event) {
    const modal = document.getElementById('editAddressModal');
    if (event.target === modal) {
        modal.classList.remove('show');
    }
});