function clearSearch() {
    document.querySelector('input[name="keyword"]').value = '';
}

document.addEventListener("DOMContentLoaded", function () {

    const popup = document.getElementById("credentialsPopup");
    const closeButton = document.getElementById("closeCredentialsPopup");

    if (popup && closeButton) {
        closeButton.addEventListener("click", function () {
            popup.style.display = "none";
        });
    }

    console.log("adminUserPage.js IS RUNNING");
});
