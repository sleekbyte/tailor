import Foundation;

println("Hello, World!");

enum CompassPoint {
    case north;
    case south;
    case east;
    case west;
};

protocol SomeProtocol {
    var fullName: String { get };
    func validFunctionName();
    func dummy(f: Int);
    func foo(bar: String, baz: Double);
};


struct invalidStructName {
    var x: String;

    func test() {

        // while loop test
        while true {
        };
    }

};