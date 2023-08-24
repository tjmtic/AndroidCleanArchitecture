package com.tiphubapps.ax.rain.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import java.util.Locale
import kotlin.math.absoluteValue

class practiceTest {




    ///////TSTING

    data class City(val name: String, val country: String)

    data class Employee(val name: String, val department: String, val salary: Double)

    data class Product(val name: String, val price: Double, val discountPercentage: Double)


    fun getUniqueCityNames(cities: List<City>): Set<String> {
        return cities.groupBy { it.name }.keys
    }

    fun filterEvenNumbers(numbers: List<Int>): List<Int> {
        // Implement your code here to filter even numbers
        return numbers.filter{ it % 2 == 0 }
    }

    fun findMaxValue(numbers: List<Int>): Int {
        // Implement your code here to find the maximum value
        return numbers.maxOrNull() ?: -1
        //return numbers.minOrNull() ?: -1
    }

    fun generateFibonacciSeries(terms: Int): List<Int> {
        // Implement your code here to generate the Fibonacci series
        return when(terms){
            0 -> emptyList()
            1 -> listOf(0)
            else -> {
                val fibonacciSeries = mutableListOf(0, 1)

                for (i in 2 until terms) {
                    val nextFibonacci = fibonacciSeries[i - 1] + fibonacciSeries[i - 2]
                    fibonacciSeries.add(nextFibonacci)
                }

                fibonacciSeries
            }
        }
    }

    fun capitalizeWords(sentence: String): String {
        return sentence.split(" ").joinToString(" ") { it.capitalize() }
        //return sentence.split(" ").map { it.capitalize() }
    }

    fun countWords(sentence: String): Map<String, Int> {
        // Implement your code here to count words
        return sentence.split(" ").groupingBy{ it }.eachCount()
    }

    fun sumEvenNumbers(numbers: List<Int>): Int {
        // Implement your code here to calculate the sum of even numbers
        //FOR SUMs
        //return numbers.filter { it % 2 == 0 }.sum()

        // return numbers.reduce{ it % 2 == 0 ? it }
        return numbers.filter{ it % 2 == 0 }.reduce{accumulatedValue, newValue -> accumulatedValue+newValue}
    }

    fun removeDuplicates(numbers: List<Int>): List<Int> {
        return numbers.distinct()
    }

    fun calculateAverage(numbers: List<Double>): Double {
        return numbers.run { sum() / size }
    }

    fun stringLengths(strings: List<String>): List<Int> {
        // Implement your code here to get string lengths using higher-order functions
        return strings.map{ it.length }
    }

    fun findMaxLengthString(strings: List<String>): String {
        //return strings.maxByOrNull { it.length } ?: ""

        // Implement your code here to find the string with maximum length using higher-order functions
        return strings.fold(strings[0]){current, new -> if (current.length > new.length) current else new }
    }

    fun countCharacters(strings: List<String>): Int {
        //return strings.sumBy { it.length }

        // Implement your code here to count characters using higher-order functions
        //return strings.map{it.length}.reduce{acc, num -> acc+num}
        return strings.map{it.length}.sum()
    }

    fun removeShortStrings(strings: List<String>, threshold: Int): List<String> {
        // Implement your code here to remove short strings using higher-order functions

        return strings.filter{ it.length > threshold }
    }

    fun calculateFactorials(numbers: List<Int>): List<Long> {
        //fun factorial(n: Int): Long = if (n == 0) 1 else n * factorial(n - 1)
        //return numbers.map { factorial(it) }

        // Implement your code here to calculate factorials using higher-order functions
        return numbers.map { (1..it).fold(0L){acc, i -> acc*i} }
    }

    fun calculateSquares(numbers: List<Int>): List<Int> {
        // Implement your code here to calculate squares using coroutines and parallel processing
        return runBlocking {
            numbers.map {
                async(Dispatchers.Default) {
                    it * it
                }
            }.awaitAll()//map { it.await() }
        }
    }

    fun filterAndSquare(numbers: List<Int>): List<Int> {
        // Implement your code here using higher order functions
        return numbers.filterNot{it % 2 == 0}.map{ it * it}
    }

