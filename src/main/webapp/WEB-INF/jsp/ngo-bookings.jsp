<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="header.jsp" />

<div class="dashboard-shell">
   <jsp:include page="ngo-sidebar.jsp">
    <jsp:param name="activePage" value="bookings" />
</jsp:include>

    <div class="dashboard-content">
        <h1>Occasion Bookings</h1>
        <p class="subtitle">Donors who want to celebrate at your home. Approve only if you are available.</p>

        <c:if test="${not empty successMsg}"><div class="success-msg">${successMsg}</div></c:if>
        <c:if test="${not empty errorMsg}"><div class="error-msg">${errorMsg}</div></c:if>

        <c:forEach var="b" items="${bookingList}">
            <div class="item-card" style="align-items:flex-start;">
                <div class="item-card__info">
                    <h3>${b.icon} ${b.displayTitle}</h3>
                    <p><strong>${b.eventDate}</strong> &bull; ${b.startTime} to ${b.endTime} &bull; ${b.guestCount} guests</p>
                    <p><strong>Donor:</strong> ${b.donor.name} &bull; ${b.contactPhone}</p>
                    <p>${b.description}</p>
                    <c:if test="${not empty b.ngoResponse}"><p><strong>Your note:</strong> ${b.ngoResponse}</p></c:if>
                </div>
                <c:choose>
                    <c:when test="${b.status == 'PENDING'}">
                        <form action="${pageContext.request.contextPath}/ngo/booking-respond" method="post" style="min-width:240px;">
                            <input type="hidden" name="bookingId" value="${b.id}">
                            <div class="form-group">
                                <input type="text" name="note" placeholder="Note to donor (optional)">
                            </div>
                            <div style="display:flex; gap:0.5rem;">
                                <button type="submit" name="action" value="APPROVE" class="btn btn-primary">Approve</button>
                                <button type="submit" name="action" value="REJECT" class="btn btn-outline"
                                        style="color:#b91c1c; border-color:#fecaca;">Reject</button>
                            </div>
                        </form>
                    </c:when>
                    <c:otherwise><span class="pill pill-open">${b.status}</span></c:otherwise>
                </c:choose>
            </div>
        </c:forEach>

        <c:if test="${empty bookingList}"><div class="empty-state">No booking requests yet.</div></c:if>
    </div>
</div>

<jsp:include page="footer.jsp" />