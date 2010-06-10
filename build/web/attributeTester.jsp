<%-- 
    Document   : attributeTester
    Created on : 09.06.2010, 20:20:05
    Author     : Axel Richert
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Attribute Tester</title>
    </head>
    <body>
        <h1>Attribute Tester Functionalities</h1>

        <%
           String apiBase = "http://" + request.getRemoteHost() + ":"  +
                            request.getLocalPort() +
                            request.getContextPath() + "/WebAPI?action=";
        %>

        <strong><%=apiBase%></strong>

        <ul>
        <li><a href="<%=apiBase%>listDomainAttributes&domainName=cars">listDomainAttributes&domainName=cars</a></li>
        </ul>

    </body>
</html>
