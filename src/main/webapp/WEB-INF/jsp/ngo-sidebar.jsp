<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<aside class="sidebar">
    <p class="sidebar__title">NGO Menu</p>
    <a class="sidebar__link ${param.activePage == 'dashboard' ? 'sidebar__link--active' : ''}"
       href="${pageContext.request.contextPath}/ngo/dashboard">Dashboard</a>
    <a class="sidebar__link ${param.activePage == 'post' ? 'sidebar__link--active' : ''}"
       href="${pageContext.request.contextPath}/ngo/post-requirement">Post Requirement</a>
    <a class="sidebar__link ${param.activePage == 'requests' ? 'sidebar__link--active' : ''}"
       href="${pageContext.request.contextPath}/ngo/requests">My Requests</a>
    <a class="sidebar__link ${param.activePage == 'available' ? 'sidebar__link--active' : ''}"
       href="${pageContext.request.contextPath}/ngo/available-donations">Available Items</a>
    <a class="sidebar__link ${param.activePage == 'received' ? 'sidebar__link--active' : ''}"
       href="${pageContext.request.contextPath}/ngo/donations-received">Donations Received</a>
    <a class="sidebar__link ${param.activePage == 'bookings' ? 'sidebar__link--active' : ''}"
       href="${pageContext.request.contextPath}/ngo/bookings">Occasion Bookings</a>
    <a class="sidebar__link ${param.activePage == 'profile' ? 'sidebar__link--active' : ''}"
       href="${pageContext.request.contextPath}/ngo/profile">Profile</a>
</aside>
