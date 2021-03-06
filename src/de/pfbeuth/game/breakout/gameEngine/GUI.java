package de.pfbeuth.game.breakout.gameEngine;
import de.pfbeuth.game.breakout.dataHandling.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * This class provides the graphical user interface and interaction methods.
 */

public class GUI {
    private Breakout breakout;
    private HBox  infoContainer,  highscoreContainer, helpContainer, startButtonContainer;
    private VBox masterButtonContainer;
    private Button playButton, helpButton, highscoreButton, startButton, confirmButton;
    private ImageView backgroundLayer, menueOverlay, playBackground;
    private Image playBackgroundImage, backgroundImage, helpImage, highscoreImage, gameOverImage;
    private Text levelInfo, lifeInfo, scoreInfo, highscoreList, highscoreListScore, helpText, gameOverInfo;
    private GridPane playerInputContainer;
    private CreatePlayer Player;
    private String playerName;
    private TextField nameInput;
	private Label nameLabel;
	private LoadXMLTable loader;

    /** ------ CONSTRUCTOR ------ */
    GUI(Breakout breakout){
        this.breakout = breakout ;
        loadImageAssets();
        createGUIImages();
        createInfoText();
        createButtons();
        createGUIContainer();
    }
    private void loadImageAssets(){
        backgroundImage = new Image("/assets/graphics/background.png",
						  Breakout.WIDTH, Breakout.HEIGHT, false, false, true);
        helpImage = new Image("/assets/graphics/help.png",
						  Breakout.WIDTH, Breakout.HEIGHT, true, false, true);
        highscoreImage = new Image("/assets/graphics/highscore.png",
					   	  Breakout.WIDTH, Breakout.HEIGHT, true, false, true);
        playBackgroundImage = new Image("/assets/graphics/background_play.png",
						  Breakout.WIDTH, Breakout.HEIGHT, false, false, true);
		gameOverImage = new Image("/assets/graphics/gameOver.png",
						  Breakout.WIDTH, Breakout.HEIGHT, true, false, true);
    }
    private void createGUIImages(){
        backgroundLayer = new ImageView(backgroundImage);
        menueOverlay = new ImageView(highscoreImage);
        playBackground = new ImageView(playBackgroundImage);
        playBackground.setVisible(false);
    }
    private void createGUIContainer() {
        Insets buttonContainerPadding = new Insets(0, 0, 12, 0);
        Insets topContainerPadding = new Insets(200, 0, 0, 0);

        masterButtonContainer = new VBox(12);
        masterButtonContainer.setAlignment(Pos.BOTTOM_LEFT);
		masterButtonContainer.setFocusTraversable(false);

		HBox buttonContainer = new HBox(12);
        buttonContainer.setAlignment(Pos.BOTTOM_CENTER);
        buttonContainer.setPadding(buttonContainerPadding);

		startButtonContainer = new HBox(12);
        startButtonContainer.setPrefHeight(Breakout.HEIGHT / 2);
        startButtonContainer.setAlignment(Pos.CENTER);
        startButtonContainer.setPadding(new Insets(0, 0, 200, 0));

        /* ------ Container for Gameinformation: Level, Lives and Score ------ */
        infoContainer = new HBox(12);
        infoContainer.setPrefHeight(Breakout.HEIGHT / 22);
        infoContainer.setAlignment(Pos.BOTTOM_CENTER);
        infoContainer.setPadding(buttonContainerPadding);

        HBox gameOverContainer = new HBox(12);
        gameOverContainer.setPrefHeight(Breakout.HEIGHT / 22);
        gameOverContainer.setAlignment(Pos.TOP_CENTER);
        gameOverContainer.setPadding(buttonContainerPadding);

        /* ------ Container for showing Highscore List ------ */
        highscoreContainer = new HBox(12);
        highscoreContainer.setPrefHeight(Breakout.HEIGHT / 50);
        highscoreContainer.setAlignment(Pos.TOP_CENTER);
        highscoreContainer.setPadding(topContainerPadding);

        helpContainer = new HBox(12);
		helpContainer.setPrefHeight(Breakout.HEIGHT / 50);
		helpContainer.setAlignment(Pos.TOP_CENTER);
		helpContainer.setTranslateY(-20);
		helpContainer.setPadding(topContainerPadding);

        /* ------ Label, input and button for the user name ------ */
        nameLabel = new Label(GuiText.getNAME_LABEL_TEXT());
        nameLabel.setTextFill(Color.WHITE);
        nameInput = new TextField();
        nameInput.setPromptText("Choose player name");

        /* ------ Container for the user name ------ */
        playerInputContainer = new GridPane();
        playerInputContainer.setTranslateY(-200);
		playerInputContainer.setVisible(false);
		playerInputContainer.setDisable(true);
		playerInputContainer.setAlignment(Pos.CENTER);
		playerInputContainer.setPadding(new Insets(0, 0, 0, 0));
		playerInputContainer.setVgap(10);
		playerInputContainer.setHgap(10);
		playerInputContainer.setConstraints(nameLabel, 0, 0);
		playerInputContainer.setConstraints(nameInput, 1, 0);
		playerInputContainer.setConstraints(confirmButton, 1, 1);

        /* ------ Add GUI Nodes to scene ------ */
        infoContainer.getChildren().addAll(levelInfo, lifeInfo, scoreInfo);
        helpContainer.getChildren().add(helpText);
        highscoreContainer.getChildren().addAll(highscoreList,highscoreListScore );
        buttonContainer.getChildren().addAll(playButton, highscoreButton, helpButton);
		playerInputContainer.getChildren().addAll(nameLabel, nameInput, confirmButton);
		startButtonContainer.getChildren().addAll(startButton);
		masterButtonContainer.getChildren().addAll(startButtonContainer, playerInputContainer, buttonContainer);
	}
    private void createInfoText(){
        levelInfo = new InfoText();
        levelInfo.setText(GuiText.getLEVEL_INFO_TEXT() + breakout.getLevel().getLevelNumber());
        lifeInfo = new InfoText();
        lifeInfo.setText(GuiText.getLIVES_INFO_TEXT() + breakout.getLife().getActualLife());
        scoreInfo = new InfoText();
		scoreInfo.setText(GuiText.getSCORE_INFO_TEXT() + breakout.getScoreCounter().getScoreNumber());
        highscoreList = new InfoText();
        highscoreList.setVisible(true);
		highscoreList.setFont(new Font("arial", 18));
		highscoreList.setWrappingWidth(180);
		highscoreList.setFill(Color.WHITE);
		highscoreList.setTextAlignment(TextAlignment.LEFT);
		highscoreListScore = new Text();
		highscoreListScore.setVisible(true);
		highscoreListScore.setFont(new Font("arial", 18));
		highscoreListScore.setWrappingWidth(70);
		highscoreListScore.setFill(Color.WHITE);
		highscoreListScore.setTextAlignment(TextAlignment.RIGHT);

        helpText = new Text();
        helpText.setText(GuiText.getINSTRUCTIONS_INFO_TEXT());
		helpText.setVisible(false);
		helpText.setFont(new Font("arial", 14));
		helpText.setWrappingWidth(450);
		helpText.setFill(Color.WHITE);
		helpText.setTextAlignment(TextAlignment.JUSTIFY);
		gameOverInfo = new InfoText();
    }
    private void createButtons(){
		startButton = new Button();
		startButton.setPrefSize(100, 100);
		startButton.setText(GuiText.getSTART_BUTTON_TEXT());
		startButton.setTextAlignment(TextAlignment.CENTER);
		startButtonVisibliy(false, 100d);
		startButton.setOnAction(e ->
			breakout.getGameStates().runGameEvents()
		);
		/* ------ Menue Buttons ------ */
		playButton = new Button();
		playButton.setFocusTraversable(false);
		playButton.setPrefWidth(120);
		playButton.setText(GuiText.getPLAY_BUTTON_TEXT());
		playButton.setOnAction(e -> {
			startButtonVisibliy(true, 100d);
			startButtonContainer.setVisible(true);
			startButtonContainer.setDisable(false);
			playerInputContainer.toBack();
			backgroundLayer.setVisible(false);
			menueOverlay.setVisible(false);
			menueOverlay.toBack();
			playBackground.setVisible(true);
			playBackground.toBack();
			highscoreList.setVisible(false);
			highscoreListScore.setVisible(false);
            helpText.setVisible(false);
			highscoreList.setVisible(false);
			highscoreListScore.setVisible(false);
			hideGameInfos();
		});
        highscoreButton = new Button();
        highscoreButton.setFocusTraversable(false);
		highscoreButton.setPrefWidth(120);
        highscoreButton.setText(GuiText.getHIGHSCORE_BUTTON_TEXT());
        highscoreButton.setOnAction(e -> {
            backgroundLayer.setVisible(true);
            menueOverlay.setVisible(true);
            menueOverlay.setImage(highscoreImage);
            startButtonVisibliy(false, 100d);
			highscoreList.setVisible(true);
			highscoreListScore.setVisible(true);
            helpText.setVisible(false);
            hideGameInfos();
            menueOverlay.toFront();
        });
        helpButton = new Button();
        helpButton.setFocusTraversable(false);
		helpButton.setPrefWidth(120);
        helpButton.setText(GuiText.getHELP_BUTTON_TEXT());
        helpButton.setOnAction(e -> {
            backgroundLayer.setVisible(true);
            menueOverlay.setVisible(true);
            startButtonVisibliy(false, 100d);
			highscoreList.setVisible(false);
			highscoreListScore.setVisible(false);
			helpText.setVisible(true);
			hideGameInfos();
			menueOverlay.setImage(helpImage);
			menueOverlay.toFront();
		});
         /* ------ Confirm-button with actions ------ */
		confirmButton = new Button();
		confirmButton.setText(GuiText.getCONFIRM_BUTTON_TEXT());
		confirmButtonVisibliy(false);
		confirmButton.setOnAction(e ->
			confirmButtonEvents()
		);
    }
	/** Displays game info texts */
	void showGameInfos(){
		levelInfo.setVisible(true);
		lifeInfo.setVisible(true);
		scoreInfo.setVisible(true);
	}
	/** Hides game info texts */
	void hideGameInfos(){
		levelInfo.setVisible(false);
		lifeInfo.setVisible(false);
		scoreInfo.setVisible(false);
		playerInputContainer.setVisible(false);
		playerInputContainer.setDisable(true);
	}
	/** Updates score info text through property binding */
	public void updateScoreInfo(){
		scoreInfo.textProperty().bind(breakout.getScoreCounter().scoreProperty());
	}
	/** creates Highscore output */
	void createHighScoreScreen() {
		loader = new LoadXMLTable();
		loader.loadTable();
		loader.displayHighscore();

		if (loader.getHighscoreList().size() != 0) {
			StringBuffer playerbuff = new StringBuffer("PLAYER "+ "\n");
			StringBuffer scorebuff = new StringBuffer("SCORE"+ "\n");

			for (int i = 0; i < loader.getHighscoreList().size() && i < 10; i++) {
				loader.getHighscoreList().get(i);
				playerbuff.append((i + 1) + ". " + "\t"+ loader.getHighscoreList().get(i).getPlayerName()+ "\n");
				scorebuff.append(loader.getHighscoreList().get(i).getPlayerScore() + "\n");

			}
			highscoreList.setText(playerbuff.toString());
			highscoreListScore.setText(scorebuff.toString());
		}

		else{
				highscoreList.setText("You're the first Player. Set a new Highscore");
		}
	}
	/** Triggers confirm button events */
	public void confirmButtonEvents() {
		if ((nameInput.getText() != null && !nameInput.getText().isEmpty())) {
			nameLabel.setText(GuiText.getNAME_LABEL_TEXT());
			playerName = nameInput.getText();
			Player = new CreatePlayer(breakout, playerName);

			UpdateXMLTable updater = new UpdateXMLTable();
			updater.add(Player);
			loader.loadTable();
			loader.displayHighscore();
			createHighScoreScreen();

			backgroundLayer.setVisible(true);
			backgroundLayer.setImage(backgroundImage);
			menueOverlay.setVisible(true);
			menueOverlay.setImage(highscoreImage);

			highscoreList.setVisible(true);
			highscoreListScore.setVisible(true);
			helpText.setVisible(false);
			hideGameInfos();

			playButtonVisibliy(true);
			highscoreButtonVisibliy(true);
			helpButtonVisibliy(true);
			confirmButtonVisibliy(false);
			gameOverInfo.setVisible(false);

			startButtonVisibliy(false, 100d);
			startButtonContainer.setVisible(true);
			startButtonContainer.setDisable(false);

			breakout.getBall().setBallToBack();
			breakout.getBrickGrid().setBricksToBack();

			breakout.getGameStates().startNextLevelEvents();

			backgroundLayer.toFront();
			menueOverlay.toFront();
			highscoreContainer.toFront();
			helpContainer.toFront();
			masterButtonContainer.toFront();
		}
		else {
			nameLabel.setText("PLEASE ENTER\nYOUR NAME!");
		}
	}
	/** sets Play Button visible and enabled without focus consume */
	void playButtonVisibliy(Boolean visibility){
		playButton.setVisible(visibility);
		playButton.setDisable(!visibility);
		playButton.setFocusTraversable(!visibility);
	}
	/** sets Highscore Button visible and enabled without focus consume */
	void highscoreButtonVisibliy(Boolean visibility){
		highscoreButton.setVisible(visibility);
		highscoreButton.setDisable(!visibility);
		highscoreButton.setFocusTraversable(!visibility);
	}
	/** sets Help Button visible and enabled without focus consume */
	void helpButtonVisibliy(Boolean visibility){
		helpButton.setVisible(visibility);
		helpButton.setDisable(!visibility);
		helpButton.setFocusTraversable(!visibility);
	}
	/** sets Start Button visible and enabled without focus consume */
	void startButtonVisibliy(Boolean visibility, Double prefWidth){
		startButton.setCancelButton(false);
		startButton.setPrefWidth(prefWidth);
		startButton.setVisible(visibility);
		startButton.setDisable(!visibility);
		startButton.setFocusTraversable(!visibility);
	}
	/** sets Confirm Button visible and enabled without focus consume */
	void confirmButtonVisibliy(Boolean visibility){
		confirmButton.setVisible(visibility);
		confirmButton.setDisable(!visibility);
		confirmButton.setFocusTraversable(!visibility);
	}

