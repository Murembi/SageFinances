console.log("MANAGER DASHBOARD JS LOADED");

document.addEventListener("DOMContentLoaded", () => {
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