<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="header.jsp" />

<div class="dashboard-shell">
    <jsp:include page="ngo-sidebar.jsp">
    <jsp:param name="activePage" value="received" />
</jsp:include>

    <div class="dashboard-content">
        <h1>Donations Received</h1>
        <p class="subtitle">Donations donors have made against your requests</p>

        <c:forEach var="donation" items="${receivedDonations}">
            <div class="item-card">
                <div class="item-card__info">
                    <h3>${donation.title} — Qty ${donation.quantity}</h3>
                    <p>From: ${donation.donor.name} • ${donation.donationDate}</p>
                    <c:if test="${not empty donation.photoPath}">
                        <p><a href="${pageContext.request.contextPath}/view-donation-photo?donationId=${donation.id}"
                              target="_blank" class="btn btn-outline">View Donated Item Photo</a></p>
                    </c:if>
                    <c:set var="assignment" value="${trackingByDonation[donation.id]}" />
                    <c:if test="${not empty assignment}">
                        <p>Volunteer: ${assignment.volunteer.name} &bull; Tracking: ${assignment.status}</p>
                    </c:if>
                </div>
                <span class="pill pill-open">${donation.status}</span>
               <c:if test="${empty assignment and donation.status == 'ACCEPTED'}">
    <span class="pill pill-medium">Waiting for a volunteer</span>
</c:if>
            </div>
        </c:forEach>

        <c:if test="${empty receivedDonations}">
            <div class="empty-state">No donations received yet.</div>
        </c:if>
    </div>
</div>

<jsp:include page="footer.jsp" />