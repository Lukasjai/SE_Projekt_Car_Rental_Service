function submitRentForm() {
    let pickupDate = document.getElementById('pickupDate').value;
    let returnDate = document.getElementById('returnDate').value;
    const today = new Date().toISOString().split('T')[0];

    if (!pickupDate || !returnDate) {
        alert('Please select both pickup and return dates.');
        return;
    }

    if (new Date(pickupDate) > new Date(returnDate)) {
        alert('Return date must be after the pickup date.');
        return;
    }

    if (new Date(pickupDate) < new Date(today)) {
        alert('Pickup date cannot be a past date');
        return;
    }

    fetch('/cars/availability', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            pickupDate: pickupDate,
            returnDate: returnDate
        })
    })
        .then(response => {
            if (response.ok) {
                return response.json();
            }
            throw new Error('Request failed!');
        })
        .then(data => {
            console.log(data)
            displayCars(data);
        })
        .catch(error => {
            console.error('Error:', error);
        });
}

function displayCars(cars) {
    const container = document.getElementById('carsContainer');
    cars.forEach(car => {
        const carTable = document.createElement('table');
        carTable.id = 'carTable';
        const carRow = carTable.insertRow();

        const brandCell = carRow.insertCell();
        const modelCell = carRow.insertCell();
        const seatsCell = carRow.insertCell();
        const priceCell = carRow.insertCell();

        brandCell.innerHTML = `Brand: ${car.car_brand_name}`;
        modelCell.innerHTML = `Model: ${car.car_model_name}`;
        seatsCell.innerHTML = `Seats: ${car.number_of_seats}`;
        priceCell.innerHTML = `Price: ${car.prices} $`;

        const bookButtonCell = carRow.insertCell();
        const bookButton = document.createElement('button');
        bookButton.id = 'bookButton';
        bookButton.textContent = 'Book';
        bookButton.addEventListener('click', () => {
            const confirmBooking = window.confirm(`Do you want to book ${car.car_brand_name} ${car.car_model_name}?`);
            if (confirmBooking) {
                bookCar(car)
            }
        });

        bookButtonCell.appendChild(bookButton);
        container.appendChild(carTable);
    });
}

function bookCar(car) {
    let pickupDate = document.getElementById('pickupDate').value;
    let returnDate = document.getElementById('returnDate').value;
    const today = new Date().toISOString().split('T')[0];

    fetch('/api/v1/bookings', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            carId: car.id,
            pickupDate: pickupDate,
            returnDate: returnDate,
            orderDate: today,
            price: car.prices
        })
    })
        .then(response => {
            if (response.status === 201) {
                return alert(`You have booked ${car.car_brand_name} ${car.car_model_name}`)
            } else {
                return alert(`Booking failed for ${car.car_brand_name} ${car.car_model_name}`);
            }
        })
        .catch(error => {
            console.log(error)
            alert("An error occurred. Please try again later.");
        })
}