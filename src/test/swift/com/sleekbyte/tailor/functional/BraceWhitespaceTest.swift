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
            for pkg in parms.dependencies where pkg.type == .ModuleMap{
                println(pkg.name)
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

func applyMutliplication(value: Int, multFunction: (Int) -> Int) -> Int  {
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

public convenience init(name: String...) rethrows  {
}

public convenience init(name: String...) throws {
}

func allItemsMatch<C1: Container, C2: Container>
    (someContainer: C1, _ anotherContainer: C2) -> Bool
    where C1.ItemType == C2.ItemType, C1.ItemType: Equatable {}
