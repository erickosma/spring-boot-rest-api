package com.spring.jpa.h2.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TutorialVO {

	private String title;

	private String description;

	private boolean published;

	@Override
	public String toString() {
		return "Tutorial [title=" + title + ", desc=" + description + ", published=" + published + "]";
	}
}
