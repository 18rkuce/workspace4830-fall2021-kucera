import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import datamodel.AppointmentKucera;
import util.UtilDBKucera;

@WebServlet("/MyServletHibernateDBKucera")
public class MyServletHibernateDBKucera extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public MyServletHibernateDBKucera() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      response.setContentType("text/html");

      // #1
      UtilDBKucera.createAppointments("10/23/2021", "3:30", "Applebees", "Fancy Like");
      UtilDBKucera.createAppointments("10/24/2021", "4:15", "Applees again", "Bouji");
      
      // #2
      retrieveDisplayData(response.getWriter());
   }

   void retrieveDisplayData(PrintWriter out) {
      String title = "Database Result";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + //
            "transitional//en\">\n"; //
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body bgcolor=\"#f0f0f0\">\n" + //
            "<h1 align=\"center\">" + title + "</h1>\n");
      out.println("<ul>");
      List<AppointmentKucera> listAppointments = UtilDBKucera.listAppointments();
      for (AppointmentKucera appointment : listAppointments) {
         System.out.println("[DBG] "
               + appointment.getDate() + ", " //
               + appointment.getTime() + ", " //
               + appointment.getLocation() + ", " //
               + appointment.getDescription());

         out.println("<li>"
               + appointment.getDate() + ", " //
               + appointment.getTime() + ", " //
               + appointment.getLocation() + ", " //
               + appointment.getDescription() + "</li>");
      }
      out.println("</ul>");
      out.println("</body></html>");
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }
}
