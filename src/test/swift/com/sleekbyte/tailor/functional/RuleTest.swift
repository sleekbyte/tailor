import Foundation; import Cocoa

let kMaximum = 42
let my_constant = 12

class SomeClass {
    // class definition goes here
    func timesTwo(myValue: Int) -> Int {
        let my_local_constant = 7
        return my_local_constant + myValue
    }
}

enum someEnumeration {
    // enumeration definition goes here
};

enum compassPoint {
    case North
    case South;
    case East
    case west
}

struct someStructure {
    // structure definition goes here
}

protocol someProtocol {
    // protocol definition goes here
}
//
var airports = [("YYZ"): "Toronto"]
var counter = (0)
//
// first line over max-file-length limit
while (counter < 1) {
    counter++
}
// missing trailing newline