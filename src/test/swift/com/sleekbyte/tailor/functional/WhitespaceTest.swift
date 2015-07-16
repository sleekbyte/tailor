prefix operator -+- {}

prefix operator  += {}

prefix operator *++  {}

prefix operator  -=   {}

postfix operator  += {}

infix operator -+*  { precedence 70 associativity right }

prefix operator
+= {

}

prefix operator-++ {}

prefix operator
+=     {

}

prefix operator
    +=    {

}

prefix operator
   += {

}

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
