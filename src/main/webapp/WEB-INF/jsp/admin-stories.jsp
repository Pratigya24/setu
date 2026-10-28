<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="header.jsp" />

<div class="dashboard-shell">
    <aside class="sidebar">
        <p class="sidebar__title">Admin Menu</p>
        <a class="sidebar__link" href="${pageContext.request.contextPath}/admin/dashboard">Dashboard</a>
        <a class="sidebar__link" href="${pageContext.request.contextPath}/admin/manage-users">Manage Users</a>
        <a class="sidebar__link" href="${pageContext.request.contextPath}/admin/manage-ngos">Manage Charitable Homes</a>
        <a class="sidebar__link" href="${pageContext.request.contextPath}/admin/manage-volunteers">Manage Volunteers</a>
        <a class="sidebar__link sidebar__link--active" href="${pageContext.request.contextPath}/admin/stories">Stories</a>
        <a class="sidebar__link" href="${pageContext.request.contextPath}/admin/reports">Reports</a>
    </aside>

    <div class="dashboard-content">
        <h1>Stories</h1>
        <p class="subtitle">Stories shown on the home page</p>

        <div class="form-wrapper" style="margin:0 0 1.5rem; max-width:620px;">
            <form action="${pageContext.request.contextPath}/admin/stories" method="post">
                <div class="form-group"><label>Title</label><input type="text" name="title" required></div>
                <div class="form-group"><label>Story</label><textarea name="content" rows="4" maxlength="1500" required></textarea></div>
                <div class="field-row">
                    <div class="form-group"><label>Author / Home</label><input type="text" name="authorName" required></div>
                    <div class="form-group"><label>Image URL</label><input type="url" name="imageUrl" required></div>
                </div>
                <button type="submit" class="btn btn-primary">Add Story</button>
            </form>
        </div>

        <c:forEach var="s" items="${storyList}">
            <div class="item-card">
                <div class="item-card__info">
                    <h3><c:out value="${s.title}"/></h3>
                    <p><c:out value="${s.authorName}"/></p>
                </div>
                <form action="${pageContext.request.contextPath}/admin/stories/delete" method="post"
                      onsubmit="return confirm('Delete this story?');">
                    <input type="hidden" name="id" value="${s.id}">
                    <button type="submit" class="btn btn-outline" style="color:#b91c1c; border-color:#fecaca;">Delete</button>
                </form>
            </div>
        </c:forEach>
    </div>
</div>

<jsp:include page="footer.jsp" />