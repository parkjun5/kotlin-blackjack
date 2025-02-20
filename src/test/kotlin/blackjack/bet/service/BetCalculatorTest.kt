package blackjack.bet.service

import blackjack.bet.domain.BetDealer
import blackjack.bet.domain.BetPlayer
import blackjack.bet.domain.WinType
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class
BetCalculatorTest : StringSpec({
    "딜러가 승리할 경우 딜러가 배팅 금액을 가진다" {
        val dealer = BetDealer()
        val player = BetPlayer("tester")
        player.charge(50_000)

        BetCalculator.settleBet(WinType.DEALER_WIN, player, dealer)
        dealer.wallet().bettingAmount() shouldBe 50_000
        dealer.wallet().income() shouldBe 50_000
        player.wallet().bettingAmount() shouldBe 0
        player.wallet().income() shouldBe -50_000
    }

    "플레이어가 승리할 경우 플레이어에게 배팅 금액을 가진다" {
        val dealer = BetDealer()
        val player = BetPlayer("tester")
        player.charge(50_000)

        BetCalculator.settleBet(WinType.PLAYER_WIN, player, dealer)
        dealer.wallet().bettingAmount() shouldBe -50_000
        dealer.wallet().income() shouldBe -50_000
        player.wallet().bettingAmount() shouldBe 100_000
        player.wallet().income() shouldBe 50_000
    }

    "딜러가 Bust 된다면(패가 21 초과) 그 시점 까지 남아있던 플레이어만 승리한다." {
        val dealer = BetDealer()
        val blackJacked = BetPlayer("tester")

        blackJacked.charge(10_000)

        BetCalculator.settleBet(WinType.DEALER_BUST, blackJacked, dealer)
        dealer.wallet().bettingAmount() shouldBe -10_000
        dealer.wallet().income() shouldBe -10_000
        blackJacked.wallet().bettingAmount() shouldBe 20_000
        blackJacked.wallet().income() shouldBe 10_000
    }

    "플레이어가 처음 뽑은 2자리의 값이 블랙잭(21)이라면 1.5배를 돌려받는다." {
        val dealer = BetDealer()
        val player = BetPlayer("tester")
        player.charge(10_000)

        BetCalculator.settleBet(WinType.PLAYER_BLACK_JACK, player, dealer)
        dealer.wallet().bettingAmount() shouldBe -5_000
        dealer.wallet().income() shouldBe -5_000
        player.wallet().bettingAmount() shouldBe 15_000
        player.wallet().income() shouldBe 5_000
    }
})
