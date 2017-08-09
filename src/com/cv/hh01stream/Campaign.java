package com.cv.hh01stream;

import java.util.List;

public class Campaign {

	private String title;
	private List<Integer> segments;

	public Campaign(String title, List<Integer> segments) {
		super();
		this.title = title;
		this.segments = segments;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Integer> getSegments() {
		return segments;
	}

	public void setSegments(List<Integer> segments) {
		this.segments = segments;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		Campaign other = (Campaign) obj;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

}
