package dto;

public class Story {
	private int story_id;
	private String story_name;
	private String story_author;
	
	public Story() {}
	
	public void setStoryId(int story_id) {
		this.story_id = story_id;
	}
	
	public void setStoryName(String story_name) {
		this.story_name = story_name;
	}
	
	public void setStoryAuthor(String story_author) {
		this.story_author = story_author;
	}
	
	public int getStoryId() {
		return this.story_id;
	}
	
	public String getStoryName() {
		return this.story_name;
	}
	
	public String getStoryAuthor() {
		return this.story_author;
	}

}
