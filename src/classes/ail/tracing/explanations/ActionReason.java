package ail.tracing.explanations;

import ail.tracing.events.ActionEvent;

public class ActionReason extends AbstractReason {
	private final ActionEvent event;
	private SelectPlanReason parent;

	public ActionReason(final int state, final ActionEvent event) {
		super(state);
		this.event = event;
	}

	@Override
	public ActionEvent getEvent() {
		return this.event;
	}

	public void setParent(final SelectPlanReason parent) {
		this.parent = parent;
	}

	@Override
	public SelectPlanReason getParent() {
		return this.parent;
	}

	@Override
	public String getExplanation(final ExplanationLevel level) {
		final StringBuilder string = new StringBuilder();
		string.append(this.event.getAction()).append(" was executed in state ").append(this.state);
		if (this.parent != null) {
			string.append(", because ").append(this.parent);
		} else {
			string.append(".");
		}
		return string.toString();
	}
}