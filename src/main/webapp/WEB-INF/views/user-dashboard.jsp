<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="java.util.List" %>
<%@ page import="com.example.demo.dashboard.dto.AvailableAssetDTO" %>
<%@ page import="com.example.demo.dashboard.dto.PendingLoanDTO" %>
<%@ page import="com.example.demo.dashboard.dto.MyLoanedAssetDTO" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Dashboard</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>

<body class="dashboard-page">

<header class="header">
    <div class="logo">
        <img src="${pageContext.request.contextPath}/images/sage.png"
             alt="Logo"
             class="dashboard-logo">
    </div>

    <span class="header-text">ASSET MANAGEMENT SYSTEM</span>
</header>

<div class="container">

    <aside class="sidebar">

        <ul class="sidebar-menu">
            <li>
                <a href="${pageContext.request.contextPath}/user-dashboard">Dashboard</a>
            </li>
        </ul>

        <form action="${pageContext.request.contextPath}/logout" method="post">
            <button type="submit" class="logout-btn">Logout →</button>
        </form>

    </aside>

    <main class="main-content">

        <section class="dashboard-header">
            <h1>Welcome, ${username}</h1>
        </section>

        <div id="loanToast" class="toast-message" style="display:none;">
            You can only have 6 active loans at a time.
        </div>

        <section class="manager-dashboard-cards">
            <div class="dashboard-grid">

                <div class="stat-card">
                    <h2>Available Assets</h2>
                    <p>${availableAssetsCount}</p>
                </div>

                <div class="stat-card">
                    <h2>My Loans</h2>
                    <p>${myLoans}</p>
                </div>

                <div class="stat-card">
                    <h2>Pending Requests</h2>
                    <p>${myPendingRequests}</p>
                </div>

            </div>
        </section>

        <section class="table-section">
            <h2>Available Assets</h2>

            <form id="loanRequestForm"
                  action="${pageContext.request.contextPath}/request-loans"
                  method="post">

                <table>
                    <thead>
                    <tr>
                        <th>Image</th>
                        <th>Select</th>
                        <th>ID</th>
                        <th>Asset Name</th>
                        <th>Category</th>
                        <th>Status</th>
                    </tr>
                    </thead>

                    <tbody>
                    <%
                        List<AvailableAssetDTO> assets =
                                (List<AvailableAssetDTO>) request.getAttribute("availableAssets");

                        if (assets != null) {
                            for (AvailableAssetDTO asset : assets) {
                    %>
                    <tr>
                        <td>
                            <img src="<%= request.getContextPath() + asset.getPhotoPath() %>"
                                 alt="<%= asset.getAssetName() %>"
                                 width="80"
                                 height="80">
                        </td>

                        <td>
                            <input type="checkbox"
                                   name="assetIds"
                                   value="<%= asset.getAssetId() %>">
                        </td>

                        <td><%= asset.getAssetId() %></td>
                        <td><%= asset.getAssetName() %></td>
                        <td><%= asset.getCategory() %></td>
                        <td><%= asset.getStatus() %></td>
                    </tr>
                    <%
                            }
                        }
                    %>
                    </tbody>
                </table>

                <br>

                <button type="submit" class="approve-btn">
                    Request Selected Assets
                </button>

            </form>
        </section>

        <section class="table-section">
            <h2>Pending Loans</h2>

            <table>
                <thead>
                <tr>
                    <th>Image</th>
                    <th>Asset Name</th>
                    <th>Request Date</th>
                    <th>Status</th>
                </tr>
                </thead>

                <tbody>
                <%
                    List<PendingLoanDTO> pendingLoans =
                            (List<PendingLoanDTO>) request.getAttribute("pendingLoans");

                    if (pendingLoans != null && !pendingLoans.isEmpty()) {
                        for (PendingLoanDTO loan : pendingLoans) {
                %>
                <tr>
                    <td>
                        <img src="<%= request.getContextPath() + loan.getPhotoPath() %>"
                             alt="<%= loan.getAssetTitle() %>"
                             width="100"
                             height="100">
                    </td>

                    <td><%= loan.getAssetTitle() %></td>

                    <td>
                        <%= loan.getRequestDate() != null
                                ? loan.getRequestDate().toLocalDate()
                                : "-" %>
                    </td>

                    <td><%= loan.getStatus() %></td>
                </tr>
                <%
                        }
                    } else {
                %>
                <tr>
                    <td colspan="4">No pending loans found.</td>
                </tr>
                <%
                    }
                %>
                </tbody>
            </table>
        </section>

        <section class="table-section">
            <h2>Approved Loans</h2>

            <table>
                <thead>
                <tr>
                    <th>Image</th>
                    <th>Asset Name</th>
                    <th>Checkout Date</th>
                    <th>Due Date</th>
                    <th>Status</th>
                </tr>
                </thead>

                <tbody>
                <%
                    List<MyLoanedAssetDTO> loanedAssets =
                            (List<MyLoanedAssetDTO>) request.getAttribute("loanedAssets");

                    if (loanedAssets != null && !loanedAssets.isEmpty()) {
                        for (MyLoanedAssetDTO loan : loanedAssets) {
                %>
                <tr>
                    <td>
                        <img src="<%= request.getContextPath() + loan.getPhotoPath() %>"
                             alt="<%= loan.getAssetName() %>"
                             width="100"
                             height="100">
                    </td>

                    <td><%= loan.getAssetName() %></td>

                    <td>
                        <%= loan.getCheckoutDate() != null
                                ? loan.getCheckoutDate().toLocalDate()
                                : "-" %>
                    </td>

                    <td class="due-date">
                        <%= loan.getDueDate() != null
                                ? loan.getDueDate().toLocalDate()
                                : "-" %>
                    </td>

                    <td><%= loan.getStatus() %></td>
                </tr>
                <%
                    }
                } else {
                %>
                <tr>
                    <td colspan="5">No approved loans found.</td>
                </tr>
                <%
                    }
                %>
                </tbody>
            </table>
        </section>


    </main>

</div>

<footer class="footer">
    <a href="${pageContext.request.contextPath}/terms">Terms & Conditions</a>
    <a href="${pageContext.request.contextPath}/contact">Contact Us</a>
</footer>

<script src="${pageContext.request.contextPath}/js/userDashboard.js"></script>

</body>
</html>