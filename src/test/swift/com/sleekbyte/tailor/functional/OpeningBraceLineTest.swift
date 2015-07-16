class Rectangle : Shape
{
    let length: Double
    let breadth: Double

    init(length: Double, breadth: Double)
    {
        //store the row and column of the square in the grid
        self.length = length
        self.breadth = breadth
    }

    func calculateArea() -> Double
    {
        return length * breadth
    }

    func printAreaIfStatement()
    {
        var area = calculateArea()
        if area == Double.infinity {
            println("something went wrong!")
            return
        }

        if area == 0
        {
            println ("Area is zero")
        }

        else if area == 1
        {
            println ("Area is 1")
        }

        else
        {
            println ("Area is \(area)")
        }
    }

    func printAreaSwitchStatement() {
        var area = calculateArea()
        if area == Double.infinity {
            println("something went wrong!")
            return
        }

        switch area
        {
            case 0:
                println ("Area is zero")
            case 1:
                println ("Area is one sq units")
            default:
                println ("Area is \(area) sq units")
        }
    }

    func funWithLoops() {
        let lengthArray = [0,1,2,3,4]
        let breadthArray = [0,1,2,3,4]

        for var x = 0; x < lengthArray.count; x+=1 {
            println(x)
        }

        for breadth in breadthArray {
            println(breadth)
        }

        while false {
            println("Will never be executed")
        }

        repeat {
            // infinite loop
        } while true
    }

    func moreFunWithLoops()
    {
        let lengthArray = [0,1,2,3,4]
        let breadthArray = [0,1,2,3,4]

        for var x = 0; x < lengthArray.count; x+=1
        {
            println(x)
        }

        for breadth in breadthArray
        {
            println(breadth)
        }

        while false
        {
            println("Will never be executed")
        }

        repeat
        {
            // infinite loop
        } while true
    }

    func multiLineFunction(arg1: String,
                           arg2: String) {
        // do nothing.
   }

   class Shape
   {

   }

   struct Square: Shape
   {
        // square struct
   }

   struct Line {
         func obscureLoops() {
            for ; ; {

            }

            for ;
                ; {

            }

            var x = 1

            for var x = 1 ; ; {

            }

            for ; ;
                x {

            }

            for ; ;
                x
            {

            }
        }
   }
}

class Hello
<T> {
}
class Hello
<T>
{
}
struct Hello
<T>{
}
struct Hello
<T>
   {
   }

protocol SomeProtocol {

}

protocol SomeOtherProtocol : X {

}

protocol
SomeProtocol {

}

protocol SomeProtocol
{

}

protocol
SomeOtherProtocol : X {

}

protocol SomeOtherProtocol
: X {

}

protocol SomeOtherProtocol : X
{

}

enum SomeEnum {
    case SomeCase
}

enum SomeOtherEnum : Int {
    case SomeCase
}

enum SomeEnum
{
    case SomeCase
}

enum SomeOtherEnum : Int
{
    case SomeCase
}

enum SomeOtherEnum :
Int
{
    case SomeCase
}

class SomeClass :
SomeProtocol, SomeOtherProtocol {

}

enum SomeOtherEnum :
Int {
    case SomeCase
}

struct SomeStruct :
SomeProtocol, SomeOtherProtocol {

}

func someFunction () -> ()
{
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

func applyMutliplication(value: Int, multFunction: Int -> Int) -> Int {
  return multFunction(value)
}

applyMutliplication(2,
{ value in
  value * 3
})

applyMutliplication(
2, { value in
  value * 3
})

enum ExampleEnum: Int
{
    case A, B, C = 5, D
}

enum ExampleEnum: Int {
    case A, B, C = 5, D
}

padawans.map()
{
  (padawan: String) -> String in
  "\(padawan) has been trained!"
}

padawans.map
{
  (padawan: String) -> String in
  "\(padawan) has been trained!"
}

func max<T, U : Comparable>(f: T -> U ) -> U? {
    return nil
}

func max<T, U : Comparable>(f: T -> U ) -> U?
{
    return nil
}

enum SomeEnum<A>
{

}

public extension SomeExtension {

}

extension SomeExtension : X {

}

extension SomeExtension
{

}

extension SomeExtension : X
{

}

extension
SomeExtension : X {

}

extension
SomeExtension : X
{

}

set
{
    someVariable = newValue * 2
}

get
{
    return someVariable / 2
}

set {
    someVariable = newValue * 2
}

get {
    return someVariable / 2
}

didSet {
    if someVariable >= 1000  {
       someVariable = 999
    }
}

didSet
{
    if someVariable >= 1000  {
       someVariable = 999
    }
}

willSet
{
    if someVariable >= 1000  {
       someVariable = 999
    }
}

willSet {
    if someVariable >= 1000  {
       someVariable = 999
    }
}
