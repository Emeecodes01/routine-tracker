import java.util.HashMap


class CardManager {
    private val timeLimit = 60 //60Mins, this can be java date and time lib

    //Getting or putting is a constant time operation,
    private val cardToNumOfUsageMap = HashMap<Card, Int>()//card to num of useage, in real world this can be a DB


    fun makeTransaction(card: Card) {
        if (!cardToNumOfUsageMap.containsKey(card)){
            cardToNumOfUsageMap[card] = 0
        }

        var numOfUseage = cardToNumOfUsageMap[card]
        if (numOfUseage != null) {
            print("Number Of Useage: $numOfUseage")
            if (numOfUseage < 3 && card.time <= timeLimit) {
                numOfUseage++
                cardToNumOfUsageMap[card] = numOfUseage
                print("\nTransaction made.....")
                print("$cardToNumOfUsageMap")
                return
            }
            print("\nTransaction failed......\n")
        }
    }
}

data class Card(val time: Int)


///Just RUN the main
fun main() {
    val cardManager = CardManager()
    val card1 = Card(40)
    val card2 = Card(70)

    cardManager.makeTransaction(card1)
    cardManager.makeTransaction(card1)
    cardManager.makeTransaction(card1)
    cardManager.makeTransaction(card1)
    cardManager.makeTransaction(card1)

    cardManager.makeTransaction(card2)
}