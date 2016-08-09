enum CompassPoint {
    case North
    case South
    case East
    case West
}

enum Planet {
    case Mercury, Venus, Earth, Mars, Jupiter, Saturn, Uranus, Neptune
}

var directionToHead = CompassPoint.West

directionToHead = .East

directionToHead = .South
switch directionToHead {
case .North:
    print("Lots of planets have a north")
case .South:
    print("Watch out for penguins")
case .East:
    print("Where the sun rises")
case .West:
    print("Where the skies are blue")
}

let somePlanet = Planet.Earth
switch somePlanet {
case .Earth:
    print("Mostly harmless")
default:
    print("Not a safe place for humans")
}


enum Barcode {
    case UPCA(Int, Int, Int, Int)
    case QRCode(String)
}

var productBarcode = Barcode.UPCA(8, 85909, 51226, 3)

productBarcode = .QRCode("ABCDEFGHIJKLMNOP")

switch productBarcode {
case .UPCA(let numberSystem, let manufacturer, let product, let check):
    print("UPC-A: \(numberSystem), \(manufacturer), \(product), \(check).")
case .QRCode(let productCode):
    print("QR code: \(productCode).")
}

switch productBarcode {
case let .UPCA(numberSystem, manufacturer, product, check):
    print("UPC-A: \(numberSystem), \(manufacturer), \(product), \(check).")
case let .QRCode(productCode):
    print("QR code: \(productCode).")
}

enum ASCIIControlCharacter: Character {
    case Tab = "\t"
    case LineFeed = "\n"
    case CarriageReturn = "\r"
}

enum Planet: Int {
    case Mercury = 1, Venus, Earth, Mars, Jupiter, Saturn, Uranus, Neptune
}

let earthsOrder = Planet.Earth.rawValue

let possiblePlanet = Planet(rawValue: 7)

let positionToFind = 9
if let somePlanet = Planet(rawValue: positionToFind) {
    switch somePlanet {
    case .Earth:
        print("Mostly harmless")
    default:
        print("Not a safe place for humans")
    }
} else {
    print("There isn't a planet at position \(positionToFind)")
}

public enum ImageTransition {
    case None
    case CrossDissolve(NSTimeInterval)
    case CurlDown(NSTimeInterval)
    case CurlUp(NSTimeInterval)
    case FlipFromBottom(NSTimeInterval)
    case FlipFromLeft(NSTimeInterval)
    case FlipFromRight(NSTimeInterval)
    case FlipFromTop(NSTimeInterval)

    private var duration: NSTimeInterval {
        switch self {
        case None:                         return 0.0
        case CrossDissolve(let duration):  return duration
        case CurlDown(let duration):       return duration
        case CurlUp(let duration):         return duration
        case FlipFromBottom(let duration): return duration
        case FlipFromLeft(let duration):   return duration
        case FlipFromRight(let duration):  return duration
        case FlipFromTop(let duration):    return duration
        }
    }

    private var animationOptions: UIViewAnimationOptions {
        switch self {
        case None:           return .TransitionNone
        case CrossDissolve:  return .TransitionCrossDissolve
        case CurlDown:       return .TransitionCurlDown
        case CurlUp:         return .TransitionCurlUp
        case FlipFromBottom: return .TransitionFlipFromBottom
        case FlipFromLeft:   return .TransitionFlipFromLeft
        case FlipFromRight:  return .TransitionFlipFromRight
        case FlipFromTop:    return .TransitionFlipFromTop
        }
    }
}

public enum Code: Int {
    case InputStreamReadFailed           = -6000
    case OutputStreamWriteFailed         = -6001
    case ContentTypeValidationFailed     = -6002
    case StatusCodeValidationFailed      = -6003
    case DataSerializationFailed         = -6004
    case StringSerializationFailed       = -6005
    case JSONSerializationFailed         = -6006
    case PropertyListSerializationFailed = -6007
}

internal indirect enum Either<L, R> {
	case Left(L)
	case Right(R)
}

public enum SystemError: ErrorType {
    case chdir(Int32)
    case close(Int32)
    case dirfd(Int32, String)
    case fopen(Int32, String)
    case fputs
    case fgetc(Int32)
    case fread(Int32)
    case getcwd(Int32)
    case mkdir(Int32, String)
    case mkdtemp(Int32)
    case opendir(Int32, String)
    case pipe(Int32)
    case popen(Int32, String)
    case posix_spawn(Int32, [String])
    case read(Int32)
    case readdir(Int32, String)
    case readlink(Int32, String)
    case realpath(Int32, String)
    case rename(Int32, old: String, new: String)
    case rmdir(Int32, String)
    case stat(Int32, String)
    case symlinkat(Int32, String)
    case unlink(Int32, String)
    case waitpid(Int32)
}

func == (l : Exp, r : Exp) -> Bool {
	switch (l, r) {
	case let (.lam(ln, le), .lam(rn, re)):
		return ln == rn && le == re
	case let (.app(ln, le), .app(rn, re)):
		return ln == rn && le == re
	case let (.var(n1), .var(n2)):
		return n1 == n2
	default:
		return false
	}
}
