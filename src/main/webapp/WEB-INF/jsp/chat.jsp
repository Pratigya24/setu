<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="header.jsp" />

<div class="dashboard-shell">
    <c:choose>
        <c:when test="${chatRole == 'DONOR'}">
            <jsp:include page="donor-sidebar.jsp">
                <jsp:param name="activePage" value="donations" />
            </jsp:include>
        </c:when>
        <c:when test="${chatRole == 'NGO'}">
            <jsp:include page="ngo-sidebar.jsp">
                <jsp:param name="activePage" value="received" />
            </jsp:include>
        </c:when>
        <c:otherwise>
            <aside class="sidebar">
                <p class="sidebar__title">Volunteer Menu</p>
                <a class="sidebar__link" href="${pageContext.request.contextPath}/volunteer/dashboard">Dashboard</a>
                <a class="sidebar__link sidebar__link--active" href="${pageContext.request.contextPath}/volunteer/assignments">My Assignments</a>
                <a class="sidebar__link" href="${pageContext.request.contextPath}/volunteer/available-pickups">Available Pickups</a>
                <a class="sidebar__link" href="${pageContext.request.contextPath}/volunteer/profile">Profile</a>
            </aside>
        </c:otherwise>
    </c:choose>

    <div class="dashboard-content">
        <h1>Chat &mdash; <c:out value="${donation.title}"/></h1>
        <p class="subtitle">Donor, charitable home and volunteer can coordinate here.</p>

        <div class="panel" style="max-width:720px;">
            <p class="panel__title">People in this chat</p>
            <p><strong>Donor:</strong> <c:out value="${donation.donor.name}"/> &bull; <c:out value="${donation.donor.phone}"/></p>
            <p><strong>Charitable Home:</strong> <c:out value="${donation.ngo.name}"/> &bull; <c:out value="${donation.ngo.phone}"/></p>
            <c:choose>
                <c:when test="${not empty assignment}">
                    <p><strong>Volunteer:</strong> <c:out value="${assignment.volunteer.name}"/> &bull; <c:out value="${assignment.volunteer.phone}"/></p>
                </c:when>
                <c:otherwise>
                    <p style="color:#94a3b8;">Volunteer will join once a pickup is accepted.</p>
                </c:otherwise>
            </c:choose>
        </div>

        <div class="panel" style="max-width:720px;">
            <div id="chatBox" style="height:380px; overflow-y:auto; display:flex; flex-direction:column; gap:0.6rem;">
                <c:forEach var="m" items="${messages}">
                    <c:set var="mine" value="${m.sender.id == currentUserId}" />
                    <div style="align-self:${mine ? 'flex-end' : 'flex-start'}; max-width:75%;
                                background:${mine ? '#dcfce7' : '#f1f5f9'}; padding:0.6rem 0.9rem; border-radius:0.9rem;">
                        <div style="font-size:0.72rem; color:#64748b; font-weight:700;">
                            <c:out value="${mine ? 'You' : m.sender.name}"/> (${m.senderRole})
                        </div>
                        <div style="font-size:0.9rem; white-space:pre-wrap;"><c:out value="${m.content}"/></div>
                        <div style="font-size:0.68rem; color:#94a3b8; text-align:right;">
                            ${m.sentAt.toString().substring(0,16).replace('T',' ')}
                        </div>
                    </div>
                </c:forEach>
                <c:if test="${empty messages}">
                    <div class="empty-state">No messages yet. Say hello!</div>
                </c:if>
            </div>

            <form action="${pageContext.request.contextPath}/chat/${donation.id}/send" method="post"
                  style="display:flex; gap:0.6rem; margin-top:1rem;">
                <input type="text" name="content" maxlength="1000" placeholder="Type a message..." required
                       style="flex:1; padding:0.7rem 1rem; border:1.5px solid #e5e7eb; border-radius:0.7rem;">
                <button type="submit" class="btn btn-primary">Send</button>
            </form>
        </div>
    </div>
</div>

<script>
    var box = document.getElementById('chatBox');
    box.scrollTop = box.scrollHeight;
</script>

<jsp:include page="footer.jsp" />