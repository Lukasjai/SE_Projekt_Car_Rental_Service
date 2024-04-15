document.getElementById("loginForm").addEventListener("submit", function(event) {
    event.preventDefault(); // Prevent the form from submitting normally
    var form = event.target;
    var formData = new FormData(form); // Create FormData object from form data
    // Send POST request to login endpoint
    fetch(form.action, {
        method: "POST",
        body: formData
    })
        .then(response => {
            if (response.ok) {
                alert("Login successful!");
                window.location.href = "/account-info.html";
            } else {
                alert("Login failed. Please check your email and password.");
            }
        })
        .catch(error => {
            console.error("Error:", error);
            alert("An error occurred. Please try again later.");
        });
});