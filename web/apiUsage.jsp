<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>API Usage</title>
    </head>
    <body>
        <h1>Web API Basic URL</h1>

        <%
           String apiBase = "http://" + request.getRemoteHost() + ":"  +
                            request.getLocalPort() + 
                            request.getContextPath() + "/WebAPI?action=";
        %>

        <strong><%=apiBase%></strong>
        <h1>Actions:</h1>
         <h2>Maintenance</h2>
        <ul>
            <li><a href="<%=apiBase%>setUpDB">setUpDB</a></li>

        </ul>
        <h2>User</h2>
        ...
        <h2>Domain</h2>
        <ul>
            <li><a href="<%=apiBase%>listDomains">listDomains</a></li>
            <li><a href="<%=apiBase%>createDomain&domainName=test">createDomain&domainName=test</a></li>
        </ul>

        <h2>Attributes</h2>
        <ul>
            <li><a href="<%=apiBase%>createAttribute&attributeName=horsepower&domainName=cars">createAttribute&attributeName=horsepower&domainName=cars</a></li>
            <li><a href="<%=apiBase%>deleteAttribute&attributeName=horsepower&domainName=cars">deleteAttribute&attributeName=horsepower&domainName=cars</a></li>
            <li><a href="<%=apiBase%>listDomainAttributes&domainName=cars">listDomainAttributes&domainName=cars</a></li>
           
        </ul>

        <h2>Items</h2>
        ...
    </body>
</html>
