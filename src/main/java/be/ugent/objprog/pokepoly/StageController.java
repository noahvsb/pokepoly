package be.ugent.objprog.pokepoly;

import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class StageController {
    private List<Stage> stages;

    public StageController(Stage... stages) {
        this.stages = new ArrayList<>();
        this.stages.addAll(List.of(stages));
    }

    public void addStages(Stage... stages) {
        this.stages.addAll(List.of(stages));
    }

    public boolean closeStage(String title) {
        for (Stage stage : stages)
            if (stage.getTitle().equals(title)) {
                stage.close();
                return true;
            }
        return false;
    }
}
