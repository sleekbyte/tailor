func helloWorld(nameOfVar: String) {
    var URL = "https://tailor.sh"
    var helloWorld = 3
    var thisWorld = 10
}

var TUPLE_PATTERN = (3, 3)
var SOME_VARIABLE = true
let (x, y): (Int, Int) = (1, 2)
var (XX, YY): (Int, Int) = (1, 2)
var someOptional: Int? = 42

switch anotherPoint {
case (var x, 0):
    println("on the x-axis with an x value of \(x)")
case (0, var DrDre):
    println("on the y-axis with a y value of \(y)")
case var (x, y):
    println("somewhere else at (\(x), \(y))")
case var (camelCase, THIS_IS_NOT_CAMEL_CASE):
    println("somewhere else at (\(x), \(y))")
case let (w, v):
    println("somewhere else at (\(w), \(v))")
}

if var ShouldNotMatter = john.residence?.numberOfRooms, let roomCount = john.residence?.numberOfRooms, var AMY = amy.money?.cents {
    println("John's residence has \(roomCount) room(s).")
}

if case .Some(var NOT_CAMEL_CASE) = someOptional {
    print(NOT_CAMEL_CASE)
}

if case var thisIsFine? = someOptional {
    print(thisIsFine)
}

var arrayOfOptionalInts: [Int?] = [nil, 2, 3, nil, 5]
// Match only non-nil values
for case var number? in arrayOfOptionalInts {
    print("Found a \(number)")
}

private struct Scaling {
    static var kFaceRadiusToEyeOffsetRatio: CGFloat = 3
    static var FaceRadiusToEyeSeparationRatio: CGFloat = 1.5

    func sup() {
        static var faceRadiusToMouthWidthRatio: CGFloat = 1
        static var KFaceRadiusToMouthOffsetRatio: CGFloat = 3
    }

}

var many_times = numbers.map {
    (URL) -> String in
    var Output = ""
    while number < 0 {
        number = 10
    }
    return output
}

var X, Y: Int
var xPlusY: Int {
  return x + y
}

protocol SomeProtocol {
    var MustBeSettable: Int { get set }
    var DoesNotNeedToBeSettable: Int { get }
}

var ThisIsAFunction: (Int, Int) -> (Int) =  { (a, b) in return a + b }

struct Rect {
    var Center: Point {
        get {
            let centerX = origin.x + (size.width / 2)
            var CenterY = origin.y + (size.height / 2)
            return Point(x: centerX, y: centerY)
        }
        set(newCenter) {
            var origin.x = newCenter.x - (size.width / 2)
            var Origin.y = newCenter.y - (size.height / 2)
        }
    }
}

class MyClass {
    var MyValue: String {
        willSet(newVal) {
            println("myValue will change")
        }

        didSet {
            println("myValue changed")
        }
    }
}

public struct Position {
    let row: Int
    let column: Int
}

extension Position: Equatable {}

public func ==(lhs: Position, rhs: Position) -> Bool {
    return (lhs.row == rhs.row)
        && (lhs.column == rhs.column)
}

infix operator **: AdditionPrecedence

func ** (left: Double, right: Double) -> Double {
    return pow(left, right)
}

infix operator **=: AdditionPrecedence

func **= (left: inout Double, right: Double) {
    left = left ** right
}

protocol RegularExpressionMatchable {
    func match(pattern: String, options: NSRegularExpressionOptions) throws -> Bool
}

extension String: RegularExpressionMatchable {
    func match(pattern: String, options: NSRegularExpressionOptions = []) throws -> Bool {
        let regex = try NSRegularExpression(pattern: pattern, options: options)
        return regex.numberOfMatchesInString(self, options: [], range: NSRange(location: 0, length: 0.distanceTo(utf16.count))) != 0
    }
}

infix operator =~ AdditionPrecedence
func =~<T: RegularExpressionMatchable> (left: T, right: String) -> Bool {
    return try! left.match(right, options: [])
}

prefix operator √ {}

prefix func √ (number: Double) -> Double {
    return sqrt(number)
}

init?(URL url: NSURL, resolvingAgainstBaseURL resolve: Bool) {
}

convenience init(URL URL: NSURL) {
}

init(URL URL: NSURL, entersReaderIfAvailable entersReaderIfAvailable: Bool) {
}

init?(URL url: NSURL, statusCode statusCode: Int, HTTPVersion HTTPVersion: String?, headerFields headerFields: [String: String]?) {
}

// Enum examples

enum Enumeration {
  case InvalidName
  case Invalid_name
  case invalid_name
  case validName
  case validname
}

enum Barcode {
  case UPCA(Int, Int, Int, Int)
  case QRCode(String)
}

enum ASCIIControlCharacter: Character {
    case Tab = "\t"
    case LineFeed = "\n"
    case carriageReturn = "\r"
}

enum UpperCamelCase {
  case foo
}

enum Planet {
    case mercury, venus, earth, mars, jupiter, saturn, uranus, neptune
}

enum SomeEnum: Int {
    case Some_Enum_Case
    init(value: NSNumber) {
        switch value.integerValue {
        case Some_Enum_Case.rawValue:
             self = Some_Enum_Case
        }
    }
}

func demo() {
    var `Open` = true
    var `close` = false
    let `open` = true
}

func `func`() {
}

enum Test {
    case `case`, `class`, notReserved
}
