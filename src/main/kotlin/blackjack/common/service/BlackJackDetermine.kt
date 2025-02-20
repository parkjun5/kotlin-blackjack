package blackjack.common.service

import blackjack.bet.domain.BetPlayer
import blackjack.bet.domain.WinType
import blackjack.scorerule.domain.ScorePlayer

object BlackJackDetermine {

    private const val BLACK_JACK_NUMBER = 21

    fun determineWinType(player: BetPlayer, dealerValue: Int): WinType {
        val playerValue = player.optimalValue()

        return when {
            dealerValue.isDraw(player, playerValue) -> WinType.DRAW
            player.isInitialBlackjack() -> WinType.PLAYER_BLACK_JACK
            playerValue > BLACK_JACK_NUMBER -> WinType.DEALER_WIN
            dealerValue > BLACK_JACK_NUMBER -> WinType.DEALER_BUST
            playerValue > dealerValue -> WinType.PLAYER_WIN
            playerValue < dealerValue -> WinType.DEALER_WIN
            else -> WinType.DRAW
        }
    }

    private fun Int.isDraw(
        player: BetPlayer,
        playerValue: Int,
    ) = player.isInitialBlackjack() && this == BLACK_JACK_NUMBER ||
        this > BLACK_JACK_NUMBER && playerValue > BLACK_JACK_NUMBER ||
        this == BLACK_JACK_NUMBER && playerValue == BLACK_JACK_NUMBER

    fun determineWinType(player: ScorePlayer, dealerValue: Int): WinType {
        val playerValue = player.optimalValue()

        return when {
            playerValue > 21 -> WinType.DEALER_WIN
            dealerValue > 21 -> WinType.DEALER_BUST
            playerValue > dealerValue -> WinType.PLAYER_WIN
            playerValue < dealerValue -> WinType.DEALER_WIN
            else -> WinType.DRAW
        }
    }
}
