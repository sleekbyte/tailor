import Foundation;

println("Hello, World!");

enum CompassPoint {
    case North;
    case South;
    case East;
    case West;
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

        // for loop
        for ; ; {
        };
    }

};