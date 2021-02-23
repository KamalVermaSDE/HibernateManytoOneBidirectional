package com.bidirectional.manytoone.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import com.bidirectional.manytoone.hibernate.model.Student;
import com.bidirectional.manytoone.hibernate.model.University;

public class HibernateStandAlone {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {

		Student student1 = new Student("Kamal", "Verma", "Maths");
		Student student2 = new Student("Gaurav", "Yadav", "Science");
		Student student3 = new Student("Dinesh", "Ch", "Physics");

		University university = new University("Uiet", "India");
		University university1 = new University("ymca", "India");
		List<Student> allStudents = new ArrayList<Student>();

		student1.setUniversity(university);
		student2.setUniversity(university1);
		student3.setUniversity(university1);

		allStudents.add(student1);
		allStudents.add(student2);
		allStudents.add(student3);

		university.setStudents(allStudents);

		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();

		session.persist(university);// Students will be presisted automatically,
									// thanks to CASCADE.ALL defined on students
									// property of University class.

		List<Student> students = (List<Student>) session.createQuery(
				"from Student ").list();
		for (Student s : students) {
			System.out.println("Student Details : " + s);
			System.out.println("Student University Details: "
					+ s.getUniversity());
		}

		// Note that now you can also access the relationship from University to Student

		session.getTransaction().commit();
		session.close();
	}

}
