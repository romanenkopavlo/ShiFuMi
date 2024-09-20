package org.example.ppcjeuxgraphique;

import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public ImageView imageYou;
    public ImageView imageComputer;
    public Button buttonStart;
    public Label winnerLabel;
    public Button tryAgainButton;
    public AnchorPane anchorPane;
    public RadioButton paperChoice;
    public RadioButton rockChoice;
    public RadioButton scissorsChoice;
    public Label humanWinsLabel;
    public Label computerWinsLabel;
    Image rock = new Image(getClass().getResource("/org/example/ppcjeuxgraphique/images/OIP-removebg-preview.png").toExternalForm());
    Image paper = new Image(getClass().getResource("/org/example/ppcjeuxgraphique/images/paper-sheet-png-transparent-photo.png").toExternalForm());
    Image scissors = new Image(getClass().getResource("/org/example/ppcjeuxgraphique/images/transparent-scissors-png-clipart-picture-5a1c385e54f646.586575351511798878348-removebg-preview.png").toExternalForm());
    Image questionMark = new Image(getClass().getResource("/org/example/ppcjeuxgraphique/images/pngtree-vector-question-mark-icon-png-image_1834249-removebg-preview.png").toExternalForm());
    Random random = new Random();
    boolean stop = false;
    int computerWins;
    int computerWinsBuffer;
    int humanWins;
    int humanWinsBuffer;
    int totalWins;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        imageYou.setImage(questionMark);
        imageYou.setFitHeight(200);
        imageYou.setFitWidth(200);

        imageComputer.setImage(questionMark);
        imageComputer.setFitHeight(200);
        imageComputer.setFitWidth(200);
        imageComputer.setX(30);

        buttonStart.setDisable(true);
        tryAgainButton.setVisible(false);

        paperChoice.setOnMouseClicked(event -> {
            buttonStart.setDisable(false);
            imageYou.setImage(paper);
        });

        scissorsChoice.setOnMouseClicked(event -> {
            buttonStart.setDisable(false);
            imageYou.setImage(scissors);
        });

        rockChoice.setOnMouseClicked(event -> {
            buttonStart.setDisable(false);
            imageYou.setImage(rock);
        });

        buttonStart.setOnMouseClicked(event -> {
            try {
                stop = false;
                startGame();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        tryAgainButton.setOnMouseClicked(event -> {
            imageComputer.setImage(questionMark);
            winnerLabel.setText("");
            paperChoice.setDisable(false);
            rockChoice.setDisable(false);
            scissorsChoice.setDisable(false);
            tryAgainButton.setVisible(false);
            if (paperChoice.isSelected() || rockChoice.isSelected() || scissorsChoice.isSelected()) {
                buttonStart.setDisable(false);
            }
        });
    }

    public void startGame() throws InterruptedException {
        paperChoice.setDisable(true);
        rockChoice.setDisable(true);
        scissorsChoice.setDisable(true);
        buttonStart.setDisable(true);
        new Thread(this::randomImageChange).start();
        new Thread(this::calculate3Seconds).start();
    }

    public void randomImageChange() {
        try {
            while (!stop) {
                Platform.runLater(() -> {
                    int randomNumber = random.nextInt(3);
                    switch (randomNumber) {
                        case 0:
                            imageComputer.setImage(paper);
                            break;
                        case 1:
                            imageComputer.setImage(rock);
                            break;
                        case 2:
                            imageComputer.setImage(scissors);
                            break;
                    }
                });
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void calculate3Seconds() {
        try {
            for (int i = 4; i > 0; i--) {
                {
                    int secondsLeft = i - 1;
                    Platform.runLater(() -> winnerLabel.setText("Results in " + secondsLeft));
                    if (i == 1) {
                        stop = true;

                        tryAgainButton.setVisible(true);
                        tryAgainButton.setDisable(false);

                        if (computerWins < 7 && humanWins < 3) {
                            if (imageComputer.getImage() == imageYou.getImage()) {
                                Platform.runLater(() -> winnerLabel.setText("Draw!"));
                            } else if ((imageComputer.getImage() == rock && imageYou.getImage() == scissors)
                                    || (imageComputer.getImage() == paper && imageYou.getImage() == rock)
                                    || (imageComputer.getImage() == scissors && imageYou.getImage() == paper)) {
                                displayComputerWinner();
                            } else {
                                displayHumanWinner();
                            }
                        } else if (humanWins >= 3) {
                            setWinner(paper, rock, scissors);
                            displayComputerWinner();
                        } else {
                            setWinner(scissors, paper, rock);
                            displayHumanWinner();
                        }

                        totalWins = computerWins + humanWins;

                        if (totalWins == 10) {
                            computerWins = 0;
                            humanWins = 0;
                        }
                    }
                    Thread.sleep(1000);
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public void setWinner(Image one, Image two, Image three) {
        if (imageYou.getImage() == rock) {
            imageComputer.setImage(one);
        } else if (imageYou.getImage() == scissors) {
            imageComputer.setImage(two);
        } else if (imageYou.getImage() == paper) {
            imageComputer.setImage(three);
        }
    }

    public void displayComputerWinner () {
        Platform.runLater(() -> winnerLabel.setText("Computer won!"));
        computerWins++;
        computerWinsBuffer++;
        Platform.runLater(() -> computerWinsLabel.setText(Integer.toString(computerWinsBuffer)));
    }

    public void displayHumanWinner () {
        Platform.runLater(() -> winnerLabel.setText("You won!"));
        humanWins++;
        humanWinsBuffer++;
        Platform.runLater(() -> humanWinsLabel.setText(Integer.toString(humanWinsBuffer)));
    }
}