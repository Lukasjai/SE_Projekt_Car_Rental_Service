document.getElementById("loginForm").addEventListener("submit", function(event) {
    event.preventDefault();
    const formData = {
        email: document.getElementById('email').value,
        password: document.getElementById('password').value
    }
    fetch('/customers/login', {
        method: "POST",
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(formData)
    })
        .then(response => {
            if (response.ok) {
                return response.json()
            } else {
                alert("Login failed. Please check your email and password.");
            }
        })
        .then(responseData => {
            window.location.href = "/index.html";
        })
        .catch(error => {
            console.error("Error:", error);
            alert("An error occurred. Please try again later.");
        });
});