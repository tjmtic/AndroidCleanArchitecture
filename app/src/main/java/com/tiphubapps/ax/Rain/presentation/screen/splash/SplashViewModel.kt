package com.tiphubapps.ax.Rain.presentation.screen.details

import androidx.lifecycle.*
import com.tiphubapps.ax.data.util.CoroutineContextProvider
import com.tiphubapps.ax.domain.repository.UseCaseResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import com.tiphubapps.ax.domain.useCase.SplashUseCases
import com.tiphubapps.ax.Rain.presentation.screen.splash.SplashState
import com.tiphubapps.ax.Rain.presentation.screen.splash.SplashStateEvent
import com.tiphubapps.ax.Rain.presentation.screen.splash.SplashUiEvent
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.lang.Integer.max
import java.lang.Integer.min

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val splashUseCases: SplashUseCases,
    coroutineContextProvider: CoroutineContextProvider
) : ViewModel() {

    /////////Default ViewModel Setup///////////
    private val _state = MutableStateFlow(SplashState())
    val state : StateFlow<SplashState> = _state

    private val _eventBus = Channel<SplashUiEvent>()
    val eventBus = _eventBus.receiveAsFlow()

    var job : Job?
    ///////////////////////////////////////////////

    //TODO: implement injection of this?
    // how to standardize onEvent responses?
    // also, standardized onEvent a good thing?
    // MAYBE just add to Default setup ^^ above, good to handle in individual viewModels.
    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        //val errorMessage = throwable.message ?: "An error occurred"
        viewModelScope.launch {
            onEvent(ERROR_A)
            //TODO: should probably be fatal error here, close and re-open the app
            // standardized responses are probably not the best approach
            // default? for logging?
        }
    }

    init {
        println("TIME123 SplashViewModel Init Start")
        //TODO:
        // Init Firebase - mainActivity?
        // Init Analytics/Logging?
        // Init Background Services
        // Init Websocket
        // Init UserData -- complete
        // awaitAll()

        val li = searchRange(intArrayOf(2,2), 1)

        println("DECODING STREING VALUE: $li")

        job = viewModelScope.launch(
            coroutineContextProvider.io + coroutineExceptionHandler, CoroutineStart.DEFAULT
        ) {

            //repeatOnLifecycle(){}
            try {
                onEvent(LOADING_INIT)

                when (splashUseCases.useCaseAuthGetToken()) {
                    is UseCaseResult.UseCaseSuccess -> {
                        onEvent(TOKEN_LOADED)
                        initUserData()
                    }
                    else -> {
                        onEvent(LOGIN_FAIL)
                    }
                }

                onEvent(LOADING_STOP)

            } catch (e: Exception) {
                onEvent(ERROR_0)
                return@launch
            }
        }
        println("TIME123 SplashViewModel Init End")
    }

    private suspend fun initUserData(){
        when(splashUseCases.useCaseUserGetCurrentUser()){
            is UseCaseResult.UseCaseSuccess -> { onEvent(LOGIN_SUCCESS) }
            is UseCaseResult.Loading -> Unit
            else -> { onEvent(ERROR_1) }
        }
    }

    override fun onCleared() {
        //TODO: How to test?
        viewModelScope.cancel()

        //TODO: Also, is this redundant call after viewModelScope?
        job?.cancel()
    }

    /////////////////////ViewModel Events/////////////////////////
    //Update STATE -> Send EVENT
    private suspend fun onEvent(event: SplashStateEvent) {
        when (event) {
            is SplashStateEvent.LoadingStarted -> {
                //TODO: Invert these call types?
                // EVENT = LOADING  (include state mutation / reducer pattern?)
                // STATE = SPLASHING
                // ...
                _state.value = _state.value.copy(isLoading = true)
                _eventBus.send(SplashUiEvent.SPLASHING)
            }
            is SplashStateEvent.LoadingFinished -> {
                _state.value = _state.value.copy(isLoading = false)
                _eventBus.send(SplashUiEvent.SPLASHED)
            }
            is SplashStateEvent.LoadingError -> {
                _state.value = _state.value.copy(isLoading = false, error = event.message)
                _eventBus.send(SplashUiEvent.Error(msg = event.message))
            }
            is SplashStateEvent.TokenLoaded -> {
                //_state.value = _state.value.copy(isLoggedIn = true)
                println("Event Called -- Token Loaded from AuthRepo")
            }
            is SplashStateEvent.LoginFinished -> {
                _state.value = _state.value.copy(isLoggedIn = true)
            }
            is SplashStateEvent.LoginFailed -> {
                _state.value = _state.value.copy(isLoggedIn = false)
            }
            else -> { println("Unknown Event Called") }
        }
    }
    ////////////////////////////////////////////////////////////


    //Enumerated Events (i.e. Event Implementation)
    companion object {
        //Loading Status (... Can We Use a Loading Delegate?)
        val LOADING_INIT = SplashStateEvent.LoadingStarted
        val LOADING_STOP = SplashStateEvent.LoadingFinished
        //Action Steps
        val TOKEN_LOADED = SplashStateEvent.TokenLoaded
        val LOGIN_SUCCESS = SplashStateEvent.LoginFinished
        val LOGIN_FAIL = SplashStateEvent.LoginFailed
        //Error Responses
        val ERROR_A = SplashStateEvent.LoadingError("Error A")
        val ERROR_0 = SplashStateEvent.LoadingError("Error 0")
        val ERROR_1 = SplashStateEvent.LoadingError("Error 1")
    }

    class ListNode(var value: Int) {
        var next: ListNode? = null
    }

    fun searchRange(nums: IntArray, target: Int): IntArray {

        if (nums.isEmpty()) return intArrayOf(-1, -1)

        var left = 0
        var right = nums.size-1

        var finished = false

        while(!finished){
            val middle = left + (right-left)/2

            println("$left $right $middle")

            if(right <= left && nums[middle] != target){
                return intArrayOf(-1,-1)
            }

            if(nums[middle] > target){
                right = middle - 1
            }

            else if (nums[middle] < target){
                left = middle + 1
            }

            else if(nums[middle] == target){
                var shifted = false
                if(nums[left] < target){
                    left++
                    shifted = true
                }
                if(nums[right] > target){
                    right--
                    shifted = true
                }
                if(!shifted){
                    finished = true
                }

            }

        }

        return intArrayOf(left,right)

    }

    fun divide(dividend: Int, divisor: Int): Int {
        var count = 0
        var total : Long = 0

        val absVal1 = Math.abs(dividend.toLong()).toLong()
        val absVal2 = Math.abs(divisor.toLong()).toLong()

        while((total+absVal2) <= absVal1){
            total += absVal2
            count++
            //println("COunting.... $count")
        }
        println("Printing Divide: $total $count $absVal1 $absVal2 $dividend $divisor")
        if(absVal1 > dividend.toLong()){
            if(absVal2 > divisor.toLong()){
                println("return divide 1 $count")
                return count
            }
            println("return divide 2")

            return -count
        }

        if(absVal2 > divisor.toLong()){
            println("return divide 3")

            return -count
        }

        println("return divide 4")


        return count
    }

    fun lengthOfLongestSubstring(s: String): Int {
            var maxString = ""
            var curString = ""

            val chars = s.toCharArray()

            for(i in chars.indices){
                if(!curString.contains(chars[i])){
                    curString += chars[i]
                    if(curString.length > maxString.length) maxString = curString
                }
                else{
                    curString = curString.substring(curString.indexOfFirst { it == chars[i] } + 1) + chars[i]
                }
            }

        return maxString.length
    }

    fun convert(s: String, numRows: Int): String {
        if (numRows == 1 || numRows >= s.length) {
            return s
        }

        val rows = Array<String?>(minOf(numRows, s.length)) { null }
        var currentRow = 0
        var goingDown = false

        for (char in s) {
            if (rows[currentRow] == null) {
                rows[currentRow] = char.toString()
            } else {
                rows[currentRow] = rows[currentRow] + char
            }
            if (currentRow == 0 || currentRow == numRows - 1) {
                goingDown = !goingDown
            }
            currentRow += if (goingDown) 1 else -1
        }

        val result = StringBuilder()
        for (row in rows) {
            row?.let { result.append(it) }
        }

        return result.toString()
    }

    fun longestPalindrome(s:String):String{
        var maxString = ""
        var chars = s.toCharArray()
        var charMap = HashMap<Char, IntArray>()

        for(i in chars.indices){
            //check
            charMap.get(chars.get(i))?.let{
                it.map{
                    val checkString = s.substring(it, i+1)

                    if(checkString == checkString.reversed()){
                        if(checkString.length > maxString.length){
                            maxString = checkString
                        }
                    }
                }
                charMap.set(chars.get(i), charMap.get(chars.get(i))!! + intArrayOf(i))
            } ?: run {
                charMap.set(chars.get(i), intArrayOf(i))
                if(maxString.length == 0){
                    maxString = chars.get(i).toString()
                }
            }
        }

        return maxString
    }


    //fun twoSum(nums: IntArray, target: Int): IntArray {

        //store needed value for each item

        //check if any new item value as a stored index associated (HashMap (key:itemValue, value:index))

    //O(n) instead of O(n^2) with for-i-for-j search

    //}

    fun addTwoNumbers(l1: ListNode?, l2: ListNode?): ListNode? {

        val l1val = getNum(l1)
        val l2val = getNum(l2)

        val totalVal = l1val.toInt() + l2val.toInt()

        return toListNode(totalVal.toString().reversed())

    }

    fun getNum(li: ListNode?): String {
        return if (li == null){
            ""
        }
        else
            li.value.toString() + getNum(li.next)

    }

    fun toListNode(str:String): ListNode? {

        return if(str.isBlank()){
            null
        }

        else {
            ListNode(str.toCharArray()[0].code).apply{
                next = toListNode(str.substring(1))
            }
        }
    }

    val colors = intArrayOf(2, 0, 2, 1, 1, 0).sortedBy { it }

    fun decodeString(str: String): Int{
        var countNum = 0

        if(str.length > 1){
            countNum += decodeString(str.removeRange(0,1))
        }

        if(str.take(2).toInt() in 1..26){
            countNum++
        }

        return countNum
    }

    fun makeAnagram(input: List<String>): List<List<String>>{
       // return input.map{ it.toCharArray().sorted().toString() }.groupBy{ it }
        return input.groupBy {it.toCharArray().sorted().toString() }.values.toList()
    }
    fun sortedListnodes(node1:ListNode, node2:ListNode):ListNode {
        val initial = if (node1.value <= node2.value) node1 else node2


        /*if(initial.next == null){
            if(node1.value <= node2.value){
                initial.next = node2
            }
            else{
                //node1.next = node2.next
                node2.next = node1
            }
        }

        else{
            initial.next = sortedListnodes(node)
        }*/
        val initial2 = sortNodesRecur(node1, node2)

        return initial2
    }

    fun sortNodesRecur(node1:ListNode, node2:ListNode):ListNode{

        return if(node1.value <= node2.value) {
            ListNode(node1.value).apply {
                next = node1.next?.let { sortNodesRecur(it, node2) } ?: node2
            }
        }

        else {
            ListNode(node2.value).apply {
                next = node2.next?.let { sortNodesRecur(it, node1) } ?: node1
            }
        }
    }

    fun subarraySum(nums: IntArray, k: Int): Int {
        // Implement your code here using higher-order functions and scope functions
        var total = 0

        nums.mapIndexed{k,v ->
            val numb = nums.sliceArray(k until nums.size)
            if(k == numb.fold(0){acc, cur -> if (acc == k){ acc } else { acc + cur }}){
                total++
            }
        }

        return total
    }

    fun merge(intervals: List<IntArray>): List<IntArray> {

        println("MERGING START!!!!!!!!!!!")
        var returnList = emptyList<IntArray>()
        val res = mutableListOf<IntArray>()

        val sortedInterval = intervals.sortedBy { it[0] }

        for (interval in sortedInterval){
            if(returnList.isEmpty()){
                returnList = returnList.plus(interval)
                res.add(interval)
            }
            else if(interval[0] > returnList.last()[1]){
                returnList = returnList.plus(interval)
                res.add(interval)
            }
            else{
                returnList.last()[1] = max(interval[1], returnList.last()[0])
                res.last()[1] = maxOf(interval[1], res.last()[0])
            }
        }

        println("MERGING END!!!!!!!!!!! $res")


        return res
    }



    //////////////////////////////
    //val bbcArticles = scrapeArticlesFromBBCNews()

    /* bbcArticles.forEachIndexed { index, article ->
         println("Article ${index + 1}:")
         println("Title: ${article.title}")
         println("Description: ${article.description}")
         println("Link: ${article.link}")
         println("header: ${article.header}")
         println("image: ${article.image}")
         println("par: ${article.desc}")

         println()
     }*/
