

const params = new URLSearchParams(window.location.search);
const assetId = params.get("id");

// Load asset when page opens
window.addEventListener("DOMContentLoaded", loadAsset);

async function loadAsset() {

    if (!assetId) {
        alert("No asset ID provided.");
        return;
    }

    try {

        const response = await fetch(
            `http://localhost:8081/assets/${assetId}`
        );

        if (!response.ok) {
            throw new Error("Asset not found");
        }

        const asset = await response.json();

        document.getElementById("title").value = asset.title || "";
        document.getElementById("category").value = asset.category || "";
        document.getElementById("serialNumber").value = asset.serialNumber || "";
        document.getElementById("cost").value = asset.cost || "";
        document.getElementById("location").value = asset.location || "";
        document.getElementById("condition").value = asset.condition || "";
        document.getElementById("photoPath").value = asset.photoPath || "";

    } catch (error) {
        console.error(error);
        alert("Could not load asset.");
    }
}

// Submit updated asset
document.getElementById("assetUpdateForm")
    .addEventListener("submit", async function (event) {

        event.preventDefault();

        try {

            const updatedAsset = {
                title: document.getElementById("title").value,
                category: document.getElementById("category").value,
                serialNumber: document.getElementById("serialNumber").value,
                cost: document.getElementById("cost").value,
                location: document.getElementById("location").value,
                condition: document.getElementById("condition").value,
                photoPath: document.getElementById("photoPath").value
            };

            const response = await fetch(
                `http://localhost:8081/assets/${assetId}`,
                {
                    method: "PUT",
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify(updatedAsset)
                }
            );

            if (!response.ok) {
                throw new Error("Update failed");
            }

            alert("Asset updated successfully!");

        } catch (error) {
            console.error(error);
            alert("Failed to update asset.");
        }
    });