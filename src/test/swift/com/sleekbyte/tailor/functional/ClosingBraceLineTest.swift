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

struct Hello <T> { }

struct Hello
<T>{
}

struct Hello
<T> { }