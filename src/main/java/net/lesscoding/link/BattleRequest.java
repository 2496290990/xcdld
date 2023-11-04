package net.lesscoding.link;

import lombok.*;
import net.lesscoding.model.dto.CurrentBattleProcess;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class BattleRequest {

    private CurrentBattleProcess currentBattleProcess;

    public CurrentBattleProcess getCurrentBattleProcess() {
        return currentBattleProcess;
    }

    public void setCurrentBattleProcess(CurrentBattleProcess currentBattleProcess) {
        this.currentBattleProcess = currentBattleProcess;
    }
}
