class Bicycle: Vehicle {
    var hasBasket = false
}

class Bicycle: Vehicle, Commute {
    var hasBasket = false
}

class Bicycle: Vehicle , Commute {
    var hasBasket = false
}

class Bicycle: Vehicle,Commute {
    var hasBasket = false
}

class Bicycle: Vehicle,  Commute {
    var hasBasket = false
}

class Bicycle: Vehicle, Commute, Object {
    var hasBasket = false
}

class Bicycle: Vehicle, Commute , Object {
    var hasBasket = false
}

class Bicycle: Vehicle, Commute,
               Object {
    var hasBasket = false
}

class Bicycle: class {
    var hasBasket = false
}

class Bicycle: class , Vehicle {
    var hasBasket = false
}

enum Planet: Int {
    case Mercury = 1, Venus, Earth, Mars, Jupiter, Saturn, Uranus, Neptune
}

enum Planet: Int,Int {
    case Mercury = 1, Venus, Earth, Mars, Jupiter, Saturn, Uranus, Neptune
}

public final class SessionDelegate: NSObject, NSURLSessionDelegate,
  NSURLSessionTaskDelegate, NSURLSessionDataDelegate,  NSURLSessionDownloadDelegate {
    var subdelegates: [Int: Request.TaskDelegate] = [:]
}

struct Whale: Mammal , Fish {
  var flippers = 2
}

func someFunction<T: SomeClass, U: SomeProtocol>(someT: T, someU: U) {
    // function body goes here
}

func someFunction<T: SomeClass,U: SomeProtocol>(someT: T, someU: U) {
    // function body goes here
}

func someFunction<T: SomeClass,  U: SomeProtocol>(someT: T, someU: U) {
    // function body goes here
}

func someFunction<T: SomeClass , U: SomeProtocol, V: AnotherClass>(someT: T, someU: U) {
    // function body goes here
}

func allItemsMatch<C1: Container, C2: Container>
    (someContainer: C1, _ anotherContainer: C2) -> Bool
    where C1.ItemType == C2.ItemType,
        C1.ItemType: Equatable {
}

func allItemsMatch<C1: Container, C2: Container>
    (someContainer: C1, _ anotherContainer: C2) -> Bool
    where C1.ItemType == C2.ItemType,C1.ItemType: Equatable {
}

extension ImageFilter where Self: Sizable ,   Self: Roundable {
}

func someFunction<T: SomeClass, U: SomeProtocol
, V: AnotherClass>(someT: T, someU: U) {
    // function body goes here
}

func someFunction<T: SomeClass, U: SomeProtocol,
V: AnotherClass>(someT: T, someU: U) {
    // function body goes here
}

if x < 2, var y = val {
    println(x + y)
}

if x < 2 , var y = val {
    println(x + y)
}

if x < 2, #available(iOS 10.2, *),var y = val {
    println(x + y)
}

if #available(iOS 10.2, *),  var y = val {
    println(x + y)
}

if #available(iOS 10.2, *), var y = val {
    println(x + y)
}

if #available(iOS 10.2, *) , x < 2 {
    println(x + y)
}

if let roomCount = john.residence?.numberOfRooms {
    println("John's residence has \(roomCount) room(s).")
}

if let roomCount = john.residence?.numberOfRooms, let roomCountTwo = john.residence?.numberOfRooms {
    println("John's residence has \(roomCount) room(s).")
}

if let roomCount = john.residence?.numberOfRooms,let roomCountTwo = john.residence?.numberOfRooms {
    println("John's residence has \(roomCount) room(s).")
}

if let roomCount = john.residence?.numberOfRooms , var roomCountTwo = john.residence?.numberOfRooms,
  var name = john.name {
    println("John's residence has \(roomCount) room(s).")
}

if var x = point.x?.val,  var y = point.y?.val,var z = point.z?.val {
  println(x, y, z)
}

if #available(iOS 9, OSX 10.10, *) {
    // Use iOS 9 APIs on iOS, and use OS X v10.10 APIs on OS X
}

if #available(iOS 9, OSX 10.10,*) {
    // Use iOS 9 APIs on iOS, and use OS X v10.10 APIs on OS X
}

if #available(iOS 9 , OSX 10.10, *) {
    // Use iOS 9 APIs on iOS, and use OS X v10.10 APIs on OS X
}

struct Array<Value>: CollectionType {}

struct Dictionary<Key: Hashable, Value>: CollectionType {}

struct Dictionary<Key: Hashable , Value>: CollectionType {}

struct Dictionary<Key: Hashable,   Value>: CollectionType {}