    fun manipulateListUppercaseAlphabetical(strings: List<String>): List<String> {
        // Implement your code here using higher order functions
        return strings.map{ it.uppercase() }.sortedByDescending { it }
    }

    fun customFilterAndTransform(numbers: List<Int>): List<String> {
        // Implement your code here using higher order functions
        return numbers.filter{ it <= 10 }.map{ it.toString() }
    }

    fun advancedFilterAndGroup(names: List<String>): Map<Int, List<String>> {
        // Implement your code here using higher order functions
        //return names.filter{ it.toCharArray()[0] !== 'A' }.groupBy{ it.length }
        return names.filterNot{ it.startsWith("A") }.groupBy{ it.length }//.mapValues{(key,value) -> value.sorted() }

    }

    fun transformAndUppercase(strings: List<String>): String {
        // Implement your code here using higher order functions
        //return strings.fold(strings[0]){c, n -> c + n}.uppercase()
        return strings.joinToString("").uppercase()
    }

    fun partitionAndSquare(numbers: List<Int>): Pair<List<Int>, List<Int>> {
        // Implement your code here using higher order functions
        val left = numbers.filter{it % 2 == 0 }.map{it*it}
        val right = numbers.filterNot{it % 2 == 0 }.map{it*it}

        return Pair(left, right)
    }

    fun flattenAndSquare(nestedLists: List<List<Int>>): List<Int> {
        // Implement your code here using higher order functions
        return nestedLists.fold(emptyList()){  c:List<Int>, v ->  v.map { c.plus(it)}.run{c}}.map{ it * it }
        //return nestedLists.flatten().map { it * it }
    }

    fun filterAndUppercase(strings: List<String>): List<String> {
        // Implement your code here using higher order functions
        return strings.filterNot { it.length > 5 }.map{ it.uppercase() }
    }

    fun averageSalaryByDepartment(employees: List<Employee>): Map<String, Double> {
        // Implement your code here using higher order functions
        //return employees.groupBy{it.department}.mapValues{ (k,v) -> (v.reduce{a,c -> a + c.salary} / v.size)}
        return employees.groupBy{it.department}.mapValues{ (_,v) -> v.map{it.salary}.average() }

    }

    fun combineAndSquare(list1: List<Int>, list2: List<Int>): List<Int> {
        // Implement your code here using higher order functions
        val vals : List<Int> = emptyList()
        var pairs = list1.fold(0){a,c-> vals.plus(list1[a] + list2[a]); a.inc()}
        return vals.map{ it * it}

        /*return list1.zip(list2) { a, b -> a + b }
            .map { it * it }*/
    }

    fun isPrime(n: Int): Boolean {
        if (n <= 1) return false
        for (i in 2 until n) {
            if (n % i == 0) {
                return false
            }
        }
        return true
    }

    fun filterPrimesAndSquare(numbers: List<Int>): List<Int> {
        // Implement your code here using higher order functions
        return numbers.filter{ isPrime(it) }.map{ it * it}
    }

    fun vowelCount(word: String):Int{
        //return word.toCharArray().count { listOf("a", "e", "i", "o", "u").contains(it as String) }
        return word.count { it in "aeiouAEIOU" }
    }

    fun filterAndConcatenate(strings: List<String>): String {
        // Implement your code here using higher order functions
        return strings.filter{ vowelCount(it) <= 2}.joinToString("")
    }

    fun sortAndConcatenate(strings: List<String>): String {
        // Implement your code here using higher order functions
        return strings.sortedByDescending{it}.joinToString("")
    }

    fun partitionAndSumSquares(numbers: List<Int>): Pair<Int, Int> {
        // Implement your code here using higher order functions
        //val list1 = numbers.filter{it%2==0}
        //val list2 = numbers.filterNot{it%2==0}
        //return Pair(list1.reduce{a,c-> (a+c*c)}, list2.reduce{a,c->(a+c*c)})

        val (evens, odds) = numbers.partition { it % 2 == 0 }
        return Pair(evens.sumOf { it * it }, odds.sumOf { it * it })
    }

