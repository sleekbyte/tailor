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

class Rectangle: Shape {
    let length: Double
    let breadth: Double

    init(length: Double, breadth: Double)  {
        // store the row and column of the square in the grid
        self.length = length
        self.breadth = breadth
    }

    func calculateArea() -> Double {
        return length * breadth
    }

    func printAreaIfStatement()  {
        var area = calculateArea()
        if area == Double.infinity {
            println("something went wrong!")
            return
        }

        if area == 0  {
            println ("Area is zero")
        }
        else if area == 1{
            println ("Area is 1")
        }
        else  {
            println ("Area is \(area)")
        }
    }

    func funWithLoops() {

        for var x = 0; x < lengthArray.count; x+=1  {
            println(x)
        }

        for breadth in breadthArray  {
            println(breadth)
        }

        while false {
            println("Will never be executed")
        }

        repeat {
            // infinite loop
        } while true
    }

    func moreFunWithLoops() {
        let lengthArray = [0,1,2,3,4]
        let breadthArray = [0,1,2,3,4]

        for var x = 0; x < lengthArray.count; x+=1 {
            println(x)
        }

        for breadth in breadthArray {
            println(breadth)
        }

        while false  {
            println("Will never be executed")
        }

        repeat  {
            // infinite loop
        } while true
    }

    func multiLineFunction(arg1: String,
                           arg2: String) {
        // do nothing.
    }

   class Shape  {

   }

   struct Square: Shape  {
        // square struct
   }

   struct Line {

         func obscureLoops() {
            for ; ;  {

            }

            for ;
                ; {

            }

            var x = 1

            for var x = 1 ; ; {

            }

            for ; ;
                x  {

            }

            for ; ;
                x{

            }
        }

   }
}

class Hello
<T> {
}
class Hello
<T>  {
}
struct Hello
<T>{
}
struct Hello
<T> {
}

protocol SomeProtocol  {

}

protocol SomeOtherProtocol: X  {

}

protocol
SomeProtocol {

}

protocol SomeProtocol  {

}

protocol
SomeOtherProtocol: X {

}

protocol SomeOtherProtocol:
X {

}

enum SomeEnum  {
    case SomeCase
}

enum SomeOtherEnum: Int   {
    case SomeCase
}

enum SomeEnum {
    case SomeCase
}

enum SomeOtherEnum: Int {
    case SomeCase
}

enum SomeOtherEnum:
Int  {
    case SomeCase
}

enum SomeOtherEnum:
Int {
    case SomeCase
}

enum ExampleEnum: Int  {
    case A, B, C = 5, D
}

enum ExampleEnum: Int {
    case A, B, C = 5, D
}

enum SomeEnum<A>  {

}

func someFunction () -> ()  {
// closure
}

func someFunction () -> () {
// closure
}

let padawans = ["Knox", "Avitla", "Mennaus"]
padawans.map({
  (padawan: String) -> String in
  "\(padawan) has been trained!"
})

func applyMutliplication(value: Int, multFunction: Int -> Int) -> Int  {
  return multFunction(value)
}

padawans.map()  {
  (padawan: String) -> String in
  "\(padawan) has been trained!"
}

padawans.map {
  (padawan: String) -> String in
  "\(padawan) has been trained!"
}

public extension SomeExtension {

}

extension SomeExtension: X {

}

extension SomeExtension  {

}

extension SomeExtension: X  {

}

extension
SomeExtension: X  {

}

var x: Int {
  set  {
    someVariable = newValue * 2
  }

  get  {
      return someVariable / 2
  }
}

var y: Int {
  set {
      someVariable = newValue * 2
  }

  get {
      return someVariable / 2
  }
}

class SomeClass {
    var x: Int {
        set  {
            self.x = 10
        }

        get {
            return 10
        }
    }
}

@objc
class ExampleClass  {
    var enabled: Bool {
        @objc(isEnabled)
        get {
            return true
        }
        set {
            self.enabled = true
        }
    }
}

struct Rect {
    var center: Point  {
        get {
            let centerX = origin.x + (size.width / 2)
            var centerY = origin.y + (size.height / 2)
            return Point(x: centerX, y: centerY)
        }
        set(newCenter)  {
            var origin.x = newCenter.x - (size.width / 2)
            var origin.y = newCenter.y - (size.height / 2)
        }
    }
}

// This is a valid comment
//This is an invalid comment
  //also bad
//  This is a valid comment
   // also valid
//

/* this is a valid comment */
/*this is an invalid comment */
/* this is also an invalid comment*/
/*so is this one*/
/*so am I */ /* me too!*/ /* but I'm valid */

/* this is a
valid comment */
/* this is
not valid*/
/*this is
also not valid
*/
/* this is
valid
though
*/
/*
valid


*/

/* valid */ /*invalid
*/ /* valid
*/ /*
invalid*/
/*/* Commentception */*/
/* /* Commentception */ */
/*
/*
Commentception
*/
*/

/// Valid single line documentation comment.

/**
    Valid multiline documentation comment.
*/

/** Valid
multi-line documentation
comment
*/

///Invalid single line documentation comment.

////nested single line comments will get flagged.

//// Nested single line comments will get flagged.

/**Invalid
multi-line documentation
comment
*/

/***Invalid
multi-line documentation
comment
*/

/*** Invalid
multi-line documentation
comment
*/
