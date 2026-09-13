<jsp:include page="header.jsp" />

<div class="table-wrapper">
    <h2 style="margin-bottom:1.5rem;">My Donations</h2>
    <table>
        <thead>
        <tr>
            <th>Date</th>
            <th>NGO</th>
            <th>Amount</th>
            <th>Status</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="donation" items="${donationList}">
            <tr>
                <td>${donation.date}</td>
                <td>${donation.ngoName}</td>
                <td>₹${donation.amount}</td>
                <td><span class="badge badge-success">Completed</span></td>
            </tr>
        </c:forEach>
        <c:if test="${empty donationList}">
            <tr><td colspan="4">No donations yet.</td></tr>
        </c:if>
        </tbody>
    </table>
</div>

<jsp:include page="footer.jsp" />