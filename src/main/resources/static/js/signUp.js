const submitButton = document.getElementById('submit_button');
const tc_button = document.getElementById('tc');
const warning_div = document.getElementById('password_warning');

function uploadPhoto(event) {
    let img = document.getElementById("proimg");
    let file = event.target.files[0];
    let warning_div = document.getElementById('warning');

    if (file) {
        let sizeInKB = (file.size / 1024).toFixed(2);
        let maxSize = 50; // Max size in KB

        if (sizeInKB > maxSize) {
            warning_div.style.color = "#ff0000";
            warning_div.innerHTML = "  File size must be less than 50KB";
        } else {
            let reader = new FileReader();
            reader.onload = function (e) {
                img.src = e.target.result;
            };
            reader.readAsDataURL(file);
            warning_div.style.color = "#008900";
            warning_div.innerHTML = "  File size is OK";
        }
    }
}

function showPassword() {
    let password = document.getElementById('password');
    let c_password = document.getElementById('c_password');

    let type = password.type === "password" ? "text" : "password";
    password.type = type;
    c_password.type = type;
}

function isValidPassword(str) {
    return /^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)(?=.*[\W_])[A-Za-z\d\W_]+$/.test(str);
}


function password_warning() {
    let password = document.getElementById('password').value;

    if (password.length < 8) {
        warning_div.style.color = "#ff0000";
        warning_div.innerHTML = "  Password must have at least 8 characters";
    } else if (!isValidPassword(password)) {
        warning_div.style.color = "#ff0000";
        warning_div.innerHTML = "  Password must be alphanumeric (no spaces) with Special characters";
    } else {
        warning_div.style.color = "#008900";
        warning_div.innerHTML = "  Password is valid";
    }
    updateSubmitButton();
}

function compare_pw() {
    let password = document.getElementById('password').value;
    let c_password = document.getElementById('c_password').value;

    if (!c_password || password !== c_password) {
        warning_div.style.color = "#ff0000";
        warning_div.innerHTML = "  Passwords do not match";
    } else {
        warning_div.style.color = "#008900";
        warning_div.innerHTML = "  Passwords matched";
    }
    updateSubmitButton();
}

function tcAgreed() {
    return tc_button.checked;
}

function updateSubmitButton() {
    let password = document.getElementById('password').value;
    let c_password = document.getElementById('c_password').value;

    if (password.length >= 8 && isValidPassword(password) && password === c_password && tcAgreed()) {
        submitButton.disabled = false;
        warning_div.style.color = "#008900";
        warning_div.innerHTML = "  Everything is OK";
        window.open("/nextmartfrontend/account.html", "_blank");

    } else {
        warning_div.style.color = "#ff0000";
        if(password.length < 8){
            warning_div.innerHTML = "  Password must have at least 8 characters";
        }
        else if(!isValidPassword(password) ){
            warning_div.innerHTML = "  Password must be alphanumeric (no spaces) with Special Characters";

        }
        else if(!c_password || password !== c_password){
            warning_div.innerHTML = "  Passwords do not match";

        }
        else if(!tcAgreed()){
            warning_div.innerHTML = "  Terms & Conditions not Agreed";

        }
        submitButton.disabled = true;
    }
}

// Add event listeners
document.getElementById('password').addEventListener("input", () => {
    password_warning();
    compare_pw();
});

document.getElementById('c_password').addEventListener("input", compare_pw);
document.getElementById('tc').addEventListener("change", updateSubmitButton);
