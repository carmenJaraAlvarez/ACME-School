
package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.AnnouncementRepository;
import domain.Announcement;

@Service
@Transactional
public class AnnouncementService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private AnnouncementRepository	announcementRepository;


	// Constructors -----------------------------------------------------------

	public AnnouncementService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	// Other business methods -------------------------------------------------

	public Announcement getValidRandom() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -2);
		Date fechaActual = calendar.getTime();
		Collection<Announcement> announcements = this.announcementRepository.findAll();
		List<Announcement> validAnnouncements = new ArrayList<Announcement>();
		for (Announcement a : announcements) {
			if (a.getPaidMoment().after(fechaActual)) {
				validAnnouncements.add(a);
			}
		}
		Random rand = new Random();
		Announcement randomAnnouncement = validAnnouncements.get(rand.nextInt(validAnnouncements.size()));
		return randomAnnouncement;
	}

}
