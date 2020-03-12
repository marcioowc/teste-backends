import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Proposal {
	
	private String eventId;
	private String eventSchema;
	private String eventAction;
	private String eventTimestamp;
	private String proposalId;
	private String proposalLoanValue;
	private String months;
	
	public Proposal() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Proposal(String eventId, String eventSchema, String eventAction, String eventTimestamp, String proposalId,
			String proposalLoanValue, String months) {
		super();
		this.eventId = eventId;
		this.eventSchema = eventSchema;
		this.eventAction = eventAction;
		this.eventTimestamp = eventTimestamp;
		this.proposalId = proposalId;
		this.proposalLoanValue = proposalLoanValue;
		this.months = months;
	}
	public String getEventId() {
		return eventId;
	}
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	public String getEventSchema() {
		return eventSchema;
	}
	public void setEventSchema(String eventSchema) {
		this.eventSchema = eventSchema;
	}
	public String getEventAction() {
		return eventAction;
	}
	public void setEventAction(String eventAction) {
		this.eventAction = eventAction;
	}
	public String getEventTimestamp() {
		return eventTimestamp;
	}
	public void setEventTimestamp(String eventTimestamp) {
		this.eventTimestamp = eventTimestamp;
	}
	public String getProposalId() {
		return proposalId;
	}
	public void setProposalId(String proposalId) {
		this.proposalId = proposalId;
	}
	public String getProposalLoanValue() {
		return proposalLoanValue;
	}
	public void setProposalLoanValue(String proposalLoanValue) {
		this.proposalLoanValue = proposalLoanValue;
	}
	public String getMonths() {
		return months;
	}
	public void setMonths(String months) {
		this.months = months;
	}
	
	public int dateToInt() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
		Date date = new Date();
		long secs = 0;
		try {
			date = formatter.parse(this.getEventTimestamp().replaceAll("Z$", "+0000"));
			secs = (date.getTime())/1000; 
		}catch(ParseException e) {
			 e.printStackTrace();
		}
		return (int) secs;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((eventAction == null) ? 0 : eventAction.hashCode());
		result = prime * result + ((eventId == null) ? 0 : eventId.hashCode());
		result = prime * result + ((eventSchema == null) ? 0 : eventSchema.hashCode());
		result = prime * result + ((eventTimestamp == null) ? 0 : eventTimestamp.hashCode());
		result = prime * result + ((months == null) ? 0 : months.hashCode());
		result = prime * result + ((proposalId == null) ? 0 : proposalId.hashCode());
		result = prime * result + ((proposalLoanValue == null) ? 0 : proposalLoanValue.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Proposal other = (Proposal) obj;
		if (eventAction == null) {
			if (other.eventAction != null)
				return false;
		} else if (!eventAction.equals(other.eventAction))
			return false;
		if (eventId == null) {
			if (other.eventId != null)
				return false;
		} else if (!eventId.equals(other.eventId))
			return false;
		if (eventSchema == null) {
			if (other.eventSchema != null)
				return false;
		} else if (!eventSchema.equals(other.eventSchema))
			return false;
		if (eventTimestamp == null) {
			if (other.eventTimestamp != null)
				return false;
		} else if (!eventTimestamp.equals(other.eventTimestamp))
			return false;
		if (months == null) {
			if (other.months != null)
				return false;
		} else if (!months.equals(other.months))
			return false;
		if (proposalId == null) {
			if (other.proposalId != null)
				return false;
		} else if (!proposalId.equals(other.proposalId))
			return false;
		if (proposalLoanValue == null) {
			if (other.proposalLoanValue != null)
				return false;
		} else if (!proposalLoanValue.equals(other.proposalLoanValue))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Proposal [eventId=" + eventId + ", eventSchema=" + eventSchema + ", eventAction=" + eventAction
				+ ", eventTimestamp=" + eventTimestamp + ", proposalId=" + proposalId + ", proposalLoanValue="
				+ proposalLoanValue + ", months=" + months + "]";
	}
}