
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.MessageRepository;

@Service
@Transactional
public class MessageService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private MessageRepository	messageRepository;


	// Constructors -----------------------------------------------------------

	public MessageService() {
		super();
	}
	// Simple CRUD methods ----------------------------------------------------

	// Other business methods -------------------------------------------------

}
