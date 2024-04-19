document.getElementById("registrationForm").addEventListener("submit", function(event) {
    event.preventDefault(); // Prevent the form from submitting normally
    var form = event.target;
    var formData = new FormData(form); // Create FormData object from form data
    // Send POST request to registration endpoint
    fetch(form.action, {
        method: "POST",
        body: formData
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