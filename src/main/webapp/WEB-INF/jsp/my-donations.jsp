<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="header.jsp" />
<link rel="stylesheet" href="https://unpkg.com/leaflet@1.9.4/dist/leaflet.css">
<script src="https://unpkg.com/leaflet@1.9.4/dist/leaflet.js"></script>

<div class="dashboard-shell">
    <aside class="sidebar">
        <p class="sidebar__title">Donor Menu</p>
        <a class="sidebar__link" href="${pageContext.request.contextPath}/donor/dashboard">Dashboard</a>
        <a class="sidebar__link" href="${pageContext.request.contextPath}/donor/browse-ngos">Browse Charitable Homes</a>
        <a class="sidebar__link sidebar__link--active" href="${pageContext.request.contextPath}/donor/my-donations">My Donations</a>
        <a class="sidebar__link" href="${pageContext.request.contextPath}/donor/profile">My Profile</a>
    </aside>

    <div class="dashboard-content">
        <h1>My Donations</h1>
        <p class="subtitle">All the donations you've made so far</p>

        <c:forEach var="donation" items="${donationList}">
            <div class="item-card">
                <div class="item-card__info">
                    <h3>${donation.title} &mdash; Qty ${donation.quantity}</h3>
                    <p>Donated on ${donation.donationDate} &bull; Pickup: ${donation.pickupAddress}</p>
                    <c:set var="assignment" value="${trackingByDonation[donation.id]}" />
                    <c:choose>
                        <c:when test="${not empty assignment}">
                            <p>Volunteer: ${assignment.volunteer.name} &bull; Tracking: ${assignment.status}</p>
                            <c:if test="${not empty assignment.pickedUpDate}">
                                <p>Picked up on ${assignment.pickedUpDate}</p>
                            </c:if>
                            <c:if test="${not empty assignment.deliveredDate}">
                                <p>Delivered on ${assignment.deliveredDate}</p>
                            </c:if>
                            <c:if test="${assignment.status == 'PICKED_UP' && assignment.trackingEnabled}">
                                <div id="map-${donation.id}" style="height:240px; margin-top:0.8rem; border-radius:0.5rem;"></div>
                                <p id="map-status-${donation.id}">Waiting for the volunteer's location...</p>
                                <script>
                                    (function () {
                                        const donationId = '${donation.id}';
                                        const status = document.getElementById('map-status-' + donationId);
                                        const mapElement = document.getElementById('map-' + donationId);
                                        let map;
                                        let marker;
                                        function refreshTracking() {
                                            fetch('${pageContext.request.contextPath}/donor/donations/' + donationId + '/tracking')
                                                .then(response => response.json())
                                                .then(data => {
                                                    if (data.latitude == null || data.longitude == null) return;
                                                    if (!map) {
                                                        map = L.map(mapElement).setView([data.latitude, data.longitude], 14);
                                                        L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {maxZoom: 19}).addTo(map);
                                                        marker = L.marker([data.latitude, data.longitude]).addTo(map);
                                                    } else {
                                                        marker.setLatLng([data.latitude, data.longitude]);
                                                        map.panTo([data.latitude, data.longitude]);
                                                    }
                                                    status.textContent = 'Live location updated at ' + (data.lastLocationUpdate || 'just now');
                                                });
                                        }
                                        refreshTracking();
                                        window.setInterval(refreshTracking, 10000);
                                    }());
                                </script>
                            </c:if>
                        </c:when>
                        <c:otherwise><p>Tracking: Waiting for volunteer assignment</p></c:otherwise>
                    </c:choose>
                </div>
                <span class="pill pill-open">${donation.status}</span>
            </div>
        </c:forEach>

        <c:if test="${empty donationList}">
            <div class="empty-state">
                You haven't made any donations yet.
                <a href="${pageContext.request.contextPath}/donor/browse-ngos">Browse NGOs</a> to get started.
            </div>
        </c:if>
        
        <c:if test="${empty donationList}">
    <div class="empty-state">
        <img class="empty-state-img" src="https://cdn-icons-png.flaticon.com/512/4076/4076432.png" alt="No donations">
        <p>You haven't made any donations yet.
        <a href="${pageContext.request.contextPath}/donor/browse-ngos">Browse NGOs</a> to get started.</p>
    </div>
</c:if>

    </div>
</div>

<jsp:include page="footer.jsp" />