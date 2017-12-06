extension NSURLSessionConfiguration {
    static func backgroundSessionConfigurationForAllPlatformsWithIdentifier(identifier: String) -> NSURLSessionConfiguration {
        let configuration: NSURLSessionConfiguration

        #if os(iOS) || os(watchOS)
            configuration = NSURLSessionConfiguration.backgroundSessionConfigurationWithIdentifier(identifier)
        #else
            if #available(OSX 10.10, *) {
                configuration = NSURLSessionConfiguration.backgroundSessionConfigurationWithIdentifier(identifier)
            } else {
                configuration = NSURLSessionConfiguration.backgroundSessionConfiguration(identifier)
            }
        #endif

        return configuration
    }
}

#if !os(watchOS)

@available(iOS 9.0, OSX 10.11, *)
extension Manager {
    private enum Streamable {
        case Stream(String, Int)
        case NetService(NSNetService)
    }

    private func stream(streamable: Streamable) -> Request {
        var streamTask: NSURLSessionStreamTask!

        switch streamable {
        case .Stream(let hostName, let port):
            dispatch_sync(queue) {
                streamTask = self.session.streamTaskWithHostName(hostName, port: port)
            }
        case .NetService(let netService):
            dispatch_sync(queue) {
                streamTask = self.session.streamTaskWithNetService(netService)
            }
        }

        let request = Request(session: session, task: streamTask)

        delegate[request.delegate.task] = request.delegate

        if startRequestsImmediately {
            request.resume()
        }

        return request
    }
}

#endif

self.totalBytes = {
    #if os(iOS) || os(watchOS)
        let size = CGSize(width: image.size.width * image.scale, height: image.size.height * image.scale)
    #elseif os(OSX)
        let size = CGSize(width: image.size.width, height: image.size.height)
    #endif

    let bytesPerPixel: CGFloat = 4.0
    let bytesPerRow = size.width * bytesPerPixel
    let totalBytes = UInt64(bytesPerRow) * UInt64(size.height)

    return totalBytes
}()

#if arch(x86_64)
  let bytesPerPixel: CGFloat = 4.0
#elseif arch(arm) || arch(arm64)
  let bytesPerPixel: CGFloat = 4.0
#else
  let bytesPerPixel: CGFloat = 3.0
#endif

public var isRunningOnDevice: Bool = {
	#if TARGET_IPHONE_SIMULATOR
		return false
	#else
		return true
	#endif
}()

func test() {
    let timer = NSTimer(timeInterval: 1, target: object,
                        selector: #selector(MyClass.test),
                        userInfo: nil, repeats: false)
    button.addTarget(object, action: #selector(MyClass.buttonTapped),
                     forControlEvents: .TouchUpInside)
    view.performSelector(#selector(UIView.insertSubview(_:aboveSubview:)),
                         withObject: button, withObject: otherButton)
}

var validSwiftVersion: Bool = {
  #if swift(>= 2.2)
    true
  #else
    false
  #endif
}

@objc class SomeClass: NSObject {
    var someProperty: Int
    init(someProperty: Int) {
        self.someProperty = someProperty
    }
    func keyPathTest() -> String {
        return #keyPath(someProperty)
    }
    func keyPathTestForSwift4() -> KeyPath<SomeClass, Int> {
        return \SomeClass.someProperty
    }
}

let c = SomeClass(someProperty: 12)
let keyPath = #keyPath(SomeClass.someProperty)

#sourceLocation(file: "foo", line: 42)
#sourceLocation()    // reset to original position.

class Person: NSObject {
    dynamic var firstName: String
    dynamic let lastName: String
    dynamic var fullName: String {
        return "\(firstName) \(lastName)"
    }

    init(firstName: String, lastName: String) {
        self.firstName = firstName
        self.lastName = lastName
    }
}

let firstNameGetter = #selector(getter: Person.firstName)
let firstNameSetter = #selector(setter: Person.firstName)

#colorLiteral(red: 2, green: 2, blue: 2, alpha: 100)