    fun groupAndConcatenate(strings: List<String>): Map<Char, String> {
        // Implement your code here using higher order functions
        return strings.groupBy{it.toCharArray()[0]}.mapValues { (k,v)-> v.joinToString("") }

        //return strings.groupBy { it[0] }
        //   .mapValues { (_, value) -> value.joinToString("") }
    }

    fun filterAndTransform(words: List<String>): List<String> {
        // Implement your code here using higher order functions
        return words.filter{it.length <= 5}.map{ it.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.getDefault()
            ) else it.toString()
        } +"!"}
    }

    fun transformAndSum(numbers: List<Int>): List<Int> {
        // Implement your code here using higher order functions
        //val n = numbers.map{ it.toString().reversed() }
        //return n.map{ it.fold(0){a,c-> (a) + (c.code)}}

        return numbers.map { num ->
            num.toString().reversed().sumOf { it.toString().toInt() }
        }
    }

    fun filterAndMap(sentences: List<String>): List<Int> {
        // Implement your code here using higher order functions

        return sentences.flatMap{it.split("")}.filterNot{it.length % 2 == 0}.map{ it.length }

        /*return sentences.flatMap { it.split(" ") }
            .filter { it.length % 2 == 1 }
            .map { it.length }*/
    }

    fun totalLengthOfStringsContainingA(strings: List<String>): Int {
        // Implement your code here using higher order functions
        //return strings.fold(0){a,c-> if (c.contains("a")) a+c.length else a}

        //return strings.filter{it.contains("a")}.sumOf{it.length}

        return strings.filter { 'a' in it }
            .sumOf { it.length }
    }

    fun processNumbers(numbers: List<Int>): Triple<Int, Int, String> {
        // Implement your code here using higher order functions
        val positiveNums = numbers.filter { it >= 0 }
        val negativeNums = numbers.filter { it < 0 }

        val prod = positiveNums.fold(1){a,c -> a * c}
        val sum = negativeNums.sumOf{it}
        //val prodSum = prod.absoluteValue.toString() + sum.absoluteValue.toString()
        val prodSum = numbers.map{ it.absoluteValue }.joinToString("")

        return Triple(prod, sum, prodSum)



    }


    fun groupWordsByLength(words: List<String>): Map<Int, List<String>> {
        // Implement your code here using higher order functions
        return words.groupBy{it.length}
    }

    fun calculateTotalPriceWithDiscount(products: List<Product>, discountThreshold: Double): Double {
        // Implement your code here using higher order functions
        return products.filter{ it.discountPercentage > discountThreshold }.sumOf{ it.price }

    }

    fun isAna(s: String, v: Int, p:String ): Boolean {

        val charString = p.toCharArray()

        val subString = s.substring(v)

        charString.forEach{
            if (!subString.contains(it)) return false
            else subString.removeRange(v, v+1)
        }

        return true


    }

    fun findAnagrams(s: String, p:String): List<Int>{
        val chars = s.toCharArray()

        /*for (index in chars.indices){
            if(isAna(s, index, p)) index else -1
        }*/
        val ind = chars.indices.map{
            if(isAna(s, it, p)) it else -1
        }
        //val ind = s.map{ k,v -> if(isAna(k,s,p)) k else -1 }.filter{ it >= 0 }

        return ind.filter{ it >= 0 }
    }

    fun mainTest() {
        val cities = listOf(
            City("New York", "USA"),
            City("Los Angeles", "USA"),
            City("Paris", "France"),
            City("Paris", "France"),
            City("Tokyo", "Japan")
        )

        val numbers = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        val evenNumbers = filterEvenNumbers(numbers)
        println("TIME123 Even numbers: $evenNumbers")

        val uniqueCityNames = getUniqueCityNames(cities)
        println("TIME123 Unique city names: $uniqueCityNames")

        val numbersMax = listOf(23, 45, 12, 67, 89, 34, 56, 78, 90)
        val max = findMaxValue(numbersMax)
        println("TIME123 Maximum value: $max")

        val terms = 10
        val fibonacciSeries = generateFibonacciSeries(terms)
        println("TIME123 Fibonacci series: $fibonacciSeries")

        val sentenceCapitalized = "hello, world! this is a test."
        val capitalizedSentence = capitalizeWords(sentenceCapitalized)
        println("TIME123 Original: $sentenceCapitalized")
        println("TIME123 Capitalized: $capitalizedSentence")

        val sentence = "the quick brown fox jumps over the lazy dog the quick brown dog"
        val wordCountMap = countWords(sentence)
        println("TIME123 Word count map: $wordCountMap")

        val numbersSum = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        val sumEven = sumEvenNumbers(numbersSum)
        println("TIME123 Sum of even numbers: $sumEven")

        val numbersUnique = listOf(1, 2, 2, 3, 4, 3, 5, 6, 7, 7, 8, 9, 10)
        val deduplicatedNumbers = removeDuplicates(numbersUnique)
        println("TIME123 Deduplicated numbers: $deduplicatedNumbers")

        val numbersAvg = listOf(10.0, 15.0, 20.0, 25.0, 30.0)
        val average = calculateAverage(numbersAvg)
        println("TIME123 Average: $average")

        val strings = listOf("apple", "banana", "cherry", "date", "elderberry")
        val lengths = stringLengths(strings)
        println("TIME123 String lengths: $lengths")

        val stringsMax = listOf("apple", "banana", "cherry", "date", "elderberry")
        val maxLengthString = findMaxLengthString(stringsMax)
        println("TIME123 String with maximum length: $maxLengthString")

        val stringsCount = listOf("apple", "banana", "cherry", "date", "elderberry")
        val characterCount = countCharacters(stringsCount)
        println("TIME123 Total character count: $characterCount")

        val stringsShort = listOf("apple", "banana", "cherry", "date", "elderberry")
        val threshold = 6
        val longStrings = removeShortStrings(stringsShort, threshold)
        println("TIME123 Long strings: $longStrings")

        val numbersFactorial = listOf(3, 5, 7, 10)
        val factorials = calculateFactorials(numbersFactorial)
        println("TIME123 Factorials: $factorials")

        //runBlocking {
        val numbersSquares = listOf(1, 2, 3, 4, 5)
        val squares = calculateSquares(numbersSquares)
        println("TIME123 Squares: $squares")
        // }

        val numbersFilterAndSquare = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        val resultFilterAndSquare = filterAndSquare(numbersFilterAndSquare)
        println("TIME123 Filtered and squared numbers: $resultFilterAndSquare")

        val stringsUppercaseAlphabetical = listOf("apple", "banana", "cherry", "date")
        val resultUppercaseAlphabetical = manipulateListUppercaseAlphabetical(stringsUppercaseAlphabetical)
        println("TIME123 Manipulated list: $resultUppercaseAlphabetical")

        val numbersFilterAndTransform = listOf(5, 12, 8, 15, 3, 18, 7)
        val resultFilterAndTransform = customFilterAndTransform(numbersFilterAndTransform)
        println("TIME123 Filtered and transformed numbers: $resultFilterAndTransform")

        val namesFilterAndGroup = listOf("Alice", "Bob", "Anna", "David", "Amy", "John")
        val resultFilterAndGroup = advancedFilterAndGroup(namesFilterAndGroup)
        println("TIME123 Filtered and grouped names: $resultFilterAndGroup")

        val stringstransformAndUppercase = listOf("hello", "world", "kotlin", "is", "fun")
        val resulttransformAndUppercase = transformAndUppercase(stringstransformAndUppercase)
        println("TIME123 Transformed and uppercase string: $resulttransformAndUppercase")

        val numbersPartitionAndSquare = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        val (evenSquares, oddSquares) = partitionAndSquare(numbersPartitionAndSquare)
        println("TIME123 Even squares: $evenSquares")
        println("TIME123 Odd squares: $oddSquares")

        val nestedLists = listOf(
            listOf(1, 2, 3),
            listOf(4, 5),
            listOf(6, 7, 8)
        )
        val resultflattenAndSquare = flattenAndSquare(nestedLists)
        println("TIME123 Flattened and squared list: $resultflattenAndSquare")

        val stringsFilterAndUppercase = listOf("apple", "banana", "cherry", "grapefruit", "kiwi", "orange")
        val resultFilterAndUppercase = filterAndUppercase(stringsFilterAndUppercase)
        println("TIME123 Filtered and uppercase strings: $resultFilterAndUppercase")

        val employeesAverageSalaryByDepartment = listOf(
            Employee("Alice", "HR", 50000.0),
            Employee("Bob", "Finance", 60000.0),
            Employee("Charlie", "HR", 55000.0),
            Employee("David", "IT", 70000.0),
            Employee("Emma", "Finance", 62000.0),
            Employee("Frank", "IT", 75000.0)
        )
        val resultAverageSalaryByDepartment = averageSalaryByDepartment(employeesAverageSalaryByDepartment)
        println("TIME123 Average salary by department: $resultAverageSalaryByDepartment")

        val list1 = listOf(1, 2, 3, 4, 5)
        val list2 = listOf(5, 4, 3, 2, 1)
        val resultCombineAndSquare = combineAndSquare(list1, list2)
        println("TIME123 Combined and squared list: $resultCombineAndSquare")

        val numbersFilterPrimesAndSquare = listOf(2, 3, 4, 5, 6, 7, 8, 9, 10)
        val resultFilterPrimesAndSquare = filterPrimesAndSquare(numbersFilterPrimesAndSquare)
        println("TIME123 Filtered primes and squared numbers: $resultFilterPrimesAndSquare")

        val stringsFilterAndConcatenate = listOf("apple", "banana", "cherry", "grapefruit", "kiwi", "orange")
        val resultFilterAndConcatenate = filterAndConcatenate(stringsFilterAndConcatenate)
        println("TIME123 Filtered and concatenated string: $resultFilterAndConcatenate")

        val numbersPartitionAndSumSquares = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        val resultPartitionAndSumSquares = partitionAndSumSquares(numbersPartitionAndSumSquares)
        println("TIME123 Sum of squares of even numbers: ${resultPartitionAndSumSquares.first}")
        println("TIME123 Sum of squares of odd numbers: ${resultPartitionAndSumSquares.second}")

        val stringsGroupAndConcatenate = listOf("apple", "banana", "cherry", "grape", "kiwi", "orange")
        val resultGroupAndConcatenate = groupAndConcatenate(stringsGroupAndConcatenate)
        println("TIME123 Grouped and concatenated strings: $resultGroupAndConcatenate")

        val wordsFilterAndTransform2 = listOf("apple", "banana", "cherry", "grapefruit", "kiwi", "orange")
        val resultFilterAndTransform2 = filterAndTransform(wordsFilterAndTransform2)
        println("TIME123 Filtered and transformed words: $resultFilterAndTransform2")

        val numbersTransformAndSum = listOf(123, 456, 789)
        val resultTransformAndSum = transformAndSum(numbersTransformAndSum)
        println("TIME123 Transformed and summed numbers: $resultTransformAndSum")

        val sentencesFilterAndMap = listOf("Hello, world!", "Kotlin is fun", "Functional programming is great")
        val resultFilterAndMap = filterAndMap(sentencesFilterAndMap)
        println("TIME123 Lengths of words with odd characters: $resultFilterAndMap")

        val stringsTotalLengthOfStringsContainingA = listOf("apple", "banana", "cherry", "grape", "kiwi", "orange")
        val resultTotalLengthOfStringsContainingA = totalLengthOfStringsContainingA(stringsTotalLengthOfStringsContainingA)
        println("TIME123 Total length of strings containing 'a': $resultTotalLengthOfStringsContainingA")

        val numbersProcessNumbers = listOf(5, -2, 3, -8, 4, -7)
        val resultProcessNumbers = processNumbers(numbersProcessNumbers)
        println("TIME123 Product of positive numbers: ${resultProcessNumbers.first}")
        println("TIME123 Sum of negative numbers: ${resultProcessNumbers.second}")
        println("TIME123 Concatenation of absolute values: ${resultProcessNumbers.third}")

        val wordsGroupWordsByLength = listOf("apple", "banana", "cherry", "orange", "grape", "kiwi")
        val resultGroupWordsByLength = groupWordsByLength(wordsGroupWordsByLength)

        resultGroupWordsByLength.forEach { (length, wordList) ->
            println("Words with length $length: $wordList")
        }
    }
}