let numX = 2, func_y = { x in println(x) }

let numX = 2 , func_y = { x in println(x) }

var numX = 2,func_y = { x in println(x) }

func initialize(x: Int) {}

func initialize(x: Int, y: Int, z: Int) {}

func initialize(x: Int,  y: Int, z: Int) {}

func initialize(x: Int,y: Int,z: Int) {}

func initialize(x_ext x: Int , y_ext y: Int, z_ext z: Int) {}

enum Planet {
    case Earth
}

enum Planet {
    case Mercury, Venus, Earth, Mars, Jupiter, Saturn, Uranus, Neptune
}

enum Planet {
    case Mercury,Venus, Earth, Mars,  Jupiter, Saturn, Uranus, Neptune
}

enum Planet {
    case Mercury, Venus, Earth, Mars, Jupiter , Saturn, Uranus, Neptune
}

enum ASCIIControlCharacter: Character {
    case Tab = "\t", LineFeed = "\n"
    case CarriageReturn = "\r"
}

enum ASCIIControlCharacter: Character {
    case Tab = "\t" , LineFeed = "\n"
    case CarriageReturn = "\r"
}

enum ASCIIControlCharacter: Character {
    case Tab = "\t",LineFeed = "\n"
    case CarriageReturn = "\r"
}

var (x, y): (Int, Int)

var (x,y): (Int , Int)

var (x, y ,z): (Int,Int, Int)

var (x): Int = 2

shoppingList += ["Chocolate Spread", "Cheese", "Butter"]

shoppingList += ["Chocolate Spread" , "Cheese",  "Butter"]

shoppingList += ["Chocolate Spread","Cheese", "Butter"]

shoppingList += ["Chocolate Spread", "Cheese", "Butter",]

shoppingList += ["Chocolate Spread", "Cheese", "Butter" ,]

shoppingList += ["Chocolate Spread", "Cheese",
 "Butter"]

var airports: [String: String] = ["YYZ": "Toronto Pearson", "DUB": "Dublin"]

var airports: [String: String] = ["YYZ": "Toronto Pearson" , "DUB": "Dublin"]

var airports: [String: String] = ["YYZ": "Toronto Pearson",  "DUB": "Dublin"]

var airports: [String: String] = ["YYZ": "Toronto Pearson","DUB": "Dublin"]

var airports: [String: String] = ["YYZ": "Toronto Pearson", "DUB": "Dublin",]

var airports: [String: String] = ["YYZ": "Toronto Pearson", "DUB": "Dublin" ,]

lazy var someClosure: () -> String = {
    [unowned self, weak delegate = self.delegate!] in
    // closure body goes here
}

lazy var someClosure: () -> String = {
    [unowned self,weak delegate = self.delegate!] in
    // closure body goes here
}

lazy var someClosure: () -> String = {
    [unowned self , weak delegate = self.delegate!] in
    // closure body goes here
}

lazy var someClosure: () -> String = {
    [unowned self,  weak delegate = self.delegate!] in
    // closure body goes here
}

lazy var someClosure: () -> String = {
    [unowned self,
     weak delegate = self.delegate!] in
    // closure body goes here
}

var arr = [ (1, 2, 3), (3, 4, 5) ]
var arr = [ (1,2, 3), (3 , 4, 5) ]
var arr = [ (1, 2,  3), (3, 4,
  5) ]

func getFullName() -> (first: String, last: String) {
    let firstName = "John"
    let lastName = "Doe"

    return (firstName , lastName)
}

func getFullName() -> (first: String, last: String) {
    let firstName = "John"
    let lastName = "Doe"

    return (firstName,lastName)
}

reversed = names.sort( { s1, s2 in s1 > s2 } )
reversed = names.sort( { s1,s2 in s1 > s2 } )
reversed = names.sort( { s1 , s2 in s1 > s2 } )
reversed = names.sort( { _, s1, s2 in s1 > s2 } )
reversed = names.sort( { _ , s1,  s2 in s1 > s2 } )

switch character {
case "a", "e", "i", "o", "u", " ": continue
default: puzzleOutput.append(character)
}

switch character {
case "a" , "e",  "i","o", "u", " ": continue
default: puzzleOutput.append(character)
}

switch character {
case "a", "e", "i", "o",
  "u"," ": continue
default: puzzleOutput.append(character)
}

reversed = names.sort( { s1, s2 in return s1 > s2 } )
reversed = names.sort( { s1,s2 in return s1 > s2 } )
reversed = names.sort( { s1 , s2 in return s1 > s2 } )
