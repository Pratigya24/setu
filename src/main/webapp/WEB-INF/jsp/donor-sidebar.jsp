<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<aside class="sidebar">
    <p class="sidebar__title">Donor Menu</p>
    <a class="sidebar__link ${activePage == 'dashboard' ? 'sidebar__link--active' : ''}"
       href="${pageContext.request.contextPath}/donor/dashboard">Dashboard</a>
    <a class="sidebar__link ${activePage == 'browse' ? 'sidebar__link--active' : ''}"
       href="${pageContext.request.contextPath}/donor/browse-ngos">Browse Requirements</a>
    <a class="sidebar__link ${activePage == 'homes' ? 'sidebar__link--active' : ''}"
       href="${pageContext.request.contextPath}/donor/charitable-homes">Verified Charitable Homes</a>
    <a class="sidebar__link ${activePage == 'offer' ? 'sidebar__link--active' : ''}"
       href="${pageContext.request.contextPath}/donor/offer-item">Donate an Item</a>
    <a class="sidebar__link ${activePage == 'donations' ? 'sidebar__link--active' : ''}"
       href="${pageContext.request.contextPath}/donor/my-donations">My Donations</a>
    <a class="sidebar__link ${activePage == 'profile' ? 'sidebar__link--active' : ''}"
       href="${pageContext.request.contextPath}/donor/profile">My Profile</a>
	    <a class="sidebar__link ${param.activePage == 'bookings' ? 'sidebar__link--active' : ''}"
       href="${pageContext.request.contextPath}/donor/my-bookings">My Occasion Bookings</a>
</aside>