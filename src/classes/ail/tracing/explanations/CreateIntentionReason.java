package ail.tracing.explanations;

import ail.syntax.Event;
import ail.tracing.events.CreateIntentionEvent;

public class CreateIntentionReason extends AbstractReason {
	private final CreateIntentionEvent event;

	public CreateIntentionReason(final int state, final CreateIntentionEvent event) {
		super(state);
		this.event = event;
	}

	@Override
	public CreateIntentionEvent getEvent() {
		return this.event;
	}

	@Override
	public AbstractReason getParent() {
		return null;
	}

	@Override
	public String getExplanation(final ExplanationLevel level) {
		final StringBuilder string = new StringBuilder();
		string.append("it was created in state ").append(this.state);
		Event event = this.event.getEvent();
		if (event != null) {
			string.append(" for the event '").append(event).append("'");
		}
		string.append(".");
		return string.toString();
	}
}