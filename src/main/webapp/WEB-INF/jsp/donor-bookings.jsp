<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="header.jsp" />

<div class="dashboard-shell">
    <jsp:include page="donor-sidebar.jsp">
        <jsp:param name="activePage" value="bookings" />
    </jsp:include>

    <div class="dashboard-content">
        <h1>My Occasion Bookings</h1>
        <p class="subtitle">Track the status of your celebration requests</p>

        <c:if test="${not empty successMsg}"><div class="success-msg">${successMsg}</div></c:if>

        <c:forEach var="b" items="${bookingList}">
            <div class="item-card">
                <div class="item-card__info">
                    <h3>${b.icon} ${b.displayTitle} &mdash; ${b.ngo.name}</h3>
                    <p>${b.eventDate} &bull; ${b.startTime} to ${b.endTime} &bull; Guests: ${b.guestCount}</p>
                    <p>${b.description}</p>
                    <c:if test="${not empty b.ngoResponse}">
                        <p><strong>Message from home:</strong> ${b.ngoResponse}</p>
                    </c:if>
                    <c:if test="${b.status == 'APPROVED'}">
                        <p><strong>Contact:</strong> ${b.ngo.phone} &bull; ${b.ngo.email}</p>
                    </c:if>
                </div>
                <c:choose>
                    <c:when test="${b.status == 'APPROVED'}"><span class="pill pill-low">APPROVED</span></c:when>
                    <c:when test="${b.status == 'PENDING'}"><span class="pill pill-medium">PENDING</span></c:when>
                    <c:when test="${b.status == 'REJECTED'}"><span class="pill pill-urgent">REJECTED</span></c:when>
                    <c:otherwise><span class="pill pill-closed">${b.status}</span></c:otherwise>
                </c:choose>
                <c:if test="${b.status == 'PENDING' || b.status == 'APPROVED'}">
                    <form action="${pageContext.request.contextPath}/donor/cancel-booking" method="post"
                          onsubmit="return confirm('Cancel this booking?');">
                        <input type="hidden" name="bookingId" value="${b.id}">
                        <button type="submit" class="btn btn-outline" style="color:#b91c1c; border-color:#fecaca;">Cancel</button>
                    </form>
                </c:if>
            </div>
        </c:forEach>

        <c:if test="${empty bookingList}">
            <div class="empty-state">No bookings yet.
                <a href="${pageContext.request.contextPath}/donor/charitable-homes">Find a charitable home</a>.</div>
        </c:if>
    </div>
</div>

<jsp:include page="footer.jsp" />