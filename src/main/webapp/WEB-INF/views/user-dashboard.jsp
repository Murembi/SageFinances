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

<body>

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
            <h1>User Dashboard</h1>
            <p>Welcome back, ${username}</p>
        </section>

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

            <form action="${pageContext.request.contextPath}/request-loans" method="post">

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
                    <th>Due Date</th>
                </tr>
                </thead>

                <tbody>
                <%
                    List<PendingLoanDTO> pendingLoans =
                            (List<PendingLoanDTO>) request.getAttribute("pendingLoans");

                    if (pendingLoans != null) {
                        for (PendingLoanDTO loan : pendingLoans) {
                %>
                <tr>
                    <td>
                        <img src="<%= request.getContextPath() + loan.getPhotoPath() %>"
                             alt="<%= loan.getAssetTitle() %>"
                             width="80"
                             height="80">
                    </td>
                    <td><%= loan.getAssetTitle() %></td>
                    <td><%= loan.getRequestDate().toLocalDate() %></td>
                    <td class="due-date"><%= loan.getDueDate().toLocalDate() %></td>
                </tr>
                <%
                        }
                    }
                %>
                </tbody>
            </table>
        </section>

        <section class="table-section">
            <h2>My Loaned Assets</h2>

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

                    if (loanedAssets != null) {
                        for (MyLoanedAssetDTO loan : loanedAssets) {
                %>
                <tr>
                    <td>
                        <img src="<%= request.getContextPath() + loan.getPhotoPath() %>"
                             alt="<%= loan.getAssetName() %>"
                             width="80"
                             height="80">
                    </td>
                    <td><%= loan.getAssetName() %></td>
                    <td><%= loan.getCheckoutDate().toLocalDate()%></td>
                    <td class="due-date"><%= loan.getDueDate().toLocalDate()%></td>
                    <td><%= loan.getStatus() %></td>
                </tr>
                <%
                        }
                    }
                %>
                </tbody>
            </table>
        </section>

    </main>

</div>
</body>
</html>