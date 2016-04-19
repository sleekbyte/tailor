### [comma-whitespace](https://github.com/sleekbyte/tailor/issues/89)

Prefer no spaces before and exactly one space after a comma (',') in the following structures:

* Generics
* Type Inheritance Clauses
* Condition Clauses
* Availability Arguments
* Generic Argument Lists
* Pattern Initializer Lists
* Parameter Lists
* Enum Case Lists
* Tuple Pattern Lists
* Tuple Type Lists
* Expression Lists
* Array Literal Items
* Dictionary Literal Items
* Capture List Items
* Paranthesized Expressions
* Identifier Lists
* Switch Case Item Lists

*Preferred*

```
func someFunction<T: SomeClass, U: SomeProtocol>(someT: T, someU: U) {
    // function body goes here
}

func someFunction<T: SomeClass, U: SomeProtocol,
	V: AnotherClass>(someT: T, someU: U) {
    // function body goes here
}

class Bicycle: Vehicle, TwoWheeler {
    var hasBasket = false
}

if x < 2, var y = val {
    println(x + y)
}

if let roomCount = john.residence?.numberOfRooms, roomCountTwo = john.residence?.numberOfRooms {
    println("John's residence has \(roomCount) room(s).")
}

if #available(iOS 9, OSX 10.10, *) {
    // Use iOS 9 APIs on iOS, and use OS X v10.10 APIs on OS X
}

struct Dictionary<Key: Hashable, Value>: CollectionType {}

let numX = 2, func_y = { x in println(x) }

func initialize(x: Int, y: Int, z: Int) {}

enum Planet {
    case Mercury, Venus, Earth, Mars, Jupiter, Saturn, Uranus, Neptune
}

enum ASCIIControlCharacter: Character {
    case Tab = "\t", LineFeed = "\n"
    case CarriageReturn = "\r"
}

var (x, y): (Int, Int)

for (i = 0, j = n - 1; i < n && j >= 0; i++, j--) {
  println(mat[i][j])
}

shoppingList += ["Chocolate Spread", "Cheese", "Butter"]

var airports: [String: String] = ["YYZ": "Toronto Pearson", "DUB": "Dublin"]

lazy var someClosure: Void -> String = {
    [unowned self, weak delegate = self.delegate!] in
    // closure body goes here
}

var arr = [ (1, 2, 3), (3, 4, 5) ]

reversed = names.sort( { s1, s2 in s1 > s2 } )

switch character {
case "a", "e", "i", "o", "u", " ": continue
default: puzzleOutput.append(character)
}
```

*Not Preferred*

```
func someFunction<T: SomeClass,U: SomeProtocol>(someT: T, someU: U) {
    // function body goes here
}

func someFunction<T: SomeClass , U: SomeProtocol>(someT: T, someU: U) {
    // function body goes here
}

class Bicycle: Vehicle,  TwoWheeler {
    var hasBasket = false
}

if x < 2 , var y = val {
    println(x + y)
}

if let roomCount = john.residence?.numberOfRooms,roomCountTwo = john.residence?.numberOfRooms {
    println("John's residence has \(roomCount) room(s).")
}

if #available(iOS 9, OSX 10.10,*) {
    // Use iOS 9 APIs on iOS, and use OS X v10.10 APIs on OS X
}

struct Dictionary<Key: Hashable,   Value>: CollectionType {}

let numX = 2 , func_y = { x in println(x) }

func initialize(x: Int,y: Int,z: Int) {}

enum Planet {
    case Mercury,Venus, Earth, Mars,  Jupiter, Saturn, Uranus, Neptune
}

enum ASCIIControlCharacter: Character {
    case Tab = "\t" , LineFeed = "\n"
    case CarriageReturn = "\r"
}

var (x,y): (Int , Int)

for (i = 0,j = n - 1; i < n && j >= 0; i++,j--) {
  println(mat[i][j])
}

shoppingList += ["Chocolate Spread" , "Cheese",  "Butter"]

var airports: [String: String] = ["YYZ": "Toronto Pearson","DUB": "Dublin"]

lazy var someClosure: Void -> String = {
    [unowned self , weak delegate = self.delegate!] in
    // closure body goes here
}

var arr = [ (1,2, 3), (3 , 4, 5) ]

reversed = names.sort( { s1,s2 in s1 > s2 } )

switch character {
case "a" , "e",  "i","o", "u", " ": continue
default: puzzleOutput.append(character)
}
```
