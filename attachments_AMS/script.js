// FORM TOGGLE
const logInBtn = document.getElementById("logInBtn");
const logInForm = document.getElementById("logInForm");


if(logInBtn && logInForm)
{
    logInBtn.onclick = () => 
    {
        logInForm.style.display = "block";
        logInBtn.classList.add("active");
    };
}


document.addEventListener('DOMContentLoaded', function() 
{
    
    
    // TOGGLE PASSWORD
    const PasswordInput = document.getElementById("loginPassword");
    const icon = document.getElementById("toggleIcon");

        if(PasswordInput && icon)
        {
            icon.addEventListener('click', function() 
            {
        
            if(PasswordInput.type == 'password')
            {
                PasswordInput.type = 'text';    // show password
                icon.classList.replace('fa-eye', 'fa-eye-slash');
            }
            else
            {
                PasswordInput.type = "password";     // hide password
                icon.classList.replace("fa-eye-slash","fa-eye");
            }
        
    

    });

}

    //login handling
    const logInForm = document.getElementById('logInForm');
    const logInSubmitBtn = document.getElementById('logInBtn');

    if (logInForm && logInSubmitBtn) 
    {
        logInForm.addEventListener('submit', async function(e) 
        {
            e.preventDefault();

            const email = document.getElementById('loginEmail').value.trim();
            const password = document.getElementById('loginPassword').value.trim();

            if (!email || !password) {
                showAlert('Please fill in all fields.');
                return;
            }


            try {
                // Encoding credentials and sending HEADER
                
                const credentials = btoa(`${email}:${password}`);
                
                // CHANGE to our actual Spring Boot server URL
                const ApiUrl = 'http://localhost:8080';
                
                const response = await fetch(`${ApiUrl}/api/auth/current-user`, 
                    {
                    method: 'POST',
                    headers: 
                    {
                        'Authorization': `Basic ${credentials}`
                    }
                });


                if (!response.ok) {
                    throw new Error('Invalid email or password');
                }

                const user = await response.json();
                console.log('Login success:', user);

                //authentication data
                localStorage.setItem('authToken', credentials);
                localStorage.setItem('user', JSON.stringify(user));

        
                showAlert(`Welcome back, ${user.name}!`, 'success');

               
                //REDIRECTION BASED  ON ROLE            
                setTimeout(() => {
                    switch(user.role) {
                        case 'ADMIN':
                            window.location.href = 'adminDashboard.html';
                            break;
                        case 'MANAGER':
                            window.location.href = 'managerDashboard.html';
                            break;
                        case 'BORROWER':
                            window.location.href = 'userDashboard.html';
                            break;
                        default:
                            window.location.href = 'index(login).html';
                    }
                }, 1000);

            } catch(error) 
            {
                console.error('Login error:', error);
                showAlert('Invalid email or password. Please try again.', 'error');
            } finally 
            {
                logInSubmitBtn.disabled = false;
                logInSubmitBtn.innerHTML = originalBtnHTML;
            }
        });
    }
// ALERT
function showAlert(msg, type = "error")
{
    const alertEl = document.getElementById("customAlert");
    if (!alertEl) return;

    alertEl.textContent = msg;
    alertEl.className = "alert " + type;
    alertEl.style.display = "block";

    if(window.alertTimeout) clearTimeout(window.alertTimeout); // Clear previous timeout

    window.alertTimeout = setTimeout(()=>{ alertEl.style.display = "none"; }, 4000);
}




//logout function
function logout() 
{
    // Clearing authentication token from localStorage
    localStorage.removeItem("authToken");

    const logoutForm = document.getElementById("hiddenLogoutForm");
    if (logoutForm) {
        logoutForm.submit();
    }

    // Redirecting to login page after logout
    window.location.href = "index(login).html";
}

// CONTACT POPUP
const popup = document.getElementById("contactPopup");
const contactLink = document.getElementById("contactLink");

if(contactLink && popup)
{
    contactLink.onclick = function() { popup.style.display="block"; };
}
window.closePopup = function() 
{
    if(popup)
    {
        popup.style.display="none";
    }
};



});