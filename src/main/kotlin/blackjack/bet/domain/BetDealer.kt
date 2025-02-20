package blackjack.bet.domain

import blackjack.bet.view.BetPlayerStatus
import blackjack.common.service.DeckManager

class BetDealer : BetPlayer(name = "딜러") {
    fun beginRound(manager: DeckManager, players: List<BetPlayer>) {
        this.hit(*manager.drawTwoCards())
        for (player in players) {
            player.hit(*manager.drawTwoCards())
        }
    }

    fun getAllStatus(players: List<BetPlayer>): List<BetPlayerStatus> {
        val betPlayerStatus = mutableListOf(BetPlayerStatus.of(this))
        for (player in players) {
            betPlayerStatus.add(BetPlayerStatus.of(player))
        }

        return betPlayerStatus
    }

    fun drawCardIfNeeded(deckManager: DeckManager, handNotice: (BetPlayer) -> Unit) {
        while (canDraw()) {
            drawPhase(deckManager = deckManager, handNotice = handNotice)
        }
    }

    override fun canDraw(): Boolean {
        return this.optimalValue() <= STAND_THRESHOLD
    }

    companion object {
        private const val STAND_THRESHOLD = 16
    }
}
