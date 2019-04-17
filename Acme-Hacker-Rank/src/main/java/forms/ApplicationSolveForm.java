
package forms;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import domain.Problem;

public class ApplicationSolveForm {

	private Problem	problem;
	private String	answerExplanation;
	private String	answerLink;


	@Valid
	@NotNull
	public Problem getProblem() {
		return this.problem;
	}

	public void setProblem(final Problem problem) {
		this.problem = problem;
	}

	@NotBlank
	public String getAnswerExplanation() {
		return this.answerExplanation;
	}

	public void setAnswerExplanation(final String answerExplanation) {
		this.answerExplanation = answerExplanation;
	}

	@NotBlank
	public String getAnswerLink() {
		return this.answerLink;
	}

	public void setAnswerLink(final String answerLink) {
		this.answerLink = answerLink;
	}
}
