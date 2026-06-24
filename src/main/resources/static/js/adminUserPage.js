function clearSearch() {
    document.querySelector('input[name="keyword"]').value = '';
}

//CREATE USER POPUP FUNCTION
function openUserModal() {
    document.getElementById("userModal").style.display = "block";
}

function closeUserModal() {
    document.getElementById("userModal").style.display = "none";
}

// close when clicking outside modal
window.onclick = function (event) {
    const modal = document.getElementById("userModal");

    if (event.target === modal) {
        modal.style.display = "none";
    }
}

// AUTO OPEN MODAL IF ERRORS EXIST
window.onload = function () {
    const hasErrors = document.body.getAttribute("data-has-errors") === "true";

    if (hasErrors) {
        openAddAssetModal();
    }
};