document.addEventListener('DOMContentLoaded', function() {
    fetchBookings();
});

function fetchBookings() {
    fetch('/api/v1/bookings', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            updateBookingTable(data);
        })
        .catch(error => {
            console.error('There has been a problem with your fetch operation:', error);
        });
}

function updateBookingTable(bookings) {
    const table = document.getElementById('CurBookingTable');
    table.innerHTML = '<tr>' +
        '<th>Brand</th>' +
        '<th>Model</th>' +
        '<th>Seat Number</th>' +
        '<th>Price</th>' +
        '<th>Order Date</th>' +
        '<th>Pickup Date</th>' +
        '<th>Return Date</th>' +
        '<th>Order ID</th>' +
        '</tr>';

    bookings.forEach(booking => {
        const row = table.insertRow(-1);
        const brandCell = row.insertCell(0);
        const modelCell = row.insertCell(1);
        const seatsCell = row.insertCell(2);
        const priceCell = row.insertCell(3);
        const pickupDateCell = row.insertCell(4);
        const returnDateCell = row.insertCell(5);
        const orderDateCell = row.insertCell(6);
        const orderIdCell = row.insertCell(7)

        brandCell.textContent = booking.carBrand;
        modelCell.textContent = booking.carModel;
        seatsCell.textContent = booking.carSeats;
        priceCell.textContent = `$${booking.carPrice.toFixed(2)}`;
        orderDateCell.textContent = formatDate(booking.orderDate);
        pickupDateCell.textContent = formatDate(booking.pickupDate);
        returnDateCell.textContent = formatDate(booking.returnDate);
        orderIdCell.textContent = booking.orderId;
    });
}

function formatDate(dateString) {
    const date = new Date(dateString);
    return date.toLocaleDateString('de-AT');
}
