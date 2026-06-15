//adminUsers Page handling 
   
document.addEventListener("DOMContentLoaded", () => {
    const usersTableBody = document.querySelector("#usersTable tbody");
    const searchInput = document.getElementById("searchInput");
    const searchBtn = document.getElementById("searchBtn");
    const addUserBtn = document.getElementById("addUserBtn");

    if (addUserBtn) {
    addUserBtn.addEventListener("click", () => {
        console.log("Add User Clicked");
        window.location.href = "createUsers.html";
    });
}

    
    async function loadUsers() {
        try {
            const response = await fetch("/api/users"); // GET all users
            if (!response.ok) throw new Error("Failed to fetch users");
            const users = await response.json();

            usersTableBody.innerHTML = ""; // clear table
            users.forEach(user => {
                const row = document.createElement("tr");
                row.innerHTML = `
                    <td>${user.id}</td>
                    <td>${user.name}</td>
                    <td>${user.department}</td>
                    <td>${user.email}</td>
                    <td><span style="background:#b0d6f9;padding:5px 10px;border-radius:8px;">${user.role}</span></td>
                    <td>
                        <button class="approve-btn" onclick="editUser(${user.id})">Edit</button>
                        <button class="reject-btn" onclick="deleteUser(${user.id})">Delete</button>
                    </td>
                `;
                usersTableBody.appendChild(row);
            });
        } catch (error) {
            console.error("Error loading users:", error);
        }
    }

    // Search users
    searchBtn.addEventListener("click", async () => {
        const query = searchInput.value.trim();
        if (!query) {
            loadUsers();
            return;
        }
        try {
            const response = await fetch(`/api/users?search=${query}`);
            if (!response.ok) throw new Error("Search failed");
            const users = await response.json();

            usersTableBody.innerHTML = "";
            users.forEach(user => {
                const row = document.createElement("tr");
                row.innerHTML = `
                    <td>${user.id}</td>
                    <td>${user.name}</td>
                    <td>${user.department}</td>
                    <td>${user.email}</td>
                    <td><span style="background:#90f012;padding:5px 10px;border-radius:8px;">${user.role}</span></td>
                    <td>
                        <button class="approve-btn" onclick="editUser(${user.id})">Edit</button>
                        <button class="reject-btn" onclick="deleteUser(${user.id})">Delete</button>
                    </td>
                `;
                usersTableBody.appendChild(row);
            });
        } catch (error) {
            console.error("Error searching users:", error);
        }
    });

    // Add user (redirect to registration form)
    // console.log(addUserBtn);
    // addUserBtn.addEventListener("click", () => {
    //     console.log("Add User clicked");
    //     window.location.href = "./create.html";
    // });
    // console.log("Add User Button:", addUserBtn);


    // Edit user
    window.editUser = async function(id) {
        window.location.href = `/dashboard/admin/edit-user/${id}`;
    };

    // Delete user
    window.deleteUser = async function(id) {
        if (!confirm("Are you sure you want to delete this user?")) return;
        try {
            const response = await fetch(`/api/users/${id}`, { method: "DELETE" });
            if (response.ok) {
                alert("User deleted successfully!");
                loadUsers();
            } else {
                alert("Failed to delete user.");
            }
        } catch (error) {
            console.error("Error deleting user:", error);
        }
    };

    // Initial load
    loadUsers();
});