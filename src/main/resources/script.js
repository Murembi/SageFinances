// FORM TOGGLE
const logInBtn = document.getElementById("logInBtn");
const logInForm = document.getElementById("logInForm");


logInBtn.onclick = () => 
{
    logInForm.style.display = "block";
    logInBtn.classList.add("active");
};


// TOGGLE PASSWORD
window.togglePassword = (loginPassword, toggleIcon) => 
{
    const input = document.getElementById(loginPassword);
    const icon = document.getElementById(toggleIcon);

    icon.onclick = () => 
    {
        if(input.type === "password")
        {
            input.type = "text";     // show password
            icon.classList.replace("fa-eye-slash","fa-eye");
        }
        else 
        {
            input.type = "password";  // hide password
            icon.classList.replace("fa-eye","fa-eye-slash");
        }
    }
};

// ALERT
function showAlert(msg, type="error")
{
    const alertEl = document.getElementById("customAlert");
    alertEl.textContent = msg;
    alertEl.className = "alert " + type;
    alertEl.style.display = "block";
    setTimeout(()=>{ alertEl.style.display="none"; },4000);
}


// login
const logInFormElement = document.querySelector("#logInForm form");

//Grabbing the submit button to disable it!
const logInSubmitBtn = logInFormElement.querySelector("button[type='submit']");

// Listen for the 'submit' event on the form, not just the button click
logInFormElement.addEventListener("submit", async (e) => 
    {

        // Prevent the default form submission behavior (which would reload the page)
        e.preventDefault();

        const email = document.getElementById("loginEmail").value.trim();
        const password = document.getElementById("loginPassword").value.trim();


        //Disable button and show loading state
        const originalBtnText = logInSubmitBtn.innerText;
        logInSubmitBtn.disabled = true;
        logInSubmitBtn.innerText = "Authenticating...";

        try    
        {
            //PACKING CREDENTIALS
            const JSONpayload = 
            {
                email: document.getElementById("loginEmail").value.trim(),
                password: document.getElementById("loginPassword").value.trim()
            };

            //sending the JSON payload to a server (uncomment and replace URL with your endpoint)
            const response = await fetch('https://your-server-endpoint.com/login',    
            {
                method: 'POST',
                headers: 
                {
                    'Content-Type': 'application/json'
                },

                body: JSON.stringify(JSONpayload)
            });

            const data = await response.json();
            console.log('Success:', data);

            // Handle successful login (e.g., redirect to dashboard)
            // Redirect to dashboard
            window.location.href = "dashboard1.html";
        }
        catch(error) 
        {
            console.error("Error during login:", error);
            showAlert("An unexpected error occurred. Please try again.");
        }
        finally
        {         
            // Re-enabling the login button and reset its text
            logInSubmitBtn.disabled = false;
            logInSubmitBtn.innerText = originalBtnText;
        }
    });

// CONTACT POPUP
const popup = document.getElementById("contactPopup");
document.getElementById("contactLink").onclick = ()=> popup.style.display="block";
window.closePopup = ()=> popup.style.display="none";