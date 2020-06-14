<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${author.nickname}'s Blog</title>
    <%@include file="shared/_libraries.jsp"%>
</head>
<body>
<%@include file="shared/_navbar.jsp"%>
<div>
    <%@include file="shared/_userProfile.jsp"%>
    <%@include file="shared/_articleSummaries.jsp"%>
</div>

</body>
</html>
