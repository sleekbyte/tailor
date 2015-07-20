func printAreaSwitchStatement() {
        var area = calculateArea()
        if area == Double.infinity {
            println("something went wrong!")
            return }

        switch area {
            case 0:
                println ("Area is zero")
            case 1:
                println ("Area is one sq units")
            default:
                println ("Area is \(area) sq units") }
}

class SomeClass {
 let x = 2 }

class Hello
<T>: SomeProtocol {
}

class Hello
<T>: SomeProtocol { }

struct SomeStruct {
 let x = 2 }

struct SomeStruct
<T> { }

struct Hello <T> { }

struct Hello
<T> {
}

protocol SomeProtocol {

}

protocol SomeOtherProtocol: X {

}

protocol
SomeProtocol {

}

protocol SomeProtocol { }

protocol
SomeOtherProtocol: X { }

protocol SomeOtherProtocol
: X { }

public extension SomeExtension {

}

extension SomeExtension: X { }

extension
SomeExtension {}

extension SomeExtension: X { }

extension
SomeExtension: X { }

extension
SomeExtension: X {

}

func demo() {}

func max<T, U: Comparable>(f: T -> U ) -> U? {
    return nil
}

func max<T, U: Comparable>(f: T -> U ) -> U? {
    return nil }

protocol SomeProtocol {}

extension SomeExtension: X { }

class SomeClass {/*comment*/}

class SomeClass {}

class SomeClass {/* comment
*/ }

class SomeClass {/* comment space comment
*/ /* comment
  comment */ }

class SomeClass {
/* */}

class SomeClass {
    var x: Int {
        set {
            self.x = 10 }

        get {
            return 10 }
    }
}

struct SomeStruct {
    var x: Int {
        set {
            x = 10
        }

        get {
            return 10
        }
    }
}

struct SomeStruct {
    var x: Int {
        get {
            return 10 }
    }
}

@objc
class ExampleClass {
    var enabled: Bool {
        @objc(isEnabled)
        get {
            return true }
        set {
            self.enabled = true
        }
    }
}

@objc
class SomeClass {
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
    var center: Point {
        get {
            let centerX = origin.x + (size.width / 2)
            var centerY = origin.y + (size.height / 2)
            return Point(x: centerX, y: centerY)
        }
        set(newCenter) {
            var origin.x = newCenter.x - (size.width / 2)
            var origin.y = newCenter.y - (size.height / 2) }
    }
}
