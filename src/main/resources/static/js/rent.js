function submitRentForm() {
    var pickupDate = document.getElementById('pickupDate').value;
    var returnDate = document.getElementById('returnDate').value;

    if (!pickupDate || !returnDate) {
        alert('Please select both pickup and return dates.');
        return;
    }

    if (new Date(pickupDate) > new Date(returnDate)) {
        alert('Return date must be after the pickup date.');
        return;
    }

    // Implement your form submission logic here, for example:
    console.log('Pickup Date:', pickupDate);
    console.log('Return Date:', returnDate);
    // You might want to send this data to a server or use it in your application
}