    /* ------ GETTER ------ */
	/** @return startButton object */
    public Button getStartButton() {
        return startButton;
    }
	/** @return confirmButton object */
	public Button getConfirmButton() {
		return confirmButton;
	}
	/** @return lifeInfo object */
	public Text getLifeInfo() {
        return lifeInfo;
    }
	/** @return levelInfo object */
	Text getLevelInfo(){
        return levelInfo;
    }
	/** @return scoreInfo object */
	Text getScoreInfo(){
    	return scoreInfo;
	}
	/** @return gameOverInfo object */
	Text getGameOverInfo() {
		return gameOverInfo;
	}
	/** @return backgroundLayer object */
	ImageView getBackgroundLayer() {
        return backgroundLayer;
    }
	/** @return playBackground object */
	ImageView getPlayBackground() {
        return playBackground;
    }
	/** @return menueOverlay object */
	ImageView getMenueOverlay() {
		return menueOverlay;
	}
	/** @return gameOverImage object */
	Image getGameOverImage() {
		return gameOverImage;
	}
	/** @return startButtonContainer object */
	HBox getStartButtonContainer() {
		return startButtonContainer;
	}
	/** @return InfoContainer object */
	HBox getInfoContainer() {
        return infoContainer;
    }
	/** @return highscoreContainer object */
	HBox getHighscoreContainer() {
        return highscoreContainer;
    }
	/** @return helpContainer object */
	HBox getHelpContainer() {
        return helpContainer;
    }
	/** @return playerInputContainer object */
	GridPane getPlayerInputContainer() {
        return playerInputContainer;
    }
	/** @return masterButtonContainer object */
	VBox getMasterButtonContainer() {
        return masterButtonContainer;
    }
	/** @return START_BUTTON_TEXT constant */
	String getStartText() {
        return GuiText.getSTART_BUTTON_TEXT();
    }
	/** @return PAUSE_GAME_TEXT constant */
	String getPauseGameText() {
        return GuiText.getPAUSE_GAME_TEXT();
    }
	/** @return LEVEL_INFO_TEXT constant */
	String getLEVEL_INFO_TEXT() {
		return GuiText.getLEVEL_INFO_TEXT();
	}
	/** @return LIVES_INFO_TEXT constant */
	public String getLIVES_INFO_TEXT(){
    	return GuiText.getLIVES_INFO_TEXT();
	}
	/** @return SCORE_INFO_TEXT constant */
	public String getSCORE_INFO_TEXT(){
    	return GuiText.getSCORE_INFO_TEXT();
	}
	/** @return NEXT_LEVEL_TEXT constant */
	String getNEXT_LEVEL_TEXT(){
		return GuiText.getNEXT_LEVEL_TEXT();
	}
}
