// Class examples

class lowerCamelCase {
  var x: Int
}

class Snake_Case {
  var x: String
}

class UpperCamelCase {
  var foo: Int
  var bar: String
}

class UpperCamelCase: SuperClass {
  var foo: Int
  var bar: String
}

// Enum examples

enum Enumeration {
  case validName
  case anotherValidName
  case yetAnotherValidName
  case four
  case five
}

enum Barcode {
  case upca(Int, Int, Int, Int)
  case qrCode(String)
}

enum ASCIIControlCharacter: Character {
    case tab = "\t"
    case lineFeed = "\n"
    case carriageReturn = "\r"
}

enum lowerCamelCase {
  case foo
}

enum snake_case {
  case foo
}

enum SCREAMING_SNAKE_CASE {
  case matters
}

enum UpperCamelCase {
  case foo
}

enum Planet {
    case mercury, venus, earth, mars, jupiter, saturn, uranus, neptune
}

enum Num3ricalNameCase {
  case foo
}

// Struct Examples

struct UpperCamelCaseCar: Vehicle {
    let numberOfWheels = 4
}

struct lowerCamelCaseCar: Vehicle {
    let numberOfWheels = 4
}

struct snake_case_car: Vehicle {
    let numberOfWheels = 4
}

struct Num3ricalNameCar: Vehicle {
    let numberOfWheels = 4
}

// Protocol Examples

protocol UpperCamelCase {
    // protocol definition goes here
}

protocol lowerCamelCase {
    // protocol definition goes here
}

protocol Snake_Case {
    // protocol definition goes here
}

protocol SCREAMING_SNAKE_CASE {
    // protocol definition goes here
}

protocol Num3ricalName {
    // protocol definition goes here
}

enum SomeEnum: Int {
    case someCase
    init (value: NSNumber) {
        switch value.integerValue {
        case someCase.rawValue:
             self = someCase
        }
    }
}

class Dictionary<Key, Value> {
}

class Dictionary<key, v4lue> {
}

func swapTwoValues<T>(a: inout T, _ b: inout T) {
    let temporaryA = a
    a = b
    b = temporaryA
}

func swapTwoValues<type>(a: inout type, _ b: inout type) {
    let temporaryA = a
    a = b
    b = temporaryA
}

struct Stack<Element>: Container {
}

struct Stack<El_ement>: Container {
}
