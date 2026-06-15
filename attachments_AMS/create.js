//register/create handling

document.getElementById("create").addEventListener("submit", async (event) => {
    event.preventDefault();

    const formData = {
        name: document.getElementById("name").value,
        email: document.getElementById("email").value,
        department: document.getElementById("department").value,
        role: document.getElementById("role").value,
        passwordHash: document.getElementById("passwordHash").value
    };

    try {
        const response = await fetch("/api/users", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(formData)
        });

        const alertBox = document.getElementById("customAlert");

        if (response.ok) {
            alertBox.textContent = "User created successfully!";
            alertBox.style.display = "block";

            setTimeout(() => {
                window.location.href = "/dashboard/admin";
            }, 2000);
        } else {
            alertBox.textContent = "Failed to create user. Please check your input.";
            alertBox.style.display = "block";
        }
    } catch (error) {
        console.error("Error:", error);
        const alertBox = document.getElementById("customAlert");
        alertBox.textContent = "An unexpected error occurred.";
        alertBox.style.display = "block";
    }
});