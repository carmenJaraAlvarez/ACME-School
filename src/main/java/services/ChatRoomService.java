
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.ChatRoomRepository;

@Service
@Transactional
public class ChatRoomService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ChatRoomRepository	chatRoomRepository;


	// Constructors -----------------------------------------------------------

	public ChatRoomService() {
		super();
	}
	// Simple CRUD methods ----------------------------------------------------

	// Other business methods -------------------------------------------------

}
