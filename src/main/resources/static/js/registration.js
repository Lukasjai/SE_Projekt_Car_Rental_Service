document.getElementById("registrationForm").addEventListener("submit", function (event) {
    event.preventDefault(); // Prevent the form from submitting the traditional way
    const formData = {
        firstName: document.getElementById('firstName').value,
        lastName: document.getElementById('lastName').value,
        email: document.getElementById('email').value,
        password: document.getElementById('password').value,
        phoneNumber: document.getElementById('phoneNumber').value,
        licenceNumber: document.getElementById('licenceNumber').value
    };
    fetch('/customers/register', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(formData)
    })
        .then(response => {
            if (response.ok) {
                //alert("Registration successful!");
                window.location.href = "/login.html";
            } else {
                alert("Registration failed. Please try again.");
            }
        })
        .catch(error => {
            console.error("Error:", error);
            alert("An error occurred. Please try again later.");
        });
});