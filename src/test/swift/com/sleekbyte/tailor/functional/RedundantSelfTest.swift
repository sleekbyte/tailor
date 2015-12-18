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

extension String {
    var localized: String {
        return NSLocalizedString(self, tableName: nil, bundle: NSBundle.mainBundle(), value: "", comment: "")
    }
}

public override func respondsToSelector(selector: Selector) -> Bool {
    switch selector {
    case "URLSession:didBecomeInvalidWithError:":
        return sessionDidBecomeInvalidWithError != nil
    case "URLSession:didReceiveChallenge:completionHandler:":
        return sessionDidReceiveChallenge != nil
    case "URLSessionDidFinishEventsForBackgroundURLSession:":
        return sessionDidFinishEventsForBackgroundURLSession != nil
    case "URLSession:task:willPerformHTTPRedirection:newRequest:completionHandler:":
        return taskWillPerformHTTPRedirection != nil
    case "URLSession:dataTask:didReceiveResponse:completionHandler:":
        return dataTaskDidReceiveResponse != nil
    default:
        return self.dynamicType.instancesRespondToSelector(selector)
    }
}
