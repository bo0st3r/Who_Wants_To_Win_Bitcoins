package view;

import exceptions.ExceedMaxStepsException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class ValidationGridPane extends GridPane {
	private Label lblValidation;
	private Button btnYes;
	private Button btnNo;

	/*
	 * Constructor. Sets rows and cols constraints, spacings and the pane content.
	 */
	public ValidationGridPane() {
		//setGridLinesVisible(true);
		this.setId("validationPane");
		// Set columns
		ColumnConstraints c = new ColumnConstraints();
		c.setPercentWidth(20);
		getColumnConstraints().addAll(c, c, c, c, c);

		// Set rows
		RowConstraints r = new RowConstraints();
		r.setPercentHeight(30);
		getRowConstraints().addAll(r, r);

		// Spacings
		setPadding(new Insets(10));
		setHgap(5);
		setVgap(5);

		// Content
		add(getLblValidation(), 2, 0, 5, 1);
		add(getBtnYes(), 0, 1, 2, 1);
		add(getBtnNo(), 3, 1, 2, 1);
	}

	/*
	 * If null instantiates lblValidation, sets it's ID and size, then returns it.
	 * 
	 * @return lblValidation, a Label object containing the validation question.
	 */
	public Label getLblValidation() {
		if (lblValidation == null) {
			lblValidation = new Label("No doubt?");
			lblValidation.setId("btnValidationText");
			lblValidation.setPrefHeight(Integer.MAX_VALUE);
			lblValidation.setPrefWidth(Integer.MAX_VALUE);
			GridPane.setHalignment(getLblValidation(), HPos.CENTER);
		}

		return lblValidation;
	}

	/*
	 * If null instantiates btnYes, sets it's ID, size and action when clicking on
	 * it, then returns it. It's action is to ask the PlayingGridPane to verify the
	 * answer picked by the user, to set invisible the validationGP, to enable the
	 * Cash-In button and the answers buttons.
	 * 
	 * @return btnYes, a Button object for the "Yes" choice.
	 */
	public Button getBtnYes() {
		if (btnYes == null) {
			btnYes = new Button("Sure");
			btnYes.setPrefHeight(Integer.MAX_VALUE);
			btnYes.setPrefWidth(Integer.MAX_VALUE);
			btnYes.setId("btnValidation");

			btnYes.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					PlayingGridPane pgp = (PlayingGridPane) getParent();

					// Verify the answer
					try {
						pgp.verifyAnswer();
					} catch (ExceedMaxStepsException e) {
						e.printStackTrace();
					}

					int answerIndex = pgp.getQuestionGP().getAnswerIndex();
					// Re-add the "button" class to the button
					pgp.getQuestionGP().getBtnAnswer(answerIndex).getStyleClass().add("button");
					// Set invisible the validation pane
					pgp.setVisibleValidationGP(false);
					// Enable btn "Cash in"
					pgp.getBtnCashIn().setDisable(false);
					// Enable answers buttons
					pgp.setDisableBtnAnswer(false);
				}
			});
		}

		return btnYes;
	}

	/*
	 * If null instantiates btnNo, sets it's ID, size and action when clicking on
	 * it, then returns it. It's action is to to set invisible the validationGP, to
	 * enable the Cash-In button and the answers buttons.
	 * 
	 * @return btnNo, a Button object for the "No" choice.
	 */
	public Button getBtnNo() {
		if (btnNo == null) {
			btnNo = new Button("Nope");
			btnNo.setPrefHeight(Integer.MAX_VALUE);
			btnNo.setPrefWidth(Integer.MAX_VALUE);
			btnNo.setId("btnValidation");

			btnNo.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					PlayingGridPane pgp = (PlayingGridPane) getParent();

					int answerIndex = pgp.getQuestionGP().getAnswerIndex();
					pgp.getQuestionGP().getBtnAnswer(answerIndex).setId("answerBtn");
					pgp.getQuestionGP().getBtnAnswer(answerIndex).getStyleClass().add("button");

					// Set validation grid pane invisible
					pgp.setVisibleValidationGP(false);
					// Enable btn "Cash in"
					pgp.getBtnCashIn().setDisable(false);
					// Enable answers buttons
					pgp.setDisableBtnAnswer(false);
				}
			});
		}

		return btnNo;
	}
}
