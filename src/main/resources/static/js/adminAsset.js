function clearSearch() {
    document.querySelector('input[name="keyword"]').value = '';
}
// Edit Modal Popup Function

function openModal(id, title, category, serial, cost, location, condition, status) {

    document.getElementById("editId").value = id;
    document.getElementById("editTitle").value = title;
    document.getElementById("editCategory").value = category;
    document.getElementById("editSerial").value = serial;
    document.getElementById("editCost").value = cost;
    document.getElementById("editLocation").value = location;
    document.getElementById("editCondition").value = condition;
    document.getElementById("editStatus").value = status;

    document.getElementById("editModal").style.display = "block";
}
function closeModal() {
    document.getElementById("editModal").style.display = "none";
}