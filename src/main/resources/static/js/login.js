const warning_div = document.getElementById('password_warning');

function showPassword() {
    let password = document.getElementById('password');

    let type = password.type === "password" ? "text" : "password";
    password.type = type;
}

function checkDetails() {
    let contact = document.getElementById('contact').value;
    let password = document.getElementById('password').value;
    if (!contact) {
        warning_div.style.color = "#ff0000";
        warning_div.innerHTML = "Please enter email or phone no";
    }
    else if (!password) {
        warning_div.style.color = "#ff0000";
        warning_div.innerHTML = "Please enter your password";
    } else {
        warning_div.style.color = "#008900";
        warning_div.innerHTML = "  Everything is OK";
        console.log("Redirecting...");
        
        window.open("/index.html", "_blank");
        
    }
}
// document.getElementById("signin_button").addEventListener("submit", function(event) {
//     event.preventDefault(); // Stop form submission
//     checkDetails(); // Run the validation
// });