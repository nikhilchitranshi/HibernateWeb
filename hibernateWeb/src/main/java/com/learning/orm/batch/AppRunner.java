package com.learning.orm.batch;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import com.learning.orm.Utilities.HibernateUtil;
import com.learning.orm.domain.Events;
import com.learning.orm.domain.Person;

public class AppRunner {

	public static void main(String[] args) {

		/*
		 * Long eventId = createAndStoreEvent("Sonu Nigam Concert", new Date());
		 * 
		 * Long personId = createAndStorePerson("Nikhil", "Chitranshi");
		 * 
		 * addPersonToEvent(personId, eventId);
		 * 
		 * List eventsList = listEvents();
		 * 
		 * displayEvents(eventsList);
		 */

		Person person = getPersonById(1);

		Events event = getEventById(1);
		
		System.out.println(person.getEvents().size());
		

		System.exit(0);

	}

	private static void addPersonToEvent(Long personId, Long eventId) {

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();

		session.beginTransaction();

		Person person = session.load(Person.class, personId);
		Events event = session.load(Events.class, eventId);

		person.getEvents().add(event);

		session.getTransaction().commit();

	}

	private static void displayEvents(List eventsList) {

		for (int i = 0; i < eventsList.size(); i++) {

			Events event = (Events) eventsList.get(i);

			System.out.println("Event is : " + event.getTitle());
			System.out.println("Event Date is : " + event.getDate());

		}

	}

	public static long createAndStoreEvent(String title, Date theDate) {

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();

		session.beginTransaction();

		Events event = new Events();

		event.setTitle(title);
		event.setDate(theDate);

		session.save(event);
		session.getTransaction().commit();

		return event.getId();
	}

	public static long createAndStorePerson(String firstName, String lastName) {

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();

		session.beginTransaction();

		Person person = new Person();

		person.setFirstName(firstName);
		person.setLastName(lastName);
		person.setAge(30);

		session.save(person);
		session.getTransaction().commit();

		return person.getId();
	}

	private static List listEvents() {

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List result = session.createQuery("from Events").list();
		session.getTransaction().commit();
		return result;
	}

	private static Person getPersonById(long id) {

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();

		session.beginTransaction();

		Person person = session.load(Person.class, id);

		person.getEvents().size();
		
		session.getTransaction().commit();

		return person;

	}

	private static Events getEventById(long id) {

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();

		session.beginTransaction();

		Events event = session.load(Events.class, id);

		event.getParticipants().size();
		
		session.getTransaction().commit();

		return event;
	}

}
