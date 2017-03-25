var i : Int
var j:  Int = 2
var k: Int

let (x, y):  (Int, Int) = (1, 2)
var someOptional:Int? = 42

struct FixedLengthRange {
    var var1:  Int
    let var2 : Int
}

func declarations(externalParam localParam :  String) {
  let oddDigits : Set = [1, 3, 5, 7, 9]
}

var x: (one :String, two: Int, three:  Int)

protocol SomeProtocol {
    var mustBeSettable: Int { get set }
    var doesNotNeedToBeSettable : Int { get }
}

var airports: [String: String] = ["YYZ" : "Toronto Pearson", "DUB": "Dublin"]
var capitals = ["Canada":  "Ottawa", "India":
  "New Delhi"]
var frequencies: [String : Int]
var marks: [String:  Int]
var airports: [String: String] = ["YYZ" : "Toronto Pearson", "DUB"
: "Dublin"]

switch anotherPoint {
case (let x, 0) : print("on the x-axis with an x value of \(x)")
case (0, let y):  print("on the y-axis with a y value of \(y)")
case let (x, y): print("somewhere else at (\(x), \(y))")
default :
  print("you had one job")
}

switch character {
case "a", "e", "i", "o", "u", " ":  continue
default:  puzzleOutput.append(character)
}

switch character {
case "a", "e", "i", "o", "u", " ":
  continue
default: puzzleOutput.append(character)
}

switch character {
  case "t":  ;
  case "s": ;
  default:;
}

class ClassOne<T>:  ParentOne, ParentTwo {

  func hello() {
    return "hello"
  }

}

class ClassTwo : ParentOne {
}

class ClassThree: ParentOne {
}

class ClassTwo :
ParentOne {
}

struct StructOne<T> : ParentStruct {
}

struct StructOne<T>:ParentStruct {
}


struct StructTwo : ParentStruct {
}

struct StructThree:  ParentStruct {
}

struct StructOne<T> :
  ParentStruct {
}

struct StructOne<T>:
  ParentStruct {
}

enum EnumOne : Int {
  case A, B, C = 5, D
}

enum CompassPoint:  Direction {
    case North
    case South
    case East
    case West
}

enum EnumOne :
Int {
  case A, B, C = 5, D
}

enum CompassPoint:
  Direction {
    case North
    case South
    case East
    case West
}

protocol SomeClassOnlyProtocol:  class, SomeInheritedProtocol {
}

protocol SomeClassOnlyProtocol : class, SomeInheritedProtocol {
}

protocol SomeClassOnlyProtocol: class, SomeInheritedProtocol {
}

protocol InheritingProtocol:  SomeProtocol, AnotherProtocol {
}

protocol InheritingProtocol : SomeProtocol, AnotherProtocol {
}

protocol InheritingProtocol:SomeProtocol, AnotherProtocol {
}

protocol InheritingProtocol: SomeProtocol, AnotherProtocol {
}

protocol SomeClassOnlyProtocol
: class, SomeInheritedProtocol {
}

protocol SomeClassOnlyProtocol
:class, SomeInheritedProtocol {
}

extension SomeType: SomeProtocol, AnotherProtocol {
}

extension SomeType : SomeProtocol, AnotherProtocol {
}

extension SomeType:  SomeProtocol, AnotherProtocol {
}

let rowHeight = contentHeight + (hasHeader ? 50:20)
a != nil ? a!: b
a != nil ? a! :  b
let light = status ? getRed() : getGreen()
let light = status ? getRed(): getGreen()
let light = status ? getRed() :  getGreen()
let boolValue = lhs.boolValue ? try rhs().boolValue : false
let boolValue = lhs.boolValue ? try rhs().boolValue: false

let http200Status = (statusCode: 200, description: "OK")
let http200Status = (statusCode : 200, description:"OK")
let http200Status = (statusCode:  200, description: "OK")
let tupleception = (statusCode: (statusCode : 200, description: "OK"), description:  "NOT OK")

func someFunction<T: SomeClass, U: SomeProtocol>(someT: T, someU: U) {
}

func someFunction<T : SomeClass, U:SomeProtocol & AnotherProtocol >(someT: T, someU: U) {
}

func someFunction<T:  SomeClass, U: SomeProtocol>(someT: T, someU: U) {
}

func allItemsMatch<
    C1 : Container, C2: Container>
    (someContainer: C1, _ anotherContainer: C2) -> Bool
    where C1.ItemType == C2.ItemType, C1.ItemType: Equatable {
}

SystemError.description(for: errorNumber)
SystemError.description(for:errorNumber)
SystemError.description(for : errorNumber)
SystemError.description(_:)
SystemError.description(_ :)
