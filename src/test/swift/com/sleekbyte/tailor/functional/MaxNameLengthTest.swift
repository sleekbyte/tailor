class CheckSuperLongClassName {
}

let checkElementNameLength: (ename: Int, veryLongElementName: Int) = (1, 2)

enum PrettyLongExampleEnum: Int {
    case A, BIsAVeryLongEnumCaseName, C = 5, D
}

func myFunctionNameIsTooLong(longExternalParameterName aLongLocalParameterName: Int) -> Bool {
    return true
}

let myStr = "hello"
mySwitchLabel: switch myStr {
    case "hello":
        print("yes")
    case "hi":
        print("no")
    default:
    print("both")
}

protocol TooLongProtocolHere {
}

struct RectIsAPrettyLongName {
    var mid: Int {
        get {
            return 1
        }
        set(newCenterTooLong) {
            mid = newCenterTooLong
        }
    }
}

typealias LongNameMusicalNote = String

struct Rect { // test enterVariableName
    var thisIsALongVariable: Point {
        get {
            return Point(12, 144)
        }
    }
}

func myFunctionNameIsAlsoLong(longExternalParameterName aLongLocalParameterName: Int) -> Bool {
    return true
}

let thisIsALongConstantName = 1
var thisIsALongVariableName = 0

enum SomeEnum: Int {
    case SomeLongEnumCaseName
    init (value: NSNumber) {
        switch value.integerValue {
        case SomeLongEnumCaseName:
             self = Some_Enum_Case
        }
    }
}
