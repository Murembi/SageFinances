function clearSearch() {
    document.querySelector('input[name="keyword"]').value = '';
}

document.addEventListener("DOMContentLoaded", function () {

    const popup = document.getElementById("credentialsPopup");
    const closeButton = document.getElementById("closeCredentialsPopup");

    if (closeButton && popup) {
        closeButton.addEventListener("click", function () {
            popup.style.display = "none";
        });
    }

    const toast = document.querySelector(".toast-message");

        if (!toast) return;

        setTimeout(() => {
            toast.style.transition = "opacity 0.5s ease";
            toast.style.opacity = "0";

            setTimeout(() => {
                toast.remove();
            }, 500);
        }, 5000);
});

console.log("adminUserPage.js IS RUNNING");

