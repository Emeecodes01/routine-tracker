import java.util.HashMap

class CardManager {
    val timeLimit = 60 //60Mins
    //Getting or putting is a constant time operation,
    val cardToNumOfUsageMap = HashMap<Card, Int>()//card to num of useage


    fun makeTransaction(card: Card) {
        if (!cardToNumOfUsageMap.containsKey(card)){
            cardToNumOfUsageMap[card] = 0
        }

        var numOfUseage = cardToNumOfUsageMap[card]
        if (numOfUseage != null) {
            print("Number Of Useage: $numOfUseage")
            if (numOfUseage < 3 && card.time <= 60) {
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