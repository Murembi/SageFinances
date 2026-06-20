<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="java.util.List" %>
<%@ page import="com.example.demo.dashboard.dto.AvailableAssetDTO" %>
<%@ page import="com.example.demo.dashboard.dto.PendingLoanDTO" %>
<%@ page import="com.example.demo.dashboard.dto.MyLoanedAssetDTO" %>

<!DOCTYPE html>
<html>
<head>
    <title>User Dashboard</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/css/styles.css">
</head>
<body>
<h1>User Dashboard</h1>

<div class="dashboard-cards">

    <div class="card">
        <h2>Available Assets</h2>
        <label>${availableAssetsCount}</label>
    </div>

    <div class="card">
        <h2>My Loans</h2>
        <label>${myLoans}</label>
    </div>

    <div class="card">
        <h2>Pending Requests</h2>
        <label>${myPendingRequests}</label>
    </div>

</div>
<h3>Available Assets</h3>

<form action="${pageContext.request.contextPath}/request-loans" method="post">

    <table border="1">
        <tr>
            <th>Select</th>
            <th>ID</th>
            <th>Asset Name</th>
            <th>Category</th>
            <th>Status</th>
        </tr>

        <%
            List<AvailableAssetDTO> assets =
                    (List<AvailableAssetDTO>) request.getAttribute("availableAssets");

            for (AvailableAssetDTO asset : assets) {
        %>
        <tr>
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
        %>
    </table>

    <br>

    <button type="submit">Request Selected Assets</button>

</form>
<h3>Pending Loans</h3>
<table border="1">
    <tr>
        <th>Asset Name</th>
        <th>Request Date</th>
        <th>Due Date</th>
    </tr>

    <%
        List<PendingLoanDTO> pendingLoans =
                (List<PendingLoanDTO>) request.getAttribute("pendingLoans");

        for (PendingLoanDTO loan : pendingLoans) {
    %>
    <tr>
        <td><%= loan.getAssetTitle() %></td>
        <td><%= loan.getRequestDate() %></td>
        <td><%= loan.getDueDate() %></td>
    </tr>
    <%
        }
    %>
</table>

<h3>My Loaned Assets</h3>
<table border="1">
    <tr>
        <th>Asset Name</th>
        <th>Checkout Date</th>
        <th>Due Date</th>
        <th>Status</th>
    </tr>

    <%
        List<MyLoanedAssetDTO> loanedAssets =
                (List<MyLoanedAssetDTO>) request.getAttribute("loanedAssets");

        for (MyLoanedAssetDTO loan : loanedAssets) {
    %>
    <tr>
        <td><%= loan.getAssetName() %></td>
        <td><%= loan.getCheckoutDate() %></td>
        <td><%= loan.getDueDate() %></td>
        <td><%= loan.getStatus() %></td>
    </tr>
    <%
        }
    %>
</table>