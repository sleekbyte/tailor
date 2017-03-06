let hello = false
let hello_World = { (kVar1: String, kVar2: String) -> Bool in return hello }

switch productBarcode {
case let .UPCA(KNumberSystem, manufacturer, Product, check):
    println("UPC-A: \(numberSystem), \(manufacturer), \(product), \(check).")
case let .QRCode(productCode):
    println("QR code: \(productCode).")
}

let (valid, in_valid) = (5 , "hello")
let numX = 2, func_y = { x in println(x) }

func toMicroseconds(seconds: Int) -> Int {
    let SecondsToMicroseconds = 100000
    let secondsToMicroseconds = 100000
    return seconds * secondsToMicroseconds
}

let exclamation_Mark: Character = "!"
let index = advance(greeting.startIndex, 7)
let range = advance(welcome.endIndex, -6)..<welcome.endIndex

let romeo_And_Juliet = [
    "Act 1 Scene 1: Verona, A public place",
    "Act 1 Scene 2: Capulet's mansion",
]

func declarations(ExternalParam LocalParam: String) {
  let OddDigits: Set = [1, 3, 5, 7, 9]
  let airport_Codes = [String](airports.keys)
  let numberOfLegs = ["spider": 8, "ant": 6, "cat": 4]
  let Movie = object as? Movie
}

let anotherPoint = (2, 0)
switch anotherPoint {
case (let x, 0):
    println("on the x-axis with an x value of \(x)")
case (0, let DrDrae):
    println("on the y-axis with a y value of \(y)")
case let (x, y):
    println("somewhere else at (\(x), \(y))")
}

let yetAnotherPoint = (1, -1)
switch yetAnotherPoint {
case let (x, y) where x == ThisShouldNotMatter:
    println("(\(x), \(y)) is on the line x == y")
case let (DrDrae, y) where DrDrae == -y:
    println("(\(x), \(y)) is on the line x == -y")
case let (x, y):
    println("(\(x), \(y)) is just some arbitrary point")
}

let many_strings = numbers.map {
    (number) -> String in
    var output = ""
    while number > 0 {
        output = digitNames[number % 10]! + output
        number /= 10
    }
    return output
}

class Queue {
    let max_QueueSize = 1000
    let maxQueueSize = 1000
    let MaxQueueSize = 1000

    func hello() {
        println("hello")
    }

}

struct FixedLengthRange {
    var firstValue: Int
    let K_Length: Int
    static let KThresholdLevel = 10
}

struct AlternativeRect {
    var origin = Point()
    var size = Size()
    var center: Point {
        get {
            let CenterX = origin.x + (size.width / 2)
            let centerY = origin.y + (size.height / 2)
            return Point(x: centerX, y: centerY)
        }
    }
}

if let RoomCount = john.residence?.numberOfRooms {
    println("John's residence has \(roomCount) room(s).")
}

for thing in things {
    switch thing {
    case 0 as Int:
        println("zero as an Int")
    case 0 as Double:
        println("zero as a Double")
    case let SomeInt as Int:
        println("an integer value of \(someInt)")
    case let someDouble as Double where someDouble > 0:
        println("a positive double value of \(someDouble)")
    }
}

if let roomCount = john.residence?.numberOfRooms, let RoomCount = john.residence?.numberOfRooms {
    println("John's residence has \(roomCount) room(s).")
}

if let hello = john.residence?.numberOfRooms, var ShouldNotMatter = john.residence?.numberOfRooms,
  var StillShouldNotMatter = john.residence?.numberOfRooms, let BadName = john.residence?.numberOfRooms,
  let AlsoBadName = john.residence?.number {
    println("John's residence has \(roomCount) room(s).")
}

if var ShouldNotMatter = john.residence?.numberOfRooms, var RoomCount = john.residence?.numberOfRooms {
    println("John's residence has \(roomCount) room(s).")
}

private struct Scaling {
    static let KFaceRadiusToEyeRadiusRatio: CGFloat = 10
    static let kFaceRadiusToEyeOffsetRatio: CGFloat = 3
    static let FaceRadiusToEyeSeparationRatio: CGFloat = 1.5

    func sup() {
        static let faceRadiusToMouthWidthRatio: CGFloat = 1
        static let kFaceRadiusToMouthHeightRatio: CGFloat = 3
        static let kFaceRadiusToMouthOffsetRatio: CGFloat = 3
    }

    let URL = "https://tailor.sh"

    init?(URL url: NSURL, resolvingAgainstBaseURL resolve: Bool) {
    }
    convenience init(URL URL: NSURL) {
    }
    init(URL URL: NSURL, entersReaderIfAvailable entersReaderIfAvailable: Bool) {
    }
    init?(URL url: NSURL, statusCode statusCode: Int, HTTPVersion HTTPVersion: String?, headerFields headerFields: [String: String]?) {
    }

    let `open` = true
    var `close` = false

    func demo() {
        let `Open` = true
    }
}
