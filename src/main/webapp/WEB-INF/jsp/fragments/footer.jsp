<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="footer">
    <div class="container">
        <fmt:message key="app.footer"/>
        <div align="right">
            <a href=${requestScope['javax.servlet.forward.request_uri']}?lang=en>Eng</a>|<a href=${requestScope['javax.servlet.forward.request_uri']}?lang=ru>Rus</a>
        </div>
    </div>
</div>
