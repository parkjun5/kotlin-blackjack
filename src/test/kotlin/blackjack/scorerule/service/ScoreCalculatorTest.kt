package blackjack.scorerule.service

import blackjack.bet.domain.WinType
import blackjack.scorerule.domain.ScoreDealer
import blackjack.scorerule.domain.ScorePlayer
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class ScoreCalculatorTest : StringSpec({
    "딜러가 승리할 경우 승패가 잘 기록된다." {
        val dealer = ScoreDealer()
        val player = ScorePlayer("tester")

        ScoreCalculator.updateScores(WinType.DEALER_WIN, player, dealer)
        dealer.scoreBoard().win() shouldBe 1
        player.scoreBoard().lose() shouldBe 1
    }

    "플레이어가 승리할 경우 승패가 잘 기록된다." {
        val dealer = ScoreDealer()
        val player = ScorePlayer("tester")

        ScoreCalculator.updateScores(WinType.PLAYER_WIN, player, dealer)
        dealer.scoreBoard().lose() shouldBe 1
        player.scoreBoard().win() shouldBe 1
    }

    "무승부의 경우 무승부가 잘 기록된다." {
        val dealer = ScoreDealer()
        val player = ScorePlayer("tester")

        ScoreCalculator.updateScores(WinType.DRAW, player, dealer)
        dealer.scoreBoard().draw() shouldBe 1
        player.scoreBoard().draw() shouldBe 1
    }
})
