package Controllers_OutDated;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.time.LocalTime;

public class GUIOrderController {

    @FXML
    private Label timeLabel;

    @FXML
    private Timeline timeline;

    public void initialize() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            updateTime();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void updateTime() {
        // Update the timeLabel with the current time
        timeLabel.setText(LocalTime.now().toString());
    }

}
