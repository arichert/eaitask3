package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class apiUsage_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.Vector _jspx_dependants;

  private org.apache.jasper.runtime.ResourceInjector _jspx_resourceInjector;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.apache.jasper.runtime.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("\n");
      out.write("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\"\n");
      out.write("   \"http://www.w3.org/TR/html4/loose.dtd\">\n");
      out.write("\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("        <title>API Usage</title>\n");
      out.write("    </head>\n");
      out.write("    <body>\n");
      out.write("        <h1>Web API Basic URL</h1>\n");
      out.write("\n");
      out.write("        ");

           String apiBase = "http://" + request.getRemoteHost() + ":"  +
                            request.getLocalPort() + 
                            request.getContextPath() + "/WebAPI?action=";
        
      out.write("\n");
      out.write("\n");
      out.write("        <strong>");
      out.print(apiBase);
      out.write("</strong>\n");
      out.write("        <h1>Actions:</h1>\n");
      out.write("         <h2>Maintenance</h2>\n");
      out.write("        <ul>\n");
      out.write("            <li><a href=\"");
      out.print(apiBase);
      out.write("setUpDB\">setUpDB</a></li>\n");
      out.write("\n");
      out.write("        </ul>\n");
      out.write("        <h2>User</h2>\n");
      out.write("        ...\n");
      out.write("        <h2>Domain</h2>\n");
      out.write("        <ul>\n");
      out.write("            <li><a href=\"");
      out.print(apiBase);
      out.write("listDomains\">listDomains</a></li>\n");
      out.write("            <li><a href=\"");
      out.print(apiBase);
      out.write("createDomain&domainName=test\">createDomain&domainName=test</a></li>\n");
      out.write("        </ul>\n");
      out.write("\n");
      out.write("        <h2>Attributes</h2>\n");
      out.write("        <ul>\n");
      out.write("            <li><a href=\"");
      out.print(apiBase);
      out.write("createAttribute&attributeName=horsepower&domainName=cars\">createAttribute&attributeName=horsepower&domainName=cars</a></li>\n");
      out.write("            <li><a href=\"");
      out.print(apiBase);
      out.write("deleteAttribute&attributeName=horsepower&domainName=cars\">deleteAttribute&attributeName=horsepower&domainName=cars</a></li>\n");
      out.write("            <li><a href=\"");
      out.print(apiBase);
      out.write("listDomainAttributes&domainName=cars\">listDomainAttributes&domainName=cars</a></li>\n");
      out.write("           \n");
      out.write("        </ul>\n");
      out.write("\n");
      out.write("        <h2>Items</h2>\n");
      out.write("        ...\n");
      out.write("    </body>\n");
      out.write("</html>\n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
