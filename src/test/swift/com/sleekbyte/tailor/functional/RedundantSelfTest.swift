import Foundation

class SomeClass {
    let x = 2
    let y = 10

    init() {
        print(self.x)
        self.demo(2, y: "2")
    }

    func demo(d: Int, y: String) {
        if self.x == 2 {
            print(self.x)
        } else {
            print(self.y)
        }
    }

    func anotherDemoMethod(x: Int) {
        print(self.x)
        print(self.y)
    }

    func callDemoTwice() {
        demo(x, y: "2")
        self.demo(self.x, y: "2")
    }

}

private class History {
    var events: [String]

    init(events: [String]) {
        self.events = events
    }

    func rewrite() {
        self.events = []
    }

}

extension History {

    var whenVictorious: () -> () {
        return {
            self.rewrite()
        }
    }
}
