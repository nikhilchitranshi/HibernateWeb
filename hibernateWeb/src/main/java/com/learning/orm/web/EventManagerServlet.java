package com.learning.orm.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.learning.orm.Utilities.HibernateUtil;
import com.learning.orm.domain.Events;

public class EventManagerServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy");

		try {

			HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();

			PrintWriter out = response.getWriter();
			out.println("<html><head><title>Event Manager</title></head><body>");
			// Handle actions
			if ("store".equals(request.getParameter("action"))) {
				String eventTitle = request.getParameter("eventTitle");
				String eventDate = request.getParameter("eventDate");
				if ("".equals(eventTitle) || "".equals(eventDate)) {
					out.println("<b><i>Please enter event title and date.</i></b>");
				} else {
					createAndStoreEvent(eventTitle, dateFormatter.parse(eventDate));
					out.println("<b><i>Added event.</i></b>");
				}
			}
			// Print page
			printEventForm(out);
			listEvents(out, dateFormatter);
			// Write HTML footer
			out.println("</body></html>");
			out.flush();
			out.close();

			HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();

		} catch (Exception ex) {

		}

	}

	private void listEvents(PrintWriter out, SimpleDateFormat dateFormatter) {

		List result = HibernateUtil.getSessionFactory().getCurrentSession().createCriteria(Events.class).list();
		if (result.size() > 0) {
			out.println("<h2>Events in database:</h2>");
			out.println("<table border='1'>");
			out.println("<tr>");
			out.println("<th>Event title</th>");
			out.println("<th>Event date</th>");
			out.println("</tr>");
			Iterator it = result.iterator();
			while (it.hasNext()) {
				Events event = (Events) it.next();
				out.println("<tr>");
				out.println("<td>" + event.getTitle() + "</td>");
				out.println("<td>" + dateFormatter.format(event.getDate()) + "</td>");
				out.println("</tr>");
			}
			out.println("</table>");
		}
	}

	private void printEventForm(PrintWriter out) {

		out.println("<h2>Add new event:</h2>");
		out.println("<form>");
		out.println("Title: <input name='eventTitle' length='50'/><br/>");
		out.println("Date (e.g. 24.12.2009): <input name='eventDate' length='10'/><br/>");
		out.println("<input type='submit' name='action' value='store'/>");
		out.println("</form>");

	}

	private void createAndStoreEvent(String eventTitle, Date date) {

		Events event = new Events();
		event.setDate(date);
		event.setTitle(eventTitle);
		HibernateUtil.getSessionFactory().getCurrentSession().save(event);
	}

}
