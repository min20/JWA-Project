package org.question.di;

public class MessageRenderer {
	private MessageProvider messageProvider;

	public void setMessageProvider(MessageProvider messageProvider) {
		this.messageProvider = messageProvider;
	}

	public void render() {
		System.out.println(messageProvider.getMessage());
	}

}
