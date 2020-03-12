import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Proponent {
	
	private String eventId;
	private String eventSchema;
	private String eventAction;
	private String eventTimestamp;
	private String proposalId;
	private String proponentId;
	private String proponentName;
	private String proponentAge;
	private String proponentMonthlyIncome;
	private String proponentMain;
	
	public Proponent() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Proponent(String eventId, String eventSchema, String eventAction, String eventTimestamp, String proposalId,
			String proponentId, String proponentName, String proponentAge, String proponentMonthlyIncome, String proponentMain) {
		super();
		
		this.eventId = eventId;
		this.eventSchema = eventSchema;
		this.eventAction = eventAction;
		this.eventTimestamp = eventTimestamp;
		this.proposalId = proposalId;
		this.proponentId = proponentId;
		this.proponentName = proponentName;
		this.proponentAge = proponentAge;
		this.proponentMonthlyIncome = proponentMonthlyIncome;
		this.proponentMain = proponentMain;
		
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
	public String getProponentId() {
		return proponentId;
	}
	public void setProponentId(String proponentId) {
		this.proponentId = proponentId;
	}
	public String getProponentName() {
		return proponentName;
	}
	public void setProponentName(String proponentName) {
		this.proponentName = proponentName;
	}
	public String getProponentAge() {
		return proponentAge;
	}
	public void setProponentAge(String proponentAge) {
		this.proponentAge = proponentAge;
	}
	public String getProponentMonthlyIncome() {
		return proponentMonthlyIncome;
	}
	public void setProponentMonthlyIncome(String proponentMonthlyIncome) {
		this.proponentMonthlyIncome = proponentMonthlyIncome;
	}
	public String getProponentMain() {
		return proponentMain;
	}
	public void setProponentMain(String proponentMain) {
		this.proponentMain = proponentMain;
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
		result = prime * result + ((proponentMonthlyIncome == null) ? 0 : proponentMonthlyIncome.hashCode());
		result = prime * result + ((proponentAge == null) ? 0 : proponentAge.hashCode());
		result = prime * result + ((proponentId == null) ? 0 : proponentId.hashCode());
		result = prime * result + ((proponentMain == null) ? 0 : proponentMain.hashCode());
		result = prime * result + ((proponentName == null) ? 0 : proponentName.hashCode());
		result = prime * result + ((proposalId == null) ? 0 : proposalId.hashCode());
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
		Proponent other = (Proponent) obj;
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
		if (proponentMonthlyIncome == null) {
			if (other.proponentMonthlyIncome != null)
				return false;
		} else if (!proponentMonthlyIncome.equals(other.proponentMonthlyIncome))
			return false;
		if (proponentAge == null) {
			if (other.proponentAge != null)
				return false;
		} else if (!proponentAge.equals(other.proponentAge))
			return false;
		if (proponentId == null) {
			if (other.proponentId != null)
				return false;
		} else if (!proponentId.equals(other.proponentId))
			return false;
		if (proponentMain == null) {
			if (other.proponentMain != null)
				return false;
		} else if (!proponentMain.equals(other.proponentMain))
			return false;
		if (proponentName == null) {
			if (other.proponentName != null)
				return false;
		} else if (!proponentName.equals(other.proponentName))
			return false;
		if (proposalId == null) {
			if (other.proposalId != null)
				return false;
		} else if (!proposalId.equals(other.proposalId))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Proponent [eventId=" + eventId + ", eventSchema=" + eventSchema + ", eventAction=" + eventAction
				+ ", eventTimestamp=" + eventTimestamp + ", proposalId=" + proposalId + ", proponentId=" + proponentId
				+ ", proponentName=" + proponentName + ", proponentAge=" + proponentAge + ", month=" + proponentMonthlyIncome
				+ ", proponentMain=" + proponentMain + "]";
	}

}
