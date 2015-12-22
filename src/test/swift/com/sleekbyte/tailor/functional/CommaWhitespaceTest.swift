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

func allItemsMatch<C1: Container, C2: Container
    where C1.ItemType == C2.ItemType,
    C1.ItemType: Equatable>
    (someContainer: C1, _ anotherContainer: C2) -> Bool {
}

func allItemsMatch<C1: Container, C2: Container
    where C1.ItemType == C2.ItemType,C1.ItemType: Equatable>
    (someContainer: C1, _ anotherContainer: C2) -> Bool {
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

if let roomCount = john.residence?.numberOfRooms, roomCountTwo = john.residence?.numberOfRooms {
    println("John's residence has \(roomCount) room(s).")
}

if let roomCount = john.residence?.numberOfRooms,roomCountTwo = john.residence?.numberOfRooms {
    println("John's residence has \(roomCount) room(s).")
}

if let roomCount = john.residence?.numberOfRooms , var roomCountTwo = john.residence?.numberOfRooms,
  var name = john.name {
    println("John's residence has \(roomCount) room(s).")
}

if var x = point.x?.val,  var y = point.y?.val,z = point.z?.val {
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
