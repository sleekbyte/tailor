print("Hello, World!")

if ( true ) {

}

if ( true) {

}

if (true) {

}

func demo( ) {

}

func eemo() {}

func aemo( ) {}

func gemo( c: Int ) {}

func sdemo( c: Int) {}

func nemo(c: Int ) {}

func sum() -> ( ) {}

let g = (5)

let h = (5 )

let tuple = ( 5, 2)

func simpleMax<T: Comparable>(x: T, _ y: T) -> T {
    if x < y {
        return y
    }
    return x
}

func simpleMaxWithWhitespace<T: Comparable>( x: T, _ y: T) -> T {
    if x < y {
        return y
    }
    return x
}

func someMethod(a: String,
    b: Int ) {

}

func someFunc () {
}

func properFunc() {
}

func multilineFunc
    () {
}

class SomeClass {
    init() {
    }
}

class SomeOtherClass {
    init () {
    }
}

func arithmeticMean(numbers: Double... ) -> Double {
    var total: Double = 0
    for number in numbers {
        total += number
    }
    return total / Double( numbers.count )
}

prefix operator √ {}

prefix func √ (number: Double) -> Double {
    return sqrt(number)
}

prefix func *(number: Double) -> Double {
    return number
}

func * (left: String, right: Int) -> String {
    if right <= 0 {
        return ""
    }

    var result = left
    for _ in 1..<right {
        result += left
    }

    return result
}

func ** (left: Double, right: Double) -> Double {
    return pow(left, right)
}

func <| (lhs: Int, rhs: Int) -> Int {}

func <|< <A> (lhs: A, rhs: A) -> A

func <|< <A>(lhs: A, rhs: A) -> A


let days = ["Monday", "Wednesday", "Friday"]
days.map({ (day: String) -> String in
  "\(day) is good!"
})

days.map({(day: String) -> String in
  "\(day) is good!"
})
