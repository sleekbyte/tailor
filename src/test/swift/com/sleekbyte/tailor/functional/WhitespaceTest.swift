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
