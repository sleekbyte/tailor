class Rectangle: Shape
{
    let length: Double
    let breadth: Double

    init(length: Double, breadth: Double)
    {
        // store the row and column of the square in the grid
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
}

class Hello
<T> {
}
class Hello
<T>
{
}
struct Hello
<T> {
}
struct Hello
<T>
   {
   }

protocol SomeProtocol {

}

protocol SomeOtherProtocol: X {

}

protocol
SomeProtocol {

}

protocol SomeProtocol
{

}

protocol
SomeOtherProtocol: X {

}

protocol SomeOtherProtocol
: X {

}

protocol SomeOtherProtocol: X
{

}

enum SomeEnum {
    case SomeCase
}

enum SomeOtherEnum: Int {
    case SomeCase
}

enum SomeEnum
{
    case SomeCase
}

enum SomeOtherEnum: Int
{
    case SomeCase
}

enum SomeOtherEnum:
Int
{
    case SomeCase
}

class SomeClass:
SomeProtocol, SomeOtherProtocol {

}

enum SomeOtherEnum:
Int {
    case SomeCase
}

struct SomeStruct:
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

func applyMutliplication(value: Int, multFunction: (Int) -> Int) -> Int {
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

func max<T, U: Comparable>(f: (T) -> U ) -> U? {
    return nil
}

func max<T, U: Comparable>(f: (T) -> U ) -> U?
{
    return nil
}

enum SomeEnum<A>
{

}

public extension SomeExtension {

}

extension SomeExtension: X {

}

extension SomeExtension
{

}

extension SomeExtension: X
{

}

extension
SomeExtension: X {

}

extension
SomeExtension: X
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
    if someVariable >= 1000 {
       someVariable = 999
    }
}

didSet
{
    if someVariable >= 1000 {
       someVariable = 999
    }
}

willSet
{
    if someVariable >= 1000 {
       someVariable = 999
    }
}

willSet {
    if someVariable >= 1000 {
       someVariable = 999
    }
}

class SomeClass {
    var x: Int {
        set {
            self.x = 10
        }

        get {
            return 10
        }
    }
}

class SomeClass {
    var x: Int {
        set
         {
            x = 10
        }

        get
         {
            return 10
        }
    }
}

struct SomeStruct {
    var x: Int {
        set {
            x = 10
        }

        get
         {
            return 10
        }
    }
}

struct SomeStruct {
    var x: Int {
        get
        {
            return 10
        }
    }
}

@objc
class ExampleClass {
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

@objc
class SomeClass {
    var enabled: Bool {
        @objc(isEnabled)
        get
        {
            return true
        }
        set
        {
            self.enabled = true
        }
    }
}

@objc
class SomeClass {
    var enabled: Bool {
        @objc(isEnabled)
        set
        {
            self.enabled = true
        }
        get
        {
            return true
        }
    }
}

struct Rect {
    var center: Point {
        get {
            let centerX = origin.x + (size.width / 2)
            var centerY = origin.y + (size.height / 2)
            return Point(x: centerX, y: centerY)
        }
        set(newCenter) {
            var origin.x = newCenter.x - (size.width / 2)
            var origin.y = newCenter.y - (size.height / 2)
        }
    }
}

struct Rect {
    var center: Point {
        get {
            let centerX = origin.x + (size.width / 2)
            var centerY = origin.y + (size.height / 2)
            return Point(x: centerX, y: centerY)
        }
        set(newCenter)
        {
            var origin.x = newCenter.x - (size.width / 2)
            var origin.y = newCenter.y - (size.height / 2)
        }
    }
}

struct TimesTable {
    let multiplier: Int

    subscript(index: Int) -> Int
    {
        return multiplier * index
    }

    subscript(index: Int) -> Int {
        return multiplier * index
    }

    subscript(index: Int) -> Int {
        return multiplier * index
    }
}

struct Matrix {
    let rows: Int, columns: Int
    var grid: [Double]
    init(rows: Int, columns: Int) {
        self.rows = rows
        self.columns = columns
        grid = Array(count: rows * columns, repeatedValue: 0.0)
    }

    func indexIsValidForRow(row: Int, column: Int) -> Bool {
        return row >= 0 && row < rows && column >= 0 && column < columns
    }

    subscript(row: Int, column: Int) -> Double
        {

        get {
            assert(indexIsValidForRow(row, column: column), "Index out of range")
            return grid[(row * columns) + column]
        }

        set {
            assert(indexIsValidForRow(row, column: column), "Index out of range")
            grid[(row * columns) + column] = newValue
        }
    }

    class StepCounter {
        var totalSteps: Int = 0 {
            willSet(newTotalSteps)
            {
                println("About to set totalSteps to \(newTotalSteps)")
            }
            didSet
            {
                if totalSteps > oldValue {
                    println("Added \(totalSteps - oldValue) steps")
                }
            }
        }
    }

    class StepCounter {
        var totalSteps: Int = 0 {
            willSet(newTotalSteps)
            {
                println("About to set totalSteps to \(newTotalSteps)")
            }
            didSet
            {
                if totalSteps > oldValue {
                    println("Added \(totalSteps - oldValue) steps")
                }
            }
        }
    }

    class StepCounter {
        var totalSteps: Int = 0 {
            willSet(newTotalSteps) {
                println("About to set totalSteps to \(newTotalSteps)")
            }
            didSet {
                if totalSteps > oldValue {
                    println("Added \(totalSteps - oldValue) steps")
                }
            }
        }
    }

    class StepCounter {
        var totalSteps: Int = 0 {
            willSet(newTotalSteps)
            {
                println("About to set totalSteps to \(newTotalSteps)")
            }
        }
    }

    class StepCounter {
        var totalSteps: Int = 0 {
            didSet
            {
                if totalSteps > oldValue {
                    println("Added \(totalSteps - oldValue) steps")
                }
            }
        }
    }

    class StepCounter {
            var totalSteps: Int = 0
            {
                didSet {
                    if totalSteps > oldValue {
                        println("Added \(totalSteps - oldValue) steps")
                    }
                }
            }
    }

    class StepCounter {
        var totalSteps: Int
        = 0 {
            didSet {
                if totalSteps > oldValue {
                    println("Added \(totalSteps - oldValue) steps")
                }
            }
        }
    }

    class StepCounter {

        var totalSteps: Int = 0 {
            willSet {
              printSomething()
            }

            didSet(newValue) {
                if totalSteps > oldValue {
                    println("Added \(totalSteps - oldValue) steps")
                }
            }
        }

        var netSteps: Int = 0 {
            willSet
            {
              printSomething()
            }

            didSet(newValue)
            {
                if totalSteps > oldValue {
                    println("Added \(totalSteps - oldValue) steps")
                }
            }
        }
    }
}
