// Check Function length
func sayHello(personName: String) -> String {
    let greeting = "Hello, " + personName + "!"
    return greeting
}

// Check Class length
class Greeting {

    // Check Function with Nested Function length
    func makeIncrementer(forIncrement amount: Int) -> () -> Int {
        var runningTotal = 0

        func incrementer() -> Int {
            runningTotal += amount
            println(amount)
            println(runningTotal)
            return runningTotal
        }

        return incrementer
    }

}

// Check Closure length
var numbers = [1, 2, 3, 4]
let strings = numbers.map {
    (number) -> String in
    var output = "hi"
    // while number > 0 {
    //     output = digitNames[number % 10]! + output
    //     number /= 10
    // }
    return output
}

// Check Struct length
struct Resolution {
    var width = 0
    var height = 0
}
