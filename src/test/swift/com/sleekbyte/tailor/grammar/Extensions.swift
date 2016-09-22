extension SomeType {
    // new functionality to add to SomeType goes here
}

extension SomeType: SomeProtocol, AnotherProtocol {
    // implementation of protocol requirements goes here
}

extension Double {
    var km: Double { return self * 1_000.0 }
    var m: Double { return self }
    var cm: Double { return self / 100.0 }
    var mm: Double { return self / 1_000.0 }
    var ft: Double { return self / 3.28084 }
}
let oneInch = 25.4.mm

extension Rect {
    init(center: Point, size: Size) {
        let originX = center.x - (size.width / 2)
        let originY = center.y - (size.height / 2)
        self.init(origin: Point(x: originX, y: originY), size: size)
    }
}

extension Int {
    func repetitions(task: () -> ()) {
        for _ in 0..<self {
            task()
        }
    }
}

3.repetitions {
    print("Goodbye!")
}

extension Int {
    mutating func square() {
        self = self * self
    }
}

extension Int {
    subscript(digitIndex: Int) -> Int {
        var decimalBase = 1
        for _ in 0..<digitIndex {
            decimalBase *= 10
        }
        return (self / decimalBase) % 10
    }
}

extension Int {
    enum Kind {
        case Negative, Zero, Positive
    }
    var kind: Kind {
        switch self {
        case 0:
            return .Zero
        case let x where x > 0:
            return .Positive
        default:
            return .Negative
        }
    }
}

extension ImageFilter where Self: Roundable {
    /// The unique idenitifier for an `ImageFilter` conforming to the `Roundable` protocol.
    public var identifier: String {
        let radius = Int64(round(self.radius))
        return "\(self.dynamicType)-radius:(\(radius))"
    }
}

extension ImageFilter where Self: Sizable, Self: Roundable {
    /// The unique idenitifier for an `ImageFilter` conforming to both the `Sizable` and `Roundable` protocols.
    public var identifier: String {
        let width = Int64(round(size.width))
        let height = Int64(round(size.height))
        let radius = Int64(round(self.radius))

        return "\(self.dynamicType)-size:(\(width)x\(height))-radius:(\(radius))"
    }
}

@available(iOS 9.0, OSX 10.11, *)
extension Manager {
    private enum Streamable {
        case Stream(String, Int)
        case NetService(NSNetService)
    }
}

extension Array {
    subscript (safe index: Int) -> Element? {
        return index >= 0 && index < count ? self[index] : nil
    }
}

@available(iOS 9.0, OSX 10.11, tvOS 9.0, *)
extension Manager.SessionDelegate: NSURLSessionStreamDelegate {}

extension Integer {
	/// Shrinks any `IntegerType`.
	public var shrinkIntegral : [Self] {
		return unfoldr({ i in
			if i <= 0 {
				return .none
			}
			let n = i / 2
			return .some((n, n))
		}, initial: self < 0 ? (Self.multiplyWithOverflow(self, -1).0) : self)
	}
}
