document.addEventListener("DOMContentLoaded", function () {

    const form = document.getElementById("loanRequestForm");

    if (!form) return;

    form.addEventListener("submit", function (e) {

        const checked = document.querySelectorAll("input[name='assetIds']:checked").length;

        const currentActiveLoans =
            Number(document.querySelector(".dashboard-grid .stat-card:nth-child(2) p").textContent) +
            Number(document.querySelector(".dashboard-grid .stat-card:nth-child(3) p").textContent);

        if (currentActiveLoans + checked > 6) {

            e.preventDefault();

            const toast = document.getElementById("loanToast");
            toast.style.display = "block";

            setTimeout(() => {
                toast.style.display = "none";
            }, 3000);
        }
    });
});