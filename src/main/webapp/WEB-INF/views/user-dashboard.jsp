<%@ page import="java.util.List" %>
<%@ page import="com.example.demo.dashboard.dto.AvailableAssetDTO" %>

<table border="1">
    <%
        List<AvailableAssetDTO> assets =
                (List<AvailableAssetDTO>) request.getAttribute("availableAssets");

        for (AvailableAssetDTO asset : assets) {
    %>

    <tr>
        <td><%= asset.getAssetId() %></td>
        <td><%= asset.getAssetName() %></td>
        <td><%= asset.getCategory() %></td>
        <td><%= asset.getStatus() %></td>
    </tr>

    <%
        }
    %>
</table>

<h3>Pending Loans</h3>
<table border="1">
    <%
        List<?> pendingLoans =
                (List<?>) request.getAttribute("pendingLoans");

        for (Object loan : pendingLoans) {
    %>
    <tr>
        <td><%= loan %></td>
    </tr>
    <%
        }
    %>
</table>

<h3>My Loaned Assets</h3>
<table border="1">
    <%
        List<?> loanedAssets =
                (List<?>) request.getAttribute("loanedAssets");

        for (Object loan : loanedAssets) {
    %>
    <tr>
        <td><%= loan %></td>
    </tr>
    <%
        }
    %>
</table>