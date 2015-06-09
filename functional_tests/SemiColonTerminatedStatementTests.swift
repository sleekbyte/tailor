import Foundation;

println("Hello, World!")

enum CompassPoint {
    case North;
    case South;
    case East;
    case West;
};

protocol SomeProtocol {
    var fullName: String { get };
    func demo();
    func dummy(f: Int);
    func foo(bar: String, baz: Double);
};

// Class examples

class lowerCamelCase {
    var x: String = "hello";
    let b = 2;

    func demo()
    {
        for var x = 0; ; {
            print(x);
        };

        if temperatureInFahrenheit <= 32 {
            println("It's very cold. Consider wearing a scarf.");
        } else if temperatureInFahrenheit >= 86 {
            println("It's really warm. Don't forget to wear sunscreen.");
        } else {
            println("It's not that cold. Wear a t-shirt.");
        }
    };
};

