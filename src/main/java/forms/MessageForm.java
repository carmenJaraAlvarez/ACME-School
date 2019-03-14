
package forms;

import java.io.IOException;
import java.net.URLDecoder;

import org.springframework.web.multipart.MultipartFile;

import domain.FileUpload;
import domain.Message;

public class MessageForm {

	private Integer			messageId;
	private Integer			messageVersion;
	private String			body;
	private MultipartFile	file;


	public MessageForm() {
		this.messageId = 0;
		this.messageVersion = 0;

	}
	public MessageForm(Message message) {
		this.messageId = message.getId();
		this.messageVersion = message.getVersion();
		this.body = message.getBody();
	}

	public Integer getMessageId() {
		return this.messageId;
	}

	public void setMessageId(Integer messageId) {
		this.messageId = messageId;
	}

	public Integer getMessageVersion() {
		return this.messageVersion;
	}

	public void setMessageVersion(Integer messageVersion) {
		this.messageVersion = messageVersion;
	}

	public String getBody() {
		return this.body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public MultipartFile getFile() {
		return this.file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public Message getMessage() throws IOException {
		final Message message = new Message();
		message.setId(this.messageId);
		message.setVersion(this.messageVersion);
		message.setBody(URLDecoder.decode(this.body, "UTF-8"));
		FileUpload fileUpload = new FileUpload();
		fileUpload.setContent(this.file.getBytes());
		fileUpload.setType(this.file.getContentType());
		fileUpload.setName(this.file.getOriginalFilename());
		message.setAttachment(fileUpload);
		return message;
	}

}
