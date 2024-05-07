document.addEventListener('DOMContentLoaded', function() {
    fetchProfile();
    document.getElementById('deleteProfileBtn').addEventListener('click', function() {
        if (confirm('Are you sure you want to delete your profile? This action cannot be undone.')) {
            deleteProfile();
        }
    });
});

function fetchProfile() {
    fetch('/api/v1/customers/profile', {
        method: 'GET',
        credentials: 'include' // Ensures cookies are sent with the request
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            document.getElementById('firstName').textContent = data.firstName || 'N/A';
            document.getElementById('lastName').textContent = data.lastName || 'N/A';
            document.getElementById('phoneNumber').textContent = data.phoneNumber || 'N/A';
            document.getElementById('email').textContent = data.email || 'N/A';
        })
        .catch(error => {
            console.error('Error fetching profile:', error);
            alert('Error fetching profile. Please try again later.');
        });
}



function deleteProfile() {
    fetch('/api/v1/customers/delete', {
        method: 'DELETE',
        credentials: 'include',
        headers: {
            'Content-Type': 'application/json',
        }
    })
        .then(response => {
            if (response.ok) {
                clearCookies();
                alert('Profile deleted successfully.');
                window.location.href = "/index.html";
            } else {
                throw new Error('Failed to delete profile');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Failed to delete profile. Please delete existing bookings and try again.');
        });
}

document.getElementById('settingsBtn').addEventListener('click', function() {
    const newPhoneNumber = prompt("Please enter your new mobile number:");
    if (newPhoneNumber) {
        if (newPhoneNumber.length <= 15) { // Assuming a maximum of 15 digits
            updateProfile({ phoneNumber: newPhoneNumber });
        } else {
            alert("Please enter a valid phone number.");
        }
    }
});


function updateProfile(updatedData) {
    fetch('/api/v1/customers/update', {
        method: 'PATCH',
        credentials: 'include',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(updatedData)
    })
        .then(response => {
            if (response.ok) {
                alert('Mobile number updated successfully.');
                fetchProfile(); // Re-fetch profile to update the displayed number
            } else {
                throw new Error('Failed to update profile');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Failed to update profile. Please try again.');
        });
}

function clearCookies() {
    var cookies = document.cookie.split(";");

    for (var i = 0; i < cookies.length; i++) {
        var cookie = cookies[i];
        var eqPos = cookie.indexOf("="); // Find the position of the "=" symbol
        var name = eqPos > -1 ? cookie.slice(0, eqPos) : cookie.trim(); // Use slice to get the cookie name
        document.cookie = name + "=;expires=Thu, 01 Jan 1970 00:00:00 GMT; path=/; secure";
        // Added `secure` flag if using HTTPS. Remove if testing locally over HTTP.
    }
}