////////////////////////////////////


    //   }
    /////////////////////////////////////////////////////////////////////////////////
    //}
    //    println("TIME123 SplashViewModel Init End")
    //}

    data class Article(
        val title: String,
        val description: String,
        val link: String,
        val header: String,
        val image: String,
        val desc: String
    )


    fun scrapeArticlesFromBBCNews(): List<Article> {
        val url = "https://www.bbc.com/news"
        val articles = mutableListOf<Article>()

        val fullArticles = mutableListOf<Article>()

        try {
            val doc: Document = Jsoup.connect(url).get()
            val elements = doc.select("div.gs-c-promo")

            for (element in elements) {
                val title = element.select("h3.gs-c-promo-heading").text()
                val description = element.select("p.gs-c-promo-summary").text()
                val link = element.select("a.gs-c-promo-heading").attr("href")

                val fullLink = if(link.contains("www")) link else "https://www.bbc.com$link"

                val doc2: Document = Jsoup.connect(fullLink).get()
                val elements2 = doc2.select("#main-content")

                val header = elements2.select("#main-heading").text()
                val image = elements2.select("picture source").attr("srcset")

                val desc = elements2.select("p b").joinToString { it.text() }

                val article = Article(title, description, fullLink, header, image, desc)
                articles.add(article)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return articles
    }
}