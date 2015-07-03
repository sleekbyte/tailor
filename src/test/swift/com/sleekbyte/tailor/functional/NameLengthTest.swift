class CheckSuperLongClassName {
}

let checkElementNameLength:(ename:Int, veryLongElementName:Int) = (1, 2)

enum PrettyLongExampleEnum: Int {
    case A, BIsAVeryLongEnumCaseName, C = 5, D
}

func myFunctionNameIsTooLong(longExternalParameterName aLongLocalParameterName: Int) -> Bool {
    return true
}

let myString = "hello"
mySwitchLabel: switch myString {
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

var thisIsALongVariableName = 0
let thisIsALongConstantName = 1
