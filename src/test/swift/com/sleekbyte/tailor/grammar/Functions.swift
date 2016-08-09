func sayHello(personName: String) -> String {
    let greeting = "Hello, " + personName + "!"
    return greeting
}

func halfOpenRangeLength(start start: Int, end: Int) -> Int {
    return end - start
}

func sayHelloWorld() -> String {
    return "hello, world"
}

func sayHello(personName: String, alreadyGreeted: Bool) -> String {
    if alreadyGreeted {
        return sayHelloAgain(personName)
    } else {
        return sayHello(personName)
    }
}

func sayGoodbye(personName: String) {
    print("Goodbye, \(personName)!")
}

func minMax(array: [Int]) -> (min: Int, max: Int) {
    var currentMin = array[0]
    var currentMax = array[0]
    for value in array[1..<array.count] {
        if value < currentMin {
            currentMin = value
        } else if value > currentMax {
            currentMax = value
        }
    }
    return (currentMin, currentMax)
}

func minMax(array: [Int]) -> (min: Int, max: Int)? {
    if array.isEmpty { return nil }
    var currentMin = array[0]
    var currentMax = array[0]
    for value in array[1..<array.count] {
        if value < currentMin {
            currentMin = value
        } else if value > currentMax {
            currentMax = value
        }
    }
    return (currentMin, currentMax)
}

if let bounds = minMax([8, -6, 2, 109, 3, 71]) {
    print("min is \(bounds.min) and max is \(bounds.max)")
}

func sayHello(to person: String, and anotherPerson: String) -> String {
    return "Hello \(person) and \(anotherPerson)!"
}

func someFunction(firstParameterName: Int, _ secondParameterName: Int) {
    // function body goes here
    // firstParameterName and secondParameterName refer to
    // the argument values for the first and second parameters
}
someFunction(1, 2)

func someFunction(parameterWithDefault: Int = 12) {
    // function body goes here
    // if no arguments are passed to the function call,
    // value of parameterWithDefault is 42
}

func arithmeticMean(numbers: Double...) -> Double {
    var total: Double = 0
    for number in numbers {
        total += number
    }
    return total / Double(numbers.count)
}
arithmeticMean(1, 2, 3, 4, 5)

func alignRight(string: String, totalLength: Int, pad: Character) -> String {
    let amountToPad = totalLength - string.characters.count
    if amountToPad < 1 {
        return string
    }
    let padString = String(pad)
    for _ in 1...amountToPad {
        string = padString + string
    }
    return string
}
let originalString = "hello"
let paddedString = alignRight(originalString, totalLength: 10, pad: "-")

func swapTwoInts(_ a: inout Int, _ b: inout Int) {
    let temporaryA = a
    a = b
    b = temporaryA
}
var someInt = 3
var anotherInt = 107
swapTwoInts(&someInt, &anotherInt)
print("someInt is now \(someInt), and anotherInt is now \(anotherInt)")

var mathFunction: (Int, Int) -> Int = addTwoInts
mathFunction = multiplyTwoInts
print("Result: \(mathFunction(2, 3))")

func printMathResult(mathFunction: (Int, Int) -> Int, _ a: Int, _ b: Int) {
    print("Result: \(mathFunction(a, b))")
}

func chooseStepFunction(backwards: Bool) -> (Int) -> Int {
    return backwards ? stepBackward : stepForward
}

func chooseStepFunction(backwards: Bool) -> (Int) -> Int {
    func stepForward(input: Int) -> Int { return input + 1 }
    func stepBackward(input: Int) -> Int { return input - 1 }
    return backwards ? stepBackward : stepForward
}
var currentValue = -4
let moveNearerToZero = chooseStepFunction(currentValue > 0)
// moveNearerToZero now refers to the nested stepForward() function
while currentValue != 0 {
    print("\(currentValue)... ")
    currentValue = moveNearerToZero(currentValue)
}
print("zero!")

public init(path: String = ".swiftlint.yml", optional: Bool = true) {}

@objc(beforeEachWithMetadata:)
public func beforeEach(closure: BeforeExampleWithMetadataClosure) {
  exampleHooks.appendBefore(closure)
}

public func success(@noescape closure: (T) -> Void) {
    switch self {
    case .Value(let value): closure(value)
    default: break
    }
}

public prefix func <-<R: FallibleSendable>(channel: R) throws -> R.T? {
    return try channel.send()
}

public func fopen(path: String..., mode: String = "r") throws -> UnsafeMutablePointer<FILE> {
    let path = joinPathComponents(path)
    let f = libc.fopen(path, mode)
    guard f != nil else { throw SystemError.fopen(errno, path) }
    return f
}

@available(*, deprecated=3.4.0)
public static func errorWithCode(code: Code, failureReason: String) -> NSError {
    return errorWithCode(code.rawValue, failureReason: failureReason)
}

@discardableResult
public func upload(
    _ method: Method,
    _ URLString: URLStringConvertible,
    headers: [String: String]? = nil,
    file: URL)
    -> Request
{
    return Manager.sharedInstance.upload(method, URLString, headers: headers, file: file)
}

public class func suggestedDownloadDestination(
        directory: FileManager.SearchPathDirectory = .documentDirectory,
        domain: FileManager.SearchPathDomainMask = .userDomainMask)
        -> DownloadFileDestination
    {
        return { temporaryURL, response -> URL in
            let directoryURLs = FileManager.default.urls(for: directory, in: domain)

            if !directoryURLs.isEmpty {
                return directoryURLs[0].appendingPathComponent(response.suggestedFilename!)
            }

            return temporaryURL
        }
    }

public static func shrink(_ : Self) -> [Self] {
	 return []
}
