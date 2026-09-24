<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="header.jsp" />

<div class="dashboard-shell">
    <aside class="sidebar">
        <p class="sidebar__title">NGO Menu</p>
        <a class="sidebar__link" href="${pageContext.request.contextPath}/ngo/dashboard">Dashboard</a>
        <a class="sidebar__link" href="${pageContext.request.contextPath}/ngo/post-requirement">Post Requirement</a>
        <a class="sidebar__link" href="${pageContext.request.contextPath}/ngo/requests">My Requests</a>
        <a class="sidebar__link sidebar__link--active" href="${pageContext.request.contextPath}/ngo/donations-received">Donations Received</a>
        <a class="sidebar__link" href="${pageContext.request.contextPath}/ngo/profile">Profile</a>
    </aside>

    <div class="dashboard-content">
        <h1>Donations Received</h1>
        <p class="subtitle">Donations donors have made against your requests</p>

        <c:forEach var="donation" items="${receivedDonations}">
            <div class="item-card">
                <div class="item-card__info">
                    <h3>${donation.title} — Qty ${donation.quantity}</h3>
                    <p>From: ${donation.donor.name} • ${donation.donationDate}</p>
                    <c:set var="assignment" value="${trackingByDonation[donation.id]}" />
                    <c:if test="${not empty assignment}">
                        <p>Volunteer: ${assignment.volunteer.name} &bull; Tracking: ${assignment.status}</p>
                    </c:if>
                </div>
                <span class="pill pill-open">${donation.status}</span>
                <c:if test="${empty assignment and donation.status != 'COMPLETED'}">
                    <form action="${pageContext.request.contextPath}/ngo/assign-volunteer" method="post">
                        <input type="hidden" name="donationId" value="${donation.id}">
                        <select name="volunteerId" required>
                            <option value="">Choose volunteer</option>
                            <c:forEach var="volunteer" items="${availableVolunteers}">
                                <option value="${volunteer.id}">${volunteer.name} (${volunteer.availability})</option>
                            </c:forEach>
                        </select>
                        <button type="submit" class="btn btn-primary">Assign Volunteer</button>
                    </form>
                </c:if>
            </div>
        </c:forEach>

        <c:if test="${empty receivedDonations}">
            <div class="empty-state">No donations received yet.</div>
        </c:if>
    </div>
</div>

<jsp:include page="footer.jsp" />