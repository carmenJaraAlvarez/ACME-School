
package forms;

import domain.Folder;
import domain.PrivateMessage;

public class MovePrivateMessageForm {

	private PrivateMessage	privateMessage;
	private Folder			currentFolder;
	private Folder			newFolder;


	public MovePrivateMessageForm() {

	}
	public MovePrivateMessageForm(PrivateMessage privateMessage, Folder currentFolder, Folder newFolder) {
		this.privateMessage = privateMessage;
		this.currentFolder = currentFolder;
		this.newFolder = newFolder;
	}

	public PrivateMessage getPrivateMessage() {
		return this.privateMessage;
	}

	public void setPrivateMessage(PrivateMessage privateMessage) {
		this.privateMessage = privateMessage;
	}

	public Folder getCurrentFolder() {
		return this.currentFolder;
	}

	public void setCurrentFolder(Folder currentFolder) {
		this.currentFolder = currentFolder;
	}

	public Folder getNewFolder() {
		return this.newFolder;
	}

	public void setNewFolder(Folder newFolder) {
		this.newFolder = newFolder;
	}

}
