package bitspilani.bosm.items;

public class ItemEvent {

    String event_id;
    String event_name, event_date, event_time, event_venue;
    String event_text;
    String event_club;

    public ItemEvent(String event_id, String event_name, String event_date, String event_time, String event_venue, String event_text, String event_club) {
        this.event_id = event_id;
        this.event_name = event_name;
        this.event_date = event_date;
        this.event_time = event_time;
        this.event_venue = event_venue;
        this.event_text = event_text;
        this.event_club = event_club;
    }

    public String getEvent_id() {
        return event_id;
    }

    public String getEvent_name() {
        return event_name;
    }

    public String getEvent_date() {
        return event_date;
    }

    public String getEvent_time() {
        return event_time;
    }

    public String getEvent_venue() {
        return event_venue;
    }

    public String getEvent_text() {
        return event_text;
    }

    public String getEvent_club() {
        return event_club;
    }
}
