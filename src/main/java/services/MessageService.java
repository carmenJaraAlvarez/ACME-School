
package services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import repositories.MessageRepository;
import domain.Message;
import domain.ParentsGroup;
import forms.MessageForm;

@Service
@Transactional
public class MessageService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private MessageRepository	messageRepository;
	@Autowired
	private ParentsGroupService	parentsGroupService;
	@Autowired
	private ParentService		parentService;
	@Autowired
	private Validator			validator;


	// Constructors -----------------------------------------------------------

	public MessageService() {
		super();
	}
	// Simple CRUD methods ----------------------------------------------------

	public Message create() {
		final Message res = new Message();
		return res;
	}

	public Message findOne(final int messageId) {
		return this.messageRepository.findOne(messageId);

	}

	public Message save(final Message message, final int parentsGroupId) {
		final Message savedMessage = this.messageRepository.save(message);
		final ParentsGroup parentsGroup = this.parentsGroupService.findOneToEdit(parentsGroupId);
		parentsGroup.getMessages().add(savedMessage);
		this.parentsGroupService.save(parentsGroup);
		return savedMessage;
	}
	// Other business methods -------------------------------------------------

	public Message reconstruct(final MessageForm messageForm, final MultipartFile file, final BindingResult binding) {
		Message result;
		try {
			result = messageForm.getMessage();
			if (file.getSize() == 0)
				result.setAttachment(null);
			result.setParent(this.parentService.findByPrincipal());
			final Calendar calendar = Calendar.getInstance();
			final Date fechaActual = calendar.getTime();
			result.setMoment(fechaActual);
			if (binding != null)
				this.validator.validate(result, binding);
		} catch (final Throwable oops) {
			result = null;
		}

		return result;
	}

	public Message saveForTeacher(final Message message, final int parentsGroupId) {
		Assert.isTrue(message.getParent() == null);
		final Message savedMessage = this.messageRepository.save(message);
		final ParentsGroup parentsGroup = this.parentsGroupService.findOne(parentsGroupId);
		parentsGroup.getMessages().add(savedMessage);
		this.parentsGroupService.save(parentsGroup);
		return savedMessage;
	}
}
