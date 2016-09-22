class Cl {
}

let checkElementNameLength: (ename: Int, veryLongElementName: Int) = (1, 2)

enum Enu: Int {
    case EnumCaseA, BIsAVeryLongEnumCaseName, C = 5, D
}

func f (ep lp: Int) -> Bool {
    return true
}

let myStr = "hello"
sl: switch myStr {
    case "hello":
        print("yes")
    case "hi":
        print("no")
    default:
    print("both")
}

protocol Z {
}

struct R {
    var mid: Int {
        get {
            return 1
        }
        set(ct) {
            mid = ct
        }
    }
}

typealias mn = String

struct Rect { // test enterVariableName
    var v: Point {
        get {
            return Point(12, 144)
        }
    }
}

func myFunctionNameIsAlsoLong(longExternalParameterName aLongLocalParameterName: In) -> Bool {
    return true
}

let C = 1
var thisIsALongVariableName = 0

enum SomeEnum: Int {
    case C
    init (value: NSNumber) {
        switch value.integerValue {
        case C:
             self = Some_Enum_Case
        }
    }
}
